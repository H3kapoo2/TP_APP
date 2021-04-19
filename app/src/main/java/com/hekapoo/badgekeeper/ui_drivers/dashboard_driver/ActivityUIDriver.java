package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hekapoo.badgekeeper.R;

public class ActivityUIDriver extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

    }
}
