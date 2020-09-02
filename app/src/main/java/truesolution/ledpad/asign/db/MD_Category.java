package truesolution.ledpad.asign.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
@Entity
public class MD_Category {
	@PrimaryKey(autoGenerate = true)
	public int idx_;
	
	@ColumnInfo(name = "name_")
	public String mName;
	
	@ColumnInfo(name = "is_default_img")
	public boolean mIsImgDefault;
	
	@ColumnInfo(name = "sub_idx")
	public int mSubIdx;
	
	@ColumnInfo(name = "img_res_id")
	public int mResID;
}