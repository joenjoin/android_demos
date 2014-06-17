package org.youdian.android_demos.storage;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.widget.TextView;
//import android.os.storage.StorageVolume;

/*

 * 
 */
public class StorageActivity extends Activity {
	TextView tv;
	StorageManager sm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_textview);
		tv = (TextView) findViewById(R.id.tv);

	}
	/*
	 * 调用了StorageManager的隐藏方法getVolumeList和隐藏类StorageVolume，需要使用Android.mk编译生成apk文件
	 * Android.mk 文件格式 LOCAL_PATH := $(call my-dir) include $(CLEAR_VARS)
	 * LOCAL_MODULE_TAGS := optional LOCAL_SRC_FILES := $(call
	 * all-java-files-under,src) LOCAL_PACKAGE_NAME := android_demo
	 * 
	 * include $(BUILD_PACKAGE)
	 */
	/*
	 * public void getExternalStorage(){
	 * 
	 * sm=(StorageManager) getSystemService(Context.STORAGE_SERVICE);
	 * StorageVolume[] volumes=sm.getVolumeList(); for(StorageVolume
	 * volume:volumes){ String path=volume.getPath(); String
	 * description=volume.getDescrition(); String
	 * isMounted=isMounted(volume.getPath()); String
	 * isExternal=volume.isRemovable(); tv.append(path+"\n");
	 * tv.append(description+"\n"); }
	 * 
	 * }
	 */
	/*
	 * protected boolean isMounted(String path){ if(TextUtils.isEmpty(path))
	 * return false; String state=null; state=sm.getVolumeState(path); return
	 * Environment.MEDIA_MOUNTED.equals(state); }
	 */

}
