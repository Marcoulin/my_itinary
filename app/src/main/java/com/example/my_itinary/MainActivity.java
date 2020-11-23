package com.example.my_itinary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    String APP_ID = "my_itinary-dcmru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(APP_ID).build());
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.Main, new Welcome_Fragment(app)).commit();
    }
}
