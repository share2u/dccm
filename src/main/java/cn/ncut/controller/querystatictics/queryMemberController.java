package cn.ncut.controller.querystatictics;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.QueryUser;
import cn.ncut.entity.system.QueryUserDiscount;
import cn.ncut.service.finance.prestoremx.impl.PreStoreMxService;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.service.user.storeddetail.impl.StoredDetailService;
import cn.ncut.service.user.usercategory.impl.UsercategoryService;
import cn.ncut.service.user.usergroup.UsergroupManager;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;


@Controller
@RequestMapping(value = "/queryMember")
public class queryMemberController extends BaseController {

	String menuUrl = "query/list.do"; // 菜单地址(权限用)
	@Resource(name = "memberService")
	private MemberManager memberService;
	@Resource(name = "usergroupService")
	private UsergroupManager usergroupService;
	@Resource(name = "usercategoryService")
	private UsercategoryService usercategoryService;
	@Resource(name = "prestoremxService")
	private PreStoreMxService prestoremxService;
	@Resource(name = "storeddetailService")
	private StoredDetailService storeddetailService;
	

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Member");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<QueryUser> varList = memberService.menberandgroup(page); // 列出Member列表
		mv.setViewName("querysatistics/memberJsp");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/storedDetail")
	public ModelAndView storedlist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Member");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		
	    page.setPd(pd);
		List<PageData> varList = memberService.querystoredDetail(page); // 列出Member列表
		mv.setViewName("querysatistics/querystoredDetail");
		mv.addObject("varList", varList);
		mv.addObject("HiddenUid", pd.getString("UID"));
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	
	@RequestMapping(value = "/prestoredDetail")
	public ModelAndView prestorelist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Member");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		
	    page.setPd(pd);
		List<PageData> varList = memberService.queryprestoredDetail(page); // 列出Member列表
		mv.setViewName("querysatistics/prestoreddetail");
		mv.addObject("varList", varList);
		mv.addObject("HiddenUid", pd.getString("UID"));
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	@RequestMapping(value = "/discount")
	public ModelAndView discountlist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Member");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		
	    page.setPd(pd);
		List<PageData> varList = memberService.querydiscountDetail(page); // 列出Member列表
		mv.setViewName("querysatistics/discountdetail");
		mv.addObject("varList", varList);
		mv.addObject("HiddenUid", pd.getString("UID"));
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	
	@RequestMapping(value = "/credit")
	public ModelAndView creditlist(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Member");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		
	    page.setPd(pd);
		List<PageData> varList = memberService.querycreditDetail(page); // 列出Member列表
		mv.setViewName("querysatistics/creditdetail");
		mv.addObject("varList", varList);
		mv.addObject("HiddenUid", pd.getString("UID"));
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	@RequestMapping(value="/storeddetailexcel")
	public ModelAndView storeddetailExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Member到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		// 添加检索条件
		// 根据姓名、微信名查询用户id
		
		String Status = pd.getString("STATUS");
		if (null != Status && !"".equals(Status)) {
			pd.put("Status", Status.trim());
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
		
		titles.add("用户姓名"); // 2
		titles.add("昵称"); // 3
		titles.add("门店"); // 7
		titles.add("客服"); // 8
		titles.add("创建日期"); // 9
		titles.add("金额"); // 10	
		titles.add("返点"); // 12
		titles.add("微信支付");
		titles.add("支付宝");
		titles.add("银联");
		titles.add("现金");
		titles.add("状态");
		
		dataMap.put("titles", titles);
		List<PageData> varOList = memberService
				.selectstoredAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("name"));
			vpd.put("var2", varOList.get(i).get("userName"));
			vpd.put("var3", varOList.get(i).get("STORE_NAME"));
			vpd.put("var4", varOList.get(i).get("STAFF_NAME"));
			vpd.put("var5", varOList.get(i).get("CREATE_TIME"));
			  java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
			   df.format(varOList.get(i).get("MONEY"));
			
			vpd.put("var6", df.format(varOList.get(i).get("MONEY"))); // 7
			vpd.put("var7", df.format(varOList.get(i).get("POINTS"))); 
			vpd.put("var8", df.format(varOList.get(i).get("WECHATPAY_MONEY")));
			vpd.put("var9", df.format(varOList.get(i).get("ALIPAY_MONEY")));
			vpd.put("var10", df.format(varOList.get(i).get("BANKPAY_MONEY")));
			vpd.put("var11", df.format(varOList.get(i).get("CASHPAY_MONEY")));
			//vpd.put("var12", varOList.get(i).get("STATUS").toString());
			if((Integer)varOList.get(i).get("STATUS")==0){
				vpd.put("var12","已完成");
			}else{
			   vpd.put("var12", "待支付"); 
			}
		
			
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Member到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 添加检索条件
		// 根据姓名、微信名查询用户id
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			//pd.put("keywords", keywords.trim());
			ArrayList<Integer> uIdsArray = new ArrayList<Integer>();
			List<PageData> uIds = memberService.selectUsersBy(pd);
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
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		
		titles.add("姓名"); // 2
		titles.add("昵称"); // 3
		titles.add("电话号码"); // 7
		titles.add("个人优惠比例"); // 8
		titles.add("会员类型"); // 9
		titles.add("会员优惠比例"); // 10
		titles.add("储值卡"); // 11
		titles.add("返点"); // 12
		titles.add("钱包");
		titles.add("积分");
		
		dataMap.put("titles", titles);
		List<QueryUser> varOList = memberService
				.selectUserAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getName());
			vpd.put("var2", varOList.get(i).getUserName());
			vpd.put("var3", varOList.get(i).getPhone());
			vpd.put("var4", String.valueOf(varOList.get(i).getProportion()));
			vpd.put("var5", varOList.get(i).getCategoryName());
			vpd.put("var6", String.valueOf(varOList.get(i).getCategoryProportion())); // 7
			
			if (null == String.valueOf(varOList.get(i).getRemainMoney())) {
				vpd.put("var7","0.00"); 
			}else{
			    vpd.put("var7", String.valueOf(varOList.get(i).getRemainMoney())); // 8
			}
			if(null==String.valueOf(varOList.get(i).getRemainPoints())){
				vpd.put("var8","0.00");
			}else{
			   vpd.put("var8", String.valueOf(varOList.get(i).getRemainPoints())); // 9
			}
			if(null==String.valueOf(varOList.get(i).getSumMoney())){
				vpd.put("var9","0.00");
			}else{
			   vpd.put("var9", String.valueOf(varOList.get(i).getSumMoney())); // 10
			}
			vpd.put("var10", String.valueOf(varOList.get(i).getCredit())); // 11
			
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	
	
	/*@RequestMapping(value = "/memberexcel")
	public ModelAndView exportmemberExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Member到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 添加检索条件
		// 根据姓名、微信名查询用户id
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
			
	
		"钱包");
		titles.add("储值卡"); // 11
		titles.add("返点"); // 12	
		titles.add("积分");
		
		dataMap.put("titles", titles);
		List<QueryUser> varOList = memberService.userexcel(pd);
		
		List<PageData> varList = new ArrayList<PageData>();
		for(int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getName());
			vpd.put("var2", varOList.get(i).getUserName());
			vpd.put("var3", varOList.get(i).getSex());
			vpd.put("var4", varOList.get(i).getPhone());
			vpd.put("var5", varOList.get(i).getCity());
			vpd.put("var6", String.valueOf(varOList.get(i).getProportion()));
			vpd.put("var7", varOList.get(i).getCategoryName());
			vpd.put("var8", String.valueOf(varOList.get(i).getCategoryProportion())); // 7
			
			if (null == String.valueOf(varOList.get(i).getRemainMoney())) {
				vpd.put("var9","0.00"); 
			}else{
			    vpd.put("var9", String.valueOf(varOList.get(i).getRemainMoney())); // 8
			}
			if(null==String.valueOf(varOList.get(i).getRemainPoints())){
				vpd.put("var10","0.00");
			}else{
			   vpd.put("var10", String.valueOf(varOList.get(i).getRemainPoints())); // 9
			}
			if(null==String.valueOf(varOList.get(i).getSumMoney())){
				vpd.put("var11","0.00");
			}else{
			   vpd.put("var11", String.valueOf(varOList.get(i).getSumMoney())); // 10
			}
			vpd.put("var12", varOList.get(i).getCredit()); // 11
		
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	*/
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,
				true));
	}

}
