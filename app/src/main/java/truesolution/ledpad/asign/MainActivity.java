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
import truesolution.ledpad.asign.async.EmoticonDeleteDBDataAsyncTask;
import truesolution.ledpad.asign.async.LoadRoomDBDataAsyncTask;
import truesolution.ledpad.asign.db.MAppDatabase;
import truesolution.ledpad.asign.db.MDB;
import truesolution.ledpad.asign.db.MDB_FD;
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

/**
 * The type Main activity.
 */
public class MainActivity extends MBaseActivity {
	/**
	 * The Btn bottom menu device list.
	 */
	@BindView(R.id.tvBtnBottomMenuDeviceList)
	TextView btnBottomMenuDeviceList;
	/**
	 * The Btn bottom menu gallery.
	 */
	@BindView(R.id.tvBtnBottomMenuGallery)
	TextView btnBottomMenuGallery;
	/**
	 * The Btn bottom menu draw.
	 */
	@BindView(R.id.tvBtnBottomMenuDraw)
	TextView btnBottomMenuDraw;
	/**
	 * The Btn bottom menu show mode.
	 */
	@BindView(R.id.tvBtnBottomMenuShowMode)
	TextView btnBottomMenuShowMode;
	/**
	 * The Vp main.
	 */
	@BindView(R.id.vpMain)
	MSwipeViewPager vpMain;
	/**
	 * The Tv btn bottom menu text.
	 */
	@BindView(R.id.tvBtnBottomMenuText)
	TextView tvBtnBottomMenuText;
	
	/**
	 * Directory File Path
	 */
	private String mFileDirPath;
	
	// ViewPager
	private MPagerAdapter mPagerAdapter;
	/**
	 * The M fragment device list.
	 */
	public Fragment mFragmentDeviceList,
	/**
	 * The M fragment gallery.
	 */
	mFragmentGallery,
	/**
	 * The M fragment draw.
	 */
	mFragmentDraw,
	/**
	 * The M fragment text.
	 */
	mFragmentText,
	/**
	 * The M fragment info.
	 */
	mFragmentInfo,
	/**
	 * The M fragment show mode.
	 */
	mFragmentShowMode;
	
	private List<MD_Category> mCategoryList = new ArrayList<>();
	private List<MD_Emoticon> mEmoticonList = new ArrayList<>();
	private List<STR_GalleryCell> mStrGalleryList = new ArrayList<>();
	
	// Bluetooth
	private BluetoothAdapter mBtAdapter;
	/**
	 * The constant mBtSpp.
	 */
	public static BluetoothSPP mBtSpp;
	/**
	 * The M is bt connect.
	 */
	public boolean mIsBtConnect = false;
	private List<STR_BluetoothDevice> mBtDeviceList = new ArrayList<>();
	private Set<BluetoothDevice> mPairedDevices;
	
	// Context
	private Context mContext;
	
	/**
	 * The M category add dialog.
	 */
// Category Dialog
	public MMCategoryAddDialog mCategoryAddDialog;
	
	/**
	 * The M emoticon add dialog.
	 */
// Emoticon Dialog
	public MMEmoticonAddDialog mEmoticonAddDialog;
	
	/**
	 * The M save file index.
	 */
// File Save Index
	public int mSaveFileIndex = MAPP.INIT_;
	/**
	 * The M save emoticon file list.
	 */
	public ArrayList<String> mSaveEmoticonFileList = new ArrayList<>();
	
	/**
	 * The constant POPUP_COLOR_SETTING_DRAW.
	 */
	public static final int POPUP_COLOR_SETTING_DRAW              = 0;
	/**
	 * The constant POPUP_COLOR_SETTING_TEXT.
	 */
	public static final int POPUP_COLOR_SETTING_TEXT              = 1;
	/**
	 * The constant POPUP_COLOR_SETTING_TEXT_BG.
	 */
	public static final int POPUP_COLOR_SETTING_TEXT_BG           = 2;
	
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mIsFinish = true;
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
		
		MShared.mGetInstance(getApplication());
		MShared.mShared.mLoadDeviceInfo();
		
		mPagerAdapter = new MMPagerAdapter(getSupportFragmentManager(), 0, this);
		vpMain.setAdapter(mPagerAdapter);
		
		mCategoryAddDialog = new MMCategoryAddDialog(this);
		mEmoticonAddDialog = new MMEmoticonAddDialog(this);
		
		mFileDirPath =
				Environment.getExternalStorageDirectory().getAbsolutePath() + FD_FILE.MDIR_NAME;
//				FD_File.DATA_ + getPackageName() +
//						FD_File.MDIR_NAME;
		MDEBUG.debug("Environment.getExternalStorageDirectory().getAbsolutePath() : " + mFileDirPath);
		
		mBtInit();
		
		new MInitLoadRoomDBDataAsyncTask(this, MAPP.mAppDatabase).execute();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	/**
	 * On view clicked.
	 *
	 * @param view the view
	 */
	@OnClick({R.id.tvBtnBottomMenuDeviceList, R.id.tvBtnBottomMenuGallery, R.id.tvBtnBottomMenuDraw, R.id.tvBtnBottomMenuText, R.id.tvBtnBottomMenuShowMode})
	public void onViewClicked(View view) {
		switch(view.getId()) {
			case R.id.tvBtnBottomMenuDeviceList:
				mBottomMenuReset();
				btnBottomMenuDeviceList.setSelected(true);
				vpMain.setCurrentItem(FD_MENU.DEVICE_LIST);
				break;
			case R.id.tvBtnBottomMenuGallery:
				screen_clear();
				mBottomMenuReset();
				btnBottomMenuGallery.setSelected(true);
				vpMain.setCurrentItem(FD_MENU.GALLERY_);
				break;
			case R.id.tvBtnBottomMenuDraw:
				screen_clear();
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
		 * Instantiates a new Mm pager adapter.
		 *
		 * @param fm       fragment manager that will interact with this adapter
		 * @param behavior determines if only current fragments are in a resumed state
		 * @param _context the context
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
					mFragmentGallery = new MFragmentGallery(MainActivity.this, mFileDirPath, mStrGalleryList);
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
	
	/**
	 * M view pager draw move and data update.
	 *
	 * @param _color_data the color data
	 * @param _w          the w
	 * @param _h          the h
	 */
	public void mViewPagerDrawMoveAndDataUpdate(int[] _color_data, int _w, int _h) {
		mViewPagerCurrentPage(FD_MENU.DRAW_);
		mShowProgress();
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				MDEBUG.debug("cancel mViewPagerDrawMoveAndDataUpdate _w : " + ", _h : " + _h);
				((MFragmentDraw) mFragmentDraw).mUpdateColors(_color_data, _w, _h);
				mCancelProgress();
			}
		}, FD_DELAY.DRAW_PIXEL_UPDATE);
	}
	
	/**
	 * M view pager draw move and data update.
	 *
	 * @param _list the list
	 * @param _w    the w
	 * @param _h    the h
	 */
	public void mViewPagerDrawMoveAndDataUpdate(List<int[]> _list, int _w, int _h) {
		mViewPagerCurrentPage(FD_MENU.DRAW_);
		mShowProgress();
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				MDEBUG.debug("cancel mViewPagerDrawMoveAndDataUpdate!");
				((MFragmentDraw) mFragmentDraw).mSetAllPageColor(_list, _w, _h);
				mCancelProgress();
			}
		}, FD_DELAY.DRAW_PIXEL_UPDATE);
	}
	
	/**
	 * M view pager current page.
	 *
	 * @param _idx the idx
	 */
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
	
	/**
	 * M go info activity.
	 */
	public void mGoInfoActivity() {
		Intent _intent = new Intent(this, InfoActivity.class);
		startActivity(_intent);
	}
	
	private class MInitLoadRoomDBDataAsyncTask extends LoadRoomDBDataAsyncTask {
		/**
		 * Instantiates a new M init load room db data async task.
		 *
		 * @param _activity the activity
		 * @param _md       the md
		 */
		public MInitLoadRoomDBDataAsyncTask(Activity _activity, MAppDatabase _md) {
			super(_activity, _md);
		}
		
		@Override
		public void mResult(List<MD_Category> _c_list, List<MD_Emoticon> _e_list) {
			mSetGalleryListData(_c_list, _e_list);
			
//			if(MShared.strBle.mName == null || !MShared.strBle.mIsAutoConnect)
				mViewPagerCurrentPage(FD_MENU.DEVICE_LIST);
//			else {
//				if(MShared.strBle.mAddress.length() > MAPP.NONE_) {
//					mShowProgress();
//					if(mFragmentDeviceList == null) {
//						mFragmentDeviceList = new MFragmentDeviceList(MainActivity.this, mBtDeviceList);
//					}
//					mBtConnect(MShared.strBle.mAddress);
//				}
//			}
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resCode, Intent data) {
		super.onActivityResult(requestCode, resCode, data);
		
		if(resCode == Activity.RESULT_OK && data != null) {
			String _realPath = Utils.getRealPath(this, data.getData());
			
			MDEBUG.debug("onActivityResult Image Path : " + _realPath);
			try {
				File _file = new File(_realPath);
				long _size = _file.getAbsoluteFile().length();
				MDEBUG.debug("onActivityResult _size : " + _size);
				Uri _uri = FileProvider.getUriForFile(MainActivity.this, getPackageName(), _file);
				Bitmap _bm = Utils.mGetBitmapFromUri(MainActivity.this, _file.getAbsolutePath(), _uri,
						((MFragmentDraw)mFragmentDraw).mGetCellW(), ((MFragmentDraw)mFragmentDraw).mGetCellH(), _size);
				if(_bm != null) {
					int[] _src = new int[_bm.getWidth() * _bm.getHeight()];
					_bm.getPixels(_src, 0, _bm.getWidth(), 0, 0, _bm.getWidth(), _bm.getHeight());
					
					((MFragmentDraw)mFragmentDraw).mUpdateColors(_src, _bm.getWidth(), _bm.getHeight());
				}
			} catch(Exception e) {
				MDEBUG.debug("onActivityResult error : " + e.toString());
			}
		}
	}
	
	/**
	 * M go color setting dialog.
	 *
	 * @param _st the st
	 */
	public void mGoColorSettingDialog(int _st) {
		AmbilWarnaDialog colorPicker = null;
		
		if(_st == MainActivity.POPUP_COLOR_SETTING_DRAW) {
			final MFragmentDraw _mfd = (MFragmentDraw) mFragmentDraw;
			colorPicker = new AmbilWarnaDialog(this, _mfd.mPenColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
				@Override
				public void onCancel(AmbilWarnaDialog dialog) {
				}
				
				@Override
				public void onOk(AmbilWarnaDialog dialog, int color) {
					_mfd.mSetColor(color);
				}
			});
		} else if(_st == MainActivity.POPUP_COLOR_SETTING_TEXT) {
			final MFragmentText _mft = (MFragmentText) mFragmentText;
			colorPicker = new AmbilWarnaDialog(this, _mft.mPenColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
				@Override
				public void onCancel(AmbilWarnaDialog dialog) {
				}
				
				@Override
				public void onOk(AmbilWarnaDialog dialog, int color) {
					_mft.mSetColor(color, true);
				}
			});
		} else if(_st == MainActivity.POPUP_COLOR_SETTING_TEXT_BG) {
			final MFragmentText _mft = (MFragmentText) mFragmentText;
			colorPicker = new AmbilWarnaDialog(this, _mft.mTextBGColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
				@Override
				public void onCancel(AmbilWarnaDialog dialog) {
				}
				
				@Override
				public void onOk(AmbilWarnaDialog dialog, int color) {
					_mft.mSetColor(color, false);
				}
			});
		}
		colorPicker.show();
	}
	
	/**
	 * M show preview dialog.
	 *
	 * @param _data the data
	 * @param _w    the w
	 * @param _h    the h
	 */
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
	
	/**
	 * M show preview dialog.
	 *
	 * @param _data the data
	 * @param _w    the w
	 * @param _h    the h
	 */
	public void mShowPreviewDialog(int[] _data, int _w, int _h) {
		if(_data == null) {
			mShowMessageDialog(R.string.pop_msg_local_img_load_error, false);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.layout_preview_dialog, null);
			ImageView _iv = view.findViewById(R.id.ivPreviewImg);
			Bitmap _bm = Bitmap.createBitmap(_data, _w, _h, Bitmap.Config.ARGB_8888);
			_iv.setImageBitmap(_bm);
			builder.setView(view);
			
			final AlertDialog dialog = builder.create();
			dialog.setCancelable(true);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
		}
	}
	
	/**
	 * M keyboard hide.
	 *
	 * @param _et the et
	 */
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
//				if(device.getBondState() != BluetoothDevice.BOND_BONDED) {
					MDEBUG.debug("BOND_BONDED name : " + device.getName() + ", address : " + device.getAddress());
					
					STR_BluetoothDevice _str = new STR_BluetoothDevice();
					_str.mName = device.getName();
					_str.mAddress = device.getAddress();
					boolean _is_update = true;
					String _address = ((MFragmentDeviceList)mFragmentDeviceList).mAddress;
					for(int i = 0; i < mBtDeviceList.size(); i++) {
						if(mBtDeviceList.get(i).mAddress.equals(_str.mAddress) || _str.mAddress.equals(_address)) {
							_is_update = false;
							return;
						}
					}
					
					if(_is_update)
						mBtDeviceList.add(_str);
//				}
				
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
	
	/**
	 * M bt scan.
	 */
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
	
	/**
	 * M bt init.
	 */
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
				if(mIsFinish)
					return;
				
				mIsBtConnect = false;
				if(mFragmentDeviceList == null)
					mFragmentDeviceList = new MFragmentDeviceList(MainActivity.this, mBtDeviceList);
				MDEBUG.debug("onDeviceDisconnected!");
				mBtSpp.stopAutoConnect();
				mBtSpp.cancelDiscovery();
				mBtSpp.disconnect();
				mBtDeviceList.clear();
				((MFragmentDeviceList)mFragmentDeviceList).mAddress = "";
				
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
				
				mHandler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
	                     ((MFragmentDeviceList)mFragmentDeviceList).mSetDisconnectDevice();
	                     
	                     mShowMessageDialog(R.string.txt_bt_disconnect, false);
	                     mViewPagerCurrentPage(FD_MENU.DEVICE_LIST);
	                     mCancelProgress();
                     }
                }, FD_DELAY.MIN_DISPLAY);
			}
			
			public void onDeviceConnectionFailed() { //연결실패
				mIsBtConnect = false;
				MDEBUG.debug("onDeviceConnectionFailed!");
				mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							mShowMessageDialog(R.string.txt_bt_connect_fail, false);
							mViewPagerCurrentPage(FD_MENU.DEVICE_LIST);
							mCancelProgress();
						}
					}, FD_DELAY.MIN_DISPLAY
				);
			}
		});
	}
	
	/**
	 * M bt connect.
	 *
	 * @param _address the address
	 */
	public void mBtConnect(String _address) {
		mShowProgress();
		mBtSpp.connect(_address);
	}
	
	/**
	 * Screen clear.
	 */
	public void screen_clear() {
		if(mBtSpp != null && mIsBtConnect)
			MBluetoothUtils.transmit_data(mBtSpp, FD_BT.SET_SCR_CLEAR);
	}
	
	private class MSendStepByStepDotDataAsyncTask extends AsyncTask<Void, Void, Void> {
		/**
		 * The M idx.
		 */
		int mIDX;
		/**
		 * The M next idx.
		 */
		int mNextIdx;
		/**
		 * The M buf.
		 */
		int[] mBuf;
		/**
		 * The M w.
		 */
		int mW,
		/**
		 * The M h.
		 */
		mH;
		/**
		 * The M send data.
		 */
		MBluetoothUtils.SendDataClass mSendData;
		
		/**
		 * Instantiates a new M send step by step dot data async task.
		 *
		 * @param _w   the w
		 * @param _h   the h
		 * @param _idx the idx
		 * @param _buf the buf
		 */
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
	
	/**
	 * M set all dot data send.
	 *
	 * @param _w         the w
	 * @param _h         the h
	 * @param _in_colors the in colors
	 * @param _idx       the idx
	 */
	public void mSetAllDotDataSend(int _w, int _h, int[][] _in_colors, int _idx) {
		if(_in_colors != null)
			MDEBUG.debug("_in_colors : " + _in_colors.length);
		new MSendAllDotDataForDeviceAsyncTask(_w, _h, _in_colors, 0, 0, _idx).execute();
	}
	
	// TODO MAX Count
	private int mMaxCount = 30;
	
	/**
	 * The type M send all dot data for device async task.
	 */
	public class MSendAllDotDataForDeviceAsyncTask extends AsyncTask<Void, Void, Void> {
		/**
		 * The M check data.
		 */
		MBluetoothUtils.SendDataClass[] mCheckData = new MBluetoothUtils.SendDataClass[mMaxCount];
		/**
		 * The M all data.
		 */
		int[][] mAllData;
		/**
		 * The M count.
		 */
		int mCount,
		/**
		 * The M idx.
		 */
		mIDX;
		/**
		 * The M w.
		 */
		int mW,
		/**
		 * The M h.
		 */
		mH,
		/**
		 * The M sx.
		 */
		mSX,
		/**
		 * The M sy.
		 */
		mSY,
		/**
		 * The M ex.
		 */
		mEX,
		/**
		 * The M ey.
		 */
		mEY;
		
		/**
		 * Instantiates a new M send all dot data for device async task.
		 *
		 * @param _w    the w
		 * @param _h    the h
		 * @param _data the data
		 * @param _s_x  the s x
		 * @param _s_y  the s y
		 * @param _idx  the idx
		 */
		public MSendAllDotDataForDeviceAsyncTask(int _w, int _h, int[][] _data, int _s_x, int _s_y, int _idx) {
			mCount = 0;
			mW = _w;
			mH = _h;
			mSX = _s_x;
			mSY = _s_y;
			mIDX = _idx;
			mAllData = _data;
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			int _total = mW * mH;
			int _idx = 0;
			for(int x = mSX; x < mW; x++) {
				for(int y = mSY; y < mH; y++) {
					MDEBUG.debug("mAllData[" + x + "][" + y + "] : " + mAllData[x][y]);
					if(Color.alpha(mAllData[x][y]) > 0 && mAllData[x][y] != Color.BLACK) {
						mCheckData[mCount] = new MBluetoothUtils.SendDataClass();
						mCheckData[mCount].x = (byte) x;
						mCheckData[mCount].y = (byte) y;
						mCheckData[mCount].red = (byte)(((mAllData[x][y] & 0x00ff0000) >> 16) / 8);
						mCheckData[mCount].green = (byte)(((mAllData[x][y] & 0x0000ff00) >> 8) / 8);
						mCheckData[mCount].blue = (byte)((mAllData[x][y] & 0x000000ff) / 8);
						mCheckData[mCount].color = mAllData[x][y];
						mCount++;
					}
					
					if(mCount == mMaxCount) {
						MDEBUG.debug("end y x : " + x);
						mEY = y;
						break;
					}
				}
				
				mSY = 0;
				if(mCount == mMaxCount) {
					mEY++;
					mEX = x;
					if(mEY >= mH) {
						mEX++;
						mEY = 0;
					}
					
					MDEBUG.debug("22 x : " + x);
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
			byte[] _send_buf =
					new byte[FD_BT.SIZE_PAGE + FD_BT.SIZE_LEN1 + (mCount * FD_BT.SIZE_FIELD)];
			int _buf_idx = 0;
			
			MDEBUG.debug("mEX : " + mEX + ", mEY : " + mEY + ", mCount : " + mCount);
			_send_buf[_buf_idx++] = (byte)mIDX;
			_send_buf[_buf_idx++] = (byte)(mCount >> 8);
			_send_buf[_buf_idx++] = (byte)mCount;
			
//			MDEBUG.debug("mCount : " + mCount);
			
			for(int i = 0; i < mCount; i++) {
//				MDEBUG.debug("mCheckData[" + i + "].x_pos : " + mCheckData[i].x + ", mCheckData[" + i + "].y_pos : " + mCheckData[i].y);
				_send_buf[_buf_idx++] = mCheckData[i].x;
				_send_buf[_buf_idx++] = mCheckData[i].y;
				_send_buf[_buf_idx++] = (byte)mCheckData[i].red;
				_send_buf[_buf_idx++] = (byte)mCheckData[i].green;
				_send_buf[_buf_idx++] = (byte)mCheckData[i].blue;
			}
			
			MBluetoothUtils.transmit_data(mBtSpp, FD_BT.SET_DRAW, _send_buf);
			
			if(mEX < (mW - 1) || mEY < (mH - 1)) {
				final int _x = mEX;
				final int _y = mEY;
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						new MSendAllDotDataForDeviceAsyncTask(mW, mH, mAllData, _x, _y, mIDX).execute();
					}
				}, FD_DELAY.DATA_UPDATE);
			} else {
				MDEBUG.debug("MSendAllDotDataForDeviceAsyncTask mEX : onPostExecute");
				mCancelProgress();
			}
		}
	}
	
	/**
	 * The type Mm category add dialog.
	 */
	public class MMCategoryAddDialog extends MCategoryAddDialog {
		/**
		 * Instantiates a new Mm category add dialog.
		 *
		 * @param _activity the activity
		 */
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
		/**
		 * Instantiates a new M category add db data async task.
		 *
		 * @param _activity the activity
		 * @param _mad      the mad
		 * @param _str      the str
		 */
		public MCategoryAddDBDataAsyncTask(Activity _activity, MAppDatabase _mad, MD_Category _str) {
			super(_activity, _mad, _str);
			mShowProgress();
		}
		
		@Override
		public void mResult() {
			new MLoadRoomDBDataAsyncTask(MainActivity.this, MAPP.mAppDatabase).execute();
		}
	}
	
	/**
	 * M delete category.
	 *
	 * @param _idx the idx
	 */
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
		/**
		 * Instantiates a new M category delete db data async task.
		 *
		 * @param _mad the mad
		 * @param _str the str
		 */
		public MCategoryDeleteDBDataAsyncTask(MAppDatabase _mad, MD_Category _str) {
			super(_mad, _str);
			mShowProgress();
		}
		
		@Override
		public void mResult() {
			new MLoadRoomDBDataAsyncTask(MainActivity.this, MAPP.mAppDatabase).execute();
		}
	}
	
	/**
	 * M delete emoticon.
	 *
	 * @param _idx the idx
	 */
	public void mDeleteEmoticon(int _idx) {
		MD_Emoticon _str_emoticon = null;
		int _c_idx = MAPP.ERROR_;
		for(int i = 0; i < mEmoticonList.size(); i++) {
			MD_Emoticon _str = mEmoticonList.get(i);
			if(_idx == _str.idx_) {
				_c_idx = i;
				_str_emoticon = _str;
				break;
			}
		}
		
		if(_str_emoticon != null)
			new MEmoticonDeleteDBDataAsyncTask(MAPP.mAppDatabase, _str_emoticon, _c_idx).execute();
	}
	
	private class MEmoticonDeleteDBDataAsyncTask extends EmoticonDeleteDBDataAsyncTask {
		/**
		 * The M idx.
		 */
		int mIDX;
		
		/**
		 * Instantiates a new M emoticon delete db data async task.
		 *
		 * @param _mad the mad
		 * @param _str the str
		 * @param _idx the idx
		 */
		public MEmoticonDeleteDBDataAsyncTask(MAppDatabase _mad, MD_Emoticon _str, int _idx) {
			super(_mad, _str);
			mShowProgress();
			mIDX = _idx;
		}
		
		@Override
		public void mResult() {
			mCancelProgress();
			mEmoticonList.remove(mIDX);
			((MFragmentGallery)mFragmentGallery).mUpdateList();
		}
	}
	
	private class MLoadRoomDBDataAsyncTask extends LoadRoomDBDataAsyncTask {
		/**
		 * Instantiates a new M load room db data async task.
		 *
		 * @param _activity the activity
		 * @param _md       the md
		 */
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
	
	/**
	 * The type Mm emoticon add dialog.
	 */
	public class MMEmoticonAddDialog extends MEmoticonAddDialog {
		/**
		 * Instantiates a new Mm emoticon add dialog.
		 *
		 * @param _activity the activity
		 */
		public MMEmoticonAddDialog(MainActivity _activity) {
			super(_activity);
		}
		
		@Override
		public void mBtnYes(String _name, int _idx) {
			MDEBUG.debug("MMEmoticonAddDialog mBtnYes _name : " + _name + ", idx : " + _idx);
			
			mShowProgress();
			mSaveFileIndex = MAPP.INIT_;
			mSaveEmoticonFileList.clear();
			new MCreateFolderAndFileSaveAsyncTask(_name, _idx).execute();
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
	
	/**
	 * M show emoticon add dialog.
	 */
	public void mShowEmoticonAddDialog() {
		mEmoticonAddDialog.mShowDialog(this, mCategoryList);
	}
	
	/**
	 * M go phone gallery.
	 */
	public void mGoPhoneGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(FD_DRAW.PHONE_GALLERY_TYPE);
		startActivityForResult(intent, FD_REQ.REQ_PHONE_GALLERY);
	}
	
	/**
	 * Create Folder & File
	 */
	private class MCreateFolderAndFileSaveAsyncTask extends AsyncTask<Void, Void, Void> {
		/**
		 * The M name.
		 */
		String mName;
		/**
		 * The M file name.
		 */
		String mFileName = "";
		/**
		 * The M files name.
		 */
		String mFilesName = "";
		/**
		 * The M category idx.
		 */
		int mCategoryIdx = MAPP.ERROR_;
		
		/**
		 * Instantiates a new M create folder and file save async task.
		 *
		 * @param _name         the name
		 * @param _category_idx the category idx
		 */
		public MCreateFolderAndFileSaveAsyncTask(String _name, int _category_idx) {
			mName = _name;
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
			
			mFileName = "" + System.currentTimeMillis();
			File _file = new File(mFileDirPath + "/" + mFileName + FD_DRAW.MSAVE_FILE_FORMAT_STR);
			OutputStream _out = null;
			
			try {
				_file.createNewFile();
				_out = new FileOutputStream(_file);
				_bm.compress(FD_DRAW.MSAVE_FILE_FORMAT,
						FD_DRAW.MSAVE_FILE_QUALITY, _out);
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
			
			String _file_name = mFileName;
			mSaveEmoticonFileList.add(_file_name);
			if(mSaveFileIndex < _mfd.mGetMatrixEA()) {
				new MCreateFolderAndFileSaveAsyncTask(mName, mCategoryIdx).execute();
			} else {
				new MDrawFileInfoInRoomAsyncTask(mCategoryIdx, mName).execute();
			}
		}
	}
	
	private class MDrawFileInfoInRoomAsyncTask extends AsyncTask<Void, Void, Void> {
		private String mName = "";
		private int mCategoryIdx = MAPP.ERROR_;
		
		/**
		 * Instantiates a new M draw file info in room async task.
		 *
		 * @param _category_idx the category idx
		 * @param _name         the name
		 */
		public MDrawFileInfoInRoomAsyncTask(int _category_idx, String _name) {
			mCategoryIdx = _category_idx;
			mName = _name;
			MDEBUG.debug("mCategoryIdx : " + mCategoryIdx);
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			MDEBUG.debug("mCategoryIdx : " + mCategoryIdx);
			if(mCategoryIdx == MAPP.ERROR_)
				return null;
			
			String _file_path = "";
			
			MD_Emoticon _md = new MD_Emoticon();
			if(mSaveEmoticonFileList.size() > MAPP.START_ALIVE) {
				for(int i = 0; i < mSaveEmoticonFileList.size(); i++) {
					_file_path += (mSaveEmoticonFileList.get(i) + FD_DRAW.SPLIT_TOKEN);
				}
				_md.mIsOneEmoticon = false;
			} else {
				_file_path = (mSaveEmoticonFileList.get(MAPP.INIT_));
				_md.mIsOneEmoticon = true;
			}
			
			_md.mName = mName;
			_md.mImageResID = MAPP.ERROR_;
			_md.mCatergoryIdx = mCategoryIdx;
			
			if(_md.mIsLocalData)
				_md.mCatergoryName = MDB_FD.MCATEGORY_TITLE[_md.mCatergoryIdx];
			else {
				for(int i = 0; i < mCategoryList.size(); i++) {
					if(_md.mCatergoryIdx == mCategoryList.get(i).idx_) {
						_md.mCatergoryName = mCategoryList.get(i).mName;
						break;
					}
				}
			}
			
			_md.mIsLocalData = false;
			_md.mIsFavorite = false;
			_md.mDate = System.currentTimeMillis();
			_md.mEmoticonFilesPath = _file_path;
			
			MDEBUG.debug("_md.mCatergoryName : " + _md.mCatergoryName);
			
			MAPP.mAppDatabase.mDAOHandler().insertEmoticon(_md);
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			new MLoadRoomDBDataAsyncTask(MainActivity.this, MAPP.mAppDatabase).execute();
		}
	}
}