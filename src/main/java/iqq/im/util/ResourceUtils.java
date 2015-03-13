package iqq.im.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Tony on 3/11/15.
 */
public class ResourceUtils {

    public static String loadResource(String name) throws IOException {
        return IOUtils.toString(ClassLoader.getSystemResourceAsStream(name));
    }

    public static String loadResourceFromServer(String url) throws IOException {
        String content = null;
        InputStream in = null;
        try {
            in = new URL(url).openStream();
            content = IOUtils.toString(in);
        } finally {
            if (in != null) in.close();
        }
        System.out.println("loadResourceFromServer url:" + url);
       // System.out.println(content);
        return content;
    }

    /**
     * 先从github获取，如果失败从本地获取
     *
     * @param url
     * @param localName
     * @return
     * @throws IOException
     */
    public static String loadResource(String url, String localName) throws IOException {
        try {
            return loadResourceFromServer(url);
        } catch (IOException e) {
            System.err.println("loadResource error url:" + url + " localName:" + localName);
        }
        return loadResource(localName);
    }
}
