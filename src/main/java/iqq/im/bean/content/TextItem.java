package iqq.im.bean.content;

import java.io.Serializable;

/**
 * 文字
 *
 * @author ChenZhiHui
 * @since 2013-2-25
 */
public class TextItem implements ContentItem, Serializable {
	private static final long serialVersionUID = 7951681072055025608L;
	private String content;
	
	/**
	 * <p>Constructor for TextItem.</p>
	 */
	public TextItem() {
	}

	/**
	 * <p>Constructor for TextItem.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public TextItem(String text){
		fromJson(text);
	}
	
	/**
	 * <p>Getter for the field <code>content</code>.</p>
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see iqq.im.bean.content.ContentItem#getType()
	 */
	/** {@inheritDoc} */
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.TEXT;
	}

	/** {@inheritDoc} */
	@Override
	public Object toJson(){
		return content;
	}

	/** {@inheritDoc} */
	@Override
	public void fromJson(String text) {
		content = text;
	}


}
