package com.softwarecooperative.softwareciooperative.pojo.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BDict implements Serializable {

  private String dictName;
  private String tag;
  private String value;

}
