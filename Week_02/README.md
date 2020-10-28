# 使用GCLogAnalysis.java演练不同的GC案例分析

## 9.9 GC的总结

![图片](https://uploader.shimo.im/f/cBd0Av7ANp8C1WTB.png!thumbnail)

# ![图片](https://uploader.shimo.im/f/2SbjMQmtSB3clj9Z.png!thumbnail)

1. GC日志解读和分

析10.1 上手一个案例来分析一下GC日志

## 
案例: 运行

```plain
java -XX:+PrintGCDetails GCLogAnalysis  
```
```plain
java -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
```
获取到GC的日志信息

![图片](https://uploader.shimo.im/f/8FMKnOci7kegQlTR.png!thumbnail)

模拟OOM,只需要把堆内存调小就可以了

```plain
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xms128m -Xmx128m GCLogAnalysis
```
![图片](https://uploader.shimo.im/f/O6ChQE88RKusVly6.png!thumbnail)


我观察到的现象就是程序启动之后,首先不断的进行youngGC,然后不断的进行Full GC.

第一次Full GC 直接把Young区的内存干到 0 ,然后把young区的对象搬到old区了.

然后不断的进行full gc,但是old区的对象已经基本不会死去,所以old区的内存也没有减少多少.

最终分配大对象内存不够,造成了OOM

这是在JDK8默认的GC回收器,也就是 并行回收器.

使用Xms 和 Xmx 为 512 和 2048的区别?

首先我把内存调到512,然后把内存调到2048去执行.

首先内存为2048的生成的对象更多,也就是效率更高.

其次从JVM垃圾回收日志里面去看的话,发现512m的垃圾回收的频率更高,但是因为内存更小,所以每次GC的暂停时间大概为10ms.

但是2048因为内存比较大,所以每次暂停的时间比较长,暂停的频率相对来说更加的少.

所以得出结论,内存和效率之间的关系:

首先内存小的话,GC的频率更多,但是暂停的时间比较少,总体效率不如大内存.

## 10.2 young GC VS Minor GC 和 Full GC vs Major GC

相关概念可以看这一篇

[https://juejin.im/post/6844903669251440653](https://juejin.im/post/6844903669251440653)

简单的讲:

Young GC就是针对young区进行一个垃圾回收,当Eden区满了之后,新产生对象分配失败,就会触发Young GC.

MinorGC : 和 Young GC的概念是一样的, Young 区也叫做Minor区

Full GC : 是针对整个堆进行垃圾回收.包括了新生代,老年代和元空间.

Major GC 等价于 Full GC


## 10.3 使用不同的GC垃圾回收工具演示

串行化的GC

```plain
java -XX:+UseSerialGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis  
```
![图片](https://uploader.shimo.im/f/qWmeDPlwwLppQ6Nt.png!thumbnail)

? 是指通过GC日志,FULLGC之后只知道老年代被压缩的数值,但是不知道年轻代的清除情况

并行GC

```plain
java -XX:+UseParallelGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis  
```
![图片](https://uploader.shimo.im/f/h6JLeArlmQRkTmlA.png!thumbnail)

观察 Young GC 与 Full GC

思考：如果不配置Xms会怎么样？

![图片](https://uploader.shimo.im/f/ezj11cevw8kB9ylk.png!thumbnail)

如果不配置Xms,那么系统一开始就只会申请一小部分内存,就会造成频繁的GC.

影响到软件的性能.但是不会发生OOM

CMS GC的运行情况

```plain
java -XX:UseConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGCDetail -XX:+PrintGCDateStamps GCLogAnalysis
```
可以观察到的是2个现象,第一个现象就是CMS是一种老年代的垃圾回收期,和ParNew也就是年轻代的并行垃圾回收期进行配合使用的.
系统Eden区内存分配失败的时候,最开始是ParNew负责垃圾回收,然后可以观察到每当Young区快满的时候,就开始进行垃圾回收,同时系统堆内存减少,如果Young区已经不够分配新的对象,并且晋升到老年代的时候,Old区的内存也不够了,就会触发GC,此时使用CMSGC处理老年代的内容.

![图片](https://uploader.shimo.im/f/ZkMxMHwmMgCd3sN9.png!thumbnail)

最后是使用G1 GC 进行垃圾回收的演示

```plain
java -XX:+UseG1GC -Xms512m -Xmx512m -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis![图片](https://uploader.shimo.im/f/kH0AKu4XZyLesr7c.png!thumbnail)
```