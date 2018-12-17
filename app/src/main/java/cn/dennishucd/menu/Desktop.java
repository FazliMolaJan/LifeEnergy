package cn.dennishucd.menu;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dennishucd.R;
import cn.dennishucd.activity.CheckInActivity;
import cn.dennishucd.activity.PhoneAlbumActivity;
import cn.dennishucd.activity.SetUpActivity;
import cn.dennishucd.activity.VoiceActivity;
import cn.dennishucd.activity.WriteRecordActivity;
import cn.dennishucd.anim.UgcAnimations;
import cn.dennishucd.cache.LifeApplication;
import cn.dennishucd.cache.UgcPathLActivity;
import cn.dennishucd.utils.ActivityForResultUtil;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.TextUtil;
import cn.dennishucd.utils.Utils;
import cn.dennishucd.utils.ViewUtil;

/**
 * 菜单界面
 * 
 * @author lqb
 * 
 */
public class Desktop extends UgcPathLActivity {
	private Context mContext;
	private Activity mActivity;
	private LifeApplication mKXApplication;
	/**
	 * 当前界面的View
	 */
	private View mDesktop;
	/**
	 * 以下为控件,自己查看布局文件
	 */
	private LinearLayout mWallpager;
	private RelativeLayout mTopLayout;
	private ImageView mAvatar;
	private TextView mName;
	private TextView mSig;
	private ListView mDisplay;
	private View mUgcView;
	/**
	 * 桌面适配器
	 */
	private DesktopAdapter mAdapter;
	/**
	 * 判断当前的path菜单是否已经显示
	 */
	private boolean mUgcIsShowing = false;
	/**
	 * 接口对象,用来修改显示的View
	 */
	private onChangeViewListener mOnChangeViewListener;

	public Desktop(Context context, Activity activity, LifeApplication application) {
		mContext = context;
		mActivity = activity;
		mKXApplication = application;
		UgcView(context,activity,application);
		// 绑定布局到当前View
		mDesktop = LayoutInflater.from(context).inflate(R.layout.desktop, null);
		findViewById();
		setListener();
		init();
	}

	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		mUgcView = (View) mDesktop.findViewById(R.id.desktop_ugc);
		initUgcView(mUgcView);
		mWallpager = (LinearLayout) mDesktop
				.findViewById(R.id.desktop_wallpager);
		mTopLayout = (RelativeLayout) mDesktop
				.findViewById(R.id.desktop_top_layout);
		mAvatar = (ImageView) mDesktop.findViewById(R.id.desktop_avatar);
		mName = (TextView) mDesktop.findViewById(R.id.desktop_name);
		mSig = (TextView) mDesktop.findViewById(R.id.desktop_sig);
		mDisplay = (ListView) mDesktop.findViewById(R.id.desktop_display);
	}

	/**
	 * UI事件监听
	 */
	private void setListener() {
		// 头布局监听
		mTopLayout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 切换界面View为用户首页
				if (mOnChangeViewListener != null) {
					mOnChangeViewListener.onChangeView(ViewUtil.USER);
					mAdapter.setChoose(-1);
					mAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	/**
	 * 界面初始化
	 */
	private void init() {
		/**
		 * 设置墙纸、姓名、签名、头像以及菜单界面
		 */
		mWallpager.setBackgroundDrawable(new BitmapDrawable(mKXApplication
				.getWallpager(mKXApplication.mWallpagerPosition)));
		mName.setText(Utils.Name);
		mSig.setText(Utils.Sig);
		mKXApplication.imageLoader.displayImage(LoadUtil.URL+"Avatar/"+Utils.Avatar, 
				mAvatar,mKXApplication.options);
		mAdapter = new DesktopAdapter(mContext);
		mDisplay.setAdapter(mAdapter);
	}

	/**
	 * 更改墙纸
	 */
	public void setWallpager() {
		mWallpager.setBackgroundDrawable(new BitmapDrawable(mKXApplication
				.getWallpager(mKXApplication.mWallpagerPosition)));
	}

	/**
	 * 更改签名
	 * 
	 * @param arg0
	 *            修改的签名文本
	 */
	public void setSignature(String arg0) {
		mSig.setText(new TextUtil(mKXApplication).replace(arg0));
	}

	/**
	 * 修改头像
	 * 
	 * @param bitmap
	 *            修改的头像
	 */
	public void setAvatar(Bitmap bitmap) {
		mAvatar.setImageBitmap(bitmap);
	}

	/**
	 * 界面修改方法
	 * 
	 * @param onChangeViewListener
	 */
	public void setOnChangeViewListener(
			onChangeViewListener onChangeViewListener) {
		mOnChangeViewListener = onChangeViewListener;
	}

	/**
	 * 获取菜单界面
	 * 
	 * @return 菜单界面的View
	 */
	public View getView() {
		return mDesktop;
	}

	/**
	 * 切换显示界面的接口
	 * 
	 * @author lqb
	 * 
	 */
	public interface onChangeViewListener {
		public abstract void onChangeView(int arg0);
	}

	/**
	 * 菜单适配器
	 * 
	 * @author lqb
	 * 
	 */
	public class DesktopAdapter extends BaseAdapter {

		private Context mContext;
		private String[] mName = { "首页", "消息","好友","能量","照片", "视频", "礼物" };
		private int[] mIcon = { R.drawable.sidebar_icon_dynamic,R.drawable.sidebar_icon_news,R.drawable.sidebar_icon_friends,
				R.drawable.sidebar_icon_energy, R.drawable.sidebar_icon_photo, R.drawable.sidebar_icon_viewed,
				R.drawable.sidebar_icon_gifts };
		private int[] mIconPressed = { R.drawable.sidebar_icon_dynamic_pressed,
				R.drawable.sidebar_icon_news_pressed,
				R.drawable.sidebar_icon_friends_pressed,
				R.drawable.sidebar_icon_energy_pressed,
				R.drawable.sidebar_icon_photo_pressed,
				R.drawable.sidebar_icon_viewed_pressed,
				R.drawable.sidebar_icon_gifts_pressed };
		private int mChoose = 0;

		public DesktopAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return 7;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public void setChoose(int choose) {
			mChoose = choose;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.desktop_item, null);
				holder = new ViewHolder();
				holder.layout = (LinearLayout) convertView
						.findViewById(R.id.desktop_item_layout);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.desktop_item_icon);
				holder.name = (TextView) convertView
						.findViewById(R.id.desktop_item_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(mName[position]);
			if (position == mChoose) {
				holder.name.setTextColor(Color.parseColor("#ffffffff"));
				holder.icon.setImageResource(mIconPressed[position]);
				holder.layout.setBackgroundColor(Color.parseColor("#20000000"));
			} else {
				holder.name.setTextColor(Color.parseColor("#7fffffff"));
				holder.icon.setImageResource(mIcon[position]);
				holder.layout.setBackgroundResource(Color
						.parseColor("#00000000"));
			}
			convertView.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (mOnChangeViewListener != null) {
						switch (position) {
						case ViewUtil.HOME:
							mOnChangeViewListener.onChangeView(ViewUtil.HOME);
							break;
						case ViewUtil.MESSAGES:
							mOnChangeViewListener
									.onChangeView(ViewUtil.MESSAGES);
							break;
						case ViewUtil.FRIENDS:
							mOnChangeViewListener
									.onChangeView(ViewUtil.FRIENDS);
							break;
						case ViewUtil.ENERGY:
							mOnChangeViewListener
									.onChangeView(ViewUtil.ENERGY);
							break;
						case ViewUtil.PHOTO:
							mOnChangeViewListener.onChangeView(ViewUtil.PHOTO);
							break;
						case ViewUtil.GIFTS:
							mOnChangeViewListener.onChangeView(ViewUtil.GIFTS);
							break;
						default:
							mOnChangeViewListener.onChangeView(ViewUtil.HOME);
							break;
						}
						mChoose = position;
						notifyDataSetChanged();
					}

				}
			});
			return convertView;
		}

		class ViewHolder {
			LinearLayout layout;
			ImageView icon;
			TextView name;
		}
	}
	
}
