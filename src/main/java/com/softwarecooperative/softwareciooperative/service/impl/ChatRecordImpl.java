package com.softwarecooperative.softwareciooperative.service.impl;

import com.alibaba.fastjson2.JSON;
import com.softwarecooperative.softwareciooperative.dao.repository.ChatRecordRepo;
import com.softwarecooperative.softwareciooperative.pojo.entity.ChatRecord;
import com.softwarecooperative.softwareciooperative.service.ChatRecordService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-14:18:18
 */
@Service
public class ChatRecordImpl implements ChatRecordService {

    @Autowired
    private ChatRecordRepo chatRecordRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void addOne(ChatRecord chatRecord) {
        String json = JSON.toJSONString(chatRecord);
        Message message = MessageBuilder
                .withBody(json.getBytes(StandardCharsets.UTF_8))
                .build();

        rabbitTemplate.convertAndSend("chatRecord.exchange", "114514", message);
    }

    @Override
    public List<ChatRecord> getOnePage(Integer groupId, Integer pageNum, Integer pageSize) {
        Query query = Query.query(Criteria.where("groupId").is(groupId))
                .with(Sort.by(Sort.Direction.DESC, "sendDateTime"))
                .with(PageRequest.of(pageNum, pageSize));
        return mongoTemplate.find(query, ChatRecord.class);
    }
}
