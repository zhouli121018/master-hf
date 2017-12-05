package com.weipai.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weipai.mapper.BetlogLotMapper;
import com.weipai.mapper.PaylogMapper;
import com.weipai.mapper.ResultLotMapper;
import com.weipai.model.Paylog;
import com.weipai.model.PaylogExt;
import com.weipai.model.ResultLot;
import com.weipai.service.PaylogService;
import com.weipai.service.ResultLotService;
import com.weipai.utils.DateUtil;
@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
public class ResultLotServiceImpl implements ResultLotService{
	@Resource
	ResultLotMapper resultLotMapper;
	@Resource
	BetlogLotMapper betlogLotMapper;

	@Override
	public int insert(ResultLot record) {
		// TODO Auto-generated method stub
		return resultLotMapper.insert(record);
	}

	@Override
	public int openLotProcess(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return betlogLotMapper.openLot(map);
	}

	@Override
	public ResultLot selectBySerialNum(Integer serialNum) {
		// TODO Auto-generated method stub
		return resultLotMapper.selectBySerialNum(serialNum);
	}
}
