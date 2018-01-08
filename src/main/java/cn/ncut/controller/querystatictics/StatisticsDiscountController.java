package cn.ncut.controller.querystatictics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.QueryOrder;
import cn.ncut.entity.system.QueryUserDiscount;
import cn.ncut.entity.system.Role;
import cn.ncut.entity.system.Staff;
import cn.ncut.service.finance.discount.DiscountManager;
import cn.ncut.service.finance.serviceall.impl.ServiceCostService;
import cn.ncut.service.system.depart.DepartManager;
import cn.ncut.service.system.role.RoleManager;
import cn.ncut.service.system.servicemodule.ServicemoduleManager;
import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.service.system.store.StoreManager;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.service.user.order.OrderManager;
import cn.ncut.service.user.order.impl.OrderService;
import cn.ncut.service.user.userdiscount.UserDiscountManager;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;

@Controller
@RequestMapping(value = "/statisticsDiscounts")
public class StatisticsDiscountController extends BaseController {

	@Resource(name = "userdiscountService")
	private UserDiscountManager userdiscountService;

	@RequestMapping(value = "/list")
	public ModelAndView listDiscounts() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		List<PageData> discountGroups = userdiscountService.selectDiscountGroups(pd);
		mv.addObject("discountGroups", new ObjectMapper().writeValueAsString(discountGroups));
		mv.addObject("pd", pd);
		mv.setViewName("querysatistics/discount_statistics");
		return mv;
	}
	@RequestMapping(value = "/selectIsUsedDiscountByGroupId")
	@ResponseBody
	public String selectIsUsedDiscountByGroupId() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> discountDetails = userdiscountService.selectIsUsedDiscountByGroupId(pd);
		HashMap<String, List<String>> hashMap = new HashMap<String,List<String>>();
		for (Iterator<PageData> iterator = discountDetails.iterator(); iterator.hasNext();) {
			PageData pageData = iterator.next();
			String discountId = pageData.getString("discountId");
			List<String> discountList=null;
			//有这个优惠券，拿出原来的list,改变引用的值，原引用是否会改变呢？
			if(hashMap.containsKey(discountId)){
				discountList = hashMap.get(discountId);
			}else{
				discountList =  Arrays.asList("0","0","0");
				discountList.set(0, pageData.getString("discountName"));// 优惠券名称
			}
			if ((int) (pageData.get("isUsed")) == 1) {
				// 使用的优惠券放在1索引位置
				discountList.set(1,
						String.valueOf((long) (pageData.get("useCount"))));
			} else {
				// 未使用的优惠券放在2索引位置
				discountList.set(2,
						String.valueOf((long) (pageData.get("useCount"))));
			}
			hashMap.put(discountId, discountList);
			
		}
		String jsonString = new ObjectMapper().writeValueAsString(hashMap);
		return jsonString;
	}
}
