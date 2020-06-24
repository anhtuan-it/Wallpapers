package com.example.wallpapers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.example.wallpapers.fragments.FragmentAbout;
import com.example.wallpapers.fragments.FragmentCategory;
import com.example.wallpapers.fragments.FragmentHome;
import com.example.wallpapers.fragments.ViewPaperAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPaperAdapter viewPaperAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wallpapers");
        setViewPaper();

    }

    private void setViewPaper() {
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpaper);
        viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager());

        // Them fragment
        viewPaperAdapter.addFragment(new FragmentHome(), "Home");
        viewPaperAdapter.addFragment(new FragmentCategory(), "Categories");
        viewPaperAdapter.addFragment(new FragmentAbout(), "About");

        viewPager.setAdapter(viewPaperAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}