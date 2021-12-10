/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package violation;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import workWithDB.DAO;
import workWithDB.SQLite;

/**
 *
 * @author User
 */
public class SaveViolationService {
    @Inject @SQLite
    DAO<Violation> daoViolation;
    public void addViolation(@Observes @Added Violation v){
        daoViolation.create(v);
    }
    public void editViolation(@Observes @Edited Violation v){
        daoViolation.update(v);
    }
}
