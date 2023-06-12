####  自定义 View 布局过程

布局过程就是把界面中的所有控件，用它们正确的大小摆放在正确的位置这样一个过程。也就是把你的 xml 布局文件里面的那些控件，把它们通过计算，来得出它们的实际尺寸值和位置值这样一个过程。

一般情况下，是不需要关心这个过程的，因为所有的自带控件，它的布局过程算法都已经写好了。



####  布局过程

包含两个过程，测量过程和布局过程

首先，界面会从最顶级的根 View 向下递归测量出每一级、每一个子 View 的尺寸和位置，

然后依然是从上到下，界面会从最顶级的根 View 向下递归把测量出每一级、每一个子 View 的尺寸和位置赋值给这些子 View ，

这样一个界面中每个 View 的尺寸和位置就都确定了，布局过程就算完成。

再接下来就是绘制过程了，也就是每一个 View 根据这些位置和尺寸来进行自我绘制。



#### 布局过程中具体每一个节点做了什么

也就是 View 和 ViewGroup 都做了什么，

当布局过程到来的时候，首先一个 View 的 measure() 方法会被它的父 View 调用，这个方法的作用是让这个 View 进行自我测量，不过真正进行自我测量的不是 measure() 方法，而是在它内部被它调用的 onMeasure() 方法。

这个 measure() 和 onMeasure() 的关系，measure() 是一个调度方法，它会做一些测量的预处理工作，然后去调用 onMeasure() 来进行真正的自我测量，这个自我测量包含呢？

分两种情况，它是 View 还是 ViewGroup



####  测量阶段

如果它就是一个 View，那么它做的事只有一件，计算出自己的尺寸。

如果它是一个 ViewGroup ，那么它会先调用它的所有子 View 的 measure() 方法，让它们都进行自我测量，然后根据这些 子 View 自我测量出的尺寸，来计算出它们的位置，并且把它们的尺寸和位置保存下来，同时它还会根据这些子 View 的尺寸和位置，最终得出自己的尺寸。

ViewGroup 的尺寸主要是由自己所有子 View 的尺寸和位置来确定的。



####  布局阶段

每一个 View 或 ViewGroup 也会被父 View 调用它的一个 layout() 方法，这个方法会对 View 进行内部布局，和 measure() 一样，layout() 也只是一个调度方法，它在内部会做两件事，首先 layout() 这个方法是有参数的，它的父 View 在调用它的时候，会把之前在测量阶段保存下来的 这个子 View 的尺寸和位置，通过参数传进来。

layout() 方法做的第一件事情，就是把传进来的这个尺寸和位置保存下来，测量阶段是父 View 统一保存所有子 View 的位置和尺寸，而到了布局阶段，就是子 View 自己来保存了。第二件事是，它会去调用自己的 onLayout() 方法，在这个 onLayout() 方法中，它会真正的对自己进行内部布局。

内部布局的意思，就是去调用它的每一个子 View 的 layout() 方法，并且把它们的位置和尺寸作为参数，给传进去让它们进行自我布局。



对于 View 来说 onLayout() 是什么也不用做的，因为它没有 子 View，实际上 View 的 onLayout() 方法就是一个空方法。

而对于 ViewGroup ，它就会真正地去调用每一个子 View 的 layout() 方法。



测量阶段 和 布局阶段 都是调用一个递归方法的过程。



####  布局过程的自定义

就是通过自定义 View 重写它的布局过程中的 测量阶段 和 布局阶段 的相关方法，以此来定制你想要的布局算法。不过需要注意的是，重写的是 onMeasure() 和 onLayout() 方法，因为 measure() 和 layout() 是用来调度的，真正做事的是在 onMeasure() 和 onLayout() 里面。

具体来说，这些重写可以分为三类，

第一类 重写 onMeasure() 来修改已有的 View 的尺寸，

第二类 重写 onMeasure() 来全新计算自定义 View 的尺寸，

第三类 重写 onMeasure() 和 onLayout() 来全新计算自定义 ViewGroup 的内部布局。



####  重写 onMeasure() 修改已有的 View 的尺寸

这一类是对一些已有的 View ，比如 ImageView ，它已经有了自己的尺寸算法了，它的 onMeasure() 方法已经正确的计算出它的尺寸了，不需要重新去计算一遍，只是根据自己的特殊需求，对它进行调整。

1. 重写 onMeasure()，并调用 super.onMeasure() 触发原先的测量，
2. 用 getMeasureWidth() 和 getMeasureHeight() 取到之前测得的尺寸，利用这两个尺寸来计算出最终尺寸，
3. 使用 setMeasuredDimension() 保存尺寸。

```java
public class SquareImageView extends ImageView {

		....
		 @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 先执行原测量算法
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取原先的测量结果
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        // 利用原先测量的结果计算得出新尺寸
        if (measuredWidth > measuredHeight) {
            measuredWidth = measuredHeight;
        } else {
            measuredHeight = measuredWidth;
        }

        // 保存结算后的结果
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
    ...
}
```



####  重写 onMeasure() 全新计算自定义 View 的尺寸

1. 重写 onMeasure() 把尺寸计算出来，

2. 把计算的结果用 resolveSize() 过滤一遍后保存。



#####  和重写 onMeasure() 修改已有 View 的尺寸的区别：

1. 不用调用 super.onMeasure() 方法，直接从头到尾都是自己来计算，

2. 在计算的同时，需要保证计算得出的那个尺寸满足 父 View 的限制。



#####  父 View 的限制是什么？

就是 onMeasure() 方法里面的两个参数，widthMeasureSpec，heightMeasureSpec .

这两个参数在父 View 调用 子 View 的 measure() 的时候传进来的，然后在 measure() 调用 onMeasure() 的时候，又把这两个参数传给 onMeasure() ，这两个参数包含了 父 View 对子 View 的尺寸限制，一个是宽度限制，一个是高度限制。

子 View 在计算自己尺寸的时候，需要遵循这两个参数所包含的限制。

关于这个限制，有两个问题，

1. 这个限制是怎么来的？
2. 子 View 的 onMeasure() 应该怎么做来让自己符合这个限制？



#####  限制是怎么来的？

其实，父 View 对 子 View 的限制，它就是 父 View 把开发者对 子 View 尺寸要求 进行处理计算之后，所得到的更精确的要求（开发者的要求是，这个 View 它在布局文件中的 layout_ 打头的属性，它们是用来设置 View 的位置和尺寸的，比如 layout_width，layout_height）。



开发者对 子 View 的尺寸要求，在经过父 View 处理之后所得到的那个 子 View 的尺寸限制，有三种

第一种  (UNSPECIFIED) 是随便你，你想要多大有多大，我不管你，没有限制，

第二种  (AT_MOST) 是给你一个上限，就是你自己怎么计算尺寸我不管，但是不要超过这个上限，

第三种  (EXACTLY) 给你一个固定值，你必须把自己计算成这个值。（这个看着有点矛盾，你已经给我固定死了，还让我自己计算，这个其实是为了流程的统一）



如果子 View 在计算的时候，不遵守父 View 的限制，如果这么做了，会发 bug ，子 View 在计算的时候，一定要严格遵守 父 View 的限制。



#####  子 View 应该怎么去遵循 父 View 的限制

很简单，调用一个 方法就行：resolveSize() ，在你计算完宽度和高度之后，分别调用一次 resolveSize() 方法，把你计算得出的宽度或高度，以及对应的父 View 的限制一起传进去，返回的结果就是符合 父 View 限制的修正之后的尺寸，然后把修正之后的尺寸用 setMeasureDimension() 保存起来，就行了。

```java
public class CustomeView extends SomeView {
		...
		@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ...

        measuredWidth = ...;
        measuredHeight = ...;

        measuredWidth = resolveSize(measuredWidth, widthMeasureSpec);
        measuredHeight = resolveSize(measuredHeight, heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
```



####  resolveSize()

首先它会把方法参数中的那个 父 View 的尺寸限制给拆开，这个尺寸限制是一个压缩数据，这个压缩数据包含了两个信息：这个限制的类型以及这个限制的尺寸值。

在拿到这个压缩数据后，需要先把它拆成类型和尺寸值这两个数据，然后才能分别对它们进行使用，怎么拆呢，调用两个方法，MeasureSpec.getMode() 和 MeasureSpec.getSize() ，它们能分别提取到这个限制的类型，也就是 mode 和 这个限制的尺寸，也就是 size .



####  重写 onMeasure() 和 onLayout() 来全新计算自定义 ViewGroup 的内部布局

1. 重写 onMeasure() 来计算内部布局，也就是子 View 的位置的尺寸，以及自己的尺寸
2. 重写 onLayout() 来摆放子 View ，根据之前计算的结果，来摆放 子 View



#####  重写 onMeasure() 来计算内部布局

onMeasure 的重写，对于 ViewGroup 包含三部分的内容

1. 调用每个 子 View 的 measure()，让 子 View 自我测量
2. 根据子 View 给出的尺寸，得出子 View 的位置，并保存它们的位置和尺寸
3. 根据子 View 的位置和尺寸计算出自己的尺寸，并用 setMeasureDimension() 保存



#####  调用每个 子 View 的 measure()，让 子 View 自我测量

这一步看起来简单，一个 for 循环，调用 measure() 方法不就完了吗，其实不是，这一步是三个步骤里面最关键的一步，也是最复杂的一步。

因为 measure() 这个方法里面有两个参数，这两个参数是 父 View 对子 View 尺寸限制，而这两个尺寸限制并不是现成的，它是需要你去计算出来的。

这两个宽度和高度的的尺寸限制，就是把开发者的要求，也就是这个子 View 在 xml 文件里面的那些 layout_ 打头的参数把 他们 和 Layout 或者说 这个 ViewGroup 它的剩余的可用空间，也就是你剩下多少宽度和高度给 子 View 去用，把这二者结合起来计算得到的。

这两个要求：开发者的要求和自己的可用空间，开发者的要求在地位上是要绝对高于可用空间的。



说完了前置条件，说一下具体算法

对于每一个 子 View，计算它的 MeasureSpec，也就是尺寸限制的时候，依次查看它们的 layout_width 和 layout_width 这两个属性，主要是这两个，具体的要根据 Layout 来定，例如 RelativeLayout 它的 layout_alignParentTop 和 layout_toRightOf 等等这些参数，也有可能影响对 子 View 尺寸限制的计算。

不过这些除了 layout_width 和 layout_height 之外的属性，它们的具体功能和用法都取决于 Layout 的作者，也就是你，你不增加这些属性那么就只需要看 layout_wdith 和 layout_height 。

对于每一个 子 View ，查看它们的 layout_width 和 layout_height ，分别用它们结合自己当前的可用宽度和可用高度，来计算出来 子 View 的限制。



这两个值如何查看呢？

xml 文件里面的 layout_width 和 layout_heigth ，在 Java 代码里会被转换为 View 的两个属性，你在 父 View 里调用 子 View 的 getLayoutParams() 方法，可以获得一个 LayoutParams 对象，它包含了 xml 文件里面的 layout_ 打头的参数的对应值，其中 它的 width 和 heigth 这两个属性就分别对应了 layout_width 和 layout_heigth 的值，而且是转换过了的值。

它们在 xml 里面如果是 wrap_content 和 或者 match_parent ，就会被分别转换成 WRAP_CONTENT 和 MATCH_PARENT 这两个常量，而如果它们 在 xml 里面是具体的数值，是多少 dp 或者 sp ，那么 width 和 height 这两个属性里面就是它们被转换后的具体的像素值。



#####  结合自己的可用空间来计算对 子 View 的宽度和高度限制

可以根据 layout_width 或者 layout_height 的值，分成三种情况：

第一种 情况是固定值，也就是多少 dp 或者 sp 的，这个简单，因为开发者 已经 有了死的要求，把精确尺寸给出来了，那就不需要考虑什么可用空间的问题了，直接用 EXACTLY 把 子 View 尺寸限制为这个固定值就可以了。

（比如这个 width 取出来是 200 ，那就直接给予 View 限制成 mode 为 EXACTLY，size 为 200，压缩成一个 MeasureSpec 就可以了，压缩的方式是使用 MeasureSpec 这个类的一个静态方法 makeMeasure(size, mode) 把 size 和 mode 分别传进去，返回值就是压缩后的尺寸限制。）



第二种 情况是 match_parent，填满父控件，以为是填满，所以原则上也 就是把 View 尺寸限制为固定值，这个值就是自己的可用宽度或者 可用高度，但！自己的可用宽度或高度怎么获得？

这个可用 空间，是从自己的 onMeasure() 方法的两个参数，也就是自己的宽度和高度限制里面获得的，onMeasure() 里面的那两个 MeasureSpec ，虽然只是一份限制，不能直接决定自己的尺寸，但依据这份限制，自己可以得到一个可用空间，我还没算出来自己多大，但是我可以知道，我最多能有多大地方去给自己和 子 View 用，这个最多是多少，要看 MeasureSpec 的 mode .

根据自己 的 MeasureSpec 中 mode 的不同：

1. EXACTLY:

   可用空间：MeasureSpec 中的 size

2. AT_MOST:

   可用空间：MeasureSpec 中的 size

3. UNSPECIFIED:

   可用空间：无限大

假如 mode 是 EXACTLY 或者 AT_MOST，初始 可用空间就是自己的 MeasureSpec 的 size，那么 子 View 就应该被固定为这个可用空间的大小，也就是 MeasureSpec 的 mode 等于 EXACTLY，size 等于你的可用空间。（注意这个可用空间是会变的，它的初始值是自己的 MeasureSpec 的 size，但随着你测量的子 View 越来越多，它可能会被挤得越来越小。）

而对于另一种情况，如果 mode 是 UNSPECIFIED，那就不一样了，这里会出现一个矛盾：开发者要求填满父控件，而父控件的可用空间是无限大，填满无限大这明显不现实，那就把这个 UNSPECIFIED 传下去就好，给子 View 也传一个 UNSPECIFIED，开发者要你填满我的可用空间，而我的可用空间无限大。



第三种  情况是 wrap_content，让子 View 自适应，或者说让 子 View 自己测量，但有一个限制条件，不能超过 父 View 的边界，或者说，要在 父 View 的可用空间之内，所以对应 wrap_content，也要根据自己的 onMeasure() 方法里面的 MeasureSpec 的 mode 来把可用空间分成两种情况，有上限和没上限两种。

如果是 mode 是 EXACTLY 或者 AT_MOST，那么表示这个可用空间是有上限的，它的初始值就是自己的 MeasureSpec 里面的 size ，然后在对一个个子 View 的测量过程中有可能会改变，给子 View 的 mode 是 AT_MOST，以为 wrap_content 原则上还是让 子 View 自己去测量的，只是要求它不能超过你的可用空间而已，所以 mode 应该是 AT_MOST。

而如果自己的 mode 是 UNSPECIFIED，可用空间无上限。

```java
public class SomeLayout extends ViewGroup {

	.....
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            ViewGroup.LayoutParams lp = childView.getLayoutParams();
            int selfWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int selfWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            switch (lp.width) {
                case MATCH_PARENT:
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                        childWidthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize, MeasureSpec.EXACTLY);
                    } else {
                        childWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    }
                    break;
                case WRAP_CONTENT:
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                        childWidthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize - usedWidth, MeasureSpec.AT_MOST);
                    } else {
                        childWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    }
                    break;
                default:
                    childWidthSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
                    break;
            }
        }
    }
    .....
}
```









