package com.weipai.mapper;

import java.util.List;
import java.util.Map;

import com.weipai.model.Bonus;
import com.weipai.model.BonusExt;
import com.weipai.model.PaylogExt;

public interface BonusMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bonus
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bonus
     *
     * @mbggenerated
     */
    int insert(Bonus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bonus
     *
     * @mbggenerated
     */
    int insertSelective(Bonus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bonus
     *
     * @mbggenerated
     */
    Bonus selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bonus
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Bonus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bonus
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Bonus record);
    
    List<BonusExt> selectBonusLog(Map<String,Object> params);
    
    
    
}