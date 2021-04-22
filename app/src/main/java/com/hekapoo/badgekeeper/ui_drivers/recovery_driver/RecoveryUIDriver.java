package com.hekapoo.badgekeeper.ui_drivers.recovery_driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.validation_module.ValidatorCore;
import com.hekapoo.badgekeeper.ui_drivers.login_driver.LoginUIDriver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecoveryUIDriver extends AppCompatActivity {

    TextView errorTV, sendBTN, backBTN;
    EditText emailET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login_pass_recovery);

        errorTV = findViewById(R.id.app_error_tv);
        sendBTN = findViewById(R.id.app_auth_reco_send_btn);
        backBTN = findViewById(R.id.app_auth_reco_back_btn);
        emailET = findViewById(R.id.auth_email_tv);


        sendBTN.setOnClickListener(e->{
            String email = emailET.getText().toString().trim();

            if(ValidatorCore.getInstance().email(email)){
                FirebaseDB.getInstance().sendRecoveryEmail(email,success->{
                    if(success){
                        Toast.makeText(this,"Password reset email sent!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this,LoginUIDriver.class);
                        startActivity(intent);
                    }else{
                        errorTV.setVisibility(View.VISIBLE);
                        errorTV.setText("Error sending email! Try again!");
                    }
                });
            }else{
                errorTV.setVisibility(View.VISIBLE);
                errorTV.setText("Error sending email! Try again!");
            }
        });

        backBTN.setOnClickListener(e->{
            Intent intent = new Intent(this, LoginUIDriver.class);
            startActivity(intent);
        });
        emailET.addTextChangedListener(new TextWatcher() {
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
    }
}
