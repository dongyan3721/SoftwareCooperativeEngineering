package com.softwarecooperative.softwareciooperative.pojo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BStudent implements Serializable {

  private Integer studentId;
  private String studentName;
  private Integer studentClass;
  private String studentRole;
  private Integer studentGroup;
  private String password;
  private String avatar;

}
