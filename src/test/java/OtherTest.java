import main.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class OtherTest {

    /**
     * 死锁问题及解决方案
     * 首先我们先来看为什么会造成死锁，归根到底是进程竞争一个资源不当的问题
     * 比如小冯和小穆两个人 他们去了一家阴阳师手办店 手办店有两个镇店之宝，一个是八岐大蛇，一个是陇夜叉姬都很好看店内现在都只有一个
     * 小冯和小穆两个人都想同时玩两个，两人去了瞬间开抢，小冯抢到了陇夜叉姬，小穆抢到了八岐大蛇，
     * 但是小冯也想要八岐大蛇 小穆也想要陇夜叉姬，
     * 这是店员来了，店员说，你们谁先玩一份，等我一会再去取出来两个给另一个人
     * 小穆说，我是不会把八岐大蛇给你的，你先给我，等下一份，
     * 小冯说，不可能，我也是不可能给你的，你把你的给我，你等下一份
     * 两个人争得不可开交，谁都不愿意放手，死锁了
     *
     * 看上面的情形，造成死锁的原因有4个：死锁必须基于基于多个线程抢夺多个资源
     * 1：互斥条件 （当一个手办被一个人持有的时候，另一个人就不能在持有，只能等待）
     * 2：请求和保持条件 （小冯持有陇夜叉姬，但是他还想要八岐大蛇，但是八岐大蛇被小穆抢到了，自己玩不到，又不愿意放弃自己拥有的陇夜叉姬）
     * 3：不剥夺条件 （小穆自己不愿意把手里的八岐大蛇放回去，谁也抢不走）
     * 4：环路等待条件（小穆和小冯都在等待着对方放弃自己手里的手办）
     *
     * 避免方案：（银行家算法）死锁避免的基本思想：系统对进程发出的每一个系统能够满足的资源申请进行动态检查，并根据检查结果决定是否分配资源，
     *         如果分配后系统可能发生死锁，则不予分配，否则予以分配，这是一种保证系统不进入死锁状态的动态策略
     *
     * 解决方案：1和3 不可破坏 只能破坏2 4
     * 4：破坏环路等待：要求进程获取资源顺序相同（店长看到这个情景，大步走来，你们两个小兔崽子，都给我放下，
     *                现在我定一个条件，只有玩过八岐大蛇的人才能接着玩陇夜叉姬）
     * 2：破坏请求与保持：获取资源设置时限（小冯等了一会，发现是在是抢不到，就不请求了，并把自己的资源释放）
     */
}