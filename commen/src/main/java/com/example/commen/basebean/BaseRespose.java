package com.example.commen.basebean;

import java.io.Serializable;

/**
 * 作者： WangZL on 2016/9/29 0029.
 */

public class BaseRespose <T> implements Serializable{
    public String code;
    public String msg;
    public T data;
    public boolean success(){
        return "000".equals(code);
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
