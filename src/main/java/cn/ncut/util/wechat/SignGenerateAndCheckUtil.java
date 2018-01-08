package cn.ncut.util.wechat;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import oracle.net.aso.p;

import cn.ncut.entity.wechat.pojo.PayH5;

/**
 * [统一下单]中的sign微信签名生成与验证工具类
 * */
public class SignGenerateAndCheckUtil {
	// 微信提供的app秘钥信息,根据具体情况设置
	private static String Key = SpringPropertyResourceReader.getProperty("apiSecret");
	
	/**
	 * 获取[统一下单]签名sign生成
	 * @param characterEncoding 字符编码
	 * @param parameters 非空参数值的参数按照参数名ASCII码从小到大排序的Map
	 * @return 结果字符串,即sign
	 * */
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set<Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Entry<Object, Object>> it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + Key);  
        String sign = MD5Util.MD5Encode(sb.toString().replace("payPackage", "package=prepay_id"), characterEncoding).toUpperCase();  
        return sign;  
    }
	
	/**
	 * 将一个对象中的非空属性按字典序排序,并放入map中;用于签名生成
	 * @param t 待排序的对象
	 * @return 排好序的map
	 * */
	public static <T> TreeMap<Object, Object> sortBeanAttributes(T t) {
		TreeMap<Object,Object> parameters = new TreeMap<Object, Object>();
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			String key = field.getName();
			Object value = null;
			try{
				value = field.get(t);
				if(value != null && key != "sign"){
					parameters.put(key, value);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return parameters;
	}
	public static void main(String[] args) {
		PayH5 payH5 = new PayH5();
		payH5.setAppId("wxc5db3f7f1772d16c");
		payH5.setTimeStamp("1483498666");
		payH5.setNonceStr("8OX1BAW29YCGH65RP0YOCC9Y1M22P5SE");
		payH5.setPayPackage("wx20170104105746d8356302c40677288339");
		payH5.setSignType("MD5");
		
		createSign("UTF-8",sortBeanAttributes(payH5));
	}
}
