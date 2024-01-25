package com.zlj.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;

public class KafkaSendUtil {

    private static KafkaProducer<String, String> producer;
    private static KafkaSendUtil kafkaSendUtil= new KafkaSendUtil();


    // 构造函数私有化，外面不能new
    private KafkaSendUtil() {
    }

    // 通过方法调用构造函数，单例初始化
    public static synchronized KafkaSendUtil getInstance(String brokerList) {
        if (producer == null) {
            /*消息先是被放进缓冲区，然后使用单独的线程发送到服务器端。
            缓冲池最大大小由参数buffer.memory控制，默认是32M，当生产消息的速度过快导致buffer满了的时候，将阻塞max.block.ms时间，超时抛异常，所以buffer的大小可以根据实际的业务情况进行适当调整。
            批量发送
            发送到缓冲池中消息将会被分为一个一个的batch，分批次的发送到broker 端，批次大小由参数batch.size控制，默认16KB。这就意味着正常情况下消息会攒够16KB时才会批量发送到broker端，所以一般减小batch大小有利于降低消息延时，增加batch大小有利于提升吞吐量。
            但是消息并不是必须要达到一个batch尺寸才会批量发送到服务端呢，Producer端提供了另一个重要参数linger.ms，用来控制batch最大的空闲时间，超过该时间的batch也会被发送到broker端。*/
            Properties properties = new Properties();
            properties.put("bootstrap.servers", brokerList);
            properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("acks", "0");
            properties.put("retries", "1");
            properties.put("batch.size", "33554432");
            properties.put("linger.ms", "1000");
            properties.put("buffer.memory", 33554432); // 缓冲内存大小，决定一次可以发送的最大消息数量，与 batch size 相关
            producer = new KafkaProducer<>(properties);
        }
        return kafkaSendUtil;
    }

    public void sendMsg(String topic, List<String> datas) {
        for (String data : datas) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
            producer.send(record); // 发送消息到Kafka topic
        }
    }
}