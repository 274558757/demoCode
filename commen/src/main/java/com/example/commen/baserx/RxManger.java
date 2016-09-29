package com.example.commen.baserx;


import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * 用于管理单个presenter的RxBus的事件和Rxjava相关代码的生命周期的处理
 * 作者： WangZL on 2016/9/29 0029.
 */

public class RxManger {

    public RxBus mRxBus=RxBus.getInstance();
    //管理RxBus订阅
    private Map<String, Observable<?>>mObservables=new HashMap<>();
    /*管理Observables 和 Subscribers订阅*/
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    /**
     * RxBus注入监听
     * @param eventName
     * @param action1
     * @param <T>
     */
    public <T>void on(String eventName, Action1<T>action1){
        Observable<T> mObservable=mRxBus.register(eventName);
        mObservables.put(eventName,mObservable);
        /*订阅管理*/
        mCompositeSubscription.add(mObservable.subscribe(action1, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        }));

    }
//Schedulers
    /**
     * 单纯的observable和Subscribres
     * @param m
     */
    public void add(Subscription m){
        mCompositeSubscription.add(m);
    }

    /**
     * 单个presenter生命周期结束，取消订阅和取消所以rxbus观察
     */
    public void clear(){
        mCompositeSubscription.unsubscribe();
        for (Map.Entry<String,Observable<?>> entry:mObservables.entrySet()){
            mRxBus.unregister(entry.getKey(),entry.getValue());
        }

    }

    /**
     * 发送rxbus
     * @param tag
     * @param content
     */
    public void post(Object tag,Object content){
        mRxBus.post(tag,content);

    }

}
