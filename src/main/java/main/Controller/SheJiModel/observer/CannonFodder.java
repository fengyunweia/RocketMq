package main.Controller.SheJiModel.observer;

/**
 * 观察者实现类
 * @author fengyunwei
 */
public class CannonFodder extends Observer {
    private Subject cmder;
    private int id;
    public CannonFodder(Subject cmder,int id){
        this.cmder= cmder;
        this.id = id;
    }

    public void getDown(){
        System.out.println(this.getClass().getSimpleName() +" id:"
                +this.id + " getDowned" +"Down on target(id:"+cmder.getState()+")");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        this.getDown();
    }

}
