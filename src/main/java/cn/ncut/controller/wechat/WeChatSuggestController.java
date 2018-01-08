package cn.ncut.controller.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.service.wechat.home.impl.HomeService;
import cn.ncut.service.wechat.suggest.WeChatSuggestManager;
import cn.ncut.util.PageData;
import cn.ncut.util.wechat.WeChatLogUtil;

@Controller
@RequestMapping("/weChatSuggest")
public class WeChatSuggestController extends BaseController {
	@Resource(name = "weChatSuggestService")
	public WeChatSuggestManager weChatSuggest;
	/**
	 * 发送投诉与建议
	 * @throws Exception 
	 */
	@RequestMapping("/sendSuggest")
	@ResponseBody
	public String sendTelToStore(HttpServletRequest request) throws Exception{
		WeChatLogUtil.controllerLog(request, "sendSuggest");
		PageData pd = new PageData();
		String suggestText = request.getParameter("suggestText");
		String tel =request.getParameter("tel");
		String suggestTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Integer uId = (Integer) request.getSession().getAttribute("uId");
		pd.put("uId", uId);
		pd.put("content", suggestText);
		pd.put("createTime", suggestTime);
		pd.put("phone", tel);
		pd.put("method", 0);
		pd.put("status", 0);
		weChatSuggest.sendSuggest(pd);
		
		return "{\"key\":\"success\"}";
	}
}
