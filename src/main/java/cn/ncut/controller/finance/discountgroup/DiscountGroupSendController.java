package cn.ncut.controller.finance.discountgroup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Staff;
import cn.ncut.service.finance.discount.DiscountManager;
import cn.ncut.service.finance.discountgroup.DiscountGroupManager;
import cn.ncut.service.system.ncutlog.NcutlogManager;
import cn.ncut.service.system.staff.impl.StaffService;
import cn.ncut.service.user.member.impl.MemberService;
import cn.ncut.util.Const;
import cn.ncut.util.DateUtil;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.PageData;

@Controller
@RequestMapping(value="/discountgroupsend")
public class DiscountGroupSendController extends BaseController{
	
	@Resource(name="discountgroupService")
	private DiscountGroupManager discountgroupService;
	
	@Resource(name="discountService")
	private DiscountManager discountService;
	
	@Resource(name = "staffService")
	private StaffService staffService;

	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "ncutlogService")
	private NcutlogManager NCUTLOG;
	
	/**
	 * 列表（经营，优惠券组合下发）
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"列表DiscountGroup");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
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
		List<PageData>	varList = discountgroupService.list(page);	//列出DiscountGroup列表
		mv.setViewName("finance/discountgroup/discountgroup_list_kefu");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * 下发优惠券组合，显示要下发的客户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goSend")
	public ModelAndView goSend(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String lastStart = pd.getString("lastStart");//获得开始时间
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", lastStart+" 00:00:01");
		}
		String lastEnd = pd.getString("lastEnd");//获得结束时间
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", lastEnd+" 23:59:59");
		}
		page.setPd(pd);
		List<PageData> varList = memberService.DiscountlistmemberlistPage(page);

	
		mv.setViewName("finance/discountgroup/discount_send");

		mv.addObject("varList", varList);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("SelectSex", pd.getString("sex"));
		mv.addObject("GROUP_ID", pd.getString("GROUP_ID"));
		return mv;
	}
	
	/**
	 * 向指定用户发送优惠券
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendDiscount")
	public void sendDiscount(HttpServletResponse response) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "向指定用户发放优惠券");
		Session session = Jurisdiction.getSession();
		
		PageData pd = new PageData();   
		pd = this.getPageData();
	
		//获取优惠券组合的groupid
		String groupid = pd.getString("GROUP_ID");
		
		String groupName = discountgroupService.findByGroupId(groupid).get(0).getString("GROUP_NAME");
		
		//查询属于同一个组合的记录
		List<PageData> discountlist = new ArrayList<PageData>();
		discountlist = discountgroupService.findByGroupId(pd.getString("GROUP_ID"));
		
		String DATA_IDS = pd.getString("DATA_IDS");
		
		String str = "";
		int flag=1;//默认是可以发放的
		
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			
			/**
			 * 判断每个优惠券的可用数量是否满足，若不满足，则所有的都不允许发放
			 */
			logger.debug("---start 判断每个优惠券的可用数量是否满足 ---");
			for(PageData discountPd : discountlist){
				
				//组合中每一种优惠券的个数
				int num = Integer.parseInt(discountPd.getString("DISCOUNT_NUMBER"));
				
				//该种优惠券一共需要发放的总张数
				int totalNum = num * ArrayDATA_IDS.length;
				
				//根据组合中优惠券的discountid查询出它的所有信息
				PageData localPd = new PageData();
				localPd.put("DISCOUNT_ID", discountPd.getString("DISCOUNT_ID"));
				PageData disPd = discountService.findById(localPd);
				
				//该种优惠券剩余多少张，即还可以发放多少张
				int surplusNum = (Integer)disPd.get("NUMBER") - (Integer)disPd.get("A_NUMBER");
				
				if(totalNum>surplusNum){
					flag = 0;
					str = "{\"error\":\"error\"}";
					break;
				}
				
			}
			logger.debug("--- end 判断每个优惠券的可用数量是否满足 ---");
			
			
			logger.debug("--- start 开始发放优惠券组合 ---");
			if(flag==1){
				for (int i = 0; i < ArrayDATA_IDS.length; i++) {
					
					/**
					 * 记录用户-优惠券组合，哪些用户有了这些优惠券组合，将来用以判断是否重发
					 * values(#{UID},#{GROUP_ID},#{CREATE_TIME})
					 */
					PageData groupPd = new PageData();
					groupPd.put("UID", Integer.parseInt(ArrayDATA_IDS[i]));
					groupPd.put("GROUP_ID", groupid);
					groupPd.put("CREATE_TIME", DateUtil.getTime());
					groupPd.put("STAFF_ID", ((Staff) session.getAttribute(Const.SESSION_USER)).getSTAFF_ID());
					discountService.addDiscountGroupToUser(groupPd);
					
					/**
					 * 循环插入用户-优惠券表
					 * values(#{UID},#{DISCOUNT_ID},#{CREATE_TIME},#{START_TIME},#{END_TIME},#{STAFF_ID})
					 */
					for(PageData discountPd : discountlist){
						
						//组合中每一种优惠券的个数
						int num = Integer.parseInt(discountPd.getString("DISCOUNT_NUMBER"));
						
						//该种优惠券一共需要发放的总张数
						int totalNum = num * ArrayDATA_IDS.length;
						
						//根据组合中优惠券的discountid查询出它的所有信息
						PageData localPd = new PageData();
						localPd.put("DISCOUNT_ID", discountPd.getString("DISCOUNT_ID"));
						PageData disPd = discountService.findById(localPd);
						
						for(int j=0; j<num; j++){
							PageData userDiscountPd = new PageData();
							userDiscountPd.put("UID", Integer.parseInt(ArrayDATA_IDS[i]));
							userDiscountPd.put("DISCOUNT_ID", discountPd.getString("DISCOUNT_ID"));
							userDiscountPd.put("GROUP_ID", groupid);
							userDiscountPd.put("CREATE_TIME", DateUtil.getTime());
							userDiscountPd.put("START_TIME", disPd.getString("STARTTIME"));
							userDiscountPd.put("END_TIME", disPd.getString("ENDTIME"));
							userDiscountPd.put("STAFF_ID", ((Staff) session.getAttribute(Const.SESSION_USER)).getSTAFF_ID());
							discountService.sendDiscountToUser(userDiscountPd);
						}
						
						//每个优惠券增加已发数量
						int newAlreadySendNum = (Integer)disPd.get("A_NUMBER") + num;
						disPd.put("A_NUMBER", newAlreadySendNum);
						discountService.edit(disPd);
						str = "{\"success\":\"ok\"}";	
							
					}
					
					NCUTLOG.save(Jurisdiction.getUsername(),  " 向" + ArrayDATA_IDS.length + "位用户发送优惠券组合——"+ groupName
							, this.getRequest().getRemoteAddr());
				}
			}
		}

		response.setContentType("text/javascript; charset=utf-8");
		response.getWriter().write(str);
		
	}
}
