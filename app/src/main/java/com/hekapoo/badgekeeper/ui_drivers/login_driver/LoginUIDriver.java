package com.hekapoo.badgekeeper.ui_drivers.login_driver;

import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.DatabaseCore;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.network_module.NetworkConnection;
import com.hekapoo.badgekeeper.modules.network_module.NetworkCore;
import com.hekapoo.badgekeeper.modules.utils_module.SettingsSchema;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;
import com.hekapoo.badgekeeper.modules.validation_module.ValidatorCore;
import com.hekapoo.badgekeeper.ui_drivers.create_account_driver.CreateAccount1UIDriver;
import com.hekapoo.badgekeeper.ui_drivers.dashboard_driver.DashboardUIDriver;
import com.hekapoo.badgekeeper.ui_drivers.recovery_driver.RecoveryUIDriver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

/*
 * Main class handling UI for login process.
 */
public class LoginUIDriver extends AppCompatActivity {

    private EditText emailET, passwordET;
    private TextView loginBTN, createAccountBTN, recoveryBTN;
    private TextView errorView;
    private ImageView fingerprintBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login_screen);

        emailET = findViewById(R.id.auth_email_et);
        passwordET = findViewById(R.id.auth_password_et);
        loginBTN = findViewById(R.id.app_auth_login_btn);
        createAccountBTN = findViewById(R.id.app_create_acc_btn);
        recoveryBTN = findViewById(R.id.app_forgot_pass_btn);
        fingerprintBTN = findViewById(R.id.fingerprint_login);
        errorView = findViewById(R.id.app_error_tv);

        //logs in using the last account that was in use on this phone
        fingerprintBTN.setOnClickListener(e -> {

            //load local app settings
            SettingsSchema settings = LocalDatabase.getInstance().getLocalSettings(LoginUIDriver.this);

            //if setting is enabled
            if (settings.isFingerprintLogin()) {
                BiometricManager biometricManager = BiometricManager.from(LoginUIDriver.this);
                boolean fingerEnrolled = true;

                //check if we have a fingerprint enrolled in our phone
                switch (biometricManager.canAuthenticate()) {
                    case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED: //proceed
                        Toast.makeText(LoginUIDriver.this, "No fingerprint enrolled.Go to settings to enroll one!", Toast.LENGTH_LONG).show();
                        fingerEnrolled = false;
                    default:
                        break;
                }

                //if we do, try to login
                if (fingerEnrolled) {

                    //extract data needed for login
                    UserSchema user = LocalDatabase.getInstance().loadUserLocally(LoginUIDriver.this);

                    Executor executor = ContextCompat.getMainExecutor(this);

                    BiometricPrompt biometricPrompt = new BiometricPrompt(LoginUIDriver.this, executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            Toast.makeText(LoginUIDriver.this, "Error occurred", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);

                            //try to login user and redirect to dashboard
                            FirebaseDB.getInstance().loginUser(user.getEmail(), user.getPassword(), success -> {
                                if (success) {
                                    Intent intent = new Intent(LoginUIDriver.this, DashboardUIDriver.class);
                                    startActivity(intent);
                                    Log.d("finger", "logged in");
                                } else
                                    Log.d("finger", "finger ok login fail");

                            });
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            Toast.makeText(LoginUIDriver.this, "Error occurred", Toast.LENGTH_LONG).show();
                        }
                    });

                    BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Login")
                            .setDescription("Use finger")
                            .setNegativeButtonText("Cancel")
                            .build();

                    biometricPrompt.authenticate(promptInfo);
                }
            } else {
                Toast.makeText(LoginUIDriver.this, "Fingerprint login not enabled!", Toast.LENGTH_LONG).show();
            }
        });

        loginBTN.setOnClickListener(e -> {

            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();

            //validate data with db
            if (ValidatorCore.getInstance().login(password, email, errorView))
                Log.d("TAG", "onCreate: ");
            //decrypt password
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] bytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                password = sb.toString();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }

            //try to log the user in
            FirebaseDB.getInstance().loginUser(email, password, loggedIn -> {
                if (loggedIn) {

                    Intent intent = new Intent(this, DashboardUIDriver.class);

                    //get user data from fb db to pass to dashboard
                    FirebaseDB.getInstance().getUserFirebase(resultUser -> {
                        if (resultUser != null) {
                            LocalDatabase.getInstance().saveUserLocally(resultUser, this);
                            startActivity(intent);
                        } else
                            errorView.setText("Database error,please try later!");
                    });
                } else {
                    errorView.setVisibility(View.VISIBLE);
                    errorView.setText("Invalid credentials!");
                }
            });
        });

        createAccountBTN.setOnClickListener(e -> {
            //launch create account activity
            Intent intent = new Intent(this, CreateAccount1UIDriver.class);
            startActivity(intent);
        });

        recoveryBTN.setOnClickListener(e -> {
            //recover password activity
            Intent intent = new Intent(this, RecoveryUIDriver.class);
            startActivity(intent);
        });


        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //TODO: HAS bug,doesnt always verify,only on change
        //Check internet connectivity
        NetworkCore.getInstance().hasInternetCallback(this, LoginUIDriver.this, connected -> {
            if (connected) {
                errorView.setVisibility(View.INVISIBLE);
                loginBTN.setClickable(true);
            } else {
                errorView.setVisibility(View.VISIBLE);
                errorView.setText("No internet connection");
                loginBTN.setClickable(false);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

}
