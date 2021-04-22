package com.hekapoo.badgekeeper.ui_drivers.create_account_driver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;
import com.hekapoo.badgekeeper.ui_drivers.dashboard_driver.DashboardUIDriver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class BadgeScanUIDriver extends AppCompatActivity {

    TextView approachTV;
    TextView errorBadgeTV;
    CardView badgeExternalRing;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badge_scan);

        approachTV = findViewById(R.id.approach_badge_tv);
        errorBadgeTV = findViewById(R.id.error_badge_tv);
        badgeExternalRing = findViewById(R.id.external_approach_badge_cv);

        //TODO: ALL NFC SCANNING HAPPENS HERE

        //todo: this things should be called after successful NFC scan, dummy for now because of testing
        badgeExternalRing.setOnClickListener(e->{

            Intent intent = getIntent();
            String password = intent.getStringExtra("password");
            String email = intent.getStringExtra("email");
            String localization = intent.getStringExtra("localization");
            String department = intent.getStringExtra("department");
            String cardID = intent.getStringExtra("cardID");
            String cardNumber = intent.getStringExtra("cardNumber");

            UserSchema newUser = new UserSchema(email,department,localization,cardID,cardNumber,"8h 0m"); //default values go here before registering

            FirebaseDB.getInstance().registerNewUser(email, password, newUser, isSuccessful -> {
                //on successful register,redirect to dashboard
                if (isSuccessful) {
                    errorBadgeTV.setVisibility(View.INVISIBLE);
                    Intent intentDashboard = new Intent(getApplicationContext(), DashboardUIDriver.class);

                    //save data locally for easier lookup
                    LocalDatabase.getInstance().saveUserLocally(newUser,this);

                    startActivity(intentDashboard);
                } else {
                    errorBadgeTV.setVisibility(View.VISIBLE);
                    errorBadgeTV.setText("Register error,please try again!");
                    Log.d("FB_REGISTER", "failed");
                }
            });
        });
    }
}
