package cn.ncut.service.wechat.mobileInfo.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatMobileInfo;
import cn.ncut.service.wechat.mobileInfo.WeChatMobileInfoManager;

@Service(value = "weChatMobileInfoService")
public class WeChatMobileInfoService implements WeChatMobileInfoManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public WeChatMobileInfo getMobileInfoByMobileNum(String mobileNum)
			throws Exception {
		return (WeChatMobileInfo)dao.findForObject("WeChatMobileInfoMapper.getMobileInfoByMobileNum", mobileNum);
	}
	
}
  