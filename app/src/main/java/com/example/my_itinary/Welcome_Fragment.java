package com.example.my_itinary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public Welcome_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.welcome_fragment, container, false);
        init(v);
        //Connect User

        connectButton.setOnClickListener(view -> {
            if(!editLogin.getText().toString().isEmpty() && !editPassword.getText().toString().isEmpty())
            {
                mAuth.signInWithEmailAndPassword(editLogin.getText().toString(), editPassword.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure =>", task.getException());
                                    Toast.makeText(getActivity(), ""+task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else
            {
                    Toast.makeText(getActivity(),"Enter Login and Password please.", Toast.LENGTH_LONG).show();
            }
        });



        signButton.setOnClickListener(view -> {
            fragmentManager = getFragmentManager();
            Signup_fragment s = new Signup_fragment();
            fragmentManager.beginTransaction().replace(R.id.Main, s).addToBackStack(null).commit();
        });

        return v;
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(getActivity(), Home_activity.class);
        intent.putExtra("user", user);
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
