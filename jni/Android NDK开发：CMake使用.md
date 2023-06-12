# Android NDK开发：CMake使用



### Cmake命令

```
camke [<options> (<path-to-source> | <path-to-existing-build>)]
```

> optons: 可选项，为空时，构建的路径为当前路径
>
> path-to-source 和 path-to-existing-build 二选一，分别表示 CMakeLists.txt 所在的路径和一个已存在的构建工程的目录

- `cmake .`表示构建**当前目录**下 CMakeLists.txt 的配置，并在当前目录下生成 Makefile 等文件；【属于内部构建】
- `cmake ..`表示构建上一级目录下 CMakeLists.txt 的配置，并在当前目录下生成 Makefile 等文件



### 安装 CMake

> brew install cmake
>
> brew link cmake
>
> camke -version #检验是否安装成功，显示对应 CMake 版本号即表示安装成功



### CMake上手

创建一个 CMake/t1 目录，并分别编写 main.c 和 CMakeLists.txt （CMakeLists.txt 是 CMake 的构建定义文件）

```c
#include <stdio.h>
int main()
{
    printf(“Hello World from CMake!\n”);
    return 0;
}
```

```cmake
PROJECT(HELLO)
SET(SRC_LIST main.c)
MESSAGE(STATUS "This is BINARY dir " ${HELLO_BINARY_DIR})  #终端打印的信息
MESSAGE(STATUS "This is SOURCE dir "${HELLO_SOURCE_DIR})
ADD_EXECUTABLE(hello ${SRC_LIST})
```

这里如果直接输入`cmake .`开始构建，属于内部构建。建议采用外部构建的方法，先建一个 build 文件夹，进入 build 文件夹在执行`cmake ..`。构建后出现很多 log 包含以下，说明构建成功，并且目录下会生成CMakeFiles, CMakeCache.txt, cmake_install.cmake, Makefile 等文件

最后执行 `./hello` 会打印输出：`Hello World from CMake!`



### CMake 常用指令

#### project 指令

> 语法：project(<projectname> [CXX] [C] [Java])
>
> 这个指令是定义工程名称的，并且可以指定工程支持的语言（当然也可以忽略，默认情况表示支持所有语言），**不是强制定义的**。例如：project(HELLO)
>
> 定义完这个指令会隐式定义了两个变量：
> `<projectname>_BINARY_DIR`和`<projectname>_SOURCE_DIR`
>
> 另外 CMake 系统还会预定义了 `PROJECT_BINARY_DIR` 和 `PROJECT_SOURCE_DIR` 变量，它们的值和上面两个的变量对应的值是一致的。不过为了统一起见，建议直接使用`PROJECT_BINARY_DIR` 和`PROJECT_SOURCE_DIR`，即使以后修改了工程名字，也不会影响两个变量的使用。



#### set 指令

> 语法：set(VAR [VALUE])
>
> 这个指令是用来显式地定义变量，多个变量用空格或分号隔开
>
> 例如：set(SRC_LIST main.c test.c)
>
> 当需要用到定义的 SRC_LIST 变量时，需要用${var}的形式来引用，如：${SRC_LIST}。不过，在 IF 控制语句中可以直接使用变量名。



#### message 指令

> 语法：message([SEND_ERROR | STATUS | FATAL_ERROR]  “message to display” … )
>  这个指令用于向终端输出用户定义的信息，包含了三种类型：
>  SEND_ERROR，产生错误，生成过程被跳过；
>  STATUS，输出前缀为—-的信息；（由上面例子也可以看到会在终端输出相关信息）
>  FATAL_ERROR，立即终止所有 CMake 过程；



#### add_executable 指令

> 语法：add_executable(executable_file_name  [source])
>  将一组源文件 source 生成一个可执行文件。 source 可以是多个源文件，也可以是对应定义的变量
>  如：add_executable(hello main.c)



#### camke_minimun_required(VERSION 3.4.1)

> 用来指定 CMake 最低版本为3.4.1，如果没指定，执行 cmake 命令时可能会出错



#### add_subdirectory 指令

> 语法：add_subdirectory(source_dir [binary_dir] [EXCLUDE_FROM_ALL])
>  这个指令用于向当前工程添加存放源文件的子目录，并可以指定中间二进制和目标二进制存放的位置。EXCLUDE_FROM_ALL参数含义是将这个目录从编译过程中排除。
>
> 另外，也可以通过 SET 指令重新定义 EXECUTABLE_OUTPUT_PATH 和 LIBRARY_OUTPUT_PATH 变量来指定最终的目标二进制的位置(指最终生成的 hello 或者最终的共享库，不包含编译生成的中间文件)
>  set(EXECUTABLE_OUTPUT_PATH ${PROJECT_BINARY_DIR}/bin)
>  set(LIBRARY_OUTPUT_PATH ${PROJECT_BINARY_DIR}/lib)



#### add_library 指令

> 语法：add_library(libname [SHARED | STATIC | MODULE] [EXCLUDE_FROM_ALL]  [source])
>  将一组源文件 source 编译出一个库文件，并保存为 libname.so (lib 前缀是生成文件时 CMake自动添加上去的)。其中有三种库文件类型，**不写的话，默认为 STATIC**:

- SHARED:  表示动态库，可以在(Java)代码中使用 `System.loadLibrary(name)` 动态调用；

- STATIC:  表示静态库，集成到代码中会在编译时调用；

- MODULE: 只有在使用 dyId 的系统有效，如果不支持 dyId，则被当作 SHARED 对待；

- EXCLUDE_FROM_ALL:  表示这个库不被默认构建，除非其他组件依赖或手工构建

```cmake
#将compress.c 编译成 libcompress.so 的共享库
add_library(compress SHARED compress.c)
```

add_library 命令也可以用来导入第三方的库:
`add_library(libname [SHARED | STATIC | MODULE | UNKNOWN] IMPORTED)`

如，导入 libjpeg.so

```cmake
add_library(libjpeg SHARED IMPORTED)
```

导入库后，当需要使用 target_link_libraries 链接库时，可以直接使用该库



#### find_library 指令

> 语法：find_library(<VAR> name1 path1 path2 ...)
> VAR 变量表示找到的库全路径，包含库文件名 。例如：

```cmake
find_library(libX  X11 /usr/lib)
find_library(log-lib log)  #路径为空，应该是查找系统环境变量路径
```



#### set_target_properties 指令

> 语法: set_target_properties(target1  target2 … PROPERTIES prop1  value1  prop2 value2 …)
>  这条指令可以用来设置输出的名称（设置构建同名的动态库和静态库，或者指定要导入的库文件的路径），对于动态库，还可以用来指定动态库版本和 API 版本。
>  如，set_target_properties(hello_static PROPERTIES OUTPUT_NAME “hello”)
>  设置同名的 hello 动态库和静态库：

```cmake
set_target_properties(hello PROPERTIES CLEAN_DIRECT_OUTPUT 1)
set_target_properties(hello_static PROPERTIES CLEAN_DIRECT_OUTPUT 1)
```

指定要导入的库文件的路径

```cmake
add_library(jpeg SHARED IMPORTED)
#注意要先 add_library，再 set_target_properties
set_target_properties(jpeg PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/libs/${ANDROID_ABI}/libjpeg.so)
```

设置动态库 hello 版本和 API 版本：

```cmake
set_target_properties(hello PROPERTIES VERSION 1.2 SOVERSION 1)
```

和它对应的指令：
get_target_property(VAR target property)
如上面的例子，获取输出的库的名字

```cmake
get_target_property(OUTPUT_VALUE hello_static OUTPUT_NAME)
message(STATUS "this is the hello_static OUTPUT_NAME:"${OUTPUT_VALUE})
```



#### include_directories 指令

> 语法：include_directories([AFTER | BEFORE] [SYSTEM] dir1 dir2…)
>  这个指令可以用来向工程添加多个特定的头文件搜索路径，路径之间用空格分割，如果路径中包含了空格，可以使用双引号将它括起来，默认的行为是追加到当前的头文件搜索路径的后面。



#### target_link_libraries 指令

> 语法：target_link_libraries(target library  <debug | optimized> library2…)
>  这个指令可以用来为 target  添加需要的链接的共享库，同样也可以用于为自己编写的共享库添加共享库链接。
>  如：

```cmake
#指定 compress 工程需要用到 libjpeg 库和 log 库
target_link_libraries(compress libjpeg ${log-lib})
```

同样，link_directories(directory1 directory2 …) 可以添加非标准的共享库搜索路径。





















