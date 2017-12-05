package com.weipai.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weipai.Withdraw.WithdrawCash;
import com.weipai.common.Params;
import com.weipai.controller.base.BaseController;
import com.weipai.model.Account;
import com.weipai.model.Bonus;
import com.weipai.model.BonusExt;
import com.weipai.model.ExchangeLotExt;
import com.weipai.model.Generallog;
import com.weipai.model.Gmconfig;
import com.weipai.model.Manager;
import com.weipai.model.ManagerNode;
import com.weipai.model.NoticeTable;
import com.weipai.model.Paylog;
import com.weipai.model.PaylogExt;
import com.weipai.model.PayloglotExt;
import com.weipai.model.ResultLot;
import com.weipai.model.Roomcardlog;
import com.weipai.service.AccountService;
import com.weipai.service.BonusService;
import com.weipai.service.ExchangeLotService;
import com.weipai.service.GenerallogService;
import com.weipai.service.GmconfigService;
import com.weipai.service.ManagerService;
import com.weipai.service.NoticeTableService;
import com.weipai.service.PaylogLotService;
import com.weipai.service.PaylogService;
import com.weipai.service.ResultLotService;
import com.weipai.service.RoomcardlogService;
import com.weipai.utils.StringUtil;

import sun.java2d.loops.DrawGlyphListAA.General;

@Controller
@RequestMapping("/controller/manager")
public class ManagerController extends BaseController {

	@Autowired
	private ManagerService managerService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private NoticeTableService noticeTableService;

    @Autowired
    private ResultLotService resultLotService;
	
	@Autowired
	private PaylogService paylogService;
	
	@Autowired
	private GmconfigService gmconfigService;
	
	@Autowired
	private PaylogLotService paylogLotService;
	
	@Autowired
	private ExchangeLotService exchangeLotService;
	
	@Autowired
	private RoomcardlogService roomcardlogService;
	
	@Autowired
	private BonusService bonusService;
	
	@Autowired
	private GenerallogService generallogService;
	
	public static Map<String,List<ManagerNode>> managerCache = new HashMap<String,List<ManagerNode>>();
//	public static List<ManagerNode> managerNodeList = new ArrayList<ManagerNode>();
	public static Map<String,ManagerNode> managerNodeCache = new HashMap<String,ManagerNode>();
	
	public static Map<Integer,Manager> maCache = new HashMap<Integer,Manager>();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Map<String,Long> cacheTimestamp = new HashMap<String,Long>();
	public static Map<String,Map<Integer,String>> pathCache = new HashMap<String,Map<Integer,String>>();
	public static Map<Integer,String> tixianDayLog = new ConcurrentHashMap<Integer,String>();
	public static volatile int status = 0;
	public static volatile int maxTixianTimes = 2;//设置每天最多提现次数
	/**
	 * 后台登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30*60);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String yqm = request.getParameter("yqm");
		JSONObject json = new JSONObject();
		double rebate = 0.0;
		String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY); 
		boolean kaptchaRight = kaptchaExpected.equals(yqm);
		if (kaptchaRight&&StringUtil.isNotEmpty(username) && StringUtil.isNotEmpty(password)) {//
			Manager manager =null;
			manager = managerService.selectManagerByInvoteCode(Integer.parseInt(username));
			Account account = new Account();
			if(manager!=null&&manager.getUuid()!=null&&manager.getUuid()>1)
			account = accountService.selectByUuid(manager.getUuid());
//			System.out.println(StringUtil.MD5(password));
			
			if (manager != null
					&& StringUtil.MD5(password).equals(manager.getPassword())) {
				managerService.updateManagerLoginTime(manager.getId());
				json.put("mess", "7");
				//登录成功 用户信息放入缓存
				session.setAttribute("manager", manager);
				if(account!=null)
				session.setAttribute("userAccount", account);
				json.put("roomCard", manager.getActualcard());
				if(manager.getManagerUpId()!=null&&manager.getManagerUpId()>0){
					Manager managerUp = managerService.selectByPrimaryKey(manager.getManagerUpId());
					session.setAttribute("managerUp", managerUp);
				}
				if(manager.getPowerId() == 1){
					//如果是超管登录  则需要返回最新公告
//					NoticeTable notice = noticeTableService.selectRecentlyObject(new HashMap<String,Object>()).get(0);
//					ContactWay contactWay = contactWayService.selectByPrimaryKey(1);
//					if(notice != null){
//						session.setAttribute("notice", notice.getContent()+"");
//						session.setAttribute("contactway", contactWay.getContent()+"");
//					}
					json.put("mess", "0");
				}
				
				
				int powerId = manager.getPowerId();
				if(manager.getId()!=1){
					rebate = Double.parseDouble(manager.getRebate());
					String str ="00000000";
					  String str_m = manager.getId().toString();
					  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
					  
					String plevelStr = manager.getLevelStr();
					String levelStr = null;
					if(StringUtil.isNotEmpty(plevelStr)){
						levelStr = plevelStr+str_m;
					}else{
						levelStr = str_m;
					}
					
				int managerId =	manager.getId();
				double remain = paylogService.remainByManagerId(managerId);
				int mineone = paylogService.sumByManagerId(manager.getId());
				int minelotone = paylogLotService.selectNextOneMoney(managerId);
//				if(manager.getPowerId()==5||manager.getPowerId()==4)//总代理
				double minetwo = powerId>2?paylogService.sumSubByManagerId(manager.getId(), levelStr,rebate):0;
				int minelottwo = paylogLotService.selectNextTwoMoney(managerId,powerId);
				double n1 = 0,w1 = 0, w2 = 0;
				if(StringUtil.isNotEmpty(manager.getRebate())){
					String[] nums = manager.getRebate().split(":|,");
					n1 = Double.parseDouble(nums[0]);
//					n2 = Double.parseDouble(nums[1]);
//					w1 = Double.parseDouble(nums[2]);
//					w2 = Double.parseDouble(nums[3]);
				}else{
					n1 = Params.getPercentage1(powerId);
//					n2 = Params.getPercentage2(powerId);
					
				}
				w1 = Params.getPercentagelot1(powerId);
				w2 = Params.getPercentagelot2(powerId);
					
				String oneString = "￥"+mineone+"("+100*n1+"%)";
				String twoString = "￥"+minetwo+"("+100*0+"%)";
				String onelotString = "￥"+ minelotone +"("+100*w1+"%)";
				String twolotString = "￥"+ minelottwo +"("+100*w2+"%)";
				DecimalFormat    df   = new DecimalFormat("######0.00");   
				String total = df.format(mineone*n1+minetwo+minelotone*w1+minelottwo*w2+remain);
				session.setAttribute("mineone", oneString);
				session.setAttribute("minetwo", twoString);
				session.setAttribute("minelotone", onelotString);
				session.setAttribute("minelottwo", twolotString);
				session.setAttribute("total", total);
				session.setAttribute("remain", remain);
				}else{
//					manager = managerService.selectByPrimaryKey(manager.getId());
//					int mineone = paylogService.sumByManagerId(manager.getId(), type);
//					int minetwo = paylogService.sumSubByManagerId(manager.getId(), type,powerId);
//					String oneString = "￥"+mineone+"("+100*Params.getPercentage1(manager.getPowerId())+"%)";
//					String twoString = "￥"+minetwo+"("+100*Params.getPercentage2(manager.getPowerId())+"%)";
//					DecimalFormat    df   = new DecimalFormat("######0.00");   
//					String total = df.format(mineone*Params.getPercentage1(manager.getPowerId())+minetwo*Params.getPercentage2(manager.getPowerId()));
//					session.setAttribute("mineone", oneString);
//					session.setAttribute("minetwo", twoString);
//					session.setAttribute("total", total);
				}
				
				
			} else {
				json.put("mess", "1");
			}
		} else {
			json.put("mess", "2");
		}
		returnMessage(response, json);
	}
	
	@RequestMapping("/refreshIncome")
	public void refreshIncome(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Manager manager = (Manager) session.getAttribute("manager");
		JSONObject json = new JSONObject();
		if (manager != null) {
			
			if(manager.getPowerId() == 1){
				return;
			}
			int powerId = manager.getPowerId();
			int type = 0;
			if(manager.getId()!=1){
			double rebate = Double.parseDouble(manager.getRebate());
				String str ="00000000";
				  String str_m = manager.getId().toString();
				  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
				  
				String plevelStr = manager.getLevelStr();
				String levelStr = null;
				if(StringUtil.isNotEmpty(plevelStr)){
					levelStr = plevelStr+str_m;
				}else{
					levelStr = str_m;
				}
				
			int managerId =	manager.getId();
			double remain = paylogService.remainByManagerId(managerId);
			int mineone = paylogService.sumByManagerId(manager.getId());
			int minelotone = paylogLotService.selectNextOneMoney(managerId);
			double minetwo = powerId>2?paylogService.sumSubByManagerId(manager.getId(),levelStr,rebate):0;
			int minelottwo = paylogLotService.selectNextTwoMoney(managerId,powerId);
			double n1 = 0,w1 = 0, w2 = 0;
			if(StringUtil.isNotEmpty(manager.getRebate())){
				String[] nums = manager.getRebate().split(":|,");
				n1 = Double.parseDouble(nums[0]);
			}else{
				n1 = Params.getPercentage1(powerId);
				
			}
			w1 = Params.getPercentagelot1(powerId);
			w2 = Params.getPercentagelot2(powerId);
				
			String oneString = "￥"+mineone+"("+100*n1+"%)";
			String twoString = "￥"+minetwo+"("+100*0+"%)";
			String onelotString = "￥"+ minelotone +"("+100*w1+"%)";
			String twolotString = "￥"+ minelottwo +"("+100*w2+"%)";
			DecimalFormat    df   = new DecimalFormat("######0.00");   
			String total = df.format(mineone*n1+minetwo+minelotone*w1+minelottwo*w2+remain);
			json.put("mineone", oneString);
			json.put("minetwo", twoString);
			json.put("minelotone", onelotString);
			json.put("minelottwo", twolotString);
			json.put("total", total);
			json.put("remain", remain);
			returnMessage(response, json.toString());
			}
		}
	}
	
	@RequestMapping("/getExchange")
	public void getExchange(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		String pageNumber = request.getParameter("pageNum");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String uuid = request.getParameter("uuid");
		
		int pageNum = 1;
		if(pageNumber!=null){
			pageNum = Integer.parseInt(pageNumber);
		}
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String,Object> map = new HashMap<String, Object>();
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			int size = 0;
			int startNum = (pageNum-1)*Params.pageNumber;
			map.put("pageNumber", Params.pageNumber);
			map.put("startNum", startNum);
			try {
				if(StringUtil.isNotEmpty(startTime)){
				  Date	startdate = new Date(sdf.parse(startTime).getTime());
					map.put("startTime", startdate);
				}
				
				if(StringUtil.isNotEmpty(endTime)){
				  Date  enddate = new Date(sdf.parse(endTime).getTime());
					map.put("endTime", enddate);
				}
				
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(StringUtil.isInteger(uuid, 0, 0)){
				map.put("uuid", uuid);
			}
			
			List<ExchangeLotExt> exchanges = exchangeLotService.selectByMidTime(map);
			size = 	exchangeLotService.selectCount(map);
		
			json.put("totalNum", size);
			SimpleDateFormat s =   new SimpleDateFormat( "yyyy年MM月dd日 HH:mm:ss" );
			for (ExchangeLotExt m : exchanges) {
			    String time = s.format(m.getCreatetime());
			    m.setDatestring(time);
				array.add(m);
			}
			json.put("exchanges", array);
			returnMessage(response, json.toString());
		}
	}
	
	
//	@RequestMapping("/proxyDetail")
//	public String proxyDetail(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		String managerId = request.getParameter("managerId");
//		JSONObject json = new JSONObject();
//		if (StringUtil.isNotEmpty(managerId)) {//
//			Manager manager =null;
//			manager = managerService.selectByPrimaryKey(Integer.parseInt(managerId));
//
//			Account account = accountService.selectByManagerId(manager.getId());
//			
//			if (manager != null) {
//				//登录成功 用户信息放入缓存
////				session.setAttribute("manager", manager);
//				session.setAttribute("weixin", manager.getWeixin());
//				if(account!=null)
//				session.setAttribute("uuid", account.getUuid());
//				else
//					session.setAttribute("uuid", null);
//				session.setAttribute("qq", manager.getQq());
//				session.setAttribute("tel", manager.getTelephone());
//				session.setAttribute("name", manager.getName());
//				session.setAttribute("type", manager.getPowerId());
//				session.setAttribute("id", manager.getId());
//				session.setAttribute("proxy", manager);
//			}
//		} 
//		return "proxyDetail";
//	}
	
	@RequestMapping("/getPidByUid")
	public void getPidByUid(HttpServletRequest request, HttpServletResponse response) {
		String uuid = request.getParameter("uuid");
		JSONObject json = new JSONObject();
		if (StringUtil.isNotEmpty(uuid)) {//
			Manager manager =null;
			int Uuid = Integer.parseInt(uuid);
			Account account = accountService.selectByUuid(Uuid);
			Integer managerUpId = account.getManagerUpId();
			if(managerUpId!=null&&managerUpId>1){
			manager = managerService.selectByPrimaryKey(managerUpId);
			if(manager!=null)//PID为1才能显示28
				json.put("pid", manager.getPid());
			}
		} 
		returnMessage(response,json);
	}
	
	/**
	 * 超管修改代理商密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping("/getNextMoney")
//	public void getNextMoney(HttpServletRequest request, HttpServletResponse response){
//		HttpSession session = request.getSession();
//		JSONObject json = new JSONObject();
//		JSONArray array = new JSONArray();
//		Manager manager = (Manager) session.getAttribute("manager");
//		if(manager != null){
//			int managerId = manager.getId();
//			if(managerId == 1 ){
//				return;
//			}else{
//				int nextOne = paylogLotService.selectNextOneMoney(managerId);
//				int nextTwo = paylogLotService.selectNextTwoMoney(managerId,manager.getPowerId());
////				System.out.println(nextOne);
////				System.out.println(nextTwo);
//				json.put("nextOne", nextOne);
//				json.put("nextTwo", nextTwo);
//			}
//		}
//		returnMessage(response,json);
//	}
	
	@RequestMapping("/getAllConfig")
	public void getAllConfig(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		List<Gmconfig> gmconfigs = gmconfigService.selectAll();
		for (Gmconfig gmconfig : gmconfigs) {
			array.add(gmconfig);
		}
		json.put("gmconfigs", array);
		returnMessage(response,json);
	}
	
	@RequestMapping("/addConfig")
	public void addConfig(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();

		String key0 = request.getParameter("key");
		String value0 = request.getParameter("value");
		String properties = request.getParameter("properties");
		String label = request.getParameter("label");
		JSONObject json = new JSONObject();
		Gmconfig addinfo = new Gmconfig();
		addinfo.setKey(key0);
		addinfo.setValue(value0);
		addinfo.setLabel(label);
		addinfo.setProperties(properties);
		addinfo.setCreatetime(new java.util.Date());
		
		int id0 = gmconfigService.insert(addinfo);
		int id1 = addinfo.getId();
//		System.out.println("测试新增的id： "+id0);
//		System.out.println("测试新增的id after： "+id1);
		json.put("id", id1);
		
		returnMessage(response, json);
	}
	
	@RequestMapping("/updateConfig")
	public void updateConfig(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String key1 = request.getParameter("key");
		String value1 = request.getParameter("value");
		String properties = request.getParameter("properties");
		String label = request.getParameter("label");
		JSONObject json = new JSONObject();
		
		Gmconfig updateinfo = new Gmconfig();
		updateinfo.setKey(key1);
		updateinfo.setValue(value1);
		updateinfo.setLabel(label);
		updateinfo.setProperties(properties);
		updateinfo.setCreatetime(new java.util.Date());
		int updateId = gmconfigService.updateByPrimaryKey(updateinfo);
//		System.out.println("测试updateId"+updateId);
		json.put("updateId", updateId);
		returnMessage(response, json);
	}
	
	@RequestMapping("/deleteConfig")
	public void deleteConfig(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		JSONObject json = new JSONObject();
		int delId = gmconfigService.deleteByPrimaryKey(Integer.parseInt(id));
//		System.out.println("测试delId"+delId);
		json.put("delId", delId);
		returnMessage(response, json);

	}
	
	
	
	
	@RequestMapping("/updateManagerPassword")
	public void updateManagerPassword(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		String newPwd = request.getParameter("newPwd");
		String oldPwd = request.getParameter("oldPwd");
		String mid = request.getParameter("mid");
		boolean pwd =false;
		boolean hasMid = StringUtil.isNotEmpty(mid);
		if(session != null){
			Manager manager = (Manager) session.getAttribute("manager");
			if(manager != null&&!hasMid){
				Map<String,Object> map = new HashMap<String, Object>();
				if(StringUtil.isNotEmpty(newPwd)){
					if(StringUtil.isNotEmpty(oldPwd)){
						if(manager.getPassword().equals(StringUtil.MD5(oldPwd))){
							map.put("password", StringUtil.MD5(newPwd));
							pwd = true;
						}
						else{
							json.put("status", "1");
							json.put("error", "旧密码输入错误!");
						}
					}
					else{
						json.put("status", "1");
						json.put("error", "修改密码时，必须输入旧密码!");
					}
				}
				if(!map.isEmpty()){
					map.put("id", manager.getId());
					managerService.updateByMap(map);
					//更新session里面的manager信息
					if(pwd){
						manager.setPassword(StringUtil.MD5(newPwd));
					}
					session.setAttribute("manager",manager);
					json.put("status", "0");
				}
				else{
					json.put("status", "1");
					json.put("error", "传入参数有误");
					
				}
			}else if(manager != null&&hasMid){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("password", StringUtil.MD5(newPwd));
				map.put("id",Integer.parseInt(mid));
				managerService.updateByMap(map);
				json.put("status", "0");
			}
			else{
				json.put("status", "1");
				json.put("error", "请先登录");
				
			}
		}
		returnMessage(response,json);
	}
	
	
	/**
	 *后台管理员修改个人信息(密码/手机号码)
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateManagerInfo ")
	public void updateManagerInfo(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();

		String mid = request.getParameter("mid");
		String weixin = request.getParameter("weixin");
		String qq = request.getParameter("qq");
		String name = request.getParameter("name");
		String rebate = request.getParameter("rebate");
		String rootManager = request.getParameter("rootManager");
		String telephone = request.getParameter("telephone");
		String uuid = request.getParameter("uuid");
		String status = request.getParameter("status");
		String powerId = request.getParameter("powerId");
		String inviteCode = request.getParameter("inviteCode");
		String parentInviteCode = request.getParameter("parentInviteCode");
		if(session != null){
			Manager manager = (Manager) session.getAttribute("manager");
			if(manager != null&&(manager.getPowerId()==1||manager.getPowerId()==5||manager.getPowerId()==4)){//超级管理员，总代理，一级代理才具有创建和修改代理的权限
				Map<String,Object> map = new HashMap<String, Object>();
				Manager edit = new Manager();
				Generallog generallog = new Generallog();
				generallog.setManagerid(manager.getId());
				generallog.setCreatetime(new java.util.Date());
				if(StringUtil.isPhone(telephone)){
					edit.setTelephone(telephone);
					map.put("telephone", telephone);
				}
				if(StringUtil.isNotEmpty(weixin)){
					edit.setWeixin(weixin);
					map.put("weixin", weixin);
				}
				if(StringUtil.isNotEmpty(qq)){
					edit.setQq(qq);
					map.put("qq", qq);
				}
				if(StringUtil.isNotEmpty(name)){
					edit.setName(name);
					map.put("name", name);
				}
				if(StringUtil.isNotEmpty(status)){
					edit.setStatus(status);
					map.put("status", status);
				}
				if(StringUtil.isInteger(powerId,0,0)){
					int powerId2 = Integer.parseInt(powerId);
					if(manager.getPowerId()==1||(manager.getPowerId()>powerId2)){
					edit.setPowerId(powerId2);
					map.put("powerId", powerId2);
					}
				}
				if(StringUtil.isInteger(uuid,0,0)){
					edit.setUuid(Integer.parseInt(uuid));
					map.put("uuid", Integer.parseInt(uuid));
					generallog.setUuid(Integer.parseInt(uuid));
				}
				if(StringUtil.isNotEmpty(inviteCode)){
					edit.setInviteCode(Integer.parseInt(inviteCode));
					map.put("inviteCode", Integer.parseInt(inviteCode));
				}
				
				if(StringUtil.isNotEmpty(rootManager)){
					edit.setRootManager(Integer.parseInt(rootManager));
					map.put("rootManager", Integer.parseInt(rootManager));
				}
				Manager parentM  = null;
				if(StringUtil.isNotEmpty(parentInviteCode)){
					int pinvite = Integer.parseInt(parentInviteCode);
					parentM = managerService.selectManagerByInvoteCode(pinvite);
					if(parentM!=null){
					if(parentM.getPowerId()!=1&&(inviteCode==null||!parentInviteCode.equals(inviteCode))){
					edit.setManagerUpId(parentM.getId());
					map.put("managerUpId", parentM.getId());
					//新增层级字符串的处理
					  String str ="00000000";
					  String str_m = parentM.getId().toString();
					  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
					  
					String plevelStr = parentM.getLevelStr();
					String levelStr = null;
					if(StringUtil.isNotEmpty(plevelStr)){
						levelStr = plevelStr+str_m;
					}else{
						levelStr = str_m;
					}
					edit.setLevelStr(levelStr);
					map.put("levelStr",levelStr);
					}else{
							map.put("levelStr","");
					}
					}
				}
				
				if(StringUtil.isNotEmpty(rebate)){
					//加入校验逻辑
					double n1 = 0.0,m1 = 0.0;
					if(StringUtil.isNotEmpty(manager.getRebate())){
						String[] nums = manager.getRebate().split(":|,");
						n1 = Double.parseDouble(nums[0]);
//						n2 = Double.parseDouble(nums[1]);
					}else{
						n1 = Params.getPercentage1(manager.getPowerId());
//						n2 = Params.getPercentage2(manager.getPowerId());
					}
					String[] nums2 = rebate.split(":|,");
					m1 = Double.parseDouble(nums2[0]);
//					m2 = Double.parseDouble(nums2[1]);
					if(m1<=n1){
					edit.setRebate(rebate);
					map.put("rebate", rebate);
					}
				}else{
					if(Integer.parseInt(powerId)==5){
						double i = 0.7;
						edit.setRebate(Double.toString(i));
						map.put("rebate", Double.toString(i));
					}else if(Integer.parseInt(powerId)==4){
						double i = 0.6;
						edit.setRebate(Double.toString(i));
						map.put("rebate", Double.toString(i));
					}else if(Integer.parseInt(powerId)==3){
						double i = 0.5;
						edit.setRebate(Double.toString(i));
						map.put("rebate", Double.toString(i));
					}else if(Integer.parseInt(powerId)==2){
						double i = 0.4;
						edit.setRebate(Double.toString(i));
						map.put("rebate", Double.toString(i));
					}
				}
				int result = 0;
					if(StringUtil.isNotEmpty(mid)){
						edit.setId(Integer.parseInt(mid));
						map.put("id", Integer.parseInt(mid));
						generallog.setType(2);//0为给用户充钻石，1为给代理充钻石，2为修改代理信息，3为新增代理
						generallog.setContent(map.toString());
						if(parentM!=null)
							map.put("pid",parentM.getPid());
						result = managerService.updateByMap(map);
						generallogService.insert(generallog);
					}else{
						if(parentM!=null)
							edit.setPid(parentM.getPid());
						else{
							edit.setPid(manager.getPid());
							edit.setManagerUpId(manager.getId());
						}
						
							edit.setPassword("e10adc3949ba59abbe56e057f20f883e");
							edit.setCreateTime(new java.util.Date());
							result = managerService.saveSelective(edit);
							generallog.setType(3);//0为给用户充钻石，1为给代理充钻石，2为修改代理信息，3为新增代理
							generallog.setContent("name:"+edit.getName()+",id:"+edit.getId()+",upId:"+edit.getManagerUpId()+",powerId:"+edit.getPowerId()+",uuid:"+edit.getUuid()+",invitecode:"+edit.getInviteCode()+",rebate:"+edit.getRebate()+",tel:"+edit.getTelephone()+",weixin:"+edit.getWeixin());
							generallogService.insert(generallog);
							json.put("managerId", result);
					}
					if(result>0){
					for(Entry<String,Long> cacheentry:cacheTimestamp.entrySet()){
						long value = cacheentry.getValue();
						if(cacheentry.getKey().contains("_"+manager.getPid()))
						cacheentry.setValue(value-1000*60*5);
					}	
					}
					//重新绑定用户的ID信息
					if(result>0&&StringUtil.isNotEmpty(uuid)){
						int iuuid = Integer.parseInt(uuid);
						Account managerAccount = accountService.selectByUuid(iuuid);
						int managerUpId = edit.getId();
						Map<String,Object> pmap = new HashMap<String,Object>();
						pmap.put("id", managerAccount.getId());
						pmap.put("managerId", managerUpId);
//						pmap.put("managerUpId", managerUpId);
						pmap.put("roomCard", 15);
//						if(parentM!=null)
//							pmap.put("managerUpId", parentM.getId());
						accountService.updateAccountStatus(pmap);
					}
					if(manager.getPowerId()==1||manager.getPowerId()>Integer.parseInt(powerId)){
						json.put("canpowerId", 1);
					}
					json.put("status", "0");
			}
			else{
				if(manager!=null&&StringUtil.isNotEmpty(mid)&&manager.getPowerId()>Integer.parseInt(powerId)){
					Map<String,Object> poweridmap = new HashMap<String, Object>();
					poweridmap.put("id", Integer.parseInt(mid));
					poweridmap.put("powerId", Integer.parseInt(powerId));
					int changePowerId = managerService.updatePowerIdByMap(poweridmap);
					json.put("changePowerId", changePowerId);
					json.put("powerId", Integer.parseInt(powerId));
					for(Entry<String,Long> cacheentry:cacheTimestamp.entrySet()){
						long value = cacheentry.getValue();
						if(cacheentry.getKey().contains("_"+manager.getPid()))
						cacheentry.setValue(value-1000*60*5);
					}	
				}
				json.put("status", "1");
				json.put("error", "请选择正确的代理等级！");
				
			}
		}
		returnMessage(response,json);
	}
	
	/**
	 * 获取游戏服务器相关信息  开放数量，在线人数，总用户等信息
	 * 在本地取的参数有： 1:会员()总数  2：售出房卡总数   3：房间总数  4:玩家总数 5：总房卡数(玩家房卡总数额/代理商房卡总数)
	 * //6:今天创建房间总数 
	 * //7:今日售出(今天充值-今天退卡)
	 * 	//8:本周售出(本周充值-本周退卡)
	 * //9:今日新增用户
	 * 
	 * 
	 * 到游戏服务器取的参数有： 1：当前在线房间总数   2：当前在线人数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getAllGameInfos")
	public void getAllGameInfos(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager != null && manager.getPowerId() == 1 ){
			json = managerService.getAllGameInfos();
		}
		returnMessage(response, json);
	}
	
	
	/**
	 * 获取所有玩家
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/getAccounts")
	public void getAccounts(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		String pageNumber = request.getParameter("pageNum");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String nickName = request.getParameter("nickName");
//		System.out.println(startTime+"==============="+endTime);
		int pageNum = 1;
		if(pageNumber!=null){
			pageNum = Integer.parseInt(pageNumber);
		}
		Manager manager = (Manager) session.getAttribute("proxy");
		if(manager==null||request.getParameter("pageType")==null)
		manager = (Manager) session.getAttribute("manager");
		
//		String searchType = request.getParameter("type");
		if(manager != null){
			Map<String , Object> map = new HashMap<String, Object>();
			try {
			if(startTime!=null&&!"".equals(startTime)&&!"null".equals(startTime)){
				
					Date startdate = new Date(sdf.parse(startTime).getTime());
					map.put("startTime", startdate);
			}
			if(manager.getPid()!=null&&manager.getPid()!=0)
			map.put("pid", manager.getPid());
			if(endTime!=null&&!"".equals(endTime)&&!"null".equals(endTime)){
				
				Date enddate = new Date(sdf.parse(endTime).getTime());
				map.put("endTime", enddate);
		}
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(nickName!=null&&!"".equals(nickName)&&!"null".equals(nickName)){
				nickName = new String(nickName.getBytes("ISO-8859-1"),"UTF-8");
				map.put("nickname", nickName);
		}
			if(manager.getPowerId()>1&&manager.getPowerId()<5){
				map.put("managerUpId", manager.getId());
			}
			int size = 0;
			int startNum = (pageNum-1)*Params.pageNumber;
			map.put("startNum", startNum);
			map.put("pageNumber", Params.pageNumber);
			if(manager.getPowerId()!=1){//不是系统管理员需要限定访问查
				map.put("managerUpId", manager.getId());
				size = accountService.selectSzieByManagerId(map);
			}else{
				size = accountService.selectSzieByManagerId(map);
			}
			//获取所有玩家   
			List<Account> accounts = accountService.selectAllAccount(map);
			for (Account account : accounts) {
				array.add(account);
			}
			//获取所有玩家   
			int totalNum = (int) Math.ceil(size/(double)Params.pageNumber);
			json.put("totalNum", size);
		}
		json.put("users", array);
		
//		System.out.println("getUsers============"+json.toJSONString());
		returnMessage(response, json);
	}
	
	@RequestMapping("/getVipCount")
	public void getVipCount(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();

		Manager manager = (Manager) session.getAttribute("manager");
		

		if(manager != null){
			Map<String , Object> map = new HashMap<String, Object>();
			if(manager.getPid()!=null&&manager.getPid()!=0)
			map.put("pid", manager.getPid());
			int size = 0,size1=0,size2=0;
			map.put("managerId", manager.getId());
			String levelStr = null;
			  String str ="00000000";
			  String str_m = manager.getId().toString();
			  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
			
			if(StringUtil.isNotEmpty(manager.getLevelStr())){
				levelStr = manager.getLevelStr()+str_m;
			}else{
				levelStr = str_m;
			}
			System.out.println(levelStr);
			map.put("levelStr", levelStr);
			if(manager.getPowerId()==1){
				size = accountService.selectVipCountNewAll(manager.getId());
			}else{
				size = accountService.selectVipCountNew(map);
			}
			json.put("totalNum", size);
		}
		returnMessage(response, json);
	}
	
	@RequestMapping("/getAllNotice")
	public void getAllNotice(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		int managerId  = ((Manager) session.getAttribute("manager")).getId();
		Map<String,Object> params = new HashMap<String,Object>();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		List<NoticeTable> notices = noticeTableService.selectRecentlyObject(params);;
		for (NoticeTable account : notices) {
			array.add(account);
		}
		json.put("notices", array);
		returnMessage(response,json);
	}
	
	
	
	@RequestMapping("/getManagers2")
	public void getManagers2(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		String uuid2 = request.getParameter("uuid");
		String inviteCode2 = request.getParameter("inviteCode");
		String name = request.getParameter("name");
		String parentInviteCode = request.getParameter("parentInviteCode");
		String powerId2 = request.getParameter("powerId");
		
		int uuid = 0,inviteCode = 0,powerId=0,pmid = 0;
		if(StringUtil.isInteger(uuid2,0,0)){
			uuid = Integer.parseInt(uuid2);
		}
		if(StringUtil.isInteger(inviteCode2,0,0)){
			inviteCode = Integer.parseInt(inviteCode2);
		}
		if(StringUtil.isInteger(powerId2,0,0)){
			powerId = Integer.parseInt(powerId2);
		}
		String searchType = request.getParameter("searchType");
		int stype = 0;
		if(StringUtil.isInteger(searchType, 0, 0))
			stype = Integer.parseInt(searchType);
		String pageNumber = request.getParameter("pageNum");
		int pageNum = 1;
		if(pageNumber!=null){
			pageNum = Integer.parseInt(pageNumber);
		}
		String str_m = null;
		if(StringUtil.isInteger(parentInviteCode,0,0)){
			int pinvite = Integer.parseInt(parentInviteCode);
			Manager parentM = managerService.selectManagerByInvoteCode(pinvite);
			if(parentM!=null){
			//新增层级字符串的处理
				pmid = parentM.getId();
			  String str ="00000000";
			  str_m = parentM.getId().toString();
			  str_m=parentM.getLevelStr()!=null?parentM.getLevelStr():""+str.substring(0, 8-str_m.length())+str_m+"$";
			}
		}
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			Map<String , Object> map = new HashMap<String, Object>();
			int size = 0;
			int startNum = (pageNum-1)*Params.pageNumber;
			map.put("pageNumber", Params.pageNumber);
			map.put("startNum", startNum);
			if(manager.getPid()!=null&&manager.getPid()!=0)
			map.put("pid", manager.getPid());
			List<ManagerNode> managerTree = null;
			Map<Integer,String> parentPath = null;
			if(cacheTimestamp.containsKey(stype+"_"+manager.getPid())){
				long cacheTime = cacheTimestamp.get(stype+"_"+manager.getPid());
				long now = new java.util.Date().getTime();
				if((now-cacheTime)<(1000*60*5)){//直接取缓存
					managerTree = managerCache.get(stype+"_"+manager.getPid());
					parentPath = pathCache.get(stype+"_"+manager.getPid());
				}
			}
			if(managerTree==null){
			List<Manager> managers = managerService.selectManagerInfoByMap(map,stype);
			size = managers.size();//需要加载全部的树
				managerTree = new ArrayList<ManagerNode>();
				List<Manager> waitting2Tree = new ArrayList<Manager>();
				parentPath = new HashMap<Integer,String>();
				for(Manager curManager:managers){
					if(curManager.getLastLoginTime()!=null)
					curManager.setLastLoginTimeStr(formatter.format(curManager.getLastLoginTime()));
					curManager.setCreateTimeStr(formatter.format(curManager.getCreateTime()));
					maCache.put(curManager.getId(), curManager);
					if(curManager.getManagerUpId()!=null&&curManager.getManagerUpId()>1){//为存在父节点的代理
						waitting2Tree.add(curManager);
					}else{//已是根节点，直接放入树中
						ManagerNode node1 = new ManagerNode(curManager);
						managerNodeCache.put(curManager.getId()+"_"+manager.getPid(), node1);
						managerTree.add(node1);
						parentPath.put(curManager.getId(), ""+(managerTree.size()-1));
					}
				}
				int allSize = waitting2Tree.size();
				int iterNum = allSize;
				for(int k=0;k<6;k++){
				for(int i=0;i<allSize;i++){
					if(iterNum==0)
						break;
					if(waitting2Tree.size()>i&&waitting2Tree.get(i)!=null){//存在且不为空对象
						int mid = waitting2Tree.get(i).getManagerUpId();
						if(parentPath.containsKey(mid)){
						String path = parentPath.get(mid);
						String[] paths = path.split("-");
						List<ManagerNode> curNode = managerTree;
						for(String curPath:paths){
							int curPathI = Integer.parseInt(curPath);
							curNode = curNode.get(curPathI).getChildagent();
						}
						ManagerNode node1 = new ManagerNode(waitting2Tree.get(i));
						managerNodeCache.put(waitting2Tree.get(i).getId()+"_"+manager.getPid(), node1);
						curNode.add(node1);//加入到子树列表
						parentPath.put(waitting2Tree.get(i).getId(), path+"-"+(curNode.size()-1));
						waitting2Tree.set(i, null);
						iterNum--;
						if(iterNum==0)
							break;
						}
					}
				}
				}
				cacheTimestamp.put(stype+"_"+manager.getPid(), new java.util.Date().getTime());
				managerCache.put(stype+"_"+manager.getPid(), managerTree);
				pathCache.put(stype+"_"+manager.getPid(), parentPath);
				
			}
				if(manager.getPowerId() == 1&&pmid==0&&powerId==0&&inviteCode==0&&uuid==0&&StringUtil.isEmpty(name)){//超级管理员是所有的,每次查询一页记录
				size = managerTree.size();
				for(int i=0;i<size;i++){
					if(i>=startNum&&i<(startNum+Params.pageNumber))
					array.add(managerTree.get(i));
				}
				}else{
					List<ManagerNode> unFilterNode = null;
					if(manager.getPowerId() != 1){
					String path = parentPath.get(manager.getId());
					String[] paths = path.split("-");
					List<ManagerNode> curNode = managerTree;
					ManagerNode flagNode = null;
					for(String curPath:paths){
						int curPathI = Integer.parseInt(curPath);
						flagNode = curNode.get(curPathI);
						curNode = flagNode.getChildagent();
					}
//					size = curNode.size();
					unFilterNode = parseNode(flagNode);
					}else
						unFilterNode = parseNode(managerTree);
					//添加过滤逻辑
					Iterator<ManagerNode> it = unFilterNode.iterator();
					while(it.hasNext()){
						ManagerNode x = it.next();
					    if(!StringUtil.isEmpty(name)&&!x.getName().equals(name)){
					        it.remove();
					        continue;
					    }
					    if(inviteCode!=0&&x.getInviteCode()!=inviteCode){
					    	it.remove();
					    	continue;
					    }
					    if(uuid!=0&&(x.getUuid()==null||x.getUuid()!=uuid)){
					    	it.remove();
					    	continue;
					    }
					    if(powerId!=0&&(x.getPowerId()==null||x.getPowerId()!=powerId)){
					    	it.remove();
					    	continue;
					    }
					    if(str_m!=null&&(StringUtil.isEmpty(x.getLevelStr())||!x.getLevelStr().startsWith(str_m))){//
					    	it.remove();
					    	continue;
					    }
					    if(x.getId().equals(manager.getId())){
					    	it.remove();
					    	continue;
					    }
					}
					size = unFilterNode.size();
					for(int i=0;i<size;i++){
						if(i>=startNum&&i<(startNum+Params.pageNumber)){
							Manager m = new Manager(unFilterNode.get(i));
							int upId = m.getManagerUpId();
							Manager n = maCache.get(upId);
							if(n!=null){
							m.setName2(n.getName());
							m.setInviteCode2(n.getInviteCode());
							}
							if(m.getManagerUpId().intValue()!=manager.getId().intValue()&&manager.getPowerId()<5&&manager.getPowerId()>1&&!m.getId().equals(manager.getId())){
								m.setName("--");
								m.setWeixin("--");
								m.setTelephone("--");
								m.setQq("--");
							}
						array.add(m);
						}
					}
					
				}

//				System.out.println("getManagers+++============"+array.toJSONString());
//			}
			//获取所有玩家   
//			int totalNum = (int) Math.ceil(size/(double)Params.pageNumber);
			json.put("totalNum", size);
			
			json.put("managers", array);
			
//			System.out.println("getManagers============"+json.toJSONString());
			returnMessage(response, json.toJSONString());
		}
	}
	
	@RequestMapping("/getBetMountSum")
	public void getBetMountSum(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		int betMountSum = 0;
		String searchType = request.getParameter("searchType");
		int stype = 0;
		if(StringUtil.isInteger(searchType, 0, 0))
			stype = Integer.parseInt(searchType);
//		    System.out.println(stype);
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			Map<String , Object> map = new HashMap<String, Object>();
			if(manager.getPowerId()==1){
				betMountSum = managerService.selectByAllTimeMountSum(map,stype);
			}else{
				map.put("managerId", manager.getId());
				betMountSum = managerService.selectByMidTimeMountSum(map, stype);
			}
			
			json.put("betMountSum", betMountSum);
			returnMessage(response, json.toJSONString());
		}
	}
	
	
	@RequestMapping("/getManagersStats")
	public void getManagersStats(HttpServletRequest request, HttpServletResponse response) {
		 Map<Integer,List<ManagerNode>> managerCache = new HashMap<Integer,List<ManagerNode>>();
		 Map<Integer,ManagerNode> managerNodeCache = new HashMap<Integer,ManagerNode>();
		 Map<Integer,Long> cacheTimestamp = new HashMap<Integer,Long>();
		 Map<Integer,Map<Integer,String>> pathCache = new HashMap<Integer,Map<Integer,String>>();
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		String searchType = request.getParameter("searchType");
		int stype = 0;
		if(StringUtil.isInteger(searchType, 0, 0))
			stype = Integer.parseInt(searchType);
		String pageNumber = request.getParameter("pageNum");
		int pageNum = 1;
		if(pageNumber!=null){
			pageNum = Integer.parseInt(pageNumber);
		}
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			Map<String , Object> map = new HashMap<String, Object>();
			int size = 0;
			int startNum = (pageNum-1)*Params.pageNumber;
			map.put("pageNumber", Params.pageNumber);
			map.put("startNum", startNum);
			List<ManagerNode> managerTree = null;
			Map<Integer,String> parentPath = null;
			if(cacheTimestamp.containsKey(stype)){
				long cacheTime = cacheTimestamp.get(stype);
				long now = new java.util.Date().getTime();
				if((now-cacheTime)<(1000*60*5)){//直接取缓存
					managerTree = managerCache.get(stype);
					parentPath = pathCache.get(stype);
				}
			}
			if(managerTree==null){
			List<Manager> managers = managerService.selectManagerInfoByMapStats(map,stype);
			size = managers.size();//需要加载全部的树
				managerTree = new ArrayList<ManagerNode>();
				List<Manager> waitting2Tree = new ArrayList<Manager>();
				parentPath = new HashMap<Integer,String>();
				for(Manager curManager:managers){
					if(curManager.getManagerUpId()!=null&&curManager.getManagerUpId()>1){//为存在父节点的代理
						waitting2Tree.add(curManager);
					}else{//已是根节点，直接放入树中
						ManagerNode node1 = new ManagerNode(curManager);
						managerNodeCache.put(curManager.getId(), node1);
						managerTree.add(node1);
						parentPath.put(curManager.getId(), ""+(managerTree.size()-1));
					}
				}
				int allSize = waitting2Tree.size();
				int iterNum = allSize;
				for(int k=0;k<5;k++){
				for(int i=0;i<allSize;i++){
					if(iterNum==0)
						break;
					if(waitting2Tree.size()>i&&waitting2Tree.get(i)!=null){//存在且不为空对象
						int mid = waitting2Tree.get(i).getManagerUpId();
						if(parentPath.containsKey(mid)){
						String path = parentPath.get(mid);
						String[] paths = path.split("-");
						List<ManagerNode> curNode = managerTree;
						for(String curPath:paths){
							int curPathI = Integer.parseInt(curPath);
							curNode = curNode.get(curPathI).getChildagent();
						}
						ManagerNode node1 = new ManagerNode(waitting2Tree.get(i));
						managerNodeCache.put(waitting2Tree.get(i).getId(), node1);
						curNode.add(node1);//加入到子树列表
						parentPath.put(waitting2Tree.get(i).getId(), path+"-"+(curNode.size()-1));
						waitting2Tree.set(i, null);
						iterNum--;
						if(iterNum==0)
							break;
						}
					}
				}
				}
				cacheTimestamp.put(stype, new java.util.Date().getTime());
				managerCache.put(stype, managerTree);
				pathCache.put(stype, parentPath);
				
			}
				if(manager.getPowerId() == 1){//超级管理员是所有的,每次查询一页记录
				size = managerTree.size();
				for(int i=0;i<size;i++){
					if(i>=startNum&&i<(startNum+Params.pageNumber))
					array.add(managerTree.get(i));
				}
				}else{
					String path = parentPath.get(manager.getId());
					String[] paths = path.split("-");
					List<ManagerNode> curNode = managerTree;
					for(String curPath:paths){
						int curPathI = Integer.parseInt(curPath);
						curNode = curNode.get(curPathI).getChildagent();
					}
					size = curNode.size();
					for (ManagerNode m : curNode){
						array.add(m);
					}
					
				}

//				System.out.println("getManagers+++============"+array.toJSONString());
//			}
			//获取所有玩家   
//			int totalNum = (int) Math.ceil(size/(double)Params.pageNumber);
			json.put("totalNum", size);
			json.put("managers", array);
//			System.out.println("getManagers============"+json.toJSONString());
			returnMessage(response, json.toJSONString());
		}
	}
	
	
	@RequestMapping("/getPaylogs")
	public void getPaylogs(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String pageNumber = request.getParameter("pageNum");
		String searchType = request.getParameter("type");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String inviteCode = request.getParameter("inviteCode");
		String uuids = request.getParameter("uuid");
		String gameIds = request.getParameter("gameId");
//		System.out.println(startTime+"==============="+endTime);
		int pageNum = 1,type = 0,payType = 0,uuid = 0,gameId=0;
		if(pageNumber!=null){
			pageNum = Integer.parseInt(pageNumber);
		}
		if(searchType!=null){
			type = Integer.parseInt(searchType);
		}
		if(StringUtil.isInteger(uuids, 0, 0)){
			uuid = Integer.parseInt(uuids);
		}
		if(StringUtil.isInteger(gameIds, 0, 0)){
			gameId = Integer.parseInt(gameIds);
		}
		JSONObject json = new JSONObject();
		String levelStr = null;
		JSONArray array = new JSONArray();
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			String str ="00000000";
			  String str_m = manager.getId().toString();
			  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
			  
			String plevelStr = manager.getLevelStr();
			if(StringUtil.isNotEmpty(plevelStr)){
				levelStr = plevelStr+str_m;
			}else if(manager.getPowerId()>1){
				levelStr = str_m;
			}
			int startNum = (pageNum-1)*Params.pageNumber;
			int limit = Params.pageNumber;
			int size = 0;
			int totalMoney=0;
			double totalBonus=0;
			Date startdate = null;
			Date enddate = null;
			try {
				if(StringUtil.isNotEmpty(startTime)){
					startdate = new Date(sdf.parse(startTime).getTime());
				}
				
				if(StringUtil.isNotEmpty(endTime)){
					enddate = new Date(sdf.parse(endTime).getTime());
				}
				
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			int inputManagerId = 0;
			if(StringUtil.isInteger(inviteCode, 0, 0)){
				int inputInviteCode = Integer.parseInt(inviteCode);
				Manager inputManager = managerService.selectManagerByInvoteCode(inputInviteCode);
				if(inputManager!=null){
					  str_m = inputManager.getId().toString();
					  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
					  
					  String strm = manager.getId().toString();
					  strm=str.substring(0, 8-strm.length())+strm+"$";
					plevelStr = inputManager.getLevelStr();
					if(StringUtil.isNotEmpty(plevelStr)){
						levelStr = plevelStr+str_m;
					}else{
						levelStr = str_m;
					}
					
					inputManagerId = inputManager.getId();
					int inputManagerUpId = 0;
					if(inputManager.getManagerUpId()!=null&&inputManager.getManagerUpId()>1)
						inputManagerUpId = inputManager.getManagerUpId();
					if(manager.getPowerId()>1&&!plevelStr.contains(strm)&&manager.getId().intValue()!=inputManagerId){//不是系统管理员且,不是自己下属，且不是本人
						json.put("paylogs", null);
						json.put("totalNum", 0);
						json.put("totalMoney", 0);
						returnMessage(response, json.toString());
						return;
					}
				}
			}
			if(inputManagerId>0){
				size = paylogService.selectByMidTimeCount(inputManagerId, 0,levelStr,uuid,manager.getPid(),startNum,limit,startdate,enddate,gameId);
				totalMoney=paylogService.selectByMidTimeMoney(inputManagerId, 0,levelStr,uuid,manager.getPid(),startNum,limit,startdate,enddate,gameId);
				totalBonus=paylogService.selectByMidTimeBonus(inputManagerId, 0,levelStr,uuid,manager.getPid(),startNum,limit,startdate,enddate,gameId);
			}else{
				if(manager.getPowerId()>1){
					size = paylogService.selectByMidTimeCount(manager.getId(), 0,levelStr,uuid,manager.getPid(),startNum,limit,startdate,enddate,gameId);
					totalMoney=paylogService.selectByMidTimeMoney(manager.getId(), 0,levelStr,uuid,manager.getPid(),startNum,limit,startdate,enddate,gameId);
					totalBonus=paylogService.selectByMidTimeBonus(manager.getId(), 0,levelStr,uuid,manager.getPid(),startNum,limit,startdate,enddate,gameId);
				}
				else{
					size = paylogService.selectByMidTimeCount(0,0,null,uuid,manager.getPid(),startNum,limit,startdate,enddate,gameId);
					totalMoney = paylogService.selectByMidTimeMoney(0,0,null,uuid,manager.getPid(),startNum,limit,startdate,enddate,gameId);
				}
				
			}
//			int totalNum = (int) Math.ceil(size/(double)Params.pageNumber);
			json.put("totalNum", size);
			json.put("totalMoney", totalMoney);
			json.put("totalBonus", totalBonus);
			//获取所有玩家   
			SimpleDateFormat time=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"); 
			List<PaylogExt> paylogs =null;
			if(inputManagerId>0){
				paylogs = paylogService.selectByMidTime(inputManagerId, 0,levelStr,uuid,manager.getPid(),startNum , limit,startdate,enddate,gameId);
			}else{
			if(manager.getPowerId()>1)
			paylogs = paylogService.selectByMidTime(manager.getId(), 0,levelStr,uuid,manager.getPid(),startNum , limit,startdate,enddate,gameId);
			else
				paylogs = paylogService.selectByMidTime(0,type,null,uuid,manager.getPid(),startNum , limit,startdate,enddate,gameId);
			}
			for (PaylogExt m : paylogs) {
				m.setDateString(time.format(m.getPaytime()));
//				m.setNickName(accountService.selectByUuid(m.getUuid()).getNickname());
				array.add(m);
			}
			json.put("paylogs", array);
//			System.out.println("返回的字符串==========="+json.toString());
			returnMessage(response, json.toString());
		}
	}
	
	@RequestMapping("/getPaylogLots")
	public void getPaylogLots(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String pageNumber = request.getParameter("pageNum");
		String searchType = request.getParameter("type");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String inviteCode = request.getParameter("inviteCode");
		String uuidStr = request.getParameter("uuid");
//		System.out.println(startTime+"==============="+endTime);
		int pageNum = 1,type = 0,payType = 0;
		if(pageNumber!=null){
			pageNum = Integer.parseInt(pageNumber);
		}
		if(searchType!=null){
			type = Integer.parseInt(searchType);
		}
		JSONObject json = new JSONObject();
		
		JSONArray array = new JSONArray();
		Manager manager = (Manager) session.getAttribute("proxy");
		if(manager==null||request.getParameter("pageType")==null)
		manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			int startNum = (pageNum-1)*Params.pageNumber;
			int limit = Params.pageNumber;
			int size = 0;
			int mountSum = 0;
			Date startdate = null;
			Date enddate = null;
			try {
				if(StringUtil.isNotEmpty(startTime)){
					startdate = new Date(sdf.parse(startTime).getTime());
				}
				
				if(StringUtil.isNotEmpty(endTime)){
					enddate = new Date(sdf.parse(endTime).getTime());
				}
				
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			int inputManagerId = 0,inputUuid=0;
			if(StringUtil.isInteger(inviteCode, 0, 0)){
				int inputInviteCode = Integer.parseInt(inviteCode);
				Manager inputManager = managerService.selectManagerByInvoteCode(inputInviteCode);
				if(inputManager!=null){
					inputManagerId = inputManager.getId();
					int inputManagerUpId = 0;
					if(inputManager.getManagerUpId()!=null&&inputManager.getManagerUpId()>1)
						inputManagerUpId = inputManager.getManagerUpId();
					if(manager.getPowerId()>1&&manager.getId().intValue()!=inputManagerUpId&&manager.getId().intValue()!=inputManagerId){//不是系统管理员且,不是自己下属，且不是本人
						json.put("paylogLots", null);
						json.put("totalNum", 0);
						returnMessage(response, json.toString());
						return;
					}
				}
			}
			if(StringUtil.isInteger(uuidStr, 0, 0)){
				inputUuid = Integer.parseInt(uuidStr);
				Account inputaAccount = accountService.selectByUuid(inputUuid);
				if(inputaAccount!=null){
					inputUuid = inputaAccount.getUuid();
					int inputManagerUpId = 0;
					if(inputaAccount.getManagerUpId()!=null&&inputaAccount.getManagerUpId()>1)
						inputManagerUpId = inputaAccount.getManagerUpId();
					if(manager.getPowerId()>1&&manager.getId().intValue()!=inputManagerUpId&&manager.getId().intValue()!=inputaAccount.getManagerId()){//不是系统管理员且,不是自己下属，且不是本人
						json.put("paylogLots", null);
						json.put("totalNum", 0);
						returnMessage(response, json.toString());
						return;
					}
				}
			}
			if(inputManagerId>0){
				size = paylogLotService.selectByMidTimeCount(inputManagerId, 0,startNum,limit,startdate,enddate,inputUuid);
				mountSum = paylogLotService.selectByMidTimeSum(inputManagerId, 0,startNum,limit,startdate,enddate,inputUuid);
			}else{
					if(manager.getPowerId()>1){
						size = paylogLotService.selectByMidTimeCount(manager.getId(), 0,startNum,limit,startdate,enddate,inputUuid);
						mountSum = paylogLotService.selectByMidTimeSum(manager.getId(), 0,startNum,limit,startdate,enddate,inputUuid);
						}
						else{
							size = paylogLotService.selectByAllTimeCount(0,startNum,limit,startdate,enddate,inputUuid);
							mountSum = paylogLotService.selectByAllTimeSum(0,startNum,limit,startdate,enddate,inputUuid);
						}
			}
			
			
			
			int totalNum = (int) Math.ceil(size/(double)Params.pageNumber);
			json.put("totalNum", size);
			json.put("mountSum", mountSum);
			//获取所有玩家   
			SimpleDateFormat time=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"); 
			List<PayloglotExt> paylogLots =null;
			
			if(inputManagerId>0){
				paylogLots = paylogLotService.selectByMidTime(inputManagerId, 0,startNum , limit,startdate,enddate,inputUuid);
			}else{
			if(manager.getPowerId()>1)
			paylogLots = paylogLotService.selectByMidTime(manager.getId(), 0,startNum , limit,startdate,enddate,inputUuid);
			else
				paylogLots = paylogLotService.selectByAllTime(type,startNum , limit,startdate,enddate,inputUuid);
			}
			for (PayloglotExt m : paylogLots) {
				m.setDateString(time.format(m.getCreatetime()));
				array.add(m);
			}
			
			json.put("paylogLots", array);
//			System.out.println("返回的字符串==========="+json.toString());
			returnMessage(response, json.toString());
		}
	}
	
	@RequestMapping("/withdrawlogs")
	public void withdrawlogs(HttpServletRequest request, HttpServletResponse response) {
		
		
		HttpSession session = request.getSession();
		String pageNumber = request.getParameter("pageNum");
		String searchType = request.getParameter("type");
		String inpayType = request.getParameter("payType");
		String inviteCode = request.getParameter("inviteCode");
		int pageNum = 1,type = 0,payType = 0;
		if(pageNumber!=null){
			pageNum = Integer.parseInt(pageNumber);
		}
		if(searchType!=null){
			type = Integer.parseInt(searchType);
		}
		if(inpayType!=null){
			payType = Integer.parseInt(inpayType);
		}
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			int inputManagerId = 0,size=0;
			if(StringUtil.isInteger(inviteCode, 0, 0)){
				int inputInviteCode = Integer.parseInt(inviteCode);
				Manager inputManager = managerService.selectManagerByInvoteCode(inputInviteCode);
				int inputUpId = 0;
				if(inputManager!=null){
					inputManagerId = inputManager.getId();
					if(inputManager.getManagerUpId()!=null&&inputManager.getManagerUpId()>1)
						inputUpId = inputManager.getManagerUpId();
					if(manager.getPowerId()>1&&(inputUpId!=manager.getId().intValue())&&inputManagerId!=manager.getId().intValue()){//不为系统管理员且不是所属代理
						json.put("paylogs", null);
						json.put("totalNum", 0);
						json.put("tixianSum", 0);
						returnMessage(response, json.toString());
						return;
					}
				}
			}
			if(inputManagerId>0){
				size = paylogService.selectSumByManagerId(inputManagerId, type,payType);
			}else{
				size = paylogService.selectSumByManagerId(manager.getId(), type,payType);
			}
			
			json.put("totalNum", size);
			//获取所有玩家   
			int startNum = (pageNum-1)*Params.pageNumber;
			int limit = Params.pageNumber;
			SimpleDateFormat time=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"); 
			List<Paylog> paylogs = null;
			double tixianSum=0.00;
			if(inputManagerId>0){
				paylogs = paylogService.selectByManagerId(inputManagerId, type,payType,startNum , limit);
				tixianSum = paylogService.selectTixianSum(inputManagerId, type);
			}
			else{
				paylogs = paylogService.selectByManagerId(manager.getId(), type,payType,startNum , limit);
				tixianSum = paylogService.selectTixianSum(manager.getId(), type);
			}
				
			for (Paylog m : paylogs) {
				m.setDateString(time.format(m.getPaytime()));
				int mid = m.getManagerid();
				if(managerNodeCache.containsKey(mid+"_"+manager.getPid()))
				m.setManagerid(managerNodeCache.get(mid+"_"+manager.getPid()).getInviteCode());
				array.add(m);
			}
			json.put("tixianSum", tixianSum);
			json.put("paylogs", array);
//			System.out.println("返回的字符串==========="+json.toString());
			returnMessage(response, json.toString());
		}
		
		
		
	}
	
	
	@RequestMapping("/getPaylogsSum")
	public void getPaylogsSum(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String searchType = request.getParameter("type");
		String managerIdIn = request.getParameter("inviteCode");
		Manager managerin = null;
		int type = 0,managerId=0,powerId = 0;
		String levelStr = null;
		if(searchType!=null){
			type = Integer.parseInt(searchType);
		}
		double rebate = 0.0;
		if(StringUtil.isInteger(managerIdIn, 0, 0)){
//			System.out.println("managerIdIn===="+managerIdIn);
			int inviteCode = Integer.parseInt(managerIdIn);
			managerin = managerService.selectManagerByInvoteCode(inviteCode);
			if(managerin!=null){
				managerId = managerin.getId();
				powerId = managerin.getPowerId();
				String str ="00000000";
				  String str_m = managerin.getId().toString();
				  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
				  
				String plevelStr = managerin.getLevelStr();
				rebate = Double.parseDouble(managerin.getRebate());
				if(StringUtil.isNotEmpty(plevelStr)){
					levelStr = plevelStr+str_m;
				}else{
					levelStr = str_m;
				}
			}
		}
		JSONObject json = new JSONObject();
		
		Manager manager = (Manager) session.getAttribute("proxy");
		if(manager==null||request.getParameter("pageType")==null)
		manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			//获取所有玩家   
			if(levelStr==null){
				String str ="00000000";
				
				  String str_m = manager.getId().toString();
				  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
				  
				String plevelStr = manager.getLevelStr();
				rebate = Double.parseDouble(manager.getRebate());
				if(StringUtil.isNotEmpty(plevelStr)){
					levelStr = plevelStr+str_m;
				}else{
					levelStr = str_m;
				}
			}
			
			if(manager.getPowerId()!=1&&managerId==0){//一般代理查询
				
				powerId = manager.getPowerId();
				managerId = manager.getId();
					
			}
			
			if(managerin!=null){
				manager = managerin;
				powerId = manager.getPowerId();
				managerId = manager.getId();
			}
			
			
			
			int mineone = paylogService.sumByManagerId2(managerId, type);
			int minelotone = paylogLotService.selectNextOneMoney2(managerId,type);
//			if(manager.getPowerId()==5||manager.getPowerId()==4)//总代理
			double minetwo = paylogService.sumSubByManagerId2(managerId, type,levelStr,rebate);
			int minelottwo = paylogLotService.selectNextTwoMoney2(managerId,powerId,type);
			double n1 = 0,w1 = 0, w2 = 0;
			if(StringUtil.isNotEmpty(manager.getRebate())){
				String[] nums = manager.getRebate().split(":|,");
				n1 = Double.parseDouble(nums[0]);
//				n2 = Double.parseDouble(nums[1]);
//				w1 = Double.parseDouble(nums[2]);
//				w2 = Double.parseDouble(nums[3]);
			}else{
//				if(managerin!=null)
//					n1 = Params.getPercentage1(managerin.getPowerId());
//				else
					n1 = Params.getPercentage1(powerId);
//				n2 = Params.getPercentage2(powerId);
				
			}
			w1 = Params.getPercentagelot1(powerId);
			w2 = Params.getPercentagelot2(powerId);
			DecimalFormat    df   = new DecimalFormat("######0.00");   	
			String oneString = "￥"+df.format(mineone*n1)+"("+100*n1+"%)";
			String twoString = "￥"+minetwo+"("+100*0+"%)";
			String onelotString = "￥"+ minelotone +"("+100*w1+"%)";
			String twolotString = "￥"+ minelottwo +"("+100*w2+"%)";
			
			String total = "分成："+df.format(mineone*n1+minetwo+minelotone*w1+minelottwo*w2);
			json.put("mineone", oneString);
			json.put("minetwo", twoString);
			json.put("minelotone", onelotString);
			json.put("minelottwo", twolotString);
			json.put("total", total);
			System.out.println("返回的字符串==========="+json.toString());
			returnMessage(response, json.toString());
		}
	}
	
	@RequestMapping("/tixian")
	public void tixian(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		Manager manager = (Manager) session.getAttribute("manager");
		String tixianMount = request.getParameter("mount");
		String levelStr = null;
		double rebate = 0.0,amount=0.0;
		synchronized(this){
		if(manager != null){
			Calendar today = Calendar.getInstance();
			int day = today.get(Calendar.DAY_OF_YEAR);
			int managerId = manager.getId();
			if(tixianDayLog.containsKey(managerId)){
				String dayTimes = tixianDayLog.get(managerId);
				int times = Integer.parseInt(dayTimes.substring(dayTimes.indexOf("-")+1));
				int existDay = Integer.parseInt(dayTimes.substring(0,dayTimes.indexOf("-")));
				if(times>=maxTixianTimes&&existDay==day){
				json.put("error", "你今天已经提现过，请不要频繁操作！如有问题，请联系系统管理员");
				returnMessage(response, json.toString());
				return;
				}
			}
			int powerId = manager.getPowerId();
			
			String str ="00000000";
			  String str_m = manager.getId().toString();
			  str_m=str.substring(0, 8-str_m.length())+str_m+"$";
			  
			String plevelStr = manager.getLevelStr();
			rebate = Double.parseDouble(manager.getRebate());
			if(StringUtil.isNotEmpty(plevelStr)){
				levelStr = plevelStr+str_m;
			}else{
				levelStr = str_m;
			}
			
			double n1 = 0,w1 = 0, w2 = 0;
			if(StringUtil.isNotEmpty(manager.getRebate())){
				String[] nums = manager.getRebate().split(":|,");
				n1 = Double.parseDouble(nums[0]);
//				n2 = Double.parseDouble(nums[1]);
//				w1 = Double.parseDouble(nums[2]);
//				w2 = Double.parseDouble(nums[3]);
			}else{
				n1 = Params.getPercentage1(powerId);
//				n2 = Params.getPercentage2(powerId);
				
			}
			w1 = Params.getPercentagelot1(powerId);
			w2 = Params.getPercentagelot2(powerId);
			
			//获取所有玩家   
			int mineone = paylogService.sumByManagerId(managerId);
			double minetwo = 0;
			double remain = paylogService.remainByManagerId(managerId);
			System.out.println("remain====="+remain);
			minetwo = paylogService.sumSubByManagerId(manager.getId(),levelStr,rebate);
			
			int minelotone = paylogLotService.selectNextOneMoney(managerId);
			int minelottwo = paylogLotService.selectNextTwoMoney(managerId,manager.getPowerId());
//			double total = mineone*Params.getPercentage1(manager.getPowerId())+minetwo*Params.getPercentage2(manager.getPowerId());
			double total1 = mineone*n1+minetwo;
			double total2 = minelotone*w1+minelottwo*w2;
			double total = total1+total2+remain;
			if(StringUtil.isDouble(tixianMount, 0, 0)){
				amount = Double.parseDouble(tixianMount);
			}
			if(amount>total){
				json.put("error", "超出收益！");
			}else if(amount>5000){
				json.put("error", "提现金额超过5000！请联系系统管理员！");
			}else if(amount>99.99&&amount<=5000){
			 Paylog paylog = new Paylog();
			 java.util.Date now = new java.util.Date();
	            paylog.setManagerid(managerId);
	            paylog.setMoney(amount);
	            paylog.setPaycount(0);
	            paylog.setPaytime(now);
	            paylog.setPaytype(1);//0是用户充值，1是代理提现
	            paylog.setStatus(0);//0为正常状态，1为已经处理的状态
	            paylog.setUuid(manager.getId());
	            paylog.setGameId(1);
	            int result = paylogService.insertPaylog(paylog);
//	            Paylog paylog2 = new Paylog();
//	            paylog2.setManagerid(managerId);
//	            paylog2.setMoney(total2);
//	            paylog2.setPaycount(0);
//	            paylog2.setPaytime(now);
//	            paylog2.setPaytype(1);//0是用户充值，1是代理提现2是红钻提现
//	            paylog2.setStatus(0);//0为正常状态，1为已经处理的状态
//	            paylog2.setUuid(manager.getId());
//	            paylog2.setGameId(2);
//	            result += paylogService.insertPaylog(paylog2);
	            Paylog paylog3 = new Paylog();
	            paylog3.setManagerid(managerId);
	            paylog3.setMoney(total-amount);
	            paylog3.setPaycount(0);
	            paylog3.setPaytime(now);
	            paylog3.setPaytype(9);//0是用户充值，1是代理提现2是红钻提现
	            paylog3.setStatus(1);//0为正常状态，1为已经处理的状态
	            paylog3.setUuid(manager.getId());
	            paylog3.setGameId(0);
//	            result += paylogService.insertPaylog(paylog3);
	            
	            if(result>0){
	            	Account account = accountService.selectByManagerId(managerId);
	            	String ip = getIpAddress(request);
	            	TixianCallback cb = new TixianCallback();
	            	cb.setPaylog(paylog);
//	            	cb.setPaylog2(paylog2);
	            	cb.setPaylog3(paylog3);
	            	cb.setPaylogService(paylogService);
	            	cb.setManagerId(manager.getId());
//	            	int tixianStatus = 1;
//	            	if(powerId==2)
//	            		tixianStatus = 1;
//	            	else if(powerId==2){
//	            		
//	            	}
	            	if(manager.getManagerUpId()!=null||manager.getManagerUpId()<1)//如果没有父级代理，则所有的可提现的都被封死
	            		powerId = 5;
	            	cb.setTixianStatus(powerId);
	            	//
	            	WithdrawCash.doTransfer(account.getOpenid(), manager.getName(), (float)amount, "这是一次转账请求", ip, cb);
//	            	cb.setPaylog(paylog2);
//	            	cb.onSuccess("", "", "");
//	            	WithdrawCash.doTransfer("oBmF40ktpoBu3w7MQ1PkCcFpQO6Q", "赖西湖", 1f, "这是一次转账请求", "221.223.235.121", cb);
//	            	paylog.setStatus(1);
//	            	paylogService.tixianDone(paylog.getId(),2);
	            	if(cb.isResult()){
	            	json.put("total", amount);
	            	DecimalFormat    df   = new DecimalFormat("######0.00"); 
	            	json.put("info", "你的提现人民币"+df.format(amount)+"元的请求已经发出！请留意你的微信转账记录！");
	            	if(tixianDayLog.containsKey(managerId)){
	            		String dayTimes = tixianDayLog.get(managerId);
	    				int existDay = Integer.parseInt(dayTimes.substring(0,dayTimes.indexOf("-")));
	    				if(existDay==day){
	    					tixianDayLog.put(managerId, day+"-2");
	    				}else{
	    					tixianDayLog.put(managerId, day+"-1");
	    				}
	            	}else{
	            		tixianDayLog.put(managerId, day+"-1");
	            	}
	            	}else{
	            		json.put("error", "你提交的个人账务信息错误，请和系统管理员联系！");
	            	}
//	            	System.out.println("返回的字符串==========="+json.toString());
	            }else{
	            	json.put("error", "提现请求失败！");
//	            	System.out.println("返回的字符串==========="+json.toString());
	            }
			}else{
				json.put("error", "你可以提现的金额不足100!");
			}
			returnMessage(response, json.toString());
		}
		}
	}
	

	/**
	 *超级管理员可以为所有用户充值房卡
	 *零售商只为玩家充值
	 *代理商只为零售商充值
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateAgentAccount")
	public void updateAgentAccount(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		if(session!=null){
			Manager manager = (Manager) session.getAttribute("manager");
			int managerId = manager.getId();
			int agentId = Integer.parseInt(request.getParameter("num"));
			String paycardnum = request.getParameter("payCardNum");
			String payRedCardNum = request.getParameter("payRedCardNum");
			String isAgent = request.getParameter("isAgent");
			JSONObject json = new JSONObject();
			Map<String,Object> mapparam = new HashMap<String,Object>();
			mapparam.put("id", agentId);
			mapparam.put("managerId", managerId);
			mapparam.put("isAgent", isAgent);
			Roomcardlog record = new Roomcardlog();
			Generallog generallog = new Generallog();
			generallog.setManagerid(manager.getId());
			generallog.setUuid(agentId);
			
			generallog.setCreatetime(new java.util.Date());
			generallog.setType(1);//0为给用户充钻石，1为给代理充钻石，2为修改代理信息，3为新增代理
			record.setRoomcard(Integer.parseInt(paycardnum));
			record.setCreatetime(new java.util.Date());
			record.setManagerid(managerId);
			record.setAccountid(agentId);
			
			int addedCard = 0;
			int redAddedCard = 0;
			String content="";
			if(StringUtil.isInteger(paycardnum, 0, 0)){
				addedCard= Integer.parseInt(paycardnum);
				mapparam.put("roomcard", addedCard);
				content+="给代理充蓝钻"+addedCard+"颗";
			}
			if(StringUtil.isInteger(payRedCardNum, 0, 0)){
				redAddedCard= Integer.parseInt(payRedCardNum);
				mapparam.put("redCard", redAddedCard);
				content+="给代理充红钻"+redAddedCard+"颗";
			}
			generallog.setContent(content);
			if(manager.getActualcard()>=addedCard&&manager.getRedCard()>=redAddedCard){
				manager.setActualcard(manager.getActualcard()-addedCard);
				manager.setRedCard(manager.getRedCard()-redAddedCard);
				accountService.updateRoomCard(mapparam);
				roomcardlogService.insert(record);
				generallogService.insert(generallog);
				json.put("status", "0");
				json.put("msg", "充值成功！");
			}else{
				json.put("status", "1");
				json.put("msg", "账户剩下钻石不够！");
			}
				
			returnMessage(response,json);

		}
	}
	
	@RequestMapping("/updateAccount")
	public void updateAccount(HttpServletRequest request, HttpServletResponse response){
		int userid = Integer.parseInt(request.getParameter("userid"));
		String inviteCode = request.getParameter("inviteCode");
		String paycardnum = request.getParameter("payCardNum");
		String payRedCardNum = request.getParameter("payRedCardNum");
		Manager manager = null;
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		if(session != null){
			manager = (Manager) session.getAttribute("manager");
//			if(manager.getPowerId()==1||manager.getRootManager()==1){
				if(StringUtil.isInteger(inviteCode, 0, 0)){
					int ininviteCode = Integer.parseInt(inviteCode);
					Manager bangding = managerService.selectManagerByInvoteCode(ininviteCode);
					if(bangding!=null){
						int managerUpId = bangding.getId();
						Map<String,Object> mapparam = new HashMap<String,Object>();
						Account account1 = accountService.selectByUuid(userid);
						mapparam.put("id", account1.getId());
						mapparam.put("managerUpId", managerUpId);
						accountService.updateAccountStatus(mapparam);
						json.put("inviteCode", 1);
						json.put("msg", "操作成功！");
					}
				}
//			}
				int payCardNum = Integer.parseInt(paycardnum);
				int managerId = manager.getId();
				Account account = accountService.selectByUuid(userid);
				Map<String,Object> mapparam = new HashMap<String,Object>();
				Generallog generallog = new Generallog();
				generallog.setManagerid(manager.getId());
				generallog.setCreatetime(new java.util.Date());
				generallog.setUuid(userid);
				generallog.setType(0);//0为给用户充钻石，1为给代理充钻石，2为修改代理信息，3为新增代理
				
				int addedCard = 0;
				int redAddedCard = 0;
				String content = "";
				if(StringUtil.isInteger(paycardnum, 0, 0)){
					addedCard= Integer.parseInt(paycardnum);
					mapparam.put("roomcard", addedCard);
					content+="给用户充蓝钻"+addedCard+"颗";
				}
				if(StringUtil.isInteger(payRedCardNum, 0, 0)){
					redAddedCard= Integer.parseInt(payRedCardNum);
					mapparam.put("redCard", redAddedCard);
					content+="给用户充红钻"+redAddedCard+"颗";
				}
				mapparam.put("id", account.getId());
				generallog.setContent(content);
				Roomcardlog record = new Roomcardlog();
				record.setManagerid(managerId);
				record.setAccountid(userid);
				record.setRoomcard(payCardNum);
				record.setCreatetime(new java.util.Date());
				if(manager.getActualcard()>=addedCard&&manager.getRedCard()>=redAddedCard){
					manager.setActualcard(manager.getActualcard()-addedCard);
					manager.setRedCard(manager.getRedCard()-redAddedCard);
					mapparam.put("managerId", managerId);
					accountService.updateRoomCard(mapparam);
					roomcardlogService.insert(record);
					generallogService.insert(generallog);
					json.put("status", "0");
					json.put("msg", "充值成功！");
				}else{
					json.put("status", "1");
					json.put("msg", "账户剩下钻石不够！");
				}
		}
		else{
//			System.out.println("未建立链接就进行充值的非法操作");
			json.put("status", "1");
			json.put("msg", "未建立链接就进行充值的非法操作");	
		}
		returnMessage(response,json);
	}
	

	/**
	 * 删除代理商用户
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteProxyAccount")
	public void deleteProxyAccount(HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		int result = managerService.deleteByPrimaryKey(id);
		if(result>0)
		managerService.updateByPrimaryKeyUpdateChild(id);
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		Manager manager = (Manager) session.getAttribute("manager");
		if(result > 0){
			for(Entry<String,Long> cacheentry:cacheTimestamp.entrySet()){
				long value = cacheentry.getValue();
				if(cacheentry.getKey().contains("_"+manager.getPid()))
				cacheentry.setValue(value-1000*60*5);
			}	
			
			json.put("mess", "删除代理商用户成功");
//			System.out.println("删除代理商用户成功");
		}else{
			json.put("mess", "删除代理商用户失败");
		}
		returnMessage(response,json);
	}
	
	
	
	
	@RequestMapping("/validUuid")
	public void validUuid(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		String code = request.getParameter("uuid");
		if(StringUtil.isInteger(code, 0, 0)){//不为空
			int inviteCode = Integer.parseInt(code);
			Account result = accountService.selectByUuid(inviteCode);
			if(result != null&&result.getManagerId()==0){
				json.put("valid", true);
			}else{
				json.put("valid", false);
			}
			returnMessage(response,json);
		}
	}
	
	
	@RequestMapping("/inviteCodeValid")
	public void inviteCodeValid(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		String code = request.getParameter("inviteCode");
		String mid = request.getParameter("id");
		int id = 0;
		if(StringUtil.isInteger(mid, 0, 0))
			id = Integer.parseInt(mid);
		if(StringUtil.isInteger(code, 0, 0)){//不为空
		int inviteCode = Integer.parseInt(code);
		Manager result = managerService.selectManagerByInvoteCode(inviteCode);
		
		
		if(result !=null&&result.getId()!=id){
			json.put("valid", false);
		}else{
			json.put("valid", true);
		}
		}
		else{
			json.put("valid", true);
		}
		returnMessage(response,json);
	}
	
	@RequestMapping("/getParentInvite")
	public void getParentInvite(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		String iuuid = request.getParameter("uuid");
		Manager result = null;
		int uuid = 0;
		if(StringUtil.isInteger(iuuid, 0, 0))
			uuid = Integer.parseInt(iuuid);
		if(uuid>10000){//不为空
			Account account = accountService.selectByUuid(uuid);
			Integer managerUpId = account.getManagerUpId();
			if(managerUpId!=null&&managerUpId.intValue()>0)
				result = managerService.selectByPrimaryKey(managerUpId.intValue());
		
		if(result !=null){
			json.put("inviteCode", result.getInviteCode());
			json.put("powerId", result.getPowerId());
		}else{
			json.put("inviteCode", "");
		}
		}
		else{
			json.put("inviteCode", "");
		}
		returnMessage(response,json);
	}
	
	@RequestMapping("/getParentInviteByChild")
	public void getParentInviteByChild(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		String inviteCodeStr = request.getParameter("inviteCode");
		Manager result = null;
		int inviteCode = 0;
		if(StringUtil.isInteger(inviteCodeStr, 0, 0))
			inviteCode = Integer.parseInt(inviteCodeStr);
		    result = managerService.selectManagerByInvoteCode(inviteCode);
			if(result !=null){
				Integer managerUpId = result.getManagerUpId();
				if(managerUpId!=null&&managerUpId>0){
					Manager upm = managerService.selectByPrimaryKey(managerUpId);
					if(upm!=null){
						json.put("inviteCode", upm.getInviteCode());
						json.put("powerId", upm.getPowerId());
					}
				}
				
			}else{
				json.put("inviteCode", "");
			}
		returnMessage(response,json);
	}

	

	/**
	 * 发送公告，通知游戏后台，发送公告
	 * @param request
	 * @param response
	 */
	@RequestMapping("/sendNotice")
	public void sendNotice(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		if(session != null){
			Manager manager = (Manager) session.getAttribute("manager");
			if(manager != null){
				String notice = request.getParameter("notice");
				String type = request.getParameter("type");
				String inviteCode = request.getParameter("inviteCode");
				if(StringUtil.isNotEmpty(notice)&&StringUtil.isInteger(type, 0, 0)){
				try {
					NoticeTable noticeTable = new NoticeTable();
					int noticeType = Integer.parseInt(type);
					noticeTable.setType(noticeType);
					noticeTable.setContent(notice);
					if(StringUtil.isInteger(inviteCode, 0, 0)){
						Manager managerInput = managerService.selectManagerByInvoteCode(Integer.parseInt(inviteCode));
						if(managerInput!=null)
						noticeTable.setManagerId(managerInput.getId());
					}
					noticeTableService.insertSelective(noticeTable);
					json.put("status_code", "0");
			        json.put("info", "保存成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.put("status_code", "1");
			        json.put("error", "异常情况");
				}
				}
			}
		}
		returnMessage(response,json);
	}


	
	/**
	 *只能根据准确的uuid搜索到玩家，
	 * @param request
	 * @param response
	 */
	@RequestMapping("/searchAccountByUuid ")
	public void searchAccountByUuid(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		String accountUuid = request.getParameter("accountUuid");
		if(session != null){
			Manager manager = (Manager) session.getAttribute("manager");
			if(manager != null){
				if(StringUtil.isNotEmpty(accountUuid) && StringUtil.isInteger(accountUuid, 0, 0)){
					try {
						int managerUpId = 0;
						Account account = accountService.selectByUuid(Integer.parseInt(accountUuid));
						if(account!=null&&account.getManagerUpId()!=null&&account.getManagerUpId()>1){
						managerUpId = account.getManagerUpId();
						Manager up = managerService.selectByPrimaryKey(managerUpId);
						account.setManagerUpId(up.getInviteCode());
						}
						if(manager.getPowerId()>1&&manager.getPowerId()<5&&managerUpId!=manager.getId().intValue()&&manager.getUuid()!=Integer.parseInt(accountUuid)){//不为超级管理员且不是自己下面的用户也不是总代
							account = null;
						}
						if(account != null){
							json.put("status_code", "0");
							json.put("data", account);
							json.put("type", manager.getPowerId());
						}
						else{
							json.put("status_code", "1");
							json.put("error", "用户不存在");
						}
					} catch (Exception e) {
						e.printStackTrace();
						json.put("status_code", "1");
						json.put("error", "异常情况");
					}
				}
				else{
					json.put("status_code", "1");
					json.put("error", "错误的玩家id");
				}
			}
			else{
				System.out.println("管理员未登录，请登录");
			}
		}
		returnMessage(response,json);
	}
	/**
	 *只能根据准确的手机号码搜索到代理商
	 * @param request
	 * @param response
	 */
	@RequestMapping("/searchManagerByTel ")
	public void searchManagerByTel(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONArray array = new JSONArray();
		String managerTel = request.getParameter("managerTel");
		if(session != null){
			Manager manager = (Manager) session.getAttribute("manager");
			if(manager != null){
				if(StringUtil.isNotEmpty(managerTel) && StringUtil.isPhone(managerTel)){
					try {
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("telephone", managerTel);
						if(manager.getId() != 1){
							map.put("managerUpId", manager.getId());
						}
						List<Manager> searchManager = managerService.selectManagerByTel(map);
						if(!searchManager.isEmpty()){
							for (Manager m : searchManager) {
								array.add(m);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		returnMessage(response,array);
	}
	
	public final static String getIpAddress(HttpServletRequest request){  
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  

            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    }
	
	
	@RequestMapping("/getManagersCount")
	public void getManagersCount(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		int stype = 0;
		int pageNum = 1;
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			Map<String , Object> map = new HashMap<String, Object>();
			int size = 0;
			int startNum = (pageNum-1)*Params.pageNumber;
			map.put("pageNumber", Params.pageNumber);
			map.put("startNum", startNum);
			if(manager.getPid()!=null&&manager.getPid()!=0)
			map.put("pid", manager.getPid());
			List<ManagerNode> managerTree = null;
			Map<Integer,String> parentPath = null;
			if(cacheTimestamp.containsKey(stype+"_"+manager.getPid())){
				long cacheTime = cacheTimestamp.get(stype+"_"+manager.getPid());
				long now = new java.util.Date().getTime();
				if((now-cacheTime)<(1000*60*5)){//直接取缓存
					managerTree = managerCache.get(stype+"_"+manager.getPid());
					parentPath = pathCache.get(stype+"_"+manager.getPid());
				}
			}
			if(managerTree==null){
			List<Manager> managers = managerService.selectManagerInfoByMap(map,stype);
			size = managers.size();//需要加载全部的树
				managerTree = new ArrayList<ManagerNode>();
				List<Manager> waitting2Tree = new ArrayList<Manager>();
				parentPath = new HashMap<Integer,String>();
				for(Manager curManager:managers){
					if(curManager.getManagerUpId()!=null&&curManager.getManagerUpId()>1){//为存在父节点的代理
						waitting2Tree.add(curManager);
					}else{//已是根节点，直接放入树中
						ManagerNode node1 = new ManagerNode(curManager);
						managerNodeCache.put(curManager.getId()+"_"+manager.getPid(), node1);
						managerTree.add(node1);
						parentPath.put(curManager.getId(), ""+(managerTree.size()-1));
					}
				}
				int allSize = waitting2Tree.size();
				int iterNum = allSize;
				for(int k=0;k<5;k++){
				for(int i=0;i<allSize;i++){
					if(iterNum==0)
						break;
					if(waitting2Tree.size()>i&&waitting2Tree.get(i)!=null){//存在且不为空对象
						int mid = waitting2Tree.get(i).getManagerUpId();
						if(parentPath.containsKey(mid)){
						String path = parentPath.get(mid);
						String[] paths = path.split("-");
						List<ManagerNode> curNode = managerTree;
						for(String curPath:paths){
							int curPathI = Integer.parseInt(curPath);
							curNode = curNode.get(curPathI).getChildagent();
						}
						ManagerNode node1 = new ManagerNode(waitting2Tree.get(i));
						managerNodeCache.put(waitting2Tree.get(i).getId()+"_"+manager.getPid(), node1);
						curNode.add(node1);//加入到子树列表
						parentPath.put(waitting2Tree.get(i).getId(), path+"-"+(curNode.size()-1));
						waitting2Tree.set(i, null);
						iterNum--;
						if(iterNum==0)
							break;
						}
					}
				}
				}
				cacheTimestamp.put(stype+"_"+manager.getPid(), new java.util.Date().getTime());
				managerCache.put(stype+"_"+manager.getPid(), managerTree);
				pathCache.put(stype+"_"+manager.getPid(), parentPath);
				
			}
				if(manager.getPowerId() == 1){//超级管理员是所有的,每次查询一页记录
				size = managerTree.size();
				for(int i=0;i<size;i++){
					if(i>=startNum&&i<(startNum+Params.pageNumber))
					array.add(managerTree.get(i));
				}
				}else{
					String path = parentPath.get(manager.getId());
					String[] paths = path.split("-");
					List<ManagerNode> curNode = managerTree;
					for(String curPath:paths){
						int curPathI = Integer.parseInt(curPath);
						curNode = curNode.get(curPathI).getChildagent();
					}
					size = curNode.size();
					for (ManagerNode m : curNode){
						array.add(m);
					}
					
				}

//				System.out.println("getManagers+++============"+array.toJSONString());
//			}
			//获取所有玩家   
//			int totalNum = (int) Math.ceil(size/(double)Params.pageNumber);
			json.put("totalNum", size);
			
			json.put("managers", array);
			
//			System.out.println("getManagers============"+json.toJSONString());
			returnMessage(response, json.toJSONString());
		}
	}
	
	@RequestMapping("/insertLotResult ")
	public void insertLotResult(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		String lotResult = request.getParameter("lotResult");
		String serialNumStr = request.getParameter("serialNum");
			if(session != null){
				int serialNum = Integer.parseInt(serialNumStr);
				Manager manager = (Manager) session.getAttribute("manager");
				if(manager != null && manager.getId() == 1){
						ResultLot re = resultLotService.selectBySerialNum(serialNum);
						if(re!=null){
							json.put("res", 0);
							returnMessage(response,json);
							return;
						}else{
							ResultLot resultLot = new ResultLot();
							resultLot.setSerialnum(serialNum);
							resultLot.setResult(lotResult);
							resultLot.setLotid(0);
							resultLot.setOpentime(new java.util.Date());
							int res = resultLotService.insert(resultLot);
							json.put("res", res);
							if(res>0){
								//增加开奖处理
								int point1=0,point2=0,point3=0;
								point1 = Integer.parseInt(lotResult.substring(0,1));
								point2 = Integer.parseInt(lotResult.substring(2,3));
								point3 = Integer.parseInt(lotResult.substring(4,5));
								int result = point1+point2+point3;//大小单双分别是1，2，3，4
								int type1 = 0,type2 = 0,type3=0,type4=0,type5=0,type6=0;
								if(result%2==0)
									type2 = 4;
								else
									type2 = 3;
								if(result>13)
									type1=1;
								else
									type1=2;
								if(type1==1){
									if(type2==3)
										type3=51;
									else
										type3=52;
								}else{
									if(type2==3)
										type3=53;
									else
										type3=54;
								}
								type4=10+point1;
								type5=20+point2;
								type6=30+point3;
								Map<String,Object> map = new HashMap<>();
								map.put("serialNum", serialNum);
								map.put("lotId", 0);
								map.put("type1", type1);
								map.put("type2", type2);
								map.put("type3", type3);
								map.put("type4", type4);
								map.put("type5", type5);
								map.put("type6", type6);
								map.put("lotResult", lotResult);
								resultLotService.openLotProcess(map);
						}
					
					}
				}
			}
		
		
		returnMessage(response,json);
	}
	
	@RequestMapping("/getParentInviteById ")
	public void getParentInviteById(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");
		if(session != null){
			Manager manager = (Manager) session.getAttribute("manager");
			if(manager != null){
					if(StringUtil.isInteger(id, 0, 0) ){
						Manager res = managerService.selectByPrimaryKey(Integer.parseInt(id));
						if(res!=null){
							json.put("inviteCode", res.getInviteCode());
							json.put("powerId", res.getPowerId());
							json.put("status", 1);
						}
					}
			}
		}
		returnMessage(response,json);
	}
	
	@RequestMapping("/changeAccountStatusByUuid")
	public void changeAccountStatusByUuid(HttpServletRequest request, HttpServletResponse response){
		int uuid = Integer.parseInt(request.getParameter("uuid"));
		String status = request.getParameter("status");
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		if(session != null){
			Manager manager = (Manager) session.getAttribute("manager");
			if(manager != null){
				if(manager.getPowerId()==1||manager.getPowerId()==5){
					Map<String,Object> map = new HashMap<String, Object>();
					Account account = accountService.selectByUuid(uuid);
					map.put("id", account.getId());
					if(StringUtil.isNotEmpty(status)){
						map.put("status", status);
					}
					int result = accountService.updateAccountStatus(map);
					if(result>0){
						json.put("status", 1);
						json.put("msg", "操作成功！");
					}
				}
			}
		}
		returnMessage(response,json);
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/logout ")
	public void logout(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		if(session != null){
			session.invalidate();
		}
		
	}
	
	@RequestMapping("/getPowerIdByInviteCode")
	public void getPowerIdByInviteCode(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		String inviteCode = request.getParameter("inviteCode");
		Manager manager = (Manager) session.getAttribute("manager");
		int powerId = 0;
		if(manager!=null){
			powerId = managerService.selectManagerByInvoteCode(Integer.parseInt(inviteCode)).getPowerId();
		}
		json.put("powerId",powerId);
		returnMessage(response,json);
	}
	
	
	@RequestMapping("/handleLotResult")
	public void handleLotResult(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String pageNumber = request.getParameter("pageNum");
		String serialNum = request.getParameter("serialNum");
//		System.out.println(startTime+"==============="+endTime);
		int pageNum = 1,type = 0,payType = 0;
		if(pageNumber!=null){
			pageNum = Integer.parseInt(pageNumber);
		}
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		Manager manager = (Manager) session.getAttribute("proxy");
		if(manager==null||request.getParameter("pageType")==null)
		manager = (Manager) session.getAttribute("manager");
		if(manager != null){
			int startNum = (pageNum-1)*Params.pageNumber;
			int limit = Params.pageNumber;
			int size = 0;
			int mountSum = 0;
			Date startdate = null;
			Date enddate = null;
		size = paylogLotService.selectByAllTimeCount(Integer.parseInt(serialNum),0,startNum,limit,startdate,enddate);
			
			int totalNum = (int) Math.ceil(size/(double)Params.pageNumber);
			json.put("totalNum", size);
			json.put("mountSum", mountSum);
			//获取所有玩家   
			SimpleDateFormat time=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"); 
			List<PayloglotExt> paylogLots =null;
			if(serialNum!=null){
				paylogLots = paylogLotService.selectByAllTime(Integer.parseInt(serialNum),type,startNum , limit,startdate,enddate);
			}
			
			
			for (PayloglotExt m : paylogLots) {
				m.setDateString(time.format(m.getCreatetime()));
				array.add(m);
			}
			
			json.put("paylogLots", array);
//			System.out.println("返回的字符串==========="+json.toString());
			returnMessage(response, json.toString());
		}
	}
	
	
	public static List<ManagerNode> parseNode(ManagerNode node){
		List<ManagerNode> result = new ArrayList<ManagerNode>();
		result.add(node);
		List<ManagerNode> child = node.getChildagent();
		if(child!=null&&child.size()>0){
			for(ManagerNode childNode:child){
				result.addAll(parseNode(childNode));
			}
		}
		return result;
	}
	
	public static List<ManagerNode> parseNode(List<ManagerNode> child){
		List<ManagerNode> result = new ArrayList<ManagerNode>();
		if(child!=null&&child.size()>0){
			for(ManagerNode childNode:child){
				result.addAll(parseNode(childNode));
			}
		}
		return result;
	}
	
	@RequestMapping("/addBonusLog")
	public void addBonusLog(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String paylogId = request.getParameter("paylogId");
		Paylog paylog = null;
		String result = "FAIL";
		if(StringUtil.isInteger(paylogId, 0, 0)){
			paylog = paylogService.selectByPrimaryKey(Integer.parseInt(paylogId));
		}
		if(paylog!=null){
		int gameId = paylog.getGameId();
		double pmoney = paylog.getMoney();
		if(paylog.getManagerid()!=null){
		int managerId = paylog.getManagerid();
		if(!maCache.containsKey(managerId)){
			List<Manager> managers = managerService.selectManagerInfoByMap(new HashMap<String,Object>(),0);
			for(Manager curManager:managers)
			maCache.put(curManager.getId(), curManager);
		}
		Manager manager = maCache.get(managerId);
		int resultInt = 0;
		if(gameId==1||gameId==3){
			double rebate1 = Double.parseDouble(manager.getRebate());
			Bonus bonus1 = new Bonus();
			bonus1.setPaylogid(Integer.parseInt(paylogId));
			bonus1.setManagerid(managerId);
			bonus1.setMoney(pmoney*rebate1);
			resultInt+=bonusService.insert(bonus1);
			String levelStr = manager.getLevelStr();
			if(!StringUtil.isEmpty(levelStr)){
			String[] levels = levelStr.split("\\$");
			//System.out.println(levels);
			for(int i=0;i<levels.length;i++){
				Bonus bonus = new Bonus();
				bonus.setPaylogid(Integer.parseInt(paylogId));
				bonus.setManagerid(Integer.parseInt(levels[i]));
				int mid0 = Integer.parseInt(levels[i]);
				double rebate0 = Double.parseDouble(maCache.get(mid0).getRebate());
				if(i==(levels.length-1)){
					bonus.setMoney(pmoney*(rebate0-rebate1));
				}else{
					int mid2 = Integer.parseInt(levels[i+1]);
					double rebate2 = Double.parseDouble(maCache.get(mid2).getRebate());
					bonus.setMoney(pmoney*(rebate0-rebate2));
				}
				resultInt+=bonusService.insert(bonus);
			}
			if(resultInt==(levels.length+1))
			result = "SUCCESS";//全部记录插入也算成功
		}else if(resultInt==1){
			result = "SUCCESS";//只有一条为成功
		}
		}else{
			result = "SUCCESS";//其他分成模式算成功
		}
		}else{
			result = "SUCCESS";//没有绑定算成功
		}
		}
		json.put("returnCode", result);
		returnMessage(response, json);
	}
	
	@RequestMapping("/getBonusLogs")
	public void getBonusLogs(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		String uuid = request.getParameter("uuid");
		String pageNumber = request.getParameter("pageNum");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager!=null){
			Map<String,Object> map = new HashMap<String, Object>();
			
			map.put("managerId", manager.getId());
			if(StringUtil.isInteger(uuid, 0, 0)){
				map.put("uuid", Integer.parseInt(uuid));
			}
			try {
				if(StringUtil.isNotEmpty(startTime)){
				  Date	startdate = new Date(sdf.parse(startTime).getTime());
					map.put("startTime", startdate);
				}
				
				if(StringUtil.isNotEmpty(endTime)){
				  Date  enddate = new Date(sdf.parse(endTime).getTime());
				  System.out.println("====enddate:"+enddate);
					map.put("endTime", enddate);
				}
				
			} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			SimpleDateFormat s =   new SimpleDateFormat( "yyyy年MM月dd日 HH:mm:ss" );
			List<BonusExt> bonusLogs = bonusService.selectBonusLog(map);
			int totalMoney=0;
			for(BonusExt b :bonusLogs){
				String time = s.format(b.getPayTime());
				totalMoney += b.getBonus();
				b.setDateString(time);
				array.add(b);
			}
			int size=bonusLogs.size();
			json.put("totalNum", size);
			json.put("bonusLogs", array);
			json.put("totalMoney", totalMoney);
		}
		returnMessage(response, json);
	}
	
}
