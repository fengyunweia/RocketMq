package main.Controller.SheJiModel.FactoryModel;

/**
 * @author fengyunwei
 * 蔚来车
 */
public class WeiLaiCar implements Car {
    @Override
    public void create() {
        System.out.println("蔚来车");
    }
}
