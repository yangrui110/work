package com.lvshou.magic.payserviceImpl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;
import com.lvshou.magic.payservice.Payment;
import com.lvshou.magic.statics.WeChatConfig;
import com.lvshou.magic.util.HttpUtils;
import com.lvshou.magic.util.MD5Util;
import com.lvshou.magic.util.ParseUtil;
import com.lvshou.magic.util.RSAUtils;
import com.lvshou.magic.util.XMLUtil;
import com.lvshou.magic.utils.MainUUID;

@Service
public class PaymentImpl implements Payment {
	
	@Override
	public String sign(String notify,String openid,String mainId,String price,String ip) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		SortedMap<String,String> map=new TreeMap<>();
		map.put("appid", WeChatConfig.APPID);
		map.put("mch_id", WeChatConfig.MCH_ID);
		map.put("nonce_str", MainUUID.getUUID());
		map.put("body", "万喜缔商品");
		map.put("out_trade_no", mainId);
		BigDecimal money=new BigDecimal(price).multiply(new BigDecimal(100));
		map.put("total_fee", ""+money.intValue());
		map.put("spbill_create_ip", ip);
		map.put("notify_url",notify);
		map.put("trade_type", WeChatConfig.TRADE_TYPE);
//		map.put("scene_info", WeChatConfig.SCENE_INFO);
		map.put("openid", openid);
		String mp=ParseUtil.parseFirst(map);

		map.put("sign", MD5Util.encode(mp,"utf-8").toUpperCase());
		map.remove("key");
		String xml=XMLUtil.parseToString(map);

		//发送httpPost请求
		String result=HttpUtils.httpsRequest(WeChatConfig.CREATE_URL, "POST", xml);
		//
		Document doc;
		try {
			doc = DocumentHelper.parseText(result);
			 Map map1=XMLUtil.Dom2Map(doc);
		    return paySort(map1.get("prepay_id"));
			 //return (String) map1.get("mweb_url");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String paySort(Object string) throws UnsupportedEncodingException {
		String str=""+string;
		SortedMap<String, String> map=new TreeMap<>();
		map.put("appId", WeChatConfig.APPID);
		map.put("nonceStr", MainUUID.getUUID());
		map.put("signType", WeChatConfig.SIGNTYPE);
		map.put("package", "prepay_id="+str);
		map.put("timeStamp", ""+System.currentTimeMillis()/1000);
		map.put("paySign",MD5Util.encode(ParseUtil.parseFirst(map),"utf-8").toUpperCase());
		map.remove("key");
		//String xml=parseToString(map);
		//System.out.println("支付接口转化为xml="+xml);
		int num=1;
		StringBuilder builder=new StringBuilder();
		builder.append("{");
		Set set=map.keySet();
		Iterator iterator=set.iterator();
		while(iterator.hasNext()) {
			String key=(String) iterator.next();
			builder.append("\""+key+"\":\""+map.get(key)+"\"");
			if(num!=set.size()) {
				builder.append(",");
				num++;
			}
		}
		builder.append("}");
		return builder.toString();
	}

	@Override
	public boolean token(String request) {
		// TODO Auto-generated method stub
		Map<String, Object> map;
		try {
			map = XMLUtil.Dom2Map(DocumentHelper.parseText(request));
			if(map.get("return_code").equals("SUCCESS")) {
				return true;  
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  false;
	}

	@Override
	public boolean reback(BigDecimal decimal) {
		// TODO Auto-generated method stub
		/*
		SortedMap<String, String> map=new TreeMap<>();
		map.put("mch_id", WeChatConfig.MCH_ID);
		map.put("partner_trade_no", MainUUID.getUUID());//订单号
		map.put("nonce_str", MainUUID.getUUID());		
		try {
			String result=RSAUtils.connect();
			Document document=DocumentHelper.parseText(result);
			Map<String, Object> map2=XMLUtil.Dom2Map(document);
			String rString=(String) map2.get("result_code");
			if(rString.equals("SUCCESS")) {
				String pubkey=((String) map2.get("pub_key"));
				System.out.println("pubkey="+pubkey);
				System.out.println("加密后的数据是:"+RSAUtils.signWithPublicKey("6228210770016508413", pubkey));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		try {
			String cardNo=Base64.getEncoder().encodeToString(RSAUtils.encrypt("6228210770016508413".getBytes("utf-8"), WeChatConfig.RSAKEY,1024,11,"RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING"));
			String name=Base64.getEncoder().encodeToString(RSAUtils.encrypt("杨瑞".getBytes("utf-8"), WeChatConfig.RSAKEY,1024,11,"RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING"));
			SortedMap<String, String> map=new TreeMap<>();
			map.put("mch_id", WeChatConfig.MCH_ID);
			map.put("partner_trade_no", MainUUID.getUUID());
			map.put("nonce_str", MainUUID.getUUID());
			map.put("enc_bank_no", cardNo);
			map.put("enc_true_name", name);
			map.put("bank_code", "1005");
			map.put("amount", "1");
			//map.put("account_type", "1");
			map.put("sign", MD5Util.encode(ParseUtil.parseFirst(map), "utf-8").toUpperCase());
			map.remove("key");
			String xml=XMLUtil.parseToString(map);

			//发送httpPost请求
			String result=HttpUtils.httpsRequest(WeChatConfig.REBACK_URL, "POST", xml);
			System.out.println("返回结果="+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	

}
