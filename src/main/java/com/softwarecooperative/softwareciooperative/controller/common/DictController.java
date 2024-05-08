package com.softwarecooperative.softwareciooperative.controller.common;

import com.softwarecooperative.softwareciooperative.framework.annotation.AccessWithoutVerification;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.framework.net.TableDataInfo;
import com.softwarecooperative.softwareciooperative.pojo.entity.BDict;
import com.softwarecooperative.softwareciooperative.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-22:55:43
 */
@RestController
@RequestMapping("/common")
@Tag(name = "公共接口")
@Slf4j
public class DictController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/dict/{dictName}")
    @Operation(summary = "获取字典键值对")
    @AccessWithoutVerification
    public TableDataInfo getDictByDictName(@PathVariable String dictName) {
        List<BDict> res = commonService.getDictByName(dictName);
        return TableDataInfo.success(res, res.size());
    }

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public AjaxResult uploadFile(@RequestBody MultipartFile file) throws IOException {
        String url = commonService.uploadFile(file.getBytes(), file.getOriginalFilename());
        return AjaxResult.success(url);
    }
}
