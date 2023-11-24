package com.example.amigosdavizinhanca.model;

import android.content.Context;

import com.example.amigosdavizinhanca.controller.Post;

import java.util.ArrayList;

public class DataModel {

    private static DataModel instance = new DataModel();

    private DataModel(){

    }
    public static DataModel getInstance(){

        if (instance == null) {
            instance =new DataModel();
        }
        return instance;
    }
    private ArrayList<Post> posts;

    private PostDataBase database;

    public void createDataBase(Context context){
        database = new PostDataBase(context);
        posts = database.getPostsFromDB();
    }
    public ArrayList<Post> getPosts(){
        return posts;
    }
    public Post getPost(int pos){
        return posts.get(pos);
    }

    public int getPostsSize() {
        return posts.size();
    }

   public boolean addPost (Post p){
        long id = database.createPostInDB(p);
        if(id>0){
            p.setId(id);
            posts.add(p);
            return true;
        }
        return false;
   }    public boolean insertPost(Post p,int pos){
        long id = database.insertPostInDB(p);
        if(id>0){
            posts.add(pos,p);
            return true;
        }
        return false;
    }
    public boolean updatePost(Post p,int pos){
        int count = database.updatePostsInDB(p);
        if (count == 1){
            posts.set(pos,p);
            return true;
        }
        return false;
    }
    public boolean deletePost(int pos) {
        if (pos >= 0 && pos < getPostsSize()) {
            Post postToDelete = getPost(pos);
            int count = database.deletePostInDB(postToDelete);
            if (count == 1) {
                posts.remove(pos);
                return true;
            }
        }
        return false;
    }
}
