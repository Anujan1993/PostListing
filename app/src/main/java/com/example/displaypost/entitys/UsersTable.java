package com.example.displaypost.entitys;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class UsersTable extends SugarRecord {

    @Unique
    @Column(name = "ID")
    private long Id;
    @Column(name = "userId")
    private Integer userId;
    @Column(name = "userName")
    private String userName;

    public UsersTable() {
    }

    public UsersTable(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public Long getId() {
        return Id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
