package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.pojo.entity.BDict;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:01:59
 */
public interface DictService {

    List<BDict> getDictByName(String dictName);

}
