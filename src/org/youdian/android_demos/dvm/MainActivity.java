package org.youdian.android_demos.dvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	public static final String DALVIK_VM_LARGE_HEAPSIZE="dalvik.vm.heapsize";
	public static final String DALVIK_VM_HEAPSIZE="dalvik.vm.heapgrowthlimit";
	private static final String TAG="HEAPSIZE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		int heapSize=getMemoryClass();
		int largeHeapSize=getLargeMemoryClass();
		Log.d(TAG, "heapSize="+heapSize);
		Log.d(TAG, "largeHeapSize="+largeHeapSize);
	}
	/*
	 * 获取系统分配给每个dvm的内存
	 * 
	 * 注 ：在ActivityManager类中，方法getMemoryClass()实现了该功能。可以通过反射技术调用
	 */
	static public int getMemoryClass(){
		int heapSize=0;
		try {
			Class<?> SYSTEM_PROPERTIES=Class.forName("android.os.SystemProperties");
			Method GET=SYSTEM_PROPERTIES.getMethod("get",String.class,String.class);
			String vmHeadSize=(String) GET.invoke(SYSTEM_PROPERTIES.newInstance(), 
					DALVIK_VM_HEAPSIZE,"");
			heapSize=Integer.parseInt(vmHeadSize.substring(0,vmHeadSize.length()-1));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Method GET=
		//String vmHeapSize=SystemP
		return heapSize;
	}
	/*
	 *android:largeHeap="true"时，应用此方法
	 * return the approximate per-application memory class of the current decice when 
	 * an application is running with a large heap.this is the space available for memory-
	 * intensive applications; most applications should not need this amount of memory.
	 * 
	 * 注 ：在ActivityManager类中，方法getLargeMemoryClass()实现了该功能。可以通过反射技术调用
	 */
	static public int getLargeMemoryClass(){
		int heapSize=0;
		try {
			Class<?> SYSTEM_PROPERTIES=Class.forName("android.os.SystemProperties");
			Method GET=SYSTEM_PROPERTIES.getMethod("get",String.class,String.class);
			String vmHeadSize=(String) GET.invoke(SYSTEM_PROPERTIES.newInstance(), 
					DALVIK_VM_LARGE_HEAPSIZE,"16m");
			heapSize=Integer.parseInt(vmHeadSize.substring(0,vmHeadSize.length()-1));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Method GET=
		//String vmHeapSize=SystemP
		return heapSize;
	}

}
