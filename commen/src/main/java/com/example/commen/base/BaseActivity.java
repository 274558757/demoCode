package com.example.commen.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.commen.baserx.RxManger;
import com.example.commen.commenutils.TUtil;

import butterknife.ButterKnife;

/**
 * 作者： WangZL on 2016/9/29 0029.
 */

public abstract class BaseActivity<T extends BasePresenter,E extends BaseModel> extends AppCompatActivity {
    public T mPersenter;
    public E mModel;
    public Context mContext;
    public RxManger mRxMager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxMager=new RxManger();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext=this;
        mPersenter = TUtil.getT(this,0);
        mModel = TUtil.getT(this,1);
        if (mPersenter!=null){
            mPersenter.mContext=this;
        }
        this.initPersenter();
        this.initView();


    }

    /*********************子类实现*****************************/
    //获取布局文件
    protected abstract int getLayoutId();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    protected abstract void initPersenter();
    //初始化view
    protected abstract void initView();
}
