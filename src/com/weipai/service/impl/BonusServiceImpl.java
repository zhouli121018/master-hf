package com.weipai.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weipai.mapper.BonusMapper;
import com.weipai.model.Bonus;
import com.weipai.model.BonusExt;
import com.weipai.service.BonusService;

@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
public class BonusServiceImpl implements BonusService {

	@Resource
	BonusMapper bonusMapper;

	@Override
	public int insert(Bonus record) {
		// TODO Auto-generated method stub
		return bonusMapper.insert(record);
	}

	@Override
	public List<BonusExt> selectBonusLog(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return bonusMapper.selectBonusLog(params);
	}
	
	
	
	

	
	
	
}
