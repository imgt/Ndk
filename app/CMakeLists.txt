# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

aux_source_directory(src/main/cpp DIR_SRCS)

#include_directories(includ e)


# 获取当前目录下的所有的文件（目录）的路径并保存到 all_source 变量中
#file(GLOB all_source  *.cpp *.c )

add_library( # Sets the name of the library.
             native-app

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
         ${DIR_SRCS} )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )
# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

#add_library(jpegs SHARED IMPORTED)
##指库添加动态链接库的库文件
##参数一：对应添加库的库名
##参数二：PROPERTIES表示导入
##参数三：IMPORTED_LOCATION 表示导入本地库
##参数四：库文件地址
#set_target_properties(
#        jpegs
#        PROPERTIES
#        IMPORTED_LOCATION
#        ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libjpeg.so)

# 设置 JNILIBS_DIR  赋值为jniLibs的路径
# CMAKE_SOURCE_DIR
set(JNILIBS_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})
#导入第三方库 【geos】
add_library(jpegs SHARED IMPORTED)
#设置导入第三方库名称、目标位置[ JNILIBS_DIR ]
set_target_properties(jpegs PROPERTIES
        IMPORTED_LOCATION ${JNILIBS_DIR}/libjpeg.so)

target_link_libraries( # Specifies the target library.
                       native-app
                        jpegs
                       # Links the target library to the log library
                       # included in the NDK.
                        jnigraphics
                       ${log-lib} )