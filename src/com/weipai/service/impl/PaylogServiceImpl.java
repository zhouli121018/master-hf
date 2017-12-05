package com.weipai.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weipai.mapper.PaylogMapper;
import com.weipai.model.Paylog;
import com.weipai.model.PaylogExt;
import com.weipai.service.PaylogService;
import com.weipai.utils.DateUtil;
@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
public class PaylogServiceImpl implements PaylogService{
	@Resource
	PaylogMapper paylogMapper;
	@Override
	public List<Paylog> selectByManagerId(int managerId, int searchType,int payType, int start, int limit) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
		Date startTime = null;
		Date endTime = null;
		if(searchType==0){//查询全部
			startTime = new Date(0L);
			endTime = new Date((new java.util.Date()).getTime()+86400000);
		}else if(searchType==1){//本周
			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
		}else if(searchType==2){//上周
			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
		}else if(searchType==3){//本月
			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
		}else if(searchType==4){//上月
			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
		}else if(searchType==5){//今天
			startTime = new Date(DateUtil.getTodayStart().getTime());
			endTime = new Date(DateUtil.getTodayEnd().getTime());
		}else if(searchType==6){//昨天
			startTime = new Date(DateUtil.getYesterdayStart().getTime());
			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>2)
		params.put("managerId", managerId);
		params.put("startNum", start);
		params.put("payType", payType);
		params.put("pageNumber", limit);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return paylogMapper.selectByManagerId(params);
	}
	
	
	public List<PaylogExt> selectByMidTime(int managerId, int searchType, String levelStr,int uuid,int pid,int start, int limit,Date startTime,Date endTime,int gameId) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
		if(startTime==null&&endTime==null){
		if(searchType==0){//查询全部
			startTime = new Date(0L);
			endTime = new Date((new java.util.Date()).getTime()+86400000);
		}else if(searchType==1){//本周
			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
		}else if(searchType==2){//上周
			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
		}else if(searchType==3){//本月
			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
		}else if(searchType==4){//上月
			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
		}else if(searchType==5){//今天
			startTime = new Date(DateUtil.getTodayStart().getTime());
			endTime = new Date(DateUtil.getTodayEnd().getTime());
		}else if(searchType==6){//昨天
			startTime = new Date(DateUtil.getYesterdayStart().getTime());
			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
		}
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>0)
		params.put("managerId", managerId);
		params.put("startNum", start);
		params.put("pageNumber", limit);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("levelStr", levelStr);
		if(gameId!=0){
			params.put("gameId", gameId);
		}
		if(pid>0)
		params.put("pid", pid);
		if(uuid>0)
			params.put("uuid", uuid);
		return paylogMapper.selectByMidTime(params);
	}
	
	
	public int selectByMidTimeCount(int managerId, int searchType,String levelStr, int uuid,int pid,int start, int limit,Date startTime,Date endTime,int gameId) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
		if(startTime==null&&endTime==null){
		if(searchType==0){//查询全部
			startTime = new Date(0L);
			endTime = new Date((new java.util.Date()).getTime()+86400000);
		}else if(searchType==1){//本周
			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
		}else if(searchType==2){//上周
			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
		}else if(searchType==3){//本月
			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
		}else if(searchType==4){//上月
			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
		}else if(searchType==5){//今天
			startTime = new Date(DateUtil.getTodayStart().getTime());
			endTime = new Date(DateUtil.getTodayEnd().getTime());
		}else if(searchType==6){//昨天
			startTime = new Date(DateUtil.getYesterdayStart().getTime());
			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
		}
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>0)
		params.put("managerId", managerId);
		params.put("startNum", start);
		params.put("pageNumber", limit);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("levelStr", levelStr);
		if(gameId!=0){
			params.put("gameId", gameId);
		}
		if(pid>0)
		params.put("pid", pid);
		if(uuid>0)
			params.put("uuid", uuid);
		return paylogMapper.selectByMidTimeCount(params);
	}
	public int selectByMidTimeMoney(int managerId, int searchType,String levelStr, int uuid,int pid,int start, int limit,Date startTime,Date endTime,int gameId) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
		if(startTime==null&&endTime==null){
		if(searchType==0){//查询全部
			startTime = new Date(0L);
			endTime = new Date((new java.util.Date()).getTime()+86400000);
		}else if(searchType==1){//本周
			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
		}else if(searchType==2){//上周
			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
		}else if(searchType==3){//本月
			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
			
			
		}else if(searchType==4){//上月
			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
			
			
		}else if(searchType==5){//今天
			startTime = new Date(DateUtil.getTodayStart().getTime());
			endTime = new Date(DateUtil.getTodayEnd().getTime());
		}else if(searchType==6){//昨天
			startTime = new Date(DateUtil.getYesterdayStart().getTime());
			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
		}
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>0)
		params.put("managerId", managerId);
		params.put("startNum", start);
		params.put("pageNumber", limit);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("levelStr", levelStr);
		if(gameId!=0){
			params.put("gameId", gameId);
		}
		if(pid>0)
			params.put("pid", pid);
		if(uuid>0)
			params.put("uuid", uuid);
		return paylogMapper.selectByMidTimeMoney(params);
	}
	
	
//	public List<PaylogExt> selectByAllTime( int searchType, int start, int limit,Date startTime,Date endTime,int pid,int uuid) {
//		//0是查询全部1是查本周2是查上周3是查本月4是查上月
//		System.out.println("searchType============="+searchType);
//		if(startTime==null&&endTime==null){
//		if(searchType==0){//查询全部
//			startTime = new Date(0L);
//			endTime = new Date((new java.util.Date()).getTime()+86400000);
//			
//			
//		}else if(searchType==1){//本周
//			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
//			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
//			
//			
//		}else if(searchType==2){//上周
//			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
//			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
//			
//			
//		}else if(searchType==3){//本月
//			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
//			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
//			
//			
//		}else if(searchType==4){//上月
//			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
//			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
//			
//			
//		}else if(searchType==5){//今天
//			startTime = new Date(DateUtil.getTodayStart().getTime());
//			endTime = new Date(DateUtil.getTodayEnd().getTime());
//		}else if(searchType==6){//昨天
//			startTime = new Date(DateUtil.getYesterdayStart().getTime());
//			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
//		}
//		}
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("startNum", start);
//		params.put("pageNumber", limit);
//		params.put("startTime", startTime);
//		params.put("endTime", endTime);
//		params.put("pid", pid);
//		if(uuid>0)
//			params.put("uuid", uuid);
//		return paylogMapper.selectByAllTime(params);
//	}
	
	
//	public int selectByAllTimeCount(int searchType, int start, int limit,Date startTime,Date endTime,int pid,int uuid) {
//		//0是查询全部1是查本周2是查上周3是查本月4是查上月
//		System.out.println("searchType============="+searchType);
//		if(startTime==null&&endTime==null){
//		if(searchType==0){//查询全部
//			startTime = new Date(0L);
//			endTime = new Date((new java.util.Date()).getTime()+86400000);
//			
//			
//		}else if(searchType==1){//本周
//			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
//			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
//			
//			
//		}else if(searchType==2){//上周
//			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
//			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
//			
//			
//		}else if(searchType==3){//本月
//			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
//			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
//			
//			
//		}else if(searchType==4){//上月
//			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
//			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
//			
//			
//		}else if(searchType==5){//今天
//			startTime = new Date(DateUtil.getTodayStart().getTime());
//			endTime = new Date(DateUtil.getTodayEnd().getTime());
//		}else if(searchType==6){//昨天
//			startTime = new Date(DateUtil.getYesterdayStart().getTime());
//			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
//		}
//		}
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("startNum", start);
//		params.put("pageNumber", limit);
//		params.put("startTime", startTime);
//		params.put("endTime", endTime);
//		params.put("pid", pid);
//		if(uuid>0)
//			params.put("uuid", uuid);
//		return paylogMapper.selectByAllTimeCount(params);
//	}

//	public int selectByAllTimeMoney(int searchType, int start, int limit,Date startTime,Date endTime,int pid,int uuid) {
//		//0是查询全部1是查本周2是查上周3是查本月4是查上月
//		System.out.println("searchType============="+searchType);
//		if(startTime==null&&endTime==null){
//		if(searchType==0){//查询全部
//			startTime = new Date(0L);
//			endTime = new Date((new java.util.Date()).getTime()+86400000);
//			
//			
//		}else if(searchType==1){//本周
//			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
//			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
//			
//			
//		}else if(searchType==2){//上周
//			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
//			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
//			
//			
//		}else if(searchType==3){//本月
//			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
//			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
//			
//			
//		}else if(searchType==4){//上月
//			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
//			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
//			
//			
//		}else if(searchType==5){//今天
//			startTime = new Date(DateUtil.getTodayStart().getTime());
//			endTime = new Date(DateUtil.getTodayEnd().getTime());
//		}else if(searchType==6){//昨天
//			startTime = new Date(DateUtil.getYesterdayStart().getTime());
//			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
//		}
//		}
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("startNum", start);
//		params.put("pageNumber", limit);
//		params.put("startTime", startTime);
//		params.put("endTime", endTime);
//		params.put("pid", pid);
//		if(uuid>0)
//			params.put("uuid", uuid);
//		return paylogMapper.selectByAllTimeMoney(params);
//	}
	
	@Override
	public int selectSumByManagerId(int managerId, int searchType,int payType) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
				Date startTime = null;
				Date endTime = null;
				if(searchType==0){//查询全部
					startTime = new Date(0L);
					endTime = new Date((new java.util.Date()).getTime()+86400000);
				}else if(searchType==1){//本周
					startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
					endTime = new Date(DateUtil.getTimesWeeknight().getTime());
				}else if(searchType==2){//上周
					startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
					endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
				}else if(searchType==3){//本月
					startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
					endTime = new Date(DateUtil.getTimesMonthnight().getTime());
				}else if(searchType==4){//上月
					startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
					endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
				}else if(searchType==5){//今天
					startTime = new Date(DateUtil.getTodayStart().getTime());
					endTime = new Date(DateUtil.getTodayEnd().getTime());
				}else if(searchType==6){//昨天
					startTime = new Date(DateUtil.getYesterdayStart().getTime());
					endTime = new Date(DateUtil.getYesterdayEnd().getTime());
				}
				Map<String,Object> params = new HashMap<String,Object>();
				if(managerId>2)
				params.put("managerId", managerId);
				params.put("payType", payType);
				params.put("startTime", startTime);
				params.put("endTime", endTime);
				return paylogMapper.selectSumByManagerId(params);
	}

	@Override
	public int sumByManagerId(int managerId) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
		Date startTime = null;
		Date endTime = null;
			startTime = new Date(0L);
			endTime = new Date((new java.util.Date()).getTime()+86400000);
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>2)
		params.put("managerId", managerId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("isTixian", true);
		return paylogMapper.sumByManagerId(params);
	}
	
	
	
	
	@Override
	public double remainByManagerId(int managerId) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("managerId", managerId);
		try{
		return paylogMapper.remainByManagerId(params);
		}catch(BindingException ee){
			return 0.0;
		}
		
	}


	@Override
	public int sumByManagerId2(int managerId, int sumType) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
		Date startTime = null;
		Date endTime = null;
		if(sumType==0){//查询全部
			startTime = new Date(0L);
			endTime = new Date((new java.util.Date()).getTime()+86400000);
		}else if(sumType==1){//本周
			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
		}else if(sumType==2){//上周
			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
		}else if(sumType==3){//本月
			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
		}else if(sumType==4){//上月
			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
		}else if(sumType==5){//今天
			startTime = new Date(DateUtil.getTodayStart().getTime());
			endTime = new Date(DateUtil.getTodayEnd().getTime());
		}else if(sumType==6){//昨天
			startTime = new Date(DateUtil.getYesterdayStart().getTime());
			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>2)
		params.put("managerId", managerId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return paylogMapper.sumByManagerId(params);
	}
	

	@Override
	public int sumByTime(int managerId, Date start, Date end) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>2)
		params.put("managerId", managerId);
		params.put("startTime", start);
		params.put("endTime", end);
		return paylogMapper.sumByManagerId(params);
	}

	@Override
	public double sumSubByManagerId(int managerId,String levelStr,double rebate) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
				Date startTime = null;
				Date endTime = null;
				startTime = new Date(0L);
				endTime = new Date((new java.util.Date()).getTime()+86400000);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("startTime", startTime);
				params.put("levelStr", levelStr);
				params.put("length", levelStr.length()+1);
				params.put("endTime", endTime);
				params.put("managerId", managerId);
				params.put("rebate", rebate);
				params.put("isTixian", true);
				double result = 0;
				if(managerId>2){
					result+=paylogMapper.sumSubByManagerId(params);
					BigDecimal bg = new BigDecimal(result).setScale(2, RoundingMode.UP);
					result = bg.doubleValue();
					
				}
				return result;
				
				
	}
	
	
	@Override
	public double sumSubByManagerId2(int managerId, int sumType,String levelStr,double rebate) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
				Date startTime = null;
				Date endTime = null;
				if(sumType==0){//查询全部
					startTime = new Date(0L);
					endTime = new Date((new java.util.Date()).getTime()+86400000);
				}else if(sumType==1){//本周
					startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
					endTime = new Date(DateUtil.getTimesWeeknight().getTime());
				}else if(sumType==2){//上周
					startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
					endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
				}else if(sumType==3){//本月
					startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
					endTime = new Date(DateUtil.getTimesMonthnight().getTime());
				}else if(sumType==4){//上月
					startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
					endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
				}else if(sumType==5){//今天
					startTime = new Date(DateUtil.getTodayStart().getTime());
					endTime = new Date(DateUtil.getTodayEnd().getTime());
				}else if(sumType==6){//昨天
					startTime = new Date(DateUtil.getYesterdayStart().getTime());
					endTime = new Date(DateUtil.getYesterdayEnd().getTime());
				}
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("startTime", startTime);
				params.put("levelStr", levelStr);
				params.put("length", levelStr.length()+1);
				params.put("endTime", endTime);
				params.put("managerId", managerId);
				params.put("rebate", rebate);
				int result = 0;
				if(managerId>2){
					result+=paylogMapper.sumSubByManagerId(params);

					
				}
				return result;
				
				
	}


	@Override
	public int insertPaylog(Paylog paylog) {
		// TODO Auto-generated method stub
		return paylogMapper.insertSelective(paylog);
	}

	@Override
	public void tixianUpdate(Integer managerId,int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("managerId", managerId);
		params.put("status", status);
		paylogMapper.tixianUpdate(params);
	}

	@Override
	public void tixianDone(Integer id,int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		if(status>1)
			params.put("status", status);
		paylogMapper.tixianDone(params);
	}


	@Override
	public List<Paylog> selectUserPaySum(int managerId, int searchType, int start, int limit, Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		if(startTime==null&&endTime==null){
		if(searchType==0){//查询全部
			startTime = new Date(0L);
			endTime = new Date((new java.util.Date()).getTime()+86400000);
		}else if(searchType==1){//本周
			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
		}else if(searchType==2){//上周
			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
		}else if(searchType==3){//本月
			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
		}else if(searchType==4){//上月
			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
		}else if(searchType==5){//今天
			startTime = new Date(DateUtil.getTodayStart().getTime());
			endTime = new Date(DateUtil.getTodayEnd().getTime());
		}else if(searchType==6){//昨天
			startTime = new Date(DateUtil.getYesterdayStart().getTime());
			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
		}
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>2)
		params.put("managerId", managerId);
		params.put("startNum", start);
		params.put("pageNumber", limit);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return paylogMapper.selectUserPaySum(params);
	}


	
	
	@Override
	public double selectTixianSum(int managerId, int searchType) {
		//0是查询全部1是查本周2是查上周3是查本月4是查上月
		Date startTime = null;
		Date endTime = null;
		if(searchType==0){//查询全部
			startTime = new Date(0L);
			endTime = new Date((new java.util.Date()).getTime()+86400000);
		}else if(searchType==1){//本周
			startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesWeeknight().getTime());
		}else if(searchType==2){//上周
			startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
		}else if(searchType==3){//本月
			startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesMonthnight().getTime());
		}else if(searchType==4){//上月
			startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
			endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
		}else if(searchType==5){//今天
			startTime = new Date(DateUtil.getTodayStart().getTime());
			endTime = new Date(DateUtil.getTodayEnd().getTime());
		}else if(searchType==6){//昨天
			startTime = new Date(DateUtil.getYesterdayStart().getTime());
			endTime = new Date(DateUtil.getYesterdayEnd().getTime());
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(managerId>2)
		params.put("managerId", managerId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return paylogMapper.selectTixianSum(params);
	}


	@Override
	public Paylog selectByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return paylogMapper.selectByPrimaryKey(id);
	}


	@Override
	public double selectByMidTimeBonus(int managerId, int searchType, String levelStr, int uuid, int pid, int start,
			int limit, Date startTime, Date endTime, int gameId) {
		// TODO Auto-generated method stub
		if(startTime==null&&endTime==null){
			if(searchType==0){//查询全部
				startTime = new Date(0L);
				endTime = new Date((new java.util.Date()).getTime()+86400000);
			}else if(searchType==1){//本周
				startTime = new Date(DateUtil.getTimesWeekmorning().getTime());
				endTime = new Date(DateUtil.getTimesWeeknight().getTime());
			}else if(searchType==2){//上周
				startTime = new Date(DateUtil.getTimesLastWeekmorning().getTime());
				endTime = new Date(DateUtil.getTimesLastWeeknight().getTime());
			}else if(searchType==3){//本月
				startTime = new Date(DateUtil.getTimesMonthmorning().getTime());
				endTime = new Date(DateUtil.getTimesMonthnight().getTime());
				
				
			}else if(searchType==4){//上月
				startTime = new Date(DateUtil.getTimesLastMonthmorning().getTime());
				endTime = new Date(DateUtil.getTimesLastMonthnight().getTime());
				
				
			}else if(searchType==5){//今天
				startTime = new Date(DateUtil.getTodayStart().getTime());
				endTime = new Date(DateUtil.getTodayEnd().getTime());
			}else if(searchType==6){//昨天
				startTime = new Date(DateUtil.getYesterdayStart().getTime());
				endTime = new Date(DateUtil.getYesterdayEnd().getTime());
			}
			}
			Map<String,Object> params = new HashMap<String,Object>();
			if(managerId>0)
			params.put("managerId", managerId);
			params.put("startNum", start);
			params.put("pageNumber", limit);
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("levelStr", levelStr);
			if(gameId!=0){
				params.put("gameId", gameId);
			}
			if(pid>0)
				params.put("pid", pid);
			if(uuid>0)
				params.put("uuid", uuid);
			return paylogMapper.selectByMidTimeBonus(params);
	}
	
	
		

}
