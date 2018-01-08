package cn.ncut.controller.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.wechat.pojo.WeChatDiscountGroup;
import cn.ncut.entity.wechat.pojo.WeChatServiceProject;
import cn.ncut.entity.wechat.pojo.WeChatUserDiscount;
import cn.ncut.service.wechat.discount.impl.WeChatUserDiscountService;
import cn.ncut.service.wechat.discountGroup.impl.WeChatDiscountGroupService;
import cn.ncut.service.wechat.order.impl.WeChatJsApiPayOrderService;
import cn.ncut.service.wechat.serviceProject.impl.WeChatServiceProjectService;
import cn.ncut.util.wechat.WeChatLogUtil;

@Controller
@RequestMapping("/weChatUserDiscount")
public class WeChatUserDiscountController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(WeChatJsApiPayOrderService.class);
	
	@Resource(name = "weChatUserDiscountService")
	private WeChatUserDiscountService weChatUserDiscountService;
	
	@Resource(name = "weChatServiceProjectService")
	private WeChatServiceProjectService weChatServiceProjectService;
	
	@Resource(name = "weChatDiscountGroupService")
	private WeChatDiscountGroupService weChatDiscountGroupService;
	
	/**
	 * 罗列用户的优惠券概要信息
	 * @param request
	 * @return mav
	 * @throws Exception
	 * */
	@RequestMapping("/listUserValidDiscounts")
	public ModelAndView listUserValidDiscounts(HttpServletRequest request) throws Exception{
		WeChatLogUtil.controllerLog(request, "listUserValidDiscounts");
		
		HttpSession session = request.getSession();
		Integer uId = (Integer)session.getAttribute("uId");
		
		logger.debug("--- 优惠券模块,当前的登录用户的uId是 --- " + uId);
		
		// 更新用户优惠券的状态
		this.weChatUserDiscountService.updateUserDiscountToScanned(uId);
		
		List<WeChatUserDiscount> weChatUserDiscounts = this.weChatUserDiscountService.getUserUnusedDiscountsByUserId(uId);
		
		ModelAndView mav = this.getModelAndView();
		mav.setViewName("wechat/discount/userDiscounts");
		mav.addObject("weChatUserDiscounts",weChatUserDiscounts);
		mav.addObject("size",weChatUserDiscounts.size());
		return mav;
	}
	
	/**
	 * 通过tab_user_discount表主键id查询用户选择的优惠券详情
	 * @param request,id
	 * @return mav
	 * @throws Exception
	 * */
	@RequestMapping("/listSelectedDiscountDetail/{id}")
	public ModelAndView listSelectedDiscountDetail(HttpServletRequest request,@PathVariable("id") String id)
			throws Exception {
		
		// 日志完善一下
		WeChatLogUtil.controllerLog(request, "listSelectedDiscountDetail");
		
		// 列出用户所有有效的优惠券
		WeChatUserDiscount weChatUserDiscount = this.weChatUserDiscountService.getUserDiscountId(id);
		
		// 处理服务项目
		if(weChatUserDiscount.getDiscount().getProjectIds() != null){
			String[] projectIds = weChatUserDiscount.getDiscount().getProjectIds().split(",");
			List<WeChatServiceProject> weChatServiceProjects = new ArrayList<WeChatServiceProject>();
			for(String projectId : projectIds){
				WeChatServiceProject weChatServiceProject = this.weChatServiceProjectService.getServiceProjectByProjectId(projectId);
				weChatServiceProjects.add(weChatServiceProject);
			}
			weChatUserDiscount.getDiscount().setServiceProjects(weChatServiceProjects);
		}
			
		ModelAndView mav = this.getModelAndView();
		mav.setViewName("wechat/discount/selectedDiscountDetail");
		mav.addObject("weChatUserDiscount",weChatUserDiscount);
		return mav;
	}
	
	/**
	 * 列出用户有效的,适用于指定项目的优惠券
	 * @throws Exception 
	 * */
	@RequestMapping("/listUserValidAndTheProjectDiscounts/{mixedId}")
	public ModelAndView listUserValidAndTheProjectDiscounts(HttpServletRequest request,@PathVariable("mixedId") String mixedId)
			throws Exception{
		HttpSession session = request.getSession();
		Integer uId = (Integer)session.getAttribute("uId");
		
		logger.debug("--- 优惠券模块,当前的登录用户的uId是 ---" + uId);
		
		String[] mixed = mixedId.split(",");
		Set<String> projectIdsSet = new HashSet<String>();
		int length = mixed.length;
		for(int i = 0;i < length / 2;i ++){
			projectIdsSet.add(mixed[i]);
		}
		
		StringBuilder orderIdsBuilder = new StringBuilder();
		int j = length / 2;
		for(int i = length / 2;i < length;i ++){
			orderIdsBuilder.append(mixed[i]);
			if(j < length - 1){
				orderIdsBuilder.append(",");
			}
			j ++;
		}
		
		List<WeChatUserDiscount> weChatUserDiscounts = this.weChatUserDiscountService.getUserUnusedDiscountsByUserId(uId);
		
		// 匹配当前服务项目的优惠券
		Iterator<WeChatUserDiscount> iterator = weChatUserDiscounts.iterator();
		while(iterator.hasNext()){
			WeChatUserDiscount weChatUserDiscount = iterator.next();
			if(weChatUserDiscount.getDiscount().getProjectIds() != null){
				List<String> projectIds = Arrays.asList(weChatUserDiscount.getDiscount().getProjectIds().split(","));
				boolean flag = true;
				for(String projectId : projectIdsSet){
					if(projectIds.contains(projectId)){
						List<WeChatServiceProject> weChatServiceProjects = new ArrayList<WeChatServiceProject>();
						for(String pId : projectIds){
							WeChatServiceProject weChatServiceProject = this.weChatServiceProjectService.getServiceProjectByProjectId(pId);
							weChatServiceProjects.add(weChatServiceProject);
						}
						weChatUserDiscount.getDiscount().setServiceProjects(weChatServiceProjects);
						flag = false;
					}else{
						
					}
				}
				if(flag == true){
					iterator.remove();
				}
			}
		}
		
		ModelAndView mav = this.getModelAndView();
		mav.setViewName("wechat/discount/discountsUsedForOrderPay");
		mav.addObject("weChatUserDiscounts",weChatUserDiscounts);
		mav.addObject("orderId", orderIdsBuilder);
		return mav;
	}
	
	/**
	 * 罗列用户有效的优惠券组信息
	 * */
	@RequestMapping("/listUserValidDiscountGroups")
	public ModelAndView listUserValidDiscountGroups(HttpServletRequest request){
		HttpSession session = request.getSession();
		Integer uId = (Integer)session.getAttribute("uId");
						
		logger.debug("--- 优惠券模块,当前的登录用户的uId是 --- " + uId);
		Map<String,WeChatDiscountGroup> map = null; 
		List<WeChatDiscountGroup> weChatDiscountGroups = new ArrayList<WeChatDiscountGroup>();
		try {
			map = this.weChatDiscountGroupService.getUserDiscountGroupInfo(uId);
			// 将map转化为list
			Set<String> groupIds = map.keySet();
			for(String groupId : groupIds){
				WeChatDiscountGroup weChatDiscountGroup = map.get(groupId);
				// 获取当前优惠券组中包含的主键信息
				Map<String, List<Integer>> discountGroupIds = weChatDiscountGroup.getMap();
				Set<String> discountGroupIdsIterator = discountGroupIds.keySet();
				StringBuilder sb = null;
				for(String discountGroupId : discountGroupIdsIterator){
					List<Integer> ids = discountGroupIds.get(discountGroupId);
					sb = new StringBuilder();
					for(int i = 0;i < ids.size();i ++){
						sb.append(ids.get(i));
						if(i < ids.size() - 1){
							sb.append(",");
						}
					}
				}
				if(sb != null){
					weChatDiscountGroup.setIds(sb.toString());
				}
				weChatDiscountGroups.add(map.get(groupId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mav = this.getModelAndView();
		
		mav.addObject("weChatDiscountGroups", weChatDiscountGroups);
		mav.addObject("size", weChatDiscountGroups.size());
		mav.setViewName("wechat/discount/userDiscountGroup");
		return mav;
	}
	
	@RequestMapping("/listSelectedDiscountGroupDetail/{mixId}")
	public ModelAndView listSelectedDiscountGroupDetail(@PathVariable("mixId") String mixId) throws Exception{
		ModelAndView mav = this.getModelAndView();
		String[] discountGroupIds = null;
		if(mixId != null){
			discountGroupIds = mixId.split(",");
		}
		
		// 查询优惠券的张数信息
		WeChatDiscountGroup weChatDiscountGroup = null;
		List<WeChatDiscountGroup> weChatDiscountGroups = new ArrayList<WeChatDiscountGroup>();
		String discountGroupName = "测试";
		for(String discountGroupId : discountGroupIds){
			weChatDiscountGroup = new WeChatDiscountGroup();
			weChatDiscountGroup.setDiscountGroupId(Integer.valueOf(discountGroupId));
			weChatDiscountGroup = this.weChatDiscountGroupService.
					getDiscountGroupInfoByDiscountGroupId(weChatDiscountGroup);
			String discountId = weChatDiscountGroup.getDiscountId();
			
			weChatDiscountGroups.add(weChatDiscountGroup);
		}
		
		mav.setViewName("wechat/discount/selectedDiscountGroupDetail");
		
		mav.addObject("weChatDiscountGroups", weChatDiscountGroups);
		mav.addObject("discountGroupName", discountGroupName);
		
		return mav;
	}
}