package com.example.my_itinary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome_Fragment extends Fragment {
    public static FragmentManager fragmentManager;
    EditText editLogin, editPassword;
    private Button connectButton;
    private TextView signButton;
    Database database = Database.getInstance();
    SharedPreferences sharedPreferences;


    public Welcome_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.welcome_fragment, container, false);
        init(v);
        //Connect User

        connectButton.setOnClickListener(view -> {
            openHomeActivity();
        });



        signButton.setOnClickListener(view -> {
            fragmentManager = getFragmentManager();
            Signup_fragment s = new Signup_fragment();
            fragmentManager.beginTransaction().replace(R.id.Main, s).addToBackStack(null).commit();
        });

        return v;
    }

    private void openHomeActivity() {
        Intent intent = new Intent(getActivity(), Home_activity.class);
        startActivity(intent);
    }

    private void init(View v)
    {
        editLogin = v.findViewById(R.id.user_log);
        editPassword = v.findViewById(R.id.pass_log);
        connectButton = v.findViewById(R.id.log_btn);
        signButton = v.findViewById(R.id.sign_btn);
    }

}
