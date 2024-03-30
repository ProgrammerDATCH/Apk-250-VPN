package https.socks.android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mudasobwatunnel.plus.R;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONObject;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SpinnerAdapter extends ArrayAdapter<JSONObject> {

    private int spinner_id;

    public SpinnerAdapter(Context context, int spinner_id, ArrayList<JSONObject> list) {
        super(context, R.layout.spinner_item, list);
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
        View v = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        TextView tv = v.findViewById(R.id.itemName);
        TextView info = v.findViewById(R.id.info);
        ImageView im = v.findViewById(R.id.itemImage);
        try {

            String name = getItem(position).getString("Name");
            tv. setText(name);


            if (spinner_id == R.id.serverSpinner) {
                getServerIcon(position, im, info);
				info.setText(getItem(position).getString("sInfo"));      

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    private void getServerIcon(int position, ImageView im, TextView info) throws Exception {
        InputStream inputStream = getContext().getAssets().open("flags/" + getItem(position).getString("FLAG"));
        im.setImageDrawable(Drawable.createFromStream(inputStream, getItem(position).getString("FLAG")));
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
