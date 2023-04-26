package com.xqxls.mall.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 胡卓
 * @create 2023-04-26 16:54
 * @Description
 */
@Slf4j
public class ObjectUtil {

    /**
     * Object对象转 List集合
     *
     * @param object Object对象
     * @param clazz 需要转换的集合
     * @param <T> 泛型类
     * @return
     */
    public static <T> List<T> objectToList(Object object, Class<T> clazz){
        try {
            List<T> result = new ArrayList<>();
            if (object instanceof List<?>){
                for (Object o : (List<?>) object) {
                    String string = JSONObject.toJSONString(o);
                    T t = JSONObject.parseObject(string, clazz);
                    result.add(t);
                }
                return result;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;
    }
}
