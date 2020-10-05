package truesolution.ledpad.asign.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.MainActivity;
import truesolution.ledpad.asign.R;
import truesolution.ledpad.asign.app.MAPP;
import truesolution.ledpad.asign.fd.FD_BT;
import truesolution.ledpad.asign.utils.MBluetoothUtils;

/**
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/07
 */
public class MFragmentShowMode extends Fragment {
	private EditText etDeviceID;
	
	private TextView tvBtnShowModeTopMenuInfo;
	private View mView;
	private MainActivity mActivity;
	
	private Spinner spChannelList;
	private ArrayAdapter mArrayAdapter;
	private final String[] CHANNEL_LIST = {
			"0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9"
	};
	
	private final int RANGE_MIN                 = 0;
	private final int RANGE_MAX                 = 255;
	
	private TextView tvConnectionResult, tvBtnMenuSMConnection, tvBtnMenuSMDisconnection;
	
	/**
	 * Instantiates a new M fragment show mode.
	 *
	 * @param _activity the activity
	 */
	public MFragmentShowMode(MainActivity _activity) {
		mActivity = _activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mView == null) {
			mView = inflater.inflate(R.layout.fragment_show_mode, container, false);
			findViewsById(mView);
		}
		return mView;
	}
	
	private void findViewsById(View _view) {
		etDeviceID = _view.findViewById(R.id.etDeviceID);
		etDeviceID.setFilters(new InputFilter[]{ new InputFilterMinMax(RANGE_MIN, RANGE_MAX)});
		
		tvBtnShowModeTopMenuInfo = _view.findViewById(R.id.tvBtnShowModeTopMenuInfo);
		tvBtnShowModeTopMenuInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mActivity.mGoInfoActivity();;
			}
		});
		
		spChannelList = _view.findViewById(R.id.spChannelList);
		spChannelList.setBackground(null);
		
		ArrayList<String> _list = new ArrayList<>();
		for(int i = 0; i < CHANNEL_LIST.length; i++) {
			_list.add(CHANNEL_LIST[i]);
		}
		mArrayAdapter = new ArrayAdapter(mActivity.getBaseContext(),
				R.layout.support_simple_spinner_dropdown_item,
				_list);
		spChannelList.setAdapter(mArrayAdapter);
		
		tvConnectionResult = _view.findViewById(R.id.tvConnectionResult);
		tvConnectionResult.setText(R.string.text_disconnected);
		tvConnectionResult.setTextColor(Color.RED);
		
		tvBtnMenuSMConnection = _view.findViewById(R.id.tvBtnMenuSMConnection);
		tvBtnMenuSMDisconnection = _view.findViewById(R.id.tvBtnMenuSMDisconnection);
		tvBtnMenuSMConnection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String _id = etDeviceID.getText().toString();
				if(_id == null || _id.isEmpty()) {
					mActivity.mShowMessageDialog(R.string.text_menu_show_mode_device_id, false);
					return;
				}
				
				int __id = Integer.parseInt(_id);
				MDEBUG.debug("__id : " + __id);
				byte[] _send_buf = new byte[FD_BT.SIZE_PERFORM_ID + FD_BT.SIZE_PERFORM_CHANNEL];
				_send_buf[FD_BT.IDX_PERFORM_CHANNEL] = (byte)(spChannelList.getSelectedItemPosition());
				_send_buf[FD_BT.IDX_PERFORM_ID] = (byte)__id;
				
//				MDEBUG.debug("_send_buf[FD_BT.IDX_PERFORM_CHANNEL] : " + _send_buf[FD_BT.IDX_PERFORM_CHANNEL]);
//				MDEBUG.debug("_send_buf[FD_BT.IDX_PERFORM_ID] : " + _send_buf[FD_BT.IDX_PERFORM_ID]);
				MBluetoothUtils.transmit_data(MainActivity.mBtSpp, FD_BT.SET_PERFORM_MODE, _send_buf);
			}
		});
		tvBtnMenuSMDisconnection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
	
	/**
	 * The type Input filter min max.
	 */
	public class InputFilterMinMax implements InputFilter {
		private int min, max;
		
		/**
		 * Instantiates a new Input filter min max.
		 *
		 * @param min the min
		 * @param max the max
		 */
		public InputFilterMinMax(int min, int max) {
			this.min = min;
			this.max = max;
		}
		
		/**
		 * Instantiates a new Input filter min max.
		 *
		 * @param min the min
		 * @param max the max
		 */
		public InputFilterMinMax(String min, String max) {
			this.min = Integer.parseInt(min);
			this.max = Integer.parseInt(max);
		}
		
		@Override
		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			try {
				int input = Integer.parseInt(dest.toString() + source.toString());
				if (isInRange(min, max, input))
					return null;
			} catch (NumberFormatException nfe) { }
			return "";
		}
		
		private boolean isInRange(int a, int b, int c) {
			return b > a ? c >= a && c <= b : c >= b && c <= a;
		}
	}
}
