// #include "HelloJNI.h"
#include <stdio.h>
#include <jni.h>

//方法名要和 Java 层包名对应上
JNIEXPORT jstring JNICALL Java_HelloJNI_sayHello(JNIEnv *env, jobject obj)
{
	printf("hahaha\n");
    return (*env)->NewStringUTF(env,"Hello from JNI !");
}