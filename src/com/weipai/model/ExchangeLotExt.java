package com.weipai.model;

import java.util.Date;

public class ExchangeLotExt {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_lot.id
     *
     * @mbggenerated
     */
    private Integer id;
    private String datestring;
    
	public String getDatestring() {
		return datestring;
	}

	public void setDatestring(String datestring) {
		this.datestring = datestring;
	}

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_lot.randomNum
     *
     * @mbggenerated
     */
    private String randomnum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_lot.amount
     *
     * @mbggenerated
     */
    private Double amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_lot.uuid
     *
     * @mbggenerated
     */
    private Integer uuid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_lot.createTime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_lot.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_lot.id
     *
     * @return the value of exchange_lot.id
     *
     * @mbggenerated
     */
    private String nickName;
    
    
    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_lot.id
     *
     * @param id the value for exchange_lot.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_lot.randomNum
     *
     * @return the value of exchange_lot.randomNum
     *
     * @mbggenerated
     */
    public String getRandomnum() {
        return randomnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_lot.randomNum
     *
     * @param randomnum the value for exchange_lot.randomNum
     *
     * @mbggenerated
     */
    public void setRandomnum(String randomnum) {
        this.randomnum = randomnum == null ? null : randomnum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_lot.amount
     *
     * @return the value of exchange_lot.amount
     *
     * @mbggenerated
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_lot.amount
     *
     * @param amount the value for exchange_lot.amount
     *
     * @mbggenerated
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_lot.uuid
     *
     * @return the value of exchange_lot.uuid
     *
     * @mbggenerated
     */
    public Integer getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_lot.uuid
     *
     * @param uuid the value for exchange_lot.uuid
     *
     * @mbggenerated
     */
    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_lot.createTime
     *
     * @return the value of exchange_lot.createTime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_lot.createTime
     *
     * @param createtime the value for exchange_lot.createTime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_lot.status
     *
     * @return the value of exchange_lot.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_lot.status
     *
     * @param status the value for exchange_lot.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}