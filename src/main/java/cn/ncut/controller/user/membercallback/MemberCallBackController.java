package cn.ncut.controller.user.membercallback;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Staff;
import cn.ncut.entity.system.UserDiscount;
import cn.ncut.entity.system.UserDiscountGroup;
import cn.ncut.util.AppUtil;
import cn.ncut.util.BigDecimalUtil;
import cn.ncut.util.Const;
import cn.ncut.util.DateUtil;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.Tools;
import cn.ncut.util.UuidUtil;
import cn.ncut.util.wechat.PrimaryKeyGenerator;
import cn.ncut.util.wechat.TimeAdjust;
import cn.ncut.service.finance.customappoint.CustomappointManager;
import cn.ncut.service.finance.discount.DiscountManager;
import cn.ncut.service.finance.serviceall.ServiceCostManager;
import cn.ncut.service.finance.serviceall.ServiceProjectManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.staff.StaffManager;

import cn.ncut.service.user.member.MemberManager;

import cn.ncut.service.system.store.StoreManager;

import cn.ncut.service.user.membercallback.MemberCallBackManager;
import cn.ncut.service.user.order.OrderManager;
import cn.ncut.service.user.order.OrderMxManager;
import cn.ncut.service.user.usercategory.UsercategoryManager;
import cn.ncut.service.user.userdiscount.UserDiscountManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 说明：用户回电 创建人：FH Q313596790 创建时间：2016-12-19
 */
@Controller
@RequestMapping(value = "/membercallback")
public class MemberCallBackController extends BaseController {

	String menuUrl = "membercallback/list.do"; // 菜单地址(权限用)
	@Resource(name = "membercallbackService")
	private MemberCallBackManager membercallbackService;
	@Resource(name = "staffService")
	private StaffManager staffService;
	@Resource(name = "servicecostService")
	private ServiceCostManager servicecostService;
	@Resource(name = "orderService")
	private OrderManager orderService;

	@Resource(name = "ordermxService")
	private OrderMxManager ordermxService;
	@Resource(name = "memberService")
	private MemberManager memberService;

	@Resource(name = "customappointService")
	private CustomappointManager customappointService;

	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "usercategoryService")
	private UsercategoryManager usercategoryService;

	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;

	@Resource(name = "serviceprojectService")
	private ServiceProjectManager serviceProjectService;
	@Resource(name = "discountService")
	private DiscountManager discountService;

	@Resource(name = "serviceprojectService")
	private ServiceProjectManager serviceprojectService;
	@Resource(name = "userdiscountService")
	private UserDiscountManager userdiscountService;
	
	
	
	

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增MemberCallBack");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MEMBERCALLBACK_ID", this.get32UUID()); // 主键
		/*
		 * pd.put("UID", "0"); //uid pd.put("NAME", ""); //客户姓名
		 * pd.put("STORE_ID", ""); //门店编号 pd.put("STAFF_ID", ""); //员工编号
		 * pd.put("PHONE", ""); //电话号码
		 */membercallbackService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除MemberCallBack");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		membercallbackService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 大表转换时间
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeTheTime")
	public void changeTheTime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PageData pd = new PageData();
		pd = this.getPageData();
		String id = (String) pd.get("Id");
		String the_date = (String) pd.get("THE_DATE");
		if (id != null && the_date != null) {
			String new_date = convertDate(the_date, id);
			System.out.print(new_date);
			// response.getWriter().write("{\"s\":" + "\"" +new_date + "\"}");
			response.getWriter().write(new_date);
		} else {
			response.getWriter().write("");
		}
	}

	
	/**
	 * 确认订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmAndPayOrder")
	public ModelAndView confirmAndPayOrder(HttpSession session,
			HttpServletResponse response) throws Exception{
		ModelAndView mv = this.getModelAndView();
		
		PageData pd = new PageData();
		
		pd = this.getPageData();
		
		System.out.println(pd);
		
		//查询 下订单的用户信息,包括储值卡，用户余额
		PageData userpd = memberService.findUserStorededAndPrestoreByUid(Integer.parseInt(pd.getString("UID")));
		
		//用户个人优惠折扣
		double  person_proportion = (Double)userpd.get("proportion");
		//用户类型的折扣
		double  category_proportion = (Double)userpd.get("categoryproportion");
		//用户最低折扣
		double lowProportion = person_proportion < category_proportion ? person_proportion : category_proportion;
		
		//查询该用户选择的服务项目收费信息
		PageData costPd = servicecostService.findById(pd);
		
		
		
		//订单中单次服务项目的价格
		BigDecimal singleprice = (BigDecimal)costPd.get("PRICE");
		
		//订单次数
		BigDecimal ordernum = new BigDecimal(pd.getString("TIMES"));
		
		//订单打折前的价格，即单次服务项目的价格*次数
		BigDecimal sumOrderMoney = singleprice.multiply(ordernum);
		
		//订单打折后的价格，即单次服务项目的价格*次数*折扣
		BigDecimal zhekouPrice = sumOrderMoney.multiply(new BigDecimal(lowProportion)).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		//查询该用户有什么优惠券组合
		List<UserDiscountGroup> discountGroupPdList = discountService.queryDiscountGroupByUid(Integer.parseInt(pd.getString("UID")));
		
		//遍历用户的优惠券组合，往组合里添加该用户拥有的属于组合的优惠券
		for(UserDiscountGroup group : discountGroupPdList){
			Integer sum = 0;//定义优惠券组中所有未使用的优惠券个数
			List<UserDiscount> discountPdList = discountService.queryDiscountByUidAndGroupid(group);
			
			for(UserDiscount userdiscount : discountPdList)//遍历它的每一个优惠券
			{
				sum = sum + userdiscount.getCount();
					
				}
			group.setSum(sum);
			group.setUserDiscounts(discountPdList);
			
			
			System.out.println(group);
			
		}
		
		
		
		mv.addObject("costPd", costPd);
		mv.addObject("userpd", userpd);
		mv.addObject("pd", pd);
		mv.addObject("lowProportion",lowProportion);
		mv.addObject("sumOrderMoney",sumOrderMoney);
		mv.addObject("zhekouPrice",zhekouPrice);
		mv.addObject("discountGroupPdList", discountGroupPdList);
		mv.setViewName("user/membercallback/confirmOrder");
		return mv;
	}
	
	@RequestMapping(value = "/judgeAvaliableProject")
	public void judgeAvaliableProject(HttpServletResponse response) throws Exception{
		
		PageData pd = new PageData();
		pd = this.getPageData();
		String pid = pd.getString("PID");
		String group_discount = pd.getString("DISCOUNT_ID");
		String discount_id = group_discount.substring(group_discount.indexOf("-")+1);
		pd.put("DISCOUNT_ID", discount_id);
		pd = discountService.findById(pd);
		
		String[] projectIds = pd.getString("PROJECT_IDS").split(",");
		boolean flag = false;
		
		for(int i=0; i<projectIds.length; i++){
			System.out.println(projectIds[i]);
			if(pid.equals(projectIds[i])){
				flag = true;
				break;
			}
		}
		if(flag==true){
			String responseJson = "{\"result\":\"" + flag + "\"}";
			response.getWriter().write(responseJson);
		}
		else{
			String responseJson = "{\"result\":\"" + flag + "\"}";
			response.getWriter().write(responseJson);
		}
	}
	/**
	 * 创建待支付订单
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/createOrder")
	public void createOrder(HttpSession session,
			HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		DecimalFormat df  = new DecimalFormat("######0.00");   
		//得到当前用户的信息
		PageData userpd = new PageData();
		userpd.put("UID", pd.getString("UID"));
		userpd = memberService.findById(userpd);
		
		//得到该项目单次价钱
		PageData costpd = new PageData();
		costpd.put("SERVICECOST_ID", pd.get("SERVICECOST_ID"));
		costpd = servicecostService.findById(costpd);
				
		/*// 把servicetime转换为日期
				String serviceTime = convertDate2(pd.getString("selectedDate"),pd.getString("servicetime"));*/
		//订单次数	
				int s = Integer.parseInt(pd.getString("ordeNum"));
				
		//得到预约时间
		String recommend_time = (String)pd.get("servicetime");
		////////////////////////////////////////优惠券相关

		/**
		 * 获取用户的优惠券信息，分两种情况创建订单：
		 * 1、没有使用优惠券，只计算折扣价
		 * 2、用了优惠券，订单中插入优惠券金额，并且将用的优惠券置为已使用
		 */
		//得到用户使用的优惠券信息
		
		if(Double.parseDouble(pd.getString("DiscountMoney"))!=0 && pd.get("DiscountMoney")!=null && !"".equals(pd.getString("DiscountMoney"))){
			String discount  = pd.getString("DiscountMoney");
			//计算单笔订单支付金额
			BigDecimal singleprice = (BigDecimal)costpd.get("PRICE");//单笔应收
			BigDecimal zhekousinglePrice = singleprice.multiply(new BigDecimal(pd.getString("PROPORTION")));//单笔打折
			BigDecimal discountAmount = new BigDecimal(pd.getString("DiscountMoney"));
			BigDecimal discountsinglePrice = zhekousinglePrice.subtract(discountAmount.divide(new BigDecimal(s),2)).setScale(2); 
			pd.put("PAY_MONEY", discountsinglePrice); // 支付金额
	pd.put("DISCOUNT_ID", discountAmount.divide(new BigDecimal(s),2));//单笔优惠金额	
	//消掉用户的优惠券
	JSONArray data = JSONArray.fromObject(pd.getString("DiscountJson"));
	for(int j=0; j<data.size(); j++){
		
		JSONObject obj =  (JSONObject) data.get(j);
		
	    String group_discount = obj.getString("discountid");
	    String discount_id = group_discount.substring(group_discount.indexOf("-")+1);
	    int number = Integer.parseInt(obj.getString("number"));
	    
	    //从tb_user_discount表里查出优惠券，置为已使用
	    PageData dis_pd = new PageData();
	    dis_pd.put("UID", pd.getString("UID"));
	    dis_pd.put("DISCOUNT_ID", discount_id);
	    
	    List<PageData> discountList = discountService.findByUidAndDiscountId(dis_pd);
	    for(int t=0; t<number; t++){
	    	PageData already_dis_pd = discountList.get(t);
	    	already_dis_pd.put("isUsed", 1);		
	    	userdiscountService.edit(already_dis_pd);
	    	
	    }
	   /* ////查询该用户有什么优惠券组合
	    Integer sum = 0;//定义该组合中所有的未使用优惠券个数
		List<UserDiscountGroup> discountGroupPdList = discountService.queryDiscountGroupByUid(Integer.parseInt(pd.getString("UID")));//得到用户的优惠券组
		//遍历用户的优惠券组合，往组合里添加该用户拥有的属于组合的优惠券
		for(UserDiscountGroup group : discountGroupPdList){
			
			List<UserDiscount> discountPdList = discountService.queryDiscountByUidAndGroupid(group);//查询这个组合有什么优惠券
			for(UserDiscount userdiscount : discountPdList)//遍历它的每一个优惠券
			{
				sum = sum + userdiscount.getCount();
					
				}
			if(sum==0){//该优惠券组中没有未使用的优惠券
				//删除该优惠券组
				String groupid =  group.getDiscount_group_id();
				userdiscountService.deleteDiscounGroupByid(groupid);
				
			}
			}*/
			
			
	
		}
	}else{//没有使用优惠券
			BigDecimal singleprice = (BigDecimal)costpd.get("PRICE");//单笔应收
			BigDecimal zhekousinglePrice = singleprice.multiply(new BigDecimal(pd.getString("PROPORTION"))).setScale(2);//单笔打折
			//discount_id设置为0
			pd.put("DISCOUNT_ID", "0.00");//单笔优惠金额
			//pay_money设置为折扣价
			pd.put("PAY_MONEY", zhekousinglePrice);
			
			
		}
		
		String orderId = "OD"+PrimaryKeyGenerator.generateKey();
		pd.put("ORDER_ID", orderId); // 主键
		pd.put("WECHAT_NAME", pd.get("NAME")); // 客户传的姓名
		pd.put("WECHAT_PHONE", pd.get("PHONE")); // 客户传的手机号
	
		pd.put("ORDER_MONEY", costpd.get("PRICE"));// 单笔订单金额
		pd.put("STORE_ID", staffService.queryStoreBySID(pd.getString("STAFF_ID")));// 门店编号
		pd.put("SERVICE_STAFF_ID", ((Staff) session
				.getAttribute(Const.SESSION_USER)).getSTAFF_ID());// 客服编号
		pd.put("CREATE_TIME", DateUtil.getTime()); // 创建时间
		
		pd.put("RECOMMEND_TIME", recommend_time);//预约时间
		
	/*	pd.put("EXPIRE_TIME", TimeAdjust.addDateMinut(DateUtil.getTime(), 30, "YYYY-MM-DD hh:mm:ss"));//过期时间
		long curren = System.currentTimeMillis();
		curren += 30 * 60 * 1000;
		Date da = new Date(curren);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// System.out.println(dateFormat.format(da));
*/		pd.put("EXPIRE_TIME", DateUtil.caculateGuoqiTime(recommend_time)); // 过期时间
		pd.put("REMARK", pd.getString("REMARK")); // 备注
		pd.put("PROPORTION",  pd.getString("PROPORTION")); // 订单折扣
		double d1 = 0.00;  
		pd.put("REFUND", df.format(d1)); // 退款
		pd.put("URL", 0); // 微信端自助下单
if(pd.get("PAY_MONEY").toString().equals("0.00"))
{
	pd.put("ORDER_STATUS", 2);// 订单状态已支付
	orderService.save(pd);
	//写预约表
	 pd.put("CUSTOMAPPOINT_ID", this.get32UUID()); //预约表主键
	 pd.put("U_ID",pd.get("UID")); 
	 if(!pd.get("RECOMMEND_TIME").equals("")){
	  pd.put("APPOINT_TIME", pd.get("RECOMMEND_TIME"));//预约时间 
	  String appoint_time = pd.getString("APPOINT_TIME");//预约时间 
	
	  pd.put("EXPIRE_TIME",  DateUtil.caculateGuoqiTime(appoint_time));//过期时间 
	  pd.put("SERVICE_STAFF_ID", "微信端自助下单");
	  pd.put("APPOINT_CODE", (char)(Math.random()*900000000)+10000000);
	  //生成验证码 
	  customappointService.save(pd);
	//写支付明细
	  PageData ppdd = new PageData();
		ppdd.put("ORDERMX_ID", this.get32UUID());
		ppdd.put("UID", pd.getString("UID"));
		ppdd.put("ORDER_ID", pd.getString("ORDER_ID"));
		ppdd.put("ORDER_MONEY", pd.get("ORDER_MONEY"));
		ppdd.put("PAY_MONEY", pd.get("PAY_MONEY"));
		ppdd.put("PAY_METHOD", 3);
		ppdd.put("PAY_TIME", pd.getString("CREATE_TIME"));
		
		
		ordermxService.save(ppdd); 
	
}
	
}
else{
	pd.put("ORDER_STATUS", 0);// 订单状态未支付
	orderService.save(pd);
}
		
		
		for (int i = 1; i < s; i++) {
			pd.put("SERVICE_STAFF_ID", ((Staff) session
				.getAttribute(Const.SESSION_USER)).getSTAFF_ID());//客服编号
			pd.put("RECOMMEND_TIME", "");// 不写推荐时间
			pd.put("CREATE_TIME", DateUtil.getTime()); // 创建时间
			pd.put("EXPIRE_TIME", "");//不写过期时间
			
			String neworderid = "OD"+PrimaryKeyGenerator.generateKey();
			pd.put("ORDER_ID", neworderid); // 主键

			orderService.save(pd);
			if(pd.get("PAY_MONEY").toString().equals("0.00"))//0元订单还要写预约表
			{
				
				  pd.put("CUSTOMAPPOINT_ID", this.get32UUID()); //预约表主键
				  pd.put("APPOINT_TIME", "");//不写预约时间
				  pd.put("EXPIRE_TIME", "");//不写过期时间 
				  pd.put("APPOINT_CODE",(char)(Math.random()*900000000)+10000000); //生成验证码
				  pd.put("SERVICE_STAFF_ID", "微信端自助下单");
				  customappointService.save(pd);
				//写支付明细
				  PageData ppdd = new PageData();
					ppdd.put("ORDERMX_ID", this.get32UUID());
					ppdd.put("UID", pd.getString("UID"));
					ppdd.put("ORDER_ID", pd.getString("ORDER_ID"));
					ppdd.put("ORDER_MONEY", pd.get("ORDER_MONEY"));
					ppdd.put("PAY_MONEY", pd.get("PAY_MONEY"));
					ppdd.put("PAY_METHOD", 3);
					ppdd.put("PAY_TIME", pd.getString("CREATE_TIME"));
					
					ordermxService.save(ppdd); 
				 
			
			}
			
}
		
		
		// /以下修改回电表状态
		PageData staffPd = staffService.findById(pd);
		PageData serviceCostPd = servicecostService.findById(pd);

		NCUTLOG.save(Jurisdiction.getUsername(),
				("新建微信端客户订单:用户名：" + (pd.getString("NAME") != null ? pd
						.getString("NAME") : pd.getString("WECHAT_NAME")) + " ，手机号 "
						+ (pd.getString("WECHAT_PHONE") == null ? pd
						.getString("PHONE") : pd.getString("WECHAT_PHONE"))
						+ "，并预约了" + staffPd.getString("STAFF_NAME") + "医生在"
						+ recommend_time + "的"
						+ serviceCostPd.getString("PNAME")+"项目"), this
						.getRequest().getRemoteAddr());

		membercallbackService.updatestatus(pd);
		
		String flag="ok";
		String responseJson = "{\"success\":\"" + flag + "\"}";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(responseJson);
	}

	/**
	 * 增加时间
	 * 
	 * @param
	 * @throws Exception
	 */
	public static String addDate(String day, int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
	
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
		
		date = cal.getTime();
		cal = null;
		return format.format(date);
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改MemberCallBack");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		membercallbackService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 根据选择的时间id（7，1）得到选择的是YYYY-MM-dd hh:mm:ss的格式
	 * 
	 * @param selectedtime
	 *            选择的日期（String YYYY-MM-dd）
	 * @param idtime
	 *            例如（7，1）
	 * @return
	 * @throws Exception
	 */
	public String convertDate(String selectedtime, String idtime)
			throws Exception {

		String[] dd = idtime.split(",");
		String hours = dd[0];
		// 通过日期获得数据库中周几减一的数字
		String week = new SimpleDateFormat("E").format(DateUtil
				.fomatDate(selectedtime));
		int week_which = DateUtil
				.ChineseToNum(week.substring(week.length() - 1));
		int servicetime_week = Integer.parseInt(dd[1]);

		String currentDate = DateUtil.getEveryDay(selectedtime,
				(servicetime_week - week_which));
		if (dd[0].length() < 2) {
			hours = "0" + dd[0];
		}
		return (currentDate + " " + hours + ":00:00");
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page, HttpSession session) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表MemberCallBack");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}

		// List<PageData> varList = membercallbackService.list(page);
		// //列出MemberCallBack列表
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());//客服本来的门店
		List<PageData>	storeList = storeService.findAllNames(pd);
		pd.put("STOREID", pd.get("STOREID"));
		pd.put("STORE_ID", pd.get("STORE_ID"));
		page.setPd(pd);
		
		List<PageData>	varList = membercallbackService.listStaffAndStore(page);
		
		mv.setViewName("user/membercallback/membercallback_list");
		mv.addObject("varList", varList);
		mv.addObject("storeList", storeList);
		mv.addObject("pd", pd);
		mv.addObject("STORE_ID",pd.get("STOREID"));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
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
		mv.setViewName("user/membercallback/membercallback_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 预约 创建订单和预约记录
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/makeOrderandAppoint")
	public ModelAndView makeOrderandAppoint(HttpSession session)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = membercallbackService.findById(pd);// 根据id读取
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());
		List<PageData> staffPdlist = staffService.listSelfStoreStaff(pd);// 查询自己门店中工作中的医生
		List<PageData> Initiallist = servicecostService
				.findServiceAndCostByStaff_id(pd);
		mv.setViewName("user/membercallback/membercallback_createOrderAndAppoint");
		mv.addObject("staffPdlist", staffPdlist);
		mv.addObject("Initiallist", Initiallist);
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
		pd = membercallbackService.findById(pd); // 根据ID读取

		PageData ppd = new PageData();
		PageData staffname = staffService.findById(pd);
		PageData storename = storeService.findById(pd);
		pd.put("STAFF_NAME", staffname.get("STAFF_NAME"));
		pd.put("STORE_NAME", storename.get("STORE_NAME"));

		mv.setViewName("user/membercallback/membercallback_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除MemberCallBack");
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
			membercallbackService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出MemberCallBack到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("uid"); // 1
		titles.add("客户姓名"); // 2
		titles.add("门店编号"); // 3
		titles.add("员工编号"); // 4
		titles.add("电话号码"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = membercallbackService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("UID").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("NAME")); // 2
			vpd.put("var3", varOList.get(i).getString("STORE_ID")); // 3
			vpd.put("var4", varOList.get(i).getString("STAFF_ID")); // 4
			vpd.put("var5", varOList.get(i).getString("PHONE")); // 5
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
	public void insertPayDetail(PageData pd, int method, String methodText) throws Exception{
		PageData ppdd = new PageData();
		ppdd.put("ORDERMX_ID", this.get32UUID());
		ppdd.put("UID", pd.getString("UID"));
		ppdd.put("ORDER_ID", pd.getString("ORDER_ID"));
		//ppdd.put("ORDER_MONEY", pd.getString("ORDER_MONEY"));
		ppdd.put("PAY_MONEY", (Double)pd.get(methodText));
		ppdd.put("PAY_METHOD", method);
		ppdd.put("PAY_TIME", pd.getString("CREATE_TIME"));
		ppdd.put("REMARK", "");
		
		ordermxService.save(ppdd);
	}
}
