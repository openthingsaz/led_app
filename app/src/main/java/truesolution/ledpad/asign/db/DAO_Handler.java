package truesolution.ledpad.asign.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
@Dao
public interface DAO_Handler {
	public static final int EMOTICON_MAX                = 999;
	
	@Query("SELECT * FROM MD_Category ORDER BY idx_ ASC LIMIT " + EMOTICON_MAX)
	List<MD_Category> mGetCategoryAll();
	
	@Insert
	void insertCategory(MD_Category... md_);
	
	@Delete
	void delete(MD_Category... md_);
	
	@Query("SELECT * FROM MD_Emoticon ORDER BY idx_ ASC LIMIT " + EMOTICON_MAX)
	List<MD_Emoticon> mGetEmotionAll();
	
	@Insert
	void insertEmoticon(MD_Emoticon... md_);
	
	@Delete
	void delete(MD_Emoticon... md_);
}