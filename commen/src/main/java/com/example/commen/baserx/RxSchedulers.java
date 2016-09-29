package com.example.commen.baserx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static u.aly.au.T;

/**
 * RxJava调度管理
 * 作者： WangZL on 2016/9/29 0029.
 */

public class RxSchedulers {

    public static <T>Observable.Transformer<T,T>io_main(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
