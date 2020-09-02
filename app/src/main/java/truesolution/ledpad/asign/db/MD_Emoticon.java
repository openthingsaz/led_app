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
public class MD_Emoticon {
	@PrimaryKey(autoGenerate = true)
	public int idx_;
	
	@ColumnInfo(name = "name_")
	public String mName;
	
	@ColumnInfo(name = "catergory_idx")
	public int mCatergoryIdx;
	
	@ColumnInfo(name = "catergory_name")
	public String mCatergoryName;
	
	@ColumnInfo(name = "is_one_emoticon")
	public boolean mIsOneEmoticon;
	
	@ColumnInfo(name = "emoticon_files_path")
	public String mEmoticonFilesPath;
	
	@ColumnInfo(name = "emoticon_res_id")
	public int mImageResID;
	
	@ColumnInfo(name = "is_local_data")
	public boolean mIsLocalData;
	
	@ColumnInfo(name = "favorite_")
	public boolean mIsFavorite;
	
	@ColumnInfo(name = "description_")
	public String mDescription;
	
	@ColumnInfo(name = "date_")
	public long mDate;
}