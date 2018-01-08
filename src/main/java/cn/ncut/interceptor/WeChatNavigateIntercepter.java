package cn.ncut.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class WeChatNavigateIntercepter extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/**
		 * 获取session中存放的用户编号
		 * */
		HttpSession session = request.getSession();
		Object uuId = session.getAttribute("uId");
		String[] url = request.getRequestURI().split("/");
		String page = url[url.length - 1]; // 访问资源请求结尾标识
		/**
		 * 当用户编号为空的时候,重新获取用户基本信息,并将uId存入session
		 * */
		System.out.println("--------------------");
		System.out.println("本用户在数据库中的表的编号为:"+uuId+",访问的的sessionId:"+request.getSession().getId());
		if(uuId != null){
			Integer uId =Integer.valueOf(String.valueOf(uuId));
			System.out.println("页面导航uId不为空:"+uId+",当前访问的page:"+page);
			response.sendRedirect(request.getContextPath() + "/weChatNavigate/process?page=" + page+"&uId="+uId);
			return false;
		}else{
			System.out.println("页面导航uId为空,当前访问的page:"+page);
			response.sendRedirect(request.getContextPath() + "/weChatNavigate/oauthAuthorize/" + page);
			return false;
		}
	}
}
