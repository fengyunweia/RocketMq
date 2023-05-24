import main.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class GitTest {
    @Test
    public void test(){
        Map<Integer, Integer > map = new HashMap();
        map.put(1,1);
        map.put(2,2);
        map.put(3,3);

        for (Map.Entry<Integer,Integer> entry:map.entrySet()){
            if(entry.getKey() == 2){
                return;
            }
            System.out.println(entry.getKey());
        }
    }
}
