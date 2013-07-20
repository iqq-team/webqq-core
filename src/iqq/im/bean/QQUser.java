/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Project  : WebQQCoreAsync
 * Package  : iqq.im.bean
 * File     : QQMember.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package iqq.im.bean;

import iqq.im.util.DateUtils;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * QQ普通用户，保存了所有用户的基本信息
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public abstract class QQUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private long uin = -2;
	private long qq;
	private QQStatus status;
	private QQClientType clientType; // 客户类型
	private QQLevel level;	//等级
	private long loginDate; // 登录时间
	private String nickname; // 昵称
	private String sign; // 个性签名
	private String gender; // 性别
	private Date birthday; // 出生日期
	private String phone; // 电话
	private String mobile; // 手机
	private String email; // 邮箱
	private String college; // 毕业院校
	private int regTime; // 註冊時間
	private int constel; // 星座
	private int blood; // 血型
	private String homepage; // 个人主页
	private int stat; // 统计
	private boolean isVip; // 是否为VIP
	private int vipLevel; // VIP等级
	private String country; // 国家
	private String province; // 省
	private String city; // 城市
	private String personal; // 个人说明
	private String occupation; // 职业
	private int chineseZodiac; // 生肖
	private int flag;
	private int cip;
	private transient BufferedImage face; // 头像,不能被序列化
	private QQAllow allow;		//对方加好友验证请求设置
	

	public QQUser() {
		status = QQStatus.OFFLINE;
		level = new QQLevel();
	}

	public long getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(long loginDate) {
		this.loginDate = loginDate;
	}

	public QQStatus getStatus() {
		return status;
	}

	public void setStatus(QQStatus status) {
		this.status = status;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public int getChineseZodiac() {
		return chineseZodiac;
	}

	public void setChineseZodiac(int chineseZodiac) {
		this.chineseZodiac = chineseZodiac;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public int getConstel() {
		return constel;
	}

	public void setConstel(int constel) {
		this.constel = constel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public QQLevel getLevel() {
		return level;
	}

	public void setLevel(QQLevel level) {
		this.level = level;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getRegTime() {
		return regTime;
	}

	public void setRegTime(int regTime) {
		this.regTime = regTime;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public long getUin() {
		return uin;
	}

	public void setUin(long uin) {
		this.uin = uin;
	}

	public int getCip() {
		return cip;
	}

	public void setCip(int cip) {
		this.cip = cip;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public BufferedImage getFace() {
		return face;
	}

	public void setFace(BufferedImage face) {
		this.face = face;
	}

	public QQClientType getClientType() {
		return clientType;
	}

	public void setClientType(QQClientType clientType) {
		this.clientType = clientType;
	}

	public QQAllow getAllow() {
		return allow;
	}

	public void setAllow(QQAllow allow) {
		this.allow = allow;
	}

	@Override
	public String toString() {
		return "QQUser [qq=" + qq + ", uin=" + uin + ", nickname=" + nickname + ", status="
				+ status + "]";
	}
	
	/**
	 * @return the qq
	 */
	public long getQQ() {
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQQ(long qq) {
		this.qq = qq;
	}

	@Override
	public int hashCode() {
		return (int) this.getUin();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		if (obj instanceof QQUser) {
			QQUser m = (QQUser) obj;
			if (m.getUin() == this.getUin()) {
				return true;
			}
		}
		return false;
	}
	
	public void parseFromJson(JSONObject json) throws JSONException{
		this.setOccupation(json.optString("occupation"));
		this.setPhone(json.optString("phone"));
		this.setAllow(QQAllow.values()[json.optInt("allow")]);
		this.setCollege(json.optString("college"));
		this.setUin(json.optLong("uin"));
		this.setConstel(json.optInt("constel"));
		this.setBlood(json.optInt("blood"));
		this.setHomepage(json.optString("homepage"));
		this.setStat(json.optInt("stat"));
		this.setVipLevel(json.optInt("vip_info")); // VIP等级 0为非VIP
		this.setCountry(json.optString("country"));
		this.setCity(json.optString("city"));
		this.setPersonal(json.optString("personal"));
		this.setNickname(json.optString("nick"));
		this.setChineseZodiac(json.optInt("shengxiao"));
		this.setEmail(json.optString("vip_info"));
		this.setProvince(json.optString("province"));
		this.setGender(json.optString("gender"));
		this.setMobile(json.optString("mobile"));
		if (!json.isNull("reg_time")) {
			this.setRegTime(json.getInt("reg_time"));
		}
		if (!json.isNull("client_type")) {
			this.setClientType(QQClientType.valueOfRaw(json.getInt("client_type")));
		}
		if(!json.isNull("birthday")){
			try {
				this.setBirthday(DateUtils.parse(json.getJSONObject("birthday")));
			} catch (ParseException e) {
				this.setBirthday(null);
			}
		}
	}
}
