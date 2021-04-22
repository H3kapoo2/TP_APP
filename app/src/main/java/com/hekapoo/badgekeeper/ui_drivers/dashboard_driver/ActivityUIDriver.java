package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hekapoo.badgekeeper.R;

public class ActivityUIDriver extends AppCompatActivity {

    ImageView backBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        backBTN = findViewById(R.id.activity_back);

        backBTN.setOnClickListener(e->{
            Intent intent = new Intent(this,DashboardUIDriver.class);
            startActivity(intent);
        });

    }
}
