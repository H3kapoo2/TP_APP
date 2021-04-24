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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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

            //encrypt password
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] bytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                password = sb.toString();
            }
            catch (NoSuchAlgorithmException ex)
            {
                ex.printStackTrace();
            }

            UserSchema newUser = new UserSchema(password,email,department,localization,cardID,cardNumber,"8h 0m"); //default values go here before registering
            newUser.setLastUsedBadge("No-info");
            newUser.setCheckInAt("No-Info");
            newUser.setLeftToWork("No-Info");

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
