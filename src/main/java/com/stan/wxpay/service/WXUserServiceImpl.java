package com.stan.wxpay.service;

import com.stan.wxpay.controller.WXPayController;
import com.stan.wxpay.utils.HttpUtil;
import com.stan.wxpay.utils.PayConfigUtil;
import com.stan.wxpay.utils.PayToolUtil;
import com.stan.wxpay.utils.XMLUtil4jdom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class WXUserServiceImpl implements WXUserService {

    private Logger lg = LoggerFactory.getLogger(WXUserServiceImpl.class);
    /**
     * 获取微信支付的二维码地址
     * @return
     * @throws Exception
     */
    public String weixinPay(String userId, String productId) throws Exception {
        String out_trade_no = "" + System.currentTimeMillis(); //订单号 （调整为自己的生产逻辑）
        // 账号信息
        String appid = PayConfigUtil.APP_ID;  // appid
        //String appsecret = PayConfigUtil.APP_SECRET; // appsecret
        String mch_id = PayConfigUtil.MCH_ID; // 商业号
        String key = PayConfigUtil.API_KEY; // key

        String currTime = PayToolUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayToolUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;

        // 获取发起电脑 ip
        String spbill_create_ip = PayConfigUtil.CREATE_IP;
        // 回调接口
        String notify_url = PayConfigUtil.NOTIFY_URL;
        String trade_type = "NATIVE";

        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", "可乐");  //（调整为自己的名称）
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", "10"); //价格的单位为分
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        String sign = PayToolUtil.createSign("UTF-8", packageParams,key);
        packageParams.put("sign", sign);

        String requestXML = PayToolUtil.getRequestXml(packageParams);
        lg.info("订单信息："+requestXML);

        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);
        lg.info("生成订单结果："+resXml);

        Map map = XMLUtil4jdom.doXMLParse(resXml);
        String urlCode = (String) map.get("code_url");

        return urlCode;
    }
}
