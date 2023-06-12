app

Application中定义一个ViewModelStore作为全局的Callback-ViewModel

Callback-ViewModel的职责 是用于 跨页面通信 的场景下，承担唯一的可信源，

所有的跨页面通讯都交由该可信源在内部决策和处理，并统一分发给所有订阅者页面。



data

实体对象创建，以及网络数据或本地数据的获取/存储操作

repository数据仓库用户管理数据的处理，在这一层中数据的回调不通过LiveData返回结果，

因为有的时候并不一定是页面获取数据，也有可能是其他组件来获取数据。



domain

数据层，用于数据的处理

​	request

​		一个项目中通常存在多个request，每个页面配备的state-ViewModel可能持有多个request

​		request的职责是对数据的转发，

​		返回的对象使用LiveData包装，达成 唯一可信源，实现读写分离，从而可以保证消息分发的一致性和可靠性

​		同时，为了方便语义上的理解，将DataResult作为LiveData的value回推给UI层，

​		又方便了UI层基于DataResult的reponseStatus来分别处理请求成功或请求失败 情况下的UI表现

​		

​		对于需要叫停的情况，可以在vm和数据层插入一个UseCase，来专门负责可叫停的情况

​		除了开闭原则，使用UseCase还有个考虑是避免内存泄漏



ui

state(viewmodel)

​	每个页面都需要单独准备一个ViewModel来托管DataBinding绑定的临时状态

​	以及视图控制器重建时状态的恢复

​	此外ViewModel的职责只用于数据状态的托管，不建议在此处理UI逻辑

​	UI逻辑的处理适合在Activity/Fragment等视图控制器中完成，是数据驱动的一部分



page(Fragment)

​	每个页面都需要单独配备一个state-ViewModel，职责仅限于 状态托管和恢复，（有点类似于Vue里data中的数据）	

​	如果vm数据需要和xml进行关联，需要进行数据绑定，通过DataBindingUtils完成绑定

​		DataBindingUtils.inflate关联布局文件获取ViewDataBinding，通过setVariable添加需要绑定的对象或属性

​		

​		

架构

模块化核心原则单一设计原则，职责越单一复用性就越强

基于 功能特性、业务特性 做模块化划分

​	功能特性比如说 网络、图片加载 

​	业务特性就是编写的业务逻辑

​	业务特性 优先级高于 功能特性

如何分层

​	数据 和 视图 是两个不同的概念

​	为了提高复用性以及可维护性，应该根据 单一设计原则 将两者分层处理

​	无论 MVC MVP MVVM 核心点都是将 数据 与 视图 进行分层

Data Mapper

​	适当引入 Data Mapper，将后端数据转换为 本地模型，本地模型与 设计图对应，将 后端业务 与 视图 隔离

业务逻辑

​	在 MVVM 模式下，将业务逻辑放到 ViewModel 处理，如果一个界面足够复杂

​	，对应的 ViewModel 代码可能很多，就会出现代码臃肿

​	对于复杂的业务逻辑，建议单独写一个 use case 处理

​	use case 通常放在 ViewModel/Presenter 与 数据层之间，业务逻辑以及 Data Mapper 放在 use case 中

数据驱动UI

​	使用 DataBinding 实现，

​	ViewModel 从 Repository 拿到数据暂存到 ViewModel 对应的 ObservableField 即可实现数据驱动 UI

​	当前提是从 Repository 拿到的数据可以直接用，如果在 Activity 或 Adapter 做数据二次处理在 notify UI

​	，就违背了数据驱动 UI 的核心思想

引入Diff

​	解决了 RecyclerView 需要实现 数据驱动UI 的性能问题

- **合理的分层可以提升复用性、降低模块间耦合性**
- **Data Mapper 可以让视图层脱离于后端进行开发**
- **复杂的业务逻辑应该写到use case中**
- **数据驱动UI的本质是控制反转**
- **通过函数式编程可以写出更加安全的代码**



Lifecycle 解决了生命周期同步问题

LiveData

​	观察者模式，扩展性强，耦合性低

​	是一个存储数据的容器，当容器数据发生变化时，会触发观察者，即数据驱动

LiveData + Lifecycle 实现 1+1>2

​	LiveData 在 Lifecycle 的加持下实现只在可见状态接收通知

​	，在 Activity 执行了 onStop 后内部的 LiveData 就无法接收到通知

ViewModel 与 LiveData 天作之合

​	MVVM ViewModel 是 MVVM 架构中的一个角色，是一种思想

​	Jetpack ViewModel 是一个实实在在的框架，用于状态托管，有对应的作用域可跟随 Activity/Fragment 生命周期

​	，这种特性恰好充当 MVVM ViewModel 的角色，分隔数据层和视图层并走数据托管

ViewModel

​	带作用域的状态托管框架，可以通过指定作用域和 Activity/Fragment共存亡

​	，Activity横竖屏切换时不会销毁对应的ViewModel，为的就是公用一个ViewModel，保证数据的一致性

​	数据的承载以及分发交给 LiveData，而ViewModel专注于托管LiveData保证不丢失

ViewModel担任VM/Presenter好处

​	规避多线程下的内存泄漏

​	内部的viewModelScope是一个协程的拓展函数，viewModelScope生命周期跟随ViewModel对应的

​	Lifecycle(Activty/Fragment)，当页面销毁时一并结束viewModelScope作用域

- **Lifecycle 解决了生命周期 同步问题**
- **LiveData 实现了真正的状态驱动**
- **ViewModel 可以让 Fragment 通讯变得更优雅**
- **DataBinding 让双向绑定成为了可能**
- **Jetpack 只是让 MVVM 更简单、更安全**































