package com.example.displaypost.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.displaypost.R;
import com.example.displaypost.api.NetworkClient;
import com.example.displaypost.api.RequestInterface;
import com.example.displaypost.response.ComentDetails;
import com.example.displaypost.entitys.Comments;
import com.example.displaypost.response.UserData;
import com.example.displaypost.entitys.UsersTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPostActivity extends AppCompatActivity {

    private TextView Title;
    private TextView Post;
    private TextView UserID;
    private TextView PostID;
    private ArrayList<Object> newARRAY;
    private String UseID;
    private String UsName;
    private String postID;
    private String post;
    private String title;
    private String userID;
    private String PosID;
    private String Uname;
    private String NserName;
    private String ComBody;
    private int CommentCount = 0;
    private int Uid;
    private int Commantid;
    private RequestInterface requestUserInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        getSupportActionBar().setTitle("View Post");

        Title = (TextView)findViewById(R.id.Title);
        Post = (TextView)findViewById(R.id.body);
        UserID = (TextView)findViewById(R.id.userID);
        PostID = (TextView)findViewById(R.id.PostID);

        // get the data from list
        final Intent intent = getIntent();
        title= intent.getStringExtra("Title");
        userID= intent.getStringExtra("UserID");
        postID= intent.getStringExtra("PostID");
        post= intent.getStringExtra("Body");

        Title.setText(title);
        Post.setText(post);
        newARRAY = new ArrayList<>();
        requestUserInterface = NetworkClient.retrofit.create(RequestInterface.class);

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected == true){
            userCheckClass();
            commentCountcheck();
        }
        else {
            noInternetUser();
            NoInternetComment();
        }
    }
    private void userCheckClass(){
        Call<List<UserData>> calls = requestUserInterface.getUserJson();
        calls.enqueue(new Callback<List<UserData>>() {

            @Override
            public void onResponse(Call<List<UserData>> calls, Response<List<UserData>> response) {
                UsersTable usersTable= new UsersTable();
                usersTable.deleteAll(UsersTable.class);
                for(int i = 0; i<response.body().size(); i++) {
                    UseID = String.valueOf(response.body().get(i).getId().intValue());
                    if(UseID.equals(userID)) {
                        UsName = response.body().get(i).getName();
                        PostID.setText(UsName);
                    }
                    Uid = response.body().get(i).getId().intValue();
                    Uname =String.valueOf(response.body().get(i).getName());
                    NserName=String.valueOf(response.body().get(i).getUsername());
                    usersTable =new UsersTable();
                    usersTable.setUserId(Uid);
                    usersTable.setUserName(Uname);
                    usersTable.save();
                }
            }

            @Override
            public void onFailure(Call<List<UserData>> calls, Throwable t) {
                Toast.makeText(ViewPostActivity.this, "Request Not", Toast.LENGTH_LONG).show();

            }
        });
    }
    private void commentCountcheck(){
        Call<List<ComentDetails>> callsC = requestUserInterface.getComentJson();
        callsC.enqueue(new Callback<List<ComentDetails>>() {

            @Override
            public void onResponse(Call<List<ComentDetails>> callsC, Response<List<ComentDetails>> responses) {
                Comments comment= new Comments();
                comment.deleteAll(Comments.class);
                for(int ii = 0; ii<responses.body().size(); ii++) {
                    PosID = String.valueOf(responses.body().get(ii).getPostId().intValue());

                    Uid = responses.body().get(ii).getPostId().intValue();
                    Commantid = responses.body().get(ii).getId().intValue();
                    ComBody =String.valueOf(responses.body().get(ii).getBody());
                    Comments comments= new Comments();
                    comments.setPostId(Uid);
                    comments.setCommantid(Commantid);
                    comments.setBody(ComBody);
                    comments.save();
                    if(PosID.equals(postID)) {
                        newARRAY.addAll(Collections.singleton(PosID));
                    }
                }
                CommentCount = newARRAY.size();
                UserID.setText(String.valueOf(CommentCount));
            }
            @Override
            public void onFailure(Call<List<ComentDetails>> callsC, Throwable t) {
                Toast.makeText(ViewPostActivity.this, "Request Not", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void noInternetUser(){
        List<UsersTable> userlist = UsersTable.listAll(UsersTable.class);
        for(int i = 0; i<userlist.size(); i++) {
            String  pID =  String.valueOf(userlist.get(i).getUserId());
            if(pID.equals(userID)) {
                UsName = userlist.get(i).getUserName();
                PostID.setText(UsName);
            }
        }
    }
    private  void NoInternetComment(){
        List<Comments> comment = Comments.listAll(Comments.class);
        for(int i = 0; i<comment.size(); i++) {
            String  pID =  String.valueOf(comment.get(i).getPostId());
            if(pID.equals(postID)) {
                newARRAY.addAll(Collections.singleton(pID));
            }
            CommentCount = newARRAY.size();
            UserID.setText(String.valueOf(CommentCount));
        }
    }
}
