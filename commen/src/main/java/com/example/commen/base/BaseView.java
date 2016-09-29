package com.example.commen.base;

/**
 * 作者： WangZL on 2016/9/29 0029.
 */

public interface BaseView {
    /**内嵌加载*/
    void showLoading();
    void stopLoading();
    void showErrorTip(String msg);
}
