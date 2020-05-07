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
import com.example.displaypost.api.NetworkClient;
import com.example.displaypost.api.RequestInterface;
import com.example.displaypost.entitys.Posts;
import com.example.displaypost.response.ListPosts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ListPosts> Data ;
    private int postID;
    private int userID;
    private String Title;
    private String Body;
    private Posts posts;
    private RequestInterface requestInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_post,container,false);

        Data = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.ListOfPost);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        requestInterface = NetworkClient.retrofit.create(RequestInterface.class);

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
        Call <List<ListPosts>> call = requestInterface.getPostJson();
        call.enqueue(new Callback<List<ListPosts>>() {
            @Override
            public void onResponse(Call<List<ListPosts>> call, Response<List<ListPosts>> response) {
                //Toast.makeText(getActivity(), "Request Success", Toast.LENGTH_LONG).show();
                Posts post= new Posts();
                post.deleteAll(Posts.class);
                for(int i = 0; i<response.body().size(); i++) {
                    postID = response.body().get(i).getId().intValue();
                    userID = response.body().get(i).getUserId().intValue();
                    Title = String.valueOf(response.body().get(i).getTitle());
                    Body = String.valueOf(response.body().get(i).getBody());

                    posts = new Posts();
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
            public void onFailure(Call<List<ListPosts>> call, Throwable t) {
                Toast.makeText(getActivity(), "Request Not", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void NoInternetFunction(){
        List<Posts> postsList = Posts.listAll(Posts.class);
        ArrayList<ListPosts> postArrayList = new ArrayList<>();
        for(int i = 0; i<postsList.size(); i++) {
            ListPosts lp = new ListPosts();
            lp.setUserId(postsList.get(i).getUserID());
            lp.setTitle(postsList.get(i).getTitle());
            lp.setId(postsList.get(i).getPostID());
            lp.setBody(postsList.get(i).getBody());
            postArrayList.add(lp);
        }
        PostAdaper postAdaper = new PostAdaper(getActivity(),postArrayList);
        recyclerView.setAdapter(postAdaper);
    }

}
