package https.socks.android.model;

import https.socks.android.SocksHttpMainActivity;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class ViewFragment extends Fragment
	implements OnUpdateLayout
{
	public void updateLayout()
	{
		updateLayout(null);
	}
}
