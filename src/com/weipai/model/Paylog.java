package com.weipai.model;

import java.util.Date;

public class Paylog {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column paylog.id
	 * @mbggenerated
	 */
	private Integer id;
	
	private String dateString;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column paylog.managerId
	 * @mbggenerated
	 */
	private Integer managerid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column paylog.uuid
	 * @mbggenerated
	 */
	private Integer uuid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column paylog.money
	 * @mbggenerated
	 */
	private Double money;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column paylog.payCount
	 * @mbggenerated
	 */
	private Integer paycount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column paylog.payTime
	 * @mbggenerated
	 */
	private Date paytime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column paylog.payType
	 * @mbggenerated
	 */
	private Integer paytype;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column paylog.status
	 * @mbggenerated
	 */
	private Integer status;
    private String nickName;
    private Integer gameId;
    
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column paylog.id
	 * @return  the value of paylog.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column paylog.id
	 * @param id  the value for paylog.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column paylog.managerId
	 * @return  the value of paylog.managerId
	 * @mbggenerated
	 */
	public Integer getManagerid() {
		return managerid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column paylog.managerId
	 * @param managerid  the value for paylog.managerId
	 * @mbggenerated
	 */
	public void setManagerid(Integer managerid) {
		this.managerid = managerid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column paylog.uuid
	 * @return  the value of paylog.uuid
	 * @mbggenerated
	 */
	public Integer getUuid() {
		return uuid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column paylog.uuid
	 * @param uuid  the value for paylog.uuid
	 * @mbggenerated
	 */
	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column paylog.money
	 * @return  the value of paylog.money
	 * @mbggenerated
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column paylog.money
	 * @param money  the value for paylog.money
	 * @mbggenerated
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column paylog.payCount
	 * @return  the value of paylog.payCount
	 * @mbggenerated
	 */
	public Integer getPaycount() {
		return paycount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column paylog.payCount
	 * @param paycount  the value for paylog.payCount
	 * @mbggenerated
	 */
	public void setPaycount(Integer paycount) {
		this.paycount = paycount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column paylog.payTime
	 * @return  the value of paylog.payTime
	 * @mbggenerated
	 */
	public Date getPaytime() {
		return paytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column paylog.payTime
	 * @param paytime  the value for paylog.payTime
	 * @mbggenerated
	 */
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column paylog.payType
	 * @return  the value of paylog.payType
	 * @mbggenerated
	 */
	public Integer getPaytype() {
		return paytype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column paylog.payType
	 * @param paytype  the value for paylog.payType
	 * @mbggenerated
	 */
	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column paylog.status
	 * @return  the value of paylog.status
	 * @mbggenerated
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column paylog.status
	 * @param status  the value for paylog.status
	 * @mbggenerated
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	
	
}