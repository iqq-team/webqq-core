package iqq.im;

import iqq.im.util.QQEncryptor;

import java.io.IOException;

/**
 * Created by Tony on 3/11/15.
 */
public class QQEncryptorTest {

    public static void main(String[] args) throws IOException {
        System.out.println(QQEncryptor.hash("6208317", "test"));
        System.out.println(QQEncryptor.encrypt2(6208317, "test", "test"));
    }
}
