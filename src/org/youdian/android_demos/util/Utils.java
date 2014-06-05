package org.youdian.android_demos.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.TimingLogger;

public class Utils {
	
/*
 * 获取MD5
 */
	public static String getMd5(InputStream is) throws NoSuchAlgorithmException, IOException{
		byte[] bytes=new byte[1024*8];
		char hexDigits[] = {       // 用来将字节转换成 16 进制表示的字符
			     '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'}; 
		int byteCount;
		MessageDigest digester=MessageDigest.getInstance("MD5");
		while((byteCount=is.read(bytes))>0){
			digester.update(bytes, 0, byteCount);
		}
		byte[] digest =digester.digest();
		//System.out.println("digest:"+digest);
		 char str[] = new char[16 * 2]; 
		   int k = 0;                                // 表示转换结果中对应的字符位置
		    for (int i = 0; i < 16; i++) {          // 从第一个字节开始，对 MD5 的每一个字节
		                                                 // 转换成 16 进制字符的转换
		     byte byte0 = digest[i];                 // 取第 i 个字节
		     str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换, 
		                                                             // >>> 为逻辑右移，将符号位一起右移
		     str[k++] = hexDigits[byte0 & 0xf];            // 取字节中低 4 位的数字转换
		    } 
		//System.out.println("digest str:"+new String(str));
		return new String(str);
		
	}
	/*
	 * 用来记录程序运行时间
	 */
	public static void  log() throws InterruptedException{
		TimingLogger loger=new TimingLogger("utils","test");
		Thread.sleep(1000);
		loger.addSplit("work a");
		Thread.sleep(500);
		loger.addSplit("work b");
		loger.dumpToLog();
		System.out.println("finished");
	}


}
