package truesolution.ledpad.asign.fragment.str;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_BluetoothDevice {
	public String mName;
	public String mAddress;
	public boolean mIsAutoConnect;
	
	public void mSetData(String _name, String _address) {
		mName = _name;
		mAddress = _address;
	}
	
	public void mSetData(STR_BluetoothDevice _str) {
		mSetData(_str.mName, _str.mAddress);
	}
}