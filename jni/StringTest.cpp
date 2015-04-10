#include <jni.h>
#include <string.h>

extern "C"

_jstring* Java_com_gdut_testlayout_StringTest_jnitest(JNIEnv* env,jobject obj){
	return (env)->NewStringUTF("hello from jni!");
}
