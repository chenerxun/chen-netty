总结:
==
> IO(BIO) :Block IO 同步阻塞 每次只能干一件事情，要么读那么写
 
>NIO:Non-Block IO 同步非阻塞（可以使用线程实现异步）
   
>AIO：Ansync IO 异步非阻塞 (事件驱动：等数据有返回状态通过事件回调)
   
>1.4 以前都是BIO
   
>1.4 之后 NIO出现，IO性能得到大幅度提升(对性能要求) Netty 默认用的NIO
   
>1.7 NIO(NIO2--->AIO) 操作系统的性能，决定的IO的性能(兼容问题)
  
