package com.xuecheng.learning.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.learning.service.MyCourseTablesService;
import com.xuecheng.messagesdk.model.po.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RocketMQ 接收支付结果消费者
 */
@Slf4j
@Service
@RocketMQMessageListener(
    topic = "pay-notify-topic",           // Topic
    consumerGroup = "pay-notify-consumer-group",    // 消费者组
    selectorExpression = "*" // Tag，只消费指定Tag的消息
)
public class ReceivePayNotifyService implements RocketMQListener<String> {

    @Autowired
    private MyCourseTablesService myCourseTablesService;

    @Override
    public void onMessage(String message) {
        // 打印消费日志
        log.debug("学习中心服务接收支付结果: {}", message);
        
        try {
            // 1. 解析消息
            MqMessage mqMessage = JSON.parseObject(message, MqMessage.class);
            
            // 2. 消息类型校验
            String messageType = mqMessage.getMessageType();
            String businessKey2 = mqMessage.getBusinessKey2();
            
            // 只处理支付结果通知 + 购买课程类型
            if ("payresult_notify".equals(messageType)
                    && "60201".equals(businessKey2)) {
                
                // 3. 获取选课记录id
                String choosecourseId = mqMessage.getBusinessKey1();
                
                // 4. 添加选课
                boolean b = myCourseTablesService.saveChooseCourseStauts(choosecourseId);
                if (!b) {
                    // 添加选课失败，抛出异常触发重试
                    log.error("收到支付结果，添加选课失败, choosecourseId:{}", choosecourseId);
                    throw new RuntimeException("添加选课失败");
                }
                log.info("支付结果处理成功, choosecourseId:{}", choosecourseId);
            } else {
                log.warn("收到非支付结果消息或非课程订单消息, messageType:{}, businessKey2:{}", 
                        messageType, businessKey2);
            }
        } catch (Exception e) {
            log.error("消费支付结果消息异常: {}", message, e);
            // 抛出异常，RocketMQ 会自动重试（默认16次）
            throw new RuntimeException("消费支付结果消息失败", e);
        }
    }
}

