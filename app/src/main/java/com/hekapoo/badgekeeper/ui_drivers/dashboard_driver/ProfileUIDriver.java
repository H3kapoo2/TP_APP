package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;
import com.hekapoo.badgekeeper.R;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileUIDriver extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
    }
}
