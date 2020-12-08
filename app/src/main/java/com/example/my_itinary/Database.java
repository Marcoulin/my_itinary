package com.example.my_itinary;

import android.net.wifi.hotspot2.pps.Credential;
import android.util.Log;
import android.widget.Toast;

import com.example.my_itinary.schema.Circuit;
import com.example.my_itinary.schema.UserData;

import org.bson.Document;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class Database
{
    private static Database database;
    private App app;
    private String APP_ID = "my_itinary-dcmru";
    private String response = "";
    private String databaseName = "My_Itinary_DB";
    private String serviceName = "mongodb-atlas";
    private Database()
    {
        this.app = new App(new AppConfiguration.Builder(APP_ID).build());
    }

    public static Database getInstance()
    {
        if(database==null)
        {
            database = new Database();
        }
        return  database;
    }

    public App getApp()
    {
        return this.app;
    }

    public String insertUser(String firstname, String lastname, String login, String password, String mail)
    {
        response = "";
        database.getApp().getEmailPassword().registerUserAsync(mail, password, it -> {
            if (it.isSuccess())
            {
                Log.i("TAG","Successfully registered user.");
                Credentials credentials = Credentials.emailPassword(mail, password);
                app.loginAsync(credentials, its -> {
                    if (its.isSuccess())
                    {
                        User user = app.currentUser();

                        MongoClient mongoClient = user.getMongoClient(serviceName);
                        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
                        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Users");

                        mongoCollection.insertOne(new Document("ID", user.getId())
                                .append("Firstname", firstname)
                                .append("Lastname", lastname)
                                .append("Login", login))
                                .getAsync(result -> {
                                    if (result.isSuccess())
                                    {
                                        // Log.v("EXAMPLE", "Inserted custom user data document. _id of inserted document: "+ result.get().getId())
                                        response = "User inserted";
                                    }
                                    else
                                    {
                                        Log.e("EXAMPLE", "Unable to insert custom user data. Error: " + result.getError());
                                        response = "Unable to insert data";
                                    }
                                });
                    }
                    else
                    {
                        Log.e("EXAMPLE", "Failed to log in :" + its.getError().toString());
                        response = "Failed to log in";
                    }
                });
            }
            else
            {
                Log.e("TAG","Failed to register user: ${it.error}");
                response = "Failed to register";
            }
        });
        return response;
    }

    public void insertCircuit(String country, String city, String adress1, String adress2, String adress3, String picture)
    {

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<UserData> projectsQuery = realm.where(UserData.class);
        realm.executeTransaction(r -> {
            Circuit circuit = realm.createObject(Circuit.class);
            circuit.setCountry(country);
            circuit.setCity(city);
            circuit.setAdresse1(adress1);
            circuit.setAdresse2(adress2);
            circuit.setAdresse3(adress3);
            circuit.setPicture(picture);
        });
    }

}
