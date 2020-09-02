package truesolution.ledpad.asign.async;

import android.app.Activity;
import android.os.AsyncTask;

import truesolution.ledpad.asign.db.MAppDatabase;
import truesolution.ledpad.asign.db.MD_Category;

/**
 * Created by TCH on 2020/08/10
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/08/10
 */
public abstract class DrawFileAsyncTask extends AsyncTask<Void, Void, Void> {
	public abstract void mResult();
	
	private MAppDatabase mAppDatabase;
	private Activity mActivity;
	public DrawFileAsyncTask(Activity _activity, MAppDatabase _mad, MD_Category _str) {
		mActivity = _activity;
		mAppDatabase = _mad;
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		return null;
	}
	
	@Override
	public void onPostExecute(Void _void) {
		mResult();
	}
}
