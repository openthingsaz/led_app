package truesolution.ledpad.asign.utils;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fd.FD_BT;
import truesolution.ledpad.asign.fd.FD_DRAW;
import truesolution.ledpad.asign.fragment.str.STR_Text;

/**
 * Created by TCH on 2020/07/27
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/27
 */
public class MBluetoothUtils {
	/**
	 * The type Send data class.
	 */
	public static class SendDataClass implements Serializable {
		/**
		 * The X.
		 */
		public byte x;
		/**
		 * The Y.
		 */
		public byte y;
		/**
		 * The Red.
		 */
		public int red;
		/**
		 * The Green.
		 */
		public int green;
		/**
		 * The Blue.
		 */
		public int blue;
		/**
		 * The Color.
		 */
		public int color;
	}
	
	/**
	 * Send all dot.
	 *
	 * @param _bspp         the bspp
	 * @param _w            the w
	 * @param _h            the h
	 * @param _matrix_array the matrix array
	 */
	public static void send_all_dot(BluetoothSPP _bspp, int _w, int _h, int[][] _matrix_array) {
		int checkCount = 0;
		for (int i=0; i<_w; i++){
			for(int j=0; j<_h; j++) {
				int __color = _matrix_array[i][j];
				if(Color.alpha(__color) != 0 && __color != -1) {
					++checkCount;
				}
			}
		}
		
		int cnt = 0;
		SendDataClass[] send = new SendDataClass[checkCount];
		SendDataClass send2 = new SendDataClass();
		
		String Col;
		for (int i=0; i<_w; i++){
			for(int j=0; j<_h; j++) {
				int _color = _matrix_array[i][j];
				if(Color.alpha(_matrix_array[i][j]) != 0 && _color != -1) {
					send[cnt] = new SendDataClass();
					send[cnt].x = (byte)i;
					send[cnt].y = (byte)j;
					send[cnt].red = (byte)Color.red(_color);
					send[cnt].green = (byte)Color.green(_color);
					send[cnt].blue = (byte)Color.blue(_color);
					++cnt;
				}
			}
		}
		
		byte[] sendData = new byte[checkCount * FD_BT.SIZE_FIELD];
		int inx = 0;
		for (int i = 0; i < checkCount; i++) {
			sendData[inx++] = send[i].x;
			sendData[inx++] = send[i].y;
			sendData[inx++] = (byte) send[i].red;
			sendData[inx++] = (byte) send[i].green;
			sendData[inx++] = (byte) send[i].blue;
		}
		transmit_data(_bspp, FD_BT.SET_DRAW, sendData);
		
		// Test Code
//		int _t_idx = 0;
//		byte[] tsendData = new byte[50 * 5];
//		for (int i=0; i < 50; i++) {
//			byte _b = (byte)((i + 1) % 30);
//			tsendData[_t_idx++] = _b;
//			tsendData[_t_idx++] = _b;
//			tsendData[_t_idx++] = (byte)255;
//			tsendData[_t_idx++] = (byte)255;
//			tsendData[_t_idx++] = (byte)0;
//		}
//		transmit_data(mBtSpp, FD_BT.SET_DRAW, tsendData);
	}
	
	/**
	 * Crc 16 ccitt short.
	 *
	 * @param buf the buf
	 * @param len the len
	 *
	 * @return the short
	 */
	public static short crc16_ccitt(byte[] buf, int len) {
		int counter;
		short crc = 0;
		for( counter = 0; counter < len; counter++)
			crc = (short)((crc << 8) ^ FD_BT.CRC16TAB[((crc >> 8) ^ buf[counter]) & 0x00FF]);
		return crc;
	}
	
	/**
	 * Transmit data.
	 *
	 * @param ba   the ba
	 * @param cmd  the cmd
	 * @param data the data
	 */
	public static void transmit_data(BluetoothSPP ba, byte cmd, byte[] data) {
		short dataLen = 0;
		short crc;
		int inx = 0;
		byte[] sendData;
		byte[] cmdData;
		
		dataLen = (short)(data.length + MAPP.START_ALIVE); // cmd + color data
		sendData = new byte[FD_BT.SIZE_PKT_DATA + data.length]; // STX + DataLength size(2byte fix) + Cmd + dataLen +  CRC + ETX
		sendData[inx++] = FD_BT.STX;
		sendData[inx++] = (byte)(dataLen);
		sendData[inx++] = (byte)(dataLen >> 8);
		
		// TODO Add : 2020. 08. 11 - Inspection Field 2byte
		sendData[inx++] = (byte)(dataLen);
		sendData[inx++] = (byte)(dataLen >> 8);
		// TODO End
		
		sendData[inx++] = cmd;
		
		for (int i=0; i<data.length; i++) {
			sendData[inx++] = data[i];
		}
		
		cmdData = new byte[dataLen];
		System.arraycopy(sendData, FD_BT.IDX_CMD, cmdData, 0, dataLen);
		crc = crc16_ccitt(cmdData, dataLen);
		
		sendData[inx++] = (byte)(crc >> 8);
		sendData[inx++] = (byte)(crc);
		sendData[inx++] = FD_BT.ETX;
		ba.send(sendData, false);
	}
	
	/**
	 * Transmit data.
	 *
	 * @param ba  the ba
	 * @param cmd the cmd
	 */
	public static void transmit_data(BluetoothSPP ba, byte cmd) {
		short dataLen = 0;
		short crc;
		int inx = 0;
		byte[] sendData;
		byte[] cmdData;
		
		dataLen = (short)MAPP.START_ALIVE; // 1 is cmd
		sendData = new byte[FD_BT.SIZE_PKT_DATA]; // STX + DataLength size(2byte fix) + Cmd + CRC + ETX
		sendData[inx++] = FD_BT.STX;
		sendData[inx++] = (byte)(dataLen);
		sendData[inx++] = (byte)(dataLen >> 8);
		// TODO Add : 2020. 08. 11 - Inspection Field 2byte
		sendData[inx++] = (byte)(dataLen);
		sendData[inx++] = (byte)(dataLen >> 8);
		// TODO End
		sendData[inx++] = cmd;
		cmdData = new byte[dataLen];
		System.arraycopy(sendData, FD_BT.IDX_CMD, cmdData, 0, dataLen);
		crc = crc16_ccitt(cmdData, dataLen);
		
		sendData[inx++] = (byte)(crc >> 8);
		sendData[inx++] = (byte)(crc);
		sendData[inx++] = FD_BT.ETX;
		ba.send(sendData, false);
	}
	
	/**
	 * Send single dot.
	 *
	 * @param _bspp      the bspp
	 * @param _page_num  the page num
	 * @param _cmd       the cmd
	 * @param xPos       the x pos
	 * @param yPos       the y pos
	 * @param colorValue the color value
	 */
	public static void send_single_dot(BluetoothSPP _bspp, int _page_num, byte _cmd, byte xPos, byte yPos, int colorValue) {
		byte red_col;
		byte green_col;
		byte blue_col;
		byte[] sendData = new byte[FD_BT.SIZE_SET_DRAW_FIELD];
		int inx = MAPP.INIT_;
		byte page_num = (byte)(MAPP.START_ALIVE + _page_num);
		short dotLen = MAPP.START_ALIVE;

//		MDEBUG.debug("send_single_dot red : " + Color.red(colorValue) + ", green : " + Color.green(colorValue) + ", blue : " + Color.blue(colorValue));
//		MDEBUG.debug("page_num : " + page_num);

		red_col = (byte)(((colorValue & 0x00ff0000) >> 16) / 8);
		green_col = (byte)(((colorValue & 0x0000ff00) >> 8) / 8);
		blue_col = (byte)(((colorValue & 0x000000ff) >> 0) / 8);
		
		sendData[inx++] = page_num;
		sendData[inx++] = (byte)(dotLen >> 8);
		sendData[inx++] = (byte)dotLen;
		
		sendData[inx++] = xPos;
		sendData[inx++] = yPos;
		sendData[inx++] = red_col;
		sendData[inx++] = green_col;
		sendData[inx++] = blue_col;
		transmit_data(_bspp, _cmd, sendData);
	}

	// TODO TCH ADD
	
	/**
	 * Send Text Data
	 *
	 * @param _bspp          the bspp
	 * @param _text          the text
	 * @param _color         the color
	 * @param _bg_color      the bg color
	 * @param _font_size     the font size
	 * @param _action        the action
	 * @param _time          the time
	 * @param _save_play_gap the save play gap
	 */
	public static void mSendText(BluetoothSPP _bspp, String _text, int _color, int _bg_color, int _font_size, int _action, int _time, byte _save_play_gap) {
		if(_text == null && _text.length() == 0)
			return;
		
//		MDEBUG.debug("_text : _" + _text + "_, _color : " + _color + ", _font_size : " + _font_size);
		byte[] _text_data = _text.getBytes();
		int _idx_s = MAPP.INIT_;
//		MDEBUG.debug("_text_data : " + _text_data.length + ", _text_data : " + new String(_text_data));
		int _data_len = (FD_BT.SIZE_PAGE + FD_BT.SIZE_TEXT_LEN + _text_data.length + FD_BT.SIZE_FONT + FD_BT.SIZE_FONT_BG_COLOR);
		if(_action != FD_DRAW.ACTION_DEFAULT) {
			_data_len += (FD_BT.SIZE_ACTION_TIME);
		}
		
		MDEBUG.debug("mSendText _text_data.length : " + _text_data.length);
		
		byte[] _send_data = new byte[_data_len];
		byte page_num = MAPP.START_ALIVE;
		_send_data[_idx_s++] = page_num;
		_send_data[_idx_s++] = (byte)_text_data.length;
		System.arraycopy(_text_data, 0, _send_data, _idx_s, _text_data.length);
		_idx_s = _idx_s + _text_data.length;
		
		byte __font_size = (byte)(_font_size & 0x000000ff);
		_send_data[_idx_s++] = __font_size;
		MDEBUG.debug("mSendText __font_size : " + __font_size);
		
		// Font Color
		_send_data[_idx_s++] = (byte)(((_color & 0x00ff0000) >> 16) / 8);
		_send_data[_idx_s++] = (byte)(((_color & 0x0000ff00) >> 8) / 8);
		_send_data[_idx_s++] = (byte)(((_color & 0x000000ff) >> 0) / 8);
		
		// BG Color
		_send_data[_idx_s++] = (byte)(((_bg_color & 0x00ff0000) >> 16) / 8);
		_send_data[_idx_s++] = (byte)(((_bg_color & 0x0000ff00) >> 8) / 8);
		_send_data[_idx_s++] = (byte)(((_bg_color & 0x000000ff) >> 0) / 8);
		
		byte _cmd = (byte)(FD_BT.SET_TEXT + _save_play_gap);
		if(_action != FD_DRAW.ACTION_DEFAULT) {
			_send_data[_idx_s++] = (byte)(_action & 0x000000ff);
			_send_data[_idx_s++] = (byte)((_time & 0x0000ff00) >> 8);
			_send_data[_idx_s++] = (byte)(_time & 0x000000ff);
			
			_cmd = (byte)(FD_BT.SET_TEXT_ACTION + _save_play_gap);
		}
		
		MDEBUG.debug("send text _cmd : " + _cmd);
		transmit_data(_bspp, _cmd, _send_data);
	}
	
	/**
	 * Send Multi Text
	 *
	 * @param _bspp          the bspp
	 * @param _ar_text       the ar text
	 * @param _bg_color      the bg color
	 * @param _font_size     the font size
	 * @param _action        the action
	 * @param _time          the time
	 * @param _save_play_gap the save play gap
	 *
	 * @return int
	 */
	public static int mSendMultiText(BluetoothSPP _bspp, ArrayList<STR_Text> _ar_text, int _bg_color, int _font_size, int _action, int _time, byte _save_play_gap) {
		if(_ar_text == null && _ar_text.size() == 0)
			return MAPP.ERROR_;
		
		MDEBUG.debug("mSendMultiText size : " + _ar_text.size());
		
		ArrayList<STR_Text> _filter_result = new ArrayList<>();
		ArrayList<Integer> _filter_result_size = new ArrayList<>();
		int _total_size = 0;
		for(int i = 0; i < _ar_text.size(); i++) {
			String _text = _ar_text.get(i).mText;
			if(_text != null && _text.length() > 0) {
				byte[] _txt_buf = _text.getBytes();
				_filter_result.add(_ar_text.get(i));
				_filter_result_size.add(_txt_buf.length);
				_total_size += (FD_BT.SIZE_MULTI_TEXT_FONT_SIZE + _txt_buf.length + FD_BT.SIZE_MULTI_TEXT_COLOR);
			}
		}
		// Except - Action
		_total_size += FD_BT.SIZE_MULTI_TEXT_PREFIX;
		if(_action != FD_DRAW.ACTION_DEFAULT) {
			_total_size += (FD_BT.SIZE_ACTION_TIME);
		}
		byte[] _send_data = new byte[_total_size];
		
		int _idx_s = 0;
		
		// PageNum
		_send_data[_idx_s++] = MAPP.INIT_;
		
		// BG Color
		_send_data[_idx_s++] = (byte)(((_bg_color & 0x00ff0000) >> 16) / 8);
		_send_data[_idx_s++] = (byte)(((_bg_color & 0x0000ff00) >> 8) / 8);
		_send_data[_idx_s++] = (byte)(((_bg_color & 0x000000ff) >> 0) / 8);
		
		// Font Size
		byte __font_size = (byte)(_font_size & 0x000000ff);
		_send_data[_idx_s++] = __font_size;
		MDEBUG.debug("mSendMultiText __font_size : " + __font_size);
		
		// Group Count
		_send_data[_idx_s++] = (byte)(_filter_result.size() & 0x000000ff);
		
		for(int i = 0; i < _filter_result.size(); i++) {
			STR_Text _str = _filter_result.get(i);
			// Text Length
			_send_data[_idx_s++] = (byte)(_filter_result_size.get(i) & 0x000000ff);
			
			MDEBUG.debug("Text len[" + i + "] : " + ((byte)(_filter_result_size.get(i) & 0x000000ff)));
			
			byte[] _text_buf = _str.mText.getBytes();
			for(int y = 0; y < _text_buf.length; y++) {
				// Text Data
				_send_data[_idx_s++] = _text_buf[y];
			}
			
			// Text Color
//			_send_data[_idx_s++] = (byte) Color.red(_str.mColor);
//			_send_data[_idx_s++] = (byte) Color.green(_str.mColor);
//			_send_data[_idx_s++] = (byte) Color.blue(_str.mColor);
			_send_data[_idx_s++] = (byte)(((_str.mColor >> 16) & 0xff)/8); // RED
			_send_data[_idx_s++] = (byte)(((_str.mColor >> 8) & 0xff)/8); // GREEN
			_send_data[_idx_s++] = (byte)(((_str.mColor >> 0) & 0xff)/8); // BLUE
		}
		
		byte _cmd = (byte)(FD_BT.SET_TEXT_EACH + _save_play_gap);
		if(_action != FD_DRAW.ACTION_DEFAULT) {
			// TODO : TCH Screen Color
//			_send_data[_idx_s++] = (byte)0x00; // RED
//			_send_data[_idx_s++] = (byte)0x00; // GREEN
//			_send_data[_idx_s++] = (byte)0x00; // BLUE
			
			// Action & Time
			_send_data[_idx_s++] = (byte)(_action & 0x000000ff);
			_send_data[_idx_s++] = (byte)((_time & 0x0000ff00) >> 8);
			_send_data[_idx_s++] = (byte)(_time & 0x000000ff);
			
			_cmd = (byte)(FD_BT.SET_TEXT_EACH_ACTION + _save_play_gap);
		}
		
		MDEBUG.debug("send multi text _cmd : " + _cmd);
		transmit_data(_bspp, _cmd, _send_data);
		
		return MAPP.SUCCESS_;
	}
	
	/**
	 * Send Multi Dot
	 *
	 * @param _bspp       the bspp
	 * @param _pixel_size the pixel size
	 * @param _w          the w
	 * @param _h          the h
	 * @param _x          the x
	 * @param _y          the y
	 * @param _color      the color
	 */
	public static void mSendMultiDot(BluetoothSPP _bspp, int _pixel_size,
	                                 int _w, int _h,
	                                 byte _x, byte _y, int _color) {
		int _size = _pixel_size * _pixel_size;
		SendDataClass[] _sdc = new SendDataClass[_size];
		byte page_num = 1;
		int _cnt = 0;
		int _s_x = (_x - (_pixel_size / 2));
		int _s_y = (_y - (_pixel_size / 2));
		if(_s_x < MAPP.INIT_)
			_s_x = MAPP.INIT_;
		if(_s_y < MAPP.INIT_)
			_s_y = MAPP.INIT_;
		int _end_x = (_s_x + _pixel_size);
		int _end_y = (_s_y + _pixel_size);
		for (int i = _s_x; i < _end_x; i++){
			for(int j = _s_y; j < _end_y; j++) {
				if(i < _w && j < _h) {
					_sdc[_cnt] = new SendDataClass();
					_sdc[_cnt].x = (byte) i;
					_sdc[_cnt].y = (byte) j;
//					_sdc[_cnt].red = (byte) Color.red(_color);
//					_sdc[_cnt].green = (byte) Color.green(_color);
//					_sdc[_cnt].blue = (byte) Color.blue(_color);
					
					_sdc[_cnt].red = (byte)(((_color >> 16) & 0xff) / 8); // RED
					_sdc[_cnt].green = (byte)(((_color >> 8) & 0xff) / 8); // GREEN
					_sdc[_cnt].blue = (byte)(((_color >> 0) & 0xff) / 8); // BLUE
					++_cnt;
				}
			}
		}
		
		byte[] _send_data = new byte[FD_BT.SIZE_PAGE + FD_BT.SIZE_LEN1 + (_cnt * FD_BT.SIZE_FIELD)];
		int inx = 0;
		_send_data[inx++] = page_num;
		_send_data[inx++] = (byte)(_cnt >> 8);
		_send_data[inx++] = (byte)_cnt;
		for (int i = 0; i < _cnt; i++) {
			_send_data[inx++] = _sdc[i].x;
			_send_data[inx++] = _sdc[i].y;
			_send_data[inx++] = (byte)_sdc[i].red;
			_send_data[inx++] = (byte)_sdc[i].green;
			_send_data[inx++] = (byte)_sdc[i].blue;
		}
		transmit_data(_bspp, FD_BT.SET_DRAW, _send_data);
	}
}
