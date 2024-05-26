package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.pojo.entity.ChatRecord;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-14:15:43
 */
public interface ChatRecordService {

    void studentAddOne(String content, Integer contentType);

    void addOne(ChatRecord chatRecord);

    List<ChatRecord> getOnePage(Integer groupId, Integer pageNum, Integer pageSize);
}
