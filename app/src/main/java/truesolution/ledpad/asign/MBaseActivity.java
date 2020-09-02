package truesolution.ledpad.asign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import truesolution.ledpad.asign.dialog.MMessageDialog;
import truesolution.ledpad.asign.dialog.MProgressDialog;
import truesolution.ledpad.asign.fd.FD_DELAY;

/**
 * MBaseActivity
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020-07-01
 */
public abstract class MBaseActivity extends AppCompatActivity {
	/**
	 * Decor View
	 */
	public View mDecorView;

	/**
	 * Get Error Message
	 * @return
	 */
	public abstract String mGetErrorMessage();
	
	/**
	 * On Confirm
	 */
	public abstract void mOnConfirm();
	
	/**
	 * On Yes
	 */
	public abstract void mOnYes();
	
	/**
	 * On Cancel
	 */
	public abstract void mOnCancel();
	
	/**
	 * Initialize
	 */
	public abstract void mInit();
	
	/**
	 * Progress Bar
	 */
	public MProgressDialog mProgressDialog;
	
	/**
	 * Message PopUp Dialog
	 */
	public MMessageDialog mMessageDialog;
	
	/**
	 * Handler
	 */
	public Handler mHandler = new Handler();
	
	/**
	 * Finish
	 */
	public boolean mIsFinish			= false;

	/**
	 * Pause
	 */
	public boolean mIsPause				= false;
	
	/**
	 * Reload
	 */
	public boolean mIsReload			= false;
	
	/**
	 * Activity Move
	 */
	public boolean mIsActivityMove	= false;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		mDecorView = getWindow().getDecorView();
//		mNavigationHidden();
		
		// Create Progress Bar
		mCreateProgressBar();
		
		// Create Message Dialog
		mCreateMessageDialog();
		
	}
	
	/**
	 * Create ProgressBar Dialog
	 */
	private void mCreateProgressBar() {
		mProgressDialog = new MProgressDialog(this);
	}
	
	/**
	 * Create Message Dialog
	 */
	private void mCreateMessageDialog() {
		mMessageDialog = new MyMessageDialog(this);
	}
	
	
	/**
	 * mShowProgress
	 *
	 * @author think.code.help@gmail.com
	 * @version 1.0
	 * @since 2018. 5. 13.
	 *
	 */
	public void mShowProgress() {
		if(mProgressDialog.isShowing())
			mProgressDialog.dismiss();
		
		mProgressDialog = mProgressDialog.show(this, "", "", true, true, null);
	}
	
	/**
	 * mCancelProgress
	 *
	 * @author think.code.help@gmail.com
	 * @version 1.0
	 * @since 2018. 5. 13.
	 *
	 */
	public void mCancelProgress() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mProgressDialog.dismiss();
			}
		}, FD_DELAY.MIN_DISPLAY);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// Null ProgressDialog
		if(mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
		
		// Message Dialog
		if(mMessageDialog != null) {
			mMessageDialog = null;
		}
		
	}
	
	/**
	 * Show Message Dialog
	 *
	 * @param _msg
	 * @param _is_cancel
	 */
	public void mShowMessageDialog(String _msg, boolean _is_cancel) {
		mMessageDialog.mShow(_msg, _is_cancel);
	}
	
	/**
	 * Show Message Dialog
	 *
	 * @param _msg_id
	 * @param _is_cancel
	 */
	public void mShowMessageDialog(int _msg_id, boolean _is_cancel) {
		mMessageDialog.mShow(getResources().getString(_msg_id), _is_cancel);
	}
	
	
	/**
	 * Show Toast Message
	 *
	 * @param _text
	 */
	public void mShowToast(String _text) {
		Toast.makeText(getBaseContext(), _text, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Show Toast Message
	 * @param _text_id
	 */
	public void mShowToast(int _text_id) {
		Toast.makeText(getBaseContext(), _text_id, Toast.LENGTH_SHORT).show();
	}
	
	public class MyMessageDialog extends MMessageDialog {
		public MyMessageDialog(Context context) {
			super(context);
		}
		
		/**
		 * Yes Event
		 */
		@Override
		public void mOnYes() {
			MBaseActivity.this.mOnYes();
		}
		
		/**
		 * Confirm Event
		 */
		@Override
		public void mOnConfirm() {
//			mNavigationHidden();
			MBaseActivity.this.mOnConfirm();
		}
		
		/**
		 * Cancel Event
		 */
		@Override
		public void mOnCancel() {
			MBaseActivity.this.mOnCancel();
		}
	}
	
	/**
	 * mStartActivity
	 *
	 * @param _class
	 */
	public void mStartActivity(Class _class) {
		Intent _intent = new Intent(this, _class);
		startActivity(_intent);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onPause() {
		super.onPause();

		mIsPause = true;
	}

	@Override
	public void onResume() {
		super.onResume();

//		mNavigationHidden();
		mIsPause = false;
		MDEBUG.debug("onResume!!!!!!");
	}

	/**
	 * Navigation Bar Hidden
	 */
	public void mNavigationHidden() {
		int _options =
//				View.SYSTEM_UI_FLAG_IMMERSIVE
				View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						// Set the content to appear under the system bars so that the
						// content doesn't resize when the system bars hide and show.
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						// Hide the nav bar and status bar
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN;
		
		mDecorView.setSystemUiVisibility(_options);
		mDecorView.invalidate();
	}
}
