package com.hekapoo.badgekeeper.ui_drivers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.DatabaseCore;
import com.hekapoo.badgekeeper.modules.database_module.FirebaseDB;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.ui_drivers.create_account_driver.CreateAccount1UIDriver;
import com.hekapoo.badgekeeper.ui_drivers.create_account_driver.CreateAccount2UIDriver;
import com.hekapoo.badgekeeper.ui_drivers.dashboard_driver.DashboardUIDriver;
import com.hekapoo.badgekeeper.ui_drivers.login_driver.LoginUIDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Main class handling start-up of the application.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!LocalDatabase.getInstance().isInit(this))
            LocalDatabase.getInstance().firstTimeLocalSetup(this);

        //if keep me logged is is enabled,skip login screen
        if (LocalDatabase.getInstance().getLocalSettings(this).isKeepLogin()) {

            String email = LocalDatabase.getInstance().loadUserLocally(this).getEmail();
            String password = LocalDatabase.getInstance().loadUserLocally(this).getPassword();

            //try to log the user in
            FirebaseDB.getInstance().loginUser(email, password, loggedIn -> {
                if (loggedIn) {

                    Intent intent = new Intent(this, DashboardUIDriver.class);

                    //get user data from fb db to pass to dashboard
                    FirebaseDB.getInstance().getUserFirebase(resultUser -> {
                        if (resultUser != null) {
                            LocalDatabase.getInstance().saveUserLocally(resultUser, this);
                            startActivity(intent);
                        }
                    });
                }
            });
        } else {
            Intent intent;
            intent = new Intent(this, LoginUIDriver.class);
            startActivity(intent);
        }
    }
}