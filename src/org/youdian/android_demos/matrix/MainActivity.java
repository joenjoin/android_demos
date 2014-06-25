package org.youdian.android_demos.matrix;

import org.youdian.android_demos.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/*		
 * 		Matrix的3*3矩阵值的含义
 *     {MSCALE_X,MSKEW_X,MTRANS_X,  
 *  	MSKEW_Y,MSCALE_Y,MTRANS_Y,  
 *  	MPERSP_0,MPERSP_1,MPERSP_2}  
 * 
 * skew变换 中文名 错切变换
 * 点（x,y）经过skew（kx，ky，px，py）变换之后，坐标为（kx*(y-py)+px，ky*(x-px)+py），
 * 如果，px和py没有，则默认为都为0。
 */
public class MainActivity extends Activity implements OnClickListener {
	ImageView iv;
	boolean changed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_imageview);
		iv = (ImageView) findViewById(R.id.iv);
		iv.setScaleType(ImageView.ScaleType.MATRIX);
		iv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Matrix matrix = null;
		if (!changed)
			matrix = applyMatrix();
		else
			matrix = clearmatrix();
		changed = !changed;
		iv.setImageMatrix(matrix);
	}

	private Matrix clearmatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	private Matrix applyMatrix() {
		Matrix matrix = new Matrix();
		Camera camera = new Camera();
		camera.rotateY(60);
		camera.getMatrix(matrix);
		return matrix;
	}

	/*
	 * 设置倒影效果
	 */
	private Matrix applyInversedMatrix() {
		Matrix matrix = new Matrix();
		matrix.postScale(1, -1);
		matrix.postTranslate(0, iv.getHeight());
		return matrix;
	}

	/*
	 * 设置镜面效果
	 */
	private Matrix applyMirroredMatrix() {
		Matrix matrix = new Matrix();
		matrix.setScale(-1, 1);
		matrix.postTranslate(iv.getWidth(), 0);
		/*
		 * 方法二 matrix.setTranslate(iv.getWidth(),0); matrix.preScale(-1,1);
		 */
		return matrix;
	}
}
