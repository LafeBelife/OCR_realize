package jdbc;

import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) {
        // 数据库驱动
        String driver = "com.mysql.jdbc.Driver";
        // 数据库访问 url
        String url = "jdbc:mysql://localhost:3306/dandan";
        // 数据库用户名
        String name = "root";
        // 数据库密码
        String password = "123456";
        // 数据库连接对象
        Connection connection = null;
        // 数据库 操作对象
        Statement statement = null;
        // 数据库结果集对象
        ResultSet resultSet = null;

        ResultSetMetaData metaData = null;
        try {
            // 1.加载数据库驱动(成功加载后，会将 driver 实例注册到 driverManager 类中)
            Class.forName(driver);
            // 2.建立数据库连接
            connection = DriverManager.getConnection(url, name, password);
            // 3.获取数据库操作对象
            statement = connection.createStatement();
            // 4.定义sql语句
            String sql = "select * from sys_log where log_id='253'";
            // 5.执行sql操作
            resultSet = statement.executeQuery(sql);
            // 6.获取数据库操作结果集
            metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            //显示列,表格的表头
            for (int i = 1; i <= columns; i++) {
                System.out.print(metaData.getColumnName(i));
                System.out.print("\t\t");
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(resultSet.getString(i));
                    System.out.print("\t\t");
                }
                System.out.println();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 7.关闭数据库连接，减少资源浪费
            if (connection != null) {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
