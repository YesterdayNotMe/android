package com.android.mvp;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T> {

    protected WeakReference<T> mViewRef;

    protected T getView() {
        if (mViewRef != null) {
            return mViewRef.get();
        }
        return null;
    }

    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
