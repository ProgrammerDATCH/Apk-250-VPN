package https.socks.android.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.slipkprojects.ultrasshservice.config.Settings;
import https.socks.android.DirectoryFragment;
import com.mudasobwatunnel.plus.R;
import https.socks.android.util.ConfigUtil;
import https.socks.android.util.DataBaseHelper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import org.json.JSONObject;
import https.socks.android.util.AESCrypt;

public class ImportActivity extends AppCompatActivity
 {
    private FragmentManager fragmentManager = ((FragmentManager) null);
    private FragmentTransaction fragmentTransaction = ((FragmentTransaction) null);
    private DirectoryFragment mDirectoryFragment;
    private LinearLayout main;
    private Toolbar toolbar;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private ConfigUtil mConfig;

    public String getExtension(File file) {
        String filename = file.getAbsolutePath();

        if (filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        }

        return "";
    }

    private void readStream(InputStream in)   {
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in));
            char[] buff = new char[1024];
            while (true) {
                int read = reader.read(buff, 0, buff.length);
                if (read <= 0) {
                    break;
                }
                sb.append(buff, 0, read);
            }
        } catch (Exception e) {

        }
        importConfig(sb.toString());
    }


    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        editor = new Settings(this).getPrefsPrivate().edit();
        sp = new Settings(this).getPrefsPrivate();
        new File(Environment.getExternalStorageDirectory() + "/"+getString(R.string.app_name)+"/").mkdirs();
        mConfig = new ConfigUtil(this);
        Intent intent = getIntent();
        String scheme = intent.getScheme();
        if (scheme != null && (scheme.equals("file") || scheme.equals("content"))) {
            Uri data = intent.getData();

            File fle = new File(data.getPath());

            String file_extensao = getExtension(fle);
            if (file_extensao != null && file_extensao.equals("as")) {

                try {
                    readStream(getContentResolver().openInputStream(data));
                } catch (Exception e) {
                    Toast.makeText(this,"Error File not compatible",
                                   Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(this,"Error File not compatible",
                               Toast.LENGTH_SHORT).show();
            }



        }
        setContentView(R.layout.import_activity);
        this.main = (LinearLayout) findViewById(R.id.mainv5);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar.setTitle("Select File");
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.fragmentManager = getSupportFragmentManager();
        this.fragmentTransaction = this.fragmentManager.beginTransaction();
        this.mDirectoryFragment = new DirectoryFragment();
        this.mDirectoryFragment.setDelegate(new DirectoryFragment.DocumentSelectActivityDelegate() {


                @Override
                public void startDocumentSelectActivity() {
                }

                @Override
                public void didSelectFiles(DirectoryFragment directoryFragment, ArrayList<String> arrayList) {
                    if (((String) arrayList.get(0)).toString().contains(".ig")) {
                        try {
                            importConfig(((String) inet(arrayList.get(0)).toString()));
                            return;
                        } catch (Exception e) {
                            Snackbar.make(main, "Error: File not compatible" + e.getMessage(), 0).show();
                            return;
                        }
                    }
                    Snackbar.make(main, "Please choose a valid file.", 0).show();
                }

                @Override
                public void updateToolBarName(String str) {
                    toolbar.setTitle(str);
                }


            });
        this.fragmentTransaction.add(R.id.fragment_container, this.mDirectoryFragment, new StringBuffer().append("").append(this.mDirectoryFragment.toString()).toString());
        this.fragmentTransaction.commit();
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)   {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)  {
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"Permission Granted",0).show();
                } else {
                    Toast.makeText(this,"Permission Denied ",0).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        this.mDirectoryFragment.onFragmentDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (this.mDirectoryFragment.onBackPressed_()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void importConfig(final String str) {

        try {    
            Toast.makeText(this,"Import Success!",0).show();
            editor.putInt("SavedPos", 5).apply();
            editor.putBoolean("SavedSetup", true).apply();
            DataBaseHelper db = new DataBaseHelper(this, "ImportedConfig");
            String data = AESCrypt.decrypt("hiro", str);
            if (db.isExist("1")) {
                db.updateData(data);
            } else {
                db.insertData(data);
            }
            JSONObject obj = new JSONObject(data);
            boolean isShowMessage = obj.getBoolean("isMsg");
            editor.putBoolean("isMsg", isShowMessage).apply();
            String cMessage = obj.getString("Message");
            editor.putString("Mess", cMessage).apply();
            finish();           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String inet(String str) {
        Environment.getExternalStorageDirectory();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream fileInputStream = new FileInputStream(str);
            try {
                for (int read = fileInputStream.read(); read != -1; read = fileInputStream.read()) {
                    byteArrayOutputStream.write(read);
                }
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            Toast.makeText(getApplicationContext(), "ERROR: Error reading File", 1).show();
            e2.printStackTrace();
            Log.e("tag", e2.getMessage());
        }
        return byteArrayOutputStream.toString();
    }    
}
