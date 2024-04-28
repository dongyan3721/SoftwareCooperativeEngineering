package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.pojo.entity.BDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-22:53:10
 */
@Mapper
public interface DictMapper {
    List<BDict> selectByDictName(@Param("dictName") String dictName);
}
