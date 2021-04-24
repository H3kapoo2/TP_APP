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

public class DashboardUIDriver extends AppCompatActivity {

    TextView firstNameTV,lastNameTV,localizationTV;
    TextView workGoalTV,workDoneTV;
    CardView activityIm,peopleIm,profileIm,settingsIm;

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

        workGoalTV = findViewById(R.id.bagde_goal);
        workDoneTV = findViewById(R.id.badge_current_time);

        activityIm = findViewById(R.id.activity_im);
        peopleIm = findViewById(R.id.people_im);
        profileIm = findViewById(R.id.profile_im);
        settingsIm = findViewById(R.id.settings_im);

        UserSchema user = LocalDatabase.getInstance().loadUserLocally(this);
        Log.d("user", "dashboard non existent: " + (user == null));

        String[] firstNameLastName = TextParser.getInstance().parseDashboardName(user.getEmail());

        firstNameTV.setText(firstNameLastName[0]);
        lastNameTV.setText(firstNameLastName[1]);
        localizationTV.setText(user.getLocalization());
        workGoalTV.setText(user.getWorkHours());

        //todo: stop user from going back button,if he does,close application instead

        //todo:
        //for WORK_DONE start at zero when the badge is first scanned that day
        //stop incrementing after WORK_DONE reached goal
        //notify user their work is done for today
        //clicking on the CIRCLE should pause/unpause the counter (maybe they have a break that shouldn't be counted as work)

        activityIm.setOnClickListener(e->{
            Intent intent = new Intent(this,ActivityUIDriver.class);
            startActivity(intent);
        });

        peopleIm.setOnClickListener(e->{
            Intent intent = new Intent(this,PeopleUIDriver.class);
            startActivity(intent);
        });

        profileIm.setOnClickListener(e->{
            Intent intent = new Intent(this,ProfileUIDriver.class);
            startActivity(intent);
        });

        settingsIm.setOnClickListener(e->{
            Intent intent = new Intent(this,SettingsUIDriver.class);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
    }
}
