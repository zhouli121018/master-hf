package com.weipai.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("Manager")
public class Manager {
    private Integer id;

    private Integer powerId;//1 管理员   2经销商    3 零售商

    private String name;
    private String name2;
    private String password;
    
    private String telephone;

	private Integer actualcard;

    private Integer totalcards;

    private Integer managerUpId;

    private String status;

    private int inviteCode;
    private int inviteCode2;
    
    private int rootManager;
    private String weixin;
    private String qq;
    
    private String rebate;
    private Integer pid;
    
    private String levelStr;
    
    private Integer uuid;
    private Date createTime;
    
    private Integer redCard;
    
    private Integer userCounts;
    private Double totalMoney;
    private String totalLevelStr;
    private Date lastLoginTime;
    private String createTimeStr;
    private String lastLoginTimeStr;
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
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public int getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(int inviteCode) {
		this.inviteCode = inviteCode;
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

	public int getRootManager() {
		return rootManager;
	}

	public void setRootManager(int rootManager) {
		this.rootManager = rootManager;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getLevelStr() {
		return levelStr;
	}

	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
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

	public void setPid(Integer pid) {
		this.pid = pid;
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
	
	public Manager(ManagerNode manager){
		this.id = manager.getId();
		this.powerId = manager.getPowerId();
		this.name = manager.getName();
		this.telephone = manager.getTelephone();
		this.actualcard = manager.getActualcard();
		this.totalcards = manager.getTotalcards();
		this.managerUpId = manager.getManagerUpId();
		this.inviteCode = manager.getInviteCode();
		this.rootManager = manager.getRootManager();
		this.weixin = manager.getWeixin();
		this.qq = manager.getQq();
		this.rebate = manager.getRebate();
		this.levelStr = manager.getLevelStr();
		this.uuid = manager.getUuid();
		this.createTime = manager.getCreateTime();
		this.redCard = manager.getRedCard();
		this.userCounts = manager.getUserCounts();
		this.totalMoney = manager.getTotalMoney();
		this.createTimeStr = manager.getCreateTimeStr();
		this.lastLoginTimeStr = manager.getLastLoginTimeStr();
	}

	public Manager() {
		
	}
    
	private List<Manager> childagent = new ArrayList<Manager>();

	public List<Manager> getChildagent() {
		return childagent;
	}

	public void setChildagent(List<Manager> childagent) {
		this.childagent = childagent;
	}

	public String getTotalLevelStr() {
		return totalLevelStr;
	}

	public void setTotalLevelStr(String totalLevelStr) {
		this.totalLevelStr = totalLevelStr;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public int getInviteCode2() {
		return inviteCode2;
	}

	public void setInviteCode2(int inviteCode2) {
		this.inviteCode2 = inviteCode2;
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