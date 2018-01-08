package cn.ncut.service.wechat;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ncut.entity.wechat.menu.Button;
import cn.ncut.entity.wechat.menu.ClickButton;
import cn.ncut.entity.wechat.menu.ComplexButton;
import cn.ncut.entity.wechat.menu.Menu;
import cn.ncut.entity.wechat.menu.ViewButton;
import cn.ncut.entity.wechat.pojo.Token;
import cn.ncut.util.wechat.CommonUtil;
import cn.ncut.util.wechat.MenuUtil;
import cn.ncut.util.wechat.SpringPropertyResourceReader;

/**
 * 菜单管理器类
 * 
 * @author liufeng
 * @date 2013-10-17
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	private static final String domainName = SpringPropertyResourceReader.getProperty("domainName");
	private static final String appId = SpringPropertyResourceReader.getProperty("appId");
	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		ViewButton btn11 = new ViewButton();
		btn11.setName("我们是谁");
		btn11.setType("view");
		btn11.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MjQzMTI4Mg==&mid=503891154&idx=1&sn=ab64997a507bc602f5f18753a317c870&chksm=3d5a090f0a2d80193f9080c325bdcb258b18911be9e1ac51c6a3d3542bce0a84aff4542fc991#rd");

		ViewButton btn12 = new ViewButton();
		btn12.setName("品牌故事");
		btn12.setType("view");
		btn12.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MjQzMTI4Mg==&mid=503891156&idx=1&sn=86c0d62c386cd4b5f22f713dbc0c8385&chksm=3d5a09090a2d801fb90bfd338c72f418f7d1e3203261dcb4679965eee9241655df85d0e5c3b1#rd");

		ViewButton btn13 = new ViewButton();
		btn13.setName("程氏在海外");
		btn13.setType("view");
		btn13.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MjQzMTI4Mg==&mid=503891155&idx=1&sn=ee45d2c11346cb0c70284c54079d2545&chksm=3d5a090e0a2d8018a0e5f0a83a0ad6938a7cce4be32f9a2a17cc9ab48256d34e5480ef32387c#rd");
		
		

		ViewButton btn21 = new ViewButton();
		btn21.setName("医疗");
		btn21.setType("view");
		btn21.setUrl("http://DOMAINNAME/dccm/weChat/getStoresByModuleId02".replace("DOMAINNAME",domainName));

		ViewButton btn22 = new ViewButton();
		btn22.setName("培训");
		btn22.setType("view");
		btn22.setUrl("http://DOMAINNAME/dccm/weChat/getStoresByModuleId01".replace("DOMAINNAME",domainName));
/*
		ViewButton btn23 = new ViewButton();
		btn23.setName("养生");
		btn23.setType("view");
		btn23.setUrl("http://DOMAINNAME/dccm/weChat/construction".replace("DOMAINNAME",domainName));

		ViewButton btn24 = new ViewButton();
		btn24.setName("电商");
		btn24.setType("view");
		btn24.setUrl("http://DOMAINNAME/dccm/weChat/construction".replace("DOMAINNAME",domainName));
	*/	
		ViewButton btn25 = new ViewButton();
		btn25.setName("首页");
		btn25.setType("view");
		btn25.setUrl("http://DOMAINNAME/dccm/weChat/home".replace("DOMAINNAME",domainName));

		ViewButton btn31 = new ViewButton();
		btn31.setName("新手指南");
		btn31.setType("view");
		btn31.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MjQzMTI4Mg==&mid=503891157&idx=1&sn=9c888cbc2ba8536b4a4da911bdfe1fac&chksm=3d5a09080a2d801e0cf4d75d3524ed82fc1fc6d7394bf89529501cbb95e950ed4494028d501c#rd");

		ViewButton btn32 = new ViewButton();
		btn32.setName("充值");
		btn32.setType("view");
		btn32.setUrl("http://DOMAINNAME/dccm/weChat/recharge".replace("DOMAINNAME",domainName));
		
		ViewButton btn33 = new ViewButton();
		btn33.setName("个人中心");
		btn33.setType("view");
		btn33.setUrl("http://DOMAINNAME/dccm/weChat/userCenter".replace("DOMAINNAME",domainName));
		
		ViewButton btn34 = new ViewButton();
		btn34.setName("投诉与建议");
		btn34.setType("view");
		btn34.setUrl("http://DOMAINNAME/dccm/weChat/suggest".replace("DOMAINNAME",domainName));
		
		

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("程氏针灸");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("服务直通车");
		mainBtn2.setSub_button(new Button[] { btn25,btn21, btn22});

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("我是会员");
		mainBtn3.setSub_button(new Button[] { btn31, btn32,btn33,btn34});

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
		
		return menu;
	}

	public static void main(String[] args) throws IOException {

	    
	    //获取oAuth2.0url链接
	    /*
	    //  构造网页授权链接
	   // String url="http://123.57.222.229/dczy/course.jsp";
	    String urlUTF8=CommonUtil.urlEncodeUTF8("http://zyiv.v112.10000net.cn/dccm/weChatNavigate/getWeChatUserInfo/page");
	   // String urlUTF8="http%3A%2F%2F123.57.222.229%2Fdczy%2FoauthServlet";
	    System.out.println(urlUTF8);
	    
	    */

		// 调用接口获取凭证		
	    	Token token = CommonUtil.getToken();
	    	System.out.println(token);
		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());

			// 判断菜单创建结果
			if (result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败！");
		}
 
	 
	}
}
