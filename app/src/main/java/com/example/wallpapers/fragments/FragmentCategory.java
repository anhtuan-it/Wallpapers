package com.example.wallpapers.fragments;

import android.os.Bundle;
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
import com.example.wallpapers.adapters.TypeImageAdapter;
import com.example.wallpapers.adapters.TypeImageModel;
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

public class FragmentCategory extends Fragment {
    public FragmentCategory() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // Khoi tao recyclerview
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Khoi tao Okhttp-Client de lay du lieu
        OkHttpClient client = new OkHttpClient();

        // Khoi tao Moshi Adapter de chuyen doi json sang model java
        Moshi moshi = new Moshi.Builder().build();
        Type usersType = Types.newParameterizedType(List.class, TypeImageModel.class);
        final JsonAdapter<List<TypeImageModel>> listJsonAdapter = moshi.adapter(usersType);

        // Tao request len server
        Request request = new Request.Builder()
                .url("https://anhtuan-it.000webhostapp.com/api/type/")
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
                final List<TypeImageModel> typeImageModelList = listJsonAdapter.fromJson(json);

                // Hien thi len recyclerview
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new TypeImageAdapter(typeImageModelList, getActivity()));
                    }
                });
            }
        });

        return view;
    }
}
