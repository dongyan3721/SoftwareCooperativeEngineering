package com.softwarecooperative.softwareciooperative.pojo.entity;


import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BClassChapterSettings implements Serializable {

  @PrimaryKey
  private Integer chapterId;
  private String classId;
  private String chapterName;
  private Integer order;

}
