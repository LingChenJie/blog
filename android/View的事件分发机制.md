### View的事件分发机制
在介绍NestedScrollingParent 和 NestedScrollingChild 嵌套滑动之前，先介绍下View的事件分发机制，因为NestedScrollingParent 和 NestedScrollingChild 主要是很好地解决了View滑动冲突问题。

一次点击事件由三个重要的方法来共同完成：dispatchTouchEvent、onInterceptTouchEvent、onTouchEvent。

dispatchTouchEvent：进行事件分发。如果事件能够传递到当前View，那么此方法一定会被调用，返回结果受当前View的onTouchEvent和下级View的dispatchTouchEvent方法的影响。方法返回结果表示是否消耗当前事件，true表示消耗，false表示不消耗。

onInterceptTouchEvent：进行事件拦截，在方法内部调用，在ViewGroup中的dispatchTouchEvent方法内部中调用，View并没有onInterceptTouchEvent方法。如果View拦截了某个事件，在同一个事件序列中，此方法不会被再次调用。方法返回结果表示是否拦截当前事件，true表示拦截，false表示不拦截。

onTouchEvent：进行事件处理，在dispatchTouchEvent方法中调用。如果View不消耗这个事件，在同一事件序列中，当前View无法再次接受到事件。方法返回结果表示是否消耗当前事件，true表示消耗，false表示不消耗。

这三个方法的关系：

```
public boolean dispatchTouchEvent(MotionEvent ev){
    boolean consume = false;
    if (onInterceptTouchEvent(ev)){
        consume =  onTouchEvnet(ev);
    } else{
        consume = child.dispatchTouchEvent(ev);
    }
    return consume;
}
```



上面表现出了事件的传递规则：一次点击事件产生后，传递的顺序是：Activity → Window → View，首先会传递给Activity，Activity再传递给Window，最后Window传递给顶级View，顶级View接受事件后，就会进行事件分发。顶级View是一个ViewGroup，首先调用它的dispatchTouchEvent，如果这个ViewGroup的onInterceptTouchEvent方法返回true，就会交给这个ViewGroup的onTouchEvent方法处理；如果这个ViewGroup的onInterceptTouchEvent方法返回false，就会交给这个ViewGroup的子View的dispatchTouchEvent方法处理，如此反复直到事件被最终处理。


