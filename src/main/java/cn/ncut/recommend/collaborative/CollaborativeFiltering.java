package cn.ncut.recommend.collaborative;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.util.PageData;


@Component("collaborativeFiltering")
@Configuration
@EnableAsync
@EnableScheduling
public class CollaborativeFiltering {
	@Resource(name = "memberService")
	private MemberManager memberService;

	@Resource(name = "recommendService")
	private RecommendManager recommendService;
	@Resource(name = "collaborativeFilteringOperation")
	
	private CollaborativeFilteringOperation collaborativeFilteringOperation;
	//@Scheduled(cron = " 00 25 16 * * ?  ")
	public void Application() throws Exception{
		PageData pd = new PageData();
		 
		//看看有多少group2
		int group2count = recommendService.selectGroup2Count(pd);
		for(int i = 1 ;i<=group2count;i++){
			collaborativeFilteringOperation.CollaborativeFiltering(i);
			
		}
	/*	//计算推荐结果
		 int n = 0;
		 int buysize = 0;
		 int recommendsize = 0;
		 double precision = 0.0;
		 double recall = 0.0;
//1、查询所有用户
		 PageData userpd = new PageData();
		 //查询所有老用户
		 
		 List<PageData> userlist = recommendService.selectOldMember(userpd);
		 for(PageData user : userlist){
			 
			 int uid2 = (int)user.get("uid");
			 user.put("uid", user.get("uid"));
			 //查询他的推荐情况
			PageData recommendpd = recommendService.selectRecommendByUid(user);
			String servicecost_ids = "";
			System.out.println("当前uid"+user.get("uid"));
			if(recommendpd.getString("servicecost_ids")!=null){
			servicecost_ids = recommendpd.getString("servicecost_ids");
			}
			if(servicecost_ids.equals("0"))
			{
				recommendpd = recommendService.selectTop10ByUid(user);
				if(recommendpd.getString("servicecost_ids")!=null){
				 servicecost_ids = recommendpd.getString("servicecost_ids");
				}
			}
			String[] servicecost_id=servicecost_ids.split(",");
			//查询他的购买情况
			List<String> buylist = recommendService.selectAfterBuyByUid(uid2);
			 //推荐∩购买
			for(int m=0;m<servicecost_id.length;m++){
				   if(buylist.contains(servicecost_id[m])){
					   n++;
				   }
			}
			
			 recommendsize = recommendsize + servicecost_id.length;
			
			 buysize =  buysize + buylist.size();
			
		 }
		 precision = (double)n/recommendsize;
		 recall =(double) n/buysize;
		 System.out.println("准确率："+precision);
		 System.out.println("召回率："+recall);*/
		
	}
	
	
}
