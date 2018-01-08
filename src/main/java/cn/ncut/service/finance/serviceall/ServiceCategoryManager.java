package cn.ncut.service.finance.serviceall;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.entity.system.ServiceCategory;
import cn.ncut.util.PageData;


/** 
 * 说明： 服务项目类别接口
 * 创建人：FH Q313596790
 * 创建时间：2016-12-16
 * @version
 */
public interface ServiceCategoryManager{

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
	 * 查询所有类别
	 * @param categoryId
	 * @return
	 * @throws Exception 
	 */
	public List<ServiceCategory> queryAllCategory(Integer categoryId) throws Exception;
	
	public List<ServiceCategory> queryAllCategory(PageData pd) throws Exception;

   //	通过id查询此分类
	public PageData findById(int parseInt) throws Exception;
}

