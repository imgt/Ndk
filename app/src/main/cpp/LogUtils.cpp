//
// Created by Zac on 2019/3/4.
//

#include "LogUtils.h"


logCallback _callback;
void* _impl;



void registerLogListener(logCallback callback, void* impl){
    _callback = callback;
    _impl = impl;
}

void showLog(const char *msg){
    _callback(1, msg, _impl);
}
