package com.android.aspect.behavior;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class BehaviorAspect {

    @Pointcut("execution(@com.android.aspect.behavior.BehaviorTrace * *(..))")
    public void annoBehavior() {
    }


    @Around("annoBehavior()")
    public Object dealPoint(ProceedingJoinPoint point) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        BehaviorTrace behaviorTrace = methodSignature.getMethod().getAnnotation(BehaviorTrace.class) ;
        int type = behaviorTrace.type() ;
        String id = behaviorTrace.id() ;
        Log.e("weichao","type="+type+" id="+id) ;
        Object result = point.proceed() ;
        return  result ;
    }

}
