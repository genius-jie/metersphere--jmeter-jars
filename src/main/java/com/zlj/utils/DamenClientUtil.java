package com.zlj.utils;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamenClientUtil {
    //定义单例
    private static DamenClientUtil clientUtil;
    private static Connection connection = null;

    //构造函数私有化，外面不能new
    @SneakyThrows
    private DamenClientUtil(String jdbcString, String urlString, String userName, String passWord) {
        Class.forName(jdbcString);
        connection = DriverManager.getConnection(urlString, userName, passWord);
    }
    //通过方法调用构造函数，单例初始化
    public static synchronized DamenClientUtil getInstance(String jdbcString, String urlString, String userName, String passWord) {
        if (clientUtil == null) {
            clientUtil = new DamenClientUtil(jdbcString, urlString, userName, passWord);
        }
        return clientUtil;
    }


    public void printfResult(Connection connection, String sqlStr) throws SQLException {
        String queryStr = sqlStr;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(queryStr);
        displayResultSet(rs);
        stmt.close();
    }

    public void displayResultSet(ResultSet rs) throws SQLException {

        // 取得结果集元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        // 取得结果集所包含的列数
        int numCols = rsmd.getColumnCount();
           /* //列头
            for (int i = 1; i <= numCols; i++) {
                if (i > 1) {
                    System.out.print(",");
                }
                System.out.print(rsmd.getColumnLabel(i));
            }*/
        System.out.println("");
        //所有数据
        while (rs.next()) {
            for (int i = 1; i <= numCols; i++) {
                if (i > 1) {
                    System.out.print(",");
                }
                // 普通字段
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }


    public List<Map<String, String>> select(String sql) {
        /*
        1. 创建语句对象；
        2. 执行查询；
        3. 显示结果集；
        4. 关闭结果集；
        5. 关闭语句对象。
         */
        System.out.println("---------------- 查 询 开 始 ----------------");
        List<Map<String, String>> tableColInfo = new ArrayList<Map<String, String>>();
        String queryStr = sql;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryStr);
//                displayResultSet(rs);
            ResultSetMetaData rsmd = rs.getMetaData();
            // 取得结果集所包含的列数
            int numCols = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= numCols; i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getString(i));
                }
                tableColInfo.add(map);
            }
            rs.close();
            stmt.close();
            System.out.println("---------------- 查 询 结 束 ----------------\n");
            connection.close();
            return tableColInfo;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void insertOrUpdate(String sql) {
        /*
         * 1. 构造插入数据库语句；
         * 2. 创建语句对象；
         * 3. 为参数赋值；
         * 4. 执行语句；
         * 5. 关闭语句。
         * */
        System.out.println("---------------- 增 加 开 始 ----------------\n");
//            CallableStatement statement = connection.prepareCall("{ call proc_adder(2,?,?)}");
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
//                printfResult(connection, "select * from XXX.XXX.XXX");
            System.out.println("---------------- 增 加 结 束 ----------------\n");
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertBycall(String sql) {
        /*
         * 1. 构造插入数据库语句；
         * 2. 创建语句对象；
         * 3. 为参数赋值；
         * 4. 执行语句；
         * 5. 关闭语句。
         * */
        System.out.println("---------------- 增 加 开 始 ----------------\n");
        try {
            CallableStatement stmt = connection.prepareCall(sql);
//                Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
//                printfResult(connection, "select * from XXX.XXX.XXX");
            System.out.println("---------------- 增 加 结 束 ----------------\n");
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(String sql) {
        /*
         * 1. 删除数据语句；
         * 2. 创建对象语言；
         * 3. 执行语句；
         * 4. 关闭语句。
         *
         * */
        System.out.println("---------------- 删 除 开 始 ----------------\n");
        //关闭连接
        try {
            Statement deleteSm = connection.createStatement();
            deleteSm.execute(sql);
            deleteSm.close();
            System.out.println("---------------- 删 除 结 束 ----------------\n");
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
