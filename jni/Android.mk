LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := StringTest
LOCAL_SRC_FILES := StringTest.cpp

include $(BUILD_SHARED_LIBRARY)
