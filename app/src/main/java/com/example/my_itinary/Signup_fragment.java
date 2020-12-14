package com.example.my_itinary;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Signup_fragment extends Fragment {

    Database database = Database.getInstance();
    EditText login, password, confirmPassword, mail, firstname, lastname;
    Button signupButton;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public Signup_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_page, container, false);
        init(v);
        signupButton.setOnClickListener(view -> {
                //Create User
                if(password.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !mail.getText().toString().isEmpty() && !firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty())
                    {
                        mAuth.createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("TAG", "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            database.insertUser(user, firstname.getText().toString(), lastname.getText().toString(), login.getText().toString());

                                            updateUI(user);
                                        }
                                        else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(getActivity(), "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please fill every fields.", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Passwords don't match.", Toast.LENGTH_LONG).show();
                }
        });

        return v;
    }

    private void updateUI(FirebaseUser user)
    {
        Intent intent = new Intent(getActivity(), Home_activity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void init(View v)
    {
        login = v.findViewById(R.id.user_sign);
        password = v.findViewById(R.id.pass_sign);
        confirmPassword = v.findViewById(R.id.cpass_sign);
        mail = v.findViewById(R.id.user_mail);
        firstname = v.findViewById(R.id.user_First);
        lastname = v.findViewById(R.id.user_last);
        signupButton = v.findViewById(R.id.connect_btn);
    }

}
