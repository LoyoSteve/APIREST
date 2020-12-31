package com.example.webservices;

public class Users {
    private int id, edad;
    private String nombre, apellido;

    public Users(int id, String nombre, String apellido, int edad){
        super();
        this.id = id;
        this.edad = edad;
        this.nombre = nombre;
        this.apellido =apellido;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nom){
        this.nombre=nom;
    }
    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int e){
        this.edad = e;
    }
}
