#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_samsunginstitute_ucsy_samsung_presentation_feature_main_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
