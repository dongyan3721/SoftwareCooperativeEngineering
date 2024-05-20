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

  public static final String NO_ROLE = "0";
  public static final String DEVELOPMENT_MANAGER = "1";
  public static final String PRODUCT_MANAGER = "2";
  public static final String PLANNING_MANAGER = "3";
  public static final String TEST_MANAGER = "4";
  public static final String QUALITY_MANAGER = "5";

  public static BStudent createIdQuery(Integer studentId) {
    return BStudent.builder()
            .studentId(studentId)
            .build();
  }

  public static String roleCode2String(String roleCode) {
    switch (roleCode) {
      case BStudent.NO_ROLE:
        return "未分配";
      case BStudent.DEVELOPMENT_MANAGER:
        return "开发经理";
      case BStudent.PRODUCT_MANAGER:
        return "产品经理";
      case BStudent.PLANNING_MANAGER:
        return "计划经理";
      case BStudent.TEST_MANAGER:
        return "测试经理";
      case BStudent.QUALITY_MANAGER:
        return "质量经理";
      default:
        return null;
    }
  }
}
