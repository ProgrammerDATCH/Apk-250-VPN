package https.socks.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mudasobwatunnel.plus.R;
import java.util.ArrayList;
import org.json.JSONObject;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SetupAdapter extends ArrayAdapter<String> {

    private int spinner_id;
    private String[] sName;
    public SetupAdapter(Context context, int spinner_id, String[] list) {
        super(context, R.layout.custom_setup, list);
        this.spinner_id = spinner_id;
        this.sName = list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return view(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return view(position, convertView, parent);
    }

    private View view(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_setup, parent, false);
        TextView tv = v.findViewById(R.id.customName);
        TextView extra = v.findViewById(R.id.customExtra);
        TextView info = v.findViewById(R.id.info);
        TextView pInfos = v.findViewById(R.id.custom_info);

        try {
            if (spinner_id == R.id.setupSpinner) {
                getPayloadIcon(position, pInfos, tv, info);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    private void getPayloadIcon(int position, TextView extra, TextView tv, TextView info) throws Exception {
        String name = sName[position];
        tv.setText(name);
        if (name.contains("direct")) {
            extra.setText("Internet");
        } else if (name.contains("payload")) {
            extra.setText("HTTP Mode");
        } else if(name.contains("ssl")) {
            extra.setText("SSL/TLS Mode");
		} else if(name.contains("sslpayload")) {
            extra.setText("SSL/PAYLOAD Mode");
        }

    }

}
