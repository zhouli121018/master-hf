package com.weipai.mapper;

import com.weipai.model.Roomcardlog;

public interface RoomcardlogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomcardlog
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomcardlog
     *
     * @mbggenerated
     */
    int insert(Roomcardlog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomcardlog
     *
     * @mbggenerated
     */
    int insertSelective(Roomcardlog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomcardlog
     *
     * @mbggenerated
     */
    Roomcardlog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomcardlog
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Roomcardlog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomcardlog
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Roomcardlog record);
}