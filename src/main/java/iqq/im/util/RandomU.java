package iqq.im.util;

import java.util.Random;

// +----------------------------------------------------------------------
// | CreateTime: 16/4/8 
// +----------------------------------------------------------------------
// | Author:     xab(89415799@qq.com)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.xueyong.net.cn
// +----------------------------------------------------------------------
public class RandomU {
    static Random r = new Random();
    static String wordStr = "0123456789ABCDEF";
    static char[] wordChars = wordStr.toCharArray();
    static String numStr = "0123456789";
    static char[] numChars = numStr.toCharArray();
    static String charStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static char[] charChars = charStr.toCharArray();

    // 产生随机字符串
    public static String randWord(int length) {
        char[] buf = new char[length];
        int rnd;
        for (int i = 0; i < length; i++) {
            rnd = Math.abs(r.nextInt()) % wordChars.length;

            buf[i] = wordChars[rnd];
        }
        return new String(buf);
    }

    // 产生随机字符串
    public static String randChar(int length) {
        char[] buf = new char[length];
        int rnd;
        for (int i = 0; i < length; i++) {
            rnd = Math.abs(r.nextInt()) % charChars.length;

            buf[i] = charChars[rnd];
        }
        return new String(buf);
    }

    // 产生随机字符串
    public static String randNum(int length) {
        char[] buf = new char[length];
        int rnd;
        for (int i = 0; i < length; i++) {
            rnd = Math.abs(r.nextInt()) % numChars.length;

            buf[i] = numChars[rnd];
        }
        return new String(buf);
    }

    public static int randomNum(int max) {
        return new Random().nextInt(max);
    }

    public static int randomNum(int min, int max) {
        return new Random().nextInt(max) + min;
    }

    public static String UUID() {
        return String.valueOf(java.util.UUID.randomUUID());
    }
}
