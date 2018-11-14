package com.example.jhon.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.ioc.view.ContentView;
import com.android.ioc.view.ViewInject;
import com.android.mvp.BaseActivity;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    @ViewInject(R.id.rv_task)
    private RecyclerView mRvTask;

    private AppHorAdapter adapter;

    private List<AppHor> mAppHorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvTask.setLayoutManager(layoutManager);
        mAppHorList = new ArrayList<>();
        adapter = new AppHorAdapter(mAppHorList);
        mRvTask.setAdapter(adapter);
        mPresenter.fetchOffers();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void showToast() {
        Toast.makeText(this, "hello world", Toast.LENGTH_LONG).show();
    }

    @Override
    public void refresh(List<AppHor> appHorList) {
        if (appHorList == null || appHorList.size() == 0) {
            return;
        }
        mAppHorList.clear();
        mAppHorList.addAll(appHorList);
        adapter.notifyDataSetChanged();
    }

}
