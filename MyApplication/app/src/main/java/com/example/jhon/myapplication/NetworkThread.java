package com.example.jhon.myapplication;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NetworkThread extends Thread {

    static Map<Integer, String> mapAppName = new HashMap<>();
    static Map<Integer, String> mapAppUrl = new HashMap<>();
    static Map<Integer, String> mapAppSize = new HashMap<>();

    static Map<Integer, String> mapAppName_2 = new HashMap<>();
    static Map<Integer, String> mapAppUrl_2 = new HashMap<>();
    static Map<Integer, String> mapAppSize_2 = new HashMap<>();
    @Override
    public void run() {
        downRank("http://www.wandoujia.com/top/app");
        downRank_2("http://www.wandoujia.com/top/game");
    }

    public void downRank(String url){
        int i = 0;
        try {
            //   Glide.get().clearDiskCache();

            Document doc = Jsoup.connect(url).get();
            Elements eles = doc.select("img[width=68]");
            Elements sizes = doc.select("span[title*=MB]");

            for(Element size : sizes){
                mapAppSize.put(i, size.text());
                Log.e(TAG, mapAppSize.get(i));
                i++;
            }
            i = 0;
            for(Element ele : eles){
                mapAppName.put(i, ele.attr("alt"));

                String str = ele.attr("src");
                if(str.charAt(0) =='/'){
                    str = "http:" + str;
                }
                Log.e(TAG, "run: "+str );
                mapAppUrl.put(i, str);
                i++;
                //      Log.e(TAG, ele.attr("alt") + ":  " + ele.attr("src") );
                //   System.out.println(ele.attr("alt") + ":  " + ele.attr("src") );
            }
            Log.e(TAG, "run: finished..............." );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "A error occurred!" );
        }

    }

    public void downRank_2(String url){
        int i = 0;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements eles = doc.select("img[width=68]");
            Elements sizes = doc.select("span[title*=MB]");

            for(Element size : sizes){
                mapAppSize_2.put(i, size.text());
                Log.e(TAG, mapAppSize.get(i));
                i++;
            }
            i = 0;
            for(Element ele : eles){
                mapAppName_2.put(i, ele.attr("alt"));

                String str = ele.attr("src");
                if(str.charAt(0) =='/'){
                    str = "http:" + str;
                }
                Log.e(TAG, "run: "+str );
                mapAppUrl_2.put(i, str);
                i++;
                      Log.e(TAG, ele.attr("alt") + ":  " + ele.attr("src") );
            }
            Log.e(TAG, "run: finished..............." );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "A error occurred!" );
        }

    }
}
