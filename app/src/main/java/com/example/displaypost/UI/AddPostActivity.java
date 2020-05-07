package com.example.displaypost.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.displaypost.R;
import com.example.displaypost.api.NetworkClient;
import com.example.displaypost.api.RequestInterface;
import com.example.displaypost.response.PostRegister;
import com.example.displaypost.response.ListPosts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    private EditText Title;
    private EditText body;
    private Button back;
    private Button submit;
    private int userID=1;
    private RequestInterface requestInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);
        Title = (EditText)findViewById(R.id.Titletext);
        body = (EditText)findViewById(R.id.BodyTest);
        back =(Button)findViewById(R.id.BacktoHome);
        submit=(Button)findViewById(R.id.Submit);
        getSupportActionBar().setTitle("Add Post");

        requestInterface = NetworkClient.retrofit.create(RequestInterface.class);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost(savelistposts());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public PostRegister savelistposts(){
        PostRegister listposts = new PostRegister();
        listposts.setTitle(Title.getText().toString());
        listposts.setBody(body.getText().toString());
        listposts.setUserId(userID);

        return  listposts;
    }

    public void savePost(PostRegister listposts){

        Call<ListPosts> call = requestInterface.savePost(listposts);
        call.enqueue(new Callback<ListPosts>() {
            @Override
            public void onResponse(Call<ListPosts> call, Response<ListPosts> response) {
                Toast.makeText(AddPostActivity.this, "Post Successfully Added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ListPosts> call, Throwable t) {
                Toast.makeText(AddPostActivity.this, "Request not", Toast.LENGTH_LONG).show();
            }
        });
    }
}
