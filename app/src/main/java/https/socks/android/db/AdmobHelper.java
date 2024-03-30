package https.socks.android.db;

import android.content.*;
import com.google.android.gms.ads.*;
import android.view.*;
import java.util.*;
import com.google.android.gms.ads.reward.*;
import android.os.*;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Color;

public class AdmobHelper
{

    private Context context;

    private AdRequest.Builder adRequest;

    private InterstitialAd interstitial;

    private AdRequest adR;

    private AdView adView;

    public RewardedVideoAd mRewardedVideoAd;

    private String mRewardedId = "ca-app-pub-3940256099942544/5224354917";
    private boolean timerads = true;

    private String bannerId = "ca-app-pub-4536337331583307/3911801649";

    private String intertialId = "ca-app-pub-4536337331583307/3089674566";

    private boolean mShowInterAdsAuto = true;

    private AdmobHelper.RewardedListener rewardlistener;

    private RelativeLayout bannerView; 

    public interface RewardedListener {
        void onLoad()
        void onLoaded()
        void onReward(RewardItem rewarditem)
        void onFaild()
    }

    public AdmobHelper(Context c)
    {
        context = c;
        adRequest = new AdRequest.Builder();
        adRequest.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        adView = new AdView(context);
        adView.setAdListener(bal);
        interstitial = new InterstitialAd(context);
        interstitial.setAdListener(al);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);

        // Insert the Ad Unit ID
    }

    public AdmobHelper setMobileAdsId(String id)
    {
        MobileAds.initialize(context, id);
        return this;
    }

    public AdmobHelper setBannerView(RelativeLayout v)
    {
        this.bannerView = v;
		//adView.setId(123);
		/*TextView tv = new TextView(context);
		 // Create a LayoutParams for TextView
		 LayoutParams lp = new RelativeLayout.LayoutParams(
		 LayoutParams.WRAP_CONTENT, // Width of TextView
		 LayoutParams.WRAP_CONTENT); // Height of TextView
		 lp.addRule(RelativeLayout.RIGHT_OF, adView.getId());
		 // Apply the layout parameters to TextView widget
		 tv.setLayoutParams(lp);

		 // Set text to display in TextView
		 tv.setText("Close (x)");
		 // Set a text color for TextView text
		 tv.setTextColor(Color.WHITE);
		 tv.setBackgroundColor(Color.RED);
		 tv.setOnClickListener(new View.OnClickListener() {
		 @Override
		 public void onClick(View p1)
		 {
		 bannerView.setVisibility(View.GONE);
		 }
		 });*/
        bannerView.addView(adView);
		//bannerView.addView(tv);
        return this;
    }

    public AdmobHelper setBannerId(String id)
    {
        bannerId = id;
        adView.setAdUnitId(id);
        return this;
    }

    public AdmobHelper setBannerSize(AdSize size)
    {
        adView.setAdSize(size);
        return this;
    }

    public AdmobHelper setIntertitialId(String id)
    {
        intertialId = id;
        interstitial.setAdUnitId(id);
        return this;
    }

    public AdmobHelper setRewardedId(String id)
    {
        mRewardedId = id;
        return this;
    }

    public AdmobHelper setRewardAdsListener(RewardedListener listener)
    {
        rewardlistener = listener;
        mRewardedVideoAd.setRewardedVideoAdListener(rvl);
        return this;
    }

    public AdmobHelper setTestDevice(String device_id)
    {
        adRequest.addTestDevice(device_id);
        return this;
    }

    public AdmobHelper setShowInterAdsAuto(boolean status) {
        mShowInterAdsAuto = status;
        return this;
    }

    public AdmobHelper setAdsListener(AdListener listener)
    {
        if (adView != null)
        {
            adView.setAdListener(listener);
        }
        return this;
    }

    public AdmobHelper buildAdsRequest()
    {
        adR = adRequest.build();
        return this;
    }

    public AdmobHelper loadBannerAdsRequest()
    {
        if (!bannerId.isEmpty())
        {
            adView.loadAd(adR);
        }
        return this;
    }

    public AdmobHelper loadAdsRequest()
    {
        if (!bannerId.isEmpty())
        {
            adView.loadAd(adR);
        }
        if (!intertialId.isEmpty())
        {
            interstitial.loadAd(adR);
        }

        return this;
    }

	public void loadIntertitial() {
		if (!intertialId.isEmpty())
        {
            interstitial.loadAd(adR);
        }
	}

    public void loadRewardedAds() {
        if (!mRewardedId.isEmpty())
        {
            rewardlistener.onLoad();
            mRewardedVideoAd.loadAd(mRewardedId, new AdRequest.Builder().build());
        }
    }

    public AdmobHelper initTimerAds(int time)
    {
        timerads = true;
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                        public void run()
                        {
                            if (adR != null)
                            {
                                if (!bannerId.isEmpty())
                                {
                                    adView.loadAd(adR);
                                }
                                if (!intertialId.isEmpty())
                                {
                                    interstitial.loadAd(adR);
                                }
                            }
                        }
                    });
            }
        };
        timer.schedule(doAsynchronousTask, 0, time);
        return this;
    }

    public void reloadAds()
    {
        if (!timerads)
        {
            if (adR != null)
            {
                if (!bannerId.isEmpty())
                {
                    adView.loadAd(adR);
                }
                if (!intertialId.isEmpty())
                {
                    interstitial.loadAd(adR);
                }
            }
        }
    }

    public void showIntertitialAds()
    {
        if (interstitial != null)
        {
            if (interstitial.isLoaded())
            {
                interstitial.show();
            }
        }
    }

    public void showRewardedAds()
    {
        if (!mRewardedId.isEmpty())
        {
            mRewardedVideoAd.show();
        }
    }

    RewardedVideoAdListener rvl = new RewardedVideoAdListener() {

        @Override
        public void onRewardedVideoAdLoaded()
        {
            // TODO: Implement this method
            rewardlistener.onLoaded();
        }

        @Override
        public void onRewardedVideoAdOpened()
        {
            // TODO: Implement this method
        }

        @Override
        public void onRewardedVideoStarted()
        {
            // TODO: Implement this method
        }

        @Override
        public void onRewardedVideoAdClosed()
        {
            // TODO: Implement this method

        }

        @Override
        public void onRewarded(RewardItem p1)
        {
            // TODO: Implement this method
            rewardlistener.onReward(p1);
            // Reward the user.
        }

        @Override
        public void onRewardedVideoAdLeftApplication()
        {
            // TODO: Implement this method
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int p1)
        {
            // TODO: Implement this method
            rewardlistener.onFaild();
        }
	};

    AdListener bal = new AdListener() {

        @Override
        public void onAdLoaded()
        {
            // Code to be executed when an ad finishes loading.
            bannerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAdFailedToLoad(int errorCode)
        {
            // Code to be executed when an ad request fails.
            bannerView.setVisibility(View.GONE);
        }

        @Override
        public void onAdOpened()
        {
            //Toast.makeText(context, "", 0).show();
            // Code to be executed when an ad opens an overlay that
            // covers the screen.
        }

        @Override
        public void onAdLeftApplication()
        {
            // Code to be executed when the user has left the app.
        }

        @Override
        public void onAdClosed()
        {

            //Toast.makeText(context, "Thanks for supporting us.", 0).show();
            // Code to be executed when when the user is about to return
            // to the app after tapping on an ad.
            //reloadAds();
        }
    };

    AdListener al = new AdListener() {

        @Override
        public void onAdLoaded()
        {
            // Code to be executed when an ad finishes loading.
            if(mShowInterAdsAuto) {
                interstitial.show();
            }
        }

        @Override
        public void onAdFailedToLoad(int errorCode)
        {
            // Code to be executed when an ad request fails.
        }

        @Override
        public void onAdOpened()
        {
            //Toast.makeText(context, "", 0).show();
            // Code to be executed when an ad opens an overlay that
            // covers the screen.
        }

        @Override
        public void onAdLeftApplication()
        {
            // Code to be executed when the user has left the app.
        }

        @Override
        public void onAdClosed()
        {
            if(!mShowInterAdsAuto) {
                loadAdsRequest();
            }
            //Toast.makeText(context, "Thanks for supporting us.", 0).show();
            // Code to be executed when when the user is about to return
            // to the app after tapping on an ad.
            //reloadAds();
        }
    };
}






