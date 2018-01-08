package cn.ncut.util.wechat;

import java.util.Random;

/**
 * [nonce_str]生成工具类
 * */
public class RandomUtil {
	private static final String LETTERMIXDIGIT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * 仅包含大写字母与数字的随机字符串,目前用于[统一下单]中的[随机字符串 nonce_str]
	 * @param length 需要生成的随机字符串的长度;默认为32位
	 * @return 目标字符串
	 * */
	public static String generateMixedCharAndDigit(int length){
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		int domain = RandomUtil.LETTERMIXDIGIT.length();
		for(int i = 0;i < length;i ++){
			sb.append(RandomUtil.LETTERMIXDIGIT.charAt(random.nextInt(domain)));
		}
		return sb.toString();
	}
}
