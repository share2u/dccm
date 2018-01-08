package cn.ncut.service.wechat.home.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatUser;
import cn.ncut.service.wechat.home.HomeManager;
import cn.ncut.util.PageData;


@Service("weChatHomeService")
public class HomeService implements HomeManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> getAds() throws Exception {
		return (List<PageData>) dao.findForList("WeChatHomeMapper.getAds", null);
	}

	@Override
	public List<PageData> getServiceModules() throws Exception {
		return (List<PageData>) dao.findForList("WeChatHomeMapper.getServiceModules", null);
	}

	



}
