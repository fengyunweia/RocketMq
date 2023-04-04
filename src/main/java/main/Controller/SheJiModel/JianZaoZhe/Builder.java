package main.Controller.SheJiModel.JianZaoZhe;


import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 做一个建造者（即抽象基类 其会包含一个用来返回最终产品的方法Product getProduct()。）
 * @author fengyunwei
 */
public abstract class Builder {

    /**
     * 放入酱汁
     */
    public abstract void setLettuce();

    /**
     * 放入生菜
     */
    public abstract void setSauce();

    /**
     * 做一个汉堡
     * @return 汉堡
     */
    public abstract Hamburg getHamburg();
}
