package truesolution.ledpad.asign.fd;

import android.graphics.Bitmap;

/**
 * Created by TCH on 2020. 7. 14.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 7. 14.
 */
public class FD_DRAW {
	/**
	 * The constant MSAVE_FILE_FORMAT.
	 */
	public static final Bitmap.CompressFormat MSAVE_FILE_FORMAT     = Bitmap.CompressFormat.PNG;
	/**
	 * The constant MSAVE_FILE_FORMAT_STR.
	 */
	public static final String MSAVE_FILE_FORMAT_STR                = ".bmp";
	/**
	 * The constant MSAVE_FILE_QUALITY.
	 */
	public static final int MSAVE_FILE_QUALITY                      = 0;
	
	/**
	 * The constant MCOLOR8.
	 */
	public static final int MCOLOR8                 = 255;
	
	/**
	 * The constant SIZE_32.
	 */
	public static final int SIZE_32                 = 32;
	/**
	 * The constant SIZE_64.
	 */
	public static final int SIZE_64                 = 64;
	/**
	 * The constant SIZE_128.
	 */
	public static final int SIZE_128                = 128;
	
	/**
	 * The constant SIZE_32_32.
	 */
	public static final int SIZE_32_32              = 0;
	/**
	 * The constant SIZE_32_64.
	 */
	public static final int SIZE_32_64              = 1;
	/**
	 * The constant SIZE_64_32.
	 */
	public static final int SIZE_64_32              = 2;
	/**
	 * The constant SIZE_64_64.
	 */
	public static final int SIZE_64_64              = 3;
	/**
	 * The constant SIZE_128_32.
	 */
	public static final int SIZE_128_32             = 4;
	
	/**
	 * The constant PAGE_1.
	 */
	public static final int PAGE_1                  = 0;
	/**
	 * The constant PAGE_2.
	 */
	public static final int PAGE_2                  = 1;
	/**
	 * The constant PAGE_3.
	 */
	public static final int PAGE_3                  = 2;
	/**
	 * The constant PAGE_4.
	 */
	public static final int PAGE_4                  = 3;
	/**
	 * The constant PAGE_5.
	 */
	public static final int PAGE_5                  = 4;
	
	/**
	 * The constant MAX_DRAW_PAGE.
	 */
	public static final int MAX_DRAW_PAGE           = 5;
	/**
	 * The constant MAX_TEXT_PAGE.
	 */
	public static final int MAX_TEXT_PAGE           = 5;
	
	/**
	 * The constant TAB_MENU_PEN.
	 */
	public static final int TAB_MENU_PEN            = 0;
	/**
	 * The constant TAB_MENU_ERASER.
	 */
	public static final int TAB_MENU_ERASER         = 1;
	/**
	 * The constant TAB_MENU_FILL.
	 */
	public static final int TAB_MENU_FILL           = 2;
	/**
	 * The constant TAB_MENU_SPOID.
	 */
	public static final int TAB_MENU_SPOID          = 3;
	/**
	 * The constant TAB_MENU_TEXT.
	 */
	public static final int TAB_MENU_TEXT           = 4;
	/**
	 * The constant TAB_MENU_ACTION.
	 */
	public static final int TAB_MENU_ACTION         = 5;
	
	/**
	 * The constant ACTION_DEFAULT.
	 */
	public static final int ACTION_DEFAULT          = 0;
	/**
	 * The constant ACTION_LEFT_TO_RIGHT.
	 */
	public static final int ACTION_LEFT_TO_RIGHT    = 1;
	/**
	 * The constant ACTION_RIGHT_TO_LEFT.
	 */
	public static final int ACTION_RIGHT_TO_LEFT    = 2;
	/**
	 * The constant ACTION_UP_TO_DOWN.
	 */
	public static final int ACTION_UP_TO_DOWN       = 3;
	/**
	 * The constant ACTION_DOWN_TO_UP.
	 */
	public static final int ACTION_DOWN_TO_UP       = 4;
	/**
	 * The constant ACTION_DOWN_BLINK.
	 */
	public static final int ACTION_DOWN_BLINK       = 5;
	
	/**
	 * The constant FONT_SIZE_IDX1.
	 */
	public static final int FONT_SIZE_IDX1          = 0;
	/**
	 * The constant FONT_SIZE_IDX2.
	 */
	public static final int FONT_SIZE_IDX2          = 1;
	/**
	 * The constant FONT_SIZE_IDX3.
	 */
	public static final int FONT_SIZE_IDX3          = 2;
	/**
	 * The constant FONT_SIZE_IDX4.
	 */
	public static final int FONT_SIZE_IDX4          = 3;
	/**
	 * The constant FONT_SIZE_IDX5.
	 */
	public static final int FONT_SIZE_IDX5          = 4;
	/**
	 * The constant FONT_SIZE_IDX6.
	 */
	public static final int FONT_SIZE_IDX6          = 5;
	/**
	 * The constant FONT_SIZE_IDX7.
	 */
	public static final int FONT_SIZE_IDX7          = 6;
	
	/**
	 * The constant DOT_SIZE1.
	 */
	public static final int DOT_SIZE1               = 1;
	/**
	 * The constant DOT_SIZE2.
	 */
	public static final int DOT_SIZE2               = 2;
	/**
	 * The constant DOT_SIZE3.
	 */
	public static final int DOT_SIZE3               = 3;
	
	/**
	 * The constant IMG_SAMPLE_SIZE.
	 */
	public static final int IMG_SAMPLE_SIZE         = 1;
	/**
	 * The constant IMG_SAMPLE_SIZE_DOWN.
	 */
	public static final int IMG_SAMPLE_SIZE_DOWN    = 4;
	/**
	 * The constant CHECK_IMG_FILE_SIZE.
	 */
	public static final long CHECK_IMG_FILE_SIZE    = 1024 * 1024;
	/**
	 * The constant PHONE_GALLERY_TYPE.
	 */
	public static final String PHONE_GALLERY_TYPE   = "image/*";
	/**
	 * The constant SPLIT_TOKEN.
	 */
	public static final String SPLIT_TOKEN          = ";";
}
