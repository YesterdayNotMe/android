package com.example.jhon.myapplication;

import android.util.Log;

import com.android.aspect.thread.Execute;
import com.android.aspect.thread.ThreadMode;
import com.android.mvp.BasePresenter;

public class MainPresenter extends BasePresenter<IMainView> {

    public void fetchOffers() {
        if (getView() != null) {
            OfferModel model = new OfferModel();
            getView().refresh(model.fetchOffers());
        }
    }

}
