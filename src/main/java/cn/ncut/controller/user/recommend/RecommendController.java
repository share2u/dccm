package cn.ncut.controller.user.recommend;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.service.finance.prestore.impl.PreStoreService;
import cn.ncut.service.finance.serviceall.ServiceCostManager;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.user.customstored.impl.CustomStoredService;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.service.user.usercategory.impl.UsercategoryService;
import cn.ncut.service.user.usergroup.UsergroupManager;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.PageData;

@Controller
@RequestMapping(value = "/recommend")
	public class RecommendController extends BaseController {

		String menuUrl = "recommend/list.do"; // 菜单地址(权限用)
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
		@Resource(name = "servicecostService")
		private ServiceCostManager servicecostService;
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
		 * 个性化分析结果页面
		 * 
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value = "/showUser")
		public ModelAndView showUser(Page page) throws Exception {
			logBefore(logger, Jurisdiction.getUsername() + "展示用户");
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			if (null != keywords && !"".equals(keywords)) {
				pd.put("keywords", keywords.trim());
			}
			
			page.setPd(pd);
			//List<PageData> userList = memberService.listCompleteMemberlistPage(page);
			// 列出Member列表
			
			List<PageData> userList = memberService.listMemberByUidlistPage(page); 
			mv.setViewName("user/recommend/recommend_analysis");
			mv.addObject("userList", userList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			return mv;
		}
		/**
		 * 算法对比页面
		 * 
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value = "/showContrast")
		public ModelAndView showContrast(Page page) throws Exception {
			logBefore(logger, Jurisdiction.getUsername() + "展示用户");
			
			ModelAndView mv = this.getModelAndView();
			
			
			mv.setViewName("user/recommend/contrast");
			
			return mv;
		}
		/**
		 * 个性化分析结果页面
		 * 
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value = "/refreshProjectAndRecommend")
		public void refreshProjectAndRecommend(HttpServletResponse response) throws Exception {
			 String servicecost_ids= "";
				PageData pd = new PageData();
				pd = this.getPageData();
	          int iscoll = 0;
	          pd.put("uid", pd.get("UID"));
	          int uid = Integer.valueOf(pd.getString("UID"));
				//查询其是否有协同过滤的推荐项目
	           iscoll = servicecostService.selectIscollByUid(uid);
				if(iscoll==0){
					//查询他的协同过滤推荐
					servicecost_ids = servicecostService.selecttop10(pd);
				}else if(iscoll==1){
					servicecost_ids = servicecostService.selectservicecost_ids(pd);
				}
				List<PageData> array[] = new ArrayList[2]; 
				List<PageData> servicepdlist = new ArrayList<PageData>();
				String [] stringArr= servicecost_ids.split(","); //推荐的数组
				for(int i =0;i<stringArr.length;i++){
					PageData servicecostpd = new PageData();
					if(!stringArr[i].endsWith("0")){
					servicecostpd.put("SERVICECOST_ID", stringArr[i]);
					
					PageData projectpd = servicecostService.selectPnameAndStaffName(servicecostpd);
					servicepdlist.add(projectpd);
					}
					
				}
				array[0] = servicepdlist;
				//查询该用户之前购买过什么什么医生的项目
				List<PageData> projectList = recommendService.selectServicecostByUidGroupByStaffid(pd);
				array[1] = projectList;
	            String s = new ObjectMapper().writeValueAsString(array);
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
