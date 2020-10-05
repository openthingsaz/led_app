package truesolution.ledpad.asign.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
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
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import androidx.loader.content.CursorLoader;
import truesolution.ledpad.asign.MDEBUG;
import truesolution.ledpad.asign.fd.FD_DRAW;

/**
 * Utils
 * Created by TCH on 2020/07/07
 *
 * @author think.code.help @gmail.com
 * @version 1.0
 * @since 2020 /07/07
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
	 * @param integer the integer
	 *
	 * @return byte [ ]
	 */
	public static byte[] mIntToByteArray(int integer) {
		ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE / 8);
		buff.putInt(integer);
		buff.order(ByteOrder.BIG_ENDIAN);
//		buff.order(ByteOrder.LITTLE_ENDIAN);
		return buff.array();
	}
	
	/**
	 * mBytesToInt
	 *
	 * @param _buf the buf
	 *
	 * @return int
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
	 * @param _buf    the buf
	 * @param _idx_sp the idx sp
	 * @param _idx_ep the idx ep
	 *
	 * @return int
	 */
	public static int mBytesToInt(byte[] _buf, int _idx_sp, int _idx_ep) {
		byte[] _data = new byte[_idx_ep - _idx_sp];
		System.arraycopy(_buf, _idx_sp, _data, 0, _data.length);
		return mBytesToInt(_data);
	}
	
	/**
	 * mMakeStrToStrLen2
	 *
	 * @param _str the str
	 *
	 * @return string
	 */
	public static String mMakeStrToStrLen2(String _str) {
		if(_str == null || _str.length() < 2)
			return null;
		
		return _str.substring(_str.length() - 2, _str.length());
	}
	
	/**
	 * mBytesToHex
	 *
	 * @param bytes the bytes
	 *
	 * @return string
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
	 * @param _g_drawable the g drawable
	 * @param _w          the w
	 * @param _h          the h
	 *
	 * @return bitmap
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
	 *
	 * @param _bm the bm
	 *
	 * @return byte [ ]
	 */
	public static byte[] mBitmapToByteArray(Bitmap _bm) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		_bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] _buf = stream.toByteArray();
		_bm.recycle();
		
		return _buf;
	}
	
	
	
	
	
	
	
	
	
	public static String getRealPath(Context context, Uri fileUri) {
		String realPath;
		// SDK < API11
		if (Build.VERSION.SDK_INT < 11) {
			realPath = Utils.getRealPathFromURI_BelowAPI11(context, fileUri);
		}
		// SDK >= 11 && SDK < 19
		else if (Build.VERSION.SDK_INT < 19) {
			realPath = Utils.getRealPathFromURI_API11to18(context, fileUri);
		}
		// SDK > 19 (Android 4.4) and up
		else {
			realPath = Utils.getRealPathFromURI_API19(context, fileUri);
		}
		return realPath;
	}
	
	
	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
		String[] proj = {MediaStore.Images.Media.DATA};
		String result = null;
		
		CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
		Cursor cursor = cursorLoader.loadInBackground();
		
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
			cursor.close();
		}
		return result;
	}
	
	public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
		String[] proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		int column_index = 0;
		String result = "";
		if (cursor != null) {
			column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
			cursor.close();
			return result;
		}
		return result;
	}
	
	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri     The Uri to query.
	 * @author paulburke
	 */
	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API19(final Context context, final Uri uri) {
		
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		
		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				
				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
				
				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {
				
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
				
				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				
				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				
				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{
						split[1]
				};
				
				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			
			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();
			
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		
		return null;
	}
	
	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	                                   String[] selectionArgs) {
		
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {
				column
		};
		
		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}
	
	
	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}
	
	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}
	
	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}
	
	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
	
	/**
	 * M get bitmap from uri bitmap.
	 *
	 * @param _context the context
	 * @param _path    the path
	 * @param uri      the uri
	 * @param _w       the w
	 * @param _h       the h
	 * @param _size    the size
	 *
	 * @return the bitmap
	 *
	 * @throws IOException the io exception
	 */
	public static Bitmap mGetBitmapFromUri(Context _context, String _path, Uri uri, int _w, int _h, long _size) throws IOException {
		ParcelFileDescriptor parcelFileDescriptor =
				_context.getContentResolver().openFileDescriptor(uri, "r");
		FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
		
		ExifInterface exif = new ExifInterface(_path);
		int _orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
		MDEBUG.debug("---- orientation : " + _orientation);
		
		android.graphics.BitmapFactory.Options _opt = new android.graphics.BitmapFactory.Options();
		if(_size > FD_DRAW.CHECK_IMG_FILE_SIZE) {
			_opt.inSampleSize = FD_DRAW.IMG_SAMPLE_SIZE_DOWN;
		} else {
			_opt.inSampleSize = FD_DRAW.IMG_SAMPLE_SIZE;
		}
		Bitmap _img = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, _opt);
		Bitmap __img = mModifyOrientation(_img, _orientation);
		Bitmap _s_img = Bitmap.createScaledBitmap(__img, _w, _h, false);
		parcelFileDescriptor.close();
		return _s_img;
	}
	
	/**
	 * M modify orientation bitmap.
	 *
	 * @param bitmap       the bitmap
	 * @param _orientation the orientation
	 *
	 * @return the bitmap
	 *
	 * @throws IOException the io exception
	 */
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
	
	/**
	 * Rotate bitmap.
	 *
	 * @param bitmap  the bitmap
	 * @param degrees the degrees
	 *
	 * @return the bitmap
	 */
	public static Bitmap rotate(Bitmap bitmap, float degrees) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degrees);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	/**
	 * Flip bitmap.
	 *
	 * @param bitmap     the bitmap
	 * @param horizontal the horizontal
	 * @param vertical   the vertical
	 *
	 * @return the bitmap
	 */
	public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
		Matrix matrix = new Matrix();
		matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
}
