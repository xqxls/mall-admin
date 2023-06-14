package com.xqxls.mall.common.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作Service
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public interface RedisService {

    /**
     * 保存属性
     * @param key 键
     * @param value 值
     * @param time 过期时间
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     * @param key 键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 获取属性
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 删除属性
     * @param key 键
     * @return 是否删除成功
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     * @param keys 键的集合
     * @return 删除成功条数
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     * @param key 键
     * @param time 过期时间
     * @return 是否设置成功
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     * @param key 键
     * @return 过期时间
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     * @param key 键
     * @return 是否有该属性
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     * @param key 键
     * @param delta 增量
     * @return 这个键按delta递增后的值
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     * @param key 键
     * @param delta 增量
     * @return 这个键按delta递减后的值
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     * @param key 键
     * @param hashKey 哈希结构的属性
     * @return 哈希结构的属性对应的值
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     * @param key 键
     * @param hashKey 哈希结构的属性
     * @param value 哈希结构的属性值
     * @param time 过期时间
     * @return 是否设置成功
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     * @param key 键
     * @param hashKey 哈希结构的属性
     * @param value 哈希结构的属性值
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     * @param key 键
     * @return 整个哈希结构
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     * @param key 键
     * @param map 整个哈希结构
     * @param time 过期时间
     * @return 是否设置成功
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     * @param key 键
     * @param map 整个哈希结构
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * 删除Hash结构中的属性
     * @param key 键
     * @param hashKey 哈希结构的属性列表
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     * @param key 键
     * @param hashKey 哈希结构的属性
     * @return 是否有该属性
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     * @param key 键
     * @param hashKey 哈希结构的属性
     * @param delta 增量
     * @return 哈希结构的属性递增之后的值
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     * @param key 键
     * @param hashKey 哈希结构的属性
     * @param delta 增量
     * @return 哈希结构的属性递减之后的值
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     * @param key 键
     * @return 整个Set结构
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     * @param key 键
     * @param values Set结构的属性列表
     * @return 成功添加到Set结构的属性个数
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     * @param key 键
     * @param time 过期时间
     * @param values Set结构的属性列表
     * @return 成功添加到Set结构的属性个数
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     * @param key 键
     * @param value Set结构的属性
     * @return 是否为Set中的属性
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     * @param key 键
     * @return Set结构的长度
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     * @param key 键
     * @param values Set结构的属性列表
     * @return 成功删除的Set结构的属性个数
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     * @param key 键
     * @param start 开始索引
     * @param end 结束索引
     * @return 获取List结构中从start到end的属性
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     * @param key 键
     * @return 获取List结构的长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     * @param key 键
     * @param index 索引
     * @return 获取List结构中index索引对应的值
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     * @param key 键
     * @param value 属性值
     * @return 成功添加到List结构中属性个数
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     * @param key 键
     * @param value 属性值
     * @param time 过期时间
     * @return 成功添加到List结构中属性个数
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     * @param key 键
     * @param values 属性列表
     * @return 成功添加到List结构中属性个数
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     * @param key 键
     * @param values 属性列表
     * @param time 过期时间
     * @return 成功添加到List结构中属性个数
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     * count > 0: Remove elements equal to element moving from head to tail.
     * count < 0: Remove elements equal to element moving from tail to head.
     * count = 0: Remove all elements equal to element.
     * @param key 键
     * @param count 移除属性的个数和方向
     * @param value 属性
     * @return 成功移除的List结构中属性个数
     */
    Long lRemove(String key, long count, Object value);
}