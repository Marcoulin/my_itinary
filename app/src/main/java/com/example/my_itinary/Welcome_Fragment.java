package com.example.my_itinary;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome_Fragment extends Fragment {
    public static FragmentManager fragmentManager;
    private Button connectButton;
    private TextView signButton;

    public Welcome_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.welcome_fragment, container, false);
        connectButton = v.findViewById(R.id.log_btn);
        signButton = v.findViewById(R.id.sign_btn);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();

            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                Signup_fragment s = new Signup_fragment();
                fragmentManager.beginTransaction().replace(R.id.Main, s).addToBackStack(null).commit();
            }
        });

        return v;
    }

    private void openHomeActivity() {
        Intent intent = new Intent(getActivity(), Home_activity.class);
        startActivity(intent);
    }
}
