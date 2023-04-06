package main.Controller.SheJiModel.observer;

import java.util.HashSet;
import java.util.Iterator;

/**
 * @author fengyunwei
 */
public class Commander implements Subject {
    private int targetPlaceID;
    private HashSet<Observer> gunnerSet = new HashSet<Observer>();


    @Override
    public void attach(Observer obs){
        this.gunnerSet.add(obs);
    }

    @Override
    public void detach(Observer obs) {
        this.gunnerSet.remove(obs);
    }

    @Override
    public void sNotify() {
        if (this.gunnerSet.isEmpty()){
            return;
        }

        Iterator itr = this.gunnerSet.iterator();
        while (itr.hasNext()){
            Observer obs = (Observer)itr.next();
            obs.update();
        }
    }

    @Override
    public int getState() {
        // TODO Auto-generated method stub
        return this.targetPlaceID;
    }

    @Override
    public void setState(int state) {
        // TODO Auto-generated method stub
        this.targetPlaceID = state;
    }

}
