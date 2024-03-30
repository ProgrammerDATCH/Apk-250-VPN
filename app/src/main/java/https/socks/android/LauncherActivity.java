package https.socks.android;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;
import https.socks.android.activities.BaseActivity;
import com.mudasobwatunnel.plus.R;

	public class LauncherActivity extends BaseActivity
	{

		private TextView versionCode;
		Handler handler;
		@Override
		protected void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_splash_screen);
			handler = new Handler();
			handler.postDelayed(new Runnable(){
					@Override
					public void run(){
						Intent intent = new Intent(getApplicationContext(), SocksHttpMainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(intent);
						finish();
					}
				},500);
		
		}

		public String getVersionName() {
			String oldVerName = "";
			try {
				PackageInfo vc = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
				oldVerName = vc.versionName;
			} catch (PackageManager.NameNotFoundException e) {}
			return oldVerName;
		}

		public int getVersionCode() {
			int oldVerCode = 0;
			try {
				PackageInfo vc = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
				oldVerCode = vc.versionCode;
			} catch (PackageManager.NameNotFoundException e) {}
			return oldVerCode;
		}

	}

