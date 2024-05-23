package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.ClassChapterContentMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.ClassChapterSettingsMapper;
import com.softwarecooperative.softwareciooperative.framework.exception.service.DeleteChapterException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterSettings;
import com.softwarecooperative.softwareciooperative.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ClassChapterSettingsMapper classChapterSettingsMapper;
    @Autowired
    ClassChapterContentMapper chapterContentMapper;

    @Override
    public void addChapter(BClassChapterSettings bClassChapterSettings) {
        classChapterSettingsMapper.insert(bClassChapterSettings);
    }

    @Override
    public void deleteChapter(Integer chapterId) throws DeleteChapterException {
        if(!chapterContentMapper.chapterHasContent(chapterId)){
            System.out.println("123");
            classChapterSettingsMapper.deleteChapter(chapterId);
        }
        else {//
            throw new DeleteChapterException(StringConstant.DELETE_CHAPTER_FILE);
        }
    }

    @Override
    public void modifyChapterSetting(BClassChapterSettings bClassChapterSettings) {
        classChapterSettingsMapper.updateChapter(bClassChapterSettings);
    }

    @Override
    public List<BClassChapterSettings> getChapters(Integer classId) {
        return classChapterSettingsMapper.getChapters(classId);
    }
}
