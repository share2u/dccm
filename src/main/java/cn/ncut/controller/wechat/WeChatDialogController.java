package cn.ncut.controller.wechat;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ncut.entity.system.Staff;
import cn.ncut.entity.wechat.pojo.WeChatDelayOfNetwork;
import cn.ncut.entity.wechat.pojo.WeChatMemberCallback;
import cn.ncut.entity.wechat.pojo.WeChatOrder;
import cn.ncut.service.wechat.memberCallback.impl.WeChatMemberCallbackService;
import cn.ncut.service.wechat.order.impl.WeChatOrderService;
import cn.ncut.service.wechat.payPerformance.impl.WeChatPayPerformanceService;
import cn.ncut.util.Const;
import cn.ncut.util.wechat.NetworkUtil;

@Controller
@RequestMapping("/weChatDialog")
public class WeChatDialogController {
	@Resource(name = "weChatMemberCallbackService")
	private WeChatMemberCallbackService weChatMemberCallbackService;
	
	@Resource(name = "weChatOrderService")
	private WeChatOrderService weChatOrderService;
	
	@Resource(name = "weChatPayPerformanceService")
    private WeChatPayPerformanceService weChatPayPerformanceService;
	
	/**
	 * 根据当登录前员工所在门店与咨询表门店对应关系,利用message提示相关门店员工有客户咨询;
	 * 仅仅只给客服提示,系统其他角色不给予提示消息
	 * @param request
	 * @param response
	 * @return 是否匹配的状态
	 * */
	@ResponseBody
	@RequestMapping(value = "/generateConsultDialog")
	public String generateDialog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Staff staff = getStaffBySessionStaffId(request,response);
		if(staff != null){
			String roleId = staff.getROLE_ID();
			String storeId = staff.getSTORE_ID();
			List<WeChatMemberCallback> weChatMemberCallbacks = this.weChatMemberCallbackService.
					getAllNonresponseMemberCallback(storeId);
			if(weChatMemberCallbacks.size() > 0 && roleId.equals("b0f249e9d7c6428e9ee37b74bdddeed6")){
				return "{\"result\":\"success\"}";
			}else{
				return "{\"result\":\"fail\"}";
			}
		}
		return "{\"result\":\"fail\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/generateOrderOverTimeDialog")
	public String generateOrderOverTimeDialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Staff staff = getStaffBySessionStaffId(request,response);
		if(staff != null){
			String roleId = staff.getROLE_ID();
			String storeId = staff.getSTORE_ID();
			List<WeChatOrder> weChatOrders = this.weChatOrderService.getOrdersByOrderStatus(3);
			// 处理属于当前门店的订单
			Iterator<WeChatOrder> iterator = weChatOrders.iterator();
			while(iterator.hasNext()){
				WeChatOrder weChatOrder = iterator.next();
				if(storeId != null && !weChatOrder.getStoreId().equals(storeId)){
					iterator.remove();
				}
			}
			if(weChatOrders.size() > 0 && roleId.equals("b0f249e9d7c6428e9ee37b74bdddeed6")){
				return "{\"result\":\"success\"}";
			}else{
				return "{\"result\":\"fail\"}";
			}
		}
		return null;
	}
	
	private Staff getStaffBySessionStaffId(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		Staff staff = (Staff)session.getAttribute(Const.SESSION_USER);
		return staff;
	}
	
	@ResponseBody
    @RequestMapping(value = "/statisticTime")
    public String statisticTime(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String flag = request.getParameter("flag");
        if (flag != null && "true".equals(flag)) {
            // 前端传递参数供后端分析
            String start = request.getParameter("start");
            String mid = request.getParameter("mid");
            String end = request.getParameter("end");
            Staff staff = this.getStaffBySessionStaffId(request, response);
            String staffId = staff == null ? null : staff.getSTAFF_ID();
            String delayIp = NetworkUtil.getIpAddress(request);
            this.weChatPayPerformanceService.saveWeChatDelayOfNetwork(WeChatDelayOfNetwork.
            		getInstance(Long.valueOf(start), Long.valueOf(mid), Long.valueOf(end), delayIp, null, staffId));
        } else {
            // 向前端传递时间戳参数
            long now = System.currentTimeMillis();
            return "{\"server\":" + now + "}";
        }
        return null;
    }
}