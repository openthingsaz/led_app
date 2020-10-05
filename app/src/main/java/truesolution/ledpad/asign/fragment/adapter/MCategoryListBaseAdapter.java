package truesolution.ledpad.asign.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.db.MDB_FD;
import truesolution.ledpad.asign.db.MD_Category;
import truesolution.ledpad.asign.fd.FD_ASSETS;
import truesolution.ledpad.asign.fd.FD_DRAW;
import truesolution.ledpad.asign.fd.FD_FILE;

/**
 * Created by TCH on 2020/07/10
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/10
 */
public class MCategoryListBaseAdapter extends BaseAdapter implements View.OnClickListener {
	private final Context mContext;
	private int mPosition = MAPP.ERROR_;
	private List<MD_Category> mList = new ArrayList<>();
	private List<ViewHolder> mVHList = new ArrayList<>();
	private List<Boolean> mCheckList = new ArrayList<>();
	
	/**
	 * Instantiates a new M category list base adapter.
	 *
	 * @param context the context
	 * @param _list   the list
	 */
	public MCategoryListBaseAdapter(Context context, List<MD_Category> _list) {
		this.mContext = context;
		mList = _list;
		
		for(int i = 0; i < mList.size(); i++) {
			mCheckList.add(false);
		}
	}
	
	/**
	 * M get cell data md category.
	 *
	 * @param _pos the pos
	 *
	 * @return the md category
	 */
	public MD_Category mGetCellData(int _pos) {
		if(_pos >= mList.size()) {
			return null;
		}
		
		return mList.get(_pos);
	};
	
	/**
	 * M update data and view.
	 *
	 * @param _list the list
	 */
	public void mUpdateDataAndView(List<MD_Category> _list) {
		mList = _list;
		mPosition = MAPP.ERROR_;
		mCheckList.clear();
		
		for(int i = 0; i < mList.size(); i++) {
			mCheckList.add(false);
		}
		
		// View Recycle
		notifyDataSetChanged();
	}
	
	/**
	 * M get position int.
	 *
	 * @return the int
	 */
	public int mGetPosition() {
		return mPosition;
	}
	
	/**
	 * M get category idx int.
	 *
	 * @param _pos the pos
	 *
	 * @return the int
	 */
	public int mGetCategoryIdx(int _pos) {
		MDEBUG.debug("mList.get(" + _pos + ").idx_ : " + mList.get(_pos).idx_);
		MDEBUG.debug("mList.get(" + _pos + ").mSubIdx : " + mList.get(_pos).mSubIdx);
//		if(mList.get(_pos).mSubIdx)

		int _idx = mList.get(_pos).idx_;

		if(_idx <= MDB_FD.CATEGORY_SIZE) {
			return mList.get(_pos).mSubIdx;
		} else
			return mList.get(_pos).idx_;
	}
	
	@Override
	public int getCount() {
		if(mList == null)
			return 0;
		
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
		MDEBUG.debug("getView : " + position);
		TextView _tv = null;
		ViewHolder _g_vh;
		if (convertView == null) {
			final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			convertView = layoutInflater.inflate(R.layout.cell_category, null);
			_g_vh = new ViewHolder();
			_g_vh.tvName = convertView.findViewById(R.id.tvName);
			_g_vh.tvName.setOnClickListener(this);
			convertView.setTag(_g_vh);
			mVHList.add(_g_vh);
		} else {
			_g_vh = (ViewHolder) convertView.getTag();
		}
		
		MD_Category _str = mList.get(position);
		String _name = _str.mName;
		_g_vh.mPosition = position;
		_g_vh.tvName.setText(_name);
		
		MDEBUG.debug("_name : " + _name + ", idx : " + _str.idx_);
		
		if(mCheckList.get(position))
			_g_vh.tvName.setSelected(true);
		else
			_g_vh.tvName.setSelected(false);
		
		_g_vh.tvName.setTag(_str);
		_g_vh.mStr = _str;
		
		return convertView;
	}
	
	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		MD_Category _str = (MD_Category)v.getTag();
		mPosition = MAPP.ERROR_;
		
		for(int i = 0; i < mCheckList.size(); i++) {
			mCheckList.set(i, false);
		}
		
		for(int i = 0; i < mVHList.size(); i++) {
			ViewHolder _vh = mVHList.get(i);
			if(_vh.mStr.idx_ == _str.idx_) {
				mPosition = _vh.mPosition;
				v.setSelected(true);
				mCheckList.set(mPosition, true);
			} else {
//				mPosition = _vh.mPosition;
				_vh.tvName.setSelected(false);
			}
		}
	}
	
	/**
	 * The type View holder.
	 */
	public static class ViewHolder {
		/**
		 * The M str.
		 */
		public MD_Category mStr;
		/**
		 * The M position.
		 */
		public int mPosition;
		/**
		 * The Tv name.
		 */
		public TextView tvName;
	}
}
