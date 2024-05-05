package com.softwarecooperative.softwareciooperative.pojo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BNoticeReceiveTeacher implements Serializable {

  private Integer noticeId;
  private Integer noticeReceiverId;
  private String noticeStatus;

}
