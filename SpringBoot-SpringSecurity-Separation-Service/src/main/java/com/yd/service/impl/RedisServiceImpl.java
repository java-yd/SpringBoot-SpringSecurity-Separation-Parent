package com.yd.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yd.service.RedisService;

@Service
public class RedisServiceImpl<T> implements RedisService<T> {

    @Autowired
    private RedisTemplate stringRedisTemplate;

    /**
     * 字符串添加缓存
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 字符串添加，设置过期时间，默认小时
     */
    public void set(String key, String value, long expiredTime) {
        set(key, value, expiredTime, TimeUnit.HOURS);
    }

    /**
     * 字符串添加，设置过期时间，时间类型自定义，介意TimeUnit.
     */
    public void set(String key, String value, long expiredTime, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, expiredTime, timeUnit);
    }

    /**
     * 根据key，得到值
     */
    public String get(String key) {
        return (String) stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key，得到值得集合，要求存的时候就是集合json
     */
    public List<T> getList(String key, Class clazz) {
        String result = get(key);
        if(StringUtils.isNotEmpty(result)){
            return JSON.parseArray(result, clazz);
        }
        return null;
    }

    /**
     * 根据key，得到pojo对象
     */
    public T getObject(String key) {
        String result = get(key);
        if(StringUtils.isNotEmpty(result)){
            return (T) JSON.parseObject(result);
        }
        return null;
    }

    /**
     * 根据key，删除缓存
     */
    public void delete(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 根据集合key，删除缓存
     */
    public void delete(Collection<String> keys){
        stringRedisTemplate.delete(keys);
    }
    
    /**
     * hash添加
     * @param key
     * @param hashKey
     * @param value
     */
    public  void  hSet(Object key, Object hashKey, Object value){
    	stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }
    /**
     * hash获取
     * @param key
     * @param hashKey
     */
    public  Object  hGet(Object key, Object hashKey){
    	return  stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    
    public  void  expire(Object  key, long expiredTime, TimeUnit timeUnit){
    	stringRedisTemplate.expire(key, expiredTime, timeUnit);
    }
}
