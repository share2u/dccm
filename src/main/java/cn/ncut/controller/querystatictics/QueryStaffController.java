package cn.ncut.controller.querystatictics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.QueryOrder;
import cn.ncut.entity.system.Role;
import cn.ncut.entity.system.Staff;
import cn.ncut.service.system.depart.DepartManager;
import cn.ncut.service.system.role.RoleManager;
import cn.ncut.service.system.servicemodule.ServicemoduleManager;
import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.service.system.store.StoreManager;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;

@Controller
@RequestMapping(value="/queryStaff")
public class QueryStaffController extends BaseController{
	
	String menuUrl = "queryStaff/list.do"; // 菜单地址(权限用)
	@Resource(name = "staffService")
	private StaffManager staffService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "roleService")
	private RoleManager roleService;
	@Resource(name = "departService")
	private DepartManager departService;
	@Resource(name = "servicemoduleService")
	private ServicemoduleManager servicemoduleService;
	
	@RequestMapping(value = "/list")
	public ModelAndView listStaffs(Page page, HttpSession session)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String model = pd.getString("model"); // 关键词检索条件
		if (null != model && !"".equals(model)) {
			pd.put("model", model.trim());
		}
		String stores = pd.getString("stores"); // 关键词检索条件
		if (null != stores && !"".equals(stores)) {
			pd.put("stores", stores.trim());
		}
		String departId = pd.getString("departId"); // 关键词检索条件
		if (null != departId && !"".equals(departId)) {
			pd.put("departId", departId.trim());
		}
		String staffNames = pd.getString("staffNames"); // 关键词检索条件
		if (null != staffNames && !"".equals(staffNames)) {
			pd.put("staffNames", staffNames.trim());
		}
		String status  = pd.getString("status"); // 关键词检索条件
		if (null != status  && !"".equals(status)) {
			pd.put("status", status.trim());
		}
		
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());
		
		List<PageData>	storeList = storeService.findAllNames(pd);
		page.setPd(pd);
		List<PageData> departs = departService.listAll(pd);
		List<PageData> staffList = staffService
				.findstaffsBySome(page); // 列出用户列表
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);// 列出所有系统用户角色
		List<PageData> serviceModules = servicemoduleService.listAll0(pd);
		mv.setViewName("querysatistics/staff_query");
		mv.addObject("staffList", staffList);
		mv.addObject("storeList", storeList);
		mv.addObject("roleList", roleList);
		mv.addObject("departs", departs);
		mv.addObject("serviceModules", serviceModules);
		mv.addObject("pd", pd);
		mv.addObject("StoreId",pd.get("STOREID"));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	@RequestMapping(value="findStoreByModelId")
	@ResponseBody
	public String findStoreByModelId(HttpServletRequest request, HttpSession session) throws Exception{
		String modelId = request.getParameter("SERVICEMODULE_ID");
		PageData pd = new PageData();
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());
		String jsonString ="";
		if(null != modelId){
			pd.put("mId", modelId);
			List<PageData> stores = storeService.selectStoresBymId(pd);
			jsonString = new ObjectMapper().writeValueAsString(stores);
		}
		return jsonString;
	}
	@RequestMapping(value="findstaffsByStoreId")
	@ResponseBody
	public String findstaffsByStoreId(HttpServletRequest request) throws Exception{
		String storeId = request.getParameter("storeId");
		PageData pd = new PageData();
		String jsonString ="";
		if(null != storeId){
			pd.put("STORE_ID", storeId);
			List<PageData> staffs = staffService.findstaffsByStoreId(pd);
			jsonString = new ObjectMapper().writeValueAsString(staffs);
		}
		return jsonString;
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出QueryStaffs到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		//检索条件
		String model = pd.getString("model"); // 关键词检索条件
		if (null != model && !"".equals(model)) {
			pd.put("model", model.trim());
		}
		String stores = pd.getString("stores"); // 关键词检索条件
		if (null != stores && !"".equals(stores)) {
			pd.put("stores", stores.trim());
		}
		String departId = pd.getString("departId"); // 关键词检索条件
		if (null != departId && !"".equals(departId)) {
			pd.put("departId", departId.trim());
		}
		String staffNames = pd.getString("staffNames"); // 关键词检索条件
		if (null != staffNames && !"".equals(staffNames)) {
			pd.put("staffNames", staffNames.trim());
		}
		String status  = pd.getString("status"); // 关键词检索条件
		if (null != status  && !"".equals(status)) {
			pd.put("status", status.trim());
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("姓名"); // 1
		titles.add("职别"); // 2
		titles.add("用户名"); // 3
		titles.add("门店"); // 4
		titles.add("性别"); // 5
		titles.add("电话"); // 5
		titles.add("专家介绍"); // 5
		titles.add("所属部门"); // 5
		titles.add("出诊信息"); // 5
		titles.add("状态"); // 5
		titles.add("邮箱"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = staffService.findstaffsBySomeALL(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STAFF_NAME")); // 1
			vpd.put("var2", varOList.get(i).getString("RANK_NAME")); // 2
			vpd.put("var3", varOList.get(i).getString("USERNAME")); // 3
			vpd.put("var4", varOList.get(i).getString("STORE_NAME")); // 4
			if((Integer)(varOList.get(i).get("SEX")) ==1){
				vpd.put("var5", "女"); // 5
			}else{
				vpd.put("var5", "男"); // 5
			}
			vpd.put("var6", varOList.get(i).getString("TELEPHONE")); // 5
			vpd.put("var7", varOList.get(i).getString("SPECIAL")); // 5
			vpd.put("var8", varOList.get(i).getString("DEP_NAME")); // 5	
			vpd.put("var9", varOList.get(i).getString("INFO")); // 5	
			if((Integer)(varOList.get(i).get("STATUS")) ==1){
				vpd.put("var10", "离职"); // 5
			}else{
				vpd.put("var10", "在职"); // 5
			}
			vpd.put("var11", varOList.get(i).getString("EMAIL")); // 5	
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

}
