package cn.ncut.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 
 * 日期工具类
 * 
 * @author xiongyuanming
 * @version premas.1.0
 * @see
 * @since 2014年7月11日
 */
public final class DateUtil2 {
	private static Logger log = Logger.getLogger(DateUtil2.class);

	private DateUtil2() {

	}

	/** 时间格式-yyyy-MM-dd HH:mm:ss */
	public final static String FORMMAT_1 = "yyyy-MM-dd HH:mm:ss";
	/** 时间格式-yyyyMMddHHmmss */
	public final static String FORMMAT_2 = "yyyyMMddHHmmss";
	public final static String FORMMAT_2_1 = "000000";
	public final static String FORMMAT_2_2 = "235959";
	/** 时间格式-yyyy-MM-dd */
	public final static String FORMMAT_3 = "yyyy-MM-dd";

	/**
	 * 将d转化为 yyyy-MM-dd HH:mm:ss时间格式
	 * 
	 * @param d
	 *            Date
	 * @return
	 */
	public static String formatDate1(Date d) {
		return formatDate(d, FORMMAT_1);
	}

	/**
	 * 将d转化为 yyyyMMddHHmmss时间格式
	 * 
	 * @param d
	 *            Date
	 * @return
	 */
	public static String formatDate2(Date d) {
		return formatDate(d, FORMMAT_2);
	}

	/**
	 * 将d转化为 yyyy-MM-dd时间格式
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDate3(Date d) {
		return formatDate(d, FORMMAT_3);
	}

	/**
	 * 将时间延后minute分钟
	 * 
	 * @param d
	 *            Date
	 * @param minute
	 *            int
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static void delayMinute(Date d, int minute) {
		d.setMinutes(d.getMinutes() + minute);
	}

	/**
	 * 将时间延后day天
	 * 
	 * @param d
	 * @param day
	 */
	@SuppressWarnings("deprecation")
	public static void delayDate(Date d, int day) {
		d.setDate(d.getDate() + day);
	}

	/**
	 * 将时间按照指定格式进行格式化
	 * 
	 * @param d
	 * @param formmatStr
	 * @return
	 */
	public static String formatDate(Date d, String formmatStr) {
		if (d == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formmatStr);
		try {
			return sdf.format(d);
		} catch (Exception e) {
			log.error("格式时间出错date=" + d + ".formmatStr=" + formmatStr, e);
			return "";
		}
	}
	// 将date时间转换为时间戳int型。
		public static int dateConverTime(){
			try {
			Date date=new Date();
		 	SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    String time= df.format(date); 
		    Date date1 = df.parse(time);
			return (int) (date1.getTime()/1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}
		
		// 将时间戳int型转换为String类型。
		public static String timeConverString(int Time){
		try {
			SimpleDateFormat fm =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			long time=Long.valueOf(Time+"000"); 
			String d = fm.format(time);
			return d;
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		}
		}
		 
		public static Date strDateToDate(String  strDate) throws ParseException{  
			SimpleDateFormat fm =  new SimpleDateFormat("yyyy-MM-dd");  
			return fm.parse(strDate);
		}  
 
}
