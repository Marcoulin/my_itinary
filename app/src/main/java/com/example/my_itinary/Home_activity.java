package com.example.my_itinary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListerner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_fragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListerner =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment = new Home_fragment();
                            break;

                        case R.id.nav_search:
                            selectedFragment = new Search_fragment();
                            break;

                        case R.id.nav_add:
                            selectedFragment = new Post_fragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}
