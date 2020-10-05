package truesolution.ledpad.asign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.fd.FD_DELAY;
import truesolution.ledpad.asign.fragment.adapter.MBluetoothDeviceBaseAdapter;
import truesolution.ledpad.asign.fragment.str.STR_BluetoothDevice;
import truesolution.ledpad.asign.share.MShared;

/**
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/07
 */
public class MFragmentDeviceList extends Fragment {
	private TextView tvBtnDeviceListTopMenuInfo;
	private View mView;
	private MainActivity mActivity;
	private List<STR_BluetoothDevice> mList;
	/**
	 * The M address.
	 */
	public String mAddress = "";
	
	private TextView tvBtnBtScan, tvBtDeviceNone, tvBtName, tvBtAddress, tvBtnCheck;
	private ListView llScanDeviceList;
	private MBluetoothDeviceBaseAdapter mBluetoothDeviceBaseAdapter;
	private LinearLayout llDeviceInfo;
	
	/**
	 * Instantiates a new M fragment device list.
	 *
	 * @param _activity the activity
	 * @param _list     the list
	 */
	public MFragmentDeviceList(MainActivity _activity, List<STR_BluetoothDevice> _list) {
		mActivity = _activity;
		mList = _list;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mView == null) {
			mView = inflater.inflate(R.layout.fragment_device_list, container, false);
			findViewsById(mView);
		}
		return mView;
	}
	
	private void findViewsById(View _view) {
		tvBtnDeviceListTopMenuInfo = _view.findViewById(R.id.tvBtnDeviceListTopMenuInfo);
		tvBtnDeviceListTopMenuInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mActivity.mGoInfoActivity();
			}
		});
		
		tvBtnBtScan = _view.findViewById(R.id.tvBtnBleScan);
		tvBtnBtScan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mActivity.mBtScan();
			}
		});
		
		llScanDeviceList = _view.findViewById(R.id.llScanDeviceList);
		mBluetoothDeviceBaseAdapter = new MBluetoothDeviceBaseAdapter(mActivity.getApplicationContext(), mList);
		llScanDeviceList.setAdapter(mBluetoothDeviceBaseAdapter);
		llScanDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				MDEBUG.debug("llScanDeviceList pos : " + i);
				if(mList.size() > 0) {
					STR_BluetoothDevice _str = mList.get(i);
					mActivity.mBtConnect(_str.mAddress);
				}
			}
		});
		
		llDeviceInfo = _view.findViewById(R.id.llDeviceInfo);
		tvBtDeviceNone = _view.findViewById(R.id.tvBtDeviceNone);
		tvBtName = _view.findViewById(R.id.tvBtName);
		tvBtAddress = _view.findViewById(R.id.tvBtAddress);
		tvBtnCheck = _view.findViewById(R.id.tvBtnCheck);
		boolean _auto_st = MShared.mShared.mSP.getBoolean(MShared.KEY_BLE_AUTO_LOGIN, true);
		tvBtnCheck.setSelected(_auto_st);
		
		tvBtnCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				boolean _st = !tvBtnCheck.isSelected();
				tvBtnCheck.setSelected(_st);
				MShared.mShared.mSetDeviceInfo(MShared.KEY_BLE_AUTO_LOGIN, _st);
			}
		});
		
		mActivity.mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				MDEBUG.debug("MFragmentDeviceList mList size : " + mList.size());
				mUpdateList();
			}
		}, FD_DELAY.DATA_UPDATE);
//		mActivity.mShowProgress();
	}
	
	/**
	 * M update list.
	 */
	public void mUpdateList() {
		mBluetoothDeviceBaseAdapter.notifyDataSetChanged();
	}
	
	/**
	 * M set device connect complete.
	 *
	 * @param _name    the name
	 * @param _address the address
	 */
	public void mSetDeviceConnectComplete(String _name, String _address) {
		mAddress = _address;
		tvBtName.setText(_name);
		tvBtAddress.setText(_address);
		llDeviceInfo.setVisibility(View.VISIBLE);
		tvBtDeviceNone.setVisibility(View.INVISIBLE);
		
		if(_address != null) {
			for(int i = 0; i < mList.size(); i++) {
				String __address = mList.get(i).mAddress;
				if(__address != null && _address.equals(__address)) {
					mList.remove(i);
					mUpdateList();
					break;
				}
			}
		}
	}
	
	/**
	 * M set disconnect device.
	 */
	public void mSetDisconnectDevice() {
		llDeviceInfo.setVisibility(View.INVISIBLE);
		tvBtDeviceNone.setVisibility(View.VISIBLE);
	}
}
