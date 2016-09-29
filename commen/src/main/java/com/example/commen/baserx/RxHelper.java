package com.example.commen.baserx;

/**
 * 对服务器返回数据成功和失败处理
 * 作者： WangZL on 2016/9/29 0029.
 */


import com.example.commen.basebean.BaseRespose;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static u.aly.au.T;
import static u.aly.au.e;

/**************使用例子******************/
/*_apiService.login(mobile, verifyCode)
        .compose(RxSchedulersHelper.io_main())
        .compose(RxResultHelper.handleResult())
        .//省略*/


public class RxHelper {
    /**
     * 对服务器返回数据进行预处理
     * @param <T>
     * @return
     */
    public static <T>Observable.Transformer<BaseRespose<T>,T>handleResule(){
        return new Observable.Transformer<BaseRespose<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseRespose<T>> mObservable) {
                return mObservable.flatMap(new Func1<BaseRespose<T>, Observable<T>>() {

                    @Override
                    public Observable<T> call(BaseRespose<T> result) {
                        if (result.success()){
                            return createData(result.data);
                        }else {
                            return Observable.error(new ServerException(result.msg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 创建成功的数据
     * @param data
     * @param <T>
     * @return
     */
    private static <T>Observable<T>createData(final T data){
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        });
    }


}
