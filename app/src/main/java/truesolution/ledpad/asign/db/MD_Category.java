package truesolution.ledpad.asign.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
@Entity
public class MD_Category {
	/**
	 * The Idx.
	 */
	@PrimaryKey(autoGenerate = true)
	public int idx_;
	
	/**
	 * The M name.
	 */
	@ColumnInfo(name = "name_")
	public String mName;
	
	/**
	 * The M is img default.
	 */
	@ColumnInfo(name = "is_default_img")
	public boolean mIsImgDefault;
	
	/**
	 * The M sub idx.
	 */
	@ColumnInfo(name = "sub_idx")
	public int mSubIdx;
	
	/**
	 * The M res id.
	 */
	@ColumnInfo(name = "img_res_id")
	public int mResID;
}