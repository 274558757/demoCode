package com.example.commen.baserx;

import android.content.Context;

import com.example.commen.R;
import com.example.commen.baseapp.BaseApplication;
import com.example.commen.baseprogress.NetworkProgressDialog;

import java.util.TreeMap;

import rx.Subscriber;
import rx.schedulers.NewThreadScheduler;

import static u.aly.au.T;

/**
 * 作者： WangZL on 2016/9/29 0029.
 */

public abstract class  RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private NetworkProgressDialog dialog;

    public RxSubscriber(Context context, String msg,boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading),true);
    }
    public RxSubscriber(Context context,boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading),showDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog){
            if (dialog==null){
                dialog=new NetworkProgressDialog(mContext);
            }
            dialog.show();
        }
    }

    @Override
    public void onCompleted() {
        if (showDialog&&dialog!=null){
            dialog.dismiss();
        }
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog&&dialog!=null){
            dialog.dismiss();
        }
        e.printStackTrace();
        if (e instanceof ServerException){
            _onError(e.getMessage());
        }else {
            _onError("网络连接失败");
        }
    }

    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);
}
