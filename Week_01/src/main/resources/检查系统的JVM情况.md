# 01 jps

```plain
29368 metadata-mg-app.jar
```
我在启动的时候没有配置参数,还是配一下,专业一点

# 02 jmap -heap 29368

```plain
Attaching to process ID 29368, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.92-b14

using thread-local object allocation.
Parallel GC with 4 thread(s)
使用的是并行的GC,也就是JDK8里面的默认GC,吞吐量比较好,但是延迟不一定了
Heap Configuration:
// heap使用率低于这个的时候开始收缩,xmx = xms 的时候无效
MinHeapFreeRatio         = 0
// heap使用率大于这个的时候开始扩张,xmx = xms 的时候无效
MaxHeapFreeRatio         = 100 
// 最大的堆内存,是当前系统内存的1/4,使用率偏低啊
MaxHeapSize              = 4158652416 (3966.0MB)
// young区初始值的大小
NewSize                  = 86507520 (82.5MB)
// 最大的young区大小
MaxNewSize               = 1386217472 (1322.0MB)
// old区初始值的大小
  OldSize                  = 173539328 (165.5MB)
// young区和old区的比例
  NewRatio                 = 2
// eden和一个s区的比例
   SurvivorRatio            = 8
   // 元空间  常量池 方法区
MetaspaceSize            = 21807104 (20.796875MB)
// 专门用来存class相关的内容,比如method 常量池
CompressedClassSpaceSize = 1073741824 (1024.0MB)
// 最大元空间
MaxMetaspaceSize         = 17592186044415 MB
// 使用G1内存的时候才会有
  G1HeapRegionSize         = 0 (0.0MB)
Heap Usage:
PS Young Generation // 并行的Young GC
Eden Space:
capacity = 503316480 (480.0MB)
used     = 300368000 (286.4532470703125MB)
free     = 202948480 (193.5467529296875MB)
59.6777598063151% used
From Space:
capacity = 97517568 (93.0MB)
used     = 19497040 (18.593826293945312MB)
free     = 78020528 (74.40617370605469MB)
19.99336160639281% used
To Space:
capacity = 98041856 (93.5MB)
used     = 0 (0.0MB)
free     = 98041856 (93.5MB)
0.0% used
PS Old Generation
capacity = 481296384 (459.0MB)
used     = 233442288 (222.62791442871094MB)
free     = 247854096 (236.37208557128906MB)
48.50281360102635% used
33874 interned Strings occupying 3772096 bytes.
```
# 
# 03 jstat -gc pid



![图片](https://uploader.shimo.im/f/Z191NbgZmfN6SIAC.png!thumbnail)


分析结果:

1. 首先我们这个系统使用的GC工具是并行GC,也就是默认的GC,这个GC的特点就是并发的去进行垃圾回收,GC的效率是比较高的,但是有一个问题就是会有延迟,当进行GC的时候,会STW,但是不进行GC的时候,GC线程不会和业务线程抢占CPU时间,所以吞吐量是可以的.我们这个系统是一个内部系统,所以对吞吐量的要求不是很高,但是在查看一些业务数据的时候,希望能够反映快一点,所以使用CMS GC 或是 G1 GC比较好

CMS GC 和 G1 GC 我们选择哪一个呢?

CMS只能用于老年代的收集,标记清除算法的.CMS要和PSnew进行配合使用,但是PSnew还是会STW

G1 GC 可以控制每次暂停的毫秒数,并且适用于大内存.是对CMS GC的优化

如果内存大于4G,算是比较大,用G1性价比高.我们的系统设置的堆最大是4G,就目前来看,用不到4G.

可以优化为使用G1 GC的方式

1. 堆内存的参数设置

-XX:+HeapDumpOnOutOfMemoryError  OOM之时导出堆镜像到文件

-XX:OnOutOfMemoryError 在OOM时，执行一个脚本

-XX:+HeapDumpPath  导出OOM文件的路径设置

nohup java -jar -Xms1500m -Xmx1500m-XX:+UseG1GC  -XX:MaxGCPauseMillis-XX:+HeapDumpOnOutOfMemoryError


优化之后

```plain
Attaching to process ID 107990, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.92-b14

using thread-local object allocation.
Garbage-First (G1) GC with 4 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 40
   MaxHeapFreeRatio         = 70
   MaxHeapSize              = 2097152000 (2000.0MB)
   NewSize                  = 1363144 (1.2999954223632812MB)
   MaxNewSize               = 1258291200 (1200.0MB)
   OldSize                  = 5452592 (5.1999969482421875MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 1048576 (1.0MB)

Heap Usage:
G1 Heap:
   regions  = 2000
   capacity = 2097152000 (2000.0MB)
   used     = 571473904 (544.9999847412109MB)
   free     = 1525678096 (1455.000015258789MB)
   27.249999237060546% used
G1 Young Generation:
Eden Space:
   regions  = 378
   capacity = 683671552 (652.0MB)
   used     = 396361728 (378.0MB)
   free     = 287309824 (274.0MB)
   57.97546012269939% used
Survivor Space:
   regions  = 3
   capacity = 3145728 (3.0MB)
   used     = 3145728 (3.0MB)
   free     = 0 (0.0MB)
   100.0% used
G1 Old Generation:
   regions  = 164
   capacity = 1410334720 (1345.0MB)
   used     = 170917872 (162.99998474121094MB)
   free     = 1239416848 (1182.000015258789MB)
   12.118957973324234% used
```
目前的整体运行情况是heap利用率仍然不高,看一下后续的运行情况.

![图片](https://uploader.shimo.im/f/KCWoB7goZ7EJzNCn.png!thumbnail)

刚跑了一会,就发现GC了36次,应该是GC的频率提高了.

后续继续观察和对比,希望后面的课程能够提供一些性能测试方面的工具.



