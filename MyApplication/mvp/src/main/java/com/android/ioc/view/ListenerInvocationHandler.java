package com.android.ioc.view;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ListenerInvocationHandler implements InvocationHandler {

    Context context ;
    String callbackMethod;
    Method methf ;
    ListenerInvocationHandler(Context context,String callbackMethod,Method method){
        this.context = context ;
        this.callbackMethod = callbackMethod ;
        this.methf = method ;
    }
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if(method.getName().equals(callbackMethod)){
            return methf.invoke(context,objects) ;
        }
        return methf.invoke(context,objects);
    }
}
