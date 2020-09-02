package truesolution.ledpad.asign.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;

/**
 * Created by TCH on 2020/08/07
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/08/07
 */

public abstract class MCategoryAddDialog {
	private MainActivity mActivity;
	public AlertDialog mDialog;
	public abstract void mBtnYes(String _name);
	public abstract void mBtnNo();
	
	private TextView etCategoryName, tvBtnCategoryAddYes, tvBtnCategoryAddNo;
	
	public MCategoryAddDialog(MainActivity _activity) {
		mActivity = _activity;
	}
	
	public void mShowDialog(Activity _activity) {
		if(mDialog != null) {
			mDialog.show();
			return;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
		LayoutInflater inflater = _activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_draw_add_category_dialog, null);
		etCategoryName = view.findViewById(R.id.etCategoryName);
		tvBtnCategoryAddYes = view.findViewById(R.id.tvBtnCategoryAddYes);
		builder.setView(view);
		
		mDialog = builder.create();
		mDialog.setCancelable(true);
		mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mDialog.show();
		
		tvBtnCategoryAddNo = view.findViewById(R.id.tvBtnCategoryAddNo);
		tvBtnCategoryAddNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.cancel();
				mBtnNo();
			}
		});
		tvBtnCategoryAddYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.cancel();
				String _name = etCategoryName.getText().toString();
				if(_name.length() > 0) {
					mBtnYes(_name);
				} else {
					mActivity.mShowMessageDialog(R.string.text_menu_gallery_category_name_input_none, false);
				}
			}
		});
	}
}
