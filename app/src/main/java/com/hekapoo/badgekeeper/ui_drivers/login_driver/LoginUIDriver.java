package com.hekapoo.badgekeeper.ui_drivers.login_driver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.DatabaseCore;
import com.hekapoo.badgekeeper.ui_drivers.dashboard_driver.DashboardUIDriver;

/*
 * Main class handling UI for login process.
 */
public class LoginUIDriver extends AppCompatActivity {

    private EditText emailET,passwordET;
    private TextView loginBTN,createAccountBTN,recoveryBTN;
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

        loginBTN.setOnClickListener(e->{
            //validate data with database
            //launch intent or throw error
            Intent intent = new Intent(this, DashboardUIDriver.class);
            startActivity(intent);
            //ceva
        });

    }
}
