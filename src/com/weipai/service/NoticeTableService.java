package com.weipai.service;

import java.util.List;
import java.util.Map;

import com.weipai.model.NoticeTable;

/**
 * 公告
 * @author luck
 *
 */
public interface NoticeTableService {
	
	
	int deleteByPrimaryKey(Integer id);

    int insert(NoticeTable record);

    int insertSelective(NoticeTable record);

    NoticeTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeTable record);

    int updateByPrimaryKey(NoticeTable record);
    
    List<NoticeTable> selectRecentlyObject(Map<String,Object> params);

}
