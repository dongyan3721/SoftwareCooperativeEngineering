package com.softwarecooperative.softwareciooperative.framework.exception;

import com.softwarecooperative.softwareciooperative.framework.net.HttpStatus;

import java.io.PrintStream;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/2/29-16:28:05
 */
public class UnAuthenticatedException extends GeneralServiceException {
    public UnAuthenticatedException(final String msg) {
        super(msg, HttpStatus.FORBIDDEN);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }
}
