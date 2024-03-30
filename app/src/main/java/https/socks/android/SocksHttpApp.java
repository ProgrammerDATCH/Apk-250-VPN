package https.socks.android;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import https.socks.android.preference.SettingsPreference;
import com.slipkprojects.ultrasshservice.util.SkProtect;
import com.slipkprojects.ultrasshservice.logger.SkStatus;
import android.content.Context;
import android.content.pm.PackageInfo;
import https.socks.android.util.Utils;
import android.os.Build;
import android.content.pm.PackageManager;
import com.slipkprojects.ultrasshservice.SocksHttpCore;
import com.slipkprojects.ultrasshservice.config.Settings;
import android.util.Log;
import com.google.android.gms.ads.MobileAds;
import android.content.res.Configuration;
import https.socks.android.preference.LocaleHelper;
import https.socks.android.util.FontsOverride;
import android.content.SharedPreferences;

/**
* App
*/
public class SocksHttpApp extends Application
{
    private static final String TAG = SocksHttpApp.class.getSimpleName();
    public static final String PREFS_GERAL = "SocksHttpGERAL";
	private static SharedPreferences sharedPreferences;
    public static final String ADS_UNITID_INTERSTITIAL_MAIN = "ca-app-pub-6670692272691929/1043079897";
    public static final String ADS_UNITID_BANNER_MAIN = "ca-app-pub-6670692272691929/8182385078";
    public static final String ADS_UNITID_BANNER_SOBRE = "ca-app-pub-6670692272691929/8182385078";
    public static final String ADS_UNITID_BANNER_TEST = "ca-app-pub-6670692272691929/8182385078";
    public static final String APP_FLURRY_KEY = "RQQ8J9Q2N4RH827G32X9";

    private static SocksHttpApp mApp;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mApp = this;

        // inicia
        SocksHttpCore.init(this);

        // protege o app
        SkProtect.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //LocaleHelper.setLocale(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //LocaleHelper.setLocale(this);
    }

   /* private void setModoNoturno(Context context) {
        boolean is = new Settings(context)
            .getModoNoturno().equals("on");

        int night_mode = is ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(night_mode);
    }*/

    public static SocksHttpApp getApp() {
        return mApp;
    }
	public static SharedPreferences getSharedPreferences()  {
        return sharedPreferences;
    }
	public static SharedPreferences getDefSharedPreferences()  {
        return sharedPreferences;
    }
}
