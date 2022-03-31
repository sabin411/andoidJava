package com.example.googlelogin;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dashboard extends AppCompatActivity {
    private RecyclerView recViewDashboard;
    RecAdapter adapter;
    static String uid;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        uid = getIntent().getStringExtra("uid");

        // for recycler view
        recViewDashboard = (RecyclerView) findViewById(R.id.recViewDashboard);

        FirebaseUser rUser = FirebaseAuth.getInstance().getCurrentUser();

        String userId = rUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("JournalEntries").child(userId);
        recViewDashboard.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<RecViewDataHolder> options =
                new FirebaseRecyclerOptions.Builder<RecViewDataHolder>()
                        .setQuery(databaseReference, RecViewDataHolder.class)
                        .build();

        adapter = new RecAdapter(options);
        recViewDashboard.setAdapter(adapter);

        // for addJournal button
        final FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, HomeActivity.class));
            }
        });

        //for logout button
        final FloatingActionButton logoutbutton = findViewById(R.id.logout);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, LandingActivity.class));
                Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected  void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}