package com.hekapoo.badgekeeper.ui_drivers.dashboard_driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hekapoo.badgekeeper.R;
import com.hekapoo.badgekeeper.modules.database_module.LocalDatabase;
import com.hekapoo.badgekeeper.modules.utils_module.PeopleListAdapter;
import com.hekapoo.badgekeeper.modules.utils_module.PeopleListModel;
import com.hekapoo.badgekeeper.modules.utils_module.TextParser;
import com.hekapoo.badgekeeper.modules.utils_module.UserSchema;

import org.w3c.dom.Text;

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

        //to convert email to username
        TextParser textParser = TextParser.getInstance();

        //extract user department to be user in search listener
        String userDept = LocalDatabase.getInstance().loadUserLocally(this).getDepartment();

        //search box listener
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence textSearched, int start, int before, int count) {

                ArrayList<PeopleListModel> peopleFound = new ArrayList<>();

                //when text changes and text length >=3, pull from DB data
                if (textSearched.length() >= 3) {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                    ref.addValueEventListener(new ValueEventListener() {

                        //we get the data
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //filter the data for Department and name searched
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                UserSchema userGot = postSnapshot.getValue(UserSchema.class);

                                //if there's user with dept and searched text,add to display list
                                if (userGot.getDepartment().equals(userDept) && userGot.getEmail().contains(textSearched)) {

                                    //array to keep track of found people
                                    PeopleListModel foundOne = new PeopleListModel(textParser.parseProfileUsername(userGot.getEmail()), userGot.getLastUsedBadge(), userGot.getCheckInAt(), userGot.getLeftToWork());
                                    peopleFound.add(foundOne);
                                }
                            }

                            //if nobody has been found
                            if (peopleFound.size() == 0) {
                                PeopleListModel foundOne = new PeopleListModel("No result :(", "--", "--", "--");
                                peopleFound.add(foundOne);
                            }

                            //show results
                            PeopleListAdapter listAdapter = new PeopleListAdapter(peopleFound);
                            peopleRecycler.setAdapter(listAdapter);
                            peopleRecycler.setLayoutManager(new LinearLayoutManager(PeopleUIDriver.this));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    //if not of specified minimum length,dont show recycler
                    peopleRecycler.setAdapter(null);
                    peopleRecycler.setLayoutManager(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        peopleRecycler.setLayoutManager(new LinearLayoutManager(PeopleUIDriver.this));

        backBTN.setOnClickListener(e -> {
            Intent intent = new Intent(this, DashboardUIDriver.class);
            startActivity(intent);
        });

    }
}
