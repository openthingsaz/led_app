package truesolution.ledpad.asign.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.fragment.str.STR_GalleryCell;

/**
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/07
 */
public class MFragmentInfo extends Fragment implements View.OnClickListener {
	private View mView;
	private Activity mActivity;
	
	private TextView tvBtnInfoBuy, tvBtnInfoCompany, tvBtnInfoUpdate;
	
	/**
	 * Instantiates a new M fragment info.
	 *
	 * @param _activity the activity
	 */
	public MFragmentInfo(Activity _activity) {
		mActivity = _activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_info, container, false);
		mFindView(mView);
		return mView;
	}
	
	private void mFindView(View _view) {
		tvBtnInfoBuy = _view.findViewById(R.id.tvBtnInfoBuy);
		tvBtnInfoBuy.setOnClickListener(this);
		tvBtnInfoCompany = _view.findViewById(R.id.tvBtnInfoCompany);
		tvBtnInfoCompany.setOnClickListener(this);
		tvBtnInfoUpdate = _view.findViewById(R.id.tvBtnInfoUpdate);
		tvBtnInfoUpdate.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		int _id = view.getId();
		
		if(_id == R.id.tvBtnInfoBuy) {
		
		} else if(_id == R.id.tvBtnInfoCompany) {
		
		} else if(_id == R.id.tvBtnInfoUpdate) {
		
		}
	}
	
	private void mGoWebBrowser(String _url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(_url));
		startActivity(browserIntent);
	}
}
