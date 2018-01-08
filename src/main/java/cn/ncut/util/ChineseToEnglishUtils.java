package cn.ncut.util;

import java.text.SimpleDateFormat;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 将中文转化为拼音工具类
 * @author xia
 *
 */
public class ChineseToEnglishUtils { 
	   //将中文转换为英文
    public static String getEname(String name) throws BadHanyuPinyinOutputFormatCombination {
         HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
         pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);    //设置样式
         pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
          pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

         return PinyinHelper.toHanyuPinyinString(name, pyFormat, "");
    }

    //姓、名 英文第一个字母大写
    public static String getUpEname(String name) throws BadHanyuPinyinOutputFormatCombination {
         char[] strs = name.toCharArray();
         String newname = null;

          if (strs.length == 2) {    //如果姓名只有两个字
               newname = toUpCase(getEname("" + strs[0])) + " " + toUpCase(getEname("" + strs[1]));
           } else if (strs.length == 3) {    //如果姓名有三个字
               newname = toUpCase(getEname("" + strs[0])) + " "+ toUpCase(getEname("" + strs[1] + strs[2]));
           } else if (strs.length == 4) {    //如果姓名有四个字
               newname = toUpCase(getEname("" + strs[0] + strs[1])) + " "+ toUpCase(getEname("" + strs[2] + strs[3]));
         } else {
                newname = toUpCase(getEname(name)).replace("（", "(").replace("）", ")");
         }

         return newname;
    }

     //首字母大写
    private static String toUpCase(String str) {
         StringBuffer newstr = new StringBuffer();
         newstr.append((str.substring(0, 1)).toUpperCase()).append(
         str.substring(1, str.length()));

         return newstr.toString();
    }

    

}
