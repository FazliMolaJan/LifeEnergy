package cn.dennishucd.menu;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.WindowManager;
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
import cn.dennishucd.cache.UgcPathRActivity;
import cn.dennishucd.utils.ActivityForResultUtil;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.TextUtil;
import cn.dennishucd.utils.ViewUtil;

/**
 * 菜单界面
 * 
 * @author lqb
 * 
 */
public class Desktopr extends UgcPathRActivity {
	private Context mContext;
	private Activity mActivity;
	private LifeApplication mKXApplication;
	/**
	 * 当前界面的View
	 */
	private View mDesktopr;
	/**
	 * 以下为控件,自己查看布局文件
	 */
	private View mUgcView;
	private ImageView mDesktopr_god;
	private ImageView mDesktopr_star;
	private ImageView mDesktopr_food;
	private ImageView mDesktopr_travel;
	private ImageView mDesktopr_joke;
	private ImageView mDesktopr_interest;
	/**
	 * 判断当前的path菜单是否已经显示
	 */
	private boolean mUgcIsShowing = false;
	/**
	 * 接口对象,用来修改显示的View
	 */
	private onChangeViewListener mOnChangeViewListener;

	public Desktopr(Context context, Activity activity, LifeApplication application) {
		mContext = context;
		mActivity = activity;
		mKXApplication = application;
		UgcView(context,activity,application);
		mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
		// 绑定布局到当前View
		mDesktopr = LayoutInflater.from(context).inflate(R.layout.desktopr, null);
		findViewById();
		setListener();
	}

	/**
	 * 绑定界面UI
	 */
	private void findViewById() {
		mUgcView = (View) mDesktopr.findViewById(R.id.desktop_ugcr);
		initUgcView(mUgcView);
		mDesktopr_interest = (ImageView) mDesktopr.findViewById(R.id.desktopr_interest);
		mDesktopr_god= (ImageView) mDesktopr.findViewById(R.id.desktopr_item_god);
		mDesktopr_star= (ImageView) mDesktopr.findViewById(R.id.desktopr_item_star);
		mDesktopr_food= (ImageView) mDesktopr.findViewById(R.id.desktopr_item_food);
		mDesktopr_joke= (ImageView) mDesktopr.findViewById(R.id.desktopr_item_joke);
		mDesktopr_travel= (ImageView) mDesktopr.findViewById(R.id.desktopr_item_travel);
	}

	/**
	 * UI事件监听
	 */
	private void setListener() {
		mDesktopr_interest.setOnClickListener(new MyOnClickAdapter());
		mDesktopr_god.setOnClickListener(new MyOnClickAdapter());
		mDesktopr_star.setOnClickListener(new MyOnClickAdapter());
		mDesktopr_food.setOnClickListener(new MyOnClickAdapter());
		mDesktopr_travel.setOnClickListener(new MyOnClickAdapter());
		mDesktopr_joke.setOnClickListener(new MyOnClickAdapter());
	}

	private void setOnClickListener(MyOnClickAdapter myOnClickAdapter) {
		// TODO Auto-generated method stub
		
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
		return mDesktopr;
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
	
	public class MyOnClickAdapter implements OnClickListener{
		
		public void onClick(View v) {
			if (mOnChangeViewListener != null) {
					switch (v.getId()) {
					case R.id.desktopr_interest:
						mOnChangeViewListener.onChangeView(ViewUtil.Interest);
						break;
					case R.id.desktopr_item_god:
						mOnChangeViewListener.onChangeView(ViewUtil.God);
						break;
					case R.id.desktopr_item_star:
						mOnChangeViewListener.onChangeView(ViewUtil.Star);
						break;
					case R.id.desktopr_item_joke:
						mOnChangeViewListener.onChangeView(ViewUtil.Joke);
						break;
					case R.id.desktopr_item_travel:
						mOnChangeViewListener.onChangeView(ViewUtil.Travel);
						break;
					case R.id.desktopr_item_food:
						mOnChangeViewListener.onChangeView(ViewUtil.Food);
						break;
					default:
						break;
					}}
		}}
}
