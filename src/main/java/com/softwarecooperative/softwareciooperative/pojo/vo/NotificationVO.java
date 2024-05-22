package com.softwarecooperative.softwareciooperative.pojo.vo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-12:17:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationVO {
    private Integer noticeId;
    private String noticeContent;
    private String noticePublisher;
    private String noticePublisherAvatar;
    private LocalDateTime noticePublishSnapshot;
    private Boolean hasRead;
}
