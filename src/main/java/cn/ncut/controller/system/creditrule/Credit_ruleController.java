package cn.ncut.controller.system.creditrule;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import cn.ncut.service.system.creditrule.Credit_ruleManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.staff.impl.StaffService;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;


/** 
 * 说明：积分管理
 * 创建人：FH Q313596790
 * 创建时间：2016-12-20
 */
@Controller
@RequestMapping(value="/credit_rule")
public class Credit_ruleController extends BaseController {
	
	String menuUrl = "credit_rule/list.do"; //菜单地址(权限用)
	@Resource(name="credit_ruleService")
	private Credit_ruleManager credit_ruleService;
	
	@Resource(name="staffService")
	private StaffService staffService;
	
	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Credit_rule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREDIT_RULE_ID", this.get32UUID());	//主键
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		pd.put("CREATETIME", date);
		
	    Session session = Jurisdiction.getSession();
	    pd.put("STAFF_ID", ((Staff)session.getAttribute(Const.SESSION_USER)).getSTAFF_ID());
		credit_ruleService.save(pd);
		
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				"新增积分规则:消费换积分比例" + pd.getString("CASH_TOCREDIT"), this.getRequest()
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Credit_rule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		credit_ruleService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Credit_rule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		credit_ruleService.edit(pd);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		pd.put("CREATETIME", date);
		
	    Session session = Jurisdiction.getSession();
	    pd.put("STAFF_ID", ((Staff)session.getAttribute(Const.SESSION_USER)).getSTAFF_ID());
		
	    credit_ruleService.edit(pd);
	    
	    NCUTLOG.save(
				Jurisdiction.getUsername(),
				"修改积分规则:消费换积分比例" + pd.getString("CASH_TOCREDIT"), this.getRequest()
						.getRemoteAddr());
	    
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Credit_rule");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		List<PageData>	varList = credit_ruleService.list(page);	//列出Credit_rule列表
		List<PageData>  ruleList = new ArrayList<PageData>();
		for(PageData pp:varList){
			PageData staff = staffService.queryById((String) pp.get("STAFF_ID"));
			if(staff != null){
				pp.put("STAFF_ID", staff.get("STAFF_NAME"));
			}
			ruleList.add(pp);
		}
		
		mv.setViewName("system/creditrule/credit_rule_list");
		mv.addObject("varList", ruleList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		mv.setViewName("system/creditrule/credit_rule_edit");
		mv.addObject("msg", "save");
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
		pd = credit_ruleService.findById(pd);	//根据ID读取
		mv.setViewName("system/creditrule/credit_rule_edit");
		mv.addObject("msg", "edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Credit_rule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			credit_ruleService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Credit_rule到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("消费换积分");	//1
		titles.add("积分换现金");	//2
		titles.add("员工");	//3
		titles.add("创建时间");	//4
		titles.add("积分状态");	//5
		titles.add("项目名");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = credit_ruleService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("CASH_TOCREDIT").toString());	//1
			vpd.put("var2", varOList.get(i).get("CREDIT_TOCASH").toString());	//2
			vpd.put("var3", varOList.get(i).getString("STAFF_ID"));	    //3
			vpd.put("var4", varOList.get(i).getString("CREATETIME"));	    //4
			vpd.put("var5", varOList.get(i).get("STATUS").toString());	//5
			vpd.put("var6", varOList.get(i).getString("PROJECT_ID"));	    //6
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
