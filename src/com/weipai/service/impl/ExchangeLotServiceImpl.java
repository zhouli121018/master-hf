package com.weipai.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weipai.mapper.ExchangeLotMapper;
import com.weipai.model.ExchangeLotExt;
import com.weipai.service.ExchangeLotService;
@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
public class ExchangeLotServiceImpl implements ExchangeLotService{
	@Resource
	ExchangeLotMapper exchangeLotMapper;

	@Override
	public List<ExchangeLotExt> selectByMidTime(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return  exchangeLotMapper.selectByMidTime(params);
	}

	@Override
	public int selectCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return exchangeLotMapper.selectCount(params);
	}

}
