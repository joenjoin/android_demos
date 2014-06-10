package org.youdian.android_demos.http;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

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
	
	public static boolean uploadFile(String url,File file){
		boolean result=false;
		OutputStream out=null;
		HttpURLConnection conn=null;
		InputStream in=null;
		try {
			conn=(HttpURLConnection) new URL(url).openConnection();
			conn.setChunkedStreamingMode(0);
			conn.setDoOutput(true);
			out=new BufferedOutputStream(conn.getOutputStream());
			writeStream(out,file);
			if(conn.getResponseCode()==HttpStatus.SC_OK)
				in=conn.getInputStream();
			
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			conn.disconnect();
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	private static void writeStream(OutputStream out,File file){
		InputStream in;
		try {
			in = new FileInputStream(file);
			byte[] buffer=new byte[1024*8];
			int n=-1;
			while((n=in.read(buffer))!=-1){
				out.write(buffer, 0, n);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
