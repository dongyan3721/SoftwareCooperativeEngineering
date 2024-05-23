package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterContent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/teacher/chapterContent")
@Tag(name = "教师章节内容相关接口")
public class ChapterContentController {

    @PostMapping("")
    @Operation(summary = "教师添加章节内容")
    public AjaxResult addChapterContent(@RequestBody BClassChapterContent bClassChapterContent){
        return AjaxResult.success();
    }

}
