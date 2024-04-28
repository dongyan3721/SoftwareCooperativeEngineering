package com.softwarecooperative.softwareciooperative.framework.exception;

import com.softwarecooperative.softwareciooperative.framework.net.CustomHttpStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Santa Antilles
 * @Date 2023/12/2-13:58
 */
@Setter
@Getter
public class GeneralServiceException extends RuntimeException{
    public GeneralServiceException(String message) {
        super(message);
        this.code = CustomHttpStatus.SERVICE_ERROR;
    }
    private int code;

    public GeneralServiceException(String message, int code) {
        super(message);
        this.code  = code;
    }
}
