package com.example.my_itinary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;

import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Signup_fragment extends Fragment {

    Database database = Database.getInstance();
    EditText login, password, confirmPassword, mail, firstname, lastname;
    Button signupButton;
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
                        String insertionMessage = database.insertUser(firstname.getText().toString(), lastname.getText().toString(), login.getText().toString(), password.getText().toString(),mail.getText().toString());
                        if(insertionMessage.equals("User inserted"))
                        {
                            Toast.makeText(getActivity(),"User inserted", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),insertionMessage, Toast.LENGTH_LONG).show();
                        }
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
