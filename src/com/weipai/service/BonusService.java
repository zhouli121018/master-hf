package com.weipai.service;
import java.util.List;
import java.util.Map;

import com.weipai.model.Bonus;
import com.weipai.model.BonusExt;
public interface BonusService {
	
	int insert(Bonus record);
	List<BonusExt> selectBonusLog(Map<String,Object> params);
}
