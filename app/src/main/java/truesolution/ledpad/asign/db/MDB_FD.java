package truesolution.ledpad.asign.db;

import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.fd.FD_DRAW;

/**
 * Created by TCH on 2020/07/10
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/10
 */

public class MDB_FD {
	public static final int CATEGORY_IDX_DATE                   = 0;
	public static final int CATEGORY_IDX_DRIVER                 = 1;
	public static final int CATEGORY_IDX_HOLIDAY                = 2;
	public static final int CATEGORY_IDX_WARNING                = 3;
	public static final int CATEGORY_IDX_TEXT                   = 4;
	
	public static final int CATEGORY_IDX_C19                    = 5;
	public static final int CATEGORY_IDX_SHOW                   = 6;
	public static final int CATEGORY_IDX_NATION                 = 7;
	public static final int CATEGORY_IDX_ANIMAL                 = 8;
	public static final int CATEGORY_IDX_SHOP                   = 9;
	
	public static final int CATEGORY_IDX_ANIMATION = 10;
	public static final int CATEGORY_IDX_OTHER                  = 11;
	
	// Default Category
	public static final String[] MCATEGORY_TITLE = {
			"데이트", "운전자", "홀리데이", "경고", "문자",
			"코로나", "공연", "국가", "동물", "상점(푸드트럭)",
			"에니메이션", "기타"
	};
	
	public static final int[] MCATEGORY_TYPE = {
			CATEGORY_IDX_DATE, CATEGORY_IDX_DRIVER, CATEGORY_IDX_HOLIDAY, CATEGORY_IDX_WARNING, CATEGORY_IDX_TEXT,
			
			CATEGORY_IDX_C19, CATEGORY_IDX_SHOW, CATEGORY_IDX_NATION, CATEGORY_IDX_ANIMAL, CATEGORY_IDX_SHOP,
			
			CATEGORY_IDX_ANIMATION, CATEGORY_IDX_OTHER
	};
	
	public static final int[] MCATEGORY_RES_ID = {
			R.drawable.cate_date,
			R.drawable.cate_driver,
			R.drawable.cate_holiday,
			R.drawable.cate_warning,
			R.drawable.cate_text,
			
			R.drawable.cate_covid,
			R.drawable.cate_show,
			R.drawable.cate_country,
			R.drawable.cate_animal,
			R.drawable.cate_shop,
			
			R.drawable.cate_3_d,
			R.drawable.cate_default
	};
	
	// Default Emoticon
	public static final String[] MEMOTICON_NAME = {
			// Date
			"사랑해", "BTS Love", "널 사랑해", "Kiss",
			// Driver
			"Baby in a car", "Beginner1", "Beginner2", "Stop",
			// Holiday
			"새해", "한가위", "여행", "야자수", "여름휴가", "휴가중",
			// Warning
			"조심1", "조심2", "정지1", "정지2", "화기조심",
			// Text
			"핵인싸", "안녕하세요", "요로", "방해금지",
			// COVID
			"2m 거리유지", "마스크", "손씻기", "바이러스",
			// Show
			"Good", "Love", "Rock",
			// Country
			"브라질", "중국", "프랑스", "독일", "이태리", "일본", "한국", "미국",
			// Animal
			"고양이", "닭", "강아지", "사자",
			// Shop
			"맥주", "커피", "아이스크림", "쥬스", "오픈",
			// Animation
			"동전",
			"지도",
			"스마일"
//			"동전1", "동전2", "동전3", "동전4", "동전5",
//			"지도1", "지도2", "지도3", "지도4", "지도5",
//			"스마일1", "스마일2", "스마일3", "스마일4"
	};
	
	public static final int[] MEMOTICON_RES_ID = {
			// Date
			// "사랑해", "BTS Love", "널 사랑해", "Kiss",
			R.drawable.date_heart, R.drawable.date_i_love_bts, R.drawable.date_i_love_u, R.drawable.date_kiss_emoji,
			// Driver
			// "Baby in a car", "Beginner1", "Beginner2", "Stop",
			R.drawable.driver_baby_in_a_car, R.drawable.driver_beginner_1, R.drawable.driver_beginner_2, R.drawable.driver_stop,
			// Holiday
			// "새해", "한가위", "여행", "야자수", "여름휴가", "크리스마스",
			R.drawable.hd_new_year, R.drawable.hd_thanksgiving, R.drawable.hd_vacation,
			R.drawable.hd_palm_tree, R.drawable.hd_summer_vacation, R.drawable.hd_christmas,
			// Warning
			// "조심1", "조심2", "정지1", "정지2", "화기조심",
			R.drawable.wr_attention_1, R.drawable.wr_attention_2, R.drawable.wr_attention_3,
			R.drawable.wr_attention_4,R.drawable.wr_fire_prohibited,
			// Text
//			"핵인싸", "안녕하세요", "요로", "방해금지",
			R.drawable.txt_1, R.drawable.txt_2, R.drawable.txt_3, R.drawable.txt_4,
			// COVID
//			"2m 거리유지", "마스크", "손씻기", "바이러스",
			R.drawable.c19_2m, R.drawable.c19_mask, R.drawable.c19_no_handshake, R.drawable.c19_virus,
			// Show
//			"Good", "Love", "Rock",
			R.drawable.date_good_sign, R.drawable.date_love_text, R.drawable.date_rock_sign,
			// Country
//			"브라질", "중국", "프랑스", "독일", "이태리", "일본", "한국", "미국",
			R.drawable.nt_brazil, R.drawable.nt_china, R.drawable.nt_france, R.drawable.nt_germany,
			R.drawable.nt_italy, R.drawable.nt_japan, R.drawable.nt_korea, R.drawable.nt_usa,
			// Animal
//			"고양이", "닭", "강아지", "사자",
			R.drawable.anm_cat, R.drawable.anm_chick, R.drawable.anm_dog, R.drawable.anm_lion,
			// Shop
//			"맥주", "커피", "아이스크림", "쥬스", "오픈",
			R.drawable.store_beer, R.drawable.store_coffee, R.drawable.store_ice_cream,
			R.drawable.store_juice, R.drawable.store_open,
			// 3D
//			"동전1", "동전2", "동전3", "동전4", "동전5",
//			"지도1", "지도2", "지도3", "지도4", "지도5",
//			"스마일1", "스마일2", "스마일3", "스마일4"
			R.drawable.ani_coin_1,// R.drawable.ani_coin_2, R.drawable.ani_coin_3, R.drawable.ani_coin_4, R.drawable.ani_coin_5,
			R.drawable.ani_korea_map_1,// R.drawable.ani_korea_map_2, R.drawable.ani_korea_map_3, R.drawable.ani_korea_map_4, R.drawable.ani_korea_map_5,
			R.drawable.ani_smile_1//, R.drawable.ani_smile_2, R.drawable.ani_smile_3, R.drawable.ani_smile_4
	};
	
	public static final String[] MANIMATION_EMOTICON_LIST = {
			((R.drawable.ani_coin_1) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_coin_2) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_coin_3) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_coin_4) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_coin_5)),
			((R.drawable.ani_korea_map_1) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_korea_map_2) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_korea_map_3) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_korea_map_4) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_korea_map_5)),
			((R.drawable.ani_smile_1) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_smile_2) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_smile_3) + FD_DRAW.SPLIT_TOKEN +
					(R.drawable.ani_smile_4))
	};
	
	public static final int[] MEMOTICON_CATEGORY_TYPE = {
			CATEGORY_IDX_DATE, CATEGORY_IDX_DATE, CATEGORY_IDX_DATE, CATEGORY_IDX_DATE,
			
			CATEGORY_IDX_DRIVER, CATEGORY_IDX_DRIVER, CATEGORY_IDX_DRIVER, CATEGORY_IDX_DRIVER,
			
			CATEGORY_IDX_HOLIDAY, CATEGORY_IDX_HOLIDAY, CATEGORY_IDX_HOLIDAY,
			CATEGORY_IDX_HOLIDAY, CATEGORY_IDX_HOLIDAY, CATEGORY_IDX_HOLIDAY,
			
			CATEGORY_IDX_WARNING, CATEGORY_IDX_WARNING, CATEGORY_IDX_WARNING, CATEGORY_IDX_WARNING, CATEGORY_IDX_WARNING,
			
			CATEGORY_IDX_TEXT, CATEGORY_IDX_TEXT, CATEGORY_IDX_TEXT, CATEGORY_IDX_TEXT,
			
			CATEGORY_IDX_C19, CATEGORY_IDX_C19, CATEGORY_IDX_C19, CATEGORY_IDX_C19,
			
			CATEGORY_IDX_SHOW, CATEGORY_IDX_SHOW, CATEGORY_IDX_SHOW,
			
			CATEGORY_IDX_NATION, CATEGORY_IDX_NATION, CATEGORY_IDX_NATION, CATEGORY_IDX_NATION,
			CATEGORY_IDX_NATION, CATEGORY_IDX_NATION, CATEGORY_IDX_NATION, CATEGORY_IDX_NATION,
			
			CATEGORY_IDX_ANIMAL, CATEGORY_IDX_ANIMAL, CATEGORY_IDX_ANIMAL, CATEGORY_IDX_ANIMAL,
			
			CATEGORY_IDX_SHOP, CATEGORY_IDX_SHOP, CATEGORY_IDX_SHOP, CATEGORY_IDX_SHOP, CATEGORY_IDX_SHOP,
			
			CATEGORY_IDX_ANIMATION,// CATEGORY_IDX_3D, CATEGORY_IDX_3D, CATEGORY_IDX_3D, CATEGORY_IDX_3D,
			CATEGORY_IDX_ANIMATION,// CATEGORY_IDX_3D, CATEGORY_IDX_3D, CATEGORY_IDX_3D, CATEGORY_IDX_3D,
			CATEGORY_IDX_ANIMATION//, CATEGORY_IDX_3D, CATEGORY_IDX_3D, CATEGORY_IDX_3D
	};
}
