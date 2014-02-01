package iqq.im.bean;


/**
 * 用于存储搜索群的结果;
 * 
 * @author 元谷
 * @create-time 2013-8-13
 */
public class QQGroupSearchInfo {
	public String m_groupName = null;  //群名;
	public long m_groupId  = 0;   //群ID;
	public long m_groupAliseId = 0;  //群别名ID,用于协议部分;
	public int m_level = 0;    //群等级;
	public long m_owerId;   //所有者QQ号;
	public long m_createTime;   //创建的日期(时间戳);
	
	public void setGroupName(String name)
	{
		this.m_groupName = name;
	}
	
	public String getGroupName()
	{
		return this.m_groupName;
	}
	
	public void setGroupId(long id)
	{
		this.m_groupId = id;
	}
	
	public long getGroupId()
	{
		return this.m_groupId;
	}
	
	public void setLevel(int level)
	{
		this.m_level = level;
	}
	
	public int getLevel()
	{
		return this.m_level;
	}
	
	public void setOwerId(long id)
	{
		this.m_owerId = id;
	}
	
	public long getOwerId()
	{
		return this.m_owerId;
	}
	
	public long getCreateTimeStamp()
	{
		return this.m_createTime;
	}
	
	public void setCreateTimeStamp(long timeStamp)
	{
		this.m_createTime = timeStamp;
	}
	
	public void setAliseGroupId(long id)
	{
		this.m_groupAliseId = id;
	}
	
	public long getAliseGroupId()
	{
		return this.m_groupAliseId;
	}
}
