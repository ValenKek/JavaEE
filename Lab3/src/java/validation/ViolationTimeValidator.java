package validation;

import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ViolationTimeValidator implements ConstraintValidator<ViolationTime, LocalDateTime>{

    @Override
    public void initialize(ViolationTime constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value.isBefore(LocalDateTime.now());
    }
    
}
