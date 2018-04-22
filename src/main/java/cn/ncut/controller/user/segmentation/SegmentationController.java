package cn.ncut.controller.user.segmentation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.service.finance.prestore.impl.PreStoreService;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.user.customstored.impl.CustomStoredService;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.service.user.usercategory.impl.UsercategoryService;
import cn.ncut.service.user.usergroup.UsergroupManager;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.PageData;

@Controller
@RequestMapping(value = "/segmentation")
	public class SegmentationController extends BaseController {

		String menuUrl = "segmentation/list.do"; // 菜单地址(权限用)
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
		@Resource(name = "recommendService")
		private RecommendManager recommendService;
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
		 * 列表
		 * 
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value = "/list")
		public ModelAndView list(Page page) throws Exception {
			logBefore(logger, Jurisdiction.getUsername() + "列表Segmentation");
			// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
			// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			page.setPd(pd);
			List<PageData> varList = recommendService.listsegmentation(page);
			
			mv.setViewName("user/segmentation/segmentation_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			return mv;
		}
		/**
		 * 结果查询,后面要加载饼图
		 * 
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value = "/showResult")
		public ModelAndView showResult(Page page,HttpServletResponse response) throws Exception {
			logBefore(logger, Jurisdiction.getUsername() + "列表Segmentation结果");
			// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
			// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			page.setPd(pd);
			List<PageData> varList = recommendService.listsegmentationresult(page);
			List<PageData> weightList = recommendService.listSegmentationWeight(page);
			int[] count = new int[5];
			int i =0;
			for(PageData var:varList){
				count[i] = (int)var.get("number");
				i++;
			}

	        JSONArray jsonArray = JSONArray.fromObject(count);  

	        String s = jsonArray.toString();  
            System.out.println(s);
	 
			response.setContentType("text/xml;charset=UTF-8");
			response.getWriter().write(s);
			
			mv.setViewName("user/segmentation/segmentation_show");
			mv.addObject("varList", varList);
			mv.addObject("weightList", weightList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			return mv;
		}
		/**
		 * 结果查询,后面要加载饼图
		 * 
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value = "/showResultChart")
		public void showResultChart(Page page,HttpServletResponse response) throws Exception {
			logBefore(logger, Jurisdiction.getUsername() + "列表Segmentation结果");
			// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
			// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			
			PageData pd = new PageData();
			pd = this.getPageData();
			
			page.setPd(pd);
			List<PageData> varList = recommendService.listsegmentationresult(page);
			List<PageData> resultList = new ArrayList<PageData>();
			
			
			
			for(PageData var:varList){
				PageData resultpd = new PageData();
				if((int)var.get("group_id")==1){
				resultpd.put("name", "第一组");
				}
				if((int)var.get("group_id")==2){
					resultpd.put("name", "第二组");
					}
				if((int)var.get("group_id")==3){
					resultpd.put("name", "第三组");
					}
				if((int)var.get("group_id")==4){
					resultpd.put("name", "第四组");
					}
				if((int)var.get("group_id")==5){
					resultpd.put("name", "第五组");
					}
				resultpd.put("value", var.get("number"));
				resultList.add(resultpd);
			}

			String s = new ObjectMapper().writeValueAsString(resultList);
            System.out.println(s);
	 
			response.setContentType("text/xml;charset=UTF-8");
			response.getWriter().write(s);
			
			
		}
		/**
		 * 去更新页面
		 * 
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value = "/goUpdateResult")
		public ModelAndView goUpdateResult(Page page) throws Exception {
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			//根据segmentation_result_id查询结果
			
			pd=  recommendService.selectSegmentationResultById(pd);
			mv.setViewName("user/segmentation/segmentation_show_edit");
			mv.addObject("pd", pd);
			return mv;
		}
		/**
		 * 修改
		 * 
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value = "/updateResult")
		public ModelAndView updateResult() throws Exception {
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			recommendService.updateSegmentationRemarkById(pd);
			mv.addObject("msg", "success");
			mv.setViewName("save_result"); 
			
			
			return mv;
		}
}
