package com.zlj.utils;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class CommonTest {

    @Test
    public void Test1() {
        List<Long> stlist = Arrays.asList(1656998391000l, 1656997191000l, 1656997791000l, 1656998392000l, 1656998393000l, 1656998993000l, 1656999593000l, 1657000193000l, 1657000793000l, 1657001393000l);
        stlist.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return (int) (o1 - o2);//o1-o2表示升序,o2-o1表示降序
            }
        });

        for (Long st : stlist) {
            String today_start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(st);
            System.out.println(today_start);
        }
       


    }
}