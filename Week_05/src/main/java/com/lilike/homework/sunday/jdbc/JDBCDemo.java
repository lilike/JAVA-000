package com.lilike.homework.sunday.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

/**
 * @Author llk
 * @Date 2020/11/18 14:59
 * @Version 1.0
 */
public class JDBCDemo {

    public static void main(String[] args) throws Exception {

//        Connection connection = getConnectionFromJdbc();

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPassword("root");
        dataSource.setUsername("root");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/jpress");
        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(100);
        dataSource.setReadOnly(false);
        dataSource.setConnectionTimeout(600000);

        Connection connection = dataSource.getConnection();

        // add
        add(connection);

        // delete
        delete(connection);

        // update
        update(connection);

        // query
        query(connection);

        // transaction
        transaction(connection);

        connection.close();

        dataSource.close();
    }

    private static Connection getConnectionFromJdbc() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/jpress", "root", "root");
    }

    private static void transaction(Connection connection) throws SQLException, InterruptedException {
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from account where id = ? for update");
        preparedStatement.setInt(1,1);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Thread.sleep(resultSet.getInt(2));
        System.out.println("休眠结束");
        resultSet.close();
        preparedStatement.close();
        connection.commit();

    }

    private static void query(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from account where id = ?");
        preparedStatement.setInt(1,1);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            Integer money = resultSet.getInt(2);
            System.out.println("Id =  " + id + " money :" + money);
        }
        resultSet.close();
        preparedStatement.close();

    }

    private static void update(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update account set money = ? where id = ?");
        preparedStatement.setInt(1,9000);
        preparedStatement.setInt(2,1);
        preparedStatement.execute();
        preparedStatement.close();
    }

    private static void delete(Connection connection) throws SQLException {
        String sql ="delete from account where id = ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1,2);
        pst.execute();
        pst.close();
    }

    private static void add(Connection connection) throws SQLException {

        PreparedStatement pst = connection.prepareStatement("insert into account(money) values (?)");
        pst.setInt(1,2000);
        pst.execute();
        pst.close();
    }



}
