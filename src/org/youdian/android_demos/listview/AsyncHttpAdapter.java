package org.youdian.android_demos.listview;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import org.youdian.android_demos.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AsyncHttpAdapter extends BaseAdapter {
	
	private static class Utils {
		private static String baseUrl="http://sapp-pics.stor.sinaapp.com/";
		private static String image;
		public static ArrayList<String> URLS=new ArrayList<String>();
		static{
			for(int i=1;i<=60;i++){
				image=baseUrl+i+".jpg";
				URLS.add(image);
			}
		}

	}

	
	ArrayList<HashMap<String,String>> mResources=new ArrayList<>();
	Context mContext;
	public AsyncHttpAdapter(Context context){
		mContext=context;
	}
	public void setResources(ArrayList<HashMap<String,String>> mResources){
		
		this.mResources=mResources;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Utils.URLS.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if(convertView==null){
			convertView=(View)LayoutInflater.from(mContext).inflate(R.layout.item_listview_async, parent, false);
			viewHolder=new ViewHolder();
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.title=(TextView)convertView.findViewById(R.id.title);
		viewHolder.content=(ImageView)convertView.findViewById(R.id.content);
		String url=Utils.URLS.get(position);
		viewHolder.title.setText("image "+(position+1));
		viewHolder.content.setTag(url);
		//viewHolder.content.setImageResource(R.drawable.ic_launcher);
		setupImage(url,viewHolder.content);
		return convertView;
	}
	
	private void setupImage(String url, ImageView content) {
		// TODO Auto-generated method stub
		ImageLoaderTask task=new ImageLoaderTask();
		task.setEntity(content);
		task.execute(url);
	}
	class ImageLoaderTask extends AsyncTask<String,Void,Bitmap>{
		String url;
		ImageView image;
		String tag;
		protected void setEntity(ImageView content){
			image=content;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			url=params[0];
			if(image==null||!image.getTag().equals(url))
				return null;
			return loadImage(url);
		}

		private  Bitmap loadImage(String url) {
			Bitmap bitmap=null;
			bitmap= tryLoadImageFromFile(url);
			if(bitmap!=null)return bitmap;
			URL u=null;
			try {
				u = new URL(url);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				HttpURLConnection conn=(HttpURLConnection) u.openConnection();
				conn.setConnectTimeout(10*1000);
				conn.setRequestMethod("GET");
				conn.connect();
				InputStream is=conn.getInputStream();
				if(is==null)System.out.println("is =null");
				System.out.println(url);
				if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
					return trySaveImageToFile(url, is);
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		private  Bitmap tryLoadImageFromFile(String url){
			File dir=mContext.getExternalCacheDir();
			try {
				String filename = generateFileName(url);
				String path=dir.getAbsolutePath()+File.separator+filename;
				//System.out.println("path in load Image="+path);
				File f=new File(path);
				if(f.exists()){
					
					Bitmap bitmap=BitmapFactory.decodeFile(path);
					if(bitmap==null)System.out.println("bitmap is null");
					return bitmap;
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
			
		}

		private String generateFileName(String url)
				throws NoSuchAlgorithmException {
			char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; 
			MessageDigest digest=MessageDigest.getInstance("MD5");
			byte[] bytes=digest.digest(url.getBytes());
			int j = bytes.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = bytes[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);

		}
		private Bitmap trySaveImageToFile(String url,InputStream is){
			String path=null;
			OutputStream os=null;
			try {
				String fileName=generateFileName(url);
				File dir=mContext.getExternalCacheDir();
				path=dir.getAbsolutePath()+File.separator+fileName;
				//System.out.println("path in save Image"+path);
				os=new BufferedOutputStream(new FileOutputStream(path), 1024*4*2);
				byte[] buffer=new byte[1024*8];
				BufferedInputStream bis=new BufferedInputStream(is,1024*8);
				int n=-1;
				while((n=bis.read(buffer))!=-1){
					os.write(buffer, 0, n);
				}
				os.close();
				//
				BitmapFactory.Options options=new BitmapFactory.Options();
				options.inSampleSize=5;
				Bitmap bitmap=BitmapFactory.decodeFile(path,options);
				if(bitmap==null)System.out.println("bitmap is null in trySaveImageToFile");
				return bitmap;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return null;
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==null)System.out.println("result is null in "+image.getTag().toString());
			if(image==null||!image.getTag().equals(url)||result==null){
				//image.setImageResource(R.drawable.ic_launcher);
				return;
			}
			image.setImageBitmap(result);
				
		}
		
	}
	class ViewHolder{
		TextView title;
		ImageView content;
	}

}
