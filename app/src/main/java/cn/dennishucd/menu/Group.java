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
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dennishucd.R;
import cn.dennishucd.activity.CheckInActivity;
import cn.dennishucd.activity.CommentDetailActivity;
import cn.dennishucd.activity.FriendsActivity;
import cn.dennishucd.activity.LoginActivity;
import cn.dennishucd.activity.PhoneAlbumActivity;
import cn.dennishucd.activity.SetUpActivity;
import cn.dennishucd.activity.VideoPlayActivity;
import cn.dennishucd.activity.VisitorsActivity;
import cn.dennishucd.activity.VoiceActivity;
import cn.dennishucd.activity.WriteRecordActivity;
import cn.dennishucd.anim.UgcAnimations;
import cn.dennishucd.cache.LifeApplication;
import cn.dennishucd.result.GridViewResult;
import cn.dennishucd.uibase.CircleImageView;
import cn.dennishucd.uibase.XListView;
import cn.dennishucd.uibase.FlipperLayout.OnOpenListener;
import cn.dennishucd.utils.ActivityForResultUtil;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.TextUtil;
import cn.dennishucd.utils.Utils;
import cn.dennishucd.utils.ViewUtil;

/**
 * 菜单首页类
 * 
 * @author lqb
 * 
 */
public class Group {
	private Context mContext;
	private LifeApplication mKXApplication;
	private View mGroup;
	private Button mReturn;
	private TextView mTopText;
	private OnOpenListener mOnOpenListener;
	private GroupGridAdapter grid_adapter;
	private GridView mGroupDisplay;
	private String mTag;
	boolean isSX=true;

	/**
	 * 判断当前的path菜单是否已经显示
	 */
	private boolean mUgcIsShowing = false;

	public Group(Context context,LifeApplication application,String tag) {
		mContext = context;
		mKXApplication = application;
		mTag=tag;
		mGroup = LayoutInflater.from(context).inflate(R.layout.group, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mReturn = (Button) mGroup.findViewById(R.id.group_return);
		mTopText = (TextView) mGroup.findViewById(R.id.group_top_text);
		mGroupDisplay=(GridView) mGroup.findViewById(R.id.group_griddisplay);
	}

	private void setListener() {
		mReturn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.openr();
				}
			}
		});
	}

	private void init() {
		mTopText.setText(ViewUtil.getTagName(mTag));
		// 获取首页数据
		getGroup();
		// 添加适配器
		grid_adapter =new GroupGridAdapter();
		mGroupDisplay.setAdapter(grid_adapter);
	}

	/**
	 * 获取首页热门动态
	 */
	private void getGroup(){
			List<GridViewResult> gridresult= new ArrayList<GridViewResult>();
			LifeApplication.mGroupResults.put(mTag, gridresult);
			String json=LoadUtil.HttpGetText("GroupServlet?tag="+"\""+mTag+"\"");
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
								mKXApplication.mGroupResults.get(mTag).add(result);
						}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}}
	}
	
	public class GroupGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mKXApplication.mGroupResults.get(mTag).size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mKXApplication.mGroupResults.get(mTag).get(position);
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
				holder.photo_group=(ImageView)convertView.findViewById(R.id.home_hot_item_photo); 
				convertView.setTag(holder);
				holder.photo_avatar=(ImageView)convertView.findViewById(R.id.home_hot_item_avatar); 
				convertView.setTag(holder);
		    }
		    else {
				holder = (ViewHolder) convertView.getTag();
			}
		    final GridViewResult result = mKXApplication.mGroupResults.get(mTag).get(position);
		    mKXApplication.imageLoader.displayImage(LoadUtil.URL+"User/"+result.getPhoto(),
					holder.photo_group, mKXApplication.options);
			holder.photo_avatar.setImageBitmap(mKXApplication.getDefaultAvatar());
			mKXApplication.imageLoader.displayImage(LoadUtil.URL+"Avatar/"+result.getAvatar(), 
					holder.photo_avatar, mKXApplication.options);
		  //holder.photo_group.setImageBitmap(mKXApplication.getGroup(result.getPhoto(),mTag));
			holder.photo_content.setText(result.getTitle());
			holder.photo_group.setOnClickListener(new OnClickListener(){

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
			ImageView photo_group;
			ImageView photo_avatar;
			TextView photo_content;
		}
	}

	public View getView() {
		return mGroup;
	}
	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
	
}
