package cn.ncut.recommend.kmeans;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.recommend.*;

/** 
 *  
 * @author 阿飞哥 
 *  
 */  
public class Kmeans<T> {  
  
    /** 
     * 所有数据列表 
     */  
    private List<T> players = new ArrayList<T>();  
  
    /** 
     * 数据类别 
     */  
    private Class<T> classT;  
  
    /** 
     * 初始化列表 
     */  
    private List<T> initPlayers;  
  
    /** 
     * 需要纳入kmeans算法的属性名称 
     */  
    private List<String> fieldNames = new ArrayList<String>();  
  
    /** 
     * 分类数 
     */  
    private int k = 1;  
  
    public Kmeans() {  
  
    }  
  
    /** 
     * 初始化列表 
     *  
     * @param list 
     * @param k 
     */  
    public Kmeans(List<T> list,int k) {  //List<List<T>> canopylist
        this.players = list;  
        this.k = k;  
        T t = list.get(0);  
        this.classT = (Class<T>) t.getClass();  
        Field[] fields = this.classT.getDeclaredFields();  
        for (int i = 0; i < fields.length; i++) {  
            Annotation kmeansAnnotation = fields[i]  
                    .getAnnotation(KmeansField.class);  
            if (kmeansAnnotation != null) {  
                fieldNames.add(fields[i].getName());  
            }  
  
        }  
  
        initPlayers = new ArrayList<T>();  //初始簇中心的点
          ArrayList<T> maxPlayers= new ArrayList<T>();//记录距离大的点
      //使用贪心算法选取初始聚类中心
        initPlayers.add(list.get(0));//选取第一个点
        for(int i1 = 1; i1 < k; i1++) {//选取其他聚类中心点
        	T maxp =  list.get(0);//记录最大的点
        	for (int j = 0; j<list.size();j++){//贪心算法优化初始聚类中心
        		
        		int k1 = initPlayers.size();//已有的聚类中心个数
        		double distance = 0.0;//计算新的点与已有的聚类中心点的距离之和
        		double distanceMax = 0.0;
        		for(int time=0;time<k1;time++){
        			distance += distance(initPlayers.get(time), list.get(j));
        			distanceMax += distance(initPlayers.get(time), maxp);
        		}
        		
        		if(distance>=distanceMax){
        			maxp = list.get(j);
        			maxPlayers.add(maxp);//把距离大的点加入进去
        		}
        	}
        	int maxPlayerSize = maxPlayers.size();
        	initPlayers.add(maxPlayers.get(maxPlayerSize-1));//把次大的点放入
        	maxPlayers.clear();//清空maxPlayers
        }
        
/*   for (int i = 0; i < k; i++) {  
        	//取出每个canopy的第一个点
        	initPlayers.add(list.get(i));//canopylist.get(i).get(0)
            //initPlayers.add(players.get(i));  //把前两个点弄成了初始簇中心
        }  
        initPlayers.add(list.get(119));
        initPlayers.add(list.get(122));*/
    }  
  
    public List<T>[] computWeight(List<Double> cvlist) {  
        List<T>[] results = new ArrayList[k];  
  int j1= 0;
        boolean centerchange = true;  
        while (centerchange) {  //迭代
        	j1++;
        	System.out.println("迭代次数： "+j1);
            centerchange = false;  
            for (int i = 0; i < k; i++) {  
                results[i] = new ArrayList<T>();  
            }  
            for (int i = 0; i < players.size(); i++) {  
                T p = players.get(i);  
                double[] dists = new double[k];  
                for (int j = 0; j < initPlayers.size(); j++) {  //initPlayers初始中心点,取了前三个点
                    T initP =  initPlayers.get(j);  //p的中心
                    /* 计算距离 */  
                    double dist = distanceWeight(initP, p,cvlist);  //聚类中心和p的距离
                    dists[j] = dist;  
                }  
  
                int dist_index = computOrder(dists);  
                results[dist_index].add(p);  
            }  
  
            for (int i = 0; i < k; i++) {  
                T player_new = findNewCenter(results[i]);  
                T player_old = initPlayers.get(i);  
                if (!IsPlayerEqual(player_new, player_old)) {  //聚类中心变没变
                    centerchange = true;  
                    initPlayers.set(i, player_new);  
                }  
  
            }  
  
        }
        //评价指标
        //紧密性cp,与自己簇中心的平均距离
        double distanceCP= 0.0;
        double distanceSP= 0.0;
        double distanceIP= 0.0;
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
          for(int j = 0;j<results[i].size();j++){//遍历每一个簇
        	 
        	   distanceCP +=distance(initPlayers.get(i),results[i].get(j));
        		 
        	  
         }
        	
        }
        distanceCP= distanceCP/players.size();
        System.out.println("CP: "+distanceCP);
        //间隔性SP 聚类中心两两距离的平均
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
        	for(int j = 0;j<initPlayers.size();j++){
        		if(i!=j){
        			distanceSP +=distance(initPlayers.get(i),initPlayers.get(j));
        		}
        	}
        }
        distanceSP = distanceSP / (initPlayers.size()*(initPlayers.size()-1));
        System.out.println("SP: "+distanceSP);
  //每一个点离其他最近的聚类中心的距离的平均
        //远离性interval，与其他聚类中心的平均距离
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
        	for(int j = 0;j<results[i].size();j++){//遍历簇中的每一个点
        		for(int k = 0;k<initPlayers.size();k++){
        			if(i!=k){
        			distanceIP +=distance(initPlayers.get(k),results[i].get(j));
        			}
        		 }
        			
        	   }
        	}
        distanceIP = distanceIP / (2*players.size());
        System.out.println("IN: "+distanceIP);
  
        return results;  
    }  
    
    public List<T>[] comput() {  
        List<T>[] results = new ArrayList[k];  
  int j1= 0;
        boolean centerchange = true;  
        while (centerchange) {  //迭代
        	j1++;
        	System.out.println("迭代次数： "+j1);
            centerchange = false;  
            for (int i = 0; i < k; i++) {  
                results[i] = new ArrayList<T>();  
            }  
            for (int i = 0; i < players.size(); i++) {  
                T p = players.get(i);  
                double[] dists = new double[k];  
                for (int j = 0; j < initPlayers.size(); j++) {  //initPlayers初始中心点
                    T initP =  initPlayers.get(j);  //p的中心
                    /* 计算距离 */  
                    double dist = distance(initP, p);  //聚类中心和p的距离
                    dists[j] = dist;  
                }  
  
                int dist_index = computOrder(dists);  
                results[dist_index].add(p); //把点加入最近的聚类中心 
            }  
  
            for (int i = 0; i < k; i++) {  
                T player_new = findNewCenter(results[i]);  //计算新聚类中心
                T player_old = initPlayers.get(i);  
                if (!IsPlayerEqual(player_new, player_old)) {  //聚类中心变没变
                    centerchange = true;  
                    initPlayers.set(i, player_new);  
                }  
  
            }  
  
        }  
        //评价指标
        //紧密性cp,与自己簇中心的平均距离
        double distanceCP= 0.0;
        double distanceSP= 0.0;
        double distanceIP= 0.0;
        double sse = 0.0;
        //计算sse
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
            for(int j = 0;j<results[i].size();j++){//遍历每一个簇
          	 sse +=Math.pow(distance(initPlayers.get(i),results[i].get(j)), 2);
          	   
          		 
          	  
           }
          	
          }
          distanceCP= distanceCP/players.size();
          System.out.println("SSE: "+sse);
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
          for(int j = 0;j<results[i].size();j++){//遍历每一个簇
        	 
        	   distanceCP +=distance(initPlayers.get(i),results[i].get(j));
        		 
        	  
         }
        	
        }
        distanceCP= distanceCP/players.size();
        System.out.print("CP: "+distanceCP);
        //间隔性SP 聚类中心两两距离的平均
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
        	for(int j = 0;j<initPlayers.size();j++){
        		if(i!=j){
        			distanceSP +=distance(initPlayers.get(i),initPlayers.get(j));
        		}
        	}
        }
        distanceSP = distanceSP / (initPlayers.size()*(initPlayers.size()-1));
        System.out.println("SP: "+distanceSP);
  //每一个点离其他最近的聚类中心的距离的平均
        //远离性interval，与其他聚类中心的平均距离
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
        	for(int j = 0;j<results[i].size();j++){//遍历簇中的每一个点
        		for(int k = 0;k<initPlayers.size();k++){
        			if(i!=k){
        			distanceIP +=distance(initPlayers.get(k),results[i].get(j));
        			}
        		 }
        			
        	   }
        	}
        distanceIP = distanceIP / (2*players.size());
        System.out.println("IP: "+distanceIP);
        
        return results;  
    }  
  
    public List<T>[] computAndEvaluate() {  
        List<T>[] results = new ArrayList[k];  
  int j1= 0;
        boolean centerchange = true;  
        while (centerchange) {  //迭代
        	j1++;
        	System.out.println("迭代次数： "+j1);
            centerchange = false;  
            for (int i = 0; i < k; i++) {  
                results[i] = new ArrayList<T>();  
            }  
            for (int i = 0; i < players.size(); i++) {  
                T p = players.get(i);  
                double[] dists = new double[k];  
                for (int j = 0; j < initPlayers.size(); j++) {  //initPlayers初始中心点
                    T initP =  initPlayers.get(j);  //p的中心
                    /* 计算距离 */  
                    double dist = distance(initP, p);  //聚类中心和p的距离
                    dists[j] = dist;  
                }  
  
                int dist_index = computOrder(dists);  
                results[dist_index].add(p); //把点加入最近的聚类中心 
            }  
  
            for (int i = 0; i < k; i++) {  
                T player_new = findNewCenter(results[i]);  //计算新聚类中心
                T player_old = initPlayers.get(i);  
                if (!IsPlayerEqual(player_new, player_old)) {  //聚类中心变没变
                    centerchange = true;  
                    initPlayers.set(i, player_new); //中心点 
                }  
  
            }  
  
        }  
        //评价指标
        //紧密性cp,与自己簇中心的平均距离
        double distanceCP= 0.0;
        double distanceSP= 0.0;
        double distanceIP= 0.0;
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
          for(int j = 0;j<results[i].size();j++){//遍历每一个簇
        	 
        	   distanceCP +=distance(initPlayers.get(i),results[i].get(j));
        		 
        	  
         }
        	
        }
        distanceCP= distanceCP/players.size();
        System.out.print("CP: "+distanceCP);
        //间隔性SP 聚类中心两两距离的平均
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
        	for(int j = 0;j<initPlayers.size();j++){
        		if(i!=j){
        			distanceSP +=distance(initPlayers.get(i),initPlayers.get(j));
        		}
        	}
        }
        distanceSP = distanceSP / (initPlayers.size()*(initPlayers.size()-1));
        System.out.print("SP: "+distanceSP);
  //每一个点离其他最近的聚类中心的距离的平均
        //远离性interval，与其他聚类中心的平均距离
        for(int i = 0;i<initPlayers.size();i++){//簇的个数
        	for(int j = 0;j<results[i].size();j++){//遍历簇中的每一个点
        		for(int k = 0;k<initPlayers.size();k++){
        			if(i!=k){
        			distanceIP +=distance(initPlayers.get(i),initPlayers.get(j));
        			}
        		 }
        			
        	   }
        	}
        distanceIP = distanceIP / (2*players.size());
        System.out.print("IP: "+distanceIP);
        return results; //结果 
    }  
  
    /** 
     * 比较是否两个对象是否属性一致 
     *  
     * @param p1 
     * @param p2 
     * @return 
     */  
    public boolean IsPlayerEqual(T p1, T p2) {  
        if (p1 == p2) {  
            return true;  
        }  
        if (p1 == null || p2 == null) {  
            return false;  
        }  
  
          
  
        boolean flag = true;  
        try {  
            for (int i = 0; i < fieldNames.size(); i++) {  
                String fieldName=fieldNames.get(i);  
                String getName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);                 
                Object value1 = invokeMethod(p1,getName,null);  
                Object value2 = invokeMethod(p2,getName,null);  
                if (!value1.equals(value2)) {  
                    flag = false;  
                    break;  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            flag = false;  
        }  
  
        return flag;  
    }  
  
    /** 
     * 得到新聚类中心对象 
     *  
     * @param ps 
     * @return 
     */  
    public T findNewCenter(List<T> ps) {  
        try {  
            T t = classT.newInstance();  
            if (ps == null || ps.size() == 0) {  
                return t;  
            }  
  
            double[] ds = new double[fieldNames.size()];  
            for (T vo : ps) {  
                for (int i = 0; i < fieldNames.size(); i++) {  
                    String fieldName=fieldNames.get(i);  
                    String getName = "get"  
                            + fieldName.substring(0, 1).toUpperCase()  
                            + fieldName.substring(1);  
                    Object obj=invokeMethod(vo,getName,null);  
                    Double fv=(obj==null?0:Double.parseDouble(obj+""));  
                    ds[i] += fv;  
                }  
  
            }  
  
            for (int i = 0; i < fieldNames.size(); i++) {  
                ds[i] = ds[i] / ps.size();  
                String fieldName = fieldNames.get(i);  
                  
                /* 给对象设值 */  
                String setName = "set"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);  
  
                invokeMethod(t,setName,new Class[]{double.class},ds[i]);  
  
            }  
  
            return t;  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return null;  
  
    }  
  
    /** 
     * 得到最短距离，并返回最短距离索引 
     *  
     * @param dists 
     * @return 
     */  
    public int computOrder(double[] dists) {  
        double min = 0;  
        int index = 0;  
        for (int i = 0; i < dists.length - 1; i++) {  
            double dist0 = dists[i];  
            if (i == 0) {  
                min = dist0;  
                index = 0;  
            }  
            double dist1 = dists[i + 1];  
            if (min > dist1) {  
                min = dist1;  
                index = i + 1;  
            }  
        }  
  
        return index;  
    }  
  
    /** 
     * 计算距离（相似性） 采用欧几里得算法 
     *  
     * @param p0 
     * @param p1 
     * @return 
     */  
    public double distance(T p0, T p1) {  
        double dis = 0;  
        try {  
  
            for (int i = 0; i < fieldNames.size(); i++) {  
                String fieldName = fieldNames.get(i);  
                String getName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);  
                //System.out.println(getName);//getGoal getAssists getBackboard getSteals
                  
                Double field0Value=Double.parseDouble(invokeMethod(p0,getName,null)+"");  
                //System.out.println(field0Value);
                Double field1Value=Double.parseDouble(invokeMethod(p1,getName,null)+"");  
                dis += Math.pow(field0Value - field1Value, 2);//计算距离
                //System.out.println(field1Value);
            }  
          
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return Math.sqrt(dis);  
  
    }  
    
    public double distanceWeight(T p0, T p1,List<Double> cvlist) {  //计算权重距离
        double dis = 0;  
        try {  
  
            for (int i = 0; i < fieldNames.size(); i++) {  
                String fieldName = fieldNames.get(i);  
                String getName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);  
                //System.out.println(getName);//getGoal getAssists getBackboard getSteals
                  
                Double field0Value=Double.parseDouble(invokeMethod(p0,getName,null)+"");  
               // System.out.println(field0Value);
                Double field1Value=Double.parseDouble(invokeMethod(p1,getName,null)+"");  
                dis += cvlist.get(i)*Math.pow(field0Value - field1Value, 2);//计算距离
                //System.out.println(field1Value);
            }  
          
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return Math.sqrt(dis);  
  
    } 
      
    /*------公共方法-----*/  
    public Object invokeMethod(Object owner, String methodName,Class[] argsClass,  
            Object... args) {  
        Class ownerClass = owner.getClass();  
        try {  
            Method method=ownerClass.getDeclaredMethod(methodName,argsClass);  
            return method.invoke(owner, args);  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (NoSuchMethodException e) {  
            e.printStackTrace();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
  
        return null;  
    }  
  
}  