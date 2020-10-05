package truesolution.ledpad.asign.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.db.MD_Category;
import truesolution.ledpad.asign.fragment.adapter.MCategoryListBaseAdapter;

/**
 * Created by TCH on 2020/08/07
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /08/07
 */
public abstract class MEmoticonAddDialog {
	private MainActivity mActivity;
	/**
	 * The M dialog.
	 */
	public AlertDialog mDialog;
	
	/**
	 * M btn yes.
	 *
	 * @param _name the name
	 * @param _idx  the idx
	 */
	public abstract void mBtnYes(String _name, int _idx);
	
	/**
	 * M btn no.
	 */
	public abstract void mBtnNo();
	
	private TextView etEmoticonName, tvBtnEmoticonAddYes, tvBtnEmoticonAddNo;
	
	private ListView lvCategoryList;
	private MMCategoryListBaseAdapter mCategoryListBaseAdapter;
	private List<MD_Category> mList;
	
	/**
	 * Instantiates a new M emoticon add dialog.
	 *
	 * @param _activity the activity
	 */
	public MEmoticonAddDialog(MainActivity _activity) {
		mActivity = _activity;
	}
	
	/**
	 * M show dialog.
	 *
	 * @param _activity the activity
	 * @param _list     the list
	 */
	public void mShowDialog(Activity _activity, List<MD_Category> _list) {
		MDEBUG.debug("List<MD_Emoticon> _list : " + _list);
		if(_list != null)
			MDEBUG.debug("_list size : " + _list.size());
		mList = _list;
		if(mDialog != null) {
			etEmoticonName.setText("");
			mCategoryListBaseAdapter.mUpdateDataAndView(_list);
			mDialog.show();
			return;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
		LayoutInflater inflater = _activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_draw_add_emoticon_dialog, null);
		etEmoticonName = view.findViewById(R.id.etEmoticonName);
		tvBtnEmoticonAddYes = view.findViewById(R.id.tvBtnEmoticonAddYes);
		builder.setView(view);
		
		mDialog = builder.create();
		mDialog.setCancelable(true);
		mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mDialog.show();
		
		tvBtnEmoticonAddNo = view.findViewById(R.id.tvBtnEmoticonAddNo);
		tvBtnEmoticonAddNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.cancel();
				mBtnNo();
			}
		});
		tvBtnEmoticonAddYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.cancel();
				
				String _name = etEmoticonName.getText().toString();
				int _position = mCategoryListBaseAdapter.mGetPosition();
				if(_position == MAPP.ERROR_) {
					mActivity.mShowMessageDialog(R.string.text_menu_gallery_category_select_none, false);
					return;
				}
				if(_name.length() > 0) {
					MDEBUG.debug("_position : " + _position);
					mBtnYes(_name, mCategoryListBaseAdapter.mGetCategoryIdx(_position));
				} else {
					mActivity.mShowMessageDialog(R.string.text_menu_gallery_category_name_input_none, false);
				}
			}
		});
		
		lvCategoryList = view.findViewById(R.id.lvCategoryList);
		mCategoryListBaseAdapter = new MMCategoryListBaseAdapter(_activity, mList);
		lvCategoryList.setAdapter(mCategoryListBaseAdapter);
		
		lvCategoryList.setClickable(false);
		lvCategoryList.setItemsCanFocus(false);
		lvCategoryList.setOnItemClickListener(null);
	}
	
	private class MMCategoryListBaseAdapter extends MCategoryListBaseAdapter {
		/**
		 * Instantiates a new Mm category list base adapter.
		 *
		 * @param context the context
		 * @param _list   the list
		 */
		public MMCategoryListBaseAdapter(Context context, List<MD_Category> _list) {
			super(context, _list);
		}
	}
}
