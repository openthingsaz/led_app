package truesolution.ledpad.asign.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;

import truesolution.ledpad.asign.R;

/**
 * MMessageDialog
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public abstract class MMessageDialog {
	/**
	 * Yes Event
	 */
	public abstract void mOnYes();
	
	/**
	 * Confirm Event
	 */
	public abstract void mOnConfirm();
	
	/**
	 * Cancel Event
	 */
	public abstract void mOnCancel();
	
	/**
	 * Context
	 */
	private Context mContext;
	
	/**
	 * Instantiates a new M message dialog.
	 *
	 * @param context the context
	 */
	public MMessageDialog(Context context) {
		mContext = context;
	}
	
	/**
	 * mMessageShow
	 *
	 * @param _msg       the msg
	 * @param _is_cancel the is cancel
	 *
	 * @author think.code.help @gmail.com
	 * @version 1.0
	 * @since 2020. 07. 01.
	 */
	public void mShow(String _msg, boolean _is_cancel) {
		/**
		 * AlertDialog
		 */
		AlertDialog.Builder mAlertBox = new AlertDialog.Builder(mContext);
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			mAlertBox = new AlertDialog.Builder(mContext);
		} else {
			mAlertBox = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT);
		}
		
		mAlertBox.setCancelable(false);
		mAlertBox.setMessage(_msg);
		
		if(_is_cancel) {
			mAlertBox.setPositiveButton(mContext.getResources().getString(R.string.pop_button_yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					mOnYes();
				}
			});
			
			mAlertBox.setNegativeButton(mContext.getResources().getString(R.string.pop_button_no), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					mOnCancel();
				}
			});
		} else {
			mAlertBox.setPositiveButton(mContext.getResources().getString(R.string.pop_button_ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					mOnConfirm();
				}
			});
		}
		mAlertBox.show();
		
//		final AlertDialog _dialog = mAlertBox.show();
//		_dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//				// Set the content to appear under the system bars so that the
//				// content doesn't resize when the system bars hide and show.
//				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//				// Hide the nav bar and status bar
//				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//				| View.SYSTEM_UI_FLAG_FULLSCREEN);
//		_dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
		
//		Handler _handler = new Handler();
//		_handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				int _options =
////				View.SYSTEM_UI_FLAG_IMMERSIVE
//						View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//								// Set the content to appear under the system bars so that the
//								// content doesn't resize when the system bars hide and show.
//								| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//								| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//								| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//								// Hide the nav bar and status bar
//								| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//								| View.SYSTEM_UI_FLAG_FULLSCREEN;
//
//				_dialog.getWindow().getDecorView().setSystemUiVisibility(_options);
//			}
//		}, 1);
	}
}
