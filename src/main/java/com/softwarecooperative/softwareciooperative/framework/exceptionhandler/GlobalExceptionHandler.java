package com.softwarecooperative.softwareciooperative.framework.exceptionhandler;


import com.softwarecooperative.softwareciooperative.framework.exception.GeneralServiceException;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.framework.net.HttpStatus;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author Santa Antilles
 * @Date 2023/12/2-14:01
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(GeneralServiceException.class)
    public AjaxResult handleServiceException(GeneralServiceException e){
        return new AjaxResult(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public AjaxResult handleMultipartFileException(MaxUploadSizeExceededException e) {
        return AjaxResult.error(HttpStatus.BAD_REQUEST, StringConstant.UPLOAD_FILE_SIZE_EXCEEDED);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public AjaxResult handleAllException(Exception e) {
        e.printStackTrace();
        return new AjaxResult(HttpStatus.ERROR, StringConstant.INTERNAL_SERVER_ERROR);
    }
}
