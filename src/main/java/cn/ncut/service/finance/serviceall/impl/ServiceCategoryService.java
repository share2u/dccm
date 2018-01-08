package cn.ncut.service.finance.serviceall.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.ServiceCategory;
import cn.ncut.service.finance.serviceall.ServiceCategoryManager;
import cn.ncut.util.PageData;


/** 
 * 说明： 服务项目类别
 * 创建人：ljj
 * 创建时间：2016-12-16
 * @version
 */
@Service("servicecategoryService")
public class ServiceCategoryService implements ServiceCategoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ServiceCategoryMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ServiceCategoryMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ServiceCategoryMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ServiceCategoryMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ServiceCategoryMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ServiceCategoryMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ServiceCategoryMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询所有类别
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceCategory> querySubCategory(Integer categoryId) throws Exception {
		return (List<ServiceCategory>) dao.findForList("ServiceCategoryMapper.queryAllCategoryByPid", categoryId);
	}
	
	/**
	 * 获取所有分类并填充每个分类的子分类
	 */
	public List<ServiceCategory> queryAllCategory(Integer categoryId) throws Exception {
		List<ServiceCategory> clist = this.querySubCategory(categoryId);
		for(ServiceCategory serviceCategory : clist){
			serviceCategory.setSubCategorylist(this.queryAllCategory(serviceCategory.getSERVICECATEGORY_ID()));
		}
		return clist;
	}

	/**
	 * 获取所有分类
	 */
	@Override
	public List<ServiceCategory> queryAllCategory(PageData pd) throws Exception {
		return (List<ServiceCategory>) dao.findForList("ServiceCategoryMapper.queryAllCategory", pd);
	}

	/**
	 * 通过id查询此分类
	 */
	@Override
	public PageData findById(int id) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("ServiceCategoryMapper.findByZJId", id);
	}

}

