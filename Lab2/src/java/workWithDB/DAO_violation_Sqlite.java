/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package workWithDB;

import violation.Violation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author User
 */
@SQLite
@RequestScoped
public class DAO_violation_Sqlite implements DAO<Violation>{
    
    @Inject @SQLiteConnection
    Connection connection;
    
    @Override
    public boolean create(Violation entity){
        boolean operationIsSuccessful = false;
            try {
            String sql="INSERT INTO violations (`id`, `carNumber`, `ownerName`, `violationType`, `dateTime`, `fine`)  VALUES ( ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,entity.getID());
            preparedStatement.setString(2,entity.getCarNum());
            preparedStatement.setString(3,entity.getOwnerName());
            preparedStatement.setString(4,entity.getViolationType());
            preparedStatement.setString(5,entity.getDateTimeAsString());
            preparedStatement.setString(6,Float.toString(entity.getFineInUAH()));
            operationIsSuccessful = preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return operationIsSuccessful;
    }
    
    @Override
    public boolean update(Violation entity) {
        boolean result = false;
        try {
            String sql="UPDATE violations  SET carNumber = ?, ownerName = ?, violationType = ?, dateTime = ?, fine = ?,   WHERE id == ?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,entity.getCarNum());
            preparedStatement.setString(2,entity.getOwnerName());
            preparedStatement.setString(3,entity.getViolationType());
            preparedStatement.setString(4,entity.getDateTimeAsString());
            preparedStatement.setString(5,Float.toString(entity.getFineInUAH()));
            preparedStatement.setString(6,entity.getID());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
   
    @Override
     public boolean delete(String id) {
        boolean result=false;
        try {
            String sql="DELETE FROM violations WHERE id == ?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            result=preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;

    }

    @Override
    public List<Violation> getAll(){
       List<Violation> result = new ArrayList<Violation>();
        try {
            String sql = "SELECT * FROM violations";
            Statement statement = connection.createStatement();
            Statement st2 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String carNumber = resultSet.getString(2);
                String ownerName = resultSet.getString(3);
                String violationType = resultSet.getString(4);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
                LocalDateTime dateTime = LocalDateTime.from(dtf.parse(resultSet.getString(5)));
                float fine = Float.parseFloat(resultSet.getString(6));
                Violation v = new Violation(ID, carNumber, ownerName, violationType, dateTime, fine);
                result.add(v);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    
    @Override
    public Violation getByID(String ID){
        if(getAll().stream().filter(x -> x.getID().equals(ID)).toArray().length==0){
            return null;
        }
        Violation[] result = (Violation[])getAll().stream().filter(x -> x.getID().equals(ID)).toArray();
        return result[0];
    }
}