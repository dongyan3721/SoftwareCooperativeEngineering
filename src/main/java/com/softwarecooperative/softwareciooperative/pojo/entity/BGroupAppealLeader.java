package com.softwarecooperative.softwareciooperative.pojo.entity;

import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/15-21:42:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BGroupAppealLeader implements Serializable {

    @PrimaryKey
    private Integer appealId;
    private Integer studentId;
    private String groupName;
    private Integer classId;
    private String groupAvatar;
    private String groupIntroduction;
    private LocalDateTime appealDate;

    public static BGroupAppealLeader createIdQuery(Integer appealId) {
        return BGroupAppealLeader.builder()
                .appealId(appealId)
                .build();
    }
}
