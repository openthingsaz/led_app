package truesolution.ledpad.asign.fd;

/**
 * Created by TCH on 2020/07/21
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/21
 */
public class FD_BT {
	/**
	 * The constant CRC16TAB.
	 */
	public static final int CRC16TAB[]= {
			0x0000,0x1021,0x2042,0x3063,0x4084,0x50a5,0x60c6,0x70e7,
			0x8108,0x9129,0xa14a,0xb16b,0xc18c,0xd1ad,0xe1ce,0xf1ef,
			0x1231,0x0210,0x3273,0x2252,0x52b5,0x4294,0x72f7,0x62d6,
			0x9339,0x8318,0xb37b,0xa35a,0xd3bd,0xc39c,0xf3ff,0xe3de,
			0x2462,0x3443,0x0420,0x1401,0x64e6,0x74c7,0x44a4,0x5485,
			0xa56a,0xb54b,0x8528,0x9509,0xe5ee,0xf5cf,0xc5ac,0xd58d,
			0x3653,0x2672,0x1611,0x0630,0x76d7,0x66f6,0x5695,0x46b4,
			0xb75b,0xa77a,0x9719,0x8738,0xf7df,0xe7fe,0xd79d,0xc7bc,
			0x48c4,0x58e5,0x6886,0x78a7,0x0840,0x1861,0x2802,0x3823,
			0xc9cc,0xd9ed,0xe98e,0xf9af,0x8948,0x9969,0xa90a,0xb92b,
			0x5af5,0x4ad4,0x7ab7,0x6a96,0x1a71,0x0a50,0x3a33,0x2a12,
			0xdbfd,0xcbdc,0xfbbf,0xeb9e,0x9b79,0x8b58,0xbb3b,0xab1a,
			0x6ca6,0x7c87,0x4ce4,0x5cc5,0x2c22,0x3c03,0x0c60,0x1c41,
			0xedae,0xfd8f,0xcdec,0xddcd,0xad2a,0xbd0b,0x8d68,0x9d49,
			0x7e97,0x6eb6,0x5ed5,0x4ef4,0x3e13,0x2e32,0x1e51,0x0e70,
			0xff9f,0xefbe,0xdfdd,0xcffc,0xbf1b,0xaf3a,0x9f59,0x8f78,
			0x9188,0x81a9,0xb1ca,0xa1eb,0xd10c,0xc12d,0xf14e,0xe16f,
			0x1080,0x00a1,0x30c2,0x20e3,0x5004,0x4025,0x7046,0x6067,
			0x83b9,0x9398,0xa3fb,0xb3da,0xc33d,0xd31c,0xe37f,0xf35e,
			0x02b1,0x1290,0x22f3,0x32d2,0x4235,0x5214,0x6277,0x7256,
			0xb5ea,0xa5cb,0x95a8,0x8589,0xf56e,0xe54f,0xd52c,0xc50d,
			0x34e2,0x24c3,0x14a0,0x0481,0x7466,0x6447,0x5424,0x4405,
			0xa7db,0xb7fa,0x8799,0x97b8,0xe75f,0xf77e,0xc71d,0xd73c,
			0x26d3,0x36f2,0x0691,0x16b0,0x6657,0x7676,0x4615,0x5634,
			0xd94c,0xc96d,0xf90e,0xe92f,0x99c8,0x89e9,0xb98a,0xa9ab,
			0x5844,0x4865,0x7806,0x6827,0x18c0,0x08e1,0x3882,0x28a3,
			0xcb7d,0xdb5c,0xeb3f,0xfb1e,0x8bf9,0x9bd8,0xabbb,0xbb9a,
			0x4a75,0x5a54,0x6a37,0x7a16,0x0af1,0x1ad0,0x2ab3,0x3a92,
			0xfd2e,0xed0f,0xdd6c,0xcd4d,0xbdaa,0xad8b,0x9de8,0x8dc9,
			0x7c26,0x6c07,0x5c64,0x4c45,0x3ca2,0x2c83,0x1ce0,0x0cc1,
			0xef1f,0xff3e,0xcf5d,0xdf7c,0xaf9b,0xbfba,0x8fd9,0x9ff8,
			0x6e17,0x7e36,0x4e55,0x5e74,0x2e93,0x3eb2,0x0ed1,0x1ef0
	};
	
	/**
	 * The constant STX.
	 */
	public static final byte STX                        = (byte)0x02;
	/**
	 * The constant ETX.
	 */
	public static final byte ETX                        = (byte)0x03;
	/**
	 * The constant SET_DRAW.
	 */
	public static final byte SET_DRAW                   = (byte)0x11;
	/**
	 * The constant SET_TEXT.
	 */
	public static final byte SET_TEXT                   = (byte)0x21;
	/**
	 * The constant SET_TEXT_ACTION.
	 */
	public static final byte SET_TEXT_ACTION            = (byte)0x22;
	/**
	 * The constant SET_SCR_CLEAR.
	 */
	public static final byte SET_SCR_CLEAR              = (byte)0x31;
	/**
	 * The constant SET_SCR_COLOR.
	 */
	public static final byte SET_SCR_COLOR              = (byte)0x41;
	
	/**
	 * The constant SET_TEXT_EACH.
	 */
	public static final byte SET_TEXT_EACH              = (byte)0x51;
	/**
	 * The constant SET_TEXT_EACH_ACTION.
	 */
	public static final byte SET_TEXT_EACH_ACTION       = (byte)0x52;
	
	/**
	 * The constant SAVE_TEXT.
	 */
	public static final byte SAVE_TEXT                  = (byte)0xa1;
	/**
	 * The constant SAVE_TEXT_ACTION.
	 */
	public static final byte SAVE_TEXT_ACTION           = (byte)(SAVE_TEXT + 1);
	/**
	 * The constant SAVE_COLOR_TEXT.
	 */
	public static final byte SAVE_COLOR_TEXT            = (byte)(SAVE_TEXT_ACTION + 1);
	/**
	 * The constant SAVE_COLOR_TEXT_ACTION.
	 */
	public static final byte SAVE_COLOR_TEXT_ACTION     = (byte)(SAVE_COLOR_TEXT + 1);
	
	/**
	 * The constant SET_FIRM_UPDATE.
	 */
	public static final byte SET_FIRM_UPDATE            = (byte)0x61;
	/**
	 * The constant GET_FIRM_UPDATE.
	 */
	public static final byte GET_FIRM_UPDATE            = (byte)0x62;
	
	/**
	 * The constant SET_PERFORM_MODE.
	 */
	public static final byte SET_PERFORM_MODE            = (byte)0x71;
	
	
	/**
	 * The constant CMD_ACT_LEFT_RIGHT.
	 */
	public static final byte CMD_ACT_LEFT_RIGHT         = (byte)0x01;
	/**
	 * The constant CMD_ACT_RIGHT_LEFT.
	 */
	public static final byte CMD_ACT_RIGHT_LEFT         = (byte)0x02;
	/**
	 * The constant CMD_ACT_TOP_DOWN.
	 */
	public static final byte CMD_ACT_TOP_DOWN           = (byte)0x03;
	/**
	 * The constant CMD_ACT_DOWN_TOP.
	 */
	public static final byte CMD_ACT_DOWN_TOP           = (byte)0x04;
	/**
	 * The constant CMD_ACT_BLINK.
	 */
	public static final byte CMD_ACT_BLINK              = (byte)0x05;
	
	/**
	 * The constant SIZE_STX.
	 */
	public static final int SIZE_STX                    = 1;
	/**
	 * The constant SIZE_LEN1.
	 */
	public static final int SIZE_LEN1                   = 2;
	/**
	 * The constant SIZE_LEN2.
	 */
	public static final int SIZE_LEN2                   = 2;
	/**
	 * The constant SIZE_CMD.
	 */
	public static final int SIZE_CMD                    = 1;
	/**
	 * The constant SIZE_CRC.
	 */
	public static final int SIZE_CRC                    = 2;
	/**
	 * The constant SIZE_ETX.
	 */
	public static final int SIZE_ETX                    = 1;
	/**
	 * The constant SIZE_FIELD.
	 */
	public static final int SIZE_FIELD                  = 5;
	/**
	 * The constant SIZE_PAGE.
	 */
	public static final int SIZE_PAGE                   = 1;
	/**
	 * The constant SIZE_SET_DRAW_FIELD.
	 */
	public static final int SIZE_SET_DRAW_FIELD         = SIZE_FIELD + SIZE_PAGE + SIZE_LEN1;
	/**
	 * The constant SIZE_WIFI_SSID.
	 */
	public static final int SIZE_WIFI_SSID              = 1;
	/**
	 * The constant SIZE_WIFI_PW.
	 */
	public static final int SIZE_WIFI_PW                = 1;
	/**
	 * The constant SIZE_WIFI_STATUS.
	 */
	public static final int SIZE_WIFI_STATUS            = 1;
	/**
	 * The constant SIZE_PKT_DATA.
	 */
// STX + DataLength size(2byte fix) + Cmd + dataLen +  CRC + ETX
	public static final int SIZE_PKT_DATA               =
			SIZE_STX + SIZE_LEN1 + SIZE_LEN2 + SIZE_CMD + SIZE_CRC + SIZE_ETX;
	/**
	 * The constant SIZE_FONT_BG_COLOR.
	 */
// (1+ 2 + 1 + 2 + 1);
	// font color + screen color
	public static final int SIZE_FONT_BG_COLOR = 6;
	/**
	 * The constant SIZE_PAGE_NUM.
	 */
// page number size
	public static final int SIZE_PAGE_NUM   	        = 1;
	/**
	 * The constant SIZE_TEXT_LEN.
	 */
// Text Size
	public static final int SIZE_TEXT_LEN          	    = 1;
	/**
	 * The constant SIZE_FONT.
	 */
	public static final int SIZE_FONT             	    = 1;
	/**
	 * The constant SIZE_TEXT_COLOR.
	 */
	public static final int SIZE_TEXT_COLOR          	= 3;
	/**
	 * The constant SIZE_STR_LEN.
	 */
// string length size
	public static final int SIZE_STR_LEN	   	        = 1;
	/**
	 * The constant SIZE_DATA_LEN.
	 */
// data length size
	public static final int SIZE_DATA_LEN	   	        = 2;
	/**
	 * The constant DOT_LEN.
	 */
// dot length size
	public static final int DOT_LEN	   	                = 2;
	/**
	 * The constant SIZE_ACTION_TIME.
	 */
// Action(action, time)
	public static final int SIZE_ACTION_TIME            = 3;
	/**
	 * The constant SIZE_PERFORM_ID.
	 */
	public static final int SIZE_PERFORM_ID             = 1;
	/**
	 * The constant SIZE_PERFORM_CHANNEL.
	 */
	public static final int SIZE_PERFORM_CHANNEL        = 1;
	
	
	/**
	 * The constant IDX_STX.
	 */
	public static final int IDX_STX                     = 0;
	/**
	 * The constant IDX_LEN.
	 */
	public static final int IDX_LEN                     = IDX_STX + SIZE_STX;
	/**
	 * The constant IDX_CMD.
	 */
	public static final int IDX_CMD                     = IDX_LEN + SIZE_LEN1 + SIZE_LEN2;
	/**
	 * The constant IDX_CRC.
	 */
	public static final int IDX_CRC                     = IDX_CMD + SIZE_CMD;
	/**
	 * The constant IDX_ETX.
	 */
	public static final int IDX_ETX                     = IDX_CRC + SIZE_CRC;
	
	/**
	 * The constant IDX_PERFORM_CHANNEL.
	 */
	public static final int IDX_PERFORM_CHANNEL         = 0;
	/**
	 * The constant IDX_PERFORM_ID.
	 */
	public static final int IDX_PERFORM_ID              = 1;
	
	/**
	 * The constant SIZE_MULTI_TEXT_COLOR.
	 */
// Multi Text
	public static final int SIZE_MULTI_TEXT_COLOR           = 3;
	/**
	 * The constant SIZE_MULTI_TEXT_FONT_SIZE.
	 */
	public static final int SIZE_MULTI_TEXT_FONT_SIZE       = 1;
	/**
	 * The constant SIZE_MULTI_TEXT_GROUP_COUNT.
	 */
	public static final int SIZE_MULTI_TEXT_GROUP_COUNT     = 1;
	/**
	 * The constant SIZE_MULTI_TEXT_PREFIX.
	 */
	public static final int SIZE_MULTI_TEXT_PREFIX          = SIZE_PAGE_NUM + SIZE_MULTI_TEXT_COLOR + SIZE_MULTI_TEXT_FONT_SIZE + SIZE_MULTI_TEXT_GROUP_COUNT;
	
	/**
	 * The constant IDX_MULTI_TEXT_COLOR.
	 */
	public static final int IDX_MULTI_TEXT_COLOR                   = 0;
	/**
	 * The constant IDX_MULTI_TEXT_FONT_SIZE.
	 */
	public static final int IDX_MULTI_TEXT_FONT_SIZE               = 3;
	/**
	 * The constant IDX_MULTI_TEXT_GROUP_CNT.
	 */
	public static final int IDX_MULTI_TEXT_GROUP_CNT               = 4;
	
	/**
	 * The constant ACTION_TIME.
	 */
	public static final int ACTION_TIME                 = 1000;
	
	/**
	 * The constant STATUS_DISCONNECT.
	 */
	public static final int STATUS_DISCONNECT           = 0;
	/**
	 * The constant STATUS_CONNECT.
	 */
	public static final int STATUS_CONNECT              = 1;
}
