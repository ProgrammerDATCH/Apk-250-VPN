package https.socks.android.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.mudasobwatunnel.plus.R;

public class TextDetailDocumentsCell extends FrameLayout {

    private TextView textView;
    private TextView valueTextView;
    private TextView typeTextView;
    private TextView storageTextView;
    private ImageView imageView;


    public TextDetailDocumentsCell(Context context) {
        super(context);

        textView = new TextView(context);
        //  textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine(true);
        textView.setGravity(Gravity.LEFT);
        addView(textView);
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.topMargin = AndroidUtilities.dp(10);
        layoutParams.leftMargin = AndroidUtilities.dp(71);
        layoutParams.rightMargin = AndroidUtilities.dp(16);
        layoutParams.gravity = Gravity.LEFT;
        textView.setLayoutParams(layoutParams);

        valueTextView = new TextView(context);
        //  valueTextView.setTextColor(Color.WHITE);
        valueTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        valueTextView.setLines(1);
        valueTextView.setMaxLines(1);
        valueTextView.setSingleLine(true);
        valueTextView.setGravity(Gravity.LEFT);
        addView(valueTextView);
        layoutParams = (LayoutParams) valueTextView.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.topMargin = AndroidUtilities.dp(35);
        layoutParams.leftMargin = AndroidUtilities.dp(71);
        layoutParams.rightMargin = AndroidUtilities.dp(16);
        layoutParams.gravity = Gravity.LEFT;
        valueTextView.setLayoutParams(layoutParams);

        typeTextView = new TextView(context);
        //   typeTextView.setBackgroundColor(Color.WHITE);
        typeTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        typeTextView.setGravity(Gravity.CENTER);
        typeTextView.setSingleLine(true);
        typeTextView.setTextColor(0xffd1d1d1);
        typeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        typeTextView.setTypeface(Typeface.DEFAULT_BOLD);
        addView(typeTextView);
        layoutParams = (LayoutParams) typeTextView.getLayoutParams();
        layoutParams.width = AndroidUtilities.dp(40);
        layoutParams.height = AndroidUtilities.dp(40);
        layoutParams.leftMargin = AndroidUtilities.dp(16);
        layoutParams.rightMargin = AndroidUtilities.dp(0);
        layoutParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        typeTextView.setLayoutParams(layoutParams);

        imageView = new ImageView(context);
        addView(imageView);
        layoutParams = (LayoutParams) imageView.getLayoutParams();
        layoutParams.width = AndroidUtilities.dp(40);
        layoutParams.height = AndroidUtilities.dp(40);
        layoutParams.leftMargin = AndroidUtilities.dp(16);
        layoutParams.rightMargin = AndroidUtilities.dp(0);
        layoutParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(layoutParams);



        storageTextView = new TextView(context);
        storageTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        storageTextView.setGravity(Gravity.CENTER);
        storageTextView.setSingleLine(true);
        storageTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
        storageTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        storageTextView.setTypeface(Typeface.DEFAULT_BOLD);
        storageTextView.setVisibility(GONE);
        storageTextView.setText("已导入");
        addView(storageTextView);
        layoutParams = (LayoutParams) storageTextView.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.leftMargin = AndroidUtilities.dp(16) ;
        layoutParams.rightMargin = AndroidUtilities.dp(16);
        layoutParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        storageTextView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(64), MeasureSpec.EXACTLY));
    }

    public void setTextAndValueAndTypeAndThumb(String text, String value, String type, String thumb, int resId,boolean isStorage) {
        textView.setText(text);
        valueTextView.setText(value);

        if (type != null) {
            typeTextView.setVisibility(VISIBLE);
            typeTextView.setText(type);

            if (isStorage){
                storageTextView.setVisibility(VISIBLE);

            }else{
                storageTextView.setVisibility(GONE);

            }
        } else {
            typeTextView.setVisibility(GONE);

            storageTextView.setVisibility(GONE);
        }

        if (resId != 0) {
            if (thumb != null) {
//                imageView.setImage(thumb, "40_40", null);
            } else  {
                imageView.setImageResource(resId);
            }
            imageView.setVisibility(VISIBLE);
        } else {
            imageView.setVisibility(GONE);
        }
    }


}
