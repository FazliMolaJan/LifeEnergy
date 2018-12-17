package cn.dennishucd.cache;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dennishucd.R;
import cn.dennishucd.activity.CheckInActivity;
import cn.dennishucd.activity.PhoneAlbumActivity;
import cn.dennishucd.activity.SetUpActivity;
import cn.dennishucd.activity.VideoRecordActivity;
import cn.dennishucd.activity.VoiceActivity;
import cn.dennishucd.anim.UgcAnimations;
import cn.dennishucd.utils.ActivityForResultUtil;

/**
 * 用于初始化彩虹path菜单
 * 
 * @author lqb
 * 
 */
public class UgcPathRActivity extends Activity {
	private Context mContext;
	private Activity mActivity;
	private LifeApplication mKXApplication;
	private boolean mUgcIsShowing = false;
	private View mUgcView;
	private ImageView mUgcVoice;
	private ImageView mUgcPhoto;
	private ImageView mUgcSetting;
	private ImageView mUgcLbs;
	private RelativeLayout mUgcLayout;
	private ImageView mUgc;
	private ImageView mUgcBg;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	protected void UgcView(Context context, Activity activity, LifeApplication application) {
		mContext = context;
		mActivity = activity;
		mKXApplication = application;
	}
	/**
	 * 初始化Path菜单
	 */
	protected void initUgcView (View view){
		mUgcView = view;
		mUgcLayout = (RelativeLayout) mUgcView.findViewById(R.id.ugc_layoutr);
		mUgc = (ImageView) mUgcView.findViewById(R.id.ugcr);
		mUgcBg = (ImageView) mUgcView.findViewById(R.id.ugc_bgr);
		mUgcVoice = (ImageView) mUgcView. findViewById(R.id.ugc_voicer);
		mUgcPhoto = (ImageView) mUgcView. findViewById(R.id.ugc_photor);
		mUgcSetting = (ImageView) mUgcView.findViewById(R.id.ugc_settingr);
		mUgcLbs = (ImageView) mUgcView.findViewById(R.id.ugc_lbsr);
		setUgcListener();
	}

	/**
	 * 获取Path菜单显示状态
	 * 
	 * @return 显示状态
	 */
	public boolean getUgcIsShowing() {
		return mUgcIsShowing;
	}

	/**
	 * 关闭Path菜单
	 */
	public void closeUgc() {
		mUgcIsShowing = false;
		UgcAnimations.startCloseAnimation(mUgcLayout, mUgcBg, mUgc, 300);
	}

	/**
	 * 显示Path菜单
	 */
	public void showUgc() {
		if (mUgcView != null) {
			mUgcView.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 关闭Path菜单
	 */
	public void dismissUgc() {
		if (mUgcView != null) {
			mUgcView.setVisibility(View.GONE);
		}
	}

	
	private void setUgcListener() {
		// Path监听
				mUgcView.setOnTouchListener(new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {
						// 判断是否已经显示,显示则关闭并隐藏
						if (mUgcIsShowing) {
							mUgcIsShowing = false;
							UgcAnimations.startCloseAnimation(mUgcLayout, mUgcBg, mUgc,
									300);
							return true;
						}
						return false;
					}
				});
				// Path监听
				mUgc.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// 判断是否显示,已经显示则隐藏,否则则显示
						mUgcIsShowing = !mUgcIsShowing;
						if (mUgcIsShowing) {
							UgcAnimations.startOpenAnimation(mUgcLayout, mUgcBg, mUgc,
									300);
						} else {
							UgcAnimations.startCloseAnimation(mUgcLayout, mUgcBg, mUgc,
									300);
						}
					}
				});
				// Path 语音按钮监听
				mUgcVoice.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Animation anim = UgcAnimations.clickAnimation(400);
						anim.setAnimationListener(new AnimationListener() {

							public void onAnimationStart(Animation animation) {

							}

							public void onAnimationRepeat(Animation animation) {

							}

							public void onAnimationEnd(Animation animation) {
								mContext.startActivity(new Intent(mContext,
										VoiceActivity.class));
								closeUgc();
							}
						});
						mUgcVoice.startAnimation(anim);
					}
				});
				// Path 拍照按钮监听
				mUgcPhoto.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Animation anim = UgcAnimations.clickAnimation(400);
						anim.setAnimationListener(new AnimationListener() {

							public void onAnimationStart(Animation animation) {

							}

							public void onAnimationRepeat(Animation animation) {

							}

							public void onAnimationEnd(Animation animation) {
								PhotoDialog();
								closeUgc();
							}
						});
						mUgcPhoto.startAnimation(anim);
					}
				});
				
				// Path 签到按钮监听
				mUgcLbs.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Animation anim = UgcAnimations.clickAnimation(400);
						anim.setAnimationListener(new AnimationListener() {

							public void onAnimationStart(Animation animation) {

							}

							public void onAnimationRepeat(Animation animation) {

							}

							public void onAnimationEnd(Animation animation) {
								mContext.startActivity(new Intent(mContext,
										CheckInActivity.class));
								closeUgc();
							}
						});
						mUgcLbs.startAnimation(anim);
					}
				});
				// Path 设置按钮监听
				mUgcSetting.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Animation anim = UgcAnimations.clickAnimation(400);
						anim.setAnimationListener(new AnimationListener() {

							public void onAnimationStart(Animation animation) {

							}

							public void onAnimationRepeat(Animation animation) {

							}

							public void onAnimationEnd(Animation animation) {
								mContext.startActivity(new Intent(mContext,
										SetUpActivity.class));
								closeUgc();
							}
						});
						mUgcSetting.startAnimation(anim);
					}
				});
	}
	
	
    private  void PhotoDialog() {
		
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("上传照片至LifeEnergy");
		builder.setItems(new String[] { "拍照上传", "上传手机中的照片" ,"录制视频上传"},
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						switch (which) {
						case 0:
							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							File dir = new File("/sdcard/LifeEnergy/Camera/");
							if (!dir.exists()) {
								dir.mkdirs();
							}
							mKXApplication.mUploadPhotoPath = "/sdcard/LifeEnergy/Camera/"
									+ UUID.randomUUID().toString()+".jpg";
							File file = new File(
									mKXApplication.mUploadPhotoPath);
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {

								}
							}
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(file));
							mActivity
							.startActivityForResult(
											intent,
											ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA);
							break;

						case 1:
							mContext.startActivity(new Intent(mContext,
									PhoneAlbumActivity.class));
							break;
						case 2:
							mActivity.startActivityForResult(
									new Intent(mContext,VideoRecordActivity.class), 
									ActivityForResultUtil.REQUESTCODE_UPLOADVIDEO_CAMERA);
							break;
						}
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}
}
