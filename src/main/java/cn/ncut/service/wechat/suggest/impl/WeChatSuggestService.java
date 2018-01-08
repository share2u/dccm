package cn.ncut.service.wechat.suggest.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.service.wechat.suggest.WeChatSuggestManager;
import cn.ncut.util.PageData;
@Service("weChatSuggestService")
public class WeChatSuggestService implements WeChatSuggestManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public void sendSuggest(PageData pd) throws Exception {
		dao.save("WeChatSuggestMapper.sendSuggest", pd);
	}

}
