####  Fragment历史

Fragment 是在 Android 3.0 引入的，最初是专门针对平板视图存在的。

在 Android 4.0 中，Fragmnet 升级为手机和平板都能使用。

Fragment 的存在，是 **专注于承担视图控制器的责任**，分担 Activity 的责任，让 Activity 更加专注于 “幕后协调者” 的工作。



####  Fragment 的使用

```java
getSupportFragmentManager().beginTransaction()
     .add(R.id.fragment_container, fragment, fragmentName)
     .addToBackStack(null)
     .commit();
```

通常我们会以如上的方式将一个 Fragment 以事务的方式添加到回退栈。



####  Fragment 生命周期

Fragment 的生命周期节点 比 Activity 的多了 onAttach、onCreateView、onViewCreated、onActivityCreated、onDestoryView、onDetech，少了 onRestart.

当 Fragment 被 replace 时，会走 onDestoryView 而不至于走 onDestory 完全被销毁，为的是销毁视图的同时，使 Fragment 的状态（包括 View 状态 及 Fragment 成员状态）得以保留，从而使当前内存空间得以被释放的同时、是下一次加载时又可直接走 onCreateView，比 “冷加载” 更快。



####  Fragment 的重建和状态管理与 Activity 的区别

Fragment 没有 onRestoreInstanceState 方法，因此如有需要，只能在 onCreate 到 onActivityCreated 之间恢复手动保存的成员状态。

为何如此设计呢？因为 Fragment 光生命周期的方法就够多了，并且跑过 Log 你就会发现，Fragment 从 onCreate 到 onActivityCreated，是在 Activity 走 onCreate 之后才走的，也因此，恰是恢复状态的最佳时机，无须额外再搞个什么 onRestoreInstanceState。



Activity 状态 = Fragment 状态 + View 状态 + 成员状态。

Fragment 状态 = View 状态 + 成员状态。

其中

- 成员状态需要我们自己在 onSaveInstanceState 中手动保存。
- 并且 Activity 的 super.onSaveInstanceState 除了保存 View 状态，还通过 FragmentController 保存所有 Fragment 的状态。



####  为何会导致 Fragment 重叠

Activity 的 super.onSaveInstanceState 会保存所有 Fragment 的状态，并在重建时，将 Fragment 重建和恢复状态。（具体可参见 FragmentActivity 的 onSaveInstanceState 和 onCreate 源码）

```java
protected void onCreate(@Nullable Bundle savedInstanceState) {
    ...

    if (savedInstanceState != null) {
        Parcelable p = savedInstanceState.getParcelable(FRAGMENTS_TAG);
        mFragments.restoreSaveState(p);
    ...
}
```

而正因为对这个状况没有事先的了解，而没有在自己的 Activity 中做出如下判断：

```java
if (savedInstanceState != null) {

    // 不动态新建 Fragment

} else {
    getSupportFragmentManager().beginTransaction()
      .add(R.id.fragment_container, fragment, fragmentName)
      .addToBackStack(null)
      .commit();
}
```



####  为何推荐使用 DialogFragment

最主要的原因是，相比于 Dialog，DialogFragment 可以跟随宿主完成重建和状态恢复。
（比如 Dialog 正在加载进度条，结果旋转屏幕后，Dialog 没有被重建和恢复状态，而是直接被关闭了，这就影响用户体验）

























