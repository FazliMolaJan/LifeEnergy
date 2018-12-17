package cn.dennishucd.activity;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dennishucd.FFmpeg4AndroidActivity;
import cn.dennishucd.R;
import cn.dennishucd.cache.BaseActivity;
import cn.dennishucd.cache.LifeApplication;
import cn.dennishucd.result.CommentResult;
import cn.dennishucd.result.FriendInfoResult;
import cn.dennishucd.result.HomeFriendsResult;
import cn.dennishucd.share.ShowShare;
import cn.dennishucd.uibase.CircleImageView;
import cn.dennishucd.uibase.XListView;
import cn.dennishucd.uibase.XListView.IXListViewListener;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.TextUtil;
import cn.dennishucd.utils.TimeUtil;
import cn.dennishucd.utils.Utils;

/**
 * 评论详情类
 * 
 * @author lqb
 * 
 */
public class CommentDetailActivity extends BaseActivity implements IXListViewListener {

		private Context mContext;
		private View mView;
		private Button mMenu;
		private Button mMenur;
		private EditText mcomment;
		private Button mcomment_send;
		private CommentAdapter adapter;
		private XListView mDisplay;
		private Handler mHandler;
		private Bundle bundle;
		boolean isSX=true;
		private HomeFriendsResult result = null;
		private String commentjson =null;
		private LinearLayout mcomment_bar;
		private List<CommentResult> results=new ArrayList<CommentResult>();
		String type="cx";
		SharedPreferences userMessage;

		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.comment);
			mContext=this;
			userMessage=mContext.getSharedPreferences("shuaxin", MODE_PRIVATE);
			bundle= getIntent().getExtras();
			findViewById();
			setListener();
			init();
		}
		
		private void findViewById() {
			mMenu = (Button) findViewById(R.id.return_button);
			mMenur = (Button) findViewById(R.id.refresh_button);
			mDisplay = (XListView) findViewById(R.id.commentdisplay);
			mcomment =(EditText) findViewById(R.id.comment);
			mcomment_send =(Button) findViewById(R.id.comment_send);
			mcomment_bar= (LinearLayout) findViewById(R.id.comment_bar);
		}

		private void setListener() {
			mMenu.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					finish();
				}
			});
			mMenur.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(mContext, FFmpeg4AndroidActivity.class);
					mContext.startActivity(intent);
				}
			});
			mcomment_send.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					new Thread(){
						public void run()
						{
							List<NameValuePair> params =new ArrayList<NameValuePair>();      
							params.add(new BasicNameValuePair("tag", "\"comment\""));
							params.add(new BasicNameValuePair("uid", "\""+bundle.getString("uid")+"\""));  
							params.add(new BasicNameValuePair("time", "\""+bundle.getString("time").substring(0, 19)+"\""));
							params.add(new BasicNameValuePair("name", "\""+Utils.Name+"\""));  
							params.add(new BasicNameValuePair("avatar", "\""+Utils.Avatar+"\""));  
							params.add(new BasicNameValuePair("content", "\""+mcomment.getText()+ "\""));  	
							String json=LoadUtil.HttpPostText("VideoCommentServlet",params);
							if(json.toString().trim().equals("comment succeed")){
								runOnUiThread(new Runnable() {		
									@Override
									public void run() {
										// TODO Auto-generated method stub	
										Toast.makeText(getApplicationContext(), "评论成功",
											     Toast.LENGTH_SHORT).show();
										CommentResult cr = new CommentResult();
										cr.setAvatar(Utils.Avatar);
										cr.setName(Utils.Name);
										cr.setContent(""+mcomment.getText()+"");
										result.setComment_count(result.getComment_count()+1);
										results.add(0, cr);
										adapter.notifyDataSetChanged();
									} 
								});
							}
							else{
								runOnUiThread(new Runnable() {		
								@Override
								public void run() {
									// TODO Auto-generated method stub	
									Toast.makeText(getApplicationContext(), "评论超时",
										     Toast.LENGTH_SHORT).show();
								} 
							});
							}
						}}.start();
					}		
				});
		}

		private void init() {

			getData();
			getComment();
			// 添加适配器
			adapter=new CommentAdapter();
			mDisplay.setAdapter(adapter);
			mDisplay.setPullLoadEnable(true);
			mDisplay.setListViewListener(this);
			mHandler = new Handler();
			if(userMessage.getString(type+"time", "").length()>0){
				 mDisplay.setRefreshTime(userMessage.getString(type+"time", ""));
			 } 
		}

		private void getData(){
			result=new HomeFriendsResult();
			String tag=bundle.getString("tag");
			if(tag.equals("friend")){
				result.setUid(bundle.getString("uid"));
				result.setAvatar((bundle.getString("avatar")));
				result.setName((bundle.getString("name")));
				result.setTime(TimeUtil.getHomeTime((bundle.getString("time"))));
				result.setTitle((bundle.getString("title")));
				result.setType("friend");
				result.setPhoto((bundle.getString("photo")));
				result.setFrom(((bundle.getString("from"))));
				result.setPath((bundle.getString("videopath")));
				result.setComment_count(((bundle.getInt("comment_count"))));
				result.setComment_count(((bundle.getInt("forward_count"))));
				result.setLike_count(((bundle.getInt("like_count"))));
			}
			else if(tag.equals("hot")){
				result.setUid(bundle.getString("uid"));
				result.setAvatar((bundle.getString("avatar")));
				result.setTime(TimeUtil.getHomeTime((bundle.getString("time"))));
				result.setTitle((bundle.getString("title")));
				result.setPhoto((bundle.getString("photo")));
				result.setType("hot");
				List<NameValuePair> params =new ArrayList<NameValuePair>();      
				params.add(new BasicNameValuePair("tag", "\"detail\""));
				params.add(new BasicNameValuePair("uid", "\""+bundle.getString("uid")+"\""));  
				params.add(new BasicNameValuePair("time", "\""+bundle.getString("time").substring(0, 19)+"\""));  
				String json=LoadUtil.HttpPostText("HomeHotServlet",params);
				if(json!=null){
					try {
						JSONArray array=new JSONArray(json);
						if(array.getJSONObject(0).getString("name").equals(Utils.Name)){
							result.setName("我");
						}
						else{
							result.setName(array.getJSONObject(0).getString("name"));
						}
						result.setFrom(array.getJSONObject(0).getString("from"));
						result.setComment_count(array.getJSONObject(0).getInt("comment_count"));
						//result.setLike_type("able");	
						result.setForward_count(10);
						result.setLike_count(array.getJSONObject(0).getInt("like_count"));
						result.setPath(array.getJSONObject(0).getString("path"));
						commentjson=array.getJSONObject(0).getString("comment");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();}
			}}
	}

	private void getComment() {
			if (!isSX) {
				results = new ArrayList<CommentResult>();
			}
			if(commentjson==null){
				List<NameValuePair> params =new ArrayList<NameValuePair>();      
				params.add(new BasicNameValuePair("tag", "\"search\""));
				params.add(new BasicNameValuePair("uid", "\""+bundle.getString("uid")+"\""));  
				params.add(new BasicNameValuePair("time", "\""+bundle.getString("time").substring(0, 19)+"\""));
				commentjson=LoadUtil.HttpPostText("VideoCommentServlet",params);
			}
			if(commentjson!=null){
					JSONArray array;
					try {
						array = new JSONArray(commentjson);
						   CommentResult result = null;
							for (int i = 0; i < array.length(); i++) {
									result = new CommentResult();
									result.setName(array.getJSONObject(i).getString("name"));
									result.setAvatar(array.getJSONObject(i).getString("avatar"));
									result.setContent(array.getJSONObject(i).getString("content"));		
									results.add(result);
							}
						} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();}
				}			
		}
	
		public class CommentAdapter extends BaseAdapter {
			
			private boolean mBusy = false;
			public void setFlagBusy(boolean busy) {
				this.mBusy = busy;
			}
			public int getCount() {
				return results.size()+1;
			}
			public Object getItem(int position) {
				if(position==0){
					return result;
				}
				else{
					return results.get(position-1);
				}
			}

			public long getItemId(int position) {
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = LayoutInflater.from(mContext).inflate(
							R.layout.comment_item, null);
					holder = new ViewHolder();
					holder.viewed = (View) convertView.findViewById(R.id.comment_item_view);
					holder.viewed_avatar = (CircleImageView) holder.viewed.
							findViewById(R.id.comment_avatar);
					holder.viewed_name =(TextView) holder.viewed.findViewById(R.id.comment_name);
					holder.viewed_content = (TextView) holder.viewed.findViewById(R.id.comment_content);
					
					holder.photo = (View) convertView
							.findViewById(R.id.home_item_photo);
					holder.photo_avatar = (CircleImageView) holder.photo
							.findViewById(R.id.home_photo_item_avatar);
					holder.photo_name = (TextView) holder.photo
							.findViewById(R.id.home_photo_item_name);
					holder.photo_time = (TextView) holder.photo
							.findViewById(R.id.home_photo_item_time);
					holder.photo_photo = (ImageView) holder.photo
							.findViewById(R.id.home_photo_item_photo);
					holder.photo_playbutton = (ImageView) holder.photo
							.findViewById(R.id.home_photo_item_playbutton);
					holder.photo_title = (TextView) holder.photo
							.findViewById(R.id.home_photo_item_text);
					holder.photo_from = (TextView) holder.photo
							.findViewById(R.id.home_photo_item_from);
					holder.photo_comment_count = (TextView) holder.photo.
							findViewById(R.id.home_photo_item_comment_count);
					holder.photo_forward_count = (TextView) holder.photo.
							findViewById(R.id.home_photo_item_forward_count);
					holder.photo_like_count = (TextView) holder.photo
							.findViewById(R.id.home_photo_item_like_count);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if(position==0){
					holder.viewed.setVisibility(View.GONE);
					holder.photo.setVisibility(View.VISIBLE);
					mKXApplication.imageLoader.displayImage(LoadUtil.URL+"User/"+result.getPhoto(),
							holder.photo_photo, mKXApplication.options);
					holder.photo_avatar.setImageBitmap(mKXApplication.getDefaultAvatar());
					mKXApplication.imageLoader.displayImage(LoadUtil.URL+"Avatar/"+result.getAvatar(), 
							holder.photo_avatar, mKXApplication.options);
					holder.photo_name.setText(result.getName());
					holder.photo_time.setText(result.getTime());
				    holder.photo_title.setText(result.getTitle());
					holder.photo_from.setText(result.getFrom());
					holder.photo_comment_count.setText(result.getComment_count()+ "");
					holder.photo_forward_count.setText(result.getForward_count()+ "");
					holder.photo_like_count.setText(result.getLike_count() + "");
					holder.photo_avatar.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(mContext, FriendInfoActivity.class);
							intent.putExtra("uid", result.getUid());
							intent.putExtra("name", result.getName());
							intent.putExtra("avatar", result.getAvatar());
							mContext.startActivity(intent);
						}			
					});
				/*	if(result.getlike_type().equals("able")){
						holder.photo_like_count.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								new Thread(){
									@Override
									public void run() {
										// TODO Auto-generated method stub	
								
									} 
								}.start();
							}
						});
					}
					else {
						holder.photo_like.setImageResource(R.drawable.photo_like_disabled);	
					}
					*/
					holder.photo_forward_count.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ShowShare share=new ShowShare(mContext,mView);
							InputStream inStream=LoadUtil.HttpGetPhoto(result.getPhoto(),"friend");
							String photopath=null;
							if(inStream !=null){
								Bitmap bitmap =BitmapFactory.decodeStream(inStream);
								photopath = PhotoUtil.saveToSDCard(bitmap);
							}
							share.setImage_Path(photopath);
							share.setVideo_URL("User/"+result.getPath());
							share.setText(result.getTitle());
							share.ShareGui(false, null, false);
						}
						
					});
				    holder.photo_playbutton.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Intent intent = new Intent();
								intent.setClass(mContext, VideoPlayActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("url", result.getPath());
								intent.putExtras(bundle);
								mContext.startActivity(intent);
							}
						});	
				}
				else{
					CommentResult Result = results.get(position-1);
					holder.photo.setVisibility(View.GONE);
					holder.viewed.setVisibility(View.VISIBLE);
					holder.viewed_avatar.setImageBitmap(mKXApplication.getDefaultAvatar());
					mKXApplication.imageLoader.displayImage(LoadUtil.URL+"Avatar/"+Result.getAvatar(), 
							holder.viewed_avatar, mKXApplication.options);
					holder.viewed_name.setText(Result.getName());
					holder.viewed_content.setText("评论："+Result.getContent());
				}
				return convertView;
			}

			public  class ViewHolder {
				View viewed;
				CircleImageView viewed_avatar;
				TextView viewed_name;
				TextView viewed_content;

				View photo;
				CircleImageView photo_avatar;
				TextView photo_name;
				TextView photo_time;
				TextView photo_title;
				ImageView photo_photo;
				ImageView photo_playbutton;
				TextView photo_from;
				TextView photo_comment_count;
				TextView photo_forward_count;
				//ImageView photo_like;
				TextView photo_like_count;
			}
		}

		private void onLoad() {
			mDisplay.stopRefresh();
			mDisplay.stopLoadMore();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.CHINA);
		    String date=sdf.format(new Date()).substring(0,sdf.format(new Date()).length()-1);
		    if(userMessage.getString(type+"time", "").length()>0){
				 mDisplay.setRefreshTime(userMessage.getString(type+"time", ""));
			 } 
			Editor editor = userMessage.edit();
			editor.putString(type+"time", date);
			editor.commit();
		}
		
		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			mHandler.postDelayed(new Runnable() {
				public void run() {
					adapter.setFlagBusy(true);
					results.clear();
					commentjson=null;
					isSX=false;
					getComment();
					onLoad();
				}
			}, 2000);
		}

		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			mHandler.postDelayed(new Runnable() {
				public void run() {
					isSX=true;
					getComment();
					adapter.setFlagBusy(false);
					onLoad();
				}
			}, 2000);
		}
}

