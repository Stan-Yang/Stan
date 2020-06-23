package com.stan.wxpay.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.stan.wxpay.service.WXUserServiceImpl;
import com.stan.wxpay.utils.PayConfigUtil;
import com.stan.wxpay.utils.PayToolUtil;
import com.stan.wxpay.utils.QRCodeUtil;
import com.stan.wxpay.utils.XMLUtil4jdom;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


@Controller
public class WXPayController {

    private static String WECHAT_TOKEN = "jinsanpang";

    private Logger lg = LoggerFactory.getLogger(WXPayController.class);

    @Autowired
    private WXUserServiceImpl wxUserService;

    @GetMapping("/goodspay")
    public String goodspay(){
        return "wxpay/goodspay";
    }


    /**
     * 微信支付->扫码支付(模式二)->统一下单->微信二维码
     * @return
     */
    @GetMapping("/qrcode.do")
    public void qrcode(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
        try {
            String productId = request.getParameter("productId");
            String userId = "user01";
            String urlCode = wxUserService.weixinPay(userId, productId);
            if(urlCode==null){
                urlCode="123456789";
            }

            try {
                //根据url来生成生成二维码
                int width = 300;
                int height = 300;
                //二维码的图片格式
                String format = "gif";
                Hashtable hints = new Hashtable();
                //内容所使用编码
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                BitMatrix bitMatrix;
                bitMatrix = new MultiFormatWriter().encode(urlCode, BarcodeFormat.QR_CODE, width, height, hints);
                QRCodeUtil.writeToStream(bitMatrix, format,response.getOutputStream());
            } catch (WriterException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
        }
    }

    /**
     * 微信平台发起的回调方法，
     * 调用我们这个系统的这个方法接口，将扫描支付的处理结果告知我们系统
     * @throws JDOMException
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/weixinNotify.do")
    public void weixinNotify(HttpServletRequest request, HttpServletResponse response) throws JDOMException, Exception{
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil4jdom.doXMLParse(sb.toString());

        //过滤空 设置 TreeMap
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = PayConfigUtil.API_KEY; //key

        //判断签名是否正确
        if(PayToolUtil.isTenpaySign("UTF-8", packageParams,key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String app_id = (String)packageParams.get("appid");
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");
                String total_fee = (String)packageParams.get("total_fee");
                //附加参数【商标申请_0bda32824db44d6f9611f1047829fa3b_15460】--【业务类型_会员ID_订单号】
                String attach = (String)packageParams.get("attach");
                //支付完成时间
                String time_end=(String)packageParams.get("time_end");

                //////////执行自己的业务逻辑////////////////
                //暂时使用最简单的业务逻辑来处理：只是将业务处理结果保存到session中
                //（根据自己的实际业务逻辑来调整，很多时候，我们会操作业务表，将返回成功的状态保留下来）
                request.getSession().setAttribute("_PAY_RESULT", "OK");

                lg.info("app_id:"+app_id);
                lg.info("mch_id:"+mch_id);
                lg.info("openid:"+openid);
                lg.info("is_subscribe:"+is_subscribe);
                lg.info("out_trade_no:"+out_trade_no);
                lg.info("total_fee:"+total_fee);
                lg.info("额外参数_attach:"+attach);
                lg.info("time_end:"+time_end);
                //执行自己的业务逻辑结束
                lg.info("支付成功");

                System.out.println("支付成功");
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

            } else {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            System.out.println("通知签名验证失败");
        }

    }

    @RequestMapping("/hadPay.do")
    public void hadPay(String productId){

    }

}
