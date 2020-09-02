package truesolution.ledpad.asign.fragment;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fd.FD_DELAY;
import truesolution.ledpad.asign.fd.FD_DRAW;
import truesolution.ledpad.asign.fragment.str.STR_Text;
import truesolution.ledpad.asign.itf.ITF_FreeDraw;
import truesolution.ledpad.asign.utils.MBluetoothUtils;
import truesolution.ledpad.asign.view.MFreeDrawView;

/**
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/07
 */

public class MFragmentText extends Fragment {
	private final int TEXT_ACTION_SPEED_MAX = 998;
	private final int[] DEVICE_TEXT_SIZE            = {
			1, 2, 3
	};
	
	private MainActivity mActivity;
	private View mView;
	public MFragmentText(Activity _activity) {
		mActivity = (MainActivity) _activity;
	}
	private int[] mArFontSize;
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mView == null) {
			mArFontSize = new int[]{
					R.dimen.f1,
					R.dimen.f2,
					R.dimen.f3
//				R.dimen.f4,
//				R.dimen.f5,
//				R.dimen.f6,
//				R.dimen.f7
			};
			
			mView = inflater.inflate(R.layout.fragment_text, container, false);
			mFindTabMenuViewById(mView);
			mFindFDVViewById(mView);
			mFindPageViewById(mView);
			mFindColorViewById(mView);
			mFindActionViewById(mView);
			mFindTopTabViewById(mView);
		}
		return mView;
	}
	
	// TODO TCH : Tab Menu
	private int mTabMenu;
	private TextView btnTabMenuPen, btnTabMenuAction;
	private void mFindTabMenuViewById(View _view) {
		btnTabMenuPen = _view.findViewById(R.id.btnTabMenuPen);
		btnTabMenuAction = _view.findViewById(R.id.btnTabMenuAction);
		btnTabMenuPen.setOnClickListener(mOnTabMenuClickListener);
		btnTabMenuAction.setOnClickListener(mOnTabMenuClickListener);
		
		mTabMenu = FD_DRAW.TAB_MENU_PEN;
		btnTabMenuPen.setSelected(true);
	}
	
	private View.OnClickListener mOnTabMenuClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			btnTabMenuPen.setSelected(false);
			btnTabMenuAction.setSelected(false);
			mActivity.mKeyboardHide(etInputText[mPageIdx]);
			if(_id == R.id.btnTabMenuPen) {
				btnTabMenuPen.setSelected(true);
				llTextMenuWrite.setVisibility(View.VISIBLE);
				llTextMenuAction.setVisibility(View.INVISIBLE);
			} else if(_id == R.id.btnTabMenuAction) {
				btnTabMenuAction.setSelected(true);
				llTextMenuWrite.setVisibility(View.INVISIBLE);
				llTextMenuAction.setVisibility(View.VISIBLE);
			}
		}
	};
	
	// TODO TCH : Tob Menu
	private TextView tvBtnPlay;
	private void mFindTopTabViewById(View _view) {
		tvBtnPlay = _view.findViewById(R.id.tvBtnPlay);
		tvBtnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mPageEA > MAPP.START_ALIVE) {
					strText[mPageIdx].mText = etInputText[mPageIdx].getText().toString();
					
					ArrayList<STR_Text> _list = new ArrayList<>();
					for(int i = 0; i < mPageEA; i++) {
						_list.add(strText[i]);
					}
					MBluetoothUtils.mSendMultiText(mActivity.mBtSpp, _list, strText[0].mFontSizeIdx, mAction, mActionTime);
					return;
				}
				
				MBluetoothUtils.mSendText(mActivity.mBtSpp,
						etInputText[mPageIdx].getText().toString(), mPenColor,
						DEVICE_TEXT_SIZE[strText[mPageIdx].mFontSizeIdx],
						mAction, mActionTime
				);
			}
		});
	}
	// End
	
	// TODO TCH : MFreeDrawView
	public MFreeDrawView mFreeDrawView;
	private EditText[] etInputText = new EditText[FD_DRAW.MAX_DRAW_PAGE];
	private void mFindFDVViewById(View _view) {
		mFreeDrawView = _view.findViewById(R.id.mFreeDrawView);
		mFreeDrawView.mSetCellEA(FD_DRAW.SIZE_64, FD_DRAW.SIZE_32);
		mFreeDrawView.mSetTouchIdxListener(mFreeDrawListener);
		mFreeDrawView.mTouchHold = true;
		int _color = getResources().getColor(R.color.c_cl_red);
		mFreeDrawView.mSetColor(_color);
		
		etInputText[FD_DRAW.PAGE_1] = _view.findViewById(R.id.etInputText1);
		etInputText[FD_DRAW.PAGE_1].setHintTextColor(getResources().getColor(R.color.c_alpha_gray_1));
		etInputText[FD_DRAW.PAGE_1].setTextColor(_color);
		etInputText[FD_DRAW.PAGE_1].setBackground(null);
		
		etInputText[FD_DRAW.PAGE_2] = _view.findViewById(R.id.etInputText2);
		etInputText[FD_DRAW.PAGE_2].setHintTextColor(getResources().getColor(R.color.c_alpha_gray_1));
		etInputText[FD_DRAW.PAGE_2].setTextColor(_color);
		etInputText[FD_DRAW.PAGE_2].setBackground(null);
		
		etInputText[FD_DRAW.PAGE_3] = _view.findViewById(R.id.etInputText3);
		etInputText[FD_DRAW.PAGE_3].setHintTextColor(getResources().getColor(R.color.c_alpha_gray_1));
		etInputText[FD_DRAW.PAGE_3].setTextColor(_color);
		etInputText[FD_DRAW.PAGE_3].setBackground(null);
		
		etInputText[FD_DRAW.PAGE_4] = _view.findViewById(R.id.etInputText4);
		etInputText[FD_DRAW.PAGE_4].setHintTextColor(getResources().getColor(R.color.c_alpha_gray_1));
		etInputText[FD_DRAW.PAGE_4].setTextColor(_color);
		etInputText[FD_DRAW.PAGE_4].setBackground(null);
		
		etInputText[FD_DRAW.PAGE_5] = _view.findViewById(R.id.etInputText5);
		etInputText[FD_DRAW.PAGE_5].setHintTextColor(getResources().getColor(R.color.c_alpha_gray_1));
		etInputText[FD_DRAW.PAGE_5].setTextColor(_color);
		etInputText[FD_DRAW.PAGE_5].setBackground(null);
		
		etInputText[FD_DRAW.PAGE_2].setVisibility(View.GONE);
		etInputText[FD_DRAW.PAGE_3].setVisibility(View.GONE);
		etInputText[FD_DRAW.PAGE_4].setVisibility(View.GONE);
		etInputText[FD_DRAW.PAGE_5].setVisibility(View.GONE);
		mActivity.mShowProgress();
		new EditTextResizeAsyncTask().execute();
	}
	
	private class EditTextResizeAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mActivity.mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mActivity.mCancelProgress();
//					RelativeLayout.LayoutParams _rllp = new RelativeLayout.LayoutParams(mFreeDrawView.mAreaW, mFreeDrawView.mAreaH);
//					_rllp.leftMargin = mFreeDrawView.mStartX;
//					_rllp.topMargin = mFreeDrawView.mStartY;
//					etInputText[mPageIdx].setLayoutParams(_rllp);
				}
			}, FD_DELAY.MIN_DISPLAY);
		}
	}
	
	private ITF_FreeDraw mFreeDrawListener = new ITF_FreeDraw() {
		/**
		 * @param _x_idx
		 * @param _y_idx
		 */
		@Override
		public void mTouchToIdx(int _x_idx, int _y_idx) {
		}
	};
	// End
	
	// TODO TCH : Action
	private LinearLayout llTextMenuWrite, llTextMenuAction;
	private int mAction;
	private TextView tvBtnMarqueeDefault, tvBtnMarqueeLeft, tvBtnMarqueeRight, tvBtnMarqueeUp, tvBtnMarqueeDown, tvTxtSpeed;
	private SeekBar sbSpeed;
	private int mActionTime;
	private void mFindActionViewById(View _view) {
		llTextMenuWrite = _view.findViewById(R.id.llTextMenuWrite);
		llTextMenuAction = _view.findViewById(R.id.llTextMenuAction);
		tvTxtSpeed = _view.findViewById(R.id.tvTxtSpeed);
		sbSpeed = _view.findViewById(R.id.sbSpeed);
		tvBtnMarqueeDefault = _view.findViewById(R.id.tvBtnMarqueeDefault);
		tvBtnMarqueeLeft = _view.findViewById(R.id.tvBtnMarqueeLeft);
		tvBtnMarqueeRight = _view.findViewById(R.id.tvBtnMarqueeRight);
		tvBtnMarqueeUp = _view.findViewById(R.id.tvBtnMarqueeUp);
		tvBtnMarqueeDown = _view.findViewById(R.id.tvBtnMarqueeDown);
		tvBtnMarqueeDefault.setOnClickListener(mOnActionClickListener);
		tvBtnMarqueeLeft.setOnClickListener(mOnActionClickListener);
		tvBtnMarqueeRight.setOnClickListener(mOnActionClickListener);
		tvBtnMarqueeUp.setOnClickListener(mOnActionClickListener);
		tvBtnMarqueeDown.setOnClickListener(mOnActionClickListener);
		
		sbSpeed.setMax(TEXT_ACTION_SPEED_MAX);
		mActionTime = TEXT_ACTION_SPEED_MAX / 2;
		sbSpeed.setProgress(mActionTime);
		tvTxtSpeed.setText(mActionTime + getResources().getString(R.string.text_speed_unit));
		sbSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				MDEBUG.debug("SeekBar onProgressChanged");
				mActionTime = (i + MAPP.START_ALIVE);
				tvTxtSpeed.setText(mActionTime + getResources().getString(R.string.text_speed_unit));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				MDEBUG.debug("SeekBar onStartTrackingTouch");
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				MDEBUG.debug("SeekBar onStopTrackingTouch");
			}
		});
		tvBtnMarqueeDefault.setSelected(true);
		mAction = FD_DRAW.ACTION_DEFAULT;
	}
	private View.OnClickListener mOnActionClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			tvBtnMarqueeDefault.setSelected(false);
			tvBtnMarqueeLeft.setSelected(false);
			tvBtnMarqueeRight.setSelected(false);
			tvBtnMarqueeUp.setSelected(false);
			tvBtnMarqueeDown.setSelected(false);
			
			if(_id == R.id.tvBtnMarqueeDefault) {
				mAction = FD_DRAW.ACTION_DEFAULT;
				tvBtnMarqueeDefault.setSelected(true);
			} else if(_id == R.id.tvBtnMarqueeRight) {
				mAction = FD_DRAW.ACTION_LEFT_TO_RIGHT;
				tvBtnMarqueeRight.setSelected(true);
			} else if(_id == R.id.tvBtnMarqueeLeft) {
				mAction = FD_DRAW.ACTION_RIGHT_TO_LEFT;
				tvBtnMarqueeLeft.setSelected(true);
			} else if(_id == R.id.tvBtnMarqueeDown) {
				mAction = FD_DRAW.ACTION_UP_TO_DOWN;
				tvBtnMarqueeDown.setSelected(true);
			} else if(_id == R.id.tvBtnMarqueeUp) {
				mAction = FD_DRAW.ACTION_DOWN_TO_UP;
				tvBtnMarqueeUp.setSelected(true);
			}
		}
	};
	// End
	
	// TODO TCH : Color
	private LinearLayout iclPenColor;
	private GradientDrawable gdColorSelect = new GradientDrawable();
	public int mPenColor;
	private TextView tvSelectColor, tvBtnColorCustom, tvBtnColorBlack, tvBtnColorRed, tvBtnColorOrange, tvBtnColorYellow, tvBtnColorGreen,
			tvBtnColorBlue, tvBtnColorIndigo, tvBtnColorPurple;
	private void mFindColorViewById(View _view) {
		tvSelectColor = _view.findViewById(R.id.tvSelectColor);
		tvBtnColorCustom = _view.findViewById(R.id.tvBtnColorCustom);
		tvBtnColorBlack = _view.findViewById(R.id.tvBtnColorBlack);
		tvBtnColorRed = _view.findViewById(R.id.tvBtnColorRed);
		tvBtnColorOrange = _view.findViewById(R.id.tvBtnColorOrange);
		tvBtnColorYellow = _view.findViewById(R.id.tvBtnColorYellow);
		tvBtnColorGreen = _view.findViewById(R.id.tvBtnColorGreen);
		tvBtnColorBlue = _view.findViewById(R.id.tvBtnColorBlue);
		tvBtnColorIndigo = _view.findViewById(R.id.tvBtnColorIndigo);
		tvBtnColorPurple = _view.findViewById(R.id.tvBtnColorPurple);
		iclPenColor = _view.findViewById(R.id.iclPenColor);
		
		gdColorSelect = (GradientDrawable) tvSelectColor.getBackground();
		
		tvBtnColorCustom.setOnClickListener(mOnColorClickListener);
		tvBtnColorBlack.setOnClickListener(mOnColorClickListener);
		tvBtnColorRed.setOnClickListener(mOnColorClickListener);
		tvBtnColorOrange.setOnClickListener(mOnColorClickListener);
		tvBtnColorYellow.setOnClickListener(mOnColorClickListener);
		tvBtnColorGreen.setOnClickListener(mOnColorClickListener);
		tvBtnColorBlue.setOnClickListener(mOnColorClickListener);
		tvBtnColorIndigo.setOnClickListener(mOnColorClickListener);
		tvBtnColorPurple.setOnClickListener(mOnColorClickListener);
		
		mPenColor = mActivity.getResources().getColor(R.color.c_cl_red);
	}
	
	private View.OnClickListener mOnColorClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			if(_id == R.id.tvBtnColorCustom) {
				MainActivity _activity = mActivity;
				_activity.mGoColorSettingDialog();
			} else if(_id == R.id.tvBtnColorBlack) {
				mPenColor = mActivity.getResources().getColor(R.color.c_black);
			} else if(_id == R.id.tvBtnColorRed) {
				mPenColor = mActivity.getResources().getColor(R.color.c_cl_red);
			} else if(_id == R.id.tvBtnColorOrange) {
				mPenColor = mActivity.getResources().getColor(R.color.c_cl_orange);
			} else if(_id == R.id.tvBtnColorYellow) {
				mPenColor = mActivity.getResources().getColor(R.color.c_cl_yellow);
			} else if(_id == R.id.tvBtnColorGreen) {
				mPenColor = mActivity.getResources().getColor(R.color.c_cl_green);
			} else if(_id == R.id.tvBtnColorBlue) {
				mPenColor = mActivity.getResources().getColor(R.color.c_cl_blue);
			} else if(_id == R.id.tvBtnColorIndigo) {
				mPenColor = mActivity.getResources().getColor(R.color.c_cl_indigo);
			} else if(_id == R.id.tvBtnColorPurple) {
				mPenColor = mActivity.getResources().getColor(R.color.c_cl_purple);
			}
			strText[mPageIdx].mColor = mPenColor;
			gdColorSelect.setColor(mPenColor);
			tvSelectColor.setBackground(gdColorSelect);
			etInputText[mPageIdx].setTextColor(mPenColor);
		}
	};
	// End
	
	// TODO TCH : Find Page View Start
	private TextView tvBtnPageSize32a32, tvBtnPageSize64a32, tvBtnPageSize128a32;
	private int mPageSizeMatrixIdx;
	private TextView[] tvBtnPageNum = new TextView[FD_DRAW.MAX_DRAW_PAGE];
	private TextView tvBtnPageAdd, tvBtnPageDel;
	private int mPageIdx, mPageEA;
	
	private TextView tvBtnFontSize1, tvBtnFontSize2, tvBtnFontSize3;
	private STR_Text[] strText = new STR_Text[FD_DRAW.MAX_TEXT_PAGE];
	private void mFindPageViewById(View _view) {
		tvBtnPageSize32a32 = _view.findViewById(R.id.tvBtn32a32);
		tvBtnPageSize64a32 = _view.findViewById(R.id.tvBtn64a32);
		tvBtnPageSize128a32 = _view.findViewById(R.id.tvBtn128a32);
		tvBtnPageSize32a32.setOnClickListener(mOnPageMatrixClickListener);
		tvBtnPageSize64a32.setOnClickListener(mOnPageMatrixClickListener);
		tvBtnPageSize128a32.setOnClickListener(mOnPageMatrixClickListener);
		tvBtnPageSize64a32.setSelected(true);
		mPageSizeMatrixIdx = FD_DRAW.SIZE_64_32;
		
		tvBtnFontSize1 = _view.findViewById(R.id.tvBtnFontSize1);
		tvBtnFontSize2 = _view.findViewById(R.id.tvBtnFontSize2);
		tvBtnFontSize3 = _view.findViewById(R.id.tvBtnFontSize3);
//		tvBtnFontSize4 = _view.findViewById(R.id.tvBtnFontSize4);
//		tvBtnFontSize5 = _view.findViewById(R.id.tvBtnFontSize5);
//		tvBtnFontSize6 = _view.findViewById(R.id.tvBtnFontSize6);
//		tvBtnFontSize7 = _view.findViewById(R.id.tvBtnFontSize7);
		tvBtnFontSize1.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize2.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize3.setOnClickListener(mOnFontSizeClickListener);
//		tvBtnFontSize4.setOnClickListener(mOnFontSizeClickListener);
//		tvBtnFontSize5.setOnClickListener(mOnFontSizeClickListener);
//		tvBtnFontSize6.setOnClickListener(mOnFontSizeClickListener);
//		tvBtnFontSize7.setOnClickListener(mOnFontSizeClickListener);
//		tvBtnFontSize7.setSelected(true);
		tvBtnFontSize1.setSelected(true);
		tvBtnFontSize2.setVisibility(View.INVISIBLE);
		tvBtnFontSize3.setVisibility(View.INVISIBLE);
		
		strText[FD_DRAW.PAGE_1] = new STR_Text("", FD_DRAW.PAGE_1,
				getResources().getColor(R.color.c_cl_red), FD_DRAW.ACTION_DEFAULT);
		strText[FD_DRAW.PAGE_2] = new STR_Text("", FD_DRAW.PAGE_1,
				getResources().getColor(R.color.c_cl_red), FD_DRAW.ACTION_DEFAULT);
		strText[FD_DRAW.PAGE_3] = new STR_Text("", FD_DRAW.PAGE_1,
				getResources().getColor(R.color.c_cl_red), FD_DRAW.ACTION_DEFAULT);
		strText[FD_DRAW.PAGE_4] = new STR_Text("", FD_DRAW.PAGE_1,
				getResources().getColor(R.color.c_cl_red), FD_DRAW.ACTION_DEFAULT);
		strText[FD_DRAW.PAGE_5] = new STR_Text("", FD_DRAW.PAGE_1,
				getResources().getColor(R.color.c_cl_red), FD_DRAW.ACTION_DEFAULT);
		tvBtnPageNum[FD_DRAW.PAGE_1] = _view.findViewById(R.id.tvBtnPageNum1);
		tvBtnPageNum[FD_DRAW.PAGE_2] = _view.findViewById(R.id.tvBtnPageNum2);
		tvBtnPageNum[FD_DRAW.PAGE_3] = _view.findViewById(R.id.tvBtnPageNum3);
		tvBtnPageNum[FD_DRAW.PAGE_4] = _view.findViewById(R.id.tvBtnPageNum4);
		tvBtnPageNum[FD_DRAW.PAGE_5] = _view.findViewById(R.id.tvBtnPageNum5);
		tvBtnPageAdd = _view.findViewById(R.id.tvBtnAdd);
		tvBtnPageDel = _view.findViewById(R.id.tvBtnTrash);
		for(int i = 0; i < tvBtnPageNum.length; i++) {
			tvBtnPageNum[i].setOnClickListener(mOnPageNumClickListener);
		}
		tvBtnPageAdd.setOnClickListener(mOnPageNumClickListener);
		tvBtnPageDel.setOnClickListener(mOnPageNumClickListener);
		
		mPageIdx = FD_DRAW.PAGE_1;
		tvBtnPageNum[mPageIdx].setSelected(true);
		mPageEA = MAPP.START_ALIVE;
	}
	
	private View.OnClickListener mOnFontSizeClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			if(_id == R.id.tvBtnFontSize1) {
				strText[mPageIdx].mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX2;
				mFreeDrawView.mSetPixel(FD_DRAW.DOT_SIZE2);
				
				tvBtnFontSize1.setSelected(false);
				tvBtnFontSize1.setVisibility(View.INVISIBLE);
				tvBtnFontSize2.setSelected(true);
				tvBtnFontSize2.setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnFontSize2) {
				strText[mPageIdx].mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX3;
				mFreeDrawView.mSetPixel(FD_DRAW.DOT_SIZE3);
				
				tvBtnFontSize2.setSelected(false);
				tvBtnFontSize2.setVisibility(View.INVISIBLE);
				tvBtnFontSize3.setSelected(true);
				tvBtnFontSize3.setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnFontSize3) {
				strText[mPageIdx].mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX1;
				mFreeDrawView.mSetPixel(FD_DRAW.DOT_SIZE1);
				
				tvBtnFontSize3.setSelected(false);
				tvBtnFontSize3.setVisibility(View.INVISIBLE);
				tvBtnFontSize1.setSelected(true);
				tvBtnFontSize1.setVisibility(View.VISIBLE);
			}
			
			etInputText[mPageIdx].setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(mArFontSize[
					strText[mPageIdx].mFontSizeIdx
					]));
		}
	};
	
	private View.OnClickListener mOnPageNumClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			for(int i = 0; i < tvBtnPageNum.length; i++) {
				tvBtnPageNum[i].setSelected(false);
				etInputText[i].setVisibility(View.GONE);
			}
			
			for(int i = 0; i < etInputText.length; i++) {
				strText[i].mText = etInputText[i].getText().toString();
			}
			
			if(_id == R.id.tvBtnPageNum1) {
				mPageIdx = FD_DRAW.PAGE_1;
				tvBtnPageNum[mPageIdx].setSelected(true);
				etInputText[mPageIdx].setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnPageNum2) {
				mPageIdx = FD_DRAW.PAGE_2;
				tvBtnPageNum[mPageIdx].setSelected(true);
				etInputText[mPageIdx].setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnPageNum3) {
				mPageIdx = FD_DRAW.PAGE_3;
				tvBtnPageNum[mPageIdx].setSelected(true);
				etInputText[mPageIdx].setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnPageNum4) {
				mPageIdx = FD_DRAW.PAGE_4;
				tvBtnPageNum[mPageIdx].setSelected(true);
				etInputText[mPageIdx].setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnPageNum5) {
				mPageIdx = FD_DRAW.PAGE_5;
				tvBtnPageNum[mPageIdx].setSelected(true);
				etInputText[mPageIdx].setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnAdd) {
				int _prev_idx = mPageEA - MAPP.START_ALIVE;
				if(mPageEA <= FD_DRAW.PAGE_5) {
					MDEBUG.debug("etInputText[" + _prev_idx + "].getText().toString() : " + etInputText[_prev_idx].getText().toString());
					strText[_prev_idx].mText = etInputText[_prev_idx].getText().toString();
					etInputText[_prev_idx].setVisibility(View.GONE);
					etInputText[mPageEA].setText("");
					etInputText[mPageEA].setVisibility(View.VISIBLE);
					tvBtnPageNum[mPageEA].setVisibility(View.VISIBLE);
					mPageIdx = mPageEA;
					mPageEA++;
				}
				
				tvBtnPageNum[mPageIdx].setSelected(true);
			} else if(_id == R.id.tvBtnTrash) {
				if(mPageEA > MAPP.START_ALIVE) {
					int _last_idx = mPageEA - MAPP.START_ALIVE;
					MDEBUG.debug("_last_idx : " + _last_idx + ", mPageEA : " + mPageEA);
					if(mPageIdx < _last_idx) {
						for(int i = mPageIdx + MAPP.START_ALIVE; i < mPageEA; i++) {
							// 0 일때 -1 에 대한 예외 처리
							// 아닐때 현재 페이지 위부터
							int _pre_i = (i - 1);
							MDEBUG.debug("strText[" + _pre_i + "] : " + strText[_pre_i].mText + ", strText[" + i + "] : " + strText[i].mText);
							strText[_pre_i].mSetData(strText[i]);
							
							etInputText[_pre_i].setText(strText[_pre_i].mText);
							etInputText[_pre_i].setTextSize(TypedValue.COMPLEX_UNIT_PX,
									getResources().getDimension(mArFontSize[
											strText[_pre_i].mFontSizeIdx
											]));
							etInputText[_pre_i].setTextColor(strText[_pre_i].mColor);
						}
					}
					etInputText[_last_idx].setVisibility(View.GONE);
					tvBtnPageNum[_last_idx].setVisibility(View.GONE);
					tvBtnPageNum[_last_idx].setSelected(false);
					mPageEA--;
				}
				etInputText[MAPP.INIT_].setTextColor(strText[MAPP.INIT_].mColor);
				etInputText[MAPP.INIT_].setText(strText[MAPP.INIT_].mText);
				etInputText[MAPP.INIT_].setVisibility(View.VISIBLE);
				tvBtnPageNum[MAPP.INIT_].setSelected(true);
			}
		}
	};
	
	private View.OnClickListener mOnPageMatrixClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			
			tvBtnPageSize32a32.setSelected(false);
			tvBtnPageSize64a32.setSelected(false);
			tvBtnPageSize128a32.setSelected(false);
			if(_id == R.id.tvBtn32a32) {
				mPageSizeMatrixIdx = FD_DRAW.SIZE_32_32;
				tvBtnPageSize32a32.setSelected(true);
				mFreeDrawView.mUpdateCell(FD_DRAW.SIZE_32, FD_DRAW.SIZE_32);
			} else if(_id == R.id.tvBtn64a32) {
				mPageSizeMatrixIdx = FD_DRAW.SIZE_64_32;
				tvBtnPageSize64a32.setSelected(true);
				mFreeDrawView.mUpdateCell(FD_DRAW.SIZE_64, FD_DRAW.SIZE_32);
			} else if(_id == R.id.tvBtn128a32) {
				mPageSizeMatrixIdx = FD_DRAW.SIZE_128_32;
				tvBtnPageSize128a32.setSelected(true);
				mFreeDrawView.mUpdateCell(FD_DRAW.SIZE_128, FD_DRAW.SIZE_32);
			}
			
			new EditTextResizeAsyncTask().execute();
		}
	};
	// End Page
}
