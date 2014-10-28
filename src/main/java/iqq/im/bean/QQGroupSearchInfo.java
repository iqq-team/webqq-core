package iqq.im.bean;


/**
 * 用于存储搜索群的结果;
 *
 * @author 元谷
 * @since 2013-8-13
 */
public class QQGroupSearchInfo {
	public String m_groupName = null;  //群名;
	public long m_groupId  = 0;   //群ID;
	public long m_groupAliseId = 0;  //群别名ID,用于协议部分;
	public int m_level = 0;    //群等级;
	public long m_owerId;   //所有者QQ号;
	public long m_createTime;   //创建的日期(时间戳);
	
	/**
	 * <p>setGroupName.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setGroupName(String name)
	{
		this.m_groupName = name;
	}
	
	/**
	 * <p>getGroupName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getGroupName()
	{
		return this.m_groupName;
	}
	
	/**
	 * <p>setGroupId.</p>
	 *
	 * @param id a long.
	 */
	public void setGroupId(long id)
	{
		this.m_groupId = id;
	}
	
	/**
	 * <p>getGroupId.</p>
	 *
	 * @return a long.
	 */
	public long getGroupId()
	{
		return this.m_groupId;
	}
	
	/**
	 * <p>setLevel.</p>
	 *
	 * @param level a int.
	 */
	public void setLevel(int level)
	{
		this.m_level = level;
	}
	
	/**
	 * <p>getLevel.</p>
	 *
	 * @return a int.
	 */
	public int getLevel()
	{
		return this.m_level;
	}
	
	/**
	 * <p>setOwerId.</p>
	 *
	 * @param id a long.
	 */
	public void setOwerId(long id)
	{
		this.m_owerId = id;
	}
	
	/**
	 * <p>getOwerId.</p>
	 *
	 * @return a long.
	 */
	public long getOwerId()
	{
		return this.m_owerId;
	}
	
	/**
	 * <p>getCreateTimeStamp.</p>
	 *
	 * @return a long.
	 */
	public long getCreateTimeStamp()
	{
		return this.m_createTime;
	}
	
	/**
	 * <p>setCreateTimeStamp.</p>
	 *
	 * @param timeStamp a long.
	 */
	public void setCreateTimeStamp(long timeStamp)
	{
		this.m_createTime = timeStamp;
	}
	
	/**
	 * <p>setAliseGroupId.</p>
	 *
	 * @param id a long.
	 */
	public void setAliseGroupId(long id)
	{
		this.m_groupAliseId = id;
	}
	
	/**
	 * <p>getAliseGroupId.</p>
	 *
	 * @return a long.
	 */
	public long getAliseGroupId()
	{
		return this.m_groupAliseId;
	}
}
