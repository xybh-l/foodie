package com.xybh.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 16:20 2021/2/1
 * @Modified:
 */
@Component
public class RedisOperator {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 键 操作

    /**
     * 实现 TTL key, 以秒为单位,返回过期时间
     *
     * @param key 键
     * @return 过期时间
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 设置过期时间,单位: 秒
     *
     * @param key     键
     * @param timeout 过期时间
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置过期时间
     *
     * @param key      键
     * @param timeout  过期时间
     * @param timeUnit 时间单元
     */
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 增加key1次
     *
     * @param key 键
     */
    public void incr(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    /**
     * 增加<ref>num</ref>次
     *
     * @param key 键
     * @param num 值
     */
    public void incyByNum(String key, long num) {
        redisTemplate.opsForValue().increment(key, num);
    }

    /**
     * 减少key1次
     *
     * @param key 键
     */
    public void decr(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

    public void decrByNum(String key, long num) {
        redisTemplate.opsForValue().decrement(key, num);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    // string 操作

    /**
     * 设置值
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置值和过期时间
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void setAndExpire(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取一段范围内的字符
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 设置不存在的键
     *
     * @param key
     * @param value
     */
    public void setn(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 批量查询, 对应mget
     *
     * @param keys
     * @return
     */
    public List<String> mget(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }


    // hash 操作

    public void hset(String key, String filed, String value) {
        redisTemplate.opsForHash().put(key, filed, value);
    }

    public String hget(String key, String filed) {
        return (String) redisTemplate.opsForHash().get(key, filed);
    }

    public void hdel(String key, Object... filed) {
        redisTemplate.opsForHash().delete(key, filed);
    }

    public Map<Object, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // list 操作

    public void lpush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public void lpush(String key, String... value) {
        redisTemplate.opsForList().leftPushAll(key, value);
    }

    public void rpush(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public void rpush(String key, String... value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    public String lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public String rpop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }
}
