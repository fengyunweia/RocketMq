import main.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * mybatis 的一二级缓存
     *
     * mybatis 的一级缓存默认开启
     * 基于sqlsession mybatis 的一二级缓存
     * [无事务前提，dao每个sql操作都是一个slSession实例在进行操作不可能共用一级缓存。
     * 在有事务前提下，同个事务内使用一个sqlSession实例所以这个事务内的所有sql会共用一级缓存]
     * 如果出现增删改操作，则自动清除缓存
     *
     * 测试代码
     * public void test(){
     *     get(1111L)
     *     get(1111L)
     * }
     * 结论：不在事务下，开启两个sqlsession 不走缓存查询两次
     *
     * @Transactional
     * public void test2(){
     *     get(1111L)
     *     get(1111L)
     * }
     * 结论：在事务下，同一个sqlsession 走缓存查询一次
     *
     * @Transactional
     * public void test3(){
     *     get(1111L)
     *     otherClass.otherGet(1111L)
     * }
     * 结论：在事务下，第二次查询开启新事务 不走缓存查询两次
     *
     * otherclass{
     *     @Transactional(propagation = Propagation.REQUIRES_NEW)
     *     otherGet(){
     *         get(1111L)
     *     }
     * }
     *
     * Cache使用时的注意事项 :
     * 1.只能在[只有单表操作]的表上使用缓存
     * 不只是要保证这个表在整个系统中只有单表操作，而且和该表有关的全部操作作必须全部在一个namespace下.
     * 2.在可以保证查询远远大于insert,update,delete操作的情况下使用缓存这一点不需要多说，
     * 所有人都应该清楚。记住，这一点需要保证在1的前提下才可以!
     *
     * mybatis 二级缓存（全局缓存）默认不开启
     * 基于namespace
     * 考虑：
     *  可能会有很多人不理解这里，二级缓存带来的好处远远比不上他所隐藏的危害缓存是以namespace为单位的，不同namespace下的操作互不影响.
     *  insert,update,delete操作会清空所在namespace下的全部缓存
     * 通常使用MvBatis Generator生成的代码中，都是各个表独立的，每个表都有自己的namespace.
     * 为什么避免使用二级缓存
     * 在符合[Cache使用时的注意事项]的要求时，并没有什么危害.
     * 其他情况就会有很多危害了
     * 针对一个表的某些操作不在他独立的namespace下进行
     * 例在UserMapperxml中有大多数针对user表的操作。但是在一个XXXMapperxml中，还有针对user单表的操作.
     * 这会导致user在两个命名空间下的数据不一致，如果在UserMapper.xm中做了刷新缓存的操作，在XXXMapperxml中缓存仍然有效，
     * 如果有针对user的单表查询，使用缓存的结果可能会不正确。更危险的情况是在XXXMapperxml做了insert,update,
     * delete操作时，会导致UserMapperxml中的各种操作充满未知和风险
     *
     */

    /**
     * springboot 三级缓存解决循环依赖
     * bean 生成经理以下几个阶段：
     * 1:读取@Controller 、@Service、@Component @bean
     * 2：通过BeanDefinitionReader 解析bean 定义成BeanDefinition
     * 3：BeanDefinition --（@1）-> 通过构造函数实例化----（@2）-->属性赋值(依赖注入)----->初始化----->通过AOP创建代理对象------>创建完成
     * 4：BeanDefinition --（@1）-> 通过构造函数实例化----（@2）-->通过AOP创建代理对象------>属性赋值(依赖注入)----->初始化------>创建完成
     * 注意：3是未出现循环依赖 4：是出现了循环依赖（因为循环依赖所以不得不提前暴露代理对象）
     *
     *  三级缓存举例子：
     *  A->B  B->A
     *  先把A 在 @1 位置 通过getSingleton()方法判断A是否存在  如果存在则返回
     *  不存在则在  @2 位置通过doCreateBean的addSingletonFactory（）把A的实例化对象放入三级缓存中
     *
     *  发现A->B 则生成B  发现B依赖A 则从三级缓存中拿到A 并把A放到二级缓存中 B初始化结束 注入A A初始化结束
     *  注意: B注入的半成品A对象只是一个引用，所以之后A初始化完成后，B这个注入的A就随之变成了完整的A
     *
     *  我们发现如果是二级缓存也能解决这个问题：为什么用三级缓存呢 因为如果是3这种情况 此时B注入的是A的普通对象  A生成之后会初始化A的代理对象，这样肯定是有问题的
     *  解决这个问题的思路就是，将AOP的操作提前
     *
     *  现在我们需要在第一步就去判断该对象是否要进行AOP操作，我们不可能所有的Bean在第一步的时候就去进行AOP，因为在Spring中整体的设计是在第5步才会去进行AOP，
     *  只有在某种特殊的情况下才需要在第一步去进行AOP。这个特殊情况就是当前Bean出现了循环依赖的情况下才需要提前进行AOP.
     *  A创建的时候，不太好判断是不是需要提前aop 但我们可以把A放到一个map中 B创建需要使用A 的时候，如果一级缓存没有，则去这个map中寻找，
     *  如果找到了则提前进行aop生成代理对象 注入B  这个map就是三级缓存
     *
     */
}
