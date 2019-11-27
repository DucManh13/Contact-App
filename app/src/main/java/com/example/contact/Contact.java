package com.example.contact;


import java.io.Serializable;

public class Contact implements Serializable{
    String name;
    String phone;
    int id;

    public Contact(int id, String name, String phone)  {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
    public Contact(String name, String phone)  {
        this.name = name;
        this.phone = phone;
    }
    public Contact(){

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
