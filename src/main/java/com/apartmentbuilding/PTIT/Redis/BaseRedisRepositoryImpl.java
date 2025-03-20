package com.apartmentbuilding.PTIT.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class BaseRedisRepositoryImpl implements IBaseRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setTimeToLive(String key, long timeToLive) {
        redisTemplate.expire(key, Duration.ofSeconds(timeToLive));
    }

    @Override
    public void hashSet(String key, String field, Object value) {
        hashOperations.put(key, field, value);
    }

    @Override
    public Object hashGet(String key, String field) {
        return hashOperations.get(key, field);
    }

    @Override
    public Set<Object> getFieldPrefixes(String key, String prefix) {
        Set<Object> result = new HashSet<>();
        for (String field : hashOperations.keys(key)) {
            if (field.startsWith(prefix)) {
                result.add(hashOperations.get(key, field));
            }
        }
        return result;
    }

    @Override
    public Set<String> getFields(String key) {
        return hashOperations.keys(key);
    }

    @Override
    public boolean hashExists(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    @Override
    public Map<String, Object> getFieldValue(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public void delete(String key, String field) {
        hashOperations.delete(key, field);
    }
}
