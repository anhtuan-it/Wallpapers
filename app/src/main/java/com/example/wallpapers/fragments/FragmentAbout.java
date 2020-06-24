package com.example.wallpapers.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wallpapers.R;

import org.w3c.dom.Text;

public class FragmentAbout extends Fragment {
    public FragmentAbout() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView txtNameApp = (TextView) view.findViewById(R.id.txtNameApp);
        TextView txtInfo = (TextView) view.findViewById(R.id.txtInfo);

        txtNameApp.setText("Wallpapers");
        txtInfo.setText("Developed by Lê Anh Tuấn - Built successfully on June 22, 2020 \n https://anhtuan-it.000webhostapp.com \n https://github.com/anhtuan-it");

        return view;
    }
}
