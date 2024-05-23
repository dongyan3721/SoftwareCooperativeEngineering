package com.softwarecooperative.softwareciooperative.framework.exception.service;

import com.softwarecooperative.softwareciooperative.framework.exception.GeneralServiceException;

public class DeleteChapterException extends GeneralServiceException {
    public DeleteChapterException(String message) {
        super(message);
    }

    public DeleteChapterException(String message, int code) {
        super(message, code);
    }
}
