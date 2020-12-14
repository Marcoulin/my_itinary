package com.example.my_itinary;


public class Database
{
    private static Database database;
    private String APP_ID = "";
    private String response = "";
    private String databaseName = "";
    private String serviceName = "";

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

    public void insertUser(String firstname, String lastname, String login, String password, String mail)
    {

    }

    public void insertCircuit(String country, String city, String adress1, String adress2, String adress3, String picture, String userId)
    {


    }

}
