package cn.ncut.util;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.entity.Sequence;
import cn.ncut.service.system.sequence.SequenceService;


@Service("sequenceUtils")
public class SequenceUtils {	
	
	@Resource(name="sequenceService")
	private SequenceService sequenceService;
	
	/*private SequenceUtils(){};
	  
	private static final SequenceUtils su = new SequenceUtils();
	  
	public static synchronized SequenceUtils getInstance(){
		  
		 return su;
	}*/
	
	public synchronized String getNextId(String type) throws Exception{
		
		
		Sequence  ss = sequenceService.getSequenceByType(type);
		
		String timeNextId = null;
		
	/*	//现在的时间
		Date date = new Date(); //Tue Jul 05 13:10:40 CST 2016
		String time = DateUtil2.formatDate3(date);  //2016-07-05
		
		Date t = DateUtil2.strDateToDate(time);
		

		String time1 = DateUtil2.formatDate3(ss.getTime());*/
		
			
		int oldNextId = ss.getNextid();
		
		int newNextId = oldNextId +1;
		
		ss.setNextid(newNextId); 
		
		sequenceService.updateSequence(ss);

			
		if (type.equals("S")) {
			timeNextId =  String.format("%06d", ss.getNextid());
		}else{
			timeNextId =  String.format("%02d", ss.getNextid());
		}		
		return  timeNextId;
	}
}	

