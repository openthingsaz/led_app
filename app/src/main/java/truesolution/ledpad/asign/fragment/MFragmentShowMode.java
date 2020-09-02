package truesolution.ledpad.asign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.fd.FD_MENU;

/**
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/07
 */

public class MFragmentShowMode extends Fragment {
	private TextView tvBtnShowModeTopMenuInfo;
	private View mView;
	private MainActivity mActivity;
	
	public MFragmentShowMode(MainActivity _activity) {
		mActivity = _activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mView == null) {
			mView = inflater.inflate(R.layout.fragment_show_mode, container, false);
			findViewsById(mView);
		}
		return mView;
	}
	
	private void findViewsById(View _view) {
		tvBtnShowModeTopMenuInfo = _view.findViewById(R.id.tvBtnShowModeTopMenuInfo);
		tvBtnShowModeTopMenuInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mActivity.mGoInfoActivity();;
			}
		});
	}
}
