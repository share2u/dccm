package cn.ncut.recommend.collaborative;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import cn.ncut.service.finance.serviceall.ServiceCostManager;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.util.PageData;

@Component("collaborativeFilteringOperation")
public class CollaborativeFilteringOperation {
	@Resource(name = "recommendService")
	private RecommendManager recommendService;
	@Resource(name = "servicecostService")
	private ServiceCostManager servicecostService;
	@Resource(name = "readFile")
	private ReadFile readFile;
	/**
	 * @param group2的值
	 * @throws Exception
	 */
	public void CollaborativeFiltering(int i) throws Exception{
		
		PageData pd = new PageData();
		
			//1、查询这个组下所有的用户 错了 应该查询个数
			pd.put("GROUP2", i);
			int usercount = recommendService.selectCountUidByGroup2(pd);
			//2、查询所有项目个数的最大值
            int servicecostcount = servicecostService.findmaxid(pd);
		
			//3、查询所有uid，从小到大排列
			//建立一个uid的数组，把uid放进去
			int[] uid=new int[usercount];//存放uid
			int j = 0;
			List<PageData> uidpdlist = recommendService.selectUidByGroup2(pd);
			for(PageData uidpd : uidpdlist){
				int id = (int)uidpd.get("uid");
				uid[j] = id;
				j++;
			}
			//对这个数组进行排序
			Arrays.sort(uid);
			//4、建立数组，读数据库
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
			  //System.out.println(sd.format(new Date()));
		int[][] user_movie_base = new int[usercount][servicecostcount];//943*1682，第一列为uid
			System.out.println("时间："+sd.format(new Date())+"： ---第"+i+"组开始进行初始化---");
			user_movie_base = readFile.readFile(i, uid, usercount, servicecostcount); // base中有943个用户对1682个项目的评分
			System.out.println("时间："+sd.format(new Date())+"： ---第"+i+"组初始化完毕---");
		    //5、进行协同过滤
			System.out.println("时间："+sd.format(new Date())+"： ---第"+i+"组开始计算相似度---");
			System.out.println("-----使用改进后的相似度计算公式计算用户间相似度-----");
			double[][] similarityMatrix = new ProduceSimilarityMatrix()
			.produceSimilarityMatrix(user_movie_base,uid);//计算用户之间的相似度,以上都是对的
			System.out.println("时间："+sd.format(new Date())+"： ---第"+i+"组计算相似度完毕---");
			System.out.println("时间："+sd.format(new Date())+"： ---第"+i+"组开始进行评分预测---");
	        double[][] matrix = GetScore.getScore(user_movie_base, similarityMatrix);//预测评分
	        System.out.println("时间："+sd.format(new Date())+"： ---第"+i+"组预测评分完毕---");
			
			//6、删除初诊复诊，把结果写入数据库
	        System.out.println("时间："+sd.format(new Date())+"： ---开始过滤初诊复诊---");
	        List<RecommendEntity> relist= new ArrayList<RecommendEntity> ();
			 for (int ii = 0; ii <usercount;++ii) {  
				 List<Integer> pids = new ArrayList<Integer>();//放入的是pid
				 //得到uid，取出排列好的uid的数组的第index个对象
				 
				 int uid_i = uid[ii];
					double[] temp = new double[matrix[ii].length];
					for (int jj = 0; jj < temp.length; jj++) {
						temp[jj] = matrix[ii][jj];
					}
				 
				  Arrays.sort(temp);//数组升序排序,这里有错
	        for (int count = 10,jj = temp.length-1; jj>=0;jj--) {  //倒序排序(取前k个)
	        	for(int kk=0;kk<servicecostcount;kk++){
	        		if(matrix[ii][kk]==temp[jj]&&temp[jj]!=0.0){
	        			 if(!pids.contains(kk+1)&&count>0){
	        				 if((kk+1)!=-2){
	        			 pids.add(new Integer(kk+1));
	        			 count--;
	        				 }
	        			
	        			 }
	        		}
	        	}
	         
	          
	        }
	        if(pids.size()!=0){//如果进行了推荐
	        	RecommendEntity re = new RecommendEntity();
	        	re.setUid(uid_i);
	        	re.setPids(pids);
	        	relist.add(re);
	        	
	        }
	        
	    }
				//把结果写入数据库
			 for(RecommendEntity re : relist){//list2
					
				 List<Integer> pidlist = re.getPids();//推荐列表
				 //用，隔开拼字符串
				 String pidstring="";
				 for(Integer pid:pidlist){
					pidstring = pidstring+pid+",";
				 }
				 if(!pidstring.equals("")){
				 pidstring = pidstring.substring(0,pidstring.length()-1);//去掉最后一个，
				 }
				 else{pidstring = "";}
				 PageData recomendpd = new PageData();
				 recomendpd.put("UID", re.getUid());
				 recomendpd.put("SERVICECOST_IDS", pidstring);
				 recommendService.insertCF(recomendpd);
	}
			 
			
			 //查询每个人的个性化推荐
			 PageData userpd = new PageData();
			 userpd.put("group2", i);
			 List<PageData> userlist = recommendService.selectAllMember(userpd);//查询所有协同过滤不是空的用户
			 for(PageData upd : userlist){//对于每一个人
				 
				 upd.put("UID", upd.get("uid"));
				
				 PageData recommendpd = recommendService.selectRecommendByUid2(upd);//如果为空返回0
				 
				 String servicecost_ids = recommendpd.getString("servicecost_ids");
				 
				
					  String servicecost_id[]=servicecost_ids.split(",");
				
				 List<Integer> pids = new ArrayList<Integer>();//该用户推荐的项目集合
				
				 if(servicecost_id.length>0){
					for(int ii=0;ii<servicecost_id.length;ii++){
						if(!servicecost_id[ii].endsWith("0")){
						pids.add(Integer.parseInt(servicecost_id[ii]));//string 转int
						}
					}
					}
				
					//1、查询用户购买了哪些医生的项目
					 List<PageData> staffpdlist = recommendService.selectStaffByUid(upd);//在预约表中进行查询
					 //2、查询用户在该医生下面买了什么项目
					 for(PageData staffpd:staffpdlist){
						 //查询该用户在该医生下购买了什么项目
						 upd.put("STAFF_ID", staffpd.get("STAFF_ID"));
						 List<PageData> servicecostpdlist = recommendService.selectServicecostByUidAndStaffid(upd);
						 for(PageData servicecostpd:servicecostpdlist){
							
							  String fuzhenpname = servicecostpd.getString("PNAME");
							  String chuzhenpname = fuzhenpname.replace("复诊", "初诊");
							  //查询初诊对应的servicecostid
							  upd.put("PNAME", chuzhenpname);
							  List<PageData> servicecostidpdlist = recommendService.selectServicecostidByPname(upd);
							  for(PageData  servicecostidpd: servicecostidpdlist){
									 Iterator<Integer> ListIterator = pids.iterator();  
									 while(ListIterator.hasNext()){  
									     Integer e = ListIterator.next();//推荐的结果 
									     if(e.equals((Integer)(servicecostidpd.get("servicecost_id")))){  
									     ListIterator.remove();  //删除初诊推荐
									    
									     }  
									 }
							  }
							  
						 }
						
						//2、未购买初诊则删除复诊推荐
						 Iterator<Integer> ListIterator = pids.iterator(); 
						 int flag = 0;//未购买
						 while(ListIterator.hasNext()){ 
							//根据pid查询pname
								 PageData pd3 = new PageData();
							 pd3.put("SERVICECOST_ID", ListIterator.next());
							List<PageData> servicecostpdlist2 = recommendService.selectPnameByServicecostid(pd3);
							for(PageData servicecostpd:servicecostpdlist2){
								 String pname = servicecostpd.getString("PNAME");
								 if(pname.indexOf("复诊")!=-1){//如果这个pname包含复诊
									//查询该复诊项目对应的初诊项目的servicecost_id
									  String fuzhenpname = servicecostpd.getString("PNAME");
									 
									  String chuzhenpname = fuzhenpname.replace("复诊", "初诊");
									  //查询pname，servicecostid
									
									  String staff_id = servicecostpd.getString("STAFF_ID");
									  pd3.put("STAFF_ID", staff_id);
									  pd3.put("PNAME", chuzhenpname);
									  List<PageData> chuzhenlistpd = recommendService.selectservicecostidBypname(pd3);//根据pname和staffif查询
									  for(PageData chuzhenpd:chuzhenlistpd){
										  chuzhenpd.put("UID", upd.get("uid"));
							            	
							            	List<PageData> servicepdlist= recommendService.selectchuzhen(chuzhenpd);//根据uid和servicecostid
							            	if(!servicepdlist.isEmpty()){
							            		//如果购买了初诊项目
							            		flag = 1;
							            	}
							            	}
							              if(flag == 0){
							              ListIterator.remove();
							              }
							              }
										  
									  }
										  }
									  }
					 //更新数据库
					 String pidstring="";
					 for(Integer pid:pids){
						pidstring = pidstring+pid+",";
					 }
					 if(!pidstring.equals("")){
					 pidstring = pidstring.substring(0,pidstring.length()-1);//去掉最后一个，
					 }
					 else{pidstring = "";}
					
					 PageData recommendpd2 = new PageData();
					 recommendpd2.put("UID", upd.get("UID"));
					 recommendpd2.put("SERVICECOST_IDS", pidstring);
					 recommendService.updateCF(recommendpd2);
						 }
					
			
		
}
}
