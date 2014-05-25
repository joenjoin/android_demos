package org.youdian.android_demos.http;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.X509HostnameVerifier;

public class HttpsRequest {
	
	private HttpsURLConnection getHttpsConnection(){
		
		return null;
		
	}
	class   HttpsHostNameVerifier implements X509HostnameVerifier{

		@Override
		public boolean verify(String host, SSLSession session) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void verify(String host, SSLSocket ssl) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void verify(String host, X509Certificate cert)
				throws SSLException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void verify(String host, String[] cns, String[] subjectAlts)
				throws SSLException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class HttpsTrustManager implements X509TrustManager{

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
