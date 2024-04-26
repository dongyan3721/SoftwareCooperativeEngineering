package com.softwarecooperative.softwareciooperative.pojo.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BClassTask implements Serializable {

  private Integer taskId;
  private String classId;
  private String taskContent;
  private LocalDateTime deadline;
  private Integer taskOrder;

}
