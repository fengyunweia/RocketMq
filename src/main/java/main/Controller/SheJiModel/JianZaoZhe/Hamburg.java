package main.Controller.SheJiModel.JianZaoZhe;

import lombok.Data;
import lombok.ToString;

/**
 * @author fengyunwei
 * 要做的产品
 */
@Data
public class Hamburg {
    /**
     * 汉堡面饼 必须有的
     */
    private String bread;

    /**
     * 汉堡夹层 必须有的
     */
    private String interlayer;

    /**
     * 汉堡酱汁 非必须
     */
    private String sauce;

    /**
     * 生菜叶 非必须
     */
    private String lettuce;

    /**
     * 下面是三个构造函数(常用的折叠构造函数) 分别是两个必填参数 还有两个非必填参数
     * 第一种主要是使用及阅读不方便。你可以想象一下，当你要调用一个类的构造函数时，你首先要决定使用哪一个，然后里面又是一堆参数
     * ，如果这些参数的类型很多又都一样，你还要搞清楚这些参数的含义，很容易就传混了。。。那酸爽谁用谁知道。
     *
     * 这里就有一个问题，参数过多的 用户使用要搞懂每一个参数的塞入位置，因此需要建造者Builder
     */
    Hamburg(String bread,String interlayer){
        this.bread = bread;
        this.interlayer = interlayer;
    }

    Hamburg(String bread,String interlayer,String sauce){
        this.bread = bread;
        this.interlayer = interlayer;
        this.sauce = sauce;
    }
    Hamburg(String bread,String interlayer,String sauce,String lettuce){
        this.bread = bread;
        this.interlayer = interlayer;
        this.sauce = sauce;
        this.lettuce = lettuce;
    }

}
