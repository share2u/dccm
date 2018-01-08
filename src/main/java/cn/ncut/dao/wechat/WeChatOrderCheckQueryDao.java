package cn.ncut.dao.wechat;

import java.util.List;

import cn.ncut.entity.wechat.pojo.WeChatPayHistory;

public interface WeChatOrderCheckQueryDao {
	public abstract void checkOrderStatus(List<WeChatPayHistory> payHistories);
}
