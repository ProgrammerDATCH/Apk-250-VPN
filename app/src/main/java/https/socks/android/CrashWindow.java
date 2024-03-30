package https.socks.android;

import android.graphics.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.widget.*;
import com.mudasobwatunnel.plus.R;
import android.support.v7.widget.Toolbar;

public class CrashWindow extends AppCompatActivity {

    private TextView error;
    @Override
    protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.crash_window);
		this.error = (TextView) findViewById(R.id.crashwindow);
        this.error.setText(getIntent().getStringExtra("error"));
		Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
		setSupportActionBar(toolbar);
		toolbar.setTitleTextColor(Color.WHITE);
    }
}
