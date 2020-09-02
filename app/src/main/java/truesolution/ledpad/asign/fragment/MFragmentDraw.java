package truesolution.ledpad.asign.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fd.FD_BT;
import truesolution.ledpad.asign.fd.FD_DRAW;
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

public class MFragmentDraw extends Fragment {
	private MainActivity mActivity;
	private View mView;
	
	public MFragmentDraw(Activity _activity) {
		mActivity = (MainActivity) _activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mView == null) {
			mView = inflater.inflate(R.layout.fragment_draw, container, false);
			mFindPageViewById(mView);
			mFindFDVViewById(mView);
			mFindTabMenuViewById(mView);
			mFindColorViewById(mView);
			mFindActionViewById(mView);
			mFindTextViewById(mView);
			mFindTopTabViewById(mView);
		}
		return mView;
	}
	
	// TODO TCH : Top Menu
	private TextView tvBtnPlay, tvBtnSave, tvBtnGallery;
	private void mFindTopTabViewById(View _view) {
		tvBtnPlay = _view.findViewById(R.id.tvBtnPlay);
		tvBtnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mActivity.mSetAllDotDataSend(mFreeDrawView.mCellEAW, mFreeDrawView.mCellEAH, mFreeDrawView.mGetCurrentColors());
//				MBluetoothUtils.mSendAllDotData(mFreeDrawView.mCellEAW, mFreeDrawView.mCellEAH, mFreeDrawView.mCellColor);
			}
		});
		tvBtnSave = _view.findViewById(R.id.tvBtnSave);
		tvBtnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.mShowEmoticonAddDialog();
			}
		});
		tvBtnGallery = _view.findViewById(R.id.tvBtnGallery);
		tvBtnGallery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.mGoPhoneGallery();
			}
		});
	}
	// End
	
	// TODO TCH : MFreeDrawView
	public MFreeDrawView mFreeDrawView;
	private void mFindFDVViewById(View _view) {
		mFreeDrawView = _view.findViewById(R.id.mFreeDrawView);
		mFreeDrawView.mSetCellEA(FD_DRAW.SIZE_64, FD_DRAW.SIZE_32);
		mFreeDrawView.mSetTouchIdxListener(mFreeDrawListener);
		mFreeDrawView.mSetColor(getResources().getColor(R.color.c_cl_red));
	}
	
	public int mGetCellW() {
		return mFreeDrawView.mCellEAW;
	}
	
	public int mGetCellH() {
		return mFreeDrawView.mCellEAH;
	}
	
	private ITF_FreeDraw mFreeDrawListener = new ITF_FreeDraw() {
		/**
		 * @param _x_idx
		 * @param _y_idx
		 */
		@Override
		public void mTouchToIdx(int _x_idx, int _y_idx) {
			if(mFreeDrawView.mTouchHold) {
				if(mTabMenu == FD_DRAW.TAB_MENU_FILL) {
					mFreeDrawView.mSetAllColor(mPenColor);
					
					MDEBUG.debug("red : " + Color.red(mPenColor) + ", green : " + Color.green(mPenColor) + ", blue : " + Color.blue(mPenColor));
					MBluetoothUtils.transmit_data(mActivity.mBtSpp, FD_BT.SET_SCR_COLOR, new byte[] {
							(byte)Color.red(mPenColor), (byte)Color.green(mPenColor), (byte)Color.blue(mPenColor)
					});
//					MBluetoothUtils.send_single_dot(mActivity.mBtSpp, FD_BT.SET_SCR_COLOR, (byte)_x_idx, (byte)_y_idx, mPenColor);
				} else if(mTabMenu == FD_DRAW.TAB_MENU_SPOID) {
					mPenColor = mFreeDrawView.mGetColor(_x_idx, _y_idx);
					gdColorSelect.setColor(mPenColor);
					mFreeDrawView.mSetColor(mPenColor);
					tvSelectColor.setBackground(gdColorSelect);
				 }
			} else {
				byte _cmd = FD_BT.SET_DRAW;
				int _color = mPenColor;
				if(mTabMenu == FD_DRAW.TAB_MENU_PEN) {
				} else if(mTabMenu == FD_DRAW.TAB_MENU_ERASER){
					_color = Color.BLACK;
				}
				
				if(mActivity.mIsBtConnect) {
					if(mDotPixel == FD_DRAW.DOT_SIZE1) {
						MBluetoothUtils.send_single_dot(mActivity.mBtSpp, mPageIdx, _cmd, (byte) _x_idx, (byte) _y_idx, _color);
					} else
						MBluetoothUtils.mSendMultiDot(mActivity.mBtSpp, mDotPixel,
								mFreeDrawView.mCellEAW, mFreeDrawView.mCellEAH,
								(byte) _x_idx, (byte) _y_idx, _color);
				}
			}
		}
	};
	// End
	
	// TODO TCH : Text
	private RelativeLayout iclDrawText;
	private int mFontSizeIdx;
	private int[] mArFontSize;
	private EditText etInputText;
	private TextView tvBtnFontSize1, tvBtnFontSize2, tvBtnFontSize3, tvBtnFontSize4, tvBtnFontSize5, tvBtnFontSize6, tvBtnFontSize7;
	private void mFindTextViewById(View _view) {
		iclDrawText = _view.findViewById(R.id.iclDrawText);
		etInputText = _view.findViewById(R.id.etInputText);
		
		tvBtnFontSize1 = _view.findViewById(R.id.tvBtnFontSize1);
		tvBtnFontSize2 = _view.findViewById(R.id.tvBtnFontSize2);
		tvBtnFontSize3 = _view.findViewById(R.id.tvBtnFontSize3);
		tvBtnFontSize4 = _view.findViewById(R.id.tvBtnFontSize4);
		tvBtnFontSize5 = _view.findViewById(R.id.tvBtnFontSize5);
		tvBtnFontSize6 = _view.findViewById(R.id.tvBtnFontSize6);
		tvBtnFontSize7 = _view.findViewById(R.id.tvBtnFontSize7);
		tvBtnFontSize1.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize2.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize3.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize4.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize5.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize6.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize7.setOnClickListener(mOnFontSizeClickListener);
		tvBtnFontSize7.setSelected(true);
		mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX7;
		mArFontSize = new int[]{
				R.dimen.f1,
				R.dimen.f2,
				R.dimen.f3,
				R.dimen.f4,
				R.dimen.f5,
				R.dimen.f6,
				R.dimen.f7
		};
		
		iclDrawText.setVisibility(View.INVISIBLE);
	}
	
	private View.OnClickListener mOnFontSizeClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			
			tvBtnFontSize1.setSelected(false);
			tvBtnFontSize2.setSelected(false);
			tvBtnFontSize3.setSelected(false);
			tvBtnFontSize4.setSelected(false);
			tvBtnFontSize5.setSelected(false);
			tvBtnFontSize6.setSelected(false);
			tvBtnFontSize7.setSelected(false);
			if(_id == R.id.tvBtnFontSize1) {
				tvBtnFontSize1.setSelected(true);
				mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX1;
			} else if(_id == R.id.tvBtnFontSize2) {
				tvBtnFontSize2.setSelected(true);
				mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX2;
			} else if(_id == R.id.tvBtnFontSize3) {
				tvBtnFontSize3.setSelected(true);
				mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX3;
			} else if(_id == R.id.tvBtnFontSize4) {
				tvBtnFontSize4.setSelected(true);
				mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX4;
			} else if(_id == R.id.tvBtnFontSize5) {
				tvBtnFontSize5.setSelected(true);
				mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX5;
			} else if(_id == R.id.tvBtnFontSize6) {
				tvBtnFontSize6.setSelected(true);
				mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX6;
			} else if(_id == R.id.tvBtnFontSize7) {
				tvBtnFontSize7.setSelected(true);
				mFontSizeIdx = FD_DRAW.FONT_SIZE_IDX7;
			}
			
			etInputText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(mArFontSize[mFontSizeIdx]));
		}
	};
	// End
	
	// TODO TCH : Action
	private LinearLayout llAction;
	private int mAction;
	private TextView tvBtnMarqueeDefault, tvBtnMarqueeLeft, tvBtnMarqueeRight, tvBtnMarqueeUp, tvBtnMarqueeDown;
	private void mFindActionViewById(View _view) {
		llAction = _view.findViewById(R.id.llAction);
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
		
		llAction.setVisibility(View.INVISIBLE);
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
				
				MBluetoothUtils.transmit_data(mActivity.mBtSpp, FD_BT.SET_TEXT_ACTION, new byte[]{
						FD_DRAW.ACTION_LEFT_TO_RIGHT,
						(byte)((FD_BT.ACTION_TIME & 0x0000ff00) >> 8),
						(byte)(FD_BT.ACTION_TIME & 0x000000ff)
				});
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
			gdColorSelect.setColor(mPenColor);
			tvSelectColor.setBackground(gdColorSelect);
			
			if(mTabMenu == FD_DRAW.TAB_MENU_ERASER)
				mFreeDrawView.mSetColor(Color.BLACK);
			else
				mFreeDrawView.mSetColor(mPenColor);
		}
	};
	
	public void mSetPenColor(int _color) {
		mPenColor = _color;
		gdColorSelect.setColor(mPenColor);
		tvSelectColor.setBackground(gdColorSelect);
		
		if(mTabMenu == FD_DRAW.TAB_MENU_ERASER)
			mFreeDrawView.mSetColor(Color.BLACK);
		else
			mFreeDrawView.mSetColor(mPenColor);
	}
	// End
	
	// TODO TCH : Tab Menu
	private int mTabMenu;
	private TextView btnTabMenuPen, btnTabMenuEraser, btnTabMenuFill, btnTabMenuSpoid, btnTabMenuText;//, btnTabMenuAction;
	private void mFindTabMenuViewById(View _view) {
		btnTabMenuPen = _view.findViewById(R.id.btnTabMenuPen);
		btnTabMenuEraser = _view.findViewById(R.id.btnTabMenuEraser);
		btnTabMenuFill = _view.findViewById(R.id.btnTabMenuFill);
		btnTabMenuSpoid = _view.findViewById(R.id.btnTabMenuSpoid);
		btnTabMenuText = _view.findViewById(R.id.btnTabMenuText);
//		btnTabMenuAction = _view.findViewById(R.id.btnTabMenuAction);
		btnTabMenuPen.setOnClickListener(mOnTabMenuClickListener);
		btnTabMenuEraser.setOnClickListener(mOnTabMenuClickListener);
		btnTabMenuFill.setOnClickListener(mOnTabMenuClickListener);
		btnTabMenuSpoid.setOnClickListener(mOnTabMenuClickListener);
		btnTabMenuText.setOnClickListener(mOnTabMenuClickListener);
//		btnTabMenuAction.setOnClickListener(mOnTabMenuClickListener);
		
		mTabMenu = FD_DRAW.TAB_MENU_PEN;
		btnTabMenuPen.setSelected(true);
	}
	
	private View.OnClickListener mOnTabMenuClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			btnTabMenuPen.setSelected(false);
			btnTabMenuEraser.setSelected(false);
			btnTabMenuFill.setSelected(false);
			btnTabMenuSpoid.setSelected(false);
			btnTabMenuText.setSelected(false);
			mActivity.mKeyboardHide(etInputText);
//			btnTabMenuAction.setSelected(false);
			if(_id == R.id.btnTabMenuPen) {
				mTabMenu = FD_DRAW.TAB_MENU_PEN;
				btnTabMenuPen.setSelected(true);
				mFreeDrawView.setVisibility(View.VISIBLE);
				mFreeDrawView.mTouchHold = false;
				iclPenColor.setVisibility(View.VISIBLE);
				llAction.setVisibility(View.INVISIBLE);
				iclDrawText.setVisibility(View.INVISIBLE);
			} else if(_id == R.id.btnTabMenuEraser) {
				mTabMenu = FD_DRAW.TAB_MENU_ERASER;
				btnTabMenuEraser.setSelected(true);
				mFreeDrawView.setVisibility(View.VISIBLE);
//				mFreeDrawView.mTouchHold = false;
				mFreeDrawView.mSetColor(Color.BLACK);
				iclPenColor.setVisibility(View.VISIBLE);
				llAction.setVisibility(View.INVISIBLE);
				iclDrawText.setVisibility(View.INVISIBLE);
			} else if(_id == R.id.btnTabMenuFill) {
				mTabMenu = FD_DRAW.TAB_MENU_FILL;
				btnTabMenuFill.setSelected(true);
				mFreeDrawView.setVisibility(View.VISIBLE);
				mFreeDrawView.mTouchHold = true;
				iclPenColor.setVisibility(View.VISIBLE);
				llAction.setVisibility(View.INVISIBLE);
				iclDrawText.setVisibility(View.INVISIBLE);
			} else if(_id == R.id.btnTabMenuSpoid) {
				mTabMenu = FD_DRAW.TAB_MENU_SPOID;
				btnTabMenuSpoid.setSelected(true);
				mFreeDrawView.setVisibility(View.VISIBLE);
				mFreeDrawView.mTouchHold = true;
				iclPenColor.setVisibility(View.VISIBLE);
				llAction.setVisibility(View.INVISIBLE);
				iclDrawText.setVisibility(View.INVISIBLE);
			} else if(_id == R.id.btnTabMenuText) {
				mTabMenu = FD_DRAW.TAB_MENU_TEXT;
				btnTabMenuText.setSelected(true);
				mFreeDrawView.setVisibility(View.INVISIBLE);
				iclDrawText.setVisibility(View.VISIBLE);
			}
//			else if(_id == R.id.btnTabMenuAction) {
//				mTabMenu = FD_DRAW.TAB_MENU_ACTION;
//				btnTabMenuAction.setSelected(true);
//				mFreeDrawView.mTouchHold = true;
//				mFreeDrawView.setVisibility(View.VISIBLE);
//				iclPenColor.setVisibility(View.INVISIBLE);
//				llAction.setVisibility(View.VISIBLE);
//				iclDrawText.setVisibility(View.INVISIBLE);
//			}
		}
	};
	// End Tab Menu
	
	// TODO TCH : Find Page View Start
	private TextView tvBtnPageAdd, tvBtnPageDel;
	private TextView[] tvBtnPageNum = new TextView[FD_DRAW.MAX_DRAW_PAGE];
	private int mPageIdx, mPageEA;
	private LinearLayout llMatrix;
	private TextView tvBtnPageSize32a32, tvBtnPageSize64a32, tvBtnPageSize128a32;
	private int mPageSizeMatrixIdx;
	private TextView tvBtnDotSize1, tvBtnDotSize2, tvBtnDotSize3;
	private int mDotPixel = FD_DRAW.DOT_SIZE1;
	
	private void mFindPageViewById(View _view) {
		llMatrix = _view.findViewById(R.id.llMatrix);
		
		tvBtnPageSize32a32 = _view.findViewById(R.id.tvBtn32a32);
		tvBtnPageSize64a32 = _view.findViewById(R.id.tvBtn64a32);
		tvBtnPageSize128a32 = _view.findViewById(R.id.tvBtn128a32);
		tvBtnPageSize32a32.setOnClickListener(mOnPageMatrixClickListener);
		tvBtnPageSize64a32.setOnClickListener(mOnPageMatrixClickListener);
		tvBtnPageSize128a32.setOnClickListener(mOnPageMatrixClickListener);
		tvBtnPageSize64a32.setSelected(true);
		mPageSizeMatrixIdx = FD_DRAW.SIZE_64_32;
		
		tvBtnDotSize1 = _view.findViewById(R.id.tvBtnDotSize1);
		tvBtnDotSize2 = _view.findViewById(R.id.tvBtnDotSize2);
		tvBtnDotSize3 = _view.findViewById(R.id.tvBtnDotSize3);
		tvBtnDotSize1.setOnClickListener(mOnDotSizeClickListener);
		tvBtnDotSize2.setOnClickListener(mOnDotSizeClickListener);
		tvBtnDotSize3.setOnClickListener(mOnDotSizeClickListener);
		tvBtnDotSize1.setSelected(true);
		tvBtnDotSize2.setVisibility(View.INVISIBLE);
		tvBtnDotSize3.setVisibility(View.INVISIBLE);
		mDotPixel = FD_DRAW.DOT_SIZE1;
		
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
	
	public int mGetMatrixEA() {
		return mPageEA;
	}
	
	private View.OnClickListener mOnDotSizeClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			
			if(_id == R.id.tvBtnDotSize1) {
				mDotPixel = FD_DRAW.DOT_SIZE2;
				mFreeDrawView.mSetPixel(mDotPixel);
				
				tvBtnDotSize1.setSelected(false);
				tvBtnDotSize1.setVisibility(View.INVISIBLE);
				tvBtnDotSize2.setSelected(true);
				tvBtnDotSize2.setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnDotSize2) {
				mDotPixel = FD_DRAW.DOT_SIZE3;
				mFreeDrawView.mSetPixel(mDotPixel);
				
				tvBtnDotSize2.setSelected(false);
				tvBtnDotSize2.setVisibility(View.INVISIBLE);
				tvBtnDotSize3.setSelected(true);
				tvBtnDotSize3.setVisibility(View.VISIBLE);
			} else if(_id == R.id.tvBtnDotSize3) {
				mDotPixel = FD_DRAW.DOT_SIZE1;
				mFreeDrawView.mSetPixel(mDotPixel);
				
				tvBtnDotSize3.setSelected(false);
				tvBtnDotSize3.setVisibility(View.INVISIBLE);
				tvBtnDotSize1.setSelected(true);
				tvBtnDotSize1.setVisibility(View.VISIBLE);
			}
		}
	};
	
	private View.OnClickListener mOnPageNumClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int _id = view.getId();
			for(int i = 0; i < tvBtnPageNum.length; i++) {
				tvBtnPageNum[i].setSelected(false);
			}
			if(_id == R.id.tvBtnPageNum1) {
				mPageIdx = FD_DRAW.PAGE_1;
				llMatrix.setVisibility(View.VISIBLE);
				mFreeDrawView.mSetFreeDrawIdx(mPageIdx);
				tvBtnPageNum[mPageIdx].setSelected(true);
			} else if(_id == R.id.tvBtnPageNum2) {
				mPageIdx = FD_DRAW.PAGE_2;
				llMatrix.setVisibility(View.INVISIBLE);
				mFreeDrawView.mSetFreeDrawIdx(mPageIdx);
				tvBtnPageNum[mPageIdx].setSelected(true);
			} else if(_id == R.id.tvBtnPageNum3) {
				mPageIdx = FD_DRAW.PAGE_3;
				llMatrix.setVisibility(View.INVISIBLE);
				mFreeDrawView.mSetFreeDrawIdx(mPageIdx);
				tvBtnPageNum[mPageIdx].setSelected(true);
			} else if(_id == R.id.tvBtnPageNum4) {
				mPageIdx = FD_DRAW.PAGE_4;
				llMatrix.setVisibility(View.INVISIBLE);
				mFreeDrawView.mSetFreeDrawIdx(mPageIdx);
				tvBtnPageNum[mPageIdx].setSelected(true);
			} else if(_id == R.id.tvBtnPageNum5) {
				mPageIdx = FD_DRAW.PAGE_5;
				llMatrix.setVisibility(View.INVISIBLE);
				mFreeDrawView.mSetFreeDrawIdx(mPageIdx);
				tvBtnPageNum[mPageIdx].setSelected(true);
			} else if(_id == R.id.tvBtnAdd) {
				if(mPageEA <= FD_DRAW.PAGE_5) {
					llMatrix.setVisibility(View.INVISIBLE);
					tvBtnPageNum[mPageEA].setVisibility(View.VISIBLE);
					mPageIdx = mPageEA;
					mFreeDrawView.mSetFreeDrawIdx(mPageIdx);
					mPageEA++;
				}
				
				tvBtnPageNum[mPageEA - MAPP.START_ALIVE].setSelected(true);
			} else if(_id == R.id.tvBtnTrash) {
				if(mPageEA > MAPP.START_ALIVE) {
					int _idx = mPageEA - MAPP.START_ALIVE;
					tvBtnPageNum[_idx].setVisibility(View.GONE);
					tvBtnPageNum[_idx].setSelected(false);
					mPageEA--;
				}
				llMatrix.setVisibility(View.VISIBLE);
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
		}
	};
	// End Page
	
	/**
	 * mUpdate
	 * @param _colors
	 * @param _w
	 * @param _h
	 */
	public void mUpdateColors(int[] _colors, int _w, int _h) {
		mFreeDrawView.mSetAllColor(_colors, _w, _h);
	}
}
