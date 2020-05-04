package com.example.displaypost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface RequestInterface {
    @GET("posts")
    Call <List<listPosts>> getPostJson();
}
