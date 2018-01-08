package cn.ncut.util.wechat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间调整工具类
 * */
public class TimeAdjust {
	/**
	 * @param day 需要调整的时间字符串
	 * @param x 分钟数
	 * @param pattern 时间格式
	 * @return 按指定分钟数延后返回的时间字符串
	 * */
	public static String addDateMinut(String day, int x,String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, x);
		date = cal.getTime();
		cal = null;
		return format.format(date);
	}
}
