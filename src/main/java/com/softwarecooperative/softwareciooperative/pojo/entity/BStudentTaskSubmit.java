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
  private Integer taskHandlerGroupId;
  private String taskHandlerName;
  private String taskHandlerWork;
  private String submitLink;
  private String submitStatus;
  private LocalDateTime submitTime;
  private Integer submitType;

  public static final Integer SUBTASK_SUBMIT = 0;
  public static final Integer MAIN_TASK_SUBMIT = 1;

  public static final Integer WAIT_FOR_STUDENT_HAND_ON = 0;
  public static final Integer WAIT_FOR_TEACHER_MARK = 1;
  public static final Integer PASS = 2;
  public static final Integer UNASSIGNED = 4;

  public static BStudentTaskSubmit createIdQuery(Integer recordId) {
    return BStudentTaskSubmit.builder()
            .recordId(recordId)
            .build();
  }
}
