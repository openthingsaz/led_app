package truesolution.ledpad.asign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by TCH
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @date 2020. 07. 01.
 */
public class InfoActivity extends MBaseActivity {
	@BindView(R.id.tvBtnBack)
	TextView tvBtnBack;
	@BindView(R.id.tvVersionInfo)
	TextView tvVersionInfo;
	@BindView(R.id.rlDeviceListTop)
	RelativeLayout rlDeviceListTop;
	@BindView(R.id.tvBtnInfoBuy)
	TextView tvBtnInfoBuy;
	@BindView(R.id.tvBtnInfoCompany)
	TextView tvBtnInfoCompany;
	@BindView(R.id.tvBtnInfoUpdate)
	TextView tvBtnInfoUpdate;
	
	private final String MARKET_URL             = "https://shopping.naver.com/home/p/index.nhn";
	private final String COMPANY_URL            = "https://google.co.kr";
	
	/**
	 * Get Error Message
	 *
	 * @return
	 */
	@Override
	public String mGetErrorMessage() {
		return null;
	}
	
	/**
	 * On Confirm
	 */
	@Override
	public void mOnConfirm() {
		finish();
	}
	
	/**
	 * On Yes
	 */
	@Override
	public void mOnYes() {
	}
	
	/**
	 * On Cancel
	 */
	@Override
	public void mOnCancel() {
	}
	
	/**
	 * Initialize
	 */
	@Override
	public void mInit() {
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		mIsPause = true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mIsPause = false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_info);
		ButterKnife.bind(this);
	}
	
	@OnClick({R.id.tvBtnBack, R.id.tvBtnInfoBuy, R.id.tvBtnInfoCompany, R.id.tvBtnInfoUpdate})
	public void onViewClicked(View view) {
		switch(view.getId()) {
			case R.id.tvBtnBack:
				finish();
				break;
			case R.id.tvBtnInfoBuy:
				mGoWebBrowser(MARKET_URL);
				break;
			case R.id.tvBtnInfoCompany:
				mGoWebBrowser(COMPANY_URL);
				break;
			case R.id.tvBtnInfoUpdate:
				mGoFirmwareUpdateActivity();
				break;
		}
	}
	
	private void mGoWebBrowser(String _url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(_url));
		startActivity(browserIntent);
	}
	
	private void mGoFirmwareUpdateActivity() {
		Intent _intent = new Intent(this, MUpdateActivity.class);
		startActivity(_intent);
	}
}