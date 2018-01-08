package cn.ncut.controller.querystatictics;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
@RequestMapping(value = "/statisticsOrder")
public class StatisticsOrderController extends BaseController {

	@Resource(name = "orderService")
	private OrderManager orderService;
	@Resource(name="storeService")
	private StoreManager storeService;
	@Resource(name = "staffService")
	private StaffManager staffService;
	
	@RequestMapping(value = "/statisticsOrdersSum")
	public ModelAndView staticticsOrdersSum() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		List<PageData> ordersMxSum = orderService.staticticsOrdersSum(pd);
		HashMap<String, List<String>> hashMap = new HashMap<String,List<String>>();
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		for (PageData pageData : ordersMxSum) {
			String orderId = pageData.getString("storeId");
			List<String> orderList = Arrays.asList("0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");
			if(hashMap.containsKey(orderId)){
				orderList= hashMap.get(orderId);
			}else{
				//门店名称
				orderList.set(0, pageData.getString("storeName"));
				hashMap.put(orderId, orderList);
			}
			//订单总数量
			orderList.set(1, String.valueOf((long)(pageData.get("orderCount"))+Long.valueOf(orderList.get(1))));
			//订单总钱，数据库中实收金额
			orderList.set(2, String.valueOf(df.format(((BigDecimal)(pageData.get("payMoney"))).doubleValue()+Double.parseDouble(orderList.get(2)))));
			//各种状态下的钱
			switch ((int)pageData.get("orderStatus")) {
			case 0:
				orderList.set(3, String.valueOf((((long)pageData.get("orderCount")))));
				orderList.set(4, String.valueOf((((BigDecimal)pageData.get("payMoney")))));
				break;
			case 1:
				orderList.set(5, String.valueOf((long)(pageData.get("orderCount"))));
				orderList.set(6, String.valueOf((((BigDecimal)pageData.get("payMoney")))));
				break;
			case 2:
				orderList.set(7, String.valueOf((long)(pageData.get("orderCount"))));
				orderList.set(8, String.valueOf((((BigDecimal)pageData.get("payMoney")))));
				break;
			case 3:
				orderList.set(9, String.valueOf((long)(pageData.get("orderCount"))));
				orderList.set(10, String.valueOf((((BigDecimal)pageData.get("payMoney")))));
				break;
			case 4:
				orderList.set(11, String.valueOf((long)(pageData.get("orderCount"))));
				orderList.set(12, String.valueOf((((BigDecimal)pageData.get("payMoney")))));
				break;
			case 5:
				orderList.set(13, String.valueOf((((long)pageData.get("orderCount")))));
				orderList.set(14, String.valueOf((BigDecimal)(pageData.get("payMoney"))));
				break;
			case 6:
				orderList.set(15, String.valueOf((long)(pageData.get("orderCount"))));
				orderList.set(16, String.valueOf((((BigDecimal)pageData.get("payMoney")))));
				break;
			default:
				break;
			}
		}
		mv.addObject("pd", pd);
		mv.addObject("ordersSum", new ObjectMapper().writeValueAsString(hashMap));
		mv.setViewName("querysatistics/ordersCount_statistics");
		return mv;
	}
	
	                        
	@RequestMapping(value = "/staticticsOrderStaff")
	public ModelAndView staticticsOrderStaff() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		List<PageData> orders = orderService.staticticsOrderByStaff(pd);
		DecimalFormat    df   = new DecimalFormat("######0.00");   
	
		mv.addObject("orders", new ObjectMapper().writeValueAsString(orders));
		mv.setViewName("querysatistics/countOrderStaff_satistics");
		mv.addObject("pd", pd);
		return mv;
	}


	@RequestMapping(value = "/staticticsOrdersSource")
	public ModelAndView staticticsOrdersSource() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd =  new PageData();
		List<PageData>	storeList = storeService.findAllNames(pd);
	    pd = this.getPageData();
		pd.put("STAFF_ID", pd.get("staffNames"));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		
		
		List<PageData> ordersResource = orderService.staticticsOrdersSource(pd);
		ArrayList<ArrayList> xyData= new ArrayList<ArrayList>();
		ArrayList<String> xData= new ArrayList<String>();
		ArrayList<Integer> yData= new ArrayList<Integer>();
		for (PageData pageData : ordersResource) {
			String source = pageData.getString("URL");
			if(source.equals("0")){
				source ="微信下单";
			}else if(source.equals("1")) {
				source ="面对面下单";
			}else if(source.equals("3")){
				source ="药品";
			}
			xData.add(source);
			yData.add(((Long) pageData.get("sourceSum")).intValue());
		}
		xyData.add(xData);
		xyData.add(yData);
		mv.addObject("pd", pd);
		mv.addObject("storeList", storeList);
		
		mv.addObject("xyData", new ObjectMapper().writeValueAsString(xyData));
		mv.setViewName("querysatistics/ordersource_statistics");
		
		return mv;
	}

	

	@RequestMapping(value = "/statisticsOrdersPayMethod")
	public ModelAndView staticticsOrdersPayMethod() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		String firstDate = pd.getString("firstDate");
		if (null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		List<PageData> ordersMxPayMethod = orderService.staticticsOrdersPayMethod(pd);
		HashMap<String, ArrayList<String>> hashMap = new HashMap<String,ArrayList<String>>();
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		for (PageData pageData : ordersMxPayMethod) {
			String orderId = pageData.getString("storeId");
			ArrayList<String> orderMxList = new ArrayList<String>();
			//list初始化
			for (int i = 0; i < 9; i++) {
				orderMxList.add("0");
			}
			if(hashMap.containsKey(orderId)){
				orderMxList= hashMap.get(orderId);
			}else{
				//门店名称
				orderMxList.set(0, pageData.getString("storeName"));
				hashMap.put(orderId, orderMxList);
			}
			//订单各种状态下的实收总钱
			orderMxList.set(1, String.valueOf(df.format(((Double)(pageData.get("orderMxMoney"))).doubleValue()+Double.parseDouble(orderMxList.get(1)))));
			//各种支付方式下下的实收钱
			int payMethod;
			if(pageData.get("payMethod") instanceof Long){
				payMethod= ((Long)pageData.get("payMethod")).intValue();
			}else{
				payMethod=(int) pageData.get("payMethod");
				
			}
			switch (payMethod) {
			case 0:
				orderMxList.set(2, String.valueOf((((Double)pageData.get("orderMxMoney")))));
				break;
			case 1:
				orderMxList.set(3, String.valueOf((((Double)pageData.get("orderMxMoney")))));
				break;
			case 2:
				orderMxList.set(4, String.valueOf((((Double)pageData.get("orderMxMoney")))));
				break;
			case 3:
				orderMxList.set(5, String.valueOf((Double)(pageData.get("orderMxMoney"))));
				break;
			case 4:
				orderMxList.set(6, String.valueOf((((Double)pageData.get("orderMxMoney")))));
				break;
			case 5:
				orderMxList.set(7, String.valueOf((((Double)pageData.get("orderMxMoney")))));
				break;
			case 6:
				orderMxList.set(8, String.valueOf((((Double)pageData.get("orderMxMoney")))));
				break;
			default:
				break;
			}
		}
		mv.addObject("pd", pd);
		mv.addObject("ordersMxPayMethod", new ObjectMapper().writeValueAsString(hashMap));
		mv.setViewName("querysatistics/ordersMxPayMethod_statistics");
		return mv;
	}
	@RequestMapping(value = "/UserSourceSum")
	public ModelAndView userSourceSum() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		String firstDate = pd.getString("firstDate");
		if(null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		
		List<PageData> ordersUserResource = orderService.staticticsUserSource(pd);
		ArrayList<ArrayList> xyData= new ArrayList<ArrayList>();
		ArrayList<String> xData= new ArrayList<String>();
		ArrayList<Integer> yData= new ArrayList<Integer>();
		for (PageData pageData : ordersUserResource) {
			String city = pageData.getString("city");
			if(city ==null){
				city ="未知";
			}
			xData.add(city);
			yData.add(((Long) pageData.get("sourceSum")).intValue());
		}
		xyData.add(xData);
		xyData.add(yData);
		mv.addObject("pd", pd);
	
		mv.addObject("xyData", new ObjectMapper().writeValueAsString(xyData));
		mv.setViewName("querysatistics/ordersUserResource_statistics");
		return mv;
	}
	@RequestMapping(value = "/UserSourceCitySum")
	public ModelAndView userSourceCitySum(String province) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String firstDate = pd.getString("firstDate");
		if(null != firstDate && !"".equals(firstDate)) {
			pd.put("firstDate", firstDate.trim());
		}
		String lastDate = pd.getString("lastDate");
		if (null != lastDate && !"".equals(lastDate)) {
			pd.put("lastDate", lastDate.trim());
		}
		/*province =new String(province.getBytes("ISO-8859-1"),"utf-8");*/
		pd.put("province",province);
		System.out.println("点击的省份是"+province);
		List<PageData> ordersUserResource = orderService.staticticsUserSourceProvince(pd);
		ArrayList<ArrayList> xyData= new ArrayList<ArrayList>();
		ArrayList<String> xData= new ArrayList<String>();
		ArrayList<Integer> yData= new ArrayList<Integer>();
		for (PageData pageData : ordersUserResource) {
			String city = pageData.getString("city");
			if(city ==null){
				city ="未知";
			}
			String namewei=city.substring(city.length()-1);
			if(namewei !="州" && namewei !="区"){
				city=city+"市";
			}
			xData.add(city);
			yData.add(((Long) pageData.get("sourceSum")).intValue());
		}
		xyData.add(xData);
		xyData.add(yData);
		mv.addObject("pd", pd);
		mv.addObject("xyData", new ObjectMapper().writeValueAsString(xyData));
		mv.setViewName("querysatistics/ordersUserResourceCity_statistics");
		return mv;
	}

	
	@RequestMapping(value="/userConversion")
	public ModelAndView userConversion() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(orderService.selectWechatUserSum());
		list.add(orderService.selectRegistUserSum());
		list.add(orderService.selectCompleteUserSum());
		list.add(orderService.selectUniqueUserOrder());
		mv.addObject("list", new ObjectMapper().writeValueAsString(list));
		mv.setViewName("querysatistics/ordersUserConversion_statistics");
		return mv;
	}
}
