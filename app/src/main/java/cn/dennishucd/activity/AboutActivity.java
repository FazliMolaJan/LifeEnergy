package cn.dennishucd.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.DatePicker.OnDateChangedListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cn.dennishucd.R;
import cn.dennishucd.cache.BaseActivity;
import cn.dennishucd.result.FriendInfoResult;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.TextUtil;
import cn.dennishucd.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * 资料关于类
 * 
 * @author lqb
 * 
 */
public class AboutActivity extends BaseActivity {

	private Button mBack;
	private TextView mTitle;
	private Button mSubmit;
	private ImageButton mAvatar;
	private Button mAvatarChange;
	private TextView mName;
	private TextView mSignature;
	
	private TextView mGender;
	private TextView mDate;
	private TextView mConstellation;
	private TextView mHometown;
	private TextView mAddress;
	private TextView mTelephone;
	private TextView mEducation;
	private TextView mExperience;
	
	private ImageView mDateIcon;
	private ImageView mAddressIcon;
	private ImageView mTelephoneIcon;
	private ImageView mSexIcon;
	private ImageView mHometownIcon;
	private ImageView mEducationIcon;
	private ImageView mExperienceIcon;

	private String mUid;// 当前查看的用户Id
	private FriendInfoResult mResult;// 当前查看的用户的资料数据

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.about_back);
		mTitle = (TextView) findViewById(R.id.about_title);
		mSubmit = (Button) findViewById(R.id.about_submit);
		mAvatar = (ImageButton) findViewById(R.id.about_avatar);
		mAvatarChange = (Button) findViewById(R.id.about_avatar_change);
		mName = (TextView) findViewById(R.id.about_name);
		mSignature = (TextView) findViewById(R.id.about_signature);

		//显示具体信息的文本框
		mGender = (TextView) findViewById(R.id.about_gender);
		mDate = (TextView) findViewById(R.id.about_date);
		mConstellation = (TextView) findViewById(R.id.about_constellation);
		mHometown = (TextView) findViewById(R.id.about_hometown);
		mAddress = (TextView) findViewById(R.id.about_address);
		mTelephone = (TextView) findViewById(R.id.about_telephone);
		mEducation = (TextView) findViewById(R.id.about_education);
		mExperience = (TextView) findViewById(R.id.about_experience);
		
		//触发信息文本可编辑的图标
		mSexIcon = (ImageView) findViewById(R.id.about_sex_icon);
		mDateIcon = (ImageView) findViewById(R.id.about_date_icon);
		mHometownIcon = (ImageView) findViewById(R.id.about_hometown_icon);
		mAddressIcon = (ImageView) findViewById(R.id.about_address_icon);
		mTelephoneIcon = (ImageView) findViewById(R.id.about_telephone_icon);
		mEducationIcon = (ImageView) findViewById(R.id.about_education_icon);
		mExperienceIcon = (ImageView) findViewById(R.id.about_experience_icon);
		
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();// 关闭当前界面
			}
		});
		mAvatarChange.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 暂时不做任何操作
			}
		});
		
		mSubmit.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				int gender = mResult.getGender(); 
				String date = mResult.getDate();
				String constellation = mResult.getConstellation();
				String hometown = mResult.getHometown();
				String address = mResult.getAddress();
				String telephone = mResult.getTelephone();
				String education = mResult.getEducation();
				String experience = mResult.getExperience();
				
				try {
					final String text = "PersonInfoUpdateServlet";
					
					/*final List<NameValuePair> data = new ArrayList<NameValuePair>(); 
					data.add(new BasicNameValuePair("uid", URLEncoder.encode(Utils.UID, "utf-8")));
					data.add(new BasicNameValuePair("gender", URLEncoder.encode(gender, "utf-8")));
					data.add(new BasicNameValuePair("date", URLEncoder.encode(date, "utf-8")));
					data.add(new BasicNameValuePair("constellation", URLEncoder.encode(constellation, "utf-8")));
					data.add(new BasicNameValuePair("hometown", URLEncoder.encode(hometown, "utf-8")));
					data.add(new BasicNameValuePair("address", URLEncoder.encode(address, "utf-8")));
					data.add(new BasicNameValuePair("telephone", URLEncoder.encode(telephone, "utf-8")));
					data.add(new BasicNameValuePair("education",URLEncoder.encode(education, "utf-8")));
					data.add(new BasicNameValuePair("experience", URLEncoder.encode(experience, "utf-8")));*/
					
					final List<NameValuePair> data = new ArrayList<NameValuePair>(); 
					data.add(new BasicNameValuePair("uid", Utils.UID));
					data.add(new BasicNameValuePair("gender", gender+""));
					data.add(new BasicNameValuePair("date", date));
					data.add(new BasicNameValuePair("constellation", constellation));
					data.add(new BasicNameValuePair("hometown", hometown));
					data.add(new BasicNameValuePair("address", address));
					data.add(new BasicNameValuePair("telephone", telephone));
					data.add(new BasicNameValuePair("education",education));
					data.add(new BasicNameValuePair("experience", experience));
					
					new Thread(){
						public void run()
						{
							final String result = LoadUtil.HttpPostText(text,data);	
							
							if(result != null){	
								runOnUiThread(new Runnable() {		
									@Override
									public void run() {
										// TODO Auto-generated method stub	
										Toast.makeText(AboutActivity.this, "信息修改成功", Toast.LENGTH_SHORT).show();			
									} 
								});
							}
							else{
								//请求失败
								runOnUiThread(new Runnable() {		
									@Override
									public void run() {
										// TODO Auto-generated method stub	
										Toast.makeText(AboutActivity.this, "信息修改失败", Toast.LENGTH_SHORT).show();			
									} 
								});
								
							}
						};
					}.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		});
		
		
		  
		
		//触发信息文本可编辑的图标
		mSexIcon.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				//女：0          男：1
				
				final String[] sexArray = new String[] {  "女" , "男" };

				AlertDialog adRef = new AlertDialog.Builder(AboutActivity.this)
						.setSingleChoiceItems(sexArray, 1,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,int arg1) {
								//		Log.v("您选中了:", sexArray[arg1]);
										System.out.println(arg1);
										mGender.setText(sexArray[arg1]);
										mResult.setGender(arg1);
										arg0.dismiss();
									}
								}).create();
				adRef.setIcon(android.R.drawable.btn_star);
				adRef.setTitle("性别");
				adRef.show();
			}
		});
		
		mDateIcon.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog adRef1 = new AlertDialog.Builder(AboutActivity.this).create();	
				final DatePicker dp = new DatePicker(AboutActivity.this);
				adRef1.setView(dp);
				adRef1.setIcon(android.R.drawable.btn_star);
				adRef1.setTitle("出生日期");
				
				Calendar calendar=Calendar.getInstance();
		        int year=calendar.get(Calendar.YEAR);
		        int monthOfYear=calendar.get(Calendar.MONTH);
		        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
		        //未触发onDateChanged时 （即：初始日期即为需要选中的日期）
		        String date1 = year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日";
            	mDate.setText(date1);
            	mResult.setDate(date1);
		        
		        dp.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener(){

		            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		            	String date = year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日";
		            	mDate.setText(date);
		            	mResult.setDate(date);
		            }
		            
		        });
				
				adRef1.setButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					//	mDate.setText( mDate.getText().toString().trim() );
						int month = dp.getMonth()+1;
						int day = dp.getDayOfMonth();
						
						Log.v("-----日期-----", month+"  "+day);
						
						//设置星座
						String ctl = "";
						
						if( (month==1 && day>=20) || (month==2 && day<=18)  )
							ctl = "水瓶座";
						else if( (month==2 && day>=19) || (month==3 && day<=20)  )
							ctl = "双鱼座";
						else if( (month==3 && day>=21) || (month==4 && day<=19)  )
							ctl = "白羊座";
						else if( (month==4 && day>=20) || (month==5 && day<=20)  )
							ctl = "金牛座";
						else if( (month==5 && day>=21) || (month==6 && day<=21)  )
							ctl = "双子座";
						else if( (month==6 && day>=22) || (month==7 && day<=22)  )
							ctl = "巨蟹座";
						else if( (month==7 && day>=23) || (month==8 && day<=22)  )
							ctl = "狮子座";
						else if( (month==8 && day>=23) || (month==9 && day<=22)  )
							ctl = "处女座";
						else if( (month==9 && day>=23) || (month==10 && day<=23)  )
							ctl = "天秤座";
						else if( (month==10 && day>=24) || (month==11 && day<=22)  )
							ctl = "天蝎座";
						else if( (month==11 && day>=23) || (month==12 && day<=21)  )
							ctl = "射手座";
						else if( (month==12 && day>=22) || (month==1 && day<=19)  )
							ctl = "魔蝎座";
						
						mConstellation.setText(ctl);
						mResult.setConstellation(ctl);
					}
				});
				
				adRef1.show();			
			}
		});
		
		mHometownIcon.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog adRef = new AlertDialog.Builder(AboutActivity.this).create();				
				final EditText et = new EditText(AboutActivity.this);
				adRef.setView(et);
				adRef.setIcon(android.R.drawable.btn_star);
				adRef.setTitle("家乡");
				adRef.setButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String data = et.getText().toString().trim();
						mResult.setHometown(data);
						mHometown.setText(data);
					}
				});
				adRef.setButton2("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				adRef.show();
			}
		});
		
		mAddressIcon.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog adRef = new AlertDialog.Builder(AboutActivity.this).create();				
				final EditText et = new EditText(AboutActivity.this);
				adRef.setView(et);
				adRef.setIcon(android.R.drawable.btn_star);
				adRef.setTitle("现居住地");
				adRef.setButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String address = et.getText().toString().trim();
						mResult.setAddress(address);
						mAddress.setText(address);
					}
				});
				adRef.setButton2("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				adRef.show();
			}
		});
		
		mTelephoneIcon.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog adRef = new AlertDialog.Builder(AboutActivity.this).create();				
				final EditText et = new EditText(AboutActivity.this);
				adRef.setView(et);
				adRef.setIcon(android.R.drawable.btn_star);
				adRef.setTitle("手机");
				adRef.setButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String telephone = et.getText().toString().trim();
						mResult.setTelephone(telephone); 
						mTelephone.setText(telephone);
					}
				});
				adRef.setButton2("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				adRef.show();
			}
		});
		
		mEducationIcon.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog adRef = new AlertDialog.Builder(AboutActivity.this).create();				
				final EditText et = new EditText(AboutActivity.this);
				adRef.setView(et);
				adRef.setIcon(android.R.drawable.btn_star);
				adRef.setTitle("教育背景");
				adRef.setButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String education = et.getText().toString().trim();
						mResult.setEducation(education);
						mEducation.setText(education);
					}
				});
				adRef.setButton2("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				adRef.show();
			}
		});
		
		mExperienceIcon.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog adRef = new AlertDialog.Builder(AboutActivity.this).create();				
				final EditText et = new EditText(AboutActivity.this);
				adRef.setView(et); 
				adRef.setIcon(android.R.drawable.btn_star);
				adRef.setTitle("工作经历");
				adRef.setButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String experience = et.getText().toString().trim();
						mResult.setExperience(experience);
						mExperience.setText(experience);
					}
				});
				adRef.setButton2("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				adRef.show();
			}
		});
	}

	private void init() {
		
		mUid = getIntent().getStringExtra("uid");// 接收传递过来的用户ID
		mResult = getIntent().getParcelableExtra("result");// 接收传递过来的用资料
		// 当Id不存在时为当前登录用户,否则则是其他用户,根据用户的不同,显示不同界面效果
		if (mUid == null) {
			mBack.setText("我的首页");
			mTitle.setText("我的资料");
			mSubmit.setVisibility(View.VISIBLE);
			mSubmit.setText("提交");
			mAvatarChange.setVisibility(View.VISIBLE);
			mDateIcon.setVisibility(View.VISIBLE);
			mAddressIcon.setVisibility(View.VISIBLE);
			mTelephoneIcon.setVisibility(View.VISIBLE);
		} else {
			mBack.setText(mResult.getName());
			mTitle.setText(mResult.getName() + "的资料");
			mSubmit.setVisibility(View.GONE);
			mAvatarChange.setVisibility(View.INVISIBLE);
			mDateIcon.setVisibility(View.GONE);
			mAddressIcon.setVisibility(View.GONE);
			mTelephoneIcon.setVisibility(View.GONE);
		}
		// 填充界面数据
		mAvatar.setImageBitmap(mKXApplication.getDefaultAvatar());
		mKXApplication.imageLoader.displayImage(LoadUtil.URL+"Avatar/"+mResult.getAvatar(),
				mAvatar, mKXApplication.options);
		mName.setText(mResult.getName());
		mSignature.setText(new TextUtil(mKXApplication).replace(mResult
				.getSignature()));
		mGender.setText(Utils.getGender(mResult.getGender()));
		mDate.setText(mResult.getDate());
		mConstellation.setText(mResult.getConstellation());
	}
}
