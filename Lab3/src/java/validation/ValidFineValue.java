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
import javax.inject.Inject;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={FineValidator.class})
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface ValidFineValue {
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
    String message() default "Fine value should be between minimum and maximum fine for this type of violation.";
}