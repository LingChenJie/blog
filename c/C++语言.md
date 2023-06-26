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















































