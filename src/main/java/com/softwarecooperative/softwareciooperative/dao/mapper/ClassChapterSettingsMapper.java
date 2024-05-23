package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassChapterSettingsMapper {
    @InjectSnowFlakeId
    void insert(BClassChapterSettings bClassChapterSettings);

    void deleteChapter(Integer chapterId);

    void updateChapter(BClassChapterSettings bClassChapterSettings);

    List<BClassChapterSettings> getChapters(@Param("classId") Integer classId);
}
