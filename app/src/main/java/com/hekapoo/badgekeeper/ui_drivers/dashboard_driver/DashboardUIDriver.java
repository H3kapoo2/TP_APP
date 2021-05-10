package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.utils_module.TextParser;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class DashboardUIDriver extends AppCompatActivity {

    TextView firstNameTV, lastNameTV, localizationTV;
    CardView peopleIm, profileIm, settingsIm;

    //TODO: THIS IS THE ONLY ACTIVITY THE USER CAN USE THE BADGE ACTIVELY
    //TODO: ON BADGE USE, UPDATE DB FOR "LAST USED TIME"
    //TODO: "CHECK IN TIME" IF THIS IS THE FIRST TIME HE USES THIS DAY
    //TODO: IF HE JUST CHECKED IN FOR THE FIRST TIME THIS DAY, UPDATE "TIME LEFT TO WORK"

    //TODO: CLICKING ON THE BADGE LETS YOU RESCAN IT


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashboard);

        firstNameTV = findViewById(R.id.badge_name1);
        lastNameTV = findViewById(R.id.badge_name2);
        localizationTV = findViewById(R.id.badge_localization);

        peopleIm = findViewById(R.id.people_im);
        profileIm = findViewById(R.id.profile_im);
        settingsIm = findViewById(R.id.settings_im);

        UserSchema user = LocalDatabase.getInstance().loadUserLocally(this);
        Log.d("user", "dashboard non existent: " + (user == null));

        String[] firstNameLastName = TextParser.getInstance().parseDashboardName(user.getEmail());

        firstNameTV.setText(firstNameLastName[0]);
        lastNameTV.setText(firstNameLastName[1]);
        localizationTV.setText(user.getLocalization());

        peopleIm.setOnClickListener(e -> {
            Intent intent = new Intent(this, PeopleUIDriver.class);
            startActivity(intent);
        });

        profileIm.setOnClickListener(e -> {
            Intent intent = new Intent(this, ProfileUIDriver.class);
            startActivity(intent);
        });

        settingsIm.setOnClickListener(e -> {
            Intent intent = new Intent(this, SettingsUIDriver.class);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
    }
}
