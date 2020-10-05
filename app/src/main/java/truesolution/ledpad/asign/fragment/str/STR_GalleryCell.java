package truesolution.ledpad.asign.fragment.str;

import truesolution.ledpad.asign.app.MAPP;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_GalleryCell {
	/**
	 * The M category.
	 */
	public STR_GalleryCategory mCategory;
	/**
	 * The M emoticon.
	 */
	public STR_GalleryEmoticon mEmoticon;
	/**
	 * The M is category.
	 */
	public boolean mIsCategory;
	/**
	 * The M cell position.
	 */
	public int mCellPosition                    = MAPP.ERROR_;
	
	/**
	 * Instantiates a new Str gallery cell.
	 */
	public STR_GalleryCell() {
		mCategory = new STR_GalleryCategory();
		mEmoticon = new STR_GalleryEmoticon();
	}
	
	/**
	 * M set category data.
	 *
	 * @param _str the str
	 */
	public void mSetCategoryData(STR_GalleryCategory _str) {
		mCategory.mSetData(_str);
	}
	
	/**
	 * M set category data.
	 *
	 * @param _idx        the idx
	 * @param _sub_idx    the sub idx
	 * @param _name       the name
	 * @param _descrition the descrition
	 * @param _res_id     the res id
	 */
	public void mSetCategoryData(int _idx, int _sub_idx, String _name, String _descrition, int _res_id) {
		mCategory.mSetData(_idx, _sub_idx, _name, _descrition, _res_id);
	}
	
	/**
	 * M set emoticon data.
	 *
	 * @param _str the str
	 */
	public void mSetEmoticonData(STR_GalleryEmoticon _str) {
		mEmoticon.mSetData(_str);
	}
	
	/**
	 * M set emoticon data.
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
	public void mSetEmoticonData(int _idx, String _name,
	                             int _category_idx, String _category_name,
	                             boolean _is_one_emoticon, String _emoticon_files_path, int _res_id,
	                             boolean _is_local_data, boolean _is_favorite, String _descrition, long _date) {
		mEmoticon.mSetData(_idx, _name,
				_category_idx, _category_name,
				_is_one_emoticon, _emoticon_files_path, _res_id,
				_is_local_data, _is_favorite, _descrition, _date);
	}
}