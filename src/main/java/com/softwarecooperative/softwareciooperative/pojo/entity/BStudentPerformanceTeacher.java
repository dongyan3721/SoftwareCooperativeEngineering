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
  private String comment;
  private Integer performanceClass;
  private Integer performanceReceptor;
  private Integer performanceMaker;
  private Integer performanceStage;

}
