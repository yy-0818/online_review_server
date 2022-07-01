package com.scientific.audit.common.util;


import cn.hutool.core.util.ObjectUtil;
import com.scientific.audit.common.model.base.ResultCode;
import com.scientific.audit.exception.CustomException;

public class AssertUtils{
    public static void assertNotNull(Object object, ResultCode resultCode) {
        if (ObjectUtil.isNull(object)) {
            throw new CustomException(resultCode);
        }
    }

    public static void assertNull(Object object, ResultCode resultCode) {
        if (ObjectUtil.isNotNull(object)) {
            throw new CustomException(resultCode);
        }
    }

    public static void assertTrue(Boolean bool, ResultCode resultCode) {
        if (!bool) {
            throw new CustomException(resultCode);
        }
    }

    public static void assertFalse(Boolean bool, ResultCode resultCode) {
        if (bool) {
            throw new CustomException(resultCode);
        }
    }
}
