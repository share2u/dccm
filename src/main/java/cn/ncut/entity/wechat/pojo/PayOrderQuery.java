package cn.ncut.entity.wechat.pojo;

/**
 * 微信支付 -- 查询订单请求参数
 * 	商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑
 * */
public class PayOrderQuery {
	// 公众账号ID,Y,微信分配的公众账号ID
	private String appid;
	// 商户号,Y,微信支付分配的商户号
	private String mch_id;
	
	// 二者选一
	// 微信支付订单号,Y
	private String transaction_id;
	// 商户订单号,Y
	private String out_trade_no;
	
	// 随机字符串,Y,使用RandomUtil.generateMixedCharAndDigit()生成
	private String nonce_str;
	// 签名,Y,使用SignUtil.createSign()生成,用于验证支付结果
	private String sign;
	// 签名类型,N,目前支持MD5,SHA-256
	private String sign_type;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
}
