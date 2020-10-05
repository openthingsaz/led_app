package truesolution.ledpad.asign.app;

import android.app.Application;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

import truesolution.ledpad.asign.db.MAppDatabase;
import truesolution.ledpad.asign.share.MShared;

/**
 * The type MAPP.
 */
public class MAPP extends Application {
	/**
	 * Init
	 */
	public static final int INIT_                   = 0;
	/**
	 * The constant SUCCESS_.
	 */
	public static final int SUCCESS_                = 1;
	/**
	 * The constant NONE_.
	 */
	public static final int NONE_                   = 0;
	/**
	 * The constant START_ALIVE.
	 */
	public static final int START_ALIVE             = 1;
	/**
	 * The constant ERROR_.
	 */
	public static final int ERROR_                  = -1;
	
	/**
	 * The constant STR_ERROR.
	 */
	public static final String STR_ERROR            = "-1";
	/**
	 * The constant STR_INIT.
	 */
	public static final String STR_INIT             = "0";
	/**
	 * The constant STR_.
	 */
	public static final String STR_                 = "";
	
	/**
	 * The constant mCategoryTitleList.
	 */
	public static ArrayList<String> mCategoryTitleList = new ArrayList<>();
	
	/**
	 * MediaPlayer Start
	 */
	public static boolean mIsStart          = false;
	
	/**
	 * DB
	 */
	public static MAppDatabase mAppDatabase;
	
	/**
	 * Shared
	 */
	public static MShared mShared;
	
	/**
	 * LCD Size and Half LCD Size
	 */
	public static int LCD_W,
	/**
	 * The Lcd h.
	 */
	LCD_H,
	/**
	 * The Lcd hw.
	 */
	LCD_HW,
	/**
	 * The Lcd hh.
	 */
	LCD_HH;
	
	/**
	 * 상태바를 제외한 영역
	 */
	public static int LCD_D_H;

	@Override
	public void onCreate() {
		super.onCreate();
		
		// Display Area
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		LCD_W = display.getWidth();
		LCD_H = display.getHeight();
		LCD_HW = LCD_W / 2;
		LCD_HH = LCD_H / 2;
		
		mShared = MShared.mGetInstance(this);
	}
}
