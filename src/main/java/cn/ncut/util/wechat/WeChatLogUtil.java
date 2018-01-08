package cn.ncut.util.wechat;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.ncut.entity.wechat.pojo.WeChatLog;

import com.google.gson.Gson;

public class WeChatLogUtil {
	private static Logger weChatInfo = LoggerFactory
			.getLogger("weChatController");

	public static void controllerLog(HttpServletRequest request, String method) {
		Gson gson = new Gson();
		HttpSession session = request.getSession();
		String uuId = String.valueOf(session.getAttribute("uId"));
		String ip = NetworkUtil.getIpAddress(request);
		String uri = request.getRequestURI();
		String refUrl = request.getHeader("Referer");
		WeChatLog weChatLog = new WeChatLog();
		String[] url = request.getRequestURI().split("/");
		if (null != uuId && !"".equals(uuId)&& !"null".equals(uuId)) {
			weChatLog.setUID(Integer.valueOf((uuId)));
		} else {
			weChatLog.setUID(-100);
		}

		if (method.equals("process")) {
			weChatLog.setRequestMethod("微信菜单跳转页面");
			weChatLog.setRequestParams("{\"page\":\""
					+ request.getParameter("page") + "\"}");
		} else if (method.equals("cardCenter")) {
			weChatLog.setRequestMethod("查询账户总额");
			weChatLog.setRequestParams("nnll");
		} else if (method.equals("rechargeDetails")) {
			weChatLog.setRequestMethod("查询充值明细");
			weChatLog.setRequestParams("nnll");
		} else if (method.equals("packageUnifiedOrder")) {
			weChatLog.setRequestMethod("储值卡充值");
			weChatLog.setRequestParams("{\"storedCategoryId\":\""
					+ request.getParameter("storedCategoryId") + "\"}");
		} else if (method.equals("creditDetails")) {
			weChatLog.setRequestMethod("查询积分");
			weChatLog.setRequestParams("nnll");
		} else if (method.equals("getStoresByModuleId")) {
			weChatLog.setRequestMethod("查询门店列表");
			weChatLog.setRequestParams("{\"mId\":\""
					+ url[url.length-1] + "\"}");
		} else if (method.equals("getStoreDetailByStoreId")) {
			weChatLog.setRequestMethod("查询门店");
			weChatLog.setRequestParams("{\"storedId\":\""
					+url[url.length-1] + "\"}");
		} else if (method.equals("getDoctorDetailById")) {
			weChatLog.setRequestMethod("查询医生或讲师");
			weChatLog.setRequestParams("{\"staffId\":\""
					+ url[url.length-1] + "\"}");
		} else if (method.equals("getStoresBySome")) {
			weChatLog.setRequestMethod("查询门店根据some");
			weChatLog.setRequestParams("{\"queryParam\":\""
					+ request.getParameter("queryParam") + "\"}");
		} else if (method.equals("sendTelToStore")) {
			weChatLog.setRequestMethod("咨询电话");
			weChatLog.setRequestParams("{\"storeId\":\""
					+ request.getParameter("storeId") + "\",\"docId\":\""
					+ request.getParameter("docId") + "\",\"tel\":\""
					+ request.getParameter("tel") + "\",\"name\":\""
					+ request.getParameter("name") + "\"}");
		} else if (method.equals("sendSuggest")) {
			weChatLog.setRequestMethod("投诉与建议");
			weChatLog.setRequestParams("{\"suggestText\":\""
					+ request.getParameter("suggestText") + "\",\"tel\":\""
					+ request.getParameter("tel") + "\"}");
		} else if (method.equals("userInfo")) {
			weChatLog.setRequestMethod("用户中心界面");
			weChatLog.setRequestParams("nnll");
		} else if (method.equals("pageNavigate")) {
			weChatLog.setRequestMethod("用户中心导航信息");
			weChatLog.setRequestParams("{\"method\":\""
					+ request.getParameter("method") + "\"}");
		} else if (method.equals("listUserValidDiscounts")) {
			weChatLog.setRequestMethod("优惠劵菜单");
			weChatLog.setRequestParams("{\"method\":\""
					+ request.getParameter("method") + "\"}");
		}else if(method.equals("listOrders")){
			Integer id=Integer.valueOf(url[url.length-1]);
			switch(id){
			case 1:
				weChatLog.setRequestMethod("我的订单");
				break;
			case 2:
				weChatLog.setRequestMethod("已预约订单");
				break;
			case 3:
				weChatLog.setRequestMethod("待支付订单");
				break;
			default:
				weChatLog.setRequestMethod("未知情况");
				break;
			}
			weChatLog.setRequestParams("nnll");
			
		}else{
			weChatLog.setRequestMethod(method.toString());
			weChatLog.setRequestParams("未知参数");
			
		}

		weChatLog.setRequestIP(ip);
		weChatLog.setRequestURI(uri);
		if (null == refUrl) {
			refUrl = "nnll";
		}
		weChatLog.setRequestPrtURI(refUrl);
		weChatInfo.info(gson.toJson(weChatLog));
	}
	
	
	
	public static void controllerError(Exception e){
		weChatInfo.error(e.getMessage());
	}
	
	public static void controllerInfo(String s){
		weChatInfo.info(s);
	}
	
	
	  /**   
     * 解析方法参数   
     * @param parames 方法参数   
     * @return 解析后的方法参数   
     */    
    private static String  parseParames(Object[] parames) {     
        StringBuffer sb = new StringBuffer(); 
        Gson gson = new Gson();
        for(int i=0; i<parames.length; i++){     
            if(parames[i] instanceof Object[] || parames[i] instanceof Collection){     
                String json = gson.toJson(parames[i]);     
                if(i==parames.length-1){     
                    sb.append(json.toString());     
                }else{     
                    sb.append(json.toString() + ",");     
                }     
            }else if(parames[i] instanceof ServletRequest){
           	 Map requestParameterMap = ((ServletRequest)parames[i]).getParameterMap();
           	 String json = gson.toJson(requestParameterMap);
           	 if(i==parames.length-1){     
                    sb.append(json.toString());     
                }else{     
                    sb.append(json.toString() + ",");     
                } 
            }else{     
           	 String json = gson.toJson(parames[i]);       
                if(i==parames.length-1){     
                    sb.append(json.toString());     
                }else{     
                    sb.append(json.toString() + ",");     
                }     
            }     
        }     
        String params = sb.toString();     
        params = params.replaceAll("(\"\\w+\":\"\",)", "");     
        params = params.replaceAll("(,\"\\w+\":\"\")", "");     
        return params;     
    }    
}
