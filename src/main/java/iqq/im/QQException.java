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
 * Project  : WebQQCore
 * Package  : iqq.im
 * File     : QQException.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0
 */
package iqq.im;

/**
 * QQ异常，所有的接口都需要声明抛出QQ异常
 *
 * @author solosky
 */
public class QQException extends Exception {
    private static final long serialVersionUID = 1L;
    private QQErrorCode errorCode;

    /**
     * <p>Constructor for QQException.</p>
     *
     * @param errorCode a {@link iqq.im.QQException.QQErrorCode} object.
     */
    public QQException(QQErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    /**
     * <p>Constructor for QQException.</p>
     *
     * @param errorCode a {@link iqq.im.QQException.QQErrorCode} object.
     * @param msg       a {@link java.lang.String} object.
     */
    public QQException(QQErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    /**
     * <p>Constructor for QQException.</p>
     *
     * @param errorCode a {@link iqq.im.QQException.QQErrorCode} object.
     * @param errorCode a {@link iqq.im.QQException.QQErrorCode} object.
     * @param errorCode a {@link iqq.im.QQException.QQErrorCode} object.
     * @param errorCode a {@link iqq.im.QQException.QQErrorCode} object.
     * @param errorCode a {@link iqq.im.QQException.QQErrorCode} object.
     * @param errorCode a {@link iqq.im.QQException.QQErrorCode} object.
     * @param e         a {@link java.lang.Throwable} object.
     */
    public QQException(QQErrorCode errorCode, Throwable e) {
        super(errorCode.toString(), e);
        this.errorCode = errorCode;
    }

    /**
     * <p>getError.</p>
     *
     * @return a {@link iqq.im.QQException.QQErrorCode} object.
     */
    public QQErrorCode getError() {
        return errorCode;
    }

    public enum QQErrorCode {
        /**
         * 二维码未失效
         **/
        QRCODE_OK,
        /**
         * 二维码失效
         **/
        QRCODE_INVALID,
        /**
         * 二维码认证中
         **/
        QRCODE_AUTH,
        /**
         * 登录凭证实效
         */
        INVALID_LOGIN_AUTH,
        /**
         * 参数无效
         */
        INVALID_PARAMETER,
        /**
         * 获取好友头像失败
         */
        UNEXPECTED_RESPONSE,
        /**
         * 无效的用户
         */
        INVALID_USER,
        /**
         * 密码错误
         */
        WRONG_PASSWORD,
        /**
         * 验证码错误
         */
        WRONG_CAPTCHA,
        /**
         * 需要验证
         */
        NEED_CAPTCHA,
        /**
         * 网络错误
         */
        IO_ERROR,
        /**
         * 网络超时
         */
        IO_TIMEOUT,
        /**
         * 用户没有找到
         */
        USER_NOT_FOUND,
        /**
         * 回答验证问题错误
         */
        WRONG_ANSWER,
        /**
         * 用户拒绝添加好友
         */
        USER_REFUSE_ADD,
        /**
         * 无法解析的结果
         */
        INVALID_RESPONSE,
        /**
         * 错误的状态码
         */
        ERROR_HTTP_STATUS,
        /**
         * 初始化错误
         */
        INIT_ERROR,
        /**
         * 用户取消操作
         */
        CANCELED,
        /**
         * 无法取消
         */
        UNABLE_CANCEL,
        /**
         * JSON解析出错
         */
        JSON_ERROR,
        /**
         * 未知的错误
         */
        UNKNOWN_ERROR,
        /**
         * 等待事件被中断
         */
        WAIT_INTERUPPTED,
        /**
         * 等待超时
         */
        WAIT_TIMEOUT,
        /**
         * 发送消息失败
         */
        SNED_MSG_ERROR
    }

}
