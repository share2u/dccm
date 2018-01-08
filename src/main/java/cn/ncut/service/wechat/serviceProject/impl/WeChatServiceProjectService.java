package cn.ncut.service.wechat.serviceProject.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatServiceProject;
import cn.ncut.service.wechat.serviceProject.WeChatServiceProjectManager;

@Service("weChatServiceProjectService")
public class WeChatServiceProjectService implements WeChatServiceProjectManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public WeChatServiceProject getServiceProjectByProjectId(String projectId)
			throws Exception {
		return (WeChatServiceProject)dao.findForObject("WeChatServiceProjectMapper.getServiceProjectByProjectId", projectId);
	}
}
