package com.softwarecooperative.softwareciooperative.dao.repository;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.ChatRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Description 聊天记录DAO方法
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-01:21:32
 */
public interface ChatRecordRepo extends MongoRepository<ChatRecord, String> {
    @Override
    @InjectSnowFlakeId
    <S extends ChatRecord> S insert(S entity);

    @Override
    @InjectSnowFlakeId
    <S extends ChatRecord> List<S> insert(Iterable<S> entities);

    Page<ChatRecord> findByGroupId(Integer groupId, Pageable pageable);
}
