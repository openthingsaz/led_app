package truesolution.ledpad.asign.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by TCH on 2020. 08. 15.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 08. 15.
 */
@Database(entities = {MD_Emoticon.class, MD_Category.class}, version = 1)
public abstract class MAppDatabase extends RoomDatabase {
	/**
	 * M dao handler dao handler.
	 *
	 * @return the dao handler
	 */
	public abstract DAO_Handler mDAOHandler();
}