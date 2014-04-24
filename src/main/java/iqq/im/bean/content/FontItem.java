package iqq.im.bean.content;

import java.io.Serializable;

import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 字体
 * 
 * @author ChenZhiHui
 * @create-time 2013-2-25
 */
public class FontItem implements ContentItem, Serializable{
	private static final long serialVersionUID = -4303036964268065910L;
	private String name = "宋体";
	private int size = 12;
	private boolean bold;
	private boolean underline;
	private boolean italic;
	private int color = 0;

	public FontItem() {
	}

	public FontItem(String text) throws QQException {
		fromJson(text);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see iqq.im.bean.content.ContentItem#getType()
	 */
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.FONT;
	}

	@Override
	public Object toJson() throws QQException {
		// ["font",{"size":10,"color":"808080","style":[0,0,0],"name":"\u65B0\u5B8B\u4F53"}]
		try {
			JSONArray json = new JSONArray();
			json.put("font");
			JSONObject font = new JSONObject();
			font.put("size", size);
			font.put("color", String.format("%06x", color));
			JSONArray style = new JSONArray();
			style.put(bold ? 1 : 0);
			style.put(italic ? 1 : 0);
			style.put(underline ? 1 : 0);
			font.put("style", style);
			font.put("name", name);
			json.put(font);
			return json;
		} catch (JSONException e) {
			throw new QQException(QQErrorCode.JSON_ERROR, e);
		}
	}

	@Override
	public void fromJson(String text) throws QQException {
		try {
			JSONArray json = new JSONArray(text);
			JSONObject font = json.getJSONObject(1);
			size = font.getInt("size");
			color = Integer.parseInt(font.getString("color"), 16);
			JSONArray style = font.getJSONArray("style");
			bold = style.getInt(0) == 1 ? true : false;
			italic = style.getInt(1) == 1 ? true : false;
			underline = style.getInt(2) == 1 ? true : false;
			name = font.getString("name");
		} catch (NumberFormatException e) {
			throw new QQException(QQErrorCode.UNKNOWN_ERROR, e);
		} catch (JSONException e) {
			throw new QQException(QQErrorCode.JSON_ERROR, e);
		}

	}

}
