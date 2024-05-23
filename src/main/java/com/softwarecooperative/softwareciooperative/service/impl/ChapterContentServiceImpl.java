package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.ClassChapterContentMapper;
import com.softwarecooperative.softwareciooperative.framework.net.IntegerConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterContent;
import com.softwarecooperative.softwareciooperative.service.ChapterContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterContentServiceImpl implements ChapterContentService {

    @Autowired
    ClassChapterContentMapper chapterContentMapper;

    @Override
    public void addContent(BClassChapterContent bClassChapterContent) {
        String fileLink = bClassChapterContent.getResourceLink();
        String fileType = null;
        if (fileLink != null && !fileLink.isEmpty()) {
            int lastIndexOfDot = fileLink.lastIndexOf('.');
            if (lastIndexOfDot != -1) {
                fileType = fileLink.substring(lastIndexOfDot + 1);
            }
        }
        Integer resourceType = null;
        // 直接在主方法中进行映射
        if (fileType != null) {
            switch (fileType) {
                case "mp4":
                case "avi":
                case "mov":
                case "wmv":
                    resourceType = IntegerConstant.RESOURCE_TYPE_VIDEO; // 视频
                    break;
                case "mp3":
                case "wav":
                case "aac":
                case "flac":
                    resourceType = IntegerConstant.RESOURCE_TYPE_AUDIO; // 音频
                    break;
                case "pdf":
                case "doc":
                case "docx":
                case "txt":
                case "ppt":
                case "pptx":
                case "xls":
                case "xlsx":
                    resourceType = IntegerConstant.RESOURCE_TYPE_DOCUMENT; // 文档
                    break;
                case "zip":
                case "rar":
                case "7z":
                case "tar":
                case "gz":
                    resourceType = IntegerConstant.RESOURCE_TYPE_ARCHIVE; // 压缩包
                    break;
                case "jpg":
                case "jpeg":
                case "png":
                case "gif":
                case "bmp":
                    resourceType = IntegerConstant.RESOURCE_TYPE_IMAGE; // 图片
                    break;
                default:
                    resourceType = IntegerConstant.RESOURCE_TYPE_OTHER; // 其他
                    break;
            }
        }
        bClassChapterContent.setResourceType(resourceType);
        chapterContentMapper.addContent(bClassChapterContent);
    }

    @Override
    public void deleteContent(Integer contentId) {
        chapterContentMapper.delete(contentId);
    }

    @Override
    public void modifyContent(BClassChapterContent bClassChapterContent) {
        if (bClassChapterContent.getResourceLink() != null) {//修改文件链接了
            String fileLink = bClassChapterContent.getResourceLink();
            String fileType = null;
            int lastIndexOfDot = fileLink.lastIndexOf('.');
            if (lastIndexOfDot != -1) {
                fileType = fileLink.substring(lastIndexOfDot + 1);
            }
            Integer resourceType = null;
            // 直接在主方法中进行映射
            if (fileType != null) {
                switch (fileType) {
                    case "mp4":
                    case "avi":
                    case "mov":
                    case "wmv":
                        resourceType = IntegerConstant.RESOURCE_TYPE_VIDEO; // 视频
                        break;
                    case "mp3":
                    case "wav":
                    case "aac":
                    case "flac":
                        resourceType = IntegerConstant.RESOURCE_TYPE_AUDIO; // 音频
                        break;
                    case "pdf":
                    case "doc":
                    case "docx":
                    case "txt":
                    case "ppt":
                    case "pptx":
                    case "xls":
                    case "xlsx":
                        resourceType = IntegerConstant.RESOURCE_TYPE_DOCUMENT; // 文档
                        break;
                    case "zip":
                    case "rar":
                    case "7z":
                    case "tar":
                    case "gz":
                        resourceType = IntegerConstant.RESOURCE_TYPE_ARCHIVE; // 压缩包
                        break;
                    case "jpg":
                    case "jpeg":
                    case "png":
                    case "gif":
                    case "bmp":
                        resourceType = IntegerConstant.RESOURCE_TYPE_IMAGE; // 图片
                        break;
                    default:
                        resourceType = IntegerConstant.RESOURCE_TYPE_OTHER; // 其他
                        break;
                }
            }
            bClassChapterContent.setResourceType(resourceType);
        }
        chapterContentMapper.modify(bClassChapterContent);
    }

    @Override
    public List<BClassChapterContent> getContents(Integer chapterId) {
        return chapterContentMapper.getContents(chapterId);
    }
}
