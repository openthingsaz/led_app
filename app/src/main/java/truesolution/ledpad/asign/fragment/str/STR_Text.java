package truesolution.ledpad.asign.fragment.str;

/**
 * Created by TCH on 2020. 07. 01.
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020. 07. 01.
 */
public class STR_Text {
	/**
	 * The M text.
	 */
	public String mText;
	/**
	 * The M font size idx.
	 */
	public int mFontSizeIdx;
	/**
	 * The M color.
	 */
	public int mColor;
	/**
	 * The M action.
	 */
	public int mAction;
	
	/**
	 * Instantiates a new Str text.
	 */
	public STR_Text(){
	}
	
	/**
	 * Set Text
	 *
	 * @param _text     the text
	 * @param _size_idx the size idx
	 * @param _color    the color
	 * @param _action   the action
	 */
	public STR_Text(String _text, int _size_idx, int _color, int _action) {
		mSetData(_text, _size_idx, _color, _action);
	}
	
	/**
	 * M set data.
	 *
	 * @param _text     the text
	 * @param _size_idx the size idx
	 * @param _color    the color
	 * @param _action   the action
	 */
	public void mSetData(String _text, int _size_idx, int _color, int _action) {
		mText = _text;
		mFontSizeIdx = _size_idx;
		mColor = _color;
		mAction = _action;
	}
	
	/**
	 * M set data.
	 *
	 * @param _str the str
	 */
	public void mSetData(STR_Text _str) {
		mSetData(_str.mText, _str.mFontSizeIdx, _str.mColor, _str.mAction);
	}
}