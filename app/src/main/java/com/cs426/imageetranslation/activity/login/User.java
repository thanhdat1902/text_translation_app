package com.cs426.imageetranslation.activity.login;

import org.json.JSONObject;

public class User {
    private String phone;
    private String password;

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }



    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
    public User (JSONObject u) {
        try {
            this.phone = u.getString("phone");
            this.password = u.getString("password");
        }catch (Exception err) {
            err.printStackTrace();
        }
    }
    public JSONObject UserToJSON() {
        try {
            JSONObject account = new JSONObject();
            account.put("phone", phone);
            account.put("password", password);
            return account;
        }catch (Exception err) {
            return new JSONObject();
        }
    }
}