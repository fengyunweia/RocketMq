package main.Controller.SheJiModel.JianZaoZhe;

/**
 * @author fengyunwei
 */
public class BeefHamburgServiceImpl extends Builder{
    private Hamburg hamburg;
    public BeefHamburgServiceImpl(String bread, String interlayer){
        hamburg = new Hamburg(bread,interlayer);
    }


    @Override
    public void setLettuce() {
        hamburg.setLettuce("无生菜");
    }

    @Override
    public void setSauce() {
        hamburg.setSauce("沙茶酱");
    }

    @Override
    public Hamburg getHamburg() {
        return this.hamburg;
    }
}
