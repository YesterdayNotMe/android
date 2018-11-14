package com.example.jhon.myapplication;

import java.util.ArrayList;
import java.util.List;

import static com.example.jhon.myapplication.NetworkThread.mapAppName_2;
import static com.example.jhon.myapplication.NetworkThread.mapAppSize_2;
import static com.example.jhon.myapplication.NetworkThread.mapAppUrl_2;

public class OfferModel {

    public List<AppHor> fetchOffers(){
        List<AppHor> appHors = new ArrayList<>() ;

        AppHor appHor1 = new AppHor("QQ","https://android-artworks.25pp.com/fs08/2018/11/01/1/110_b10623426c3c96c30de9ee546216625c_con_130x130.png", "52.78M","https://www.wandoujia.com/apps/com.tencent.mobileqq/download/dot?ch=detail_normal_dl");
        AppHor appHor2 = new AppHor("淘宝","https://android-artworks.25pp.com/fs08/2018/10/26/6/110_95c7ec4bc130188bb1129a35ea1b4844_con_130x130.png", "88.61M","https://www.wandoujia.com/apps/com.taobao.taobao/download/dot?ch=detail_normal_dl");
        appHors.add(appHor1);
        appHors.add(appHor2);
        //        for (int i = 0; i < 18; i++) {
////                Log.e(TAG, mapAppName.get(i) + mapAppUrl.get(i) + mapAppSize.get(i));
//            AppHor appHor = new AppHor("今日头条",, "50");
//            appHors.add(appHor);
//        }

        return appHors ;
    }
}
