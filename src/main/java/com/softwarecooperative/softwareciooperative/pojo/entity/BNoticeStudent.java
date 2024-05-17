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
public class BNoticeStudent implements Serializable {

  @PrimaryKey
  private Integer noticeId;
  private String noticeContent;
  private String noticePublisher;
  private String noticePublisherAvatar;
  private LocalDateTime noticePublishSnapshot;

}
