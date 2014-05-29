package org.youdian.android_demos.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpUtils {
	/*
	 * 把普通中文字符串转换成 application/x-www-form-urlencoded 字符串
	 */
	public static String encodeUrl(String url){
		String newUrl=null;
		if(url==null)
			return url;
		try {
			newUrl=URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newUrl;
	}

}
