package cn.ncut.service.user.credit.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;
import cn.ncut.service.user.credit.CreditManager;

/** 
 * 说明： 积分管理
 * 创建人：FH Q313596790
 * 创建时间：2017-01-06
 * @version
 */
@Service("creditService")
public class CreditService implements CreditManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增  
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CreditMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CreditMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CreditMapper.edit", pd);
	}
	
	
	/**更新积分
	 * @param pd
	 * @throws Exception
	 */
	public void updateCreditByUid(PageData pd)throws Exception{
		dao.update("CreditMapper.updateCreditByUid", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CreditMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CreditMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CreditMapper.findById", pd);
	}
	
	/**通过uid获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CreditMapper.findByUId", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CreditMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

