package truesolution.ledpad.asign.fragment.str;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_Text {
	public String mText;
	public int mFontSizeIdx;
	public int mColor;
	public int mAction;
	
	public STR_Text(){
	}
	
	/**
	 * Set Text
	 * @param _text
	 * @param _size_idx
	 * @param _color
	 * @param _action
	 */
	public STR_Text(String _text, int _size_idx, int _color, int _action) {
		mSetData(_text, _size_idx, _color, _action);
	}
	
	public void mSetData(String _text, int _size_idx, int _color, int _action) {
		mText = _text;
		mFontSizeIdx = _size_idx;
		mColor = _color;
		mAction = _action;
	}
	
	public void mSetData(STR_Text _str) {
		mSetData(_str.mText, _str.mFontSizeIdx, _str.mColor, _str.mAction);
	}
}