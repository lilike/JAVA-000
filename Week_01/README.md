# 01 编程语言

Java 是一种面向对象、静态类型、编译执行，有 VM/GC 和运行时、跨平台的高级语言。

![图片](https://uploader.shimo.im/f/aAuwe202xNT3lO80.png!thumbnail)

![图片](https://uploader.shimo.im/f/t0zPPZRQaBDcGaDa.png!thumbnail)


Java是通过JDK不跨平台实现了class文件的跨平台,从而一次编译,导出执行.

C++是通过源代码跨平台,所以到了不同的平台还要反复编译,但是编译的过程中可能出现很多的错误.依赖等很多头疼的问题.

![图片](https://uploader.shimo.im/f/IKNF0awHjvnolYQq.png!thumbnail)

我们把Java的源代码进行编译后变成了Class文件,也就是字节码文件,这个里面的文件内容是byte类型的,然后Java虚拟机启动之后,通过类加载器,把字节码文件加载成对象实例的过程.


# 02 Java字节码技术

## 2.1 认识字节码

什么是字节码文件,字节码文件是由单字节(Byte) 的指令组成的,每一个字节表示一个指令,然后理论上有256个指令,因为正数Byte的取值范围是0-255.实际上只有200个指令左右.

主要的指令分为下面四类:

1. 栈操作的指令,包括和局部变量进行交互的指令 i_store i_load
2. 程序的流程控制指令  goto ifCmp
3. 对象操作指令,包括方法的调用指令 invorkSpecial
4. 算术运算指令,包括类型转换 i2d

如何生成字节码文件(带有助记词的字节码文件)

```plain
package com.lilike.daily;
public class HelloWorld {
    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
    }
}
```
```plain
javac HelloWorld.java
-v 输出附加信息
-c 反编译
javap -v -c Helloworld
```
得出如下的结果

```plain
![图片](https://uploader.shimo.im/f/qx5jdQn4ExOIrA4s.png!thumbnail)
```
关于字节码的指令如何看可以看下面的文章:

[https://juejin.im/post/6844903588716609543](https://juejin.im/post/6844903588716609543)

主要的组成

1. 文件的信息,包括位置,版本,修改时间,权限
2. 常量池信息
3. 方法表集合
    1. 操作数栈 本地变量表数目 参数数量
    2. 操作权限
    3. 指令集
    4. 本地变量表
    5. 指令行对应源码的行号

同时我们反编译出的带有助记符的字节码文件里面的助记符其实是和二进制byte指令是一一对应的.如果我们直接看byte是看不出什么名堂的.

![图片](https://uploader.shimo.im/f/4rhGD0kSUZTxVxGg.png!thumbnail)

关于字节与助记词之间的对应表如下:

![图片](https://uploader.shimo.im/f/dm49fVQdB28L0vCP.png!thumbnail)





Java里面如何实现的循环呢?其实是通过字节码文件,我们可以发现是通过比较 + goto指令来实现的.

![图片](https://uploader.shimo.im/f/dNZWOhP7DNHvy3Bo.png!thumbnail)

方法调用的指令有哪些?

invorkStatic

invorkSpecial

invorkVirtual

invorkinterface

invorkdynamic


## 2.2 字节码运行时的结构

![图片](https://uploader.shimo.im/f/l8G3PdRKbuzjJbOP.png!thumbnail)

JVM是基于栈的计算机器.

每当创建一条线程,就会创建一个虚拟机栈.VM Stack,用来存储栈帧.

每当进入一个方法,就会创建一个栈帧.

栈帧是由操作数栈,临时变量表,Class引用(常量池中的Class)表示是属于哪个类的方法.组成.

一个栈帧里面的例子:

![图片](https://uploader.shimo.im/f/mOJCdxylXWUZW0pu.png!thumbnail)

# 03 JVM类加载器

## 3.1 类的生命周期

![图片](https://uploader.shimo.im/f/tVRtLffE8FMLyFZ8.png!thumbnail)

类的生命周期如下:

1. 加载 : 通过全类名找到class文件并加载为字节流的过程
2. 校验: 校验字节流的格式,指令格式,版本等等
3. 准备: 为类的变量提供空间,还有初始化值等
4. 解析: 符号解析为引用
5. 初始化: 构造器,静态值,静态方法等
6. 使用:
7. 卸载
## 3.2 类的加载时机

类加载到JVM里面但是不一定会去初始化.注意这一点.

![图片](https://uploader.shimo.im/f/UzQCYKO96KIsYNFn.png!thumbnail)


## 3.3 类不会初始化的时机(可能会加载)

![图片](https://uploader.shimo.im/f/vXrWmXDpF8GfP2Mm.png!thumbnail)


## 3.4 类加载器

通过类的全类名,找到class文件,并且以字节流的格式加载到JVM里面的过程,就是类加载器干的活.

有三类加载器:

1. BootStrapClassLoader
2. ExtClassLoader
3. AppClassLoader

加载器的特点:

1. 双亲委派: 也就是如果一个class需要被一个类加载器加载,他不会马上去加载,而是去找自己的父亲,看看他有没有加载,如果这个class文件在自己的加载范围无法找到,那么就不会加载,
2. 负责依赖 不同的加载器负责的class路径不一样,比如Boostrap负责的是JAVA_HOME/lib
3. 缓存加载 : 已经加载过了,也不会加载,然后才是自己去加载.

注意双亲委派,但是实际上加载器之间的关系不是继承而是组合的关系.取名有一些歧义.


# 04 JVM内存模型

## 4.1 JVM内存结构

![图片](https://uploader.shimo.im/f/QXd6XrH6Z0KAUbwl.png!thumbnail)

每个线程都有自己的线程栈,线程栈里面的局部变量,基本数据类型是保存在线程里面的.

线程之间的局部变量是相互隔离的.

对于创建的对象,包括对象里面的基本数据类型,都是存入到堆内存里面的.

即使赋值给局部变量,也是保存在堆内存里面的.

## ![图片](https://uploader.shimo.im/f/EbUo2lWIB3mmHGXY.png!thumbnail)

对于线程栈里面的局部变量是保存在栈帧里面的局部变量表里面,如果是对象变量,保存的是对象在堆内存里面的地址.

堆内存里面保存的是对象,包括对象里面的基本数据类型和引用.

对于静态变量和静态方法都是保存在堆里面的.

## 4.2 JVM栈内存结构

![图片](https://uploader.shimo.im/f/fLDe1tvaTadNRmoA.png!thumbnail)

## 
JVM每当启动一个线程,就会创建一个方法栈.如果是JNI,那么就会创建本地方法栈.并且每个栈还有对应的空间大小,可以通过Xss1m来指定


JVM每当执行一个方法,就会创建一个栈帧.

![图片](https://uploader.shimo.im/f/tz2zzU5KNqm6pljw.png!thumbnail)

栈帧的组成:

1. 方法返回值
2. 局部变量表
3. 操作数栈
4. Class/Method指针表名该方法是属于哪一个类的哪一个方法,指向常量池里面的内容
## 4.3 JVM堆内存结构

![图片](https://uploader.shimo.im/f/mBTMW6Xpk8UgdZPV.png!thumbnail)

JVM的堆的内存结构分为 年轻代和 老年代.

年轻代分为 Eden 和两个 Survivor区

此外还有元空间MetaSpace,里面有常量池和方法区.

Compressed Class Space, 存放 class 信息的，和 Metaspace 有交叉。

CodeCache  存放 JIT 编译器编译后的本地机器代码。

# 05 JVM启动参数

## 5.1 JVM启动命令分类

java启动命令:

```plain
java [options] -jar xxxx [args] -- options 就是启动参数
```
![图片](https://uploader.shimo.im/f/QFYLsRKHofi5vslK.png!thumbnail)


JVM启动参数分类:

1. 系统属性参数
2. 运行模式的参数
3. 堆内存设置参数
4. GC策略的参数
5. 分析诊断的参数
6. JavaAgent的参数

![图片](https://uploader.shimo.im/f/fktlSysRpk6e0ZxH.png!thumbnail)

-Xms和-Xmx如果设置不一样,可能会导致一开始启动就产生比较多的FGC,然后从而导致性能不稳定,最终软件性能抖动

## 5.2 JVM默认配置

-Xmx 默认是系统内存的1/4

-Xms 默认是系统内存是1/6  初始化young区大小4

默认 -server 模式启动

-Xss 单个线程栈的大小,默认是512K

## 5.3 GC相关的参数

![图片](https://uploader.shimo.im/f/LWeHJH4Myfw9WyZS.png!thumbnail)

## 5.4 JVM调试参数

主要是两个,一个是JVM发生OOM的时候,导出内存快照,另外一个是远程Debug

![图片](https://uploader.shimo.im/f/4kfcqdvczOVHCdAy.png!thumbnail)


# 06 JDK内置命令行工具

![图片](https://uploader.shimo.im/f/RW1xmCQrKdctq8ER.png!thumbnail)

![图片](https://uploader.shimo.im/f/27a5RqhwoBSQQYia.png!thumbnail)


## 6.1 jstat常用方法

jstat通常是用来看gc信息的

```plain
jstat -gc pid 1000 1000 -- 数字表示时间间隔和次数
```
## 6.2 jmap的常用方法

![图片](https://uploader.shimo.im/f/Mqw7HViRGk57HHaF.png!thumbnail)

## 6.3 jstack 的用法

![图片](https://uploader.shimo.im/f/M7OSCkshFxRy3gAW.png!thumbnail)

## 6.4 jcmd的用法

![图片](https://uploader.shimo.im/f/q2D3Y38ceqHH3BEq.png!thumbnail)

# 07 JDK内置图形化工具

1. jconsole
2. jvisualvm
3. VisualGC
4. jmc
# 08 GC背景和原理

## 8.1 如何确定某些对象实例可以回收?

采用计数法.如果某些对象,被其他对象引用了一次,那么给它计数+1,那么为0的对象就是没有其他对象引用了,那么就可以清理掉了.

但是可能会遇到一个问题,就是相互引用.形成一个小团体的问题.

这样的话,就需要采用GC ROOT开始进行计数了.

也就是引用计数变成了引用跟踪.

把某一些对象标记为GCROOT,从这些对象开始进行标记,如果他们引用了某些对象,就给那些对象进行一个标记,然后依次进行标记,如果没有被标记的,那么就是不可达到的对象,就可以清理掉了.

具体有哪些对象会被标记为GCroot.

```plain
Thread - 活着的线程
Stack Local - Java方法的local变量或参数
JNI Local - JNI方法的local变量或参数
JNI Global - 全局JNI引用
Monitor Used - 用于同步的监控对象
```
![图片](https://uploader.shimo.im/f/1Yf8bqDCvW5nManQ.png!thumbnail)

![图片](https://uploader.shimo.im/f/5IcNChCFQ2EdDshR.png!thumbnail)


因为活动的线程是作为GCroot存在的,如果程序一直运行,那么活动的线程不断的产生,对于标记是非常有阻碍的,于是就需要STW,stop the world



![图片](https://uploader.shimo.im/f/tNYU9qGDGBGNT3qM.png!thumbnail)

## 8.2 有哪些清理内存的方法?

![图片](https://uploader.shimo.im/f/GrWnGgRWtkpA2SkJ.png!thumbnail)

[https://blog.csdn.net/kzadmxz/article/details/96574203](https://blog.csdn.net/kzadmxz/article/details/96574203)

标记清除和标记整理都是适用于老年代.

标记清除会产生空间碎片,标记整理不会.

标记复制算法也不会产生空间碎片,但是内存利用效率比较低,用空间换时间.

1. 标记清除法

从GCROOT进行出发,将内存中可以到达的对象进行标记,不可以到达的对象就会一次性清理掉,这样做的好处是比较简单,但是会产生内存碎片.

优势可以处理循环依赖的问题,只扫描到部分对象.

![图片](https://uploader.shimo.im/f/slGb3ASXcLdSqd4c.png!thumbnail)

1. 标记整理法

![图片](https://uploader.shimo.im/f/tA596o1ANaPFIitO.png!thumbnail)

清理完之后,会产生内存碎片,还有一种方法就是对内存里面存活的对象进行压缩,把不连续的空间变成连续的空间.

这是Old老年代常用的移动对象整理内存的方法.

## 8.3 GC的分代处理

分代假设:

有些对象存活很久,并且会一直存活,有些对象出生后不久就会死亡,并且会很短命.

内存池的划分:

分为Young 和 Old,不同对象处理不同的区域,![图片](https://uploader.shimo.im/f/EK2Z1W4udVMQDHVk.png!thumbnail)


## 8.4 存活对象在内存区域中的移动

![图片](https://uploader.shimo.im/f/TrtJuMdzyNXsybZy.png!thumbnail)

为什么从Eden区到S区是采用复制算法,而不是移动算法?

因为复制不会产生内存碎片,移动可能会产生.

对象首先在Eden区出生,然后Eden区满了之后,会把Eden区的存活对象复制到S区,然后把Eden区的内存清理掉.

S区的对象存活到一定周期,会复制到老年代.

老年代默认都是存活的对象,采用的是标记,整理的算法.

进行GC的时候.首先从GCroot出发标记所有可以到达的对象.

删除不可以到达的对象.

然后进行压缩,从老年代开始的地方,依次整理内存空间,.

# 09 常用GC策略

## 9.1 串行GC

## 9.2 并行GC

简称是PS

```plain
-X : +UseParallelGC
-X : +UseParallelOldGC
-X : +UseParallelGC -XX:+UseParallelOldGC
```
年轻代和老年代都会触发STW的事件.

在年轻代会使用标记-复制的算法,在老年代, 标记--复制--清除的算法.

-XX : ParallelGCThreads=N指定GC的线程数量,默认值是CPU的线程数.

适合多核的服务器,主要的目标就是增加服务器的的吞吐量,因为对于系统资源有效使用,可以达到更多的吞吐量,.

GC的时候,所有的CPU都在并行清理垃圾,所以总的暂停时间更短.

两次GC的间隔期,没有GC线程运行,不会消耗系统的资源.

JDK8的默认GC策略是什么?

并行GC的暂停时间比较长,但是比较专注,效果比较好.

CMSGC的暂停时间短,但是效果不太好,比较不够专注,GC的时间也是不可控的.


## 9.3 CMS GC

-XX: +UseConcMarkSweepGC

CMS的GC对于年轻代采用的STW的mark-copy的标记复制算法,对老年代使用的是mark标记-sweep清理的算法.注意: 这里没有压缩的算法.

CMS GC为什么没有压缩?

CMS设计的初衷就是避免长时间的卡顿.

主要采用两种手段实现:

1 不对老年代进行整理,采用空闲列表 free-lists 来管理内存空间的回收,

1. 在 mark - and - sweep 阶段的大部分工作和应用线程一起并发的执行

也就是说,在这些阶段,没有明显的应用线程暂停,但是值得注意的是,它仍然和应用线程抢CPU的时间,默认情况下,CMS使用的并发线程数是CPU核心数的1/4;

CMS是比较复杂的实现,实验性的GC,用于降低系统的延迟.

进行老年代的并发回收的时候,可能伴随多次的年轻代的minor gc.

并行GC和并发的CMSGC的区别?

CMS的6个阶段:

1. 阶段1: Initial Mark (初始标记) STW, 暂停时间比较短,标记所有的根对象,以及跨代的第一个对象

![图片](https://uploader.shimo.im/f/BX6GkiVIvm7IuZxu.png!thumbnail)

2. 阶段2 : Concurrent Mark (并发标记) 和应用线程一起干活,粗略标记

![图片](https://uploader.shimo.im/f/XriebFpMHfDjfkvc.png!thumbnail)

3. 阶段3 : Conrrent Preclean (并发预清理)

![图片](https://uploader.shimo.im/f/hMdrjJ5x6DiVG3vy.png!thumbnail)

1. 阶段4 ; Final Remark ( 最终标记) 最后一次Stw停顿

![图片](https://uploader.shimo.im/f/G7fcJ71nHOjeu81A.png!thumbnail)

2. 阶段5 : Conrrent Sweep (并发清除) 删除不需要再使用的对象

![图片](https://uploader.shimo.im/f/yNOgVtp5MdevNjgy.png!thumbnail)

3. 阶段5 : Conrrent Reset (并发重置) 准备下一次的GC,重置CMS算法相关内部数据

CMS把GC的过程拆的非常的细,把大的阶段打碎了,有的阶段可以并发,有的阶段不能够并发.

CMS 垃圾收集器在减少停顿时间上做了很多复杂而有用的工作，用于垃圾回收的并发线程执行的同时，并不需要暂停应用线程。 当然，CMS 也有一些缺点，其中最大的问题就是老年代内存碎片问题（因为不压缩），在某些情况下 GC 会造成不可预测的暂停时间，特别是堆内存较大的情况下

## 9.4 G1 GC

![图片](https://uploader.shimo.im/f/8LYtb19ZmY7JLLEE.png!thumbnail)


G1的全程是Garbage-First,意思就是垃圾有限,哪一块垃圾最多,就会有限清理.

G1 GC最主要的设计目标,就是将STW停顿的时间和分布,变成可以预期和可以配置的.

事实上,G1 GC是一款软实时的垃圾收集器.可以为其设置某项特定的性能指标,为了达到可预期的停顿时间指标,G1 GC有一些独特的实现,.

首先堆已经部分为老年代和年轻代了,而是划分为多个(通常是2048)个可以存放对象的小块堆区域(smaller heap regions), 每一个小块,可能被定义为 Eden区,一会被指定为Survior区或是Old区,

在逻辑上，所有的Eden区和Survivor区合起来就是年轻代，所有的Old区拼在一起那就是老年代

![图片](https://uploader.shimo.im/f/TPYdwsGyJ6Mf9EAj.png!thumbnail)

有容忍度了,并不一定一次性要把所有的年轻代或是老年代全部清理掉.

这样做就可以做到增量处理.

这样划分之后，使得 G1不必每次都去收集整个堆空间，而是以增量的方式来进行处理: 每

次只处理一部分内存块，称为此次GC的回收集(collection set)。每次GC暂停都会收集所有

年轻代的内存块，但一般只包含部分老年代的内存块。

G1的另一项创新是，在并发阶段估算每个小堆块存活对象的总数。构建回收集的原则是：垃圾最多的小块会被优先收集。这也是G1名

称的由来。

-XX: + UseG1Gc -XX:MaxGCPauseMillis=50

告诉G1GC,告诉系统GC每次暂停的时间控制在50ms,可以执行GC的次数增多.

G1的参数

```plain
-XX:+UseG1GC：启用G1 GC；
G1的年轻代已经不是固定的了,而是一个动态的值
-XX:G1NewSizePercent：初始年轻代占整个Java Heap的大小，默认值为5%；
-XX:G1MaxNewSizePercent：最大年轻代占整个Java Heap的大小，默认值为60%；
-XX:G1HeapRegionSize：设置每个Region的大小，单位MB，需要为1，2，4，8，16，32中的某个值，默认是堆内存的1/2000。如果这个值设置比较大，那么大对象就可以进入Region了。
// 这个可以看到是继承CMS的
-XX:ConcGCThreads：与Java应用一起执行的GC线程数量，默认是Java线程的1/4，减少这个参数的数值可能会提升并行回收的效率，提高系统内部吞吐量。如果这个数值过低，参与回收垃圾的线程不足，也会导致并行回收机制耗时加长。
// 
-XX:+InitiatingHeapOccupancyPercent（简称IHOP）：G1内部并行回收循环启动的阈值，默认为Java Heap的45%。这个可以理解为老年代使用大于等于45%的时候，JVM会启动垃圾回收。这个值非常重要，它决定了在什么时间启动老年代的并行回收。
// 留一点活不干了
-XX:G1HeapWastePercent：G1停止回收的最小内存大小，默认是堆大小的5%。 GC会收集所有的Region中的对象，但是如果下降到了5%，就会停下来不再收集了。就是说，不必每次回收就把所有的垃圾都处理完，可以遗留少量的下次处理，这样也降低了单次消耗的时间。
// 
-XX:G1MixedGCCountTarget：设置并行循环之后需要有多少个混合GC启动，默认值是8个。老年代Regions的回收时间通常比年轻代的收集时间要长一些。所以如果混合收集器比较多，可以允许G1延长老年代的收集时间。
-XX:+G1PrintRegionLivenessInfo：这个参数需要和 -XX:+UnlockDiagnosticVMOptions 配合启动，打印JVM的调试信息，每个Region里的对象存活信息。
-XX:G1ReservePercent：G1为了保留一些空间用于年代之间的提升，默认值是堆空间的10%。因为大量执行回收的地方在年轻代（存活时间较短），所以如果你的应用里面有比较大的堆内存空间、比较多的大对象存活，这里需要保留一些内存。
-XX:+G1SummarizeRSetStats：这也是一个VM的调试信息。如果启用，会在VM退出的时候打印出RSets的详细总结信息。
如果启用-XX:G1SummaryRSetStatsPeriod参数，就会阶段性地打印RSets信息。
-XX:+G1TraceConcRefinement：这个也是一个VM的调试信息，如果启用，并行回收阶段的日志就会被详细打印出来。
-XX:+GCTimeRatio：这个参数就是计算花在Java应用线程上和花在GC线程上的时间比率，默认是9，跟新生代内存的分配比例一致。这个参数主要的目的是让用户可以控制花在应用上的时间，G1的计算公式是100/（1+GCTimeRatio）。这样如果参数设置为9，则最多10%的时间会花在GC工作上面。 Parallel GC的默认值是99，表示1%的时间被用在GC上面，这是因为Parallel GC贯穿整个GC，而G1则根据Region来进行划分，不需要全局性扫描整个内存堆。
-XX:+UseStringDeduplication：手动开启Java String对象的去重工作，这个是JDK8u20版本之后新增的参数，主要用于相同String避免重复申请内存，节约Region的使用。
-XX:MaxGCPauseMills：预期G1每次执行GC操作的暂停时间，单位是毫秒，默认值是200毫秒，G1会尽量保证控制在这个范围内。  
```
G1GC的注意事项

![图片](https://uploader.shimo.im/f/gLEWsZ4eP44j5pye.png!thumbnail)


## 9.5 GC版本的对比

![图片](https://uploader.shimo.im/f/HvJJ63aP4nWoEry8.png!thumbnail)

# 
## 9.6 常用的GC组合

![图片](https://uploader.shimo.im/f/ven6SGj8AcvjZStv.png!thumbnail)

## 9.7 GC如何选择

![图片](https://uploader.shimo.im/f/KEyohRWbYjQ2e85s.png!thumbnail)

电影票购买流程: 可以延迟比较高,整个流程十几秒,吞吐量优先,CPU竟可能的处理大多的业务

数字货币交易系统: 要求低延迟优先,每次GC的时间要竟可能短.

## 9.7 ZGC/Shenandoah GC

主要的优势是,暂停时间的短.GC的频率非常的高

ZGC最主要的特点包括:

1. GC 最大停顿时间不超过 10ms

2. 堆内存支持范围广，小至几百 MB 的堆空间，大至4TB 的超大堆内

存（JDK13升至16TB）

3. 与 G1 相比，应用吞吐量下降不超过15%

4. 当前只支持 Linux/x64 位平台，JDK15后支持MacOS和Windows系统

## 9.8 ShennandoahGC

Shenandoah 团队对外宣称ShenandoahGC的暂停时间与堆大小无关，无论是200MB 还是 200 GB的堆内存，都可以保障具有很低的暂停时间（注意:并不像ZGC那样保证暂停时间在10ms以内）

## 9.9 GC的总结

![图片](https://uploader.shimo.im/f/cBd0Av7ANp8C1WTB.png!thumbnail)

![图片](https://uploader.shimo.im/f/2SbjMQmtSB3clj9Z.png!thumbnail)




