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
import truesolution.ledpad.asign.fragment.str.STR_BluetoothDevice;

/**
 * Created by TCH on 2020/07/10
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/10
 */
public class MBluetoothDeviceBaseAdapter extends BaseAdapter {
	private final Context mContext;
	private List<STR_BluetoothDevice> mList = new ArrayList<>();
	
	/**
	 * Instantiates a new M bluetooth device base adapter.
	 *
	 * @param context the context
	 * @param _list   the list
	 */
	public MBluetoothDeviceBaseAdapter(Context context, List<STR_BluetoothDevice> _list) {
		this.mContext = context;
		mList = _list;
	}
	
	/**
	 * M get cell data str bluetooth device.
	 *
	 * @param _pos the pos
	 *
	 * @return the str bluetooth device
	 */
	public STR_BluetoothDevice mGetCellData(int _pos) {
		if(_pos >= mList.size()) {
			return null;
		}
		
		return mList.get(_pos);
	};
	
	/**
	 * M update data and view.
	 */
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
		MDEBUG.debug("getView : " + position);
		TextView _tv = null;
		ViewHolder _g_vh;
		if (convertView == null) {
			final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			convertView = layoutInflater.inflate(R.layout.cell_device, null);
			_g_vh = new ViewHolder();
			_g_vh.tvName = convertView.findViewById(R.id.tvName);
			_g_vh.tvAddress = convertView.findViewById(R.id.tvAddress);
			
			convertView.setTag(_g_vh);
		} else {
			_g_vh = (ViewHolder) convertView.getTag();
		}
		STR_BluetoothDevice _str = mList.get(position);
		String _name = _str.mName;
		if(_name == null) {
			_name = mContext.getResources().getString(R.string.text_device_name_none);
		}
		_g_vh.tvName.setText(_name);
		_g_vh.tvAddress.setText(_str.mAddress);
		
		return convertView;
	}
	
	/**
	 * The type View holder.
	 */
	public static class ViewHolder {
		/**
		 * The Tv name.
		 */
		public TextView tvName;
		/**
		 * The Tv address.
		 */
		public TextView tvAddress;
	}
}
