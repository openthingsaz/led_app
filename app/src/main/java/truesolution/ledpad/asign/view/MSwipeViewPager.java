package truesolution.ledpad.asign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by TCH on 2020/07/18
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/18
 */
public class MSwipeViewPager extends ViewPager {
	private boolean enabled;
	
	/**
	 * Instantiates a new M swipe view pager.
	 *
	 * @param context the context
	 */
	public MSwipeViewPager(Context context) {
		super(context);
	}
	
	/**
	 * Instantiates a new M swipe view pager.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public MSwipeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (enabled) {
			return super.onInterceptTouchEvent(ev);
		} else {
			if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
// ignore move action
			} else {
				if (super.onInterceptTouchEvent(ev)) {
					super.onTouchEvent(ev);
				}
			}
			return false;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (enabled) {
			return super.onTouchEvent(ev);
		} else {
			return MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev);
		}
	}
	
	/**
	 * M set paging enabled.
	 *
	 * @param enabled the enabled
	 */
	public void mSetPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
