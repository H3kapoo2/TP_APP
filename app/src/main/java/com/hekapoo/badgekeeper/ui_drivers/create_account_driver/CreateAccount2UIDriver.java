package com.hekapoo.badgekeeper.ui_drivers.create_account_driver;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.network_module.NetworkCore;
import com.hekapoo.badgekeeper.modules.utils_module.DialogUtils;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;
import com.hekapoo.badgekeeper.modules.validation_module.ValidatorCore;
import com.hekapoo.badgekeeper.ui_drivers.dashboard_driver.DashboardUIDriver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount2UIDriver extends AppCompatActivity {

    TextView errorTV;
    EditText cardIDET, cardNumberET;
    TextView registerBTN, backBTN, localizationTV, departmentTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_newaccount_screen_2);

        errorTV = findViewById(R.id.app_error_tv);

        localizationTV = findViewById(R.id.newacc_local_tv);
        departmentTV = findViewById(R.id.newacc_dept_tv);
        cardIDET = findViewById(R.id.newacc_auth_cardid_et);
        cardNumberET = findViewById(R.id.newacc_card_number_et);

        registerBTN = findViewById(R.id.app_newacc_next_btn);
        backBTN = findViewById(R.id.app_newacc_back_btn);

        backBTN.setOnClickListener(e -> {
            Intent intent = new Intent(getApplicationContext(), CreateAccount1UIDriver.class);
            startActivity(intent);
        });


        //localization on click dialog picker
        localizationTV.setOnClickListener(e -> {
            DialogUtils.getInstance().buildAndShowPickerDialog("Localization",
                    LocalDatabase.getInstance().getLocalizationArray(),
                    CreateAccount2UIDriver.this,
                    localizationTV);
        });

        //department on click dialog picker
        departmentTV.setOnClickListener(e -> {
            DialogUtils.getInstance().buildAndShowPickerDialog("Department",
                    LocalDatabase.getInstance().getDepartmentArray(),
                    CreateAccount2UIDriver.this,
                    departmentTV);
        });

        //TODO: Normally start scan badge activity and only if that's successful we should start the dashboard
        registerBTN.setOnClickListener(e -> {

            //retrieve data from previous intent
            Intent intent = getIntent();
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");

            //get & validate data
            String localization = localizationTV.getText().toString().trim();
            String department = departmentTV.getText().toString().trim();
            String cardID = cardIDET.getText().toString().trim();
            String cardNumber = cardNumberET.getText().toString().trim();

            //create user schema on success
            UserSchema newUser = new UserSchema(email, department, localization, cardID, cardNumber);

            ValidatorCore validator = ValidatorCore.getInstance();

            if (validator.userSchema(newUser, errorTV))
                FirebaseDB.getInstance().registerNewUser(email, password, newUser, isSuccessful -> {
                    //on successful register,redirect to dashboard
                    if (isSuccessful) {
                        errorTV.setVisibility(View.INVISIBLE);
                        Intent intentDashboard = new Intent(getApplicationContext(), DashboardUIDriver.class);
                        startActivity(intentDashboard);
                    } else {
                        errorTV.setVisibility(View.VISIBLE);
                        errorTV.setText("Register error,please try again!");
                        Log.d("FB_REGISTER", "failed");
                    }
                });
        });

        //listeners to reset errorTV message on text change
        localizationTV.addTextChangedListener(new TextWatcher() {
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

        departmentTV.addTextChangedListener(new TextWatcher() {
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

        cardIDET.addTextChangedListener(new TextWatcher() {
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

        cardNumberET.addTextChangedListener(new TextWatcher() {
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

        //Check internet connectivity
        NetworkCore.getInstance().hasInternetCallback(this, CreateAccount2UIDriver.this, connected -> {
            if (connected) {
                errorTV.setVisibility(View.INVISIBLE);
                registerBTN.setClickable(true);
            } else {
                errorTV.setVisibility(View.VISIBLE);
                errorTV.setText("No internet connection");
                registerBTN.setClickable(false);
            }
        });

    }
}
