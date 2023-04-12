package main.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import main.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengyunwei
 */
@RestController
@RequestMapping("/test")
public class DubboController {
    @Reference
    private DubboService dubboService;

    @GetMapping("/say")
    public Object getGoods() {
        return dubboService.sayDubbo("小穆同学");
    }

}
