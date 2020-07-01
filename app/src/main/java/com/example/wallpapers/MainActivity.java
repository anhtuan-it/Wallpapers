package com.example.wallpapers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

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
        checkNetwork();

    }

    // Kiem tra ket noi internet
    private void checkNetwork() {
        boolean wifiConnection = false;
        boolean mobileConnection = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // neu co ket noi internet wifi hoac mobile
        if(networkInfo != null && networkInfo.isConnected()) {
            wifiConnection = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnection = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiConnection) {
                setViewPaper();
            } else if(mobileConnection) {
                setViewPaper();
                }
        } else { // neu khong co ket noi mang

            // dialog loading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("No internet connection. Please connect to the internet and try again! The application will automatically close!");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);

            // hien thi dialog
            progressDialog.show();

            // dong ung dung sau 7s
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    System.exit(0);
                }
            }, 7000);
        }
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