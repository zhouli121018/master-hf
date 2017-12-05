package com.weipai.mapper;

import java.util.List;

import com.weipai.model.Gmconfig;

public interface GmconfigMapper {
    List<Gmconfig> selectAll();
	
    int insert(Gmconfig record);
	
	int updateByPrimaryKey(Gmconfig record);
	
	int deleteByPrimaryKey(Integer id);
  
}