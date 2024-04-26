package com.softwarecooperative.softwareciooperative.pojo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BClassChapterContent implements Serializable {

  private Integer contentId;
  private Integer chapterId;
  private String resourceLink;

}
