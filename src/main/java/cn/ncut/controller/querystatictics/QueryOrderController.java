package cn.ncut.controller.querystatictics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.QueryOrder;
import cn.ncut.entity.system.Role;
import cn.ncut.entity.system.Staff;
import cn.ncut.entity.wechat.pojo.WeChatPayDetail;
import cn.ncut.service.finance.serviceall.impl.ServiceCostService;
import cn.ncut.service.system.depart.DepartManager;
import cn.ncut.service.system.role.RoleManager;
import cn.ncut.service.system.servicemodule.ServicemoduleManager;
import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.service.system.store.StoreManager;
import cn.ncut.service.system.user.impl.UserService;
import cn.ncut.service.user.member.impl.MemberService;
import cn.ncut.service.user.order.OrderManager;
import cn.ncut.service.user.order.OrderMxManager;
import cn.ncut.service.user.order.impl.OrderService;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;

@Controller
@RequestMapping(value = "/queryOrder")
public class QueryOrderController extends BaseController {

	String menuUrl = "queryOrder/list.do"; // 菜单地址(权限用)
	@Resource(name = "staffService")
	private StaffManager staffService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "orderService")
	private OrderManager orderService;

	@Resource(name = "ordermxService")
	private OrderMxManager orderMxService;

	@Resource(name = "servicemoduleService")
	private ServicemoduleManager servicemoduleService;
	@Resource(name = "memberService")
	private MemberService memberService;

	@RequestMapping(value = "/list")
	public ModelAndView listStaffs(Page page, HttpSession session)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<QueryOrder> orders = null;
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		List<PageData> serviceModules = servicemoduleService.listAll0(pd);

		mv.addObject("serviceModules", serviceModules);
		mv.setViewName("querysatistics/order_query");

		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		String storeId = sessionstaff.getSTORE_ID();
		String mId = storeId.substring(0, 1);// 0,0,1.
		String store = pd.getString("store");
		if (mId.equals("0")) {
			mId = storeId.substring(0, 2);// 01,02
			store = storeId;// 0201
			pd.put("mId", mId);
			pd.put("store", store);
		} else {
			// 总店张
			mId = pd.getString("mId");
			pd.put("mId", mId);
			store = pd.getString("store");
		}

		// 添加检索条件
		if (null != store && !"".equals(store)) {
			pd.put("store", store.trim());
		} else {
			if (null != mId && !"".equals(mId)) {
				pd.put("mId", mId.trim());
				List<PageData> storeList = storeService.selectStoresBymId(pd);
				ArrayList<String> storeArray = new ArrayList<>();
				for (Iterator<PageData> ite = storeList.iterator(); ite
						.hasNext();) {
					PageData next = ite.next();
					storeArray.add((String) next.get("STORE_ID"));
				}
				if (storeArray.size() > 0) {
					pd.put("storeIds", storeArray);
				} else {
					page.setPd(pd);
					mv.addObject("orders", orders);
					mv.addObject("pd", pd);
					return mv;
				}
			}
		}

		String payMethod = pd.getString("payMethod");
		if (null != payMethod && !"".equals(payMethod)) {
			// 根据pmathod 查询 订单明细中的order_id,再根据orderids 查询订单
			pd.put("payMethod", payMethod.trim());
			List<PageData> orderIds = orderService.queryOrderMxBypMethod(pd);
			ArrayList<String> orderIdsArray = new ArrayList<String>();
			for (Iterator<PageData> ite = orderIds.iterator(); ite.hasNext();) {
				PageData next = ite.next();
				orderIdsArray.add(next.getString("orderId"));
			}
			if (orderIdsArray.size() > 0) {
				pd.put("orderIds", orderIdsArray);
			} else {
				page.setPd(pd);
				mv.addObject("orders", orders);
				mv.addObject("pd", pd);
				return mv;
			}
		}

		String projectName = pd.getString("projectName");

		if (null != projectName && !"".equals(projectName)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("projectName", projectName.trim());
			List<PageData> serviceCosts = orderService
					.queryServiceCostByPName(pd);
			ArrayList<Integer> serviceCostArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = serviceCosts.iterator(); ite
					.hasNext();) {
				PageData next = ite.next();
				serviceCostArray.add((Integer) next.get("serviceCostId"));
			}
			if (serviceCostArray.size() > 0) {
				pd.put("projectId", serviceCostArray);
			} else {
				page.setPd(pd);
				mv.addObject("orders", orders);
				mv.addObject("pd", pd);
				return mv;
			}
		}
		String userPhone = pd.getString("userPhone");

		if (null != userPhone && !"".equals(userPhone)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("userPhone", userPhone.trim());
			List<PageData> users = memberService.selectUsersByName(pd);
			ArrayList<Integer> usersArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = users.iterator(); ite.hasNext();) {
				PageData next = ite.next();
				usersArray.add((Integer) next.get("uId"));
			}
			if (usersArray.size() > 0) {
				pd.put("uIds", usersArray);
			} else {
				page.setPd(pd);
				mv.addObject("orders", orders);
				mv.addObject("pd", pd);
				return mv;
			}
		}
		String doctorName = pd.getString("doctorName");

		if (null != doctorName && !"".equals(doctorName)) {
			pd.put("doctorName", doctorName.trim());
		}
		String staffName = pd.getString("staffName");
		if (null != staffName && !"".equals(staffName)) {
			pd.put("staffName", staffName.trim());
		}
		// serviceCost中对应serviceCostId，找到对应
		String isFirst = pd.getString("isFirst");
		if (null != isFirst && !"".equals(isFirst)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("isFirst", isFirst.trim());
			List<PageData> serviceCosts = orderService
					.queryServiceCostByPName(pd);
			ArrayList<Integer> serviceCostArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = serviceCosts.iterator(); ite
					.hasNext();) {
				PageData next = ite.next();
				serviceCostArray.add((Integer) next.get("serviceCostId"));
			}
			if (serviceCostArray.size() > 0) {
				pd.put("projectId", serviceCostArray);
			} else {
				page.setPd(pd);
				mv.addObject("orders", orders);
				mv.addObject("pd", pd);
				return mv;
			}
		}

		String url = pd.getString("url");
		if (null != url && !"".equals(url)) {
			pd.put("url", url.trim());
		}
		String orderStatus = pd.getString("orderStatus");
		if (null != orderStatus && !"".equals(orderStatus)) {
			pd.put("orderStatus", orderStatus.trim());
		}
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		String yuyuefirstDate = pd.getString("yuyuefirstDate");
		if (null != yuyuefirstDate && !"".equals(yuyuefirstDate)) {
			pd.put("yuyuefirstDate", yuyuefirstDate.trim());
		}
		String yuyuelastDate = pd.getString("yuyuelastDate");
		if (null != yuyuelastDate && !"".equals(yuyuelastDate)) {
			pd.put("yuyuelastDate", yuyuelastDate.trim());
		}

		page.setPd(pd);
		orders = orderService.quaryAllOrder(page);
		mv.addObject("orders", orders);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出QueryOrders到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 添加检索条件
		String mId = pd.getString("mId");
		String store = pd.getString("store");
		if (null != store && !"".equals(store)) {
			pd.put("store", store.trim());
		} else {
			if (null != mId && !"".equals(mId)) {
				pd.put("mId", mId.trim());
				List<PageData> stores = storeService.selectStoresBymId(pd);
				ArrayList<String> storeArray = new ArrayList<>();
				for (Iterator<PageData> ite = stores.iterator(); ite.hasNext();) {
					PageData next = ite.next();
					storeArray.add((String) next.get("STORE_ID"));
				}
				if (storeArray.size() > 0) {
					pd.put("storeIds", storeArray);
				} else {
					storeArray.add("-1");
					pd.put("storeIds", storeArray);
				}

			}
		}

		String payMethod = pd.getString("payMethod");
		if (null != payMethod && !"".equals(payMethod)) {
			// 根据pmathod 查询 订单明细中的order_id,再根据orderids 查询订单
			pd.put("payMethod", payMethod.trim());
			List<PageData> orderIds = orderService.queryOrderMxBypMethod(pd);
			ArrayList<String> orderIdsArray = new ArrayList<String>();
			for (Iterator<PageData> ite = orderIds.iterator(); ite.hasNext();) {
				PageData next = ite.next();
				orderIdsArray.add(next.getString("orderId"));
			}
			if (orderIdsArray.size() > 0) {
				pd.put("orderIds", orderIdsArray);
			} else {
				orderIdsArray.add("-1");
				pd.put("orderIds", orderIdsArray);
			}
		}

		String projectName = pd.getString("projectName");
		if (null != projectName && !"".equals(projectName)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("projectName", projectName.trim());
			List<PageData> serviceCosts = orderService
					.queryServiceCostByPName(pd);
			ArrayList<Integer> serviceCostArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = serviceCosts.iterator(); ite
					.hasNext();) {
				PageData next = ite.next();
				serviceCostArray.add((Integer) next.get("serviceCostId"));
			}
			if (serviceCostArray.size() > 0) {
				pd.put("projectId", serviceCostArray);
			} else {
				serviceCostArray.add(-1);
				pd.put("projectId", serviceCostArray);
			}

		}
		String userPhone = pd.getString("userPhone");

		if (null != userPhone && !"".equals(userPhone)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("userPhone", userPhone.trim());
			List<PageData> users = memberService.selectUsersByName(pd);
			ArrayList<Integer> usersArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = users.iterator(); ite.hasNext();) {
				PageData next = ite.next();
				usersArray.add((Integer) next.get("uId"));
			}
			if (usersArray.size() > 0) {
				pd.put("uIds", usersArray);
			} else {
				usersArray.add(-1);
				pd.put("uIds", usersArray);
			}
		}
		String doctorName = pd.getString("doctorName");

		if (null != doctorName && !"".equals(doctorName)) {
			pd.put("doctorName", doctorName.trim());
		}
		String staffName = pd.getString("staffName");
		if (null != staffName && !"".equals(staffName)) {
			pd.put("staffName", staffName.trim());
		}

		// serviceCost中对应serviceCostId，找到对应
		String isFirst = pd.getString("isFirst");
		if (null != isFirst && !"".equals(isFirst)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("isFirst", isFirst.trim());
			List<PageData> serviceCosts = orderService
					.queryServiceCostByPName(pd);
			ArrayList<Integer> serviceCostArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = serviceCosts.iterator(); ite
					.hasNext();) {
				PageData next = ite.next();
				serviceCostArray.add((Integer) next.get("serviceCostId"));
			}
			if (serviceCostArray.size() > 0) {
				pd.put("projectId", serviceCostArray);
			} else {
				serviceCostArray.add(-1);
				pd.put("projectId", serviceCostArray);
			}
		}
		String url = pd.getString("url");
		if (null != url && !"".equals(url)) {
			pd.put("url", url.trim());
		}
		String orderStatus = pd.getString("orderStatus");
		if (null != orderStatus && !"".equals(orderStatus)) {
			pd.put("orderStatus", orderStatus.trim());
		}
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		String yuyuefirstDate = pd.getString("yuyuefirstDate");
		if (null != yuyuefirstDate && !"".equals(yuyuefirstDate)) {
			pd.put("yuyuefirstDate", yuyuefirstDate.trim());
		}
		String yuyuelastDate = pd.getString("yuyuelastDate");
		if (null != yuyuelastDate && !"".equals(yuyuelastDate)) {
			pd.put("yuyuelastDate", yuyuelastDate.trim());
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("订单号"); // 1
		titles.add("微信姓名"); // 1
		titles.add("微信姓名昵称"); // 1
		titles.add("微信手机号"); // 2
		titles.add("患者姓名"); // 2
		titles.add("患者手机号"); // 2
		titles.add("门店"); // 3
		titles.add("科室"); // 3
		titles.add("服务项目"); // 4
		titles.add("医生"); // 5
		titles.add("创建时间"); // 6
		titles.add("预约类型");// 7
		titles.add("订单状态"); // 8
		titles.add("预约时间"); // 9
		titles.add("应收金额"); // 10
		titles.add("折扣"); // 11
		titles.add("优惠券金额"); // 12
		titles.add("实收金额"); // 13
		titles.add("微信支付"); // 14
		titles.add("支付宝支付"); // 15
		titles.add("储值卡余额支付"); // 16
		titles.add("现金支付"); // 17
		titles.add("银行卡支付"); // 18
		titles.add("钱包支付"); // 19
		titles.add("储值卡返点支付"); // 20
		titles.add("订单来源"); // 21
		titles.add("退款金额"); // 22
		titles.add("客服"); // 23
		titles.add("备注"); // 24
		dataMap.put("titles", titles);
		List<QueryOrder> varOList = orderService.quaryAllOrder(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getOrderId()); // 1
			vpd.put("var2", varOList.get(i).getUser().getName()); // 1
			vpd.put("var3", varOList.get(i).getUser().getUserName()); // 2
			vpd.put("var4", varOList.get(i).getUser().getPhone()); // 1
			vpd.put("var5", varOList.get(i).getWeChatName()); // 2
			vpd.put("var6", varOList.get(i).getWeChatPhone()); // 2
			vpd.put("var7", varOList.get(i).getStore().getSTORE_NAME()); // 3
			if (varOList.get(i).getServiceCostId() == -2){
				vpd.put("var8", "药品"); // 4
			}else if (varOList.get(i).getServiceCostId() == -3){
				vpd.put("var8", "消耗品"); // 4
			}else if (varOList.get(i).getServiceCostId() == -1) {
				vpd.put("var8", "退款");
			}else{
				vpd.put("var8", varOList.get(i).getServiceCost()
						.getF2serviceCategory().getCATEGORY_NAME());
			}
			if (varOList.get(i).getServiceCostId() == -2){
				vpd.put("var9", "药品"); // 4
			}else if (varOList.get(i).getServiceCostId() == -3){
				vpd.put("var9", "消耗品"); // 4
			}else if (varOList.get(i).getServiceCostId() == -1) {
				vpd.put("var9", "退款");
			}else{
				vpd.put("var9", varOList.get(i).getServiceCost()
						.getServiceProject().getpName());
			}
			vpd.put("var10", varOList.get(i).getStaff().getSTAFF_NAME()); // 5
			vpd.put("var11", varOList.get(i).getCreateTime()); // 5
			if (varOList.get(i).getServiceCostId() == -1)  {
				vpd.put("var12", "退款");
			}else if( varOList.get(i).getServiceCostId() == -2){
				vpd.put("var12", "药品");
			}else if( varOList.get(i).getServiceCostId() == -3){
				vpd.put("var12", "消耗品");
			}else if (varOList.get(i).getServiceCostId() != -1) {
				switch (varOList.get(i).getServiceCost().getIsFirst()) {
				case 0:
					vpd.put("var12", "初诊");
					break;
				case 1:
					vpd.put("var12", "复诊");
					break;
				case 2:
					vpd.put("var12", "课程");
					break;
				default:
					vpd.put("var12", "未知");
					break;
				}
			}
			switch (varOList.get(i).getOrderStatus()) {
			case 0:
				vpd.put("var13", "待支付"); // 5
				break;
			case 1:
				vpd.put("var13", "已关闭"); // 5
				break;
			case 2:
				vpd.put("var13", "已预约"); // 5
				break;
			case 3:
				vpd.put("var13", "已过期"); // 5
				break;
			case 4:
				vpd.put("var13", "待评价"); // 5
				break;
			case 5:
				vpd.put("var13", "已完成"); // 5
				break;
			case 6:
				vpd.put("var13", "已退款"); // 5
				break;
			default:
				vpd.put("var13", "未知"); // 5
				break;
			}
			vpd.put("var14", varOList.get(i).getRecommendTime()); // 5
			vpd.put("var15", varOList.get(i).getOrderMoney()); // 5
			vpd.put("var16", varOList.get(i).getProportion()); // 5
			if ("" != varOList.get(i).getDisCountId()
					&& null != varOList.get(i).getDisCountId()) {
				vpd.put("var17", varOList.get(i).getDisCountId()); // 5
			} else {
				vpd.put("var17", "0.00"); // 5
			}
			vpd.put("var18", varOList.get(i).getPayMoney()); // 5
			vpd.put("var19", String.valueOf(0.00)); // 5
			vpd.put("var20", String.valueOf(0.00)); // 5
			vpd.put("var21", String.valueOf(0.00)); // 5
			vpd.put("var22", String.valueOf(0.00)); // 5
			vpd.put("var23", String.valueOf(0.00)); // 5
			vpd.put("var24", String.valueOf(0.00)); // 5
			vpd.put("var25", String.valueOf(0.00)); // 5
			// 支付方式的输出，把每个方式都写上0，然后有哪种方式的话再改变
			Set<WeChatPayDetail> payDetails = varOList.get(i).getPayDetails();

			for (WeChatPayDetail weChatPayDetail : payDetails) {
				switch (weChatPayDetail.getPayMethod()) {
				case 0:
					vpd.put("var19", weChatPayDetail.getPayMoney());
					break;
				case 1:
					vpd.put("var20", weChatPayDetail.getPayMoney());
					break;
				case 2:
					vpd.put("var21", weChatPayDetail.getPayMoney());
					break;
				case 3:
					vpd.put("var22", weChatPayDetail.getPayMoney());
					break;
				case 4:
					vpd.put("var23", weChatPayDetail.getPayMoney());
					break;
				case 5:
					vpd.put("var24", weChatPayDetail.getPayMoney());
					break;
				case 6:
					vpd.put("var25", weChatPayDetail.getPayMoney());
					break;
				}
			}
			switch (varOList.get(i).getUrl()) {
			case "0":
				vpd.put("var26", "线上付款");
				break;
			case "1":
				vpd.put("var26", "面对面付款");
				break;
			case "2":
				vpd.put("var26", "退款手续费");
				break;
			default:
				vpd.put("var26", "未知");
				break;
			}
			if (null != varOList.get(i).getRefund()) {
				vpd.put("var27", String.valueOf(varOList.get(i).getRefund())); // 5
			} else {
				vpd.put("var27", "无退款"); // 5
			}
			vpd.put("var28", varOList.get(i).getServiceStaff().getSTAFF_NAME()); // 5
			vpd.put("var29", varOList.get(i).getRemark()); // 5
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@RequestMapping(value = "/sum")
	@ResponseBody
	public String sum() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "查询订单营业额统计");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 添加检索条件
		String mId = pd.getString("mId");
		String store = pd.getString("store");
		if (null != store && !"".equals(store)) {
			pd.put("store", store.trim());
		} else {
			if (null != mId && !"".equals(mId)) {
				pd.put("mId", mId.trim());
				List<PageData> stores = storeService.selectStoresBymId(pd);
				ArrayList<String> storeArray = new ArrayList<>();
				for (Iterator<PageData> ite = stores.iterator(); ite.hasNext();) {
					PageData next = ite.next();
					storeArray.add((String) next.get("STORE_ID"));
				}
				if (storeArray.size() > 0) {
					pd.put("storeIds", storeArray);
				} else {
					storeArray.add("-1");
					pd.put("storeIds", storeArray);
				}

			}
		}

		String payMethod = pd.getString("payMethod");
		if (null != payMethod && !"".equals(payMethod)) {
			// 根据pmathod 查询 订单明细中的order_id,再根据orderids 查询订单
			pd.put("payMethod", payMethod.trim());
			List<PageData> orderIds = orderService.queryOrderMxBypMethod(pd);
			ArrayList<String> orderIdsArray = new ArrayList<String>();
			for (Iterator<PageData> ite = orderIds.iterator(); ite.hasNext();) {
				PageData next = ite.next();
				orderIdsArray.add(next.getString("orderId"));
			}
			if (orderIdsArray.size() > 0) {
				pd.put("orderIds", orderIdsArray);
			} else {
				orderIdsArray.add("-1");
				pd.put("orderIds", orderIdsArray);
			}
		}

		String projectName = pd.getString("projectName");
		if (null != projectName && !"".equals(projectName)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("projectName", projectName.trim());
			List<PageData> serviceCosts = orderService
					.queryServiceCostByPName(pd);
			ArrayList<Integer> serviceCostArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = serviceCosts.iterator(); ite
					.hasNext();) {
				PageData next = ite.next();
				serviceCostArray.add((Integer) next.get("serviceCostId"));
			}
			if (serviceCostArray.size() > 0) {
				pd.put("projectId", serviceCostArray);
			} else {
				serviceCostArray.add(-1);
				pd.put("projectId", serviceCostArray);
			}

		}
		String userPhone = pd.getString("userPhone");

		if (null != userPhone && !"".equals(userPhone)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("userPhone", userPhone.trim());
			List<PageData> users = memberService.selectUsersByName(pd);
			ArrayList<Integer> usersArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = users.iterator(); ite.hasNext();) {
				PageData next = ite.next();
				usersArray.add((Integer) next.get("uId"));
			}
			if (usersArray.size() > 0) {
				pd.put("uIds", usersArray);
			} else {
				usersArray.add(-1);
				pd.put("uIds", usersArray);
			}
		}
		String doctorName = pd.getString("doctorName");

		if (null != doctorName && !"".equals(doctorName)) {
			pd.put("doctorName", doctorName.trim());
		}
		String staffName = pd.getString("staffName");
		if (null != staffName && !"".equals(staffName)) {
			pd.put("staffName", staffName.trim());
		}

		// serviceCost中对应serviceCostId，找到对应
		String isFirst = pd.getString("isFirst");
		if (null != isFirst && !"".equals(isFirst)) {
			// 根据pname 查询 project_id,再根据pid 查询costid
			pd.put("isFirst", isFirst.trim());
			List<PageData> serviceCosts = orderService
					.queryServiceCostByPName(pd);
			ArrayList<Integer> serviceCostArray = new ArrayList<Integer>();
			for (Iterator<PageData> ite = serviceCosts.iterator(); ite
					.hasNext();) {
				PageData next = ite.next();
				serviceCostArray.add((Integer) next.get("serviceCostId"));
			}
			if (serviceCostArray.size() > 0) {
				pd.put("projectId", serviceCostArray);
			} else {
				serviceCostArray.add(-1);
				pd.put("projectId", serviceCostArray);
			}
		}
		String url = pd.getString("url");
		if (null != url && !"".equals(url)) {
			pd.put("url", url.trim());
		}
		String orderStatus = pd.getString("orderStatus");
		if (null != orderStatus && !"".equals(orderStatus)) {
			pd.put("orderStatus", orderStatus.trim());
		}
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		String yuyuefirstDate = pd.getString("yuyuefirstDate");
		if (null != yuyuefirstDate && !"".equals(yuyuefirstDate)) {
			pd.put("yuyuefirstDate", yuyuefirstDate.trim());
		}
		String yuyuelastDate = pd.getString("yuyuelastDate");
		if (null != yuyuelastDate && !"".equals(yuyuelastDate)) {
			pd.put("yuyuelastDate", yuyuelastDate.trim());
		}
		List<QueryOrder> varOList = orderService.quaryAllOrder(pd);

		ArrayList<String> orderSumIdsArray = new ArrayList<String>();
		for (Iterator<QueryOrder> iterator = varOList.iterator(); iterator
				.hasNext();) {
			QueryOrder queryOrder = iterator.next();
			orderSumIdsArray.add(queryOrder.getOrderId());
		}
		PageData pdd = new PageData();
		if (orderSumIdsArray.size() > 0) {
			pdd.put("ordersSumIds", orderSumIdsArray);
		} else {
			orderSumIdsArray.add("-1");
			pdd.put("ordersSumIds", orderSumIdsArray);
		}
		Map<String, List<PageData>> jsonMap = new HashMap<String, List<PageData>>();
		// 查询订单各项总额
		List<PageData> orderSum = orderService.queryOrderSum(pdd);
		// 根据查询出来的ids去订单明细中查询各种支付方式的金额总和
		List<PageData> orderMxSum = orderService.queryOrderMxSum(pdd);
		jsonMap.put("orderSum", orderSum);
		jsonMap.put("orderMxSum", orderMxSum);
		String jsonString = new ObjectMapper().writeValueAsString(jsonMap);
		return jsonString;
	}

}
