package com.example.my_itinary.schema;

public class UserData {

    private String _id;
    private String firstname;
    private String lastname;
    private String login;

    // Standard getters & setters

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String get_id() {
        return _id;
    }
}
