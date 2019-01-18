package com.example.shoppingapp;

public class Singleton {
    static Singleton entity;
    static String username;

    private Singleton(){

    }
    static public Singleton getEntity() {
        if (entity == null){
            entity=new Singleton();
        }
        return entity;
    }
}
