package org.youdian.android_demos.storage;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.widget.TextView;

public class StorageActivity extends Activity{
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv=(TextView)findViewById(R.id.tv);
		StorageManager sm=(StorageManager) getSystemService(Context.STORAGE_SERVICE);
		StorageVolume[] volumes=sm.getVolumeList();
		for(StorageVolume volume:volumes){
			String path=volume.getPath();
			String des=volume.getDescrition();
			tv.append(path+"\n");
			tv.append(des+"\n");
		}
	}
	
	

}
