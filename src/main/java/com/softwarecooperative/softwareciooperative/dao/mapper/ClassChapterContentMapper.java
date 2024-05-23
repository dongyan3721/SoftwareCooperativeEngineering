package com.softwarecooperative.softwareciooperative.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassChapterContentMapper {
    Boolean chapterHasContent(@Param("chapterId") Integer chapterId);
}
