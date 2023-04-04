package main.Controller.SheJiModel.FactoryModel;

/**
 * @author fengyunwei
 * 小米车
 */
public class XiaoMiCar implements Car {
    @Override
    public void create() {
        System.out.println("创建小米车");
    }
}
