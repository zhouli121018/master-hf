package com.weipai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weipai.mapper.RoomcardlogMapper;
import com.weipai.model.Roomcardlog;
import com.weipai.service.RoomcardlogService;
@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
public class RoomcardlogServiceImpl implements RoomcardlogService{
	@Resource
	RoomcardlogMapper roomcardlogMapper;

	@Override
	public int insert(Roomcardlog record) {
		// TODO Auto-generated method stub
		return roomcardlogMapper.insert(record);
	}

}
