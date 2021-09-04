package com.cs426.imageetranslation.model;

import org.json.JSONObject;

public class User {
    private String phone;
    private String password;
    private String dob;
    private String gender;
    private String email;
    private String name;
    private String id;

    public String getPhone() {
        return phone;
    }

    public void setPassword(String pw){
        this.password = pw;
    }
    public String getPassword() {
        return password;
    }

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public User(String name, String phone, String password, String dob, String gender, String email, String id) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.name = name;
    }
    public User (JSONObject u) {
        try {
            this.phone = u.getString("phone");
            this.password = u.getString("password");
            this.dob = u.getString("dob");
            this.gender = u.getString("gender");
            this.email = u.getString("email");
            this.name = u.getString("name");
            this.id = u.getString("id");
        }catch (Exception err) {
            err.printStackTrace();
        }
    }
    public JSONObject UserToJSON() {
        try {
            JSONObject account = new JSONObject();
            account.put("name",name);
            account.put("phone", phone);
            account.put("password", password);
            account.put("dob", dob);
            account.put("gender", gender);
            account.put("email", email);
            account.put("id", id);
            return account;
        }catch (Exception err) {
            return new JSONObject();
        }
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}