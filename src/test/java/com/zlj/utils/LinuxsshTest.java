package com.zlj.utils;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

 class LinuxsshTest {


    @SneakyThrows
    @Test
    public void test1() {
        String host = "10.1.56.162";
        int port = 22;
        String user = "root";
        String password = "Uyun_123";
        String command1 = "";//如果有echo打印就会显示出来
//        String command = "javapath=`ps -ef |grep java|head -1|awk '{print $8}'`&&ln -fs $javapath /usr/local/bin/java";
//        String command = "curl -XDELETE -H \"Content-Type: application/json\"  -u admin:Admin@123 http://10.1.56.162:9200/zlj_datasync_test&&rm -rf  3.sh&&for i in $(seq 1 100) ;do let x=1626328955000+i*1000&& echo -e  \"{ \\\"index\\\": {}}\\n{ \\\"id\\\":$i,\\\"name\\\":\\\"zlj$i\\\", \\\"street\\\":\\\"Nagan\\\",\\\"city\\\":\\\"paprola\\\", \\\"time\\\":$x}\">>3.sh;done&&curl -u admin:Admin@123 -X POST http://10.1.56.162:9200/zlj_datasync_test513/_doc/_bulk?pretty  -H \"Content-Type: application/json\" --data-binary @3.sh ";
        String command ="echo 111";

        LinuxsshUtill linuxsshUtill = LinuxsshUtill.getInstance(host, port, user, password);
        System.out.println(linuxsshUtill.exeCommand(command));
        System.out.println(linuxsshUtill.exeCommand("echo 222"));
        System.out.println(linuxsshUtill.exeCommand("echo 333"));
        System.out.println(linuxsshUtill.exeCommand("echo 444"));
        String s ="\"\"";
    }
}