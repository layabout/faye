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
 * 微信公众号消息体加解密工具类
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

}
