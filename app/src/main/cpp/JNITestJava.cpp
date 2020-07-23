

#include <jni.h>
#include <string>
#include "NativeText.h"
#include "LogUtils.h"

class NativeListenerImpl{

public:
    void init() {
        _class = _env->GetObjectClass(_listener);
        _showLogMethodID = _env->GetMethodID(_class, "showLog", "(ILjava/lang/String;)V");
    }

    NativeListenerImpl(JNIEnv* env, jobject listener){
        _listener = env->NewGlobalRef(listener);
        _env = env;
        init();
    }

    void onLog(int level, const char* logMsg){
        _env->CallVoidMethod(_listener, _showLogMethodID, level, _env->NewStringUTF(logMsg));
    }


private:
    jobject _listener;
    JNIEnv* _env;
    jmethodID  _showLogMethodID;
    jclass _class;
};


void OnLogListerCallBack(int severity, const char *log,void* userData){
    NativeListenerImpl* logger = (NativeListenerImpl*)userData;
    logger->onLog(severity,log);
}

extern "C" {
    JNIEXPORT void JNICALL
    Java_com_lg_ndk_MainActivity_init(JNIEnv *env, jobject type, jstring config_) {
        const char *config = env->GetStringUTFChars(config_, 0);
        NativeInit(std::string(config));
        env->ReleaseStringUTFChars(config_, config);
    }


    JNIEXPORT void JNICALL
    Java_com_lg_ndk_MainActivity_registerListener(JNIEnv *env, jobject type,
                                                               jobject listener) {
        // TODO
        NativeListenerImpl* impl = new NativeListenerImpl(env, listener);
        registerLogListener(OnLogListerCallBack, impl);
    }
}
