package cn.ncut.controller.user.userpay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.annotation.Token;
import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Staff;
import cn.ncut.entity.system.UserDiscount;
import cn.ncut.entity.system.UserDiscountGroup;
import cn.ncut.entity.wechat.pojo.WeChatPayPerformance;
import cn.ncut.service.finance.customappoint.CustomappointManager;
import cn.ncut.service.finance.discount.DiscountManager;
import cn.ncut.service.finance.prestore.PreStoreManager;
import cn.ncut.service.finance.prestoremx.PreStoreMxManager;
import cn.ncut.service.finance.serviceall.ServiceCostManager;
import cn.ncut.service.finance.serviceall.ServiceProjectManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.service.user.customstored.CustomStoredManager;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.service.user.order.OrderManager;
import cn.ncut.service.user.order.OrderMxManager;
import cn.ncut.service.user.storeddetail.StoredDetailManager;
import cn.ncut.service.user.userdiscount.UserDiscountManager;
import cn.ncut.service.wechat.payPerformance.impl.WeChatPayPerformanceService;
import cn.ncut.util.BigDecimalUtil;
import cn.ncut.util.Const;
import cn.ncut.util.DateUtil;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.PageData;
import cn.ncut.util.wechat.PrimaryKeyGenerator;

/**
 * 用户缴费管理
 * 
 * @author ljj
 * 
 */
@Controller
@RequestMapping(value = "/userpay")
public class UserPayController extends BaseController {

	String menuUrl = "userpay/show.do"; // 菜单地址(权限用)

	@Resource(name = "customappointService")
	private CustomappointManager customappointService;

	@Resource(name = "staffService")
	private StaffManager staffService;

	@Resource(name = "servicecostService")
	private ServiceCostManager servicecostService;

	@Resource(name = "memberService")
	private MemberManager memberService;

	@Resource(name = "orderService")
	private OrderManager orderService;

	@Resource(name = "ordermxService")
	private OrderMxManager ordermxService;
	
	@Resource(name="prestoreService")
	private PreStoreManager prestoreService;
	
	@Resource(name="prestoremxService")
	private PreStoreMxManager prestoremxService;
	
	@Resource(name="customstoredService")
	private CustomStoredManager customstoredService;
	
	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;
	
	@Resource(name = "discountService")
	private DiscountManager discountService;
	
	@Resource(name = "userdiscountService")
	private UserDiscountManager userdiscountService;
	
	@Resource(name = "serviceprojectService")
	private ServiceProjectManager serviceprojectService;

	@Resource(name = "storeddetailService")
	private StoredDetailManager storeddetailService;
	
	@Resource(name = "weChatPayPerformanceService")
    private WeChatPayPerformanceService weChatPayPerformanceService;

	// 菜单进入地址，初始化页面
	@RequestMapping(value = "/show")
	public ModelAndView showPayPage(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		
		// 得到当前的客服的门店编号
		Session session = Jurisdiction.getSession();
		Staff staff = ((Staff) session.getAttribute(Const.SESSION_USER));
		
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		
		page.setPd(pd);
		
		//查询用户信息
		List<PageData> userList = (List<PageData>) memberService.listCompleteMemberlistPage(page);// 列出Member列表
		
		//查询此门店的医生信息
		pd.put("STORE_ID", staff.getSTORE_ID());
		
		List<PageData> staffPdlist = staffService.listAll(pd);

		mv.addObject("staffPdlist", staffPdlist);
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.setViewName("user/userpay/user_pay");
		return mv;
	}

	// 根据医生信息异步出属于他的服务项目
	@RequestMapping(value = "/refreshProjectCostBySId")
	public void refreshProjectCostBySId(HttpServletResponse response)
			throws Exception {

		PageData pd = new PageData();
		pd = this.getPageData();

		// 得到当前的客服的门店编号
		Session session = Jurisdiction.getSession();

		// 在服务标准表中查询所属门店的当前选中医生的所有项目
		List<PageData> pdlist = servicecostService.findServiceAndCostByStaff_id(pd);
		String s = new ObjectMapper().writeValueAsString(pdlist);

		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().write(s);
	}

	//根据服务项目生成服务价钱
		@RequestMapping(value="/refreshCostByProjectid")
		public void refreshCostByProjectid(HttpServletResponse response) throws Exception{
			
			PageData pd = new PageData();
			pd = this.getPageData();
			
			//得到当前的客服的门店编号
			Session session = Jurisdiction.getSession();
			//pd.put("STORE_ID", ((Staff)session.getAttribute(Const.SESSION_USER)).getSTORE_ID());
			pd.put("PID", pd.get("PROJECT_ID"));
			
			//根据服务项目和员工生成服务价钱
			List<PageData> costlist = servicecostService.queryPriceByPIDAndStaff(pd);			
			String s = new ObjectMapper().writeValueAsString(costlist);
			
			response.setContentType("text/xml;charset=UTF-8");
			response.getWriter().write(s);
		}
	
		
		@RequestMapping(value="/queryAlreadyAppointAmount")
		public void queryAlreadyAppointAmount(HttpServletResponse response) throws Exception{
			PageData pd = new PageData();
			pd = this.getPageData();
			String appointtime = convertDate2(pd.getString("selecttime"), pd.getString("id"));
			//查询预约表中   该医生  该预约时间的有多少人
			pd.put("APPOINT_TIME", appointtime);
			List<PageData> pdlist = customappointService.querySum(pd);
			
			String responseJson = "{\"count\":\"" + pdlist.size()+ "\"}";
			response.getWriter().write(responseJson);
			
		}
		
		
		@RequestMapping(value = "/showAvaliableProject")
		public void showAvaliableProject(HttpServletResponse response) throws Exception{
			
			PageData pd = new PageData();
			pd = this.getPageData();
			
			pd = discountService.findById(pd);
			
			String[] projectIds = pd.getString("PROJECT_IDS").split(",");
			
			//构造可用项目的List
			List<String> projectNames = new ArrayList<String>();
			
			for(int i=0; i<projectIds.length; i++){
				PageData p = new PageData();
				p.put("PROJECT_ID", projectIds[i]);
				p = serviceprojectService.findById(p);
				projectNames.add(p.getString("PNAME"));
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().write(JSONArray.fromObject(projectNames).toString());
		}
		
		
		/**
		 * 确认订单
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/confirmAndPayOrder")
		@Token(save=true)
		public ModelAndView confirmAndPayOrder() throws Exception{
			ModelAndView mv = this.getModelAndView();
			
			PageData pd = new PageData();
			pd = this.getPageData();
			
			//查询 下订单的用户信息,包括储值卡，用户余额
			PageData userpd = memberService.findUserStorededAndPrestoreByUid(Integer.parseInt(pd.getString("uid")));
			
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
			BigDecimal ordernum = new BigDecimal(pd.getString("orderNum"));
			
			//订单打折前的价格，即单次服务项目的价格*次数
			BigDecimal sumOrderMoney = singleprice.multiply(ordernum).setScale(2,BigDecimal.ROUND_HALF_UP);
			
			//订单打折后的价格，即单次服务项目的价格*次数*折扣
			BigDecimal zhekouPrice = sumOrderMoney.multiply(new BigDecimal(lowProportion)).setScale(2,BigDecimal.ROUND_HALF_UP);
			
			
			//查询该用户有什么优惠券组合
			List<UserDiscountGroup> discountGroupPdList = discountService.queryDiscountGroupByUid(Integer.parseInt(pd.getString("uid")));
			
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
				
			}
			
			
			mv.addObject("costPd", costPd);
			mv.addObject("userpd", userpd);
			mv.addObject("pd", pd);
			mv.addObject("lowProportion",lowProportion);
			mv.addObject("sumOrderMoney",sumOrderMoney);
			mv.addObject("zhekouPrice",zhekouPrice);
			mv.addObject("discountGroupPdList", discountGroupPdList);
			mv.setViewName("user/userpay/confirmOrder");
			return mv;
		}
		
		
		
	 // 创建订单
	 @RequestMapping(value = "/createOrder")
	 @Token(remove=true)
	 public void createOrder(HttpServletResponse response) throws Exception{
		long order_start = System.currentTimeMillis();
		 
		PageData pd = new PageData();
		pd = this.getPageData();
		
		DecimalFormat df = new DecimalFormat("######0.00");
				
		//得到当前用户的信息
		PageData userpd = new PageData();
		userpd = memberService.findUserStorededAndPrestoreByUid(Integer.parseInt(pd.getString("UID")));
		
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
		
		//订单中单次服务项目的价格打过折后的价格
		BigDecimal zhekou_singleprice = singleprice.multiply(new BigDecimal(lowProportion)).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		// 得到当前的客服的门店编号
		Session session = Jurisdiction.getSession();
		Staff staff = ((Staff) session.getAttribute(Const.SESSION_USER));

		
		//得到用户的储值卡信息
		PageData czkpd = new PageData();
		czkpd.put("UID", pd.getString("UID"));
		czkpd = customstoredService.findById(czkpd);
		double czkRemainMoney;
		if(czkpd!=null){
		czkRemainMoney = (Double)czkpd.get("REMAIN_MONEY");
		}else{
			czkRemainMoney = 0;
		}
		
		//得到用户的余额信息
		PageData prepd = prestoreService.findByUid(Integer.parseInt(pd.getString("UID")));
		
		// 把servicetime转换为日期
		String serviceTime = pd.getString("servicetime");
		
		//订单次数
		int n = Integer.parseInt(pd.getString("ordeNum").trim());
		
		//用于标识订单
		int flag = 1;
		
		/**
		 * 获取用户的优惠券信息，分两种情况创建订单：
		 * 1、没有使用优惠券，只计算折扣价
		 * 2、用了优惠券，订单中插入优惠券金额，并且将用的优惠券置为已使用
		 */
		BigDecimal finalPrice = new BigDecimal(0);
		BigDecimal averageDiscountMoney = new BigDecimal(0);
		
		
		
		logger.debug("--- start 开始创建订单---");
		
		
		/**
		 * 关于用户储值卡的金额
		 * 有n次订单，使用储值卡总金额为money,那么储值卡金额要减去money
		 * 但是要插入n次订单支付方式和n次储值卡明细，每次的金额为money/n
		 * 要先判断钱够不够，如果不够则不往下进行，并让flag=0
		 */
		if (!"0".equals(pd.getString("STOREDPAY_MONEY")) && !"".equals(pd.getString("STOREDPAY_MONEY")) && pd.get("STOREDPAY_MONEY") != null) {
			
			//用户用储值卡支付的钱
			double user_STOREDPAY_MONEY = Double.parseDouble(pd.getString("STOREDPAY_MONEY"));
				
			//判断密码是否正确
			/*if(!pd.getString("password").equals(czkpd.getString("PASSWORD"))){
				flag=0;
			}else */if(((Double)czkpd.get("REMAIN_MONEY")+(Double)czkpd.get("REMAIN_POINTS"))<user_STOREDPAY_MONEY){
				//如果钱不够
				flag=0;
			}else{
				//如果用户支付的钱<储值卡余额的钱，即储值卡的钱够支付这n次订单,那么直接减去用户支付的钱
				if(user_STOREDPAY_MONEY < (Double)czkpd.get("REMAIN_MONEY")){
					czkpd.put("REMAIN_MONEY", (Double)czkpd.get("REMAIN_MONEY")-user_STOREDPAY_MONEY);
					czkpd.put("REMAIN_POINTS", (Double)czkpd.get("REMAIN_POINTS"));
				}else{
					//储值卡余额的钱不够支付这n次订单，需要先把储值卡的钱扣为0，再扣除余额的钱
					double hm = user_STOREDPAY_MONEY - (Double)czkpd.get("REMAIN_MONEY");  
					double pointsmm = (Double)czkpd.get("REMAIN_POINTS") - hm;
					czkpd.put("REMAIN_MONEY", Double.parseDouble("0.00"));
					czkpd.put("REMAIN_POINTS", pointsmm);
				}
				customstoredService.editSubMoney(czkpd);
			}
		}
		
		
		if (!"0".equals(pd.getString("PRESTOREPAY_MONEY")) && !"".equals(pd.getString("PRESTOREPAY_MONEY")) && pd.get("PRESTOREPAY_MONEY") != null) {
			double pre_mm = (Double)prepd.get("SUM_MONEY") - Double.parseDouble(pd.getString("PRESTOREPAY_MONEY"));
			if(pre_mm<0){
				flag=0;
			}else{
				prepd.put("SUM_MONEY", pre_mm);
				prestoreService.edit(prepd);
			}
		}
		
		if(flag==1){
			if(Double.parseDouble(pd.getString("DiscountMoney"))!=0 && pd.get("DiscountMoney")!=null && !"".equals(pd.getString("DiscountMoney"))){
				BigDecimal DiscountMoney = new BigDecimal(pd.getString("DiscountMoney"));
				averageDiscountMoney = DiscountMoney.divide(new BigDecimal(n), 2, RoundingMode.HALF_UP);
			
				//每个订单中服务项目打过折并且减过优惠券的价格，保留两位小数
				finalPrice = zhekou_singleprice.subtract(averageDiscountMoney).setScale(2,BigDecimal.ROUND_HALF_UP);
				
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
				}
			}else{
				finalPrice = zhekou_singleprice;
			}
		}
		
		
		if(flag==1){
		for(int i=0; i<n; i++){
			// 插入订单记录			
			PageData order_pd = new PageData();
			String orderId = "OD"+PrimaryKeyGenerator.generateKey();
			order_pd.put("ORDER_ID", orderId);
			order_pd.put("UID", pd.getString("UID"));
			order_pd.put("STORE_ID",  costPd.getString("STORE_ID"));//对应医生的门店编号
			order_pd.put("SERVICECOST_ID", pd.getString("SERVICECOST_ID"));
			order_pd.put("STAFF_ID", costPd.getString("STAFF_ID"));
			order_pd.put("SERVICE_STAFF_ID", staff.getSTAFF_ID());
			order_pd.put("ORDER_MONEY", singleprice.toString()); //每一次应收,打过折没用优惠券的价格
			order_pd.put("PAY_MONEY", finalPrice.toString());        //减过优惠券的价格
			order_pd.put("PROPORTION", lowProportion);        //用户最低折扣
			order_pd.put("CREATE_TIME", DateUtil.getTime()); // 创建时间
			order_pd.put("WECHAT_NAME", userpd.getString("name"));
			order_pd.put("WECHAT_PHONE", userpd.getString("phone"));
			order_pd.put("URL", "1");
			if(averageDiscountMoney.doubleValue()!=0){
				order_pd.put("DISCOUNT_ID", averageDiscountMoney); // 存入该订单使用的优惠券金额
			}else{
				order_pd.put("DISCOUNT_ID", "0.00");
			}
			order_pd.put("REFUND", Double.parseDouble("0.00"));
			order_pd.put("ORDER_STATUS", 2); // 状态
			if(i==0){
				order_pd.put("RECOMMEND_TIME", serviceTime);//预约时间
			}else{
				order_pd.put("RECOMMEND_TIME", "");//预约时间
			}
			order_pd.put("REMARK", pd.getString("REMARK"));
			orderService.save(order_pd);
			
			
			/**
			 * /插入各种支付方式的支付详情记录
			 */
			//微信支付
			if (!"".equals(pd.getString("WECHATPAY_MONEY")) && pd.get("WECHATPAY_MONEY") != null) {
				order_pd.put("WECHATPAY_MONEY", BigDecimalUtil.divString(Double.parseDouble(pd.getString("WECHATPAY_MONEY")), n, 2));
				insertPayDetail(order_pd,0,"WECHATPAY_MONEY");
			}
			
			//支付宝支付
			if (!"".equals(pd.getString("ALIPAY_MONEY")) && pd.get("ALIPAY_MONEY") != null ) {
				order_pd.put("ALIPAY_MONEY", BigDecimalUtil.divString(Double.parseDouble(pd.getString("ALIPAY_MONEY")), n, 2));
				insertPayDetail(order_pd,1,"ALIPAY_MONEY");
			}
			
			//银行卡支付
			if (!"".equals(pd.getString("BANKPAY_MONEY")) && pd.get("BANKPAY_MONEY") != null) {
				order_pd.put("BANKPAY_MONEY", BigDecimalUtil.divString(Double.parseDouble(pd.getString("BANKPAY_MONEY")), n, 2));
				insertPayDetail(order_pd,4,"BANKPAY_MONEY");
			}
			
			//现金支付
			if (!"".equals(pd.getString("CASHPAY_MONEY")) && pd.get("CASHPAY_MONEY") != null) {
				order_pd.put("CASHPAY_MONEY", BigDecimalUtil.divString(Double.parseDouble(pd.getString("CASHPAY_MONEY")), n, 2));
				insertPayDetail(order_pd,3,"CASHPAY_MONEY");
			}
			
			//用户余额支付
			if (!"".equals(pd.getString("PRESTOREPAY_MONEY")) && pd.get("PRESTOREPAY_MONEY") != null) {
				
				//每一次订单余额支付的金额
				double prePayMoney = BigDecimalUtil.divString(Double.parseDouble(pd.getString("PRESTOREPAY_MONEY")), n, 2);
				
				order_pd.put("PRESTOREPAY_MONEY", prePayMoney);
				insertPayDetail(order_pd,5,"PRESTOREPAY_MONEY");
				
				if(prepd!=null){
				//插入预存消费明细记录，预存的总金额需要一次扣除n个订单的
				PageData premxpd = new PageData();
				
				premxpd.put("UID",pd.getString("UID"));
				premxpd.put("PRESTOREMX_ID", this.get32UUID());
				premxpd.put("PRESTORE_ID", prepd.getString("PRESTORE_ID"));
				premxpd.put("TYPE", 2);
				premxpd.put("STAFF_ID", staff.getSTAFF_ID());
				premxpd.put("PHONE", userpd.getString("phone"));
				premxpd.put("USERNAME", userpd.getString("username"));
				premxpd.put("CREATE_TIME", DateUtil.getTime());
				premxpd.put("PRESTOREMONEY", prePayMoney);
				prestoremxService.save(premxpd);
				}
				
			}
			
			
			//储值卡支付，两种明细 
			//2 代表储值卡余额支付    6代表储值卡返点支付
			if (!"".equals(pd.getString("STOREDPAY_MONEY")) && pd.get("STOREDPAY_MONEY") != null) {
				//减少该用户的储值卡金额，先扣除储值卡余额，再扣除返点余额,插入储值卡的明细
				
				//该次订单储值卡需要支付的金额
				double czkMoney = BigDecimalUtil.divString(Double.parseDouble(pd.getString("STOREDPAY_MONEY")),n,2);
				
				if(czkMoney<=czkRemainMoney){
					//储值卡本身的余额已经足够支付这次,先插入订单支付明细   
					order_pd.put("STOREDPAY_MONEY", czkMoney);
					insertPayDetail(order_pd,2,"STOREDPAY_MONEY");
					
					if(czkpd!=null){
						//再减去储值卡本身的余额	
						czkRemainMoney = Double.parseDouble(df.format(czkRemainMoney-czkMoney));//000000000
						//double czkmm = (Double)czkpd.get("REMAIN_MONEY") - czkMoney;
						//czkpd.put("REMAIN_MONEY", czkmm);
						//czkpd.put("REMAIN_POINTS", (Double)czkpd.get("REMAIN_POINTS"));
						//customstoredService.editSubMoney(czkpd);
						
						//插入储值卡消费明细
						PageData czkmx_pd = new PageData();
						czkmx_pd.put("STOREDDETAIL_ID", "OC"+PrimaryKeyGenerator.generateKey());
						czkmx_pd.put("UID", pd.getString("UID"));
						czkmx_pd.put("STORE_ID", staffService.queryStoreBySID(costPd.getString("STAFF_ID")));
						czkmx_pd.put("STAFF_ID", staff.getSTAFF_ID());
						czkmx_pd.put("CREATE_TIME", DateUtil.getTime());
						czkmx_pd.put("MONEY", czkMoney);
						czkmx_pd.put("POINTS", 0);
						czkmx_pd.put("TYPE", 5);
						czkmx_pd.put("STATUS", 0);
						storeddetailService.save(czkmx_pd);
					}
				}else{
					//储值卡本身的余额不够支付这次，需要从返点金额里扣除不足的部分，同时插入用返点支付的订单明细
					
					//先插入储值卡余额的消费明细
					if(czkRemainMoney>0){
						order_pd.put("STOREDPAY_MONEY",czkRemainMoney);
						insertPayDetail(order_pd,2,"STOREDPAY_MONEY");
						
					}
					
					//还需支付的钱
					double hm = Double.parseDouble(df.format(czkMoney - czkRemainMoney));  
					//double pointsmm = (Double)czkpd.get("REMAIN_POINTS") - hm;
					
					
					//再插入储值卡返点的消费明细
					order_pd.put("POINTS_MONEY",Double.parseDouble(df.format(hm)));
					insertPayDetail(order_pd,6,"POINTS_MONEY");
					
					//czkpd.put("REMAIN_MONEY", 0);
					//czkpd.put("REMAIN_POINTS", pointsmm);
					//customstoredService.editSubMoney(czkpd);
					
					//插入储值卡消费明细
					PageData czkmx_pd = new PageData();
					czkmx_pd.put("STOREDDETAIL_ID", "OC"+PrimaryKeyGenerator.generateKey());
					czkmx_pd.put("UID", pd.getString("UID"));
					czkmx_pd.put("STORE_ID", staffService.queryStoreBySID(costPd.getString("STAFF_ID")));
					czkmx_pd.put("STAFF_ID", staff.getSTAFF_ID());
					czkmx_pd.put("CREATE_TIME", DateUtil.getTime());
					czkmx_pd.put("MONEY", czkRemainMoney);   //修改了
					czkmx_pd.put("POINTS", Double.parseDouble(df.format(hm)));
					czkmx_pd.put("TYPE", 5);
					czkmx_pd.put("STATUS", 0);
					storeddetailService.save(czkmx_pd);
					
					czkRemainMoney = Double.parseDouble("0.00");//000000000
					
				}
			}
			
			//插入预约记录
			PageData appoint_pd = new PageData();
			appoint_pd.put("U_ID", pd.getString("UID"));
			appoint_pd.put("CUSTOMAPPOINT_ID", this.get32UUID());
			appoint_pd.put("APPOINT_CODE", (char)(Math.random()*900000000)+10000000);
			appoint_pd.put("SERVICE_STAFF_ID",staff.getSTAFF_ID());
			if(i==0){
				appoint_pd.put("APPOINT_TIME", serviceTime);
				appoint_pd.put("EXPIRE_TIME", DateUtil.caculateGuoqiTime(serviceTime));
			}else{
				appoint_pd.put("APPOINT_TIME", "");
				appoint_pd.put("EXPIRE_TIME", "");
			}
			
			appoint_pd.put("ORDER_ID", orderId);
			customappointService.save(appoint_pd);			
			
			NCUTLOG.save(
					Jurisdiction.getUsername(),
					"用户：" + (userpd.getString("name")!=null?userpd.getString("name"):userpd.getString("username")) + "，手机号："
							+ userpd.getString("phone") + "。到店支付了："
							+ order_pd.get("PAY_MONEY") + "元，项目为："+ costPd.getString("PNAME")+" ,订单号为："
							+ order_pd.getString("ORDER_ID"), this.getRequest()
							.getRemoteAddr());
		
		}
		
		}
		
		long order_end = System.currentTimeMillis();
		this.weChatPayPerformanceService.savePayPerformance(WeChatPayPerformance.
		        savePayPerformance("userpay/createOrder", n+"", order_start, order_end, "用户ID为" + pd.getString("UID")));
		
		logger.debug("--- end 创建订单---");
		
		response.setContentType("text/xml;charset=UTF-8");
		if(flag==1){
			response.getWriter().write("{\"success\":\"ok\"}");
		}else if(flag==0){
			response.getWriter().write("{\"error\":\"error\"}");
		}
		
	}
	 
	/**
	 * 插入订单后插入n条支付方式的明细
	 * @param pd
	 * @param method
	 * @throws Exception
	 */
	public void insertPayDetail(PageData pd, int method, String methodText) throws Exception{
		PageData ppdd = new PageData();
		ppdd.put("ORDERMX_ID", this.get32UUID());
		ppdd.put("UID", pd.getString("UID"));
		ppdd.put("ORDER_ID", pd.getString("ORDER_ID"));
		ppdd.put("ORDER_MONEY", pd.getString("ORDER_MONEY"));
		ppdd.put("PAY_MONEY", (Double)pd.get(methodText));
		ppdd.put("PAY_METHOD", method);
		ppdd.put("PAY_TIME", pd.getString("CREATE_TIME"));
		ppdd.put("REMARK", "");
		
		ordermxService.save(ppdd);
	}
	
	/**大表转换时间
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/changeTime")
	public void changeTime(HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = (String) pd.get("Id");
		String new_date = convertDate((String) pd.get("THE_DATE"), id);
		//日期比较，如果s>=e 返回true 否则返回false
		Boolean flag = false;
		if(DateUtil.compareToDate(new_date, DateUtil.getDay())>0){
			flag=true;
		}else if(DateUtil.compareToDate(new_date, DateUtil.getDay())==0&&Integer.parseInt(id.length()==4?id.substring(0, 2):id.substring(0, 1))>new Date().getHours()-1){
			flag=true;
		}else {
			flag=false;
		}
		String msg = "";
		if(flag){
			msg = "ok";
		}else{
			msg = "error";
		} 
		String hour = id.length()==4?id.substring(0, 2):("0"+id.substring(0, 1));
		String all = new_date +" "+ hour +":00:00";
		String responseJson = "{\"selectTime\":\"" + all + "\",\"msg\":\"" + msg+ "\"}";
		response.getWriter().write(responseJson);
	}
	
	/**
	 * 根据选择的时间id（7，1）得到选择的是YYYY-MM-dd hh:mm:ss的格式
	 * @param selectedtime 选择的日期（String YYYY-MM-dd）
	 * @param idtime 例如（7，1）
	 * @return
	 * @throws Exception
	 */
	public String convertDate(String selectedtime, String idtime) throws Exception{
		
		// 通过日期获得数据库中周几减一的数字
		String week = new SimpleDateFormat("E").format(DateUtil.fomatDate(selectedtime));
		int week_which = DateUtil.ChineseToNum(week.substring(week.length() - 1));
		int servicetime_week = Integer.parseInt(idtime.split(",")[1]);
		return DateUtil.getEveryDay(selectedtime, (servicetime_week-week_which)); 
	}
	
	/**
	 * 根据选择的时间id（7，1）得到选择的是YYYY-MM-dd hh:mm:ss的格式
	 * @param selectedtime 选择的日期（String YYYY-MM-dd）
	 * @param idtime 例如（7，1）
	 * @return
	 * @throws Exception
	 */
	public String convertDate2(String selectedtime, String idtime) throws Exception{
		
		String[] dd = idtime.split(",");
		String hours=dd[0];
		// 通过日期获得数据库中周几减一的数字
		String week = new SimpleDateFormat("E").format(DateUtil.fomatDate(selectedtime));
		int week_which = DateUtil.ChineseToNum(week.substring(week.length() - 1));
		int servicetime_week = Integer.parseInt(dd[1]);
		
		String currentDate = DateUtil.getEveryDay(selectedtime, (servicetime_week-week_which));
		if(dd[0].length()<2){
			hours = "0"+dd[0];
		}
		return (currentDate+" "+ hours +":00:00");
	}


}