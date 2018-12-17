package cn.dennishucd.menu;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dennishucd.R;
import cn.dennishucd.activity.CheckInActivity;
import cn.dennishucd.activity.CommentDetailActivity;
import cn.dennishucd.activity.FriendInfoActivity;
import cn.dennishucd.activity.FriendsActivity;
import cn.dennishucd.activity.LoginActivity;
import cn.dennishucd.activity.PhoneAlbumActivity;
import cn.dennishucd.activity.SetUpActivity;
import cn.dennishucd.activity.VideoPlayActivity;
import cn.dennishucd.activity.VisitorsActivity;
import cn.dennishucd.activity.VoiceActivity;
import cn.dennishucd.activity.VoiceSearchActivity;
import cn.dennishucd.activity.WriteRecordActivity;
import cn.dennishucd.anim.ListAnimations;
import cn.dennishucd.anim.ScaleInAnimationAdapter;
import cn.dennishucd.anim.UgcAnimations;
import cn.dennishucd.cache.LifeApplication;
import cn.dennishucd.cache.UgcPathLActivity;
import cn.dennishucd.result.HomeFriendsResult;
import cn.dennishucd.result.GridViewResult;
import cn.dennishucd.share.OnekeyShare;
import cn.dennishucd.share.ShowShare;
import cn.dennishucd.uibase.CircleImageView;
import cn.dennishucd.uibase.XListView;
import cn.dennishucd.uibase.FlipperLayout.OnOpenListener;
import cn.dennishucd.uibase.XListView.IXListViewListener;
import cn.dennishucd.utils.ActivityForResultUtil;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.Options;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.TextUtil;
import cn.dennishucd.utils.TimeUtil;
import cn.dennishucd.utils.Utils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 菜单首页类
 * 
 * @author lqb
 * 
 */
public class Home extends UgcPathLActivity  implements IXListViewListener{
	private static final int MODE_PRIVATE = 0;
	private Context mContext;
	private LifeApplication mKXApplication;
	private View mHome;
	private Button mMenu;
	private Button mMenur;
	private LinearLayout mTopLayout;
	private TextView mTopText;
	private View mUgcView;
	private ImageView mPlayButton;
	private OnOpenListener mOnOpenListener;
	private String[] mPopupWindowItems = { "热门动态", "好友动态" };
	private PopupWindow mPopupWindow;
	private View mPopView;
	private ListView mPopDisplay;
	private HomeListAdapter list_adapter;
	private HomeGridAdapter grid_adapter;
	private XListView mDisplay;
	private GridView mHotDisplay;
	private Handler mHandler;
	boolean isSX=true;
	String type="cx";
	SharedPreferences userMessage;
	
	public Home(Context context, Activity activity, LifeApplication application) {
		mContext = context;
		mKXApplication = application;
		UgcView(context,activity,application);
		mHome = LayoutInflater.from(context).inflate(R.layout.home, null);
		mPopView = LayoutInflater.from(context).inflate(
				R.layout.home_popupwindow, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mUgcView = (View) mHome.findViewById(R.id.home_ugc);
		initUgcView(mUgcView);
		mMenu = (Button) mHome.findViewById(R.id.home_menu);
		mMenur = (Button) mHome.findViewById(R.id.home_menur);
		mTopLayout = (LinearLayout) mHome.findViewById(R.id.home_top_layout);
		mTopText = (TextView) mHome.findViewById(R.id.home_top_text);
		mDisplay = (XListView) mHome.findViewById(R.id.home_display);
		mPlayButton =(ImageView) mHome.findViewById(R.id.home_photo_item_playbutton);
		mPopDisplay = (ListView) mPopView.findViewById(R.id.home_popupwindow_display);
		mHotDisplay=(GridView) mHome.findViewById(R.id.griddisplay);
	}

	private void setListener() {
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		mMenur.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.openr();
				}
			}
		});
		mTopLayout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示菜单
				initPopupWindow();
			}
		});
		mPopDisplay.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mPopupWindow.dismiss();	
				mTopText.setText(mPopupWindowItems[arg2]);
				if(mPopupWindowItems[arg2].equals("好友动态"))
				{
					mHotDisplay.setVisibility(View.GONE);
					mDisplay.setVisibility(View.VISIBLE);
				}
				else {
					mDisplay.setVisibility(View.GONE);
					mHotDisplay.setVisibility(View.VISIBLE);
				}
			}
		});
		
	}

	private void init() {
		// 获取首页数据
		getHomeHot();
		getHomeFriends();
		// 添加适配器
		list_adapter=new HomeListAdapter();
		grid_adapter =new HomeGridAdapter();
		mHotDisplay.setAdapter(grid_adapter);
		ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(list_adapter, 0f);
		scaleInAnimationAdapter.setListView(mDisplay);
		mDisplay.setAdapter(scaleInAnimationAdapter);
		//mDisplay.setAdapter(list_adapter);
		mDisplay.setPullLoadEnable(true);
		mDisplay.setListViewListener(this);
		//mDisplay.setLayoutAnimation(ListAnimations.getListAnim());
		mHandler = new Handler();
		userMessage=mContext.getSharedPreferences("shuaxin", MODE_PRIVATE);
		if(userMessage.getString(type+"time", "").length()>0){
			 mDisplay.setRefreshTime(userMessage.getString(type+"time", ""));
		 } 
	}
	
	/**
	 * 获取首页热门动态
	 */
	private void getHomeHot(){
		if (!isSX) {
			LifeApplication.mMyHomeHotResults = new ArrayList<GridViewResult>();
		}
			String json=LoadUtil.HttpGetText("HomeHotServlet?tag="+"\"Search\"");
			if(json!=null){
				JSONArray array;
				try {
					  array = new JSONArray(json);
					   GridViewResult result = null;
						for (int i = 0; i < array.length(); i++) {
								result = new GridViewResult();
								result.setUid(array.getJSONObject(i).getString("uid"));
								result.setAvatar(array.getJSONObject(i).getString("avatar"));
								result.setTime(array.getJSONObject(i).getString("time"));
								result.setTitle(array.getJSONObject(i).getString("title"));
								result.setPhoto(array.getJSONObject(i).getString("photo"));
								mKXApplication.mMyHomeHotResults.add(result);
						}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}}
	}
	/**
	 * 获取首页好友动态
	 */
	private void getHomeFriends() {
		if (!isSX) {
			LifeApplication.mMyHomeResults = new ArrayList<HomeFriendsResult>();
		}
			String json=LoadUtil.HttpGetText("HomeFriendsServlet?uid="+"\""+Utils.UID+"\"");
			if(json!=null){
				JSONArray array;
				try {
					array = new JSONArray(json);
					   HomeFriendsResult result = null;
						for (int i = 0; i < array.length(); i++) {
								result = new HomeFriendsResult();
								result.setUid(array.getJSONObject(i).getString("uid"));
								if(array.getJSONObject(i).getString("name").equals(Utils.Name)){
									result.setName("我");
								}
								else{
									result.setName(array.getJSONObject(i).getString("name"));
								}
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
									result.setPath(array.getJSONObject(i).getString("path"));
								}
								mKXApplication.mMyHomeResults.add(result);
				}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
	}

	/**
	 * 初始化菜单
	 */
	private void initPopupWindow() {
		PopupWindowAdapter adapter = new PopupWindowAdapter();
		mPopDisplay.setAdapter(adapter);
		if (mPopupWindow == null) {
			mPopupWindow = new PopupWindow(mPopView, mTopLayout.getWidth(),
					LayoutParams.WRAP_CONTENT, true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		}
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		} else {
			mPopupWindow.showAsDropDown(mTopLayout, 0, -10);
		}
	}

	
	private class PopupWindowAdapter extends BaseAdapter {

		public int getCount() {
			return mPopupWindowItems.length;
		}

		public Object getItem(int position) {
			return mPopupWindowItems[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.home_popupwindow_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.home_popupwindow_item_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(mPopupWindowItems[position]);
			return convertView;
		}

		class ViewHolder {
			TextView name;
		}
	}

	public class HomeGridAdapter extends BaseAdapter{
        
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mKXApplication.mMyHomeHotResults.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mKXApplication.mMyHomeHotResults.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
		    if(convertView == null) {
	            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_hot_item, null);
				holder = new ViewHolder();
				holder.viewed=(View) convertView.findViewById(R.layout.home_hot_item);
				holder.photo_content=(TextView)convertView.findViewById(R.id.home_hot_item_text); 
				holder.photo_hot=(ImageView)convertView.findViewById(R.id.home_hot_item_photo); 
				holder.photo_avatar=(CircleImageView)convertView.findViewById(R.id.home_hot_item_avatar); 
				convertView.setTag(holder);
		    }
		    else {
				holder = (ViewHolder) convertView.getTag();
			}
		    final GridViewResult result = mKXApplication.mMyHomeHotResults.get(position);
			holder.photo_content.setText(result.getTitle());
			mKXApplication.imageLoader.displayImage(LoadUtil.URL+"User/"+result.getPhoto(), 
					holder.photo_hot,mKXApplication.options);
			holder.photo_avatar.setImageBitmap(mKXApplication.getDefaultAvatar());
			mKXApplication.imageLoader.displayImage(LoadUtil.URL+"Avatar/"+result.getAvatar(),
					holder.photo_avatar, mKXApplication.options);
			holder.photo_hot.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(mContext, CommentDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("tag", "hot");
					bundle.putString("uid", result.getUid());
					bundle.putString("avatar", result.getAvatar());
					bundle.putString("time", result.getTime());
					bundle.putString("title", result.getTitle());
					bundle.putString("photo", result.getPhoto());
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}			
			});
	        return convertView;
	    }
		public  class ViewHolder {
			View viewed;
			ImageView photo_hot;
			CircleImageView photo_avatar;
			TextView photo_content;
		}
	}
	
	public class HomeListAdapter extends BaseAdapter  {
		
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
				
				holder.photo_forward_count.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ShowShare share=new ShowShare(mContext,mHome);
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
							bundle.putString("url", result.getPath());
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
			//SurfaceView video_surface;
			//SurfaceHolder video_holder;
		}
		
	}
	
	
	public void setCurView(){
		mTopText.setText("好友动态");
		mHotDisplay.setVisibility(View.GONE);
		mDisplay.setVisibility(View.VISIBLE);
	}
	public View getView() {
		return mHome;
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
				list_adapter.setFlagBusy(true);
				mKXApplication.mMyHomeResults.clear();
				isSX=false;
				getHomeFriends();
				onLoad();
				//mDisplay.setLayoutAnimation(ListAnimations.getListAnim());
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
				list_adapter.setFlagBusy(false);
				onLoad();
			}
		}, 2000);
	}
	
}
