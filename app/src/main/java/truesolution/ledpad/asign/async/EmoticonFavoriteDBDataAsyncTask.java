package truesolution.ledpad.asign.async;

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
public abstract class EmoticonFavoriteDBDataAsyncTask extends AsyncTask<Void, Void, Void> {
	/**
	 * M result.
	 */
	public abstract void mResult();
	
	private MAppDatabase mAppDatabase;
	private int mIDX;
	private boolean mIsFavorite;
	
	/**
	 * Instantiates a new Emoticon favorite db data async task.
	 *
	 * @param _mad the mad
	 * @param _idx the idx
	 * @param _st  the st
	 */
	public EmoticonFavoriteDBDataAsyncTask(MAppDatabase _mad, int _idx, boolean _st) {
		mAppDatabase = _mad;
		mIDX = _idx;
		mIsFavorite = _st;
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		// Category
		mAppDatabase.mDAOHandler().favorite(mIsFavorite, mIDX);
		return null;
	}
	
	@Override
	public void onPostExecute(Void _void) {
		mResult();
	}
}
