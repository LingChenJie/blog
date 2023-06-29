# C++语言

## 从C到C++

### 命令空间

为了解决合作开发时的命名冲突问题，[C++](http://c.biancheng.net/cplus/) 引入了**命名空间（Namespace）**的概念。

namespace 是C++中的关键字，用来定义一个命名空间，语法格式为：

```c++
namespace name{
    //variables, functions, classes
}
```



例子：

```c++
namespace Li{  //小李的变量定义
    FILE* fp = NULL;
}
namespace Han{  //小韩的变量定义
    FILE* fp = NULL;
}
```

使用变量、函数时要指明它们所在的命名空间。以上面的 fp 变量为例，可以这样来使用：

```c++
Li::fp = fopen("one.txt", "r");  //使用小李定义的变量 fp
Han::fp = fopen("two.txt", "rb+");  //使用小韩定义的变量 fp
```

`::`是一个新符号，称为域解析操作符，在C++中用来指明要使用的命名空间。



除了直接使用域解析操作符，还可以采用 u[sin](http://c.biancheng.net/ref/sin.html)g 关键字声明，例如：

```c++
using Li::fp;
fp = fopen("one.txt", "r");  //使用小李定义的变量 fp
Han :: fp = fopen("two.txt", "rb+");  //使用小韩定义的变量 fp
```

在代码的开头用`using`声明了 Li::fp，它的意思是，using 声明以后的程序中如果出现了未指明命名空间的 fp，就使用 Li::fp；但是若要使用小韩定义的 fp，仍然需要 Han::fp



using 声明不仅可以针对命名空间中的一个变量，也可以用于声明整个命名空间，例如：

```c
using namespace Li;
fp = fopen("one.txt", "r");  //使用小李定义的变量 fp
Han::fp = fopen("two.txt", "rb+");  //使用小韩定义的变量 fp
```

如果命名空间 Li 中还定义了其他的变量，那么同样具有 fp 变量的效果。在 using 声明后，如果有未具体指定命名空间的变量产生了命名冲突，那么默认采用命名空间 Li 中的变量。



```c
#include <stdio.h>
//将类定义在命名空间中
namespace Diy{
    class Student{
    public:
        char *name;
        int age;
        float score;
  
    public:
        void say(){
            printf("%s的年龄是 %d，成绩是 %f\n", name, age, score);
        }
    };
}
int main(){
    Diy::Student stu1;
    stu1.name = "小明";
    stu1.age = 15;
    stu1.score = 92.5f;
    stu1.say();
    return 0;
}
```



### 头文件和std命令空间

[C++](http://c.biancheng.net/cplus/) 是在C语言的基础上开发的，早期的 C++ 还不完善，不支持命名空间，没有自己的编译器，而是将 C++ 代码翻译成C代码，再通过C编译器完成编译。这个时候的 C++ 仍然在使用C语言的库，stdio.h、stdlib.h、string.h 等头文件依然有效；此外 C++ 也开发了一些新的库，增加了自己的头文件，例如：

- iostream.h：用于控制台输入输出头文件。
- fstream.h：用于文件操作的头文件。
- complex.h：用于复数计算的头文件。

和C语言一样，C++ 头文件仍然以`.h`为后缀，它们所包含的类、函数、宏等都是全局范围的。

后来 C++ 引入了命名空间的概念，计划重新编写库，将类、函数、宏等都统一纳入一个命名空间，这个命名空间的名字就是`std`。std 是 standard 的缩写，意思是“标准命名空间”。

为了避免头文件重名，新版 C++ 库也对头文件的命名做了调整，去掉了后缀`.h`，所以老式 C++ 的`iostream.h`变成了`iostream`，`fstream.h`变成了`fstream`。而对于原来C语言的头文件，也采用同样的方法，但在每个名字前还要添加一个`c`字母，所以C语言的`stdio.h`变成了`cstdio`，`stdlib.h`变成了`cstdlib`。



 C++ 头文件的现状：

1) 旧的 C++ 头文件，如 iostream.h、fstream.h 等将会继续被支持，尽管它们不在官方标准中。这些头文件的内容不在命名空间 std 中。

2) 新的 C++ 头文件，如 iostream、fstream 等包含的基本功能和对应的旧版头文件相似，但头文件的内容在命名空间 std 中。

> ​	注意：在标准化的过程中，库中有些部分的细节被修改了，所以旧的头文件和新的头文件不一定完全对应。

3) 标准C头文件如 stdio.h、stdlib.h 等继续被支持。头文件的内容不在 std 中。

4) 具有C库功能的新C++头文件具有如 cstdio、cstdlib 这样的名字。它们提供的内容和相应的旧的C头文件相同，只是内容在 std 中。



对于不带`.h`的头文件，所有的符号都位于命名空间 std 中，使用时需要声明命名空间 std；对于带`.h`的头文件，没有使用任何命名空间，所有符号都位于全局作用域。这也是 C++ 标准所规定的。

不过现实情况和 C++ 标准所期望的有些不同，对于原来C语言的头文件，即使按照 C++ 的方式来使用，即`#include <cstdio>`这种形式，那么符号可以位于命名空间 std 中，也可以位于全局范围中，请看下面的两段代码。

1) 使用命名空间 std：

```c
#include <cstdio>
int main(){
    std::printf("http://c.biancheng.net\n");
    return 0;
}
```

2) 不使用命名空间 std：

```c
#include <cstdio>
int main(){
    printf("http://c.biancheng.net\n");
    return 0;
}
```

第 1) 种写法是标准的，第 2) 种不标准，虽然它们在目前的编译器中都没有错误，依然推荐使用第 1) 种写法。



#### 使用C++头文件

虽然 C++ 几乎完全兼容C语言，C语言的头文件在 C++ 中依然被支持，但 C++ 新增的库更加强大和灵活，尽量使用这些 C++ 新增的头文件，例如 iostream、fstream、string 等。

```c
#include <iostream>
#include <string>
int main(){
    //声明命名空间std
    using namespace std;
   
    //定义字符串变量
    string str;
    //定义 int 变量
    int age;
    //从控制台获取用户输入
    cin>>str>>age;
    //将数据输出到控制台
    cout<<str<<"已经成立"<<age<<"年了！"<<endl;
    return 0;
}
```

string 是 C++ 中的字符串类，可以将 string 看做一种内置的数据类型，就像 int、float 等，可以用来定义变量。cin 用于从控制台获取用户输入，cout 用于将数据输出到控制台。



在 main() 函数中声明命名空间 std，它的作用范围就位于 main() 函数内部，如果在其他函数中又用到了 std，就需要重新声明:

```c
#include <iostream>
void func(){
    //必须重新声明
    using namespace std;
    cout<<"http://c.biancheng.net"<<endl;
}
int main(){
    //声明命名空间std
    using namespace std;
   
    cout<<"C语言中文网"<<endl;
    func();
    return 0;
}
```

如果希望在所有函数中都使用命名空间 std，可以将它声明在全局范围中，例如：

```c
#include <iostream>
//声明命名空间std
using namespace std;
void func(){
    cout<<"c++biancheng"<<endl;
}
int main(){
    cout<<"C++语言"<<endl;
    func();
    return 0;
}
```



### 输入和输出

iostream 是 Input Output Stream 的缩写，意思是“输入输出流”。

cout 和 cin 都是 C++ 的内置对象，而不是关键字。C++ 库定义了大量的类（Class），程序员可以使用它们来创建对象，cout 和 cin 就分别是 ostream 和 istream 类的对象，只不过它们是由标准库的开发者提前创建好的，可以直接拿来使用。这种在 C++ 中提前创建好的对象称为内置对象。

使用 cout 进行输出时需要紧跟`<<`运算符，使用 cin 进行输入时需要紧跟`>>`运算符，这两个运算符可以自行分析所处理的数据类型，因此无需像使用 scanf 和 printf 那样给出格式控制字符串。

```c
#include<iostream>
using namespace std;
int main(){
    int x;
    float y;
    cout<<"Please input an int number:"<<endl;
    cin>>x;
    cout<<"The int number is x= "<<x<<endl;
    cout<<"Please input a float number:"<<endl;
    cin>>y;
    cout<<"The float number is y= "<<y<<endl;   
    return 0;
}
```



### 布尔类型bool

在C语言中，关系运算和逻辑运算的结果有两种，真和假：0 表示假，非 0 表示真。例如：

```c
#include <stdio.h>
int main(){
    int a, b, flag;
    scanf("%d %d", &a, &b);
    flag = a > b;  //flag保存关系运算结果
    printf("flag = %d\n", flag);
    
    return 0;
}
```

C++ 新增了 **bool 类型（布尔类型）**，它一般占用 1 个字节长度。bool 类型只有两个取值，true 和 false：true 表示“真”，false 表示“假”。

```c
#include <iostream>
using namespace std;
int main(){
    int a, b;
    bool flag;  //定义布尔变量
    cin>>a>>b;
    flag = a > b;
    cout<<"flag = "<<flag<<endl;
    return 0;
}
```



### const

C语言对 const 的处理和普通变量一样，会到内存中读取数据；

C++ 对 const 的处理更像是编译时期的`#define`，是一个值替换的过程。



#### C++中全局 const 变量的可见范围是当前文件

C++ 规定，全局 const 变量的作用域仍然是当前文件，但是它在其他文件中是不可见的，这和添加了`static`关键字的效果类似。

由于 C++ 中全局 const 变量的可见范围仅限于当前源文件，所以可以将它放在头文件中，这样即使头文件被包含多次也不会出错，请看下面的例子。

module.h ：

```c
const int n = 10;
void func();
```

module.cpp:

```c
#include <stdio.h>
#include "module.h"
void func(){
    printf("module: %d\n", n);
}
```



main.cpp:

```c
#include <stdio.h>
#include "module.h"
int main(){
    func();
    printf("main: %d\n", n);
    return 0;
}
```

C和C++中全局 const 变量的作用域相同，都是当前文件，不同的是它们的可见范围：C语言中 const 全局变量的可见范围是整个程序，在其他文件中使用 extern 声明后就可以使用；而C++中 const 全局变量的可见范围仅限于当前文件，在其他文件中不可见，所以它可以定义在头文件中，多次引入后也不会出错。





### new和delete

在C语言中，动态分配内存用 malloc() 函数，释放内存用 free() 函数。

```c
int *p = (int*) malloc( sizeof(int) * 10 );  //分配10个int型的内存空间
free(p);  //释放内存
```

在[C++](http://c.biancheng.net/cplus/)中，这两个函数仍然可以使用，但是C++又新增了两个关键字，new 和 delete：new 用来动态分配内存，delete 用来释放内存。



用 new 和 delete 分配内存更加简单：

```c
int *p = new int;  //分配1个int型的内存空间
delete p;  //释放内存
```

new 操作符会根据后面的数据类型来推断所需空间的大小。



如果希望分配一组连续的数据，可以使用 new[]：

```c
int *p = new int[10];  //分配10个int型的内存空间
delete[] p;
```

用 new[] 分配的内存需要用 delete[] 释放，它们是一一对应的。





### inline内联函数

为了消除函数调用的时空开销，C++ 提供一种提高效率的方法，即在编译时将函数调用处用函数体替换，类似于C语言中的宏展开。这种在函数调用处直接嵌入函数体的函数称为内联函数（Inline Function），又称内嵌函数或者内置函数。

指定内联函数的方法很简单，只需要在函数定义处增加 inline 关键字。

```c
#include <iostream>
using namespace std;
//内联函数，交换两个数的值
inline void swap(int *a, int *b){
    int temp;
    temp = *a;
    *a = *b;
    *b = temp;
}

int main(){
    int m, n;
    cin>>m>>n;
    cout<<m<<", "<<n<<endl;
    swap(&m, &n);
    cout<<m<<", "<<n<<endl;
    return 0;
}
```

使用内联函数的缺点也是非常明显的，编译后的程序会存在多份相同的函数拷贝，如果被声明为内联函数的函数体非常大，那么编译后的程序体积也将会变得很大，所以再次强调，一般只将那些短小的、频繁调用的函数声明为内联函数。



### 内联函数可以用来代替宏

```c
#include <iostream>
using namespace std;
inline int SQ(int y){ return y*y; }
int main(){
    int n, sq;
    cin>>n;
    //SQ(n)
    sq = SQ(n);
    cout<<sq<<endl;
    //SQ(n+1)
    sq = SQ(n+1);
    cout<<sq<<endl;
    //200 / SQ(n+1)
    sq = 200 / SQ(n+1);
    cout<<sq<<endl;
    return 0;
}
```

和宏一样，内联函数可以定义在头文件中（不用加 static 关键字），并且头文件被多次`#include`后也不会引发重复定义错误。这一点和非内联函数不同，非内联函数是禁止定义在头文件中的，它所在的头文件被多次`#include`后会引发重复定义错误。

内联函数在编译时会将函数调用处用函数体替换，编译完成后函数就不存在了，所以在链接时不会引发重复定义错误。这一点和宏很像，宏在预处理时被展开，编译时就不存在了。从这个角度讲，内联函数更像是编译期间的宏。

内联函数主要有两个作用，一是消除函数调用时的开销，二是取代带参数的宏。不过我更倾向于后者，取代带参数的宏更能凸显内联函数存在的意义。



### 规范地使用内联函数

内联函数不应该有声明，应该将函数定义放在本应该出现函数声明的地方，这是一种良好的编程风格。

在多文件编程中，我们通常将函数的定义放在源文件中，将函数的声明放在头文件中，希望调用函数时，引入对应的头文件即可。但这种做法不适用于内联函数，将内联函数的声明和定义分散到不同的文件中会出错。

内联函数虽然叫做函数，在定义和声明的语法上也和普通函数一样，但它已经失去了函数的本质。函数是一段可以重复使用的代码，它位于虚拟地址空间中的代码区，也占用可执行文件的体积，而内联函数的代码在编译后就被消除了，不存在于虚拟地址空间中，没法重复使用。





### 函数的默认参数

定义函数时可以给形参指定一个默认的值，这样调用函数时如果没有给这个形参赋值（没有对应的实参），那么就使用这个默认的值。

```c
#include<iostream>
using namespace std;
//带默认参数的函数
void func(int n, float b=1.2, char c='@'){
    cout<<n<<", "<<b<<", "<<c<<endl;
}

int main(){
    //为所有参数传值
    func(10, 3.5, '#');
    //为n、b传值，相当于调用func(20, 9.8, '@')
    func(20, 9.8);
    //只为n传值，相当于调用func(30, 1.2, '@')
    func(30);
    return 0;
}
```





### 函数重载

++ 允许多个函数拥有相同的名字，只要它们的参数列表不同就可以，这就是函数的重载（Function Overloading）。借助重载，一个函数名可以有多种用途。

```c
#include <iostream>
using namespace std;

//交换 int 变量的值
void Swap(int *a, int *b){
    int temp = *a;
    *a = *b;
    *b = temp;
}

//交换 float 变量的值
void Swap(float *a, float *b){
    float temp = *a;
    *a = *b;
    *b = temp;
}

//交换 char 变量的值
void Swap(char *a, char *b){
    char temp = *a;
    *a = *b;
    *b = temp;
}

//交换 bool 变量的值
void Swap(bool *a, bool *b){
    char temp = *a;
    *a = *b;
    *b = temp;
}

int main(){
    //交换 int 变量的值
    int n1 = 100, n2 = 200;
    Swap(&n1, &n2);
    cout<<n1<<", "<<n2<<endl;
   
    //交换 float 变量的值
    float f1 = 12.5, f2 = 56.93;
    Swap(&f1, &f2);
    cout<<f1<<", "<<f2<<endl;
   
    //交换 char 变量的值
    char c1 = 'A', c2 = 'B';
    Swap(&c1, &c2);
    cout<<c1<<", "<<c2<<endl;
   
    //交换 bool 变量的值
    bool b1 = false, b2 = true;
    Swap(&b1, &b2);
    cout<<b1<<", "<<b2<<endl;

    return 0;
}
```

函数的重载的规则：

- 函数名称必须相同。
- 参数列表必须不同（个数不同、类型不同、参数排列顺序不同等）。
- 函数的返回类型可以相同也可以不相同。
- 仅仅返回类型不同不足以成为函数的重载。



#### C++ 是如何做到函数重载的

C++代码在编译时会根据参数列表对函数进行重命名，例如`void Swap(int a, int b)`会被重命名为`_Swap_int_int`，`void Swap(float x, float y)`会被重命名为`_Swap_float_float`。当发生函数调用时，编译器会根据传入的实参去逐个匹配，以选择对应的函数，如果匹配失败，编译器就会报错，这叫做重载决议（Overload Resolution）。

> 不同的编译器有不同的重命名方式，这里仅仅举例说明，实际情况可能并非如此。

从这个角度讲，函数重载仅仅是语法层面的，本质上它们还是不同的函数，占用不同的内存，入口地址也不一样。





### C++和C的混合编程

C++ 就是在 C 语言的基础上增加了一些新特性，从大的方面讲，C++ 不仅支持面向过程编程，还支持面向对象编程和泛型编程；从小的方面讲，C++ 还支持命名空间、函数重载、内联函数等。

C++ 和 C 可以进行混合编程。但需要注意的是，由于 C++ 和 C 在程序的编译、链接等方面都存在一定的差异，而这些差异往往会导致程序运行失败。



如下就是一个用 C++ 和 C 混合编程实现的实例项目：

```c
//myfun.h
void display();
//myfun.c
#include <stdio.h>
#include "myfun.h"
void display(){
   printf("C++：http://c.biancheng/net/cplus/");
}

//main.cpp
#include <iostream>
#include "myfun.h"
using namespace std;
int main(){
   display();
   return 0;
}
```

在此项目中，主程序是用 C++ 编写的，而 display() 函数的定义是用 C 语言编写的。从表面上看，这个项目很完整，我们可以尝试运行它：

```
In function `main': undefined reference to `display()'
```

如上是调用 GCC 编译器运行此项目时给出的错误信息，指的是编译器无法找到 main.cpp 文件中 display() 函数的实现代码。导致此错误的原因，就是因为 C++ 和 C 编译程序的方式存在差异。

之所以 C++ 支持函数的重载，是因为 C++ 会在程序的编译阶段对函数的函数名进行“再次重命名”。

但是，C 语言是不支持函数重载的，它不会在编译阶段对函数的名称做较大的改动。仍以 void Swap(int a, int b) 和 void Swap(float x, float y) 为例，若以 C 语言的标准对它们进行编译，两个函数的函数名将都是`Swap`。



#### extern "C"

extern 是 C 和 C++ 的一个关键字，但对于 extern "C"，读者大可以将其看做一个整体，和 extern 毫无关系。

extern "C" 既可以修饰一句 C++ 代码，也可以修饰一段 C++ 代码，它的功能是让编译器以处理 C 语言代码的方式来处理修饰的 C++ 代码。

在实际开发中，对于解决 C++ 和 C 混合编程的问题，通常在头文件中使用如下格式：

```c
#ifdef __cplusplus
extern "C" {
#endif
  
void display();
  
#ifdef __cplusplus
}
#endif
```

由此可以看出，extern "C" 大致有 2 种用法，当仅修饰一句 C++ 代码时，直接将其添加到该函数代码的开头即可；如果用于修饰一段 C++ 代码，只需为 extern "C" 添加一对大括号`{}`，并将要修饰的代码囊括到括号内即可。





## 类和对象

类是创建对象的模板，一个类可以创建多个对象，每个对象都是类类型的一个变量；创建对象的过程也叫类的实例化。每个对象都是类的一个具体实例（Ins[tan](http://c.biancheng.net/ref/tan.html)ce），拥有类的成员变量和成员函数。

与结构体一样，类只是一种复杂数据类型的声明，不占用内存空间。而对象是类这种数据类型的一个变量，或者说是通过类这种数据类型创建出来的一份实实在在的数据，所以占用内存空间。



### 类的定义

类是用户自定义的类型，如果程序中要用到类，必须提前说明，或者使用已存在的类（别人写好的类、标准库中的类等）。

一个简单的类的定义：

```c
class Student{
public:
    //成员变量
    char *name;
    int age;
    float score;
    //成员函数
    void say(){
        cout<<name<<"的年龄是"<<age<<"，成绩是"<<score<<endl;
    }
};
```

`class`是 C++ 中新增的关键字，专门用来定义类。

`Student`是类的名称；类名的首字母一般大写，以和其他的标识符区分开。

`{ }`内部是类所包含的成员变量和成员函数，它们统称为类的成员（Member）；

由`{ }`包围起来的部分有时也称为类体，和函数体的概念类似。

`public`也是 C++ 的新增关键字，它只能用在类的定义中，表示类的成员变量或成员函数具有“公开”的访问权限。



### 创建对象

有了 Student 类后，就可以通过它来创建对象了，例如：

```c
Student liLei;  //创建对象
```

我们可以把 Student 看做一种新的数据类型，把 liLei 看做一个变量。



在创建对象时，class 关键字可要可不要，但是出于习惯我们通常会省略掉 class 关键字，例如：

```c++
class Student LiLei;  //正确
Student LiLei;  //同样正确
```

除了创建单个对象，还可以创建对象数组：

```c++
Student allStu[100];
```

该语句创建了一个 allStu 数组，它拥有100个元素，每个元素都是 Student 类型的对象。



### 访问类的成员

```c++
int main(){
    //创建对象
    Student stu;
    stu.name = "小明";
    stu.age = 15;
    stu.score = 92.5f;
    stu.say();
    return 0;
}
```



### 对象指针

上面代码中创建的对象 stu 在栈上分配内存，需要使用`&`获取它的地址，例如：

```c
Student stu;
Student *pStu = &stu;
```

pStu 是一个指针，它指向 Student 类型的数据，也就是通过 Student 创建出来的对象。



当然，你也可以在堆上创建对象，这个时候就需要使用前面讲到的`new`关键字，例如：

```c++
Student *pStu = new Student;
```

在栈上创建出来的对象都有一个名字，比如 stu，使用指针指向它不是必须的。

但是通过 new 创建出来的对象就不一样了，它在堆上分配内存，没有名字，只能得到一个指向它的指针，所以必须使用一个指针变量来接收这个指针，否则以后再也无法找到这个对象了，更没有办法使用它。也就是说，使用 new 在堆上创建出来的对象是匿名的，没法直接使用，必须要用一个指针指向它，再借助指针来访问它的成员变量或成员函数。

栈内存是程序自动管理的，不能使用 delete 删除在栈上创建的对象；堆内存由程序员管理，对象使用完毕后可以通过 delete 删除。在实际开发中，new 和 delete 往往成对出现，以保证及时删除不再使用的对象，防止无用内存堆积。

有了对象指针后，可以通过箭头`->`来访问对象的成员变量和成员函数，这和通过[结构体指针](http://c.biancheng.net/view/246.html)来访问它的成员类似。

```c
pStu -> name = "小明";
pStu -> age = 15;
pStu -> score = 92.5f;
pStu -> say();
```



类的成员函数也和普通函数一样，都有返回值和参数列表，它与一般函数的区别是：成员函数是一个类的成员，出现在类体中，它的作用范围由类来决定；而普通函数是独立的，作用范围是全局的，或位于某个命名空间内。



可以只在类体中声明函数，而将函数定义放在类体外面，如下所示：

```c++
class Student{
public:
    //成员变量
    char *name;
    int age;
    float score;
    //成员函数
    void say();  //函数声明
};

//函数定义
void Student::say(){
    cout<<name<<"的年龄是"<<age<<"，成绩是"<<score<<endl;
}
```

当成员函数定义在类外时，就必须在函数名前面加上类名予以限定。`::`被称为域解析符（也称作用域运算符或作用域限定符），用来连接类名和函数名，指明当前函数属于哪个类。



### 在类体中和类体外定义成员函数的区别

在类体中和类体外定义成员函数是有区别的：在类体中定义的成员函数会自动成为内联函数，在类体外定义的不会。当然，在类体内部定义的函数也可以加 inline 关键字，但这是多余的，因为类体内部定义的函数默认就是内联函数。

内联函数一般不是我们所期望的，它会将函数调用处用函数体替代，建议在类体内部对成员函数作声明，而在类体外部进行定义，这是一种良好的编程习惯。

如果希望将函数定义在类体外部，又希望它是内联函数，那么可以在定义函数时加 inline 关键字。

下面是一个将内联函数定义在类外部的例子：

```c++
class Student{
public:
    char *name;
    int age;
    float score;
    void say();  //内联函数声明，可以增加 inline 关键字，但编译器会忽略
};

//函数定义
inline void Student::say(){
    cout<<name<<"的年龄是"<<age<<"，成绩是"<<score<<endl;
}
```



### 类成员的访问权限以及类的封装

C++ 中的 public、private、protected 只能修饰类的成员，不能修饰类，C++中的类没有共有私有之分。

```c++
#include <iostream>
using namespace std;
//类的声明
class Student{
private:  //私有的
    char *m_name;
    int m_age;
    float m_score;
public:  //共有的
    void setname(char *name);
    void setage(int age);
    void setscore(float score);
    void show();
};
//成员函数的定义
void Student::setname(char *name){
    m_name = name;
}
void Student::setage(int age){
    m_age = age;
}
void Student::setscore(float score){
    m_score = score;
}
void Student::show(){
    cout<<m_name<<"的年龄是"<<m_age<<"，成绩是"<<m_score<<endl;
}
int main(){
    //在栈上创建对象
    Student stu;
    stu.setname("小明");
    stu.setage(15);
    stu.setscore(92.5f);
    stu.show();
    //在堆上创建对象
    Student *pstu = new Student;
    pstu -> setname("李华");
    pstu -> setage(16);
    pstu -> setscore(96);
    pstu -> show();
    return 0;
}
```

类的声明和成员函数的定义都是类定义的一部分，在实际开发中，我们通常将类的声明放在头文件中，而将成员函数的定义放在源文件中。

成员变量大都以`m_`开头，这是约定成俗的写法，不是语法规定的内容。以`m_`开头既可以一眼看出这是成员变量，又可以和成员函数中的形参名字区分开。

以 setname() 为例，如果将成员变量`m_name`的名字修改为`name`，那么 setname() 的形参就不能再叫`name`了，得换成诸如`name1`、`_name`这样没有明显含义的名字，否则`name=name;`这样的语句就是给形参`name`赋值，而不是给成员变量`name`赋值。



### 构造函数

```c++
#include <iostream>
using namespace std;
class Student{
private:
    char *m_name;
    int m_age;
    float m_score;
public:
    //声明构造函数
    Student(char *name, int age, float score);
    //声明普通成员函数
    void show();
};
//定义构造函数
Student::Student(char *name, int age, float score){
    m_name = name;
    m_age = age;
    m_score = score;
}
//定义普通成员函数
void Student::show(){
    cout<<m_name<<"的年龄是"<<m_age<<"，成绩是"<<m_score<<endl;
}
int main(){
    //创建对象时向构造函数传参
    Student stu("小明", 15, 92.5f);
    stu.show();
    //创建对象时向构造函数传参
    Student *pstu = new Student("李华", 16, 96);
    pstu -> show();
    return 0;
}
```



### 构造函数初始化列表

构造函数的初始化列表使得代码更加简洁

```c++
#include <iostream>
using namespace std;
class Student{
private:
    char *m_name;
    int m_age;
    float m_score;
public:
    Student(char *name, int age, float score);
    void show();
};
//采用初始化列表
Student::Student(char *name, int age, float score): m_name(name), m_age(age), m_score(score){
    //TODO:
}
void Student::show(){
    cout<<m_name<<"的年龄是"<<m_age<<"，成绩是"<<m_score<<endl;
}
int main(){
    Student stu("小明", 15, 92.5f);
    stu.show();
    Student *pstu = new Student("李华", 16, 96);
    pstu -> show();
    return 0;
}
```

如本例所示，定义构造函数时并没有在函数体中对成员变量一一赋值，其函数体为空（当然也可以有其他语句），而是在函数首部与函数体之间添加了一个冒号`:`，后面紧跟`m_name(name), m_age(age), m_score(score)`语句，这个语句的意思相当于函数体内部的`m_name = name; m_age = age; m_score = score;`语句，也是赋值的意思。



### 初始化 const 成员变量

构造函数初始化列表还有一个很重要的作用，那就是初始化 const 成员变量。初始化 const 成员变量的唯一方法就是使用初始化列表。例如 VS/VC 不支持变长数组（数组长度不能是变量），我们自己定义了一个 VLA 类，用于模拟变长数组，请看下面的代码：

```c++
class VLA{
private:
    const int m_len;
    int *m_arr;
public:
    VLA(int len);
};
//必须使用初始化列表来初始化 m_len
VLA::VLA(int len): m_len(len){
    m_arr = new int[len];
}
```



### 析构函数

创建对象时系统会自动调用构造函数进行初始化工作，同样，销毁对象时系统也会自动调用一个函数来进行清理工作，例如释放分配的内存、关闭打开的文件等，这个函数就是析构函数。

析构函数（Destructor）也是一种特殊的成员函数，没有返回值，不需要程序员显式调用（程序员也没法显式调用），而是在销毁对象时自动执行。构造函数的名字和类名相同，而析构函数的名字是在类名前面加一个`~`符号。

我们定义了一个 VLA 类来模拟变长数组，它使用一个构造函数为数组分配内存，这些内存在数组被销毁后不会自动释放，所以非常有必要再添加一个析构函数，专门用来释放已经分配的内存。请看下面的完整示例：

```c++
#include <iostream>
using namespace std;

class VLA{
public:
    VLA(int len);  //构造函数
    ~VLA();  //析构函数
public:
    void input();  //从控制台输入数组元素
    void show();  //显示数组元素
private:
    int *at(int i);  //获取第i个元素的指针
private:
    const int m_len;  //数组长度
    int *m_arr; //数组指针
    int *m_p;  //指向数组第i个元素的指针
};

VLA::VLA(int len): m_len(len){  //使用初始化列表来给 m_len 赋值
    if(len > 0){ m_arr = new int[len];  /*分配内存*/ }
    else{ m_arr = NULL; }
}
VLA::~VLA(){
    delete[] m_arr;  //释放内存
}
void VLA::input(){
    for(int i=0; m_p=at(i); i++){ cin>>*at(i); }
}
void VLA::show(){
    for(int i=0; m_p=at(i); i++){
        if(i == m_len - 1){ cout<<*at(i)<<endl; }
        else{ cout<<*at(i)<<", "; }
    }
}
int * VLA::at(int i){
    if(!m_arr || i<0 || i>=m_len){ return NULL; }
    else{ return m_arr + i; }
}

int main(){
    //创建一个有n个元素的数组（对象）
    int n;
    cout<<"Input array length: ";
    cin>>n;
    VLA *parr = new VLA(n);
    //输入数组元素
    cout<<"Input "<<n<<" numbers: ";
    parr -> input();
    //输出数组元素
    cout<<"Elements: ";
    parr -> show();
    //删除数组（对象）
    delete parr;

    return 0;
}
```



#### 析构函数的执行时机

析构函数在对象被销毁时调用，而对象的销毁时机与它所在的内存区域有关。

在所有函数之外创建的对象是全局对象，它和全局变量类似，位于内存分区中的全局数据区，程序在结束执行时会调用这些对象的析构函数。

在函数内部创建的对象是局部对象，它和局部变量类似，位于栈区，函数执行结束时会调用这些对象的析构函数。

new 创建的对象位于堆区，通过 delete 删除时才会调用析构函数；如果没有 delete，析构函数就不会被执行。

下面的例子演示了析构函数的执行。

```c++
#include <iostream>
#include <string>
using namespace std;
class Demo{
public:
    Demo(string s);
    ~Demo();
private:
    string m_s;
};
Demo::Demo(string s): m_s(s){ }
Demo::~Demo(){ cout<<m_s<<endl; }
void func(){
    //局部对象
    Demo obj1("1");
}
//全局对象
Demo obj2("2");
int main(){
    //局部对象
    Demo obj3("3");
    //new创建的对象
    Demo *pobj4 = new Demo("4");
    func();
    cout<<"main"<<endl;
  
    return 0;
}
```



运行结果：

```
1
main
3
2
```





### static静态成员变量

在[C++](http://c.biancheng.net/cplus/)中，我们可以使用静态成员变量来实现多个对象共享数据的目标。静态成员变量是一种特殊的成员变量，它被关键字`static`修饰，例如：

```c++
class Student{
public:
    Student(char *name, int age, float score);
    void show();
public:
    static int m_total;  //静态成员变量
private:
    char *m_name;
    int m_age;
    float m_score;
};
```

这段代码声明了一个静态成员变量 m_total，用来统计学生的人数。

static 成员变量属于类，不属于某个具体的对象，即使创建多个对象，也只为 m_total 分配一份内存，所有对象使用的都是这份内存中的数据。当某个对象修改了 m_total，也会影响到其他对象。



static 成员变量必须在类声明的外部初始化，具体形式为：

```
type class::name = value;
```

将上面的 m_total 初始化：

```c++
int Student::m_total = 0;
```

静态成员变量在初始化时不能再加 static，但必须要有数据类型。被 private、protected、public 修饰的静态成员变量都可以用这种方式初始化。



static 成员变量的内存既不是在声明类时分配，也不是在创建对象时分配，而是在（类外）初始化时分配。反过来说，没有在类外初始化的 static 成员变量不能使用。

static 成员变量既可以通过对象来访问，也可以通过类来访问。

```c++
//通过类类访问 static 成员变量
Student::m_total = 10;
//通过对象来访问 static 成员变量
Student stu("小明", 15, 92.5f);
stu.m_total = 20;
//通过对象指针来访问 static 成员变量
Student *pstu = new Student("李华", 16, 96);
pstu -> m_total = 20;
```





### static静态成员函数

static 除了可以声明静态成员变量，还可以声明静态成员函数。普通成员函数可以访问所有成员（包括成员变量和成员函数），静态成员函数只能访问静态成员。

编译器在编译一个普通成员函数时，会隐式地增加一个形参 this，并把当前对象的地址赋值给 this，所以普通成员函数只能在创建对象后通过对象来调用，因为它需要当前对象的地址。而静态成员函数可以通过类来直接调用，编译器不会为它增加形参 this，它不需要当前对象的地址，所以不管有没有创建对象，都可以调用静态成员函数。

静态成员函数与普通成员函数的根本区别在于：普通成员函数有 this 指针，可以访问类中的任意成员；而静态成员函数没有 this 指针，只能访问静态成员（包括静态成员变量和静态成员函数）。





### class和struct区别

[C++](http://c.biancheng.net/cplus/) 中保留了C语言的 struct 关键字，并且加以扩充。在C语言中，struct 只能包含成员变量，不能包含成员函数。而在C++中，struct 类似于 class，既可以包含成员变量，又可以包含成员函数。

在编写C++代码时，建议使用 class 来定义类，而使用 struct 来定义结构体，这样做语义更加明确。





### string

[C++](http://c.biancheng.net/cplus/) 大大增强了对字符串的支持，除了可以使用C风格的字符串，还可以使用内置的 string 类。string 类处理起字符串来会方便很多，完全可以代替C语言中的字符数组或字符串[指针](http://c.biancheng.net/c/80/)。

```c++
string s = "http://c.biancheng.net";
int len = s.length();
cout<<len<<endl;
```

输出结果为`22`。由于 string 的末尾没有`'\0'`字符，所以 length() 返回的是字符串的真实长度，而不是长度 +1。



#### 转换为C风格的字符串

虽然 C++ 提供了 string 类来替代C语言中的字符串，但是在实际编程中，有时候必须要使用C风格的字符串（例如打开文件时的路径），为此，string 类为我们提供了一个转换函数 c_str()，该函数能够将 string 字符串转换为C风格的字符串，并返回该字符串的 const 指针（const char*）。

```c++
string path = "D:\\demo.txt";
FILE *fp = fopen(path.c_str(), "rt");
```

为了使用C语言中的 fopen() 函数打开文件，必须将 string 字符串转换为C风格的字符串。



#### 字符串的输入输出

```c++
#include <iostream>
#include <string>
using namespace std;
int main(){
    string s;
    cin>>s;  //输入字符串
    cout<<s<<endl;  //输出字符串
    return 0;
}
```



#### 访问字符串中的字符

string 字符串也可以像C风格的字符串一样按照下标来访问其中的每一个字符。string 字符串的起始下标仍是从 0 开始。

```c++
#include <iostream>
#include <string>
using namespace std;
int main(){
    string s = "1234567890";
    for(int i=0,len=s.length(); i<len; i++){
        cout<<s[i]<<" ";
    }
    cout<<endl;
    s[5] = '5';
    cout<<s<<endl;
    return 0;
}
```

本例定义了一个 string 变量 s，并赋值 "1234567890"，之后用 [for 循环](http://c.biancheng.net/view/172.html)遍历输出每一个字符。借助下标，除了能够访问每个字符，也可以修改每个字符，`s[5] = '5';`就将第6个字符修改为 '5'，所以 s 最后为 "1234557890"。



#### 字符串的拼接

有了 string 类，我们可以使用`+`或`+=`运算符来直接拼接字符串，非常方便，再也不需要使用C语言中的 strcat()、strcpy()、malloc() 等函数来拼接字符串了，不用担心空间不够会溢出了。



#### 字符串的增删改查

##### 插入字符串

insert() 函数可以在 string 字符串中指定的位置插入另一个字符串，它的一种原型为：

```c++
string& insert (size_t pos, const string& str);
```

pos 表示要插入的位置，也就是下标；str 表示要插入的字符串，它可以是 string 字符串，也可以是C风格的字符串。

```c++
#include <iostream>
#include <string>
using namespace std;
int main(){
    string s1, s2, s3;
    s1 = s2 = "1234567890";
    s3 = "aaa";
    s1.insert(5, s3);
    cout<< s1 <<endl;
    s2.insert(5, "bbb");
    cout<< s2 <<endl;
    return 0;
}
```



##### 删除字符串

erase() 函数可以删除 string 中的一个子字符串。它的一种原型为：

```c++
string& erase (size_t pos = 0, size_t len = npos);
```

pos 表示要删除的子字符串的起始下标，len 表示要删除子字符串的长度。如果不指明 len 的话，那么直接删除从 pos 到字符串结束处的所有字符（此时 len = str.length - pos）

```c++
#include <iostream>
#include <string>
using namespace std;
int main(){
    string s1, s2, s3;
    s1 = s2 = s3 = "1234567890";
    s2.erase(5);
    s3.erase(5, 3);
    cout<< s1 <<endl;
    cout<< s2 <<endl;
    cout<< s3 <<endl;
    return 0;
}
```

运行结果：

```
1234567890
12345
1234590
```



##### 提取子字符串

substr() 函数用于从 string 字符串中提取子字符串，它的原型为：

```c++
string substr (size_t pos = 0, size_t len = npos) const;
```

pos 为要提取的子字符串的起始下标，len 为要提取的子字符串的长度。

```c++
#include <iostream>
#include <string>
using namespace std;
int main(){
    string s1 = "first second third";
    string s2;
    s2 = s1.substr(6, 6);
    cout<< s1 <<endl;
    cout<< s2 <<endl;
    return 0;
}
```

运行结果：

```
first second third
second
```





#### 字符串查找

##### find() 函数

find() 函数用于在 string 字符串中查找子字符串出现的位置，它其中的两种原型为：

```c++
size_t find (const string& str, size_t pos = 0) const;
size_t find (const char* s, size_t pos = 0) const;
```

第一个参数为待查找的子字符串，它可以是 string 字符串，也可以是C风格的字符串。第二个参数为开始查找的位置（下标）；如果不指明，则从第0个字符开始查找。

```c++
#include <iostream>
#include <string>
using namespace std;
int main(){
    string s1 = "first second third";
    string s2 = "second";
    int index = s1.find(s2,5);
    if(index < s1.length())
        cout<<"Found at index : "<< index <<endl;
    else
        cout<<"Not found"<<endl;
    return 0;
}
```



#####  rfind() 函数

rfind() 和 find() 很类似，同样是在字符串中查找子字符串，不同的是 find() 函数从第二个参数开始往后查找，而 rfind() 函数则最多查找到第二个参数处，如果到了第二个参数所指定的下标还没有找到子字符串，则返回 string::npos。



##### find_first_of() 函数

find_first_of() 函数用于查找子字符串和字符串共同具有的字符在字符串中首次出现的位置。请看下面的代码：

```c++
#include <iostream>
#include <string>
using namespace std;
int main(){
    string s1 = "first second second third";
    string s2 = "second";
    int index = s1.find_first_of(s2);
    if(index < s1.length())
        cout<<"Found at index : "<< index <<endl;
    else
        cout<<"Not found"<<endl;
    return 0;
}
```

















