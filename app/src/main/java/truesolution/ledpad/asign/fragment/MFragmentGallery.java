package truesolution.ledpad.asign.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.async.EmoticonFavoriteDBDataAsyncTask;
import truesolution.ledpad.asign.db.MAppDatabase;
import truesolution.ledpad.asign.fd.FD_ASSETS;
import truesolution.ledpad.asign.fd.FD_DRAW;
import truesolution.ledpad.asign.fragment.adapter.MGalleryBaseAdapter;
import truesolution.ledpad.asign.fragment.str.STR_GalleryCell;

/**
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/07
 */
public class MFragmentGallery extends Fragment implements View.OnClickListener {
	private static final int TAB_MENU_CATEGORY                  = 0;
	private static final int TAB_MENU_ALL                       = 1;
	private static final int TAB_MENU_FAVORITE                  = 2;
	private int mTabMenu;
	
	/**
	 * The M gallery base adapter.
	 */
	public MMGalleryBaseAdapter mGalleryBaseAdapter;
	
	/**
	 * The Tv search box.
	 */
	public TextView tvSearchBox;
	/**
	 * The Tv btn search box.
	 */
	public TextView tvBtnSearchBox;
	/**
	 * The Et gallery search.
	 */
	public EditText etGallerySearch;
	
	/**
	 * The Btn tab menu category.
	 */
// Tab
	public TextView btnTabMenuCategory;
	/**
	 * The Btn tab menu all.
	 */
	public TextView btnTabMenuAll;
	/**
	 * The Btn tab menu favorite.
	 */
	public TextView btnTabMenuFavorite;
	/**
	 * The M is depth.
	 */
	public boolean mIsDepth                         = true;
	
	/**
	 * The Tv btn gallery add.
	 */
	public TextView tvBtnGalleryAdd;
	
	/**
	 * The Rl sub tab menu.
	 */
	public LinearLayout rlSubTabMenu;
	/**
	 * The Rl draw top.
	 */
	public RelativeLayout rlDrawTop;
	private View mView;
	private MainActivity mActivity;
	private GridView gvGalleryList;
	private List<STR_GalleryCell> mGalleryList;
	private List<STR_GalleryCell> mDisplayGalleryList = new ArrayList<>();
	private String mFileDirPath;
	
	/**
	 * Instantiates a new M fragment gallery.
	 *
	 * @param _activity the activity
	 * @param _path     the path
	 * @param _list     the list
	 */
	public MFragmentGallery(MainActivity _activity, String _path, List<STR_GalleryCell> _list) {
		mActivity = _activity;
		mGalleryList = _list;
		mFileDirPath = _path;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mView == null) {
			mView = inflater.inflate(R.layout.fragment_gallery, container, false);
			mFindView(mView);
		}
		return mView;
	}
	
	/**
	 * Find View
	 *
	 * @param _view the view
	 */
	public void mFindView(View _view) {
		tvSearchBox = _view.findViewById(R.id.tvSearchBox);
		tvBtnSearchBox = _view.findViewById(R.id.tvBtnSearchBox);
		etGallerySearch = _view.findViewById(R.id.etGallerySearch);
		
		gvGalleryList = _view.findViewById(R.id.gvGalleryList);
		mGalleryBaseAdapter = new MMGalleryBaseAdapter(mActivity.getApplicationContext(), mFileDirPath, mDisplayGalleryList);
		gvGalleryList.setAdapter(mGalleryBaseAdapter);
		gvGalleryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			}
		});
		
		// Keyboard Hide
		mActivity.mKeyboardHide(etGallerySearch);
		
		btnTabMenuCategory = _view.findViewById(R.id.btnTabMenuCategory);
		btnTabMenuAll = _view.findViewById(R.id.btnTabMenuAll);
		btnTabMenuFavorite = _view.findViewById(R.id.btnTabMenuFavorite);
		rlSubTabMenu = _view.findViewById(R.id.rlSubTabMenu);
		rlDrawTop = _view.findViewById(R.id.rlDrawTop);
		
		tvBtnGalleryAdd = _view.findViewById(R.id.tvBtnGalleryAdd);
		
		tvBtnSearchBox.setOnClickListener(this);
		btnTabMenuCategory.setOnClickListener(this);
		btnTabMenuAll.setOnClickListener(this);
		btnTabMenuFavorite.setOnClickListener(this);
		tvBtnGalleryAdd.setOnClickListener(this);
		
		btnTabMenuCategory.setSelected(true);
		new MSetCategoryAsyncTask(MAPP.STR_).execute();
	}
	
	/**
	 * M tab btn reset.
	 */
	public void mTabBtnReset() {
		btnTabMenuCategory.setSelected(false);
		btnTabMenuAll.setSelected(false);
		btnTabMenuFavorite.setSelected(false);
	}
	
	// TODO TCH : Category
	private class MSetCategoryAsyncTask extends AsyncTask<Void, Void, Void> {
		/**
		 * The M keyword.
		 */
		String mKeyword;
		
		/**
		 * Instantiates a new M set category async task.
		 *
		 * @param _keyword the keyword
		 */
		public MSetCategoryAsyncTask(String _keyword) {
			mKeyword = _keyword;
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			mSetCategoryList(mKeyword);
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mGalleryBaseAdapter.mInitInfoLayout();
			if(mKeyword.isEmpty() && mDisplayGalleryList.size() == 0) {
				new MSetCategoryAsyncTask(mKeyword).execute();
			}
			else {
				mGalleryBaseAdapter.mUpdateDataAndView();
			}
		}
	}
	
	// TODO TCH : Emoticon
	private class MSetEmoticonAsyncTask extends AsyncTask<Void, Void, Void> {
		/**
		 * The M str.
		 */
		STR_GalleryCell mStr;
		/**
		 * The M keyword.
		 */
		String mKeyword;
		
		/**
		 * Instantiates a new M set emoticon async task.
		 *
		 * @param _str     the str
		 * @param _keyword the keyword
		 */
		public MSetEmoticonAsyncTask(STR_GalleryCell _str, String _keyword) {
			mStr = _str;
			mKeyword = _keyword;
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			mSetEmoticonList(mStr, mKeyword);
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mGalleryBaseAdapter.mInitInfoLayout();
			if(mDisplayGalleryList.size() == 0)
				new MSetCategoryAsyncTask(MAPP.STR_).execute();
			else
				mGalleryBaseAdapter.mUpdateDataAndView();
		}
	}
	
	// TODO TCH : Favorite
	private class MSetFavoriteAsyncTask extends AsyncTask<Void, Void, Void> {
		/**
		 * The M keyword.
		 */
		String mKeyword;
		
		/**
		 * Instantiates a new M set favorite async task.
		 *
		 * @param _keyword the keyword
		 */
		public MSetFavoriteAsyncTask(String _keyword) {
			mKeyword = _keyword;
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			mSetFavoriteList(mKeyword);
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mGalleryBaseAdapter.mInitInfoLayout();
			mGalleryBaseAdapter.mUpdateDataAndView();
		}
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.tvBtnSearchBox:
				String _search_str = etGallerySearch.getText().toString();
				if(_search_str == null || _search_str.isEmpty()) {
					mActivity.mShowMessageDialog(R.string.text_menu_gallery_search_name_input_none, false);
					return;
				}
				
				mSearchKeywordAfterUpdateView(mTabMenu, _search_str);
				break;
			case R.id.btnTabMenuCategory:
				mIsDepth = true;
				mTabBtnReset();
				mTabMenu = TAB_MENU_CATEGORY;
				btnTabMenuCategory.setSelected(true);
				tvBtnGalleryAdd.setVisibility(View.VISIBLE);
				
				new MSetCategoryAsyncTask(MAPP.STR_).execute();
				break;
			case R.id.btnTabMenuAll:
				mIsDepth = false;
				mTabBtnReset();
				mTabMenu = TAB_MENU_ALL;
				btnTabMenuAll.setSelected(true);
				tvBtnGalleryAdd.setVisibility(View.GONE);
				
				new MSetEmoticonAsyncTask(null, MAPP.STR_).execute();
				break;
			case R.id.btnTabMenuFavorite:
				mIsDepth = false;
				mTabBtnReset();
				mTabMenu = TAB_MENU_FAVORITE;
				btnTabMenuFavorite.setSelected(true);
				tvBtnGalleryAdd.setVisibility(View.GONE);
				
				new MSetFavoriteAsyncTask(MAPP.STR_).execute();
				break;
				
			case R.id.tvBtnGalleryAdd:
				mActivity.mCategoryAddDialog.mShowDialog(mActivity);
				break;
		}
	}
	
	private void mSearchKeywordAfterUpdateView(int _tab_menu, String _keyword) {
		MDEBUG.debug("_tab_menu : " + _tab_menu + ", _keyword : " + _keyword);
		if(_tab_menu == TAB_MENU_CATEGORY) {
			new MSetCategoryAsyncTask(_keyword).execute();
		} else if(_tab_menu == TAB_MENU_ALL) {
			new MSetEmoticonAsyncTask(null, _keyword).execute();
		} else if(_tab_menu == TAB_MENU_FAVORITE) {
			new MSetFavoriteAsyncTask(_keyword).execute();
		}
	}
	
	private void mSetCategoryList(String _keyword) {
		mDisplayGalleryList.clear();
		if(_keyword.isEmpty()) {
			for(int i = 0; i < mGalleryList.size(); i++) {
				STR_GalleryCell _str = mGalleryList.get(i);
				if(_str.mIsCategory == true)
					mDisplayGalleryList.add(_str);
				
				MDEBUG.debug("mSetCategoryList : " + _str.mIsCategory);
			}
		} else {
			for(int i = 0; i < mGalleryList.size(); i++) {
				STR_GalleryCell _str = mGalleryList.get(i);
				if(_str.mIsCategory == true) {
					if(_str.mCategory.mName.contains(_keyword)) {
						mDisplayGalleryList.add(_str);
					}
				}
			}
		}
	}
	
	private void mSetEmoticonList(STR_GalleryCell _str, String _keyword) {
		mDisplayGalleryList.clear();
		if(_str != null && _str.mIsCategory && _str.mCategory.mImgResID != MAPP.NONE_) {
			STR_GalleryCell _str_home = new STR_GalleryCell();
			_str_home.mIsCategory = true;
			_str_home.mCategory.mName = mActivity.getResources().getString(R.string.text_back);
			_str_home.mCategory.mIDX = MAPP.NONE_;
			_str_home.mCategory.mImgResID = MAPP.NONE_;
			_str_home.mCategory.mSubIDX = MAPP.ERROR_;
			mDisplayGalleryList.add(_str_home);
		}
		MDEBUG.debug("1. mGalleryList.size() : " + mGalleryList.size());
		if(_str != null && _str.mCategory.mImgResID == MAPP.NONE_) {
			for(int i = 0; i < mGalleryList.size(); i++) {
				STR_GalleryCell __str = mGalleryList.get(i);
				if(__str.mIsCategory == true) {
					mDisplayGalleryList.add(__str);
				}
			}
		} else {
			MDEBUG.debug("2. mGalleryList.size() : " + mGalleryList.size());
			for(int i = 0; i < mGalleryList.size(); i++) {
				STR_GalleryCell __str = mGalleryList.get(i);
				if(__str.mIsCategory == false) {
					if(_str == null) {
						MDEBUG.debug("_keyword : " + _keyword + ", __str.mEmoticon.mName : " + __str.mEmoticon.mName);
						if(_keyword.isEmpty()) {
							mDisplayGalleryList.add(__str);
						} else if(__str.mEmoticon.mName.contains(_keyword)) {
							mDisplayGalleryList.add(__str);
						}
					} else {
						if(_str.mCategory.mSubIDX == MAPP.ERROR_) {
							if(_str.mCategory.mIDX == __str.mEmoticon.mCatergoryIdx)
								mDisplayGalleryList.add(__str);
						} else {
							if(_str.mCategory.mSubIDX == __str.mEmoticon.mCatergoryIdx)
								mDisplayGalleryList.add(__str);
						}
					}
				}
			}
		}
	}
	
	private void mSetFavoriteList(String _keyword) {
		mDisplayGalleryList.clear();
		for(int i = 0; i < mGalleryList.size(); i++) {
			STR_GalleryCell _str = mGalleryList.get(i);
			if(_str.mIsCategory == false) {
				if(_str.mEmoticon.mIsFavorite) {
					if(_keyword.isEmpty()) {
						mDisplayGalleryList.add(_str);
					} else if(_str.mEmoticon.mName.contains(_keyword)) {
						mDisplayGalleryList.add(_str);
					}
				}
			}
		}
	}
	
	private class MMGalleryBaseAdapter extends MGalleryBaseAdapter {
		/**
		 * Instantiates a new Mm gallery base adapter.
		 *
		 * @param context the context
		 * @param _path   the path
		 * @param _list   the list
		 */
		public MMGalleryBaseAdapter(Context context, String _path, List<STR_GalleryCell> _list) {
			super(context, _path, _list);
		}
		
		@Override
		public void mClick(ViewHolder _vh) {
			STR_GalleryCell _str = _vh.mStr;
			if(_str != null) {
				if(_str.mIsCategory) {
//					mActivity.mShowProgress();
					new MSetEmoticonAsyncTask(_str, MAPP.STR_).execute();
				} else {
					mGalleryBaseAdapter.mCloseAllInfoLayout();
					
					if(_vh.llIcon.getVisibility() == View.VISIBLE) {
						_vh.tvBtnDelete.setVisibility(View.INVISIBLE);
						_vh.llIcon.setVisibility(View.INVISIBLE);
						_vh.llInfo.setVisibility(View.VISIBLE);
						_vh.mStr.mEmoticon.mIsInfoLayoutShow = true;
					} else {
						_vh.tvBtnDelete.setVisibility(View.VISIBLE);
						_vh.llIcon.setVisibility(View.VISIBLE);
						_vh.llInfo.setVisibility(View.INVISIBLE);
						_vh.mStr.mEmoticon.mIsInfoLayoutShow = false;
					}
				}
			}
			// TODO TCH Debug Code
//			MDEBUG.debug("_str.mIsCategory : " + _str.mIsCategory);
//			MDEBUG.debug("mCategory.mSubIDX _idx : " + _str.mCategory.mSubIDX);
//			MDEBUG.debug("mEmoticon.mCatergoryIdx _idx : " + _str.mEmoticon.mCatergoryIdx);
		}
		
		@Override
		public void mPreview(STR_GalleryCell _cell) {
//			MGalleryBaseAdapter.ViewHolder _g_vh = mGalleryBaseAdapter.mGetCellView(_pos);
			
			// TODO TCH Modify
//			final STR_GalleryCell _str = _cell;
//			int[] _data = mImageToIntAR(_str, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
//			mActivity.mShowPreviewDialog(_data, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
		}
		
		@Override
		public void mWrite(STR_GalleryCell _cell) {
//			MGalleryBaseAdapter.ViewHolder _g_vh = mGalleryBaseAdapter.mGetCellView(_pos);
			final STR_GalleryCell _str = _cell;
			if(_str.mEmoticon.mIsLocalData) {
				if(_str.mEmoticon.mIsOneEmoticon) {
					final int[] _data = mImageToIntAR(_str, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
					mActivity.mViewPagerDrawMoveAndDataUpdate(_data, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
				} else {
					final List<int[]> _list = mAnimationImageToIntAR(_str);
					mActivity.mViewPagerDrawMoveAndDataUpdate(_list, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
				}
			} else {
				if(_str.mEmoticon.mIsOneEmoticon) {
					File _img_file = new File(mFileDirPath + "/" + _str.mEmoticon.mEmoticonFilesPath + FD_DRAW.MSAVE_FILE_FORMAT_STR);
					if(_img_file != null && _img_file.exists()) {
						Bitmap _c_bm = BitmapFactory.decodeFile(_img_file.getAbsolutePath());
						MDEBUG.debug("_c_bm w : " + _c_bm.getWidth() + ", h : " + _c_bm.getHeight());
						final int[] _data = new int[_c_bm.getWidth() * _c_bm.getHeight()];
						_c_bm.getPixels(_data, 0, _c_bm.getWidth(), 0, 0, _c_bm.getWidth(), _c_bm.getHeight());
						mActivity.mViewPagerDrawMoveAndDataUpdate(_data, _c_bm.getWidth(), _c_bm.getHeight());
					}
				} else {
					String[] _img_file_name_ar = _str.mEmoticon.mEmoticonFilesPath.split(FD_DRAW.SPLIT_TOKEN);
					final List<int[]> _list = new ArrayList<>();
					int _img_w = MAPP.INIT_, _img_h = MAPP.INIT_;
					if(_img_file_name_ar != null) {
						for(int i = 0; i < _img_file_name_ar.length; i++) {
							File _img_file = new File(mFileDirPath + "/" + _img_file_name_ar[i] + FD_DRAW.MSAVE_FILE_FORMAT_STR);
							if(_img_file != null && _img_file.exists()) {
								Bitmap _c_bm = BitmapFactory.decodeFile(_img_file.getAbsolutePath());
								MDEBUG.debug("_c_bm w : " + _c_bm.getWidth() + ", h : " + _c_bm.getHeight());
								int[] _data = new int[_c_bm.getWidth() * _c_bm.getHeight()];
								_c_bm.getPixels(_data, 0, _c_bm.getWidth(), 0, 0, _c_bm.getWidth(), _c_bm.getHeight());
								_list.add(_data);
								_img_w = _c_bm.getWidth();
								_img_h = _c_bm.getHeight();
							}
						}
						
						mActivity.mViewPagerDrawMoveAndDataUpdate(_list, _img_w, _img_h);
					}
				}
			}
		}
		
		@Override
		public void mDelete(STR_GalleryCell _str) {
			MDEBUG.debug("mDelete _str.mIsCategory : " + _str.mIsCategory);
			if(_str.mIsCategory) {
				mActivity.mDeleteCategory(_str.mCategory.mIDX);
			} else {
				int _idx = MAPP.ERROR_;
				for(int i = 0; i < mDisplayGalleryList.size(); i++) {
					if(_str.mEmoticon.mIDX == mDisplayGalleryList.get(i).mEmoticon.mIDX) {
						_idx = i;
						break;
					}
				}
				if(_idx != MAPP.ERROR_) {
					mDisplayGalleryList.remove(_idx);
				}
				
				for(int i = 0; i < mGalleryList.size(); i++) {
					if(_str.mEmoticon.mIDX == mGalleryList.get(i).mEmoticon.mIDX) {
						_idx = i;
						break;
					}
				}
				if(_idx != MAPP.ERROR_) {
					mGalleryList.remove(_idx);
				}
				
				mActivity.mDeleteEmoticon(_str.mEmoticon.mIDX);
			}
		}
		
		@Override
		public void mFavorite(STR_GalleryCell _str, boolean _is_favorite) {
			mActivity.mShowProgress();
			int _idx = _str.mEmoticon.mIDX;
			if(mTabMenu == TAB_MENU_FAVORITE && !_is_favorite) {
//				for(int i = 0; i < mDisplayGalleryList.size(); i++) {
//					STR_GalleryCell __str = mDisplayGalleryList.get(i);
//					if(_str.mEmoticon.mIDX == __str.mEmoticon.mIDX) {
//						break;
//					}
//				}
				mDisplayGalleryList.remove(_str);
				mGalleryBaseAdapter.mUpdateDataAndView();
				
			}
			
			new MEmoticonFavoriteDBDataAsyncTask(MAPP.mAppDatabase, _idx, _is_favorite).execute();
		}
	}
	
	private class MEmoticonFavoriteDBDataAsyncTask extends EmoticonFavoriteDBDataAsyncTask {
		/**
		 * Instantiates a new M emoticon favorite db data async task.
		 *
		 * @param _mad the mad
		 * @param _idx the idx
		 * @param _st  the st
		 */
		public MEmoticonFavoriteDBDataAsyncTask(MAppDatabase _mad, int _idx, boolean _st) {
			super(_mad, _idx, _st);
		}
		
		@Override
		public void mResult() {
			mActivity.mCancelProgress();
		}
	}
	
	/**
	 * M update list.
	 */
	public void mUpdateList() {
		mGalleryBaseAdapter.mUpdateDataAndView();
	}
	
	private List<int[]> mAnimationImageToIntAR(STR_GalleryCell _str) {
		List<int[]> _list = new ArrayList<>();
		
		AssetManager am = getResources().getAssets();
		String[] _path_ar = _str.mEmoticon.mEmoticonFilesPath.split(FD_DRAW.SPLIT_TOKEN);
		
		
		for(int i = 0; i < _path_ar.length; i++) {
			InputStream _is = null;
			Bitmap _bm = null;
			byte[] _buf = null;
			try {
				_is = am.open(_path_ar[i] + FD_ASSETS.MFILE_FORMAT);
				int _size = _is.available();
				_buf = new byte[_size];
				_is.read(_buf);
				_is.close();
			} catch(Exception e) {
				MDEBUG.debug("mAnimationImageToIntAR error : " + e.toString());
			}
			
			if(_buf != null) {
				_bm = BitmapFactory.decodeByteArray(_buf, 0, _buf.length);
			}
		
			if(_bm != null) {
				int[] _bm_ar_int = new int[_bm.getWidth() * _bm.getHeight()];
				_bm.getPixels(_bm_ar_int, 0, _bm.getWidth(), 0, 0, _bm.getWidth(), _bm.getHeight());
				
				_list.add(_bm_ar_int);
			}
		}
		
		return _list;
	}
	
	private int[] mImageToIntAR(STR_GalleryCell _str, int _w, int _h) {
		Bitmap __bm = null;
		if(_str.mEmoticon.mImageResID == MAPP.ERROR_) {
		
		} else {
			int _idx = MAPP.ERROR_;
			int _cnt_idx = 0;
			for(int i = 0; i < mGalleryList.size(); i++) {
				STR_GalleryCell _cell = mGalleryList.get(i);
				if(!_cell.mIsCategory) {
					if(_cell.mEmoticon.mIDX == _str.mEmoticon.mIDX) {
						_idx = _cnt_idx;
						break;
					}
					_cnt_idx++;
				}
			}
			AssetManager am = getResources().getAssets();
			InputStream _is;
			byte[] _buf = null;
			
			try {
				_is = am.open(FD_ASSETS.MEMOTICON_FILE_NAME[_idx] + FD_ASSETS.MFILE_FORMAT);
				int _size = _is.available();
				_buf = new byte[_size];
				MDEBUG.debug("_size : " + _size);
				_is.read(_buf);
				_is.close();
			} catch(Exception e) {
				MDEBUG.debug("mImageToIntAR error : " + e.toString());
			}
			
			if(_buf != null) {
				__bm = BitmapFactory.decodeByteArray(_buf, 0, _buf.length);
			}
		}
		
		if(__bm == null)
			return null;
		else {
			int[] _bm_ar_int = new int[__bm.getWidth() * __bm.getHeight()];
			__bm.getPixels(_bm_ar_int, 0, __bm.getWidth(), 0, 0, __bm.getWidth(), __bm.getHeight());
			return _bm_ar_int;
		}
	}
	
	/**
	 * M refresh gallery.
	 */
	public void mRefreshGallery() {
		new MSetCategoryAsyncTask(MAPP.STR_).execute();
	}
}
