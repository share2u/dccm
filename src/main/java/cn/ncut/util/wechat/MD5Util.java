package cn.ncut.util.wechat;

import java.security.MessageDigest;

/**
 * MD5工具类
 * */
public class MD5Util {
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" }; 
	
	/**
	 * 将字节数组转化为十六进制字符串
	 * @param b 待转化的字节数组
	 * @return 十六进制结果字符串
	 * */
	public static String byteArrayToHexString(byte[] b){
		StringBuilder result = new StringBuilder();
		for(int i = 0;i < b.length;i ++){
			result.append(byteToHexString(b[i]));
		}
		return result.toString();
	}
	
	/**
	 * 十进制转化为十六进制
	 * @param b 待转化的字节
	 * @return 结果字符串
	 * */
	private static String byteToHexString(byte b) {  
        int n = b;  
        if (n < 0)  
            n += 256;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    } 
	
	/**
	 * MD5编码字符串
	 * @param origin 原字符串
	 * @param charsetname 字符集
	 * @return 经MD5转化后的字符串
	 * */
	public static String MD5Encode(String origin, String charsetname) {  
        String resultString = null;  
        try {  
            resultString = new String(origin);
            /**
             *  Returns a MessageDigest object that implements the specified digest algorithm.
             */
            MessageDigest md = MessageDigest.getInstance("MD5");  
            if (charsetname == null || "".equals(charsetname))  
                resultString = byteArrayToHexString(md.digest(resultString  
                        .getBytes()));  
            else  
                resultString = byteArrayToHexString(md.digest(resultString  
                        .getBytes(charsetname)));  
        } catch (Exception exception) {  
        	
        }  
        return resultString;  
    }
}
