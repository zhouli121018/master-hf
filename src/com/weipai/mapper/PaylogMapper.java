package com.weipai.mapper;

import com.weipai.model.Paylog;
import com.weipai.model.PaylogExample;
import com.weipai.model.PaylogExt;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PaylogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    int insert(Paylog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    int insertSelective(Paylog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    List<Paylog> selectByExample(PaylogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    Paylog selectByPrimaryKey(Integer id);
    
    List<Paylog> selectByManagerId(Map<String,Object> params);
    double selectTixianSum(Map<String,Object> params);
    
    List<Paylog> selectUserPaySum(Map<String,Object> params);
    
    

    List<PaylogExt> selectByMidTime(Map<String,Object> params);
    
    int selectByMidTimeCount(Map<String,Object> params);
    int selectByMidTimeMoney(Map<String,Object> params);
    double selectByMidTimeBonus(Map<String,Object> params);
    
    List<PaylogExt> selectByAllTime(Map<String,Object> params);
    
    int selectByAllTimeCount(Map<String,Object> params);
    int selectByAllTimeMoney(Map<String,Object> params);
    
    int selectSumByManagerId(Map<String,Object> params);
    
    int sumByManagerId(Map<String,Object> params);
    
    double sumSubByManagerId(Map<String,Object> params);
    
    int sumSub2ByManagerId(Map<String,Object> params);
    
    int sumSub3ByManagerId(Map<String,Object> params);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Paylog record, @Param("example") PaylogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Paylog record, @Param("example") PaylogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Paylog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paylog
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Paylog record);

	void tixianUpdate(Map<String, Object> params);
	void tixianDone(Map<String, Object> params);

	double remainByManagerId(Map<String, Object> params);
}