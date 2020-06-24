package com.example.wallpapers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpapers.R;
import com.example.wallpapers.fragments.FragmentCategoryDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TypeImageAdapter extends RecyclerView.Adapter<TypeImageAdapter.ViewHolder> {
    private List<TypeImageModel> typeImageModelList;
    private Context contextM;

    public TypeImageAdapter(List<TypeImageModel> typeImageModelList, Context contextM) {
        this.typeImageModelList = typeImageModelList;
        this.contextM = contextM;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextM).inflate(R.layout.rcv_items_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TypeImageModel typeImageModel = typeImageModelList.get(position);
        holder.type.setText(typeImageModel.type);
        Picasso.with(contextM).load(typeImageModel.img_url).into(holder.img_url);

        // Lang nghe su kien click vao LinearLayout
        holder.parentLayout_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCategory = new Intent(contextM, FragmentCategoryDetail.class);
                intentCategory.putExtra("id", typeImageModel.id);
                intentCategory.putExtra("type", typeImageModel.type);
                contextM.startActivity(intentCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeImageModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView type;
        private ImageView img_url;
        private LinearLayout parentLayout_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.txtCategory);
            img_url = itemView.findViewById(R.id.img_category);
            parentLayout_category = itemView.findViewById(R.id.parentLayout_category);
        }
    }
}
