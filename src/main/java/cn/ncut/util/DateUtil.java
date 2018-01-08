package cn.ncut.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 
 * 
 * <p>
 * Title:DateUtil
 * </p>
 * <p>
 * Description:日期处理
 * </p>
 * <p>
 * Company:ncut
 * </p>
 * 
 * @author lph
 * @date 2016-12-6上午10:13:50
 * 
 */
public class DateUtil {

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
			"yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
			"yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getSdfTimes() {
		return sdfTimes.format(new Date());
	}

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author fh
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}
	
	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回长整形正数 否则返回付整形或者0)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author fh
	 */
	public static long compareToDate(String s, String e) {
		
		return (DateUtil.fomatDate(s).getTime()-DateUtil.fomatDate(e).getTime());
	}

	
	
	
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// long aa=0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	
	/**
	 * 
	 * <p>
	 * Tittle:ChineseToNum
	 * </p>
	 * <p>
	 * Description:一到七转换成0-6
	 * </p>
	 * 
	 * @param Chinese
	 * @return
	 * @author lph
	 * @date 2016-12-22下午7:31:44
	 */
	public static int ChineseToNum(String Chinese) {
		int num;
		switch (Chinese) {
		case "一":
			num = 1;
			break;
		case "二":
			num = 2;
			break;
		case "三":
			num = 3;
			break;
		case "四":
			num = 4;
			break;
		case "五":
			num = 5;
			break;
		case "六":
			num = 6;
			break;
		default:
			num = 0;
			break;
		}
		return num;
	}

	/**
	 * 
	 * <p>
	 * Tittle:getWeekOfYear
	 * </p>
	 * <p>
	 * Description:通过日期(yyyy-mm-dd)获得是该年第几周的int型数字,不足一周的算下一年第一周
	 * </p>
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 * @author lph
	 * @date 2016-12-23上午10:46:53
	 */
	public static int getWeekOfYear(String s) throws Exception {

		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 
	 *<p>Tittle:getYearAndWeekOfYear</p>
	 *<p>Description:根据一个yyyy-mm-dd判断是哪一年的第几周,不足一周的算下一年第一周
	 *</p>
	 *@param s
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2016-12-30下午5:01:57
	 */
	
	public static String getYearAndWeekOfYear(String s) throws Exception{

		int week_of_year = DateUtil.getWeekOfYear(s);
		int month = Integer.parseInt(s.substring(5, 7));
		int year = Integer.parseInt(s.substring(0, 4));
		if(week_of_year==1&&month==12){
			year++;
		}
		return (year+"-"+week_of_year);
	}

	/**
	 * 
	 *<p>Tittle:getEveryDay</p>
	 *<p>Description:通过年月日以及差值获得时间</p>
	 *@param s
	 *@param i
	 *@return
	 *@throws ParseException
	 *@author lph
	 *@date 2016-12-28下午9:25:55
	 */
	public static String getEveryDay(String s, int i) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d = df.parse(s);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, i);
		return df.format(cal.getTime());
	}
	
	public static Boolean formerOrLatter(String Date) throws Exception{
		Date nowdate=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date d = sdf.parse(Date);
		return d.before(nowdate);
	}
	/**
	 * 
	 *<p>Tittle:isMidTime</p>
	 *<p>Description:判断当前时间是否在两个时间之间</p>
	 *@param s1
	 *@param s2
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2017-1-10下午3:38:49
	 */
	public static boolean isMidTime(String s1 , String s2) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = new Date();
		return df.parse(s1).getTime()<nowDate.getTime()&&nowDate.getTime()<df.parse(s2).getTime();
	}
	/**
	 * 把一个String的 YYYY-MM-DD转换为毫秒值
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Long convertMTime(String date) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date).getTime();
	}
	
	/**
	 * 把一个String的 YYYY-MM-DD hh:mm:dd的时间变成YYYY-MM-DD 23:59:59
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String caculateGuoqiTime(String servicetime) throws Exception{
		String date = servicetime.substring(0, 10);
		
		return date + " 23:59:59";
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(caculateGuoqiTime("2016-09-01 10:02:04"));
	}

	public static String getDayBegDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return dateToStrLong(calendar.getTime());
	}
	
	public static String getDayEndDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return dateToStrLong(calendar.getTime());
	}
	
	public static String dateToStrLong(Date dateDate) {
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate=sdf.format(dateDate);
			return sDate;
		}

}
