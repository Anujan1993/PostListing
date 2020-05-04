package com.example.displaypost;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {

    private static final String UriData = "http://jsonplaceholder.typicode.com/";
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_post,container,false);

//        recyclerView = (RecyclerView)view.findViewById(R.id.ListOfPost);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        
        lodeRecyclerView();

        return view;

    }

    private void lodeRecyclerView() {
//        ProgressDialog progressDialog = new ProgressDialog(this.getContext());
//        progressDialog.setMessage("Loading");
//        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Call <List<listPosts>> call = requestInterface.getPostJson();
        call.enqueue(new Callback<List<listPosts>>() {
            @Override
            public void onResponse(Call<List<listPosts>> call, Response<List<listPosts>> response) {
                Toast.makeText(getActivity(), "Request Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<listPosts>> call, Throwable t) {
                Toast.makeText(getActivity(), "Request Not", Toast.LENGTH_LONG).show();
            }
        });
    }
}
