package com.example.my_itinary;


import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.my_itinary.schema.Circuit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.StructuredQuery;

import java.net.URI;
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

    public void insertCircuit(String city, String country, String[] addressSplit, StorageReference storageRef, Uri imageData)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference circuitRef = db.collection("Circuit");
        storageRef.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Circuit circuit = new Circuit(addressSplit[0], addressSplit[1], addressSplit[2], city, country, imageData.toString(), "Fab" );
                        circuitRef.add(circuit);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
