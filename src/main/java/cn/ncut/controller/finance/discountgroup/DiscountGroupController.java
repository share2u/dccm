package cn.ncut.controller.finance.discountgroup;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import cn.ncut.service.finance.discount.DiscountManager;
import cn.ncut.service.finance.discount.impl.DiscountService;
import cn.ncut.service.finance.discountgroup.DiscountGroupManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.staff.impl.StaffService;
import cn.ncut.service.user.member.impl.MemberService;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Const;
import cn.ncut.util.DateUtil;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;


/** 
 * 说明：优惠券组
 * 创建人：FH Q313596790
 * 创建时间：2017-02-16
 */
@Controller
@RequestMapping(value="/discountgroup")
public class DiscountGroupController extends BaseController {
	
	String menuUrl = "discountgroup/list.do"; //菜单地址(权限用)
	@Resource(name="discountgroupService")
	private DiscountGroupManager discountgroupService;
	
	@Resource(name="discountService")
	private DiscountManager discountService;
	
	@Resource(name = "staffService")
	private StaffService staffService;

	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;

	// 定义全局变量，防止在重新换页是原来的值带不过去
	String discountId = ""; //优惠券id
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DiscountGroup");
		
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		Session session = Jurisdiction.getSession();
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String groupid = this.get32UUID();  //优惠券组合id
		
		//获取前台传入的json数据，逐条保存
		JSONArray data = JSONArray.fromObject(pd.getString("jsonData"));
		
		for(int i=0; i<data.size(); i++){
			
			JSONObject obj =  (JSONObject) data.get(i);
			
		    String discountid = obj.getString("discountid");
		    String number = obj.getString("number");
		    
		    //构造要保存的每条记录
		    PageData groupDiscount = new PageData();
		    groupDiscount.put("GROUP_ID", groupid);
		    groupDiscount.put("GROUP_NAME", pd.getString("GROUP_NAME"));
		    groupDiscount.put("DISCOUNT_ID", discountid);
		    groupDiscount.put("DISCOUNT_NUMBER", number);
		    groupDiscount.put("CREATE_TIME", DateUtil.getTime());
		    groupDiscount.put("STAFF_ID", ((Staff) session.getAttribute(Const.SESSION_USER)).getSTAFF_ID());
		    groupDiscount.put("REMARK",pd.getString("REMARK"));
		    discountgroupService.save(groupDiscount);
		}
		
		// 存入日志
		NCUTLOG.save(Jurisdiction.getUsername(),
				" 创建了一个优惠券组合：" + pd.getString("GROUP_NAME"), this.getRequest()
						.getRemoteAddr());
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除DiscountGroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		discountgroupService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DiscountGroup");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		Session session = Jurisdiction.getSession();
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String old_groupid = pd.getString("GROUP_ID");
		
		//查询属于同一个组合的记录
		List<PageData> discountlist = new ArrayList<PageData>();
		discountlist = discountgroupService.findByGroupId(old_groupid);
		
		//修改时首先把原来的组合的记录全部删除，再插入新的
		for(PageData p : discountlist){
			discountgroupService.delete(p);
		}
		
		String groupid = old_groupid;  //新的优惠券组合等于原来的groupid
		
		//获取前台传入的json数据，逐条保存
		JSONArray data = JSONArray.fromObject(pd.getString("jsonData"));
		
		for(int i=0; i<data.size(); i++){
			
			JSONObject obj =  (JSONObject) data.get(i);
			
		    String discountid = obj.getString("discountid");
		    String number = obj.getString("number");
		    
		    //构造要保存的每条记录
		    PageData groupDiscount = new PageData();
		    groupDiscount.put("GROUP_ID", groupid);
		    groupDiscount.put("GROUP_NAME", pd.getString("GROUP_NAME"));
		    groupDiscount.put("DISCOUNT_ID", discountid);
		    groupDiscount.put("DISCOUNT_NUMBER", number);
		    groupDiscount.put("CREATE_TIME", DateUtil.getTime());
		    groupDiscount.put("STAFF_ID", ((Staff) session.getAttribute(Const.SESSION_USER)).getSTAFF_ID());
		    groupDiscount.put("REMARK",pd.getString("REMARK"));
		    discountgroupService.save(groupDiscount);
		}
		// 存入日志
		NCUTLOG.save(Jurisdiction.getUsername(),  " 修改优惠券组合:"
								+ pd.getString("GROUP_NAME"), this.getRequest().getRemoteAddr());
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表DiscountGroup");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = discountgroupService.list(page);	//列出DiscountGroup列表
		mv.setViewName("finance/discountgroup/discountgroup_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	
	/**去查看优惠券组合明细页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goScanGroupDetail")
	public ModelAndView goScanGroupDetail()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询属于同一个组合的记录
		List<PageData> discountlist = new ArrayList<PageData>();
		discountlist = discountgroupService.findByGroupId(pd.getString("GROUP_ID"));
		
		mv.setViewName("finance/discountgroup/scanGroupDetail");
		mv.addObject("msg", "save");
		mv.addObject("varList", discountlist);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 查看已经发放该优惠券组合的会员 
	 * @return
	 */
	@RequestMapping(value = "/goScanUser")
	public ModelAndView goScanUser(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		
		String lastStart = pd.getString("lastStart");//获得开始时间
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", lastStart+" 00:00:01");
		}
		String lastEnd = pd.getString("lastEnd");//获得结束时间
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", lastEnd+" 23:59:59");
		}
		page.setPd(pd);
		
		List<PageData> userpdlist = discountService.queryUserByGroupid(page);
		
		mv.setViewName("finance/discountgroup/scanUsers");
		mv.addObject("GROUP_ID", pd.getString("GROUP_ID"));
		mv.addObject("num",userpdlist.size());
		mv.addObject("userpdlist", userpdlist);
		mv.addObject("lastStart", pd.getString("lastStart"));
		mv.addObject("lastEnd", pd.getString("lastEnd"));
		return mv;
	}
	
	
	
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询出所有的优惠券
		List<PageData> alldiscountlist = new ArrayList<PageData>();
		alldiscountlist = discountService.listAll(new PageData());
		
		mv.setViewName("finance/discountgroup/discountgroup_edit");
		mv.addObject("msg", "save");
		mv.addObject("alldiscountlist", alldiscountlist);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = discountgroupService.findByGroupId(pd.getString("GROUP_ID")).get(0);	//根据ID读取
		
		//查询出所有的优惠券
		List<PageData> alldiscountlist = new ArrayList<PageData>();
		alldiscountlist = discountService.listAll(new PageData());
		
		mv.setViewName("finance/discountgroup/discountgroup_edit");
		mv.addObject("msg", "edit");
		mv.addObject("alldiscountlist",alldiscountlist);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除DiscountGroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			discountgroupService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出DiscountGroup到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("组ID");	//1
		titles.add("优惠券组合名称");	//2
		titles.add("优惠券id");	//3
		titles.add("组合中该种优惠券的数量");	//4
		titles.add("创建时间");	//5
		titles.add("客服id");	//6
		titles.add("备注");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = discountgroupService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("GROUP_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("GROUP_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("DISCOUNT_ID"));	    //3
			vpd.put("var4", varOList.get(i).getString("DISCOUNT_NUMBER"));	    //4
			vpd.put("var5", varOList.get(i).getString("CREATE_TIME"));	    //5
			vpd.put("var6", varOList.get(i).getString("STAFF_ID"));	    //6
			vpd.put("var7", varOList.get(i).getString("REMARK"));	    //7
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
