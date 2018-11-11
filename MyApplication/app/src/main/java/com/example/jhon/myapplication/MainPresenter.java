package com.example.jhon.myapplication;

import android.util.Log;

import com.android.aspect.thread.Execute;
import com.android.aspect.thread.ThreadMode;
import com.android.mvp.BasePresenter;

public class MainPresenter extends BasePresenter<IMainView> {

    @Execute(threadMode=ThreadMode.WORK)
    public void loading(){
        Log.e("weichao","run thread == "+Thread.currentThread().getName()) ;
        showToast() ;
    }
    @Execute(threadMode = ThreadMode.MAIN)
    public void showToast(){
        Log.e("weichao","run thread == "+Thread.currentThread().getName()) ;
        getView().showToast();
    }

}
