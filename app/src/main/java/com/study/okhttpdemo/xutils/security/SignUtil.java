package com.study.okhttpdemo.xutils.security;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by zhangzhenguo on 2017/3/31.
 * 网络请求签名工具
 */

public class SignUtil {

    /**
     * 实体类属性按照ASCII方式排序的签名原文
     * @param object 实体类
     * @param ignores 忽略的请求参数
     * @return 按照ASCII方式拼接的签名源数据
     */
    public String getASCIISignOrigin(Object object, String... ignores) {
        Set<String> ignoreSets = new HashSet<String>();
        for (String ignore : ignores) {
            ignoreSets.add(ignore);
        }
        return this.getASCIISignOrigin(object, ignoreSets);
    }

    /**
     * 实体类属性按照ASCII方式排序的签名原文
     * @param object 实体类
     * @param ignores 忽略的请求参数
     * @return 按照ASCII方式拼接的签名源数据
     */
    protected String getASCIISignOrigin(Object object, Set<String> ignores) {
        Class<?> clazz = object.getClass();
        List<Field> fields = getFields(clazz, null);

        List<ASCII> asciiList = new ArrayList<ASCII>();

        for (Field field : fields) {
            String name = field.getName();
            int modify = field.getModifiers();
            if (Modifier.isPublic(modify) && Modifier.isStatic(modify) && Modifier.isFinal(modify)) {
                // public static fianl
                continue;
            }
            if (name.contains("$")){
                continue;
            }
            if (ignores.contains(name)) {
                // 需要忽略的参数
                continue;
            }
            try {
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                String value = (String) clazz.getMethod(methodName).invoke(object);
                if (TextUtils.isEmpty(value)) {
                    // 为空的数据不参与签名
                    continue;
                }
                ASCII ascii = new ASCII();
                ascii.name = name;
                ascii.value = value;
                asciiList.add(ascii);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 没有参数
        if (asciiList.size() == 0) {
            return "";
        }

        // ASCII排序
        Collections.sort(asciiList, new Comparator<ASCII>() {

            @Override
            public int compare(ASCII o1, ASCII o2) {
                String name1 = o1.name;
                String name2 = o2.name;
                int len1 = name1.length();
                int len2 = name2.length();
                int len = len1 > len2 ? len2 : len1;
                for (int i = 0; i < len; i++) {
                    int differ = name1.charAt(i) - name2.charAt(i);
                    if (differ == 0) {
                        continue;
                    }
                    return differ;
                }
                return len1 - len2;
            }
        });

        StringBuilder buffer = new StringBuilder();
        // 拼接参数
        for (int i = 0; i < asciiList.size(); i++) {
            ASCII ascii = asciiList.get(i);
            buffer.append("&").append(ascii.name).append("=").append(ascii.value);
        }
        String origin = buffer.toString().substring(1);
        Log.d("SignUtil", "sign origin={}"+origin);
        return origin;
    }

    /**
     * 获取类的所有属性及所有父属性
     * @param clazz 类
     * @param names 已经存在的属性
     * @return 类的属性及所有父属性
     */
    public static List<Field> getFields(Class<?> clazz, Set<String> names) {
        List<Field> fieldList = new ArrayList<Field>();
        if (clazz == null) {
            return fieldList;
        }
        if (names == null) {
            names = new HashSet<String>();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (names.contains(field.getName())) {
                continue;
            }
            fieldList.add(field);
            names.add(field.getName());
        }
        fieldList.addAll(getFields(clazz.getSuperclass(), names));
        return fieldList;
    }

    // ASCII
    private class ASCII {
        private String name;
        private String value;
    }
}
