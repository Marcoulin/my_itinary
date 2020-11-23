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
        View v = inflater.inflate(R.layout.welcome_fragment, container, false);
        init(v);
        signupButton.setOnClickListener(view -> {
            //Create User
            database.getApp().getEmailPassword().registerUserAsync(mail.getText().toString(), password.getText().toString(), it -> {
                if(password.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !mail.getText().toString().isEmpty() && !firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty())
                    {
                        if (it.isSuccess()) {
                            Log.i("TAG","Successfully registered user.");
                        } else {
                            Log.e("TAG","Failed to register user: ${it.error}");
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
        });

        return inflater.inflate(R.layout.sign_up_page, container, false);
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

    /*private void insertUserData()
    {
        Credentials credentials;
        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                User user = app.currentUser();

                MongoClient mongoClient =
                        user.getMongoClient("My_Itinary");
                MongoDatabase mongoDatabase =
                        mongoClient.getDatabase("My_Itinary_DB");
                MongoCollection<org.bson.Document> mongoCollection =
                        mongoDatabase.getCollection("<custom user data collection>");

                mongoCollection.insertOne(
                        new Document("<user ID field>", user.getId()).append("favoriteColor", "pink"))
                        .getAsync(result -> {
                            if (result.isSuccess()) {
                                Log.v("EXAMPLE", "Inserted custom user data document. _id of inserted document: "
                                        + result.get().getId());
                            } else {
                                Log.e("EXAMPLE", "Unable to insert custom user data. Error: " + result.getError());
                            }
                        });
            } else {
                Log.e("EXAMPLE", "Failed to log in anonymously:" + it.getError().toString());
            }
        });
    }*/
}
