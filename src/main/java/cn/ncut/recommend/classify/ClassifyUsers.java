package cn.ncut.recommend.classify;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.service.user.member.MemberManager;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ncut.util.AgeUtils;
import cn.ncut.util.PageData;
@Component("classifyUsers")
@Configuration

public class ClassifyUsers {
	@Resource(name = "recommendService")
	private RecommendManager recommendService;

	@Resource(name = "memberService")
	private MemberManager memberService;


	/**
	 * @param args
	 */
	
	public void Classify() throws Exception{
		
		//给用户进行分类，并计算每个组最常购买的十件商品
		
		List<PageData> userlist = memberService.selectAll(new PageData());//所有用户
		for(PageData userpd : userlist){
			PageData pd = new PageData();
			int uid = (int)userpd.get("uid");//uid
			String birth = userpd.getString("age");//出生年月,字符串
			int sex = (int)userpd.get("sex");//性别
			String city = userpd.getString("city");//城市
			boolean rs;
			if(birth!=null){
			String regEx = "^\\d{4}-\\d{2}-\\d{2}$";
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(birth);
			 rs= matcher.matches();
			}else{
				rs=false;
			}
			int age=0;
			 if(city!=null&&city.length()>=2){
	        	   city = city.substring(0,2);
	        	   }
			 if(birth!=null&&rs!=false){
          	   age = AgeUtils.getAgeFromBirthTime(birth);//用户的年龄
			 }else{
				 age = 20;
			 }
			 if(city!=null){
			 if(age<=20&&city.equals("北京")&&sex==1){
				 pd.put("UID", uid);
				 pd.put("GROUP1", 1);
				 //查询该uid是否已经插入
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
			 }
             else if(age<=20&&city.equals("北京")&&sex==2){
            	 pd.put("UID", uid);
				 pd.put("GROUP1", 2);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
			 }
             else if(age<=20&&city.equals("上海")&&sex==1){
            	 pd.put("UID", uid);
				 pd.put("GROUP1", 3);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
			 }
             else if(age<=20&&city.equals("上海")&&sex==2){
            	 pd.put("UID", uid);
				 pd.put("GROUP1", 4);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
	         }
             else if(age>20&&age<=40&&city.equals("北京")&&sex==1){
            	 pd.put("UID", uid);
				 pd.put("GROUP1", 5);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
	         }  else if(age>20&&age<=40&&city.equals("北京")&&sex==2){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 6);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
	         }  else if(age>20&&age<=40&&city.equals("上海")&&sex==1){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 7);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
	         }
	         else if(age>20&&age<=40&&city.equals("上海")&&sex==2){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 8);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		     }
	         else if(age>40&&age<=60&&city.equals("北京")&&sex==1){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 9);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		     }
	         else if(age>40&&age<=60&&city.equals("北京")&&sex==2){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 10);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		     }
	         else if(age>40&&age<=60&&city.equals("上海")&&sex==1){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 11);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		     }
	         else if(age>40&&age<=60&&city.equals("上海")&&sex==2){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 12);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		     }
	         else if(age>60&&city.equals("北京")&&sex==1){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 13);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		     }
	         else if(age>60&&city.equals("北京")&&sex==2){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 14);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		     }
	         else if(age>60&&city.equals("上海")&&sex==1){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 15);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		      }
	         else if(age>60&&city.equals("上海")&&sex==2){
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 16);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		      }
	         else {
	        	 pd.put("UID", uid);
				 pd.put("GROUP1", 17);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
		      }
			 
			 }
			 else{
				 pd.put("UID", uid);
				 pd.put("GROUP1", 17);
				 int count = recommendService.selectUid(pd);
				 if(count==0){
				 recommendService.insertinit(pd);
				 }else{
					 recommendService.updateGroup1ByUid(pd);
				 }
			 }
		}
		//查询每一个group1的用户经常购买的项目
		for(int i = 1;i<=17;i++){
			String pidstring="";
			PageData pd = new PageData();
			pd.put("GROUP1", i);
			List<PageData> top10pdlist = recommendService.selectTop10Bygroup1(pd);
			for(PageData top10:top10pdlist){
				Integer pid = (Integer) top10.get("servicecost_id");
				if(pid!=-2){
					pidstring = pidstring+pid+",";
				}
			}
				 if(!pidstring.equals("")){
				 pidstring = pidstring.substring(0,pidstring.length()-1);//去掉最后一个，
				 }
				 else{pidstring = "";}
				 //更新top10
				 pd.put("TOP10", pidstring);
				 recommendService.updateTop10ByGroup1(pd);
			
			
		}
		
	}

}