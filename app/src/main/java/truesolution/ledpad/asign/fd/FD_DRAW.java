package truesolution.ledpad.asign.fd;

import android.graphics.Bitmap;

/**
 * Created by TCH on 2020. 7. 14.
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020. 7. 14.
 */
public class FD_DRAW {
	public static final Bitmap.CompressFormat MSAVE_FILE_FORMAT     = Bitmap.CompressFormat.JPEG;
	public static final String MSAVE_FILE_FORMAT_STR                = ".bmp";
	public static final int MSAVE_FILE_QUALITY                      = 100;
	
	public static final int MCOLOR8                 = 255;
	
	public static final int SIZE_32                 = 32;
	public static final int SIZE_64                 = 64;
	public static final int SIZE_128                = 128;
	
	public static final int SIZE_32_32              = 0;
	public static final int SIZE_32_64              = 1;
	public static final int SIZE_64_32              = 2;
	public static final int SIZE_64_64              = 3;
	public static final int SIZE_128_32             = 4;
	
	public static final int PAGE_1                  = 0;
	public static final int PAGE_2                  = 1;
	public static final int PAGE_3                  = 2;
	public static final int PAGE_4                  = 3;
	public static final int PAGE_5                  = 4;
	
	public static final int MAX_DRAW_PAGE           = 5;
	public static final int MAX_TEXT_PAGE           = 5;
	
	public static final int TAB_MENU_PEN            = 0;
	public static final int TAB_MENU_ERASER         = 1;
	public static final int TAB_MENU_FILL           = 2;
	public static final int TAB_MENU_SPOID          = 3;
	public static final int TAB_MENU_TEXT           = 4;
	public static final int TAB_MENU_ACTION         = 5;
	
	public static final int ACTION_DEFAULT          = 0;
	public static final int ACTION_LEFT_TO_RIGHT    = 1;
	public static final int ACTION_RIGHT_TO_LEFT    = 2;
	public static final int ACTION_UP_TO_DOWN       = 3;
	public static final int ACTION_DOWN_TO_UP       = 4;
	
	public static final int FONT_SIZE_IDX1          = 0;
	public static final int FONT_SIZE_IDX2          = 1;
	public static final int FONT_SIZE_IDX3          = 2;
	public static final int FONT_SIZE_IDX4          = 3;
	public static final int FONT_SIZE_IDX5          = 4;
	public static final int FONT_SIZE_IDX6          = 5;
	public static final int FONT_SIZE_IDX7          = 6;
	
	public static final int DOT_SIZE1               = 1;
	public static final int DOT_SIZE2               = 2;
	public static final int DOT_SIZE3               = 3;
	
	public static final int IMG_SAMPLE_SIZE         = 4;
	public static final String PHONE_GALLERY_TYPE   = "image/*";
	public static final String SPLIT_TOKEN          = ";";
}
