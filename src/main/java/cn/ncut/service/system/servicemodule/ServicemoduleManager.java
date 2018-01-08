package cn.ncut.service.system.servicemodule;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/** 
 * 说明： 版块管理接口
 * 创建人：FH Q313596790
 * 创建时间：2016-12-09
 * @version
 */
public interface ServicemoduleManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	
	/**修改不上传图片
	 * @param pd
	 * @throws Exception
	 */
	public void editnoimg(PageData pd)throws Exception;
	
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
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll0(PageData pd)throws Exception;
	
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
	
	
	/**
	 * 根据板块id查询该板块
	 * @param object
	 * @return
	 */
	public PageData queryModuleById(String id) throws Exception;
	
}

