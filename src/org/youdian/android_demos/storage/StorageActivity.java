package org.youdian.android_demos.storage;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.widget.TextView;
//android系统隐藏类
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
		tv=new TextView(this);
		setContentView(tv);
		getStorageInfo();
	}
	
	/*
	 * 使用反射技术调用StorageManager和StorageVolume的隐藏方法
	 * 
	 */
		private void getStorageInfo(){
			sm=(StorageManager) getSystemService(Context.STORAGE_SERVICE);
			Method getVolumeList=null;
			//StorageVolume[];
			Object mStorageVolumeList=null;
			Object mStorageVolume=null;
			try {
				getVolumeList=sm.getClass().getMethod("getVolumeList");
				mStorageVolumeList=(Object) getVolumeList.invoke(sm, null);
				Class<?> StorageVolumeReflection=Class.forName("android.os.storage.StorageVolume");
				//volume.getPath();
				Method getPath=StorageVolumeReflection.getMethod("getPath", null);
				//volume.getDescription()
				Method getDescription=StorageVolumeReflection.getMethod("getDescription", null);
				//volume.isRemovable()
				Method isExternal=StorageVolumeReflection.getMethod("isRemovable", null);
				//sm.getVolumeState(String path)
				Method getVolumeState=sm.getClass().getMethod("getVolumeState", String.class);
				
				int volumeCount=Array.getLength(mStorageVolumeList);
				System.out.println("count="+volumeCount);
				for(int i=0;i<volumeCount;i++){
					mStorageVolume=(Object) Array.get(mStorageVolumeList, i);
					// 存储设备路径
					String path=(String) getPath.invoke(mStorageVolume,null);
					//存储设备名称 ，如手机存储，SD卡等
					String description=(String) getDescription.invoke(mStorageVolume, null);
					//存储设备是否可移除,可以移除为SD卡，不能移除则为手机存储
					boolean isSDCard=(boolean) isExternal.invoke(mStorageVolume, null);
					String volumeState=(String) getVolumeState.invoke(sm, path);
					boolean isMounted=Environment.MEDIA_MOUNTED.equals(volumeState);
					tv.append(path+"  "+description+"  isSDCard="+isSDCard+"  isMounted="+isMounted+"\n");
				}
				
				
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	
	/*
	 * 调用了StorageManager的隐藏方法getVolumeList和隐藏类StorageVolume，需要使用Android.mk编译生成apk文件
	 * Android.mk 文件格式
	 *  LOCAL_PATH := $(call my-dir) 
	 *  include $(CLEAR_VARS)
	 * LOCAL_MODULE_TAGS := optional 
	 * LOCAL_SRC_FILES := $(call all-java-files-under,src) 
	 * LOCAL_PACKAGE_NAME := android_demo
	 * include $(BUILD_PACKAGE)
	 */
	/*
	 * public void getExternalStorage(){
	 * 
	 * sm=(StorageManager) getSystemService(Context.STORAGE_SERVICE);
	 * StorageVolume[] volumes=sm.getVolumeList(); 
	 * for(StorageVolume volume:volumes){
	 *  String path=volume.getPath(); 
	 * 	String description=volume.getDescrition(); 
	 * 	String isMounted=isMounted(volume.getPath()); 
	 * 	String isExternal=volume.isRemovable(); tv.append(path+"\n");
	 * 	tv.append(description+"\n"); }
	 * 
	 * }
	 */
	/*
	 * protected boolean isMounted(String path){ 
	 * if(TextUtils.isEmpty(path))
	 * 		return false; 
	 * String state=null; 
	 * state=sm.getVolumeState(path); 
	 * return Environment.MEDIA_MOUNTED.equals(state); 
	 * }
	 */

}
