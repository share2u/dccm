package cn.ncut.controller.system.store;

import java.io.File;
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
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.City;
import cn.ncut.entity.system.Staff;
import cn.ncut.entity.system.Store;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.servicemodule.ServicemoduleManager;
import cn.ncut.service.system.staff.impl.StaffService;
import cn.ncut.service.system.store.StoreManager;
import cn.ncut.util.AppUtil;
import cn.ncut.util.ChineseToEnglishUtils;
import cn.ncut.util.Const;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.ObjectExcelView;
import cn.ncut.util.PageData;
import cn.ncut.util.SequenceUtils;

import com.google.gson.Gson;


/**
 * 
*
*<p>Title:StoreController</p>
*<p>Description:门店管理</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-9下午8:30:28
*
 */
@Controller
@RequestMapping(value="/store")
public class StoreController extends BaseController {
	
	String menuUrl = "store/list.do"; //菜单地址(权限用)
	@Resource(name="storeService")
	private StoreManager storeService;
	
	@Resource(name="sequenceUtils")
	private SequenceUtils sequenceUtils;
	
	@Resource(name="servicemoduleService")
	private ServicemoduleManager servicemoduleService;
	
	@Resource(name="staffService")
	private StaffService staffService;
	
	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request, @ModelAttribute Store store) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Store");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		
		//设置城市
		String cityid = storeService.queryCityIDByName(store.getCITY_NAME());
		store.setCITY_ID(cityid);
		//设置主键
		store.setSTORE_ID(store.getM_ID() + sequenceUtils.getNextId(store.getM_ID()));	//主键
			
		List<MultipartFile> storeimages = store.getUploadimages();
		
		if(null != storeimages && storeimages.size()>0){
			
			for(int i=0; i<storeimages.size(); i++){
				
				MultipartFile mf = storeimages.get(i);
				
				if(mf.getSize()!=0){
					//原始名字
					String imageName = mf.getOriginalFilename();
					
					//重新命名
					String imagename = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + "_" +ChineseToEnglishUtils.getUpEname(imageName);
					
					//上传到服务器
					File file = new File(request.getSession().getServletContext().getRealPath("uploadFiles/storeimg"),imagename);
					
					if(i==0){
						store.setSTORE_SMALL_IMG(imagename);
					}else{
						store.setSTORE_BIG_IMG(imagename);
					}
					
					mf.transferTo(file);
				}
				
			}
		}
		
		
		
		storeService.save(store);
		
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				"新增门店:" + store.getSTORE_NAME(), this.getRequest()
						.getRemoteAddr());
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/*@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Store");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String cityid = storeService.queryCityIDByName((String)pd.get("cityname"));
		pd.put("CITY_ID", cityid);
		pd.put("STORE_SMALL_IMG", pd.get("FILEPATH"));
		pd.put("STORE_BIG_IMG", pd.get("FILEPATH"));
		pd.put("STORE_ID", "02"+sequenceUtils.getNextId("02"));	//主键
		storeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}*/
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Store");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		storeService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest request, @ModelAttribute Store store) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Store");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
	
		//设置城市
		String cityid = storeService.queryCityIDByName(store.getCITY_NAME());
		store.setCITY_ID(cityid);
		
		//上传图片
		List<MultipartFile> storeimages = store.getUploadimages();
		
		if(null != storeimages && storeimages.size()>0){
			
			for(int i=0; i<storeimages.size(); i++){
				
				MultipartFile mf = storeimages.get(i);
				
				//原始名字
				String imageName = mf.getOriginalFilename();
				
				if(null != imageName &&  !"".equals(imageName)){
				
					//重新命名
					String imagename = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + "_" +ChineseToEnglishUtils.getUpEname(imageName);
				
					//上传到服务器
					File file = new File(request.getSession().getServletContext().getRealPath("uploadFiles/storeimg"),imagename);
				
					if(i==0){
						store.setSTORE_SMALL_IMG(imagename);
					}else{
						store.setSTORE_BIG_IMG(imagename);
					}
				
					mf.transferTo(file);
				}
				
			}
		}
		
		storeService.edit(store);
		
		NCUTLOG.save(
				Jurisdiction.getUsername(),
				"修改门店:" + store.getSTORE_NAME(), this.getRequest()
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
	public ModelAndView list(Page page, HttpSession session) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Store");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		Staff sessionstaff = (Staff) session.getAttribute(Const.SESSION_USER);
		pd.put("STORE_ID", sessionstaff.getSTORE_ID());
		page.setPd(pd);
		List<PageData>	varList = storeService.list(page);	//列出Store列表
		
		//改变store列表中的负责人字段
		List<PageData>	storelist = new ArrayList<PageData>();
		
		for(PageData ppdd : varList){
			PageData module = servicemoduleService.queryModuleById((String)ppdd.get("M_ID"));
			PageData module2 = storeService.queryNameById((String)ppdd.get("CITY_ID"));
			if(module != null && module2 != null){
				ppdd.put("M_ID", module.get("M_NAME"));
			}
			if(null != module2){
				ppdd.put("CITY_ID", module2.get("CITY_NAME"));
			}
			PageData staff = staffService.queryById((String) ppdd.get("STAFF_ID"));
			if(staff != null){
				ppdd.put("leader", staff.get("STAFF_NAME"));
			}
			storelist.add(ppdd);
		}
		mv.setViewName("system/store/store_list");
		mv.addObject("varList", storelist);
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
		//查询板块信息存在pd里
		List<PageData> modulelist = servicemoduleService.listAll(pd);
		mv.setViewName("system/store/store_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("modulelist", modulelist);
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
		pd = storeService.findById(pd);	//根据ID读取
		//pd.put("CITY_NAME", storeService.queryNameById((String)pd.get("CITY_ID")).get("CITY_NAME"));
		//获取板块列表
		List<PageData> modulelist = servicemoduleService.listAll(pd);
		mv.addObject("modulelist", modulelist);
		mv.setViewName("system/store/store_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	 /**查城市名
		 * @param request
		 * @throws Exception
		 */
	@RequestMapping(value="/querycityname")
	public void querycityname(HttpServletRequest request, HttpServletResponse response)throws Exception{
		request.setCharacterEncoding("UTF-8");

		response.setContentType("text/html;charset=utf-8");//解决乱码 
		String tt =  request.getParameter("term");
		try {  
            List<City> citys = storeService.findCityByName(tt); 
            if(citys.size()>0){
            	Gson gson = new Gson();  
            	String str = gson.toJson(citys);  
            	response.getWriter().write(str);  
            }else{
            	response.getWriter().write("{\"error\":\"该城市不存在\"}");
            }
	    } catch (Exception e) {  
	          e.printStackTrace();  

	        }  
	    }  
		
	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Store");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			storeService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Store到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("名店名称");	//1
		titles.add("地址");	//2
		titles.add("交通信息");	//3
		titles.add("工作时间");	//4
		titles.add("联系电话");	//5
		titles.add("服务板块");	//6
		titles.add("负责人");	//7
		titles.add("门店小图");	//8
		titles.add("门店大图");	//9
		titles.add("状态");	//10
		titles.add("经营模式");	//11
		titles.add("所属城市");	//12
		titles.add("是否有资质");	//13
		titles.add("门店编号");	//14
		dataMap.put("titles", titles);
		List<PageData> varOList = storeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STORE_NAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("ADDRESS"));	    //2
			vpd.put("var3", varOList.get(i).getString("TRAFFIC_MESSAGE"));	    //3
			vpd.put("var4", varOList.get(i).getString("WORKTIME"));	    //4
			vpd.put("var5", varOList.get(i).getString("TELEPHONE"));	    //5
			vpd.put("var6", varOList.get(i).getString("M_ID"));	    //6
			vpd.put("var7", varOList.get(i).getString("STAFF_ID"));	    //7
			vpd.put("var8", varOList.get(i).getString("STORE_SMALL_IMG"));	    //8
			vpd.put("var9", varOList.get(i).getString("STORE_BIG_IMG"));	    //9
			vpd.put("var10", varOList.get(i).get("STATUS").toString());	//10
			vpd.put("var11", varOList.get(i).get("BUSSINPATTERN").toString());	//11
			vpd.put("var12", varOList.get(i).get("CITY_ID").toString());	//12
			vpd.put("var13", varOList.get(i).get("IS_QUALIFICATION").toString());	//13
			vpd.put("var14", varOList.get(i).get("STORE_NUMBER").toString());	//14
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
