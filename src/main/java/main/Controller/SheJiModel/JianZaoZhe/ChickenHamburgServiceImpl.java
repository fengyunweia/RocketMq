package main.Controller.SheJiModel.JianZaoZhe;

/**
 * @author fengyunwei
 */
public class ChickenHamburgServiceImpl extends Builder{

    private Hamburg hamburg;
    public ChickenHamburgServiceImpl(String bread, String interlayer){
        hamburg = new Hamburg(bread,interlayer);
//        hamburg.setSauce("番茄酱");
//        hamburg.setLettuce("有生菜");
    }

    @Override
    public void setLettuce() {
        hamburg.setLettuce("有生菜");
    }

    @Override
    public void setSauce() {
        hamburg.setSauce("番茄酱");
    }

    @Override
    public Hamburg getHamburg() {
        return this.hamburg;
    }
}
