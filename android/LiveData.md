####  LiveData

LiveData 天生是粘性事件的，所谓粘性事件的意思就是，LiveData 在 observer 之后，仍然可以收到在没有绑定之前数据发生的变化。也就是说，一旦 LiveData 中持有数据，那么在 观察者 订阅 LiveData 时，会接收到最后一次的数据推送。



####  使用 ViewModel + LiveData 处理页面间通信的好处

1. LiveData 内部对页面生命周期有很好的处理，在页面销毁处于销毁的状态时，对于数据的变化不会作出处理，保证订阅者回调生命周期安全。

2. 事件的观察者必须是页面，对页面的消息推送，不能在其他地方 能够监听和收到不可预期的推送。



#####  LiveData 存在前的混沌世界

在 LiveData 面市前，我们分发状态，多是通过 EventBus 或 Java Interface 来完成的。不管你是用于网络请求回调的情况，还是跨页面通信的情况。

那这造成了什么问题呢？首先，EventBus 只是纯粹的 Bus，它 **缺乏上述提到的 标准化开发理念 的约束，在使用这个框架时，容易因 去中心化 地滥用，而造成 诸如 毫无防备地收到 预期外的 不明来源的推送、拿到过时的数据 及 事件源追溯复杂度 为 n² 的局面**。

并且，**EventBus 本身缺乏 Lifecycle 的加持，存在生命周期管理的一致性问题。这是 EventBus 的硬伤。**



##### LiveData 如何解决问题

首先，**LiveData 是在 Google 希望确立 标准化、规范化 的开发模式 —— 这样一种背景下诞生的**，因而为了达成这个艰巨的 **使命**，LiveData 被十分克制地设计为，**仅支持状态的输入和监听，并且可基于 “访问权限控制” 来实现 “读写分离”**。

**这使得任何一次数据推送，都可被限制为 “只能单方面地从唯一可信源推送而来”，从而避免了消息同步不一致、不可靠、或是在事件追溯复杂度为 n² 的迷宫中白费时间。**（也即，无论是从哪个视图控制器发起的 对某个共享状态改变的请求，状态最终的改变 都由 作为唯一可信源的 单例或 SharedViewModel **在其内部统一决策，并一对多地通知改变**）。

并且，这种承上启下的方式，使得单向依赖成为可能：单例无需通过 Java Interface 回调通知视图控制器，从而规避了视图控制器 被生命周期更长的单例 依赖 所埋下的内存泄漏的隐患。



























