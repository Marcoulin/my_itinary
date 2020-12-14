package com.example.my_itinary;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Database
{
    private static Database database;
    private String APP_ID = "";
    private String response = "";
    private String databaseName = "";
    private String serviceName = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Database()
    {

    }

    public static Database getInstance()
    {
        if(database==null)
        {
            database = new Database();
        }
        return  database;
    }

    public void insertUser(FirebaseUser user, String firstname, String lastname, String login)
    {
        Map<String, Object> userdata = new HashMap<>();
        userdata.put("id", user.getUid());
        userdata.put("firstname", firstname);
        userdata.put("lastname", lastname);
        userdata.put("login", login);

        // Add a new document with a generated ID
        db.collection("Users")
                .add(userdata)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public void insertCircuit(String country, String city, String adress1, String adress2, String adress3, String picture, String userId)
    {


    }

}
