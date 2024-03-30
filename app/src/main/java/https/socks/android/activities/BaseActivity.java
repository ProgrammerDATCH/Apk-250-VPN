package https.socks.android.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import com.slipkprojects.ultrasshservice.config.Settings;
import com.mudasobwatunnel.plus.R;
import https.socks.android.preference.LocaleHelper;
import https.socks.android.util.AESCrypt;
import https.socks.android.util.Utils;

import static android.content.pm.PackageManager.GET_META_DATA;
import org.json.*;
import java.io.*;
import android.widget.*;
import java.net.*;

/**
 * Created by Pankaj on 03-11-2017.
 */
public abstract class BaseActivity extends AppCompatActivity
{
	public static int mTheme = 0;
    public static final String PASSWORD = "PudayNaMalake@Pinasok2022";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if (!(((String) getPackageManager().getApplicationLabel(getApplicationInfo())).equals("MUDASOBWA TUNNEL Plus") && getPackageName().equals("com.mudasobwatunnel.plus"))) {
			AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
			builder.setTitle("OPPS APP MODIFIED");
			builder.setMessage("Please install the original application version")
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
						Utils.exitAll(BaseActivity.this);
					}
				});
			builder.show();
		}
		resetTitles();
	}
	
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(LocaleHelper.setLocale(base));
	}
	
    protected void http_sign(Context cont) {
        if (getSign().equals(AESCrypt.http_sign)) {
            return;
        } else {
            finish();
        }
    }

    public String getSign() {
        StringBuilder str = new StringBuilder();
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), getPackageManager().GET_SIGNATURES);
            for (Signature signs: pi.signatures) {
                str.append(signs.toCharsString());
            }
        } catch (Exception e) {}
        return str.toString();
    }
	
	protected void writeMyFile(JSONObject obj) {
		try {
			String encoded_name = 
				String.format("%s", URLEncoder.encode("Config.json", "UTF-8")
							  );

			File dir = new File(getFilesDir(), encoded_name);
			OutputStream out = new FileOutputStream(dir);
			String value = AESCrypt.encrypt(PASSWORD, obj.toString(2));
			out.write(value.getBytes());
			out.flush();

			if (out != null)
				out.close();

		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
    protected JSONObject getJSONConfig2(Context context) throws Exception {
		String json = null;
        File file = new File(context.getFilesDir(), "Config.json");
		if (file.exists()) {
			String json_file = Utils.readStream(new FileInputStream(file));
			json = AESCrypt.decrypt(PASSWORD, json_file);
			// return new JSONObject(json);
		} else {
			InputStream inputStream = context.getAssets().open("config/config.json");
			json = AESCrypt.decrypt(PASSWORD, Utils.readStream(inputStream));
			// return new JSONObject(json);
		}
        return new JSONObject(json);
    }
	
	protected void resetTitles() {
		try {
			ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
			if (info.labelRes != 0) {
				setTitle(info.labelRes);
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
