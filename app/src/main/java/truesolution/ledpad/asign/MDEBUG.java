package truesolution.ledpad.asign;

import android.util.Log;

/**
 * Created by TCH on 2020-07-01
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020-07-01
 */
public class MDEBUG {
	/**
	 * Debug Mode
	 */
	public static boolean isDebug =
			true;
//			false;
	
	/**
	 * debug
	 * 
	 * @Author	: ThinkCodeHelp
	 * @Date	: 2020.07.01
	 * @Comment	: Debug Message 출력
	 *
	 * @param _str
	 */
	public static void debug(String _str) {
		if(isDebug) {
			Log.d("debug", "=========>" + _str);
		}
	}
	
	/**
	 * error
	 * 
	 * @Author	: ThinkCodeHelp
	 * @Date	: 2020.07.01
	 * @Comment	: Error Message 출력
	 *
	 * @param _str
	 */
	public static void error(String _str) {
		if(isDebug) {
			Log.e("error", "=========>" + _str);
		}
	}
	
	public static void adebug(String _str) {
		Log.d("debug", "=========>" + _str);
	}
}