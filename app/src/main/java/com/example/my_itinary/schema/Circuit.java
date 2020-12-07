package com.example.my_itinary.schema;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import org.bson.types.ObjectId;

public class Circuit extends RealmObject {

    @PrimaryKey
    private ObjectId _id;
    private UserData _id_user;
    private String adresse1;
    private String adresse2;
    private String adresse3;
    private String city;
    private String country;
    private String picture;

    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    public UserData get_id_user() { return _id_user; }
    public void set_id_user(UserData _id_user) { this._id_user = _id_user; }
    public String getAdresse1() { return adresse1; }
    public void setAdresse1(String adresse1) { this.adresse1 = adresse1; }
    public String getAdresse2() { return adresse2; }
    public void setAdresse2(String adresse2) { this.adresse2 = adresse2; }
    public String getAdresse3() { return adresse3; }
    public void setAdresse3(String adresse3) { this.adresse3 = adresse3; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
}
