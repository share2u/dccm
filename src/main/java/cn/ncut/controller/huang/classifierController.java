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

@Controller
@RequestMapping(value = "/classifier")

public class classifierController extends BaseController {

	String menuUrl = "classifier/list.do"; // 菜单地址(权限用)
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
	
	ModelAndView mv = this.getModelAndView();
	PageData pd = new PageData();
	pd = this.getPageData();
	String keywords = pd.getString("keywords"); // 关键词检索条件
	if (null != keywords && !"".equals(keywords)) {
		pd.put("keywords", keywords.trim());
	}
	String label = pd.getString("label"); // 关键词检索条件
	if (null != label && !"".equals(label)) {
		pd.put("label", label.trim());
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
	List<PageData> varList = ClusterService.listLiushi(page); // 列出Member列表
	mv.setViewName("datamining/liushi_list");
	mv.addObject("varList", varList);
	mv.addObject("pd", pd);
	mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
	return mv;
 }

@RequestMapping(value="/listclassifier")
public ModelAndView listclassifier(Page page) throws Exception {
	
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
	List<PageData> classifier = ClusterService.listClassifier(page); 
	List<PageData> liushigroups = ClusterService.findLiushi(page);
	mv.setViewName("datamining/classifiersatis");
	mv.addObject("classifier", new ObjectMapper().writeValueAsString(classifier));
	mv.addObject("liushigroups", new ObjectMapper().writeValueAsString(liushigroups));
	return mv;
 }



}
