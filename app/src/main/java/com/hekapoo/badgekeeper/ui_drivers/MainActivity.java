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

        //If 'keep me logged is active' , skip to main dashboard screen
        //get this info from the local database thru DatabaseCore

        Intent intent;

        if (DatabaseCore.getInstance().isKeepLoggedActive())
            intent = new Intent(this, DashboardUIDriver.class);
        else
            intent = new Intent(this, LoginUIDriver.class);

        startActivity(intent);
    }
}