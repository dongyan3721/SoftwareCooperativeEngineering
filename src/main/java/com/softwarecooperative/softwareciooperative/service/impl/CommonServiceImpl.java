package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.DictMapper;
import com.softwarecooperative.softwareciooperative.pojo.entity.BDict;
import com.softwarecooperative.softwareciooperative.service.CommonService;
import com.softwarecooperative.softwareciooperative.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:02:51
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    public List<BDict> getDictByName(String dictName) {
        return dictMapper.selectByDictName(dictName);
    }

    @Override
    public String uploadFile(byte[] bytes, String originalFilename) {
        String ext;
        try {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }catch (StringIndexOutOfBoundsException e){
            ext = ".png";
        }
        String storageFileName = UUID.randomUUID() + ext;
        return aliOssUtil.upload(bytes, storageFileName);
    }
}
