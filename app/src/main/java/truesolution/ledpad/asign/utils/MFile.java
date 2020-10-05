package truesolution.ledpad.asign.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import truesolution.ledpad.asign.MDEBUG;

/**
 * Created by TCH on 2018. 8. 3.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2018. 8. 3.
 */
public class MFile {
	/**
	 * Text to File
	 *
	 * @param _file the file
	 * @param _data the data
	 */
	public static void mTextToFile(File _file, String _data) {
		try {
			FileOutputStream _fos = new FileOutputStream(_file);
			_fos.write(_data.getBytes());
			MDEBUG.debug("mTextToFile mWriteToFile getAbsolutePath : " + _file.getAbsolutePath());
			_fos.close();
		} catch(IOException e) {
			MDEBUG.debug("mTextToFile Write To Files Error : " + e.toString());
		}
	}
	
	/**
	 * Byte Array To File
	 *
	 * @param _file the file
	 * @param _buf  the buf
	 */
	public static void mByteArrayToFile(File _file, byte[] _buf) {
		try {
			FileOutputStream _fos = new FileOutputStream(_file);
			_fos.write(_buf);
			MDEBUG.debug("mByteArrayToFile getAbsolutePath : " + _file.getAbsolutePath());
			_fos.close();
		} catch(IOException e) {
			MDEBUG.debug("mByteArrayToFile Write To Files Error : " + e.toString());
		}
	}
}
