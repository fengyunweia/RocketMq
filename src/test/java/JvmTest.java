public class JvmTest {

    /**
     * JVM java虚拟机
     * 分为5部分分别是：方法区、堆、虚拟机栈、程序计数器、本地方法栈
     * 方法区：主要存放类的信息，比如类的静态常量，构造函数，方法等信息
     * 堆：所有的对象实例、数组等都存放在这里
     * 虚拟机栈：虚拟机栈是有单位的，单位是栈帧（一个方法就是一个栈帧），分为 局部变量表、操作数栈，动态链接、出口
     *          局部变量表：存放了方法里面的局部变量（常用的基本数据类型）
     *          操作数栈：给方法用来操作运算的空间 比如 int c= 2+2 这里2+2的运算过程是在操作数栈里面 然后赋值给局部变量中的c
     *          动态链接：当我们要链接一些方法内部调用的其他方法比如 xxxServie.add() 这里存储这个链接的地方
     *          出口：方法的返回，正常return 异常抛出异常类
     * 程序计数器:JVM多线程是通过线程切换分配CPU执行时间实现的，为了在切换线程之后恢复到正确的位置，因此加入了程序计数器，
     *           记录当前线程的执行地址。每个线程独立分配程序计数器，互不影响。
     * 本地方法栈：java并不能直接调用操作系统，所以需要使用本地方法栈（底层是C和C++）来操作与操作系统相关的指令
     *
     * 从上面的描述可以知道，堆是占据空间最大的，因为频繁的创建对象实例和数组，如果我们不管理，很快堆内存就会被占满，引发OOM
     * 因此有了垃圾回收机制，但是怎末判断这个对象或者这块内存是不是无用垃圾，一般根据一下两个方法进行判断
     *   引用计数法：当一个对象创建的时候，给这个对象绑定一个计数器，用计数器统计对象被引用的次数，当次数为0时表示无引用，判定为垃圾对象
     *             优点：实现简单   缺点：无法解决循环引用问题
     *   可达性分析:从根节点开始向下搜索，搜索所走过的对象，标记，搜索结束，没被标记的对象为无用垃圾对象
     *
     * 当我们发现了垃圾，就要进行垃圾回收，垃圾回收也有算法的：
     * 标记-清除算法：通过可达性分析，将未被标记的对象全部清除
     *              不足之处：标记和清除效率都不好，比较分散，当一个内存比较大的对象进来，不得不触发垃圾回收，如果触发之后，还是放不下，就会放到老年代
     * 标记-整理算法：通过可达性分析，将无用对象标记，将被标记的对象整理到内存的某一端，之后清除无用对象
     *             优点：减少了内存碎片  缺点：比起标记-清除算法 多了一步内存移动操作，效率降低
     * 复制算法：将内存复制为1：1的两块区域，同一时间只允许使用一块活跃区域，标记完对象之后，把存活的对象复制到另一块区域，
     *          变成活跃区域将之前区域清空（清空这部操作基本不耗时）
     * 通过上面我们可以发现，
     * 标记-清除算法 和标记-整理算法对于对象存活较低的场景（新生代）很不合适，因为会产生大量的内存碎片，却很适合存活较高的场景（老年代经过15次考验）
     * 复制算法 很适合存活较低的场景 因为这样只需要复制少量的内存空间到新的区域
     * 所以有了新的垃圾回收算法分代垃圾回收：--------老年代用标记-整理或者标记-清除算法，新生代用复制算法
     *
     * 有了找垃圾的方法，有了回收垃圾的方法，就需要一个做这件事的器 即垃圾回收器（对垃圾回收算法的具体实现）
     * 年轻代垃圾回收器有：Serial、      ParNew、  Parallel Scavenge
     * 老年代垃圾回收器有：Serial Old、  CMS、     Parallel Old
     * 年轻代Serial :只能使用一个CPU回收 GC时，其他线程进入暂停状态 特点：使用复制算法  适合单CPU环境，没有上下文切换的成本，运行效率高
     *
     * 年轻代-ParNew：多线程垃圾收集器，同时启动多个线程进行垃圾收集，且GC时，其他线程会进入暂停状态 特点：采用复制算法 适合多CPU环境 只能与CMS配合使用
     *
     * 年轻代-Parallel Scavenge：多线程收集器，可以调整程序吞吐量 特点：采用复制算法 适合多CPU环境 可调节吞吐量
     *
     * 老年代-Serial Old：单线程收集器 特点：采用标记-整理算法
     *
     * 老年代-Parallel Old：多线程收集器 特点：采用标记-整理算法
     *
     * 老年代-CMS ： 特点：采用标记-清除算法 并发收集、低停顿 GC时，工作线程不暂停
     *
     * G1 ：分代回收器 特点：多线程收集器 并发GC、与工作线程同事进行 分年轻代、老年代进行垃圾回收 采用复制算法、标记-整理算法
     */
}
