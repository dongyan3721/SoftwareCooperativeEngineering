package com.softwarecooperative.softwareciooperative.framework.exception.service;

import com.softwarecooperative.softwareciooperative.framework.exception.GeneralServiceException;
import com.softwarecooperative.softwareciooperative.framework.net.HttpStatus;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:55:21
 */
public class LoginFailedException extends GeneralServiceException {
    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, int code) {
        super(message, code);
    }
}
