package com.example.my_itinary.schema;
public class Circuit {


public class Circuit {

    private String _id;

    private String adresse1;
    private String adresse2;
    private String adresse3;
    private String city;
    private String country;

    private String picture;
    private String username;

    public Circuit()
    {

    }

    public Circuit(String adresse1, String adresse2, String adresse3, String city, String country, String picture, String username) {
        this.adresse1 = adresse1;
        this.adresse2 = adresse2;
        this.adresse3 = adresse3;
        this.city = city;
        this.country = country;
        this.picture = picture;
        this.username = username;
    }

    // Standard getters & setters

    private String itinaryPartitionKey;
    private String picture;
    private String username;

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

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
