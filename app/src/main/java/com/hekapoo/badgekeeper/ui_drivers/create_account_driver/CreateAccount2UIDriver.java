package com.hekapoo.badgekeeper.ui_drivers.create_account_driver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.user_module.UserSchema;
import com.hekapoo.badgekeeper.ui_drivers.dashboard_driver.DashboardUIDriver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount2UIDriver extends AppCompatActivity {

    TextView errorTV;
    EditText localizationET, departmentET, cardIDET, cardNumberET;
    TextView registerBTN, backBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_newaccount_screen_2);

        errorTV = findViewById(R.id.app_error_tv);

        localizationET = findViewById(R.id.newacc_local_et);
        departmentET = findViewById(R.id.newacc_dept_et);
        cardIDET = findViewById(R.id.newacc_auth_cardid_et);
        cardNumberET = findViewById(R.id.newacc_card_number_et);

        registerBTN = findViewById(R.id.app_newacc_next_btn);
        backBTN = findViewById(R.id.app_newacc_back_btn);

        backBTN.setOnClickListener(e->{
            Intent intent = new Intent(getApplicationContext(),CreateAccount1UIDriver.class);
            startActivity(intent);
        });

        registerBTN.setOnClickListener(e -> {

            //retrieve data from previous intent
            Intent intent = getIntent();

            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");

            //validate rest of inputs
            String localization = localizationET.getText().toString().trim();
            String department = departmentET.getText().toString().trim();
            String cardID = cardIDET.getText().toString().trim();
            String cardNumber = cardNumberET.getText().toString().trim();

            //create user schema on success
            UserSchema newUser = new UserSchema(email, department, localization, cardID, cardNumber);

            FirebaseDB.getInstance().registerNewUser(email, password, newUser, isSuccessful -> {

                //on successful register,redirect to dashboard
                if (isSuccessful) {
                    Intent intentDashboard = new Intent(getApplicationContext(), DashboardUIDriver.class);
                    startActivity(intentDashboard);
                } else
                    Log.d("FB_REGISTER", "failed");
            });

        });

    }
}
