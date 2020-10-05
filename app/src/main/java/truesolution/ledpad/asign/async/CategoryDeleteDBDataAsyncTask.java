package truesolution.ledpad.asign.async;

import android.app.Activity;
import android.os.AsyncTask;

import truesolution.ledpad.asign.db.MAppDatabase;
import truesolution.ledpad.asign.db.MD_Category;

/**
 * Created by TCH on 2020/07/10
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/10
 */
public abstract class CategoryDeleteDBDataAsyncTask extends AsyncTask<Void, Void, Void> {
	/**
	 * M result.
	 */
	public abstract void mResult();
	
	private MAppDatabase mAppDatabase;
	private MD_Category mStrCategory;
	
	/**
	 * Instantiates a new Category delete db data async task.
	 *
	 * @param _mad the mad
	 * @param _str the str
	 */
	public CategoryDeleteDBDataAsyncTask(MAppDatabase _mad, MD_Category _str) {
		mAppDatabase = _mad;
		mStrCategory = _str;
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		// Category
		mAppDatabase.mDAOHandler().delete(mStrCategory);
		return null;
	}
	
	@Override
	public void onPostExecute(Void _void) {
		mResult();
	}
}
