package cn.dennishucd.utils;

import java.util.*;
import java.text.*;
import java.util.Calendar;

import android.content.Context;
import android.text.format.DateUtils;


public class TimeUtil {

 /**
  * 
  * @returnyyyy-MM-dd HH:mm:ss
  */
 public static String getStringDate() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateString = formatter.format(currentTime);
  return dateString;
 }


 /**
  * ��ȡ����ʱ��
  * 
  * @return ʽyyyy-MM-dd
  */
 public static String getStringDateShort() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  String dateString = formatter.format(currentTime);
  return dateString;
 }


 /**
 HH:mm:ss
  * 
  * @return
  */
 public static String getTimeShort() {
  SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
  Date currentTime = new Date();
  String dateString = formatter.format(currentTime);
  return dateString;
 }
 /**
  */
 public static String getHour(String dateString) {
  String hour;
  hour = dateString.substring(11, 13);
  return hour;
 }

 /**
  * 
  * @return
  */
 public static String getSecond(String dateString) {
  String second;
  second = dateString.substring(14, 16);
  return second;
 }
 /**
  * 
  * @return
  */
 public static String getMinute(String dateString){
	 String minute;
	 minute=dateString.substring(17,19);
	 return minute;
 }
 
 public static String getHomeTime(String dateString){
	 String time = dateString.substring(5,7)+"月"+dateString.substring(8,10)+
			 "日 "+dateString.substring(11,16);
	 return time;
 }


public static String getVisitorTime(String dateString) {
	// TODO Auto-generated method stub
	 String time =dateString.substring(8,10)+"日 "+dateString.substring(11,16);
	 return time;
}
/**
 * 转换long型日期格式
 * 
 * @param context
 * @param date
 * @return
 */
public static String formatDate(Context context, long date) {
	int format_flags = DateUtils.FORMAT_NO_NOON_MIDNIGHT
			| DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_CAP_AMPM
			| DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_DATE
			| DateUtils.FORMAT_SHOW_TIME;
	return DateUtils.formatDateTime(context, date, format_flags);
}

/**
 * 转换long型日期格式
 * 
 * @param date
 * @return
 */
public static String formatDate(long date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	return format.format(new Date(date));
}

/**
 * 获取当前的时间
 * 
 * @param context
 * @return
 */
public static String getTime(Context context) {
	return formatDate(context, System.currentTimeMillis());
}

/**
 * 获取当前的时间
 * 
 * @return
 */
public static String getTime() {
	return formatDate(System.currentTimeMillis());
}

}