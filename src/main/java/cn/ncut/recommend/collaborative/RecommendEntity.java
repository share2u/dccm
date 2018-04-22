package cn.ncut.recommend.collaborative;
import java.util.List;


public class RecommendEntity {
	public int uid;//用户编号
	public List<Integer> pids;//放入推荐的项目编号
	

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public List<Integer> getPids() {
		return pids;
	}
	public void setPids(List<Integer> pids) {
		this.pids = pids;
	}
	
}