package main;

/**
 * @author fengyunwei
 */
public interface DubboService {
    /**
     * 接口方法
     * @param who 谁say dubbo
     * @return
     */
    public String sayDubbo(String who);
}
