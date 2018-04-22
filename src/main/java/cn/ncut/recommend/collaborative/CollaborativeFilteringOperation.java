package cn.ncut.recommend.collaborative;


import java.util.ArrayList;
import java.util.Arrays;
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
			int[][] user_movie_base = new int[usercount][servicecostcount];//943*1682，第一列为uid
			user_movie_base = readFile.readFile(i, uid, usercount, servicecostcount); // base中有943个用户对1682个项目的评分
			System.out.println("第"+i+"组初始化完毕");
		    //5、进行协同过滤
			double[][] similarityMatrix = new ProduceSimilarityMatrix()
			.produceSimilarityMatrix(user_movie_base,uid);//计算用户之间的相似度,以上都是对的
			System.out.println("第"+i+"组计算相似度完毕");
	        double[][] matrix = GetScore.getScore(user_movie_base, similarityMatrix);//预测评分
	        System.out.println("第"+i+"组预测评分完毕");
			
			//6、删除初诊复诊，把结果写入数据库
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
	        			// System.out.println("uid:"+uid_i+", 加一："+(kk+1));
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
			 
			//删除初诊复诊
			 //1、删除初诊信息
			 for(RecommendEntity re : relist){
				 System.out.println("uid:"+re.getUid());
				 //1、查询这个用户都购买了哪些医生的项目
				 PageData pd2 = new PageData();
				 pd2.put("UID", re.getUid());
				 List<PageData> staffpdlist = recommendService.selectStaffByUid(pd2);//在预约表中进行查询
				 for(PageData staffpd:staffpdlist){
					 //查询该用户在该医生下购买了什么项目
					 pd2.put("STAFF_ID", staffpd.get("STAFF_ID"));
					 List<PageData> servicecostpdlist = recommendService.selectServicecostByUidAndStaffid(pd2);
					 for(PageData servicecostpd:servicecostpdlist){
						  String fuzhenpname = servicecostpd.getString("PNAME");
						  String chuzhenpname = fuzhenpname.replace("复诊", "初诊");
						  //查询初诊对应的servicecostid
						  pd2.put("PNAME", chuzhenpname);
						  List<PageData> servicecostidpdlist = recommendService.selectServicecostidByPname(pd2);
						  for(PageData  servicecostidpd: servicecostidpdlist){
							  List<Integer> pids = re.getPids();//推荐列表
								 Iterator<Integer> ListIterator = pids.iterator();  
								 while(ListIterator.hasNext()){  
								     Integer e = ListIterator.next();  
								     if(e==Integer.parseInt(servicecostidpd.getString("servicecost_id"))){  
								     ListIterator.remove();  //删除初诊推荐
								     }  
								 }
						  }
					 }
				 }
				 
			 }
			 List<RecommendEntity> list2= new ArrayList<RecommendEntity> ();//保存要删除的复诊项目
			 List<RecommendEntity> droplist= new ArrayList<RecommendEntity> ();//保存要删除的复诊项目
			 list2.addAll(relist);//复制
			 //2、删除复诊信息
			 //以下删除复诊挂号
			 Iterator<RecommendEntity> it= list2.iterator();//迭代器遍历
			 while(it.hasNext()){
				 RecommendEntity re2 = it.next();
			
				 //System.out.println("uid:"+re2.getUid());
				 //这里错了，应该用迭代器
				 int flag = 0;//未购买
				 List<Integer> pids = re2.getPids();//得到推荐了什么项目
				 Iterator<Integer> ListIterator = pids.iterator();  
				 while(ListIterator.hasNext()){
					 Integer pid = ListIterator.next(); //pid
					 //根据pid查询pname
					 PageData pd3 = new PageData();
					 pd3.put("SERVICECOST_ID", pid);
					List<PageData> servicecostpdlist = recommendService.selectPnameByServicecostid(pd3);
						for(PageData servicecostpd:servicecostpdlist){
							 String pname = servicecostpd.getString("PNAME");
							 if(pname.indexOf("复诊")!=-1){//如果这个pname包含复诊
								//查询该复诊项目对应的初诊项目的servicecost_id
								  String fuzhenpname = servicecostpd.getString("PNAME");
								 // System.out.println("复诊costid："+rs.getString(2)+"   复诊pname："+rs.getString(1));
								  String chuzhenpname = fuzhenpname.replace("复诊", "初诊");
								  //查询pname，servicecostid
								  ////////////////////////
								  String staff_id = servicecostpd.getString("STAFF_ID");
								  pd3.put("STAFF_ID", staff_id);
								  pd3.put("PNAME", chuzhenpname);
								  List<PageData> chuzhenlistpd = recommendService.selectservicecostidBypname(pd3);//根据pname和staffif查询
					              for(PageData chuzhenpd : chuzhenlistpd){
					            	chuzhenpd.put("UID", re2.getUid());
					            	/////
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
		
	//把结果写入数据库
			 for(RecommendEntity re : list2){//list2
					
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
	
}
}
