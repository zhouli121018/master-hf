package com.weipai.Withdraw;

/**
 * Created by westlake on 2017/6/26.
 */

public interface WithdrawCallback {
    void onFail(String err_code, String err_code_des);

    void onSuccess(String partner_trade_no, String payment_no, String payment_time);
}
