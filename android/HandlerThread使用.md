####  HandlerThread

HandlerThread 是 Thread 的一个子类

HandlerThread 自带的 Lopper 可以使它通过消息队列来重复使用当前线程，每一个任务都将以队列的方式逐个被执行。



如何使用：

```java
HandlerThread thread = new HandlerThread("MyHandlerThread");
thread.start();
mHandler = new Handler(thread.getLooper());
mHandler.post(new Runnable(){...});
```

