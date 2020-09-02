package truesolution.ledpad.asign.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import androidx.annotation.RequiresApi;
import androidx.loader.content.CursorLoader;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.fd.FD_DRAW;

/**
 * Utils
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help@gmail.com
 * @version 1.0
 * @since 2020/07/07
 *
 */
public class Utils {
	/**
	 * Char Set - UTF-8
	 */
	public static final String PH_CHAR_SET							= "UTF-8";
	
	/**
	 * Hex Array '0123456789ABCDEF'
	 */
	private final static char[] mHexStrArray						= "0123456789ABCDEF".toCharArray();
	
	/**
	 * mIntToByteArray
	 * 
	 * @param integer
	 * @return
	 */
	public static byte[] mIntToByteArray(final int integer) {
		ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE / 8);
		buff.putInt(integer);
		buff.order(ByteOrder.BIG_ENDIAN);
//		buff.order(ByteOrder.LITTLE_ENDIAN);
		return buff.array();
	}
	
	/**
	 * mBytesToInt
	 * 
	 * @param _buf
	 * @return
	 */
	public static int mBytesToInt(byte[] _buf) {
		return (_buf[0] << 24) & 0xff000000 |
				(_buf[1] << 16) & 0x00ff0000 |
				(_buf[2] << 8) & 0x0000ff00 |
				(_buf[3] << 0) & 0x000000ff;
	}
	
	/**
	 * mBytesToInt
	 * 
	 * @param _buf
	 * @param _idx_sp
	 * @param _idx_ep
	 * @return
	 */
	public static int mBytesToInt(byte[] _buf, int _idx_sp, int _idx_ep) {
		byte[] _data = new byte[_idx_ep - _idx_sp];
		System.arraycopy(_buf, _idx_sp, _data, 0, _data.length);
		return mBytesToInt(_data);
	}
	
	/**
	 * mMakeStrToStrLen2
	 * 
	 * @param _str
	 * @return
	 */
	public static String mMakeStrToStrLen2(String _str) {
		if(_str == null || _str.length() < 2)
			return null;
		
		return _str.substring(_str.length() - 2, _str.length());
	}
	
	/**
	 * mBytesToHex
	 * 
	 * @param bytes
	 * @return
	 */
	public static String mBytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = mHexStrArray[v >>> 4];
			hexChars[j * 2 + 1] = mHexStrArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	/**
	 * GradientDrawable to Bitmap
	 *
	 * @param _g_drawable
	 * @param _w
	 * @param _h
	 * @return
	 */
	public static Bitmap mGDrawableToBitmap(GradientDrawable _g_drawable, int _w, int _h) {
		Bitmap _bm = Bitmap.createBitmap(_w, _h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(_bm);
		_g_drawable.setBounds(0, 0, _w, _h);
		_g_drawable.draw(canvas);
		
		return _bm;
	}
	
	/**
	 * Bitmap To Byte Array
	 * @param _bm
	 * @return
	 */
	public static byte[] mBitmapToByteArray(Bitmap _bm) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		_bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] _buf = stream.toByteArray();
		_bm.recycle();
		
		return _buf;
	}
	
	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API19(Context context, Uri uri){
		String filePath = "";
		String wholeID = DocumentsContract.getDocumentId(uri);
		
		String id = wholeID.split(":")[1];
		
		String[] column = { MediaStore.Images.Media.DATA };
		
		// where id is equal to
		String sel = MediaStore.Images.Media._ID + "=?";
		
		Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				column, sel, new String[]{ id }, null);
		
		int columnIndex = cursor.getColumnIndex(column[0]);
		
		if (cursor.moveToFirst()) {
			filePath = cursor.getString(columnIndex);
		}
		cursor.close();
		return filePath;
	}
	
	
	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		String result = null;
		
		CursorLoader cursorLoader = new CursorLoader(
				context,
				contentUri, proj, null, null, null);
		Cursor cursor = cursorLoader.loadInBackground();
		
		if(cursor != null){
			int column_index =
					cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
		}
		return result;
	}
	
	public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		int column_index
				= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	public static Bitmap mGetBitmapFromUri(Context _context, String _path, Uri uri, int _w, int _h) throws IOException {
		ParcelFileDescriptor parcelFileDescriptor =
				_context.getContentResolver().openFileDescriptor(uri, "r");
		FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
		
		ExifInterface exif = new ExifInterface(_path);
		int _orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
		MDEBUG.debug("---- orientation : " + _orientation);
		
		android.graphics.BitmapFactory.Options _opt = new android.graphics.BitmapFactory.Options();
		_opt.inSampleSize = FD_DRAW.IMG_SAMPLE_SIZE;
		Bitmap _img = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, _opt);
		Bitmap __img = mModifyOrientation(_img, _orientation);
		Bitmap _s_img = Bitmap.createScaledBitmap(__img, _w, _h, false);
		parcelFileDescriptor.close();
		return _s_img;
	}
	
	public static Bitmap mModifyOrientation(Bitmap bitmap, int _orientation) throws IOException {
		switch (_orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				return rotate(bitmap, 90);
			
			case ExifInterface.ORIENTATION_ROTATE_180:
				return rotate(bitmap, 180);
			
			case ExifInterface.ORIENTATION_ROTATE_270:
				return rotate(bitmap, 270);
			
			case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
				return flip(bitmap, true, false);
			
			case ExifInterface.ORIENTATION_FLIP_VERTICAL:
				return flip(bitmap, false, true);
			
			default:
				return bitmap;
		}
	}
	
	public static Bitmap rotate(Bitmap bitmap, float degrees) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degrees);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
		Matrix matrix = new Matrix();
		matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
}
