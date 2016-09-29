package com.example.commen.baserx;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static u.aly.au.L;
import static u.aly.au.T;
import static u.aly.au.t;

/**
 * 作者： WangZL on 2016/9/29 0029.
 */

public class RxBus {
    private static RxBus instance;
    public static synchronized RxBus getInstance(){
        if (instance==null){
            instance = new RxBus();
        }
        return  instance;

    }

    private RxBus(){}

    private ConcurrentHashMap<Object,List<Subject>> subjectMapper =new ConcurrentHashMap<>();

    /**
     * 订阅事件源
     * @param mObservable
     * @param mAction1
     * @return
     */
    public RxBus onEvent(Observable<?>mObservable, Action1<Object> mAction1){
        mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(mAction1, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        return getInstance();

    }

    /**
     * 注册事件源
     * @param tag
     * @param <T>
     * @return
     */
    public <T> Observable<T>register(@NonNull Object tag){
        List<Subject>subjectList = subjectMapper.get(tag);
        if (null == subjectList){
            subjectList = new ArrayList<>();
            subjectMapper.put(tag,subjectList);
        }
        Subject<T,T>subject;
        subjectList.add(subject = PublishSubject.<T>create());

        return subject;

    }

    public void unregister(@NonNull Object tag){
        List<Subject>subjectList=subjectMapper.get(tag);
        if (subjectList!=null){
            subjectList.remove(tag);
        }
    }

    /**
     * 取消监听
     * @param tag
     * @param observable
     * @return
     */
    public RxBus unregister(@NonNull Object tag,@NonNull Observable<?>observable){
        if (null == observable){
            return getInstance();
        }
        List<Subject>subjectList = subjectMapper.get(tag);
        if (null != subjectList){
            subjectList.remove(observable);
            if (isEmpty(subjectList)){
                subjectMapper.remove(tag);
            }
        }
        return getInstance();
    }

    /**
     * 触发事件
     * @param content
     */
    public void post(@NonNull Object content){
        post(content.getClass().getName(),content);
    }

    /**
     * 触发事件
     * @param tag
     * @param content
     */
    public void post(@NonNull Object tag,@NonNull Object content){
        List<Subject> subjectList = subjectMapper.get(tag);
        if (!isEmpty(subjectList)){
            for (Subject subject : subjectList){
                subject.onNext(content);

            }
        }
    }

    public static boolean isEmpty(Collection<Subject>collection){
        return null == collection || collection.isEmpty();
    }
}
