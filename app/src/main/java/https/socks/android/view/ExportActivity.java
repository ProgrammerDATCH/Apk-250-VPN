package https.socks.android.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import com.mudasobwatunnel.plus.R;
import https.socks.android.util.ConfigUtil;
import java.io.File;
import java.io.FileOutputStream;
import org.json.JSONObject;
import com.slipkprojects.ultrasshservice.config.Settings;
import https.socks.android.util.AESCrypt;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

public class ExportActivity extends AppCompatActivity
{
    private EditText etFilename;
    private TextInputLayout messageLayout;
    private EditText etMessage;
    private Button btnExport;
    private CheckBox ckAddMessage;
    private CheckBox ckLock;
    private ConfigUtil mConfig;
    private SharedPreferences sp;

	private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
		MobileAds.initialize(this,"ca-app-pub-4327217352829821~8010087111");
        mAdView = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
		int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if (permissionCheck != PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
		}
        sp = new Settings(this).getPrefsPrivate();
        new File(Environment.getExternalStorageDirectory() + "/"+getString(R.string.app_name)+"/").mkdirs();
        mConfig = new ConfigUtil(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etFilename = (EditText) findViewById(R.id.etFilename);
        messageLayout = (TextInputLayout) findViewById(R.id.messageLayout);
        messageLayout.setVisibility(View.GONE);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnExport = (Button) findViewById(R.id.btnExport);
        ckAddMessage = (CheckBox) findViewById(R.id.ckAddMessage);
        ckAddMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton p1, boolean p3)
                {
                    sp.edit().putBoolean("isTrue", p3).apply();
                    if (p3) {
                        messageLayout.setVisibility(View.VISIBLE);
                        etMessage.setVisibility(View.VISIBLE);
                    } else {
                        messageLayout.setVisibility(View.GONE);
                        etMessage.setVisibility(View.GONE);
                    }
                }
            });
        ckLock = (CheckBox) findViewById(R.id.ckLock);
        ckLock.setChecked(true);
        ckLock.setEnabled(false);
        btnExport.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View p1)
                {
                    try {
                        String sName = etFilename.getText().toString();
                        boolean isAddMessage = sp.getBoolean("isTrue", false);
                        String sMessage = etMessage.getText().toString();
                        String sPayload = sp.getString("SavedHTTP", "");
                        String sSNI = sp.getString("SavedSSL", "");
						String sSSL = sp.getString("SavedHTTP + SSL", "");
                        String ssPayload = sp.getString("SavedSSL + HTTP", "");
						String sUser = sp.getString("SavedUSER", "");
                        String pPass = sp.getString("SavedPASS", "");
						String sBug = sp.getString("SavedBUG", "");
						
						String dnsC = sp.getString("SavedCHAVE", "");
						String dnsS = sp.getString("SavedSERKEY", "");
						String dnsD = sp.getString("SavedDNS", "");
						int tunType = sp.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
                        
                        JSONObject obj = new JSONObject();
                        obj.put("Payload", sPayload);
                        obj.put("SNI", sSNI);
						obj.put("Payload", sSSL);
                        obj.put("SNI", ssPayload);
						obj.put("ServerUser", sUser);
                        obj.put("ServerPass", pPass);
						obj.put("ServerIP", sBug);
						
						obj.put("chaveKey", dnsC);
						obj.put("serverNameKey", dnsS);
						obj.put("dnsKey", dnsD);
						
                        obj.put("TunnelType", tunType);
                        obj.put("isMsg", isAddMessage);
                        obj.put("Message", sMessage);
                        String data = AESCrypt.encrypt("hiro", obj.toString());
                        StringBuffer sb = new StringBuffer();
                        File path = new File(sb.append(Environment.getExternalStorageDirectory().getAbsolutePath()).append("/"+getString(R.string.app_name)+"").toString());
                        export(path, sName, data);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), 0).show();
                    }
                }
            });
    }

    void export(File directory, String fileName, String content) {
        try {
            File fileToSave = new File(directory, fileName+".ig");
            FileOutputStream fos = new FileOutputStream(fileToSave);
            String sl = "/";
            fos.write(content.getBytes());
            String saveNot = "Successfully Saved to "+directory+sl+fileToSave.getName();
            Toast.makeText(this, saveNot, Toast.LENGTH_SHORT).show();
            fos.close();
            finish();
        }catch (Exception e) {
            Toast.makeText(this, e.getMessage(),0).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
