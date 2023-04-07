package main.Controller.SheJiModel.proxy;

/**
 * @author fengyunwei
 */
public class ProxyImage implements Image{

    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void display() {
        //增加功能
        if(realImage == null){
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
