#include"stdio.h"
#include"jni.h"

jstring Java_org_youdian_android_demos_MainActivity_hello(JNIEnv* env, jobject this){
	return (*env)->NewStringUTF(env,"hello ,world");
}
