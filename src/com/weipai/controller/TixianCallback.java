package com.weipai.controller;

import com.weipai.Withdraw.WithdrawCallback;
import com.weipai.model.Paylog;
import com.weipai.service.PaylogService;

public class TixianCallback implements WithdrawCallback {
	private Paylog paylog;
//	private Paylog paylog2;
	private Paylog paylog3;
	private PaylogService paylogService;
	private int managerId;
	private boolean result;
	private int tixianStatus;
	@Override
	public void onFail(String err_code, String err_code_des) {
		// TODO Auto-generated method stub
		//2标识提现失败
		paylogService.tixianDone(paylog.getId(),2);
//		paylogService.tixianDone(paylog2.getId(),2);
		result = false;
		System.out.println("提现失败");
//		paylogService.
	}

	@Override
	public void onSuccess(String partner_trade_no, String payment_no, String payment_time) {
		// TODO Auto-generated method stub
		paylog.setStatus(1);
    	paylogService.tixianDone(paylog.getId(),1);
//    	paylogService.tixianDone(paylog2.getId(),1);
    	paylogService.insertPaylog(paylog3);
    	result = true;
    	System.out.println("提现成功");
	}

	public Paylog getPaylog() {
		return paylog;
	}

	public void setPaylog(Paylog paylog) {
		this.paylog = paylog;
	}

	public PaylogService getPaylogService() {
		return paylogService;
	}

	public void setPaylogService(PaylogService paylogService) {
		this.paylogService = paylogService;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getTixianStatus() {
		return tixianStatus;
	}

	public void setTixianStatus(int tixianStatus) {
		this.tixianStatus = tixianStatus;
	}

//	public Paylog getPaylog2() {
//		return paylog2;
//	}
//
//	public void setPaylog2(Paylog paylog2) {
//		this.paylog2 = paylog2;
//	}

	public Paylog getPaylog3() {
		return paylog3;
	}

	public void setPaylog3(Paylog paylog3) {
		this.paylog3 = paylog3;
	}

	
	
}
