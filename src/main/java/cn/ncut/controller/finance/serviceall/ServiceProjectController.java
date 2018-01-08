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
import cn.ncut.entity.system.Staff;
import cn.ncut.service.finance.serviceall.ServiceProjectManager;
import cn.ncut.service.finance.serviceall.impl.ServiceCategoryService;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;

/**
 * 说明：服务项目 创建人：ljj 创建时间：2016-12-16
 */
@Controller
@RequestMapping(value = "/serviceproject")
public class ServiceProjectController extends BaseController {

	String menuUrl = "serviceproject/list.do"; // 菜单地址(权限用)
	@Resource(name = "serviceprojectService")
	private ServiceProjectManager serviceprojectService;

	@Resource(name = "servicecategoryService")
	private ServiceCategoryService servicecategoryService;

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
		logBefore(logger, Jurisdiction.getUsername() + "新增ServiceProject");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("PROJECT_ID", this.get32UUID()); //主键
		serviceprojectService.save(pd);

		NCUTLOG.save(
				Jurisdiction.getUsername(),
				" 新增服务项目类别:" + pd.getString("PNAME") + "("
						+ pd.getString("REMARK") + ")" + "，服务编码"
						+ pd.getString("PCODE"), this.getRequest()
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
		logBefore(logger, Jurisdiction.getUsername() + "删除ServiceProject");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		serviceprojectService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改ServiceProject");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		serviceprojectService.edit(pd);
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				"修改服务项目类别:" + pd.getString("PNAME") + "("
						+ pd.getString("REMARK") + ")" + "，服务编码"
						+ pd.getString("PCODE"), this.getRequest()
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
		logBefore(logger, Jurisdiction.getUsername() + "列表ServiceProject");
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
		List<PageData> varList = serviceprojectService.list(page); // 列出ServiceProject列表

		// 遍历得到子分类的每一层父分类
		for (PageData p : varList) {
			StringBuffer sb = new StringBuffer();
			Integer cid = (Integer) p.get("CATEGORY_ID");
			if (cid != null) {
				PageData p1 = servicecategoryService.findById(cid);
				if (p1 != null) {
					test(p1, sb);
					p.put("CATEGORY_NAME", splitString(sb.toString())
							.substring(1, sb.toString().length()));
				}
			}

		}
		mv.setViewName("finance/serviceproject/serviceproject_list");
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
		mv.setViewName("finance/serviceproject/serviceproject_edit");
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
		pd = serviceprojectService.findById(pd); // 根据ID读取
		mv.setViewName("finance/serviceproject/serviceproject_edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ServiceProject");
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
			serviceprojectService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出ServiceProject到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("所属类别"); // 1
		titles.add("项目名"); // 2
		titles.add("单位"); // 3
		titles.add("备注"); // 4
		titles.add("服务-收入编码"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = serviceprojectService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("CATEGORY_ID").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("PNAME")); // 2
			vpd.put("var3", varOList.get(i).getString("UNIT")); // 3
			vpd.put("var4", varOList.get(i).getString("REMARK")); // 4
			vpd.put("var5", varOList.get(i).getString("PCODE")); // 5
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
