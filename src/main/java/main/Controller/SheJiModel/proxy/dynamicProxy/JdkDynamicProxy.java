package main.Controller.SheJiModel.proxy.dynamicProxy;

import org.junit.jupiter.api.extension.InvocationInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 * @author fengyunwei
 */
public class JdkDynamicProxy implements InvocationHandler {

    private Object bean;
    public JdkDynamicProxy(Object bean){
        this.bean = bean;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if(name.equals("wakeup")){
            System.out.println("早安~~~");
        }
        if(name.equals("sleep")){
            System.out.println("晚安~~~");
        }
        return method.invoke(bean,args);
    }
}
