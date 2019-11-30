package com.example.twitchapi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    ArrayList<String> userName;
    ArrayList<String> type;
    ArrayList<String> title;
    ArrayList<String> viewCount;
    ArrayList<String> thumbnail;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> userName, ArrayList<String> type, ArrayList<String> title,
                         ArrayList<String> viewCount, ArrayList<String> thumbnail) {
        this.context = context;
        this.userName = userName;
        this.type = type;
        this.title = title;
        this.viewCount = viewCount;
        this.thumbnail = thumbnail;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // set the data in items
        holder.name.setText(userName.get(position));
        holder.vtype.setText("Type: "+type.get(position));
        holder.vtitle.setText("Title: "+title.get(position));
        holder.viewcount.setText("ViewCount: "+viewCount.get(position));
        Picasso.get().load(thumbnail.get(position)).into(holder.vthumbnail);
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, userName.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Activity2.class);
                intent.putExtra("name", userName.get(position));
                intent.putExtra("title", title.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, vtype, vtitle, viewcount;
        ImageView vthumbnail;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            vtype = (TextView) itemView.findViewById(R.id.vtype);
            vtitle = (TextView) itemView.findViewById(R.id.vtitle);
            viewcount = (TextView) itemView.findViewById(R.id.viewcount);
            vthumbnail = (ImageView) itemView.findViewById(R.id.vthumbnail);
        }
    }
}
