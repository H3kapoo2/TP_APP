package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.utils_module.PeopleListAdapter;
import com.hekapoo.badgekeeper.modules.utils_module.PeopleListModel;

import java.util.ArrayList;

public class PeopleUIDriver extends AppCompatActivity {

    ImageView backBTN;
    EditText searchET;
    RecyclerView peopleRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_screen);

        searchET = findViewById(R.id.people_search);
        peopleRecycler = findViewById(R.id.people_recycler);
        backBTN = findViewById(R.id.people_back);


        //dummy populate
        ArrayList<PeopleListModel> models = new ArrayList<>();

        PeopleListModel model = new PeopleListModel("ceva","ceva","ceva","ceva");
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);

        PeopleListAdapter listAdapter = new PeopleListAdapter(models);
        peopleRecycler.setAdapter(listAdapter);
        peopleRecycler.setLayoutManager(new LinearLayoutManager(this));


        backBTN.setOnClickListener(e->{
            Intent intent = new Intent(this,DashboardUIDriver.class);
            startActivity(intent);
        });

    }
}
