package com.example.customloginsignup;

public class User {

    public String fullName,age,email;

//    Empty constructor is made so that if we want to create empty
//    object of this class and access this varibles we can do it.
    public User(){

    }

    public User(String fullName,String age,String email){
        this.fullName  = fullName;
        this.age = age;
        this.email = email;
    }

}
