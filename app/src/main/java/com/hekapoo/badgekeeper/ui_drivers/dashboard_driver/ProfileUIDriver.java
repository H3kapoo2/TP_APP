package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;
import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.utils_module.DialogUtils;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;
import com.hekapoo.badgekeeper.ui_drivers.create_account_driver.CreateAccount2UIDriver;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileUIDriver extends AppCompatActivity {

    TextView emailTV,userNameTV,localizationTV,cardIDTV,cardNumberTV,workHoursTV,departmentTV;
    ImageView backBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        emailTV = findViewById(R.id.profile_email);
        userNameTV = findViewById(R.id.profile_user);
        localizationTV = findViewById(R.id.profile_localization);
        cardIDTV = findViewById(R.id.profile_cardid);
        cardNumberTV = findViewById(R.id.profile_cardNumber);
        workHoursTV = findViewById(R.id.profile_workHours);
        departmentTV = findViewById(R.id.profile_department);

        backBTN = findViewById(R.id.profile_back);


        //load data from local storage
        UserSchema user = LocalDatabase.getInstance().loadUserLocally(this);
        Log.d("user", "profile non existent: " + (user == null));

        emailTV.setText(user.getEmail());
        userNameTV.setText(user.getEmail()); //todo: parse into parts and uppercase first letter
        localizationTV.setText(user.getLocalization());
        departmentTV.setText(user.getDepartment());
        cardIDTV.setText(user.getCardID());
        cardNumberTV.setText(user.getCardNumber());
        workHoursTV.setText(user.getWorkHours());


        //localization on click dialog picker
        localizationTV.setOnClickListener(e -> {
            DialogUtils.getInstance().buildAndShowPickerDialog("Localization",
                    LocalDatabase.getInstance().getLocalizationArray(),
                    ProfileUIDriver.this,
                    localizationTV);
        });

        //department on click dialog picker
        departmentTV.setOnClickListener(e -> {
            DialogUtils.getInstance().buildAndShowPickerDialog("Department",
                    LocalDatabase.getInstance().getDepartmentArray(),
                    ProfileUIDriver.this,
                    departmentTV);
        });

        //workHours on click dialog picker
        workHoursTV.setOnClickListener(e -> {
            DialogUtils.getInstance().buildAndShowPickerDialog("Work hours",
                    LocalDatabase.getInstance().getWorkHoursArray(),
                    ProfileUIDriver.this,
                    workHoursTV);
        });

        //handle back
        backBTN.setOnClickListener(e->{
            Intent intent = new Intent(this,DashboardUIDriver.class);
            startActivity(intent);
        });

        //on localization change,save changes to DB
        localizationTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //save to local db & to fb db
                user.setLocalization(localizationTV.getText().toString());
                LocalDatabase.getInstance().saveUserLocally(user,ProfileUIDriver.this);
                FirebaseDB.getInstance().saveUserChanged(user);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //on department change,save changes to DB
        departmentTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //save to local db & to fb db
                user.setDepartment(departmentTV.getText().toString());
                LocalDatabase.getInstance().saveUserLocally(user,ProfileUIDriver.this);
                FirebaseDB.getInstance().saveUserChanged(user);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //on workhours change,save changes to DB
        workHoursTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //save to local db & to fb db
                user.setWorkHours(workHoursTV.getText().toString());
                LocalDatabase.getInstance().saveUserLocally(user,ProfileUIDriver.this);
                FirebaseDB.getInstance().saveUserChanged(user);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
