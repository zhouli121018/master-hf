package com.weipai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weipai.mapper.GmconfigMapper;
import com.weipai.model.Gmconfig;
import com.weipai.service.GmconfigService;

@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
public class GmconfigServiceImpl implements GmconfigService{
	@Resource
	GmconfigMapper gmconfigMapper;

	@Override
	public int insert(Gmconfig record) {
		// TODO Auto-generated method stub
		return gmconfigMapper.insert(record);
	}

	@Override
	public List<Gmconfig> selectAll() {
		// TODO Auto-generated method stub
		return gmconfigMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Gmconfig record) {
		// TODO Auto-generated method stub
		return gmconfigMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return gmconfigMapper.deleteByPrimaryKey(id);
	}
}
