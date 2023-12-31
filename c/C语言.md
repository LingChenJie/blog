# C语言

## C语言初探

### 第一个C语言程序

```C
#include <stdio.h>
int main()
{
    printf("C语言学习");
    return 0;
}
```



### C语言标准：C89、C99和C11

#### C89 标准

为统一C语言版本，1983 年美国国家标准局（American National Standards Institute，简称 ANSI）成立了一个委员会，专门来制定C语言标准。1989 年C语言标准被批准，由于这个版本是 89 年完成制定的，因此也被称为 C89。

后来 ANSI 把这个标准提交到 ISO（国际化标准组织），1990 年被 ISO 采纳为国际标准，称为 ISO C。又因为这个版本是1990年发布的，因此也被称为 C90。

#### C89 标准

在 ANSI C 标准确立之后，C语言的规范在很长一段时间内都没有大的变动。1995 年C程序设计语言工作组对C语言进行了一些修改，增加了新的关键字，编写了新的库，取消了原有的限制，并于 1999 年形成新的标准——ISO/IEC 9899:1999 标准，通常被成为 C99.

但是这个时候的C语言编译器基本已经成熟，各个组织对 C99 的支持所表现出来的兴趣不同。当 GCC 和其它一些商业编译器支持 C99 的大部分特性的時候，微软和 Borland 却似乎对此不感兴趣，或者说没有足够的资源和动力来改进编译器，最终导致不同的编译器在部分语法上存在差异.

#### C11 标准

C11 标准由国际标准化组织（ISO）和国际电工委员会（IEC） 旗下的C语言标准委员会于 2011 年底正式发布，支持此标准的主流C语言编译器有 GCC、LLVM/Clang、Intel C++ Compile 等

- 增加了安全函数，例如 gets_s()、fopen_s() 等；
- 增加了 <threads.h> 头文件以支持多线程；
- 增加了 <uchar.h> 头文件以支持 Unicode 字符集；
- 以及其它一些细节。

2018 年，ISO/IEC 又发布了 C11 标准的修正版，称为 C17 或者 C18 标准。和 C11 标准相比，C17 并没有添加新的功能和语法特性，仅仅修正了 C11 标准中已知的一些缺陷。

截至到 2022 年 7 月份，新的 C2x 标准尚未发布，最新的 C 语言标准仍是 C17.



##  变量和数据类型

### 数据类型

数据是放在内存中的，变量是给这块内存起的名字，有了变量就可以找到并使用这份数据.

数据类型用来说明数据的类型，确定了数据的解释方式，让计算机和程序员不会产生歧义。在C语言中，有多种数据类型.

| 说  明   | 字符型 | 短整型 | 整型 | 长整型 | 单精度浮点型 | 双精度浮点型 | 无类型 |
| -------- | ------ | ------ | ---- | ------ | ------------ | ------------ | ------ |
| 数据类型 | char   | short  | int  | long   | float        | double       | void   |

#### 数据的长度

| 说  明   | 字符型 | 短整型 | 整型 | 长整型 | 单精度浮点型 | 双精度浮点型 |
| -------- | ------ | ------ | ---- | ------ | ------------ | ------------ |
| 数据类型 | char   | short  | int  | long   | float        | double       |
| 长  度   | 1      | 2      | 4    | 4      | 4            | 8            |

#### 输出各种类型的数据

我们可以使用 puts 来输出字符串。puts 是 output string 的缩写，只能用来输出字符串。

printf 比 puts 更加强大，不仅可以输出字符串，还可以输出整数、小数、单个字符等，并且输出格式也可以自己定义

```c
int a=100;
int b=200;
int c=300;
printf("a=%d, b=%d, c=%d", a, b, c);
```

`%d`称为格式控制符，它指明了以何种形式输出数据。格式控制符均以`%`开头，后跟其他字符。%d 表示以十进制形式输出一个整数。除了 %d，printf 支持更多的格式控制，例如：

- %c：输出一个字符。c 是 character 的简写。
- %s：输出一个字符串。s 是 string 的简写。
- %f：输出一个小数。f 是 float 的简写

```c
#include <stdio.h>
int main()
{
    int n = 100;
    char c = '@';  //字符用单引号包围，字符串用双引号包围
    float money = 93.96;
    printf("n=%d, c=%c, money=%f\n", n, c, money);
    return 0;
}
```

#### sizeof 操作符

获取某个数据类型的长度可以使用 sizeof 操作符

```c
#include <stdio.h>
int main()
{
    short a = 10;
    int b = 100;
   
    int short_length = sizeof a;
    int int_length = sizeof(b);
    int long_length = sizeof(long);
    int char_length = sizeof(char);
   
    printf("short=%d, int=%d, long=%d, char=%d\n", short_length, int_length, long_length, char_length);
   
    return 0;
}
```

在 32 位环境以及 Win64 环境下的运行结果为：

> short=2, int=4, long=4, char=1

在 64 位 Linux 和 Mac OS 下的运行结果为：

>  short=2, int=4, long=8, char=1

#### 不同整型的输出

- `%hd`用来输出 short int 类型，hd 是 short decimal 的简写；
- `%d`用来输出 int 类型，d 是 decimal 的简写；
- `%ld`用来输出 long int 类型，ld 是 long decimal 的简写。

```c
#include <stdio.h>
int main()
{
    short a = 10;
    int b = 100;
    long c = 9437;
    printf("a=%hd, b=%d, c=%ld\n", a, b, c);
    return 0;
}
```

#### 二进制数、八进制数和十六进制数

一个数字默认就是十进制的，表示一个十进制数字不需要任何特殊的格式。但是，表示一个二进制、八进制或者十六进制数字就不一样了，为了和十进制数字区分开来，需要在数字前面加前缀。

##### 二进制

二进制由 0 和 1 两个数字组成，使用时必须以`0b`或`0B`（不区分大小写）开头，例如

```c
//合法的二进制
int a = 0b101;  //换算成十进制为 5
int b = -0b110010;  //换算成十进制为 -50
int c = 0B100001;  //换算成十进制为 33
```

标准的C语言并不支持上面的二进制写法，只是有些编译器自己进行了扩展，才支持二进制数字。换句话说，并不是所有的编译器都支持二进制数字，只有一部分编译器支持，并且跟编译器的版本有关系。

##### 八进制

八进制由 0~7 八个数字组成，使用时必须以`0`开头（注意是数字 0，不是字母 o），例如：

```c
//合法的八进制数
int a = 015;  //换算成十进制为 13
int b = -0101;  //换算成十进制为 -65
int c = 0177777;  //换算成十进制为 65535
```

##### 十六进制

十六进制由数字 0~9、字母 A~F 或 a~f（不区分大小写）组成，使用时必须以`0x`或`0X`（不区分大小写）开头，例如：

```c
//合法的十六进制
int a = 0X2A;  //换算成十进制为 42
int b = -0XA0;  //换算成十进制为 -160
int c = 0xffff;  //换算成十进制为 65535
```

##### 二进制数、八进制数和十六进制数的输出

|          | short        | int        | long         |
| -------- | ------------ | ---------- | ------------ |
| 八进制   | %ho          | %o         | %lo          |
| 十进制   | %hd          | %d         | %ld          |
| 十六进制 | %hx 或者 %hX | %x 或者 %X | %lx 或者 %lX |

```c
#include <stdio.h>
int main()
{
    short a = 0b1010110;  //二进制数字
    int b = 02713;  //八进制数字
    long c = 0X1DAB83;  //十六进制数字
   
    printf("a=%ho, b=%o, c=%lo\n", a, b, c);  //以八进制形似输出
    printf("a=%hd, b=%d, c=%ld\n", a, b, c);  //以十进制形式输出
    printf("a=%hx, b=%x, c=%lx\n", a, b, c);  //以十六进制形式输出（字母小写）
    printf("a=%hX, b=%X, c=%lX\n", a, b, c);  //以十六进制形式输出（字母大写）
    return 0;
}
```

输出时加上前缀

```c
#include <stdio.h>
int main()
{
    short a = 0b1010110;  //二进制数字
    int b = 02713;  //八进制数字
    long c = 0X1DAB83;  //十六进制数字
   
    printf("a=%#ho, b=%#o, c=%#lo\n", a, b, c);  //以八进制形似输出
    printf("a=%hd, b=%d, c=%ld\n", a, b, c);  //以十进制形式输出
    printf("a=%#hx, b=%#x, c=%#lx\n", a, b, c);  //以十六进制形式输出（字母小写）
    printf("a=%#hX, b=%#X, c=%#lX\n", a, b, c);  //以十六进制形式输出（字母大写）
   
    return 0;
}
```

#####  正负数及其输出

short、int 和 long 类型默认都是带符号位的，符号位以外的内存才是数值位。如果只考虑正数，那么各种类型能表示的数值范围（取值范围）就比原来小了一半。

但是在很多情况下，我们非常确定某个数字只能是正数，比如班级学生的人数、字符串的长度、内存地址等，这个时候符号位就是多余的了，就不如删掉符号位，把所有的位都用来存储数值，这样能表示的数值范围更大（大一倍）。

##### **unsigned** 关键字

如果不希望设置符号位，可以在数据类型前面加上 **unsigned** 关键字

```c
unsigned short a = 12;
unsigned int b = 1002;
unsigned long c = 9892320;
```

> 注意一个小细节，如果是`unsigned int`类型，那么可以省略 int ，只写 unsigned，例如:
>
> unsigned n = 100;

#####  无符号数的输出

| short    | int  | long | unsigned short | unsigned int | unsigned long |              |
| -------- | ---- | ---- | -------------- | ------------ | ------------- | ------------ |
| 八进制   | --   | --   | --             | %ho          | %o            | %lo          |
| 十进制   | %hd  | %d   | %ld            | %hu          | %u            | %lu          |
| 十六进制 | --   | --   | --             | %hx 或者 %hX | %x 或者 %X    | %lx 或者 %lX |

##### 整数在内存中的存储

加法和减法是计算机中最基本的运算，计算机时时刻刻都离不开它们，所以它们由硬件直接支持。为了提高加减法的运算效率，硬件电路要设计得尽量简单。

对于有符号数，内存要区分符号位和数值位，对于人脑来说，很容易辨别，但是对于计算机来说，就要设计专门的电路，这无疑增加了硬件的复杂性，增加了计算的时间。要是能把符号位和数值位等同起来，让它们一起参与运算，不再加以区分，这样硬件电路就变得简单了。

另外，加法和减法也可以合并为一种运算，就是加法运算，因为减去一个数相当于加上这个数的相反数，例如，5 - 3 等价于 5 + (-3)，10 - (-9) 等价于 10 + 9。

> 相反数是指数值相同，符号不同的两个数，例如，10 和 -10 就是一对相反数，-98 和 98 也是一对相反数。

###### 原码

将一个整数转换成二进制形式，就是其原码。例如`short a = 6;`，a 的原码就是`0000 0000 0000 0110`；更改 a 的值`a = -18;`，此时 a 的原码就是`1000 0000 0001 0010`。

通俗的理解，原码就是一个整数本来的二进制形式。

###### 反码

谈到反码，正数和负数要区别对待，因为它们的反码不一样。

对于正数，它的反码就是其原码（原码和反码相同）；负数的反码是将原码中除符号位以外的所有位（数值位）取反，也就是 0 变成 1，1 变成 0。例如`short a = 6;`，a 的原码和反码都是`0000 0000 0000 0110`；更改 a 的值`a = -18;`，此时 a 的反码是`1111 1111 1110 1101`。

######  补码

正数和负数的补码也不一样，也要区别对待。

对于正数，它的补码就是其原码（原码、反码、补码都相同）；负数的补码是其反码加 1。例如`short a = 6;`，a 的原码、反码、补码都是`0000 0000 0000 0110`；更改 a 的值`a = -18;`，此时 a 的补码是`1111 1111 1110 1110`。

可以认为，补码是在反码的基础上打了一个补丁，进行了一下修正，所以叫“补码”。

> 在计算机内存中，整数一律采用补码的形式来存储。这意味着，当读取整数时还要采用逆向的转换，也就是将补码转换为原码。正数的补码和原码相同，负数的补码转换为原码也很简单：先减去 1，再将数值位取反即可。



## 预处理命令

C语言源文件要经过编译、链接才能生成可执行程序：

1) 编译（Compile）会将源文件（`.c`文件）转换为目标文件。

   对于 VC/VS，目标文件后缀为`.obj`；对于[GCC](http://c.biancheng.net/gcc/)，目标文件后缀为`.o`

2) 链接（Link）是针对多个文件的，它会将编译生成的多个目标文件以及系统中的库、组件等合并成一个可执行程序。

在编译之前对源文件进行简单加工的过程，就称为**预处理**（即预先处理、提前处理）

预处理主要是处理以`#`开头的命令，例如`#include <stdio.h>`等。预处理命令要放在所有函数之外，而且一般都放在源文件的前面

#### 实例

假如现在要开发一个C语言程序，让它暂停 5 秒以后再输出内容，并且要求跨平台，在 Windows 和 Linux 下都能运行，怎么办呢？

这个程序的难点在于，不同平台下的暂停函数和头文件都不一样：

- Windows 平台下的暂停函数的原型是`void Sleep(DWORD dwMilliseconds)`（注意 S 是大写的），参数的单位是“毫秒”，位于 <windows.h> 头文件。
- Linux 平台下暂停函数的原型是`unsigned int sleep (unsigned int seconds)`，参数的单位是“秒”，位于 <unistd.h> 头文件

不同的平台下必须调用不同的函数，并引入不同的头文件，否则就会导致编译错误，因为 Windows 平台下没有 sleep() 函数，也没有 <unistd.h> 头文件，反之亦然。这就要求我们在编译之前，也就是预处理阶段来解决这个问题。请看下面的代码：

```c
#include <stdio.h>
//不同的平台下引入不同的头文件
#if _WIN32  //识别windows平台
#include <windows.h>
#elif __linux__  //识别linux平台
#include <unistd.h>
#endif
int main() {
    //不同的平台下调用不同的函数
    #if _WIN32  //识别windows平台
    Sleep(5000);
    #elif __linux__  //识别linux平台
    sleep(5);
    #endif
    puts("http://c.biancheng.net/");
    return 0;
}
```

对于 Windows 平台，预处理以后的代码变成

```c
#include <stdio.h>
#include <windows.h>
int main() {
    Sleep(5000);
    puts("http://c.biancheng.net/");
    return 0;
}
```

对于 Linux 平台，预处理以后的代码变成：

```c
#include <stdio.h>
#include <unistd.h>
int main() {
    sleep(5);
    puts("http://c.biancheng.net/");
    return 0;
}
```

### #include的用法

`#include`叫做文件包含命令，用来引入对应的头文件（`.h`文件）。#include 也是C语言预处理命令的一种。

`#include` 的处理过程很简单，就是将头文件的内容插入到该命令所在的位置，从而把头文件和当前源文件连接成一个源文件，这与复制粘贴的效果相同

```c
#include <stdHeader.h>
#include "myHeader.h"
```

> 使用尖括号`< >`和双引号`" "`的区别在于头文件的搜索路径不同：
>
> - 使用尖括号`< >`，编译器会到系统路径下查找头文件；
> - 而使用双引号`" "`，编译器首先在当前目录下查找头文件，如果没有找到，再到系统路径下查找

### #define的用法

`#define` 叫做宏定义命令，它也是C语言预处理命令的一种。所谓宏定义，就是用一个标识符来表示一个字符串，如果在后面的代码中出现了该标识符，那么就全部替换成指定的字符串。

```c
#include <stdio.h>
#define N 100
int main(){
    int sum = 20 + N;
    printf("%d\n", sum);
    return 0;
}
```

> `#define N 100`就是宏定义，`N`为宏名，`100`是宏的内容（宏所表示的字符串）。在预处理阶段，对程序中所有出现的“宏名”，预处理器都会用宏定义中的字符串去代换，这称为“宏替换”或“宏展开”。
>
> 宏定义是由源程序中的宏定义命令`#define`完成的，宏替换是由预处理程序完成的。

1) 宏定义是用宏名来表示一个字符串，在宏展开时又以该字符串取代宏名，这只是一种简单粗暴的替换。字符串中可以含任何字符，它可以是常数、表达式、if 语句、函数等，预处理程序对它不作任何检查，如有错误，只能在编译已被宏展开后的源程序时发现。

2) 宏定义不是说明或语句，在行末不必加分号，如加上分号则连分号也一起替换。

3) 宏定义必须写在函数之外，其作用域为宏定义命令起到源程序结束。如要终止其作用域可使用`#undef`命令。

> 宏定义只是简单的字符串替换，由预处理器来处理；
>
>  typedef 是在编译阶段由编译器处理的，它并不是简单的字符串替换，而给原有的数据类型起一个新的名字，将它作为一种新的数据类型。

#### 带参数的宏

```c
#include <stdio.h>
#define MAX(a,b) (a>b) ? a : b
int main(){
    int x , y, max;
    printf("input two numbers: ");
    scanf("%d %d", &x, &y);
    max = MAX(x, y);
    printf("max=%d\n", max);
    return 0;
}
```

> 1) 带参宏定义中，形参之间可以出现空格，但是宏名和形参列表之间不能有空格出现。
> 2) 在带参宏定义中，不会为形式参数分配内存，因此不必指明数据类型。而在宏调用中，实参包含了具体的数据，要用它们去替换形参，因此实参必须要指明数据类型

#### 预定义宏

```
ANSI C 规定了以下几个预定义宏，它们在各个编译器下都可以使用：
__LINE__：表示当前源代码的行号；
__FILE__：表示当前源文件的名称；
__DATE__：表示当前的编译日期；
__TIME__：表示当前的编译时间；
__STDC__：当要求程序严格遵循ANSI C标准时该标识被赋值为1；
__cplusplus：当编写C++程序时该标识符被定义
```

预定义宏演示

```c
#include <stdio.h>
#include <stdlib.h>
int main() {
    printf("Date : %s\n", __DATE__);
    printf("Time : %s\n", __TIME__);
    printf("File : %s\n", __FILE__);
    printf("Line : %d\n", __LINE__);
    system("pause");
    return 0;
}
```

#### #if、#ifdef、#ifndef的用法

假如现在要开发一个C语言程序，让它输出红色的文字，并且要求跨平台，在 Windows 和 Linux 下都能运行，怎么办呢？

这个程序的难点在于，不同平台下控制文字颜色的代码不一样，我们必须要能够识别出不同的平台。

Windows 有专有的宏`_WIN32`，Linux 有专有的宏`__linux__`

```c
#include <stdio.h>
int main(){
    #if _WIN32
        system("color 0c");
        printf("http://c.biancheng.net\n");
    #elif __linux__
        printf("\033[22;31mhttp://c.biancheng.net\n\033[22;30m");
    #else
        printf("http://c.biancheng.net\n");
    #endif
    return 0;
}
```

\#if、#elif、#else 和 #endif 都是预处理命令，整段代码的意思是：如果宏 _WIN32 的值为真，就保留第 4、5 行代码，删除第 7、9 行代码；如果宏 __linux__ 的值为真，就保留第 7 行代码；如果所有的宏都为假，就保留第 9 行代码。

这些操作都是在预处理阶段完成的，多余的代码以及所有的宏都不会参与编译，不仅保证了代码的正确性，还减小了编译后文件的体积。

这种能够根据不同情况编译不同代码、产生不同目标文件的机制，称为条件编译。条件编译是预处理程序的功能，不是编译器的功能。

##### #if 的用法

```
#if 整型常量表达式1
    程序段1
#elif 整型常量表达式2
    程序段2
#elif 整型常量表达式3
    程序段3
#else
    程序段4
#endif
```

它的意思是：如常“表达式1”的值为真（非0），就对“程序段1”进行编译，否则就计算“表达式2”，结果为真的话就对“程序段2”进行编译，为假的话就继续往下匹配，直到遇到值为真的表达式，或者遇到 #else。这一点和 if else 非常类似。

需要注意的是，#if 命令要求判断条件为“整型常量表达式”，也就是说，表达式中不能包含变量，而且结果必须是整数；而 if 后面的表达式没有限制，只要符合语法就行。这是 #if 和 if 的一个重要区别。

\#elif 和 #else 也可以省略，如下所示：

```c
#include <stdio.h>
int main(){
    #if _WIN32
        printf("This is Windows!\n");
    #else
        printf("Unknown platform!\n");
    #endif
   
    #if __linux__
        printf("This is Linux!\n");
    #endif
    return 0;
}
```

##### #ifdef 的用法

```
#ifdef  宏名
    程序段1
#else
    程序段2
#endif
```

它的意思是，如果当前的宏已被定义过，则对“程序段1”进行编译，否则对“程序段2”进行编译。

也可以省略 #else：

```
#ifdef  宏名
    程序段
#endif
```

VS/VC 有两种编译模式，Debug 和 Release。在学习过程中，我们通常使用 Debug 模式，这样便于程序的调试；而最终发布的程序，要使用 Release 模式，这样编译器会进行很多优化，提高程序运行效率，删除冗余信息。

为了能够清楚地看到当前程序的编译模式，我们不妨在程序中增加提示，请看下面的代码：

```c
#include <stdio.h>
#include <stdlib.h>
int main(){
    #ifdef _DEBUG
        printf("正在使用 Debug 模式编译程序...\n");
    #else
        printf("正在使用 Release 模式编译程序...\n");
    #endif
    system("pause");
    return 0;
}
```

当以 Debug 模式编译程序时，宏 _DEBUG 会被定义，预处器会保留第 5 行代码，删除第 7 行代码。反之会删除第 5 行，保留第 7 行。

##### #ifndef 的用法

```
#ifndef 宏名
    程序段1 
#else 
    程序段2 
#endif
```

与 #ifdef 相比，仅仅是将 #ifdef 改为了 #ifndef。它的意思是，如果当前的宏未被定义，则对“程序段1”进行编译，否则对“程序段2”进行编译，这与 #ifdef 的功能正好相反。

##### 三者之间的区别

最后需要注意的是，#if 后面跟的是“整型常量表达式”，而 #ifdef 和 #ifndef 后面跟的只能是一个宏名，不能是其他的。

```c
#include <stdio.h>
#define NUM 10
int main(){
    #if NUM == 10 || NUM == 20
        printf("NUM: %d\n", NUM);
    #else
        printf("NUM Error\n");
    #endif
    return 0;
}
```

再如，两个宏都存在时编译代码A，否则编译代码B：

```c
#include <stdio.h>
#define NUM1 10
#define NUM2 20
int main(){
    #if (defined NUM1 && defined NUM2)
        //代码A
        printf("NUM1: %d, NUM2: %d\n", NUM1, NUM2);
    #else
        //代码B
        printf("Error\n");
    #endif
    return 0;
}
```

### #error命令

\#error 指令用于在编译期间产生错误信息，并阻止程序的编译，其形式如下：

```
#error error_message
```

例如，我们的程序针对 Linux 编写，不保证兼容 Windows，那么可以这样做：

```c
#ifdef WIN32
#error This programme cannot compile at Windows Platform
#endif
```

WIN32 是 Windows 下的预定义宏。当用户在 Windows 下编译该程序时，由于定义了 WIN32 这个宏，所以会执行 `#error `命令，提示用户发生了编译错误，错误信息是：

```c
This programme cannot compile at Windows Platform
```

再如，当我们希望以 C++ 的方式来编译程序时，可以这样做：

```c
#ifndef __cplusplus
#error 当前程序必须以C++方式编译
#endif
```

#### 预处理总结

| 指令     | 说明                                                      |
| -------- | --------------------------------------------------------- |
| #        | 空指令，无任何效果                                        |
| #include | 包含一个源代码文件                                        |
| #define  | 定义宏                                                    |
| #undef   | 取消已定义的宏                                            |
| #if      | 如果给定条件为真，则编译下面代码                          |
| #ifdef   | 如果宏已经定义，则编译下面代码                            |
| #ifndef  | 如果宏没有定义，则编译下面代码                            |
| #elif    | 如果前面的#if给定条件不为真，当前条件为真，则编译下面代码 |
| #endif   | 结束一个#if……#else条件编译块                              |



## 指针

计算机中所有的数据都必须放在内存中，不同类型的数据占用的字节数不一样，例如 int 占用 4 个字节，char 占用 1 个字节。为了正确地访问这些数据，必须为每个字节都编上号码，就像门牌号、身份证号一样，每个字节的编号是唯一的，根据编号可以准确地找到某个字节。

我们将内存中字节的编号称为地址（Address）或指针（Pointer）。地址从 0 开始依次增加，对于 32 位环境，程序能够使用的内存为 4GB，最小的地址为 0，最大的地址为 0XFFFFFFFF

下面的代码演示了如何输出一个地址：

```c
#include <stdio.h>
int main(){
    int a = 100;
    char str[20] = "c.biancheng.net";
    printf("%#X, %#X\n", &a, str);
    return 0;
}
```

`%#X`表示以十六进制形式输出，并附带前缀`0X`。a 是一个变量，用来存放整数，需要在前面加`&`来获得它的地址；str 本身就表示字符串的首地址，不需要加`&`。



数据和代码都以二进制的形式存储在内存中，计算机无法从格式上区分某块内存到底存储的是数据还是代码。当程序被加载到内存后，操作系统会给不同的内存块指定不同的权限，拥有读取和执行权限的内存块就是代码，而拥有读取和写入权限（也可能只有读取权限）的内存块就是数据。

CPU 只能通过地址来取得内存中的代码和数据，程序在执行过程中会告知 CPU 要执行的代码以及要读写的数据的地址。如果程序不小心出错，或者开发者有意为之，在 CPU 要写入数据时给它一个代码区域的地址，就会发生内存访问错误。这种内存访问错误会被硬件和操作系统拦截，强制程序崩溃，程序员没有挽救的机会。

CPU 访问内存时需要的是地址，而不是变量名和函数名！变量名和函数名只是地址的一种助记符，当源文件被编译和链接成可执行程序后，它们都会被替换成地址。编译和链接过程的一项重要任务就是找到这些名称所对应的地址。

### 指针变量

数据在内存中的地址也称为[指针](http://c.biancheng.net/c/80/)，如果一个变量存储了一份数据的指针，我们就称它为**指针变量**。

定义指针变量与定义普通变量非常类似，不过要在变量名前面加星号`*`，格式为：

```c
datatype *name;
```

或者

```c
datatype *name = value;
```

`*`表示这是一个指针变量，`datatype`表示该指针变量所指向的数据的类型 。例如：

```c
int a = 100;
int *p_a = &a;
```

在定义指针变量 p_a 的同时对它进行初始化，并将变量 a 的地址赋予它，此时 p_a 就指向了 a。值得注意的是，p_a 需要的一个地址，a 前面必须要加取地址符`&`，否则是不对的。

> 定义指针变量时必须带`*`，给指针变量赋值时不能带`*`

指针变量存储了数据的地址，通过指针变量能够获得该地址上的数据，格式为：

```c
*pointer;
```

`*`在不同的场景下有不同的作用：

>  `*`可以用在指针变量的定义中，表明这是一个指针变量，以和普通变量区分开；
>
> 使用指针变量时在前面加`*`表示获取指针指向的数据，或者说表示的是指针指向的数据本身。

#### 关于 * 和 &

假设有一个 int 类型的变量 a，pa 是指向它的指针，那么`*&a`和`&*pa`分别是什么意思呢？

> `*&a`可以理解为`*(&a)`，`&a`表示取变量 a 的地址（等价于 pa），`*(&a)`表示取这个地址上的数据（等价于 *pa），绕来绕去，又回到了原点，`*&a`仍然等价于 a。

> `&*pa`可以理解为`&(*pa)`，`*pa`表示取得 pa 指向的数据（等价于 a），`&(*pa)`表示数据的地址（等价于 &a），所以`&*pa`等价于 pa。

#### 对星号`*`的总结

- 表示乘法，例如`int a = 3, b = 5, c;  c = a * b;`，这是最容易理解的。
- 表示定义一个指针变量，以和普通变量区分开，例如`int a = 100;  int *p = &a;`。
- 表示获取指针指向的数据，是一种间接操作，例如`int a, b, *p = &a;  *p = 100;  b = *p;`。



### 指针变量的运算

[指针](http://c.biancheng.net/c/80/)变量保存的是地址，而地址本质上是一个整数，所以指针变量可以进行部分运算

```c
#include <stdio.h>
int main(){
    int    a = 10,   *pa = &a, *paa = &a;
    double b = 99.9, *pb = &b;
    char   c = '@',  *pc = &c;
    //最初的值
    printf("&a=%#X, &b=%#X, &c=%#X\n", &a, &b, &c);
    printf("pa=%#X, pb=%#X, pc=%#X\n", pa, pb, pc);
    //加法运算
    pa++; pb++; pc++;
    printf("pa=%#X, pb=%#X, pc=%#X\n", pa, pb, pc);
    //减法运算
    pa -= 2; pb -= 2; pc -= 2;
    printf("pa=%#X, pb=%#X, pc=%#X\n", pa, pb, pc);
    //比较运算
    if(pa == paa){
        printf("%d\n", *paa);
    }else{
        printf("%d\n", *pa);
    }
    return 0;
}
```

运行结果

```
&a=0X28FF44, &b=0X28FF30, &c=0X28FF2B
pa=0X28FF44, pb=0X28FF30, pc=0X28FF2B
pa=0X28FF48, pb=0X28FF38, pc=0X28FF2C
pa=0X28FF40, pb=0X28FF28, pc=0X28FF2A
2686784
```

从运算结果可以看出：pa、pb、pc 每次加 1，它们的地址分别增加 4、8、1，正好是 int、double、char 类型的长度；减 2 时，地址分别减少 8、16、2，正好是 int、double、char 类型长度的 2 倍。



#### 数组指针

数组（Array）是一系列具有相同类型的数据的集合，每一份数据叫做一个数组元素（Element）。数组中的所有元素在内存中是连续排列的，整个数组占用的是一块内存。

定义数组时，要给出数组名和数组长度，数组名可以认为是一个[指针](http://c.biancheng.net/c/80/)，它指向数组的第 0 个元素。在C语言中，我们将第 0 个元素的地址称为数组的首地址。



指针的方式遍历数组元素

```c
#include <stdio.h>
int main(){
    int arr[] = { 99, 15, 100, 888, 252 };
    int len = sizeof(arr) / sizeof(int);  //求数组长度
    int i;
    for(i=0; i<len; i++){
        printf("%d  ", *(arr+i) );  //*(arr+i)等价于arr[i]
    }
    printf("\n");
    return 0;
}
```

sizeof(arr) 会获得整个数组所占用的字节数，sizeof(int) 会获得一个数组元素所占用的字节数，它们相除的结果就是数组包含的元素个数，也即数组长度。

我们使用了`*(arr+i)`这个表达式，arr 是数组名，指向数组的第 0 个元素，表示数组首地址， arr+i 指向数组的第 i 个元素，*(arr+i) 表示取第 i 个元素的数据，它等价于 arr[i]

如果一个指针指向了数组，我们就称它为数组指针（Array Pointer）.

数组指针指向的是数组中的一个具体元素，而不是整个数组，所以数组指针的类型和数组元素的类型有关.

更改上面的代码，使用数组指针来遍历数组元素：

```c
#include <stdio.h>
int main(){
    int arr[] = { 99, 15, 100, 888, 252 };
    int i, *p = arr, len = sizeof(arr) / sizeof(int);
    for(i=0; i<len; i++){
        printf("%d  ", *(p+i) );
    }
    printf("\n");
    return 0;
}
```

#### 访问数组元素

> 1. 使用下标
>
> 也就是采用 arr[i] 的形式访问数组元素。如果 p 是指向数组 arr 的指针，那么也可以使用 p[i] 来访问数组元素，它等价于 arr[i]
>
> 2. 使用指针
>
> 也就是使用 *(p+i) 的形式访问数组元素。另外数组名本身也是指针，也可以使用 *(arr+i) 来访问数组元素，它等价于 *(p+i)



### 字符串指针

C语言中没有特定的字符串类型，我们通常是将字符串放在一个字符数组中

```c
#include <stdio.h>
#include <string.h>
int main(){
    char str[] = "http://c.biancheng.net";
    int len = strlen(str), i;
    //直接输出字符串
    printf("%s\n", str);
    //每次输出一个字符
    for(i=0; i<len; i++){
        printf("%c", str[i]);
    }
    printf("\n");
    return 0;
}
```

除了字符数组，C语言还支持另外一种表示字符串的方法，就是直接使用一个指针指向字符串，例如：

```c
char *str = "http://c.biancheng.net";
```

字符数组存储在全局数据区或栈区，字符串常量形式的字符串存储在常量区。

全局数据区和栈区的字符串（也包括其他数据）有读取和写入的权限，而常量区的字符串（也包括其他数据）只有读取权限，没有写入权限。



### 空指针 NULL

一个指针变量可以指向计算机中的任何一块内存，不管该内存有没有被分配，也不管该内存有没有使用权限，只要把地址给它，它就可以指向，C语言没有一种机制来保证指向的内存的正确性，程序员必须自己提高警惕。

```c
char *str = NULL;
```

NULL 是“零值、等于零”的意思，在C语言中表示空指针。从表面上理解，空指针是不指向任何数据的指针，是无效指针，程序使用它不会产生效果

```c
void func(char *p){
    if(p == NULL){
        printf("(null)\n");
    }else{
        printf("%s\n", p);
    }
}
```

这样能够从很大程度上增加程序的健壮性，防止对空指针进行无意义的操作。

其实，NULL 是在`stdio.h`中定义的一个宏，它的具体内容为：

```c
#define NULL ((void *)0)
```

`(void *)0`表示把数值 0 强制转换为`void *`类型，最外层的`( )`把宏定义的内容括起来，防止发生歧义。从整体上来看，NULL 指向了地址为 0 的内存，而不是前面说的不指向任何数据。

在进程的虚拟地址空间中，最低地址处有一段内存区域被称为保留区，这个区域不存储有效数据，也不能被用户程序访问，将 NULL 指向这块区域很容易检测到违规指针。



### void指针

void 用在函数定义中可以表示函数没有返回值或者没有形式参数。

`void *`表示一个有效指针，它确实指向实实在在的数据，只是数据的类型尚未确定，在后续使用过程中一般要进行强制类型转换。

C语言动态内存分配函数 malloc() 的返回值就是`void *`类型，在使用时要进行强制类型转换，请看下面的例子：

```c
#include <stdio.h>
int main(){
    //分配可以保存30个字符的内存，并把返回的指针转换为 char *
    char *str = (char *)malloc(sizeof(char) * 30);
    gets(str);
    printf("%s\n", str);
    return 0;
}
```



### 数组在什么时候会转换为指针

当数组名作为数组定义的标识符（也就是定义或声明数组时）、sizeof 或 & 的操作数时，它表示整个数组本身；

在其他的表达式中，数组名会被转换为指向第 0 个元素的指针（地址）。



### 数组下标[ ]

对数组的引用 a[i] 在编译时总是被编译器改写成`*(a+i)`的形式，C语言标准也要求编译器必须具备这种行为。

取下标操作符`[ ]`是建立在指针的基础上，它的作用是使一个指针和一个整数相加，产生出一个新的指针，然后从这个新指针（新地址）上取得数据；假设指针的类型为`T *`，所产生的结果的类型就是`T`



### 数组作函数参数

作为“类型的数组”的形参应该调整为“类型的指针”。在函数形参定义这个特殊情况下，编译器必须把数组形式改写成指向数组第 0 个元素的指针形式。编译器只向函数传递数组的地址，而不是整个数组的拷贝。

这种隐式转换意味着下面三种形式的函数定义是完全等价的：

```c
void func(int *parr){ ...... }
void func(int arr[]){ ...... }
void func(int arr[5]){ ...... }
```

把作为形参的数组和指针等同起来是出于效率方面的考虑。数组是若干类型相同的数据的集合，数据的数目没有限制，可能只有几个，也可能成千上万，如果要传递整个数组，无论在时间还是内存空间上的开销都可能非常大。



### 指针数组

一个数组中的所有元素保存的都是[指针](http://c.biancheng.net/c/80/)，那么我们就称它为指针数组

```c
#include <stdio.h>
int main(){
    int a = 16, b = 932, c = 100;
    //定义一个指针数组
    int *arr[3] = {&a, &b, &c};//也可以不指定长度，直接写作 int *arr[]
    //定义一个指向指针数组的指针
    int **parr = arr;
    printf("%d, %d, %d\n", *arr[0], *arr[1], *arr[2]);
    printf("%d, %d, %d\n", **(parr+0), **(parr+1), **(parr+2));
    return 0;
}
```

指针数组还可以和字符串数组结合使用

```c
#include <stdio.h>
int main(){
    char *str[3] = {
        "c.biancheng.net",
        "C语言中文网",
        "C Language"
    };
    printf("%s\n%s\n%s\n", str[0], str[1], str[2]);
    return 0;
}
```

字符数组 str 中存放的是字符串的首地址，不是字符串本身，字符串本身位于其他的内存区域，和字符数组是分开的。



### 二维数组指针

为了更好的理解[指针](http://c.biancheng.net/c/80/)和二维数组的关系，我们先来定义一个指向 a 的指针变量 p：

```c
int a[3][4] = { {0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11} };
int (*p)[4] = a;
```

括号中的`*`表明 p 是一个指针，它指向一个数组，数组的类型为`int [4]`，这正是 a 所包含的每个一维数组的类型。

对指针进行加法（减法）运算时，它前进（后退）的步长与它指向的数据类型有关，p 指向的数据类型是`int [4]`，那么`p+1`就前进 4×4 = 16 个字节，`p-1`就后退 16 个字节，这正好是数组 a 所包含的每个一维数组的长度。也就是说，`p+1`会使得指针指向二维数组的下一行，`p-1`会使得指针指向数组的上一行。

数组名 a 在表达式中也会被转换为和 p 等价的指针。

```c
#include <stdio.h>
int main(){
    int a[3][4] = { {0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11} };
    int (*p)[4] = a;
    printf("%d\n", sizeof(*(p+1)));
    return 0;
}
```

运行结果：
16

`*(p+1)+1`表示第 1 行第 1 个元素的地址。如何理解呢？

`*(p+1)`单独使用时表示的是第 1 行数据，放在表达式中会被转换为第 1 行数据的首地址，也就是第 1 行第 0 个元素的地址，因为使用整行数据没有实际的含义，编译器遇到这种情况都会转换为指向该行第 0 个元素的指针；就像一维数组的名字，在定义时或者和 sizeof、& 一起使用时才表示整个数组，出现在表达式中就会被转换为指向数组第 0 个元素的指针。

`*(*(p+1)+1)`表示第 1 行第 1 个元素的值。很明显，增加一个 * 表示取地址上的数据。

```c
a+i == p+i
a[i] == p[i] == *(a+i) == *(p+i)
a[i][j] == p[i][j] == *(a[i]+j) == *(p[i]+j) == *(*(a+i)+j) == *(*(p+i)+j)
```



使用指针遍历二维数组

```c
#include <stdio.h>
int main(){
    int a[3][4]={0,1,2,3,4,5,6,7,8,9,10,11};
    int(*p)[4];
    int i,j;
    p=a;
    for(i=0; i<3; i++){
        for(j=0; j<4; j++) printf("%2d  ",*(*(p+i)+j));
        printf("\n");
    }
    return 0;
}
```

运行结果：

```
 0   1   2   3
 4   5   6   7
 8   9  10  11
```



### 指针数组和二维数组指针的区别

指针数组和二维数组指针在定义时非常相似，只是括号的位置不同：

```c
int *(p1[5]);  //指针数组，可以去掉括号直接写作 int *p1[5];
int (*p2)[5];  //二维数组指针，不能去掉括号
```

指针数组和二维数组指针有着本质上的区别：

指针数组是一个数组，只是每个元素保存的都是指针，以上面的 p1 为例，在32位环境下它占用 4×5 = 20 个字节的内存。

二维数组指针是一个指针，它指向一个二维数组，以上面的 p2 为例，它占用 4 个字节的内存。



### 函数指针

函数指针的定义形式为：

```c
returnType (*pointerName)(param list);
```

returnType 为函数返回值类型，pointerName 为指针名称，param list 为函数参数列表。参数列表中可以同时给出参数的类型和名称，也可以只给出参数的类型，省略参数的名称，这一点和函数原型非常类似。

注意`( )`的优先级高于`*`，第一个括号不能省略，如果写作`returnType *pointerName(param list);`就成了函数原型，它表明函数的返回值类型为`returnType *`



用指针来实现对函数的调用

```c
#include <stdio.h>
//返回两个数中较大的一个
int max(int a, int b){
    return a>b ? a : b;
}
int main(){
    int x, y, maxval;
    //定义函数指针
    int (*pmax)(int, int) = max;  //也可以写作int (*pmax)(int a, int b)
    printf("Input two numbers:");
    scanf("%d %d", &x, &y);
    maxval = (*pmax)(x, y);
    printf("Max value: %d\n", maxval);
    return 0;
}
```



对于初学者，有几种运算符的优先级非常容易混淆，它们的优先级从高到低依次是：

- 定义中被括号`( )`括起来的那部分。
- 后缀操作符：括号`( )`表示这是一个函数，方括号`[ ]`表示这是一个数组。
- 前缀操作符：星号`*`表示“指向xxx的指针”。



#### int *p1[6]

从 p1 开始理解，它的左边是 *，右边是 [ ]，[ ] 的优先级高于 *，所以编译器先解析`p1[6]`，p1 首先是一个拥有 6 个元素的数组，然后再解析`int *`，它用来说明数组元素的类型。从整体上讲，p1 是一个拥有 6 个 int * 元素的数组，也即指针数组。



#### int (*p3)[6]

从 p3 开始理解，( ) 的优先级最高，编译器先解析`(*p3)`，p3 首先是一个指针，剩下的`int [6]`是 p3 指向的数据的类型，它是一个拥有 6 个元素的一维数组。从整体上讲，p3 是一个指向拥有 6 个 int 元素数组的指针，也即二维数组指针。

> 为了能够通过指针来遍历数组元素，在定义数组指针时需要进行降维处理，例如三维数组指针实际指向的数据类型是二维数组，二维数组指针实际指向的数据类型是一维数组，一维数组指针实际指向的是一个基本类型；在表达式中，数组名也会进行同样的转换（下降一维）。



#### int (*p4)(int, int)

从 p4 开始理解，( ) 的优先级最高，编译器先解析`(*p4)`，p4 首先是一个指针，它后边的 ( ) 说明 p4 指向的是一个函数，括号中的`int, int`是参数列表，开头的`int`用来说明函数的返回值类型。整体来看，p4 是一个指向原型为`int func(int, int);`的函数的指针。



#### char *( * c[10])(int **p)

明 c 是一个指针数组，红色粗体表明指针指向的数据类型，合起来就是：c 是一个拥有 10 个元素的指针数组，每个指针指向一个原型为`char *func(int **p);`的函数。



#### int (* ( * ( *pfunc)(int *))[5])(int *)

pfunc 是一个函数指针，该函数的返回值是一个指针，它指向一个指针数组，指针数组中的指针指向原型为`int func(int *);`的函数。



### main()函数

main() 是C语言程序的入口函数，有且只能有一个，它实际上有两种标准的原型：

```c
int main();
int main(int argc, char *argv[]);
```

第二种原型在实际开发中也经常使用，它能够让我们在程序启动时给程序传递数据。

在第二个原型中，argc 表示传递的字符串的数目，argv 是一个指针数组，每个指针指向一个字符串（一份数据）。我们来看一个具体的例子

```c
#include <stdio.h>
int main(int argc, char *argv[]){
    int i;
    printf("The program receives %d parameters:\n", argc);
    for(i=0; i<argc; i++){
        printf("%s\n", argv[i]);
    }
    return 0;
}
```



### 总结

[指针](http://c.biancheng.net/c/80/)（Pointer）就是内存的地址，C语言允许用一个变量来存放指针，这种变量称为指针变量。指针变量可以存放基本类型数据的地址，也可以存放数组、函数以及其他指针变量的地址。

| 定  义       | 含  义                                                       |
| ------------ | ------------------------------------------------------------ |
| int *p;      | p 可以指向 int 类型的数据，也可以指向类似 int arr[n] 的数组。 |
| int **p;     | p 为二级指针，指向 int * 类型的数据。                        |
| int *p[n];   | p 为指针数组。[ ] 的优先级高于 *，所以应该理解为 int *(p[n]); |
| int (*p)[n]; | p 为[二维数组](http://c.biancheng.net/c/array/)指针。        |
| int *p();    | p 是一个函数，它的返回值类型为 int *。                       |
| int (*p)();  | p 是一个函数指针，指向原型为 int func() 的函数。             |

1) 指针变量可以进行加减运算，例如`p++`、`p+i`、`p-=i`。指针变量的加减运算并不是简单的加上或减去一个整数，而是跟指针指向的数据类型有关。

2) 给指针变量赋值时，要将一份数据的地址赋给它，不能直接赋给一个整数，例如`int *p = 1000;`是没有意义的，使用过程中一般会导致程序崩溃。

3) 使用指针变量之前一定要初始化，否则就不能确定指针指向哪里，如果它指向的内存没有使用权限，程序就崩溃了。对于暂时没有指向的指针，建议赋值`NULL`。

4) 两个指针变量可以相减。如果两个指针变量指向同一个数组中的某个元素，那么相减的结果就是两个指针之间相差的元素个数。

5) 数组也是有类型的，数组名的本意是表示一组类型相同的数据。在定义数组时，或者和 sizeof、& 运算符一起使用时数组名才表示整个数组，表达式中的数组名会被转换为一个指向数组的指针。



## 结构体

### struct用法

使用**结构体（struct）**来存放一组不同类型的数据。结构体的定义形式为：

```c
struct 结构体名{
    结构体所包含的变量或数组
};
```

结构体是一种集合，它里面包含了多个变量或数组，它们的类型可以相同，也可以不同，每个这样的变量或数组都称为结构体的成员（Member）。

```c
struct stu{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在学习小组
    float score;  //成绩
};
```

stu 为结构体名，它包含了 5 个成员，分别是 name、num、age、group、score。结构体成员的定义方式与变量和数组的定义方式相同，只是不能初始化。



### 结构体变量

```c
struct stu stu1, stu2;
```

定义了两个变量 stu1 和 stu2，它们都是 stu 类型，都由 5 个成员组成。注意关键字`struct`不能少。

stu 就像一个“模板”，定义出来的变量都具有相同的性质。也可以将结构体比作“图纸”，将结构体变量比作“零件”，根据同一张图纸生产出来的零件的特性都是一样的。

也可以在定义结构体的同时定义结构体变量，将变量放在结构体定义的最后即可：

```c
struct stu{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在学习小组
    float score;  //成绩
} stu1, stu2;

```

如果只需要 stu1、stu2 两个变量，后面不需要再使用结构体名定义其他变量，那么在定义时也可以不给出结构体名，如下所示：

```c
struct{  //没有写 stu
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在学习小组
    float score;  //成绩
} stu1, stu2;
```



### 成员的获取与赋值

结构体和数组类似，也是一组数据的集合，整体使用没有太大的意义。数组使用下标`[ ]`获取单个元素，结构体使用点号`.`获取单个成员。获取结构体成员的一般格式为：

```c
结构体变量名.成员名;
```

通过这种方式可以获取成员的值，也可以给成员赋值：

```c
#include <stdio.h>
int main(){
    struct{
        char *name;  //姓名
        int num;  //学号
        int age;  //年龄
        char group;  //所在小组
        float score;  //成绩
    } stu1;
    //给结构体成员赋值
    stu1.name = "Tom";
    stu1.num = 12;
    stu1.age = 18;
    stu1.group = 'A';
    stu1.score = 136.5;
    //读取结构体成员的值
    printf("%s的学号是%d，年龄是%d，在%c组，今年的成绩是%.1f！\n", stu1.name, stu1.num, stu1.age, stu1.group, stu1.score);
    return 0;
}
```

除了可以对成员进行逐一赋值，也可以在定义时整体赋值，例如：

```c
struct{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在小组
    float score;  //成绩
} stu1, stu2 = { "Tom", 12, 18, 'A', 136.5 };
```

不过整体赋值仅限于定义结构体变量的时候，在使用过程中只能对成员逐一赋值，这和数组的赋值非常类似。



### 结构体数组

定义结构体数组和定义结构体变量的方式类似，请看下面的例子：

```c
struct stu{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在小组 
    float score;  //成绩
}class[5];
```

表示一个班级有5个学生。

结构体数组在定义的同时也可以初始化，例如：

```c
struct stu{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在小组 
    float score;  //成绩
}class[5] = {
    {"Li ping", 5, 18, 'C', 145.0},
    {"Zhang ping", 4, 19, 'A', 130.5},
    {"He fang", 1, 18, 'A', 148.5},
    {"Cheng ling", 2, 17, 'F', 139.0},
    {"Wang ming", 3, 17, 'B', 144.5}
};
```

计算全班学生的总成绩、平均成绩和以及 140 分以下的人数

```c
#include <stdio.h>
struct{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在小组
    float score;  //成绩
}class[] = {
    {"Li ping", 5, 18, 'C', 145.0},
    {"Zhang ping", 4, 19, 'A', 130.5},
    {"He fang", 1, 18, 'A', 148.5},
    {"Cheng ling", 2, 17, 'F', 139.0},
    {"Wang ming", 3, 17, 'B', 144.5}
};
int main(){
    int i, num_140 = 0;
    float sum = 0;
    for(i=0; i<5; i++){
        sum += class[i].score;
        if(class[i].score < 140) num_140++;
    }
    printf("sum=%.2f\naverage=%.2f\nnum_140=%d\n", sum, sum/5, num_140);
    return 0;
}
```



### 结构体指针

当一个[指针](http://c.biancheng.net/c/80/)变量指向结构体时，我们就称它为**结构体指针**。C语言结构体指针的定义形式一般为：

```c
struct 结构体名 *变量名;
```

下面是一个定义结构体指针的实例：

```c
//结构体
struct stu{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在小组
    float score;  //成绩
} stu1 = { "Tom", 12, 18, 'A', 136.5 };
//结构体指针
struct stu *pstu = &stu1;
```

也可以在定义结构体的同时定义结构体指针：

```c
struct stu{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在小组
    float score;  //成绩
} stu1 = { "Tom", 12, 18, 'A', 136.5 }, *pstu = &stu1;
```



#### 获取结构体成员

通过结构体指针可以获取结构体成员，一般形式为：

```c
(*pointer).memberName
```

或者：

```c
pointer->memberName
```

第一种写法中，`.`的优先级高于`*`，`(*pointer)`两边的括号不能少。如果去掉括号写作`*pointer.memberName`，那么就等效于`*(pointer.memberName)`，这样意义就完全不对了。

第二种写法中，`->`是一个新的运算符，习惯称它为“箭头”，有了它，可以通过结构体指针直接取得结构体成员；这也是`->`在C语言中的唯一用途。

结构体指针的使用：

```c
#include <stdio.h>
int main(){
    struct{
        char *name;  //姓名
        int num;  //学号
        int age;  //年龄
        char group;  //所在小组
        float score;  //成绩
    } stu1 = { "Tom", 12, 18, 'A', 136.5 }, *pstu = &stu1;
    //读取结构体成员的值
    printf("%s的学号是%d，年龄是%d，在%c组，今年的成绩是%.1f！\n", (*pstu).name, (*pstu).num, (*pstu).age, (*pstu).group, (*pstu).score);
    printf("%s的学号是%d，年龄是%d，在%c组，今年的成绩是%.1f！\n", pstu->name, pstu->num, pstu->age, pstu->group, pstu->score);
    return 0;
}
```



#### 结构体指针作为函数参数

结构体变量名代表的是整个集合本身，作为函数参数时传递的整个集合，也就是所有成员，而不是像数组一样被编译器转换成一个指针。如果结构体成员较多，尤其是成员为数组时，传送的时间和空间开销会很大，影响程序的运行效率。所以最好的办法就是使用结构体指针，这时由实参传向形参的只是一个地址，非常快速。

计算全班学生的总成绩、平均成绩和以及 140 分以下的人数：

```c
#include <stdio.h>
struct stu{
    char *name;  //姓名
    int num;  //学号
    int age;  //年龄
    char group;  //所在小组
    float score;  //成绩
}stus[] = {
    {"Li ping", 5, 18, 'C', 145.0},
    {"Zhang ping", 4, 19, 'A', 130.5},
    {"He fang", 1, 18, 'A', 148.5},
    {"Cheng ling", 2, 17, 'F', 139.0},
    {"Wang ming", 3, 17, 'B', 144.5}
};
void average(struct stu *ps, int len);
int main(){
    int len = sizeof(stus) / sizeof(struct stu);
    average(stus, len);
    return 0;
}
void average(struct stu *ps, int len){
    int i, num_140 = 0;
    float average, sum = 0;
    for(i=0; i<len; i++){
        sum += (ps + i) -> score;
        if((ps + i)->score < 140) num_140++;
    }
    printf("sum=%.2f\naverage=%.2f\nnum_140=%d\n", sum, sum/5, num_140);
}
```



#### 枚举

枚举类型的定义形式为：

```c
enum typeName{ valueName1, valueName2, valueName3, ...... };
```

列出一个星期有几天：

```c
enum week{ Mon, Tues, Wed, Thurs, Fri, Sat, Sun };
```

枚举值默认从 0 开始，往后逐个加 1（递增）；也就是说，week 中的 Mon、Tues ...... Sun 对应的值分别为 0、1 ...... 6

也可以给每个名字都指定一个值:

```c
enum week{ Mon = 1, Tues = 2, Wed = 3, Thurs = 4, Fri = 5, Sat = 6, Sun = 7 };
```

枚举变量

```c
enum week{ Mon = 1, Tues, Wed, Thurs, Fri, Sat, Sun };
enum week a = Mon, b = Wed, c = Sat;
```

或者

```c
enum week{ Mon = 1, Tues, Wed, Thurs, Fri, Sat, Sun } a = Mon, b = Wed, c = Sat;
```

判断用户输入的是星期几:

```c
#include <stdio.h>
int main(){
    enum week{ Mon = 1, Tues, Wed, Thurs, Fri, Sat, Sun } day;
    scanf("%d", &day);
    switch(day){
        case Mon: puts("Monday"); break;
        case Tues: puts("Tuesday"); break;
        case Wed: puts("Wednesday"); break;
        case Thurs: puts("Thursday"); break;
        case Fri: puts("Friday"); break;
        case Sat: puts("Saturday"); break;
        case Sun: puts("Sunday"); break;
        default: puts("Error!");
    }
    return 0;
}
```

枚举和宏其实非常类似：宏在预处理阶段将名字替换成对应的值，枚举在编译阶段将名字替换成对应的值。我们可以将枚举理解为编译阶段的宏。

对于上面的代码，在编译的某个时刻会变成类似下面的样子：

```c
#include <stdio.h>
int main(){
    enum week{ Mon = 1, Tues, Wed, Thurs, Fri, Sat, Sun } day;
    scanf("%d", &day);
    switch(day){
        case 1: puts("Monday"); break;
        case 2: puts("Tuesday"); break;
        case 3: puts("Wednesday"); break;
        case 4: puts("Thursday"); break;
        case 5: puts("Friday"); break;
        case 6: puts("Saturday"); break;
        case 7: puts("Sunday"); break;
        default: puts("Error!");
    }
    return 0;
}
```

Mon、Tues、Wed 这些名字都被替换成了对应的数字。这意味着，Mon、Tues、Wed 等都不是变量，它们不占用数据区（常量区、全局数据区、栈区和堆区）的内存，而是直接被编译到命令里面，放到代码区，所以不能用`&`取得它们的地址。这就是枚举的本质。



### 共同体

共用体有时也被称为联合或者联合体，这也是 Union 这个单词的本意。

```c
union 共用体名{
    成员列表
};
```

结构体和共用体的区别在于：结构体的各个成员会占用不同的内存，互相之间没有影响；而共用体的所有成员占用同一段内存，修改一个成员会影响其余所有成员。

结构体占用的内存大于等于所有成员占用的内存的总和（成员之间可能会存在缝隙），共用体占用的内存等于最长的成员占用的内存。共用体使用了内存覆盖技术，同一时刻只能保存一个成员的值，如果对新的成员赋值，就会把原来成员的值覆盖掉。

共用体也是一种自定义类型，可以通过它来创建变量，例如：

```c
union data{
    int n;
    char ch;
    double f;
};
union data a, b, c;
```

或

```c
union data{
    int n;
    char ch;
    double f;
} a, b, c;
```

如果不再定义新的变量，也可以将共用体的名字省略：

```c
union{
    int n;
    char ch;
    double f;
} a, b, c;
```

共用体 data 中，成员 f 占用的内存最多，为 8 个字节，所以 data 类型的变量（也就是 a、b、c）也占用 8 个字节的内存。



#### 共用体的应用

共用体在一般的编程中应用较少，在单片机中应用较多。

对于 PC 机，经常使用到的一个实例是： 现有一张关于学生信息和教师信息的表格。学生信息包括姓名、编号、性别、职业、分数，教师的信息包括姓名、编号、性别、职业、教学科目。请看下面的表格：

| Name        | Num  | Sex  | Profession | Score / Course |
| ----------- | ---- | ---- | ---------- | -------------- |
| HanXiaoXiao | 501  | f    | s          | 89.5           |
| YanWeiMin   | 1011 | m    | t          | math           |
| LiuZhenTao  | 109  | f    | t          | English        |
| ZhaoFeiYan  | 982  | m    | s          | 95.0           |

f 和 m 分别表示女性和男性，s 表示学生，t 表示教师。可以看出，学生和教师所包含的数据是不同的。现在要求把这些信息放在同一个表格中，并设计程序输入人员信息然后输出。

如果把每个人的信息都看作一个结构体变量的话，那么教师和学生的前 4 个成员变量是一样的，第 5 个成员变量可能是 score 或者 course。

当第 4 个成员变量的值是 s 的时候，第 5 个成员变量就是 score;

当第 4 个成员变量的值是 t 的时候，第 5 个成员变量就是 course.

可以设计一个包含共用体的结构体:

```c
#include <stdio.h>
#include <stdlib.h>
#define TOTAL 4  //人员总数
struct{
    char name[20];
    int num;
    char sex;
    char profession;
    union{
        float score;
        char course[20];
    } sc;
} bodys[TOTAL];
int main(){
    int i;
    //输入人员信息
    for(i=0; i<TOTAL; i++){
        printf("Input info: ");
        scanf("%s %d %c %c", bodys[i].name, &(bodys[i].num), &(bodys[i].sex), &(bodys[i].profession));
        if(bodys[i].profession == 's'){  //如果是学生
            scanf("%f", &bodys[i].sc.score);
        }else{  //如果是老师
            scanf("%s", bodys[i].sc.course);
        }
        fflush(stdin);
    }
    //输出人员信息
    printf("\nName\t\tNum\tSex\tProfession\tScore / Course\n");
    for(i=0; i<TOTAL; i++){
        if(bodys[i].profession == 's'){  //如果是学生
            printf("%s\t%d\t%c\t%c\t\t%f\n", bodys[i].name, bodys[i].num, bodys[i].sex, bodys[i].profession, bodys[i].sc.score);
        }else{  //如果是老师
            printf("%s\t%d\t%c\t%c\t\t%s\n", bodys[i].name, bodys[i].num, bodys[i].sex, bodys[i].profession, bodys[i].sc.course);
        }
    }
    return 0;
}
```

运行结果：

```
Input info: HanXiaoXiao 501 f s 89.5↙
Input info: YanWeiMin 1011 m t math↙
Input info: LiuZhenTao 109 f t English↙
Input info: ZhaoFeiYan 982 m s 95.0↙

Name            Num     Sex     Profession      Score / Course
HanXiaoXiao     501     f       s               89.500000
YanWeiMin       1011    m       t               math
LiuZhenTao      109     f       t               English
ZhaoFeiYan      982     m       s               95.000000
```



### 大端模式和小端模式

1) 大端模式（Big-endian）是指将数据的低位（比如 1234 中的 34 就是低位）放在内存的高地址上，而数据的高位（比如 1234 中的 12 就是高位）放在内存的低地址上。这种存储模式有点儿类似于把数据当作字符串顺序处理，地址由小到大增加，而数据从高位往低位存放。

2) 小端模式（Little-endian）是指将数据的低位放在内存的低地址上，而数据的高位放在内存的高地址上。这种存储模式将地址的高低和数据的大小结合起来，高地址存放数值较大的部分，低地址存放数值较小的部分，这和我们的思维习惯是一致，比较容易理解。

#### 为什么有大小端模式之分

计算机中的数据是以字节（Byte）为单位存储的，每个字节都有不同的地址。现代 CPU 的位数（可以理解为一次能处理的数据的位数）都超过了 8 位（一个字节），PC机、服务器的 CPU 基本都是 64 位的，嵌入式系统或单片机系统仍然在使用 32 位和 16 位的 CPU.



对于一次能处理多个字节的CPU，必然存在着如何安排多个字节的问题，也就是大端和小端模式。

以 int 类型的 0x12345678 为例，它占用 4 个字节，如果是小端模式（Little-endian），那么在内存中的分布情况为（假设从地址 0x 4000 开始存放）：

| 内存地址 | 0x4000 | 0x4001 | 0x4002 | 0x4003 |
| -------- | ------ | ------ | ------ | ------ |
| 存放内容 | 0x78   | 0x56   | 0x34   | 0x12   |

如果是大端模式（Big-endian），那么分布情况正好相反：

| 内存地址 | 0x4000 | 0x4001 | 0x4002 | 0x4003 |
| -------- | ------ | ------ | ------ | ------ |
| 存放内容 | 0x12   | 0x34   | 0x56   | 0x78   |



借助共用体，我们可以检测 CPU 是大端模式还是小端模式

```c
#include <stdio.h>
int main(){
    union{
        int n;
        char ch;
    } data;
    data.n = 0x00000001;  //也可以直接写作 data.n = 1;
    if(data.ch == 1){
        printf("Little-endian\n");
    }else{
        printf("Big-endian\n");
    }
    return 0;
}
```



### 位域（位段）

有些数据在存储时并不需要占用一个完整的字节，只需要占用一个或几个二进制位即可。例如开关只有通电和断电两种状态，用 0 和 1 表示足以，也就是用一个二进位。正是基于这种考虑，C语言又提供了一种叫做位域的数据结构。

在结构体定义时，我们可以指定某个成员变量所占用的二进制位数（Bit），这就是位域。请看下面的例子：

```c
struct bs{
    unsigned m;
    unsigned n: 4;
    unsigned char ch: 6;
};
```

`:`后面的数字用来限定成员变量占用的位数。成员 m 没有限制，根据数据类型即可推算出它占用 4 个字节（Byte）的内存。成员 n、ch 被`:`后面的数字限制，不能再根据数据类型计算长度，它们分别占用 4、6 位（Bit）的内存。



#### 位域的存储

C语言标准并没有规定位域的具体存储方式，不同的编译器有不同的实现，但它们都尽量压缩存储空间。

位域的具体存储规则如下：

当相邻成员的类型相同时，如果它们的位宽之和小于类型的 sizeof 大小，那么后面的成员紧邻前一个成员存储，直到不能容纳为止；如果它们的位宽之和大于类型的 sizeof 大小，那么后面的成员将从新的存储单元开始，其偏移量为类型大小的整数倍。

以下面的位域 bs 为例：

```c
#include <stdio.h>
int main(){
    struct bs{
        unsigned m: 6;
        unsigned n: 12;
        unsigned p: 4;
    };
    printf("%d\n", sizeof(struct bs));
    return 0;
}
```

m、n、p 的类型都是 unsigned int，sizeof 的结果为 4 个字节（Byte），也即 32 个位（Bit）。m、n、p 的位宽之和为 6+12+4 = 22，小于 32，所以它们会挨着存储，中间没有缝隙。



当相邻成员的类型不同时，不同的编译器有不同的实现方案，[GCC](http://c.biancheng.net/gcc/) 会压缩存储，而 VC/VS 不会。

请看下面的位域 bs：

```c
#include <stdio.h>
int main(){
    struct bs{
        unsigned m: 12;
        unsigned char ch: 4;
        unsigned p: 4;
    };
    printf("%d\n", sizeof(struct bs));
    return 0;
}
```

在 GCC 下的运行结果为 4，三个成员挨着存储；在 VC/VS 下的运行结果为 12，三个成员按照各自的类型存储（与不指定位宽时的存储方式相同）。



如果成员之间穿插着非位域成员，那么不会进行压缩。例如对于下面的 bs：

```c
struct bs{
    unsigned m: 12;
    unsigned ch;
    unsigned p: 4;
};
```

在各个编译器下 sizeof 的结果都是 12

通过上面的分析，我们发现位域成员往往不占用完整的字节，有时候也不处于字节的开头位置，因此使用`&`获取位域成员的地址是没有意义的，C语言也禁止这样做。地址是字节（Byte）的编号，而不是位（Bit）的编号。



#### 无名位域

位域成员可以没有名称，只给出数据类型和位宽，如下所示：

```c
struct bs{
    int m: 12;
    int  : 20;  //该位域成员不能使用
    int n: 4;
};
```

无名位域一般用来作填充或者调整成员位置。因为没有名称，无名位域不能使用。

上面的例子中，如果没有位宽为 20 的无名成员，m、n 将会挨着存储，sizeof(struct bs) 的结果为 4；有了这 20 位作为填充，m、n 将分开存储，sizeof(struct bs) 的结果为 8.



### 位运算

对一个比特（Bit）位进行操作。比特（Bit）是一个电子元器件，8个比特构成一个字节（Byte），它已经是粒度最小的可操作单元了。

| 运算符 | &      | \|     | ^        | ~    | <<   | >>   |
| ------ | ------ | ------ | -------- | ---- | ---- | ---- |
| 说明   | 按位与 | 按位或 | 按位异或 | 取反 | 左移 | 右移 |



#### 按位与&

两个位都为 1 时，结果才为 1，否则为 0.

`&`是根据内存中的二进制位进行运算的，而不是数据的二进制形式；其他位运算符也一样。

#### 按位或|

有一个为 1 时，结果就为 1，两个都为 0 时结果才为 0.

#### 按位异或^

参与`^`运算两个二进制位不同时，结果为 1，相同时结果为 0。例如`0^1`为1，`0^0`为0，`1^1`为0

#### 取反~

取反运算符`~`为单目运算符，右结合性，作用是对参与运算的二进制位取反。例如`~1`为0，`~0`为1，这和逻辑运算中的`!`非常类似。

#### 左移<<

左移运算符`<<`用来把操作数的各个二进制位全部左移若干位，高位丢弃，低位补0

#### 右移>>

右移运算符`>>`用来把操作数的各个二进制位全部右移若干位，低位丢弃，高位补 0 或 1。如果数据的最高位是 0，那么就补 0；如果最高位是 1，那么就补 1。



#### 位运算对数据加密

数据加密解密的原理也很简单，就是使用异或运算。请先看下面的代码：

```c
#include <stdio.h>
#include <stdlib.h>
int main(){
    char plaintext = 'a';  // 明文
    char secretkey = '!';  // 密钥
    char ciphertext = plaintext ^ secretkey;  // 密文
    char decodetext = ciphertext ^ secretkey;  // 解密后的字符
    char buffer[9];
    printf("            char    ASCII\n");
    // itoa()用来将数字转换为字符串，可以设定转换时的进制（基数）
    // 这里将字符对应的ascii码转换为二进制
    printf(" plaintext   %c     %7s\n", plaintext, itoa(plaintext, buffer, 2));
    printf(" secretkey   %c     %7s\n", secretkey, itoa(secretkey, buffer, 2));
    printf("ciphertext   %c     %7s\n", ciphertext, itoa(ciphertext, buffer, 2));
    printf("decodetext   %c     %7s\n", decodetext, itoa(decodetext, buffer, 2));
    return 0;
}
```

运行结果：

```
            char    ASCII
 plaintext   a     1100001
 secretkey   !      100001
ciphertext   @     1000000
decodetext   a     1100001
```

两次异或运算后还是原来的结果。

这就是加密的关键技术：

- 通过一次异或运算，生成密文，密文没有可读性，与原文风马牛不相及，这就是加密；
- 密文再经过一次异或运算，就会还原成原文，这就是解密的过程；
- 加密和解密需要相同的密钥，如果密钥不对，是无法成功解密的。

上面的加密算法称为对称加密算法，加密和解密使用同一个密钥。

如果加密和解密的密钥不同，则称为非对称加密算法。在非对称算法中，加密的密钥称为公钥，解密的密钥称为私钥，只知道公钥是无法解密的，还必须知道私钥。

>  注意：程序中的 itoa() 位于 stdlib.h 头文件，它并不是一个标准的C函数，只有Windows下有。





 























