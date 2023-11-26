package com.example.amigosdavizinhanca.controller;

public class Post {
    private long id, creatorId, date;
    private String text, uri;

    public Post (long id, String text, long creatorId, String uri, long date) {
        this.id = id;
        this.text = text;
        this.creatorId = creatorId;
        this.uri = uri;
        this.date = date;
    }

    public Post (String text, long creatorId, String uri, long date) {
        this.text = text;
        this.creatorId = creatorId;
        this.uri = uri;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
