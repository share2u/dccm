package cn.ncut.controller.finance.customappoint;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;

import cn.ncut.service.finance.businesscontact.BusinesscontactManager;

import cn.ncut.entity.system.Staff;

import cn.ncut.service.finance.customappoint.CustomappointManager;
import cn.ncut.service.finance.serviceall.ServiceCostManager;
import cn.ncut.service.finance.serviceall.ServiceProjectManager;

import cn.ncut.service.system.creditrule.Credit_ruleManager;
import cn.ncut.service.user.credit.CreditManager;
import cn.ncut.service.user.credit.CreditMxManager;

import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.service.system.store.StoreManager;
import cn.ncut.service.system.user.impl.UserService;

import cn.ncut.service.user.member.MemberManager;
import cn.ncut.service.user.order.OrderManager;
import cn.ncut.service.user.order.OrderMxManager;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Const;
import cn.ncut.util.DateUtil;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;

/**     
 * 说明：预约表 创建人： 创建时间：2017-01-03
 */
@Controller
@RequestMapping(value = "/customappoint")
public class CustomappointController extends BaseController {

	String menuUrl = "customappoint/list.do"; // 菜单地址(权限用)
	@Resource(name = "customappointService")
	private CustomappointManager customappointService;

	@Resource(name = "memberService")
	private MemberManager memberService;

	@Resource(name = "orderService")
	private OrderManager orderService;
	@Resource(name = "credit_ruleService")
	private Credit_ruleManager credit_ruleService;

	@Resource(name = "staffService")
	private StaffManager staffService;

	@Resource(name = "creditService")
	private CreditManager creditService;
	@Resource(name = "creditmxService")
	private CreditMxManager creditmxService;
	@Resource(name = "businesscontactService")
	private BusinesscontactManager businesscontactService;
	@Resource(name = "ordermxService")
	private OrderMxManager ordermxService;
	@Resource(name = "storeService")
	private StoreManager storeService;

	@Resource(name = "servicecostService")
	private ServiceCostManager servicecostService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Customappoint");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CUSTOMAPPOINT_ID", this.get32UUID()); // 主键
		customappointService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 到店结束订单
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/endorder")
	public void endorder(PrintWriter out) throws Exception {
		double credit_money = 0.0;
		logBefore(logger, Jurisdiction.getUsername() + "结束订单Customappoint");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData customappoint = customappointService.findById(pd);// 获得该条记录
		Page p = new Page();
		p.setPd(customappoint);// 里面有orderid
		customappoint.put("UID", customappoint.get("U_ID"));
		// ///更改订单状态
		orderService.updateStatus4(customappoint);
		// ///计算积分（储值卡返点支付的不产生积分）
		PageData rule = credit_ruleService.findnew(pd);
		BigDecimal i = (BigDecimal) rule.get("CASH_TOCREDIT");
		int i2 = i.intValue();
		System.out.print(i2);

		PageData orderpd = orderService.findById(customappoint);// 得到订单信息
		 credit_money = Double.valueOf((String)orderpd.getString("PAY_MONEY"));//得到支付的金额
		//查询支付明细
		PageData fandianmxpd = ordermxService.findFandianByOrderId(orderpd);//这个订单的返点支付情况
		if(fandianmxpd!=null){//有返点支付的情况
				credit_money = Double.valueOf(credit_money) - Double.valueOf((String)fandianmxpd.getString("PAY_MONEY"));
		}
		//double dd = Double.valueOf(pay_money);
		double credit = credit_money / i2;// 积分
		int creditnew = (int) credit;// 积分的int型

		// 先查询该用户有没有积分
		PageData creditpd = creditService.findByUId(customappoint);
		if (creditpd != null) {// 有原积分
			// 查看积分
			/*
			 * int intcredit = (int) creditpd.get("CREDIT"); int
			 * intcredit=Integer.parseInt(usercredit);
			 */
			PageData creditmx = new PageData();
			creditmx.put("CREDITMX_ID", this.get32UUID());
			creditmx.put("CREDIT_ID", creditpd.get("CREDIT_ID"));
			// 原积分
			int originalcredit = (Integer) creditpd.get("CREDIT");
			int nowcredit = creditnew + originalcredit;
			creditmx.put("ORIGINAL_CREDIT", originalcredit);// 原积分
			creditmx.put("NOW_CREDIT", nowcredit);// 现积分
			creditmx.put("UID", customappoint.get("U_ID"));
			creditmx.put("CREATE_TIME", DateUtil.getTime()); // 创建时间
			creditmxService.save(creditmx);// 写积分明细
			creditpd.put("CREDIT", nowcredit);// 积分表的积分
			creditService.updateCreditByUid(creditpd);// 更新积分表积分
		} else {// 没有原积分
			PageData creditmx = new PageData();
			creditmx.put("CREDITMX_ID", this.get32UUID());
			creditmx.put("CREDIT_ID", this.get32UUID());
			creditmx.put("UID", customappoint.get("U_ID"));
			creditmx.put("ORIGINAL_CREDIT", 0);
			creditmx.put("NOW_CREDIT", creditnew);
			creditmx.put("CREATE_TIME", DateUtil.getTime()); // 创建时间
			creditmxService.save(creditmx);// 写积分明细
			PageData creditnew1 = new PageData();
			creditnew1.put("CREDIT_ID", creditmx.get("CREDIT_ID"));
			creditnew1.put("UID", customappoint.get("U_ID"));
			creditnew1.put("CREDIT", creditnew);
			creditnew1.put("STATUS", 0);
			creditService.save(creditnew1);// 写积分表
		}
		/*
		 * ///////////写门店业务往来 ///查询最新的业务往来规则 PageData businessrulepd =
		 * businesscontactService.findnew(pd); /////得到对门店的返点规则 Double d =
		 * (Double) businessrulepd.get("TOSTORE");//门店返点规则 ///查询这个订单的支付明细
		 * List<PageData> mxpdlist = ordermxService.list(p); //遍历mxpd
		 * 找到有没有用储值卡交钱的记录 for(PageData mxpd : mxpdlist){ int pay_method = (int)
		 * mxpd.get("PAY_METHOD"); double money = (double)
		 * mxpd.get("PAY_MONEY"); if(pay_method == 2){ money*d; }
		 * 
		 * }
		 */
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Customappoint");
//		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
//			return null;
//		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String date1 = pd.getString("APPOINT_TIME");// 预约时间
		// String date2= addDate(date1,1);//加1小时方法
		pd.put("EXPIRE_TIME", DateUtil.caculateGuoqiTime(date1));// 过期时间
		customappointService.edit(pd);
		// //////////修改订单表的预约时间
		PageData appointpd = customappointService.findById(pd);
		String orderid = appointpd.getString("ORDER_ID");
		System.out.print(orderid);
		// 修改订单表的recommend_time
		appointpd.put("RECOMMEND_TIME", pd.get("APPOINT_TIME"));
		appointpd.put("REMARK", pd.get("REMARK"));
		// 判断是否要修改订单状态
		if (addDate(pd.getString("APPOINT_TIME"), 1).compareTo(
				DateUtil.getTime()) > 0) {// 如果增加了一个小时的时间比现在的时间晚，说明订单还没过期
			// 修改订单状态为未完成
			orderService.updateStatus2(appointpd);

		}/* else {
			// 修改订单状态为已完成
			//查询之前的订单状态 如果订单状态为已过期
			orderService.updateStatus4(appointpd);
		}*/
		orderService.updateTime(appointpd);
		orderService.updateRemark(appointpd);
		// //////////
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 增加时间
	 * 
	 * @param
	 * @throws Exception
	 */
	public static String addDate(String day, int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, x);// 24小时制
		// cal.add(Calendar.HOUR, x);12小时制
		date = cal.getTime();
		System.out.println("front:" + date);
		cal = null;
		return format.format(date);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page, HttpSession session) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Customappoint");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");// 获得开始时间
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", lastStart + " 00:00:01");
		}
		String lastEnd = pd.getString("lastEnd");// 获得结束时间
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", lastEnd + " 23:59:59");
		}
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());
		List<PageData> storeList = storeService.findAllNames(pd);
		pd.put("STOREID", pd.get("STOREID"));
		// pd.put("STORE_ID", pd.get("STOREID"));
		page.setPd(pd);
		List<PageData> varList = customappointService.list(page); // 列出Customappoint列表
		List<PageData> varlist = new ArrayList<PageData>();
		for (PageData ppdd : varList) {

			String staffname = staffService.getStaffNameById((String) ppdd
					.get("SERVICE_STAFF_ID"));
			if (staffname != null) {
				ppdd.put("SERVICE_STAFF_ID", staffname);
			}
			// varlist.add(ppdd);

			varlist.add(ppdd);
		}

		mv.setViewName("user/customappoint/customappoint_list");
		mv.addObject("varList", varList);
		mv.addObject("storeList", storeList);
		mv.addObject("pd", pd);
		mv.addObject("STOREID", pd.get("STOREID"));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	/**判断订单状态
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/judgeStatus")
	public  void judgeStatus(HttpServletResponse response) throws Exception{
		
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData customappoint = customappointService.findById(pd);//获得该条记录
		Page p = new Page();
		p.setPd(customappoint);//里面有orderid
		customappoint.put("UID", customappoint.get("U_ID"));
		//先查询订单状态，如果为2，3则修改订单状态为4，否则不修改
				PageData orderpd2 = orderService.findById(customappoint);//查询订单状态
				int orderstatus = (int) orderpd2.get("ORDER_STATUS");
				if(orderstatus==2||orderstatus==3){
					String responseJson = "{\"result\":\"true\"}";
					response.getWriter().write(responseJson);
					
				}else{
					String responseJson = "{\"result\":\"false\"}";
					response.getWriter().write(responseJson);
				}
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/showToday")
	public ModelAndView showToday(Page page, HttpSession session)
			throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Customappoint");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BEGINTIME", weeHours(new Date(), 0));// 今天0点0分0秒
		pd.put("ENDTIME", weeHours(new Date(), 1));// 今天23点59分59秒
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());
		List<PageData> storeList = storeService.findAllNames(pd);
		pd.put("STOREID", pd.get("STOREID"));
		// pd.put("STORE_ID", pd.get("STOREID"));
		page.setPd(pd);

		List<PageData> varList = customappointService.listToday(page);// 列出Customappoint列表
		List<PageData> varlist = new ArrayList<PageData>();
		for (PageData ppdd : varList) {
			PageData staff_pd = staffService.queryById(ppdd
					.getString("SERVICE_STAFF_ID"));
			if (staff_pd == null) {
				ppdd.put("SERVICE_STAFF_NAME", "微信端自助下单");
			} else {
				ppdd.put("SERVICE_STAFF_NAME", staff_pd.getString("STAFF_NAME"));
			}

			varlist.add(ppdd);
		}

		mv.setViewName("user/customappoint/showToday");
		mv.addObject("varList", varlist);
		mv.addObject("storeList", storeList);
		mv.addObject("pd", pd);
		mv.addObject("STOREID", pd.get("STOREID"));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	public static Date weeHours(Date date, int flag) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		// 时分秒（毫秒数）
		long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second
				* 1000;
		// 凌晨00:00:00
		cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

		if (flag == 0) {
			return cal.getTime();
		} else if (flag == 1) {
			// 凌晨23:59:59
			cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000
					+ 59 * 60 * 1000 + 59 * 1000);
		}
		return cal.getTime();
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("user/customappoint/customappoint_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = customappointService.findById(pd); // 根据ID读取
		// 还要获得STAFF_ID
		PageData orderpd = orderService.findById(pd);
		pd.put("STAFF_ID", orderpd.get("STAFF_ID"));
		mv.setViewName("user/customappoint/customappoint_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("orderpd", orderpd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Customappoint");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			customappointService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 批量到店
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/endorderAll")
	@ResponseBody
	public Object endorderAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量到店Customappoint");

		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			customappointService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Customappoint到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("订单号"); // 1
		titles.add("用户编号"); // 2
		titles.add("预约时间"); // 3
		titles.add("客服"); // 4
		titles.add("验证码"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = customappointService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("ORDER_ID")); // 1
			vpd.put("var2", varOList.get(i).get("U_ID").toString()); // 2
			vpd.put("var3", varOList.get(i).getString("APPOINT_TIME")); // 3
			vpd.put("var4", varOList.get(i).get("SERVICE_STAFF_ID").toString()); // 4
			vpd.put("var5", varOList.get(i).get("APPOINT_CODE").toString()); // 5
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,
				true));
	}

	/**
	 * 大表转换时间
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeTime")
	public void changeTime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PageData pd = new PageData();
		pd = this.getPageData();
		String id = (String) pd.get("ID");
		String new_date = convertDate((String) pd.get("THE_DATE"), id);
		// 日期比较，如果s>=e 返回true 否则返回false
		Boolean flag = false;
		if (DateUtil.compareToDate(new_date, DateUtil.getDay()) > 0) {
			flag = true;
		} else if (DateUtil.compareToDate(new_date, DateUtil.getDay()) == 0
				&& Integer.parseInt(id.length() == 4 ? id.substring(0, 2) : id
						.substring(0, 1)) > new Date().getHours() - 1) {
			flag = true;
		} else {
			flag = false;
		}
		String msg = "";
		if (flag) {
			msg = "ok";
		} else {
			msg = "error";
		}
		String hour = id.length() == 4 ? id.substring(0, 2) : ("0" + id
				.substring(0, 1));
		String all = new_date + " " + hour + ":00:00";
		String responseJson = "{\"selectTime\":\"" + all + "\",\"msg\":\""
				+ msg + "\"}";
		response.getWriter().write(responseJson);
		// response.getWriter().write(new_date);
	}

	public String convertDate(String selectedtime, String idtime)
			throws Exception {

		// 通过日期获得数据库中周几减一的数字
		String week = new SimpleDateFormat("E").format(DateUtil
				.fomatDate(selectedtime));
		int week_which = DateUtil
				.ChineseToNum(week.substring(week.length() - 1));
		int servicetime_week = Integer.parseInt(idtime.split(",")[1]);
		return DateUtil.getEveryDay(selectedtime,
				(servicetime_week - week_which));
	}

	/**
	 * 去打印订单页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goPrintOrder")
	public ModelAndView goPrintOrder() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd = orderService.findById(pd);
		pd.putAll(memberService.findById(pd));
		pd.putAll(customappointService.findByOrderId(pd));
		pd.put("STAFF_NAME", staffService.findById(pd).getString("STAFF_NAME"));
		pd.put("STAFF_ID", pd.get("SERVICE_STAFF_ID"));
		pd.put("SERVICE_STAFF_NAME",
				staffService.findById(pd).getString("STAFF_NAME"));
		pd.put("ORDER_PRINTER", Jurisdiction.getUsername());
		pd.put("STORE_NAME", storeService.findNameById(pd));
		pd.put("PNAME", servicecostService.findById(pd).getString("PNAME"));
		pd.put("ORDER_PRINT_TIME", DateUtil.getTime());
//ORDER_STATUS=2, SERVICECOST_ID=595 UID=1551
		pd.put("COUNT", orderService.findCountByUser(pd));
		if (pd.get("WECHAT_PHONE") == null)
			pd.put("WECHAT_PHONE", pd.getString("phone"));
		if (pd.get("WECHAT_NAME") == null)
			pd.put("WECHAT_NAME", pd.getString("name"));
		pd.put("PAY_METHOD", ordermxService.findAllByOrderId(pd).get(0).get("PAY_METHOD")) ;
		mv.setViewName("user/customappoint/print_order");
		mv.addObject("msg", "printOrder");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 打印订单
	 */
	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/printOrder")
	public ModelAndView printOrder() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "打印订单");

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		customappointService.findById(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

}
