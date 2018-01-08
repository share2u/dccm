package cn.ncut.controller.wechat;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.wechat.pojo.SNSUserInfo;
import cn.ncut.entity.wechat.pojo.WeChatOrder;
import cn.ncut.entity.wechat.pojo.WeChatStore;
import cn.ncut.entity.wechat.pojo.WeChatUser;
import cn.ncut.entity.wechat.pojo.WeChatUserDiscount;
import cn.ncut.entity.wechat.pojo.WeixinOauth2Token;
import cn.ncut.service.wechat.card.WeChatCardManager;
import cn.ncut.service.wechat.discount.impl.WeChatUserDiscountService;
import cn.ncut.service.wechat.home.impl.HomeService;
import cn.ncut.service.wechat.order.impl.WeChatOrderService;
import cn.ncut.service.wechat.store.WeChatStoreManager;
import cn.ncut.service.wechat.user.impl.WeChatUserService;
import cn.ncut.util.PageData;
import cn.ncut.util.wechat.AdvancedUtil;
import cn.ncut.util.wechat.CommonUtil;
import cn.ncut.util.wechat.SpringPropertyResourceReader;
import cn.ncut.util.wechat.TypeConvertUtil;
import cn.ncut.util.wechat.WeChatLogUtil;

/**
 * 自定义菜单导航控制,授权获取用户的基本信息
 * */
@Controller
@RequestMapping("/weChatNavigate")
public class WeChatNavigateController extends BaseController {
	
	private static final String domainName = SpringPropertyResourceReader
			.getProperty("domainName");
	private static final String appId = SpringPropertyResourceReader
			.getProperty("appId");
	Logger logger = LoggerFactory.getLogger("weChatService");
	
	@Resource(name = "weChatUserService")
	private WeChatUserService weChatUserService;
	
	@Resource(name = "weChatOrderService")
	private WeChatOrderService weChatOrderService;

	@Resource(name = "weChatUserDiscountService")
	private WeChatUserDiscountService weChatUserDiscountService;

	@Resource(name = "weChatCardService")
	private WeChatCardManager weChatCardService;

	@Resource(name = "weChatHomeService")
	public HomeService homeService;

	@Resource(name = "weChatStoreService")
	private WeChatStoreManager weChatStoreService;

	/**
	 * 发起获取用户openid普通授权请求
	 * 
	 * @param request
	 * @param response
	 * @param page
	 *            自定义菜单资源链接,如用户中心..
	 * @throws Exceptionm
	 * */
	@RequestMapping("/oauthAuthorize/{page}")
	public ModelAndView oauthAuthorize(@PathVariable("page") String page) {
		logger.debug("-- 发起获取用户openid普通授权请求 -- 起源page:" + page);
		String userInfoUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http%3A%2F%2FDOMAINNAME%2Fdccm%2FweChatNavigate%2FgetWeChatUserOpenId%2Fpage&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		userInfoUrl = userInfoUrl.replace("page", page);
		userInfoUrl = userInfoUrl.replace("APPID", appId);
		userInfoUrl = userInfoUrl.replace("DOMAINNAME", domainName);
		ModelAndView mav = this.getModelAndView();
		mav.addObject("userInfoUrl", userInfoUrl);
		mav.setViewName("wechat/home/sendOAuth2");
		return mav;
	}

	/**
	 * 通过静默授权获得openId 判断数据库中是否存在某用户
	 * 
	 * @param request
	 * @param response
	 * @param page
	 *            自定义菜单资源链接,如用户中心..
	 * @throws Exceptionm
	 * */
	@RequestMapping("/getWeChatUserOpenId/{page}")
	public ModelAndView getWeChatUserOpenId(HttpServletRequest request,
			@PathVariable("page") String page) throws Exception {
		logger.debug("-- start 静默授权获取用户openId 回调通知 -- 起源page:" + page);
		ModelAndView mav = this.getModelAndView();
		request.setCharacterEncoding("gb2312");
		String code = "";
		code = request.getParameter("code");
		String appId = CommonUtil.getAppId();
		String appSecret = CommonUtil.getAppSecret();
		HttpSession session = request.getSession();
		logger.debug("--静默授权时的code:" + code);

		if(null !=code){
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil
					.getOauth2AccessToken(appId, appSecret, code);
			String openId = weixinOauth2Token.getOpenId();
			WeChatUser weChatUser = weChatUserService.getUserByOpenId(openId);
			if (weChatUser == null) {
				String userInfoUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http%3A%2F%2FDOMAINNAME%2Fdccm%2FweChatNavigate%2FgetWeChatUserInfo%2fpage&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
				userInfoUrl = userInfoUrl.replace("page", page);
				userInfoUrl = userInfoUrl.replace("APPID", appId);
				userInfoUrl = userInfoUrl.replace("DOMAINNAME", domainName);
				mav.addObject("userInfoUrl", userInfoUrl);
				logger.debug("-- 用户未注册--发起获取用户openid用户信息授权请求 -- 起源page:" + page);
				mav.setViewName("wechat/home/sendOAuth2");
				return mav;
			}
			session.setAttribute("uId", weChatUser.getuId());
			logger.debug("--用户已注册-- end 静默授权获取用户openId 回调通知 -- uId:"
					+ weChatUser.getuId());
			return process(page, weChatUser.getuId());
		}else{
			logger.debug("--静默授权时发生错误，code为null----");
			return null;
			
		}

		
	}

	@RequestMapping("/getWeChatUserInfo/{page}")
	public ModelAndView getWeChatUserInfo(HttpServletRequest request,
			@PathVariable("page") String page) throws Exception {
		logger.debug("-- start 高级授权获取用户基本信息openId 回调通知  -- 起源page:" + page);
		String code = request.getParameter("code");
		logger.debug("=====获取用户信息时code====" + code);
		String appId = CommonUtil.getAppId();
		String appSecret = CommonUtil.getAppSecret();
		WeChatUser weChatUser = new WeChatUser();
		if (null != code) {
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil
					.getOauth2AccessToken(appId, appSecret, code);
			String accessToken = weixinOauth2Token.getAccessToken();
			String openId = weixinOauth2Token.getOpenId();
			weChatUser= weChatUserService.getUserByOpenId(openId);
			// TODO 避免一个微信用户在TAB_USER插入多次
			if(null ==weChatUser){
				 synchronized (this) {
					 weChatUser= weChatUserService.getUserByOpenId(openId);
					 if(null == weChatUser){
						 SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken,
								 openId);
						 logger.debug("未转化之前用户的昵称是:" + snsUserInfo.getNickname());
						 weChatUser = TypeConvertUtil.SNSUser2WeChatUser(snsUserInfo);
						 logger.debug("插入数据库的用户信息是:" + weChatUser.toString());
						 weChatUser = weChatUserService.insertWeChatUser(weChatUser);
						 logger.debug("创建微信用户:"+weChatUser);
					 }
				 }
			}
		} else {
			weChatUser.setuId(-1);
		}
		HttpSession session = request.getSession();
		session.setAttribute("uId", weChatUser.getuId());
		return process(page, weChatUser.getuId());
	}

	/**
	 * 对微信菜单进行分发
	 * 
	 * @param page
	 * @param uId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/process")
	private ModelAndView process(String page, Integer uId) throws Exception {
		ModelAndView mav = this.getModelAndView();
		// uId可能为-1
	
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WeChatLogUtil.controllerLog(request, "process");
		
		if (page.equals("userCenter")) {
			// 待支付订单的数量,已预约订单的数量,优惠券的数量
			List<WeChatOrder> prePayOrders = weChatOrderService.getUserPrePayOrCloseOrderByUserId(uId);
			List<WeChatOrder> appointOrders = weChatOrderService.getUserAppointOrOvertimeOrderByUserId(uId);
			List<WeChatUserDiscount> discounts = weChatUserDiscountService.getUserUnusedAndUnscannedDiscountsByUserId(uId);
			mav.addObject("prePayOrderAmount",prePayOrders.size());
			mav.addObject("appointOrderAmount", appointOrders.size());
			mav.addObject("discountAmount", discounts.size());
			mav.setViewName("wechat/user/userCenter");
		} else if (page.equals("recharge")) {
			PageData cardMoney = weChatCardService.getTotalCardMoney(uId);
			List<PageData> cardTypes = weChatCardService.getCardTypes();
			mav.addObject("cardMoney", cardMoney);
			mav.addObject("cardTypes", cardTypes);
			mav.setViewName("wechat/card/recharge");
		} else if (page.equals("home")) {
			List<PageData> modules = homeService.getServiceModules();
			// List<PageData> ads = homeService.getAds();
			mav.addObject("modules", modules);
			// mv.addObject("ads", ads);
			mav.setViewName("wechat/home/home");
		} else if (page.equals("suggest")) {
			mav.setViewName("wechat/suggest");
		} else if (page.equals("construction")) {
			mav.setViewName("wechat/construction");
		} else if (page.contains("getStoresByModuleId")) {
			List<WeChatStore> stores = weChatStoreService.getStoresBymId(page
					.substring(19, 21));
			mav.addObject("stores", stores);
			mav.setViewName("wechat/store/storeList");
		}
		return mav;
	}
}
