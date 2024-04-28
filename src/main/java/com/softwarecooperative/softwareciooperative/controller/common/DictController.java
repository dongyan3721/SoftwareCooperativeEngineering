package com.softwarecooperative.softwareciooperative.controller.common;

import com.softwarecooperative.softwareciooperative.framework.net.TableDataInfo;
import com.softwarecooperative.softwareciooperative.pojo.entity.BDict;
import com.softwarecooperative.softwareciooperative.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-22:55:43
 */
@RestController
@RequestMapping("/dev-api/common/dict")
@Tag(name = "公共字典接口")
@Slf4j
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("/{dictName}")
    @Operation(summary = "获取字典键值对")
    public TableDataInfo getDictByDictName(@PathVariable String dictName) {
        List<BDict> res = dictService.getDictByName(dictName);
        return TableDataInfo.success(res, res.size());
    }
}
