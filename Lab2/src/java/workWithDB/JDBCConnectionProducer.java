/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package workWithDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

/**
 *
 * @author User
 */
public class JDBCConnectionProducer {
    private static final String url = "jdbc:sqlite:F:\\JavaEE\\Lab_1-2\\violationDB.db";
    private static Connection connection;
    @Produces @SQLiteConnection
    private Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        if (connection == null){
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }
    private void closeConnection(@Disposes Connection con)throws SQLException{
        con.close();
    }
}
