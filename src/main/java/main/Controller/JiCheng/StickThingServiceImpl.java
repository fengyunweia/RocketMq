package main.Controller.JiCheng;

import org.springframework.stereotype.Service;

/**
 * @author fengyunwei
 */
@Service
public class StickThingServiceImpl extends BaseThingServiceImpl<Stick> implements StickThingService {

    @Override
    public String doSomething(Stick stick) {
        return "我是大棒:我想锤你";
    }
}
