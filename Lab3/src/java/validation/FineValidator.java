package validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import violation.Violation;
import workWithDB.DAO;
import workWithDB.typeOfViolation.DAO_typeOfViol_Sqlite;
import workWithDB.typeOfViolation.ViolationType;


public class FineValidator implements ConstraintValidator<ValidFineValue, Violation> {

    @Inject @DAO_typeOfViol_Sqlite
    DAO<ViolationType> daoViolType;
    
    @Override
    public void initialize(ValidFineValue constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(Violation value, ConstraintValidatorContext context) {
        ViolationType vt = daoViolType.getByID(value.getViolationType());
        if(vt==null)
            return false;
        return value.getFineInUAH()>=vt.getMinFine() && value.getFineInUAH()<=vt.getMaxFine();
    }
    
}
