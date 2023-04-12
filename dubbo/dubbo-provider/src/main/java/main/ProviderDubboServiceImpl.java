package main;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author fengyunwei
 */
@Service
@Component
public class ProviderDubboServiceImpl implements DubboService {
    @Override
    public String sayDubbo(String who) {
        return who+"say Dubbo!!";
    }
}
