package com.hekapoo.badgekeeper.ui_drivers.login_driver;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hekapoo.badgekeeper.R;

/*
 * Main class handling UI for login process.
 */
public class LoginUIDriver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login_screen);
    }
}
