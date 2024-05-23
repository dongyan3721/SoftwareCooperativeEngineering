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
public class BClassTask implements Serializable {

  @PrimaryKey
  private Integer taskId;
  private Integer classId;
  private String taskContent;
  private LocalDateTime deadline;
  private Integer taskOrder;
  private String taskStudentRole;

  public static final Integer BEFORE_LAUNCH = 0;
  public static final Integer OBTAINING_DEMAND = 1;
  public static final Integer PLANNING_PROCESS = 2;
  public static final Integer PLANNING_QUALITY = 3;
  public static final Integer DESIGN_AND_IMPL = 4;
  public static final Integer TESTING = 5;
  public static final Integer PROJECT_ACCEPTED = 6;

  public static final Long DEADLINE_BUFFER_SECONDS = 10L;

  public static BClassTask createIdQuery(Integer taskId) {
    return BClassTask.builder()
            .taskId(taskId)
            .build();
  }
}
