/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author User
 */
@NotNull(message="violation Type must be specified.")
@Size(max=1000)
@Constraint(validatedBy={ViolationTypeValidator.class})
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface TypeOfViolation {
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
    String message() default "Type of violation should be one of the <b>{typeOfLiability}</b> violation types from DB.";
    String typeOfLiability();
}
