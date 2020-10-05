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
public class MD_Emoticon {
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
	 * The M catergory idx.
	 */
	@ColumnInfo(name = "catergory_idx")
	public int mCatergoryIdx;
	
	/**
	 * The M catergory name.
	 */
	@ColumnInfo(name = "catergory_name")
	public String mCatergoryName;
	
	/**
	 * The M is one emoticon.
	 */
	@ColumnInfo(name = "is_one_emoticon")
	public boolean mIsOneEmoticon;
	
	/**
	 * The M emoticon files path.
	 */
	@ColumnInfo(name = "emoticon_files_path")
	public String mEmoticonFilesPath;
	
	/**
	 * The M image res id.
	 */
	@ColumnInfo(name = "emoticon_res_id")
	public int mImageResID;
	
	/**
	 * The M is local data.
	 */
	@ColumnInfo(name = "is_local_data")
	public boolean mIsLocalData;
	
	/**
	 * The M is favorite.
	 */
	@ColumnInfo(name = "favorite_")
	public boolean mIsFavorite;
	
	/**
	 * The M description.
	 */
	@ColumnInfo(name = "description_")
	public String mDescription;
	
	/**
	 * The M date.
	 */
	@ColumnInfo(name = "date_")
	public long mDate;
}