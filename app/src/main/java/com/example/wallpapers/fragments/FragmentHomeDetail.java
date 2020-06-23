package com.example.wallpapers.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wallpapers.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class FragmentHomeDetail extends AppCompatActivity {
    Button buttonDownload;
    Button buttonSetWallpaper;
    ImageView logo;
    private static final int PERMISSION_STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home_detail);

        // Lay du lieu hinh anh tu fragment home va hien thi len fragment detail
        final String img_url = getIntent().getStringExtra("img_url");
        logo = findViewById(R.id.img_home_detail);
        Picasso.with(this).load(img_url).into(logo);

        buttonDownload = (Button) findViewById(R.id.btnDownload);
        buttonSetWallpaper = (Button) findViewById(R.id.btnSetWallpaper);

        // Lang nghe su kien click nut download
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Neu la android 6.0 tro len thi yeu cau quyen truy cap
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        // Neu nguoi dung tu choi quyen truy cap
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_STORAGE_CODE);
                    } else {
                        // Nguoi dung cap quyen truy cap thi bat dau download
                        startDownload();
                    }
                } else {
                    // Neu he dieu hanh nho hon 6.0 thi download ma khong can cap quyen truy cap
                    startDownload();
                }
            }
        });

        // Lang nghe su kien click nut set wallpaper
        buttonSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lay link hinh anh
                Picasso.with(getBaseContext()).load(img_url).into(target);
            }
        });
    }
    // Ham download hinh anh
    private void startDownload() {

        // Lay link hinh anh
        String img_url = getIntent().getStringExtra("img_url").toString().trim();

        // Tao yeu cau download
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(img_url));

        // Cho phep loai mang de download (mang wifi hoac di dong)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download"); // Hien thi tieu de thong bao download
        request.setDescription("Downloading..."); // Hien thi mo ta thong bao download

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis()); // Lay thoi gian hien tai lam ten file

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Neu duoc cap phep truy cap thi download
                    startDownload();
                } else {
                    // Neu truy cap bi tu choi thi hien thi thong bao loi
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(bitmap); // set hinh anh lam wallpaper
                Toast.makeText(FragmentHomeDetail.this, "Wallpaper was set!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
}