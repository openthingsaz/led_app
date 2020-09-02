package truesolution.ledpad.asign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fd.FD_DRAW;
import truesolution.ledpad.asign.itf.ITF_FreeDraw;

/**
 * Created by TCH on 2020/07/15
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/15
 */

public class MFreeDrawView extends View {
	private int mW, mH;
	private int mCellWH;
	public int mCellEAW, mCellEAH;
	private int mTempIdxX, mTempIdxY;
	private Paint mCellPaint = new Paint();
	public boolean mTouchHold;
	public int mStartX, mStartY;
	public int mAreaW, mAreaH;
	public int[][][] mCellColor;
	private int mCellIdx;
	private int mColor;
	private int mDotPixel = FD_DRAW.DOT_SIZE1;
	private ITF_FreeDraw itfFreeDraw;
	
	private final int LINE_PX                               = 1;
	
	public MFreeDrawView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		
		new MViewSizeAsyncTask().execute();
		setBackgroundColor(context.getResources().getColor(R.color.c_black_1));
		
		mColor = Color.BLACK;
		
		mCellPaint.setStyle(Paint.Style.FILL);
		mCellPaint.setColor(mColor);
	}
	
	public void mSetTouchIdxListener(ITF_FreeDraw _itf) {
		itfFreeDraw = _itf;
	}
	
	public void mSetColor(int _color) {
		mColor = _color;
	}
	
	public void mSetPixel(int _pixel_size) {
		mDotPixel = _pixel_size;
	}
	
	public int mGetColor(int _x_idx, int _y_idx) {
		return mCellColor[mCellIdx][_x_idx][_y_idx];
	}
	
	public int[][] mGetCurrentColors() {
		return mCellColor[mCellIdx];
	}
	
	public int[][] mGetColors(int _idx) {
		return mCellColor[_idx];
	}
	
	public void mSetFreeDrawIdx(int _idx) {
		mCellIdx = _idx;
		invalidate();
	}
	
	public void mSetAllColor(int[] _colors, int _w, int _h) {
		for(int y = 0; y < _h; y++) {
			for(int x = 0; x < _w; x++) {
				int _color = _colors[y * _w + x];
				if(Color.alpha(_color) == 0) {
					mCellColor[mCellIdx][x][y] = Color.BLACK;
				} else {
					mCellColor[mCellIdx][x][y] = _color;
				}
			}
		}
		
		invalidate();
	}
	
	public void mSetAllColor(int _color) {
		mColor = _color;
		
		for(int y = 0; y < mCellEAH; y++) {
			for(int x = 0; x < mCellEAW; x++) {
				mCellColor[mCellIdx][x][y] = mColor;
			}
		}
		
		invalidate();
	}
	
	public void mSetCellEA(int _w_ea, int _h_ea) {
		mCellEAW = _w_ea;
		mCellEAH = _h_ea;
	}
	
	public void mUpdateCell(int _w_ea, int _h_ea) {
		mSetCellEA(_w_ea, _h_ea);
		new MSetCellViewAsyncTask().execute();
	}
	
	private class MSetCellViewAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			final int _line_px_w = (mCellEAW * LINE_PX);
			final int _line_px_h = (mCellEAH * LINE_PX);
			final int _w = (mW - _line_px_w) / mCellEAW;
			final int _h = (mH - _line_px_h) / mCellEAH;
			if(_w > _h) {
				mCellWH = _h;
			} else {
				mCellWH = _w;
			}
			
			mStartX = (int)((mW - ((mCellWH * mCellEAW) + (_line_px_w))) / 2);
			mStartY = (int)((mH - ((mCellWH * mCellEAH) + (_line_px_h))) / 2);
			
			mAreaW = mW - (mStartX * 2);
			mAreaH = mH - (mStartY * 2);
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mCellColor = new int[FD_DRAW.MAX_DRAW_PAGE][mCellEAW][mCellEAH];
			for(int i = 0; i < FD_DRAW.MAX_DRAW_PAGE; i++) {
				for(int y = 0; y < mCellEAH; y++) {
					for(int x = 0; x < mCellEAW; x++) {
						mCellColor[i][x][y] = Color.BLACK;
					}
				}
			}
			invalidate();
		}
	}
	
	private class MViewSizeAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			return null;
		}
		
		@Override
		public void onPostExecute(Void _void) {
			mW = getMeasuredWidth();
			mH = getMeasuredHeight();
			
			if(mW > MAPP.LCD_W) {
				new MViewSizeAsyncTask().execute();
			} else {
				if(mCellEAW > 0) {
					new MSetCellViewAsyncTask().execute();
				}
			}
			MDEBUG.debug("MViewSizeAsyncTask w : " + getMeasuredWidth() + ", h : " + getMeasuredHeight());
		}
	}
	
	@Override
	public void onDraw(Canvas _canvas) {
		super.onDraw(_canvas);
		
		if(mCellColor == null)
			return;
		
		if(mCellWH > 0) {
			for(int y = 0; y < mCellEAH; y++) {
				for(int x = 0; x < mCellEAW; x++) {
					int __x = mStartX + (x * mCellWH) + (x * LINE_PX);
					int __y = mStartY + (y * mCellWH) + (y * LINE_PX);
					int _color = mCellColor[mCellIdx][x][y];
					mCellPaint.setColor(_color);
					Rect _rect = new Rect(__x, __y, __x + mCellWH, __y + mCellWH);
					_canvas.drawRect(_rect, mCellPaint);
				}
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent _event) {
		float _x = _event.getX();
		float _y = _event.getY();
		int _action = _event.getAction();
		
//		MDEBUG.debug("onTouchEvent _x : " + _x + ", _y : " + _y);
		final int _idx_x = mTouchXToIdx(_x);
		final int _idx_y = mTouchYToIdx(_y);
		
		if(_action == MotionEvent.ACTION_DOWN) {
			mTempIdxX = _idx_x;
			mTempIdxY = _idx_y;
		} else {
			if(mTempIdxX == _idx_x && mTempIdxY == _idx_y) {
				return true;
			}
		}
		if((_idx_x >= 0 && _idx_y >= 0) &&
				_idx_x < mCellEAW && _idx_y < mCellEAH) {
			itfFreeDraw.mTouchToIdx(_idx_x, _idx_y);
			
			if(mTouchHold) {
				return true;
			}
			
			int _end_x = (_idx_x + mDotPixel);
			int _end_y = (_idx_y + mDotPixel);
//			MDEBUG.debug("mDotPixel : " + mDotPixel);
//			MDEBUG.debug("_idx_x : " + _idx_x + ", _idx_y : " + _idx_y);
			
			if(mDotPixel == FD_DRAW.DOT_SIZE1)
				mCellColor[mCellIdx][_idx_x][_idx_y] = mColor;
			else {
				for(int __x = _idx_x; __x < _end_x; __x++) {
					for(int __y = _idx_y; __y < _end_y; __y++) {
						if(__x < mCellEAW && __y < mCellEAH) {
							mCellColor[mCellIdx][__x][__y] = mColor;
						}
					}
				}
			}
			invalidate();
		}
		return true;
	}
	
	private int mTouchXToIdx(float _x) {
		int _idx = MAPP.ERROR_;
		_idx = (int)((_x - mStartX) / (mCellWH + LINE_PX));
		return _idx;
	}
	
	private int mTouchYToIdx(float _y) {
		int _idx = MAPP.ERROR_;
		_idx = (int)((_y - mStartY) / (mCellWH + LINE_PX));
		return _idx;
	}
}
