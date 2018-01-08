package cn.ncut.service.wechat.store;

import java.util.List;

import cn.ncut.entity.wechat.pojo.WeChatStore;
import cn.ncut.util.PageData;

public interface WeChatStoreManager {
	public List<WeChatStore> getStoresBymId(String mId)throws Exception;

	public WeChatStore getStoreDetailByStoreId(String storeId) throws Exception;

	public List<WeChatStore> getStoresBySome(String queryParam) throws Exception;

	public List<PageData> getStoreDoctersByStoreId(String storeId) throws Exception;

	public List<PageData> getCommentsByStoreId(String storeId) throws Exception;

	public void sendTelToStore(PageData pageData)throws Exception;

	public PageData getDoctorDetailById(String staffId) throws Exception;

	public List<PageData> getServiceProjectsById(PageData pageData) throws Exception;

}
