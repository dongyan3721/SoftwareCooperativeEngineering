package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterSettings;
import com.softwarecooperative.softwareciooperative.service.ChapterService;
import com.softwarecooperative.softwareciooperative.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("TeacherChapterController")
@RequestMapping("/teacher/chapter")
@Tag(name = "教师章节相关接口")
@Slf4j
public class ChapterController {

    @Autowired
    ChapterService chapterService;
    @PostMapping("")
    @Operation(summary = "教师新增本课程章节")
    public AjaxResult addChapter(@RequestBody BClassChapterSettings bClassChapterSettings){
        chapterService.addChapter(bClassChapterSettings);
        return AjaxResult.success();
    }

    @DeleteMapping("/{chapterId}")
    @Operation(summary = "教师删除课程章节")
    public AjaxResult deleteChapter(@PathVariable Integer chapterId){
        chapterService.deleteChapter(chapterId);
        return AjaxResult.success();
    }

    @PutMapping("")
    @Operation(summary = "教师修改章节")
    public AjaxResult modifyChapterSetting(@RequestBody BClassChapterSettings bClassChapterSettings){
        chapterService.modifyChapterSetting(bClassChapterSettings);
        return AjaxResult.success();
    }

    @GetMapping("")
    @Operation(summary = "教师查看本课程章节")
    public AjaxResult getChapters(Integer classId){
        List<BClassChapterSettings> res = chapterService.getChapters(classId);
        return AjaxResult.success(res);
    }
}
