package com.weipai.mapper;

import com.weipai.model.ResultLot;

public interface ResultLotMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result_lot
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result_lot
     *
     * @mbggenerated
     */
    int insert(ResultLot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result_lot
     *
     * @mbggenerated
     */
    int insertSelective(ResultLot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result_lot
     *
     * @mbggenerated
     */
    ResultLot selectByPrimaryKey(Integer id);
    ResultLot selectBySerialNum(Integer serialNum);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result_lot
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ResultLot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result_lot
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ResultLot record);
}