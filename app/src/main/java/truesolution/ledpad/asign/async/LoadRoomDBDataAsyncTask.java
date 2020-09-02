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
public abstract class LoadRoomDBDataAsyncTask extends AsyncTask<Void, Void, Void> {
	public abstract void mResult(List<MD_Category> _c_list, List<MD_Emoticon> _e_list);
	
	private MAppDatabase mAppDatabase;
	private List<MD_Category> mCategoryList = new ArrayList<>();
	private List<MD_Emoticon> mEmoticonList = new ArrayList<>();
	private Activity mActivity;
	
	public LoadRoomDBDataAsyncTask(Activity _activity, MAppDatabase _md) {
		mActivity = _activity;
		mAppDatabase = _md;
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		// Room DB
		mCategoryList = mAppDatabase.mDAOHandler().mGetCategoryAll();
		
		// Category
		if(mCategoryList.size() > 0) {
			// TODO Test Code
//			for(int i = 0; i < mCategoryList.size(); i++) {
//				MDEBUG.debug("mCategoryList[" + i + "] : " + mCategoryList.get(i).mName);
//			}
		} else {
			// Insert Data
//			MDB_Init.MCATEGORY_TITLE = mContext.getResources().getStringArray(R.array.sa_category_title);
			for(int i = 0; i < MDB_FD.MCATEGORY_TITLE.length; i++) {
				MD_Category _md = new MD_Category();
				_md.mName = MDB_FD.MCATEGORY_TITLE[i];
				_md.mResID = MDB_FD.MCATEGORY_RES_ID[i];
				_md.mSubIdx = MDB_FD.MCATEGORY_TYPE[i];
				_md.mIsImgDefault = false;
				mAppDatabase.mDAOHandler().insertCategory(_md);
				MDEBUG.debug("- i : " + i);
			}
			
			mCategoryList = mAppDatabase.mDAOHandler().mGetCategoryAll();
			MDEBUG.debug("- mCategoryList size : " + mCategoryList.size());
		}
		
		// Emoticon
		mEmoticonList = mAppDatabase.mDAOHandler().mGetEmotionAll();
		if(mEmoticonList.size() > 0) {
			MDEBUG.debug("mEmoticonList.size() : " + mEmoticonList.size());
		} else {
			// Insert Data
//			MDB_Init.MCATEGORY_TITLE = mContext.getResources().getStringArray(R.array.sa_category_title);
//			MDEBUG.debug("MDB_FD.MEMOTICON_NAME.length : " + MDB_FD.MEMOTICON_NAME.length);
			MDEBUG.debug("MDB_FD.MEMOTICON_RES_ID.length : " + MDB_FD.MEMOTICON_RES_ID.length);
//			MDEBUG.debug("MDB_FD.MEMOTICON_CATEGORY_TYPE.length : " + MDB_FD.MEMOTICON_CATEGORY_TYPE.length);
			int _idx_cnt = 0;
			for(int i = 0; i < MDB_FD.MEMOTICON_NAME.length; i++) {
				MD_Emoticon _md = new MD_Emoticon();
				_md.mName = MDB_FD.MEMOTICON_NAME[i];
				_md.mImageResID = MDB_FD.MEMOTICON_RES_ID[i];
				_md.mCatergoryIdx = MDB_FD.MEMOTICON_CATEGORY_TYPE[i];
				_md.mCatergoryName = MDB_FD.MCATEGORY_TITLE[_md.mCatergoryIdx];
				_md.mIsLocalData = true;
				_md.mIsFavorite = false;
				_md.mDate = System.currentTimeMillis();
				_md.mIsOneEmoticon = true;
				
				if(_md.mCatergoryIdx == MDB_FD.CATEGORY_IDX_ANIMATION) {
					_md.mIsOneEmoticon = false;
					_md.mEmoticonFilesPath = MDB_FD.MANIMATION_EMOTICON_LIST[_idx_cnt];
					MDEBUG.debug("_md.mEmoticonFilesPath : " + _md.mEmoticonFilesPath);
					_idx_cnt++;
				}
				mAppDatabase.mDAOHandler().insertEmoticon(_md);
			}
			
			mEmoticonList = mAppDatabase.mDAOHandler().mGetEmotionAll();
			MDEBUG.debug("- mEmoticonList size : " + mEmoticonList.size());
		}
		return null;
	}
	
	@Override
	public void onPostExecute(Void _void) {
		mResult(mCategoryList, mEmoticonList);
	}
}
