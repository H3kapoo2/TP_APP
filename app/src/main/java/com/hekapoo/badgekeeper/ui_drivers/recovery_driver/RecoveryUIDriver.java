package com.hekapoo.badgekeeper.ui_drivers.recovery_driver;

import android.os.Bundle;
import com.hekapoo.badgekeeper.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecoveryUIDriver extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login_pass_recovery);
    }
}
