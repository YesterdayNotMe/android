package com.example.jhon.myapplication;

import android.view.View;
import android.widget.Toast;

import com.android.ioc.view.ContentView;
import com.android.ioc.view.OnLongClick;
import com.android.mvp.BaseActivity;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void showToast() {
        Toast.makeText(this, "hello world", Toast.LENGTH_LONG).show();
    }


    @OnLongClick(R.id.btn_loading)
    public boolean onClick(View view) {
        mPresenter.loading();
        return true;
    }
}
