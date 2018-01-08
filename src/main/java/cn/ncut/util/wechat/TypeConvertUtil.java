package cn.ncut.util.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.format.datetime.DateFormatter;

import cn.ncut.entity.wechat.pojo.SNSUserInfo;
import cn.ncut.entity.wechat.pojo.WeChatUser;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 执行
 * 	bean - > xml
 * 	xml - > bean
 * */
public class TypeConvertUtil {
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out){
				 boolean cdata = true;  
				  
	                @SuppressWarnings("unchecked")  
	                public void startNode(String name, Class clazz) {  
	                    super.startNode(name, clazz);  
	                }  
	                
	                
	                @Override
	                public void setValue(String text) {
	                    
	                    if(text!=null && !"".equals(text)){
	                        //浮点型判断
	                        Pattern patternInt = Pattern.compile("[0-9]*(\\.?)[0-9]*");
	                        //整型判断
	                        Pattern patternFloat = Pattern.compile("[0-9]+");
	                        //如果是整数或浮点数 就不要加[CDATA[]了
	                        if(patternInt.matcher(text).matches() || patternFloat.matcher(text).matches()){
	                        cdata = false;
	                        }else{
	                        cdata = true;
	                        }
	                    }
	                    super.setValue(text);  
	                }
	                
	                
	                protected void writeText(QuickWriter writer, String text) {  
	                    if (cdata) {  
	                        writer.write("<![CDATA[");  
	                        writer.write(text);  
	                        writer.write("]]>");  
	                    } else {  
	                        writer.write(text);  
	                    }  
	                }
			 };
			}});

	
	/**
	 * @param requestUrl 请求微信提供的远程接口链接
	 * @param object 待转化为xml的bean对象,作为微信远程接口链接的请求参数 
	 * @param clazz 希望得到的类型
	 * @return clazz 的一个对象,在使用该对象之前需要验证它的有效性
	 * */
	public static <T> T transformBeanToBeanFactory(String requestUrl,Object object,Class<T> clazz){
		// 将业务bean转化为xml 
		String requestXml = transformBeanToXml(object);
		
		// 替换"__"为"_"
		requestXml = requestXml.replace("__", "_");
		
		// 请求微信提供的远程接口链接,并获取返回结果
		String responseXml = requestRemoteUrl(requestUrl, requestXml);
		
		// 替换xml的根节点<xml> -- > T 类型
		responseXml = responseXml.replace("xml", clazz.getSimpleName());
		
		// 将微信返回的xml转化为bean,CDATA信息将会自动过滤
		T destinationBean = transformXmlToBean(clazz, responseXml);
		
		return destinationBean;
	}
	
	/**
	 * 将java bean转化为xml
	 * 	注意替换文档的根节点为xml,未考虑商品详情details如何处理
	 * @param object 待转化为xml的bean
	 * @return 返回的xml
	 * */
	public static <T> String transformBeanToXml(T object){
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>)object.getClass();
		String simpleName = clazz.getSimpleName();
		Map<String, Class<T>> map = new HashMap<String,Class<T>>();
		map.put(simpleName, clazz);
		return bean2xml(map,object).replace(simpleName,"xml").replace("__", "_");
	}
	
	/**
	 * 请求微信端提供的远程接口链接,并以字符串的形式返回微信端响应的xml
	 * @param requestUrl 远程接口链接
	 * @param xml 请求参数
	 * @return 返回的xml
	 * */
	public static String requestRemoteUrl(String requestUrl,String xml){
		URL url = null;
        HttpURLConnection conn = null;
        
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;
        try {
        	/**
        	 * 初始化连接
        	 * */
        	url = new URL(requestUrl);
			conn = (HttpURLConnection)url.openConnection();
			
			/**
			 * 设置连接属性
			 * */
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			conn.setRequestProperty("Pragma:", "no-cache");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Content-Type", "text/xml");
			conn.setRequestMethod("POST");
			
			/**
			 * 传输xml数据到远程url
			 * */
			if(xml != null){
				outputStream = conn.getOutputStream();
				outputStream.write(xml.getBytes("UTF-8"));
			}
			
			/**
			 * 处理远程url返回的xml
			 * */
			inputStream = conn.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			sb = new StringBuilder();
			String buffer = null;
			while((buffer = bufferedReader.readLine()) != null){
				sb.append(buffer);
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				outputStream.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将request转变为bean
	 * @param clazz 需要的类类型
	 * @param request 
	 * @throws IOException 
	 */
	public static <T> T transformRequestToBean(Class<T> clazz,HttpServletRequest request){
		InputStream inputStream;
		try {
			inputStream = request.getInputStream();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String buffer = null;
		while((buffer = bufferedReader.readLine()) != null){
			sb.append(buffer);
		}
		inputStream.close();
		inputStream = null;
		return transformXmlToBean(clazz, sb.toString().replace("xml", clazz.getSimpleName()));
		} catch (IOException e) {
			WeChatLogUtil.controllerError(e);
			return null;
		}
		
	}
	
	
	/**
	 * 将xml转化为bean对象
	 * @param clazz bean对象的Class信息
	 * @param xml
	 * @return 转化的bean对象
	 * */
	public static <T> T transformXmlToBean(Class<T> clazz,String xml){	
		String simpleName = clazz.getSimpleName();
		Map<String, Class<T>> map = new HashMap<String,Class<T>>();
		map.put(simpleName, clazz);
		return xml2Bean(map,xml);
	}
	
	
	/**
	 * 将微信默认的用户转换为服务器的用户
	 * @param snsUserInfo 微信默认的用户pojo
	 * @return WechatUser 服务器使用的用户pojo
	 */
	public static WeChatUser SNSUser2WeChatUser(SNSUserInfo snsUserInfo){
		WeChatUser weChatUser=new WeChatUser();
		weChatUser.setOpenId(snsUserInfo.getOpenId());
		weChatUser.setUserName(snsUserInfo.getNickname());
		weChatUser.setUserImg(snsUserInfo.getHeadImgUrl());
		weChatUser.setSex(snsUserInfo.getSex());
		weChatUser.setSource(0);
		weChatUser.setUserCategory(1);
		weChatUser.setProportion(1.00);
		String attentionTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		weChatUser.setAttentionTime(attentionTime);
		return weChatUser;
	};
	
	private static <T> String bean2xml(Map<String, Class<T>> clazzMap, Object bean) {
        for (Iterator<Entry<String, Class<T>>> it = clazzMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Class<T>> m = (Map.Entry<String, Class<T>>) it.next();
            xstream.alias(m.getKey(), m.getValue());
        }
        String xml = xstream.toXML(bean);
        return xml;
    }
	
	@SuppressWarnings("unchecked")
	private static <T> T xml2Bean(Map<String, Class<T>> clazzMap, String xml) {
        for (Iterator<Entry<String, Class<T>>> it = clazzMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Class<T>> m = (Map.Entry<String, Class<T>>) it.next();
            xstream.alias(m.getKey(), m.getValue());
        }
        T bean = (T)xstream.fromXML(xml);
        return bean;
	}
}
