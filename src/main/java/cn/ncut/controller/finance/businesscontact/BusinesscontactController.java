package cn.ncut.controller.finance.businesscontact;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

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
import cn.ncut.service.finance.businesscontact.BusinesscontactManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;

/**
 * 说明：往来规则 创建人：FH Q313596790 创建时间：2016-12-26
 */
@Controller
@RequestMapping(value = "/businesscontact")
public class BusinesscontactController extends BaseController {

	String menuUrl = "businesscontact/list.do"; // 菜单地址(权限用)
	@Resource(name = "businesscontactService")
	private BusinesscontactManager businesscontactService;

	@Resource(name = "staffService")
	private StaffManager staffService;

	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Businesscontact");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 插入创建人
		Session session = Jurisdiction.getSession();
		pd.put("OPERATOR", ((Staff) session.getAttribute(Const.SESSION_USER))
				.getSTAFF_ID());
		pd.put("BUSINESSCONTACT_ID", this.get32UUID()); // 主键
		businesscontactService.save(pd);

		NCUTLOG.save(
				Jurisdiction.getUsername(),
				"新增业务规则，给业务员返点比例：" + pd.getString("TOSTAFF") + "，给门店返点比例："
						+ pd.getString("TOSTORE")+"，生效时间为:"+pd.getString("CREATETIME")+"，状态为"+(Integer.parseInt((String) pd.get("STATUS"))==0?"正常":"无效"), this.getRequest()
						.getRemoteAddr());

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
		logBefore(logger, Jurisdiction.getUsername() + "删除Businesscontact");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		businesscontactService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改Businesscontact");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		Session session = Jurisdiction.getSession();
		pd.put("STAFF_ID", ((Staff) session.getAttribute(Const.SESSION_USER))
				.getSTAFF_ID());
		businesscontactService.edit(pd);
		
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				"修改业务规则，给业务员返点比例：" + pd.getString("TOSTAFF") + "，给门店返点比例："
						+ pd.getString("TOSTORE")+"，生效时间为:"+pd.getString("CREATETIME")+"，状态为"+(Integer.parseInt((String) pd.get("STATUS"))==0?"正常":"无效"), this.getRequest()
						.getRemoteAddr());
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Businesscontact");
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
		List<PageData> varList = businesscontactService.list(page); // 列出Businesscontact列表

		List<PageData> businessList = new ArrayList<PageData>();
		for (PageData pp : varList) {
			PageData staff = staffService
					.queryById((String) pp.get("OPERATOR"));
			if (staff != null) {
				pp.put("OPERATOR", staff.get("STAFF_NAME"));
			}
			businessList.add(pp);
		}

		mv.setViewName("finance/businesscontact/businesscontact_list");
		mv.addObject("varList", businessList);
		mv.addObject("pd", pd);
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
		mv.setViewName("finance/businesscontact/businesscontact_edit");
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
		pd = businesscontactService.findById(pd); // 根据ID读取
		mv.setViewName("finance/businesscontact/businesscontact_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Businesscontact");
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
			businesscontactService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()
				+ "导出Businesscontact到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("员工业务规则"); // 1
		titles.add("门店业务规则"); // 2
		titles.add("创建时间"); // 3
		titles.add("状态"); // 4
		titles.add("操作员"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = businesscontactService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("TOSTAFF").toString()); // 1
			vpd.put("var2", varOList.get(i).get("TOSTORE").toString()); // 2
			vpd.put("var3", varOList.get(i).getString("CREATETIME")); // 3
			vpd.put("var4", varOList.get(i).get("STATUS").toString()); // 4
			vpd.put("var5", varOList.get(i).getString("OPERATOR")); // 5
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
