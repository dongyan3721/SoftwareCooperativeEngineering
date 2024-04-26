package com.softwarecooperative.softwareciooperative.pojo.entity;


import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BGroup implements Serializable {

  @PrimaryKey
  private Integer groupId;
  private String groupName;
  private Integer classId;
  private String groupAvatar;
  private String groupIntroduction;
  private Integer groupLeaderId;
  private String groupLeaderName;
  private String groupLeaderAvatar;

}
