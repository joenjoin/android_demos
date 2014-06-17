package org.youdian.android_demos.bitmap;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ImageSelectActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	private Button selectImageBtn;
	private Button cutImageBtn;
	private ImageView imageView;
	// 声明两个静态的整形变量，主要是用于意图的返回的标志
	private static final int IMAGE_SELECT = 1;
	private static final int IMAGE_CUT = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitmap_imageselect);
		selectImageBtn = (Button) this.findViewById(R.id.imageSelect);
		cutImageBtn = (Button) this.findViewById(R.id.imageCut);
		imageView = (ImageView) this.findViewById(R.id.imageView);
		selectImageBtn.setOnClickListener(this);
		cutImageBtn.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			// 处理图片按照手机的屏幕大小显示
			if (requestCode == IMAGE_SELECT) {
				Uri uri = data.getData();// 获得图片的路径
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				int dw = dm.widthPixels;// 获得屏幕的宽度
				int dh = dm.heightPixels;// 获得屏幕的高度
				try {
					// 实现对图片的裁剪的类，是一个匿名内部类
					BitmapFactory.Options factory = new BitmapFactory.Options();
					factory.inJustDecodeBounds = true;// 如果设置为true,允许查询图片不是按照像素分配给内存
					Bitmap bmp = BitmapFactory.decodeStream(
							getContentResolver().openInputStream(uri), null,
							factory);
					// 对图片的宽度、高度对应手机的屏幕进行匹配
					int hRatio = (int) Math
							.ceil(factory.outHeight / (float) dh);
					// 如果大于1表示图片的高度大于手机屏幕的高度
					int wRatio = (int) Math.ceil(factory.outWidth / (float) dw);
					// 如果大于1表示图片的宽度大于手机屏幕的宽度
					// 缩放到1/radio的尺寸和1/radio^2像素
					if (hRatio > 1 || wRatio > 1) {
						if (hRatio > wRatio) {
							factory.inSampleSize = hRatio;
						} else {
							factory.inSampleSize = wRatio;
						}
					}

					factory.inJustDecodeBounds = false;
					// 使用BitmapFactory对图片进行适屏的操作
					bmp = BitmapFactory.decodeStream(getContentResolver()
							.openInputStream(uri), null, factory);
					imageView.setImageBitmap(bmp);
				} catch (Exception e) {
					// TODO: handle exception
				}
				// 表示裁剪图片
			} else if (requestCode == IMAGE_CUT) {
				Bitmap bitmap = data.getParcelableExtra("data");
				if (bitmap == null) {
					Log.d("ImageSelectActivity", "bitmap is null");
				}
				imageView.setImageBitmap(bitmap);
			}
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageSelect:
			// 如何提取手机的图片，并且进行选择图片的功能
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 打开手机的图片库
			startActivityForResult(intent, IMAGE_SELECT);
			break;
		case R.id.imageCut:
			Intent intent2 = getImageClipIntent();
			startActivityForResult(intent2, IMAGE_CUT);
			break;
		}
	}

	private Intent getImageClipIntent() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		// 实现对图片的裁剪，必须要设置图片的属性和大小
		intent.setType("image/*");// 获取任意的图片类型
		intent.putExtra("crop", "true");// 以滑动形式来选中图片区域
		intent.putExtra("aspectX", 1);// 表示剪切框的比例为：1
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80);// 指定输出图片的大小为：80*80
		intent.putExtra("outputY", 80);
		intent.putExtra("return_data", true);
		return intent;
	}

}
