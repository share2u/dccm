package cn.ncut.service.wechat.payPerformance;

import cn.ncut.entity.wechat.pojo.WeChatDelayOfNetwork;
import cn.ncut.entity.wechat.pojo.WeChatPayPerformance;

public interface WeChatPayPerformanceManager {
    public abstract void savePayPerformance(WeChatPayPerformance weChatPayPerformance) throws Exception;
    public abstract void saveWeChatDelayOfNetwork(WeChatDelayOfNetwork WeChatDelayOfNetwork) throws Exception;
}
