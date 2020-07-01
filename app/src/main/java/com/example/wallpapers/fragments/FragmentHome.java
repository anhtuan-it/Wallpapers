package com.example.wallpapers.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class FragmentHome extends Fragment {
    public FragmentHome() {
    }

    public void dialog() {
        // dialog loading
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        // hien thi dialog
        progressDialog.show();

        // dong dialog sau 5s
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 5000);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dialog();

        // Khoi tao recyclerview
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_home);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
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
                .url("https://anhtuan-it.000webhostapp.com/api/types/")
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new WallpaperAdapter(wallpaperModelList, getActivity()));
                    }
                });
            }
        });

        return view;
    }
}
