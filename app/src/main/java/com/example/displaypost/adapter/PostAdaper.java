package com.example.displaypost.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.displaypost.R;
import com.example.displaypost.UI.ViewPostActivity;
import com.example.displaypost.response.listPosts;

import java.util.ArrayList;

public class PostAdaper extends RecyclerView.Adapter<PostAdaper.postViewAdapter> {

    ArrayList<listPosts> data1;
    Context context;

    public PostAdaper(Context ct, ArrayList<listPosts> s1){
        context = ct;
        data1 = s1;
    }
    @NonNull
    @Override
    public postViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_row,parent,false);
        return new postViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postViewAdapter holder, final int position) {
        holder.textView.setText(data1.get(position).getTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPostActivity.class);
                intent.putExtra("Title", data1.get(position).getTitle());
                intent.putExtra("UserID", String.valueOf(data1.get(position).getUserId().intValue()));
                intent.putExtra("PostID",String.valueOf( data1.get(position).getId().intValue()));
                intent.putExtra("Body", data1.get(position).getBody());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class postViewAdapter extends RecyclerView.ViewHolder{
        TextView textView;
        public postViewAdapter(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.Title);

        }
    }
}
