package main.Controller.SheJiModel.proxy.dynamicProxy;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author fengyunwei
 */
public class CglibDynamicProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();
    private Object bean;
    public CglibDynamicProxy(Object bean){
        this.bean = bean;
    }

    public Object getProxy(){
        //设置需要创建子类的类
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(this);
        //通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String name = method.getName();
        if(name.equals("wakeup")){
            System.out.println("早安~~~");
        }
        if(name.equals("sleep")){
            System.out.println("晚安~~~");
        }
        return method.invoke(bean,objects);
    }
}
