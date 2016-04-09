package com.xabad.tuling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// +----------------------------------------------------------------------
// | CreateTime: 15/7/30 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.xueyong.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------
public class BaseJson {
    //STRING
    public static String getString(String resource, String key) {
        return getString(resource, key, null);
    }

    public static String getString(String resource, String key, String value) {
        if (StringU.isEmpty(resource)) return value;
        if (StringU.isEmpty(key)) return value;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resource);
            if (!jsonObject.has(key)) return value;
            return jsonObject.getString(key);
        } catch (JSONException e) {
            return value;
        }
    }

    public static JSONArray getJsonArray(String resource, String key) {
        JSONObject jsonObject = null;
        if (StringU.isEmpty(resource)) return null;
        if (StringU.isEmpty(key)) return null;

        try {
            jsonObject = new JSONObject(resource);
            if (!jsonObject.has(key)) return null;
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            return null;
        }
    }

    //LONG
    public static long getLong(String resource, String key) {
        return getLong(resource, key, 0);
    }

    public static long getLong(String resource, String key, long value) {
        if (StringU.isEmpty(resource)) return value;
        if (StringU.isEmpty(key)) return value;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resource);
            if (!jsonObject.has(key)) return value;
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            return value;
        }
    }

    //INT
    public static int getInt(String resource, String key) {
        return getInt(resource, key, 0);
    }

    public static int getInt(String resource, String key, int value) {
        if (StringU.isEmpty(resource)) return value;
        if (StringU.isEmpty(key)) return value;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resource);
            if (!jsonObject.has(key)) return value;
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            return value;
        }
    }

    //BOOLEAN
    public static boolean getBoolean(String resource, String key) {
        return getBoolean(resource, key, false);
    }

    public static boolean getBoolean(String resource, String key, boolean value) {
        if (StringU.isEmpty(resource)) return value;
        if (StringU.isEmpty(key)) return value;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resource);
            if (!jsonObject.has(key)) return value;
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            return value;
        }
    }

    //DOUBLE
    public static double getDouble(String resource, String key) {
        return getDouble(resource, key, 0D);
    }

    public static double getDouble(String resource, String key, double value) {
        if (StringU.isEmpty(resource)) return value;
        if (StringU.isEmpty(key)) return value;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resource);
            if (!jsonObject.has(key)) return value;
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            return value;
        }
    }
}
