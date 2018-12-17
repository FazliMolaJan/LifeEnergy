package cn.dennishucd.activity;

import org.json.JSONArray;
import org.json.JSONException;

import cn.dennishucd.R;
import cn.dennishucd.cache.BaseActivity;
import cn.dennishucd.result.FriendsResult;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.Utils;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * 添加好友类
 * 
 * @author lqb
 * 
 */
public class FriendFindActivity extends BaseActivity {

	private Button mBack;
	private Button mConfirm;
	private ImageView mAddview;
	private EditText mUidText;
	private ImageView mAvatar;
	private TextView mName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_find);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.friend_find_return);
		mConfirm = (Button) findViewById(R.id.friend_find_confirm);
		mAddview = (ImageView) findViewById(R.id.friend_add);
		mUidText = (EditText) findViewById(R.id.friend_number);		
		mAvatar = (ImageView) findViewById(R.id.friend_avatar);
		mName= (TextView) findViewById(R.id.friend_name);
		
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mConfirm.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				new Thread(){
					public void run()
					{
						String mUid = mUidText.getText().toString();
						String json=LoadUtil.HttpGetText("PersonNewAddServlet?tag="+"\"search\""+"&&uid=\""+mUid+"\"");
						try {
							JSONArray array=new JSONArray(json);
							runOnUiThread(new Runnable() {		
								@Override
								public void run() {
									// TODO Auto-generated method stub
									mName.setText("a");
									mAvatar.setImageBitmap(mKXApplication.getAvatar("lqb000.jpg"));
									} 
								});
							} catch (JSONException e) {
								//查找失败
								runOnUiThread(new Runnable() {		
									@Override
									public void run() {
										// TODO Auto-generated method stub	
										Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_SHORT).show();			
									} 
								});
						}};
				}.start();
		}});
		
		mAddview.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				new Thread(){
					public void run()
					{
						String mUid = mUidText.getText().toString();
						String json=LoadUtil.HttpGetText("PersonNewAddServlet?tag="+"\"add\""+"&&uid=\""+Utils.UID+"\""+"&&uidx="+"\""+mUid+"\"");
						if(json.toString().trim().equals("Succeed")){
							runOnUiThread(new Runnable() {		
								@Override
								public void run() {
									// TODO Auto-generated method stub	
									Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();			
								} 
							});
						}
						else{
							runOnUiThread(new Runnable() {		
								@Override
								public void run() {
									// TODO Auto-generated method stub	
									Toast.makeText(getApplicationContext(), "该用户已是您的好友", Toast.LENGTH_SHORT).show();			
								} 
							});
						}
					}
				}.start();
		}});
		
	}

	private void init() {
		
		
	}
}
