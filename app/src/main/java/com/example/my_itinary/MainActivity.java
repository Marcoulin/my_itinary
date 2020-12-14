package com.example.my_itinary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database database = Database.getInstance();
        fragmentManager = getSupportFragmentManager();
        Preferences.init(getApplicationContext());
        fragmentManager.beginTransaction().add(R.id.Main, new Welcome_Fragment()).commit();
    }
}
