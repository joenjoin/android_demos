package org.youdian.android_demos.bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
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
	
	/*
	 * 圆角图片
	 * 为图片添加圆角效果
	 */
	public static Bitmap createRoundedCornerBitmap(Bitmap src){
		if(src==null)
			return null;
		Bitmap dst=Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(dst);
		Paint paint=new Paint();
		Rect rect=new Rect(0,0,src.getWidth(),src.getHeight());
		RectF rectF=new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.BLACK);
		float corner=15.0f;
		canvas.drawRoundRect(rectF, corner, corner, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(src, rect, rect, paint);
		return dst;
	}
	/*
	 * 创建圆角图片的第二种方式,这种方式最好
	 */
	public static Bitmap createRoundedCornerBitmap2(Bitmap src){
		Bitmap dst=Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
		BitmapShader shader;
		shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		Canvas canvas=new Canvas(dst);
		float radius=15.0f;
		Paint paint = new Paint();

		paint.setAntiAlias(true);

		paint.setShader(shader);

		RectF rect = new RectF(0.0f, 0.0f, src.getWidth(), src.getHeight());

		// rect contains the bounds of the shape

		// radius is the radius in pixels of the rounded corners

		// paint contains the shader that will texture the shape

		canvas.drawRoundRect(rect, radius, radius, paint);
		return dst;
	}
	/*
	 * bitmap to byte[]
	 */
	public byte[] bitmapToBytes(Bitmap bitmap){
		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bout);
		return bout.toByteArray();
	}
	/*
	 * byte[] to bitmap
	 */
	public Bitmap bytesToBitmap(byte[] bytes){
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		
	}
	
	public File bitmapToFile(Bitmap bitmap){
		File f=new File("b.png");
		OutputStream out=null;
		try {
			out = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return f;
	}
	/*
	 * 创建带镜像图片
	 */
	public  static Bitmap createReflection(Bitmap src){
		if(src==null)
			return null;
		int gap=4;
		int width=src.getWidth();
		int height=src.getHeight();
		Matrix matrix=new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflection=Bitmap.createBitmap(src, 0, height/2, width, height/2,matrix,false);
		Bitmap dst=Bitmap.createBitmap(width, height+height/2, Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(dst);
		Paint paint=new Paint();
		canvas.drawBitmap(src, 0, 0, null);
		canvas.drawRect(0, height, width, height+gap, paint);
		canvas.drawBitmap(reflection, 0, height+gap, null);
		LinearGradient shader = new LinearGradient(0,src.getHeight(), 0, 
				dst.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, dst.getHeight()+gap, paint);

		return dst;
		
	}
}
