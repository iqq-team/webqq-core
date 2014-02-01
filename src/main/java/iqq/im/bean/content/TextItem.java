package iqq.im.bean.content;

import java.io.Serializable;

/**
 * 文字
 * @author ChenZhiHui
 * @create-time 2013-2-25
 */
public class TextItem implements ContentItem, Serializable {
	private static final long serialVersionUID = 7951681072055025608L;
	private String content;
	
	public TextItem() {
	}

	public TextItem(String text){
		fromJson(text);
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see iqq.im.bean.content.ContentItem#getType()
	 */
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.TEXT;
	}

	@Override
	public Object toJson(){
		return content;
	}

	@Override
	public void fromJson(String text) {
		content = text;
	}


}
