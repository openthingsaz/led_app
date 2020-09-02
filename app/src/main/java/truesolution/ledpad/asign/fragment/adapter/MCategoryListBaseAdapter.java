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
import truesolution.ledpad.asign.db.MD_Category;

/**
 * Created by TCH on 2020/07/10
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/10
 */

public class MCategoryListBaseAdapter extends BaseAdapter implements View.OnClickListener {
	private final Context mContext;
	private int mPosition = MAPP.ERROR_;
	private List<MD_Category> mList = new ArrayList<>();
	private List<ViewHolder> mVHList = new ArrayList<>();
	private List<Boolean> mCheckList = new ArrayList<>();
	
	public MCategoryListBaseAdapter(Context context, List<MD_Category> _list) {
		this.mContext = context;
		mList = _list;
		
		for(int i = 0; i < mList.size(); i++) {
			mCheckList.add(false);
		}
	}
	
	public MD_Category mGetCellData(int _pos) {
		if(_pos >= mList.size()) {
			return null;
		}
		
		return mList.get(_pos);
	};
	
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
	
	public int mGetPosition() {
		return mPosition;
	}
	
	public int mGetCategoryIdx(int _pos) {
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
	
	public static class ViewHolder {
		public MD_Category mStr;
		public int mPosition;
		public TextView tvName;
	}
}
