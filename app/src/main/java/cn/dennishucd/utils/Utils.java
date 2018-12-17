package cn.dennishucd.utils;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;
import cn.dennishucd.R;
import cn.dennishucd.activity.LoginActivity;

/**
 * 工具类
 *
 * @author lqb
 *
 */
public class Utils {
    /**
     * 根据性别数字获取到性别图片
     * 保存用户的基本信息
     * 设置UI容器
     * @param res:Resources对象
     * @param gender: 0代表女性,1代表男性
     */
    public static List<Activity> ActivityList = new LinkedList<Activity>();
    public static String Avatar;
    public static String Name;
    public static String Sig;
    public static String UID;
    public static int EnergyPoint ;
    
    public static void setEnergyPoint(int point){
        EnergyPoint = EnergyPoint + point;
    }
    
    public static void removeActivity() {
        // TODO Auto-generated method stub
        for(int i=0;i<ActivityList.size();i++){
            Activity activity = ActivityList.get(i);
            if(i==0){
                activity.setResult(Activity.RESULT_OK);
            }
            activity.finish();
        }
    }
    
    
    public static Bitmap getGender(Resources res, int gender) {
        switch (gender) {
            case 0:
                return BitmapFactory.decodeResource(res,
                                                    R.drawable.profile_icon_girl);
            case 1:
                return BitmapFactory.decodeResource(res,
                                                    R.drawable.profile_icon_boy);
            default:
                return BitmapFactory.decodeResource(res,
                                                    R.drawable.profile_icon_boy);
        }
    }
    
    /**
     * 根据性别数字获取到性别名称
     *
     * @param gender
     *            0代表女性,1代表男性
     * @return 性别名称(String 类型)
     */
    public static String getGender(int gender) {
        switch (gender) {
            case 0:
                return "女";
            case 1:
                return "男";
            default:
                return "未知";
        }
    }
    
    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    
    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    
    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
    
    /**
     * 把密度转换为像素
     */
    public static int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }
}
