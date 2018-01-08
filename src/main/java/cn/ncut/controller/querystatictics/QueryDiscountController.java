package cn.ncut.controller.querystatictics;

import java.util.ArrayList;
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
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;

@Controller
@RequestMapping(value = "/queryDiscount")
public class QueryDiscountController extends BaseController {

	String menuUrl = "queryDiscount/list.do"; // 菜单地址(权限用)
	@Resource(name = "staffService")
	private StaffManager staffService;
	@Resource(name = "storeService")
	private StoreManager storeService;

	@Resource(name = "memberService")
	private MemberManager memberService;
	@Resource(name = "discountService")
	private DiscountManager discountService;

	@Resource(name = "servicemoduleService")
	private ServicemoduleManager servicemoduleService;

	@RequestMapping(value = "/list")
	public ModelAndView listDiscounts(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		List<QueryUserDiscount> userDiscounts = null;
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("querysatistics/discount_query");
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		// 添加检索条件
		// 根据用户名、微信名查询用户id
		String name = pd.getString("name");
		if (null != name && !"".equals(name)) {
			ArrayList<Integer> uIdsArray = new ArrayList<Integer>();
			List<PageData> uIds = memberService.selectUsersByName(pd);
			for (Iterator<PageData> iterator = uIds.iterator(); iterator
					.hasNext();) {
				uIdsArray.add((Integer) iterator.next().get("uId"));
			}
			if (uIdsArray.size() > 0) {
				pd.put("uIds", uIdsArray);
			} else {
				page.setPd(pd);
				mv.addObject("userDiscounts", userDiscounts);
				mv.addObject("pd", pd);
				return mv;
			}
		}
		// 根据优惠券名称查询优惠券id
		String discountName = pd.getString("discountName");
		if (null != discountName && !"".equals(discountName)) {
			ArrayList<String> disCountIdsArray = new ArrayList<String>();
			List<PageData> disCountIds = discountService
					.selectDisCountsByName(pd);
			for (Iterator<PageData> iterator = disCountIds.iterator(); iterator
					.hasNext();) {
				disCountIdsArray.add(iterator.next().getString("discountId"));
			}
			if (disCountIdsArray.size() > 0) {
				pd.put("discountIds", disCountIdsArray);
			} else {
				page.setPd(pd);
				mv.addObject("userDiscounts", userDiscounts);
				mv.addObject("pd", pd);
				return mv;
			}
		}
		// 根据员工名称查询员工id
		String staffName = pd.getString("staffName");
		if (null != staffName && !"".equals(staffName)) {
			List<PageData> staffIds = staffService.findstaffsBySome(pd);
			ArrayList<String> staffIdsArray = new ArrayList<String>();
			for (Iterator<PageData> iterator = staffIds.iterator(); iterator
					.hasNext();) {
				staffIdsArray.add(iterator.next().getString("staffId"));
			}
			if (staffIdsArray.size() > 0) {
				pd.put("staffIds", staffIdsArray);
			} else {
				page.setPd(pd);
				mv.addObject("userDiscounts", userDiscounts);
				mv.addObject("pd", pd);
				return mv;
			}
		}
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String isUsed = pd.getString("isUsed");
		if (null != isUsed && !"".equals(isUsed)) {
			pd.put("isUsed", isUsed.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		page.setPd(pd);
		userDiscounts = discountService.selectUserDiscounts(page);
		mv.addObject("userDiscounts", userDiscounts);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 根据门店编号医生姓名，客服姓名等
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findstaffProjectServiceStaffByStoreId")
	@ResponseBody
	public String findstaffProjectServiceStaffByStoreId() throws Exception {
		PageData pd = this.getPageData();
		String storeId = (String) pd.get("storeId");
		pd.put("DEPART_ID", "06");
		pd.put("STORE_ID", storeId);
		List<PageData> doctors = staffService.findstaffsByStoreId(pd);
		pd.put("DEPART_ID", "01");
		List<PageData> serviceStaffs = staffService.findstaffsByStoreId(pd);
		ArrayList<List<PageData>> al = new ArrayList();
		al.add(doctors);
		al.add(serviceStaffs);
		String jsonString = new ObjectMapper().writeValueAsString(al);
		return jsonString;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出QueryDiscounts到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 添加检索条件
		// 根据用户名、微信名查询用户id
		String name = pd.getString("name");
		if (null != name && !"".equals(name)) {
			ArrayList<Integer> uIdsArray = new ArrayList<Integer>();
			List<PageData> uIds = memberService.selectUsersByName(pd);
			for (Iterator<PageData> iterator = uIds.iterator(); iterator
					.hasNext();) {
				uIdsArray.add((Integer) iterator.next().get("uId"));
			}
			if (uIdsArray.size() > 0) {
				pd.put("uIds", uIdsArray);
			} else {
				uIdsArray.add(-1);
				pd.put("uIds", uIdsArray);
			}
		}
		// 根据优惠券名称查询优惠券id
		String discountName = pd.getString("discountName");
		if (null != discountName && !"".equals(discountName)) {
			ArrayList<String> disCountIdsArray = new ArrayList<String>();
			List<PageData> disCountIds = discountService
					.selectDisCountsByName(pd);
			for (Iterator<PageData> iterator = disCountIds.iterator(); iterator
					.hasNext();) {
				disCountIdsArray.add(iterator.next().getString("discountId"));
			}
			if (disCountIdsArray.size() > 0) {
				pd.put("discountIds", disCountIdsArray);
			} else {
				disCountIdsArray.add("-1");
				pd.put("discountIds", disCountIdsArray);
			}
		}
		// 根据员工名称查询员工id
		String staffName = pd.getString("staffName");
		if (null != staffName && !"".equals(staffName)) {
			List<PageData> staffIds = staffService.findstaffsBySome(pd);
			ArrayList<String> staffIdsArray = new ArrayList<String>();
			for (Iterator<PageData> iterator = staffIds.iterator(); iterator
					.hasNext();) {
				staffIdsArray.add(iterator.next().getString("staffId"));
			}
			if (staffIdsArray.size() > 0) {
				pd.put("staffIds", staffIdsArray);
			} else {
				staffIdsArray.add("-1");
				pd.put("staffIds", staffIdsArray);
			}
			System.out.println(pd.get(staffIds));
		}
		
		
		String isUsed = pd.getString("isUsed");
		if (null != isUsed && !"".equals(isUsed)) {
			pd.put("isUsed", isUsed.trim());
		}
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("姓名"); // 1
		titles.add("微信名"); // 2
		titles.add("优惠券名称"); // 3
		titles.add("优惠券所属组"); // 4
		titles.add("客服"); // 5
		titles.add("发放时间"); // 5
		titles.add("开始时间"); // 5
		titles.add("结束时间"); // 5
		titles.add("是否已经使用"); // 5
		dataMap.put("titles", titles);
		List<QueryUserDiscount> varOList = discountService
				.selectUserDiscountsAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getUser().getName()); // 1
			vpd.put("var2", varOList.get(i).getUser().getUserName()); // 2
			vpd.put("var3", varOList.get(i).getDiscount().getDISCOUNT_NAME()); // 3
			vpd.put("var4", varOList.get(i).getDiscountGroup().getGROUP_NAME()); // 4
			vpd.put("var5", varOList.get(i).getStaff().getSTAFF_NAME()); // 5
			vpd.put("var6", varOList.get(i).getCreateTime()); // 5
			vpd.put("var7", varOList.get(i).getStartTime()); // 5
			vpd.put("var8", varOList.get(i).getEndTime()); // 5
			if (varOList.get(i).getIsUsed() == 1) {
				vpd.put("var9", "已使用"); // 5
			} else {
				vpd.put("var9", "未使用"); // 5
			}
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	@RequestMapping(value = "/grantExcel")
	public ModelAndView grantExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出QueryDiscounts到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 添加检索条件
		String groupName = pd.getString("groupName");
		if (null != groupName && !"".equals(groupName)) {
			pd.put("groupName", groupName.trim());
		}
		String staffName = pd.getString("staffName");
		if (null != staffName && !"".equals(staffName)) {
			pd.put("staffName", staffName.trim());
		}
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("优惠卷组名称"); // 1
		titles.add("发放时间"); // 2
		titles.add("客服"); // 3
		dataMap.put("titles", titles);
		List<PageData> varOList = discountService
				.queryGrantDiscountsAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("groupName")); // 1
			vpd.put("var2", varOList.get(i).get("createTime")); // 3
			vpd.put("var3", varOList.get(i).get("staffName")); // 4
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	@RequestMapping(value="/grantlist")
	public ModelAndView grantlist(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String groupName = pd.getString("groupName");
		if (null != groupName && !"".equals(groupName)) {
			pd.put("groupName", groupName.trim());
		}
		String staffName = pd.getString("staffName");
		if (null != staffName && !"".equals(staffName)) {
			pd.put("staffName", staffName.trim());
		}
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		page.setPd(pd);
		List<PageData> grantDiscounts = discountService.queryGrantDiscountsListPage(page);
		mv.addObject("grantDiscounts", grantDiscounts);
		mv.addObject("pd", pd);
		mv.setViewName("querysatistics/grantdiscount_query");
		return mv;
	}
}
