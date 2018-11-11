package com.android.aspect.thread ;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.aspect.behavior.BehaviorTrace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Aspect
public class ThreadAspect {

    @Pointcut("execution(@com.android.aspect.thread.Execute * *(..))")
    public void annoExecute() {
    }

    @Around("annoExecute()")
    public void dealPoint(final ProceedingJoinPoint point) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Execute execute = methodSignature.getMethod().getAnnotation(Execute.class) ;
        int threadMode = execute.threadMode() ;
        String afterMethod = execute.afterMethod() ;
        if(threadMode == ThreadMode.MAIN){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    try {
                        Object result = point.proceed() ;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }) ;
        }else if(threadMode == ThreadMode.WORK){
//            Callable<> callable = new Callable() ;
//            FutureTask<> futureTask = new FutureTask(callable) ;
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Object result = point.proceed() ;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }.start();
        }
        //Object result = point.proceed() ;
    }
}
