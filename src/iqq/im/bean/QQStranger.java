/**
 * 
 */
package iqq.im.bean;

/**
 * @author ZhiHui_Chen<6208317@qq.com>
 * @create date 2013-4-11
 */
public class QQStranger extends QQUser {
	private static final long serialVersionUID = -5581405313260458197L;
	
	// 临时会话信道
	private String groupSig;
	private int serviceType;
	/**加好友时需要的参数*/
	private String token;

	public String getGroupSig() {
		return groupSig;
	}

	public void setGroupSig(String groupSig) {
		this.groupSig = groupSig;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
}
