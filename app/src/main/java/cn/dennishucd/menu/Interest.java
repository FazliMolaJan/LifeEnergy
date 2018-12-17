package cn.dennishucd.menu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dennishucd.R;
import cn.dennishucd.activity.AboutActivity;
import cn.dennishucd.activity.ChangeWallpagerActivity;
import cn.dennishucd.activity.CheckInActivity;
import cn.dennishucd.activity.CommentDetailActivity;
import cn.dennishucd.activity.ContactsActivity;
import cn.dennishucd.activity.EditSignatureActivity;
import cn.dennishucd.activity.FriendInfoActivity;
import cn.dennishucd.activity.FriendsActivity;
import cn.dennishucd.activity.LoginActivity;
import cn.dennishucd.activity.PhoneAlbumActivity;
import cn.dennishucd.activity.PhotoActivity;
import cn.dennishucd.activity.SetUpActivity;
import cn.dennishucd.activity.DiaryActivity;
import cn.dennishucd.activity.VideoPlayActivity;
import cn.dennishucd.activity.VisitorsActivity;
import cn.dennishucd.activity.VoiceActivity;
import cn.dennishucd.activity.WriteRecordActivity;
import cn.dennishucd.anim.UgcAnimations;
import cn.dennishucd.cache.LifeApplication;
import cn.dennishucd.menu.Home.HomeListAdapter.ViewHolder;
import cn.dennishucd.result.FriendInfoResult;
import cn.dennishucd.result.HomeFriendsResult;
import cn.dennishucd.result.StatusResult;
import cn.dennishucd.result.VisitorsResult;
import cn.dennishucd.share.ShowShare;
import cn.dennishucd.uibase.CircleImageView;
import cn.dennishucd.uibase.XListView;
import cn.dennishucd.uibase.FlipperLayout.OnOpenListener;
import cn.dennishucd.uibase.XListView.IXListViewListener;
import cn.dennishucd.utils.ActivityForResultUtil;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.TextUtil;
import cn.dennishucd.utils.TimeUtil;
import cn.dennishucd.utils.Utils;

/**
 * 用户首页
 * 
 * @author lqb
 * 
 */
public class Interest  implements IXListViewListener{
	private static final int MODE_PRIVATE = 0;
	private Context mContext;
	private LifeApplication mKXApplication;
	private View mInterest;
	private View mInterestHead;
	private Button mFriend;
	private Button mTag;
	private Button mAll;
	private TextView mCancle;
	private String mCurButton;
	private LinearLayout mHead_Friends_List;
	//private Button mHead_Friends_List_Count;
	private XListView mDisplay;
	private HomeListAdapter mAdapter;
	private OnOpenListener mOnOpenListener;
	private Handler mHandler;
	boolean isSX=true;
	String type="cx";
	SharedPreferences userMessage;


	public Interest(Context context, LifeApplication application) {
		mContext = context;
		mKXApplication = application;
		mInterest = LayoutInflater.from(context).inflate(R.layout.interest, null);
		mInterestHead = LayoutInflater.from(context).inflate(
				R.layout.interest_head, null);
		userMessage=mContext.getSharedPreferences("shuaxin", MODE_PRIVATE);
		findViewById();
		setListener();
		init();
	}

	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		mCancle = (TextView) mInterest.findViewById(R.id.interest_cancle);
		mAll = (Button) mInterest.findViewById(R.id.interest_all);
		mFriend = (Button) mInterest.findViewById(R.id.interest_friend);
		mTag = (Button) mInterest.findViewById(R.id.interest_tag);
		mHead_Friends_List = (LinearLayout) mInterestHead
				.findViewById(R.id.interest_friends_list);
		//mHead_Friends_List_Count = (Button) mInterestHead
		//		.findViewById(R.id.interest_friends_list_count);
		mDisplay = (XListView) mInterest.findViewById(R.id.interest_display);
	}

	/**
	 * UI事件监听
	 */
	private void setListener() {
	
		mCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mOnOpenListener != null) {
					mOnOpenListener.openr();
				}
			}		
		});
		
		mAll.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 如果显示的不是全部则显示全部
				if (!mCurButton.equals("All")) {
					mCurButton = "All";
					//mAll.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mAll.setBackgroundColor(Color.parseColor("#6495ed"));
					mAll.setTextColor(Color.WHITE);
					mFriend.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mTag.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
				
				}
			}
		});
		
		mFriend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 如果显示的不是好友内容则显示好友内容
				if (!mCurButton.equals("Friend")) {
					mCurButton = "Friend";
					mFriend.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mAll.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mTag.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
				
				}
			}
		});
		
		mTag.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 如果显示的不是标签内容则显示标签内容
				if (!mCurButton.equals("Tag")) {
					mCurButton = "Tag";
					
					mTag.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mFriend.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mAll.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
				
				}
			}
		});
		// 最近来访数量监听
	/*	mHead_Friends_List_Count.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到最近来访列表界面
				mContext.startActivity(new Intent(mContext,
						VisitorsActivity.class));
			}
		});
*/
	}

	/**
	 * 界面初始化
	 */
	private void init() {
		mCurButton ="All";
		getInfo();
		getVisitors();
	    getHomeFriends();
	    mDisplay.addHeaderView(mInterestHead);
		mAdapter = new HomeListAdapter();
		mDisplay.setAdapter(mAdapter);
		mDisplay.setPullLoadEnable(true);
		mDisplay.setListViewListener(this);
		mHandler = new Handler();
		if(userMessage.getString(type+"time", "").length()>0){
			mDisplay.setRefreshTime(userMessage.getString(type+"time", ""));
		 } 
	}
	
	private void getHomeFriends() {
			LifeApplication.mMyHomeResults = new ArrayList<HomeFriendsResult>();
			String json=LoadUtil.HttpGetText("HomeFriendsServlet?uid="+"\""+Utils.UID+"\"");
			if(json!=null){
				JSONArray array;
				try {
					array = new JSONArray(json);
					   HomeFriendsResult result = null;
						for (int i = 0; i < array.length(); i++) {
								result = new HomeFriendsResult();
								result.setUid(array.getJSONObject(i).getString("uid"));
								result.setName(array.getJSONObject(i).getString("name"));
								result.setAvatar(array.getJSONObject(i).getString("avatar"));
								result.setType("photo");
								result.setTime(array.getJSONObject(i).getString("time"));
								result.setTitle(array.getJSONObject(i).getString("title"));
								result.setForward_count(10);
								if (array.getJSONObject(i).has("from")) {
									result.setFrom(array.getJSONObject(i).getString("from"));
								}
								if (array.getJSONObject(i).has("comment_count")) {
									result.setComment_count(array.getJSONObject(i).getInt(
											"comment_count"));
								}
								if (array.getJSONObject(i).has("like_count")) {
									result.setLike_count(array.getJSONObject(i).getInt(
											"like_count"));
								}
								if (array.getJSONObject(i).has("photo")) {
									result.setPhoto(array.getJSONObject(i).getString(
											"photo"));
								}
								if(array.getJSONObject(i).has("path")){
									//result.setPath(array.getJSONObject(i).getString("path"));
									result.setPath("/storage/sdcard1/VID_9002.mp4");
								}
								mKXApplication.mMyHomeResults.add(result);
				}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
	}

	/**
	 * 获取用户资料
	 */
	private void getInfo() {
		if (!isSX) {
			LifeApplication.mMyHomeResults = new ArrayList<HomeFriendsResult>();
		}
		mKXApplication.mMyInfoResult = new FriendInfoResult();
		try {
			String json=LoadUtil.HttpGetText("PersonInfoServlet?uid="+"\""+Utils.UID+"\"");
			if(!json.toString().trim().equals("")){
			JSONObject object = new JSONObject(json);
			mKXApplication.mMyInfoResult.setName(object.getString("name"));
			mKXApplication.mMyInfoResult.setAvatar(object.getString("avatar"));
			mKXApplication.mMyInfoResult.setGender(object.getInt("gender"));
			mKXApplication.mMyInfoResult.setConstellation(object
					.getString("constellation"));
			mKXApplication.mMyInfoResult.setSignature(object
					.getString("signature"));
			mKXApplication.mMyInfoResult.setPhoto_count(object
					.getInt("photo_count"));
			mKXApplication.mMyInfoResult.setDiary_count(object
					.getInt("video_count"));
			mKXApplication.mMyInfoResult.setFriend_count(object
					.getInt("friend_count"));
			mKXApplication.mMyInfoResult.setVisitor_count(object
					.getInt("visitor_count"));
			mKXApplication.mMyInfoResult.setWallpager(object
					.getInt("wallpager"));
			mKXApplication.mMyInfoResult.setDate(object.getString("date"));
			}
		
		//	mHead_Friends_List_Count.setText(mKXApplication.mMyInfoResult
			//		.getVisitor_count() + "");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户最近来访
	 */
	private void getVisitors() {
		InputStream inputStream;
		try {
			String json=LoadUtil.HttpGetText("PersonVisitorsServlet?tag=\"search\"&&uid="+"\""+Utils.UID+"\"");
			JSONArray array = new JSONArray(json);
			VisitorsResult result = null;
			for (int i = 0; i < array.length(); i++) {
				result = new VisitorsResult();
				result.setUid(array.getJSONObject(i).getString("uid"));
				result.setName(array.getJSONObject(i).getString("name"));
				result.setAvatar(array.getJSONObject(i).getString("avatar"));
				result.setTime(array.getJSONObject(i).getString("time"));
				mKXApplication.mMyVisitorsResults.add(result);

				// 显示最近头像
				CircleImageView imageView = new CircleImageView(mContext);
				int widthAndHeight = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_FRACTION_PARENT, 60, mContext
								.getResources().getDisplayMetrics());
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						widthAndHeight, widthAndHeight);
				imageView.setImageBitmap(mKXApplication.getDefaultAvatar());
				mKXApplication.imageLoader.displayImage(LoadUtil.URL+"Avatar/"+result.getAvatar(), 
						imageView, mKXApplication.options);
				imageView.setLayoutParams(params);
				imageView.setPadding(4, 4,4, 4);
				imageView.setTag(result);
				mHead_Friends_List.addView(imageView);
				mHead_Friends_List.invalidate();
				imageView.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						VisitorsResult result = (VisitorsResult) v.getTag();
						Intent intent = new Intent();
						intent.setClass(mContext, FriendInfoActivity.class);
						intent.putExtra("uid", result.getUid());
						intent.putExtra("name", result.getName());
						intent.putExtra("avatar", result.getAvatar());
						mContext.startActivity(intent);
					}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户首页界面
	 * 
	 * @return 用户首页界面的View
	 */
	public View getView() {
		return mInterest;
	}

	public class HomeListAdapter extends BaseAdapter {
		
		private boolean mBusy = false;
		public void setFlagBusy(boolean busy) {
			this.mBusy = busy;
		}
		public int getCount() {
			
			return mKXApplication.mMyHomeResults.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mMyHomeResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.home_item, null);
				holder = new ViewHolder();
				holder.viewed = (View) convertView.findViewById(R.id.home_item_viewed);
				holder.viewed_avatar = (ImageView) holder.viewed.
						findViewById(R.id.home_viewed_item_avatar);
				holder.viewed_name = (TextView) holder.viewed
						.findViewById(R.id.home_viewed_item_name);
				holder.viewed_time = (TextView) holder.viewed
						.findViewById(R.id.home_viewed_item_time);
				holder.viewed_title = (TextView) holder.viewed
						.findViewById(R.id.home_viewed_item_title);
				holder.viewed_all = (TextView) holder.viewed
						.findViewById(R.id.home_viewed_item_all);
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
				holder.photo_comment_count = (TextView) holder.photo
						.findViewById(R.id.home_photo_item_comment_count);
				holder.photo_forward_count = (TextView) holder.photo
						.findViewById(R.id.home_photo_item_forward_count);
				holder.photo_like_count = (TextView) holder.photo
						.findViewById(R.id.home_photo_item_like_count);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final HomeFriendsResult result = mKXApplication.mMyHomeResults.get(position);
			 if ("viewed".equals(result.getType())) {
				holder.viewed.setVisibility(View.VISIBLE);
				holder.photo.setVisibility(View.GONE);
				holder.viewed_avatar.setImageBitmap(mKXApplication.getAvatar(result.getAvatar()));
				holder.viewed_name.setText(result.getName());
				holder.viewed_time.setText(TimeUtil.getHomeTime(result.getTime()));
				holder.viewed_title.setText(result.getTitle());
				holder.viewed_all.setText("查看" + result.getName() + "的全部转帖");
			} else {
				holder.viewed.setVisibility(View.GONE);
				holder.photo.setVisibility(View.VISIBLE);
				mKXApplication.imageLoader.displayImage(LoadUtil.URL+"User/"+result.getPhoto(),
						holder.photo_photo, mKXApplication.options);
				holder.photo_avatar.setImageBitmap(mKXApplication.getDefaultAvatar());
				mKXApplication.imageLoader.displayImage(LoadUtil.URL+"Avatar/"+result.getAvatar(), 
						holder.photo_avatar, mKXApplication.options);
				holder.photo_name.setText(result.getName());
				holder.photo_time.setText(TimeUtil.getHomeTime(result.getTime()));
			    holder.photo_title.setText(result.getTitle());
				holder.photo_from.setText(result.getFrom());
				holder.photo_comment_count.setText(result.getComment_count()+ "");
				holder.photo_forward_count.setText(result.getForward_count()+"");
				holder.photo_like_count.setText(result.getLike_count() + "");
			
				holder.photo_comment_count.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(mContext, CommentDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("tag", "friend");
						bundle.putString("uid", result.getUid());
						bundle.putString("avatar", result.getAvatar());
						bundle.putString("name", result.getName());
						bundle.putString("time", result.getTime());
						bundle.putString("title", result.getTitle());
						bundle.putString("photo", result.getPhoto());
						bundle.putString("from", result.getFrom());
						bundle.putInt("comment_count", result.getComment_count());
						bundle.putInt("forward_count", result.getForward_count());
						bundle.putInt("like_count", result.getLike_count());
						bundle.putString("videopath", result.getPath());
						intent.putExtras(bundle);
						mContext.startActivity(intent);
					}	
				});
			    holder.photo_playbutton.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.setClass(mContext, VideoPlayActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("videopath", result.getPath());
							intent.putExtras(bundle);
							mContext.startActivity(intent);
						}
					});	
			}
			return convertView;
		}

		public  class ViewHolder {
			View viewed;
			ImageView viewed_avatar;
			TextView viewed_name;
			TextView viewed_time;
			TextView viewed_title;
			TextView viewed_all;

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
			TextView photo_like_count;
		}
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
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
				mAdapter.setFlagBusy(true);
				mKXApplication.mMyHomeResults.clear();
				isSX=false;
				getHomeFriends();
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
				getHomeFriends();
				mAdapter.setFlagBusy(false);
				onLoad();
			}
		}, 2000);
	}

}
