#include <jni.h>
//#include <string>
#include "NativeText.h"
#include "LogUtils.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_lg_ndk_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
void NativeInit(std::string config){

    showLog(config.c_str());
}