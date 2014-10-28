
package iqq.im.bean;

/**
 * <p>QQStranger class.</p>
 *
 * @author ZhiHui_Chen
 * @since date 2013-4-11
 */
public class QQStranger extends QQUser {
	private static final long serialVersionUID = -5581405313260458197L;
	
	// 临时会话信道
	private String groupSig;
	private int serviceType;
	/**加好友时需要的参数*/
	private String token;

	/**
	 * <p>Getter for the field <code>groupSig</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getGroupSig() {
		return groupSig;
	}

	/**
	 * <p>Setter for the field <code>groupSig</code>.</p>
	 *
	 * @param groupSig a {@link java.lang.String} object.
	 */
	public void setGroupSig(String groupSig) {
		this.groupSig = groupSig;
	}

	/**
	 * <p>Getter for the field <code>serviceType</code>.</p>
	 *
	 * @return a int.
	 */
	public int getServiceType() {
		return serviceType;
	}

	/**
	 * <p>Setter for the field <code>serviceType</code>.</p>
	 *
	 * @param serviceType a int.
	 */
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * <p>Getter for the field <code>token</code>.</p>
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * <p>Setter for the field <code>token</code>.</p>
	 *
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
}
