package com.yd.service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by fred on 2017/12/5.
 */
public interface RedisService<T> {

	 /**
     * 字符串添加缓存
     */
    void set(String key, String value);

    /**
     * 字符串添加，设置过期时间，默认小时
     */
    void set(String key, String value, long expiredTime);

    /**
     * 字符串添加，设置过期时间，时间类型自定义，介意TimeUnit.
     */
    void set(String key, String value, long expiredTime, TimeUnit timeUnit);

    /**
     * 根据key，得到值
     */
    String get(String key);

    /**
     * 根据key，得到值得集合，要求存的时候就是集合json
     */
    List<T> getList(String key, Class clazz);

    /**
     * 根据key，得到pojo对象
     */
    T getObject(String key);

    /**
     * 根据key，删除缓存
     */
    void delete(String key);

    /**
     * 根据集合key，删除缓存
     */
    void delete(Collection<String> keys);
    
    /**
     * hash添加
     * @param key
     * @param hashKey
     * @param value
     */
    void  hSet(Object key, Object hashKey, Object value);

    /**
     * hash获取
     * @param key
     * @param hashKey
     */
    Object  hGet(Object key, Object hashKey);
    
    /**
     * 设置过期时间
     * @param key
     * @param expiredTime
     * @param timeUnit
     */
    void  expire(Object  key, long expiredTime, TimeUnit timeUnit);
}
