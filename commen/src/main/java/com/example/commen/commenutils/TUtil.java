package com.example.commen.commenutils;

import java.lang.reflect.ParameterizedType;

import static u.aly.au.T;

/**
 * 作者： WangZL on 2016/9/29 0029.
 */

public class TUtil {
    public static <T> T getT(Object o,int i){
        try {
            return ((Class<T>)((ParameterizedType)o.getClass().getGenericSuperclass()).
                    getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
