package cn.ncut.recommend.segmentation;

import java.util.ArrayList;
import java.util.List;

/*public class Weight {
	public List<Player> produceWeightCV(ArrayList<Player> playerlist) {
		List<Player> playerlistnew = new ArrayList<Player>();
		 double usercategoryid_sum = 0.0;
		 double proportion_sum = 0.0;
		 double attentionttime_sum = 0.0;
		 double ordercount_sum = 0.0;
		 double ordersum_sum = 0.0;
		 double avgordercount_sum = 0.0;
		 double avgordersum_sum = 0.0;
	     double storedcount_sum = 0.0;
		 double remain_sum = 0.0;
		 double refundcount_sum = 0.0;
		 double refundmoney_sum = 0.0;
		 //最大最小值
		 double usercategoryid_max;
		 double usercategoryid_min;
		 double proportion_max;
		 double proportion_min;
		 double attentionttime_max = 0.0;
		 double attentionttime_min = 0.0;
		 double ordercount_max;
		 double ordercount_min;
		 double ordersum_max;
		 double ordersum_min;
		 double avgordercount_max;
		 double avgordercount_min;
		 double avgordersum_max;
		 double avgordersum_min;
		 double storedcount_max;
		 double storedcount_min;
		 double remain_max;
		 double remain_min;
		 double refundcount_max;
		 double refundcount_min;
		 double refundmoney_max;
		 double refundmoney_min;
		
		 double usercategoryid_avg = 0.0;
		 double proportion_avg = 0.0;
		 double attentionttime_avg = 0.0;
		 double ordercount_avg = 0.0;
		 double ordersum_avg = 0.0;
		 double avgordercount_avg = 0.0;
		 double avgordersum_avg = 0.0;
		 double storedcount_avg = 0.0;
		 double remain_avg = 0.0;
		 double refundcount_avg = 0.0;
		 double refundmoney_avg = 0.0;
		 
		
		 double usercategoryid_ratio= 0.0;
		 double proportion_ratio = 0.0;
		 double attentionttime_ratio = 0.0;
		 double ordercount_ratio= 0.0;
		 double ordersum_ratio = 0.0;
		 double avgordercount_ratio = 0.0;
		 double avgordersum_ratio = 0.0;
		 double storedcount_ratio = 0.0;
		 double remain_ratio= 0.0;
		 double refundcount_ratio = 0.0;
		 double refundmoney_ratio = 0.0;
		 int i=1;
		 
		 usercategoryid_min = usercategoryid_max = playerlist.get(0).getUsercategoryid();
		 proportion_min = proportion_max = playerlist.get(0).getProportion();
		 attentionttime_min = attentionttime_max = playerlist.get(0).getAttentiontime();
		 ordercount_min = ordercount_max = playerlist.get(0).getOrdercount();
		 ordersum_min = ordersum_max = playerlist.get(0).getOrdersum();
		 avgordercount_min = avgordercount_max = playerlist.get(0).getAvgordercount();
		 avgordersum_min = avgordersum_max = playerlist.get(0).getAvgordersum();
		 storedcount_min = storedcount_max = playerlist.get(0).getStoredcount();
		 remain_min = remain_max = playerlist.get(0).getRemain();
		 refundcount_min = refundcount_max = playerlist.get(0).getRefundcount();
		 refundmoney_min = refundmoney_max = playerlist.get(0).getRefundmoney();
		  for (Player p : playerlist) {
			  //计算平均值
			  if(p.getKmeansfield1()>=max1)   // 判断最大值  
				  max1=p.getKmeansfield1();  
			  if(p.getKmeansfield1()<=min1)   // 判断最小值  
				  min1=p.getKmeansfield1();
			
			  if(p.getKmeansfield2()>=max2)   // 判断最大值  
				  max2=p.getKmeansfield2();  
			  if(p.getKmeansfield2()<=min2)   // 判断最小值  
				  min2=p.getKmeansfield2();
			  
			  if(p.getKmeansfield3()>=max3)   // 判断最大值  
				  max3=p.getKmeansfield3();  
			  if(p.getKmeansfield3()<=min3)   // 判断最小值  
				  min3=p.getKmeansfield3();
			  
			  if(p.getKmeansfield4()>=max4)   // 判断最大值  
				  max4=p.getKmeansfield4();  
			  if(p.getKmeansfield4()<=min4)   // 判断最小值  
				  min4=p.getKmeansfield4();
				  
			  
			  if(p.getKmeansfield5()>=max5)   // 判断最大值  
				  max5=p.getKmeansfield5();  
			  if(p.getKmeansfield5()<=min5)   // 判断最小值  
				  min5=p.getKmeansfield5();
			  
			  sum1 = sum1 + p.getKmeansfield1();
			  sum2 = sum2 + p.getKmeansfield2();
			  sum3 = sum3 + p.getKmeansfield3();
			  //sum4 = sum4 + p.getKmeansfield4();
			  //sum5 = sum5 + p.getKmeansfield5();
			  
			 i++;
	}
		  avg1 = sum1/i;
		  avg2 = sum2 /i;
		  avg3 = sum3/i;
		  //avg4 = sum4/i;
		  //avg5 = sum5/i;
		  for (Player p : playerlist) {
			  ratio1 = ratio1 + Math.pow(Math.abs(p.getKmeansfield1()-avg1), 2);
			  ratio2 = ratio2 + Math.pow(Math.abs(p.getKmeansfield2()-avg2), 2);
			  ratio3 = ratio3 + Math.pow(Math.abs(p.getKmeansfield3()-avg3), 2);
			  //ratio4 = ratio4 + Math.pow(Math.abs(p.getKmeansfield4()-avg4), 2);
			 // ratio5 = ratio5 + Math.pow(Math.abs(p.getKmeansfield5()-avg5), 2);
		  }
		  ratio1=Math.sqrt(ratio1/playerlist.size());//标准差
		  ratio2=Math.sqrt(ratio2/playerlist.size());
		  ratio3=Math.sqrt(ratio3/playerlist.size());
		 // ratio4=Math.sqrt(ratio4/playerlist.size());
		  //ratio5=Math.sqrt(ratio5/playerlist.size());
		 
		  
		  
		  ratio1 = Math.sqrt((max1-min1)*ratio1)/avg1;
		  ratio2 =  Math.sqrt((max2-min2)*ratio2)/avg2;
		  ratio3 =  Math.sqrt((max3-min3)*ratio3)/avg3;
		  //ratio4 = Math.sqrt((max4-min4)*ratio4)/avg4;
		  //ratio5 = Math.sqrt((max5-min5)*ratio5)/avg5;
		 
		  double ratiosum = ratio1 + ratio2 + ratio3;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 ;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 + ratio5;
		  ratio1 = ratio1/ratiosum;
		  ratio2 = ratio2/ratiosum;
		  ratio3 = ratio3/ratiosum;
		 // ratio4 = ratio4/ratiosum;
		 // ratio5 = ratio5/ratiosum;
		  System.out.println("ratio1;"+ratio1+"; ratio2:"+ratio2+"; ratio3:"+ratio3+";");
		  
		  Player weight = new Player();
		  weight.setKmeansfield1(ratio1);
		  weight.setKmeansfield2(ratio2);
		  weight.setKmeansfield3(ratio3);
		  //weight.setKmeansfield4(ratio4);
		 // weight.setKmeansfield5(ratio5);
		  //赋值
		  for (Player p : playerlist) {
			  p.setKmeansfield1(p.getKmeansfield1()*weight.getKmeansfield1());
			  p.setKmeansfield2(p.getKmeansfield2()*weight.getKmeansfield2());
			  p.setKmeansfield3(p.getKmeansfield3()*weight.getKmeansfield3());
			 // p.setKmeansfield4(p.getKmeansfield4()*weight.getKmeansfield4());
			 // p.setKmeansfield5(p.getKmeansfield5()*weight.getKmeansfield5());
			  playerlistnew.add(p);
		  }
		  
		return playerlistnew;
	}
	public List<Player> produceWeight(ArrayList<Player> playerlist) {
		List<Player> playerlistnew = new ArrayList<Player>();
		 double sum1 = 0.0;
		 double sum2 = 0.0;
		 double sum3 = 0.0;
		 //double sum4 = 0.0;
		// double sum5 = 0.0;
		 //最大最小值
		 double max1;
		 double min1;
		 double max2;
		 double min2;
		 double max3;
		 double min3;
		 //double max4;
		 //double min4;
		// double max5;
		 //double min5;
		 //double sum5 = 0.0;
		 double avg1 = 0.0;
		 double avg2 = 0.0;
		 double avg3 = 0.0;
		// double avg4 = 0.0;
		// double avg5 = 0.0;
		 double ratio1= 0.0;
		 double ratio2 = 0.0;
		 double ratio3= 0.0;
		 //double ratio4 = 0.0;
		 //double ratio5 = 0.0;
		 int i=1;
		 min1 = max1 = playerlist.get(0).getKmeansfield1();
		 min2 = max2 = playerlist.get(0).getKmeansfield2();
		 min3 = max3 = playerlist.get(0).getKmeansfield3();
		 //min4 = max4 = playerlist.get(0).getKmeansfield4();
		 //min5 = max5 = playerlist.get(0).getKmeansfield5();
		  for (Player p : playerlist) {
			  //计算平均值
			  if(p.getKmeansfield1()>=max1)   // 判断最大值  
				  max1=p.getKmeansfield1();  
			  if(p.getKmeansfield1()<=min1)   // 判断最小值  
				  min1=p.getKmeansfield1();
			
			  if(p.getKmeansfield2()>=max2)   // 判断最大值  
				  max2=p.getKmeansfield2();  
			  if(p.getKmeansfield2()<=min2)   // 判断最小值  
				  min2=p.getKmeansfield2();
			  
			  if(p.getKmeansfield3()>=max3)   // 判断最大值  
				  max3=p.getKmeansfield3();  
			  if(p.getKmeansfield3()<=min3)   // 判断最小值  
				  min3=p.getKmeansfield3();
			  
			  if(p.getKmeansfield4()>=max4)   // 判断最大值  
				  max4=p.getKmeansfield4();  
			  if(p.getKmeansfield4()<=min4)   // 判断最小值  
				  min4=p.getKmeansfield4();
				  
			  
			  if(p.getKmeansfield5()>=max5)   // 判断最大值  
				  max5=p.getKmeansfield5();  
			  if(p.getKmeansfield5()<=min5)   // 判断最小值  
				  min5=p.getKmeansfield5();
			  
			  sum1 = sum1 + p.getKmeansfield1();
			  sum2 = sum2 + p.getKmeansfield2();
			  sum3 = sum3 + p.getKmeansfield3();
			  //sum4 = sum4 + p.getKmeansfield4();
			  //sum5 = sum5 + p.getKmeansfield5();
			  
			 i++;
	}
		  avg1 = sum1/i;
		  avg2 = sum2 /i;
		  avg3 = sum3/i;
		  //avg4 = sum4/i;
		  //avg5 = sum5/i;
		  for (Player p : playerlist) {
			  ratio1 = ratio1 + Math.pow(Math.abs(p.getKmeansfield1()-avg1), 2);
			  ratio2 = ratio2 + Math.pow(Math.abs(p.getKmeansfield2()-avg2), 2);
			  ratio3 = ratio3 + Math.pow(Math.abs(p.getKmeansfield3()-avg3), 2);
			  //ratio4 = ratio4 + Math.pow(Math.abs(p.getKmeansfield4()-avg4), 2);
			 // ratio5 = ratio5 + Math.pow(Math.abs(p.getKmeansfield5()-avg5), 2);
		  }
		  ratio1=Math.sqrt(ratio1/playerlist.size())/avg1;//cv变异系数
		  ratio2=Math.sqrt(ratio2/playerlist.size())/avg2;
		  ratio3=Math.sqrt(ratio3/playerlist.size())/avg3;
		 // ratio4=Math.sqrt(ratio4/playerlist.size())/avg4;
		  //ratio5=Math.sqrt(ratio5/playerlist.size())/avg5;
		 
		  
		  
		 
		  double ratiosum = ratio1 + ratio2 + ratio3;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 ;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 + ratio5;
		  ratio1 = ratio1/ratiosum;
		  ratio2 = ratio2/ratiosum;
		  ratio3 = ratio3/ratiosum;
		 // ratio4 = ratio4/ratiosum;
		 // ratio5 = ratio5/ratiosum;
		  System.out.println("ratio1;"+ratio1+"; ratio2:"+ratio2+"; ratio3:"+ratio3+";");
		  
		  Player weight = new Player();
		  weight.setKmeansfield1(ratio1);
		  weight.setKmeansfield2(ratio2);
		  weight.setKmeansfield3(ratio3);
		  //weight.setKmeansfield4(ratio4);
		 // weight.setKmeansfield5(ratio5);
		  //赋值
		  for (Player p : playerlist) {
			  p.setKmeansfield1(p.getKmeansfield1()*weight.getKmeansfield1());
			  p.setKmeansfield2(p.getKmeansfield2()*weight.getKmeansfield2());
			  p.setKmeansfield3(p.getKmeansfield3()*weight.getKmeansfield3());
			 // p.setKmeansfield4(p.getKmeansfield4()*weight.getKmeansfield4());
			 // p.setKmeansfield5(p.getKmeansfield5()*weight.getKmeansfield5());
			  playerlistnew.add(p);
		  }
		  
		return playerlistnew;
	}
	public List<Player> produceWeightAvg(ArrayList<Player> playerlist) {
		List<Player> playerlistnew = new ArrayList<Player>();
		 double sum1 = 0.0;
		 double sum2 = 0.0;
		 double sum3 = 0.0;
		 //double sum4 = 0.0;
		// double sum5 = 0.0;
		 //最大最小值
		 double max1;
		 double min1;
		 double max2;
		 double min2;
		 double max3;
		 double min3;
		 //double max4;
		 //double min4;
		// double max5;
		 //double min5;
		 //double sum5 = 0.0;
		 double avg1 = 0.0;
		 double avg2 = 0.0;
		 double avg3 = 0.0;
		// double avg4 = 0.0;
		// double avg5 = 0.0;
		 double ratio1= 0.0;
		 double ratio2 = 0.0;
		 double ratio3= 0.0;
		 //double ratio4 = 0.0;
		 //double ratio5 = 0.0;
		 int i=1;
		 min1 = max1 = playerlist.get(0).getKmeansfield1();
		 min2 = max2 = playerlist.get(0).getKmeansfield2();
		 min3 = max3 = playerlist.get(0).getKmeansfield3();
		 //min4 = max4 = playerlist.get(0).getKmeansfield4();
		 //min5 = max5 = playerlist.get(0).getKmeansfield5();
		  for (Player p : playerlist) {
			  //计算平均值
			  if(p.getKmeansfield1()>=max1)   // 判断最大值  
				  max1=p.getKmeansfield1();  
			  if(p.getKmeansfield1()<=min1)   // 判断最小值  
				  min1=p.getKmeansfield1();
			
			  if(p.getKmeansfield2()>=max2)   // 判断最大值  
				  max2=p.getKmeansfield2();  
			  if(p.getKmeansfield2()<=min2)   // 判断最小值  
				  min2=p.getKmeansfield2();
			  
			  if(p.getKmeansfield3()>=max3)   // 判断最大值  
				  max3=p.getKmeansfield3();  
			  if(p.getKmeansfield3()<=min3)   // 判断最小值  
				  min3=p.getKmeansfield3();
			  
			  if(p.getKmeansfield4()>=max4)   // 判断最大值  
				  max4=p.getKmeansfield4();  
			  if(p.getKmeansfield4()<=min4)   // 判断最小值  
				  min4=p.getKmeansfield4();
				  
			  
			  if(p.getKmeansfield5()>=max5)   // 判断最大值  
				  max5=p.getKmeansfield5();  
			  if(p.getKmeansfield5()<=min5)   // 判断最小值  
				  min5=p.getKmeansfield5();
			  
			  sum1 = sum1 + p.getKmeansfield1();
			  sum2 = sum2 + p.getKmeansfield2();
			  sum3 = sum3 + p.getKmeansfield3();
			  //sum4 = sum4 + p.getKmeansfield4();
			  //sum5 = sum5 + p.getKmeansfield5();
			  
			 i++;
	}
		  avg1 = sum1/i;
		  avg2 = sum2 /i;
		  avg3 = sum3/i;
		  //avg4 = sum4/i;
		  //avg5 = sum5/i;
		
		  ratio1=(max1-min1)/avg1;//cv变异系数
		  ratio2=(max2-min2)/avg2;
		  ratio3=(max3-min3)/avg3;
		  
		  double max=(ratio1>ratio2?ratio1:ratio2)>ratio3?(ratio1>ratio2?ratio1:ratio2):ratio3;  
		       
		 // ratio4=Math.sqrt(ratio4/playerlist.size())/avg4;
		  //ratio5=Math.sqrt(ratio5/playerlist.size())/avg5;
		 
		  
		  
		 
		  double ratiosum = ratio1 + ratio2 + ratio3;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 ;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 + ratio5;
		  ratio1 = ratio1/max;
		  ratio2 = ratio2/max;
		  ratio3 = ratio3/max;
		 // ratio4 = ratio4/ratiosum;
		 // ratio5 = ratio5/ratiosum;
		  System.out.println("ratio1;"+ratio1+"; ratio2:"+ratio2+"; ratio3:"+ratio3+";");
		  
		  Player weight = new Player();
		  weight.setKmeansfield1(ratio1);
		  weight.setKmeansfield2(ratio2);
		  weight.setKmeansfield3(ratio3);
		  //weight.setKmeansfield4(ratio4);
		 // weight.setKmeansfield5(ratio5);
		  //赋值
		  for (Player p : playerlist) {
			  p.setKmeansfield1(p.getKmeansfield1()*weight.getKmeansfield1());
			  p.setKmeansfield2(p.getKmeansfield2()*weight.getKmeansfield2());
			  p.setKmeansfield3(p.getKmeansfield3()*weight.getKmeansfield3());
			 // p.setKmeansfield4(p.getKmeansfield4()*weight.getKmeansfield4());
			 // p.setKmeansfield5(p.getKmeansfield5()*weight.getKmeansfield5());
			  playerlistnew.add(p);
		  }
		  
		return playerlistnew;
	}
	public List<Player> produceWeightMAD(ArrayList<Player> playerlist) {
		List<Player> playerlistnew = new ArrayList<Player>();
		 double sum1 = 0.0;
		 double sum2 = 0.0;
		 double sum3 = 0.0;
		 double sum4 = 0.0;
		 double sum5 = 0.0;
		 double sum6 = 0.0;
		 double sum7 = 0.0;
		 //最大最小值
		 double max1;
		 double min1;
		 double max2;
		 double min2;
		 double max3;
		 double min3;
		 double max4;
		 double min4;
		 double max5;
		 double min5;
		 double max6;
		 double min6;
		 double max7;
		 double min7;
		 
		 double avg1 = 0.0;
		 double avg2 = 0.0;
		 double avg3 = 0.0;
		 double avg4 = 0.0;
		 double avg5 = 0.0;
		 double avg6 = 0.0;
		 double avg7 = 0.0;
		 
		 double ratio1= 0.0;
		 double ratio2 = 0.0;
		 double ratio3= 0.0;
		 double ratio4 = 0.0;
		 double ratio5 = 0.0;
		 double ratio6 = 0.0;
		 double ratio7 = 0.0;
		 int i=1;
		 min1 = max1 = playerlist.get(0).getKmeansfield1();
		 min2 = max2 = playerlist.get(0).getKmeansfield2();
		 min3 = max3 = playerlist.get(0).getKmeansfield3();
		 min4 = max4 = playerlist.get(0).getKmeansfield4();
		 min5 = max5 = playerlist.get(0).getKmeansfield5();
		// min6 = max6 = playerlist.get(0).getKmeansfield6();
		// min7 = max7 = playerlist.get(0).getKmeansfield7();
		  for (Player p : playerlist) {
			  //计算平均值
			  if(p.getKmeansfield1()>=max1)   // 判断最大值  
				  max1=p.getKmeansfield1();  
			  if(p.getKmeansfield1()<=min1)   // 判断最小值  
				  min1=p.getKmeansfield1();
			
			  if(p.getKmeansfield2()>=max2)   // 判断最大值  
				  max2=p.getKmeansfield2();  
			  if(p.getKmeansfield2()<=min2)   // 判断最小值  
				  min2=p.getKmeansfield2();
			  
			  if(p.getKmeansfield3()>=max3)   // 判断最大值  
				  max3=p.getKmeansfield3();  
			  if(p.getKmeansfield3()<=min3)   // 判断最小值  
				  min3=p.getKmeansfield3();
			  
			  if(p.getKmeansfield4()>=max4)   // 判断最大值  
				  max4=p.getKmeansfield4();  
			  if(p.getKmeansfield4()<=min4)   // 判断最小值  
				  min4=p.getKmeansfield4();
				  
			 
			  if(p.getKmeansfield5()>=max5)   // 判断最大值  
				  max5=p.getKmeansfield5();  
			  if(p.getKmeansfield5()<=min5)   // 判断最小值  
				  min5=p.getKmeansfield5();
			  
			   if(p.getKmeansfield6()>=max6)   // 判断最大值  
				  max6=p.getKmeansfield6();  
			  if(p.getKmeansfield6()<=min6)   // 判断最小值  
				  min6=p.getKmeansfield6();
			  
			  if(p.getKmeansfield7()>=max7)   // 判断最大值  
				  max7=p.getKmeansfield7();  
			  if(p.getKmeansfield7()<=min7)   // 判断最小值  
				  min7=p.getKmeansfield7();
			  
			  sum1 = sum1 + p.getKmeansfield1();
			  sum2 = sum2 + p.getKmeansfield2();
			  sum3 = sum3 + p.getKmeansfield3();
			  sum4 = sum4 + p.getKmeansfield4();
			  sum5 = sum5 + p.getKmeansfield5();
			  //sum6 = sum6 + p.getKmeansfield6();
			  //sum7 = sum7 + p.getKmeansfield7();
			  
			 i++;
	}
		  avg1 = sum1/i;
		  avg2 = sum2 /i;
		  avg3 = sum3/i;
		  avg4 = sum4/i;
		  avg5 = sum5/i;
		  //avg6 = sum6/i;
		  //avg7 = sum7/i;
		
		  for (Player p : playerlist) {
			  ratio1 = ratio1 + Math.abs(p.getKmeansfield1()-avg1);
			  ratio2 = ratio2 + Math.abs(p.getKmeansfield2()-avg2);
			  ratio3 = ratio3 + Math.abs(p.getKmeansfield3()-avg3);
			  ratio4 = ratio4 + Math.abs(p.getKmeansfield4()-avg4);
			  ratio5 = ratio5 + Math.abs(p.getKmeansfield5()-avg5);
			  //ratio6 = ratio6 + Math.abs(p.getKmeansfield6()-avg6);
			  //ratio7 = ratio7 + Math.abs(p.getKmeansfield7()-avg7);
			  
		  }
		  ratio1=ratio1/avg1;//cv变异系数
		  ratio2=ratio2/avg2;
		  ratio3=ratio3/avg3;
		  ratio4=ratio4/avg4;
		  ratio5=ratio5/avg5;
		  //ratio6=ratio6/avg6;
		  //ratio7=ratio7/avg7;
		 
		       
		 // ratio4=Math.sqrt(ratio4/playerlist.size())/avg4;
		  //ratio5=Math.sqrt(ratio5/playerlist.size())/avg5;
		 
		  
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 + ratio5 + ratio6 + ratio7;
		 
		  //double ratiosum = ratio1 + ratio2 + ratio3;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 ;
		  double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 + ratio5;
		  ratio1 = ratio1/ratiosum;
		  ratio2 = ratio2/ratiosum;
		  ratio3 = ratio3/ratiosum;
		  ratio4 = ratio4/ratiosum;
		  ratio5 = ratio5/ratiosum;
		  //ratio6 = ratio6/ratiosum;
		 // ratio7 = ratio7/ratiosum;
		  //System.out.println("ratio1;"+ratio1+"; ratio2:"+ratio2+"; ratio3:"+ratio3+";");
		 // System.out.println("ratio1;"+ratio1+"; ratio2:"+ratio2+"; ratio3:"+ratio3+";"+"ratio4:"+ratio4+";ratio5:"+ratio5+";ratio6:"+ratio6+";ratio7:"+ratio7);
		  Player weight = new Player();
		  weight.setKmeansfield1(ratio1);
		  weight.setKmeansfield2(ratio2);
		  weight.setKmeansfield3(ratio3);
		  weight.setKmeansfield4(ratio4);
		  weight.setKmeansfield5(ratio5);
		  //weight.setKmeansfield6(ratio6);
		  //weight.setKmeansfield7(ratio7);
		  //赋值
		  for (Player p : playerlist) {
			  p.setKmeansfield1(p.getKmeansfield1()*weight.getKmeansfield1());
			  p.setKmeansfield2(p.getKmeansfield2()*weight.getKmeansfield2());
			  p.setKmeansfield3(p.getKmeansfield3()*weight.getKmeansfield3());
			  p.setKmeansfield4(p.getKmeansfield4()*weight.getKmeansfield4());
			  p.setKmeansfield5(p.getKmeansfield5()*weight.getKmeansfield5());
			  //p.setKmeansfield6(p.getKmeansfield6()*weight.getKmeansfield6());
			  //p.setKmeansfield7(p.getKmeansfield7()*weight.getKmeansfield7());
			  playerlistnew.add(p);
		  }
		  
		return playerlistnew;
	}
	public List<Player> produceWeightR(ArrayList<Player> playerlist) {
		List<Player> playerlistnew = new ArrayList<Player>();
		 double sum1 = 0.0;
		 double sum2 = 0.0;
		 double sum3 = 0.0;
		 double sum4 = 0.0;
		 double sum5 = 0.0;
		 double sum6 = 0.0;
		 double sum7 = 0.0;
		 //最大最小值
		 double max1;
		 double min1;
		 double max2;
		 double min2;
		 double max3;
		 double min3;
		 double max4;
		 double min4;
		double max5;
		 double min5;
		 double max6;
		 double min6;
		 double max7;
		 double min7;
		 
		 double avg1 = 0.0;
		 double avg2 = 0.0;
		 double avg3 = 0.0;
		 double avg4 = 0.0;
		 double avg5 = 0.0;
		 double avg6 = 0.0;
		 double avg7 = 0.0;
		 
		 double ratio1= 0.0;
		 double ratio2 = 0.0;
		 double ratio3= 0.0;
		 double ratio4 = 0.0;
		 double ratio5 = 0.0;
		 double ratio6 = 0.0;
		 double ratio7 = 0.0;
		 int i=1;
		 min1 = max1 = playerlist.get(0).getKmeansfield1();
		 min2 = max2 = playerlist.get(0).getKmeansfield2();
		 min3 = max3 = playerlist.get(0).getKmeansfield3();
		 min4 = max4 = playerlist.get(0).getKmeansfield4();
		 min5 = max5 = playerlist.get(0).getKmeansfield5();
		 min6 = max6 = playerlist.get(0).getKmeansfield6();
		 min7 = max7 = playerlist.get(0).getKmeansfield7();
		  for (Player p : playerlist) {
			  //计算平均值
			  if(p.getKmeansfield1()>=max1)   // 判断最大值  
				  max1=p.getKmeansfield1();  
			  if(p.getKmeansfield1()<=min1)   // 判断最小值  
				  min1=p.getKmeansfield1();
			
			  if(p.getKmeansfield2()>=max2)   // 判断最大值  
				  max2=p.getKmeansfield2();  
			  if(p.getKmeansfield2()<=min2)   // 判断最小值  
				  min2=p.getKmeansfield2();
			  
			  if(p.getKmeansfield3()>=max3)   // 判断最大值  
				  max3=p.getKmeansfield3();  
			  if(p.getKmeansfield3()<=min3)   // 判断最小值  
				  min3=p.getKmeansfield3();
			  
			  if(p.getKmeansfield4()>=max4)   // 判断最大值  
				  max4=p.getKmeansfield4();  
			  if(p.getKmeansfield4()<=min4)   // 判断最小值  
				  min4=p.getKmeansfield4();
				  
			  
			  if(p.getKmeansfield5()>=max5)   // 判断最大值  
				  max5=p.getKmeansfield5();  
			  if(p.getKmeansfield5()<=min5)   // 判断最小值  
				  min5=p.getKmeansfield5();
			  
			  if(p.getKmeansfield6()>=max6)   // 判断最大值  
				  max6=p.getKmeansfield6();  
			  if(p.getKmeansfield6()<=min6)   // 判断最小值  
				  min6=p.getKmeansfield6();
			  
			  if(p.getKmeansfield7()>=max7)   // 判断最大值  
				  max7=p.getKmeansfield7();  
			  if(p.getKmeansfield7()<=min7)   // 判断最小值  
				  min7=p.getKmeansfield7();
			  
			  sum1 = sum1 + p.getKmeansfield1();
			  sum2 = sum2 + p.getKmeansfield2();
			  sum3 = sum3 + p.getKmeansfield3();
			  sum4 = sum4 + p.getKmeansfield4();
			  sum5 = sum5 + p.getKmeansfield5();
			  sum6 = sum6 + p.getKmeansfield6();
			  sum7 = sum7 + p.getKmeansfield7();
			  
			 i++;
	}
		  avg1 = sum1/i;
		  avg2 = sum2/i;
		  avg3 = sum3/i;
		  avg4 = sum4/i;
		  avg5 = sum5/i;
		  avg6 = sum6/i;
		  avg7 = sum7/i;
		
		 
		  ratio1=(max1-min1)/avg1;//range
		  ratio2=(max2-min2)/avg2;
		  ratio3=(max3-min3)/avg3;
		  ratio4=(max4-min4)/avg4;
		  ratio5=(max5-min5)/avg5;
		  ratio6=(max6-min6)/avg6;
		  ratio7=(max7-min7)/avg7;
		  
		 
		       
		 // ratio4=Math.sqrt(ratio4/playerlist.size())/avg4;
		  //ratio5=Math.sqrt(ratio5/playerlist.size())/avg5;
		 
		  
		  
		  double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 + ratio5 + ratio6 + ratio7;
		  //double ratiosum = ratio1 + ratio2 + ratio3;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 ;
		  //double ratiosum = ratio1 + ratio2 + ratio3 + ratio4 + ratio5;
		  ratio1 = ratio1/ratiosum;
		  ratio2 = ratio2/ratiosum;
		  ratio3 = ratio3/ratiosum;
		  ratio4 = ratio4/ratiosum;
		  ratio5 = ratio5/ratiosum;
		  ratio6 = ratio6/ratiosum;
		  ratio7 = ratio7/ratiosum;
		  System.out.println("ratio1;"+ratio1+"; ratio2:"+ratio2+"; ratio3:"+ratio3+";"+"ratio4:"+ratio4+";ratio5:"+ratio5+";ratio6:"+ratio6+";ratio7:"+ratio7);
		  
		  Player weight = new Player();
		  weight.setKmeansfield1(ratio1);
		  weight.setKmeansfield2(ratio2);
		  weight.setKmeansfield3(ratio3);
		  weight.setKmeansfield4(ratio4);
		  weight.setKmeansfield5(ratio5);
		  weight.setKmeansfield6(ratio6);
		  weight.setKmeansfield7(ratio7);
		  //赋值
		  for (Player p : playerlist) {
			  p.setKmeansfield1(p.getKmeansfield1()*weight.getKmeansfield1());
			  p.setKmeansfield2(p.getKmeansfield2()*weight.getKmeansfield2());
			  p.setKmeansfield3(p.getKmeansfield3()*weight.getKmeansfield3());
			  p.setKmeansfield4(p.getKmeansfield4()*weight.getKmeansfield4());
			  p.setKmeansfield5(p.getKmeansfield5()*weight.getKmeansfield5());
			  p.setKmeansfield6(p.getKmeansfield6()*weight.getKmeansfield6());
			  p.setKmeansfield7(p.getKmeansfield7()*weight.getKmeansfield7());
			  playerlistnew.add(p);
		  }
		  
		return playerlistnew;
	}
}*/
