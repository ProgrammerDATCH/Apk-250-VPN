package https.socks.android;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import https.socks.android.SocksHttpApp;
import https.socks.android.activities.AboutActivity;
import https.socks.android.activities.BaseActivity;
import https.socks.android.activities.ConfigGeralActivity;
import https.socks.android.adapter.LogsAdapter;
import https.socks.android.adapter.SpinnerAdapter;
import https.socks.android.fragments.ProxyRemoteDialogFragment;
import https.socks.android.util.AESCrypt;
import https.socks.android.util.ConfigUpdate;
import https.socks.android.util.ConfigUtil;
import https.socks.android.util.Utils;
import com.slipkprojects.ultrasshservice.LaunchVpn;
import com.slipkprojects.ultrasshservice.SocksHttpService;
import com.slipkprojects.ultrasshservice.config.ConfigParser;
import com.slipkprojects.ultrasshservice.config.Settings;
import com.slipkprojects.ultrasshservice.logger.ConnectionStatus;
import com.slipkprojects.ultrasshservice.logger.SkStatus;
import com.slipkprojects.ultrasshservice.tunnel.TunnelManagerHelper;
import com.slipkprojects.ultrasshservice.tunnel.TunnelUtils;
import com.slipkprojects.ultrasshservice.util.SkProtect;
import com.mudasobwatunnel.plus.BuildConfig;
import com.mudasobwatunnel.plus.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.reward.RewardItem;
import android.graphics.Typeface;
import https.socks.android.adapter.PromoAdapter;
import android.util.TypedValue;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.view.Gravity;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import https.socks.android.activities.CustomDNS;
import https.socks.android.activities.GetPackageClass;
import https.socks.android.model.UpdateAsync;
import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.ArrayAdapter;
import android.widget.Adapter;
import android.app.ProgressDialog;
import https.socks.android.util.*;
import java.util.*;
import java.text.*;
import https.socks.android.view.*;
import org.json.*;
import android.view.*;
import android.util.*;
import android.view.ContextMenu.*;
import android.support.v4.widget.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.os.*;
import android.widget.AdapterView.OnItemSelectedListener;
import https.socks.android.adapter.SetupAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import https.socks.android.core.UpdateCore;
import android.widget.RelativeLayout;
import java.util.concurrent.TimeUnit;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Activity Principal
 * @author SlipkHunter
 */

public class SocksHttpMainActivity extends BaseActivity
implements View.OnClickListener,  RadioGroup.OnCheckedChangeListener,
CompoundButton.OnCheckedChangeListener, NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, SkStatus.StateListener
{


	private Spinner spin;
	private boolean cancelProgressBar;

	private ProgressDialog progressDialog;

	private TextView mTextViewCountDown;

	private Button mButtonSet;

	private SharedPreferences prefs;

	private Switch imgFavorite;

	private DrawerLayout drawer;

	private NavigationView navi;

    private LinearLayout messLay;

    private TextView tvMess;

	private int PICK_FILE;


	private CheckBox geoCheckBox;

	private TextInputLayout sslPayloadLay;

	private TextInputLayout payLaySsl;

	private TextInputLayout edUserLayout;

	private TextInputLayout edPassLayout;

	private EditText webuser;

	private EditText webpass;

	private TextInputLayout bugsLayout;

	private EditText bugremote;

	private ProgressDialog ppd;

	private InterstitialAd success;
	 private int default_time = 1; //(minutes)
	 private String time_over = "Your Time is Over";
	 private CountDownTimer mCountDownTimer;
	 private boolean mTimerRunning;
	 private long mStartTimeInMillis;
	 private long mTimeLeftInMillis;
	private long mEndTime;

	private long saved_ads_time;

	private boolean mTimerEnabled;

	@Override  
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {  
       // Toast.makeText(getApplicationContext(),country[position] , Toast.LENGTH_LONG).show();  
    }  
    @Override  
    public void onNothingSelected(AdapterView<?> arg0) {  
        // TODO Auto-generated method stub  
    }  
	
	private TextInputLayout chavesKey;

	private EditText chaKey;

	private TextInputLayout serverKey;
	
	private EditText sersKey;

	private TextInputLayout dnnsKey;

	private EditText dnsKeys;
	
	private TextInputLayout dnsuser;
	
	private TextInputLayout dnspass;
	
	private EditText dnssuser;
	
	private EditText dnsspass;
   private SharedPreferences sp;
    private LogsAdapter mLogAdapter;
    private RecyclerView logList;
    private ViewPager vp;
    private TabLayout tabs;
    private TextView custom;
    private ImageView icon1;

    private InterstitialAd mInterstitialAd;
	//public static CheckBox mNoLoadTimer;
    public static TextView timerTextView;
    public static EditText timerEditText;
    public static Button controllerButton;
	
	public static final String TODAY_DATA = "todaydata";
	private SharedPreferences myData;
	private CountDownTimer countDownTimer;
	private Context mContxt;
	private CheckBox dnsCheckBox;
	private TextView txt1;
	private TextView txt2;
	private boolean edit_state_pressed = false;
	private static final int S_ONSTART_CALLED = 2;
	private static final int S_BIND_CALLED = 1;
	private static final boolean RETAIN_AUTH = false;
	public static View htoRelativeLayout;
    public static TextView bytes_in_view;
	public static TextView bytes_out_view;
    @Override
    public void onCheckedChanged(CompoundButton p1, boolean p2) {
    }
    
    
	private static final String TAG = SocksHttpMainActivity.class.getSimpleName();
	private static final String UPDATE_VIEWS = "MainUpdate";
	public static final String OPEN_LOGS = "com.slipkprojects.sockshttp:openLogs";
	private static ArrayList modeList = new ArrayList();
	private DrawerPanelMain mDrawerPanel;
	private Settings mConfig;
	private Toolbar toolbar_main;
	private Handler mHandler;
	private LinearLayout mainLayout;
	private LinearLayout loginLayout;
	private LinearLayout proxyInputLayout;
	private TextView proxyText;
	private RadioGroup metodoConexaoRadio;
	private LinearLayout payloadLayout;
	private TextInputEditText payloadEdit;
	private SwitchCompat customPayloadSwitch;
	private Button starterButton;
	private ImageButton inputPwShowPass;
	private TextInputEditText inputPwUser;
	private TextInputEditText inputPwPass;
	private LinearLayout configMsgLayout;
	private TextView configMsgText;
	private AdView adsBannerView;
	private ConfigUtil config;
	private Spinner serverSpinner;
	private Spinner payloadSpinner;
    private static final String[] tabTitle = {"HOME","LOGS"};
	private SpinnerAdapter serverAdapter;
	private PromoAdapter payloadAdapter;
	private ArrayList<JSONObject> serverList;
	private ArrayList<JSONObject> payloadList;
	String[] country = { "AUTO"}; 
    String[] setupList = {"Direct Connection", "Custom Payload", "Custom SSL", "SSL + PAYLOAD", "SlowDNS", "Imported Config"};
    private EditText edPayload;
    private EditText edSsl;
	private EditText sslPayload;
	private EditText edSslpayload;
    private TextInputLayout payLay, sslLay;
    private Spinner setupSpinner;
	private String[] torrentList = new String[] {
        "com.njlabs.showjava",
        "com.gmail.heagoo.apkeditor.pro",
        "com.termux",
        "mph.trunksku.apps.dexencryptor",
        "com.tdo.showbox",
        "com.nitroxenon.terrarium",
        "com.pklbox.translatorspro",
        "com.xunlei.downloadprovider",
        "com.epic.app.iTorrent",
        "hu.bute.daai.amorg.drtorrent",
        "com.mobilityflow.torrent.prof",
        "com.brute.torrentolite",
        "com.nebula.swift",
        "tv.bitx.media",
        "com.DroiDownloader",
        "bitking.torrent.downloader",
        "org.transdroid.lite",
        "com.mobilityflow.tvp",
        "com.gabordemko.torrnado",
        "com.frostwire.android",
        "com.vuze.android.remote",
        "com.akingi.torrent",
        "com.utorrent.web",
        "com.paolod.torrentsearch2",
        "com.delphicoder.flud.paid",
        "com.teeonsoft.ztorrent",
        "megabyte.tdm",
        "com.bittorrent.client.pro",
        "com.mobilityflow.torrent",
        "com.utorrent.client",
        "com.utorrent.client.pro",
        "com.bittorrent.client",
        "torrent",
        "com.AndroidA.DroiDownloader",
        "com.indris.yifytorrents",
        "com.delphicoder.flud",
        "com.oidapps.bittorrent",
        "dwleee.torrentsearch",
        "com.vuze.torrent.downloader",
        "megabyte.dm",
        "com.fgrouptech.kickasstorrents",
        "com.jrummyapps.rootbrowser.classic",
        "com.bittorrent.client",
        "hu.tagsoft.ttorrent.lite",
		"co.we.torrent"};
	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		mContxt = (this);
		mHandler = new Handler();
		mConfig = new Settings(this);
		mDrawerPanel = new DrawerPanelMain(this);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		setupInterstitial();
		loadInterstitialAds();
		doLayout();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
		prefs = mConfig.getPrefsPrivate();
		myData = getSharedPreferences(TODAY_DATA, Context.MODE_PRIVATE);
		boolean showFirstTime = prefs.getBoolean("connect_first_time", true);
		int lastVersion = prefs.getInt("last_version", 0);
		// http_sign(this);
		if (showFirstTime)
        {
            SharedPreferences.Editor pEdit = prefs.edit();
            pEdit.putBoolean("connect_first_time", false);
            pEdit.apply();

			Settings.setDefaultConfig(this);

			//  show_intro();
			//showBoasVindas();
        }

		try {
			int idAtual = ConfigParser.getBuildId(this);

			if (lastVersion < idAtual) {
				SharedPreferences.Editor pEdit = prefs.edit();
				pEdit.putInt("last_version", idAtual);
				pEdit.apply();

				// se estiver atualizando
				if (!showFirstTime) {
					if (lastVersion <= 12) {
						Settings.setDefaultConfig(this);
						Settings.clearSettings(this);

					}
				}
			}
		} catch(IOException e) {}
		// set layou
		SkProtect.CharlieProtect();
		// recebe local dados
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_VIEWS);
		filter.addAction(OPEN_LOGS);
		LocalBroadcastManager.getInstance(this)
			.registerReceiver(mActivityReceiver, filter);
		//new GetPackageClass(this,torrentList).check();
		doUpdateLayout();
		MobileAds.initialize(this, "ca-app-pub-4327217352829821~8010087111");
		AutoupdateApp();
		
		if(!StoredData.isSetData) {
			StoredData.setZero();
		}liveData();
	}

	private void doLayout() {
		setContentView(R.layout.activity_main_drawer);
		toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar_main);
		final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        prefs = mConfig.getPrefsPrivate();
        SharedPreferences.Editor edit = prefs.edit();
		SharedPreferences sPrefs = mConfig.getPrefsPrivate();
	    // set ADS
		adsBannerView = (AdView) findViewById(R.id.adView1);

		if (TunnelUtils.isNetworkOnline(SocksHttpMainActivity.this)) {
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
		
		mTextViewCountDown = (TextView)findViewById(R.id.timerTextView);
		mButtonSet = (Button) findViewById(R.id.btnAddTime);
		mButtonSet.setOnClickListener(this);	

		
		bytes_in_view = (TextView) findViewById(R.id.bytes_in);
        bytes_out_view = (TextView) findViewById(R.id.bytes_out);
		geoCheckBox=(CheckBox) findViewById(R.id.geo_location);
		geoCheckBox.setChecked(false);
		dnsCheckBox=(CheckBox) findViewById(R.id.useDns);
		dnsCheckBox.setChecked(false);
		txt1 = (TextView) findViewById(R.id.toastxt);
		txt2 = (TextView)findViewById(R.id.btntoastxt);
		txt1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
		txt2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
		htoRelativeLayout = findViewById(R.id.htoRelativeLayout1);
		htoRelativeLayout.setVisibility(View.GONE);
		//logs = (TextView) findViewById(R.id.textLog);
        icon1 = (ImageView) findViewById(R.id.itemImage);
        custom = (TextView) findViewById(R.id.customP);
		drawer = (DrawerLayout)findViewById(R.id.drawer);
		navi = (NavigationView)findViewById(R.id.navigation);
		mainLayout = (LinearLayout) findViewById(R.id.activity_mainLinearLayout);
		loginLayout = (LinearLayout) findViewById(R.id.activity_mainInputPasswordLayout);
		starterButton = (Button) findViewById(R.id.activity_starterButtonMain);
		inputPwUser = (TextInputEditText) findViewById(R.id.activity_mainInputPasswordUserEdit);
		inputPwPass = (TextInputEditText) findViewById(R.id.activity_mainInputPasswordPassEdit);
		inputPwShowPass = (ImageButton) findViewById(R.id.activity_mainInputShowPassImageButton);

		((TextView) findViewById(R.id.activity_mainAutorText))
			.setOnClickListener(this);

		proxyInputLayout = (LinearLayout) findViewById(R.id.activity_mainInputProxyLayout);
		proxyText = (TextView) findViewById(R.id.activity_mainProxyText);
		
		spin = (Spinner) findViewById(R.id.ports);  
        spin.setOnItemSelectedListener(this); 
        //Creating the ArrayAdapter instance having the country list  
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);  
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        //Setting the ArrayAdapter data on the Spinner  
        spin.setAdapter(aa);
		doSaveData();
        payLay = (TextInputLayout) findViewById(R.id.payloadLayout);
        sslLay = (TextInputLayout) findViewById(R.id.sniLayout);
        edPayload = (EditText) findViewById(R.id.edCustomPayload);
        edPayload.setText(prefs.getString("SavedHTTP", ""));
        edSsl = (EditText) findViewById(R.id.edCustomSSL);
        edSsl.setText(prefs.getString("SavedSSL", ""));

        payLaySsl = (TextInputLayout) findViewById(R.id.sslpayloadLayout);
        sslPayloadLay = (TextInputLayout) findViewById(R.id.snipayloadLayout);
        sslPayload = (EditText) findViewById(R.id.sslCustomPayload);
        sslPayload.setText(prefs.getString("SavedHTTP + SSL", ""));
        edSslpayload = (EditText) findViewById(R.id.sniCustomSSL);
        edSslpayload.setText(prefs.getString("SavedSSL + HTTP", ""));
		
		edUserLayout = (TextInputLayout) findViewById(R.id.UserLayout);
        edPassLayout = (TextInputLayout) findViewById(R.id.PassLayout);
        webuser = (EditText) findViewById(R.id.webUser);
        webuser.setText(prefs.getString("SavedUSER", ""));
        webpass = (EditText) findViewById(R.id.webPass);
        webpass.setText(prefs.getString("SavedPASS", ""));
		
		 chavesKey = (TextInputLayout) findViewById(R.id.pubLayout);
		 chaKey = (EditText) findViewById(R.id.textPub);
		 chaKey.setText(prefs.getString("SavedCHAVE", ""));
		 
		 serverKey = (TextInputLayout) findViewById(R.id.svLayout);
		 sersKey = (EditText) findViewById(R.id.textServer);
		 sersKey.setText(prefs.getString("SavedSERKEY", ""));
		 dnnsKey = (TextInputLayout) findViewById(R.id.DnsLayout);
		 dnsKeys = (EditText) findViewById(R.id.textDns);
		 dnsKeys.setText(prefs.getString("SavedDNS", ""));

		 dnsuser = (TextInputLayout) findViewById(R.id.UserDns);
		 dnspass = (TextInputLayout) findViewById(R.id.PassDns);
		 dnssuser = (EditText) findViewById(R.id.textUser);
		 dnssuser.setText(prefs.getString("SavedUSER", ""));
		 dnsspass = (EditText) findViewById(R.id.textPass);
		 dnsspass.setText(prefs.getString("SavedPASS", ""));
		bugsLayout = (TextInputLayout) findViewById(R.id.bugLayout);
        bugremote = (EditText) findViewById(R.id.webBug);
        bugremote.setText(prefs.getString("SavedBUG", ""));
        setupSpinner = (Spinner) findViewById(R.id.setupSpinner);
        SetupAdapter setupAdapter = new SetupAdapter(this, R.id.setupSpinner, setupList);
        setupSpinner.setAdapter(setupAdapter);
        setupSpinner.setSelection(prefs.getInt("SavedPos",0));
        setupSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
                {
                    prefs.edit().putInt("SavedPos", p3).apply();
                    if (p3 == 0) {

						bugsLayout.setVisibility(View.GONE);
                        bugremote.setVisibility(View.GONE);
                        edPayload.setVisibility(View.GONE);
                        edSsl.setVisibility(View.GONE);
                        payLay.setVisibility(View.GONE);
                        sslLay.setVisibility(View.GONE);
                        messLay.setVisibility(View.GONE);
						sslPayload.setVisibility(View.GONE);
						edSslpayload.setVisibility(View.GONE);
						payLaySsl.setVisibility(View.GONE);
						sslPayloadLay.setVisibility(View.GONE);
						edUserLayout.setVisibility(View.GONE);
						edPassLayout.setVisibility(View.GONE);
						webuser.setVisibility(View.GONE);
						webpass.setVisibility(View.GONE);

						chavesKey.setVisibility(View.GONE);
						chaKey.setVisibility(View.GONE);
						serverKey.setVisibility(View.GONE);
						sersKey.setVisibility(View.GONE);
						dnnsKey.setVisibility(View.GONE);
						dnsKeys.setVisibility(View.GONE);
						dnsuser.setVisibility(View.GONE);
						dnssuser.setVisibility(View.GONE);
						dnspass.setVisibility(View.GONE);
						dnsspass.setVisibility(View.GONE);
                    } else if (p3 == 1) {

                        edPayload.setVisibility(View.VISIBLE);
                        edSsl.setVisibility(View.GONE);
						sslPayload.setVisibility(View.GONE);
						edSslpayload.setVisibility(View.GONE);
						payLaySsl.setVisibility(View.GONE);
						sslPayloadLay.setVisibility(View.GONE);
                        payLay.setVisibility(View.VISIBLE);
                        sslLay.setVisibility(View.GONE);
                        messLay.setVisibility(View.GONE);
						edUserLayout.setVisibility(View.GONE);
						edPassLayout.setVisibility(View.GONE);
						webuser.setVisibility(View.GONE);
						webpass.setVisibility(View.GONE);
						bugsLayout.setVisibility(View.GONE);
                        bugremote.setVisibility(View.GONE);

						chavesKey.setVisibility(View.GONE);
						chaKey.setVisibility(View.GONE);
						serverKey.setVisibility(View.GONE);
						sersKey.setVisibility(View.GONE);
						dnnsKey.setVisibility(View.GONE);
						dnsKeys.setVisibility(View.GONE);
						dnsuser.setVisibility(View.GONE);
						dnssuser.setVisibility(View.GONE);
						dnspass.setVisibility(View.GONE);
						dnsspass.setVisibility(View.GONE);
                    } else if (p3 == 2) {

                        edPayload.setVisibility(View.GONE);
                        edSsl.setVisibility(View.VISIBLE);
                        payLay.setVisibility(View.GONE);
                        sslLay.setVisibility(View.VISIBLE);
                        messLay.setVisibility(View.GONE);
						sslPayload.setVisibility(View.GONE);
						edSslpayload.setVisibility(View.GONE);
						payLaySsl.setVisibility(View.GONE);
						sslPayloadLay.setVisibility(View.GONE);
						edUserLayout.setVisibility(View.GONE);
						edPassLayout.setVisibility(View.GONE);
						webuser.setVisibility(View.GONE);
						webpass.setVisibility(View.GONE);
						bugsLayout.setVisibility(View.GONE);
                        bugremote.setVisibility(View.GONE);

						chavesKey.setVisibility(View.GONE);
						chaKey.setVisibility(View.GONE);
						serverKey.setVisibility(View.GONE);
						sersKey.setVisibility(View.GONE);
						dnnsKey.setVisibility(View.GONE);
						dnsKeys.setVisibility(View.GONE);
						dnsuser.setVisibility(View.GONE);
						dnssuser.setVisibility(View.GONE);
						dnspass.setVisibility(View.GONE);
						dnsspass.setVisibility(View.GONE);
					}else if(p3 == 3) {

						prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
						sslPayload.setVisibility(View.VISIBLE);
						edSslpayload.setVisibility(View.VISIBLE);
						payLaySsl.setVisibility(View.VISIBLE);
						sslPayloadLay.setVisibility(View.VISIBLE);
						edUserLayout.setVisibility(View.VISIBLE);
						edPassLayout.setVisibility(View.VISIBLE);
						webuser.setVisibility(View.VISIBLE);
						webpass.setVisibility(View.VISIBLE);
						bugsLayout.setVisibility(View.VISIBLE);
						bugremote.setVisibility(View.VISIBLE);
                        edPayload.setVisibility(View.GONE);
                        edSsl.setVisibility(View.GONE);
                        payLay.setVisibility(View.GONE);
                        sslLay.setVisibility(View.GONE);
                        messLay.setVisibility(View.GONE);

						chavesKey.setVisibility(View.GONE);
						chaKey.setVisibility(View.GONE);
						serverKey.setVisibility(View.GONE);
						sersKey.setVisibility(View.GONE);
						dnnsKey.setVisibility(View.GONE);
						dnsKeys.setVisibility(View.GONE);
						dnsuser.setVisibility(View.GONE);
						dnssuser.setVisibility(View.GONE);
						dnspass.setVisibility(View.GONE);
						dnsspass.setVisibility(View.GONE);
					}else if(p3 == 4) {

						chavesKey.setVisibility(View.VISIBLE);
						chaKey.setVisibility(View.VISIBLE);
						serverKey.setVisibility(View.VISIBLE);
						sersKey.setVisibility(View.VISIBLE);
						dnnsKey.setVisibility(View.VISIBLE);
						dnsKeys.setVisibility(View.VISIBLE);
						dnsuser.setVisibility(View.VISIBLE);
						dnssuser.setVisibility(View.VISIBLE);
						dnspass.setVisibility(View.VISIBLE);
						dnsspass.setVisibility(View.VISIBLE);

						edPayload.setVisibility(View.GONE);
                        edSsl.setVisibility(View.GONE);
                        payLay.setVisibility(View.GONE);
                        sslLay.setVisibility(View.VISIBLE);
                        messLay.setVisibility(View.GONE);
						sslPayload.setVisibility(View.GONE);
						edSslpayload.setVisibility(View.GONE);
						payLaySsl.setVisibility(View.GONE);
						sslPayloadLay.setVisibility(View.GONE);
						edUserLayout.setVisibility(View.GONE);
						edPassLayout.setVisibility(View.GONE);
						webuser.setVisibility(View.GONE);
						webpass.setVisibility(View.GONE);
						bugsLayout.setVisibility(View.GONE);
                        bugremote.setVisibility(View.GONE);

                    }else if (p3 == 5) {

						chavesKey.setVisibility(View.GONE);
						chaKey.setVisibility(View.GONE);
						serverKey.setVisibility(View.GONE);
						sersKey.setVisibility(View.GONE);
						dnnsKey.setVisibility(View.GONE);
						dnsKeys.setVisibility(View.GONE);
						dnsuser.setVisibility(View.GONE);
						dnssuser.setVisibility(View.GONE);
						dnspass.setVisibility(View.GONE);
						dnsspass.setVisibility(View.GONE);

						bugsLayout.setVisibility(View.GONE);
                        bugremote.setVisibility(View.GONE);
						edUserLayout.setVisibility(View.GONE);
						edPassLayout.setVisibility(View.GONE);
						webuser.setVisibility(View.GONE);
						webpass.setVisibility(View.GONE);
                        edPayload.setVisibility(View.GONE);
                        edSsl.setVisibility(View.GONE);
                        payLay.setVisibility(View.GONE);
                        sslLay.setVisibility(View.GONE);
						sslPayload.setVisibility(View.GONE);
						edSslpayload.setVisibility(View.GONE);
						payLaySsl.setVisibility(View.GONE);
						sslPayloadLay.setVisibility(View.GONE);
                        if (prefs.getBoolean("isMsg", false)) {
                            messLay.setVisibility(View.VISIBLE);
                            tvMess.setText(prefs.getString("Mess",""));
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> p1)
                {

                }
            });

        imgFavorite = (Switch) findViewById(R.id.ckSetup);
        imgFavorite.setChecked(prefs.getBoolean("SavedSetup", false));
        imgFavorite.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton p1, boolean p2)
                {
                    prefs.edit().putBoolean("SavedSetup", p2).apply();
                    if (p2) {
                        payloadSpinner.setVisibility(View.GONE);
                        setupSpinner.setVisibility(View.VISIBLE);
                        int p3 = prefs.getInt("SavedPos", 0);
                        if (p3 == 0) {
                            prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT).apply();
                            edPayload.setVisibility(View.GONE);
                            edSsl.setVisibility(View.GONE);
                            payLay.setVisibility(View.GONE);
                            sslLay.setVisibility(View.GONE);
                            messLay.setVisibility(View.GONE);
							sslPayload.setVisibility(View.GONE);
							edSslpayload.setVisibility(View.GONE);
							payLaySsl.setVisibility(View.GONE);
							sslPayloadLay.setVisibility(View.GONE);
							edUserLayout.setVisibility(View.GONE);
							edPassLayout.setVisibility(View.GONE);
							webuser.setVisibility(View.GONE);
							webpass.setVisibility(View.GONE);
							bugsLayout.setVisibility(View.GONE);
							bugremote.setVisibility(View.GONE);

						    chavesKey.setVisibility(View.GONE);
						    chaKey.setVisibility(View.GONE);
						    serverKey.setVisibility(View.GONE);
						    sersKey.setVisibility(View.GONE);
						    dnnsKey.setVisibility(View.GONE);
						    dnsKeys.setVisibility(View.GONE);
						    dnsuser.setVisibility(View.GONE);
						    dnssuser.setVisibility(View.GONE);
						    dnspass.setVisibility(View.GONE);
						    dnsspass.setVisibility(View.GONE);

                        } else if (p3 == 1) {
							bugsLayout.setVisibility(View.GONE);
							bugremote.setVisibility(View.GONE);
							edUserLayout.setVisibility(View.GONE);
						    edPassLayout.setVisibility(View.GONE);
						    webuser.setVisibility(View.GONE);
						    webpass.setVisibility(View.GONE);
                            edPayload.setVisibility(View.VISIBLE);
                            edSsl.setVisibility(View.GONE);
                            payLay.setVisibility(View.VISIBLE);
                            sslLay.setVisibility(View.GONE);
                            messLay.setVisibility(View.GONE);

							chavesKey.setVisibility(View.GONE);
							chaKey.setVisibility(View.GONE);
							serverKey.setVisibility(View.GONE);
							sersKey.setVisibility(View.GONE);
							dnnsKey.setVisibility(View.GONE);
							dnsKeys.setVisibility(View.GONE);
							dnsuser.setVisibility(View.GONE);
							dnssuser.setVisibility(View.GONE);
							dnspass.setVisibility(View.GONE);
							dnsspass.setVisibility(View.GONE);

                        } else if (p3 == 2) {
							edUserLayout.setVisibility(View.GONE);
							edPassLayout.setVisibility(View.GONE);
							webuser.setVisibility(View.GONE);
							webpass.setVisibility(View.GONE);
                            edPayload.setVisibility(View.GONE);
                            edSsl.setVisibility(View.VISIBLE);
                            payLay.setVisibility(View.GONE);
                            sslLay.setVisibility(View.VISIBLE);
                            messLay.setVisibility(View.GONE);
							sslPayload.setVisibility(View.GONE);
							edSslpayload.setVisibility(View.GONE);
							payLaySsl.setVisibility(View.GONE);
							sslPayloadLay.setVisibility(View.GONE);
							bugsLayout.setVisibility(View.GONE);
                            bugremote.setVisibility(View.GONE);

							chavesKey.setVisibility(View.GONE);
							chaKey.setVisibility(View.GONE);
							serverKey.setVisibility(View.GONE);
							sersKey.setVisibility(View.GONE);
							dnnsKey.setVisibility(View.GONE);
							dnsKeys.setVisibility(View.GONE);
							dnsuser.setVisibility(View.GONE);
							dnssuser.setVisibility(View.GONE);
							dnspass.setVisibility(View.GONE);
							dnsspass.setVisibility(View.GONE);


						}else if(p3 == 3){
						    prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
							sslPayload.setVisibility(View.VISIBLE);
							edSslpayload.setVisibility(View.VISIBLE);
							payLaySsl.setVisibility(View.VISIBLE);
							sslPayloadLay.setVisibility(View.VISIBLE);
							edUserLayout.setVisibility(View.VISIBLE);
							edPassLayout.setVisibility(View.VISIBLE);
							webuser.setVisibility(View.VISIBLE);
							webpass.setVisibility(View.VISIBLE);
							bugsLayout.setVisibility(View.VISIBLE);
							bugremote.setVisibility(View.VISIBLE);
							edPayload.setVisibility(View.GONE);
							edSsl.setVisibility(View.GONE);
							payLay.setVisibility(View.GONE);
							sslLay.setVisibility(View.GONE);
							messLay.setVisibility(View.GONE);

							chavesKey.setVisibility(View.GONE);
							chaKey.setVisibility(View.GONE);
							serverKey.setVisibility(View.GONE);
							sersKey.setVisibility(View.GONE);
							dnnsKey.setVisibility(View.GONE);
							dnsKeys.setVisibility(View.GONE);
							dnsuser.setVisibility(View.GONE);
							dnssuser.setVisibility(View.GONE);
							dnspass.setVisibility(View.GONE);
							dnsspass.setVisibility(View.GONE);


						}else if(p3 == 4) {


							chavesKey.setVisibility(View.VISIBLE);
							chaKey.setVisibility(View.VISIBLE);
							serverKey.setVisibility(View.VISIBLE);
							sersKey.setVisibility(View.VISIBLE);
							dnnsKey.setVisibility(View.VISIBLE);
							dnsKeys.setVisibility(View.VISIBLE);
							dnsuser.setVisibility(View.VISIBLE);
							dnssuser.setVisibility(View.VISIBLE);
							dnspass.setVisibility(View.VISIBLE);
							dnsspass.setVisibility(View.VISIBLE);

							edPayload.setVisibility(View.GONE);
							edSsl.setVisibility(View.GONE);
							payLay.setVisibility(View.GONE);
							sslLay.setVisibility(View.VISIBLE);
							messLay.setVisibility(View.GONE);
							sslPayload.setVisibility(View.GONE);
							edSslpayload.setVisibility(View.GONE);
							payLaySsl.setVisibility(View.GONE);
							sslPayloadLay.setVisibility(View.GONE);
							edUserLayout.setVisibility(View.GONE);
							edPassLayout.setVisibility(View.GONE);
							webuser.setVisibility(View.GONE);
							webpass.setVisibility(View.GONE);
							bugsLayout.setVisibility(View.GONE);
							bugremote.setVisibility(View.GONE);
                        }else if (p3 == 5) {


							chavesKey.setVisibility(View.GONE);
							chaKey.setVisibility(View.GONE);
							serverKey.setVisibility(View.GONE);
							sersKey.setVisibility(View.GONE);
							dnnsKey.setVisibility(View.GONE);
							dnsKeys.setVisibility(View.GONE);
							dnsuser.setVisibility(View.GONE);
							dnssuser.setVisibility(View.GONE);
							dnspass.setVisibility(View.GONE);
							dnsspass.setVisibility(View.GONE);

							bugsLayout.setVisibility(View.GONE);
							bugremote.setVisibility(View.GONE);
							edUserLayout.setVisibility(View.GONE);
							edPassLayout.setVisibility(View.GONE);
							webuser.setVisibility(View.GONE);
							webpass.setVisibility(View.GONE);
                            edPayload.setVisibility(View.GONE);
                            edSsl.setVisibility(View.GONE);
                            payLay.setVisibility(View.GONE);
                            sslLay.setVisibility(View.GONE);
							sslPayload.setVisibility(View.GONE);
							edSslpayload.setVisibility(View.GONE);
							payLaySsl.setVisibility(View.GONE);
							sslPayloadLay.setVisibility(View.GONE);
                            if (prefs.getBoolean("isMsg", false)) {
                                messLay.setVisibility(View.VISIBLE);
                                tvMess.setText(prefs.getString("Mess",""));
                            }
                        }
                    } else {


						chavesKey.setVisibility(View.GONE);
						chaKey.setVisibility(View.GONE);
						serverKey.setVisibility(View.GONE);
						sersKey.setVisibility(View.GONE);
						dnnsKey.setVisibility(View.GONE);
						dnsKeys.setVisibility(View.GONE);
						dnsuser.setVisibility(View.GONE);
						dnssuser.setVisibility(View.GONE);
						dnspass.setVisibility(View.GONE);
						dnsspass.setVisibility(View.GONE);

                        payloadSpinner.setVisibility(View.VISIBLE);
                        setupSpinner.setVisibility(View.GONE);
                        edPayload.setVisibility(View.GONE);
                        edSsl.setVisibility(View.GONE);
                        payLay.setVisibility(View.GONE);
                        sslLay.setVisibility(View.GONE);
                        messLay.setVisibility(View.GONE);
						sslPayload.setVisibility(View.GONE);
						edSslpayload.setVisibility(View.GONE);
						payLaySsl.setVisibility(View.GONE);
						sslPayloadLay.setVisibility(View.GONE);
						edUserLayout.setVisibility(View.GONE);
						edPassLayout.setVisibility(View.GONE);
						webuser.setVisibility(View.GONE);
						webpass.setVisibility(View.GONE);
						bugsLayout.setVisibility(View.GONE);
                        bugremote.setVisibility(View.GONE);
                    }
                }
            });

        sPrefs.edit().putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, false).apply();
		sPrefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY).apply();
        config = new ConfigUtil(this);
		serverSpinner = (Spinner) findViewById(R.id.serverSpinner);
		payloadSpinner = (Spinner) findViewById(R.id.payloadSpinner);
		// connectionStatus = (TextView) findViewById(R.id.connection_status);
		serverList = new ArrayList<>();
		payloadList = new ArrayList<>();
		navi.setNavigationItemSelectedListener(this);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar_main,R.string.app_name,R.string.app_name);
		toggle.syncState();
		drawer.setDrawerListener(toggle);
		serverAdapter = new SpinnerAdapter(this, R.id.serverSpinner, serverList);
		payloadAdapter = new PromoAdapter(this, R.id.payloadSpinner, payloadList);
        serverSpinner.setSelection(prefs.getInt("LastSelectedServer", 0));
        serverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {

                    SharedPreferences prefs = mConfig.getPrefsPrivate();
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putInt("LastSelectedServer", p3).apply();

                }

                @Override
                public void onNothingSelected(AdapterView<?> p1) {
                }
            });
        messLay = (LinearLayout) findViewById(R.id.messageLayout);
        tvMess = (TextView) findViewById(R.id.tvMessage);

		payloadSpinner.setSelection(prefs.getInt("LastSelectedPayload", -1));
        payloadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {

                    SharedPreferences prefs = mConfig.getPrefsPrivate();
                    SharedPreferences.Editor edit = prefs.edit();
					int pos = payloadSpinner.getSelectedItemPosition();
                    edit.putInt("LastSelectedPayload", pos).apply();
					// payloadSpinner.getSelectedItemPosition();
                }

                @Override
                public void onNothingSelected(AdapterView<?> p1) {
                }
            });

		serverSpinner.setAdapter(serverAdapter);
		payloadSpinner.setAdapter(payloadAdapter);
		//registerForContextMenu(this.payloadSpinner);
		payloadSpinner.setOnItemSelectedListener(this);

		loadServer();
		loadNetworks();
		updateConfig(true);

		metodoConexaoRadio = (RadioGroup) findViewById(R.id.activity_mainMetodoConexaoRadio);
		customPayloadSwitch = (SwitchCompat) findViewById(R.id.activity_mainCustomPayloadSwitch);

		starterButton.setOnClickListener(this);
		proxyInputLayout.setOnClickListener(this);
		dnsCheckBox.setOnClickListener(this);
		geoCheckBox.setOnClickListener(this);
		payloadLayout = (LinearLayout) findViewById(R.id.activity_mainInputPayloadLinearLayout);
		payloadEdit = (TextInputEditText) findViewById(R.id.activity_mainInputPayloadEditText);

		configMsgLayout = (LinearLayout) findViewById(R.id.activity_mainMensagemConfigLinearLayout);
		configMsgText = (TextView) findViewById(R.id.activity_mainMensagemConfigTextView);

		// fix bugs
		if (mConfig.getPrefsPrivate().getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			if (mConfig.getPrefsPrivate().getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				inputPwUser.setText(mConfig.getPrivString(Settings.USUARIO_KEY));
				inputPwPass.setText(mConfig.getPrivString(Settings.SENHA_KEY));
			}
		}
		else {
			payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
		}
        customPayloadSwitch.setChecked(true);
        edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, !true);
        setPayloadSwitch(prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT), true);
		metodoConexaoRadio.setOnCheckedChangeListener(this);
		inputPwShowPass.setOnClickListener(this);
        int p3 = setupSpinner.getSelectedItemPosition();
        if (prefs.getBoolean("SavedSetup", false)) {
            setupSpinner.setVisibility(View.VISIBLE);
            payloadSpinner.setVisibility(View.GONE);
            if (p3 == 0) {
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT).apply();
                edPayload.setVisibility(View.GONE);
                edSsl.setVisibility(View.GONE);
                payLay.setVisibility(View.GONE);
                sslLay.setVisibility(View.GONE);
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
                messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
				bugsLayout.setVisibility(View.GONE);
				bugremote.setVisibility(View.GONE);

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);


            } else if (p3 == 1) {
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
                edPayload.setVisibility(View.VISIBLE);
                edSsl.setVisibility(View.GONE);
                payLay.setVisibility(View.VISIBLE);
                sslLay.setVisibility(View.GONE);
                messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
				bugsLayout.setVisibility(View.GONE);
				bugremote.setVisibility(View.GONE);

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);

            } else if (p3 == 2) {
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
                edPayload.setVisibility(View.GONE);
                edSsl.setVisibility(View.VISIBLE);
                payLay.setVisibility(View.GONE);
                sslLay.setVisibility(View.VISIBLE);
                messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
				bugsLayout.setVisibility(View.GONE);
                bugremote.setVisibility(View.GONE);

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);

			}else if (p3 == 3) {
				prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
				edPayload.setVisibility(View.GONE);
                edSsl.setVisibility(View.GONE);
                payLay.setVisibility(View.GONE);
                sslLay.setVisibility(View.GONE);
                messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.VISIBLE);
				edSslpayload.setVisibility(View.VISIBLE);
				payLaySsl.setVisibility(View.VISIBLE);
				sslPayloadLay.setVisibility(View.VISIBLE);
				edUserLayout.setVisibility(View.VISIBLE);
				edPassLayout.setVisibility(View.VISIBLE);
				webuser.setVisibility(View.VISIBLE);
				webpass.setVisibility(View.VISIBLE);
				bugsLayout.setVisibility(View.VISIBLE);
				bugremote.setVisibility(View.VISIBLE);

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);

			}else if(p3 == 4) {

				chavesKey.setVisibility(View.VISIBLE);
				chaKey.setVisibility(View.VISIBLE);
				serverKey.setVisibility(View.VISIBLE);
				sersKey.setVisibility(View.VISIBLE);
				dnnsKey.setVisibility(View.VISIBLE);
				dnsKeys.setVisibility(View.VISIBLE);
				dnsuser.setVisibility(View.VISIBLE);
				dnssuser.setVisibility(View.VISIBLE);
				dnspass.setVisibility(View.VISIBLE);
				dnsspass.setVisibility(View.VISIBLE);

				edPayload.setVisibility(View.GONE);
				edSsl.setVisibility(View.GONE);
				payLay.setVisibility(View.GONE);
				sslLay.setVisibility(View.VISIBLE);
				messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
				bugsLayout.setVisibility(View.GONE);
				bugremote.setVisibility(View.GONE);
            }else if (p3 == 5) {

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);

				bugsLayout.setVisibility(View.GONE);
                bugremote.setVisibility(View.GONE);
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
                edPayload.setVisibility(View.GONE);
                edSsl.setVisibility(View.GONE);
                payLay.setVisibility(View.GONE);
                sslLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
                if (prefs.getBoolean("isMsg", false)) {
                    messLay.setVisibility(View.VISIBLE);
                    tvMess.setText(prefs.getString("Mess",""));
                }
            }
            //   configMessageLay.setVisibility(View.GONE);
        } else {

			chavesKey.setVisibility(View.GONE);
			chaKey.setVisibility(View.GONE);
			serverKey.setVisibility(View.GONE);
			sersKey.setVisibility(View.GONE);
			dnnsKey.setVisibility(View.GONE);
			dnsKeys.setVisibility(View.GONE);
			dnsuser.setVisibility(View.GONE);
			dnssuser.setVisibility(View.GONE);
			dnspass.setVisibility(View.GONE);
			dnsspass.setVisibility(View.GONE);

            setupSpinner.setVisibility(View.GONE);
            payloadSpinner.setVisibility(View.VISIBLE);
            edPayload.setVisibility(View.GONE);
            edSsl.setVisibility(View.GONE);
            payLay.setVisibility(View.GONE);
            sslLay.setVisibility(View.GONE);
            messLay.setVisibility(View.GONE);
			sslPayload.setVisibility(View.GONE);
			edSslpayload.setVisibility(View.GONE);
			payLaySsl.setVisibility(View.GONE);
			sslPayloadLay.setVisibility(View.GONE);
			edUserLayout.setVisibility(View.GONE);
			edPassLayout.setVisibility(View.GONE);
			webuser.setVisibility(View.GONE);
			webpass.setVisibility(View.GONE);
			bugsLayout.setVisibility(View.GONE);
            bugremote.setVisibility(View.GONE);
        }

        if (imgFavorite.isChecked()) {
            if (p3 == 0) {
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT).apply();
                edPayload.setVisibility(View.GONE);
                edSsl.setVisibility(View.GONE);
                payLay.setVisibility(View.GONE);
                sslLay.setVisibility(View.GONE);
                messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
				bugsLayout.setVisibility(View.GONE);
				bugremote.setVisibility(View.GONE);

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);

            } else if (p3 == 1) {
                edPayload.setVisibility(View.VISIBLE);
                edSsl.setVisibility(View.GONE);
                payLay.setVisibility(View.VISIBLE);
                sslLay.setVisibility(View.GONE);
                messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
				bugsLayout.setVisibility(View.GONE);
				bugremote.setVisibility(View.GONE);

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);
            } else if (p3 == 2) {
                edPayload.setVisibility(View.GONE);
                edSsl.setVisibility(View.VISIBLE);
                payLay.setVisibility(View.GONE);
                sslLay.setVisibility(View.VISIBLE);
                messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
				bugsLayout.setVisibility(View.GONE);
				bugremote.setVisibility(View.GONE);

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);
			} else if (p3 == 3) {
				prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
				edPayload.setVisibility(View.GONE);
                edSsl.setVisibility(View.GONE);
                payLay.setVisibility(View.GONE);
                sslLay.setVisibility(View.GONE);
                messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.VISIBLE);
				edSslpayload.setVisibility(View.VISIBLE);
				payLaySsl.setVisibility(View.VISIBLE);
				sslPayloadLay.setVisibility(View.VISIBLE);
				edUserLayout.setVisibility(View.VISIBLE);
				edPassLayout.setVisibility(View.VISIBLE);
				webuser.setVisibility(View.VISIBLE);
				webpass.setVisibility(View.VISIBLE);
				bugsLayout.setVisibility(View.VISIBLE);
				bugremote.setVisibility(View.VISIBLE);

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);
			}else if(p3 == 4) {

				chavesKey.setVisibility(View.VISIBLE);
				chaKey.setVisibility(View.VISIBLE);
				serverKey.setVisibility(View.VISIBLE);
				sersKey.setVisibility(View.VISIBLE);
				dnnsKey.setVisibility(View.VISIBLE);
				dnsKeys.setVisibility(View.VISIBLE);
				dnsuser.setVisibility(View.VISIBLE);
				dnssuser.setVisibility(View.VISIBLE);
				dnspass.setVisibility(View.VISIBLE);
				dnsspass.setVisibility(View.VISIBLE);

				edPayload.setVisibility(View.GONE);
				edSsl.setVisibility(View.GONE);
				payLay.setVisibility(View.GONE);
				sslLay.setVisibility(View.VISIBLE);
				messLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
				bugsLayout.setVisibility(View.GONE);
				bugremote.setVisibility(View.GONE);
            } else if (p3 == 5) {

				chavesKey.setVisibility(View.GONE);
				chaKey.setVisibility(View.GONE);
				serverKey.setVisibility(View.GONE);
				sersKey.setVisibility(View.GONE);
				dnnsKey.setVisibility(View.GONE);
				dnsKeys.setVisibility(View.GONE);
				dnsuser.setVisibility(View.GONE);
				dnssuser.setVisibility(View.GONE);
				dnspass.setVisibility(View.GONE);
				dnsspass.setVisibility(View.GONE);

				bugsLayout.setVisibility(View.GONE);
                bugremote.setVisibility(View.GONE);
				edUserLayout.setVisibility(View.GONE);
				edPassLayout.setVisibility(View.GONE);
				webuser.setVisibility(View.GONE);
				webpass.setVisibility(View.GONE);
                edPayload.setVisibility(View.GONE);
                edSsl.setVisibility(View.GONE);
                payLay.setVisibility(View.GONE);
                sslLay.setVisibility(View.GONE);
				sslPayload.setVisibility(View.GONE);
				edSslpayload.setVisibility(View.GONE);
				payLaySsl.setVisibility(View.GONE);
				sslPayloadLay.setVisibility(View.GONE);
                if (prefs.getBoolean("isMsg", false)) {
                    messLay.setVisibility(View.VISIBLE);
                    tvMess.setText(prefs.getString("Mess",""));
                }
            }
        }
        doTabs();
	}
	
	
	
	private void liveData(){
		new Timer().schedule(new TimerTask(){
				@Override
				public void run(){
					mHandler.post(new Runnable() {
							@Override
							public void run() {
								getData();
							}
						});
				}
			}, 0,1000);
    }
	
	public void getData() {
		boolean isRunning = SkStatus.isTunnelActive();
        long mUpload, mDownload, saved_Send ,saved_Down/*,up, down*/;
        String saved_date, tDate;
        List<Long> allData;
        allData = RetrieveData.findData();
        mDownload = allData.get(0);
        mUpload = allData.get(1);
        StoredData.storedData(mDownload, mUpload);
		//down = mDownload;
		//up = mUpload;
        Calendar ca = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        tDate = sdf.format(ca.getTime());
        saved_date = myData.getString("today_date", "empty");
		SharedPreferences.Editor editor = myData.edit();
		if (saved_date.equals(tDate)) {
			saved_Send = myData.getLong("UP_DATA", 0);
			saved_Down = myData.getLong("DOWN_DATA", 0);
            editor.putLong("UP_DATA", mUpload + saved_Send);
			editor.putLong("DOWN_DATA", mDownload + saved_Down);
			editor.apply();
        } else {
			editor.clear();
			editor.putString("today_date", tDate);
			editor.apply();
        }
		if(isRunning){
			bytes_out_view.setText(render_bandwidth(myData.getLong("UP_DATA", 0)));
			bytes_in_view.setText(render_bandwidth(myData.getLong("DOWN_DATA", 0)));
		}else{
			myData.edit().putLong("UP_DATA", 0).apply();
			myData.edit().putLong("DOWN_DATA", 0).apply();
		}
		StatisticGraphData.DataTransferStats stats = StatisticGraphData.getStatisticData().getDataTransferStats();
		String duration = stats.isConnected() ? stats.elapsedTimeToDisplay(stats.getElapsedTime()) : "00h:00m:00s";
		
    }
	
    public void doTabs() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mLogAdapter = new LogsAdapter(layoutManager,this);
        logList = (RecyclerView) findViewById(R.id.recyclerLog);
        logList.setAdapter(mLogAdapter);
        logList.setLayoutManager(layoutManager);
        mLogAdapter.scrollToLastPosition();
        vp = (ViewPager)findViewById(R.id.viewpager);
        tabs = (TabLayout)findViewById(R.id.tablayout);
        vp.setAdapter(new MyAdapter(Arrays.asList(tabTitle)));
        vp.setOffscreenPageLimit(2);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(vp);
        
        }
	@SuppressLint("StringFormatMatches")
    private String getAppInfoString(Context c) {
        c.getPackageManager();
        String version = "";
        try {
            @SuppressLint("PackageManagerGetSignatures")

				PackageInfo packageinfo = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
            version = String.format(packageinfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
        }
        return version;
		
	}
	
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
            case R.id.mPayloadGen:
                if (!imgFavorite.isChecked()) {
                    showToast("Please Enable Custom Setup");
                } else {
                    int pos = setupSpinner.getSelectedItemPosition();
                    if (pos == 1) {
                        showPayloadGenerator();
                    } else {
                        showToast("Please Select Custom Payload");
                    }
                
			}
                break;
			case R.id.radio:
				PackageInfo app_info = Utils.getAppInfo(this);
				if (app_info != null) {
					String aparelho_marca = Build.BRAND.toUpperCase();

					if (aparelho_marca.equals("SAMSUNG") || aparelho_marca.equals("HUAWEY")) {
						Toast.makeText(this, R.string.error_no_supported, Toast.LENGTH_SHORT)
							.show();
					}
					else {
						try {
							Intent in = new Intent(Intent.ACTION_MAIN);
							in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							in.setClassName("com.android.settings", "com.android.settings.RadioInfo");
							this.startActivity(in);
						} catch(Exception e) {
							Toast.makeText(this, R.string.error_no_supported, Toast.LENGTH_SHORT)
								.show();
						}
					}
				}
				break;
			case R.id.miAbout:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
				startActivity(aboutIntent);
				break;
			case R.id.feedback:
				startActivity(new Intent("android.intent.action.VIEW", 
										 Uri.parse("https://t.me/phctvpndev")));
				break;
			case R.id.fb:
				startActivity(new Intent("android.intent.action.VIEW", 
										 Uri.parse("https://t.me/phctofficial")));
				break;
			case R.id.settings:
				Intent Intent = new Intent(this, ConfigGeralActivity.class);
			    startActivity(Intent);
				break;
			
		}
		drawer.closeDrawer(Gravity.START);
		return true;
	}
	
	public void AutoupdateApp(){
		new UpdateAsync(SocksHttpMainActivity.this, new UpdateAsync.Listener() {
				@Override
				public void onCompleted(final String config)
				{
					SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(SocksHttpMainActivity.this);
					try
					{
						JSONObject obj = new JSONObject(config);
						if (obj.getString("newVersion").equals(getAppInfoString(SocksHttpMainActivity.this))){

						}else{
							try
							{
								mPref.edit().putString("version_Notes",obj.getString("versionNotes")).apply();
								AppInfo("New Version Avalable",obj.getString("versionNotes"),obj.getString("apkUrl"),obj.getString("newVersion"));
							}
							catch (org.json.JSONException e)
							{Toast.makeText(SocksHttpMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
									.show();}
						}
					}
					catch (Exception e)
					{
						Toast.makeText(SocksHttpMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
							.show();
					}
				}

				@Override
				public void onCancelled()
				{
					// TODO: Implement this method
				}

				@Override
				public void onException(String ex)
				{
				}
			}).execute();
	}
		
	private void AppInfo(String s1,final String s2,final String s3,final String s4){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.karl_appupdate, (ViewGroup) null);
        AlertDialog.Builder builer = new AlertDialog.Builder(this); 
        builer.setView(inflate); 
		TextView app = (TextView) inflate.findViewById(R.id.happTextView1);
		TextView txtAppLogs = (TextView) inflate.findViewById(R.id.happTextView2);
        TextView version = (TextView) inflate.findViewById(R.id.happTextView3);
		LinearLayout Layout = (LinearLayout) inflate.findViewById(R.id.happLinearLayout1);
		Button update = (Button) inflate.findViewById(R.id.hdevButton1);
		app.setText(s1);
		txtAppLogs.setText(s2);
		version.setText("Current version: "+getAppInfoString(this)+"\n"+"Latest version: "+s4);
		if(s1.equals("No updates available")){
			Layout.setVisibility(View.GONE);
			update.setVisibility(View.GONE);
		}else{
			Layout.setVisibility(View.VISIBLE);
			update.setVisibility(View.VISIBLE);
		}
        final AlertDialog alert = builer.create(); 
		alert.setCancelable(true);
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setGravity(Gravity.CENTER); 
        alert.show();
		update.setOnClickListener(new View.OnClickListener() { 
				@Override 
				public void onClick(View v) { 
					alert.dismiss();
					Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(s3));
					intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(Intent.createChooser(intent3, SocksHttpMainActivity.this.getText(R.string.open_with)));
				}
			}); 
    }
	
    public class MyAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            // TODO: Implement this method
            return 2;
        }

        @Override
        public boolean isViewFromObject(View p1, Object p2)
        {
            // TODO: Implement this method
            return p1 == p2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            int[] ids = new int[]{R.id.tab1, R.id.tab2};
            int id = 0;
            id = ids[position];
            // TODO: Implement this method
            return findViewById(id);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            // TODO: Implement this method
            return titles.get(position);
        }

        private List<String> titles;
        public MyAdapter(List<String> str)
        {
            titles = str;
        }
	}
        
	private void doUpdateLayout() {
		SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences prefs = mConfig.getPrefsPrivate();
		
		boolean isRunning = SkStatus.isTunnelActive();
		int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
		custom.setText(mPref.getString(Settings.DNSTYPE_KEY,Settings.DNS_GOOGLE_KEY));
		dnsCheckBox.setChecked(mPref.getBoolean(Settings.DNSFORWARD_KEY,false));
		dnsCheckBox.setEnabled(!isRunning);
		setStarterButton(starterButton, this);
		setPayloadSwitch(tunnelType, !prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true));
        isRunning(isRunning);
		String proxyStr = getText(R.string.no_value).toString();

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			proxyStr = "*******";
			proxyInputLayout.setEnabled(false);
		}
		else {
			String proxy = mConfig.getPrivString(Settings.PROXY_IP_KEY);

			if (proxy != null && !proxy.isEmpty())
				proxyStr = String.format("%s:%s", proxy, mConfig.getPrivString(Settings.PROXY_PORTA_KEY));
			proxyInputLayout.setEnabled(!isRunning);
		} 

		proxyText.setText(proxyStr);


		switch (tunnelType) {
			case Settings.bTUNNEL_TYPE_SSH_DIRECT:
				customPayloadSwitch.setChecked(true);
				break;

			case Settings.bTUNNEL_TYPE_SSH_PROXY:
				customPayloadSwitch.setChecked(true);
                break;
            case Settings.bTUNNEL_TYPE_SSH_SSL:
				customPayloadSwitch.setChecked(false);
                break;
				
			case Settings.bTUNNEL_TYPE_PAY_SSL:
				customPayloadSwitch.setChecked(true);
                break;
			case Settings.bTUNNEL_TYPE_SSL_RP:
				customPayloadSwitch.setChecked(true);
                break;
			case Settings.bTUNNEL_TYPE_SLOWDNS:
				customPayloadSwitch.setChecked(false);
                break;
		}


		int msgVisibility = View.GONE;
		int loginVisibility = View.GONE;
		String msgText = "";
		boolean enabled_radio = !isRunning;

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			
			if (prefs.getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				loginVisibility = View.VISIBLE;
				
				inputPwUser.setText(mConfig.getPrivString(Settings.USUARIO_KEY));
				inputPwPass.setText(mConfig.getPrivString(Settings.SENHA_KEY));
				
				inputPwUser.setEnabled(!isRunning);
				inputPwPass.setEnabled(!isRunning);
				inputPwShowPass.setEnabled(!isRunning);
			}
			
			String msg = mConfig.getPrivString(Settings.CONFIG_MENSAGEM_KEY);
			if (!msg.isEmpty()) {
				msgText = msg.replace("\n", "<br/>");
				msgVisibility = View.VISIBLE;
			}
			
			if (mConfig.getPrivString(Settings.PROXY_IP_KEY).isEmpty() ||
					mConfig.getPrivString(Settings.PROXY_PORTA_KEY).isEmpty()) {
				enabled_radio = false;
			}
		}

		loginLayout.setVisibility(loginVisibility);
		configMsgText.setText(msgText.isEmpty() ? "" : Html.fromHtml(msgText));
		configMsgLayout.setVisibility(msgVisibility);
        
		for (int i = 0; i < metodoConexaoRadio.getChildCount(); i++) {
			metodoConexaoRadio.getChildAt(i).setEnabled(enabled_radio);
		}
	}
	
    
	private synchronized void doSaveData() {
        try {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
            SharedPreferences.Editor edit = prefs.edit();

            if (imgFavorite.isChecked()) {
                int pos = setupSpinner.getSelectedItemPosition();
                int pos1 = serverSpinner.getSelectedItemPosition();
                switch (pos) {
                    case 0:
                        prefs.edit().putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, false).apply();
                        prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT).apply();
                        break;
                    case 1:
                        prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY).apply();
                        String payload = edPayload.getText().toString();
                        edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payload);
                        String ssh_port = config.getServersArray().getJSONObject(pos1).getString("ServerPort");
                        edit.putString(Settings.SERVIDOR_PORTA_KEY, ssh_port);
                        prefs.edit().putString("SavedHTTP", payload).apply();
                        break;
                    case 2:
                        prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_SSL).apply();
                        String sni = edSsl.getText().toString();
                        edit.putString(Settings.CUSTOM_PAYLOAD_KEY, sni);
                        String ssl_port = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
                        edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port);
                        prefs.edit().putString("SavedSSL", sni).apply();
                        break;
					case 3:
						prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
						String sslpayload = sslPayload.getText().toString();
						String snipayload = edSslpayload.getText().toString();
						String user = webuser.getText().toString();
						String pass = webpass.getText().toString();
						String bughost1 = bugremote.getText().toString();
						edit.putString(Settings.CUSTOM_SNI, snipayload);
						edit.putString(Settings.CUSTOM_PAYLOAD_KEY, sslpayload);
						edit.putString(Settings.USUARIO_KEY, user);
						edit.putString(Settings.SENHA_KEY, pass);
						edit.putString(Settings.SERVIDOR_KEY, bughost1);
						String ssl_port2 = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
						edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port2);
						prefs.edit().putString("SavedHTTP + SSL", sslpayload).apply();
						prefs.edit().putString("SavedSSL + HTTP", snipayload).apply();
						prefs.edit().putString("SavedUSER", user).apply();
						prefs.edit().putString("SavedPASS", pass).apply();
						prefs.edit().putString("SavedBUG", bughost1).apply();
					break;
					
					case 4:
						prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SLOWDNS).apply();
						String chaveKey = chaKey.getText().toString();
						String serverNameKey = sersKey.getText().toString();
						String dnsKey = dnsKeys.getText().toString();
						String userDns = dnssuser.getText().toString();
						String passDns = dnsspass.getText().toString();
						
						edit.putString(Settings.CHAVE_KEY, chaveKey);
						edit.putString(Settings.NAMESERVER_KEY, serverNameKey);
						edit.putString(Settings.DNS_KEY, dnsKey);
						edit.putString(Settings.USUARIO_KEY, userDns);
						edit.putString(Settings.SENHA_KEY, passDns);
						edit.putString(Settings.SERVIDOR_KEY, "127.0.0.1");
						edit.putString(Settings.SERVIDOR_PORTA_KEY, "2222");
						prefs.edit().putString("SavedCHAVE", chaveKey).apply();
						prefs.edit().putString("SavedSERKEY", serverNameKey).apply();
						prefs.edit().putString("SavedDNS", dnsKey).apply();
						prefs.edit().putString("SavedUSER", userDns).apply();
						prefs.edit().putString("SavedPASS", passDns).apply();
					break;

					case 5:
						DataBaseHelper db = new DataBaseHelper(this, "ImportedConfig");
						JSONObject obj = new JSONObject(db.getData());
						String sP = obj.getString("Payload");
						String sS = obj.getString("SNI");
						String pls = obj.getString("SNI");
						String slp = obj.getString("Payload");
						String us = obj.getString("ServerUser");
						String ps = obj.getString("ServerPass");
						String bh = obj.getString("ServerIP");

						String chavezKey = obj.getString("chaveKey");
						String serverzNameKey = obj.getString("serverNameKey");
						String dnszKey = obj.getString("dnsKey");
						String userzDns = obj.getString("ServerUser");
						String passzDns = obj.getString("ServerPass");
						int tun = obj.getInt("TunnelType");
						switch (tun)
						{

                            case Settings.bTUNNEL_TYPE_SSH_PROXY:
								prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY).apply();
								edit.putString(Settings.CUSTOM_PAYLOAD_KEY, sP);
								String ssh_ports = config.getServersArray().getJSONObject(pos1).getString("ServerPort");
								edit.putString(Settings.SERVIDOR_PORTA_KEY, ssh_ports);
								prefs.edit().putString("SavedHTTP", sP).apply();
                                break;

							case Settings.bTUNNEL_TYPE_SSH_SSL:
								prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_SSL).apply();
								edit.putString(Settings.CUSTOM_PAYLOAD_KEY, sS);
                                String ssl_ports = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
                                edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_ports);
                                prefs.edit().putString("SavedSSL", sS).apply();
								break;

							case Settings.bTUNNEL_TYPE_PAY_SSL:
								prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
								edit.putString(Settings.CUSTOM_SNI, pls);
								edit.putString(Settings.CUSTOM_PAYLOAD_KEY, slp);
								edit.putString(Settings.USUARIO_KEY, us);
								edit.putString(Settings.SENHA_KEY, ps);
								edit.putString(Settings.SERVIDOR_KEY, bh);
								String ssl_ports1 = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
								edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_ports1);
								prefs.edit().putString("SavedHTTP + SSL", pls).apply();
								prefs.edit().putString("SavedSSL + HTTP", slp).apply();
								prefs.edit().putString("SavedUSER", us).apply();
								prefs.edit().putString("SavedPASS", ps).apply();
								prefs.edit().putString("SavedBUG", bh).apply();
								break;
								
							case Settings.bTUNNEL_TYPE_SLOWDNS:
								prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SLOWDNS).apply();
						        edit.putString(Settings.CHAVE_KEY, chavezKey);
						        edit.putString(Settings.NAMESERVER_KEY, serverzNameKey);
						        edit.putString(Settings.DNS_KEY, dnszKey);
						        edit.putString(Settings.USUARIO_KEY, userzDns);
						        edit.putString(Settings.SENHA_KEY, passzDns);
						        edit.putString(Settings.SERVIDOR_KEY, "127.0.0.1");
						        edit.putString(Settings.SERVIDOR_PORTA_KEY, "2222");
						        prefs.edit().putString("SavedCHAVE", chavezKey).apply();
						        prefs.edit().putString("SavedSERKEY", serverzNameKey).apply();
						        prefs.edit().putString("SavedDNS", dnszKey).apply();
						        prefs.edit().putString("SavedUSER", userzDns).apply();
						        prefs.edit().putString("SavedPASS", passzDns).apply();
								break;
							}
                }
            } else {
                if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
                    if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
                        int pos = payloadSpinner.getSelectedItemPosition();
                        // int modeType = prefs.getInt("TunnelMode",modeGroup.getCheckedRadioButtonId());


                        boolean directModeType = config.getNetworksArray().getJSONObject(pos).getBoolean("isSSL");
                        boolean sshssltype =  config.getNetworksArray().getJSONObject(pos).getBoolean("wsPayload");
						boolean remotessltype = config.getNetworksArray().getJSONObject(pos).getBoolean("remoteHost");
					    boolean slowdnstype = config.getNetworksArray().getJSONObject(pos).getBoolean("SlowDns");
                          if (directModeType) {
                            prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_SSL).apply();
                            String sni = config.getNetworksArray().getJSONObject(pos).getString("SNI");
                            edit.putString(Settings.CUSTOM_PAYLOAD_KEY, sni);
                            
                    
                        } else if (sshssltype) {
                            prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
                            String payload = config.getNetworksArray().getJSONObject(pos).getString("Payload");
                            String snissl = config.getNetworksArray().getJSONObject(pos).getString("SNI");
                            edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payload);
                            edit.putString(Settings.CUSTOM_SNI, snissl);
				
						}else if (remotessltype){
							prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSL_RP).apply();
							String payloadrp = config.getNetworksArray().getJSONObject(pos).getString("Payload");
							String sslrp = config.getNetworksArray().getJSONObject(pos).getString("SNI");
							edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payloadrp);
							edit.putString(Settings.CUSTOM_SNI, sslrp);
							
						}else if (slowdnstype){
							prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SLOWDNS).apply();
							String chvKey = config.getNetworksArray().getJSONObject(pos).getString("chaveKey");
							String nvKey = config.getNetworksArray().getJSONObject(pos).getString("serverNameKey");
							String dnsKey = config.getNetworksArray().getJSONObject(pos).getString("dnsKey");
							
							edit.putString(Settings.CHAVE_KEY, chvKey);
							edit.putString(Settings.NAMESERVER_KEY, nvKey);
							edit.putString(Settings.DNS_KEY, dnsKey);
							
                        } else {
                            prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY).apply();
                            String payload = config.getNetworksArray().getJSONObject(pos).getString("Payload");
                            edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payload);
                        }
                    }
                }
            }
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private void loadServerData() {
		try {
			SharedPreferences prefs = mConfig.getPrefsPrivate();
			SharedPreferences.Editor edit = prefs.edit();
            int pos1 = serverSpinner.getSelectedItemPosition();
            int pos2 = payloadSpinner.getSelectedItemPosition();
            boolean directModeType = config.getNetworksArray().getJSONObject(pos2).getBoolean("isSSL");
            boolean sshssltype = config.getNetworksArray().getJSONObject(pos2).getBoolean("wsPayload");
			boolean remotessltype = config.getNetworksArray().getJSONObject(pos2).getBoolean("remoteHost");
			boolean slowdnstype = config.getNetworksArray().getJSONObject(pos2).getBoolean("SlowDns");
            if (directModeType) {
                String ssl_port = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
                edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port);
               
           } else if (sshssltype) {
                 String ssl_port1 = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
                 edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port1);
              
           } else if (remotessltype) {
                 String ssl_port2 = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
                 edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port2);
                
		   } else if (slowdnstype) {
			   edit.putString(Settings.SERVIDOR_KEY, "127.0.0.1");
			   edit.putString(Settings.SERVIDOR_PORTA_KEY, "2222");
            } else {
                String ssh_port = config.getServersArray().getJSONObject(pos1).getString("ServerPort");
                edit.putString(Settings.SERVIDOR_PORTA_KEY, ssh_port);
            }
                    
			String ssh_server = config.getServersArray().getJSONObject(pos1).getString("ServerIP");
			String remote_proxy = config.getServersArray().getJSONObject(pos1).getString("ProxyIP");
			String proxy_port = config.getServersArray().getJSONObject(pos1).getString("ProxyPort");
            String ssh_user = config.getServersArray().getJSONObject(pos1).getString("ServerUser");
            String ssh_pass = config.getServersArray().getJSONObject(pos1).getString("ServerPass");

            edit.putString(Settings.USUARIO_KEY, ssh_user);
			edit.putString(Settings.SENHA_KEY, ssh_pass);
			edit.putString(Settings.SERVIDOR_KEY, ssh_server);
			edit.putString(Settings.PROXY_IP_KEY, remote_proxy);
			edit.putString(Settings.PROXY_PORTA_KEY, proxy_port);

			edit.apply();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void customToast(String t1, String t2) {
		final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
		if(dnsCheckBox.isChecked()){
			mPref.edit().putBoolean(Settings.DNSFORWARD_KEY,true).apply();
			mPref.edit().putString(Settings.DNSTYPE_KEY, Settings.DNS_GOOGLE_KEY).apply();
			custom.setText(mPref.getString(Settings.DNSTYPE_KEY,Settings.DNS_DEFAULT_KEY));
			Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_fade_in);
			htoRelativeLayout.setVisibility(View.VISIBLE);
			txt1.setText(t1);
			txt2.setText(t2);
            if(dnsCheckBox.getText().toString().equals("DNS (Google DNS)")){
                mPref.edit().putBoolean("Default_dns",false).apply();
                mPref.edit().putBoolean("Google_dns",true).apply();
                mPref.edit().putBoolean("Primary_dns",false).apply();
                mPref.edit().putString(Settings.DNSRESOLVER_KEY1,"8.8.8.8").apply();
                mPref.edit().putString(Settings.DNSRESOLVER_KEY2,"8.8.4.4").apply();
            }
			txt2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(dnsCheckBox.getText().toString().equals("DNS (Default DNS)")){
							mPref.edit().putBoolean("Default_dns",true).apply();
							mPref.edit().putBoolean("Google_dns",false).apply();
							mPref.edit().putBoolean("Primary_dns",false).apply();
						}else if(dnsCheckBox.getText().toString().equals("DNS (Google DNS)")){
							mPref.edit().putBoolean("Default_dns",false).apply();
							mPref.edit().putBoolean("Google_dns",true).apply();
							mPref.edit().putBoolean("Primary_dns",false).apply();
                            mPref.edit().putString(Settings.DNSRESOLVER_KEY1,"8.8.8.8").apply();
                            mPref.edit().putString(Settings.DNSRESOLVER_KEY2,"8.8.4.4").apply();
						}else if(dnsCheckBox.getText().toString().equals("DNS (Primary DNS)")){
							mPref.edit().putBoolean("Default_dns",false).apply();
							mPref.edit().putBoolean("Google_dns",false).apply();
							mPref.edit().putBoolean("Primary_dns",true).apply();
						}
						Intent TunnDNS = new Intent(SocksHttpMainActivity.this, CustomDNS.class);
						startActivity(TunnDNS);
						htoRelativeLayout.setVisibility(View.VISIBLE);
					}
				});
			new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						htoRelativeLayout.setVisibility(View.GONE);
					}
				}, 2000);
			htoRelativeLayout.startAnimation(anim);
		}else{
			mPref.edit().putBoolean(Settings.DNSFORWARD_KEY,false).apply();
			mPref.edit().putString(Settings.DNSTYPE_KEY, Settings.DNS_DEFAULT_KEY).apply();
			custom.setText(mPref.getString(Settings.DNSTYPE_KEY,Settings.DNS_DEFAULT_KEY));
		}
    }

	private void loadServer() {
		try {
			if (serverList.size() > 0) {
				serverList.clear();
				serverAdapter.notifyDataSetChanged();
			}
			for (int i = 0; i < config.getServersArray().length(); i++) {
				JSONObject obj = config.getServersArray().getJSONObject(i);
				serverList.add(obj);
				serverAdapter.notifyDataSetChanged();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadNetworks1() {
		try {
			if (payloadList.size() > 0) {
				payloadList.clear();
				payloadAdapter.clear();
			}
			JSONObject obj = getJSONConfig2(this);
			JSONArray networkPayload = obj.getJSONArray("Networks");
			for (int i = 0; i < networkPayload.length(); i++) {
				payloadList.add(networkPayload.getJSONObject(i));
			}
			//Collections.sort(listNetwork, NetworkNameComparator());
			payloadAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			Toast.makeText(SocksHttpMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void loadNetworks() {
		try {
			if (payloadList.size() > 0) {
				payloadList.clear();
				payloadAdapter.notifyDataSetChanged();
			}
			for (int i = 0; i < config.getNetworksArray().length(); i++) {
				JSONObject obj = config.getNetworksArray().getJSONObject(i);
				payloadList.add(obj);
				payloadAdapter.notifyDataSetChanged();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateConfig(final boolean isOnCreate) {
		new ConfigUpdate(this, new ConfigUpdate.OnUpdateListener() {
			@Override
			public void onUpdateListener(String result) {
				try {
					if (!result.contains("Error on getting data")) {
						String json_data = AESCrypt.decrypt(config.PASSWORD, result);
						if (isNewVersion(json_data)) {
							newUpdateDialog(result);
						} else {
							if (!isOnCreate) {
								noUpdateDialog();
							}
						}
					} else if(result.contains("Error on getting data") && !isOnCreate){
						errorUpdateDialog(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start(isOnCreate);
	}

    
    
	private boolean isNewVersion(String result) {
		try {
			String current = config.getVersion();
			String update = new JSONObject(result).getString("Version");
			return config.versionCompare(update, current);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

    private void newUpdateDialog(final String result) throws JSONException, GeneralSecurityException{
        String json_data = AESCrypt.decrypt(config.PASSWORD, result);
        String notes = new JSONObject(json_data).getString("ReleaseNotes");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.update_dialog, (ViewGroup) null);
        AlertDialog.Builder builer = new AlertDialog.Builder(this); 
        builer.setView(inflate);
        ImageView iv = inflate.findViewById(R.id.update_image);
        TextView title = inflate.findViewById(R.id.update_text1);
        TextView ms = inflate.findViewById(R.id.update_text2);
        Button ok = inflate.findViewById(R.id.update_button);
        iv.setImageResource(R.drawable.notif);
        title.setText("New Update Available!");
        ms.setText(notes);
        ok.setText("Update Now");
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setGravity(Gravity.CENTER); 
        alert.show();
        ok.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) { 

                    // TODO: Implement this method
                    try
                    {
						alert.dismiss();
                        File file = new File(getFilesDir(), "Config.json");
                        OutputStream out = new FileOutputStream(file);
                        out.write(result.getBytes());
                        out.flush();
                        out.close();
                        restart_app();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }});

        alert.show();
    }

    private void noUpdateDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.no_update, (ViewGroup) null);
        AlertDialog.Builder builer = new AlertDialog.Builder(this); 
        builer.setView(inflate); 
        ImageView iv = inflate.findViewById(R.id.hadsImageView);
        TextView title = inflate.findViewById(R.id.hadsTextView1);
        TextView ms = inflate.findViewById(R.id.hadsTextView2);
        Button ok = inflate.findViewById(R.id.hadsButton);
        iv.setImageResource(R.drawable.notif);
        title.setText("No Update Available");
        ms.setText("Please try again soon.");
        ok.setText("Hide");
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setGravity(Gravity.CENTER); 
        alert.show();
        ok.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) { 
                    try
                    {
						alert.dismiss();

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }});

        alert.show();
    }

    private void errorUpdateDialog(String error) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.update_error, (ViewGroup) null);
        AlertDialog.Builder builer = new AlertDialog.Builder(this); 
        builer.setView(inflate); 
        ImageView iv = inflate.findViewById(R.id.images_update_error1);
        TextView title = inflate.findViewById(R.id.text_view_update_error1);
        TextView ms = inflate.findViewById(R.id.text_view_update_error2);
        Button ok = inflate.findViewById(R.id.update_error);
        iv.setImageResource(R.drawable.icon);
        title.setText("Error on Update!");
        ms.setText("There is an error occurred while checking for update. Please contact Developer.");
        ok.setText("okay");
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setGravity(Gravity.CENTER); 
        alert.show();
        ok.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) { 
                    try
                    {
						alert.dismiss();

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }});

        alert.show();
    }

	private void restart_app() {
		Intent intent = new Intent(this, SocksHttpMainActivity.class);
		int i = 123456;
		PendingIntent pendingIntent = PendingIntent.getActivity(this, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + ((long) 1000), pendingIntent);
		finish();
	}
	

	public void offlineUpdate() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		startActivityForResult(intent, PICK_FILE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PICK_FILE)
		{
			if (resultCode == RESULT_OK) {
				try {
					Uri uri = data.getData();
					String intentData = importer(uri);
					//String cipter = AESCrypt.decrypt(ConfigUtil.PASSWORD, intentData);
					File file = new File(getFilesDir(), "Config.json");
					OutputStream out = new FileOutputStream(file);
					out.write(intentData.getBytes());
					out.flush();
					out.close();
					loadServer();
					loadNetworks();
					restart_app();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String importer(Uri uri)
	{
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try
		{
			reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));

			String line = "";
			while ((line = reader.readLine()) != null)
			{
				builder.append(line);
			}
			reader.close();
		}
		catch (IOException e) {e.printStackTrace();}
		return builder.toString();
	} 
	/**
	 * Tunnel SSH
	 */

	void isRunning(boolean z) {
		if (z) {
			inputPwUser.setEnabled(false);
			spin.setEnabled(false);
			inputPwPass.setEnabled(false);
			payloadSpinner.setEnabled(false);
			serverSpinner.setEnabled(false);
			setupSpinner.setEnabled(false);
			imgFavorite.setEnabled(false);
			edPayload.setEnabled(false);
			edSsl.setEnabled(false);
			sslPayload.setEnabled(false);
			edSslpayload.setEnabled(false);
			webuser.setEnabled(false);
			webpass.setEnabled(false);
			bugremote.setEnabled(false);

			chaKey.setEnabled(false);
			sersKey.setEnabled(false);
			dnsKeys.setEnabled(false);
			dnssuser.setEnabled(false);
			dnsspass.setEnabled(false);
			
            
		} else {
			prefs.edit().putBoolean("isConnected", true).apply();
			payloadSpinner.setEnabled(true);
			serverSpinner.setEnabled(true);
			setupSpinner.setEnabled(true);
			imgFavorite.setEnabled(true);
			edPayload.setEnabled(true);
			edSsl.setEnabled(true);
			inputPwUser.setEnabled(true);
			spin.setEnabled(true);
			inputPwPass.setEnabled(true);
			sslPayload.setEnabled(true);
			edSslpayload.setEnabled(true);
			webuser.setEnabled(true);
			webpass.setEnabled(true);
			bugremote.setEnabled(true);

			chaKey.setEnabled(true);
			sersKey.setEnabled(true);
			dnsKeys.setEnabled(true);
			dnssuser.setEnabled(true);
			dnsspass.setEnabled(true);
			

		}
	}
	public void startOrStopTunnel(Activity activity) {
		if (SkStatus.isTunnelActive()) {
			TunnelManagerHelper.stopSocksHttp(activity);           
            
		}else {
			 
            
            vp.setCurrentItem(1);
			Settings config = new Settings(activity);
			
			if (config.getPrefsPrivate()
					.getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				if (inputPwUser.getText().toString().isEmpty() || 
						inputPwPass.getText().toString().isEmpty()) {
					Toast.makeText(this, R.string.error_userpass_empty, Toast.LENGTH_SHORT)
						.show();
					return;
				}
			}
			
			Intent intent = new Intent(activity, LaunchVpn.class);
			intent.setAction(Intent.ACTION_MAIN);
			
			if (config.getHideLog()) {
				intent.putExtra(LaunchVpn.EXTRA_HIDELOG, true);
			}
			
			activity.startActivity(intent);
		}
	}

	private void setPayloadSwitch(int tunnelType, boolean isCustomPayload) {
		SharedPreferences prefs = mConfig.getPrefsPrivate();

		boolean isRunning = SkStatus.isTunnelActive();

		customPayloadSwitch.setChecked(isCustomPayload);

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			payloadEdit.setEnabled(false);
			
			if (mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY).isEmpty()) {
				customPayloadSwitch.setEnabled(false);
			}
			else {
				customPayloadSwitch.setEnabled(!isRunning);
			}
			
			if (!isCustomPayload && tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY)
				payloadEdit.setText(Settings.PAYLOAD_DEFAULT);
			else
				payloadEdit.setText("*******");
		}
		else {
			customPayloadSwitch.setEnabled(!isRunning);

			if (isCustomPayload) {
				payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
				payloadEdit.setEnabled(!isRunning);
			}
			else if (tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY) {
				payloadEdit.setText(Settings.PAYLOAD_DEFAULT);
				payloadEdit.setEnabled(true);
			}
		}

		if (isCustomPayload || tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY) {
			payloadLayout.setVisibility(View.VISIBLE);
		}
		else {
			payloadLayout.setVisibility(View.GONE);
		}
	}

	public void setStarterButton(Button starterButton, Activity activity) {
		String state = SkStatus.getLastState();
		boolean isRunning = SkStatus.isTunnelActive();

		if (starterButton != null) {
			int resId;
			
			SharedPreferences prefsPrivate = new Settings(activity).getPrefsPrivate();

			if (ConfigParser.isValidadeExpirou(prefsPrivate
					.getLong(Settings.CONFIG_VALIDADE_KEY, 0))) {
				resId = R.string.expired;
				starterButton.setEnabled(false);

				if (isRunning) {
					startOrStopTunnel(activity);
				}
			}
			else if (prefsPrivate.getBoolean(Settings.BLOQUEAR_ROOT_KEY, false) &&
					ConfigParser.isDeviceRooted(activity)) {
			   resId = R.string.blocked;
			   starterButton.setEnabled(false);
			   
			   Toast.makeText(activity, R.string.error_root_detected, Toast.LENGTH_SHORT)
					.show();

			   if (isRunning) {
				   startOrStopTunnel(activity);
			   }
			}
			else if (SkStatus.SSH_INICIANDO.equals(state)) {
				resId = R.string.stop;
				starterButton.setEnabled(false);
			}
			else if (SkStatus.SSH_PARANDO.equals(state)) {
				resId = R.string.state_stopping;
				starterButton.setEnabled(false);
			}
			else {
				resId = isRunning ? R.string.stop : R.string.start;
				starterButton.setEnabled(true);
			}

			starterButton.setText(resId);
		}
	}
	

	
	@Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        if (mDrawerPanel.getToogle() != null)
			mDrawerPanel.getToogle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerPanel.getToogle() != null)
			mDrawerPanel.getToogle().onConfigurationChanged(newConfig);
    }
	
	private boolean isMostrarSenha = false;
	
	@Override
	public void onClick(View p1)
	{
		SharedPreferences prefs = mConfig.getPrefsPrivate();

		switch (p1.getId()) {
			case R.id.activity_starterButtonMain:
				doSaveData();
				loadServerData();
				startOrStopTunnel(this);
                isRunning(true);
				break;

			case R.id.activity_mainInputProxyLayout:
				if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
					doSaveData();

					DialogFragment fragProxy = new ProxyRemoteDialogFragment();
					fragProxy.show(getSupportFragmentManager(), "proxyDialog");
				}
				break;

			case R.id.activity_mainAutorText:
				String url = "http://t.me/SlipkProjects";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, getText(R.string.open_with)));
				break;
				
			case R.id.useDns:
                customToast("Improve privacy and bypass internet censorship","SET DNS");
				break;
			case R.id.geo_location:
				location();
				break;

			case R.id.btnAddTime:
                startActivity(new Intent(SocksHttpMainActivity.this,CoinsActivity.class));
				break;
                
			case R.id.activity_mainInputShowPassImageButton:
				isMostrarSenha = !isMostrarSenha;
				if (isMostrarSenha) {
					inputPwPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					inputPwShowPass.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_visibility_black_24dp));
				}
				else {
					inputPwPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					inputPwShowPass.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_visibility_off_black_24dp));
				}
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup p1, int p2)
	{
		SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();

		switch (p1.getCheckedRadioButtonId()) {
			case R.id.activity_mainSSHDirectRadioButton:
				edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
				proxyInputLayout.setVisibility(View.GONE);
				break;

			case R.id.activity_mainSSHProxyRadioButton:
				edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY);
				proxyInputLayout.setVisibility(View.VISIBLE);
				break;
		}

		edit.apply();
		doSaveData();
		doUpdateLayout();
	}
	
	private String render_bandwidth(double bw) {
        String postfix;
        float div;
        Object[] objArr;
        float bwf = (float) bw;
        if (bwf >= 1.0E12f) {
            postfix = "TB";
            div = 1.0995116E12f;
        } else if (bwf >= 1.0E9f) {
            postfix = "GB";
            div = 1.0737418E9f;
        } else if (bwf >= 1000000.0f) {
            postfix = "MB";
            div = 1048576.0f;
        } else if (bwf >= 1000.0f) {
            postfix = "KB";
            div = 1024.0f;
        } else {
            objArr = new Object[S_BIND_CALLED];
            objArr[0] = Float.valueOf(bwf);
            return String.format("%.0f", objArr);
        }
        objArr = new Object[S_ONSTART_CALLED];
        objArr[0] = Float.valueOf(bwf / div);
        objArr[S_BIND_CALLED] = postfix;
        return String.format("%.2f %s", objArr);
    }
  
	@Override
    public void updateState(final String state, String msg, int localizedResId, final ConnectionStatus level, Intent intent)
    {
        mHandler.post(new Runnable() {
                @Override
                public void run() {
                    doUpdateLayout();
                    if (SkStatus.isTunnelActive()){

                        if (level.equals(ConnectionStatus.LEVEL_CONNECTED)){
                             Toast.makeText(SocksHttpMainActivity.this, R.string.connected, Toast.LENGTH_SHORT).show();
                             isRunning(true);
                             start();
							showInterstitial();
							loadInterstitialAds();
                        }

                        if (level.equals(ConnectionStatus.LEVEL_NOTCONNECTED)){
                            isRunning(false); 
							
						
                           // connectionStatus.setText(R.string.servicestop);
                        }   

                        if (level.equals(ConnectionStatus.LEVEL_CONNECTING_SERVER_REPLIED)){
                            //connectionStatus.setText(R.string.authenticating);
                        }       

                        if (level.equals(ConnectionStatus.LEVEL_CONNECTING_NO_SERVER_REPLY_YET)){
                           // connectionStatus.setText(R.string.connecting);
                        }           
                        if (level.equals(ConnectionStatus.LEVEL_AUTH_FAILED)){
                           // connectionStatus.setText(R.string.authfailed);
                        }                   
                        if (level.equals(ConnectionStatus.UNKNOWN_LEVEL)){
                           // connectionStatus.setText(R.string.disconnected);
							adsPopUp();
							stop();
                        }               
                        //if (level.equals(ConnectionStatus.LEVEL_RECONNECTING)){
                        //      status.setText(R.string.reconnecting);
                    }               
                    if (level.equals(ConnectionStatus.LEVEL_NONETWORK)){
                       // connectionStatus.setText(R.string.nonetwork);
                    }           
                }
            });
		
		switch (state) {
			case SkStatus.SSH_CONECTADO:
				// carrega ads banner
				if (adsBannerView != null && TunnelUtils.isNetworkOnline(SocksHttpMainActivity.this)) {
					adsBannerView.setAdListener(new AdListener() {
						@Override
						public void onAdLoaded() {
							if (adsBannerView != null && !isFinishing()) {
								adsBannerView.setVisibility(View.VISIBLE);
							}
						}
					});
				
				}
			
			break;
		}
	}
	

	/**
	 * Recebe locais Broadcast
	 */

	private BroadcastReceiver mActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null)
                return;

            if (action.equals(UPDATE_VIEWS) && !isFinishing()) {
				doUpdateLayout();
			}
			
        }
    };


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerPanel.getToogle() != null && mDrawerPanel.getToogle().onOptionsItemSelected(item)) {
            return true;
        }

		// Menu Itens
		switch (item.getItemId()) {
			case R.id.miUpdate:
				updateConfig(false);
				break;
            case R.id.mImport:
                if (!TunnelUtils.isActiveVpn(this)) {
                    startActivity(new Intent(this, ImportActivity.class));
                } else {
                    showToast("Please Stop Service First");
                }
                break;
            case R.id.mExport:
                if (!TunnelUtils.isActiveVpn(this)) {
                    if (imgFavorite.isChecked()) {
                        int i = setupSpinner.getSelectedItemPosition();
                        if (i == 0) {
                            showToast("Cannot Export this Profile");
						 } else if (i == 5) {
                            showToast("Cannot Export this Profile");
                        } else {
                            startActivity(new Intent(this, ExportActivity.class));
                        }
                    } else {
                        showToast("Switch to Custom to make Export");
                    }
                } else {
                    showToast("Please Stop Service First");
                }
                break;
			
			case R.id.offline:
				offlineUpdate();
				break;
			case R.id.miOption:
				Intent Intent = new Intent(this, ConfigGeralActivity.class);
			    startActivity(Intent);
				break;

		}

		return super.onOptionsItemSelected(item);
	}
    
    void showToast(String str) {
        Toast.makeText(this, str, 0).show();
      }
    
    private void showPayloadGenerator() {
        PayloadGeneratorDialog paygen = new PayloadGeneratorDialog(this);
        /** set positive button**/
        paygen.setGenerateListener("Generate", new PayloadGeneratorDialog.OnGenerateListener() {
                @Override
                public void onGenerate(String payloadGenerated) {
                    edPayload.setText(payloadGenerated);
                }
            });
        /** set negative button**/
        paygen.setCancelListener("Cancel", new PayloadGeneratorDialog.OnCancelListener() {
                @Override
                public void onCancelListener() {

                }
            });
        /** set neutral button**/
        paygen.show();
    }


	@Override
	public void onBackPressed() {
		
	}

	@Override
    public void onResume() {
        
        super.onResume();
		
        imgFavorite.setChecked(prefs.getBoolean("SavedSetup", false));
        setupSpinner.setSelection(prefs.getInt("SavedPos",0));
        if (imgFavorite.isChecked()) {
            int i = setupSpinner.getSelectedItemPosition();
            if (i == 5) {
                if (prefs.getBoolean("isMsg", false)) {
                    messLay.setVisibility(View.VISIBLE);
                    tvMess.setText(prefs.getString("Mess", ""));
                }
            }
        }
        if (!mTimerEnabled) {

            resumeTime(); 
        }
        addTime();
        SkStatus.addStateListener(this);
        new Timer().schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                checkNetwork();
                            }
                        });
                }
            }, 0,1000);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        doSaveData();
        SkStatus.removeStateListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mActivityReceiver);
    }

    private void start() {

        if (saved_ads_time == 0) {

            Toast.makeText(SocksHttpMainActivity.this, "Your time is expiring soon, please click ADD TIME to renew access!", Toast.LENGTH_LONG).show();

            long millisInput = 1000 * 500;

            setTime(millisInput);
        }

        if (!mTimerRunning) {
            startTimer();
        }

    }


    private void stop() {
        if (mTimerRunning) {
            pauseTimer();
        }

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    private void updateCountDownText() {

        long days = TimeUnit.MILLISECONDS.toDays(mTimeLeftInMillis);
        long daysMillis = TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis - daysMillis);
        long hoursMillis = TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis - daysMillis - hoursMillis);
        long minutesMillis = TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis - daysMillis - hoursMillis - minutesMillis);

        String resultString = days + "d:" + hours + "h:" + minutes + "m:" + seconds + "s";

        mTextViewCountDown.setText(resultString);
    }

    private void setTime(long milliseconds) {

        saved_ads_time = mTimeLeftInMillis + milliseconds;

        mTimeLeftInMillis = saved_ads_time;
        updateCountDownText();

    }


    private void addTime(long time){

        setTime(time);

        if (mTimerRunning){
            pauseTimer();
        }

        startTimer();
    }


    private void saveTime(long time) {
        SharedPreferences mTime = getSharedPreferences("time", Context.MODE_PRIVATE);

        SharedPreferences.Editor time_edit = mTime.edit();
        time_edit.putLong("SAVED_TIME", time).apply();
    }

    private void addTime(){
        long added = sp.getLong("isAdded", 0);

        if (added == 1){
            long added_time = sp.getLong("AddedTime", 0);

            if (mTimerRunning){
                addTime(added_time);
            }else{
                setTime(added_time);
            }
            sp.edit().putLong("isAdded", 0).apply();
            saveTime(mTimeLeftInMillis);
        }
    }

    private void resumeTime() {

        SharedPreferences mTime = getSharedPreferences("time", Context.MODE_PRIVATE);

        long saved_time = mTime.getLong("SAVED_TIME", 1);
        setTime(saved_time);

        // Use this code to continue time if app close accidentally while connected
        /**
         String state = SkStatus.getLastState();

         if (SkStatus.SSH_CONECTADO.equals(state)) {

         if (!mTimerRunning){
         startTimer();
         }
         }**/

        mTimerEnabled = true;
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                saveTime(mTimeLeftInMillis);
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                pauseTimer();
                saved_ads_time = 0;

                // Code for auto stop vpn (sockshtttp)

                Intent stopVPN = new Intent(SocksHttpService.TUNNEL_SSH_STOP_SERVICE);
                LocalBroadcastManager.getInstance(SocksHttpMainActivity.this)
                    .sendBroadcast(stopVPN);


                Toast.makeText(SocksHttpMainActivity.this, "Time expired! Click Add + Time to renew access!", Toast.LENGTH_LONG).show();

            }

        }.start();
        mTimerRunning = true;


    }
    
	private void setupInterstitial(){

        mInterstitialAd = new InterstitialAd(this);
        
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); // inter ads
		
		mInterstitialAd.setAdListener(new AdListener() {

			@Override
			public void onAdClosed() {
				// Code to be executed when the interstitial ad is closed.
				Toast.makeText(SocksHttpMainActivity.this, "Thank you for supporting!! 💙", Toast.LENGTH_SHORT)
					.show();
                 
                 loadInterstitial();
					
			}
		});
        
        loadInterstitial();
	}
    
    private void loadInterstitial(){

        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        
    }
    
    private void showInterstitial(){
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show(); 
         } else {
            loadInterstitial();    
        }                                                     
    }
	
	private void adsPopUp() {
		if (this.success.isLoaded()) {
            success.show();
		} else {
			// Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
	}


	private void loadInterstitialAds(){
		success = new InterstitialAd(this);
		success.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		success.loadAd(new AdRequest.Builder().build());
		success.setAdListener(new AdListener() {

				private Context applicationContext;

				@Override
				public void onAdLoaded() {
					// Code to be executed when an ad finishes loading.
					loadInterstitial();
				}

				@Override
				public void onAdFailedToLoad(int errorCode) {
					// Code to be executed when an ad request fails.
				}

				@Override
				public void onAdOpened() {
					// Code to be executed when the ad is displayed.
				}

				@Override
				public void onAdLeftApplication() {
					// Code to be executed when the user has left the app.
				}

				@Override
				public void onAdClosed() {
					loadInterstitialAds();
					//Toast.makeText(getApplicationContext(),"Thanks for Supporting ❤️",Toast.LENGTH_SHORT).show();
					// Code to be executed when the interstitial ad is closed.
				}
			});
	}
	private void timehours ()
	{

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View inflate = inflater.inflate(R.layout.addtime, (ViewGroup) null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
		builer.setView(inflate); 
		ImageView iv = inflate.findViewById(R.id.images_dialog1);
		TextView title = inflate.findViewById(R.id.text_view_dialog1);
		TextView ms = inflate.findViewById(R.id.text_view_dialog2);
		Button ok = inflate.findViewById(R.id.add_time_dialog);
		iv.setImageResource(R.drawable.timer);
		title.setText("Reward Time!");
		ms.setText("Watch the whole video to get + 2 hours");
		ok.setText("GET + 2 HOUR");
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
						//loadRewardedVideoAd();
						watcher();

					}
					catch (Exception e)
					{
						e.printStackTrace();
					}

				}});

		time.show();
	}

	private void watcher () {
		ppd = new ProgressDialog(SocksHttpMainActivity.this);
		ppd.setIcon(R.drawable.timer);
		ppd.setTitle("Watch Video - FREE");
		ppd.setMessage("Loading video please wait...");
		ppd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		ppd.setCanceledOnTouchOutside(false);
		cancelProgressBar = true;
		ppd.show();

	}

	private void sucess_text () {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View inflate = inflater.inflate(R.layout.succes, (ViewGroup) null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
		builer.setView(inflate); 
		ImageView iv = inflate.findViewById(R.id.succes_Image);
		TextView title = inflate.findViewById(R.id.success_text1);
		TextView ms = inflate.findViewById(R.id.success_text2);
		Button ok = inflate.findViewById(R.id.success_ok);
		iv.setImageResource(R.drawable.timer);
		title.setText("Success!");
		ms.setText("Your access will be renewed shortly..");
		ok.setText("Confirm");
		final AlertDialog alert = builer.create(); 
		alert.setCanceledOnTouchOutside(false);
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alert.getWindow().setGravity(Gravity.CENTER); 
		alert.show();
		ok.setOnClickListener(new View.OnClickListener() { 
				@Override 
				public void onClick(View v) { 
					try
					{
						alert.dismiss();
						//https://facebook.com/groups/201305111236913/
						adsPopUp();

					}
					catch (Exception e)
					{
						e.printStackTrace();
					}

				}});

		alert.show();
	}


	private void failed ()
	{

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.rewarded_error, (ViewGroup) null);
        AlertDialog.Builder builer = new AlertDialog.Builder(this); 
        builer.setView(inflate); 
        ImageView iv = inflate.findViewById(R.id.images_error1);
        TextView title = inflate.findViewById(R.id.text_view_error1);
        TextView ms = inflate.findViewById(R.id.text_view_error2);
        Button ok = inflate.findViewById(R.id.add_time_error);
        iv.setImageResource(R.drawable.timer);
        title.setText("Reward Video Failed!");
        ms.setText("Reward Video failed to load do you want to try again?");
        ok.setText("TRY AGAIN");
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setGravity(Gravity.CENTER); 
        alert.show();
        ok.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) { 
                    try
                    {
						alert.dismiss();
						//loadRewardedVideoAd();
					    watcher();

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }});

        alert.show();
    }
	
	private void checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected())
        {
	        toolbar_main.setSubtitle("WI-FI: "+TunnelUtils.getLocalIpAddress());
			toolbar_main.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText);

        } else if (mMobile.isConnected()) {
            
			toolbar_main.setSubtitle("MOBILE: "+TunnelUtils.getLocalIpAddress());
			toolbar_main.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText);
			
        } else {
			toolbar_main.setSubtitle("NO CONNECTION");
			toolbar_main.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText);
        }
	} 
	
	
public void location(){
if(geoCheckBox.isChecked()){
    new UpdateCore(SocksHttpMainActivity.this, "http://ip-api.com/json", new UpdateCore.Listener() {
	@Override
    public void onLoading()
	{

		progressDialog = new ProgressDialog(SocksHttpMainActivity.this);
		progressDialog.setIcon(R.drawable.icon);
		progressDialog.setTitle("Checking your location");
		progressDialog.setMessage("Loading please wait...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

    @Override
    public void onCompleted(String config) throws Exception
	{
	progressDialog.dismiss();
	JSONObject geo = new JSONObject(config);
	StringBuffer sb = new StringBuffer();
	sb.append("").append("ISP: ").append(geo.getString("isp"));
	sb.append("\n\n").append("Time Zone: ").append(geo.getString("timezone"));
	sb.append("\n\n").append("Country Code: ").append(geo.getString("countryCode"));
	sb.append("\n\n").append("Country: ").append(geo.getString("country"));
	sb.append("\n\n").append("Region: ").append(geo.getString("regionName"));
	sb.append("\n\n").append("City: ").append(geo.getString("city"));
	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.geo, (ViewGroup) null);
        AlertDialog.Builder builer = new AlertDialog.Builder(SocksHttpMainActivity.this); 
        builer.setView(inflate);
        ImageView iv = inflate.findViewById(R.id.new_Image);
        TextView title = inflate.findViewById(R.id.new_text1);
        TextView ms = inflate.findViewById(R.id.new_text2);
        Button ok = inflate.findViewById(R.id.new_button1);
        iv.setImageResource(R.drawable.icon);
        title.setText("Your Location!");
        ms.setText(sb);
        ok.setText("Confirm");
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setGravity(Gravity.CENTER); 
        alert.show();
        ok.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) { 

                    // TODO: Implement this method
                    try
                    {
						alert.dismiss();
                        
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }});

        alert.show();
    }

	@Override
    public void onCancelled()
	{

	}

	@Override
	public void onException(String ex)
		{
			Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
			progressDialog.dismiss();
	    }
									
		}).execute();
   }
}
	public static void updateMainViews(Context context) {
		Intent updateView = new Intent(UPDATE_VIEWS);
		LocalBroadcastManager.getInstance(context)
			.sendBroadcast(updateView);
	}
	
	public void showExitDialog() {
		AlertDialog dialog = new AlertDialog.Builder(this).
			create();
		dialog.setTitle(getString(R.string.attention));
		dialog.setMessage(getString(R.string.alert_exit));

		dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.
				string.exit),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Utils.exitAll(SocksHttpMainActivity.this);
				}
			}
		);

		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.
				string.minimize),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// minimiza app
					Intent startMain = new Intent(Intent.ACTION_MAIN);
					startMain.addCategory(Intent.CATEGORY_HOME);
					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(startMain);
				}
			}
		);

		dialog.show();
	}
}

