package cn.ncut.service.wechat.serviceProject;

import cn.ncut.entity.wechat.pojo.WeChatServiceProject;

/**
 * 服务项目业务类
 * */
public interface WeChatServiceProjectManager {
	/**
	 * 根据服务项目编号查询服务项目信息
	 * @param projectId 服务项目编号
	 * @return 服务项目
	 * @throws Exception
	 * */
	public abstract WeChatServiceProject getServiceProjectByProjectId(String projectId) throws Exception;
}
