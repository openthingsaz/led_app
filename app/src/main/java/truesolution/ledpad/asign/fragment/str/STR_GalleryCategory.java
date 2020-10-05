package truesolution.ledpad.asign.fragment.str;

import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.app.MAPP;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_GalleryCategory {
	/**
	 * The M idx.
	 */
	public int mIDX;
	/**
	 * The M name.
	 */
	public String mName;
	/**
	 * The M sub idx.
	 */
	public int mSubIDX;
	/**
	 * The M descrition.
	 */
	public String mDescrition;
	/**
	 * The M img res id.
	 */
	public int mImgResID;
	
	/**
	 * Instantiates a new Str gallery category.
	 */
	public STR_GalleryCategory() {
		mInit();
	}
	
	/**
	 * M init.
	 */
	public void mInit() {
		mIDX = MAPP.ERROR_;
		mSubIDX = MAPP.ERROR_;
		mName = "";
		mDescrition = "";
		mImgResID = MAPP.ERROR_;
	}
	
	/**
	 * M set data.
	 *
	 * @param _str the str
	 */
	public void mSetData(STR_GalleryCategory _str) {
		mSetData(_str.mIDX, _str.mSubIDX, _str.mName, _str.mDescrition, _str.mImgResID);
	}
	
	/**
	 * M set data.
	 *
	 * @param _idx        the idx
	 * @param _sub_idx    the sub idx
	 * @param _name       the name
	 * @param _descrition the descrition
	 * @param _res_id     the res id
	 */
	public void mSetData(int _idx, int _sub_idx, String _name, String _descrition, int _res_id) {
		mIDX = _idx;
		mName = _name;
		mDescrition = _descrition;
		mImgResID = _res_id;
		mSubIDX = _sub_idx;
	}
}