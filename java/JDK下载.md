# JDK下载

### JDK资源下载路径

找到官网https://www.oracle.com/index.html

JDK下载页面，你会首先看到最新的JDK版本的下载：
https://www.oracle.com/java/technologies/downloads/

1. JDK11下载：
   https://www.oracle.com/java/technologies/downloads/#java11
2. JDK1.8下载：
   https://www.oracle.com/java/technologies/downloads/#java8



### JDK配置

查看jdk安装路径

执行命令:

```
/usr/libexec/java_home -V 
```



### 配置多个版本的JDK

Mac下通过bash_profile文件来对环境信息进行配置。执行以下命令打开配置文件：

```bash
vim ~/.bash_profile
```

如果原本没有.bash_profile文件，在运行vim ~/.bash_profile命令时会创建该文件。

```
# Java config
export JAVA_8_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home"
export JAVA_9_HOME="/Library/Java/JavaVirtualMachines/jdk-9.0.4.jdk/Contents/Home"

# config alias
alias jdk8="export JAVA_HOME=$JAVA_8_HOME"
alias jdk9="export JAVA_HOME=$JAVA_9_HOME"

# config default jdk
export JAVA_HOME=$JAVA_8_HOME
export PATH="$JAVA_HOME:$PATH"
```

修改保存上述配置，对bash_profile进行编译生效：

```bash
source ~/.bash_profile
```

此时，在执行java -version，会发现jdk版本为正常显示。

```
192:JavaVirtualMachines zzs$ java -version
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```













