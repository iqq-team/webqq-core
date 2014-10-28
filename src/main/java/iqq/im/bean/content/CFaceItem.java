package iqq.im.bean.content;

import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 图片
 *
 * @author ChenZhiHui
 * @since 2013-2-25
 */
public class CFaceItem implements ContentItem, Serializable {
	private static final long serialVersionUID = -5851165085517534445L;
	private boolean isSuccess;
	private long fileId;
	private String fileName;
	private String key;
	private String server;

	/**
	 * <p>Constructor for CFaceItem.</p>
	 */
	public CFaceItem() {
	}

	/**
	 * <p>Constructor for CFaceItem.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @throws iqq.im.QQException if any.
	 */
	public CFaceItem(String text) throws QQException {
		fromJson(text);
	}

	/**
	 * <p>isSuccess.</p>
	 *
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * <p>setSuccess.</p>
	 *
	 * @param isSuccess
	 *            the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * <p>Getter for the field <code>fileName</code>.</p>
	 *
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * <p>Setter for the field <code>fileName</code>.</p>
	 *
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * <p>Getter for the field <code>fileId</code>.</p>
	 *
	 * @return the fileId
	 */
	public long getFileId() {
		return fileId;
	}

	/**
	 * <p>Setter for the field <code>fileId</code>.</p>
	 *
	 * @param fileId
	 *            the fileId to set
	 */
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	/**
	 * <p>Getter for the field <code>key</code>.</p>
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * <p>Setter for the field <code>key</code>.</p>
	 *
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * <p>Getter for the field <code>server</code>.</p>
	 *
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * <p>Setter for the field <code>server</code>.</p>
	 *
	 * @param server
	 *            the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see iqq.im.bean.content.ContentItem#getType()
	 */
	/** {@inheritDoc} */
	@Override
	public Type getType() {
		return Type.CFACE;
	}

	/** {@inheritDoc} */
	@Override
	public Object toJson() throws QQException {
		// [\"cface\",\"group\",\"5F7E31F0001EF4310865F1FF4549B12B.jPg\"]
		JSONArray json = new JSONArray();
		json.put("cface");
		json.put("group");
		json.put(fileName);
		return json;
	}

	/** {@inheritDoc} */
	@Override
	public void fromJson(String text) throws QQException {
		// ["cface",{"name":"{94E8E5BA-9304-043E-F028-93986EEF0450}.jpg","file_id":445318646,"key":"PN576E5TyB53muY9","server":"124.115.11.111:8000"}]
		//["cface","4D72EF8CF64D53DECB31ABC2B601AB23.jpg",""],
		try {
			JSONArray json = new JSONArray(text);
			if(json.get(1) instanceof String){
				this.setFileName((String)json.get(1));
			}else{
				JSONObject pic = json.getJSONObject(1);
				this.setFileName(pic.getString("name"));
				this.setFileId(pic.getLong("file_id"));
				this.setKey(pic.getString("key"));
				this.setServer(pic.getString("server"));
			}
		} catch (JSONException e) {
			throw new QQException(QQErrorCode.JSON_ERROR, e);
		}

	}

}
