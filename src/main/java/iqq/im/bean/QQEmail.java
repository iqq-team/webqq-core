
package iqq.im.bean;

/**
 * 邮件
 *
 * @author 承∮诺
 * @since 2014年1月24日
 */
public class QQEmail {
	private String id;
	private String sender;
	private String senderNick;
	private String senderEmail;
	private String subject;
	private String summary;
	private boolean unread;
	private long flag;

	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <p>Getter for the field <code>sender</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * <p>Setter for the field <code>sender</code>.</p>
	 *
	 * @param sender a {@link java.lang.String} object.
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * <p>Getter for the field <code>senderNick</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSenderNick() {
		return senderNick;
	}

	/**
	 * <p>Setter for the field <code>senderNick</code>.</p>
	 *
	 * @param senderNick a {@link java.lang.String} object.
	 */
	public void setSenderNick(String senderNick) {
		this.senderNick = senderNick;
	}

	/**
	 * <p>Getter for the field <code>senderEmail</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSenderEmail() {
		return senderEmail;
	}

	/**
	 * <p>Setter for the field <code>senderEmail</code>.</p>
	 *
	 * @param senderEmail a {@link java.lang.String} object.
	 */
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	/**
	 * <p>Getter for the field <code>subject</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * <p>Setter for the field <code>subject</code>.</p>
	 *
	 * @param subject a {@link java.lang.String} object.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * <p>Getter for the field <code>summary</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * <p>Setter for the field <code>summary</code>.</p>
	 *
	 * @param summary a {@link java.lang.String} object.
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * <p>Getter for the field <code>flag</code>.</p>
	 *
	 * @return a long.
	 */
	public long getFlag() {
		return flag;
	}

	/**
	 * <p>Setter for the field <code>flag</code>.</p>
	 *
	 * @param flag a long.
	 */
	public void setFlag(long flag) {
		this.flag = flag;
	}

	/**
	 * <p>isUnread.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isUnread() {
		return unread;
	}

	/**
	 * <p>Setter for the field <code>unread</code>.</p>
	 *
	 * @param unread a boolean.
	 */
	public void setUnread(boolean unread) {
		this.unread = unread;
	}

}
