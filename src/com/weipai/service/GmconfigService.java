package com.weipai.service;

import java.util.List;

import com.weipai.model.Gmconfig;


public interface GmconfigService {
    List<Gmconfig> selectAll();
	
    int insert(Gmconfig record);
	
	int updateByPrimaryKey(Gmconfig record);
	
	int deleteByPrimaryKey(Integer id);
}
