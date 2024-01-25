package com.zlj.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
class KafkaSendUtilTest {
    @Test
    public void kafkaSendTest1() {
        List<String> list = new ArrayList<String>();
//        list.add("{\"name\":\"zlj\",\"price\":3,\"timestamp\":1654764422080,\"__PROCESS_TIME__\":1654764422080}");
//        list.add("{\"name\":\"zlj\",\"price\":32,\"timestamp\":1654764422080,\"__PROCESS_TIME__\":1654764422080}");
//        KafkaSendUtil kafka= KafkaSendUtil.getInstance("10.1.56.161:9192");
//        kafka.sendMsg( "ods_zlj_out_78", list);

        for(int i=0;i<10;i++){
list.add("{\"id\":"+i+",\"name\":\"李四3\",\"age\":"+i+",\"doing\":\"偷袭人\",\"why\":\"执行刺激\",\"logintime\":123456}");
        }
        KafkaSendUtil kafka1= KafkaSendUtil.getInstance("10.1.11.52:9192");
        kafka1.sendMsg( "zljsync_kafka_ds", list);

    }
    @Test
        public void kafkaSendTest2() {
        List list = new ArrayList();
        for(int i=0;i<10;i++){
        	list.add("{ \"data\": { \"metrics\": [ { \"code\": \"zhanglijie1\", \"type\": \"gauge\", \"value\": ${__Random(1,80)}, \"tags\": { \"ip\":  \"10.1.66.66\" , \"device\":  \"device1\" , \"object\":  \"${ob}\" ,\"name\":\"新增指标组\" }, \"timestamp\": ${__time()} } ], \"object\": \"${ob}\" }, \"type\": \"metric\", \"uuid\": \"${__UUID}\", \"tenant\": \"e10adc3949ba59abbe56e057f20f88dd\", \"ingress_time\": ${__time()} }");
        }
        KafkaSendUtil kafka1= KafkaSendUtil.getInstance("10.1.11.52:9192");
        kafka1.sendMsg( "zljsync_kafka_ds",list);
    }
}