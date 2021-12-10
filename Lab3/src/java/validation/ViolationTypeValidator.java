/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import workWithDB.DAO;
import workWithDB.typeOfViolation.DAO_typeOfViol_Sqlite;
import workWithDB.typeOfViolation.ViolationType;

/**
 *
 * @author 1
 */
public class ViolationTypeValidator implements ConstraintValidator<TypeOfViolation, String> {

    @Inject @DAO_typeOfViol_Sqlite
    DAO<ViolationType> daoViolType;
    
    private String typeOfLiability;
    
    @Override
    public void initialize(TypeOfViolation typeOfViolation) {
        this.typeOfLiability =  typeOfViolation.typeOfLiability();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<ViolationType> violTypes = daoViolType.getAll();
        Object[] validViolTypes = violTypes.stream().filter(vt -> vt.getTypeOfLiability().equals(this.typeOfLiability)).toArray();
        ArrayList<String> validViolTypesList = new ArrayList<>();
        for(int i=0; i<validViolTypes.length; i++){
            validViolTypesList.add(((ViolationType)validViolTypes[i]).getName());
        }
        return validViolTypesList.contains(value);
    }   
}
