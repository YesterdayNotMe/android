package com.android.aspect.thread;

public @interface Execute {
    int threadMode() ;
    String afterMethod() default "" ;
}
