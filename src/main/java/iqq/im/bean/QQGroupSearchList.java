package iqq.im.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找群信息
 *
 * @author 元谷
 * @since 2013-8-13
 */
public class QQGroupSearchList {
	public enum SearchType 
	{
		QQGROUPSEARCH_TEXT,  //0表示QQ号码
		QQGROUPSEARCH_KEY,  //1表示关键字
		
	}
	private int m_pageNum;   //页数;
	private int m_currentPage;  //当前页数;
	private int m_pageSize;    //一页的信息量;
	private int m_searchType;   //1表示关键字，0表示QQ号码
	private String m_keyStr;    //搜索的关键字
	private Boolean m_needVfcode;  //是否要验证码;
	private String m_vfcode;  //验证码数据
	private List<QQGroupSearchInfo> m_GroupsResult = new ArrayList<QQGroupSearchInfo>();  //搜索出的群集合;
	
	
	/**
	 * <p>Constructor for QQGroupSearchList.</p>
	 */
	public QQGroupSearchList()
	{
		this.setCurrentPage(1);
		this.setPageSize(10);
		this.setSearchType(SearchType.QQGROUPSEARCH_TEXT);
		m_needVfcode = false;
	}
	
	/**
	 * <p>setResult.</p>
	 *
	 * @param groups a {@link java.util.List} object.
	 */
	public void setResult(List<QQGroupSearchInfo> groups)
	{
		this.m_GroupsResult = groups;
	}
	
	/**
	 * <p>getResult.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<QQGroupSearchInfo> getResult()
	{
		return this.m_GroupsResult;
	}
	
	/**
	 * <p>setNeedVfcode.</p>
	 *
	 * @param isNeedVfcode a {@link java.lang.Boolean} object.
	 */
	public void setNeedVfcode(Boolean isNeedVfcode)
	{
		this.m_needVfcode = isNeedVfcode;
	}
	
	/**
	 * <p>getNeedVfcode.</p>
	 *
	 * @return a {@link java.lang.Boolean} object.
	 */
	public Boolean getNeedVfcode()
	{
		return this.m_needVfcode;
	}

	/**
	 * <p>getVfcode.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getVfcode()
	{
		return this.m_vfcode;
	}
	
	/**
	 * <p>setVfcode.</p>
	 *
	 * @param vfcode a {@link java.lang.String} object.
	 */
	public void setVfcode(String vfcode)
	{
		this.m_vfcode = vfcode;
	}
	
	/**
	 * <p>setKeyStr.</p>
	 *
	 * @param keyStr a {@link java.lang.String} object.
	 */
	public void setKeyStr(String keyStr)
	{
		this.m_keyStr = keyStr;
	}
	
	/**
	 * <p>getKeyStr.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getKeyStr()
	{
		return this.m_keyStr;
	}
	
	/**
	 * <p>setPageNum.</p>
	 *
	 * @param num a int.
	 */
	public void setPageNum(int num)
	{
		this.m_pageNum = num;
	}
	
	/**
	 * <p>setCurrentPage.</p>
	 *
	 * @param num a int.
	 */
	public void setCurrentPage(int num)
	{
		this.m_currentPage = num;
	}
	
	/**
	 * <p>setPageSize.</p>
	 *
	 * @param num a int.
	 */
	public void setPageSize(int num)
	{
		this.m_pageSize = num;
	}
	
	/**
	 * <p>getPageNum.</p>
	 *
	 * @return a int.
	 */
	public int getPageNum()
	{
		return this.m_pageNum;
	}
	
	/**
	 * <p>getCurrentPage.</p>
	 *
	 * @return a int.
	 */
	public int getCurrentPage()
	{
		return this.m_currentPage;
	}
	
	/**
	 * <p>getPageSize.</p>
	 *
	 * @return a int.
	 */
	public int getPageSize()
	{
		return this.m_pageSize;		
	}
	
	/**
	 * <p>setSearchType.</p>
	 *
	 * @param type a {@link iqq.im.bean.QQGroupSearchList.SearchType} object.
	 */
	public void setSearchType(SearchType type)
	{
		m_searchType = type.ordinal();
	}
	
	/**
	 * <p>getSearchType.</p>
	 *
	 * @return a int.
	 */
	public int getSearchType()
	{
		return m_searchType;
	}
	
}
