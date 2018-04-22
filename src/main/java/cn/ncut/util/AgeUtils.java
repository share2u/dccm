package cn.ncut.util;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgeUtils {
	// 根据年月日计算年龄,birthTimeString:"1994-11-14"  
	
    public static int getAgeFromBirthTime(String birthTimeString) { 
    	int selectYear;
		int selectMonth;
		int selectDay;
    	if(exec(birthTimeString)){
        // 先截取到字符串中的年、月、日  
        String strs[] = birthTimeString.trim().split("-");  
         selectYear = Integer.parseInt(strs[0]);  
        selectMonth = Integer.parseInt(strs[1]);  
         selectDay = Integer.parseInt(strs[2]);  
    	}else{
    		selectYear = 2000;  
            selectMonth = 1;  
             selectDay = 1;
    	}
    		
        // 得到当前时间的年、月、日  
        Calendar cal = Calendar.getInstance();  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH) + 1;  
        int dayNow = cal.get(Calendar.DATE);  
  
        // 用当前年月日减去生日年月日  
        int yearMinus = yearNow - selectYear;  
        int monthMinus = monthNow - selectMonth;  
        int dayMinus = dayNow - selectDay;  
  
        int age = yearMinus;// 先大致赋值  
        
        if (yearMinus < 0) {// 选了未来的年份  
            age = 0;  
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0  
            if (monthMinus < 0) {// 选了未来的月份  
                age = 0;  
            } else if (monthMinus == 0) {// 同月份的  
                if (dayMinus < 0) {// 选了未来的日期  
                    age = 0;  
                } else if (dayMinus >= 0) {  
                    age = 1;  
                }  
            } else if (monthMinus > 0) {  
                age = 1;  
            }  
        } else if (yearMinus > 0) {  
            if (monthMinus < 0) {// 当前月>生日月  
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄  
                if (dayMinus < 0) {  
                } else if (dayMinus >= 0) {  
                    age = age + 1;  
                }  
            } else if (monthMinus > 0) {  
                age = age + 1;  
            }  
        } 
        return age;  
}
    
    public static boolean exec(String s) {
    	Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    	Matcher matcher = pattern.matcher(s);
    	if (matcher.find()){
        return true;
    	}else {
    	      return false;
    	}
    	}
    }

