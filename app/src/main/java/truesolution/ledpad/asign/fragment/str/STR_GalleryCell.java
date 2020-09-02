package truesolution.ledpad.asign.fragment.str;

import truesolution.ledpad.asign.app.MAPP;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_GalleryCell {
	public STR_GalleryCategory mCategory;
	public STR_GalleryEmoticon mEmoticon;
	public boolean mIsCategory;
	public int mCellPosition                    = MAPP.ERROR_;
	
	public STR_GalleryCell() {
		mCategory = new STR_GalleryCategory();
		mEmoticon = new STR_GalleryEmoticon();
	}
	
	public void mSetCategoryData(STR_GalleryCategory _str) {
		mCategory.mSetData(_str);
	}
	
	public void mSetCategoryData(int _idx, int _sub_idx, String _name, String _descrition, int _res_id) {
		mCategory.mSetData(_idx, _sub_idx, _name, _descrition, _res_id);
	}
	
	public void mSetEmoticonData(STR_GalleryEmoticon _str) {
		mEmoticon.mSetData(_str);
	}
	
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