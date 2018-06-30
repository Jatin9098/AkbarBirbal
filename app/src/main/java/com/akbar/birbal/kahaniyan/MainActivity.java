package com.akbar.birbal.kahaniyan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {


    RecyclerView rv_items;

    private AdView mAdView;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());

        MobileAds.initialize(this, getString(R.string.app_id));
        mAdView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        rv_items = (RecyclerView) findViewById(R.id.rv_items);
        AdapterRecycler adapter = new AdapterRecycler(MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 1);
        rv_items.setLayoutManager(layoutManager);
        rv_items.setAdapter(adapter);

    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        mAdView.loadAd(adRequest);
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        mAdView.loadAd(new AdRequest.Builder().build());


        super.onRestart();
    }
}
