package cn.ncut.service.user.userdiscount;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/** 
 * 说明： 用户优惠券接口
 * 创建人：FH Q313596790
 * 创建时间：2017-02-17
 * @version
 */
public interface UserDiscountManager{

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
	
	public List<PageData> findByUId(Page page)throws Exception;
	
	
	public void deleteDiscounGroupByid(String groupid) throws Exception;
	
	//统计优惠券
	/**
	 * 根据优惠券组的创建时间等统计优惠券组的发放数量
	 */
	public List<PageData> selectDiscountGroups(PageData pd) throws Exception;
	/**
	 * 根据优惠券组的id 查询组内优惠券种类和是否使用的个数
	 */
	public List<PageData> selectIsUsedDiscountByGroupId(PageData pd) throws Exception;
	
	
	
}

