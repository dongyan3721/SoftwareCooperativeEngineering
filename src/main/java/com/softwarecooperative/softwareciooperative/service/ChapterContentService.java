package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterContent;

import java.util.List;

public interface ChapterContentService {

    void addContent(BClassChapterContent bClassChapterContent);

    void deleteContent(Integer contentId);

    void modifyContent(BClassChapterContent bClassChapterContent);

    List<BClassChapterContent> getContents(Integer chapterId);
}
