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
 * @author solosky
 */
public abstract class QQUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private long uin = -2;
    private long qq;
    private QQStatus status;
    private QQClientType clientType; // 客户类型
    private QQLevel level;    //等级
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
    private QQAllow allow;        //对方加好友验证请求设置


    /**
     * <p>Constructor for QQUser.</p>
     */
    public QQUser() {
        status = QQStatus.OFFLINE;
        level = new QQLevel();
    }

    /**
     * <p>Getter for the field <code>loginDate</code>.</p>
     *
     * @return a long.
     */
    public long getLoginDate() {
        return loginDate;
    }

    /**
     * <p>Setter for the field <code>loginDate</code>.</p>
     *
     * @param loginDate a long.
     */
    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * <p>Getter for the field <code>status</code>.</p>
     *
     * @return a {@link iqq.im.bean.QQStatus} object.
     */
    public QQStatus getStatus() {
        return status;
    }

    /**
     * <p>Setter for the field <code>status</code>.</p>
     *
     * @param status a {@link iqq.im.bean.QQStatus} object.
     */
    public void setStatus(QQStatus status) {
        this.status = status;
    }

    /**
     * <p>Getter for the field <code>flag</code>.</p>
     *
     * @return a int.
     */
    public int getFlag() {
        return flag;
    }

    /**
     * <p>Setter for the field <code>flag</code>.</p>
     *
     * @param flag a int.
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * <p>Getter for the field <code>sign</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSign() {
        return sign;
    }

    /**
     * <p>Setter for the field <code>sign</code>.</p>
     *
     * @param sign a {@link java.lang.String} object.
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * <p>Getter for the field <code>birthday</code>.</p>
     *
     * @return a {@link java.util.Date} object.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * <p>Setter for the field <code>birthday</code>.</p>
     *
     * @param birthday a {@link java.util.Date} object.
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * <p>Getter for the field <code>blood</code>.</p>
     *
     * @return a int.
     */
    public int getBlood() {
        return blood;
    }

    /**
     * <p>Setter for the field <code>blood</code>.</p>
     *
     * @param blood a int.
     */
    public void setBlood(int blood) {
        this.blood = blood;
    }

    /**
     * <p>Getter for the field <code>chineseZodiac</code>.</p>
     *
     * @return a int.
     */
    public int getChineseZodiac() {
        return chineseZodiac;
    }

    /**
     * <p>Setter for the field <code>chineseZodiac</code>.</p>
     *
     * @param chineseZodiac a int.
     */
    public void setChineseZodiac(int chineseZodiac) {
        this.chineseZodiac = chineseZodiac;
    }

    /**
     * <p>Getter for the field <code>city</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>Setter for the field <code>city</code>.</p>
     *
     * @param city a {@link java.lang.String} object.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * <p>Getter for the field <code>college</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCollege() {
        return college;
    }

    /**
     * <p>Setter for the field <code>college</code>.</p>
     *
     * @param college a {@link java.lang.String} object.
     */
    public void setCollege(String college) {
        this.college = college;
    }

    /**
     * <p>Getter for the field <code>constel</code>.</p>
     *
     * @return a int.
     */
    public int getConstel() {
        return constel;
    }

    /**
     * <p>Setter for the field <code>constel</code>.</p>
     *
     * @param constel a int.
     */
    public void setConstel(int constel) {
        this.constel = constel;
    }

    /**
     * <p>Getter for the field <code>country</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCountry() {
        return country;
    }

    /**
     * <p>Setter for the field <code>country</code>.</p>
     *
     * @param country a {@link java.lang.String} object.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * <p>Getter for the field <code>email</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>Setter for the field <code>email</code>.</p>
     *
     * @param email a {@link java.lang.String} object.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>Getter for the field <code>gender</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getGender() {
        return gender;
    }

    /**
     * <p>Setter for the field <code>gender</code>.</p>
     *
     * @param gender a {@link java.lang.String} object.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * <p>Getter for the field <code>homepage</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * <p>Setter for the field <code>homepage</code>.</p>
     *
     * @param homepage a {@link java.lang.String} object.
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * <p>Getter for the field <code>level</code>.</p>
     *
     * @return a {@link iqq.im.bean.QQLevel} object.
     */
    public QQLevel getLevel() {
        return level;
    }

    /**
     * <p>Setter for the field <code>level</code>.</p>
     *
     * @param level a {@link iqq.im.bean.QQLevel} object.
     */
    public void setLevel(QQLevel level) {
        this.level = level;
    }

    /**
     * <p>Getter for the field <code>mobile</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * <p>Setter for the field <code>mobile</code>.</p>
     *
     * @param mobile a {@link java.lang.String} object.
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * <p>Getter for the field <code>nickname</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * <p>Setter for the field <code>nickname</code>.</p>
     *
     * @param nickname a {@link java.lang.String} object.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * <p>Getter for the field <code>occupation</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * <p>Setter for the field <code>occupation</code>.</p>
     *
     * @param occupation a {@link java.lang.String} object.
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * <p>Getter for the field <code>personal</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPersonal() {
        return personal;
    }

    /**
     * <p>Setter for the field <code>personal</code>.</p>
     *
     * @param personal a {@link java.lang.String} object.
     */
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    /**
     * <p>Getter for the field <code>phone</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * <p>Setter for the field <code>phone</code>.</p>
     *
     * @param phone a {@link java.lang.String} object.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * <p>Getter for the field <code>province</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getProvince() {
        return province;
    }

    /**
     * <p>Setter for the field <code>province</code>.</p>
     *
     * @param province a {@link java.lang.String} object.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * <p>Getter for the field <code>regTime</code>.</p>
     *
     * @return a int.
     */
    public int getRegTime() {
        return regTime;
    }

    /**
     * <p>Setter for the field <code>regTime</code>.</p>
     *
     * @param regTime a int.
     */
    public void setRegTime(int regTime) {
        this.regTime = regTime;
    }

    /**
     * <p>Getter for the field <code>stat</code>.</p>
     *
     * @return a int.
     */
    public int getStat() {
        return stat;
    }

    /**
     * <p>Setter for the field <code>stat</code>.</p>
     *
     * @param stat a int.
     */
    public void setStat(int stat) {
        this.stat = stat;
    }

    /**
     * <p>Getter for the field <code>uin</code>.</p>
     *
     * @return a long.
     */
    public long getUin() {
        return uin;
    }

    /**
     * <p>Setter for the field <code>uin</code>.</p>
     *
     * @param uin a long.
     */
    public void setUin(long uin) {
        this.uin = uin;
    }

    /**
     * <p>Getter for the field <code>cip</code>.</p>
     *
     * @return a int.
     */
    public int getCip() {
        return cip;
    }

    /**
     * <p>Setter for the field <code>cip</code>.</p>
     *
     * @param cip a int.
     */
    public void setCip(int cip) {
        this.cip = cip;
    }

    /**
     * <p>isVip.</p>
     *
     * @return a boolean.
     */
    public boolean isVip() {
        return isVip;
    }

    /**
     * <p>setVip.</p>
     *
     * @param isVip a boolean.
     */
    public void setVip(boolean isVip) {
        this.isVip = isVip;
    }

    /**
     * <p>Getter for the field <code>vipLevel</code>.</p>
     *
     * @return a int.
     */
    public int getVipLevel() {
        return vipLevel;
    }

    /**
     * <p>Setter for the field <code>vipLevel</code>.</p>
     *
     * @param vipLevel a int.
     */
    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    /**
     * <p>Getter for the field <code>face</code>.</p>
     *
     * @return a {@link java.awt.image.BufferedImage} object.
     */
    public BufferedImage getFace() {
        return face;
    }

    /**
     * <p>Setter for the field <code>face</code>.</p>
     *
     * @param face a {@link java.awt.image.BufferedImage} object.
     */
    public void setFace(BufferedImage face) {
        this.face = face;
    }

    /**
     * <p>Getter for the field <code>clientType</code>.</p>
     *
     * @return a {@link iqq.im.bean.QQClientType} object.
     */
    public QQClientType getClientType() {
        return clientType;
    }

    /**
     * <p>Setter for the field <code>clientType</code>.</p>
     *
     * @param clientType a {@link iqq.im.bean.QQClientType} object.
     */
    public void setClientType(QQClientType clientType) {
        this.clientType = clientType;
    }

    /**
     * <p>Getter for the field <code>allow</code>.</p>
     *
     * @return a {@link iqq.im.bean.QQAllow} object.
     */
    public QQAllow getAllow() {
        return allow;
    }

    /**
     * <p>Setter for the field <code>allow</code>.</p>
     *
     * @param allow a {@link iqq.im.bean.QQAllow} object.
     */
    public void setAllow(QQAllow allow) {
        this.allow = allow;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "QQUser [qq=" + qq + ", uin=" + uin + ", nickname=" + nickname + ", status="
                + status + "]";
    }

    /**
     * <p>getQQ.</p>
     *
     * @return the qq
     */
    public long getQQ() {
        return qq;
    }

    /**
     * <p>setQQ.</p>
     *
     * @param qq the qq to set
     */
    public void setQQ(long qq) {
        this.qq = qq;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return (int) this.getUin();
    }

    /** {@inheritDoc} */
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

    /**
     * <p>parseFromJson.</p>
     *
     * @param json a {@link org.json.JSONObject} object.
     * @throws org.json.JSONException if any.
     */
    public void parseFromJson(JSONObject json) throws JSONException {
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
        if (!json.isNull("birthday")) {
            try {
                this.setBirthday(DateUtils.parse(json.getJSONObject("birthday")));
            } catch (ParseException e) {
                this.setBirthday(null);
            }
        }
    }


}
