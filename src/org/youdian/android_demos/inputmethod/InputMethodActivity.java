package org.youdian.android_demos.inputmethod;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputMethodActivity extends Activity {
	
	EditText et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inputmethod_inputmethod);
		et=(EditText)findViewById(R.id.et);
	}

	public void hideOrShowInputMethod(View view){
		InputMethodManager imm=(InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive()){
			Log.d("inputmethod", "isActive");
			imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
		}
		
		
	}

}
