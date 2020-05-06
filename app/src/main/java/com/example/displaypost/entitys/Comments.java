package com.example.displaypost.entitys;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class Comments extends SugarRecord {
    @Unique
    @Column(name = "ID")
    private long ID;
    @Column(name = "postId")
    private Integer postId;
    @Column(name = "Commantid")
    private Integer Commantid;
    @Column(name = "body")
    private String body;

    public Comments() {
    }

    public Comments(Integer postId, Integer commantid, String body) {
        this.postId = postId;
        Commantid = commantid;
        this.body = body;
    }

    public long getID() {
        return ID;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getCommantid() {
        return Commantid;
    }

    public void setCommantid(Integer commantid) {
        Commantid = commantid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
