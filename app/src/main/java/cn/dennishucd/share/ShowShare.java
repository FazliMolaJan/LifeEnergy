
package cn.dennishucd.share;

import java.io.File;
import java.io.FileOutputStream;

import m.framework.ui.widget.slidingmenu.SlidingMenu;
import android.R.menu;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.dennishucd.R;
import cn.dennishucd.cache.LifeApplication;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.Utils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;

public class ShowShare extends Activity   {
	private static View mView;
	private static Context mContext;
	private static String mText;
	private static String mVideo_URL;
	private static String imagePath;

	public ShowShare(Context context,View view) {
		mContext = context;
		mView = view;
	}
	
	public static void setText(String text){
		mText=text;
	}
	
	public static void setVideo_URL(String Video_URL){
		mVideo_URL= LoadUtil.URL+Video_URL;
	}
	
	public static void setImage_Path(String Image_Path){
		imagePath=Image_Path;
	}
	/**ShareSDK集成方法有两种</br>
	 * 1、第一种是引用方式，例如引用onekeyshare项目，onekeyshare项目再引用mainlibs库</br>
	 * 2、第二种是把onekeyshare和mainlibs集成到项目中，本例子就是用第二种方式</br>
	 * 请看“ShareSDK 使用说明文档”，SDK下载目录中 </br>
	 * 或者看网络集成文档 http://wiki.sharesdk.cn/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
	 * 3、混淆时，把sample或者本例子的混淆代码copy过去，在proguard-project.txt文件中
	 *
	 *
	 * 平台配置信息有三种方式：
	 * 1、在我们后台配置各个微博平台的key
	 * 2、在代码中配置各个微博平台的key，http://sharesdk.cn/androidDoc/cn/sharesdk/framework/ShareSDK.html
	 * 3、在配置文件中配置，本例子里面的assets/ShareSDK.conf,
	 */
	public static  void ShareGui(boolean silent, String platform, boolean captureView) {
		final OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.icon, mContext.getString(R.string.app_name));
		//oks.setAddress("12345678901");
		//oks.setTitle("aa");
		//oks.setTitleUrl("http://sharesdk.cn");
		oks.setText("我转发了一条新微视: "+mText+",点击链接即可观看 "+mVideo_URL +" @LifeEnergy");
		if (captureView) {
			oks.setViewToShare(mView);
		} else {
			oks.setImagePath(imagePath);		
			//oks.setImageUrl("http://59.77.134.151:90/Avatar/lqb000.jpg");
		}
		//oks.setUrl("http://www.sharesdk.cn");
		//oks.setFilePath(image_URL);
		//oks.setComment(mContext.getString(R.string.share));
		//oks.setSite(getContext().getString("tt"));
		oks.setSiteUrl("http://imagelife.cn");
		oks.setVenueName("LifeEnergy");
		oks.setVenueDescription("This is a beautiful place!");
		oks.setLatitude(23.056081f);
		oks.setLongitude(113.385708f);
		oks.setSilent(silent);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		// 令编辑页面显示为Dialog模式
		oks.setDialogMode();
		// 在自动授权时可以禁用SSO方式
		oks.disableSSOWhenAuthorize();
		// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
//		oks.setCallback(new OneKeyShareCallback());
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
		// 去除注释，演示在九宫格设置自定义的图标
//		Bitmap logo = BitmapFactory.decodeResource(menu.getResources(), R.drawable.ic_launcher);
//		String label = menu.getResources().getString(R.string.app_name);
//		OnClickListener listener = new OnClickListener() {
//			public void onClick(View v) {
//				String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
//				Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
//				oks.finish();
//			}
//		};
//		oks.setCustomerLogo(logo, label, listener);
		// 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
//		oks.addHiddenPlatform(SinaWeibo.NAME);
//		oks.addHiddenPlatform(TencentWeibo.NAME);
		// 为EditPage设置一个背景的View
		oks.setEditPageBackground(mView);
		oks.show(mContext);
	}




	





}
