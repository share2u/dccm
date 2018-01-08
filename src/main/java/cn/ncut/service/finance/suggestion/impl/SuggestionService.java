package cn.ncut.service.finance.suggestion.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.finance.suggestion.SuggestionManager;
import cn.ncut.util.PageData;


/** 
 * 说明： 投诉与建议
 * 创建人：FH Q313596790
 * 创建时间：2017-01-08
 * @version
 */
@Service("suggestionService")
public class SuggestionService implements SuggestionManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception  
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SuggestionMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SuggestionMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SuggestionMapper.edit", pd);
	}
	
	/**修改评价
	 * @param pd
	 * @throws Exception
	 */
	public void editComment(PageData pd)throws Exception{
		dao.update("SuggestionMapper.updateCheckStatus", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SuggestionMapper.datalistPage", page);
	}
	
	/**列表所有评价
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllComment(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SuggestionMapper.listAllCommentlistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SuggestionMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SuggestionMapper.findById", pd);
	}
	
	/**通过id获取评价的数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData queryCommentById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SuggestionMapper.queryCommentById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SuggestionMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

