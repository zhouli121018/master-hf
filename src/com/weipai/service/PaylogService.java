package com.weipai.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.weipai.model.Paylog;
import com.weipai.model.PaylogExt;

public interface PaylogService {
	//查询所有，本周，上周，本月，上月的充值数据
	public List<Paylog> selectByManagerId(int managerId,int searchType,int payType,int start,int limit);
	
	public List<PaylogExt> selectByMidTime(int managerId, int searchType,String levelStr,int uuid, int pid,int start, int limit,Date startTime,Date endTime,int gameId);
	
	
	public List<Paylog> selectUserPaySum(int managerId, int searchType, int start, int limit,Date startTime,Date endTime);
	
	
	public int selectByMidTimeCount(int managerId, int searchType,String levelStr, int uuid,int pid,int start, int limit,Date startTime,Date endTime,int gameId);
	public int selectByMidTimeMoney(int managerId, int searchType,String levelStr, int uuid,int pid,int start, int limit,Date startTime,Date endTime,int gameId);
	public double selectByMidTimeBonus(int managerId, int searchType,String levelStr, int uuid,int pid,int start, int limit,Date startTime,Date endTime,int gameId);
    
//	public List<PaylogExt> selectByAllTime(int searchType, int start, int limit,Date startTime,Date endTime,int pid,int uuid);
	
//	public int selectByAllTimeCount(int searchType, int start, int limit,Date startTime,Date endTime,int pid,int uuid);
//	public int selectByAllTimeMoney(int searchType, int start, int limit,Date startTime,Date endTime,int pid,int uuid);
	//统计总的数据，分页之用
	public int selectSumByManagerId(int managerId,int searchType,int payType);
	//汇总充值数据
	public int sumByManagerId(int managerId);
	
	public double remainByManagerId(int managerId);
	//按自定义时间汇总充值数据
	public int sumByTime(int managerId,Date start,Date end);
	public double  selectTixianSum(int managerId,int searchType);
	public Paylog selectByPrimaryKey(int id);
	//汇总下面第二级充值数据
	public double sumSubByManagerId(int managerId,String levelStr,double rebate);

		
	//发起提现
	public int insertPaylog(Paylog paylog);
	public void tixianUpdate(Integer managerId,int status);
	public void tixianDone(Integer id,int status);

	int sumByManagerId2(int managerId, int sumType);

	double sumSubByManagerId2(int managerId, int sumType, String levelStr, double rebate);
	
	
}
