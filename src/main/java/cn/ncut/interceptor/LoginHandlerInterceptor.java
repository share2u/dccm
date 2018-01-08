package cn.ncut.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.ncut.entity.system.Staff;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
/**
 * 
*
*<p>Title:LoginHandlerInterceptor</p>
*<p>Description:登录过滤，权限验证</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-5下午8:40:39
*
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			Staff staff = (Staff)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if(staff!=null){
				path = path.substring(1, path.length());
				boolean b = Jurisdiction.hasJurisdiction(path); //访问权限校验
				if(!b){
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
				}
				return b;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;		
			}
		}
	}
	
}
