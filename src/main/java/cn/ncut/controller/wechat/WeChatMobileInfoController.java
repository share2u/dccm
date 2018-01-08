package cn.ncut.controller.wechat;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ncut.entity.wechat.pojo.WeChatMobileInfo;
import cn.ncut.entity.wechat.pojo.WeChatUser;
import cn.ncut.service.wechat.mobileInfo.impl.WeChatMobileInfoService;
import cn.ncut.service.wechat.user.impl.WeChatUserService;

@Controller
@RequestMapping("/weChatMobileInfo")
public class WeChatMobileInfoController {
	@Resource(name = "weChatMobileInfoService")
	private WeChatMobileInfoService weChatMobileInfoService;
	
	@Resource(name = "weChatUserService")
	private WeChatUserService weChatUserService;
	
	@ResponseBody
	@RequestMapping("/getMobileInfoByMobileNum")
	public WeChatMobileInfo getMobileInfoByMobileNum(HttpServletRequest request) throws Exception{
		WeChatMobileInfo weChatMobileInfo = new WeChatMobileInfo();
		String phone = request.getParameter("phone");
		if(phone != null && !(phone.trim()).equals("") && phone.trim().length() > 7){
			phone = phone.substring(0, 7);
			return this.weChatMobileInfoService.getMobileInfoByMobileNum(phone);
		}
		weChatMobileInfo.setMobileArea("城市未知");
		return weChatMobileInfo;
	}
	
	/**
	 * 检测手机号是否重复
	 * */
	@ResponseBody
	@RequestMapping("/checkMobileIsDuplicate")
	public String checkMobileIsDuplicate(HttpServletRequest request){
		String result = "{\"result\":\"success\"}";
		String phone = request.getParameter("phone");
		String uId = request.getParameter("uId");
		
		WeChatUser weChatUser = new WeChatUser();
		weChatUser.setPhone(phone);
		if(uId != null && !"".equals(uId)){
			weChatUser.setuId(Integer.valueOf(uId));
		}
		
		List<WeChatUser> weChatUsers = null;
		if(phone != null && !(phone.trim()).equals("") && phone.trim().length() == 11){
			try{
				weChatUsers = this.weChatUserService.checkMobileIsDuplicate(weChatUser);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		// 该手机号已被注册
		if(weChatUsers != null && weChatUsers.size() > 0){
			result = "{\"result\":\"fail\"}";
		}
		return result;
	} 
}
