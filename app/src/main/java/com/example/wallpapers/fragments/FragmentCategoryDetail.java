package com.example.wallpapers.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpapers.R;
import com.example.wallpapers.adapters.WallpaperAdapter;
import com.example.wallpapers.adapters.WallpaperModel;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentCategoryDetail extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_category_detail);

        // Lay id hinh anh
        final String id = getIntent().getStringExtra("id");
        // Lay the loai hinh anh
        final String type = getIntent().getStringExtra("type");

        //set tieu de toolbar
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Category: " + type);

        dialog();

        // Khoi tao recyclerview
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_category_detail);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Khoi tao Okhttp-Client de lay du lieu
        OkHttpClient client = new OkHttpClient();

        // Khoi tao Moshi Adapter de chuyen doi json sang model java
        Moshi moshi = new Moshi.Builder().build();
        Type usersType = Types.newParameterizedType(List.class, WallpaperModel.class);
        final JsonAdapter<List<WallpaperModel>> listJsonAdapter = moshi.adapter(usersType);

        // Tao request len server
        Request request = new Request.Builder()
                .url("https://anhtuan-it.000webhostapp.com/api/types/" + id + "/")
                .build();

        // Thuc thi request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", "Network Error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // Lay thong tin json tra ve
                String json = response.body().string();
                final List<WallpaperModel> wallpaperModelList = listJsonAdapter.fromJson(json);

                // Hien thi len recyclerview
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new WallpaperAdapter(wallpaperModelList, FragmentCategoryDetail.this));
                    }
                });
            }
        });
    }
    public void dialog() {
        // dialog loading
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        // hien thi dialog
        progressDialog.show();

        // dong dialog sau 3s
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000);
    }

}
