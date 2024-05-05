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

}
