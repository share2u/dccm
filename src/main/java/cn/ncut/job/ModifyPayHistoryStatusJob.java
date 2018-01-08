package cn.ncut.job;

import javax.annotation.Resource;
import cn.ncut.service.wechat.orderQuery.impl.OrderCheckQueryService;

public class ModifyPayHistoryStatusJob {
	@Resource(name = "orderCheckQueryService")
	private OrderCheckQueryService orderCheckQueryService;
	
	public void modifyPayHistoryStatus(){
		this.orderCheckQueryService.checkOrderStatus();
	}
}
