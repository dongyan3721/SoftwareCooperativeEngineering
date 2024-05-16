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

}
