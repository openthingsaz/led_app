package truesolution.ledpad.asign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.async.CategoryAddDBDataAsyncTask;
import truesolution.ledpad.asign.async.CategoryDeleteDBDataAsyncTask;
import truesolution.ledpad.asign.async.LoadRoomDBDataAsyncTask;
import truesolution.ledpad.asign.db.MAppDatabase;
import truesolution.ledpad.asign.db.MDB;
import truesolution.ledpad.asign.db.MD_Category;
import truesolution.ledpad.asign.db.MD_Emoticon;
import truesolution.ledpad.asign.dialog.MCategoryAddDialog;
import truesolution.ledpad.asign.dialog.MEmoticonAddDialog;
import truesolution.ledpad.asign.fd.FD_BT;
import truesolution.ledpad.asign.fd.FD_DELAY;
import truesolution.ledpad.asign.fd.FD_DRAW;
import truesolution.ledpad.asign.fd.FD_FILE;
import truesolution.ledpad.asign.fd.FD_MENU;
import truesolution.ledpad.asign.fd.FD_REQ;
import truesolution.ledpad.asign.fragment.MFragmentDeviceList;
import truesolution.ledpad.asign.fragment.MFragmentDraw;
import truesolution.ledpad.asign.fragment.MFragmentGallery;
import truesolution.ledpad.asign.fragment.MFragmentShowMode;
import truesolution.ledpad.asign.fragment.MFragmentText;
import truesolution.ledpad.asign.fragment.adapter.MPagerAdapter;
import truesolution.ledpad.asign.fragment.str.STR_BluetoothDevice;
import truesolution.ledpad.asign.fragment.str.STR_GalleryCell;
import truesolution.ledpad.asign.share.MShared;
import truesolution.ledpad.asign.utils.MBluetoothUtils;
import truesolution.ledpad.asign.utils.Utils;
import truesolution.ledpad.asign.view.MSwipeViewPager;
import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends MBaseActivity {
	@BindView(R.id.tvBtnBottomMenuDeviceList)
	TextView btnBottomMenuDeviceList;
	@BindView(R.id.tvBtnBottomMenuGallery)
	TextView btnBottomMenuGallery;
	@BindView(R.id.tvBtnBottomMenuDraw)
	TextView btnBottomMenuDraw;
	@BindView(R.id.tvBtnBottomMenuShowMode)
	TextView btnBottomMenuShowMode;
	@BindView(R.id.vpMain)
	MSwipeViewPager vpMain;
	@BindView(R.id.tvBtnBottomMenuText)
	TextView tvBtnBottomMenuText;
	
	/**
	 * Directory File Path
	 */
	private String mFileDirPath;
	
	// ViewPager
	private MPagerAdapter mPagerAdapter;
	public Fragment mFragmentDeviceList, mFragmentGallery, mFragmentDraw, mFragmentText, mFragmentInfo, mFragmentShowMode;
	
	private List<MD_Category> mCategoryList = new ArrayList<>();
	private List<MD_Emoticon> mEmoticonList = new ArrayList<>();
	private List<STR_GalleryCell> mStrGalleryList = new ArrayList<>();
	
	// Bluetooth
	private BluetoothAdapter mBtAdapter;
	public static BluetoothSPP mBtSpp;
	public boolean mIsBtConnect = false;
	private List<STR_BluetoothDevice> mBtDeviceList = new ArrayList<>();
	private Set<BluetoothDevice> mPairedDevices;
	
	// Context
	private Context mContext;
	
	// Category Dialog
	public MMCategoryAddDialog mCategoryAddDialog;
	
	// Emoticon Dialog
	public MMEmoticonAddDialog mEmoticonAddDialog;
	
	// File Save Index
	public int mSaveFileIndex = MAPP.INIT_;
	
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
		MDEBUG.debug("mOnConfirm!");
	}
	
	/**
	 * On Yes
	 */
	@Override
	public void mOnYes() {
		MDEBUG.debug("mOnYes!");
		MainActivity.this.finish();
	}
	
	/**
	 * On Cancel
	 */
	@Override
	public void mOnCancel() {
		MDEBUG.debug("mOnCancel!");
	}
	
	/**
	 * Initialize
	 */
	@Override
	public void mInit() {
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (!mBtSpp.isBluetoothEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
		} else {
			if (!mBtSpp.isServiceAvailable()) {
				mBtSpp.setupService();
				mBtSpp.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
			}
		}
	}
	
	public void onDestroy() {
		super.onDestroy();
		mBtSpp.stopService();
		unregisterReceiver(mBtReceiver);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		
		MAPP.mAppDatabase = Room.databaseBuilder(this,
				MAppDatabase.class, MDB.DB_NAME_).build();
		
		mContext = this;
		
		mPagerAdapter = new MMPagerAdapter(getSupportFragmentManager(), 0, this);
		vpMain.setAdapter(mPagerAdapter);
		
		mCategoryAddDialog = new MMCategoryAddDialog(this);
		mEmoticonAddDialog = new MMEmoticonAddDialog(this);
		
		mFileDirPath =
				Environment.getExternalStorageDirectory().getAbsolutePath() + FD_FILE.MDIR_NAME;
//				FD_File.DATA_ + getPackageName() +
//						FD_File.MDIR_NAME;
		MDEBUG.debug("Environment.getExternalStorageDirectory().getAbsolutePath() : " + mFileDirPath);
		
		mBtInit();;
		
		new MInitLoadRoomDBDataAsyncTask(this, MAPP.mAppDatabase).execute();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@OnClick({R.id.tvBtnBottomMenuDeviceList, R.id.tvBtnBottomMenuGallery, R.id.tvBtnBottomMenuDraw, R.id.tvBtnBottomMenuText, R.id.tvBtnBottomMenuShowMode})
	public void onViewClicked(View view) {
		switch(view.getId()) {
			case R.id.tvBtnBottomMenuDeviceList:
				mBottomMenuReset();
				btnBottomMenuDeviceList.setSelected(true);
				vpMain.setCurrentItem(FD_MENU.DEVICE_LIST);
				break;
			case R.id.tvBtnBottomMenuGallery:
				mBottomMenuReset();
				btnBottomMenuGallery.setSelected(true);
				vpMain.setCurrentItem(FD_MENU.GALLERY_);
				break;
			case R.id.tvBtnBottomMenuDraw:
				mBottomMenuReset();
				btnBottomMenuDraw.setSelected(true);
				vpMain.setCurrentItem(FD_MENU.DRAW_);
				break;
			case R.id.tvBtnBottomMenuText:
				screen_clear();
				mBottomMenuReset();
				tvBtnBottomMenuText.setSelected(true);
				vpMain.setCurrentItem(FD_MENU.TEXT_);
				break;
			case R.id.tvBtnBottomMenuShowMode:
				mBottomMenuReset();
				btnBottomMenuShowMode.setSelected(true);
				vpMain.setCurrentItem(FD_MENU.SHOW_MODE);
				break;
		}
	}
	
	// Bottom Button Reset
	private void mBottomMenuReset() {
		btnBottomMenuDeviceList.setSelected(false);
		btnBottomMenuGallery.setSelected(false);
		btnBottomMenuDraw.setSelected(false);
		tvBtnBottomMenuText.setSelected(false);
		btnBottomMenuShowMode.setSelected(false);
	}
	
	private class MMPagerAdapter extends MPagerAdapter {
		/**
		 * @param fm       fragment manager that will interact with this adapter
		 * @param behavior determines if only current fragments are in a resumed state
		 * @param _context
		 */
		public MMPagerAdapter(@NonNull FragmentManager fm, int behavior, Context _context) {
			super(fm, behavior, _context);
		}
		
		@Override
		public Fragment getItem(int position) {
			MDEBUG.debug("getitem!!!!!!!!! : " + position);
			if(position == FD_MENU.DEVICE_LIST) {
				if(mFragmentDeviceList == null)
					mFragmentDeviceList = new MFragmentDeviceList(MainActivity.this, mBtDeviceList);
				return mFragmentDeviceList;
			} else if(position == FD_MENU.GALLERY_) {
				if(mFragmentGallery == null)
					mFragmentGallery = new MFragmentGallery(MainActivity.this, mStrGalleryList);
				return mFragmentGallery;
			} else if(position == FD_MENU.DRAW_) {
				if(mFragmentDraw == null)
					mFragmentDraw = new MFragmentDraw(MainActivity.this);
				return mFragmentDraw;
			} else if(position == FD_MENU.TEXT_) {
				if(mFragmentText == null)
					mFragmentText = new MFragmentText(MainActivity.this);
				return mFragmentText;
			} else if(position == FD_MENU.SHOW_MODE) {
				if(mFragmentShowMode == null)
					mFragmentShowMode = new MFragmentShowMode(MainActivity.this);
				return mFragmentShowMode;
			}
			
			return null;
		}
	}
	
	public void mViewPagerDrawMoveAndDataUpdate(int[] _color_data, int _w, int _h) {
		mViewPagerCurrentPage(FD_MENU.DRAW_);
		mShowProgress();
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				MDEBUG.debug("cancel mViewPagerDrawMoveAndDataUpdate!");
				mCancelProgress();
				((MFragmentDraw) mFragmentDraw).mFreeDrawView.mSetAllColor(_color_data, _w, _h);
			}
		}, FD_DELAY.DATA_UPDATE);
	}
	
	public void mViewPagerDrawMoveAndDataUpdate(List<int[]> _list, int _w, int _h) {
		mViewPagerCurrentPage(FD_MENU.DRAW_);
		mShowProgress();
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				MDEBUG.debug("cancel mViewPagerDrawMoveAndDataUpdate!");
				mCancelProgress();
//				((MFragmentDraw) mFragmentDraw).mFreeDrawView.mSetAllColor(_color_data, _w, _h);
			}
		}, FD_DELAY.DATA_UPDATE);
	}
	
	// Current Page
	public void mViewPagerCurrentPage(int _idx) {
		if(vpMain == null)
			return;
		
		mBottomMenuReset();
		switch(_idx) {
			case FD_MENU.DEVICE_LIST:
				btnBottomMenuDeviceList.setSelected(true);
				break;
			case FD_MENU.GALLERY_:
				btnBottomMenuGallery.setSelected(true);
				break;
			case FD_MENU.DRAW_:
				btnBottomMenuDraw.setSelected(true);
				break;
			case FD_MENU.TEXT_:
				tvBtnBottomMenuText.setSelected(true);
				break;
			case FD_MENU.SHOW_MODE:
				btnBottomMenuShowMode.setSelected(true);
				break;
		}
		
		vpMain.setCurrentItem(_idx);
	}
	
	public void mGoInfoActivity() {
		Intent _intent = new Intent(this, InfoActivity.class);
		startActivity(_intent);
	}
	
	private class MInitLoadRoomDBDataAsyncTask extends LoadRoomDBDataAsyncTask {
		public MInitLoadRoomDBDataAsyncTask(Activity _activity, MAppDatabase _md) {
			super(_activity, _md);
		}
		
		@Override
		public void mResult(List<MD_Category> _c_list, List<MD_Emoticon> _e_list) {
			mSetGalleryListData(_c_list, _e_list);
			
			if(MShared.strBle.mName == null)
				mViewPagerCurrentPage(FD_MENU.DEVICE_LIST);
			else {
				if(MShared.strBle.mAddress.length() > MAPP.NONE_) {
					mShowProgress();
					if(mFragmentDeviceList == null) {
						mFragmentDeviceList = new MFragmentDeviceList(MainActivity.this, mBtDeviceList);
					}
					mBtConnect(MShared.strBle.mAddress);
				}
			}
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resCode, Intent data) {
		super.onActivityResult(requestCode, resCode, data);
		
		if(resCode == Activity.RESULT_OK && data != null) {
			String _realPath;
			// SDK < API11
			if(Build.VERSION.SDK_INT < 11) {
				_realPath = Utils.getRealPathFromURI_BelowAPI11(this, data.getData());
			}
			// SDK >= 11 && SDK < 19
			else if(Build.VERSION.SDK_INT < 19) {
				_realPath = Utils.getRealPathFromURI_API11to18(this, data.getData());
			}
			// SDK > 19 (Android 4.4)
			else {
				_realPath = Utils.getRealPathFromURI_API19(this, data.getData());
			}
			
			MDEBUG.debug("onActivityResult Image Path : " + _realPath);
			try {
				File _file = new File(_realPath);
				Uri _uri = FileProvider.getUriForFile(MainActivity.this, getPackageName(), _file);
				Bitmap _bm = Utils.mGetBitmapFromUri(MainActivity.this, _file.getAbsolutePath(), _uri,
						((MFragmentDraw)mFragmentDraw).mGetCellW(), ((MFragmentDraw)mFragmentDraw).mGetCellH());
				if(_bm != null) {
					MDEBUG.debug("img w : " + _bm.getWidth());
					int[] _src = new int[_bm.getWidth() * _bm.getHeight()];
					_bm.getPixels(_src, 0, _bm.getWidth(), 0, 0, _bm.getWidth(), _bm.getHeight());
					
					((MFragmentDraw)mFragmentDraw).mUpdateColors(_src, _bm.getWidth(), _bm.getHeight());
				}
			} catch(Exception e) {
				MDEBUG.debug("onActivityResult error : " + e.toString());
			}
		}
	}
	
	public void mGoColorSettingDialog() {
		final MFragmentDraw _mfd = (MFragmentDraw) mFragmentDraw;
		AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, _mfd.mPenColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
			}
			
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				_mfd.mSetPenColor(color);
			}
		});
		colorPicker.show();
	}
	
	public void mShowPreviewDialog(int[][] _data, int _w, int _h) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_preview_dialog, null);
		ImageView _iv = view.findViewById(R.id.ivPreviewImg);
		int[] _colors = new int[_w * _h];
		for(int y = 0; y < _h; y++) {
			for(int x = 0; x < _w; x++) {
				_colors[(y * _h) + x] = _data[x][y];
			}
		}
		MDEBUG.debug("_data.length : " + _data.length);
		Bitmap _bm = Bitmap.createBitmap(_colors, _w, _h, Bitmap.Config.ARGB_8888);
		_iv.setImageBitmap(_bm);
		builder.setView(view);
		
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
	}
	
	public void mShowPreviewDialog(int[] _data, int _w, int _h) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_preview_dialog, null);
		ImageView _iv = view.findViewById(R.id.ivPreviewImg);
		MDEBUG.debug("_data.length : " + _data.length);
		Bitmap _bm = Bitmap.createBitmap(_data, _w, _h, Bitmap.Config.ARGB_8888);
		_iv.setImageBitmap(_bm);
		builder.setView(view);
		
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
	}
	
	public void mKeyboardHide(EditText _et) {
		InputMethodManager inputMethodManager =
				(InputMethodManager) getSystemService(
						Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(_et.getWindowToken(), 0);
	}
	
	@Override
	public void onBackPressed() {
		mShowMessageDialog(R.string.pop_msg_destroy, true);
	}
	
	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mBtReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			MDEBUG.debug("BroadcastReceiver mReceiver!");
			String action = intent.getAction();
			// When discovery finds a device
			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				MDEBUG.debug("ACTION_FOUND!!!!");
				// If it's already paired, skip it, because it's been listed already
				if(device.getBondState() != BluetoothDevice.BOND_BONDED) {
					MDEBUG.debug("BOND_BONDED name : " + device.getName() + ", address : " + device.getAddress());
					
					STR_BluetoothDevice _str = new STR_BluetoothDevice();
					_str.mName = device.getName();
					_str.mAddress = device.getAddress();
					boolean _is_update = true;
					for(int i = 0; i < mBtDeviceList.size(); i++) {
						if(mBtDeviceList.get(i).mAddress.equals(_str.mAddress)) {
							_is_update = false;
							return;
						}
					}
					
					if(_is_update)
						mBtDeviceList.add(_str);
				}
				
				// When discovery is finished, change the Activity title
			} else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				MDEBUG.debug("ACTION_DISCOVERY_FINISHED!!!!");
//				mPairedDevices = mBtAdapter.get();
//				mBtDeviceList.clear();
//
//				if(mPairedDevices.size() > 0) {
//					for(BluetoothDevice device : mPairedDevices) {
//						STR_BluetoothDevice _str = new STR_BluetoothDevice();
//						_str.mName = device.getName();
//						_str.mAddress = device.getAddress();
//						MDEBUG.debug("_str.mName : " + _str.mName);
//						mBtDeviceList.add(_str);
//					}
//				}
				
				if(mFragmentDeviceList != null) {
					((MFragmentDeviceList) mFragmentDeviceList).mUpdateList();
				}
				
				mCancelProgress();
			}
		}
	};
	
	public void mBtScan() {
		mShowProgress();
		mBtDeviceList.clear();
		if(mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		mBtAdapter.startDiscovery();
		
		// Scan Timeout
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mBtAdapter.cancelDiscovery();
			}
		}, FD_DELAY.BT_SCAN_TIMEOUT);
	}
	
	public void mBtInit() {
		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mBtReceiver, filter);
		
		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mBtReceiver, filter);
		
		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		// Get a set of currently paired devices
		mPairedDevices = mBtAdapter.getBondedDevices();
		// If there are paired devices, add each one to the ArrayAdapter
		if(mPairedDevices.size() > 0) {
			for(BluetoothDevice device : mPairedDevices) {
				STR_BluetoothDevice _str = new STR_BluetoothDevice();
				_str.mName = device.getName();
				_str.mAddress = device.getAddress();
				mBtDeviceList.add(_str);
			}
		}
		
		mBtSpp = new BluetoothSPP(this); //Initializing
		mBtSpp.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
			public void onDataReceived(byte[] data, String message) {
				MDEBUG.debug("onDataReceived!");
			}
		});
		
		mBtSpp.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
			public void onDeviceConnected(String name, String address) {
				MDEBUG.debug("onDeviceConnected name : " + name + ", address : " + address);
				mHandler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
	                     mIsBtConnect = true;
	                     MShared.mShared.mSetDeviceNameAddress(name, address);
	                     ((MFragmentDeviceList)mFragmentDeviceList).mSetDeviceConnectComplete(name, address);
	                     MDEBUG.debug("onDeviceConnected Data Save!");
	                     mCancelProgress();
                     }
                 }, FD_DELAY.BLUETOOTH_CONNECT_WAIT
				);
				screen_clear();
			}
			
			public void onDeviceDisconnected() { //연결해제
				mIsBtConnect = false;
				MDEBUG.debug("onDeviceDisconnected!");
			}
			
			public void onDeviceConnectionFailed() { //연결실패
				mIsBtConnect = false;
				MDEBUG.debug("onDeviceConnectionFailed!");
				mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							mShowMessageDialog(R.string.txt_bt_connect_fail, false);
							mViewPagerCurrentPage(FD_MENU.DEVICE_LIST);
							MDEBUG.debug("cancel onDeviceConnectionFailed!");
							mCancelProgress();
						}
					}, FD_DELAY.MIN_DISPLAY
				);
			}
		});
	}
	
	public void mBtConnect(String _address) {
		mShowProgress();
		mBtSpp.connect(_address);
	}
	
	public void screen_clear() {
		MBluetoothUtils.transmit_data(mBtSpp, FD_BT.SET_SCR_CLEAR);
	}
	
	private class MSendStepByStepDotDataAsyncTask extends AsyncTask<Void, Void, Void> {
		int mIDX;
		int mNextIdx;
		int[] mBuf;
		int mW, mH;
		MBluetoothUtils.SendDataClass mSendData;
		public MSendStepByStepDotDataAsyncTask(int _w, int _h, int _idx, int[] _buf) {
			mW = _w;
			mH = _h;
			mIDX = _idx;
			mBuf = _buf;
			mNextIdx = _idx;
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			int _max = mW * mH;
			for(int i = mIDX; i < _max; i++) {
				if(Color.alpha(mBuf[i]) > 0 && mBuf[i] != Color.BLACK) {
					mSendData = new MBluetoothUtils.SendDataClass();
					mSendData.x = (byte) (i % mW);
					mSendData.y = (byte) (i / mW);
					mSendData.red = (byte) Color.red(mBuf[i]);
					mSendData.green = (byte) Color.green(mBuf[i]);
					mSendData.blue = (byte) Color.blue(mBuf[i]);
					
//					MDEBUG.debug("Color.red(mBuf[" + i + "] : " + Color.red(mBuf[i]) + ", Color.green(mBuf[" + i + "]) : " + Color.green(mBuf[i]) + ", Color.blue(mBuf[" + i + "]) : " + Color.blue(mBuf[i]));
//					MDEBUG.debug("mSendData.red : " + mSendData.red + ", mSendData.green : " + mSendData.green + ", mSendData.blue : " + mSendData.blue);
					
					mSendData.color = mBuf[i];
					
					mNextIdx = (i + 1);
					break;
				}
			}
			return null;
		}
		
		
		@Override
		public void onPostExecute(Void _void) {
			if(mSendData != null && mIsBtConnect) {
				int _color = (Color.rgb(Color.red(mSendData.color), Color.green(mSendData.color), Color.blue(mSendData.color)));
				MBluetoothUtils.send_single_dot(mBtSpp, 0, FD_BT.SET_DRAW, mSendData.x, mSendData.y, _color);
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						new MSendStepByStepDotDataAsyncTask(mW, mH, mNextIdx, mBuf).execute();
					}
				}, FD_DELAY.DATA_UPDATE);
			} else {
				MDEBUG.debug("cancel onPostExecute");
				mCancelProgress();
			}
		}
	}
	
	public void mSetAllDotDataSend(int _w, int _h, int[][] _in_colors) {
		// mShowProgress();
		MBluetoothUtils.send_all_dot(mBtSpp, _w, _h, _in_colors);
		
//		int[] _colors = new int[_w * _h];
//		for(int y = 0; y < _h; y++) {
//			for(int x = 0; x < _w; x++) {
//				_colors[(y * _w) + x] = _in_colors[x][y];
//			}
//		}
//		new MSendStepByStepDotDataAsyncTask(_w, _h, 0, _colors).execute();
	}
	
	private int mMaxCount = 30;
	public class MSendAllDotDataForDeviceAsyncTask extends AsyncTask<Void, Void, Void> {
		MBluetoothUtils.SendDataClass[] mCheckData = new MBluetoothUtils.SendDataClass[mMaxCount];
		int[][] mAllData;
		int mCount;
		int mW, mH, mSX, mSY, mEX, mEY;
		
		public MSendAllDotDataForDeviceAsyncTask(int _w, int _h, int[][] _data, int _s_x, int _s_y) {
			mCount = 0;
			mW = _w;
			mH = _h;
			mSX = _s_x;
			mSY = _s_y;
			mAllData = _data;
			
			MDEBUG.debug("Con In _w : " + _w + ", _h : " + _h);
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			int _total = mW * mH;
			int _idx = 0;
			for(int x = mSX; x < mW; x++) {
				for(int y = 0; y < mH; y++) {
					MDEBUG.debug("mAllData[" + x + "][" + y + "] : " + mAllData[x][y]);
					if(Color.alpha(mAllData[x][y]) > 0 && mAllData[x][y] != Color.BLACK) {
						mCheckData[mCount] = new MBluetoothUtils.SendDataClass();
						mCheckData[mCount].x = (byte) x;
						mCheckData[mCount].y = (byte) y;
						mCheckData[mCount].red = (byte) Color.red(mAllData[x][y]);
						mCheckData[mCount].green = (byte) Color.green(mAllData[x][y]);
						mCheckData[mCount].blue = (byte) Color.blue(mAllData[x][y]);
						mCheckData[mCount].color = mAllData[x][y];
						mCount++;
					}
					
					if(mCount == mMaxCount) {
						mEY = y;
						break;
					}
				}
				if(mCount == mMaxCount) {
					mEX = x;
					break;
				}
			}
			
			if(mCount < mMaxCount) {
				mEX = mW - 1;
				mEY = mH - 1;
			}
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			byte[] _send_buf = new byte[mCount * 5];
			int _buf_idx = 0;
			
			MDEBUG.debug("mEX : " + mEX + ", mEY : " + mEY + ", mCount : " + mCount);
			
			for(int i = 0; i < mCount; i++) {
//				MDEBUG.debug("mCheckData[" + i + "].x_pos : " + mCheckData[i].x_pos + ", mCheckData[" + i + "].y_pos : " + mCheckData[i].y_pos);
				_send_buf[_buf_idx++] = mCheckData[i].x;
				_send_buf[_buf_idx++] = mCheckData[i].y;
				_send_buf[_buf_idx++] = (byte) mCheckData[i].red;
				_send_buf[_buf_idx++] = (byte) mCheckData[i].green;
				_send_buf[_buf_idx++] = (byte) mCheckData[i].blue;
			}
			
			MBluetoothUtils.transmit_data(mBtSpp, FD_BT.SET_DRAW, _send_buf);
			if(mEX < (mW - 1) || mEY < (mH - 1)) {
				final int _x = mEX;
				final int _y = mEY;
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						new MSendAllDotDataForDeviceAsyncTask(mW, mH, mAllData, _x, _y).execute();
					}
				}, FD_DELAY.DATA_UPDATE);
			} else {
				MDEBUG.debug("MSendAllDotDataForDeviceAsyncTask mEX : onPostExecute");
				mCancelProgress();
			}
		}
	}
	
	public class MMCategoryAddDialog extends MCategoryAddDialog {
		public MMCategoryAddDialog(MainActivity _activity) {
			super(_activity);
		}
		
		@Override
		public void mBtnYes(String _name) {
			MDEBUG.debug("MMCategoryAddDialog mBtnYes _name : " + _name);
			
			MD_Category _md = new MD_Category();
			_md.mName = _name;
			_md.mResID = R.drawable.cate_default;
			_md.mSubIdx = MAPP.ERROR_;
			_md.mIsImgDefault = true;
			
			new MCategoryAddDBDataAsyncTask(MainActivity.this, MAPP.mAppDatabase, _md).execute();
		}
		
		@Override
		public void mBtnNo() {
			MDEBUG.debug("mBtnNo!");
		}
	}
	
	private class MCategoryAddDBDataAsyncTask extends CategoryAddDBDataAsyncTask {
		public MCategoryAddDBDataAsyncTask(Activity _activity, MAppDatabase _mad, MD_Category _str) {
			super(_activity, _mad, _str);
			mShowProgress();
		}
		
		@Override
		public void mResult() {
			new MLoadRoomDBDataAsyncTask(MainActivity.this, MAPP.mAppDatabase).execute();
		}
	}
	
	public void mDeleteCategory(int _idx) {
		MD_Category _str_category = null;
		for(int i = 0; i < mCategoryList.size(); i++) {
			MD_Category _str = mCategoryList.get(i);
			if(_idx == _str.idx_) {
				_str_category = _str;
				break;
			}
		}
		
		if(_str_category != null)
			new MCategoryDeleteDBDataAsyncTask(MAPP.mAppDatabase, _str_category).execute();
	}
	
	private class MCategoryDeleteDBDataAsyncTask extends CategoryDeleteDBDataAsyncTask {
		public MCategoryDeleteDBDataAsyncTask(MAppDatabase _mad, MD_Category _str) {
			super(_mad, _str);
			mShowProgress();
		}
		
		@Override
		public void mResult() {
			new MLoadRoomDBDataAsyncTask(MainActivity.this, MAPP.mAppDatabase).execute();
		}
	}
	
	private class MLoadRoomDBDataAsyncTask extends LoadRoomDBDataAsyncTask {
		public MLoadRoomDBDataAsyncTask(Activity _activity, MAppDatabase _md) {
			super(_activity, _md);
			mCategoryList.clear();
			mEmoticonList.clear();
			mStrGalleryList.clear();
		}
		
		@Override
		public void mResult(List<MD_Category> _c_list, List<MD_Emoticon> _e_list) {
			mCancelProgress();
			mSetGalleryListData(_c_list, _e_list);
			((MFragmentGallery)mFragmentGallery).mRefreshGallery();
		}
	}
	
	public class MMEmoticonAddDialog extends MEmoticonAddDialog {
		public MMEmoticonAddDialog(MainActivity _activity) {
			super(_activity);
		}
		
		@Override
		public void mBtnYes(String _name, int _idx) {
			MDEBUG.debug("MMEmoticonAddDialog mBtnYes _name : " + _name + ", idx : " + _idx);
			
			mShowProgress();
			mSaveFileIndex = MAPP.INIT_;
			new MCreateFolderAndFileSaveAsyncTask(_name, "", _idx).execute();
		}
		
		@Override
		public void mBtnNo() {
			MDEBUG.debug("MMEmoticonAddDialog mBtnNo!");
		}
	}
	
	private void mSetGalleryListData(List<MD_Category> _c_list, List<MD_Emoticon> _e_list) {
		mCategoryList.addAll(_c_list);
		mEmoticonList.addAll(_e_list);
		
		// Set Category Data
		for(int i = 0; i < mCategoryList.size(); i++) {
			STR_GalleryCell _str = new STR_GalleryCell();
			_str.mIsCategory = true;
			MD_Category _md = mCategoryList.get(i);
			_str.mSetCategoryData(_md.idx_, _md.mSubIdx, _md.mName, "", _md.mResID);
			mStrGalleryList.add(_str);
		}
		
		// Set Emoticon Data
		for(int i = 0; i < mEmoticonList.size(); i++) {
			STR_GalleryCell _str = new STR_GalleryCell();
			_str.mIsCategory = false;
			MD_Emoticon _md = mEmoticonList.get(i);
			_str.mSetEmoticonData(_md.idx_, _md.mName,
					_md.mCatergoryIdx, _md.mCatergoryName,
					_md.mIsOneEmoticon, _md.mEmoticonFilesPath, _md.mImageResID,
					_md.mIsLocalData, _md.mIsFavorite, _md.mDescription, _md.mDate);
			mStrGalleryList.add(_str);
		}
	}
	
	public void mShowEmoticonAddDialog() {
		mEmoticonAddDialog.mShowDialog(this, mCategoryList);
	}
	
	public void mGoPhoneGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(FD_DRAW.PHONE_GALLERY_TYPE);
		startActivityForResult(intent, FD_REQ.REQ_PHONE_GALLERY);
	}
	
	/**
	 * Create Folder & File
	 */
	private class MCreateFolderAndFileSaveAsyncTask extends AsyncTask<Void, Void, Void> {
		String mName;
		String mFileName = "";
		String mFilesName = "";
		int mCategoryIdx = MAPP.ERROR_;
		public MCreateFolderAndFileSaveAsyncTask(String _name, String _files_name, int _category_idx) {
			mName = _name;
			mFilesName = _files_name;
			mCategoryIdx = _category_idx;
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			File _dir = new File(mFileDirPath);
			if(!_dir.exists())
				_dir.mkdirs();
			
			MFragmentDraw _mfd = ((MFragmentDraw)mFragmentDraw);
			int _w = _mfd.mGetCellW();
			int _h = _mfd.mGetCellH();
			int _ea = _mfd.mGetMatrixEA();
			MDEBUG.debug("_w : " + _w + ", _h : " + _h + ", _ea : " + _ea);
			MDEBUG.debug("mFileDirPath : " + mFileDirPath);
			
			@SuppressLint("WrongThread")
			int[][] _ar = _mfd.mFreeDrawView.mGetColors(mSaveFileIndex);
			Bitmap _bm = Bitmap.createBitmap(_w, _h, Bitmap.Config.ARGB_8888);
			MDEBUG.debug("ar 1 : " + _ar.length + ", 2 : " + _ar[0].length);
			for(int y = 0; y < _h; y++) {
				for(int x = 0; x < _w; x++) {
					_bm.setPixel(x, y, _ar[x][y]);
				}
			}
			
			mFileName = (System.currentTimeMillis() + FD_DRAW.MSAVE_FILE_FORMAT_STR);
			File _file = new File(mFileDirPath + "/" + mFileName);
			OutputStream _out = null;
			
			try {
				_file.createNewFile();
				_out = new FileOutputStream(_file);
				_bm.compress(FD_DRAW.MSAVE_FILE_FORMAT, FD_DRAW.MSAVE_FILE_QUALITY, _out);
			} catch (Exception e) {
				MDEBUG.debug("1. MCreateFolderAsyncTask error : " + e.toString());
			} finally {
				try {
					_out.close();
				} catch (IOException e) {
					MDEBUG.debug("2. MCreateFolderAsyncTask error : " + e.toString());
				}
			}
			
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mSaveFileIndex++;
			
			MFragmentDraw _mfd = ((MFragmentDraw)mFragmentDraw);
			MDEBUG.debug("mSaveFileIndex : " + mSaveFileIndex + ", _mfd.mGetMatrixEA() : " + _mfd.mGetMatrixEA());
			if(mSaveFileIndex < _mfd.mGetMatrixEA()) {
				mFilesName += (mFileName + FD_DRAW.SPLIT_TOKEN);
				new MCreateFolderAndFileSaveAsyncTask(mName, mFilesName, mCategoryIdx).execute();
			} else {
				new MDrawFileInfoInRoomAsyncTask().execute();
			}
		}
	}
	
	private class MDrawFileInfoInRoomAsyncTask extends AsyncTask<Void, Void, Void> {
		public MDrawFileInfoInRoomAsyncTask() {
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mCancelProgress();
		}
	}
}