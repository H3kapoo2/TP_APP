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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.DatabaseCore;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.network_module.NetworkConnection;
import com.hekapoo.badgekeeper.modules.network_module.NetworkCore;
import com.hekapoo.badgekeeper.modules.validation_module.ValidatorCore;
import com.hekapoo.badgekeeper.ui_drivers.create_account_driver.CreateAccount1UIDriver;
import com.hekapoo.badgekeeper.ui_drivers.dashboard_driver.DashboardUIDriver;
import com.hekapoo.badgekeeper.ui_drivers.recovery_driver.RecoveryUIDriver;

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

        //todo: replace this with actual fingerprint, this could play the role of keep me logged in
//        fingerprintBTN.setOnClickListener(e->{
//
//            if(LocalDatabase.getInstance().getLocalSettings(this).isFingerprintLogin()){
//
//                String email = LocalDatabase.getInstance().loadUserLocally(this).getEmail();
//                String password =LocalDatabase.getInstance().loadUserLocally(this).getPassword();
//
//                //try to log the user in
//                FirebaseDB.getInstance().loginUser(email, password, loggedIn -> {
//                    if (loggedIn) {
//
//                        Intent intent = new Intent(this, DashboardUIDriver.class);
//
//                        //get user data from fb db to pass to dashboard
//                        FirebaseDB.getInstance().getUserFirebase(resultUser -> {
//                            if (resultUser != null) {
//                                LocalDatabase.getInstance().saveUserLocally(resultUser, this);
//                                startActivity(intent);
//                            }
//                        });
//                    }
//                });
//            }else{
//                errorView.setVisibility(View.VISIBLE);
//                errorView.setText("Fingerprint login not enabled!");
//            }
//        });

        loginBTN.setOnClickListener(e -> {

            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();

            //validate data with db
            if (ValidatorCore.getInstance().login(password, email, errorView))
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
