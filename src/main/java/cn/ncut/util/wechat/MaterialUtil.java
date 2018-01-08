package cn.ncut.util.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import cn.ncut.entity.wechat.pojo.Token;

/**
 * 描述：素材管理的工具类
 * 
 * @author CWM
 * @date 2017-1-5 上午10:39:42
 */
public class MaterialUtil {
	private static Logger log = LoggerFactory.getLogger(MaterialUtil.class);
	// 获取素材总数
	public final static String getMaterialCountUrl = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	//获取永久素材列表
	public final static String batchgetMaterialUrl="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	//获取永久素材
	public final static String getMaterialUrl="https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	/**
	 * 获取素材总数，如果错误返回null; 
	 * { "voice_count":COUNT, 
	 * "video_count":COUNT,
	 * "image_count":COUNT, 
	 * "news_count":COUNT }
	 * 
	 * @return
	 */
	public static JSONObject getMaterialCount() {
		String accessToken = CommonUtil.getToken().getAccessToken();
		String url = getMaterialCountUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "GET", null);
		if (null != jsonObject) {
			if (!jsonObject.containsKey("errcode")) {
				log.info("获取素材总数成功 "+jsonObject.toString());
			}else{
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.info("获取素材总数失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				jsonObject = null;
			}
		}
		return jsonObject;
	}
	
	/** 获取永久素材列表
	 * @param type 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * 		offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * 		count  返回素材的数量，取值在1到20之间
	 * @return
	 */
	public static JSONObject batchgetMaterial(String type,int offset,int count) {
		String accessToken = CommonUtil.getToken().getAccessToken();
		String url = batchgetMaterialUrl.replace("ACCESS_TOKEN", accessToken);
		String params ="{\"type\":\""+type+"\",\"offset\":"+offset+",\"count\":"+count+"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", params);
		if (null != jsonObject) {
			if (!jsonObject.containsKey("errcode")) {
				log.info("获取永久素材列表成功 "+jsonObject.toString());
			}else{
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.info("获取永久素材列表失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				jsonObject = null;
			}
		}
		return jsonObject;
	}
	
	
	/**获取永久素材，url是图文url
	 * @param mediaId 通过获取素材列表获知素材的media_id
	 * @return
	 */
	public static JSONObject getMaterial(String mediaId){
		String accessToken = CommonUtil.getToken().getAccessToken();
		String url = getMaterialUrl.replace("ACCESS_TOKEN", accessToken);
		String params ="{\"media_id\":\""+mediaId+"\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", params);
		if (null != jsonObject) {
			if (!jsonObject.containsKey("errcode")) {
				log.info("获取永久素材成功 "+jsonObject.toString());
			}else{
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.info("获取永久素材失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				jsonObject = null;
			}
		}
		return jsonObject;
	}
	public static void main(String[] args) {
		batchgetMaterial("news", 0, 20);
		//getMaterial("pQkBBci354J0PYVBwluHzGMqmdepuGXNDgQ2TRoQIQ4");

	}
}
