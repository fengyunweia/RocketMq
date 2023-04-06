package main.Controller.SheJiModel.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者 主要包含了有绑定观察者到 Client 对象和从 Client 对象解绑观察者的方法。以及被观察（触发）方法
 * @author fengyunwei
 */
public interface Subject {
    public void attach(Observer obs);
    public void detach(Observer obs);
    public void sNotify();   //notify is a finel method of Object class
    public int  getState();
    public void setState(int state);

}
