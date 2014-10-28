
package iqq.im.bean;

/**
 * <p>QQDiscuzMember class.</p>
 *
 * @author ZhiHui_Chen
 * @since date 2013-4-11
 */
public class QQDiscuzMember extends QQStranger {
	private static final long serialVersionUID = -9123961549383958201L;
	
	private QQDiscuz discuz;

	/**
	 * <p>Getter for the field <code>discuz</code>.</p>
	 *
	 * @return a {@link iqq.im.bean.QQDiscuz} object.
	 */
	public QQDiscuz getDiscuz() {
		return discuz;
	}

	/**
	 * <p>Setter for the field <code>discuz</code>.</p>
	 *
	 * @param discuz a {@link iqq.im.bean.QQDiscuz} object.
	 */
	public void setDiscuz(QQDiscuz discuz) {
		this.discuz = discuz;
	}
	
}
