package cn.ncut.service.user.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.user.order.OrderMxManager;
import cn.ncut.util.PageData;


/** 
 * 说明： 订单(明细)
 * 创建人：FH Q313596790
 * 创建时间：2016-12-30
 * @version
 */
@Service("ordermxService")
public class OrderMxService implements OrderMxManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OrderMxMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OrderMxMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OrderMxMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderMxMapper.data", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrderMxMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrderMxMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OrderMxMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**查询明细总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrderMxMapper.findCount", pd);
	}

	/**
	 * 通过orderId找到对应的明细信息
	 */
	@Override
	public List<PageData> findAllByOrderId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("OrderMxMapper.findAllByOrderId", pd);
	}
	
	/**
	 * 通过orderId找到用储值卡返点支付的钱
	 */
	@Override
	public PageData findFandianByOrderId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OrderMxMapper.findFandianByOrderId", pd);
	}
	
}

