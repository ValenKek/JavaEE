/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workWithDB.typeOfViolation;

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
import violation.Violation;
import workWithDB.DAO;
import workWithDB.SQLiteConnection;
import workWithDB.violation.SQLite;

/**
 *
 * @author 1
 */
@RequestScoped
@DAO_typeOfViol_Sqlite
public class ViolationTypeDAO implements DAO<ViolationType> {
    @Inject @SQLiteConnection
    Connection connection;
    
    @Override
    public boolean create(ViolationType entity){
        boolean operationIsSuccessful = false;
            try {
            String sql="INSERT INTO typesOfViolation (`name`, `typeOfLiability`)  VALUES ( ?, ?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getTypeOfLiability());
            operationIsSuccessful = preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return operationIsSuccessful;
    }
    
    @Override
    public boolean update(ViolationType entity) {
        boolean result = false;
        try {
            String sql="UPDATE typesOfViolation SET typeOfLiability = ? WHERE name = ?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,entity.getTypeOfLiability());
            preparedStatement.setString(2,entity.getName());
            result = preparedStatement.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
   
    @Override
     public boolean delete(String name) {
        boolean result=false;
        try {
            String sql="DELETE FROM typesOfViolation WHERE name == ?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            result=preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;

    }

    @Override
    public List<ViolationType> getAll(){
       List<ViolationType> result = new ArrayList<ViolationType>();
        try {
            String sql = "SELECT name, typeOfLiability, minFine, maxFine FROM typesOfViolation";
            Statement statement = connection.createStatement();
            Statement st2 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String type = resultSet.getString(2);
                float minFine = resultSet.getFloat(3);
                float maxFine = resultSet.getFloat(4);
                ViolationType v = new ViolationType(name, type, minFine, maxFine);
                result.add(v);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    
    @Override
    public ViolationType getByID(String name){
        if(getAll().stream().filter(x -> x.getName().equals(name)).toArray().length==0){
            return null;
        }
        return (ViolationType)getAll().stream().filter(x -> x.getName().equals(name)).toArray()[0];
    }
}
