package truesolution.ledpad.asign.async;

import android.os.AsyncTask;

import truesolution.ledpad.asign.db.MAppDatabase;
import truesolution.ledpad.asign.db.MD_Category;
import truesolution.ledpad.asign.db.MD_Emoticon;

/**
 * Created by TCH on 2020/07/10
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/10
 */
public abstract class EmoticonDeleteDBDataAsyncTask extends AsyncTask<Void, Void, Void> {
	/**
	 * M result.
	 */
	public abstract void mResult();
	
	private MAppDatabase mAppDatabase;
	private MD_Emoticon mStrEmoticon;
	
	/**
	 * Instantiates a new Emoticon delete db data async task.
	 *
	 * @param _mad the mad
	 * @param _str the str
	 */
	public EmoticonDeleteDBDataAsyncTask(MAppDatabase _mad, MD_Emoticon _str) {
		mAppDatabase = _mad;
		mStrEmoticon = _str;
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		// Emoticon
		mAppDatabase.mDAOHandler().delete(mStrEmoticon);
		return null;
	}
	
	@Override
	public void onPostExecute(Void _void) {
		mResult();
	}
}
