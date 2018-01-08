package cn.ncut.entity.wechat.pojo;

/**
 * 统一下单请求微信参数
 *商品二级简单描述
 *	腾讯充值中心-QQ会员充值
 *商品详情，
 *	商品的编号Y
 *	微信支付定于的统一商品编号 N
 *	商品名称Y
 *	商品数量 Y
 *	商品单价 Y 分
 *	商品类目 N 商品类目Id
 *	商品描述信息 N
 *	
 *附加数据，商户订单号，总金额
 */
public class PayUnifiedOrder {
	//公众帐号ID,Y，微信分配的公众帐号ID
	private String appid;
	//微信支付分配的商户号,Y
	private String mch_id;
	//终端设备号，N
	private String device_info;
	//随机字符串，Y,随机数生成算法
	private String nonce_str;
	//签名，Y,签名生成算法最后生成
	private String sign;
	//商品描述,Y,商品描述规定两级
	private String body;
	//商品详情，N,格式
	private String detail;
	//附加数据,N,商户鞋带的自定义数据
	private String attach;
	//商品订单号，Y,商家内部的订单号
	private String out_trade_no;
	//货币类型，N,
	private String fee_type = "CNY";
	//总金额,Y,分
	private Integer total_fee;
	//终端IP,Y,用户端ip?
	private String spbill_create_ip;
	//交易起始时间，N,时间规则
	private String time_start;
	//交易结束时间，N,间隔必须大约5分钟
	private String time_expire;
	//商品标记，N,代金卷的参数
	private String goods_tag;
	//通知地址，Y,不能携带参数
	private String notify_url;
	//交易类型，Y,
	private String trade_type = "JSAPI";
	//商品ID,N，二维码中商品包含的商品ID必须有
	private String product_id;
	//指定支付方式,N,可以不可以使用信用卡
	private String limit_pay;
	//商品标示，Y，
	private String openid;
	
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
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
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
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getGoods_tag() {
		return goods_tag;
	}
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getLimit_pay() {
		return limit_pay;
	}
	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Override
	public String toString() {
		return "PayUnifiedOrder [appid=" + appid + ", mch_id=" + mch_id
				+ ", device_info=" + device_info + ", nonce_str=" + nonce_str
				+ ", sign=" + sign + ", body=" + body + ", detail=" + detail
				+ ", attach=" + attach + ", out_trade_no=" + out_trade_no
				+ ", fee_type=" + fee_type + ", total_fee=" + total_fee
				+ ", spbill_create_ip=" + spbill_create_ip + ", time_start="
				+ time_start + ", time_expire=" + time_expire + ", goods_tag="
				+ goods_tag + ", notify_url=" + notify_url + ", trade_type="
				+ trade_type + ", product_id=" + product_id + ", limit_pay="
				+ limit_pay + ", openid=" + openid + "]";
	}
}
