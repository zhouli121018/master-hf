package com.weipai.service;

import java.util.List;
import java.util.Map;

import com.weipai.model.ExchangeLotExt;

public interface ExchangeLotService {
	public List<ExchangeLotExt> selectByMidTime(Map<String, Object> params);
	int selectCount(Map<String,Object> params);
}
