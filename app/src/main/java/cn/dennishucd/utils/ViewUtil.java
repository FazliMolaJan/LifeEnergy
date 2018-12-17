package cn.dennishucd.utils;

/**
 * Desktop View工具类
 * 
 * @author lqb
 * 
 */
public class ViewUtil {
	// 用户界面
	public static final int USER = -1;
	// 首页界面
	public static final int HOME = 0;
	// 消息界面
	public static final int MESSAGES = 1;
	// 好友界面
	public static final int FRIENDS = 2;
	// 能量界面
    public static final int ENERGY = 3;
	// 照片界面
	public static final int PHOTO = 4;
	// 视频界面
	public static final int ViDEO = 5;
	// 礼物界面
	public static final int GIFTS = 6;
	//女神选框
	public static final int God = 7;
	//明星选框
	public static final int Star = 8;
	//搞笑选框
	public static final int  Joke= 9;
	//旅游选框
	public static final int Travel = 10;
	//美食选框
	public static final int  Food= 11;
	//兴趣选框
	public static final int  Interest= 12;
	
	//获得小组名
	public static String getTagName(String tag){
		String tagname=null;
	    if(tag.equals("God")){
	    	tagname="女神";
	    }
	    else if(tag.equals("Star")){
	    	tagname="明星制造";
	    }
	    else if(tag.equals("Food")){
	    	tagname="美食";
	    }
	    else if(tag.equals("Travel")){
	    	tagname="旅游";
	    }
	    else if(tag.equals("Joke")){
	    	tagname="搞笑";
	    }
		return tagname;
	}
}
