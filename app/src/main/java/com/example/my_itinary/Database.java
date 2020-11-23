package com.example.my_itinary;

import android.net.wifi.hotspot2.pps.Credential;
import android.util.Log;
import android.widget.Toast;

import org.bson.Document;

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
    private String databaseName = "My_Itinary";
    private String serviceName = "My_Itinary_DB";
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

    public void insertUser(Credentials credentials)
    {
        app.loginAsync(credentials, it -> {
            if (it.isSuccess())
            {
                User user = app.currentUser();

                MongoClient mongoClient = user.getMongoClient(serviceName);
                MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Users");

                mongoCollection.insertOne(new Document("<user ID field>", user.getId()).append("favoriteColor", "pink"))
                        .getAsync(result -> {
                            if (result.isSuccess())
                            {
                               // Log.v("EXAMPLE", "Inserted custom user data document. _id of inserted document: "
                                        //+ result.get().getId());
                            }
                            else
                            {
                                Log.e("EXAMPLE", "Unable to insert custom user data. Error: " + result.getError());
                            }
                        });
            }
            else
            {
                Log.e("EXAMPLE", "Failed to log in anonymously:" + it.getError().toString());
            }
        });
    }


}
