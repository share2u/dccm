package cn.ncut.service.system.store.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.City;
import cn.ncut.entity.system.Store;
import cn.ncut.service.system.store.StoreManager;
import cn.ncut.util.PageData;


/** 
 * 说明： 门店管理
 * 创建人：FH Q313596790
 * 创建时间：2016-12-09
 * @version
 */
@Service("storeService")
public class StoreService implements StoreManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(Store store)throws Exception{
		dao.save("StoreMapper.save", store);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("StoreMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(Store store)throws Exception{
		dao.update("StoreMapper.edit", store);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.listAll", pd);
	}
	
	/**列表所有门店
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.listAllStore", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoreMapper.findById", pd);
	}
	/*public PageData findCityByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoreMapper.findById", pd);
	}*/
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StoreMapper.deleteAll", ArrayDATA_IDS);
	}

	
	public List<City> findCityByName(String cityname) throws Exception{
		
		return (List<City>) dao.findForList("CityMapper.getCityByName", cityname);
	}

	/**
	 *门店 城市的自动补全：根据name查cityID
	 */
	@Override
	public String queryCityIDByName(String name) throws Exception {
		List<City> citys = new ArrayList<City>();
		String id = new String();
		citys = (List<City>) dao.findForList("CityMapper.queryCityIDByName", name);
		if (null != citys && citys.size() > 0) {
			id =  citys.get(0).getCITY_ID();
			}
		
		return id;
	}

	/**
	 *门店 城市的自动补全：根据id查cityName
	 */
	@Override
	public PageData queryNameById(String id) throws Exception {
		City city = new City();
		return (PageData)dao.findForObject("CityMapper.queryNameById", id);
	}

	/**
	 * 根据门店id查询门店名称
	 */
	@Override
	public String findNameById(PageData pd) throws Exception {
		return (String) dao.findForObject("StoreMapper.findNameById", pd);
	}

	@Override
	public List<PageData> findAllNames(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StoreMapper.findAllNames", pd);
	}

	@Override
	public List<PageData> selectStoresBymId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.selectStoresBymId", pd);
	}

	
}
	


