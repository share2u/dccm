package cn.ncut.controller.system.staff;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Role;
import cn.ncut.entity.system.Staff;
import cn.ncut.entity.system.Store;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Const;
import cn.ncut.util.FileDownload;
import cn.ncut.util.FileUpload;
import cn.ncut.util.GetPinyin;
import cn.ncut.util.ObjectExcelRead;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.PathUtil;
import cn.ncut.util.SequenceUtils;
import cn.ncut.util.Tools;
import cn.ncut.service.system.depart.DepartManager;
import cn.ncut.service.system.depart.impl.DepartService;
import cn.ncut.service.system.menu.MenuManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.rank.RankManager;
import cn.ncut.service.system.role.RoleManager;
import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.service.system.store.StoreManager;

/**
 * 
 * 
 * <p>
 * Title:StaffController
 * </p>
 * <p>
 * Description:总的用户控制器
 * </p>
 * <p>
 * Company:ncut
 * </p>
 * 
 * @author lph
 * @date 2016-12-9下午9:38:12
 * 
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffController extends BaseController {

	String menuUrl = "staff/listStaffs.do"; // 菜单地址(权限用)
	@Resource(name = "staffService")
	private StaffManager staffService;

	@Resource(name = "roleService")
	private RoleManager roleService;

	@Resource(name = "menuService")
	private MenuManager menuService;

	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;

	@Resource(name = "sequenceUtils")
	private SequenceUtils sequenceUtils;

	@Resource(name = "storeService")
	private StoreManager storeService;

	@Resource(name = "departService")
	private DepartManager departService;

	@Resource(name = "rankService")
	private RankManager rankService;

	/**
	 * 显示用户列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listStaffs")
	public ModelAndView listStaffs(Page page, HttpSession session)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart"); // 开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd"); // 结束时间
		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			pd.put("lastLoginEnd", lastLoginEnd + " 00:00:00");
		}
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());
		
		List<PageData>	storeList = storeService.findAllNames(pd);
		
		pd.put("STOREID", pd.get("STOREID"));
		page.setPd(pd);

		List<PageData> staffList = staffService
				.findStoreDepartStaffByStaff(page); // 列出用户列表
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);// 列出所有系统用户角色
		mv.setViewName("system/staff/staff_list");
		mv.addObject("staffList", staffList);
		mv.addObject("storeList", storeList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		//mv.addObject("StoreId",pd.get("STOREID"));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		mv.addObject("STORE_ID",pd.get("STOREID"));
		//mv.addObject("selectStoreid", pd.get("STOREID"));
		return mv;
	}

	/**
	 * 删除用户
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteU")
	public void deleteU(PrintWriter out) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "删除staff");
		PageData pd = new PageData();
		pd = this.getPageData();
		staffService.deleteU(pd);
		HttpServletRequest request =this.getRequest();
		String IP = request.getRemoteAddr();
		NCUTLOG.save(Jurisdiction.getUsername(), "删除系统用户：" + pd,IP);
		out.write("success");
		out.close();
	}

	/**
	 * 去新增用户页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAddU")
	public ModelAndView goAddU(HttpSession session) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "1");
		pd.put("STORE_ID",
				((Staff) Jurisdiction.getSession().getAttribute(
						Const.SESSION_USER)).getSTORE_ID());
		List<Role> roleList = roleService.listAllRolesByPId(pd);// 列出所有系统用户角色
		
		
		List<PageData> storeList = storeService.listAllStore(pd);
		List<PageData> departList = departService.listAll(pd);
		List<PageData> rankList = rankService.listAll(pd);
		mv.setViewName("system/staff/staff_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("rankList", rankList);
		mv.addObject("roleList", roleList);
		mv.addObject("storeList", storeList);
		mv.addObject("departList", departList);
		return mv;
	}

	/**
	 * 保存用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveU")
	public ModelAndView saveU(HttpServletRequest servletRequest,
			@ModelAttribute Staff staff) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增staff");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		staff.setSTAFF_ID(sequenceUtils.getNextId("S"));
		staff.setLAST_LOGIN("");
		staff.setIP("");
		staff.setSKIN("default");
		staff.setRIGHTS("");
		staff.setPASSWORD(new SimpleHash("SHA-1", staff.getUSERNAME(), staff
				.getPASSWORD()).toString());

		// 上传图片
		List<MultipartFile> images = staff.getUploadimages();
		if (null != images && images.size() > 0) {
			for (MultipartFile multipartFile : images) {
				String imageName = multipartFile.getOriginalFilename();
				String extraName = imageName.substring(imageName
						.lastIndexOf(".") + 1);
				if (null != imageName.trim() && !"".equals(imageName.trim())) {
					imageName = get32UUID() + "." + extraName;
					File imageFile = new File(servletRequest.getSession()
							.getServletContext()
							.getRealPath("uploadFiles/staffimg"), imageName);
					staff.setSTAFF_IMG(imageName);

					try {
						multipartFile.transferTo(imageFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}

		HttpServletRequest request =this.getRequest();
		String IP = request.getRemoteAddr();
		if (null == staffService.findByUsername(pd)) { // 判断用户名是否存在
			NCUTLOG.save(Jurisdiction.getUsername(),
					"新增系统用户：" + staff.getSTAFF_NAME(),IP);
			staffService.saveU(staff); // 执行保存
			mv.addObject("msg", "success");
		} else {
			mv.addObject("msg", "failed");
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 判断用户名是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasU")
	@ResponseBody
	public Object hasU() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (staffService.findByUsername(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断邮箱是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasE")
	@ResponseBody
	public Object hasE() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (staffService.findByUE(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断编码是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasN")
	@ResponseBody
	public Object hasN() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (staffService.findByUN(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 去修改用户页面(系统用户列表修改)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditU")
	public ModelAndView goEditU() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("1".equals(pd.getString("STAFF_ID"))) {
			return null;
		} // 不能修改admin用户

		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd); // 列出所有系统用户角色
		pd.put("STORE_ID",
				((Staff) Jurisdiction.getSession().getAttribute(
						Const.SESSION_USER)).getSTORE_ID());
		List<PageData> storeList = storeService.listAllStore(pd);
		List<PageData> departList = departService.listAll(pd);
		List<PageData> rankList = rankService.listAll(pd);
		pd = staffService.findById(pd);
		mv.addObject("fx", "staff"); // 根据ID读取
		mv.setViewName("system/staff/staff_edit");
		mv.addObject("rankList", rankList);
		
		mv.addObject("storeList", storeList);
		mv.addObject("departList", departList);
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 去修改用户页面(个人修改)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditMyU")
	public ModelAndView goEditMyU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "head");
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd); // 列出所有系统用户角色
		pd.put("USERNAME", Jurisdiction.getUsername());
		pd = staffService.findByUsername(pd); // 根据用户名读取
		mv.setViewName("system/staff/staff_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 查看用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view")
	public ModelAndView view() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("admin".equals(pd.getString("USERNAME"))) {
			return null;
		} // 不能查看admin用户
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd); // 列出所有系统用户角色
		pd = staffService.findByUsername(pd); // 根据ID读取
		mv.setViewName("system/staff/staff_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 去修改用户页面(在线管理页面打开)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditUfromOnline")
	public ModelAndView goEditUfromOnline() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("admin".equals(pd.getString("USERNAME"))) {
			return null;
		} // 不能查看admin用户
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd); // 列出所有系统用户角色
		pd = staffService.findByUsername(pd); // 根据ID读取
		mv.setViewName("system/staff/staff_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/editU")
	public ModelAndView editU(HttpServletRequest servletRequest,
			@ModelAttribute Staff staff) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改staff");
		ModelAndView mv = this.getModelAndView();

		if (staff.getPASSWORD().length()<30&&staff.getPASSWORD() != null && !"".equals(staff.getPASSWORD())) {
			staff.setPASSWORD(new SimpleHash("SHA-1", staff.getUSERNAME(),
					staff.getPASSWORD()).toString());
		}

		// 上传图片
		List<MultipartFile> images = staff.getUploadimages();
		if (null != images && images.size() > 0) {
			for (MultipartFile multipartFile : images) {
				String imageName = multipartFile.getOriginalFilename();
				String extraName = imageName.substring(imageName
						.lastIndexOf(".") + 1);
				if (null != imageName.trim() && !"".equals(imageName.trim())) {
					imageName = get32UUID() + "." + extraName;
					File imageFile = new File(servletRequest.getSession()
							.getServletContext()
							.getRealPath("uploadFiles/staffimg"), imageName);
					File oldImagePath = new File(servletRequest.getSession()
							.getServletContext()
							.getRealPath("uploadFiles/staffimg"),
							staff.getSTAFF_IMG());
					if (oldImagePath.exists()) {
						oldImagePath.delete();
					}
					staff.setSTAFF_IMG(imageName);

					try {
						multipartFile.transferTo(imageFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
		HttpServletRequest request =this.getRequest();
		String IP = request.getRemoteAddr();
		NCUTLOG.save(Jurisdiction.getUsername(),
				"修改系统用户：" + staff.getSTAFF_NAME(),IP);
		staffService.editU(staff); // 执行修改

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "批量删除user");
		HttpServletRequest request =this.getRequest();
		String IP = request.getRemoteAddr();
		NCUTLOG.save(Jurisdiction.getUsername(), "批量删除user",IP);
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String STAFF_IDS = pd.getString("STAFF_IDS");
		if (null != STAFF_IDS && !"".equals(STAFF_IDS)) {
			String ArraySTAFF_IDS[] = STAFF_IDS.split(",");
			staffService.deleteAllU(ArraySTAFF_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出用户信息到EXCEL
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		HttpServletRequest request =this.getRequest();
		String IP = request.getRemoteAddr();
		NCUTLOG.save(Jurisdiction.getUsername(), "导出用户信息到EXCEL",IP);
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
				String keywords = pd.getString("keywords"); // 关键词检索条件
				if (null != keywords && !"".equals(keywords)) {
					pd.put("keywords", keywords.trim());
				}
				String lastLoginStart = pd.getString("lastLoginStart"); // 开始时间
				String lastLoginEnd = pd.getString("lastLoginEnd"); // 结束时间
				if (lastLoginStart != null && !"".equals(lastLoginStart)) {
					pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
				}
				if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
					pd.put("lastLoginEnd", lastLoginEnd + " 00:00:00");
				}
				Map<String, Object> dataMap = new HashMap<String, Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("员工姓名"); // 1
				titles.add("职别"); // 2
				titles.add("用户名"); // 3
				titles.add("密码"); // 4
				titles.add("门店编号"); // 5
				titles.add("性别 0男 1女"); // 6
				titles.add("身份证号"); // 7
				titles.add("电话"); // 8
				titles.add("专长"); // 9
				titles.add("专家介绍"); // 10
				titles.add("所属部门"); // 11
				titles.add("头像"); // 12
				titles.add("部门负责人ID"); // 13
				titles.add("状态 0正常 1离职"); // 14
				titles.add("权限"); // 15
				titles.add("角色ID"); // 16
				titles.add("上次登录时间"); // 17
				titles.add("登录IP"); // 18
				titles.add("邮箱"); // 19
				titles.add("皮肤"); // 20
				dataMap.put("titles", titles);
				List<PageData> staffList = staffService.listAllStaff(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for (int i = 0; i < staffList.size(); i++) {
					PageData vpd = new PageData();
					vpd.put("var1", staffList.get(i).getString("STAFF_NAME")); // 1
					vpd.put("var2", staffList.get(i).getString("RANK_ID")); // 2
					vpd.put("var3", staffList.get(i).getString("USERNAME")); // 3
					vpd.put("var4", staffList.get(i).getString("PASSWORD")); // 4
					vpd.put("var5", staffList.get(i).get("STORE_ID").toString()); // 5
					vpd.put("var6", staffList.get(i).get("SEX").toString()); // 6
					vpd.put("var7", staffList.get(i).getString("ID_NUMBER")); // 7
					vpd.put("var8", staffList.get(i).getString("TELEPHONE")); // 8
					vpd.put("var9", staffList.get(i).getString("SPECIAL")); // 9
					vpd.put("var10", staffList.get(i).getString("INFO")); // 10
					vpd.put("var11", staffList.get(i).get("DEP_NO").toString()); // 11
					vpd.put("var12", staffList.get(i).getString("STAFF_IMG")); // 12
					vpd.put("var13", staffList.get(i).get("DEP_HEADER")
							.toString()); // 13
					vpd.put("var14", staffList.get(i).get("STATUS").toString()); // 14
					vpd.put("var15", staffList.get(i).getString("RIGHTS")); // 15
					vpd.put("var16", staffList.get(i).getString("ROLE_ID")); // 16
					vpd.put("var17", staffList.get(i).getString("LAST_LOGIN")); // 17
					vpd.put("var18", staffList.get(i).getString("IP")); // 18
					vpd.put("var19", staffList.get(i).getString("EMAIL")); // 19
					vpd.put("var20", staffList.get(i).getString("SKIN")); // 20
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView(); // 执行excel操作
				mv = new ModelAndView(erv, dataMap);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 打开上传EXCEL页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}

	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath()
				+ Const.FILEPATHFILE + "Staffs.xls", "Staffs.xls");
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		HttpServletRequest request =this.getRequest();
		String IP = request.getRemoteAddr();
		NCUTLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库",IP);
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE; // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "userexcel"); // 执行上传
			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath,
					fileName, 2, 0, 0); // 执行读EXCEL操作,读出的数据导入List
										// 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/* 存入数据库操作====================================== */
			pd.put("RIGHTS", ""); // 权限
			pd.put("LAST_LOGIN", ""); // 最后登录时间
			pd.put("IP", ""); // IP
			pd.put("STATUS", "0"); // 状态
			pd.put("SKIN", "default"); // 默认皮肤
			pd.put("ROLE_ID", "1");
			List<Role> roleList = roleService.listAllRolesByPId(pd);// 列出所有系统用户角色
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID()); // 设置角色ID为随便第一个
			/**
			 * var0 :编号 var1 :姓名 var2 :手机 var3 :邮箱 var4 :备注
			 */
			for (int i = 0; i < listPd.size(); i++) {
				pd.put("STAFF_ID", this.get32UUID()); // ID
				pd.put("NAME", listPd.get(i).getString("var1")); // 姓名

				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString(
						"var1")); // 根据姓名汉字生成全拼
				pd.put("USERNAME", USERNAME);
				if (staffService.findByUsername(pd) != null) { // 判断用户名是否重复
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString(
							"var1"))
							+ Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				pd.put("BZ", listPd.get(i).getString("var4")); // 备注
				if (Tools.checkEmail(listPd.get(i).getString("var3"))) { // 邮箱格式不对就跳过
					pd.put("EMAIL", listPd.get(i).getString("var3"));
					if (staffService.findByUE(pd) != null) { // 邮箱已存在就跳过
						continue;
					}
				} else {
					continue;
				}
				pd.put("NUMBER", listPd.get(i).getString("var0")); // 编号已存在就跳过
				pd.put("PHONE", listPd.get(i).getString("var2")); // 手机号

				pd.put("PASSWORD",
						new SimpleHash("SHA-1", USERNAME, "123").toString()); // 默认密码123
				if (staffService.findByUN(pd) != null) {
					continue;
				}
				// 方法改了，先注释掉 staffService.saveU(pd);
			}
			/* 存入数据库操作====================================== */
			mv.addObject("msg", "success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 显示用户列表(弹窗选择用)
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listStaffsForWindow")
	public ModelAndView listStaffsForWindow(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart"); // 开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd"); // 结束时间
		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			pd.put("lastLoginEnd", lastLoginEnd + " 00:00:00");
		}
		page.setPd(pd);
		List<PageData> staffList = staffService.listStaffsBystaff(page); // 列出用户列表(弹窗选择用)
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);// 列出所有系统用户角色
		mv.setViewName("system/staff/window_staff_list");
		mv.addObject("staffList", staffList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,
				true));
	}

}
