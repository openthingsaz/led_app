package truesolution.ledpad.asign.fragment.str;

import androidx.room.ColumnInfo;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.db.MD_Emoticon;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_GalleryEmoticon {
	public int mIDX;
	public String mName;
	public int mCatergoryIdx;
	public String mCatergoryName;
	public boolean mIsOneEmoticon;
	public String mEmoticonFilesPath;
	public int mImageResID;
	public boolean mIsLocalData;
	public boolean mIsFavorite;
	public String mDescription;
	public long mDate;
	
	public boolean mIsInfoLayoutShow = true;
	
	public STR_GalleryEmoticon() {
		mInit();
	}
	
	public void mInit() {
		mIDX = MAPP.ERROR_;
		mName = "";
		mCatergoryIdx = MAPP.ERROR_;
		mCatergoryName = "";
		mIsInfoLayoutShow = true;
		mIsOneEmoticon = false;
		mEmoticonFilesPath = "";
		mImageResID = MAPP.ERROR_;
		mIsLocalData = false;
		mIsFavorite = false;
		mDescription = "";
		mDate = System.currentTimeMillis();
	}
	
	/**
	 *
	 * @param _str
	 */
	public void mSetData(STR_GalleryEmoticon _str) {
		mSetData(_str.mIDX, _str.mName,
				_str.mCatergoryIdx, _str.mCatergoryName,
				_str.mIsOneEmoticon, _str.mEmoticonFilesPath, _str.mImageResID,
				_str.mIsLocalData, _str.mIsFavorite, _str.mDescription, _str.mDate);
	}
	
	/**
	 *
	 * @param _idx
	 * @param _name
	 * @param _category_idx
	 * @param _category_name
	 * @param _is_one_emoticon
	 * @param _emoticon_files_path
	 * @param _res_id
	 * @param _is_local_data
	 * @param _is_favorite
	 * @param _descrition
	 * @param _date
	 */
	public void mSetData(int _idx, String _name,
	                     int _category_idx, String _category_name,
	                     boolean _is_one_emoticon, String _emoticon_files_path, int _res_id,
	                     boolean _is_local_data, boolean _is_favorite, String _descrition, long _date) {
		mIDX = _idx;
		mName = _name;
		mCatergoryIdx = _category_idx;
		mCatergoryName = _category_name;
		mIsOneEmoticon = _is_one_emoticon;
		mEmoticonFilesPath = _emoticon_files_path;
		mImageResID = _res_id;
		mIsLocalData = _is_local_data;
		mIsFavorite = _is_favorite;
		mDescription = _descrition;
		mDate = _date;
	}
}