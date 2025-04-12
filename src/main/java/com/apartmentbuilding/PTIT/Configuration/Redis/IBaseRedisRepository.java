package com.apartmentbuilding.PTIT.Configuration.Redis;

import java.util.Map;
import java.util.Set;

public interface IBaseRedisRepository {
    void set(String key, Object value);

    Object get(String key);

    void delete(String key);

    void setTimeToLive(String key, long timeToLive);

    void hashSet(String key, String field, Object value);

    Object hashGet(String key, String field);

    Set<Object> getFieldPrefixes(String key, String prefix);

    Set<String> getFields(String key);

    boolean hashExists(String key, String field);

    Map<String, Object> getFieldValue(String key);

    void delete(String key, String field);
}
