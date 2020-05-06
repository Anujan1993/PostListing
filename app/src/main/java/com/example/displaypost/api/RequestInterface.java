package com.example.displaypost.api;

import com.example.displaypost.response.PostRegister;
import com.example.displaypost.response.ComentDetails;
import com.example.displaypost.response.UserData;
import com.example.displaypost.response.listPosts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestInterface {
    @GET("posts")
    Call <List<listPosts>> getPostJson();

    @GET("comments")
    Call<List<ComentDetails>> getComentJson();

    @GET("users")
    Call<List<UserData>> getUserJson();

    @POST("posts")
    Call<listPosts> savePost(@Body PostRegister postRegister);
}
