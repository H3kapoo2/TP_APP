package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hekapoo.badgekeeper.R;

public class SettingsUIDriver extends AppCompatActivity {

    ImageView backBTN;
    TextView languageTV;
    Switch fingerprintLoginSW ,keepLogInSW, notifSW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        backBTN= findViewById(R.id.settings_back);
        fingerprintLoginSW = findViewById(R.id.settings_fingerprint);
        languageTV = findViewById(R.id.settings_language);
        keepLogInSW = findViewById(R.id.settings_keeplogin);
        notifSW = findViewById(R.id.settings_notifications);

        //todo: load user saved settings for this app

    }
}
