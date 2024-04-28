package com.softwarecooperative.softwareciooperative.pojo.entity;

import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/27-23:32:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "ChatRecord")
public class ChatRecord implements Serializable {
    // 主键标识，该属性会自动对应mongodb的"_id"字段
    @Id
    @PrimaryKey
    private Integer id;
    // 该属性对应mongodb字段的名字，如果一致则不需要起别名
    private String content;  // 聊天正文
    private Integer contentType;  // 正文类型（0文本1图片2文件）
    @Indexed
    private Integer groupId;  // 队伍id
    @Indexed
    private LocalDateTime sendDateTime;  // 发布时间
    @Indexed
    private Integer userId;  // 发言用户id
    private Integer userType;  // 发言用户类型（0学生1老师）



    // 用户类型常量
    @Transient
    public static final Integer STUDENT = 0;
    @Transient
    public static final Integer TEACHER = 1;

    // 正文类型常量
    @Transient
    public static final Integer TEXT = 0;
    @Transient
    public static final Integer PICTURE = 1;
    @Transient
    public static final Integer FILE = 2;

}
