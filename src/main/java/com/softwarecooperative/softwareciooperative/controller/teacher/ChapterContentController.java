package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterContent;
import com.softwarecooperative.softwareciooperative.service.ChapterContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/teacher/chapterContent")
@Tag(name = "教师章节内容相关接口")
public class ChapterContentController {

    @Autowired
    ChapterContentService chapterContentService;

    @PostMapping("")
    @Operation(summary = "教师添加章节内容")
    public AjaxResult addChapterContent(@RequestBody BClassChapterContent bClassChapterContent) {
        chapterContentService.addContent(bClassChapterContent);
        return AjaxResult.success();
    }

    @DeleteMapping("/{contentId}")
    @Operation(summary = "教师删除章节内容")
    public AjaxResult deleteChapterContent(@PathVariable("contentId") Integer contentId) {
        chapterContentService.deleteContent(contentId);
        return AjaxResult.success();
    }

    @PutMapping("")
    @Operation(summary = "教师修改章节内容")
    public AjaxResult modifyChapterContent(@RequestBody BClassChapterContent bClassChapterContent) {
        chapterContentService.modifyContent(bClassChapterContent);
        return AjaxResult.success();
    }

    @GetMapping("")
    @Operation(summary = "教师查看章节全部内容")
    public AjaxResult getChapterContents(Integer chapterId) {
        List<BClassChapterContent> res = chapterContentService.getContents(chapterId);
        return AjaxResult.success(res);
    }
}
