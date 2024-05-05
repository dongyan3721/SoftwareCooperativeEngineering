package com.softwarecooperative.softwareciooperative.pojo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BStudentPerformanceTeacher implements Serializable {

  private Integer performance;
  private Integer performanceReceptor;
  private Integer performanceClass;
  private Integer performanceMaker;
  private Integer performanceStage;
  private String performanceTag;

}
