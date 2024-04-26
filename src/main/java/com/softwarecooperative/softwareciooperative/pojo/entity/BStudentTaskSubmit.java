package com.softwarecooperative.softwareciooperative.pojo.entity;


import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BStudentTaskSubmit implements Serializable {

  @PrimaryKey
  private Integer recordId;
  private Integer taskId;
  private Integer taskHandler;
  private Integer taskHandlerName;
  private String taskHandlerWork;
  private String submitLink;
  private LocalDateTime submitTime;

}
