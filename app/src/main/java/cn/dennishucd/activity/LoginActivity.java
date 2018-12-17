package cn.dennishucd.activity;


import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.dennishucd.ImageEngine;
import cn.dennishucd.R;
import cn.dennishucd.uibase.CircleImageView;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.Utils;

/**
 * 登录界面
 * 
 * @author lqb
 * 
 */
public class LoginActivity extends Activity {
	/**
	 * 登录按钮
	 */
	private Context mContext;
	private Button mLogin;
	private EditText username;
	private EditText password;
	private CircleImageView mAvatar;
	private TextView register;
	private TextView password_foggotten;
	private String pwd =null;
	private String UID= null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		setContentView(R.layout.login_activity);
		mContext = this;
		findViewById();
		setListener();
	}


	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		mLogin = (Button) findViewById(R.id.login_activity_login);
		username =(EditText) findViewById(R.id.login_uid);
		password =(EditText) findViewById(R.id.login_pwd);
		mAvatar = (CircleImageView) findViewById(R.id.login_avatar);
		register = (TextView) findViewById(R.id.register_activity_register);
		password_foggotten = (TextView) findViewById(R.id.password_foggotten);
		mAvatar.setImageDrawable(getResources().getDrawable(R.drawable.head));
	} 


	/**
	 * UI事件监听
	 */
	private void setListener() {
		
		// 获取头像监听
		password.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
		    @Override  
		    public void onFocusChange(View v, boolean hasFocus) {  
		        if(hasFocus) {
		        	new Thread(){
						public void run()
						{
							UID= username.getText().toString().trim();
							final String json=LoadUtil.HttpGetText("LoginServlet?tag=\"avatar\"&&uid="+"\""+UID+"\"");
							if(!json.toString().trim().equals("Wrong")){
								runOnUiThread(new Runnable() {		
									@Override
									public void run() {
										// TODO Auto-generated method stub	
										InputStream inStream=LoadUtil.HttpGetPhoto(json,"avatar");
										if(inStream !=null){
											Bitmap bitmap =BitmapFactory.decodeStream(inStream);
									    	mAvatar.setImageBitmap(bitmap);
										}
								  } 			
							});
					  }}
					}.start();
				} else {
					// 此处为失去焦点时的处理内容
				}
		    }
		});
		// 登录按钮监听
		mLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到主界面
			showRoundProcessDialog(mContext);
			mLogin.setBackgroundColor(Color.parseColor("#ffcb9090"));
			UID= username.getText().toString().trim();
			pwd= password.getText().toString().trim();
			new Thread(){
				public void run(){		
					final String json=LoadUtil.HttpGetText("LoginServlet?tag=\"confirm\"&&uid="+"\""+UID+"\""+"&password="+"\""+pwd+"\"");
					if(!json.toString().trim().equals("Wrong")){
						runOnUiThread(new Runnable() {		
							@Override
							public void run() {
								// TODO Auto-generated method stub	
								JSONArray array;
								try {
									array = new JSONArray(json);
									Utils.Avatar=(array.getJSONObject(0).getString("avatar"));
									Utils.Sig=(array.getJSONObject(0).getString("sig"));
									Utils.Name=(array.getJSONObject(0).getString("name"));
									Utils.UID = UID;		
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}					
							     startActivity(new Intent(LoginActivity.this,MainActivity.class));
								 finish();
							}
						});
				    }
				   else {
							runOnUiThread(new Runnable() {		
							@Override
							public void run() {
							// TODO Auto-generated method stub	
								mLogin.setBackgroundColor(Color.parseColor("#ddcb9090"));
								Toast.makeText(getApplicationContext(), "用户不存在或者密码错误", Toast.LENGTH_SHORT).show();		
							} 
						});
				   }
			  }	
			}.start();
		  }
		});
		
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
				finish();
			}
		});
		
		password_foggotten.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LoginActivity.this,VideoRecordActivity.class));
				finish();
			}
		});
	}

	public void onBackPressed() {
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
	

    public void showRoundProcessDialog(Context mContext){
    	Dialog mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        // 注意此处要放在show之后 否则会报异常
        mDialog.setContentView(R.layout.loading_process_dialog_anim);
       // mDialog.setCancelable(false);  //false设置点击其他地方不能取消进度条
    }
    
}
