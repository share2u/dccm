package cn.ncut.service.wechat.preStoreMx.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatPreStoreMx;
import cn.ncut.service.wechat.preStoreMx.WeChatPreStoreMxManager;

@Service("weChatPreStoreMxService")
public class WeChatPreStoreMxService implements WeChatPreStoreMxManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@SuppressWarnings("unchecked")
	public List<WeChatPreStoreMx> getUserPreStoreMxByUserId(Integer uId) throws Exception{
		return (List<WeChatPreStoreMx>)dao.findForList("WeChatPreStoreMxMapper.getUserPreStoreMxByUserId", uId);
	}
}
