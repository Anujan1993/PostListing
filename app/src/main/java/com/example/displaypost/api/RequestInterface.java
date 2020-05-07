package com.example.displaypost.api;

import com.example.displaypost.response.PostRegister;
import com.example.displaypost.response.ComentDetails;
import com.example.displaypost.response.UserData;
import com.example.displaypost.response.ListPosts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestInterface {
    @GET("posts")
    Call <List<ListPosts>> getPostJson();

    @GET("comments")
    Call<List<ComentDetails>> getComentJson();

    @GET("users")
    Call<List<UserData>> getUserJson();

    @POST("posts")
    Call<ListPosts> savePost(@Body PostRegister postRegister);
}
