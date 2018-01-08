package cn.ncut.service.system.sequence;

import cn.ncut.entity.Sequence;



public interface SequenceService {

	public Sequence getSequenceByType(String type) throws Exception;
	
	public void updateSequence(Sequence sequence) throws Exception;

}
