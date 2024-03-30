package https.socks.android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mudasobwatunnel.plus.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONObject;
import android.widget.LinearLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class PromoAdapter extends ArrayAdapter<JSONObject> {

    private int spinner_id;

	private ImageView im;

    public PromoAdapter(Context context, int spinner_id, ArrayList<JSONObject> list) {
        super(context, R.layout.promo_item, list);
        this.spinner_id = spinner_id;
    }

    @Override
    public JSONObject getItem(int position) {
        return super.getItem(position);
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
      
		
		View v = LayoutInflater.from(getContext()).inflate(R.layout.promo_item, parent, false);
		TextView tv = v.findViewById(R.id.itemName);
        TextView extra = v.findViewById(R.id.textExtra);
        TextView pInfos = v.findViewById(R.id.payload_info);
		RelativeLayout p1_logo  = (RelativeLayout) v.findViewById(R.id.bg);
		ImageView im = v.findViewById(R.id.pImages);
		
        try {
           String name = getItem(position).getString("Name");
            tv. setText(name);

                       
               if (spinner_id == R.id.payloadSpinner) {
			   getPayloadIcon(position, im, extra, pInfos);
			   pInfos.setText(getItem(position).getString("pInfo"));
				String qwerty = getItem(position).getString("pInfo").toLowerCase();
				if (qwerty.contains("ssl")) {
                    extra.setText("SSL | TLS");
                    p1_logo.setBackgroundResource(R.drawable.stroke);
                } else if (qwerty.contains("inject")) {
                    extra.setText("INJECT");
                    p1_logo.setBackgroundResource(R.drawable.stroke);
                } else if (qwerty.contains("ws")) {
                    extra.setText("SSL + INJECT");
                    p1_logo.setBackgroundResource(R.drawable.stroke);
				} else if (qwerty.contains("dn")) {
					extra.setText("SLOWDNS");
					p1_logo.setBackgroundResource(R.drawable.stroke);
                } else if(qwerty.contains("dt")) {
                    extra.setText("DIRECT");
                    p1_logo.setBackgroundResource(R.drawable.stroke);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }


    private void getPayloadIcon(int position, ImageView im, TextView extra, TextView pInfos) throws Exception {
        String name = getItem(position).getString("Name").toLowerCase();


        if (name.contains("globe")) {
            im.setImageResource(R.drawable.ic_globe);
        } else if (name.contains("smart")) {
            im.setImageResource(R.drawable.ic_smart);
        } else if (name.contains("gtm")) {
            im.setImageResource(R.drawable.ic_gtm);
        } else if (name.contains("tm")) {
            im.setImageResource(R.drawable.ic_tm);
        } else if (name.contains("tnt")) {
            im.setImageResource(R.drawable.ic_tnt);
        }else if(name.contains("sun")) {
            im.setImageResource(R.drawable.ic_sun);
        }else if(name.contains("dito")) {
            im.setImageResource(R.drawable.ic_dito);
		}else if(name.contains("gomo")) {
            im.setImageResource(R.drawable.ic_gomo);
        }else if(name.contains("")) {
            im.setImageResource(R.drawable.icon_speed);
        }

    }

	
}
