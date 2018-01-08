package cn.ncut.service.wechat.home;

import java.util.List;

import cn.ncut.entity.wechat.pojo.SNSUserInfo;
import cn.ncut.entity.wechat.pojo.WeChatUser;
import cn.ncut.util.PageData;

public interface HomeManager {
	//home页图片轮播，图片路径，图片描述，图片url
	public List<PageData> getAds() throws Exception; 
	//home四个板块的显示,图片路径，图片描述，图片名称，
	public List<PageData> getServiceModules()throws Exception;
}
