package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.ncut.recommend.collaborative.ReadFile;
import cn.ncut.service.finance.serviceall.ServiceCostManager;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.util.PageData;

public class TestFilter {
	 @Test
	public void Filter() throws Exception{
	String servicecost_ids = "3033,4074,5667,328,3070,3044,3054,3029";
	String[] aa = servicecost_ids.split(",");
	List<Integer> pids = new ArrayList<Integer>();
	for(int i=0;i<aa.length;i++){
		pids.add(Integer.parseInt(aa[i]));
	}
	 PageData pd2 = new PageData();
	 pd2.put("UID", 109);
	
			
					 Iterator<Integer> ListIterator = pids.iterator();  
					 while(ListIterator.hasNext()){  
					     Integer e = ListIterator.next();  
					     if(e==3029){  
					     ListIterator.remove();  //删除初诊推荐
					     }  
					 }
					 
					 for(Integer p:pids){
						 System.out.println(p);
					 }
			  }
	
	


	}

	
