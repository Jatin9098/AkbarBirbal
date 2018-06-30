package com.akbar.birbal.kahaniyan;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class SplashScreen extends AppCompatActivity {

    AdRequest  adRequest;
    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        MobileAds.initialize(this,getString(R.string.app_id));

       adRequest = new AdRequest.Builder().build();
       interstitialAd = new InterstitialAd(this);
       interstitialAd.setAdUnitId(getString(R.string.interstitial_id));
       interstitialAd.loadAd(adRequest);


//       if (interstitialAd.isLoaded())
//            interstitialAd.show();
//       else
//           interstitialAd.loadAd(adRequest);


       interstitialAd.setAdListener(new AdListener(){

           @Override
           public void onAdClosed() {
               Log.d("IDD", "onAdClosed: ");

               Intent intent = new Intent();
               intent.setClass(SplashScreen.this, MainActivity.class);
               startActivity(intent);
               overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               finish();
           }

           @Override
           public void onAdLoaded() {
               Log.d("IDD", "onAdLoaded: ");
               interstitialAd.show();
           }

           @Override
           public void onAdFailedToLoad(int i) {
               Log.d("IDD", "onAdFailedToLoad: "+i);
               Intent intent = new Intent();
               intent.setClass(SplashScreen.this, MainActivity.class);
               startActivity(intent);
               overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               finish();
           }
       });


        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
        },3000);*/
    }
}
