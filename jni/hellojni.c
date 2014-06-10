#include<string.h>
#include<jni.h>

jstring Java_org_youdian_android_1demos_MainActivity_hello(JNIEnv* env, jobject thiz){

	return (*env)->NewStringUTF(env,"hello ,world");
}
