
LOCAL_PATH :=$(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := hellojni

LOCAL_SRC_FILES :=hellojni.c

include $(BUILD_SHARED_LIBRARY)