package https.socks.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mudasobwatunnel.plus.R;
import https.socks.android.SocksHttpApp;
import https.socks.android.model.AppInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GetPackageClass {
    private static final String TAG = "GetPackageClass";
    private Context context;
    private String[] items;

    public GetPackageClass(Context c, String[] i) {
        context = c;
        items = i;
    }

    public String getAppString(Context context) {
        Log.e(TAG, "getAppString: ");
        StringBuffer stringBuffer = new StringBuffer();

        for (AppInfo appInfo : getAllAppList(context)) {
            stringBuffer.append(appInfo.toString());

        }

        return stringBuffer.toString();
    }

    private boolean check(String uri)
    {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try
        {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed;
    }

    public void check() {
        try
        {
            PackageManager pm = context.getPackageManager();
            for (int i=0;i < items.length ;i++)
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setPackage(items[i]);
                List<ResolveInfo> list = pm.queryIntentActivities(intent, android.content.pm.PackageManager.GET_ACTIVITIES);
                for (ResolveInfo resolveInfo : list) {
                    if(check(items[i])){
                        BitmapDrawable drawable = (BitmapDrawable) pm.getApplicationIcon(items[i]);
                        blckApp(context,items[i],resolveInfo.loadLabel(pm).toString(),drawable.getBitmap());
                        break;
                    }
                }

            }
        }
        catch (PackageManager.NameNotFoundException e)
        {}

    }

    void hidekey(final Context c){
        final SharedPreferences mPref = c.getSharedPreferences(SocksHttpApp.PREFS_GERAL, Context.MODE_PRIVATE);
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.blocked_key, (ViewGroup) null);
        AlertDialog.Builder builer = new AlertDialog.Builder(c); 
        builer.setView(inflate); 
        final EditText ed = (EditText) inflate.findViewById(R.id.blckedEditText1);
        Button ok = (Button) inflate.findViewById(R.id.blckedButton1);
        final AlertDialog alert = builer.create(); 
        alert.setCancelable(true);
        alert.getWindow().setGravity(Gravity.CENTER); 
        alert.show();
        ok.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View v) { 
                    if(ed.getText().toString().equals("tunnelx core v2")){
                        mPref.edit().putString("HIDE_KEY",ed.getText().toString()).apply();
                        Toast.makeText(c, "Key successful select Cancel again ☑️", 1).show();
                    }else{
                        Toast.makeText(c, "ERROR KEY ❌", 1).show();
                    }
                    alert.dismiss();
                }
            }); 
    }

    public void blckApp(final Context c,String packageName,String appName,Bitmap mIcon){
        final SharedPreferences mPref = c.getSharedPreferences(SocksHttpApp.PREFS_GERAL, Context.MODE_PRIVATE);
        if(mPref.getString("HIDE_KEY","").equals("arxhival")){
            return;
        }else{
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflate = inflater.inflate(R.layout.app_blocked, (ViewGroup) null);
            AlertDialog.Builder builer = new AlertDialog.Builder(c); 
            builer.setView(inflate); 
            ImageView iv = (ImageView) inflate.findViewById(R.id.iv);
            TextView tv1 = (TextView) inflate.findViewById(R.id.blckTextView1);
            TextView tv2 = (TextView) inflate.findViewById(R.id.blckTextView2);
            TextView tv3 = (TextView) inflate.findViewById(R.id.tv1);
            TextView tv4 = (TextView) inflate.findViewById(R.id.tv2);
            Button ok = (Button) inflate.findViewById(R.id.ok_Button);
            Button cancel = (Button) inflate.findViewById(R.id.cancel);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
            iv.setImageBitmap(mIcon);
            tv3.setText(appName);
            tv4.setText(packageName);
            final AlertDialog alert = builer.create(); 
            alert.setCancelable(false);
            alert.getWindow().setGravity(Gravity.CENTER); 
            alert.show();
            ok.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View v) { 
                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            ((Activity) c).finishAndRemoveTask();
                        } else {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                        System.exit(0);
                    }
                }); 
            cancel.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View v) { 
                        if(mPref.getString("HIDE_KEY","").equals("tunnelx core v2")){
                            alert.dismiss();
                        }else{
                            hidekey(c);
                        }
                    }
                }); 
        }

    }
    public static List<AppInfo> getAllAppList(Context context) {
        Log.e(TAG, "getAllAppList: " );
        PackageManager packageManager = context.getPackageManager();
        List<AppInfo> allAppsList = new ArrayList<AppInfo>();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : list) {
            AppInfo appInfo = new AppInfo();
            appInfo.setPackageName(resolveInfo.activityInfo.packageName);//package name
            appInfo.setActivityName(resolveInfo.activityInfo.name);//class name
            appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());//app name
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.packageName, 0);
                //  appInfo.setInstallTime(packageInfo.firstInstallTime);//install time
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    appInfo.setSystemFlag(0);//is not system app
                } else {
                    appInfo.setSystemFlag(1);//is system app
                }
                //icon
                if (Build.VERSION.SDK_INT < 26) {
                    BitmapDrawable drawable = (BitmapDrawable) packageManager.getApplicationIcon(resolveInfo.activityInfo.packageName);
                    appInfo.setIcon(drawable.getBitmap());
                } else {
                    Drawable drawable = packageManager.getApplicationIcon(resolveInfo.activityInfo.packageName);
                    if (drawable instanceof BitmapDrawable) {
                        appInfo.setIcon(((BitmapDrawable) drawable).getBitmap());
                    } else if (drawable instanceof AdaptiveIconDrawable) {
                        Drawable bgDrawable = ((AdaptiveIconDrawable) drawable).getBackground();
                        Drawable fgDrawable = ((AdaptiveIconDrawable) drawable).getForeground();
                        Drawable[] drs = new Drawable[2];
                        drs[0] = bgDrawable;
                        drs[1] = fgDrawable;
                        LayerDrawable layerDrawable = new LayerDrawable(drs);
                        int width = layerDrawable.getIntrinsicWidth();
                        int height = layerDrawable.getIntrinsicHeight();
                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        layerDrawable.draw(canvas);
                        appInfo.setIcon(bitmap);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            allAppsList.add(appInfo);
        }
        Collections.sort(allAppsList, new Comparator<AppInfo>() {
                public int compare(AppInfo arg0, AppInfo arg1) {
                    Long long0 = arg0.getInstallTime();
                    Long long1 = arg1.getInstallTime();
                    return long1.compareTo(long0);
                }
            });
        return allAppsList;
    }
}
