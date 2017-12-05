package com.weipai.Withdraw;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;


/**
 * Created by westlake on 2017/6/26.
 */

public class WithdrawCash {
    private final static String TAG = "WithdrawCash";

    private final static String CHAR_SET = "UTF-8";
    private final static int MAX_TOTAL_CONNECTIONS = 1000;
    private final static int MAX_ROUTE_CONNECTIONS = 500;
    //旧的
//    private final static String APP_ID = "wx0b0da56105e931d5";
//
//    private final static String APP_PARTNER = "1481903462";
//    private final static String APP_KEY = "LQ049856yy7289ebcceokplqep23mxc7";
    
    private final static String APP_ID = "wx07022b5bc486f279";

    private final static String APP_PARTNER = "1370897202";
    private final static String APP_KEY = "LQ0929xxfy982fjielx39093ooxx3987";

    private final static String TRANSFER_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    private static CloseableHttpClient sHttpsClient;

    //public final static String ip = "120.76.100.224";

    public static void doTransfer(String openId, String name, float money, String desc, String ip, WithdrawCallback cb) {
        int cash = (int) (money * 100); //以分为单位
        String amount = String.valueOf(cash);

        String nonce_str = Futil.createNonceStr();
        String partner_trade_no = Futil.createOrderNo();

        //String check_name = "FORCE_CHECK";
        String check_name = "NO_CHECK";

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();

        finalpackage.put("mch_appid", APP_ID);
        finalpackage.put("mchid", APP_PARTNER);
        finalpackage.put("partner_trade_no", partner_trade_no);
        finalpackage.put("nonce_str", nonce_str);
        finalpackage.put("openid", openId);
        finalpackage.put("check_name", check_name);
        //finalpackage.put("re_user_name", name);
        finalpackage.put("amount", amount);
        finalpackage.put("desc", desc);
        finalpackage.put("spbill_create_ip", ip);
        
        


        String sign = Futil.createSign(finalpackage, APP_KEY);

        String xml = "<xml>" +
                "<mch_appid>" + APP_ID + "</mch_appid>" +
                "<mchid>" + APP_PARTNER + "</mchid>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<partner_trade_no>" + partner_trade_no + "</partner_trade_no>" +
                "<openid>" + openId + "</openid>" +
                "<check_name>" + check_name + "</check_name>" +
//                "<re_user_name>" + name + "</re_user_name>" +
                "<amount>" + amount + "</amount>" +
                "<desc>" + desc + "</desc>" +
                "<sign>" + sign + "</sign>" +
                "<spbill_create_ip>" + ip + "</spbill_create_ip>" +
                "</xml>";

        System.out.println(TAG + " send = " + xml);

        doPost(xml, cb, 0);
    }

    private static CloseableHttpClient getHttpsInst() {
        if (sHttpsClient != null) {
            return sHttpsClient;
        }

        //PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        //cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File("/root/apiclient_cert.p12"));
            try {
                keyStore.load(instream, APP_PARTNER.toCharArray());
            } finally {
                instream.close();
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, APP_PARTNER.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            sHttpsClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
//                    .setConnectionManager(cm)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sHttpsClient;
    }

    private static void doPost(final String xmlCnt, final WithdrawCallback cb, int retryCount) {
        HttpPost httpPost = new HttpPost(TRANSFER_URL);
        HttpContext httpContext = new BasicHttpContext();

        httpPost.setEntity(new StringEntity(xmlCnt, CHAR_SET));

        try {
            CloseableHttpResponse response = getHttpsInst().execute(httpPost, httpContext);
            String xmlResp = EntityUtils.toString(response.getEntity(), CHAR_SET);

            System.out.println(TAG + " resp = " + xmlResp);

            Map map = Futil.doXMLParse(xmlResp);

            String return_code = (String) map.get("return_code");

            if (return_code.equals("SUCCESS")) {
                String result_code = (String) map.get("result_code");
                if (result_code.equals("SUCCESS")) {
                    if (cb != null) {
                        String trade_no = (String) map.get("partner_trade_no");
                        String payment_no = (String) map.get("payment_no");
                        String payment_time = (String) map.get("payment_time");
                        cb.onSuccess(trade_no, payment_no, payment_time);
                    }

                } else {
                    if (cb != null) {
                        String err_code = (String) map.get("err_code");
                        String err_code_des = (String) map.get("err_code_des");

                        if (err_code.equals("SYSTEMERROR") && retryCount < 5) {
                            doPost(xmlCnt, cb, ++retryCount);
                        } else {
                            cb.onFail(err_code, err_code_des);
                        }
                    }
                }
            } else {
                System.out.println(TAG + " return_msg =" + (String) map.get("return_msg"));
                if (cb != null) {
                    String err_code = (String) map.get("err_code");
                    String err_code_des = (String) map.get("err_code_des");
                    if (err_code.equals("SYSTEMERROR") && retryCount < 5) {
                        doPost(xmlCnt, cb, ++retryCount);
                    } else {
                        cb.onFail(err_code, err_code_des);
                    }
                }
            }

            response.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("args wrong");
        }
        String openid = args[0];
        String name = args[1];
        float money = 1.0f;
        String desc = name + "提取一元";
        String ip = "120.76.100.224";
        WithdrawCallback cb = null;

        doTransfer(openid, name, money, desc, ip, cb);
    }
}
