package cn.dennishucd.activity;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.dennishucd.R;
import cn.dennishucd.cache.BaseActivity;
import cn.dennishucd.result.HomeFriendsResult;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.Utils;

/**
 * 登录界面
 * 
 * @author lqb
 * 
 */
public class RegisterActivity extends BaseActivity {
	/**
	 * 登录按钮
	 */
	private Button mRegister;
	private EditText username;
	private EditText password;
	private EditText passwordr;
	private String Uid;
	private String Pwd;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		findViewById();
		setListener();
	}

	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		mRegister = (Button) findViewById(R.id.register_activity_login);
		username =(EditText) findViewById(R.id.register_uid);
		password =(EditText) findViewById(R.id.register_pwd);
		passwordr =(EditText) findViewById(R.id.register_pwdr);
		
	}


	/**
	 * UI事件监听
	 */
	private void setListener() {
		// 登录按钮监听
		mRegister.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到主界面
				Uid=username.getText().toString().trim();
				Pwd=password.getText().toString().trim();
				if(Pwd.equals(passwordr.getText().toString().trim())){
					String json=LoadUtil.HttpGetText("RegisterServlet?uid="+"\""+Uid+"\""+"&password="+"\""+Pwd+"\"");
					if(json.toString().trim().equals("Register Successfully")){	
						startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
						finish();
					}
					else{
						Toast.makeText(getApplicationContext(), "ID  Exists",
								Toast.LENGTH_SHORT).show();
					}
				}
				else {
					Toast.makeText(getApplicationContext(), "两次输入的密码不匹配",
						     Toast.LENGTH_SHORT).show();
				}
		}});
	}

	public void onBackPressed() {
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
}
