package com.softwarecooperative.softwareciooperative.controller;

import com.softwarecooperative.softwareciooperative.framework.annotation.permission.SpecificRoleOnly;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/12-15:01:44
 */
@RestController
@RequestMapping("/test")
public class PermissionTestFController {


    @SpecificRoleOnly(role="aaa")
    @GetMapping("test")
    public AjaxResult test(){

        return AjaxResult.success();
    }
}
