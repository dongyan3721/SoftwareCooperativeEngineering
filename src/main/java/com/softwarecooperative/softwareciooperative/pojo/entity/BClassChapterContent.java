package com.softwarecooperative.softwareciooperative.pojo.entity;


import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BClassChapterContent implements Serializable {

    @PrimaryKey
    private Integer contentId;
    private Integer chapterId;
    private String resourceLink;
    private String contentDescription;
    private Integer resourceType;

}
