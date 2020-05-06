package com.example.displaypost.entitys;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class Posts extends SugarRecord {

    @Unique
    @Column(name = "ID")
    private long ID;
    @Column(name = "userID")
    private Integer userID;
    @Column(name = "postID")
    private Integer postID;
    @Column(name = "Title")
    private String Title;
    @Column(name = "Body")
    private String body;

    public Posts() {
    }

    public Posts(Integer userID,Integer postID, String title, String body) {
        this.userID = userID;
        postID = postID;
        Title = title;
        this.body = body;
    }

    public long getID() {
        return ID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
