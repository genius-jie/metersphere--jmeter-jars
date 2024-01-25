package com.zlj.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class DamenClientUtilTest {
    @Test
    public void DamenSelectTest() {
        DamenClientUtil damenServer =  DamenClientUtil.getInstance("dm.jdbc.driver.DmDriver", "jdbc:dm://10.1.56.68:5236/DAMENG?allowMultiQueries=true", "SYSDBA", "SYSDBA");
        List<Map<String, String>> result = damenServer.select("select * from t1");
        System.out.println(result.size());
    }

    @Test
    public void DamenInsert() {
        DamenClientUtil damenServer = DamenClientUtil.getInstance("dm.jdbc.driver.DmDriver", "jdbc:dm://10.1.56.68:5236/DAMENG?allowMultiQueries=true", "SYSDBA", "SYSDBA");
        damenServer.insertOrUpdate("INSERT INTO t1(T_NAME,T_PRICE,T_DATE,T_Time) VALUES(concat('wang',1250)  ,CAST(1 as decimal(9,2)),CAST(NOW()  as DATE),DATEADD(n,1,sysdate-1));");
    }

    @Test
    public void DamenInsert2() {
        DamenClientUtil damenServer = DamenClientUtil.getInstance("dm.jdbc.driver.DmDriver", "jdbc:dm://10.1.56.68:5236/DAMENG?allowMultiQueries=true", "SYSDBA", "SYSDBA");
        /*调用存储过程*/
        damenServer.insertBycall("BEGIN\n" +
                "FOR v_count IN 101..110 Loop\n" +
                "INSERT INTO t1(T_NAME,T_PRICE,T_DATE,T_Time) VALUES(\n" +
                "concat('wang',v_count)  ,CAST(v_count as decimal(9,2)),CAST(NOW()  as DATE),DATEADD(n,v_count,sysdate-1)\n" +
                ");\n" +
                "END Loop;\n" +
                "COMMIT;\n" +
                "END;\n");
    }

    @Test
    public void DamenUpdate() {
        DamenClientUtil damenServer = DamenClientUtil.getInstance("dm.jdbc.driver.DmDriver", "jdbc:dm://10.1.56.68:5236/DAMENG?allowMultiQueries=true", "SYSDBA", "SYSDBA");
        damenServer.insertOrUpdate("update t1 set T_NAME='787878' where T_NAME='wang1250';");
    }

    @Test
      public void DamenDelete() {
        DamenClientUtil damenServer = DamenClientUtil.getInstance("dm.jdbc.driver.DmDriver", "jdbc:dm://10.1.56.68:5236/DAMENG?allowMultiQueries=true", "SYSDBA", "SYSDBA");
          damenServer.delete("delete from t1 where T_NAME='787878';");
      }

}