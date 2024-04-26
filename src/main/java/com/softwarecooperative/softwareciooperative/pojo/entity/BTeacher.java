package com.softwarecooperative.softwareciooperative.pojo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BTeacher implements Serializable {

  private Integer teacherId;
  private String teacherName;
  private String password;
  private String avatar;

}
