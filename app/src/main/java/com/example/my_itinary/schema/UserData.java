package com.example.my_itinary.schema;

import android.content.Context;
import android.content.SharedPreferences;

import org.bson.types.ObjectId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import io.realm.RealmObject;
import org.bson.types.ObjectId;

public class UserData extends RealmObject {

    @PrimaryKey
    private ObjectId _id;
    private String firstname;
    private String lastname;
    private String login;
    public RealmList<Circuit> circuits;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
}
