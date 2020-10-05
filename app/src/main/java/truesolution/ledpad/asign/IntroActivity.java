package truesolution.ledpad.asign;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fd.FD_DELAY;
import truesolution.ledpad.asign.fd.FD_DRAW;
import truesolution.ledpad.asign.fd.FD_REQ;
import truesolution.ledpad.asign.share.MShared;

/**
 * Created by TCH
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @date 2020. 07. 01.
 */
public class IntroActivity extends MBaseActivity {
	/**
	 * The M tv center.
	 */
	@BindView(R.id.mTVCenter)
	TextView mTVCenter;
	/**
	 * The M intro logo.
	 */
	@BindView(R.id.mIntroLogo)
	ImageView mIntroLogo;
	/**
	 * The M tv version.
	 */
	@BindView(R.id.mTVVersion)
	TextView mTVVersion;
	/**
	 * The Tv cover l.
	 */
	@BindView(R.id.tvCoverL)
	TextView tvCoverL;
	/**
	 * The Tv cover e.
	 */
	@BindView(R.id.tvCoverE)
	TextView tvCoverE;
	/**
	 * The Tv cover d.
	 */
	@BindView(R.id.tvCoverD)
	TextView tvCoverD;
	/**
	 * The Tv cover pad.
	 */
	@BindView(R.id.tvCoverPAD)
	TextView tvCoverPAD;
	
	private int mCntCoverL, mCntCoverE, mCntCoverD, mCntCoverPAD;
	
	private boolean mIsRun = true;
	private boolean mIsHold = false;
	
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
		mIsHold = true;
		mIsPause = true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mIsHold = false;
		mIsPause = false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		ButterKnife.bind(this);
		
		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(
					this.getPackageName(), 0);
		} catch(PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		int _versionCode = pInfo.versionCode;
		String _versionName = getResources().getString(R.string.ver_code_name) + pInfo.versionName;
		
		mTVVersion.setText(_versionName);
		
		mCheckBluetoothPermission();
		
		MShared.mShared.mLoadDeviceInfo();
	}
	
	@Override
	public void onBackPressed() {
	}
	
	/**
	 * Go Main Activity
	 */
	public void mGoMainActivity() {
		finish();
		Intent _intent = new Intent(this, MainActivity.class);
		startActivity(_intent);
	}
	
	@Override
	public void onActivityResult(int _req_code, int _result_code, Intent _data) {
		super.onActivityResult(_req_code, _result_code, _data);
		
		if(_req_code == FD_REQ.REQ_ENABLE_BT) {
			mCheckBluetoothPermission();
		}
	}
	
	private void mCheckBluetoothPermission() {
		BluetoothAdapter _bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(_bluetoothAdapter == null) {
			mShowMessageDialog(R.string.notice_bluetooth_on, false);
		} else if(!_bluetoothAdapter.isEnabled()) {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent, FD_REQ.REQ_ENABLE_BT);
				}
			}, FD_DELAY.MIN_DISPLAY);
		} else {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
//					mGoMainActivity();
					BGAlphaThread _thread = new BGAlphaThread();
					_thread.start();
				}
			}, FD_DELAY.INTRO_WAIT);
		}
	}
	
	private class BGAlphaThread extends Thread implements Runnable {
		@Override
		public void run() {
			while(mIsRun) {
				try {
					if(mIsHold) {
					} else {
						if(mCntCoverL < FD_DRAW.MCOLOR8)
							mCntCoverL++;
						else if(mCntCoverE < FD_DRAW.MCOLOR8)
							mCntCoverE++;
						else if(mCntCoverD < FD_DRAW.MCOLOR8)
							mCntCoverD++;
						else if(mCntCoverPAD < FD_DRAW.MCOLOR8)
							mCntCoverPAD++;
						else {
							mIsRun = false;
							mHandler.postDelayed(new Runnable() {
								@Override
								public void run() {
									mGoMainActivity();
								}
							}, FD_DELAY.MIN_DISPLAY);
							
							break;
						}
						
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								tvCoverL.setAlpha((FD_DRAW.MCOLOR8 - mCntCoverL) / (float) FD_DRAW.MCOLOR8);
								tvCoverE.setAlpha((FD_DRAW.MCOLOR8 - mCntCoverE) / (float) FD_DRAW.MCOLOR8);
								tvCoverD.setAlpha((FD_DRAW.MCOLOR8 - mCntCoverD) / (float) FD_DRAW.MCOLOR8);
								tvCoverPAD.setAlpha((FD_DRAW.MCOLOR8 - mCntCoverPAD) / (float) FD_DRAW.MCOLOR8);
							}
						}, MAPP.START_ALIVE);
					}
					
					Thread.sleep(FD_DELAY.MIN_DATA);
				} catch(Exception e) {
					MDEBUG.debug("BGAlphaThread error : " + e.toString());
				}
			}
		}
	}
}