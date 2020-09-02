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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fd.FD_ASSETS;
import truesolution.ledpad.asign.fd.FD_DRAW;
import truesolution.ledpad.asign.fragment.adapter.MGalleryBaseAdapter;
import truesolution.ledpad.asign.fragment.str.STR_GalleryCell;

/**
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/07
 */

public class MFragmentGallery extends Fragment implements View.OnClickListener {
	private static final int TAB_MENU_CATEGORY                  = 0;
	private static final int TAB_MENU_ALL                       = 0;
	private static final int TAB_MENU_FAVORITE                  = 0;
	private int mTabMenu;
	
	public MGalleryBaseAdapter mGalleryBaseAdapter;
	
	public TextView tvSearchBox;
	public TextView tvBtnSearchBox;
	public EditText etGallerySearch;
	
	// Tab
	public TextView btnTabMenuCategory;
	public TextView btnTabMenuAll;
	public TextView btnTabMenuFavorite;
	public boolean mIsDepth                         = true;
	
	public TextView tvBtnGalleryAdd;
	
	public LinearLayout rlSubTabMenu;
	public RelativeLayout rlDrawTop;
	private View mView;
	private MainActivity mActivity;
	private GridView gvGalleryList;
	private List<STR_GalleryCell> mGalleryList;
	private List<STR_GalleryCell> mDisplayGalleryList = new ArrayList<>();
	
	public MFragmentGallery(MainActivity _activity, List<STR_GalleryCell> _list) {
		mActivity = _activity;
		mGalleryList = _list;
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
	 * @param _view
	 */
	public void mFindView(View _view) {
		tvSearchBox = _view.findViewById(R.id.tvSearchBox);
		tvBtnSearchBox = _view.findViewById(R.id.tvBtnSearchBox);
		etGallerySearch = _view.findViewById(R.id.etGallerySearch);
		
		gvGalleryList = _view.findViewById(R.id.gvGalleryList);
		mGalleryBaseAdapter = new MMGalleryBaseAdapter(mActivity.getApplicationContext(), mDisplayGalleryList);
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
		MDEBUG.debug("Gallery FindView");
		new MSetCategoryAsyncTask().execute();
	}
	
	public void mTabBtnReset() {
		btnTabMenuCategory.setSelected(false);
		btnTabMenuAll.setSelected(false);
		btnTabMenuFavorite.setSelected(false);
	}
	
	// TODO TCH : Category
	private class MSetCategoryAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			mSetCategoryList();
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mGalleryBaseAdapter.mInitInfoLayout();
			if(mDisplayGalleryList.size() == 0) {
				new MSetCategoryAsyncTask().execute();
			}
			else {
				mGalleryBaseAdapter.mUpdateDataAndView();
			}
		}
	}
	
	// TODO TCH : Emoticon
	private class MSetEmoticonAsyncTask extends AsyncTask<Void, Void, Void> {
		STR_GalleryCell mStr;
		public MSetEmoticonAsyncTask(STR_GalleryCell _str) {
			mStr = _str;
		}
		
		@Override
		protected Void doInBackground(Void... voids) {
			mSetEmoticonList(mStr);
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mGalleryBaseAdapter.mInitInfoLayout();
			if(mDisplayGalleryList.size() == 0)
				new MSetCategoryAsyncTask().execute();
			else
				mGalleryBaseAdapter.mUpdateDataAndView();
		}
	}
	
	// TODO TCH : Favorite
	private class MSetFavoriteAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			mSetFavoriteList();
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
				break;
			case R.id.btnTabMenuCategory:
				mIsDepth = true;
				mTabBtnReset();
				mTabMenu = TAB_MENU_CATEGORY;
				btnTabMenuCategory.setSelected(true);
				tvBtnGalleryAdd.setVisibility(View.VISIBLE);
				
				new MSetCategoryAsyncTask().execute();
				break;
			case R.id.btnTabMenuAll:
				mIsDepth = false;
				mTabBtnReset();
				mTabMenu = TAB_MENU_ALL;
				btnTabMenuAll.setSelected(true);
				tvBtnGalleryAdd.setVisibility(View.GONE);
				
				new MSetEmoticonAsyncTask(null).execute();
				break;
			case R.id.btnTabMenuFavorite:
				mIsDepth = false;
				mTabBtnReset();
				mTabMenu = TAB_MENU_FAVORITE;
				btnTabMenuFavorite.setSelected(true);
				tvBtnGalleryAdd.setVisibility(View.GONE);
				
				new MSetFavoriteAsyncTask().execute();
				break;
				
			case R.id.tvBtnGalleryAdd:
				mActivity.mCategoryAddDialog.mShowDialog(mActivity);
				break;
		}
	}
	
	private void mSetCategoryList() {
		mDisplayGalleryList.clear();
		for(int i = 0; i < mGalleryList.size(); i++) {
			STR_GalleryCell _str = mGalleryList.get(i);
			if(_str.mIsCategory == true)
				mDisplayGalleryList.add(_str);
			
			MDEBUG.debug("mSetCategoryList : " + _str.mIsCategory);
		}
	}
	
	private void mSetEmoticonList(STR_GalleryCell _str) {
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
		
		if(_str != null && _str.mCategory.mImgResID == MAPP.NONE_) {
			for(int i = 0; i < mGalleryList.size(); i++) {
				STR_GalleryCell __str = mGalleryList.get(i);
				if(__str.mIsCategory == true) {
					mDisplayGalleryList.add(__str);
				}
			}
		} else {
			for(int i = 0; i < mGalleryList.size(); i++) {
				STR_GalleryCell __str = mGalleryList.get(i);
				if(__str.mIsCategory == false) {
					if(_str == null) {
						mDisplayGalleryList.add(__str);
					} else if(_str.mCategory.mSubIDX == __str.mEmoticon.mCatergoryIdx)
						mDisplayGalleryList.add(__str);
				}
			}
		}
	}
	
	private void mSetFavoriteList() {
		mDisplayGalleryList.clear();
		for(int i = 0; i < mGalleryList.size(); i++) {
			STR_GalleryCell _str = mGalleryList.get(i);
			if(_str.mIsCategory == false) {
				if(_str.mEmoticon.mIsFavorite)
					mDisplayGalleryList.add(_str);
			}
		}
	}
	
	private class MMGalleryBaseAdapter extends MGalleryBaseAdapter {
		public MMGalleryBaseAdapter(Context context, List<STR_GalleryCell> _list) {
			super(context, _list);
		}
		
		@Override
		public void mClick(ViewHolder _vh) {
			STR_GalleryCell _str = _vh.mStr;
			if(_str != null) {
				if(_str.mIsCategory) {
//					mActivity.mShowProgress();
					new MSetEmoticonAsyncTask(_str).execute();
				} else {
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
		public void mPreview(int _pos) {
			MGalleryBaseAdapter.ViewHolder _g_vh = mGalleryBaseAdapter.mGetCellView(_pos);
			STR_GalleryCell _str = _g_vh.mStr;
			int[] _data = mImageToIntAR(_str, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
			mActivity.mShowPreviewDialog(_data, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
		}
		
		@Override
		public void mWrite(int _pos) {
			MGalleryBaseAdapter.ViewHolder _g_vh = mGalleryBaseAdapter.mGetCellView(_pos);
			STR_GalleryCell _str = _g_vh.mStr;
			if(_g_vh.mStr.mEmoticon.mIsLocalData) {
				if(_g_vh.mStr.mEmoticon.mIsOneEmoticon) {
					int[] _data = mImageToIntAR(_str, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
					mActivity.mViewPagerDrawMoveAndDataUpdate(_data, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
				} else {
					List<int[]> _list = mAnimationImageToIntAR(_str);
					mActivity.mViewPagerDrawMoveAndDataUpdate(_list, FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
				}
			} else {
			
			}
		}
		
		@Override
		public void mDelete(STR_GalleryCell _str) {
			MDEBUG.debug("mDelete _str.mIsCategory : " + _str.mIsCategory);
			if(_str.mIsCategory) {
				mActivity.mDeleteCategory(_str.mCategory.mIDX);
			} else {
			
			}
		}
	}
	
	private List<int[]> mAnimationImageToIntAR(STR_GalleryCell _str) {
		Bitmap __bm = null;
		List<int[]> _list = new ArrayList<>();
		
		AssetManager am = getResources().getAssets();
		InputStream _is = null;
		
		String[] _ar = _str.mEmoticon.mEmoticonFilesPath.split(FD_DRAW.SPLIT_TOKEN);
		for(int i = 0; i < _ar.length; i++) {
			MDEBUG.debug("str res id : " + _ar[i]);
			int _res_id = Integer.parseInt(_ar[i]);
			MDEBUG.debug("res id : " + _res_id);
		}
		
//		byte[] _buf = null;
//		try {
//			_is = am.open(FD_ASSETS.MEMOTICON_FILE_NAME[_idx] + FD_ASSETS.MFILE_FORMAT);
//			int _size = _is.available();
//			_buf = new byte[_size];
//			MDEBUG.debug("_size : " + _size);
//			_is.read(_buf);
//			_is.close();
//		} catch(Exception e) {
//			MDEBUG.debug("mImageToIntAR error : " + e.toString());
//		}
//
//		if(_buf != null) {
//			__bm = BitmapFactory.decodeByteArray(_buf, 0, _buf.length);
//		}
//
//		if(__bm == null)
			return null;
//		else {
//			int[] _bm_ar_int = new int[__bm.getWidth() * __bm.getHeight()];
//			__bm.getPixels(_bm_ar_int, 0, __bm.getWidth(), 0, 0, __bm.getWidth(), __bm.getHeight());
//			_list.add(_bm_ar_int);
//			return _list;
//		}
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
			InputStream _is = null;
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
		
//		// Real
//		MGalleryBaseAdapter.ViewHolder _g_vh = mGalleryBaseAdapter.mGetCellView(_pos);
//		Bitmap _bm = ((BitmapDrawable)_g_vh.mBG.getBackground()).getBitmap();
//		Bitmap _scale_bm = Bitmap.createScaledBitmap(_bm, _w, _h, false);
//
//		if(_scale_bm != null) {
//			MDEBUG.debug("_scale_bm w : " + _scale_bm.getWidth() + ", h : " + _scale_bm.getHeight());
//		}
//		int[] _bm_ar_int = new int[_w * _w];
//		_scale_bm.getPixels(_bm_ar_int, 0, _w, 0, 0, _w, _h);
//
//		return _bm_ar_int;
//		// Real End
		
		if(__bm == null)
			return null;
		else {
			int[] _bm_ar_int = new int[__bm.getWidth() * __bm.getHeight()];
			__bm.getPixels(_bm_ar_int, 0, __bm.getWidth(), 0, 0, __bm.getWidth(), __bm.getHeight());
			return _bm_ar_int;
		}
	}
	
	public void mRefreshGallery() {
		new MSetCategoryAsyncTask().execute();
	}
}
