package cn.ncut.entity.wechat.pojo;

/**
 * 支付结果通用通知响应微信端参数 -- 商家后台传递给微信服务器
 * */
public class PayResultNotifyResponse {
	// 返回状态码,Y,取值为SUCCESS/FAIL,SUCCESS表示商户接收通知成功并校验成功
	private String return_code;
	// 返回消息,N,如非空,为错误原因,签名失败,参数格式校验错误
	private String return_msg;
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
	@Override
	public String toString() {
		return "PayResultNotifyResponse [return_code=" + return_code
				+ ", return_msg=" + return_msg + "]";
	}
	
}
