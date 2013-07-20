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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 
 * @author solosky <solosky772@qq.com>
 */
public class QQEncryptor {
	/**
	 * 获取好友列表时计算的HASH参数 基本上照抄eqq.all.js代码即可
	 * 
	 * @param uin
	 *            当前登录用户UIN
	 * @param ptwebqq
	 *            Cookie中的ptwebqq的值
	 * @return
	 */
	public static String hash(String uin, String ptwebqq) {
		String a = uin;
		String e = ptwebqq;
		byte[] c = new byte[a.length()];
		for (int i = 0; i < a.length(); i++) {
			c[i] = (byte) (a.charAt(i) - '0');
		}
		int k = -1;
		for (int b = 0, d = 0; d < c.length; d++) {
			b += c[d];
			b %= e.length();
			int f = 0;
			if (b + 4 > e.length()) {
				for (int g = 4 + b - e.length(), h = 0; h < 4; h++) {
					if (h < g) {
						f |= (e.charAt(b + h) & 255) << (3 - h) * 8;
					} else {
						f |= (e.charAt(h - g) & 255) << (3 - h) * 8;
					}
				}
			} else {
				for (int h = 0; h < 4; h++) {
					f |= (e.charAt(b + h) & 255) << (3 - h) * 8;
				}
			}
			k ^= f;
		}
		return Integer.toHexString(k).toUpperCase();
	}

	/**
	 * 获取好友列表时计算的HASH参数 v2012.04.19更新
	 * 
	 * @param uin
	 *            当前登录用户UIN
	 * @param ptwebqq
	 *            Cookie中的ptwebqq的值
	 * @return
	 */
	public static String hash2(String uin, String ptwebqq) {
		String i = ptwebqq;
		String b = uin;
		String a = i + "password error";
		StringBuffer s = new StringBuffer();
		while (s.length() < a.length()) {
			s.append(b);
		}
		String ss = s.substring(0, a.length());
		byte[] j = new byte[a.length()];
		for (int d = 0; d < a.length(); d++) {
			j[d] = (byte) (ss.charAt(d) ^ a.charAt(d));
		}
		return byte2HexString(j);
	}

	/**
	 * 获取好友列表时计算的HASH参数 v2012.07.20更新 
	 * 感谢IQQ群里面的@枫叶(953884102)提供
	 * 
	 * @param uin
	 *            当前登录用户UIN
	 * @param ptwebqq
	 *            Cookie中的ptwebqq的值
	 * @return
	 */
	public static String hash3(String uin, String ptwebqq) {
		String i = ptwebqq;
		String b = uin;
		long[] a = new long[i.length()];
		char[] iString = i.toCharArray();
		for (int s = 0; s < i.length(); s++) {
			a[s % 4] ^= (int) iString[s];
		}
		String[] j = { "EC", "OK" };
		long[] d = new long[4];
		d[0] = Long.parseLong(b) >> 24 & 255 ^ (long) j[0].toCharArray()[0];
		d[1] = Long.parseLong(b) >> 16 & 255 ^ (long) j[0].toCharArray()[1];
		d[2] = Long.parseLong(b) >> 8 & 255 ^ (long) j[1].toCharArray()[0];
		d[3] = Long.parseLong(b) & 255 ^ (long) j[1].toCharArray()[1];

		long[] k = new long[8];
		for (int s = 0; s < 8; s++)
			k[s] = s % 2 == 0 ? a[s >> 1] : d[s >> 1];
		String[] aa = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F" };

		String dd = "";
		for (int s = 0; s < k.length; s++) {
			dd += aa[(int) (k[s] >> 4 & 15)];
			dd += aa[(int) (k[s] & 15)];
		}
		return dd;
	}

	/***
	 * 计算登录时密码HASH值
	 * 
	 * @param uin
	 * @param plain
	 * @param verify
	 * @return
	 */
	public static String encrypt(long uin, String plain, String verify) {
		byte[] data = concat(md5(plain.getBytes()), long2bytes(uin));
		String code = byte2HexString(md5(data));
		data = md5((code + verify.toUpperCase()).getBytes());
		return byte2HexString(data);
	}

	private static byte[] concat(byte[] bytes1, byte[] bytes2) {
		byte[] big = new byte[bytes1.length + bytes2.length];
		System.arraycopy(bytes1, 0, big, 0, bytes1.length);
		System.arraycopy(bytes2, 0, big, bytes1.length, bytes2.length);
		return big;
	}

	/**
	 * 计算一个字节数组的Md5值
	 * 
	 * @param bytes
	 * @return
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
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2HexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		char[] hex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F' };
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
	 * @param skey
	 * @return
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
	 * @param i
	 * @return
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
	 * @param s
	 * @return
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
