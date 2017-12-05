package com.weipai.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerNode {
	private Integer id;

    private Integer powerId;//1 管理员   2经销商    3 零售商

    private String name;

    private String userId;
    
    private String telephone;

	private Integer actualcard;

    private Integer totalcards;

    private Integer managerUpId;

    private boolean status;

    private int inviteCode;
    
    private int rootManager;
    private String weixin;
    private String qq;
    private String rebate;
    
    private Integer uuid;
    private Date createTime;
    private Date lastLoginTime;
    private Integer redCard;
    
    private Integer userCounts;
    private Double totalMoney;
    private String levelStr;
    private List<ManagerNode> childagent = new ArrayList<ManagerNode>();
    
    private String createTimeStr;
    private String lastLoginTimeStr;

	public ManagerNode(Manager manager) {
		super();
		this.id = manager.getId();
		this.powerId = manager.getPowerId();
		this.name = manager.getName();
//		this.userId = ""+manager.getUuid();
		this.telephone = manager.getTelephone();
		this.actualcard = manager.getActualcard();
		this.levelStr = manager.getLevelStr();
		this.totalcards = manager.getTotalcards();
		this.managerUpId = manager.getManagerUpId();
		this.status = manager.getStatus().equals("0");
		this.inviteCode = manager.getInviteCode();
		this.rootManager = manager.getRootManager();
		this.weixin = manager.getWeixin();
		this.qq = manager.getQq();
		this.rebate = manager.getRebate();
		
		this.uuid = manager.getUuid();
		this.createTime = manager.getCreateTime();
		this.lastLoginTime = manager.getLastLoginTime();
		
		this.createTimeStr = manager.getCreateTimeStr();
		this.lastLoginTimeStr = manager.getLastLoginTimeStr();
		this.redCard = manager.getRedCard();
		this.userCounts = manager.getUserCounts();
		this.totalMoney = manager.getTotalMoney();
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPowerId() {
		return powerId;
	}

	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getActualcard() {
		return actualcard;
	}

	public void setActualcard(Integer actualcard) {
		this.actualcard = actualcard;
	}

	public Integer getTotalcards() {
		return totalcards;
	}

	public void setTotalcards(Integer totalcards) {
		this.totalcards = totalcards;
	}

	public Integer getManagerUpId() {
		return managerUpId;
	}

	public void setManagerUpId(Integer managerUpId) {
		this.managerUpId = managerUpId;
	}

	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(int inviteCode) {
		this.inviteCode = inviteCode;
	}

	public int getRootManager() {
		return rootManager;
	}

	public void setRootManager(int rootManager) {
		this.rootManager = rootManager;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public List<ManagerNode> getChildagent() {
		return childagent;
	}

	public void setChildagent(List<ManagerNode> childagent) {
		this.childagent = childagent;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRedCard() {
		return redCard;
	}

	public void setRedCard(Integer redCard) {
		this.redCard = redCard;
	}

	public Integer getUserCounts() {
		return userCounts;
	}

	public void setUserCounts(Integer userCounts) {
		this.userCounts = userCounts;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}



	public String getLevelStr() {
		return levelStr;
	}



	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}



	public Date getLastLoginTime() {
		return lastLoginTime;
	}



	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}



	public String getCreateTimeStr() {
		return createTimeStr;
	}



	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}



	public String getLastLoginTimeStr() {
		return lastLoginTimeStr;
	}



	public void setLastLoginTimeStr(String lastLoginTimeStr) {
		this.lastLoginTimeStr = lastLoginTimeStr;
	}
    
    
    
}
