package https.socks.android.fragments;

import android.app.Dialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import com.mudasobwatunnel.plus.R;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.slipkprojects.ultrasshservice.logger.SkStatus;
import https.socks.android.SocksHttpMainActivity;
import com.slipkprojects.ultrasshservice.config.Settings;
import https.socks.android.preference.SettingsSSHPreference;

public class ClearConfigDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).
			create();
		dialog.setTitle(getActivity().getString(R.string.attention));
		dialog.setMessage(getActivity().getString(R.string.alert_clear_settings));

		dialog.setButton(DialogInterface.BUTTON_POSITIVE, getActivity().getString(R.
																				  string.yes),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Settings.clearSettings(getContext());				
					SkStatus.clearLog();				
					SocksHttpMainActivity.updateMainViews(getContext());

				}
			}
		);

		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getActivity().getString(R.
																				  string.no),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismiss();
				}
			}
		);

		return dialog;
	}

}
