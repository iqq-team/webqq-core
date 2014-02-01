package iqq.im.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找群信息
 * 
 * @author 元谷
 * @create-time 2013-8-13
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
	
	
	public QQGroupSearchList()
	{
		this.setCurrentPage(1);
		this.setPageSize(10);
		this.setSearchType(SearchType.QQGROUPSEARCH_TEXT);
		m_needVfcode = false;
	}
	
	public void setResult(List<QQGroupSearchInfo> groups)
	{
		this.m_GroupsResult = groups;
	}
	
	public List<QQGroupSearchInfo> getResult()
	{
		return this.m_GroupsResult;
	}
	
	public void setNeedVfcode(Boolean isNeedVfcode)
	{
		this.m_needVfcode = isNeedVfcode;
	}
	
	public Boolean getNeedVfcode()
	{
		return this.m_needVfcode;
	}

	public String getVfcode()
	{
		return this.m_vfcode;
	}
	
	public void setVfcode(String vfcode)
	{
		this.m_vfcode = vfcode;
	}
	
	public void setKeyStr(String keyStr)
	{
		this.m_keyStr = keyStr;
	}
	
	public String getKeyStr()
	{
		return this.m_keyStr;
	}
	
	public void setPageNum(int num)
	{
		this.m_pageNum = num;
	}
	
	public void setCurrentPage(int num)
	{
		this.m_currentPage = num;
	}
	
	public void setPageSize(int num)
	{
		this.m_pageSize = num;
	}
	
	public int getPageNum()
	{
		return this.m_pageNum;
	}
	
	public int getCurrentPage()
	{
		return this.m_currentPage;
	}
	
	public int getPageSize()
	{
		return this.m_pageSize;		
	}
	
	public void setSearchType(SearchType type)
	{
		m_searchType = type.ordinal();
	}
	
	public int getSearchType()
	{
		return m_searchType;
	}
	
}
