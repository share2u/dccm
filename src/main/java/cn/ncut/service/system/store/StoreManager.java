package cn.ncut.service.system.store;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.entity.system.City;
import cn.ncut.entity.system.Store;
import cn.ncut.util.PageData;

/** 
 * 说明： 门店管理接口
 * 创建人：FH Q313596790
 * 创建时间：2016-12-09
 * @version
 */
public interface StoreManager{

	/**新增
	 * @param store
	 * @throws Exception
	 */
	public void save(Store store)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param store
	 * @throws Exception
	 */
	public void edit(Store store)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	
	/**员工管理选择门店：列表列出所有门店
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllStore(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	public List<City>  findCityByName(String name) throws Exception;
	
	public String queryCityIDByName(String name) throws Exception;
	
	public PageData queryNameById(String id)  throws Exception;
	

	public String findNameById(PageData pd) throws Exception;

	public List<PageData> findAllNames(PageData pd) throws Exception;
	
	
	/**
	 * 根据板块id查询门店列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selectStoresBymId(PageData pd) throws Exception;
}

