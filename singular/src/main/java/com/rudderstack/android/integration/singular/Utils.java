package com.rudderstack.android.integration.singular;

import com.google.gson.Gson;
import com.rudderstack.android.sdk.core.RudderLogger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

public class Utils {
    static String getString(Object object) {
        if (object == null) {
            return null;
        }
        switch (getType(object)) {
            case "Array":
            case "Collection":
            case "Map":
                return new Gson().toJson(object);
            default:
                return object.toString();
        }
    }

    static String getType(Object object) {
        if (object.getClass().isArray()) {
            return "Array";
        }
        if (object instanceof Collection) {
            return "Collection";
        }
        if (object instanceof Map) {
            return "Map";
        }
        return object.getClass().getSimpleName();
    }

    static double getDouble(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
                RudderLogger.logDebug("Unable to convert the value: " + value +
                        " to Double, using the defaultValue: " + (double) 0);
            }
        }
        return 0;
    }

    static boolean isEmpty(Object value) {
        if(value == null){
            return true;
        }
        if (value instanceof String) {
            return (((String) value).trim().isEmpty());
        }
        if (value instanceof JSONArray) {
            return (((JSONArray) value).length() == 0);
        }
        if (value instanceof JSONObject) {
            return (((JSONObject) value).length() == 0);
        }
        if (value instanceof Map) {
            return ((Map<?, ?>) value).size() == 0;
        }
        if (value instanceof Collection) {
            return ((Collection<?>) value).size() == 0;
        }
        return false;
    }
}