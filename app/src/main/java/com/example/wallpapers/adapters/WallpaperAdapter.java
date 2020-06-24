package com.example.wallpapers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpapers.R;
import com.example.wallpapers.fragments.FragmentHomeDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {

    private List<WallpaperModel> wallpaperModelList;
    private Context context;

    public WallpaperAdapter(List<WallpaperModel> wallpaperModelList, Context context) {
        this.wallpaperModelList = wallpaperModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rcv_items_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WallpaperModel wallpaperModel = wallpaperModelList.get(position);
        Picasso.with(context).load(wallpaperModel.img_url).into(holder.img);

        // Lang nghe su kien click vao LinearLayout
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FragmentHomeDetail.class);
                intent.putExtra("img_url", wallpaperModel.img_url);
                intent.putExtra("type", wallpaperModel.type);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
