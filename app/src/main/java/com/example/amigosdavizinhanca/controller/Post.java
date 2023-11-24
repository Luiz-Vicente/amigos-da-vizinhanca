package com.example.amigosdavizinhanca.controller;

public class Post {

    private long id;

    private long idUser;

    private String text, image;

    public Post (long id, long idUser, String text, String image) {
        this.id = id;
        this.idUser = idUser;
        this.text = text;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
