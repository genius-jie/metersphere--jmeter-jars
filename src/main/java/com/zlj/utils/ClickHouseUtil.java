package com.zlj.utils;

import ru.yandex.clickhouse.ClickHousePreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ClickHouseUtil {

    // 驱动器
    private static String driverName = "ru.yandex.clickhouse.ClickHouseDriver";

    private static Connection conn;

    private ClickHouseUtil() {
    }

    public static synchronized ClickHouseUtil getInstance(String url, String user, String password) {

        ClickHouseUtil clickHouseUtil = new ClickHouseUtil();

        try {
            Class.forName(driverName);
            clickHouseUtil.conn = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("connection fail ,please check your entities");
        }
        return clickHouseUtil;
    }


    public void close(AutoCloseable... closes) {
        for (AutoCloseable close : closes) {
            if (close != null) {
                try {
                    close.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close = null;
                }
            }
        }
    }

    public int insertorupdate( String sql, String... params) {
        int b = 0;
        ClickHousePreparedStatement pst = null;

        try {
            pst = (ClickHousePreparedStatement) conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                //query("select * from  t1  where  cbdw=? and data BETWEEN ? AND ?",new Object[]{new Long(cbdw),java.sql.Date1,java.sql.Date2})
                pst.setObject(i + 1, params[i]);
            }
            b = pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭下次就不能调用了
//            close(pst, conn);
        }

        return b;
    }

    public int delete(String sql, String... params) {
        int b=0 ;
        ClickHousePreparedStatement pst = null;

        try {
            pst = (ClickHousePreparedStatement) conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            b = pst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
//            close(pst, conn);
        }

        return b;
    }

    public ResultSet QueryResultSet(String sql, String... params) {
        ResultSet rst = null;
        ClickHousePreparedStatement pst = null;
        try {
            pst = (ClickHousePreparedStatement) conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            rst = pst.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
//            close(rst, pst, conn);
        }
        return rst;
    }
}
