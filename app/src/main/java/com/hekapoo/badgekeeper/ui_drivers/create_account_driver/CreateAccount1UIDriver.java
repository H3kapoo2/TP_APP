package com.hekapoo.badgekeeper.ui_drivers.create_account_driver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.ui_drivers.login_driver.LoginUIDriver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class CreateAccount1UIDriver extends AppCompatActivity {

    private TextView errorTV;
    private TextView nextBTN,backBTN;
    private EditText emailTV,passwordTV,confirmPassTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_newaccount_screen_1);

        errorTV = findViewById(R.id.app_error_tv);
        emailTV = findViewById(R.id.auth_email_et);
        passwordTV = findViewById(R.id.auth_password_et);
        confirmPassTV = findViewById(R.id.auth_password2_et);

        nextBTN = findViewById(R.id.app_newacc_next_btn);
        backBTN = findViewById(R.id.app_newacc_back_btn);

        backBTN.setOnClickListener(e->{
            Intent intent = new Intent(getApplicationContext(), LoginUIDriver.class);
            startActivity(intent);
        });

        nextBTN.setOnClickListener(e->{

            String email = emailTV.getText().toString().trim();
            String password = passwordTV.getText().toString().trim();
            String confirmedPass = confirmPassTV.getText().toString().trim();

            //validate data

            //send & start new intent
            Intent intent = new Intent(getApplicationContext(),CreateAccount2UIDriver.class);
            intent.putExtra("email",email);
            intent.putExtra("password",password);
            startActivity(intent);
        });

    }
}
