package iqq.im.bean.content;

import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 图片
 * @author ChenZhiHui
 * @create-time 2013-2-25
 */
public class OffPicItem implements ContentItem, Serializable {
	private static final long serialVersionUID = -6284318327952015407L;
	private boolean isSuccess;
	private String filePath;
	private String fileName;
	private int fileSize;
	
	public OffPicItem() {
	}

	public OffPicItem(String text) throws QQException {
		fromJson(text);
	}
	
	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileSize
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	/* (non-Javadoc)
	 * @see iqq.im.bean.content.ContentItem#getType()
	 */
	@Override
	public Type getType() {
		return Type.OFFPIC;
	}

	@Override
	public Object toJson() throws QQException {
		//[\"offpic\",\"/27d736df-2a59-4007-8701-7218bc70898d\",\"Beaver.bmp\",14173]
		JSONArray json = new JSONArray();
		json.put("offpic");
		json.put(filePath);
		json.put(fileName);
		json.put(fileSize);
		return json;
	}

	@Override
	public void fromJson(String text) throws QQException {
		//["offpic",{"success":1,"file_path":"/7acccf74-0fcd-4bbd-b885-03a5cc2f7507"}]
		try {
			JSONArray json = new JSONArray(text);
			JSONObject pic = json.getJSONObject(1);
			isSuccess = pic.getInt("success") == 1 ? true : false;
			filePath = pic.getString("file_path");
		} catch (JSONException e) {
			throw new QQException(QQErrorCode.JSON_ERROR, e);
		}
		
	}

}
