package com.softwarecooperative.softwareciooperative.pojo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BCourseBasicSettings implements Serializable {

  private Integer courseId;
  private String chapterName;

}
