package com.example.commen.base;

import android.content.Context;

import com.example.commen.baserx.RxManger;

/**
 * 作者： WangZL on 2016/9/29 0029.
 */

public abstract class BasePresenter<T,E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManger mRxManger=new RxManger();

    public void setVM(T v,E m){
        this.mModel=m;
        this.mView=v;
        this.onStart();
    }
    public void onStart(){}
    public void onDestroy(){
        mRxManger.clear();
    }

}
