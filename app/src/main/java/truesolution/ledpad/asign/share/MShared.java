package truesolution.ledpad.asign.share;

import android.app.Application;
import android.content.SharedPreferences;

import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.fragment.str.STR_BluetoothDevice;

/**
 * Created by TCH on 2020-07-31
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020-07-31
 */
public class MShared {
	public static MShared mShared;
	public static STR_BluetoothDevice strBle;
	
	public static final String NAME_                       = "ASIGN_";
	public static final int PERMISSION_                    = Application.MODE_PRIVATE;
	
	public static final String KEY_BLE_NAME                = "BLE_NAME_";
	public static final String KEY_BLE_ADDRESS             = "BLE_ADDRESS_";
	public static final String KEY_BLE_AUTO_LOGIN          = "BLE_AUTO_LOGIN";
	
	public SharedPreferences mSP;
	public SharedPreferences.Editor mSPEditor;
	
	public static MShared mGetInstance(Application _app) {
		if(mShared == null) {
			mShared = new MShared(_app);
		}
		if(strBle == null) {
			strBle = new STR_BluetoothDevice();
		}
		return mShared;
	}
	
	public MShared(Application _app) {
		mSP = _app.getSharedPreferences(NAME_, PERMISSION_);
		mSPEditor = mSP.edit();
	}
	
	/**
	 * Save Data
	 *
	 * @param _key
	 * @param _value
	 */
	public void mSetDeviceInfo(String _key, String _value) {
		mSPEditor.putString(_key, _value);
		mSPEditor.commit();
	}
	public void mSetDeviceInfo(String _key, Boolean _value) {
		mSPEditor.putBoolean(_key, _value);
		mSPEditor.commit();
	}
	public void mSetDeviceNameAddress(String _name, String _address) {
		mSPEditor.putString(KEY_BLE_NAME, _name);
		mSPEditor.putString(KEY_BLE_ADDRESS, _address);
		mSPEditor.commit();
	}
	
	/**
	 * Load Device Info
	 */
	public void mLoadDeviceInfo() {
		strBle.mName = mSP.getString(KEY_BLE_NAME, null);
		strBle.mAddress = mSP.getString(KEY_BLE_ADDRESS, null);
		strBle.mIsAutoConnect = mSP.getBoolean(KEY_BLE_AUTO_LOGIN, true);
	}
	
	public void mPrint() {
		MDEBUG.debug("Bluetooth Device name : " + strBle.mName + ", address : " + strBle.mAddress);
	}
}
