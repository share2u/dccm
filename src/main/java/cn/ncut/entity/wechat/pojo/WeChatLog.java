package cn.ncut.entity.wechat.pojo;

public class WeChatLog {
	Integer UID ;
	String RequestMethod ;
	String RequestParams ;
	String RequestIP;
	String RequestURI ;
	String RequestPrtURI;
	
	
	
	public Integer getUID() {
		return UID;
	}
	public void setUID(Integer uID) {
		UID = uID;
	}
	public String getRequestMethod() {
		return RequestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		RequestMethod = requestMethod;
	}
	public String getRequestParams() {
		return RequestParams;
	}
	public void setRequestParams(String requestParams) {
		RequestParams = requestParams;
	}
	public String getRequestIP() {
		return RequestIP;
	}
	public void setRequestIP(String requestIP) {
		RequestIP = requestIP;
	}
	public String getRequestURI() {
		return RequestURI;
	}
	public void setRequestURI(String requestURI) {
		RequestURI = requestURI;
	}
	public String getRequestPrtURI() {
		return RequestPrtURI;
	}
	public void setRequestPrtURI(String requestPrtURI) {
		RequestPrtURI = requestPrtURI;
	}
	
}
