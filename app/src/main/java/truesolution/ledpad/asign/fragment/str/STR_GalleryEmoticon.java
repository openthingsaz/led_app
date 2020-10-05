package truesolution.ledpad.asign.fragment.str;

import androidx.room.ColumnInfo;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.db.MD_Emoticon;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_GalleryEmoticon {
	/**
	 * The M idx.
	 */
	public int mIDX;
	/**
	 * The M name.
	 */
	public String mName;
	/**
	 * The M catergory idx.
	 */
	public int mCatergoryIdx;
	/**
	 * The M catergory name.
	 */
	public String mCatergoryName;
	/**
	 * The M is one emoticon.
	 */
	public boolean mIsOneEmoticon;
	/**
	 * The M emoticon files path.
	 */
	public String mEmoticonFilesPath;
	/**
	 * The M image res id.
	 */
	public int mImageResID;
	/**
	 * The M is local data.
	 */
	public boolean mIsLocalData;
	/**
	 * The M is favorite.
	 */
	public boolean mIsFavorite;
	/**
	 * The M description.
	 */
	public String mDescription;
	/**
	 * The M date.
	 */
	public long mDate;
	
	/**
	 * The M is info layout show.
	 */
	public boolean mIsInfoLayoutShow = true;
	
	/**
	 * Instantiates a new Str gallery emoticon.
	 */
	public STR_GalleryEmoticon() {
		mInit();
	}
	
	/**
	 * M init.
	 */
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
	 * M set data.
	 *
	 * @param _str the str
	 */
	public void mSetData(STR_GalleryEmoticon _str) {
		mSetData(_str.mIDX, _str.mName,
				_str.mCatergoryIdx, _str.mCatergoryName,
				_str.mIsOneEmoticon, _str.mEmoticonFilesPath, _str.mImageResID,
				_str.mIsLocalData, _str.mIsFavorite, _str.mDescription, _str.mDate);
	}
	
	/**
	 * M set data.
	 *
	 * @param _idx                 the idx
	 * @param _name                the name
	 * @param _category_idx        the category idx
	 * @param _category_name       the category name
	 * @param _is_one_emoticon     the is one emoticon
	 * @param _emoticon_files_path the emoticon files path
	 * @param _res_id              the res id
	 * @param _is_local_data       the is local data
	 * @param _is_favorite         the is favorite
	 * @param _descrition          the descrition
	 * @param _date                the date
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