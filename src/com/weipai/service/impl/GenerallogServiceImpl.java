package com.weipai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weipai.mapper.GenerallogMapper;
import com.weipai.model.Generallog;
import com.weipai.service.GenerallogService;

@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
public class GenerallogServiceImpl implements GenerallogService {

	@Resource
	GenerallogMapper generallogMapper;

	@Override
	public int insert(Generallog record) {
		// TODO Auto-generated method stub
		return generallogMapper.insert(record);
	}
	
	
	
}
