package cn.ncut.controller.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.wechat.pojo.SNSUserInfo;
import cn.ncut.entity.wechat.pojo.WeChatUser;
import cn.ncut.entity.wechat.pojo.WeixinOauth2Token;
import cn.ncut.service.wechat.CoreService;
import cn.ncut.service.wechat.home.HomeManager;
import cn.ncut.service.wechat.home.impl.HomeService;
import cn.ncut.service.wechat.user.impl.WeChatUserService;
import cn.ncut.util.PageData;
import cn.ncut.util.wechat.AdvancedUtil;
import cn.ncut.util.wechat.CommonUtil;
import cn.ncut.util.wechat.SignUtil;
import cn.ncut.util.wechat.TypeConvertUtil;

@Controller
@RequestMapping(value = "/weChatCoreServlet")
public class CoreServlet extends BaseController {

	@Resource(name = "weChatUserService")
	public WeChatUserService weChatUserService;
	@Resource(name = "weChatHomeService")
	public HomeService homeService;

	/**
	 * 请求校验（确认请求来自微信服务器）
	 */
	@RequestMapping(value = "/hello")
	public void wechatMain(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getMethod();
		if (method.equals("GET")) {
			System.out.println("doget");
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");

			PrintWriter out = response.getWriter();
			// 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
			out.close();
			out = null;
		} else if (method.equals("POST")) {
			System.out.println("post");
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			// 调用核心业务类接收消息、处理消息
			String respXml = CoreService.processRequest(request);

			// 响应消息
			PrintWriter out = response.getWriter();
			out.print(respXml);
			out.close();
		}
	}
	@RequestMapping("/world")
	public String world() {
		return "wechat/success";
	}
}
