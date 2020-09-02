package truesolution.ledpad.asign.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fragment.str.STR_GalleryCell;
import truesolution.ledpad.asign.fragment.str.STR_GalleryEmoticon;

/**
 * Created by TCH on 2020/07/10
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/10
 */

public abstract class MGalleryBaseAdapter extends BaseAdapter implements View.OnClickListener, View.OnLongClickListener {
	public abstract void mClick(ViewHolder _vh);
	public abstract void mPreview(int _pos);
	public abstract void mWrite(int _pos);
	public abstract void mDelete(STR_GalleryCell _str);
	private final Context mContext;
	private List<STR_GalleryCell> mList = new ArrayList<>();
	private List<ViewHolder> mCellVH = new ArrayList<>();
	
	public MGalleryBaseAdapter(Context context, List<STR_GalleryCell> _list) {
		this.mContext = context;
		mList = _list;
	}
	
	public ViewHolder mGetCellView(int _pos) {
		MDEBUG.debug("mConvertViewList size : " + mCellVH.size());
		if(_pos >= mCellVH.size()) {
			return null;
		}
		
		for(int i = 0; i < mCellVH.size(); i++) {
			if(_pos == mCellVH.get(i).mStr.mCellPosition)
				return mCellVH.get(i);
		}
		
		return mCellVH.get(_pos);
	};
	
	public void mInitInfoLayout() {
		for(int i = 0; i < mCellVH.size(); i++) {
			mCellVH.get(i).llInfo.setVisibility(View.VISIBLE);
			mCellVH.get(i).llIcon.setVisibility(View.INVISIBLE);
		}
		
		for(int i = 0; i < mList.size(); i++) {
			STR_GalleryEmoticon _str_ge = mList.get(i).mEmoticon;
			if(_str_ge != null)
				mList.get(i).mEmoticon.mIsInfoLayoutShow = true;
		}
	}
	
	public void mUpdateDataAndView() {
		// View Recycle
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}
	
	// 3
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	// 4
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder _g_vh;
		if (convertView == null) {
			final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			convertView = layoutInflater.inflate(R.layout.cell_gallery, null);
			
			_g_vh = new ViewHolder();
			_g_vh.mTVName = convertView.findViewById(R.id.tvCategoryName);
			
			_g_vh.mTVBtn = convertView.findViewById(R.id.tvBtnBGBoard);
			_g_vh.mTVBtn.setOnClickListener(this);
			_g_vh.mTVBtn.setOnLongClickListener(this);
			
			_g_vh.mBG = convertView.findViewById(R.id.tvBoardOnImage);
			
			_g_vh.tvBtnDelete = convertView.findViewById(R.id.tvBtnDelete);
			_g_vh.tvBtnDelete.setOnClickListener(this);
			_g_vh.tvBtnDelete.setVisibility(View.INVISIBLE);
			
			_g_vh.llIcon = convertView.findViewById(R.id.llGalleryCellIcon);
			_g_vh.llInfo = convertView.findViewById(R.id.llGalleryCellInfo);
			
			_g_vh.tvBtnGalleryPreview = convertView.findViewById(R.id.tvBtnGalleryPreview);
			_g_vh.tvBtnGalleryWrite = convertView.findViewById(R.id.tvBtnGalleryWrite);
			_g_vh.tvBtnGalleryFavorite = convertView.findViewById(R.id.tvBtnGalleryFavorite);
			_g_vh.tvBtnGalleryPreview.setOnClickListener(this);
			_g_vh.tvBtnGalleryWrite.setOnClickListener(this);
			_g_vh.tvBtnGalleryFavorite.setOnClickListener(this);
			
			convertView.setTag(_g_vh);
			_g_vh.mTVBtn.setTag(_g_vh);
			_g_vh.tvBtnDelete.setTag(_g_vh);
			_g_vh.tvBtnGalleryPreview.setTag(_g_vh);
			_g_vh.tvBtnGalleryWrite.setTag(_g_vh);
			_g_vh.tvBtnGalleryFavorite.setTag(_g_vh);
			
			mCellVH.add(_g_vh);
		} else {
			_g_vh = (ViewHolder) convertView.getTag();
			_g_vh.tvBtnDelete.setVisibility(View.INVISIBLE);
		}
		
		STR_GalleryCell _str = mList.get(position);
		if(_str != null) {
			_g_vh.mStr = _str;
			_g_vh.mStr.mCellPosition = position;
			if(_str.mIsCategory) {
				if(_str.mCategory.mSubIDX == MAPP.ERROR_)
					_g_vh.mTVBtn.setOnLongClickListener(this);
				else
					_g_vh.mTVBtn.setOnLongClickListener(null);
				
				_g_vh.mTVName.setText(_str.mCategory.mName);
				int _res_id = _str.mCategory.mImgResID;
				if(_str.mCategory.mImgResID == MAPP.NONE_)
					_res_id = R.drawable.category_back_img;
				
				_g_vh.mBG.setBackgroundResource(_res_id);
			} else {
				_g_vh.mTVBtn.setOnLongClickListener(null);
				_g_vh.mTVName.setText(_str.mEmoticon.mName);
				if(_str.mEmoticon.mIsLocalData)
					_g_vh.mBG.setBackgroundResource(_str.mEmoticon.mImageResID);
				
				if(_str.mEmoticon.mIsFavorite) {
					_g_vh.tvBtnGalleryFavorite.setSelected(true);
				} else {
					_g_vh.tvBtnGalleryFavorite.setSelected(false);
				}
				
				if(_g_vh.mStr.mEmoticon.mIsInfoLayoutShow) {
					_g_vh.llInfo.setVisibility(View.VISIBLE);
					_g_vh.tvBtnDelete.setVisibility(View.INVISIBLE);
					_g_vh.llIcon.setVisibility(View.INVISIBLE);
				} else {
					_g_vh.llInfo.setVisibility(View.INVISIBLE);
					_g_vh.tvBtnDelete.setVisibility(View.VISIBLE);
					_g_vh.llIcon.setVisibility(View.VISIBLE);
				}
			}
		}
		
		return convertView;
	}
	
	@Override
	public void onClick(View view) {
		int _id = view.getId();
		ViewHolder _holder = (ViewHolder) view.getTag();
		if(_id == R.id.tvBtnBGBoard) {
			mClick(_holder);
		} else if(_id == R.id.tvBtnGalleryPreview) {
			mPreview(_holder.mStr.mCellPosition);
		} else if(_id == R.id.tvBtnGalleryWrite) {
			mWrite(_holder.mStr.mCellPosition);
		} else if(_id == R.id.tvBtnGalleryFavorite) {
			boolean _st = !view.isSelected();
			_holder.mStr.mEmoticon.mIsFavorite = _st;
			view.setSelected(_st);
		} else if(_id == R.id.tvBtnDelete) {
			mDelete(_holder.mStr);
		}
	}
	
	@Override
	public boolean onLongClick(View view) {
		MDEBUG.debug("long click tag : " + view.getTag());
		ViewHolder _holder = (ViewHolder) view.getTag();
		_holder.tvBtnDelete.setVisibility(View.VISIBLE);
		
		MDEBUG.debug("onLongClick mSubIDX : " + _holder.mStr.mCategory.mSubIDX);
		return true;
	}
	
	public static class ViewHolder {
		public LinearLayout llInfo;
		public LinearLayout llIcon;
		public ImageView mBG;
		public TextView mTVName;
		public TextView mTVBtn;
		
		public TextView tvBtnGalleryPreview;
		public TextView tvBtnGalleryWrite;
		public TextView tvBtnGalleryFavorite;
		public TextView tvBtnDelete;
		public STR_GalleryCell mStr;
	}
}
