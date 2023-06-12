##### Jetpack ViewModel

ViewModel 的存在，主要是为了解决 状态管理 和 页面通信 的问题。

基于 **工厂模式**，使得 ViewModel **被 LifecycleOwner 所持有、通过 ViewModelProvider 来引用**，

所以 **它既类似于单例：**
—— 当被作为 LifecycleOwner 的 Activity 持有时，能够脱离 Activity 旗下 Fragment 的生命周期，从而实现作用域共享，

**实际上又不是单例：**
—— 生命周期跟随 作为 LifecycleOwner 的视图控制器，当 Owner（Activity 或 Fragment）被销毁时，它也被 clear。



此外，出于对视图控制器重建的考虑，Google 在视图控制器基类中通过 retain 机制对 ViewModel 进行了保留。

因此，对于 作用域共享 和 视图重建 的情况，状态因完好地被保留，而得以被视图控制器在恢复时直接使用。



#####  ViewModel 如何做到作用域可控

Google 是这么设计的：

**首先，ViewModel 的职责是专门管理状态**，尤其是那些 “重新获取的代价比较大” 的状态（例如通过网络请求到的 List 等数据，抛开 “费流量” 不说，https 请求本身涉及大量的加解密运算，耗费 CPU 资源，耗电显著）。

**并且，ViewModel 归根结底是被对应的 Activity/Fragment 持有**。只不过 ViewModel 和 Lifecycle 一样，通过模版方法模式封装得太好了，乃至于开发者感觉不到 它实际上与视图控制器的关系十分紧密，并不是表面看上去的 “那么独立” 和 “逍遥法外”。



其中，Activity 和 Fragment，是作为 ViewModelStoreOwner 的实现，每个 ViewModelStoreOwner 持有着唯一的一个 ViewModelStore，也即 **每个视图控制器都持有自己的一个 ViewModelStore**。

**ViewModelStore 的作用是维护一个 Map，来管理视图控制器所持有的所有 ViewModel**。

所以三者的关系是，ViewModelStoreOwner 持有 ViewModelStore 持有 ViewModel。



并且 **当作为 ViewModelStoreOwner 的视图控制器被 destory 时（重建的情况除外），ViewModelStore 会被通知去完成清除状态操作**，从而将 Map 中管理着的 ViewModel 全部走一遍 clear 方法，并且清空 Map。

clear 方法是 final 级，不可修改，clear 方法中包含 onClear 钩子，开发者可重写 onClear 方法来自定义数据的清空.



####  需要明确的3个细节

#####  1.传入不同的作用域，拿到的是毫不相干的两个 ViewModel 实例

如果被引用的 ViewModel 来自不同的 ViewModelStoreOwner，它俩实际上是两个 ViewModel 实例，而不是同一个，所以所持有的 LiveData 也不是同一个。



##### 2.在状态重建时，Activity 持有的 ViewModelStore 不会被销毁

因为视图控制器中存在某个叫 **“当环境变化时保留状态”** 的机制（onRetainNonConfigurationInstance，详见 ComponentActivity 源码），使得当重建时，ViewModelStore 被保留，而在重建后，恢复了 Activity 对该 ViewModelStore 的持有。



#####  3.只要不作，就能避免因中间层持有页面而导致的内存泄漏

最后，正由于 ViewModel 是个被精妙设计的 “单例”，使得视图控制器依赖它，它却不依赖于视图控制器，从而你只要不往 ViewModel 中塞视图控制器的 Context 等引用，就能避免一系列内存泄漏的隐患（例如当 Fragment 引用了 Activity 持有的 ViewModel 实例时）。



#####  ViewModel 的作用

1.让状态管理独立于视图控制器，从而做到重建状态的分治、状态在多页面的共享，以及跨页面通信。

2.为状态设置作用域，使状态的共享做到作用域可控。

3.实现单向依赖，避免内存泄漏。















