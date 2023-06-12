#### Activity 的 Launch Mode

#####  启动模式的作用

默认情况下，我们多次启动同一个 Activity ，系统会创建多个实例并把他们一一放入任务栈中，这种做法显得有点不明智，Android 在设计的时候就发现了这个问题，所以就提供了启动模式来修改系统的默认行为。



#####  standard

标准模式

创建 Activity 的实例，并 **添加到启动它的源 Activity 所在任务栈的栈顶**。在这种模式下，谁启动了这个 Activity，那么这个 Activity 就运行在它那个 Activity 所在的栈中。



#####  singleTop

栈顶复用模式

当 启动它的**源 Activity 所在的任务栈的 栈顶已存在该 Activity 的实例**，那么不创建该 Activity 的新实例，而是走该 Activity 实例的 onNewIntent 回调，注入新的 intent，并执行 onResume（也就是不走 onCreate、onStart）.

否则就在栈顶创建一个新的实例。



#####  singleTask

栈内复用模式

当 **该 Activity 所属的任务栈中已存在该 Activity 的实例**，那么不创建该 Activity 的新实例，而是 首先将该任务中 **该 Activity 实例之上的 Activity 全都出栈**，并且走该 Activity 实例的 onNewIntent 回调，注入新的 intent，并执行 onResume.



#####  singleInstance

单例模式

会新建一个任务栈，并且 **独享这个任务**，也即 **整个系统 有且只有这么一个 Activity 的实例**，多个 App 可共享该实例。



####  如何给 Activity 设置启动模式

通常有两种方式：

1. 在清单文件中，静态的为 Activity 指定启动模式

```xml
<activity
            android:name=".BActivity_SingleTop"
            android:launchMode="singleTop" />
```



2. 在代码中，通过为 Intent setFlag 的方式，动态的为 Activity 指定启动模式

```java
Intent i = new Intent(getApplicationContext(), TestActivity.class);
i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(i);
```

这两种方式都可以为 Activity 指定启动模式，二者是有区别的。优先级上，第二种方式的优先级高于第一种。



####  常见的 FLAG 

#####  FLAG_ACTIVITY_NEW_TASK

近似于 singleTask 模式。当在清单中为 Activity 设置 taskAffinity 属性时，能跳转到指定任务（若先前不存在该任务，则先创建该任务）。该 FLAG 通常用于从非 Activity 的环境下启动 Activity（这么设计，是为了给 Activity 一个容身之处）.



#####  FLAG_ACTIVITY_SINGLE_TOP

对应着 singleTop 模式



#####  FLAG_ACTIVITY_CLEAR_TOP

近似 singleTask 模式。当该 Activity 已存在于任务中，该 Activity 之上的 Activity 都会出栈，并且该 Activity 如为 standard ，则会被重新创建，如为 singleTop ，则是走 onNewIntent.



#####  FLAG_ACTIVITY_NO_HISTORY

被指定的 Activity 在跳转到其他 Activity 后，将从任务中移除



#####  FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS

被指定的 Activity 不出现在 “最近应用” 列表中



####  Activity 如何指定和哪个任务关联

在清单文件中为该 Activity 设置 taskAffinity 属性

```xml
<activity
            android:name=".CActivity_SingleTask"
            android:launchMode="singleTask"
            android:taskAffinity="com.chen.task2"/>
```



默认情况下，APP 的任务名称为 包名，因而我们这里为 taskAffinity 设置一个不一样的属性值，例如 “com.chen.task2”，来指定与该任务关联。如果返回栈中不存在该任务，则会新建一个该任务。

taskAffinity 属性不对 standard 和 singleTop 模式有任何影响，即时你指定了该属性为其他不同的值，这两种启动模式下不会创建新的task。



####  allowTaskReparenting 

清单文件中，Activity 的属性 allowTaskReparenting = true 意味着该 Activity 可以从一个任务迁移（回）到 taskAffinity 指定的任务。

**该属性的存在，主要是为了支持 DeepLink 的使用场景**：

例如你通过 浏览器链接 启动了 知乎 的 allowTaskReparenting = true 的 Activity，那么此时该 Activity 处于 浏览器 的任务中；当通过 Home 键让 浏览器任务脱离焦点，并从桌面启动知乎时，由于该 Activity 被指定的 taskAffinity 值与 知乎 的任务一致，因而该 Activity 又会被迁移回 知乎 的任务中继续展示。



####  任务清空或保留 Activity的几种方式

如果用户将任务切到后台，过了很长一段时间，系统会将这个任务中除了最底层的那个 Activity 之外的其它所有 Activity 全部清除。

当用户重新回到这个任务时，最底层的那个 Activity 将得到恢复。

因为过了这么长时间，用户可能早忘了自己当初做了什么，所以一切从新。



那么除了系统的这一默认设计，还有什么别的策略呢？

#####  **alwaysRetainTaskState**

如果在清单文件中，将最底层的那个 Activity 的这个属性设置为 true，那么上面所描述的默认行为就将不会发生，任务中所有的Activity即使过了很长一段时间之后仍然会被继续保留。



#####  **clearTaskOnLaunch**

如果将最底层的那个 Activity 的这个属性设置为 true，那么只要用户离开了当前任务，再次返回的时候就会将最底层 Activity 之上的所有其它 Activity 全部清除掉。



#####  **finishOnTaskLaunch**

这个属性和 clearTaskOnLaunch 是比较类似的，不过它不是作用于整个任务上的，而是作用于单个 Activity 上。如果某个 Activity 将这个属性设置成 true，那么用户一旦离开了当前任务，再次返回时这个 Activity 就会被清除掉。





























