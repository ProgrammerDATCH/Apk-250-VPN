package https.socks.android;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import java.util.Locale;
import com.mudasobwatunnel.plus.R;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.google.android.gms.ads.AdView;
import android.widget.RelativeLayout;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.view.Gravity;
import com.google.android.gms.ads.AdSize;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;

public class CoinsActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private TextView my_coins;
    private long current_coins;
    private Button addcoins, btn1, btn2, btn3, btn4, btn5;

    private boolean success;
    public static SharedPreferences sp;
    private long add_time;

    private RewardedVideoAd rewardedAd;

    private ProgressDialog progressDialog;

    private String key = "faraja";
    private CountDownTimer mBtnCountDown;

    private long mTimeLeftBtn;

    private long hours;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        my_coins = (TextView) findViewById(R.id.coins);
        addcoins = (Button) findViewById(R.id.addcoins);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MobileAds.initialize(this,"ca-app-pub-4327217352829821~2717766369");
        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                }
            });

        addcoins.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {   

                    loadingAds();
                    loadRewardedVideoAd();

                }
            });


        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    convert(1);


                }
            });



        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    convert(5);


                }
            });

        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    convert(10);


                }
            });
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    convert(30);


                }
            });
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    convert(100);


                }
            });


        // Use an activity context to get the rewarded video instance.
        rewardedAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedAd.setRewardedVideoAdListener(this);



    }

    private void convert(long coins) {

        SharedPreferences saved_token = getSharedPreferences("code", Context.MODE_PRIVATE);

        String admin = saved_token.getString("SAVED_CODE", "");

        if (admin.equals(key)){
            success = true;

        }else{

            SharedPreferences mCoins = getSharedPreferences("coins", Context.MODE_PRIVATE);

            long saved_coins = mCoins.getLong("SAVED_COINS", 1);

            SharedPreferences.Editor coins_edit = mCoins.edit();

            if (saved_coins < coins) {
                Toast.makeText(this, "Not enough coins!", Toast.LENGTH_LONG).show();
                success = false;
            } else {
                long x = saved_coins - coins;

                coins_edit.putLong("SAVED_COINS", x).apply();

                updateCoins();

                success = true;
            }
        }


        if (success) {

            if (coins == 1) {

                add_time = 2 * 3600 * 1000;

                hours = 2;

            } else if (coins == 5) {

                add_time = 11 * 3600 * 1000;

                hours = 11;

            } else if (coins == 10) {

                add_time =  24 * 3600 * 1000;

                hours = 24;

            } else if (coins == 30) {

                add_time = 4 * 24 * 3600 * 1000;

                hours = 96;

            } else if (coins == 100) {

                add_time = 24 * 24 * 3600 * 1000;

                hours = 576;

            }
            sp.edit().putLong("AddedTime", add_time).apply();
            sp.edit().putLong("isAdded", 1).apply();
            Toast.makeText(this, hours+ " hours successfully added to your time!", Toast.LENGTH_LONG).show();
            finish();

        }

    }

    private void addCoins(long coins) {
        long added_coins = current_coins + coins;

        SharedPreferences mCoins = getSharedPreferences("coins", Context.MODE_PRIVATE);

        SharedPreferences.Editor coins_edit = mCoins.edit();

        coins_edit.putLong("SAVED_COINS", added_coins);

        coins_edit.apply();

        updateCoins();

    }

    private void updateCoins() {

        SharedPreferences mCoins = getSharedPreferences("coins", Context.MODE_PRIVATE);

        long saved_coins = mCoins.getLong("SAVED_COINS", 1);

        current_coins = saved_coins;

        SharedPreferences saved_token = getSharedPreferences("code", Context.MODE_PRIVATE);

        String admin = saved_token.getString("SAVED_CODE", "");

        if (admin.equals(key)){
            my_coins.setText("Unlimited");
        }else{
            my_coins.setText(Long.toString(current_coins));

        }

    }

    private void loadingAds() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while loading...\n\nNote: You need to finish the video to claim your coins reward!");
        progressDialog.setTitle("Loading Video");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }


    private void showError(int i) {
        new AlertDialog.Builder(this)
            . setTitle("Failed to load Video")
            . setMessage("Failed to Load Video w/ error code: "+ i+ ", please contact the developer for further assistance !!")
            . setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface di, int p) {
                    // ok
                }
            })
            . setCancelable(true)
            . show();

    }


    private void loadRewardedVideoAd() {
        rewardedAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                          new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        if (rewardedAd.isLoaded()) {
            rewardedAd.show();
        }
        progressDialog.dismiss();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(CoinsActivity.this, "Thank you for supporting the app !! 💙", Toast.LENGTH_SHORT).show();
        btnTimer();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        addCoins(rewardItem.getAmount());
        Toast.makeText(CoinsActivity.this, rewardItem.getAmount() + " coin(s) successfully added!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

        showError(i);
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateCoins();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    private void unliTime(){
        View v = LayoutInflater.from(CoinsActivity.this).inflate(R.layout.admin_access, null);
        final EditText token = v.findViewById(R.id.code);

        AlertDialog dialog=new AlertDialog.Builder(CoinsActivity.this)
            .setView(v)
            .setPositiveButton("Authorize", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dia, int which) {
                    if (token.getText().toString().equals(key)){
                        Toast.makeText(CoinsActivity.this, "Authorized!", Toast.LENGTH_LONG).show();

                        SharedPreferences saved_token = getSharedPreferences("code", Context.MODE_PRIVATE);

                        SharedPreferences.Editor token_edit = saved_token.edit();

                        token_edit.putString("SAVED_CODE", key);

                        token_edit.apply();

                        updateCoins();


                    }else{
                        Toast.makeText(CoinsActivity.this, "Invalid token!", Toast.LENGTH_LONG).show();
                    }
                }
            })
            .setNegativeButton("Reset", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dia, int which) {

                    SharedPreferences saved_token = getSharedPreferences("code", Context.MODE_PRIVATE);

                    SharedPreferences.Editor token_edit = saved_token.edit();

                    token_edit.clear();

                    token_edit.apply();

                    updateCoins();


                }
            })
            .create();
        dialog.show();


    }
    private void btnTimer() {

        mBtnCountDown = new CountDownTimer(16000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftBtn = millisUntilFinished;
                addcoins.setEnabled(false);
                updateBtnText();
            }
            @Override
            public void onFinish() {
                addcoins.setEnabled(true);
                addcoins.setText("GET MORE COINS - FREE");
            }

        }.start();

    }


    private void updateBtnText() {
        int seconds = (int) (mTimeLeftBtn / 1000) % 60;
        String timeLeftFormatted;
        if (seconds > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                                              "%02d", seconds);

            addcoins.setText("Refresh in " + timeLeftFormatted);

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Menu Itens
        switch (item.getItemId()) {

            case R.id.token:

                unliTime();

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
