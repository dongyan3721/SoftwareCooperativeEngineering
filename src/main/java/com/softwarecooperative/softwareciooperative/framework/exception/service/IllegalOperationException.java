package com.softwarecooperative.softwareciooperative.framework.exception.service;

import com.softwarecooperative.softwareciooperative.framework.exception.GeneralServiceException;
import com.softwarecooperative.softwareciooperative.framework.net.HttpStatus;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/13-20:13:34
 */
public class IllegalOperationException extends GeneralServiceException {
    public IllegalOperationException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
