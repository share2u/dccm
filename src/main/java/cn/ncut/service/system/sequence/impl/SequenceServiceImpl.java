package cn.ncut.service.system.sequence.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Sequence;
import cn.ncut.service.system.sequence.SequenceService;


@Service("sequenceService")
public class SequenceServiceImpl implements SequenceService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查找一条记录
	@Override
	public Sequence getSequenceByType(String type) throws Exception {
		return (Sequence) dao.findForObject("SequenceMapper.getSequenceByType",type);
		
	}

	//修改一条记录
	@Override
	public void updateSequence(Sequence sequence) throws Exception {
		dao.update("SequenceMapper.updateSequence", sequence);
	}
}
