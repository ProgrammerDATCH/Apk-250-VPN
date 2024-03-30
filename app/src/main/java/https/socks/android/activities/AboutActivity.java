package https.socks.android.activities;

import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
import android.os.Bundle;
import com.mudasobwatunnel.plus.R;
import android.view.View;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.text.Html;
import android.content.Intent;
import android.net.Uri;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import https.socks.android.SocksHttpApp;
import com.slipkprojects.ultrasshservice.tunnel.TunnelUtils;
import android.widget.ImageView;
import android.widget.Button;
import android.view.Gravity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.content.Context;
import android.view.ViewGroup;
import https.socks.android.util.Utils;
import com.mudasobwatunnel.plus.BuildConfig;

public class AboutActivity extends BaseActivity implements OnClickListener {

	private Toolbar tb;
	private View changelog, license, dev;
    private TextView app_info_text;

	private AdView adsBannerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		adsBannerView = (AdView) findViewById(R.id.adView2);
		if (!BuildConfig.DEBUG) {
			//adsBannerView.setAdUnitId(SocksHttpApp.ADS_UNITID_BANNER_SOBRE);
		}
		
		tb = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(tb);
		changelog = findViewById(R.id.changelog);
		license = findViewById(R.id.license);
		dev = findViewById(R.id.developer);
		changelog.setOnClickListener(this);
		license.setOnClickListener(this);
		dev.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PackageInfo pinfo = Utils.getAppInfo(this);
        if (pinfo != null) {
            String version_nome = pinfo.versionName;
            int version_code = pinfo.versionCode;
            String header_text = String.format("%s (%d)", version_nome, version_code);
            app_info_text = (TextView) findViewById(R.id.appVersion);
			app_info_text.setText(header_text);
		}
	
	
		if (TunnelUtils.isNetworkOnline(this)) {
			
			adsBannerView.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {
					if (adsBannerView != null) {
						adsBannerView.setVisibility(View.VISIBLE);
					}
				}
			});

			adsBannerView.loadAd(new AdRequest.Builder()
				.build());
		}
	}

	@Override
	public void onClick(View view) {
		// TODO: Implement this method
		int id = view.getId();
		if (id == R.id.changelog) {
			changelog();
		} else if (id == R.id.developer) {
			startActivity(new Intent("android.intent.action.VIEW", 
									 Uri.parse("https://m.me/Alzero.blue/")));
		}
	}

	private void changelog() {
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View inflate = inflater.inflate(R.layout.changes_logs, (ViewGroup) null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
		builer.setView(inflate); 
		ImageView iv = inflate.findViewById(R.id.about_image);
		TextView title = inflate.findViewById(R.id.about_text1);
		TextView ms = inflate.findViewById(R.id.about_text2);
		TextView ms2 = inflate.findViewById(R.id.about_text3);
		TextView ms3 = inflate.findViewById(R.id.about_text4);
		TextView ms4 = inflate.findViewById(R.id.about_text5);
		TextView ms5 = inflate.findViewById(R.id.about_text6);
		TextView ms6 = inflate.findViewById(R.id.about_text7);
		TextView ms7 = inflate.findViewById(R.id.about_text8);
		TextView ms8 = inflate.findViewById(R.id.about_text9);
		TextView ms9 = inflate.findViewById(R.id.about_text10);
		TextView ms10 = inflate.findViewById(R.id.about_text11);
		Button ok = inflate.findViewById(R.id.about_button);
		iv.setImageResource(R.drawable.icon);
		title.setText("Hello user!");
		ms.setText("Mudasobwa tunnel Plus is a tool set custom HTTP Header with VPN and proxy support");
		ms2.setText("How to connect?");
		ms3.setText("1. Select your desired server");
		ms4.setText("2. Select your desired payload");
		ms5.setText("3. Tap CONNECT button");
		ms6.setText("How to claim Time?");
		ms7.setText("1. Make sure you have internet connection or Data connection that can browse");
		ms8.setText("2. Click Add + Time");
		ms9.setText("3. And Click Watch Video. Make sure you finish the rewarded video so that you get coins and just simply convert your current coins to time");
		ms10.setText("Enjoy Fast and secure VPN");
		ok.setText("Need Help? Contact us!");
		final AlertDialog time = builer.create(); 
		time.setCanceledOnTouchOutside(false);
		time.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		time.getWindow().setGravity(Gravity.CENTER); 
		time.show();
		ok.setOnClickListener(new View.OnClickListener() { 
				@Override 
				public void onClick(View v) { 
					try
					{
						time.dismiss();
						startActivity(new Intent("android.intent.action.VIEW", 
												 Uri.parse("https://t.me/phctvpndev")));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}

				}});

		time.show();
}
	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();

		if (adsBannerView != null) {
			adsBannerView.resume();
		}
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();

		if (adsBannerView != null) {
			adsBannerView.pause();
		}
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();

		if (adsBannerView != null) {
			adsBannerView.destroy();
		}
	}

}



