package com.softwarecooperative.softwareciooperative.pojo.entity;


import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BClass implements Serializable {

  @PrimaryKey
  private Integer classId;
  private Integer teacherId;
  private String teacherName;
  private Integer courseId;
  private String courseName;
  private String classTerm;

  public static final int STUDENT = 0;
  public static final int TEACHER = 1;
  public static final int SYSTEM = 2;

}
