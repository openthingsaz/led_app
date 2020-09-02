package truesolution.ledpad.asign.fragment.str;

import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.app.MAPP;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_GalleryCategory {
	public int mIDX;
	public String mName;
	public int mSubIDX;
	public String mDescrition;
	public int mImgResID;
	
	public STR_GalleryCategory() {
		mInit();
	}
	
	public void mInit() {
		mIDX = MAPP.ERROR_;
		mSubIDX = MAPP.ERROR_;
		mName = "";
		mDescrition = "";
		mImgResID = MAPP.ERROR_;
	}
	
	public void mSetData(STR_GalleryCategory _str) {
		mSetData(_str.mIDX, _str.mSubIDX, _str.mName, _str.mDescrition, _str.mImgResID);
	}
	
	public void mSetData(int _idx, int _sub_idx, String _name, String _descrition, int _res_id) {
		mIDX = _idx;
		mName = _name;
		mDescrition = _descrition;
		mImgResID = _res_id;
		mSubIDX = _sub_idx;
	}
}