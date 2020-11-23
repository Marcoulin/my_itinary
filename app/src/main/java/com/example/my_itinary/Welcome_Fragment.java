package com.example.my_itinary;

import android.content.Intent;
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

import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome_Fragment extends Fragment {
    public static FragmentManager fragmentManager;
    EditText editLogin, editPassword;
    private Button connectButton;
    private TextView signButton;
    private App app;

    public Welcome_Fragment(App app) {
        // Required empty public constructor
        this.app = app;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.welcome_fragment, container, false);
        editLogin = (EditText) v.findViewById(R.id.user_log);
        editPassword = (EditText) v.findViewById(R.id.pass_log);
        connectButton = v.findViewById(R.id.log_btn);
        signButton = v.findViewById(R.id.sign_btn);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openHomeActivity();
                if(!editLogin.getText().toString().isEmpty() && !editPassword.getText().toString().isEmpty())
                {
                    Credentials emailPasswordCredentials = Credentials.emailPassword(editLogin.getText().toString(), editPassword.getText().toString());
                    app.loginAsync(emailPasswordCredentials, new App.Callback<io.realm.mongodb.User>() {
                        @Override
                        public void onResult(App.Result<User> result) {
                            if(result.isSuccess())
                            {
                                //Log.v("User", "Logged success");
                                openHomeActivity();
                            }
                            else
                            {
                                //Log.v("User", "Failed to log");
                                Toast.makeText(getActivity(),"Login or Password incorrect.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                        Toast.makeText(getActivity(),"Enter Login and Password please.", Toast.LENGTH_LONG).show();
                }

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
