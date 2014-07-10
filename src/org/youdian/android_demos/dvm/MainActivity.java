package org.youdian.android_demos.dvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
public class MainActivity extends Activity {
	public static final String DALVIK_VM_HEAPSIZE="dalvik.vm.heapsize";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Toast.makeText(this, String.valueOf(statifGetLargeMemoryClass()), Toast.LENGTH_LONG).show();
	}
	
	static public int statifGetLargeMemoryClass(){
		int heapSize=0;
		try {
			Class<?> SYSTEM_PROPERTIES=Class.forName("android.os.SystemProperties");
			Method GET=SYSTEM_PROPERTIES.getMethod("get",String.class,String.class);
			String vmHeadSize=(String) GET.invoke(SYSTEM_PROPERTIES.newInstance(), 
					DALVIK_VM_HEAPSIZE,"16m");
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
