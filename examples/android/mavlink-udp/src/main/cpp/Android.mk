LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
#relative path
#LOCAL_CFLAGS += -I $(LOCAL_PATH)/c_library_v2-master
#absolute path, Windows
LOCAL_CFLAGS += -I C:\Users\msuma\Documents\libraries\c_library_v2-master
#absolute path, Linux
#LOCAL_CFLAGS += -I /mnt/sda3/Dokumenty/Android/mavlink/generated/include
LOCAL_MODULE    := mavlink_udp
LOCAL_SRC_FILES := mavlink_udp.c
include $(BUILD_SHARED_LIBRARY)