package com.example.wallpapers.adapters;

public class WallpaperModel {
    int type_id;
    String type;
    String img_url;

    public WallpaperModel(int type_id, String type, String img_url) {
        this.type_id = type_id;
        this.type = type;
        this.img_url = img_url;
    }
}
