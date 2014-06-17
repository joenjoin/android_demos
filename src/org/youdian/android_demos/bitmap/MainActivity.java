package org.youdian.android_demos.bitmap;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitmap_main);
		iv = (ImageView) findViewById(R.id.iv);
	}

	public void onClick(View view) {
		Bitmap src = BitmapFactory.decodeResource(getResources(),
				R.drawable.profile);
		Bitmap dst = BitmapUtils.createFrameBitmap(src, Color.BLUE);
		iv.setImageBitmap(dst);
	}

}
