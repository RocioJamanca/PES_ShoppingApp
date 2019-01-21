package com.example.shoppingapp;

//Clase singleton para pasar entre activities el username del usuario introducido
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
