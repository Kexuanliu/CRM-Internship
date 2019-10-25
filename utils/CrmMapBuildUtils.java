package com.xuebei.crm.utils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

/**
 * 构建Map的工具类.
 *
 * @author Rong Weicheng
 */
public final class CrmMapBuildUtils {

    private CrmMapBuildUtils() {
        // hide the constructor
    }

    /**
     * 初始化一个值为空List的EnumMap.
     *
     * @param enumClass 指定的enum类
     * @return 值为空List的EnumMap
     */
    public static <E extends Enum<E>, T> EnumMap<E, List<T>> initEnumListMap(Class<E> enumClass) {
        EnumMap<E, List<T>> enumMap = new EnumMap<>(enumClass);
        for (E enumValue : enumClass.getEnumConstants()) {
            enumMap.put(enumValue, new ArrayList<>());
        }
        return enumMap;
    }

    /**
     * 构造一个按枚举类型分类的EnumMap.
     * 如果待分类中没有特定枚举类型的元素，则返回的map中该enum key 对应的值为空list
     * @param enumClass 指定的enum类
     * @param list      待分类的list
     * @param extractor list中的元素的指定enum field的getter
     * @return 按枚举类型分类的EnumMap
     */
    public static <E extends Enum<E>, T> EnumMap<E, List<T>> buildEnumListMap(Class<E> enumClass,
                                                                              List<T> list,
                                                                              Function<T, E> extractor) {
        EnumMap<E, List<T>> enumMap = initEnumListMap(enumClass);
        for (T t : list) {
            enumMap.get(extractor.apply(t)).add(t);
        }
        return enumMap;
    }
}
