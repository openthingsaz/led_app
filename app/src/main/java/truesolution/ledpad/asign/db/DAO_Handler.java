package truesolution.ledpad.asign.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
@Dao
public interface DAO_Handler {
	/**
	 * The constant EMOTICON_MAX.
	 */
	public static final int EMOTICON_MAX                = 999;
	
	/**
	 * M get category all list.
	 *
	 * @return the list
	 */
	@Query("SELECT * FROM MD_Category ORDER BY idx_ ASC LIMIT " + EMOTICON_MAX)
	List<MD_Category> mGetCategoryAll();
	
	/**
	 * Insert category.
	 *
	 * @param md_ the md
	 */
	@Insert
	void insertCategory(MD_Category... md_);
	
	/**
	 * Delete.
	 *
	 * @param md_ the md
	 */
	@Delete
	void delete(MD_Category... md_);
	
	/**
	 * M get emotion all list.
	 *
	 * @return the list
	 */
	@Query("SELECT * FROM MD_Emoticon ORDER BY idx_ ASC LIMIT " + EMOTICON_MAX)
	List<MD_Emoticon> mGetEmotionAll();
	
	/**
	 * Insert emoticon.
	 *
	 * @param md_ the md
	 */
	@Insert
	void insertEmoticon(MD_Emoticon... md_);
	
	/**
	 * Delete.
	 *
	 * @param md_ the md
	 */
	@Delete
	void delete(MD_Emoticon... md_);
	
	/**
	 * Favorite.
	 *
	 * @param is_favorite the is favorite
	 * @param idx         the idx
	 */
	@Query("UPDATE MD_Emoticon SET favorite_ = :is_favorite WHERE idx_=:idx")
	void favorite(boolean is_favorite, int idx);
}