/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package workWithDB.action;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import violation.Violation;
import workWithDB.DAO;
import workWithDB.DAO;
import workWithDB.SQLiteConnection;
/**
 *
 * @author User
 */
@ApplicationScoped
@ActionWithViolDB
public class DAO_ActionWithViolationDB implements DAO<ActionWithViolationDB> {

    @Inject @SQLiteConnection
    Connection connection;
    
    @Override
    public boolean create(ActionWithViolationDB entity) {
        boolean operationIsSuccessful = false;
            try {
            String sql="INSERT INTO actions (`violationId`, `actionType`, `time`, `info`)  VALUES ( ?, ?, ?, ?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,entity.violationId);
            preparedStatement.setString(2,entity.actionType);
            preparedStatement.setString(3,entity.time.format(DateTimeFormatter.ISO_DATE_TIME));
            preparedStatement.setString(4,entity.info);
            operationIsSuccessful = preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return operationIsSuccessful;
    }

    @Override
    public boolean update(ActionWithViolationDB entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ActionWithViolationDB> getAll() {
        List<ActionWithViolationDB> result = new ArrayList<ActionWithViolationDB>();
        String sql = "SELECT * FROM actions";
        try {
            PreparedStatement stmnt = connection.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                String ID = rs.getString(1);
                String violId = rs.getString(2);
                String type = rs.getString(3);
                LocalDateTime ldt = LocalDateTime.parse(rs.getString(4), DateTimeFormatter.ISO_DATE_TIME);
                String info = rs.getString(5);
                result.add(new ActionWithViolationDB(violId, type, ldt, info));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }

    @Override
    public ActionWithViolationDB getByID(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
