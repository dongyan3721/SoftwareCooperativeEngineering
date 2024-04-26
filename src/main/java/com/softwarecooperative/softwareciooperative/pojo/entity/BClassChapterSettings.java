package com.softwarecooperative.softwareciooperative.pojo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BClassChapterSettings implements Serializable {

  private Integer chapterId;
  private String classId;
  private String chapterName;

}
