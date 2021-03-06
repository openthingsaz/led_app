package truesolution.ledpad.asign.fragment.str;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_Wifi {
	/**
	 * The M name.
	 */
	public String mName;
	/**
	 * The M address.
	 */
	public String mAddress;
	
	/**
	 * M set data.
	 *
	 * @param _name    the name
	 * @param _address the address
	 */
	public void mSetData(String _name, String _address) {
		mName = _name;
		mAddress = _address;
	}
	
	/**
	 * M set data.
	 *
	 * @param _str the str
	 */
	public void mSetData(STR_Wifi _str) {
		mSetData(_str.mName, _str.mAddress);
	}
}