package com.weipai.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.weipai.model.Paylog;
import com.weipai.model.PaylogExt;
import com.weipai.model.ResultLot;

public interface ResultLotService {
	//查询所有，本周，上周，本月，上月的充值数据
	int insert(ResultLot record);
	public int openLotProcess(Map<String ,Object> map);
	ResultLot selectBySerialNum(Integer serialNum);
}
