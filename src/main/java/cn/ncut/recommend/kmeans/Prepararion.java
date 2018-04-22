package cn.ncut.recommend.kmeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;



import cn.ncut.recommend.kmeans.Player;

import cn.ncut.service.finance.serviceall.ServiceCategoryManager;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.service.user.member.MemberManager;

import cn.ncut.util.PageData;


@Component("preparation")
public class Prepararion {
	
	@Resource(name = "recommendService")
	private RecommendManager recommendService;
	@Resource(name = "memberService")
	private MemberManager memberService;
	@Resource(name = "servicecategoryService")
	private ServiceCategoryManager servicecategoryService;
	/**
	 * 1、读取用户项目类别评分
	 * 2、
	 * @return
	 * @throws Exception 
	 */
	
	public  List<Player> prepareFile() throws Exception{
		PageData pd = new PageData();
		//1、查询最大用户编号，建立用户-项目类别购买次数矩阵
		//int countuser = memberService.selectCount(pd);
		int countuser = memberService.selectMaxUid(pd);//最大用户编号
		int sex = 0;
		
		//2、查询所有项目类别，这个项目类别是聚类中的特征 
		int countcategory = servicecategoryService.findFatherCategoryCount(pd);//查询项目类别个数
		int[] category = new int[countcategory];//放排好序的父分类数组
		
		int [][] user_category=new int [countuser][countcategory];//用户-项目类别矩阵
		
		
		List<Player> playerlist = new ArrayList<Player>();//下单的用户-项目类别评分集合
		
		//3、查询所有的项目类别编号,按升序排列
		List<PageData> categorylistpd = servicecategoryService.findFatherCategory(pd);
		int i = 0;
	    for(PageData categorypd:categorylistpd){
	    	int categoryid = (int)categorypd.get("F_CATEGORY_ID");
	    	category[i] = categoryid;
	    	i++;
		}
		//3、查询每个用户购买这些项目类别的项目的个数（在预约表中查询）
		List<PageData> userbuypdlist = recommendService.selectcategorybuy(pd);//2017年九月1日之前的下单//改后
		for(PageData userbuypd:userbuypdlist){
			int category_id = (int)userbuypd.get("F_CATEGORY_ID");//父分类编号
			
			String uidstring = userbuypd.getString("U_ID");
			userbuypd.put("UID", userbuypd.getString("U_ID"));
			int uid = Integer.parseInt(uidstring);//用户编号
			int points = (int)((long)userbuypd.get("POINTS"));//用户评分
			category_id=Arrays.binarySearch(category, category_id);
			if(category_id>0){//找到了
				user_category[uid-1][category_id]=1;//用户购买过该类别下的商品
				//user_category[uid-1][category_id]=points;
				
			}
			
			
		}
		
		Player player = null;
		for(int j = 0;j<user_category.length;j++){//行数，用户个数
			player = new Player();
			
			player.setAttribute1(String.valueOf(j+1));//id
			
			//4、查询该用户的性别
			PageData userpd = memberService.findByuid(player.getAttribute1());
			if(userpd!=null){
			 sex = (int)userpd.get("sex");//性别
			}
			player.setAttribute2(String.valueOf(sex));//性别
			player.setAttribute3(String.valueOf(j+1));//id
			player.setKmeansField1(user_category[j][0]);
			player.setKmeansField2(user_category[j][1]);
			player.setKmeansField3(user_category[j][2]);
			player.setKmeansField4(user_category[j][3]);
			player.setKmeansField5(user_category[j][4]);
			player.setKmeansField6(user_category[j][5]);
			player.setKmeansField7(user_category[j][6]);
			player.setKmeansField8(user_category[j][7]);
			player.setKmeansField9(user_category[j][8]);
			player.setKmeansField10(user_category[j][9]);
			player.setKmeansField11(user_category[j][10]);
			player.setKmeansField12(user_category[j][11]);
			player.setKmeansField13(user_category[j][12]);
			player.setKmeansField14(user_category[j][13]);
			player.setKmeansField15(user_category[j][14]);
			player.setKmeansField16(user_category[j][15]);//项目类型
			player.setKmeansField17(user_category[j][16]);//项目类型
			//player.setSex(sex);//性别
			if(player.getKmeansField1()!=0.0||player.getKmeansField2()!=0.0||player.getKmeansField3()!=0.0||player.getKmeansField4()!=0.0||player.getKmeansField5()!=0.0||player.getKmeansField6()!=0.0||player.getKmeansField7()!=0.0||player.getKmeansField8()!=0.0||player.getKmeansField9()!=0.0||player.getKmeansField10()!=0.0||player.getKmeansField11()!=0.0||player.getKmeansField12()!=0.0||player.getKmeansField13()!=0.0||player.getKmeansField14()!=0.0||player.getKmeansField15()!=0.0||player.getKmeansField16()!=0.0||player.getKmeansField17()!=0.0){
				playerlist.add(player);	//购买过商品的用户
			}
			//System.out.println(playerlist.size());
			
			
		}
		
		
		return playerlist;
		
	}
		
		
	}

