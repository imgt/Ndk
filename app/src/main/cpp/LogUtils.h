//
// Created by Zac on 2019/3/4.
//

#ifndef JNILEARN_LOGUTILS_H
#define JNILEARN_LOGUTILS_H

typedef void (*logCallback)(int , const char *,void *userData);

extern void registerLogListener(logCallback callback, void* impl);

void showLog(const char *msg);





#endif //JNILEARN_LOGUTILS_H
