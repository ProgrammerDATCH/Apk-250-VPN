package https.socks.android.model;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import java.io.*;
import java.net.*;
import org.apache.http.*;
import android.preference.*;

public class UpdateApp extends AsyncTask<String, String, String> {
    private static final String TAG = "NetGuard.Download";
    private Context context;
    private Listener listener;
	private HttpURLConnection uRLConnection;
	private InputStream is;
	private BufferedReader buffer;
	private SharedPreferences mPref;
	private ProgressDialog pd;

    public interface Listener {
        void onCompleted(String config);

        void onCancelled();

        void onException(String ex);
    }

    public UpdateApp(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
		mPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    protected void onPreExecute() {
		pd = new ProgressDialog(context);
		pd.setMessage("Checking Please Wait...");
		pd.show();
    }

	@Override
    protected String doInBackground(String... args) {
		try {
			String api = "https://pastegen.com/raw/i9zsFnl28O";
            URL mURL = new URL(api);
			HttpURLConnection con = (HttpURLConnection)mURL.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			StringBuilder sb = new StringBuilder();
			char[] buf = new char[1024];
			Reader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while (true) {
				int read = reader.read(buf);
				if (read <= 0) {
					break;
				}
				sb.append(buf, 0, read);
			}
			return sb.toString();
        } catch (Exception e) {
            return "error";
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException ignored) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {

                }
            }
            if (uRLConnection != null) {
                uRLConnection.disconnect();
            }
        }
	}


    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.i(TAG, "Cancelled");
		pd.dismiss();
        listener.onCancelled();
    }

    @Override
    protected void onPostExecute(String result) {
		pd.dismiss();
        if (result.equals("error")) {
            listener.onException(result);
        } else
            listener.onCompleted(result);
    }

}

