package truesolution.ledpad.asign.async;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.db.MAppDatabase;
import truesolution.ledpad.asign.db.MDB_FD;
import truesolution.ledpad.asign.db.MD_Category;
import truesolution.ledpad.asign.db.MD_Emoticon;

/**
 * Created by TCH on 2020/07/10
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/10
 */
public abstract class CategoryAddDBDataAsyncTask extends AsyncTask<Void, Void, Void> {
	public abstract void mResult();
	
	private MAppDatabase mAppDatabase;
	private MD_Category mStrCategory;
	private Activity mActivity;
	public CategoryAddDBDataAsyncTask(Activity _activity, MAppDatabase _mad, MD_Category _str) {
		mActivity = _activity;
		mAppDatabase = _mad;
		mStrCategory = _str;
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		// Category
		mAppDatabase.mDAOHandler().insertCategory(mStrCategory);
		return null;
	}
	
	@Override
	public void onPostExecute(Void _void) {
		mResult();
	}
}
