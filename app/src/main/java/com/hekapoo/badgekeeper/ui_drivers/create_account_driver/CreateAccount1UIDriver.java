package com.hekapoo.badgekeeper.ui_drivers.create_account_driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.network_module.NetworkCore;
import com.hekapoo.badgekeeper.modules.validation_module.Validator;
import com.hekapoo.badgekeeper.ui_drivers.login_driver.LoginUIDriver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class CreateAccount1UIDriver extends AppCompatActivity {

    private TextView errorTV;
    private TextView nextBTN, backBTN;
    private EditText emailTV, passwordTV, confirmPassTV;

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

        emailTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorTV.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorTV.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmPassTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorTV.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backBTN.setOnClickListener(e -> {
            Intent intent = new Intent(getApplicationContext(), LoginUIDriver.class);
            startActivity(intent);
        });

        //Check internet connectivity
        NetworkCore.getInstance().hasInternetCallback(this, CreateAccount1UIDriver.this, connected -> {
            if (connected) {
                errorTV.setVisibility(View.INVISIBLE);
                nextBTN.setClickable(true);
            } else {
                errorTV.setVisibility(View.VISIBLE);
                errorTV.setText("No internet connection");
                nextBTN.setClickable(false);
            }
        });

        nextBTN.setOnClickListener(e -> {

            String email = emailTV.getText().toString().trim();
            String password = passwordTV.getText().toString().trim();
            String confirmedPass = confirmPassTV.getText().toString().trim();

            //validate data
            Validator validator = Validator.getInstance();

            validator.emailAsync(email, errorTV, success -> {
                if (success && validator.password(password, confirmedPass, errorTV)) {
                    //send & start new intent
                    Intent intent = new Intent(getApplicationContext(), CreateAccount2UIDriver.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            });
        });

    }
}
