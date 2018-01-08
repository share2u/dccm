package cn.ncut.controller.querystatictics;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.system.Staff;
import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.service.system.store.StoreManager;
import cn.ncut.service.user.order.OrderManager;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.PageData;

@Controller
@RequestMapping(value = "/statisticsServiceStaff")
public class StatisticsServiceStaffController extends BaseController {

	@Resource(name = "orderService")
	private OrderManager orderService;
	@Resource(name="storeService")
	private StoreManager storeService;
	@Resource(name = "staffService")
	private StaffManager staffService;
	
	@RequestMapping(value = "/statisticsService")
	public ModelAndView staticticsOrdersSum(HttpSession session) throws Exception {
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

		String STORE_ID  = pd.getString("STORE_ID"); // 关键词检索条件
		if (null != STORE_ID  && !"".equals(STORE_ID)) {
			pd.put("status", STORE_ID.trim());
		}	
		List<PageData>	storeList = storeService.findAllNames(pd);
		
		List<PageData> ordersMxSum = orderService.staticticsService(pd);
		HashMap<String, List<String>> hashMap = new HashMap<String,List<String>>();
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		for (PageData pageData : ordersMxSum) {
			String orderId = pageData.getString("SERVICE_STAFF_ID");
			List<String> orderList = Arrays.asList("0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");
			if(hashMap.containsKey(orderId)){
				orderList= hashMap.get(orderId);
			}else{
				//门店名称
				orderList.set(0, pageData.getString("STAFF_NAME"));
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
		mv.addObject("STORE_ID",pd.get("STORE_ID"));
		mv.addObject("storeList",storeList);
		mv.setViewName("querysatistics/serviceStaff_satistics");
		return mv;
	}
}

