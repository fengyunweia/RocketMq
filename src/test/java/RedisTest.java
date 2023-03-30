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
     * 原理：比如有m个数据需要
     *  前言 你在开发或者面试过程中，有没有遇到过 海量数据需要查重，
     *  缓存穿透（请求的数据不在缓存中，也不再数据库中）怎么避免等等这样的问题呢？下面这个东西超屌，好好了解下，面试过关斩将，凸显你的不一样。
     *  数据库写入数据的时候，把数据先通过布隆过滤器映射到散列表中，然后，数据不在缓存中，
     *  则查询数据库的时候，先通过计算散列值看是否存在 不在则直接返回失败
     *          * 比如A，B 两个文件，每个文件里面有m个url{1,2,3.....m} 想要计算B里面有哪些url 不在A里面
     *          *
     *          * 使用K个散列函数{h1,h1,h3....hk}
     *          * bit集合是n个（值都是0）
     *          * 则每一个url都通过K个散列函数散列到bit集合上 （第m个，通过K个散列函数，散列到bit集合的K个点上 k个点赋值1）
     *          * 然后对B文件里面的每一个url 通过K个散列函数找到K个点 只要存在一个0则次url不存在A中
     *          *
     *          * 缺点：
     *          * （由于不同元素计算的hash值可能相同，所以存在一定的误判）布隆过滤器认为不在的，一定不会在集合中；布隆过滤器认为在的，不一定存在集合中。
     *          * 布隆过滤器有一定的误判率，
     *          * 不支持删除元素：如果一个元素被删除，但是却不能从布隆过滤器中删除，这也是存在假阳性的原因之一
     *          *
     *          * 优点：
     *          * 节省空间：不需要存储数据本身，只需要存储数据对应hash比特位
     *          * 时间复杂度低：插入和查找的时间复杂度都为O(k)，k为哈希函数的个数
     *          *
     *          * 假设E表示错误率，n表示要插入的元素个数，m表示bit数组的长度，k表示hash函数的个数。
     *          *
     *          * （1）当hash函数个数 k = (ln2) * (m/n)时，错误率E最小（此时bit数组中有一半的值为0）
     *          * （2）在错误率不大于E的情况下，bit数组的长度m需要满足的条件为：m ≥ n * lg(1/E)。
     *          * （3）结合上面两个公式，在hash函数个数k取到最优时，要求错误率不大于E，这时我们对bit数组长度m的要求是：m>=nlg(1/E) * lg(e) ，
     *          * 也就是 m ≥ 1.44n*lg(1/E)（lg表示以2为底的对数）
     */

    /**
     * 分布式锁：
     * 情景：两个订票系统同时订票 但是票只有1张，如果并发，会导致出现-1 的情形
     *
     * 解决方案：redis( setnx key value)它会先判断key是否已经存在，如果key不存在，那么就设置key的值为value，并返回1；如果key已经存在，则不更新key的值，直接返回0：
     * 基于这个方案，我们可以实现一个简单的分布式锁
     *
     * 版本1：（redis 发送一个setnx命令 如果返回1 则设置锁成功，可以进行业务操作
     * 如果返回0 则没有获取成功 可以通过循环一直获取锁  知道这个key被删除 获得锁 执行）
     * 版本1问题：如果获取锁之后，业务报错异常则锁一直不会被释放 造成死锁
     *
     * 版本2：基于版本1问题 可以给锁设置一个超时时间（跟获取锁是两条代码行（不具有原子性）） 防止死锁
     * 版本2问题：两条代码，可能出现执行结束获取锁，和设置超时时间之间服务器宕机
     *
     * 版本3：使用一条命令同时设置超时时间和获取锁
     * 版本3问题：设置超时时间，A服务获取锁设置超时时间30S 业务代码处理时间50秒 执行到30s后B服务获取到了锁
     * 然后A服务释放锁 把B服务的锁给释放了
     *
     * 版本4： 方法1：把超时时间设置长一点 远超业务处理时间 但影响系统性能
     *        方法2：把key 的value设置成一个唯一的值（比如UUID+当前线程id） A服务器获取锁设置锁时间30S
     * 且设置的value唯一 业务代码处理时间50秒 执行到30s后B服务想要获取锁 此时B服务器还没结束 A结束了想要释放锁
     * 先根据key 获取value判断是不是自己的唯一value 结果不是，则不删除
     * 判断是不是跟自己的value值相同，如果不是，则不删除key，然后B执行结束 删除
     * 版本4问题：判断锁是否是自己的唯一value 和释放锁是两步操作，如果此时判断是自己的锁 执行删除之前
     * 服务器卡顿了 恰好这几秒锁过期，另一个服务得到锁，然后A删除 还是会把B的锁删掉
     *
     * 版本5： 使用Lua 将get key value 判断是不是当前线程 删除锁 变成一个整体提交到Redis
     *
     * 上面这些过程如果自己写，还是很复杂的，所以推荐使用Redisson的分布式锁，把上面的步骤封装 我们只需要简单的调用
     *
     * redisson 原理：
     * 第一步先尝试使用 tryAcquire（）试图获取锁（如果获得锁成功 则进入业务处理方法） 获取锁失败，回循环尝试获取锁
     * 如果设置了超时时间（没有看门狗） 如果超时线程还没执行结束，锁已经被释放，因为value值唯一问题，不会把其他线程的锁释放掉
     * 如果没有设置超时时间（有看门狗） 则根据当前信息创建默认30S超时  如果超时线程还没执行结束 看门狗自动延续锁超时时间
     *
     * 最后为了保证原子性，使用Lua语法将多个操作组合成一条获得锁命令给redis执行
     */
    /**
     * redis 与数据库的数据一致性
     * 大概可分为四种情况
     *  1：先更新数据库，在更新缓 2：先更新缓存，在更新数据库 3：先删除缓存 ，在更新数据库 4:先更新数据库， 在删除缓存
     *
     *  问题1：选择更新缓存还是删除缓存
     *  更新缓存：缓存未命中的概率低
     *  如果更新缓存的数据需要经过复杂的计算 频繁的更新则导致系统的性能降低，如果是写入频繁的业务数据，频繁的更新却没有读取数据
     *  删除缓存：缓存未命中率概率高 未命中都会从数据库查找 ----------综合：选择删除缓存
     *
     *  问题2：先更新数据库，还是先删除缓存
     *  先删除缓存：
     *  1：线程A 删除缓存
     *  2：线程A：更新数据库失败
     *  3：线程B 从数据库获取数据并放入缓存（旧）
     *  4：线程A 更新数据库成功
     *  5：则缓存里面是旧数据，数据库是新数据 前后数据不一致
     *  （如果实际场景中必须使用这种 可以使用延时双删） 延时双删： 加两步 6：阻塞一段时间sleep(1000) 7:再次删除缓存
     *   原因：通过阻塞一段时间，否则线程B还没有把脏数据放到缓存中 再次删除脏缓存，下次就会从数据库找新数据 保证前后数据一致性
     *
     *  先更新数据库
     *  1：线程A更新数据库
     *  2：线程A删除缓存失败
     *  3：线程B 获取缓存（旧）
     *  4：线程A删除缓存成功
     *  5：线程C 获取缓存则为最新的数据 即前后数据一致
     *  有一些线程跟B类似在（1-4步中间） 会获得一部分旧数据 但因为这两步的执行速度会比较快 所以影响较小
     *  --------------------------综上：选择先更新数据库，在删除缓存
     */

    /**
     * redis 为什么说是单线程
     * redis 内部使用文件事件处理器File event handler他是单线程的，所以说redis是单线程模型
     *  它采用IO多路复用机制（epoll）同时监听多个socket ,把这些连接放入队列中，文件事件分派器每次从队列取一个socket
     *  通过事件类别分给不同的文件事件处理器
     */

    /**
     * redis 怎末处理过期key 以及内存淘汰策略
     *
     * 答案：定期删除+惰性删除
     * 定期删除：redis 默认每隔100ms 就随机抽取（注意是随机抽取）一些设置了过期事件的key,如果过期就会删除，
     * 比如有10万个key 设置了一个小时后过期，如果redis 每隔100ms就扫描10万个key redis 基本就凉了
     * 因此有了惰性删除：获取某个key之前，redis 会检查一下 这个key 是否设置了过期时间，并检查是否过期，
     * 如果过期就会删除不返回东西
     *
     * 这里又会引起一个问题，定期删除一直没有检查到一些过期key 系统也没有使用这些类，不会进行惰性删除 导致大量过期key堆积在内存
     * redis内存很快被消耗殆尽，
     * 因此可以使用内存淘汰策略：
     * redis 有7种淘汰策略：
     * 1：noeviction：当内存不足以写新数据的时候，那么写操作会报错（一般没人用，毕竟只是缓存，又不是mysql这种ACID的关系型数据库）
     * 2：allkeys-lru：当内存不足以写新数据的时候，在key空间中，移除最近最少使用的key（这个是最常用的），如果只移除一个的话，那么最后一个key肯定被移除
     * 3：allkeys-random：当内存不足以写新数据的时候，在key空间中，随机移除某个key（一般没人用，万一把我活跃的key移除了咋办）
     * 4：volatile-lru：当内存不足以写新数据的时候，在设置了过期时间的key中，移除最近最少使用的key（这个也还凑合，但是也不咋地，都设置过期时间了，你还给我移除干嘛）
     * 5：volatile-random：当内存不足以写新数据的时候，在设置了过期时间的键空间中，随机移除某个key（极其不合适，还不如allkeys-random）
     * 6：volatile-ttl：当内存不足以写新数据的时候，在设置了过期时间的键空间中，有更早过期时间的key优先移除
     * （也不咋合适，不一定更早过期的就是不活跃key，而且都快过期了你还给我移除干嘛，自生自灭）
     * 7：Redis 4.0 引入了 volatile-lfu 和 allkeys-lfu 淘汰策略，LFU 策略通过统计访问频率，将访问频率最少的键值对淘汰。
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
    /**
    支付中心 是为了智己汽车科技有限公司网售汽车流程中的支付服务，
    上游对接订单中心，用户从网上预约试车，到下订单，到支付中心，下定金，付尾款 ，贷款，全款包括，对车的一系列日常维护保养金额等的信息的网页管理平台，
    下游对接上汽财务平台，多家银行的支付调用，包括建设，交通等银行的微信和支付宝API付款功能，包含了一些支付平台的管理，人员信息的管理

    负责任务：
       1：对接上财聚合支付功能，根据上财接口文档，定制API接口，包括，撰写，测试，配合连调等
       2：编写单测，使用Mockito编写用例测试 云校管理平台单元测试用例覆盖率达到50%，
       3：sonar扫描重危，异味问题解决
       4：maven 管理优化
       5：优化定时任务，对接智已二次开发的xxl-job定时任务
       6：对接智已异常信息收集服务
       7：人员权限功能的实现（不同公司的人员只能查看自己组织下的支付信息）
     */

    /**
      msm 是为上汽通用（最大的客户）使用的媒介运营管理平台，大致的功能与MC一致，MC创建媒体和排期需要推送到MSM ,
      MSM创建媒体和排期需要推送到所有代理商MC，代理商提交的媒体和排期也需要MSM客户的最终审批
      任务描述：
      1：排期相关的数据库建模设计 梳理排期模块的业务关系，使用建模软件设计数据库，挑选合适表字段以及类型，
      2：主从数据库的搭建 系统有查看历史数据和历史版本与当前版本的需求，因此设计了主从数据库，
      3：排期模块业务撰写 根据AD需求撰写业务代码
      4：秒针监播效果数据的处理与展示 同步秒针模块的监播原始数据，并映射成表字段存库以及展示
      5：xxl-job 定时任务接入搭建客户端服务器 集成更易管理的定时任务
      6：接口优化 优化接口用时
     */


}
