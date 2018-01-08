package cn.ncut.service.wechat.payPerformance.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatDelayOfNetwork;
import cn.ncut.entity.wechat.pojo.WeChatPayPerformance;
import cn.ncut.service.wechat.payPerformance.WeChatPayPerformanceManager;

@Service("weChatPayPerformanceService")
public class WeChatPayPerformanceService implements WeChatPayPerformanceManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    
    @Override
    public void savePayPerformance(WeChatPayPerformance weChatPayPerformance) throws Exception {
        dao.save("WeChatPayPerformanceMapper.savePayPerformance", weChatPayPerformance);
    }

	@Override
	public void saveWeChatDelayOfNetwork(
			WeChatDelayOfNetwork WeChatDelayOfNetwork) throws Exception {
		dao.save("WeChatPayPerformanceMapper.saveDelayOfNetwork", WeChatDelayOfNetwork);
	}
}
