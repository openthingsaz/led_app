package truesolution.ledpad.asign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fd.FD_BT;
import truesolution.ledpad.asign.fd.FD_DELAY;
import truesolution.ledpad.asign.fragment.adapter.MBluetoothDeviceBaseAdapter;
import truesolution.ledpad.asign.fragment.adapter.MWifiDeviceBaseAdapter;
import truesolution.ledpad.asign.fragment.str.STR_BluetoothDevice;
import truesolution.ledpad.asign.fragment.str.STR_Wifi;
import truesolution.ledpad.asign.utils.MBluetoothUtils;

/**
 * Created by TCH on 2020/09/01
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/09/01
 */

public class MUpdateActivity extends MBaseActivity {
	@BindView(R.id.tvBtnBack)
	TextView tvBtnBack;
	@BindView(R.id.rlDeviceListTop)
	RelativeLayout rlDeviceListTop;
	@BindView(R.id.tvSubTitleView)
	TextView tvSubTitleView;
	@BindView(R.id.etWifiSSID)
	EditText etWifiSSID;
	@BindView(R.id.tvTextBottomLine1)
	TextView tvTextBottomLine1;
	@BindView(R.id.etWifiPW)
	EditText etWifiPW;
	@BindView(R.id.tvTextBottomLine2)
	TextView tvTextBottomLine2;
	@BindView(R.id.tvBtnConnect)
	TextView tvBtnConnect;
	@BindView(R.id.llWifiInfo)
	LinearLayout llWifiInfo;
	@BindView(R.id.tvBtnWifiScan)
	TextView tvBtnWifiScan;
	@BindView(R.id.llScanWifiList)
	ListView llScanWifiList;
	
	private List<STR_Wifi> mList = new ArrayList<>();
	private WifiManager mWifiManager;
	private MWifiDeviceBaseAdapter mWifiDeviceBaseAdapter;
	
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firmware_update);
		ButterKnife.bind(this);
		
		mInitWifi();
	}
	
	private void mInitWifi() {
		mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		getApplicationContext().registerReceiver(mWifiScanReceiver, intentFilter);
		
		mWifiDeviceBaseAdapter = new MWifiDeviceBaseAdapter(getApplicationContext(), mList);
		llScanWifiList.setAdapter(mWifiDeviceBaseAdapter);
		llScanWifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				MDEBUG.debug("llScanDeviceList pos : " + i);
				if(mList.size() > 0) {
					STR_Wifi _str = mList.get(i);
					etWifiSSID.setText(_str.mName);
				}
			}
		});
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				MDEBUG.debug("MFragmentDeviceList mList size : " + mList.size());
				mUpdateList();
			}
		}, FD_DELAY.DATA_UPDATE);
	}
	
	BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context c, Intent intent) {
			mCancelProgress();
			
			boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
			if(success) {
				mWifiScanSuccess();
			} else {
				// scan failure handling
				mWifiScanFailure();
			}
		}
	};
	
	private void mWifiScanSuccess() {
		mList.clear();
		
		List<ScanResult> results = mWifiManager.getScanResults();
		for(int i = 0; i < results.size(); i++) {
			STR_Wifi _str_wifi = new STR_Wifi();
			ScanResult _sr = results.get(i);
			MDEBUG.debug("BSSID [" + i + "] : " + _sr.BSSID);
			MDEBUG.debug("SSID [" + i + "] : " + _sr.SSID);
			_str_wifi.mName = _sr.SSID;
			_str_wifi.mAddress = _sr.BSSID;
			mList.add(_str_wifi);
		}
		
		mWifiDeviceBaseAdapter.mUpdateDataAndView();
	}
	
	private void mWifiScanFailure() {
		MDEBUG.debug("mWifiScanFailure!!!!!!");
	}
	
	public void mUpdateList() {
		mWifiDeviceBaseAdapter.notifyDataSetChanged();
	}
	
	@OnClick({R.id.tvBtnBack, R.id.tvBtnConnect, R.id.tvBtnWifiScan})
	public void onViewClicked(View view) {
		switch(view.getId()) {
			case R.id.tvBtnBack:
				finish();
				break;
			case R.id.tvBtnConnect:
				String _ssid = etWifiSSID.getText().toString();
				String _pw = etWifiPW.getText().toString();
				
				if(_ssid.isEmpty()) {
					mShowMessageDialog(R.string.text_device_wifi_firmware_update_ssid_none, false);
					return;
				}
				
				if(_pw.isEmpty()) {
					mShowMessageDialog(R.string.text_device_wifi_firmware_update_pw_none, false);
					return;
				}
				
				if(MainActivity.mBtSpp == null || MainActivity.mBtSpp.getConnectedDeviceAddress() == null || MainActivity.mBtSpp.getConnectedDeviceAddress().isEmpty()) {
					mShowMessageDialog(R.string.text_device_ble_connect_none, false);
					return;
				}
				
				// TODO TCH Set Firmware, Get Firmware
				mSetFirmwareUpdate(_ssid, _pw);
//				mGetFirmwareUpdate(_ssid, _pw, FD_BT.STATUS_CONNECT);
				break;
			case R.id.tvBtnWifiScan:
				mShowProgress();
				boolean _is_scan_avaliable = mWifiManager.startScan();
				if(!_is_scan_avaliable) {
					mWifiScanFailure();
					mCancelProgress();
				}
				break;
		}
	}
	
	private void mSetFirmwareUpdate(String _ssid, String _pw) {
		byte[] _buf_ssid = _ssid.getBytes();
		byte[] _buf_pw = _pw.getBytes();
		byte[] _send_buf = new byte[FD_BT.SIZE_WIFI_SSID + FD_BT.SIZE_WIFI_PW + _buf_ssid.length + _buf_pw.length];
		
		int _idx_ssid = FD_BT.SIZE_WIFI_SSID;
		_send_buf[MAPP.INIT_] = (byte)_buf_ssid.length;
		System.arraycopy(_buf_ssid, 0, _send_buf, _idx_ssid, _buf_ssid.length);
		
		int _idx_pw = (FD_BT.SIZE_WIFI_SSID + _buf_ssid.length);
		_send_buf[_idx_pw] = (byte)_buf_pw.length;
		System.arraycopy(_buf_pw, 0, _send_buf, _idx_pw + MAPP.START_ALIVE, _buf_pw.length);
		
		MBluetoothUtils.transmit_data(MainActivity.mBtSpp, FD_BT.SET_FIRM_UPDATE, _send_buf);
	}
	
	private void mGetFirmwareUpdate(String _ssid, String _pw, int _status) {
		byte[] _buf_ssid = _ssid.getBytes();
		byte[] _buf_pw = _ssid.getBytes();
		byte[] _send_buf = new byte[
				FD_BT.SIZE_WIFI_SSID + FD_BT.SIZE_WIFI_PW + FD_BT.SIZE_WIFI_STATUS
				+ _buf_ssid.length + _buf_pw.length
				];
		
		int _idx_ssid = FD_BT.SIZE_WIFI_SSID;
		_send_buf[MAPP.INIT_] = (byte)_buf_ssid.length;
		System.arraycopy(_buf_ssid, 0, _send_buf, _idx_ssid, _buf_ssid.length);
		
		int _idx_pw = (FD_BT.SIZE_WIFI_SSID + _buf_ssid.length);
		_send_buf[_idx_pw] = (byte)_buf_pw.length;
		System.arraycopy(_buf_pw, 0, _send_buf, _idx_pw + MAPP.START_ALIVE, _buf_pw.length);
		
		_send_buf[_idx_pw + MAPP.START_ALIVE + _buf_pw.length] = (byte)_status;
		
		MBluetoothUtils.transmit_data(MainActivity.mBtSpp, FD_BT.SET_FIRM_UPDATE, _send_buf);
	}
}
