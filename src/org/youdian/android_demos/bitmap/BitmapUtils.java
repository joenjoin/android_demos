package org.youdian.android_demos.bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.graphics.Bitmap;

public class BitmapUtils {
	/*
	 * bitmap 转换成inputstream
	 */
	public static InputStream bitmapToInputStream(Bitmap bitmap){
		InputStream is=null;
		if(bitmap==null)return null;
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		is=new ByteArrayInputStream(out.toByteArray());
		return is;
	}

}
