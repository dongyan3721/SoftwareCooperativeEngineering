package com.softwarecooperative.softwareciooperative.pojo.entity;


import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BTeacher implements Serializable {

  @PrimaryKey
  private Integer teacherId;
  private String teacherName;
  private String password;
  private String avatar;

  public static BTeacher createIdQuery(int teacherId) {
    return BTeacher.builder()
            .teacherId(teacherId)
            .build();
  }
}
