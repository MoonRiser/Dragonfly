package com.example.dragonfly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dragonfly.R;

import java.util.List;

public class MyGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> imgs;
    private Context context;


    public MyGalleryAdapter(List<Integer> imgs) {
        this.imgs = imgs;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.iv_content);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_gallery, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Glide.with(context)
                .load(imgs.get(position))
                .centerCrop()
                // .transform(new MultiTransformation(new CenterCrop(), new RoundedCorners(16)))
                .into(((MyViewHolder) holder).imageView);

    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }
}
