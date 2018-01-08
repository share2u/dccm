package cn.ncut.entity.wechat.pojo;

/**
 * 微信支付 -- 查询订单返回参数
 * 	商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑
 * */
public class PayOrderQueryResponse {
	// 返回状态码,Y,取值为SUCCESS/FAIL,SUCCESS表示商户接收通知成功并校验成功
	private String return_code;
	// 返回消息,N,如非空,为错误原因,签名失败,参数格式校验错误
	private String return_msg;
	
	// return_code为SUCCESS的时候有返回 
	// 公众账号ID,Y,微信分配的公众账号ID
	private String appid;
	// 商户号,Y,微信支付分配的商户号
	private String mch_id;
	// 随机字符串,Y,
	private String nonce_str;
	// 签名,Y,商户系统根据其自己生成的sign与此sign彼表验证支付结果
	private String sign;
	// 业务结果,Y,取值为SUCCESS/FAIL
	private String result_code;
	// 错误代码,N,错误码
	private String err_code;
	// 错误信息,N,错误信息描述
	private String err_code_des;
	
	// return_code和result_code都成功时返回
	// 设备号,N,微信支付分配的终端设备号
	private String device_info;
	// 用户标识,Y
	private String openid;
	// 用户是否关注微信公众号,N,取值Y/N
	private String is_subscribe;
	// 交易类型,Y,取值JSAPI/NATIVE/APP
	private String trade_type;
	// 付款银行,Y,一个标识付款银行的字符串
	private String bank_type;
	// 总费用,Y,代为为分
	private Integer total_fee;
	// 应结订单金额,N,应结订单金额=订单金额-非充值代金券金额
	private Integer settlement_total_fee;
	// 货币种类,N
	private String fee_type;
	// 现金支付金额,Y
	private Integer cash_fee;
	// 现金支付货币种类,N
	private String cash_fee_type;
	// 代金券金额,N
	private Integer coupon_fee;
	// 代金券数量,N
	private Integer coupon_count;
	// 代金券类型,N
	private Integer coupon_type_$n;
	// 代金券ID,N
	private String coupon_id_$n;
	// 单个代金券支付金额,N
	private Integer coupon_fee_$n;
	// 微信支付订单号,Y
	private String transaction_id;
	// 商户订单号,Y
	private String out_trade_no;
	// 商家数据包,N
	private String attach;
	// 支付完成时间,Y,支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	private String time_end;
	// 交易状态描述 ,Y,对当前查询订单状态的描述和下一步操作的指引
	private String trade_state_desc;
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
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
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public Integer getSettlement_total_fee() {
		return settlement_total_fee;
	}
	public void setSettlement_total_fee(Integer settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public Integer getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public Integer getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(Integer coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public Integer getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}
	public Integer getCoupon_type_$n() {
		return coupon_type_$n;
	}
	public void setCoupon_type_$n(Integer coupon_type_$n) {
		this.coupon_type_$n = coupon_type_$n;
	}
	public String getCoupon_id_$n() {
		return coupon_id_$n;
	}
	public void setCoupon_id_$n(String coupon_id_$n) {
		this.coupon_id_$n = coupon_id_$n;
	}
	public Integer getCoupon_fee_$n() {
		return coupon_fee_$n;
	}
	public void setCoupon_fee_$n(Integer coupon_fee_$n) {
		this.coupon_fee_$n = coupon_fee_$n;
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
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getTrade_state_desc() {
		return trade_state_desc;
	}
	public void setTrade_state_desc(String trade_state_desc) {
		this.trade_state_desc = trade_state_desc;
	}
}
