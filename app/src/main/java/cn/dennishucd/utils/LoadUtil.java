package cn.dennishucd.utils;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;
import cn.dennishucd.R;
import cn.dennishucd.activity.LoginActivity;

/**
 * 上传/下载工具类
 * 
 * @author lqb
 * 
 */
public class LoadUtil {
	public static String URL ="http://192.168.0.100:9081/ServerForVideoSharing/";
	//public static String URL ="http://121.40.135.152:90/";
	public static String HttpGetText(String path){
		String realpath =URL+path;
		Log.v("realpath", realpath);
		HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) new URL(realpath).openConnection();
				
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				InputStream inputstream =null;
				if(conn.getResponseCode() == 200){
					inputstream = conn.getInputStream();	
				}
		        byte[] data = StreamTool.read(inputstream);
		    	String json = new String(data);
		    	return json;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return null;
	}
	
	public static String HttpPostText(String path,List<NameValuePair> param){
		
		String realpath =URL+path;       
		Log.v("realpath", realpath);
		HttpPost httpRequest = new HttpPost(realpath);  
		List<NameValuePair> params = param;         
		HttpEntity httpentity;
		try {
			httpentity = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			httpRequest.setEntity(httpentity);                     
				HttpClient httpclient = new DefaultHttpClient();                
				HttpResponse httpResponse = httpclient.execute(httpRequest);                         
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)              
				{                         
					String strResult = EntityUtils.toString(httpResponse.getEntity());
					return strResult;
				}              
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                
		return null;     
	}
	
	public static InputStream HttpGetPhoto(String path,String tag){
		String realpath=null;
		if(tag.equals("avatar")){
			realpath =URL+"Avatar/"+path;
		}
		else{
			realpath =URL+"User/"+path;
		}
		Log.v("realpath", realpath);
		HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) new URL(realpath).openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				InputStream inputstream =null;
				if(conn.getResponseCode() == 200){
					inputstream = conn.getInputStream();	
				}
		    	return inputstream;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	/**
	 * 上传视频文件
	 */
	public static String sendVideoFile(String fileName,String time) throws Exception {
		
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		URL url = new URL(URL+"UploadFileServlet");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		/* 允许Input、Output，不使用Cache */
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		
		/* 设置DataOutputStream */
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; "
				+"name=\"User/"+Utils.UID+"/Video/\";"
				+"filename=\"" + time+".mp4" + "\"" + end);
		ds.writeBytes(end);
		/* 取得文件的FileInputStream */
		FileInputStream fStream = new FileInputStream(fileName);
		/* 设置每次写入1024bytes */
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		/* 从文件读取数据至缓冲区 */
		while ((length = fStream.read(buffer)) != -1) {
			/* 将资料写入DataOutputStream中 */
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(end);
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

		/* close streams */
		fStream.close();
		ds.flush();

		/* 取得Response内容 */
		InputStream is = con.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		/* 关闭DataOutputStream */
		ds.close();
		return b.toString();
	}

	/**
	 * 上传缩略图图片文件
	 */
	public static String sendTblFile(String fileName,String time) throws Exception {
		
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		URL url = new URL(URL+"UploadFileServlet");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; "
				+"name=\"User/"+Utils.UID+"/Status/\";"
				+"filename=\"" + time+".jpg" + "\"" + end);
		ds.writeBytes(end);
		FileInputStream fStream = new FileInputStream(fileName);
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		while ((length = fStream.read(buffer)) != -1) {
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(end);
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
		fStream.close();
		ds.flush();
		InputStream is = con.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		ds.close();
		return b.toString();
	}
	
	/**
	 * 上传图片文件
	 */
	public static String sendPhotoFile(String fileName,String time) throws Exception {
		
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		URL url = new URL(URL+"UploadFileServlet");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; "
				+"name=\"User/"+Utils.UID+"/Photo/\";"
				+"filename=\"" + time+".jpg" + "\"" + end);
		ds.writeBytes(end);
		FileInputStream fStream = new FileInputStream(fileName);
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		while ((length = fStream.read(buffer)) != -1) {
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(end);
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
		fStream.close();
		ds.flush();
		InputStream is = con.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		ds.close();
		return b.toString();
	}
	
	/**
	 * 上传头像文件
	 */
	public static String sendAvatarFile(String fileName) throws Exception {
		
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		URL url = new URL(URL+"UploadFileServlet");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; "
				+"name=\"Avatar/\";"
				+"filename=\"" + Utils.UID+".jpg" + "\"" + end);
		ds.writeBytes(end);
		FileInputStream fStream = new FileInputStream(fileName);
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		while ((length = fStream.read(buffer)) != -1) {
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(end);
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
		fStream.close();
		ds.flush();
		InputStream is = con.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		ds.close();
		return b.toString();
	}
	
}
