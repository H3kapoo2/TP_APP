package com.hekapoo.badgekeeper.ui_drivers.login_driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.DatabaseCore;
import com.hekapoo.badgekeeper.modules.network_module.NetworkConnection;
import com.hekapoo.badgekeeper.modules.network_module.NetworkCore;
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

        loginBTN.setOnClickListener(e -> {
            //validate data with database
            //launch intent or throw error
            Intent intent = new Intent(this, DashboardUIDriver.class);
            startActivity(intent);
            //ceva
        });

        createAccountBTN.setOnClickListener(e->{
            //launch create account activity
            Intent intent = new Intent(this, CreateAccount1UIDriver.class);
            startActivity(intent);
        });

        recoveryBTN.setOnClickListener(e->{
            //recover password activity
            Intent intent = new Intent(this, RecoveryUIDriver.class);
            startActivity(intent);
        });
    }
}
