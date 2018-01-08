package cn.ncut.controller.system.servicetime;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import oracle.net.aso.a;
import oracle.net.aso.d;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.wall.WallConfig.TenantCallBack;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Staff;
import cn.ncut.util.AppUtil;
import cn.ncut.util.Const;
import cn.ncut.util.DateUtil;
import cn.ncut.util.NumberWordFormat;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.Tools;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.servicetime.ServiceTimeManager;
import cn.ncut.service.system.staff.StaffManager;
import cn.ncut.service.system.store.StoreManager;

/**
 * 
 * 
 * <p>
 * Title:ServiceTimeController
 * </p>
 * <p>
 * Description:医生排班
 * </p>
 * <p>
 * Company:ncut
 * </p>
 * 
 * @author lph
 * @date 2016-12-18下午3:56:31
 * 
 */
@Controller
@RequestMapping(value = "/servicetime")
public class ServiceTimeController extends BaseController {

	String menuUrl = "servicetime/list.do"; // 菜单地址(权限用)
	@Resource(name = "servicetimeService")
	private ServiceTimeManager servicetimeService;

	@Resource(name = "storeService")
	private StoreManager storeService;

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
		logBefore(logger, Jurisdiction.getUsername() + "新增ServiceTime");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("WEEK_OF_YEAR",
				DateUtil.getWeekOfYear((String) pd.getString("THE_DATE")));
		pd.put("WEEKANDYEAR_OF_TIME", DateUtil.getYearAndWeekOfYear((String) pd
				.getString("THE_DATE")));
		/*
		 * if(DateUtil.formerOrLatter(DateUtil.getEveryDay((String)pd.getString(
		 * "THE_DATE"), 1))){ mv.addObject("msg", "必须选择以后的日期!");
		 * mv.setViewName("save_result"); return mv; }
		 */

		int[][] arr = new int[24][7];
		int[] s = new int[168];
		String[] splits = ((String) pd.get("Mdzz")).split(",");
		pd.remove("Mdzz");
		// 字符串数组传送到整形数组中
		for (int i = 0; i < s.length; i++) {
			s[i] = Integer.parseInt(splits[i]);
		}
		// 一阶矩阵转二阶
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = s[7 * i + j];
			}
		}

		// 矩阵转置
		int[][] midArray = new int[7][24];
		for (int i = 0; i < midArray.length; i++) {
			for (int j = 0; j < midArray[i].length; j++) {
				midArray[i][j] = arr[j][i];
				System.out.print(midArray[i][j]);
			}
			System.out.println();
		}
		// 在插入前把该医生该周的所有记录删除
		servicetimeService.deleteByStaffAndWeek(pd);

		// 对每条数据进行分别插入
		for (int i = 0; i < midArray.length; i++) {

			// 通过日期获得选择的日期是周几的数字
			String week = new SimpleDateFormat("E").format(DateUtil
					.fomatDate((String) pd.get("THE_DATE")));
			int week_which = DateUtil
					.ChineseToNum(week.substring(week.length() - 1));
			pd.put("THE_DATE", DateUtil.getEveryDay(
					(String) pd.get("THE_DATE"), (i - week_which)));
			for (int j = 0; j < midArray[i].length; j++) {
				pd.put(NumberWordFormat.format(j), midArray[i][j]);
			}

			servicetimeService.addEveryDay(pd);
		}
		// 记录到日志
		String staffName = staffService.getStaffNameById((String) pd
				.getString("STAFF_ID"));
		NCUTLOG.save(Jurisdiction.getUsername(), "添加（修改）医生" + staffName + "在"
				+ pd.getString("WEEKANDYEAR_OF_TIME").substring(0, 4) + "年第"
				+ pd.getString("WEEKANDYEAR_OF_TIME").substring(5) + "周的排班",
				this.getRequest().getRemoteAddr());

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
		logBefore(logger, Jurisdiction.getUsername() + "删除ServiceTime");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		servicetimeService.delete(pd);
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
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改ServiceTime");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("WEEK_OF_YEAR",
				DateUtil.getWeekOfYear((String) pd.getString("THE_DATE")));
		pd.put("WEEKANDYEAR_OF_TIME", DateUtil.getYearAndWeekOfYear((String) pd
				.getString("THE_DATE")));

		int[][] arr = new int[24][7];
		int[] s = new int[168];
		String[] splits = ((String) pd.get("Mdzz")).split(",");
		pd.remove("Mdzz");
		// 字符串数组传送到整形数组中
		for (int i = 0; i < s.length; i++) {
			s[i] = Integer.parseInt(splits[i]);
		}
		// 一阶矩阵转二阶
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = s[7 * i + j];
			}
		}

		// 矩阵转置
		int[][] midArray = new int[7][24];
		for (int i = 0; i < midArray.length; i++) {
			for (int j = 0; j < midArray[i].length; j++) {
				midArray[i][j] = arr[j][i];
				System.out.print(midArray[i][j]);
			}
			System.out.println();
		}
		// 在插入前把该医生该周的所有记录删除
		servicetimeService.deleteByStaffAndWeek(pd);

		// 对每条数据进行分别插入
		for (int i = 0; i < midArray.length; i++) {

			// 通过日期获得选择的日期是周几的数字
			String week = new SimpleDateFormat("E").format(DateUtil
					.fomatDate((String) pd.get("THE_DATE")));
			int week_which = DateUtil
					.ChineseToNum(week.substring(week.length() - 1));
			pd.put("THE_DATE", DateUtil.getEveryDay(
					(String) pd.get("THE_DATE"), (i - week_which)));
			for (int j = 0; j < midArray[i].length; j++) {
				pd.put(NumberWordFormat.format(j), midArray[i][j]);
			}

			servicetimeService.addEveryDay(pd);
		}
		// 记录到日志
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				"添加（修改）医生"
						+ staffService.getStaffNameById((String) pd
								.getString("STAFF_ID")) + "在"
						+ pd.getString("WEEKANDYEAR_OF_TIME").substring(0, 4)
						+ "年第"
						+ pd.getString("WEEKANDYEAR_OF_TIME").substring(5)
						+ "周的排班", request.getRemoteAddr());
		// servicetimeService.edit(pd);
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
	public ModelAndView list(Page page, HttpSession session) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表ServiceTime");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());
		page.setPd(pd);

		List<PageData> varList = servicetimeService.list(page); // 列出ServiceTime列表
		mv.setViewName("system/servicetime/servicetime_list");
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

		pd.put("STORE_ID",
				((Staff) Jurisdiction.getSession().getAttribute(
						Const.SESSION_USER)).getSTORE_ID());
		List<PageData> storeList = storeService.listAll(pd);
		List<PageData> staffList = staffService.listAll(pd);
		mv.setViewName("system/servicetime/servicetime_edit");
		mv.addObject("msg", "save");
		List<Integer> ifCanOrder = new ArrayList<Integer>();
		int midArray[][] = new int[24][7];
		for (int i = 0; i < midArray.length; i++) {
			for (int j = 0; j < midArray[i].length; j++) {
				midArray[i][j] = 0;
				ifCanOrder.add(midArray[i][j]);
			}
		}
		mv.addObject("ifCanOrder", ifCanOrder);
		mv.addObject("staffList", staffList);
		mv.addObject("storeList", storeList);
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
		PageData ppdd = new PageData();
		ppdd = this.getPageData();
		ppdd = servicetimeService.findById(ppdd); // 根据ID读取
		ppdd.put("THE_DATE",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		pd.put("STAFF_ID", ppdd.get("STAFF_ID"));
		pd.put("WEEK_OF_YEAR", DateUtil.getWeekOfYear(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
		pd.put("WEEKANDYEAR_OF_TIME", DateUtil.getYearAndWeekOfYear(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
		List<PageData> pds = new ArrayList<>();
		pds = servicetimeService.findByStaffAndWeek(pd);
		
		
		
		int arr[][] = new int[7][24];

		for (int size = 0; size < pds.size(); size++) {

			PageData pdd = new PageData();
			Set<String> set = pds.get(size).keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String date = iterator.next();
				boolean flag = Arrays.asList(NumberWordFormat.INDNUM).contains(
						date);
				if (flag) {
					int drop = (int) pds.get(size).get(date);
					pdd.put(NumberWordFormat.parse(date), drop);
				}
			}
			// 通过日期获得数据库中周几减一的数字
			String week = new SimpleDateFormat("E").format(DateUtil
					.fomatDate((String) pds.get(size).get("THE_DATE")));
			int week_which = DateUtil
					.ChineseToNum(week.substring(week.length() - 1));
			// 将数据库中日期对应时间医生是否上班给取出来拼到字符串中
			for (int i = 0; i < 24; i++) {
				arr[week_which][i] = (int) pdd.get(i);
			}

		}
		List<Integer> ifCanOrder = new ArrayList<Integer>();
		int midArray[][] = new int[24][7];
		// 矩阵转置
		for (int i = 0; i < midArray.length; i++) {
			for (int j = 0; j < midArray[i].length; j++) {
				midArray[i][j] = arr[j][i];
				ifCanOrder.add(midArray[i][j]);
			}
		}

		List<PageData> storeList = storeService.listAll(ppdd);
		List<PageData> staffList = staffService.listAll(ppdd);
		mv.setViewName("system/servicetime/servicetime_edit");
		mv.addObject("ifCanOrder", ifCanOrder);
		mv.addObject("storeList", storeList);
		mv.addObject("staffList", staffList);
		mv.addObject("msg", "edit");
		mv.addObject("pd", ppdd);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Tittle:findStaffByStore
	 * </p>
	 * <p>
	 * Description:通过ajax中传递的Store_ID找到Staff_ID和Staff_Name并改成json格式返回
	 * </p>
	 * 
	 * @param STORE_ID
	 * @return
	 * @throws Exception
	 * @author lph
	 * @date 2016-12-18下午10:19:24
	 */
	@RequestMapping(value = "/findStaffByStore", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object findStaffByStore(String STORE_ID) throws Exception {
		List<PageData> staffNameList = servicetimeService
				.listStaffNameAndIdById(STORE_ID);
		JSONArray jsonArr = JSONArray.fromObject(staffNameList);
		return jsonArr;
	}

	@RequestMapping(value = "/findWeekServiceByDate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object findWeekServiceByDate(String THE_DATE, String STAFF_ID)
			throws Exception {

		String WEEKANDYEAR_OF_TIME = DateUtil.getYearAndWeekOfYear(THE_DATE);
		int WEEK_OF_YEAR = DateUtil.getWeekOfYear(THE_DATE);
		PageData pd = new PageData();
		pd.put("STAFF_ID", STAFF_ID);
		pd.put("WEEKANDYEAR_OF_TIME", WEEKANDYEAR_OF_TIME);
		pd.put("WEEK_OF_YEAR", WEEK_OF_YEAR);
		List<PageData> pds = servicetimeService
				.findByYearAndWeekOfYearStaff(pd);
		int arr[][] = new int[7][24];

		for (int size = 0; size < pds.size(); size++) {

			PageData pdd = new PageData();
			Set<String> set = pds.get(size).keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String date = iterator.next();
				boolean flag = Arrays.asList(NumberWordFormat.INDNUM).contains(
						date);
				if (flag) {
					int drop = (int) pds.get(size).get(date);
					pdd.put(NumberWordFormat.parse(date), drop);
				}
			}
			// 通过日期获得数据库中周几减一的数字
			String week = new SimpleDateFormat("E").format(DateUtil
					.fomatDate((String) pds.get(size).get("THE_DATE")));
			int week_which = DateUtil
					.ChineseToNum(week.substring(week.length() - 1));
			// 将数据库中日期对应时间医生是否上班给取出来拼到字符串中
			for (int i = 0; i < 24; i++) {
				arr[week_which][i] = (int) pdd.get(i);
			}

		}
		List<Integer> ifCanOrder = new ArrayList<Integer>();
		int midArray[][] = new int[24][7];
		// 矩阵转置
		for (int i = 0; i < midArray.length; i++) {
			for (int j = 0; j < midArray[i].length; j++) {
				midArray[i][j] = arr[j][i];
				ifCanOrder.add(midArray[i][j]);
			}
		}

		JSONArray jsonArr = JSONArray.fromObject(ifCanOrder);

		return jsonArr;
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ServiceTime");
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
			servicetimeService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername() + "导出ServiceTime到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("员工id"); // 1
		titles.add("门店id"); // 2
		titles.add("日期"); // 3
		titles.add("上班时间"); // 4
		titles.add("下班时间"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = servicetimeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STAFF_ID")); // 1
			vpd.put("var2", varOList.get(i).getString("STORE_ID")); // 2
			vpd.put("var3", varOList.get(i).getString("THE_DATE")); // 3
			vpd.put("var4", varOList.get(i).getString("TIME_START")); // 4
			vpd.put("var5", varOList.get(i).getString("TIME_END")); // 5
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
