package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;

public class DashboardUIDriver extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashboard);

        UserSchema user = LocalDatabase.getInstance().loadUserLocally(this);

        Log.d("user", "onCreate: " + (user == null));
    }
}
