package iqq.im.bean.content;

import java.io.Serializable;

import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * 表情
 * @author ChenZhiHui
 * @create-time 2013-2-25
 */
public class FaceItem implements ContentItem, Serializable {
	private static final long serialVersionUID = 3700557436009352505L;
	/**
	 * 表情的ID
	 */
	private int id;
	
	public FaceItem() {
	}

	public FaceItem(String text) throws QQException {
		fromJson(text);
	}
	
	public FaceItem(int id){
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see iqq.im.bean.content.ContentItem#getType()
	 */
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.FACE;
	}

	@Override
	public Object toJson() throws QQException {
		JSONArray json = new JSONArray();
		json.put("face");
		json.put(id);
		return json;
	}

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
