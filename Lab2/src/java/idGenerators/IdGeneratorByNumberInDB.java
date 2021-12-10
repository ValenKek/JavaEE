/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idGenerators;

import javax.inject.Inject;
import workWithDB.ActionWithViolDB;
import workWithDB.ActionWithViolationDB;
import workWithDB.DAO;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author User
 */
@ApplicationScoped
@NumberInDB
public class IdGeneratorByNumberInDB implements IdGenerator{
    
    @Inject @ActionWithViolDB
    DAO<ActionWithViolationDB> daoAction;
    
    @Override
    public String generate(){
        String ID;
        ID = "viol_"+Integer.toString(daoAction.getAll().stream().filter(x -> x.actionType.equals("create")).toArray().length+1);
        return ID;
    }
}
