package com.akbar.birbal.kahaniyan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;

public class DisplayScrolling extends AppCompatActivity {
    int id;
    InterstitialAd interstitialAd;
    AdRequest adRequest;
    TextView tv_fullstory, tv_title;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imageView_banner;
    String sTitle, sStory;

    int[] arrayImages = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten};

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_scrolling);
        Fabric.with(this,new Crashlytics());
        MobileAds.initialize(this, getString(R.string.app_id));
        mAdView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        interstitialAd.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView_banner = (ImageView) findViewById(R.id.iv_banner);
        FloatingActionButton floating_button = (FloatingActionButton) findViewById(R.id.floating_button);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_fullstory = (TextView) findViewById(R.id.tv_fullstory);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        id = getIntent().getExtras().getInt("ID");
        int setImage = id % 10;
        imageView_banner.setImageResource(arrayImages[setImage]);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getResources().getStringArray(R.array.storiesTitle)[id]);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");// this is hide the expandable title name
                    isShow = false;
                }
            }
        });

        sTitle = getResources().getStringArray(R.array.storiesTitle)[id];
        sStory = getResources().getStringArray(R.array.storiesData)[id];
        tv_title.setText(sTitle);

        sStory = sStory.replaceAll("अकबर", "<font color=\"#094D9A\"><b>अकबर</b></font>");
        sStory = sStory.replaceAll("बीरबल", "<font color=\"#006600\"><b>बीरबल</b></font>");
        sStory = sStory.replaceAll("शिक्षा:", "<font color=\"#000\"><br/><br/>शिक्षा:");
        tv_fullstory.setText(Html.fromHtml(sStory));

        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    String sAux = "\n" + sTitle + "\n\n";
                    sAux = sAux + "\n" + Html.fromHtml(sStory) + "\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.akbar.birbal.kahaniyan \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Share via"));
                } catch (Exception e) {
                    //e.toString();
                }

            }
        });
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (interstitialAd.isLoaded())
            interstitialAd.show();
        else
            interstitialAd.loadAd(adRequest);

        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
            finish();
             }

            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                finish();
            }
        });
    }
}
