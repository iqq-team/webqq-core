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
 * Project  : MiniQQ2
 * Package  : net.solosky.miniqq
 * File     : Encryptor.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2010-4-10
 * License  : Apache License 2.0 
 */
package iqq.im.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * QQ加密解码
 *
 * @author solosky
 */
public class QQEncryptor {

    /**
     * 登录邮箱时用到的，auth_token
     *
     * @param str a {@link java.lang.String} object.
     * @return a long.
     */
    public static long time33(String str) {
        long hash = 0;
        for (int i = 0, length = str.length(); i < length; i++) {
            hash = hash * 33 + str.charAt(i);
        }
        return hash % 4294967296L;
    }

    /**
     * 获取好友列表时计算的Hash参数 v2014.06.14更新
     *
     * @param uin     当前登录用户UIN
     * @param ptwebqq Cookie中的ptwebqq的值
     * @return hash
     */
    public static String hash(String uin, String ptwebqq) {
        String s = "";
        try {
            // 先从github获取，如果失败从本地获取
            String url = "https://raw.githubusercontent.com/im-qq/webqq-core/master/src/main/resources/hash.js";
            String js = ResourceUtils.loadResource(url, "hash.js");
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByMimeType("application/javascript");
            engine.eval(js);
            Invocable inv = (Invocable) engine;
            // 调用js
            s = (String) inv.invokeFunction("hash", uin, ptwebqq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;

    }

    /**
     * 计算登录时密码HASH值
     *
     * @param uin    a long.
     * @param plain  a {@link java.lang.String} object.
     * @param verify a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String encrypt(long uin, String plain, String verify) {
        byte[] data = concat(md5(plain.getBytes()), long2bytes(uin));
        String code = byte2HexString(md5(data));
        data = md5((code + verify.toUpperCase()).getBytes());
        return byte2HexString(data);
    }

    public static String encrypt2(long uin, String plain, String verify) {
        String s = "";
        try {
            String url = "https://raw.githubusercontent.com/im-qq/webqq-core/master/src/main/resources/mq_comm.js";
            String js = ResourceUtils.loadResource(url, "mq_comm.js");
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByMimeType("application/javascript");
            engine.eval(js);
            Invocable inv = (Invocable) engine;
            String byte2HexString = byte2HexString(long2bytes(uin));
            s = (String) inv.invokeFunction("getPassword", plain, byte2HexString.toLowerCase(), verify);
        } catch (Exception e) {
        }
        return s;
    }

    private static byte[] concat(byte[] bytes1, byte[] bytes2) {
        byte[] big = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, big, 0, bytes1.length);
        System.arraycopy(bytes2, 0, big, bytes1.length, bytes2.length);
        return big;
    }

    /**
     * 计算一个字节数组的Md5值
     */
    private static byte[] md5(byte[] bytes) {
        MessageDigest dist = null;
        byte[] result = null;
        try {
            dist = MessageDigest.getInstance("MD5");
            result = dist.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return result;
    }

    /**
     * 把字节数组转换为16进制表示的字符串
     */
    private static String byte2HexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        char[] hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        if (b == null)
            return "null";

        int offset = 0;
        int len = b.length;

        // 检查索引范围
        int end = offset + len;
        if (end > b.length)
            end = b.length;

        sb.delete(0, sb.length());
        for (int i = offset; i < end; i++) {
            sb.append(hex[(b[i] & 0xF0) >>> 4]).append(hex[b[i] & 0xF]);
        }
        return sb.toString();
    }

    /**
     * 计算GTK(gtk啥东东？)
     *
     * @param skey a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String gtk(String skey) {
        int hash = 5381;
        for (int i = 0; i < skey.length(); i++) {
            hash += (hash << 5) + skey.charAt(i);
        }
        return Integer.toString(hash & 0x7fffffff);
    }

    /**
     * 把整形数转换为字节数组
     *
     * @param i a long.
     * @return an array of byte.
     */
    public static byte[] long2bytes(long i) {
        byte[] b = new byte[8];
        for (int m = 0; m < 8; m++, i >>= 8) {
            b[7 - m] = (byte) (i & 0x000000FF); // 奇怪, 在C# 整型数是低字节在前 byte[]
            // bytes =
            // BitConverter.GetBytes(i);
            // 而在JAVA里，是高字节在前
        }
        return b;
    }

    /**
     * 把一个16进制字符串转换为字节数组，字符串没有空格，所以每两个字符 一个字节
     *
     * @param s a {@link java.lang.String} object.
     * @return an array of byte.
     */
    public static byte[] hexString2Byte(String s) {
        int len = s.length();
        byte[] ret = new byte[len >>> 1];
        for (int i = 0; i <= len - 2; i += 2) {
            ret[i >>> 1] = (byte) (Integer.parseInt(s.substring(i, i + 2)
                    .trim(), 16) & 0xFF);
        }
        return ret;
    }
}
