package com.softwarecooperative.softwareciooperative.framework.exception.file;


import com.softwarecooperative.softwareciooperative.framework.exception.BaseException;

/**
 * 文件信息异常类
 * 
 * @author yy
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
