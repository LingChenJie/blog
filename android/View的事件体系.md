View 的位置参数

top、left、right、bottom

3.0 之后增加 x、y、translationX、translastionY

x、y、translationX、translastionY 默认值为 0

View 在平移过程中 top、left 不会发生变化，表示原始左上角的位置信息



TouchSlop 

手指在屏幕滑动距离小于这个值，系统认为没有滑动，大于这个值认为在滑动

获取这个值: ViewConfiguration.get(getContext()).getScaledTouchSlop();



VelocityTracker

速度追踪器

用于追踪滑动过程的速度

在 View 的 onTouch 方法中追踪当前点击事件的速度

```
VelocityTracker velocityTracker = VelocityTracker.obtain();
velocityTracker.addMovement(envent);
```

获取当前的速度

```
velocityTracker.computeCurrentVelocity(1000);
float xVelocity = velocityTracker.getXVelocity();
float yVelocity = velocityTracker.getYVelocity();
```

computeCurrentVelocity 这个方法表示的是一个事件单位内，单位是 ms

不需要的时候需要释放

```
velocityTracker.clear();
velocityTracker.recycle();
```



GestureDetector

手势检测，辅助检测用户单击，滑动，长按，双击



Scroller

弹性滑动对象

当使用 View 的 scrollTo/scrollBy 滑动，体验不好

Scroller 本身无法让 View 弹性滑动，需要和 View 的 computeScroll 结合使用

```java
Scroller scroller = new Scroller(getContext());

	  // 平滑到指定位置
    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // 1000ms 内滑完
        scroller.startScroll(scrollX, 0, delta, 0, 1000);
    }
    
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrY(), scroller.getCurrY());
            postInvalidate();
        }
    }
```



View的滑动

scrollTo/scrollBy

滑动过程 mScrollX/mScrollY变化情况

当  View内容 的左边缘 在 View视图 的左边缘 的左边时，mScrollX 为正值

当 View内容 的上边缘 在 View视图 的上边缘 的上边时，mScrollY 为正值

为啥是这样，把View想象成一把比较长刻度尺，向右滑动，刻度增大

缺点：只能滑动 View 的内容，不能滑动 View 视图本身



采用动画

使用动画操作View，主要是改变View的translationX/translationY的值

```
ObjectAnimator.ofFloat(targetView, "translationX", 0,  100)
        .setDuration(100)
        .start();
```

在 3.0 以上采用没有问题，在3.0 以下也不能改变 View视图本身，现在基本上不用在3.0以下了



改变布局参数

通过改变 LayoutParams 的方式去实现 View 的滑动同样是一种灵活的方式



View的事件分发

点击事件的事件分发，其实就是对 MotionEvent 事件的分发过程

```java
public boolean dispatchTouchEvent(MotionEvent ev) {
    boolean consume = false;
    if (onInterceptTouchEvent(ev)) {
        consume = onTouchEvent(ev);
    } else {
        consume = chlid.dispatchTouchEvent(ev);
    }
    return consume;
}
```

当一个点击事件产生后，它的传递顺序：Activity -> Window -> View

事件分发的过程是由外向内的，先传递给父View，再分发给子View

通过requestDisallowInterceptTouchEnvet方法可以在子View中干预父View的分发过程，但是ACTION_DOWN事件除外



事件拦截

如果一个 View 的 onTouchEvent 返回 false，表示不拦截该事件，那么父View就会被调用 onTouchEvent

手指按下到抬起为一个事件序列，一个事件序列只能被一个 View 消费掉

若一个View拦截了个事件，那么在该事件序列中，事件序列中的其他的方法都会交给它来处理。并且后续不会在调用它的onInterceptTouchEvent方法



ViewGroup的onInterceptTouchEvent默认返回false，即交给子View进行分发事件

View没有onInterceptTouchEvnet方法，一旦有点击事件就会触发onTouchEvent方法



事件分发源码解析

点一个点击事件触发时，事件最先传递到当前的Activity，由Actvity的dispatchTouchEvent进行分发

具体的工作是由Activity内部的Window完成的

```
Activity#dispatchTouchEvent
	
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        onUserInteraction();
    }
    if (getWindow().superDispatchTouchEvent(ev)) {
        return true;
    }
    return onTouchEvent(ev);
}
```

Window会将事件传递给decor view，decor view一般就是当前页面的底层容器(通过Activity.getWindow.getDecorView()可以获得)



Window是一个抽象类，它的实现类是PhoneWindow

```
PhoneWindow#superDispatchKeyEvent

public boolean superDispatchKeyEvent(KeyEvent event) {
    return mDecor.superDispatchKeyEvent(event);
}
```

PhoneWindow将事件传递给了DecorView

这个DecorView是getWindow().getDecorView返回的View，我们通过setContentView设置的View是它的一个子View



ViewGroup对事件的分发过程

```
ViewGroup#dispatchTouchEvent

// Check for interception.
final boolean intercepted;
if (actionMasked == MotionEvent.ACTION_DOWN
        || mFirstTouchTarget != null) {
    final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
    if (!disallowIntercept) {
        intercepted = onInterceptTouchEvent(ev);
        ev.setAction(action); // restore action in case it was changed
    } else {
        intercepted = false;
    }
} else {
    // There are no touch targets and this action is not an initial down
    // so this view group continues to intercept touches.
    intercepted = true;
}
```

当事件由子View处理时，mFristTouchTargetView就会指向该View

当事件由ViewGroup的子View成功处理时，mFristTouchTargetView!=null成立

当事件由ViewGroup自己处理拦截时，mFristTouchTargetView!=null不成立，那么当Action_MOVE和Action_UP事件到来时，ViewGroup的onInterceptTouchEvent不会被调用。

当然还有一种特殊情况，FLAG_DISALLOW_INTERCEPT这个标识位，这个标志位是通过子View的requestDisallowInterceptTouchEvent设置的，FLAG_DISALLOW_INTERCEPT一旦设置后，ViewGroup将无法拦截除Action_DOWN以外的点击事件，因为在Action_DOWN中会重置FLAG_DISALLOW_INTERCEPT标志位。



FLAG_DISALLOW_INTERCEPT这个标志位为了让ViewGroup不拦截事件，前提是ViewGroup没有在Action_DWON的时候进行拦截事件



View的滑动冲突

内外两层布局都可以滑动，就产生了滑动冲突



View的绘制流程

View 的绘制流程是从 ViewRoot 的 performTraversals 方法开始，经过了 measure, layout 和 draw

measure 过程决定了决定了 View 的宽高，measure 完成后，可通过 getMeasuredWidth 和 getMeasuredHeight 获取到 View 测量后的宽/高

layout 过程决定了 View 的四个顶点坐标和实际 View 的宽/高

draw 过程决定了 View 的显示，只有 draw 方法完成以后 View 的内容才能呈现在屏幕上



DecorView 作为一个顶级 View，内部包含一个竖直方法的 LinearLayout



MeasureSpec

MeasureSpec 通过 将 SpecMode 和 SpecSize 打包成一个 int 值来避免过多的对象分配

UNSPECIFIED

父容器不对 View 有任何限制，要多大有多大

EXACTLY

父容器已经检测出 View 所需要的精确大小，这个时候 View 的最终大小就是 SpecSize 所指定的值。

对应于 LayoutParams 中的  match_parent 和 具体的数值这两种模式

AT_MOST

父容器指定了一个可用大小即 SpecSize，View 的大小不能大于这个值，对应 LayoutParams 中的 wrap_content





























