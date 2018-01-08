package cn.ncut.controller.wechat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.wechat.pojo.WeChatPreStoreMx;
import cn.ncut.service.wechat.preStoreMx.impl.WeChatPreStoreMxService;

/**
 * 预存
 * */
@Controller
@RequestMapping("/weChatPreStoreMx")
public class WeChatPreStoreMxController extends BaseController {
	@Resource(name = "weChatPreStoreMxService")
	private WeChatPreStoreMxService weChatPreStoreMxService;
	/**
	 * 查看当前用户预存变动明细
	 * */
	@RequestMapping("/preStoreMxDetail")
	public ModelAndView preStoreDetails(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		Integer uId = (Integer)session.getAttribute("uId");
		
		// 查询预存明细信息
		List<WeChatPreStoreMx> weChatPreStoreMxs = this.weChatPreStoreMxService.getUserPreStoreMxByUserId(uId);
		
		weChatPreStoreMxs = this.process(weChatPreStoreMxs);
		
		ModelAndView mav = this.getModelAndView();
		mav.addObject("weChatPreStoreMxs", weChatPreStoreMxs);
		mav.addObject("size", weChatPreStoreMxs.size());
		mav.setViewName("wechat/preStoreMx/preStoreMxDetail");
		
		return mav;
	}
	
	private List<WeChatPreStoreMx> process(List<WeChatPreStoreMx> weChatPreStoreMxs){
		DecimalFormat df = new DecimalFormat("######0.00");
		List<WeChatPreStoreMx> interceptor = new ArrayList<WeChatPreStoreMx>();
		for(WeChatPreStoreMx weChatPreStoreMx : weChatPreStoreMxs){
			if(weChatPreStoreMx.getPreStoreMoney() != null && !"".equals(weChatPreStoreMx.getPreStoreMoney())){
				// 过滤金额为0的记录
				if(Math.abs(weChatPreStoreMx.getPreStoreMoney()) > 0.00000001){
					String formate = df.format(weChatPreStoreMx.getPreStoreMoney());
					weChatPreStoreMx.setMoney(formate);
					interceptor.add(weChatPreStoreMx);
				}
			}
		}
		return interceptor;
	}
}
