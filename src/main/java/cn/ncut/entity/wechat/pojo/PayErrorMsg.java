package cn.ncut.entity.wechat.pojo;

/**
 * 微信统一下单错误信息记录类
 * 记录常见的错误代码与错误描述
 * */
public class PayErrorMsg extends PayH5 {
	private String errorCode;
	private String errorDes;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDes() {
		return errorDes;
	}
	public void setErrorDes(String errorDes) {
		this.errorDes = errorDes;
	}
	@Override
	public String toString() {
		return "PayErrorMsg [errorCode=" + errorCode + ", errorDes=" + errorDes
				+ "]";
	}
}
