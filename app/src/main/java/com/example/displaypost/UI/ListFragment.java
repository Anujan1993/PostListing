package com.example.displaypost.UI;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.displaypost.adapter.PostAdaper;
import com.example.displaypost.R;
import com.example.displaypost.api.RequestInterface;
import com.example.displaypost.entitys.Posts;
import com.example.displaypost.response.listPosts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<listPosts> Data ;
    int postID,userID;
    String Title,Body;
    private Posts posts;
    private  ArrayList<Posts> postsArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_post,container,false);

        Data = new ArrayList<>();

        recyclerView = (RecyclerView)view.findViewById(R.id.ListOfPost);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected == true){
            lodeRecyclerView();
        }
        else {
            NoInternetFunction();
        }



        return view;

    }

    private void lodeRecyclerView() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Call <List<listPosts>> call = requestInterface.getPostJson();
        call.enqueue(new Callback<List<listPosts>>() {
            @Override
            public void onResponse(Call<List<listPosts>> call, Response<List<listPosts>> response) {
                //Toast.makeText(getActivity(), "Request Success", Toast.LENGTH_LONG).show();
                Posts post= new Posts();
                post.deleteAll(Posts.class);
                for(int ii = 0; ii<response.body().size(); ii++) {
                    postID = response.body().get(ii).getId().intValue();
                    userID = response.body().get(ii).getUserId().intValue();
                    Title = String.valueOf(response.body().get(ii).getTitle());
                    Body = String.valueOf(response.body().get(ii).getBody());

                    Posts posts= new Posts();
                    posts.setPostID(postID);
                    posts.setTitle(Title);
                    posts.setBody(Body);
                    posts.setUserID(userID);
                    posts.save();
                }
                Data.addAll(response.body());
                PostAdaper postAdaper = new PostAdaper(getActivity(),Data);
                recyclerView.setAdapter(postAdaper);

            }
            @Override
            public void onFailure(Call<List<listPosts>> call, Throwable t) {
                Toast.makeText(getActivity(), "Request Not", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void NoInternetFunction(){
        List<Posts> postsList = Posts.listAll(Posts.class);
        ArrayList<listPosts> postArrayList = new ArrayList<>();

        for(int ii = 0; ii<postsList.size(); ii++) {

            listPosts lp = new listPosts();
            lp.setUserId(postsList.get(ii).getUserID());
            lp.setTitle(postsList.get(ii).getTitle());
            lp.setId(postsList.get(ii).getPostID());
            lp.setBody(postsList.get(ii).getBody());

            postArrayList.add(lp);
        }
        PostAdaper postAdaper = new PostAdaper(getActivity(),postArrayList);
        recyclerView.setAdapter(postAdaper);
    }

}
