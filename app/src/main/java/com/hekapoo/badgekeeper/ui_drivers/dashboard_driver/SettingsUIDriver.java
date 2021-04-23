package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.utils_module.DialogUtils;
import com.hekapoo.badgekeeper.modules.utils_module.SettingsSchema;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingsUIDriver extends AppCompatActivity {

    ImageView backBTN;
    TextView languageTV,languageTV2;
    Switch fingerprintLoginSW, keepLogInSW, notifSW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        backBTN = findViewById(R.id.settings_back);
        fingerprintLoginSW = findViewById(R.id.settings_fingerprint);
        languageTV = findViewById(R.id.settings_language);
        languageTV2 = findViewById(R.id.settings_language_2);
        keepLogInSW = findViewById(R.id.settings_keeplogin);
        notifSW = findViewById(R.id.settings_notifs);

        //load spp settings
        SettingsSchema settings1 = LocalDatabase.getInstance().getLocalSettings(this);

        //todo: language
        //language change listener
        languageTV.setOnClickListener(e->{
            DialogUtils.getInstance().buildAndShowPickerDialog("Language",
                    LocalDatabase.getInstance().getLanguageArray(),
                    SettingsUIDriver.this,
                    languageTV2);
        });

        //set statuses
        fingerprintLoginSW.setChecked(settings1.isFingerprintLogin());
        keepLogInSW.setChecked(settings1.isKeepLogin());
        notifSW.setChecked(settings1.isNotifications());

        //keep me logged in listener
        keepLogInSW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingsSchema settings = LocalDatabase.getInstance().getLocalSettings(this);

            if (isChecked)
                settings.setKeepLogin(true);
            else
                settings.setKeepLogin(false);

            LocalDatabase.getInstance().saveLocalSettings(settings,this);

        });

        //enable fingerprint listener
        fingerprintLoginSW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingsSchema settings = LocalDatabase.getInstance().getLocalSettings(this);

            if (isChecked)
                settings.setFingerprintLogin(true);
            else
                settings.setFingerprintLogin(false);
            LocalDatabase.getInstance().saveLocalSettings(settings,this);

        });

        //enable notifications listener
        notifSW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //todo:
        });

        //back
        backBTN.setOnClickListener(e->{
            Intent intent = new Intent(this,DashboardUIDriver.class);
            startActivity(intent);
        });
    }
}
