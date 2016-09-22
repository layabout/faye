package com.ruby.wechat.utils;

import com.ruby.wechat.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * 微信公众号工具类
 * Created by ruby on 2016/9/21.
 * Email:liyufeng_23@163.com
 */
public class WeChatUtil {

    private static WXBizMsgCrypt wxBizMsgCrypt = null;

    // 消息加解密密钥
    private final static String ENCODING_AESKEY = Constants.WX_ENCODING_AESKEY;
    // 令牌
    private final static String TOKEN = Constants.WX_TOKEN;
    // 应用ID
    private final static String APPID = Constants.WX_APPID;


    public static void main(String[] args) {
        String nonce = "xxxxxx";
        String replyMsg = "<xml><ToUserName><![CDATA[oia2Tj我是中文jewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
        String timestamp = "1409304348";
        try {
            String xml = encryptMsg(timestamp, nonce ,replyMsg);
            System.out.println(xml);

            //解密xml
            String result = decryptMsg(timestamp, nonce, xml);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        String requestIp = "111.226.62.83";
//        String iplist = "\"101.226.62.77\",\"101.226.62.78\",\"101.226.62.79\",\"101.226.62.80\",\"101.226.62.81\",\"101.226.62.82\",\"101.226.62.83\",\"101.226.62.84\",\"101.226.62.85\",\"101.226.62.86\",\"101.226.103.59\",\"101.226.103.60\",\"101.226.103.61\",\"101.226.103.62\",\"101.226.103.63\",\"101.226.103.69\",\"101.226.103.70\",\"101.226.103.71\",\"101.226.103.72\",\"101.226.103.73\",\"140.207.54.73\",\"140.207.54.74\",\"140.207.54.75\",\"140.207.54.76\",\"140.207.54.77\",\"140.207.54.78\",\"140.207.54.79\",\"140.207.54.80\",\"182.254.11.203\",\"182.254.11.202\",\"182.254.11.201\",\"182.254.11.200\",\"182.254.11.199\",\"182.254.11.198\",\"59.37.97.100\",\"59.37.97.101\",\"59.37.97.102\",\"59.37.97.103\",\"59.37.97.104\",\"59.37.97.105\",\"59.37.97.106\",\"59.37.97.107\",\"59.37.97.108\",\"59.37.97.109\",\"59.37.97.110\",\"59.37.97.111\",\"59.37.97.112\",\"59.37.97.113\",\"59.37.97.114\",\"59.37.97.115\",\"59.37.97.116\",\"59.37.97.117\",\"59.37.97.118\",\"112.90.78.158\",\"112.90.78.159\",\"112.90.78.160\",\"112.90.78.161\",\"112.90.78.162\",\"112.90.78.163\",\"112.90.78.164\",\"112.90.78.165\",\"112.90.78.166\",\"112.90.78.167\",\"140.207.54.19\",\"140.207.54.76\",\"140.207.54.77\",\"140.207.54.78\",\"140.207.54.79\",\"140.207.54.80\",\"180.163.15.149\",\"180.163.15.151\",\"180.163.15.152\",\"180.163.15.153\",\"180.163.15.154\",\"180.163.15.155\",\"180.163.15.156\",\"180.163.15.157\",\"180.163.15.158\",\"180.163.15.159\",\"180.163.15.160\",\"180.163.15.161\",\"180.163.15.162\",\"180.163.15.163\",\"180.163.15.164\",\"180.163.15.165\",\"180.163.15.166\",\"180.163.15.167\",\"180.163.15.168\",\"180.163.15.169\",\"180.163.15.170\",\"101.226.103.0\\/25\",\"101.226.233.128\\/25\",\"58.247.206.128\\/25\",\"182.254.86.128\\/25\",\"103.7.30.21\",\"103.7.30.64\\/26\",\"58.251.80.32\\/27\",\"183.3.234.32\\/27\",\"121.51.130.64\\/27\"";
//        System.out.println(validateWeChatIP(requestIp, iplist));
    }

    /**
     * 加密方法
     * @param timestamp 时间戳
     * @param nonce 随机串
     * @param replyMsg 明文消息
     * @return
     */
    public static String encryptMsg(String timestamp, String nonce, String replyMsg) throws Exception {

        WXBizMsgCrypt pc = getWxBizMsgCryptInstance();
        String encryptResult = pc.encryptMsg(replyMsg, timestamp, nonce);

        return encryptResult;
    }

    /**
     * 解密方法
     * @param timestamp 时间戳
     * @param nonce 随机串
     * @param receiveMsg 密文消息
     * @return
     */
    public static String decryptMsg(String timestamp, String nonce, String receiveMsg) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(receiveMsg);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);

        Element root = document.getDocumentElement();
        NodeList nodelist1 = root.getElementsByTagName("Encrypt");
        NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

        String encrypt = nodelist1.item(0).getTextContent();
        String msgSignature = nodelist2.item(0).getTextContent();

        String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
        String fromXML = String.format(format, encrypt);

        WXBizMsgCrypt pc = getWxBizMsgCryptInstance();
        String decryptResult = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);

        return decryptResult;
    }



    private static WXBizMsgCrypt getWxBizMsgCryptInstance() throws Exception {
        if (wxBizMsgCrypt == null) {
            wxBizMsgCrypt = new WXBizMsgCrypt(TOKEN, ENCODING_AESKEY, APPID);
        }
        return wxBizMsgCrypt;
    }

    /**
     * 验证请求IP是否来自微信服务器
     */
    public static boolean validateWeChatIP(String requestIP, String wechatIPList) {
        if(wechatIPList.contains(requestIP))
            return true;
        else
            return false;
    }

}
