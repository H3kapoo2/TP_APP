package com.hekapoo.badgekeeper.ui_drivers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hekapoo.badgekeeper.R;

/*
 * Main class handling start-up of the application.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        //change113
    }
}