package com.example.athentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    AppCompatButton logout;
    FirebaseAuth firebaseAuth;

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNav = findViewById(R.id.bottom_navigation_layout);
        bottomNav.setBackground(null);

        bottomNav.setOnItemSelectedListener(navListener);

        //Here we're setting the home fragment as default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DiseasesFragment()).commit();


    }
    //Switch between fragments
    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_diseases:
                            selectedFragment = new DiseasesFragment();
                            break;
                        case R.id.nav_hosp:
                            selectedFragment = new HospitalisationFragment();
                            break;
                        case R.id.nav_market:
                            selectedFragment = new MarketFragment();
                            break;
                        case R.id.nav_announcement:
                            selectedFragment = new AnnouncementsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

}