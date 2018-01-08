package cn.ncut.controller.finance.discount;

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
import cn.ncut.service.finance.serviceall.impl.ServiceProjectService;
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
 * 说明：优惠券 创建人：ljj 创建时间：2016-12-20
 */
@Controller
@RequestMapping(value = "/discount")
public class DiscountController extends BaseController {

	String menuUrl = "discount/list.do"; // 菜单地址(权限用)
	@Resource(name = "discountService")
	private DiscountManager discountService;

	@Resource(name = "staffService")
	private StaffService staffService;

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "serviceprojectService")
	private ServiceProjectService serviceprojectService;

	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;

	// 定义全局变量，防止在重新换页是原来的值带不过去
	String discountId = ""; //优惠券id
	int discountnumber = 0; //优惠券总数量减去已经发放数量

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Discount");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("DISCOUNT_ID", this.get32UUID()); // 主键
		// 插入时间
		pd.put("CREATETIME", DateUtil.getTime());
		// 插入创建人
		Session session = Jurisdiction.getSession();
		// session.getAttribute(Const.SESSION_USER);
		pd.put("STAFF_ID", ((Staff) session.getAttribute(Const.SESSION_USER))
				.getSTAFF_ID());
		
		pd.put("A_NUMBER", '0');
		discountService.save(pd);
		// 存入日志
		NCUTLOG.save(Jurisdiction.getUsername()," 创建优惠券:"
				+ pd.get("DISCOUNT_NAME")+",数量："+pd.getString("NUMBER")+"张", this.getRequest().getRemoteAddr());

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
		logBefore(logger, Jurisdiction.getUsername() + "删除Discount");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdd = discountService.findById(pd);
		discountService.delete(pd);
		// 存入日志
		NCUTLOG.save(Jurisdiction.getUsername(),  " 删除优惠券:"
				+ pdd.get("DISCOUNT_NAME"), this.getRequest().getRemoteAddr());
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
		logBefore(logger, Jurisdiction.getUsername() + "修改Discount");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 插入时间
		pd.put("CREATETIME", DateUtil.getTime());
		// 插入创建人
		Session session = Jurisdiction.getSession();
		// session.getAttribute(Const.SESSION_USER);
		pd.put("STAFF_ID", ((Staff) session.getAttribute(Const.SESSION_USER))
				.getSTAFF_ID());
		// 存入日志
		NCUTLOG.save(Jurisdiction.getUsername(),  " 修改优惠券:"
						+ pd.get("DISCOUNT_NAME"), this.getRequest().getRemoteAddr());
		discountService.edit(pd);
		
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表（财务，优惠券规则制定）
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Discount");
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
		List<PageData> varList = discountService.list(page); // 列出Discount列表

		for (PageData p : varList) {
			PageData staffpd = staffService.queryById(p.getString("STAFF_ID"));
			p.put("staff_name", staffpd.get("STAFF_NAME"));
			if(isMidTime(p.getString("STARTTIME"),p.getString("ENDTIME"))){
				p.put("STATUS", "1");
			}else{
				p.put("STATUS", "0");
			}
			discountService.edit(p);
		}
		mv.setViewName("finance/discount/discount_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 列表（经营，优惠券下发）已经废弃
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list2")
	public ModelAndView list2(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Discount");
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
		List<PageData> varList = discountService.list(page); // 列出Discount列表

		for (PageData p : varList) {
			PageData staffpd = staffService.queryById(p.getString("STAFF_ID"));
			p.put("staff_name", staffpd.get("STAFF_NAME"));
			if(isMidTime(p.getString("STARTTIME"),p.getString("ENDTIME"))){
				p.put("STATUS", "1");
			}else{
				p.put("STATUS", "0");
			}
			discountService.edit(p);
		}
		mv.setViewName("finance/discount/discount_list2");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	//查询时判断当前时间是否在给定的时间之间
	public static boolean isMidTime(String s1 , String s2) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = new Date();
		return df.parse(s1).getTime()<nowDate.getTime()&&nowDate.getTime()<df.parse(s2).getTime();
	}

	@RequestMapping(value = "/listMember")
	public ModelAndView listMember(Page page) throws Exception {

		return null;
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
		mv.setViewName("finance/discount/discount_edit");
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
		pd = discountService.findById(pd); // 根据ID读取
		mv.setViewName("finance/discount/discount_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去查看可用项目的界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goScanProject")
	public ModelAndView goScanProject() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = discountService.findById(pd); // 根据ID读取

		String[] pids = pd.getString("PROJECT_IDS").split(",");

		List<String> projectsarr = new ArrayList<String>();

		for (int i = 0; i < pids.length; i++) {
			PageData p = new PageData();
			p.put("PID", pids[i]);
			String pname = serviceprojectService.findNameById(p);
			projectsarr.add(pname);
		}
		mv.setViewName("finance/discount/scanProjects");
		mv.addObject("projectsarr", projectsarr);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 下发优惠券，显示要下发的客户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goSend")
	public ModelAndView goSend(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = memberService.listmenberandgroup(page);

		//查出当前的优惠券
		pd = discountService.findById(pd);
		
		
		discountId = pd.getString("GROUP_ID");
		
		
			//还能发放的数量
		discountnumber = (Integer)pd.get("NUMBER")-(Integer)pd.get("A_NUMBER");
	
		mv.setViewName("finance/discount/discount_send");

		mv.addObject("varList", varList);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("discountid", discountId);
		mv.addObject("discountnumber", discountnumber);
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Discount");
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
			discountService.deleteAll(ArrayDATA_IDS);
			// 存入日志
			NCUTLOG.save(Jurisdiction.getUsername()," 删除了一批优惠券", this.getRequest().getRemoteAddr());
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 向指定用户发送优惠券(废弃)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendDiscount")
	public void sendDiscount(HttpServletResponse response) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "向指定用户发放优惠券");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		String did = pd.getString("DISCOUNT_ID");
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		String str = "";
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");

			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				PageData p = new PageData();
				p.put("UID", Integer.parseInt(ArrayDATA_IDS[i]));
				p.put("DISCOUNT_ID", did);
				p.put("CREATE_TIME", DateUtil.getDay());
				discountService.sendDiscountToUser(p);
				// 存入日志
				PageData pdd = discountService.findById(p);
				pd.putAll(pdd);
			}
			
			//减少优惠券的数量，更新已发数量
			PageData dispd = new PageData();
			dispd.put("DISCOUNT_ID", pd.getString("DISCOUNT_ID"));
			dispd = discountService.findById(dispd);
			
			int new_num = (Integer)dispd.get("A_NUMBER")+ArrayDATA_IDS.length;
			dispd.put("A_NUMBER", new_num);
			
			discountService.edit(dispd);
			
			NCUTLOG.save(Jurisdiction.getUsername(),  " 向用户发送优惠券"
					+ pd.get("DISCOUNT_NAME")+"，数量："+ArrayDATA_IDS.length+"张", this.getRequest().getRemoteAddr());
			
			str = "{\"success\":\"ok\"}";
		} else {
			str = "{\"error\":\"error\"}";
		}

		response.setContentType("text/javascript; charset=utf-8");
		response.getWriter().write(str);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Discount到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("优惠券名称"); // 1
		titles.add("优惠券金额"); // 2
		titles.add("开始时间"); // 3
		titles.add("结束时间"); // 4
		titles.add("创建时间"); // 5
		titles.add("创建人"); // 6
		titles.add("可用项目"); // 7
		titles.add("状态"); // 8
		dataMap.put("titles", titles);
		List<PageData> varOList = discountService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("DISCOUNT_NAME")); // 1
			vpd.put("var2", varOList.get(i).get("DISCOUNT_AMOUNT").toString()); // 2
			vpd.put("var3", varOList.get(i).getString("STARTTIME")); // 3
			vpd.put("var4", varOList.get(i).getString("ENDTIME")); // 4
			vpd.put("var5", varOList.get(i).getString("CREATETIME")); // 5
			vpd.put("var6", varOList.get(i).getString("STAFF_ID")); // 6
			vpd.put("var7", varOList.get(i).getString("PROJECT_IDS")); // 7
			vpd.put("var8", varOList.get(i).get("STATUS").toString()); // 8
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
}
