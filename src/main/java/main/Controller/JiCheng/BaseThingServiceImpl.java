package main.Controller.JiCheng;

import org.springframework.stereotype.Service;

/**
 * @author fengyuwnei
 *
 * 这里用抽象类是因为 抽象类跟正常类基本没区别，可以有自己的实现，也可以扩展
 */
@Service
public abstract class BaseThingServiceImpl<T> implements BaseThingService<T> {
    @Override
    public String doSomething(T t) {
        return t.getClass().toString()+"万能的工具包";
    }
}
