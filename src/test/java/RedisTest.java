import main.Application;
import main.utils.RedisUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    /**
     * redis
     * 五种常规数据机构：String list hash set zset
     * 三种特殊数据结构：
     * geospatial（地理空间）
     * geospatial类型可以用来搜索附近的人，也可以用来判断两地之间的直线距离等；
     *
     * Hyperloglog（基数统计）
     * 基数概念
     * 举个例子，A = {1, 2, 3, 4, 5}， B = {3, 5, 6, 7, 9}；
     * 那么基数（不重复的元素）= 1, 2, 4, 6, 7, 9； （允许容错，即可以接受一定误差）
     * 估算的基数并不一定准确，是一个带有 0.81% 标准错误的近似值
     * （对于可以接受一定容错的业务场景，比如IP数统计，UV等，是可以忽略不计的）。
     *
     * Bitmap(位存储)
     * Bitmap 即位图数据结构，都是操作二进制位来进行记录，只有0 和 1 两个状态
     * 比如：统计用户信息，活跃，不活跃！ 登录，未登录！ 打卡，不打卡！ 两个状态的，都可以使用 Bitmaps！
     *
     * 如果存储一年的打卡状态需要多少内存呢？ 365 天 = 365 bit 1字节 = 8bit 46 个字节左右！
     */

    /**
     * 哨兵（Sentinel）和复制（保证Redis的高可用）
     *
     * Sentinel可以管理多个Redis服务器，它提供了监控、提醒以及自动的故障转移功能；
     * 复制则是让Redis服务器可以配备备份的服务器；
     * Redis也是通过这两个功能保证Redis的高可用；
     */

    /**
     * Redis真的是单线程的吗？
     * 单线程的好处
     * 不存在多线程切换而消耗CPU；
     *
     * Redis6.0之前是单线程的，Redis6.0之后开始支持多线程；
     * redis内部使用了基于epoll的多路服用，也可以多部署几个redis服务器解决单线程的问题；
     * redis主要的性能瓶颈是内存和网络；
     * 内存好说，加内存条就行了，而网络才是大麻烦，所以redis6内存好说，加内存条就行了；
     * 而网络才是大麻烦，所以redis6.0引入了多线程的概念，
     * redis6.0在网络IO处理方面引入了多线程，如网络数据的读写和协议解析等，需要注意的是，执行命令的核心模块还是单线程的。
     */

    /**
     * Redis持久化有几种方式？
     * redis提供了两种持久化的方式，分别是RDB（Redis DataBase）和AOF（Append Only File）。
     * RDB，简而言之，就是在不同的时间点，将redis存储的数据生成快照并存储到磁盘等介质上；
     * AOF，则是换了一个角度来实现持久化，那就是将redis执行过的所有写指令记录下来，在下次redis重新启动时，只要把这些写指令从前到后再重复执行一遍，就可以实现数据恢复了。
     * 其实RDB和AOF两种方式也可以同时使用，在这种情况下，如果redis重启的话，则会优先采用AOF方式来进行数据恢复，这是因为AOF方式的数据恢复完整度更高。
     * 如果你没有数据持久化的需求，也完全可以关闭RDB和AOF方式，这样的话，redis将变成一个纯内存数据库，就像memcache一样。
     */

    /**
     * 为什么 Redis 需要把所有数据放到内存中？
     *
     * Redis 为了达到最快的读写速度将数据都读到内存中，并通过异步的方式将数据写入磁盘。
     * 所以 Redis 具有快速和数据持久化的特征。如果不将数据放在内存中，磁盘 I/O 速度为严重影响 Redis 的性能。
     * 在内存越来越便宜的今天，Redis 将会越来越受欢迎。如果设置了最大使用的内存，则数据已有记录数达到内存限值后不能继续插入新值。
     */

    /**
     * Bloom Fifter
     *
     *  前言 你在开发或者面试过程中，有没有遇到过 海量数据需要查重，
     *  缓存穿透怎么避免等等这样的问题呢？下面这个东西超屌，好好了解下，面试过关斩将，凸显你的不一样。
     *
     *
     */

    @Before
    public void before() {
    }

    @Test
    public void test(){
        RedisUtil.set("c","fengyunwei");
        String secondKey = RedisUtil.get("c");
        System.out.println(secondKey);
    }

    @Test
    public void test1(){
        RedisUtil.set("d","4");
        String secondKey = RedisUtil.get("d");
        System.out.println(secondKey);
    }

    @Test
    public void test2(){
        RedisUtil.set("p","pffdfd");
        String secondKey = RedisUtil.get("p");
        System.out.println(secondKey);
    }

}
