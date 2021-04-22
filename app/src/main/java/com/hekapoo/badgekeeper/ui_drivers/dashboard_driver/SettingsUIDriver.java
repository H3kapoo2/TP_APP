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
import com.hekapoo.badgekeeper.modules.utils_module.SettingsSchema;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingsUIDriver extends AppCompatActivity {

    ImageView backBTN;
    TextView languageTV;
    Switch fingerprintLoginSW, keepLogInSW, notifSW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        backBTN = findViewById(R.id.settings_back);
        fingerprintLoginSW = findViewById(R.id.settings_fingerprint);
        languageTV = findViewById(R.id.settings_language);
        keepLogInSW = findViewById(R.id.settings_keeplogin);
        notifSW = findViewById(R.id.settings_notifs);

        //todo: load user saved settings for this app
        SettingsSchema settings1 = LocalDatabase.getInstance().getLocalSettings(this);

        fingerprintLoginSW.setChecked(settings1.isFingerprintLogin());
        keepLogInSW.setChecked(settings1.isKeepLogin());
        notifSW.setChecked(settings1.isNotifications());

        keepLogInSW.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingsSchema settings = LocalDatabase.getInstance().getLocalSettings(this);

            if (isChecked)
                //check if there are fingerprints registered on device,if not,u cant enable this
                //else enable and save settings locally
                settings.setKeepLogin(true);
            else
                settings.setKeepLogin(false);

            LocalDatabase.getInstance().saveLocalSettings(settings,this);

        });

        backBTN.setOnClickListener(e->{
            Intent intent = new Intent(this,DashboardUIDriver.class);
            startActivity(intent);
        });
    }
}
