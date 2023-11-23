package com.example.amigosdavizinhanca.controller;

public class User {

    private long id;
    private int cpf;
    private String name, address, email, password;


    public User(long id, String name, int cpf, String address, String email, String password) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public User( String name, int cpf, String address, String email, String password) {
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


