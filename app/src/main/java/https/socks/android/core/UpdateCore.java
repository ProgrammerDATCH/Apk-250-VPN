package https.socks.android.core;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import java.io.*;
import java.net.*;
import org.apache.http.client.methods.*;
import org.apache.http.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.*;

public class UpdateCore extends AsyncTask<String, String, String> {
    private static final String TAG = "NetGuard.Download";

    private Context context;

    private Listener listener;
    private PowerManager.WakeLock wakeLock;

    private HttpURLConnection uRLConnection;

    private InputStream is;

    private BufferedReader buffer;

	private String url;

    
    public interface Listener {
        void onLoading();
        void onCompleted(String config) throws Exception;

        void onCancelled();

        void onException(String ex);
    }

    public UpdateCore(Context context, String url, Listener listener) {
        this.context = context;
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onLoading();
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            String api = url;
            if(!api.startsWith("http")){
                api = new StringBuilder().append("http://").append(url).toString();
            }
            URL oracle = new URL(api);
            HttpClient Client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(oracle.toURI());
            HttpResponse response = Client.execute(httpget);
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                                                           in, "iso-8859-1"), 8);

            //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
            {
                str.append(line);
            }
            in.close();
            return str.toString();
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
       // Log.i(TAG, "Cancelled");
       // pd.dismiss();
        listener.onCancelled();
    }

    @Override
    protected void onPostExecute(String result) {
        // wakeLock.release();
        //nm.cancel(1);
       // pd.dismiss();
        try
		{
			if (result.equals("error"))
			{
				listener.onException(result);
			}
			else {
				listener.onCompleted(result);
			}
		}
		catch (Exception e)
		{
			listener.onException(e.getMessage());
		}
    }

}



