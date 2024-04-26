package com.softwarecooperative.softwareciooperative.pojo.entity;


import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BStudent implements Serializable {

  @PrimaryKey
  private Integer studentId;
  private String studentName;
  private Integer studentClass;
  private String studentRole;
  private Integer studentGroup;
  private String password;
  private String avatar;

}
