package cn.ncut.controller.huang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.service.datamining.ClusterManager;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.PageData;

/**
 * @客户细分
 */

@Controller
@RequestMapping(value = "/cluster")


public class clusterController extends BaseController {

	String menuUrl = "cluster/list.do"; // 菜单地址(权限用)
	@Resource(name = "memberService")
	private MemberManager memberService;

	@Resource(name = "ClusterService")
	private ClusterManager ClusterService;


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
	
	Date day=new Date();    
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	Calendar calendar = Calendar.getInstance();  
    calendar.setTime(day);  
    calendar.add(Calendar.DAY_OF_MONTH, -1);  
    day = calendar.getTime();   
    String sdate=(new SimpleDateFormat("yyyy-MM-dd")).format(day);
    pd.put("date", sdate);
	if (null != keywords && !"".equals(keywords)) {
		pd.put("keywords", keywords.trim());
	}
/*	String lastStart = pd.getString("lastStart");//获得开始时间
	if (null != lastStart && !"".equals(lastStart)) {
		pd.put("lastStart", lastStart+" 00:00:01");
	}
	String lastEnd = pd.getString("lastEnd");//获得结束时间
	if (null != lastEnd && !"".equals(lastEnd)) {
		pd.put("lastEnd", lastEnd+" 23:59:59");
	}*/
	page.setPd(pd);
	List<PageData> varList = ClusterService.list(page); 
	List<PageData> VIPgroups = ClusterService.findVIP(page);
	List<PageData> Allgroups = ClusterService.findAll(page);
	mv.setViewName("datamining/segment_list");
	mv.addObject("varList", varList);
	mv.addObject("pd", pd);
	mv.addObject("VIPgroups", new ObjectMapper().writeValueAsString(VIPgroups));
	mv.addObject("Allgroups", new ObjectMapper().writeValueAsString(Allgroups));
	mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
	return mv;
 }

/**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/golevel")
	public ModelAndView goEdit(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(day);  
	    calendar.add(Calendar.DAY_OF_MONTH, -1);  
	    day = calendar.getTime();  
	    String sdate=(new SimpleDateFormat("yyyy-MM-dd")).format(day);
	    pd.put("date", sdate);
	
		page.setPd(pd);
		pd = ClusterService.findById(page);	//根据聚类数目读取
		mv.setViewName("datamining/segment_remark");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去修改level
	 */
	@RequestMapping(value="/edit")
	public ModelAndView updatelevel() throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(day);  
	    calendar.add(Calendar.DAY_OF_MONTH, -1);  
	    day = calendar.getTime();  
	    String sdate=(new SimpleDateFormat("yyyy-MM-dd")).format(day);
	    pd.put("date", sdate);
	    
		ClusterService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
		
	
	}
	@RequestMapping(value="/goquery")
	public ModelAndView query(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}	
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(day);  
	    calendar.add(Calendar.DAY_OF_MONTH, -1);  
	    day = calendar.getTime();  
	    String sdate=(new SimpleDateFormat("yyyy-MM-dd")).format(day);
	    pd.put("date", sdate);
	    
		page.setPd(pd);
		List<PageData> varList = ClusterService.findByLabel(page);
		mv.setViewName("datamining/segment_edit");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	
	}
		
	@RequestMapping(value = "/listcluster")
	public ModelAndView listcluster(Page page) throws Exception {
		//logBefore(logger, Jurisdiction.getUsername() + "列表Member");
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
		List<PageData> cluster = ClusterService.listcluster(page); 
		//List<PageData> VIPgroups = ClusterService.findVIP(page);
		mv.setViewName("datamining/clusterstatic2");
		//mv.addObject("cluster", cluster);
		mv.addObject("cluster", new ObjectMapper().writeValueAsString(cluster));
		//mv.addObject("VIPgroups", new ObjectMapper().writeValueAsString(VIPgroups));
		return mv;
	 }
}
