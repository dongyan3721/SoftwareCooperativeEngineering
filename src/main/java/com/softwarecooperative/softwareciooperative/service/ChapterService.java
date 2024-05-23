package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.framework.exception.service.DeleteChapterException;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterSettings;

import java.util.List;

public interface ChapterService {
    void addChapter(BClassChapterSettings bClassChapterSettings);

    void deleteChapter(Integer chapterId) throws DeleteChapterException;

    void modifyChapterSetting(BClassChapterSettings bClassChapterSettings);

    List<BClassChapterSettings> getChapters(Integer classId);
}
