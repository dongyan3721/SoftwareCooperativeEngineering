package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassChapterContentMapper {
    Boolean chapterHasContent(@Param("chapterId") Integer chapterId);
    @InjectSnowFlakeId
    void addContent(BClassChapterContent bClassChapterContent);

    void delete(@Param("contentId") Integer contentId);

    void modify(BClassChapterContent bClassChapterContent);

    List<BClassChapterContent> getContents(@Param("chapterId") Integer chapterId);
}
