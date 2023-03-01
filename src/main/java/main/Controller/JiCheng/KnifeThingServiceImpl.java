package main.Controller.JiCheng;

import org.springframework.stereotype.Service;

/**
 * @author fengyunwei
 */
@Service
public class KnifeThingServiceImpl extends BaseThingServiceImpl<Knife> implements KnifeThingService {

    @Override
    public String doSomething(Knife knife) {
        return "我是刀子: i am knife";
    }
}
