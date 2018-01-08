package cn.ncut.controller.finance.prestoremx;

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

import org.apache.shiro.session.Session;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.annotation.Token;
import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Staff;
import cn.ncut.service.finance.prestore.PreStoreManager;
import cn.ncut.service.finance.prestoremx.PreStoreMxManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Const;
import cn.ncut.util.DateUtil;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;


/** 
 * 说明：预存总金额(明细)
 * 创建人：FH Q313596790
 * 创建时间：2017-01-06
 */
@Controller
@RequestMapping(value="/prestoremx")
public class PreStoreMxController extends BaseController {
	
	String menuUrl = "prestoremx/list.do"; //菜单地址(权限用)
	@Resource(name="prestoremxService")
	private PreStoreMxManager prestoremxService;
	
	@Resource(name="prestoreService")
	private PreStoreManager prestoreService;
	
	@Resource(name="memberService")
	private MemberManager memberService;
	
	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	@Token(remove=true)
	public void save(HttpServletResponse response) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PreStoreMx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRESTOREMX_ID", this.get32UUID());	//主键
		
		// 得到当前的客服的门店编号
		Session session = Jurisdiction.getSession();
		Staff staff = ((Staff) session.getAttribute(Const.SESSION_USER));

		PageData p = new PageData();
		p.put("UID", Integer.parseInt(pd.getString("UID")));
		p = memberService.findById(p);
		pd.put("USERNAME", p.getString("name"));

		pd.put("STAFF_ID", staff.getSTAFF_ID());	//员工编号
		pd.put("CREATE_TIME", DateUtil.getTime());
		pd.put("TYPE", 1);
		
		// 判断预存表中是否有这个用户，如果有就update 没有就新增一条记录
		PageData presotrepd = prestoreService.findByUid(Integer.parseInt(pd.getString("UID")));
		PageData pre_pd = new PageData();
		String uuid = this.get32UUID();
		if (presotrepd == null) {//该用户没有预存
			pre_pd.put("PRESTORE_ID", uuid);
			pre_pd.put("UID", pd.getString("UID")); 
			pre_pd.put("USERNAME", pd.getString("USERNAME")); 
			pre_pd.put("PHONE", pd.getString("PHONE")); 
			pre_pd.put("SUM_MONEY", pd.get("PRESTOREMONEY"));
			prestoreService.save(pre_pd);
			
			pd.put("PRESTORE_ID", uuid);
		} else {//该用户已经有了预存
			Double sum_money = Double.parseDouble(pd.getString("PRESTOREMONEY")) + (Double)presotrepd.get("SUM_MONEY");
			pre_pd.put("PRESTORE_ID", presotrepd.getString("PRESTORE_ID"));
			pre_pd.put("SUM_MONEY", sum_money); 
			pre_pd.put("UID", pd.getString("UID")); 
			pre_pd.put("USERNAME", pd.getString("USERNAME")); 
			pre_pd.put("PHONE", pd.getString("PHONE")); 
			prestoreService.edit(pre_pd);
			
			pd.put("PRESTORE_ID", presotrepd.getString("PRESTORE_ID"));
		}
		
		//插入预存明细记录
		prestoremxService.save(pd);	
		
		
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				" 给用户" + (p.getString("name")!=null?p.getString("name"):p.getString("username"))+"钱包充值"+pd.get("PRESTOREMONEY") + "元", 
				this.getRequest().getRemoteAddr());
		
		response.getWriter().write("success");
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除PreStoreMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		prestoremxService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PreStoreMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		prestoremxService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表PreStoreMx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = prestoremxService.list(page);	//列出PreStoreMx列表
		mv.setViewName("finance/prestoremx/prestoremx_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	@Token(save=true)
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		//PageData pd = new PageData();
		//pd = this.getPageData();
		mv.setViewName("finance/prestoremx/prestoremx_edit");
		mv.addObject("msg", "save");
		//mv.addObject("pd", pd);
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
		pd = prestoremxService.findById(pd);	//根据ID读取
		mv.setViewName("finance/prestoremx/prestoremx_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 根据用户手机号查询用户记录
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/queryUsernameByPhone")
	public void queryUsernameByPhone(HttpServletResponse response) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> pdlist = new ArrayList<PageData>();
		pdlist = memberService.queryUsernameByPhone(pd.getString("phone"));
		
		response.setContentType("text/json;charset=utf-8");
		
		String responseJson = "{\"userlist\":" + new ObjectMapper().writeValueAsString(pdlist) + "}";
		response.getWriter().write(responseJson);
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PreStoreMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			prestoremxService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PreStoreMx到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户姓名");	//1
		titles.add("汇款单号");	//2
		titles.add("银行名称");	//3
		titles.add("汇款地址");	//4
		titles.add("预存金额");	//5
		titles.add("员工编号");	//6
		titles.add("创建时间");	//7
		titles.add("类型");	//8
		dataMap.put("titles", titles);
		List<PageData> varOList = prestoremxService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("UID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("REMITNO"));	    //2
			vpd.put("var3", varOList.get(i).getString("BANK"));	    //3
			vpd.put("var4", varOList.get(i).getString("ADDRESS"));	    //4
			vpd.put("var5", varOList.get(i).getString("PRESTOREMONEY"));	    //5
			vpd.put("var6", varOList.get(i).getString("STAFF_ID"));	    //6
			vpd.put("var7", varOList.get(i).getString("CREATE_TIME"));	    //7
			vpd.put("var8", varOList.get(i).get("TYPE").toString());	//8
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
