package com.example.bankofpasswords;

public class List_Item {

    String password_name;
    String password_value;

    public List_Item(String password_name, String password_value) {
        this.password_name = password_name;
        this.password_value = password_value;
    }

    public String getPassword_name() {
        return password_name;
    }

    public String getPassword_value() {
        return password_value;
    }

    public void setPassword_name(String password_name) {
        this.password_name = password_name;
    }

    public void setPassword_value(String password_value) {
        this.password_value = password_value;
    }

}
