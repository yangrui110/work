package com.lvshou.magic.statics;

public class WeChatConfig {

	/**登录入口和个人中心的入口地址*/
	public static String LOGIN="https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect";
	/**登录授权回调地址*/
	public static String LOGIN_URL="http://api.wanxidi.com.cn/wechat/modify";//"http://api.wanxidi.com.cn/wechat/modify";
	/**个人中心的授权回调地址*/
	public static String PERSON="http://api.wanxidi.com.cn/wechat/wxPerson";
	/**扫码成功后回调地址*/
	public static String SHARE_PATH="http://api.wanxidi.com.cn/wechat/shareOne?parent=%s";
	/**获取用户基本信息的access_token路径*/
	public static String USER_ACCESS_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	/**美业商城授权回调路径*/
	public static String MEIYE_URL="http://api.wanxidi.com.cn/wechat/meiye";
	public static String CREATE_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String REBACK_URL="https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";
	public static String RSA_URL="https://fraud.mch.weixin.qq.com/risk/getpublickey";
	public static String APPID="wx9a36243980a50ce0";
	public static String MCH_ID="1503923001";
	public static String APPSECRET="f3241d6c32871dcda59505ee9c445395";
	/**支付结果通知的回调路径*/
	public static String NOTIFY_URL="http://api.wanxidi.com.cn/wechat/notify/";
	/**充值结果通知*/
	public static String CHARGE_NOTIFY_URL="http://api.wanxidi.com.cn/wechat/chargeNotify/";
	public static String TRADE_TYPE="JSAPI";
	public static String KEY="8ddfb8deb73646508ad82289a513e810";
	public static String PAKAGE="Sign=WXPay";
	public static String TOKEN="wanxidi2018";
	public static String SIGNTYPE="MD5";
	public static String SCENE_INFO="{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://pay.qq.com\",\"wap_name\": \"腾讯充值\"}}";
	public static String OPENID="oBiTP0tRrp00WVn82VD88k3GnL4A";
	
	public static String RSAKEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApXW+mBce2VjFQ04HqQzJyvDtAWmM3ZIcmDYYNG/CT+28qEhwwPFYU4qQVTsYwg2eMY2EY4Apgqz5VHGI85oUiQE4E2GwEw0j71jxrRDx6QBsvq590vpKAwEzh931g0jE0QYbMnm2CXcv1ysy5PVD7uvk3jkKJRhkkky2wPzAmjmDY/BaYMe6/rCKwN9DEyq2O1RKRcXUs1Z+Culqieg5yOeRacOy/yqhWcm2wG28xn6EaBLupkTRUr7bCbr5j3Rtb3udpdaqvE6WeJt9yOh8hu5MgW6L2+iZdyk48+eg7jbAWlvK5iS8wkEMjB81X4G2lbWYsEKSfLDwsSahr4yCwwIDAQAB";

	/**证书路径*/
	public static String CERT_PATH="D:\\\\wanxidi\\\\apiclient_cert.p12";
	/**获取access_token*/
	public static String ACCESS_TOKEN="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
	/**生成菜单*/
	public static String CREATE_MENU="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
	/**新增临时素材的地址*/
	public static String NEW_MEDIA="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";
	/**获取用户的基本信息*/
	public static String USER_INFO="https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
}
