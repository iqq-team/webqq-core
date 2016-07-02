package iqq.im.util;

/**
 * Created by liaojiacan on 2016/6/24.
 */
public class URLUtils {
    public static String getOrigin(String url) {
        return url.substring(0, url.lastIndexOf("/"));
    }
}
