import main.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class TransactionalTest {

    @Test
    public void test(){
        List<String> a =null;
        //这里会报错null
        for (String string: a) {
            System.out.printf(string);
        }
        /**
         * 实例：根据媒体查询供应商（即一条sql） 未查到 则开一个事务新增（新增的代码设置了传播机制为PROPAGATION_REQUIRES_NEW
         * （它会开启一个新的事务。如果一个事务已经存在，则先将这个存在的事务挂起）），然后再查sql 结果还是没查到
         *
         * 解读: spring 设置的默认隔离级别是可重复读（即在一个事务中，两次对一个数据查询中间开启一个新事物更改查询结果一致）
         * spring的默认传播机制是(Propagation.REQUIRED 查询上下文如果当前没有事务则开启一个事务，如果有则加入)
         * 则第一次根据媒体查询供应商查询的结果是0 开启事务B新增供应商并提交（由于新增的事务传播机制为 新增的代码设置了传播机制为PROPAGATION_REQUIRES_NEW）
         * 则之后的查询失败引起的数据回滚并不会引起B事务的回滚（即数据库能够查到此新增的供应商数据）然后因为可重复读 则第二次查询还是再A事务中
         * 则返回第一次查询的结果 即0条 所以查询不到新增的供应商数据
         *
         * 解决方案：思考了两个解决方案
         * 1：将第二此的查询开启一个新事务C（事务传播机制设置为PROPAGATION_REQUIRES_NEW ） 最开始的A事务是包含了B事务 所以才有隔离级别的问题
         * 此时B和C 属于顺序执行事务关系 则C能查到
         *  2：再A事务上设置事务隔离级别为读已提交（但由于在事务的执行中可以读取到其他事务提交的结果）则不会发成解读中前后两次查询都为0条的问题
         */
        /**
         * spring 默认事务传播机制（required（方法A调用方法B，它们用同一个事务。(如果B没有事务，它们会用同一个事务。)(只要有一个回滚，整体就会回滚)））
         * 默认隔离级别（使用数据库的 mysql默认不可重复读） 一下
         * 以下基于默认事务传播机制
         * 事务调用总结
         * 总结：
         * 方法A调用方法B：
         * 如果A和B方法在同一个类中：
         * 如果A加@Transactional注解，B加不加@Transactional注解，事务是有效的，则AB在同一事务中。
         * 如果A不加@Transactional注解，B加不加@Transactional注解，事务都是无效的。
         *
         * 如果A和B不在同一个类中：
         * 如果A加@Transactional注解，B加不加@Transactional注解，事务是有效的。
         * 如果A不加@Transactional注解，B加了@Transactional注解，只有B是有事务的；
         * 如果A不加@Transactional注解，B不加@Transactional注解，A、B都是没有事务的。
         *
         * 或者这样理解
         * 1、如果A加@Transactional注解，不管是不是在一个类中，不管B加不加注解，AB都是在同一事务中；
         * 2、如果A不加@Transactional注解，只有B加@Transactional注解，AB方法为同一类，事务失效；AB不同类，只有B有事务；
         * 3、如果A不加@Transactional注解，B不加@Transactional注解，则没有事务；
         *
         * 原因：A方法上有@Transactional注解，spring在管理的时候，会生成一个代理类，真正调用到A方法时，
         * 实际执行的是代理类里面的方法，该代理类里面的方法已经包括了B方法的调用，已经成为了一个方法。所以事务是有效的。
         * （启动服务，spring初始化bean的时候，如果该类或方法有@Transactional注解 会给这个类生成一个代理类（带事务）
         * spring会使用aop的方式增强这个类，
         * 如果想要使用事务，就必须调用这个代理类，则内部调用时，相当于this. 没有调用代理类，所以内部调用事务不生效）
         */
    }

}
