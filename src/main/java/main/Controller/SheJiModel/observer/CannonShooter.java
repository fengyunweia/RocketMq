package main.Controller.SheJiModel.observer;

/**
 * @author fengyunwei
 */
public class CannonShooter extends Observer{

    private Subject cmder;

    public CannonShooter(Subject cmder){
        this.cmder = cmder;
    }

    public Subject getCmder() {
        return cmder;
    }

    public void setCmder(Subject cmder) {
        this.cmder = cmder;
    }

    public void fireCannon(int targetPlace){
        System.out.println(this.getClass().getSimpleName() + ": fired on target(id:"
                + targetPlace + ") by Cannon");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        fireCannon(cmder.getState());
    }

}
