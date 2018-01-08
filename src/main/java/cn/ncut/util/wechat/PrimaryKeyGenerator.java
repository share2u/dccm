package cn.ncut.util.wechat;

import java.util.Random;
import java.util.UUID;

/**
 * 主键生成器
 * */
public class PrimaryKeyGenerator {
	/**
	 * 根据uuid的hashCode拼接16位唯一编码
	 * @return 生成的16位唯一编码
	 * */
	public static String generateKey(){
		int first = new Random(10).nextInt(8) + 1;
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		// 有可能是负数
		if (hashCodeV < 0) {
			hashCodeV = - hashCodeV;
		}
		return first + String.format("%015d", hashCodeV);
	}
}