package cn.ncut.controller.user.usercategory;

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
import cn.ncut.service.user.usercategory.UsercategoryManager;

/** 
 * 说明：会员类型
 * 创建人：FH Q313596790
 * 创建时间：2016-12-21
 */
@Controller
@RequestMapping(value="/usercategory")
public class UsercategoryController extends BaseController {
	
	String menuUrl = "usercategory/list.do"; //菜单地址(权限用)
	@Resource(name="usercategoryService")
	private UsercategoryManager usercategoryService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Usercategory");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		usercategoryService.save(pd);//新增用户组
		if(!"0".equals(pd.getString("PARENT_ID"))){
			//修改父的优惠比例为1.0
			PageData father = new PageData();
			father.put("USERCATEGORY_ID", pd.get("PARENT_ID"));//得到父
			father = usercategoryService.findById(father);
			father.put("PROPORTION", 1.0);
			usercategoryService.edit(father);
		}
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Usercategory");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		usercategoryService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Usercategory");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		usercategoryService.edit(pd);//修改子比例
		if(!"0".equals(pd.getString("PARENT_ID"))){
			//修改父的优惠比例为1.0
			PageData father = new PageData();
			father.put("USERCATEGORY_ID", pd.get("PARENT_ID"));//得到父
			father = usercategoryService.findById(father);
			father.put("PROPORTION", 1.0);
			usercategoryService.edit(father);
		}
		
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Usercategory");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = usercategoryService.list(page);	//列出Usercategory列表
		mv.setViewName("user/usercategory/usercategory_list");
		mv.addObject("varList", varList);
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
		List<PageData> categoryList = usercategoryService.listAllParentCategory(pd);
		mv.setViewName("user/usercategory/usercategory_edit");
		mv.addObject("categoryList",categoryList);
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
		pd = usercategoryService.findById(pd);	//根据ID读取
		List<PageData> categoryList = usercategoryService.listAllParentCategory(pd);
		
		mv.setViewName("user/usercategory/usercategory_edit");
		mv.addObject("categoryList",categoryList);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	
	 /**根据parent_id查子用户组
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/queryChildByParentID")
		public void queryChildByParentID(HttpServletRequest request, HttpServletResponse response)throws Exception{
			request.setCharacterEncoding("UTF-8");

			response.setContentType("text/html;charset=utf-8");//解决乱码 
			String json=""; 
			PageData pd = new PageData();
			pd = this.getPageData();
			 try{
					String PARENT_ID =  request.getParameter("PARENT_ID");
					
					pd.put("PARENT_ID", PARENT_ID);
		            List<Map<String,Object>> msgList=new ArrayList<Map<String,Object>>();  
		            List<PageData> categoryList = usercategoryService.listChildCategoryByParent(pd);
		            //json = JSONArray.fromObject(servicecostlist).toString();
		            if(categoryList.size()>0){  
		                for (int i = 0; i < categoryList.size(); i++) {  
		                	PageData pd2 =categoryList.get(i);  
		                    Map<String,Object> msgMap=new HashMap<String, Object>();  
		                    msgMap.put("USERCATEGORY_ID",  pd2.get("USERCATEGORY_ID"));  
		                    msgMap.put("CATEGORY_NAME",  pd2.get("CATEGORY_NAME"));  
		                    msgList.add(msgMap);  
		                }  
		                if(msgList.size()>0){  
		                	// json = JSONArray.fromObject(msgList).toString(); 
		                    //msg=JsonUtil.beanToJson(msgList);  
		                	StringBuilder sb = new StringBuilder();
		                	sb.append("[");
		                   // sb.append("{");
		               for(Map<String,Object> map:msgList){
		            	   sb.append("{");
		                    for (String key : map.keySet()) {
		                    	
		                        sb.append("\"").append(key).append("\":\"").append(map.get(key));
		                        sb.append("\"").append(",") ;  
		                        
		                        
		                    }
		                    sb.deleteCharAt(sb.length() - 1);
		                   // sb.append("\"").append(",");
		                    sb.append("}");
		                    sb.append(",");
		            }
		                    sb.deleteCharAt(sb.lastIndexOf(","));
		                    //sb.append("}");
		                    sb.append("]");
		                    json = sb.toString();
		                }  
		           
		                }
		            
				}
		                catch (Exception e) {  
		            e.printStackTrace();  
		        } 
				System.out.println(json);
//				//json = "[{\"a\":\"1\"}]";
				response.getWriter().write(json);
				
		}	
		
		
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Usercategory");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			usercategoryService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Usercategory到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("类型名称");	//1
		titles.add("优惠比例");	//2
		titles.add("状态 0正常 1过期");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = usercategoryService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("CATEGORY_NAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("PROPORTION"));	    //2
			vpd.put("var3", varOList.get(i).get("STATUS").toString());	//3
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
