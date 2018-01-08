package cn.ncut.controller.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.wechat.pojo.WeChatStore;
import cn.ncut.service.wechat.store.WeChatStoreManager;
import cn.ncut.service.wechat.store.impl.WeChatStoreService;
import cn.ncut.service.wechat.user.impl.WeChatUserService;
import cn.ncut.util.PageData;
import cn.ncut.util.wechat.WeChatLogUtil;

@Controller
@RequestMapping("/weChatStore")
public class WeChatStoreController extends BaseController {
	final static Logger logger = LoggerFactory
			.getLogger(WeChatStoreController.class);

	@Resource(name = "weChatStoreService")
	private WeChatStoreManager weChatStoreService;
	@Resource(name = "weChatUserService")
	private WeChatUserService weChatUserService;
	/**
	 * 根据板块id跳转门店列表页面
	 * 
	 * @param mId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getStoresByModuleId/{mId}")
	public ModelAndView getStoresByModulesId(@PathVariable("mId") String mId) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WeChatLogUtil.controllerLog(request, "getStoresByModuleId");
		
		ModelAndView mv = getModelAndView();
		List<WeChatStore> stores;
		try {
			stores = weChatStoreService.getStoresBymId(mId);
			mv.addObject("stores", stores);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("wechat/store/storeList");
		return mv;
	}

	@RequestMapping("/getStoreDetailByStoreId/{storedId}")
	public ModelAndView getStoreDetailByStoreId(
			@PathVariable("storedId") String storeId) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WeChatLogUtil.controllerLog(request, "getStoreDetailByStoreId");
		
		ModelAndView mv = getModelAndView();
		try {
			WeChatStore store = weChatStoreService.getStoreDetailByStoreId(storeId);
			List<PageData> docters = weChatStoreService
					.getStoreDoctersByStoreId(storeId);
			List<PageData> comments = weChatStoreService
					.getCommentsByStoreId(storeId);

			// 门店的信息
			mv.addObject("store", store);
			// 门店医生或讲师
			mv.addObject("storeType",storeId.substring(0, 2));
			mv.addObject("docters", docters);
			// 门店的评价
			mv.addObject("comments", comments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("wechat/store/storeDetail");
		return mv;
	}

	@RequestMapping("/getDoctorDetailById/{staffId}")
	public ModelAndView getDoctorDetailById(@PathVariable("staffId")String staffId)  {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WeChatLogUtil.controllerLog(request, "getDoctorDetailById");
		
		ModelAndView mv = getModelAndView();
		PageData doctor;
		try {
			doctor = weChatStoreService.getDoctorDetailById(staffId);
		
		PageData pd = new PageData();
		String storeType = doctor.getString("STORE_ID").substring(0,2);
		pd.put("staffId", staffId);
		pd.put("storeIdSub2", storeType);
		logger.debug(staffId+",,"+storeType);
		List<PageData> serviceProjects = weChatStoreService.getServiceProjectsById(pd);
		// 门店医生或讲师的简要介绍
		mv.addObject("doctor", doctor);
		//门店项目的介绍
		mv.addObject("serviceProjects", serviceProjects);
		mv.addObject("storeType",storeType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("wechat/store/doctorDetail");
		return mv;
	}
	@RequestMapping("/getStoresBySome")
	@ResponseBody
	public Object getStoresBySome(HttpServletRequest request){
		WeChatLogUtil.controllerLog(request, "getStoresBySome");
		
		String queryParam = request.getParameter("queryParam");
		List<WeChatStore> stores;
		try {
			stores = weChatStoreService
					.getStoresBySome(queryParam);
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		logger.debug("筛选门店" + stores.toString());
		return stores;
	}

	@RequestMapping("/sendTelToStore")
	@ResponseBody
	public Object sendTelToStore(HttpServletRequest request) {
		WeChatLogUtil.controllerLog(request, "sendTelToStore");
		
		String staffId = request.getParameter("docId");
		String storeId = request.getParameter("storeId");
		String phone = request.getParameter("tel");
		String name = request.getParameter("name");
		Integer uId = (Integer) request.getSession().getAttribute("uId");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		PageData pageData = new PageData();
		pageData.put("uId", uId);
		pageData.put("storeId", storeId);
		pageData.put("staffId", staffId);
		pageData.put("phone", phone);
		pageData.put("name", name);
		pageData.put("status", 0);
		pageData.put("createTime", createTime);
		try {
			weChatStoreService.sendTelToStore(pageData);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"key\":\"fail\"}";
		}
		logger.debug("插入咨询单记录成功" + pageData.toString());
		return "{\"key\":\"success\"}";
	}

	@RequestMapping("/world")
	public String test() {
		return "wechat/success";
	}

}
