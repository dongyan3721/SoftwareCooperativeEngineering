package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.DictMapper;
import com.softwarecooperative.softwareciooperative.pojo.entity.BDict;
import com.softwarecooperative.softwareciooperative.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:02:51
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<BDict> getDictByName(String dictName) {
        return dictMapper.selectByDictName(dictName);
    }
}
