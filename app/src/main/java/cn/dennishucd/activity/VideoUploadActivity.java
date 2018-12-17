package cn.dennishucd.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cn.dennishucd.FFmpeg4AndroidActivity;
import cn.dennishucd.R;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.TimeUtil;
import cn.dennishucd.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class VideoUploadActivity extends Activity{

	private MediaPlayer mPlayer;    
	private EditText mContent;
	private Button mSubmit;
	private Button mCancel;
	private ImageView mImage;
	
	private String[] mTagItems = new String[] { "女神", "明星","风景",
			"搞笑", "旅游","美食" };
	private int mTagPosition;
	private Button mDisplayBottomTag;
	private Button mDisplayBottomAt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_upload_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mCancel = (Button) findViewById(R.id.video_upload_cancel);
		mSubmit = (Button) findViewById(R.id.video_upload_submit);
		mContent = (EditText) findViewById(R.id.video_upload_content);
		mImage = (ImageView) findViewById(R.id.video_upload_tbl);
		mDisplayBottomTag = (Button) findViewById(R.id.video_display_bottom_tag);
		mDisplayBottomAt = (Button) findViewById(R.id.video_display_bottom_at);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}			
		});
		
		mDisplayBottomTag.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				TagDialog();
			}			
		});
		
		mDisplayBottomAt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				 startActivity(new Intent(VideoUploadActivity.this,SelectFriendsActivity.class));
			}
		});
		
		mSubmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//showRoundProcessDialog(VideoUploadActivity.this);
				String date_time = TimeUtil.getStringDate();
				String sys_time =String.valueOf(System.currentTimeMillis());
				String tblpath  = Environment.getExternalStorageDirectory()+"/"+"a.jpg";
				String videopath =getIntent().getExtras().getString("path");
				try {
					LoadUtil.sendTblFile(tblpath, sys_time);
					LoadUtil.sendVideoFile(videopath,sys_time);
					String content= mContent.getText().toString().trim();
					String photo = Utils.UID+"/Status/"+sys_time+".jpg";
					String path = Utils.UID+"/Video/"+sys_time+".mp4";
					List<NameValuePair> params =new ArrayList<NameValuePair>();      
					params.add(new BasicNameValuePair("tag", "\"new\""));
					params.add(new BasicNameValuePair("uid", "\""+Utils.UID+"\""));  
					params.add(new BasicNameValuePair("time", "\""+date_time+"\""));
					params.add(new BasicNameValuePair("content", "\""+content+"\""));  
					params.add(new BasicNameValuePair("from", "\""+"来自Android客户端"+"\""));  
					params.add(new BasicNameValuePair("photo", "\""+photo+ "\""));  	
					params.add(new BasicNameValuePair("path", "\""+path+ "\""));  	
					String json=LoadUtil.HttpPostText("PersonStatusServlet",params);
					if(json.toString().trim().equals("Upload Succeed")){
							Toast.makeText(VideoUploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					}
					else{
							Toast.makeText(VideoUploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(VideoUploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
				}
				Utils.removeActivity();
			}		
		});
	}

	private void init() {
		Utils.ActivityList.add(this);
		Bitmap bitmap = PhotoUtil.getImageThumbnail(Environment.getExternalStorageDirectory()+"/"+"a.jpg",480,480);
		mImage.setImageBitmap(bitmap);
	}
	
	/**
	 * 标签对话框
	 */
	private void TagDialog() {
		AlertDialog.Builder builder = new Builder(VideoUploadActivity.this);
		builder.setTitle("请选择标签");
		builder.setAdapter(new CompetenceAdapter(),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						mTagPosition = which;
						mDisplayBottomTag
								.setText(mTagItems[which]);
						dialog.dismiss();
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create().show();
	}

	private class CompetenceAdapter extends BaseAdapter {

		public int getCount() {
			return mTagItems.length;
		}

		public Object getItem(int position) {
			return mTagItems[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(VideoUploadActivity.this).inflate(
						R.layout.voice_activity_competence_item, null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.voice_activity_competence_item_icon);
				holder.name = (TextView) convertView
						.findViewById(R.id.voice_activity_competence_item_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (mTagPosition == position) {
				holder.icon.setVisibility(View.VISIBLE);
			} else {
				holder.icon.setVisibility(View.INVISIBLE);
			}
			holder.name.setText(mTagItems[position]);
			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView name;
		}
	}
	
	   public void showRoundProcessDialog(Context mContext){
	    	Dialog mDialog = new AlertDialog.Builder(mContext).create();
	        mDialog.show();
	        // 注意此处要放在show之后 否则会报异常
	        mDialog.setContentView(R.layout.loading_process_dialog_anim);
	       // mDialog.setCancelable(false);  //false设置点击其他地方不能取消进度条
	    }
}
