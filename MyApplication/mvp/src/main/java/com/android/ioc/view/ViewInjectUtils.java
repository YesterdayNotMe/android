package com.android.ioc.view;

import android.content.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ViewInjectUtils {

    public static void inject(Context context){
        injectLayout(context);
        injectView(context);
        injectEvent(context);
    }

    private static void injectEvent(Context context){
        Class<?> clazz = context.getClass() ;

        Method[] methods = clazz.getDeclaredMethods() ;
        for(Method method:methods){
            Annotation[] annotations = method.getDeclaredAnnotations() ;
            for (Annotation annotation:annotations){
                Class<?> annotationType = annotation.annotationType() ;
                EventBase eventBase = annotationType.getAnnotation(EventBase.class) ;
                if(eventBase!=null){
                    String listenerSetter = eventBase.listenerSetter() ;
                    Class<?> listenerType = eventBase.listenerType() ;
                    String callbackMethod = eventBase.callbackMethod() ;
                    try {
                        Method valueMethod = annotationType.getDeclaredMethod("value") ;
                        int[] ids = (int[]) valueMethod.invoke(annotation);
                        for(int id:ids){
                            Method findViewById = clazz.getMethod("findViewById",int.class) ;
                            Object view = findViewById.invoke(context,id) ;
                            Method listenerSetterMethod = view.getClass().getMethod(listenerSetter,listenerType) ;
                            Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),new Class[]{listenerType},new ListenerInvocationHandler(context,callbackMethod,method)) ;
                            listenerSetterMethod.invoke(view,proxy) ;
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectView(Context context)  {
        Class<?> clazz = context.getClass() ;
        Field[] fields = clazz.getDeclaredFields() ;
        if(fields == null||fields.length == 0){
            return;
        }
        try {
            Method method = clazz.getMethod("findViewById",int.class) ;
            for(Field field:fields){
                ViewInject viewInject = field.getAnnotation(ViewInject.class) ;
                if(viewInject!=null) {
                    int viewId = viewInject.value();
                    try {
                        field.setAccessible(true);
                        field.set(context,method.invoke(context,viewId));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void injectLayout(Context context){
        Class<?> clazz = context.getClass() ;
        ContentView layout = clazz.getAnnotation(ContentView.class);
        if(layout!=null){
            int layoutId = layout.value() ;
            try {
                Method method = clazz.getMethod("setContentView",int.class) ;
                method.invoke(context,layoutId) ;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
