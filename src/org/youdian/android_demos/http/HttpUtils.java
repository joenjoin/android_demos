package org.youdian.android_demos.http;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.net.CookieStore;

import org.apache.http.cookie.Cookie;

import android.annotation.SuppressLint;
import android.util.Log;

public class HttpUtils {
	
	private static final String TAG="HttpUtils";
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
	
	@SuppressLint("NewApi")
	public static boolean uploadFile(String url,File file){
		boolean result=false;
		OutputStream out=null;
		HttpURLConnection conn=null;
		InputStream in=null;
		initCookieStore();

		
		
		try {
			conn=(HttpURLConnection) new URL(url).openConnection();
			conn.setChunkedStreamingMode(0);
			conn.setDoOutput(true);
			out=new BufferedOutputStream(conn.getOutputStream());
			writeStream(out,file);
			if(conn.getResponseCode()==HttpStatus.SC_OK){
				in=conn.getInputStream();
				/*
				 * 有些wifi 网络会重定向请求到登录页，因此这里需要判断返回的数据是否是请求的数据，
				 * 可以在 getHeaderFields或者getInputStream()后判断
				 */
				if(!new URL(url).getHost().equals(conn.getURL().getHost())){
					in=null;
				}
			}
				
			
	
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
	public static InputStream get(String url){
		initCookieStore();
		HttpURLConnection conn=null;
		InputStream in=null;
		try {
			conn=(HttpURLConnection) new URL(url).openConnection();
			in=conn.getInputStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
	
	@SuppressLint("NewApi")
	private static void initCookieStore() {
		CookieStore store = new MyCookieStore();
		CookiePolicy policy = new LocalCookiePolicy();
		CookieManager handler = new CookieManager(store, policy);
		CookieHandler.setDefault(handler);
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
	
	private static List<Cookie> cookies=new ArrayList<Cookie>();
	@SuppressLint("NewApi")
	private static class LocalCookiePolicy implements CookiePolicy{

		@Override
		public boolean shouldAccept(URI uri, HttpCookie cookie) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	@SuppressLint("NewApi")
	private static class MyCookieStore implements CookieStore {
		  private Map<URI, List<HttpCookie>> map = new HashMap<URI, List<HttpCookie>>();

		  public void add(URI uri, HttpCookie cookie) {
		    List<HttpCookie> cookies = map.get(uri);
		    if (cookies == null) {
		      cookies = new ArrayList<HttpCookie>();
		      map.put(uri, cookies);
		    }
		    Log.d(TAG, "name="+cookie.getName()+",value="+cookie.getValue());
		    cookies.add(cookie);
		  }

		  public List<HttpCookie> get(URI uri) {
		    List<HttpCookie> cookies = map.get(uri);
		    if (cookies == null) {
		      cookies = new ArrayList<HttpCookie>();
		      map.put(uri, cookies);
		    }
		    return cookies;
		  }

		  public List<HttpCookie> getCookies() {
		    Collection<List<HttpCookie>> values = map.values();
		    List<HttpCookie> result = new ArrayList<HttpCookie>();
		    for (List<HttpCookie> value : values) {
		      result.addAll(value);
		    }
		    return result;
		  }

		  public List<URI> getURIs() {
		    Set<URI> keys = map.keySet();
		    return new ArrayList<URI>(keys);

		  }

		  public boolean remove(URI uri, HttpCookie cookie) {
		    List<HttpCookie> cookies = map.get(uri);
		    if (cookies == null) {
		      return false;
		    }
		    return cookies.remove(cookie);
		  }

		  public boolean removeAll() {
		    map.clear();
		    return true;
		  }
		}

}
