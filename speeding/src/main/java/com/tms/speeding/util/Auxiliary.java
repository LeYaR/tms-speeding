package com.tms.speeding.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Auxiliary {

    public static final String SV_FAILED = "Failed to save the data";
    public static final String SV_OVERWRITTEN = "An existing object has been overwritten";
    public static final String SV_INVALID = "The object you attempting to save is invalid";
    public static final String SV_EXISTS = "An existing object with the same data has already been written";
    public static final String RD_FAILED = "Failed to obtain the data";

    private Auxiliary() {}

    public static boolean isEmpty(String property) {
        return property == null || property.isEmpty() || property.isBlank();
    }

    public static <T>ResponseObject prepareListResponse (long count, List<T> list) {
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("list", list);
        var response = new ResponseObject();
        response.setData(result);
        return response;
    }
}
