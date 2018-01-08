package cn.ncut.controller.finance.serviceall;

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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.ServiceCategory;
import cn.ncut.service.finance.serviceall.ServiceCategoryManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;

/**
 * 说明：服务项目类别 创建人：ljj 创建时间：2016-12-16
 */
@Controller
@RequestMapping(value = "/servicecategory")
public class ServiceCategoryController extends BaseController {

	String menuUrl = "servicecategory/list.do"; // 菜单地址(权限用)
	@Resource(name = "servicecategoryService")
	private ServiceCategoryManager servicecategoryService;
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
		logBefore(logger, Jurisdiction.getUsername() + "新增ServiceCategory");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		servicecategoryService.save(pd);
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				" 新增服务项目类别:" + pd.getString("CATEGORY_NAME") + "("
						+ pd.getString("DESCRIPTION") + ")", this
						.getRequest().getRemoteAddr());
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
		logBefore(logger, Jurisdiction.getUsername() + "删除ServiceCategory");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdd = servicecategoryService.findById(pd);
		// 加入日志
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				" 删除服务项目类别:" + pdd.getString("CATEGORY_NAME") + "("
						+ pdd.getString("DESCRIPTION") + ")", this
						.getRequest().getRemoteAddr());
		servicecategoryService.delete(pd);

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
		logBefore(logger, Jurisdiction.getUsername() + "修改ServiceCategory");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		servicecategoryService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表ServiceCategory");
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
		List<PageData> varList = servicecategoryService.list(page); // 列出ServiceCategory列表

		for (PageData p : varList) {
			StringBuffer sb = new StringBuffer();

			/*
			 * 注释的方法为去掉最底层的分类 Integer id = (Integer) p.get("F_CATEGORY_ID");
			 * if(id!=null){ PageData p2 = servicecategoryService.findById(id);
			 * if(p2 != null){ test(p2,sb); p.put("F_CATEGORY_NAME",
			 * splitString(sb.toString()).substring(1,sb.toString().length()));
			 * } }
			 */

			test(p, sb);
			p.put("F_CATEGORY_NAME",
					splitString(sb.toString()).substring(1,
							sb.toString().length()));
		}
		mv.setViewName("finance/servicecategory/servicecategory_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 遍历得到子分类的每一层父分类
	 * 
	 * @param p
	 * @param sb
	 * @throws Exception
	 */
	public void test(PageData p, StringBuffer sb) throws Exception {
		while (true) {
			sb.append(">" + p.getString("CATEGORY_NAME"));
			if ((Integer) p.get("F_CATEGORY_ID") == 0) {
				break;
			} else {
				Integer id = (Integer) p.get("F_CATEGORY_ID");
				// int id = Integer.parseInt(name);
				PageData fp = servicecategoryService.findById(id);
				test(fp, sb);
			}
			break;
		}
	}

	/**
	 * 将字符串数组反转
	 * 
	 * @param s
	 * @return
	 */
	public String splitString(String s) {
		String[] arr = s.split(">");
		String st = "";
		for (int i = arr.length; i > 0; i--) {
			st = st + ">" + arr[i - 1];
		}
		return st;
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

		mv.setViewName("finance/servicecategory/servicecategory_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 通过ajax获得分类的树形结构
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllCategorys")
	public void getAllCategorys(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();

		List<ServiceCategory> allcategorylist = servicecategoryService
				.queryAllCategory(pd);
		List<String> lstTree = new ArrayList<String>();
		String s = "";
		int i = 1;
		for (ServiceCategory sc : allcategorylist) {
			s = "{id:" + sc.getSERVICECATEGORY_ID() + ", pId:"
					+ sc.getF_CATEGORY_ID() + ", digit:" + i ++ + ", name:\""
					+ sc.getCATEGORY_NAME() + "\" , open:false}";
			lstTree.add(s);
		}

		// 利用Json插件将Array转换成Json格式
		String returnvalue = JSONArray.fromObject(lstTree).toString();
		String value = "success";
		response.setCharacterEncoding("UTF-8");
		String json = "{\"success\":\"" + value + "\",\"returnvalue\":"
				+ returnvalue + "}";
		response.getWriter().print(json);
	}

	/*
	 * public List<ServiceCategory> readCategory(List<ServiceCategory> list, int
	 * pid){ for(ServiceCategory sc : list){ if(sc.getF_CATEGORY_ID() == pid){
	 * readCategory(list,sc.getSERVICECATEGORY_ID());
	 * System.out.println(sc.getCATEGORY_NAME()); } } return null; }
	 */

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
		pd = servicecategoryService.findById(pd); // 根据ID读取
		mv.setViewName("finance/servicecategory/servicecategory_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ServiceCategory");
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
			servicecategoryService.deleteAll(ArrayDATA_IDS);
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
				+ "导出ServiceCategory到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("类别名称"); // 1
		titles.add("父类别"); // 2
		titles.add("备注"); // 3
		titles.add("状态"); // 4
		dataMap.put("titles", titles);
		List<PageData> varOList = servicecategoryService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("CATEGORY_NAME")); // 1
			vpd.put("var2", varOList.get(i).get("F_CATEGORY_ID").toString()); // 2
			vpd.put("var3", varOList.get(i).getString("DESCRIPTION")); // 3
			vpd.put("var4", varOList.get(i).get("STATUS").toString()); // 4
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
