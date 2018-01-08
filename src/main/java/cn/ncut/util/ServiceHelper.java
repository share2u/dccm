package cn.ncut.util;

import cn.ncut.service.system.menu.impl.MenuService;
import cn.ncut.service.system.role.impl.RoleService;
import cn.ncut.service.system.user.UserManager;


/**
 * 
*
*<p>Title:ServiceHelper</p>
*<p>Description:获取Spring容器中的service bean</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-6上午10:19:14
*
 */
public final class ServiceHelper {
	
	public static Object getService(String serviceName){
		//WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}
	
	public static UserManager getUserService(){
		return (UserManager) getService("userService");
	}
	
	public static RoleService getRoleService(){
		return (RoleService) getService("roleService");
	}
	
	public static MenuService getMenuService(){
		return (MenuService) getService("menuService");
	}
}
