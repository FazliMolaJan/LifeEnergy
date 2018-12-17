package cn.dennishucd.cache;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import cn.dennishucd.R;
import cn.dennishucd.result.ChatResult;
import cn.dennishucd.result.DiaryResult;
import cn.dennishucd.result.FriendInfoResult;
import cn.dennishucd.result.FriendsBirthdayResult;
import cn.dennishucd.result.FriendsResult;
import cn.dennishucd.result.GiftResult;
import cn.dennishucd.result.HomeFriendsResult;
import cn.dennishucd.result.GridViewResult;
import cn.dennishucd.result.LocationResult;
import cn.dennishucd.result.PhotoResult;
import cn.dennishucd.result.PublicPageResult;
import cn.dennishucd.result.RecommendResult;
import cn.dennishucd.result.StatusResult;
import cn.dennishucd.result.ViewedResult;
import cn.dennishucd.result.VisitorsResult;
import cn.dennishucd.utils.LoadUtil;
import cn.dennishucd.utils.Options;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.Utils;

/**
 * 存放共有的数据
 * 
 * @author lqb
 * 
 */
public class LifeApplication extends Application {
    
	public DisplayImageOptions options;
	public ImageLoader imageLoader;
	/**
	 * 默认壁纸
	 */
	public Bitmap mDefault_Wallpager;
	/**
	 * 默认标题壁纸
	 */
	public Bitmap mDefault_TitleWallpager;
	/**
	 * 默认头像
	 */
	public Bitmap mDefault_Avatar;
	/**
	 * 默认照片
	 */
	public Bitmap mDefault_Photo;
	/**
	 * 壁纸缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mWallpagersCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 壁纸名称
	 */
	public String[] mWallpagersName;
	/**
	 * 标题壁纸缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mTitleWallpagersCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 标题壁纸名称
	 */
	public String[] mTitleWallpagersName;
	/**
	 * 当前壁纸编号
	 */
	public int mWallpagerPosition = 0;
	/**
	 * 圆形头像缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 默认头像缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mDefaultAvatarCache = new HashMap<String, SoftReference<Bitmap>>();

	/**
	 * 公共主页头像缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mPublicPageAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 公共主页头像名称
	 */
	public String[] mPublicPageAvatars;
	/**
	 * 表情
	 */
	public int[] mFaces = { R.drawable.face_0, R.drawable.face_1,
			R.drawable.face_2, R.drawable.face_3, R.drawable.face_4,
			R.drawable.face_5, R.drawable.face_6, R.drawable.face_7,
			R.drawable.face_8, R.drawable.face_9, R.drawable.face_10,
			R.drawable.face_11, R.drawable.face_12, R.drawable.face_13,
			R.drawable.face_14, R.drawable.face_15, R.drawable.face_16,
			R.drawable.face_17, R.drawable.face_18, R.drawable.face_19,
			R.drawable.face_20, R.drawable.face_21, R.drawable.face_22,
			R.drawable.face_23, R.drawable.face_24, R.drawable.face_25,
			R.drawable.face_26, R.drawable.face_27, R.drawable.face_28,
			R.drawable.face_29, R.drawable.face_30, R.drawable.face_31,
			R.drawable.face_32, R.drawable.face_33, R.drawable.face_34,
			R.drawable.face_35, R.drawable.face_36, R.drawable.face_37,
			R.drawable.face_38, R.drawable.face_39, R.drawable.face_40,
			R.drawable.face_41, R.drawable.face_42, R.drawable.face_43,
			R.drawable.face_44, R.drawable.face_45, R.drawable.face_46,
			R.drawable.face_47, R.drawable.face_48, R.drawable.face_49,
			R.drawable.face_50, R.drawable.face_51, R.drawable.face_52,
			R.drawable.face_53, R.drawable.face_54, R.drawable.face_55,
			R.drawable.face_56, R.drawable.face_57, R.drawable.face_58,
			R.drawable.face_59, R.drawable.face_60, R.drawable.face_61,
			R.drawable.face_62, R.drawable.face_63, R.drawable.face_64,
			R.drawable.face_65, R.drawable.face_66, R.drawable.face_67,
			R.drawable.face_68, R.drawable.face_69, R.drawable.face_70,
			R.drawable.face_71, R.drawable.face_72, R.drawable.face_73,
			R.drawable.face_74, R.drawable.face_75, R.drawable.face_76,
			R.drawable.face_77, R.drawable.face_78, R.drawable.face_79,
			R.drawable.face_80, R.drawable.face_81, R.drawable.face_82,
			R.drawable.face_83, R.drawable.face_84, R.drawable.face_85,
			R.drawable.face_86, R.drawable.face_87, R.drawable.face_88,
			R.drawable.face_89, R.drawable.face_90, R.drawable.face_91,
			R.drawable.face_92, R.drawable.face_93, R.drawable.face_94,
			R.drawable.face_95, R.drawable.face_96, R.drawable.face_97,
			R.drawable.face_98, R.drawable.face_99, R.drawable.face_100,
			R.drawable.face_101, R.drawable.face_102, R.drawable.face_103,
			R.drawable.face_104, R.drawable.face_105, R.drawable.face_106,
			R.drawable.face_107, R.drawable.face_108, R.drawable.face_109,
			R.drawable.face_110 };
	/**
	 * 表情名称
	 */
	public List<String> mFacesText = new ArrayList<String>();
	/**
	 * 表情缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mFaceCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 照片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mPhotoCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 照片名称
	 */
	public String[] mPhotosName;
	/**
	 * 主页图片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mHomeCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 主页热门图片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mHomeHotCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 小组图片缓存
	 */
	public HashMap<String,HashMap<String, SoftReference<Bitmap>>> mGroupCache = new HashMap<String,HashMap<String, SoftReference<Bitmap>>>();
	/**
	 * 手机SD卡图片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mPhoneAlbumCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 手机SD卡图片的路径
	 */
	public Map<String, List<Map<String, String>>> mPhoneAlbum = new HashMap<String, List<Map<String, String>>>();

	/**
	 * 首页动态
	 */
	public static List<HomeFriendsResult> mMyHomeResults = new ArrayList<HomeFriendsResult>();
	/**
	 * 首页动态
	 */
	public static List<GridViewResult> mMyHomeHotResults = new ArrayList<GridViewResult>();
	/**
	 * 小组动态
	 */
	public static HashMap<String,List<GridViewResult>> mGroupResults = new HashMap<String,List<GridViewResult>>();
	/**
	 * 当前用户的资料数据
	 */
	public FriendInfoResult mMyInfoResult;

	/**
	 * 当前用户的来访数据
	 */
	public List<VisitorsResult> mMyVisitorsResults = new ArrayList<VisitorsResult>();
	/**
	 * 当前用户的状态数据
	 */
	public List<StatusResult> mMyStatusResults = new ArrayList<StatusResult>();
	/**
	 * 当前用户的相册数据
	 */
	public List<PhotoResult> mMyPhotoResults = new ArrayList<PhotoResult>();
	/**
	 * 当前用户的日记数据
	 */
	public List<DiaryResult> mMyDiaryResults = new ArrayList<DiaryResult>();
	/**
	 * 当前用户的好友数据
	 */
	public List<FriendsResult> mMyFriendsResults = new ArrayList<FriendsResult>();
	/**
	 * 当前用户的好友根据姓名首字母分组
	 */
	public Map<String, List<FriendsResult>> mMyFriendsGroupByFirstName = new HashMap<String, List<FriendsResult>>();
	/**
	 * 当前用户的好友的姓名首字母在列表中的位置
	 */
	public Map<String, Integer> mMyFriendsFirstNamePosition = new HashMap<String, Integer>();
	/**
	 * 当前用户的好友的姓名的首字母数据
	 */
	public List<String> mMyFriendsFirstName = new ArrayList<String>();
	/**
	 * 当前用户的好友的姓名的首字母的在列表中的位置
	 */
	public List<Integer> mMyFriendsPosition = new ArrayList<Integer>();

	/**
	 * 当前用户的公共主页数据
	 */
	public List<PublicPageResult> mMyPublicPageResults = new ArrayList<PublicPageResult>();
	/**
	 * 当前用户的公共主页根据姓名首字母分组
	 */
	public Map<String, List<PublicPageResult>> mMyPublicPageGroupByFirstName = new HashMap<String, List<PublicPageResult>>();

	/**
	 * 当前用户的好友转帖数据
	 */
	public List<ViewedResult> mMyViewedResults = new ArrayList<ViewedResult>();

	/**
	 * 当前用户的热门转帖数据
	 */
	public List<ViewedResult> mViewedHotResults = new ArrayList<ViewedResult>();

	/**
	 * 当前用户的最近过生日好友数据
	 */
	public List<FriendsBirthdayResult> mMyFriendsBirthdayResults = new ArrayList<FriendsBirthdayResult>();

	/**
	 * 当前用户的推荐官方模块数据
	 */
	public List<RecommendResult> mMyRecommendOfficialResults = new ArrayList<RecommendResult>();
	/**
	 * 当前用户的推荐应用下载数据
	 */
	public List<RecommendResult> mMyRecommendAppDownLoadResults = new ArrayList<RecommendResult>();

	public List<LocationResult> mMyLocationResults = new ArrayList<LocationResult>();

	/**
	 * 所有好友的资料数据 (Key对应该好友的uid)
	 */
	public Map<String, FriendInfoResult> mFriendInfoResults = new HashMap<String, FriendInfoResult>();

	/**
	 * 所有好友的来访数据 (Key对应该好友的uid)
	 */
	public Map<String, List<VisitorsResult>> mFriendVisitorsResults = new HashMap<String, List<VisitorsResult>>();

	/**
	 * 所有好友的状态数据 (Key对应该好友的uid)
	 */
	public Map<String, List<StatusResult>> mFriendStatusResults = new HashMap<String, List<StatusResult>>();

	/**
	 * 所有好友的相册数据 (Key对应该好友的uid)
	 */
	public Map<String, List<PhotoResult>> mFriendPhotoResults = new HashMap<String, List<PhotoResult>>();

	/**
	 * 所有好友的日记数据 (Key对应该好友的uid)
	 */
	public Map<String, List<DiaryResult>> mFriendDiaryResults = new HashMap<String, List<DiaryResult>>();

	/**
	 * 所有好友的好友数据 (Key对应该好友的uid)
	 */
	public Map<String, List<FriendsResult>> mFriendFriendsResults = new HashMap<String, List<FriendsResult>>();

	/**
	 * 存放聊天记录
	 */
	public List<ChatResult> mChatResults = new ArrayList<ChatResult>();

	/**
	 * 存放赠送礼物的好友
	 */
	public Map<String, Map<String, String>> mGiftFriendsList = new HashMap<String, Map<String, String>>();

	/**
	 * 存放礼物图片
	 */
	public HashMap<String, SoftReference<Bitmap>> mGiftsCache = new HashMap<String, SoftReference<Bitmap>>();

	public String[] mGiftsName;
	/**
	 * 存放礼物的具体信息
	 */
	public List<GiftResult> mGiftResults = new ArrayList<GiftResult>();

	/**
	 * 存放存为草稿的日记标题
	 */
	public String mDraft_DiaryTitle;
	/**
	 * 存放存为草稿的日记内容
	 */
	public String mDraft_DiaryContent;

	/**
	 * 存放拍照上传的照片路径
	 */
	public String mUploadPhotoPath;
	/**
	 * 存放本地选取的照片集合
	 */
	public List<Map<String, String>> mAlbumList = new ArrayList<Map<String, String>>();

	public void onCreate() {
		super.onCreate();
		/**
		 * 初始化默认数据
		 */
		mDefault_Wallpager = BitmapFactory.decodeResource(getResources(),
				R.drawable.login_bg);
		mDefault_TitleWallpager = BitmapFactory.decodeResource(getResources(),
				R.drawable.login_bg);
		mDefault_Photo = BitmapFactory.decodeResource(getResources(),
				R.drawable.photo);
		//mDefault_Avatar = PhotoUtil.toRoundCorner(
			//	BitmapFactory.decodeResource(getResources(), R.drawable.head),	15);
		mDefault_Avatar = BitmapFactory.decodeResource(getResources(), R.drawable.head);
		mWallpagerPosition = (int) (Math.random() * 12);
		/**
		 * 初始化所有的数据信息
		 */
		try {
			mWallpagersName = getAssets().list("wallpaper");
			//mTitleWallpagersName = getAssets().list("title_wallpager");
			mTitleWallpagersName = getAssets().list("wallpager");
			mPublicPageAvatars = getAssets().list("publicpage_avatar");
			mPhotosName = getAssets().list("photo");
			mGiftsName = getAssets().list("gifts");
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * 初始化表情名称
		 */
		for (int i = 0; i < mFaces.length; i++) {
			mFacesText.add("[face_" + i + "]");
		}
	}

	/**
	 * 根据壁纸编号获取壁纸
	 */
	public Bitmap getWallpager(int position) {
		try {
			String wallpagerName = mWallpagersName[position];
			Bitmap bitmap = null;
			if (mWallpagersCache.containsKey(wallpagerName)) {
				SoftReference<Bitmap> reference = mWallpagersCache
						.get(wallpagerName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(getAssets().open(
					"wallpaper/" + wallpagerName));
			mWallpagersCache.put(wallpagerName, new SoftReference<Bitmap>(
					bitmap));
			return bitmap;

		} catch (Exception e) {
			return mDefault_Wallpager;
		}
	}

	/**
	 * 根据壁纸编号获取标题壁纸
	 */
	public Bitmap getTitleWallpager(int position) {
		try {
			String titleWallpagerName = mTitleWallpagersName[position];
			Bitmap bitmap = null;
			if (mTitleWallpagersCache.containsKey(titleWallpagerName)) {
				SoftReference<Bitmap> reference = mTitleWallpagersCache
						.get(titleWallpagerName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			//bitmap = BitmapFactory.decodeStream(getAssets().open(
				//	"title_wallpager/" + titleWallpagerName));
			bitmap = BitmapFactory.decodeStream(getAssets().open(
			  "wallpager/" + titleWallpagerName));
			mTitleWallpagersCache.put(titleWallpagerName,
					new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return mDefault_TitleWallpager;
		}
	}

	/**
	 * 根据图片名称获取热门动态主页图片
	 */
	public Bitmap getHomeHot(String photo) {
		Bitmap bitmap = null;
		if (mHomeHotCache.containsKey(photo)) {
			SoftReference<Bitmap> reference = mHomeHotCache.get(photo);
			bitmap = reference.get();
		}
		else{
			mHomeHotCache.put(photo, new SoftReference<Bitmap>(bitmap));
		}
		return bitmap;
	}
	
	/**
	 * 根据图片名称获取好友动态主页图片
	 */
	public Bitmap getHome(String photo) {
		Bitmap bitmap = null;
		if (mHomeCache.containsKey(photo)) {
			SoftReference<Bitmap> reference = mHomeCache.get(photo);
			bitmap = reference.get();
		}
		else{
			mHomeCache.put(photo, new SoftReference<Bitmap>(bitmap));
		}
		return bitmap;
	}
	/**
	 * 根据图片名称获取兴趣小组动态图片
	 */
	public Bitmap getGroup(String photo,String tag) {
		try {
			String homeName=photo;
			Bitmap bitmap = null;
			if(!mGroupCache.containsKey(tag)){
				HashMap<String,SoftReference<Bitmap>> group= new HashMap<String,SoftReference<Bitmap>>();
				mGroupCache.put(tag, group);
			}
			if (mGroupCache.get(tag).containsKey(homeName)) {
				SoftReference<Bitmap> reference = mGroupCache.get(tag).get(homeName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
				}
			InputStream inStream =LoadUtil.HttpGetPhoto(homeName,"group");
			bitmap = BitmapFactory.decodeStream(inStream);
			mGroupCache.get(tag).put(homeName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return BitmapFactory.decodeResource(getResources(),
					R.drawable.picture_default_fg);
		}
	}

	/**
	 * 根据编号获取用户圆形头像
	 */
	public Bitmap getAvatar(String avatar) {
		Bitmap bitmap = null;
		if (mAvatarCache.containsKey(avatar)) {
			SoftReference<Bitmap> reference = mAvatarCache.get(avatar);
			bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}
		//else{
			//mAvatarCache.put(avatar, new SoftReference<Bitmap>(bitmap));
		//}
		//bitmap = PhotoUtil.toRoundCorner(
		//		BitmapFactory.decodeStream(inStream), 15);
		return  bitmap;
	}

	/**
	 * 获取用户默认头像
	 */
	public Bitmap getDefaultAvatar() {
		return mDefault_Avatar;
	}

	/**
	 * 根据编号获取公共主页头像
	 */
	public Bitmap getPublicPageAvatar(int position) {
		try {
			String avatarName = mPublicPageAvatars[position];
			Bitmap bitmap = null;
			if (mPublicPageAvatarCache.containsKey(avatarName)) {
				SoftReference<Bitmap> reference = mPublicPageAvatarCache
						.get(avatarName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = PhotoUtil.toRoundCorner(
					BitmapFactory.decodeStream(getAssets().open(
							"publicpage_avatar/" + avatarName)), 15);
			mPublicPageAvatarCache.put(avatarName, new SoftReference<Bitmap>(
					bitmap));
			return bitmap;
		} catch (Exception e) {
			return mDefault_Avatar;
		}
	}

	/**
	 * 根据编号获取照片
	 */
	public Bitmap getPhoto(String path) {
		try {
			String photosName = path;
			Bitmap bitmap = null;
			if (mPhotoCache.containsKey(photosName)) {
				SoftReference<Bitmap> reference = mPhotoCache.get(photosName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(getAssets().open(
					"photo/" + photosName));
			mPhotoCache.put(photosName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return mDefault_Photo;
		}
	}

	/**
	 * 根据编号获取表情图片
	 */
	public Bitmap getFaceBitmap(int position) {
		try {
			String faceName = mFacesText.get(position);
			Bitmap bitmap = null;
			if (mFaceCache.containsKey(faceName)) {
				SoftReference<Bitmap> reference = mFaceCache.get(faceName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeResource(getResources(),
					mFaces[position]);
			mFaceCache.put(faceName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据礼物编号获取礼物图片
	 */
	public Bitmap getGift(String gid) {
		try {
			Bitmap bitmap = null;
			if (mGiftsCache.containsKey(gid)) {
				SoftReference<Bitmap> reference = mGiftsCache.get(gid);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(getAssets()
					.open("gifts/" + gid));
			mGiftsCache.put(gid, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return BitmapFactory.decodeResource(getResources(),
					R.drawable.gifts_default_01);
		}
	}

	/**
	 * 根据地址获取手机SD卡图片
	 */
	public Bitmap getPhoneAlbum(String path) {
		Bitmap bitmap = null;
		if (mPhoneAlbumCache.containsKey(path)) {
			SoftReference<Bitmap> reference = mPhoneAlbumCache.get(path);
			bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}
		bitmap = BitmapFactory.decodeFile(path);
		mPhoneAlbumCache.put(path, new SoftReference<Bitmap>(bitmap));
		return bitmap;
	}
}
