package iqq.im.bean.content;

import java.io.Serializable;

import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * 表情
 *
 * @author ChenZhiHui
 * @since 2013-2-25
 */
public class FaceItem implements ContentItem, Serializable {
	private static final long serialVersionUID = 3700557436009352505L;
	/**
	 * 表情的ID
	 */
	private int id;
	
	/**
	 * <p>Constructor for FaceItem.</p>
	 */
	public FaceItem() {
	}

	/**
	 * <p>Constructor for FaceItem.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @throws iqq.im.QQException if any.
	 */
	public FaceItem(String text) throws QQException {
		fromJson(text);
	}
	
	/**
	 * <p>Constructor for FaceItem.</p>
	 *
	 * @param id a int.
	 */
	public FaceItem(int id){
		this.id = id;
	}
	
	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see iqq.im.bean.content.ContentItem#getType()
	 */
	/** {@inheritDoc} */
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.FACE;
	}

	/** {@inheritDoc} */
	@Override
	public Object toJson() throws QQException {
		JSONArray json = new JSONArray();
		json.put("face");
		json.put(id);
		return json;
	}

	/** {@inheritDoc} */
	@Override
	public void fromJson(String text) throws QQException {
		try {
			JSONArray json = new JSONArray(text);
			id = json.getInt(1);
		} catch (JSONException e) {
			throw new QQException(QQErrorCode.JSON_ERROR, e);
		}
	}


}
