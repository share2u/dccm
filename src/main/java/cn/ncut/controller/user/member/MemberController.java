package cn.ncut.controller.user.member;

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
import cn.ncut.util.AppUtil;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.Tools;
import cn.ncut.service.finance.prestore.impl.PreStoreService;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.user.customstored.impl.CustomStoredService;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.service.user.usercategory.impl.UsercategoryService;
import cn.ncut.service.user.usergroup.UsergroupManager;

/**
 * 
 * 
 * <p>
 * Title:MemberController
 * </p>
 * <p>
 * Description:会员管理
 * </p>
 * <p>
 * Company:ncut
 * </p>
 * 
 * @author 
 * @date 2017-1-4下午6:25:14
 * 
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController {

	String menuUrl = "member/list.do"; // 菜单地址(权限用)
	@Resource(name = "memberService")
	private MemberManager memberService;
	@Resource(name = "usergroupService")
	private UsergroupManager usergroupService;
	@Resource(name = "usercategoryService")
	private UsercategoryService usercategoryService;
	@Resource(name = "prestoreService")
	private PreStoreService prestoreService;
	@Resource(name = "customstoredService")
	private CustomStoredService customstoredService;
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
		logBefore(logger, Jurisdiction.getUsername() + "新增Member");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("UID", this.get32UUID()); //主键
		
		pd.put("SOURCE", 1);
		memberService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "删除Member");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		memberService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername() + "修改Member");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String iiiii = pd.getString("USERCATEGORY_ID");
		if("".equals(pd.get("FIRST_DATE"))){
		pd.put("FIRST_DATE",null);}
		if(pd.get("USERCATEGORY_ID2")!=null&&!"".equals(pd.getString("USERCATEGORY_ID2"))){
			pd.put("USERCATEGORY_ID", pd.get("USERCATEGORY_ID2"));
		}//子
		//查询原来的用户类别
		PageData oldpd = memberService.findById(pd);
		oldpd.put("USERCATEGORY_ID", oldpd.get("usercategory_id"));
		PageData oldusercategory = usercategoryService.findById(oldpd);
		
		memberService.edit(pd);//更新用户表
		
		//判断是否修改了用户类别
		int biaodan = Integer.parseInt(pd.getString("USERCATEGORY_ID"));
		
		if((Integer)oldpd.get("usercategory_id")!=biaodan){
			NCUTLOG.save(
					Jurisdiction.getUsername(),
					"用户：" + (pd.getString("NAME")!=null?pd.getString("NAME"):pd.getString("USERNAME")) + "，手机号："
							+ pd.getString("PHONE") + "被修改了用户类别，原类别："
							+ oldusercategory.getString("CATEGORY_NAME")+ ", 现类别："+ usercategoryService.findById(pd).getString("CATEGORY_NAME"), this.getRequest()
							.getRemoteAddr());
			}
		
		prestoreService.updateMember(pd);
		customstoredService.updateMember(pd);
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
		String lastStart = pd.getString("lastStart");//获得开始时间
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", lastStart+" 00:00:01");
		}
		String lastEnd = pd.getString("lastEnd");//获得结束时间
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", lastEnd+" 23:59:59");
		}
		page.setPd(pd);
		List<PageData> varList = memberService.listmenberandgroup(page); // 列出Member列表
		mv.setViewName("user/member/member_list");
		mv.addObject("varList", varList);
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
		List<PageData> categoryList = usercategoryService
				.listAllParentCategory(pd);

		mv.setViewName("user/member/member_add");
		mv.addObject("categoryList", categoryList);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 根据parent_id查用户的折扣
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/findProportion")
	public void queryChildByParentID(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		response.setContentType("text/html;charset=utf-8");// 解决乱码
		String json = "";
		PageData pd = new PageData();
		Map<String, Double> pp = new HashMap<String, Double>();
		pd = this.getPageData();
		try {
			String PARENT_ID = request.getParameter("PARENT_ID");

			pd.put("USERCATEGORY_ID", PARENT_ID);

			Double proportion = usercategoryService.findProportion(pd);
			if (proportion != null) {
				pp.put("proportion", proportion);

			}

			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append("{");

			for (String key : pp.keySet()) {

				sb.append("\"").append(key).append("\":\"").append(pp.get(key));
				sb.append("\"");
			}
			sb.append("}");
			sb.append("]");

			json = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(json);
		response.getWriter().write(json);

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
		pd = memberService.findMemberAndGroupById(pd); // 根据ID读取
		List<PageData> categoryList = usercategoryService
				.listAllParentCategory(pd);
		mv.setViewName("user/member/member_edit");

		mv.addObject("categoryList", categoryList);
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Member");
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
			memberService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出Member到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("openid"); // 1
		titles.add("姓名"); // 2
		titles.add("用户名"); // 3
		titles.add("用户头像"); // 4
		titles.add("性别"); // 5
		titles.add("年龄"); // 6
		titles.add("电话号码"); // 7
		titles.add("身份证号"); // 8
		titles.add("所属城市"); // 9
		titles.add("初诊日期"); // 10
		titles.add("会员类型"); // 11
		titles.add("团购组"); // 12
		dataMap.put("titles", titles);
		List<PageData> varOList = memberService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("OPENID")); // 1
			vpd.put("var2", varOList.get(i).getString("NAME")); // 2
			vpd.put("var3", varOList.get(i).getString("USERNAME")); // 3
			vpd.put("var4", varOList.get(i).getString("USERIMG")); // 4
			vpd.put("var5", varOList.get(i).get("SEX").toString()); // 5
			vpd.put("var6", varOList.get(i).get("AGE").toString()); // 6
			vpd.put("var7", varOList.get(i).getString("PHONE")); // 7
			vpd.put("var8", varOList.get(i).getString("IDCODE")); // 8
			vpd.put("var9", varOList.get(i).getString("CITY")); // 9
			vpd.put("var10", varOList.get(i).getString("FIRST_DATE")); // 10
			vpd.put("var11", varOList.get(i).getString("USER_CATEGORY")); // 11
			vpd.put("var12", varOList.get(i).getString("GROUP_ID")); // 12
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

	/**
	 * 
	 * <p>
	 * Tittle:findCityByPhone
	 * </p>
	 * <p>
	 * Description:通过手机号查询所属城市，手机号唯一进行唯一性判断
	 * </p>
	 * 
	 * @param STORE_ID
	 * @return
	 * @throws Exception
	 * @author lph
	 * @date 2017-1-7下午2:58:17
	 */
	@RequestMapping(value = "/findCityByPhone", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object findCityByPhone(String PHONE) throws Exception {
		
		boolean isExist = memberService.isPhoneNumExist(PHONE);
		System.out.println(isExist);
		JSONArray jsonArr = JSONArray.fromObject("[{mobile_area:\"未知城市\"},{isExist:\""+isExist+"\"}]");
		if (PHONE.split("-")[0].length() > 6&&PHONE.startsWith("1")) {
			PHONE = PHONE.substring(0, 7);
			String mobile_area = (String) memberService.findCityByPhone(PHONE);
			if (mobile_area != null) {
				jsonArr = JSONArray.fromObject("[{mobile_area:" + "\""
						+ mobile_area + "\"},{isExist:\""+isExist+"\"}]");
			}
		}else{
			PHONE = PHONE.split("-")[0];
			String mobile_area = (String) memberService.findCityByAreaCode(PHONE);
			if (mobile_area != null) {
				jsonArr = JSONArray.fromObject("[{mobile_area:" + "\""
						+ mobile_area + "\"},{isExist:\""+isExist+"\"}]");
			}
		}

		System.out.println(jsonArr);
		return jsonArr;
	}
}
