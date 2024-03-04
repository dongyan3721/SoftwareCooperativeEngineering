package com.softwarecooperative.softwareciooperative.utils.crypto;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Component;

/**
 * MD5加密/编码
 */
@Component
public final class MD5Util {
    public static String encrypt(String strSrc) {
        return DigestUtil.md5Hex(strSrc);
    }
}
